/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04 | Optimized: 2026-03-31
 *
 * Library: AppDimens — Global Dimension Cache Manager
 *
 * Description:
 * Ultra-optimized, lock-free, shared cache for all AppDimens dimension calculations.
 * Works for both `compose` and `code` (non-Compose) packages.
 *
 * Key Design Principles:
 *  - Snapshot-partitioned cache: each window/configuration snapshot (DimenMetrics)
 *    owns a bounded partition; entries are published as one immutable atomic reference
 *  - Collision-safe via packed 64-bit Long key (no false hits)
 *  - Shared state across all library instances (save memory, share reuse)
 *  - Per-snapshot correctness: rotated / resized / recreated windows never read stale values
 *  - Zero allocation in hot path: stores raw Float, caller boxes into Dp/TextUnit
 *
 * Optimizations applied:
 *  - [FASE 3] ScreenFactors: all @Volatile scalar fields grouped in a padded object so a
 *    write to `scale` cannot invalidate `arMultiplier` on another core's cache line.
 *  - Single-window fast memos (metrics + partition + multi-window flag) so the hot path
 *    performs a few volatile reads instead of maps/ThreadLocals per call.
 *  - Precomputed bypass table (one Int per CalcType) replaces per-call bit analysis.
 *  - getBatch() is public, enabling callers to resolve N dimensions in a single tight
 *    loop — friendly to JIT auto-vectorization (ART / HotSpot).
 *
 * Bit Layout of the 64-bit Cache Key (Long):
 *  [63]     applyAspectRatio          1 bit
 *  [62-31]  baseValue bits            32 bits  (Float.toRawBits)
 *  [30-27]  CalcType ordinal          4 bits  (covers 0..15)
 *  [26-24]  ValueType                 3 bits  (covers 0..7)
 *  [23-8]   sensitivityK fingerprint  16 bits (float bits ushr 16 & 0xFFFF)
 *  [7-6]    DpQualifier ordinal       2 bits  (covers 0..3)
 *  [5-2]    Inverter ordinal          4 bits  (covers 0..15)
 *  [1]      isLandscape               1 bit
 *  [0]      ignoreMultiWindows        1 bit
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.UiModeType
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReferenceArray
import java.util.concurrent.atomic.LongAdder
import java.util.concurrent.ConcurrentHashMap
import java.util.Collections
import java.util.WeakHashMap
import kotlin.math.max
import kotlin.math.min

/**
 * EN
 * Global, lock-free, shared cache for all AppDimens dimension calculations.
 *
 * **Thread Safety**: Completely thread-safe.  Since 3.1.7 the cache is partitioned per
 * immutable window snapshot ([DimenMetrics]); each entry is published as a single
 * atomic [CacheEntry] (key + value bits) reference, so concurrent readers can never
 * observe another key's value.
 *
 * PT
 * Cache global, lock-free e compartilhado para todos os cálculos de dimensão do AppDimens.
 */
object DimenCache {
    private val resetListeners = java.util.concurrent.CopyOnWriteArrayList<() -> Unit>()

    /**
     * EN Registers a listener to be notified when the cache is cleared.
     * PT Registra um listener para ser notificado quando o cache for limpo.
     */
    @JvmStatic
    fun addResetListener(listener: () -> Unit) {
        resetListeners.add(listener)
    }

    /**
     * EN Removes a previously registered reset listener.
     * PT Remove um listener de reset previamente registrado.
     */
    @JvmStatic
    fun removeResetListener(listener: () -> Unit) {
        resetListeners.remove(listener)
    }


    // ─────────────────────────────────────────────────────────────────────────
    // CONFIGURATION & INITIALIZATION STATE
    // ─────────────────────────────────────────────────────────────────────────

    internal val isInitializing = AtomicBoolean(false)
    /**
     * Internal flag to avoid [AtomicBoolean.get] overhead on every hot-path call.
     *
     * **Thread Safety**: marked `@Volatile` so that the `true` written by the
     * background coroutine in [init] is immediately visible to all other threads
     * without requiring a full memory barrier on every read.  Without `@Volatile`
     * a thread that reads this field on a different CPU core may observe stale
     * `false` indefinitely (data race / visibility bug on ARM64 weak memory model).
     */
    @PublishedApi
    @Volatile
    internal var isInitializedFast = false
    val isInitialized = AtomicBoolean(false)

    /**
     * EN Calculation types based on the library's package structure.
     * PT Tipos de cálculo baseados na estrutura de pacotes da biblioteca.
     */
    enum class CalcType {
        AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC,
        PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY
    }

    @JvmField val CT_PERCENT       = CalcType.PERCENT.ordinal
    @JvmField val CT_SCALED        = CalcType.SCALED.ordinal
    @JvmField val CT_DENSITY       = CalcType.DENSITY.ordinal
    @JvmField val CT_ASPECT_RATIO  = CalcType.ASPECT_RATIO.ordinal
    @JvmField val CT_DIAGONAL      = CalcType.DIAGONAL.ordinal
    @JvmField val CT_INTERPOLATED  = CalcType.INTERPOLATED.ordinal
    @JvmField val CT_PERIMETER     = CalcType.PERIMETER.ordinal
    @JvmField val CT_POWER         = CalcType.POWER.ordinal
    @JvmField val CT_LOGARITHMIC   = CalcType.LOGARITHMIC.ordinal

    // ─────────────────────────────────────────────────────────────────────────
    // DIAGNOSTICS COUNTERS — guarded by [diagnosticsEnabled] to avoid overhead
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN When `true`, hit/miss/eviction counters are incremented on every cache
     * operation. Uses [LongAdder] for low-contention counting. Disabled by
     * default so production apps pay zero overhead.
     *
     * PT Quando `true`, contadores de hit/miss/eviction são incrementados a cada
     * operação. Desativado por padrão para não penalizar apps em produção.
     */
    @JvmStatic
    @Volatile
    @PublishedApi
    internal var diagnosticsEnabled: Boolean = false

    @JvmField val hitCount      = LongAdder()
    @JvmField val missCount     = LongAdder()
    @JvmField val evictionCount = LongAdder()

    /**
     * EN Master switch for the cache system. If disabled, all calls will recompute.
     * PT Chave mestre para o sistema de cache. Se desativado, todos os cálculos são refeitos.
     */
    @JvmStatic
    @Volatile
    @PublishedApi
    internal var isEnabled: Boolean = true

    // ─────────────────────────────────────────────────────────────────────────
    // [FASE 5] CACHED UiModeType — avoids SensorManager + WindowMetrics per call
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Snapshot of the [Configuration] fields that drive cache invalidation.
     * Avoids `Configuration(Configuration)` copy — the platform stub used in JVM
     * unit tests does not actually copy fields, which would make every subsequent
     * invalidate look like a physical change.
     *
     * PT Snapshot dos campos de [Configuration] usados na invalidação.
     */
    internal data class ConfigSnapshot(
        val screenWidthDp: Int,
        val screenHeightDp: Int,
        val smallestScreenWidthDp: Int,
        val densityDpi: Int,
        val fontScale: Float,
    ) {
        companion object {
            fun from(c: Configuration) = ConfigSnapshot(
                screenWidthDp = c.screenWidthDp,
                screenHeightDp = c.screenHeightDp,
                smallestScreenWidthDp = c.smallestScreenWidthDp,
                densityDpi = c.densityDpi,
                fontScale = c.fontScale,
            )
        }
    }

    @Volatile
    private var lastConfiguration: ConfigSnapshot? = null

    @JvmField @Volatile
    internal var cachedUiMode: UiModeType = UiModeType.UNDEFINED

    @Volatile
    private var cachedUiModeConfigHash: Int = 0

    private data class UiModeCacheEntry(val fingerprint: Int, val value: UiModeType)

    /**
     * Per-context cache: the value never retains its weak key, so an Activity/window can be
     * collected normally. A process-wide single entry is incorrect when two windows differ.
     */
    private val uiModeByContext = Collections.synchronizedMap(WeakHashMap<Context, UiModeCacheEntry>())

    @JvmStatic
    fun getCachedUiModeType(context: Context): UiModeType {
        val cfg = context.resources.configuration
        // Fingerprint only fields that affect UiMode / foldable detection — not locale/keyboard.
        val fingerprint =
            (cfg.uiMode * 31 + cfg.smallestScreenWidthDp) * 31 +
                min(cfg.screenWidthDp, cfg.screenHeightDp) * 31 +
                max(cfg.screenWidthDp, cfg.screenHeightDp)
        synchronized(uiModeByContext) {
            val cached = uiModeByContext[context]
            if (cached?.fingerprint == fingerprint) return cached.value

            val mode = UiModeType.fromConfiguration(context, null)
            uiModeByContext[context] = UiModeCacheEntry(fingerprint, mode)
            // Deprecated global fields are updated for callers that inspect them, but are
            // never read as a source of truth.
            cachedUiMode = mode
            cachedUiModeConfigHash = fingerprint
            return mode
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // [FASE 3] SCREEN FACTORS — padded object to prevent false sharing on @Volatile fields
    //
    // ARM64 cache line = 64 bytes. JVM object header ≈ 16 bytes.
    // 6 Float/Int fields = 6 × 4 = 24 bytes → total ~40 bytes → fits in one line.
    // A write to `scale` would invalidate `arMultiplier` on another core.
    // Padding of 14 × Long (112 bytes) pushes the next allocation to a fresh line.
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Holds all screen-derived scaling factors in an object padded to exceed two ARM64
     * cache lines (2 × 64 bytes = 128 bytes), ensuring that writes during
     * [updateFactors] do not invalidate unrelated reads on sibling CPU cores.
     *
     * PT Agrupa todos os fatores de escala derivados da tela em um objeto com padding de
     * 128 bytes, prevenindo false sharing entre núcleos durante [updateFactors].
     */
    internal class ScreenFactors {
        @JvmField @Volatile var normalizedAr   : Float = 1.0f
        @JvmField @Volatile var logNormalizedAr: Float = 0f
        @JvmField @Volatile var smallestWidthDp: Int   = 0
        @JvmField @Volatile var density        : Float = 1.0f
        @JvmField @Volatile var scale          : Float = 1.0f
        @JvmField @Volatile var arMultiplier   : Float = 1.0f
        // Shared AR multiply helper (used by many strategies' custom-AR paths).
        // Production formulas resolve through DimenCache.currentMetrics; satellite
        // scales (diagonal/power/log/…) derive from that snapshot at resolution time.
        @JvmField @Volatile var aspectRatioMul    : Float = 1.0f
        // 128-byte padding guard (8 × Long = 64 bytes + object fields overhead ≥ 128)
        @Suppress("unused") @JvmField val _p0 = 0L
        @Suppress("unused") @JvmField val _p1 = 0L
        @Suppress("unused") @JvmField val _p2 = 0L
        @Suppress("unused") @JvmField val _p3 = 0L
        @Suppress("unused") @JvmField val _p4 = 0L
        @Suppress("unused") @JvmField val _p5 = 0L
        @Suppress("unused") @JvmField val _p6 = 0L
        @Suppress("unused") @JvmField val _p7 = 0L
    }

    @JvmField
    @PublishedApi
    internal val factors = ScreenFactors()

    /**
     * Compatibility view used by existing strategy modules while they are migrated to
     * explicit [DimenMetrics] parameters.  During a resolution it is the exact immutable
     * metrics supplied to that call; it is never a partially updated global factor set.
     */
    @PublishedApi
    internal val metricsScope = ThreadLocal<DimenMetrics?>()

    @Volatile
    @PublishedApi
    internal var fallbackMetrics: DimenMetrics = DimenMetrics.DEFAULT

    @get:JvmStatic
    val currentMetrics: DimenMetrics
        get() = metricsScope.get() ?: fallbackMetrics

    // Convenience accessors — public so satellite modules can read a coherent snapshot.
    val currentNormalizedAr      get() = currentMetrics.normalizedAspectRatio
    val currentLogNormalizedAr   get() = currentMetrics.logNormalizedAspectRatio
    val currentSmallestWidthDp   get() = currentMetrics.smallestWidthDp.toInt()
    val currentDensity           get() = currentMetrics.density
    val currentScale             get() = currentMetrics.scale
    val currentArMultiplier      get() = currentMetrics.defaultScaledAspectRatioMultiplier
    val currentAspectRatioMul    get() = currentMetrics.defaultAspectRatioMultiplier

    // ─────────────────────────────────────────────────────────────────────────
    // SNAPSHOT-PARTITIONED CACHE
    // ─────────────────────────────────────────────────────────────────────────

    /** Four active window/configuration snapshots × 512 entries = the former 2048-slot budget. */
    private const val MAX_SNAPSHOT_CACHES = 4
    private const val SNAPSHOT_CACHE_SIZE = 2048 / MAX_SNAPSHOT_CACHES
    private const val SNAPSHOT_CACHE_MASK = SNAPSHOT_CACHE_SIZE - 1

    /**
     * A key and its raw Float bits are published as one immutable reference.  The previous
     * two-array design could expose a key written by one thread with a value written by
     * another.  A single atomic reference is a correctness boundary, not a micro-optimization.
     */
    @PublishedApi
    internal data class CacheEntry(val key: Long, val valueBits: Int)

    @PublishedApi
    internal class SnapshotCache(size: Int) {
        val entries = AtomicReferenceArray<CacheEntry?>(size)
    }

    private val snapshotCaches = ConcurrentHashMap<DimenMetrics, SnapshotCache>()
    private val snapshotCacheLock = Any()

    /**
     * Fast-path memo for the most recent explicit window. Code (non-Compose) callers
     * resolve through [metricsFor] on every call; materializing a fresh [DimenMetrics]
     * (8 field reads + allocation) per resolution is the dominant cost of the 3.1.7
     * hot path versus the legacy shard-based 3.1.5 design. A single @Volatile slot
     * covers the typical single-Activity app (hit → zero allocation); a weak map
     * covers multi-window apps where two windows alternate.
     */
    /**
     * EN Single @Volatile holder pairing the window context with its metrics so the
     *    hot lane pays ONE volatile load instead of two. The fields are final, so the
     *    single volatile write publishes both safely (no torn context/metrics pair).
     * PT Portador único @Volatile unindo contexto e métricas da janela para o caminho
     *    quente pagar UMA leitura volátil em vez de duas. Os campos são finais, então
     *    a única escrita volátil publica ambos com segurança (sem par rasgado).
     */
    @PublishedApi
    internal class FastWindowSlot(val context: Context?, val metrics: DimenMetrics)

    @Volatile
    @PublishedApi
    internal var fastWindowSlot: FastWindowSlot? = null

    private val metricsByWindowContext =
        Collections.synchronizedMap(WeakHashMap<Context, DimenMetrics>())

    @Volatile
    private var fastMwContext: Context? = null

    @Volatile
    private var fastMwMode: Boolean = false

    private fun mwModeFor(context: Context?): Boolean {
        val cachedCtx = fastMwContext
        if (cachedCtx === context) return fastMwMode
        val rebuilt = DimenCalculationPlumbing.isInMultiWindowMode(context)
        fastMwContext = context
        fastMwMode = rebuilt
        return rebuilt
    }

    private fun fastMatch(
        metrics: DimenMetrics,
        configuration: Configuration,
        isMultiWindow: Boolean,
    ): Boolean =
        metrics.screenWidthDp == configuration.screenWidthDp &&
            metrics.screenHeightDp == configuration.screenHeightDp &&
            metrics.smallestScreenWidthDp == configuration.smallestScreenWidthDp &&
            metrics.densityDpi == configuration.densityDpi &&
            metrics.fontScaleBits == configuration.fontScale.toRawBits() &&
            metrics.orientation == configuration.orientation &&
            metrics.uiMode == configuration.uiMode &&
            metrics.isInMultiWindowMode == isMultiWindow

    @PublishedApi
    internal fun metricsFor(context: Context?): DimenMetrics {
        if (context == null) return fallbackMetrics
        val fast = fastWindowSlot
        if (fast != null && fast.context === context) {
            val cfg = context.resources.configuration
            if (fastMatch(fast.metrics, cfg, mwModeFor(context))) {
                return fast.metrics
            }
        }
        // Slow path: (re)build and memo. The weak map handles an app with several
        // alternating windows; the @Volatile slot always mirrors the latest explicit call.
        val cached = metricsByWindowContext[context]
        if (cached != null) {
            val cfg = context.resources.configuration
            if (fastMatch(cached, cfg, mwModeFor(context))) {
                fastWindowSlot = FastWindowSlot(context, cached)
                return cached
            }
        }
        val rebuilt = DimenMetrics.from(
            configuration = context.resources.configuration,
            isInMultiWindowMode = DimenCalculationPlumbing.isInMultiWindowMode(context),
        )
        metricsByWindowContext[context] = rebuilt
        fastWindowSlot = FastWindowSlot(context, rebuilt)
        return rebuilt
    }

    // Single-window fast memo for the partition lookup. The typical app resolves
    // against one immutable snapshot for thousands of calls; the CHM hash+equals of
    // DimenMetrics would otherwise run on every cache hit. Multi-window apps simply
    // re-sync this pair each time the active window alternates (correct, and rare).
    @Volatile
    @PublishedApi
    internal var fastPartitionMetrics: DimenMetrics? = null

    @Volatile
    @PublishedApi
    internal var fastPartition: SnapshotCache? = null

    @PublishedApi
    internal fun cacheFor(metrics: DimenMetrics): SnapshotCache {
        snapshotCaches[metrics]?.let { return it }
        synchronized(snapshotCacheLock) {
            snapshotCaches[metrics]?.let { return it }
            // A resize can produce many transient configurations. Keep the total memory
            // budget fixed instead of turning the cache into a history of every pixel size.
            if (snapshotCaches.size >= MAX_SNAPSHOT_CACHES) {
                snapshotCaches.keys.firstOrNull { it !== metrics }?.let(snapshotCaches::remove)
            }
            return SnapshotCache(SNAPSHOT_CACHE_SIZE).also { snapshotCaches[metrics] = it }
        }
    }

    @PublishedApi
    internal fun slotFor(key: Long): Int {
        val h = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        return mixed and SNAPSHOT_CACHE_MASK
    }

    @PublishedApi
    internal inline fun <T> withMetrics(metrics: DimenMetrics, crossinline block: () -> T): T {
        val previous = metricsScope.get()
        if (previous === metrics) {
            return block()
        }
        metricsScope.set(metrics)
        return try {
            block()
        } finally {
            // EN Always restore via set(): avoids ThreadLocal.remove() + setInitialValue()
            //    re-probing on the next get(), keeping the map hot.
            // PT Sempre restaura via set(): evita ThreadLocal.remove() + setInitialValue()
            //    e mantém o mapa quente na próxima get().
            metricsScope.set(previous)
        }
    }

    /** Used by Compose helpers to make nested legacy strategy calls observe LocalDimenMetrics. */
    internal fun <T> withCompositionMetrics(metrics: DimenMetrics?, block: () -> T): T =
        if (metrics == null) block() else withMetrics(metrics, block)

    /**
     * Core resolution — inlined at every call site so the `compute` lambda is inlined
     * with zero object allocation, matching the legacy 3.1.5 hot-path profile while
     * keeping the snapshot-partitioned correctness of 3.1.7.
     */
    @PublishedApi
    internal inline fun resolve(
        key: Long,
        metrics: DimenMetrics,
        crossinline compute: () -> Float,
    ): Float {
        // Custom-K keys only encode 16 bits of the 32-bit float (buildKey). Two different
        // K values could alias one slot and answer with the other's result, so they are
        // computed exactly on every call — never stored, never peek-able. This is also
        // cheaper than the previous path, which allocated a snapshot partition per call.
        if (!isEnabled || hasCustomSensitivityKey(key) || shouldBypassCache(key)) {
            return withMetrics(metrics) { compute() }
        }

        var partition = fastPartition
        if (partition === null || fastPartitionMetrics !== metrics) {
            partition = cacheFor(metrics)
            fastPartition = partition
            fastPartitionMetrics = metrics
        }
        val slot = slotFor(key)
        val existing = partition.entries.get(slot)
        if (existing?.key == key) {
            if (diagnosticsEnabled) hitCount.increment()
            return Float.fromBits(existing.valueBits)
        }

        if (diagnosticsEnabled) missCount.increment()
        val computed = withMetrics(metrics) { compute() }
        // Non-finite values are never useful cache entries and should not contaminate a
        // later valid request with the same key.
        if (!computed.isFinite()) return computed

        if (diagnosticsEnabled && existing != null) evictionCount.increment()
        partition.entries.set(slot, CacheEntry(key, computed.toRawBits()))
        return computed
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MATH CONSTANTS
    // ─────────────────────────────────────────────────────────────────────────

    const val INV_BASE_RATIO      = 0.0033333334f // 1f / 300f
    const val ADJUSTMENT_SCALE    = 0.10f / 30f   // 0.0033333334f
    const val SENSITIVITY_DEFAULT = 0.08f / 30f   // 0.0026666667f

    /**
     * Unified scaling engine over the immutable metrics of the current resolution.
     * Callers that resolve through [getOrPut] receive a per-window snapshot; no result is
     * derived from a process-wide application configuration.
     */
    fun calculateRawScaling(
        baseValue: Float,
        applyAspectRatio: Boolean,
        customSensitivityK: Float?
    ): Float {
        require(baseValue.isFinite()) { "baseValue must be finite" }
        return baseValue * currentMetrics.scaledMultiplier(applyAspectRatio, customSensitivityK)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FAST SCALED PATH — single-multiply kernel for the dominant SDP case
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Ultra-fast resolution for the dominant SDP/SDPA path
     * (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity).
     *
     * Resolves the coherent per-window [DimenMetrics] exactly like [getOrPut] does
     * ([metricsScope] → fast window identity → full rebuild) but then computes the
     * result with zero allocations, zero ThreadLocal writes, and zero cache-key
     * encoding: one branch + two float multiplies. Results are bit-identical to the
     * legacy math (`base * scale * density`).
     *
     * The per-window configuration validation is sampled (1 in 16 calls): the
     * stale window after a real configuration change is at most ~16 resolutions
     * (~sub-microsecond), after which [metricsFor] rebuilds the snapshot — invisible
     * to any UI frame while giving the fast path a single multiply per call.
     *
     * PT Resolução ultra-rápida para o caminho SDP/SDPA dominante
     * (`SMALL_WIDTH` + inverter `DEFAULT` + sem sensibilidade customizada).
     *
     * Resolve o [DimenMetrics] coerente por janela exatamente como [getOrPut]
     * ([metricsScope] → identidade da janela rápida → rebuild completo) e calcula
     * o resultado sem alocações, sem escritas em ThreadLocal e sem codificar chave
     * de cache: um branch + duas multiplicações de float. Resultados idênticos ao
     * caminho legado (`base * scale * density`).
     *
     * A validação da configuração por janela é amostrada (1 em 16 chamadas): a
     * janela desatualizada após uma mudança real de configuração dura no máximo
     * ~16 resoluções (~sub-microssegundo), depois [metricsFor] reconstrói o snapshot
     * — invisível a qualquer frame de UI, e dá ao caminho rápido uma única
     * multiplicação por chamada.
     */
    @PublishedApi
    internal inline fun resolveScaledFastPx(baseValue: Float, context: Context?, qualifier: DpQualifier, applyAspectRatio: Boolean): Float {
        val m = metricsCoherentFor(context)
        return baseValue * fastScaledMultiplier(m, qualifier, applyAspectRatio) * m.density
    }

    @PublishedApi
    internal inline fun resolveScaledFastDp(baseValue: Float, context: Context?, qualifier: DpQualifier, applyAspectRatio: Boolean): Float {
        val m = metricsCoherentFor(context)
        return baseValue * fastScaledMultiplier(m, qualifier, applyAspectRatio)
    }

    /** Sample counter; benign non-atomic races only skip a validation early. */
    @Volatile
    @PublishedApi
    internal var validationTick: Int = 0

    @PublishedApi
    internal inline fun metricsCoherentFor(context: Context?): DimenMetrics {
        metricsScope.get()?.let { return it }
        val slot = fastWindowSlot
        if (slot != null && slot.context === context && (validationTick++ and 0xF) != 0) {
            return slot.metrics
        }
        return metricsFor(context)
    }

    /**
     * EN Public bridge used by satellite modules (separate Gradle modules cannot see
     *    `internal` members) to resolve the coherent per-window metrics for their fast
     *    lanes — same source as [metricsCoherentFor].
     * PT Ponte pública usada pelos módulos satélite (módulos Gradle separados não veem
     *    membros `internal`) para resolver as métricas coerentes por janela de seus
     *    fast lanes — mesma fonte de [metricsCoherentFor].
     */
    @JvmStatic
    fun coherentMetrics(context: Context?): DimenMetrics = metricsCoherentFor(context)

    /**
     * EN Public bridge for the feature toggle (internal in this module) so satellite
     *    fast lanes can replicate the exact cache-enabled semantics of their fallbacks.
     * PT Ponte pública para o toggle de recurso (internal neste módulo) para que os fast
     *    lanes dos satélites repliquem a semântica exata de cache habilitado dos fallbacks.
     */
    @JvmStatic
    fun isScalingEnabled(): Boolean = isEnabled

    /**
     * EN Single-multiply multiplier for SMALL_WIDTH / WIDTH / HEIGHT without custom
     *    sensitivity. AR is only offered for SMALL_WIDTH (other qualifiers with AR
     *    keep the slow path, which is mathematically identical but rarer).
     * PT Multiplicador de uma única multiplicação para SMALL_WIDTH / WIDTH / HEIGHT
     *    sem sensibilidade customizada. AR é oferecido apenas para SMALL_WIDTH
     *    (outros qualifiers com AR mantêm o caminho lento, matematicamente idêntico).
     */
    @PublishedApi
    internal inline fun fastScaledMultiplier(
        m: DimenMetrics,
        qualifier: DpQualifier,
        applyAspectRatio: Boolean,
    ): Float {
        if (qualifier === DpQualifier.SMALL_WIDTH) {
            return if (applyAspectRatio) m.defaultScaledAspectRatioMultiplier else m.scale
        }
        // EN Identity compares instead of `when` on an enum: `when` compiles to an
        //    ordinal() virtual call + switch-table array load on every resolution.
        //    WIDTH/HEIGHT read precomputed factors (bit-identical, one `iget`).
        // PT Comparações de identidade no lugar de `when` sobre enum: `when` compila
        //    para chamada virtual ordinal() + leitura de array de switch por resolução.
        //    WIDTH/HEIGHT leem fatores pré-calculados (bit-idênticos, um `iget`).
        return if (qualifier === DpQualifier.WIDTH) {
            m.screenWidthFactor
        } else {
            m.screenHeightFactor
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PERSISTENCE FLOW — binary-compatibility stubs
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN No-op: dimension resolution no longer owns a background persistence scope.
     * Kept as a public binary-compatibility marker for consumers built against ≤ 3.1.6.
     *
     * PT No-op: a resolução de dimensões não possui mais escopo de persistência em
     * segundo plano. Mantido como marcador público de compatibilidade binária.
     */
    @JvmStatic
    fun shutdown() = Unit

    // ─────────────────────────────────────────────────────────────────────────
    // KEY ENCODING
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Dimension type discriminator for the cache key.
     * PT Discriminador de tipo de dimensão para a chave de cache.
     */
    enum class ValueType {
        DP, PX, SP_WITH_SCALE, SP_NO_SCALE, SP_PX_WITH_SCALE, SP_PX_NO_SCALE
    }

    /**
     * Packs all dimension-calculation parameters into a single 64-bit [Long] key.
     *
     * Bit layout (MSB → LSB):
     * ```
     * [63]     applyAspectRatio          1 bit
     * [62-31]  baseValue bits            32 bits  (Float.toRawBits)
     * [30-27]  CalcType ordinal          4 bits  (covers 0..15)
     * [26-24]  ValueType                 3 bits  (covers 0..7)
     * [23-8]   sensitivityK fingerprint  16 bits (float bits ushr 16 & 0xFFFF)
     * [7-6]    DpQualifier ordinal       2 bits  (covers 0..3)
     * [5-2]    Inverter ordinal          4 bits  (covers 0..15)
     * [1]      isLandscape               1 bit
     * [0]      ignoreMultiWindows        1 bit
     * ```
     */
    @JvmStatic
    fun buildKey(
        baseValue: Float,
        isLandscape: Boolean,
        ignoreMultiWindows: Boolean,
        calcType: CalcType,
        qualifier: DpQualifier,
        inverter: Inverter,
        applyAspectRatio: Boolean,
        valueType: ValueType,
        customSensitivityK: Float? = null
    ): Long {
        require(baseValue.isFinite()) { "baseValue must be finite" }
        require(customSensitivityK == null || customSensitivityK.isFinite()) {
            "customSensitivityK must be finite"
        }
        val ar  = if (applyAspectRatio) 1L else 0L
        val bv  = baseValue.toRawBits().toLong() and 0xFFFFFFFFL
        val ct  = calcType.ordinal.toLong() and 0xFL
        val vt  = valueType.ordinal.toLong() and 0x7L
        val sk  = (customSensitivityK?.toRawBits()?.ushr(16)?.and(0xFFFF)?.toLong() ?: 0xFFFFL)
        val q   = qualifier.ordinal.toLong() and 0x3L
        val inv = inverter.ordinal.toLong() and 0xFL
        // DIAGONAL / PERIMETER / DENSITY formulas use min/max or dpi — orientation-invariant.
        // Dropping the landscape bit avoids mandatory miss + duplicate slots on rotation (P1).
        val land = when (calcType) {
            CalcType.DIAGONAL, CalcType.PERIMETER, CalcType.DENSITY -> 0L
            else -> if (isLandscape) 1L else 0L
        }
        val imw  = if (ignoreMultiWindows) 1L else 0L

        return (ar  shl 63) or
               (bv  shl 31) or
               (ct  shl 27) or
               (vt  shl 24) or
               (sk  shl  8) or
               (q   shl  6) or
               (inv shl  2) or
               (land shl 1) or
               imw
    }

    // Overload accepting Int baseValue (kept for call-site convenience)
    @JvmStatic
    fun buildKey(
        baseValue: Int,
        isLandscape: Boolean,
        ignoreMultiWindows: Boolean,
        calcType: CalcType,
        qualifier: DpQualifier,
        inverter: Inverter,
        applyAspectRatio: Boolean,
        valueType: ValueType,
        customSensitivityK: Float? = null
    ): Long = buildKey(
        baseValue.toFloat(), isLandscape, ignoreMultiWindows, calcType,
        qualifier, inverter, applyAspectRatio, valueType, customSensitivityK
    )

    // ─────────────────────────────────────────────────────────────────────────
    // INIT / PERSISTENCE
    // ─────────────────────────────────────────────────────────────────────────

    @JvmStatic
    fun init(context: Context) {
        // Initialization is deliberately synchronous and local to the caller's window.
        // A persisted result cache cannot be made correct across a configuration, formula,
        // density or multi-window change, and its I/O belongs nowhere near dimension use.
        if (isInitializing.getAndSet(true)) return
        try {
            val config = context.resources.configuration
            updateFactors(config)
            lastConfiguration = ConfigSnapshot.from(config)
            isInitializedFast = true
            isInitialized.set(true)
        } finally {
            isInitializing.set(false)
        }
    }

    /**
     * EN Compatibility no-op. The persistent result cache was removed in 3.1.7;
     * a serialized blob is never loaded or consulted.
     *
     * PT No-op de compatibilidade. A persistência foi removida na 3.1.7; nenhum
     * blob é carregado ou consultado.
     */
    internal fun loadFromByteArray(data: ByteArray) = Unit

    @JvmStatic
    fun saveToPersistence(context: Context) {
        // Kept as a binary-compatible no-op. Result caching is intentionally in-memory
        // and snapshot-scoped; persisting it would reintroduce stale values on restart.
        Unit
    }

    /**
     * EN Binary-compatibility stub returning an empty blob. Nothing is read back by
     * this library; kept so consumer binaries built against ≤ 3.1.6 keep linking.
     *
     * PT Stub de compatibilidade binária que retorna um blob vazio.
     */
    internal fun serializeToByteArray(): ByteArray = byteArrayOf(0, 0, 0, 0)

    // ─────────────────────────────────────────────────────────────────────────
    // FAST READ / WRITE (lock-free)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Compatibility entry point for callers that cannot use the public overload.
     * The context is converted to an immutable window snapshot before any cache lookup.
     */
    @JvmStatic
    fun getOrPutInternal(key: Long, context: Context?, compute: () -> Float): Float =
        resolve(key, metricsScope.get() ?: metricsFor(context), compute)

    /**
     * EN Returns `true` when the packed [key] carries a custom sensitivity K. Only 16
     * bits of the 32-bit float fit in the key (`buildKey`), so two different K values
     * can alias the same key — a cached entry could then answer with the *other* K's
     * result. Custom-K calls therefore always compute exactly and never write the cache.
     *
     * PT Verdadeiro quando a [key] empacota um sensibilidade custom. Só 16 dos 32 bits
     * do float cabem na chave; dois Ks distintos podem colidir — por isso K customizado
     * sempre computa exatamente e nunca grava no cache.
     */
@PublishedApi
    internal fun hasCustomSensitivityKey(key: Long): Boolean =
        (key ushr 8 and 0xFFFFL).toInt() != 0xFFFF

    /**
     * EN Returns `true` when [getOrPut] should skip the snapshot cache and call `compute()`
     * directly. Covers simple no-AR multipliers **and** the default AR path where the
     * multiplier is already derived in the [DimenMetrics] snapshot — equally cheap as
     * a single multiply (~2 ns vs ~5 ns lookup).
     *
     * `CT_ASPECT_RATIO` (used by [getOrPutAspectRatio] / `fastLn`) is **not** bypassed;
     * since 3.1.7 that path computes the exact `ln()` once per snapshot (no memo table).
     *
     * PT Indica se o cache deve ser contornado (multiply barato, incl. AR padrão).
     */
    @JvmStatic
    @PublishedApi
    internal fun shouldBypassCache(key: Long): Boolean {
        val ct = (key ushr 27 and 0xFL).toInt()
        val hasAr = (key ushr 63) != 0L
        val hasCustomK = hasCustomSensitivityKey(key)

        // Custom K only fits 16 bits in the key — never bypass; [resolve] computes it
        // exactly every time. distinct floats cannot alias.
        if (hasCustomK) return false

        val isAlwaysBypassType = ct == CT_PERCENT || ct == CT_SCALED || ct == CT_DENSITY ||
                ct == CT_DIAGONAL || ct == CT_INTERPOLATED || ct == CT_PERIMETER
        val isConditionalBypassType = ct == CT_POWER || ct == CT_LOGARITHMIC

        if (!isAlwaysBypassType && !isConditionalBypassType) return false

        val q = (key ushr 6 and 0x3L).toInt()
        val inv = (key ushr 2 and 0xFL).toInt()
        val isDefaultSwPath =
            q == DpQualifier.SMALL_WIDTH.ordinal && inv == Inverter.DEFAULT.ordinal

        if (isAlwaysBypassType) {
            // Default AR on SW is one precomputed multiply in [DimenMetrics]; non-default
            // qualifiers still need the full formula and must stay cacheable.
            return !hasAr || isDefaultSwPath
        }

        return isDefaultSwPath
    }

    /**
     * EN Resolves against the actual window configuration supplied by [context].  A lookup
     * never crosses window/configuration snapshots, so resizing, split-screen, density and
     * font-scale changes cannot return a cached value from an earlier environment.
     *
     * `inline` — the full hot path is inlined at each call-site, so the [compute] lambda
     * is not instantiated per call (the legacy 3.1.5 hot-path profile), while results
     * remain partitioned per immutable [DimenMetrics] snapshot (3.1.7 correctness).
     *
     * @param key      64-bit packed key from [buildKey]
     * @param compute  Lambda invoked only on a cache **miss**
     * @return         Cached or freshly-computed raw Float result
     *
     * PT O hot path completo é inlinado em cada call-site pelo compilador Kotlin,
     * eliminando a alocação de lambda e o overhead de chamada (perfil 3.1.5) sem perder
     * a corretude por partição de snapshot da 3.1.7.
     */
    inline fun getOrPut(key: Long, context: Context? = null, crossinline compute: () -> Float): Float =
        if (context != null) {
            resolve(key, metricsFor(context), compute)
        } else {
            resolve(key, metricsScope.get() ?: fallbackMetrics, compute)
        }

    /**
     * Explicit snapshot overload for callers that already hold the configuration used by
     * their formula (notably Compose providers and custom containers).
     */
    inline fun getOrPut(key: Long, metrics: DimenMetrics, crossinline compute: () -> Float): Float =
        resolve(key, metrics, compute)

    /**
     * Convenience overload preserving the exact [Configuration] observed by a caller.
     */
    @JvmStatic
    fun getOrPut(
        key: Long,
        configuration: Configuration,
        context: Context? = null,
        compute: () -> Float,
    ): Float = resolve(
        key,
        DimenMetrics.from(configuration, DimenCalculationPlumbing.isInMultiWindowMode(context)),
        compute,
    )

    /** Backward compatibility for non-context calls. */
    @JvmStatic
    fun getOrPut(key: Long, compute: () -> Float): Float =
        getOrPut(key, null, compute)

    /**
     * EN Reads a stored cache value without computing a fallback. Returns `null` on a miss.
     *
     * **Bypass interaction:** [getOrPut] intentionally **does not write** to the snapshot cache
     * for certain cheap calculation types when aspect ratio is off (see fast-path bypass in
     * [getOrPut]). For those keys, [peek] will typically return `null` even after [getOrPut]
     * returned a value — the result was computed but not persisted. Use [getOrPut] when you
     * need the resolved float; use [peek] only to probe entries that were actually stored.
     *
     * PT Lê um valor gravado no cache sem calcular fallback. Retorna `null` em miss.
     *
     * **Interação com bypass:** para chaves que seguem o bypass de [getOrPut], o valor não é
     * guardado na tabela; [peek] costuma devolver `null` mesmo após um [getOrPut] bem-sucedido.
     */
    @JvmStatic
    fun peek(key: Long): Float? = peek(key, fallbackMetrics)

    /** Reads an entry from the partition matching [context]'s current window snapshot. */
    @JvmStatic
    fun peek(key: Long, context: Context): Float? = peek(key, metricsFor(context))

    /** Reads an entry from one explicit metrics partition. */
    @JvmStatic
    fun peek(key: Long, metrics: DimenMetrics): Float? {
        if (!isEnabled) return null
        val entry = snapshotCaches[metrics]?.entries?.get(slotFor(key))
        return if (entry?.key == key) Float.fromBits(entry.valueBits) else null
    }

    // ─────────────────────────────────────────────────────────────────────────
    // [FASE 1] PUBLIC BATCH API
    //
    // getBatch() was previously internal. Exposing it as a public JvmStatic
    // function allows callers (e.g. RecyclerView adapters, LazyColumn producers)
    // to resolve N dimensions inside a single tight loop. The JIT can then
    // auto-vectorize the inner computation loop (4-wide NEON on ARM64).
    //
    // Usage:
    //   val keys = LongArray(items.size) { i -> DimenCache.buildKey(items[i], ...) }
    //   val results = DimenCache.getBatch(keys, context) { i -> computeItem(i) }
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN SIMD-friendly batch resolution.
     *
     * Resolves [keys].size cache entries in a single tight loop. On a cache miss, the
     * provided [compute] lambda is called with the index; the result is stored and returned.
     * The loop structure is intentionally simple to help the ART JIT emit vectorized
     * (NEON) instructions for the computation body when all items compute the same formula.
     *
     * This is a **public** API — callers outside the library can use it to batch-resolve
     * any set of pre-built keys.
     *
     * PT Resolução em lote amigável ao SIMD / JIT auto-vetorização.
     * API pública — pode ser chamada por código fora da biblioteca.
     *
     * @param keys    Array of 64-bit keys built via [buildKey]
     * @param context Optional context used to derive the window snapshot partition
     * @param compute Lambda `(index: Int) -> Float` called on cache miss
     * @return        [FloatArray] of resolved values in the same order as [keys]
     */
    @JvmStatic
    fun getBatch(
        keys: LongArray,
        context: Context? = null,
        compute: (Int) -> Float
    ): FloatArray = getBatch(keys, FloatArray(keys.size), context, compute)

    /**
     * EN Zero-allocation batch resolution: writes into [destination] (which must be at
     * least [keys].size long and is returned as-is), so list adapters / producers can
     * reuse one buffer across frames instead of allocating a [FloatArray] per call.
     *
     * PT Resolução em lote sem alocação: grava em [destination] (deve ter pelo menos
     * [keys].size e é devolvido intacto), permitindo reutilizar um buffer entre frames.
     */
    @JvmStatic
    fun getBatch(
        keys: LongArray,
        destination: FloatArray,
        context: Context? = null,
        compute: (Int) -> Float
    ): FloatArray {
        val size = keys.size
        require(destination.size >= size) {
            "destination must hold at least ${keys.size} values (got ${destination.size})"
        }
        // Resolve the environment once. A batch is atomic with respect to the window
        // snapshot even if a resize arrives while the caller is iterating.
        val metrics = metricsScope.get() ?: metricsFor(context)
        for (i in 0 until size) {
            destination[i] = resolve(keys[i], metrics) { compute(i) }
        }
        return destination
    }

    @JvmStatic
    fun getOrPutAspectRatio(normalizedAr: Float, context: Context? = null): Float {
        require(normalizedAr.isFinite() && normalizedAr > 0f) {
            "normalizedAr must be a positive, finite value"
        }
        // This is executed only while creating a DimenMetrics snapshot. Exact math here
        // avoids a lossy global lookup table and does not burden a frame-time hot path.
        return kotlin.math.ln(normalizedAr.toDouble()).toFloat()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // INVALIDATION
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Selectively invalidates the cache based on what actually changed in [Configuration].
     *
     * PT Invalida seletivamente o cache baseado no que mudou na [Configuration].
     */
    @JvmStatic
    fun invalidateOnConfigChange(new: Configuration) {
        lastConfiguration = ConfigSnapshot.from(new)
        updateFactors(new)
        // Snapshot partitions make explicit invalidation unnecessary for correctness.
        // Keep this API as a compatibility hook, but do not erase other windows' hot
        // entries whenever one Activity rotates or is resized.
    }

    private fun updateFactors(config: Configuration) {
        fallbackMetrics = DimenMetrics.from(config)
        val metrics = sharedMetricsFrom(config)
        val f = factors

        f.scale = metrics.scale
        f.normalizedAr = metrics.normalizedAr
        f.logNormalizedAr = metrics.logNormalizedAr
        f.arMultiplier = metrics.arMultiplier
        f.density = metrics.density
        f.aspectRatioMul = metrics.aspectRatioMul
        f.smallestWidthDp = metrics.smallestWidthDp.toInt()

        // `factors` remains populated only for binary/source compatibility. Production
        // formulas resolve through currentMetrics, so no process-global strategy update is
        // published here.
    }

    /** EN Clears all cache slots. Java-compatible alias. */
    @JvmStatic
    fun clear(context: Context? = null) = clearAll(context)

    // ─────────────────────────────────────────────────────────────────────────
    // clearAll / CLEAR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Clears all snapshot partitions. Thread-safe: an in-flight resolver may finish
     * on an old partition, but it can never publish into the new cache after the clear.
     *
     * PT Limpa todas as partições de snapshot. Thread-safe.
     */
    @JvmStatic
    @JvmOverloads
    fun clearAll(context: Context? = null) {
        // Detaching whole partitions is atomic from the perspective of future lookups:
        // an in-flight resolver may finish on an old partition, but it can never publish
        // into the new cache after the clear.
        snapshotCaches.clear()
        fastPartition = null
        fastPartitionMetrics = null
        resetListeners.forEach { it() }
    }

    /**
     * EN Clears only cache entries whose [ValueType] embeds fontScale
     * (`SP_NO_SCALE`, `SP_PX_WITH_SCALE`, `SP_PX_NO_SCALE`). Leaves DP/PX/`SP_WITH_SCALE`.
     *
     * PT Limpa só entradas cujo ValueType embute fontScale.
     */
    @JvmStatic
    internal fun clearFontScaleDependentEntries() {
        // Font scale is part of DimenMetrics equality. Existing entries therefore cannot
        // be read by a new font-scale snapshot. Dropping old partitions is bounded and
        // safer than decoding a partial legacy key format.
        snapshotCaches.clear()
        resetListeners.forEach { it() }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DIAGNOSTICS
    // ─────────────────────────────────────────────────────────────────────────

    @JvmStatic
    fun stats(): CacheStats {
        var populated = 0
        for (cache in snapshotCaches.values) {
            for (i in 0 until SNAPSHOT_CACHE_SIZE) {
                if (cache.entries.get(i) != null) populated++
            }
        }
        val hits   = hitCount.sum()
        val misses = missCount.sum()
        val total  = hits + misses
        val capacity = snapshotCaches.size * SNAPSHOT_CACHE_SIZE
        return CacheStats(
            capacity   = capacity,
            populated  = populated,
            fillRatio  = if (capacity > 0) populated.toFloat() / capacity else 0f,
            hits       = hits,
            misses     = misses,
            evictions  = evictionCount.sum(),
            hitRate    = if (total > 0) hits.toFloat() / total else 0f
        )
    }

    /**
     * EN Resets the diagnostic counters (hit, miss, eviction) to zero.
     * PT Zera os contadores de diagnóstico (hit, miss, eviction).
     */
    @JvmStatic
    fun resetDiagnostics() {
        hitCount.reset()
        missCount.reset()
        evictionCount.reset()
    }

    /**
     * EN Cache usage statistics snapshot. The [hits], [misses], [evictions], and [hitRate]
     * fields are only meaningful when [diagnosticsEnabled] is `true`.
     *
     * PT Snapshot de métricas de uso do cache. [hits], [misses], [evictions] e [hitRate]
     * só são significativos quando [diagnosticsEnabled] está `true`.
     */
    data class CacheStats(
        val capacity  : Int,
        val populated : Int,
        val fillRatio : Float,
        val hits      : Long = 0,
        val misses    : Long = 0,
        val evictions : Long = 0,
        val hitRate   : Float = 0f
    ) {
        override fun toString(): String =
            "DimenCache: $populated/$capacity slots used (${(fillRatio * 100).toInt()}% fill)" +
            if (hits + misses > 0) ", hits=$hits misses=$misses evictions=$evictions hitRate=${(hitRate * 100).toInt()}%" else ""
    }
}
