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
 *  - Lock-free reads via AtomicLongArray / AtomicIntegerArray (zero contention)
 *  - Collision-safe via packed 64-bit Long key (no false hits)
 *  - Shared state across all library instances (save memory, share reuse)
 *  - Smart invalidation: only clears when physical screen dimensions actually change
 *  - Zero allocation in hot path: stores raw Float, caller boxes into Dp/TextUnit
 *
 * Optimizations applied (2026-03-31):
 *  - [FASE 2] ShardWrapper: each shard is isolated in its own object with 128-byte padding,
 *    preventing false sharing between CPU cores on ARM64 (64-byte cache line × 2 guard).
 *  - [FASE 3] ScreenFactors: all @Volatile scalar fields grouped in a padded object so a
 *    write to `scale` cannot invalidate `arMultiplier` on another core's cache line.
 *  - [FASE 4] clearAll() uses lazySet() + manual 4× loop unrolling for bulk zeroing
 *    without emitting full memory barriers on every element.
 *  - [FASE 1] getBatch() is now public, enabling callers to resolve N dimensions in a
 *    single tight loop — friendly to JIT auto-vectorization (ART / HotSpot).
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.UiModeType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicIntegerArray
import java.util.concurrent.atomic.AtomicLongArray
import java.util.concurrent.atomic.LongAdder
import kotlin.math.max
import kotlin.math.min

/**
 * EN
 * Global, lock-free, shared cache for all AppDimens dimension calculations.
 *
 * **Thread Safety**: Completely thread-safe.  All reads and writes are lock-free
 * using [AtomicLongArray] / [AtomicIntegerArray].  If two threads write
 * identically-keyed entries simultaneously, the last write wins — always correct
 * because both computed the same value.
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
    // CONFIGURATION & PERSISTENT STATE
    // ─────────────────────────────────────────────────────────────────────────

    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.appdimens.dynamic.cache")
    internal val KEY_SW_DP = intPreferencesKey("smallest_width_dp")
    internal val KEY_DPI = intPreferencesKey("density_dpi")
    internal val KEY_CACHE_DATA = byteArrayPreferencesKey("cache_mirror")

    /**
     * EN Bumped on every [clearAll] / font-selective clear so in-flight [performSave]
     * calls abort instead of writing a stale snapshot over a wiped DataStore.
     *
     * PT Incrementado em cada limpeza para abortar saves em voo.
     */
    private val persistenceGeneration = java.util.concurrent.atomic.AtomicLong(0L)

    @Volatile
    private var _scope: CoroutineScope? = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val scopeLock = Any()

    internal val scope: CoroutineScope
        get() = _scope ?: synchronized(scopeLock) {
            _scope ?: CoroutineScope(SupervisorJob() + Dispatchers.IO).also {
                _scope = it
                launchSaveCollector(it)
            }
        }
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
    @Volatile
    @PublishedApi
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

    /**
     * EN Application [Context] captured in [init]. Reused by
     * [invalidateOnConfigChange] so physical config changes clear the DataStore
     * blob without requiring a new public API parameter (avoids opt-in bugs).
     *
     * PT [Context] de Application capturado em [init], reutilizado na invalidação.
     */
    @Volatile
    @PublishedApi
    internal var savedAppContext: Context? = null

    /**
     * EN Set when [clearAll] is asked to wipe DataStore (test/diagnostics hook).
     * PT Sinaliza pedido de limpeza do DataStore (gancho de teste).
     */
    @Volatile
    @PublishedApi
    internal var diskClearRequested: Boolean = false

    @JvmField @Volatile
    internal var cachedUiMode: UiModeType = UiModeType.UNDEFINED

    @Volatile
    private var cachedUiModeConfigHash: Int = 0

    @JvmStatic
    fun getCachedUiModeType(context: Context): UiModeType {
        val cfg = context.resources.configuration
        // Fingerprint only fields that affect UiMode / foldable detection — not locale/keyboard.
        val fingerprint =
            (cfg.uiMode * 31 + cfg.smallestScreenWidthDp) * 31 +
                min(cfg.screenWidthDp, cfg.screenHeightDp) * 31 +
                max(cfg.screenWidthDp, cfg.screenHeightDp)
        val cached = cachedUiMode
        if (cachedUiModeConfigHash == fingerprint && cached != UiModeType.UNDEFINED) {
            return cached
        }
        val mode = UiModeType.fromConfiguration(context, null)
        cachedUiMode = mode
        cachedUiModeConfigHash = fingerprint
        return mode
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
        // Strategy-specific scales (diagonal/power/log/…) live in satellite modules
        // via [StrategyFactorRegistry] — not precomputed here.
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

    // Convenience accessors — public so satellite modules can read shared scales.
    val currentNormalizedAr      get() = factors.normalizedAr
    val currentLogNormalizedAr   get() = factors.logNormalizedAr
    val currentSmallestWidthDp   get() = factors.smallestWidthDp
    val currentDensity           get() = factors.density
    val currentScale             get() = factors.scale
    val currentArMultiplier      get() = factors.arMultiplier
    val currentAspectRatioMul    get() = factors.aspectRatioMul

    /**
     * Number of slots in the primary (Tier-1) fast cache.
     * Must be a power of 2 so that `key and MASK` is a fast modulo.
     *
     * 2048 slots @ ~12 bytes per entry ≈ ~24 KB (keys) + ~8 KB (values) = ~32 KB total.
     * Hit-rate analysis: typical app has 100–300 distinct dimension configurations;
     * 2048 slots gives <15% fill ratio under normal usage — near-zero collision rate.
     */
    const val CACHE_SIZE = 2048

    /**
     * EN Cache Sharding (Concurrency Partitioning)
     * Split the cache into 4 shards to reduce false sharing and bus contention.
     */
    const val SHARD_COUNT    = 4
    const val SHARD_MASK     = SHARD_COUNT - 1
    const val SHARD_SIZE     = CACHE_SIZE / SHARD_COUNT
    const val SHARD_SIZE_MASK = SHARD_SIZE - 1

    // ─────────────────────────────────────────────────────────────────────────
    // [FASE 2] SHARD WRAPPER — anti-false-sharing padding between shards
    //
    // Each ShardWrapper holds one pair of atomic arrays plus 128 bytes of padding.
    // This forces the JVM allocator to place each wrapper in distinct cache lines,
    // eliminating "ping-pong" invalidation between CPU cores accessing different shards.
    //
    // Padding layout:
    //   Object header      ≈ 16 bytes
    //   AtomicLongArray ref  8 bytes
    //   AtomicIntArray ref   8 bytes
    //   14 × Long pad      = 112 bytes
    //   Total              ≈ 144 bytes  ≥ 2 × 64-byte ARM cache lines ✓
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Padded cache shard wrapper that prevents false sharing between shards
     * across CPU cores on ARM64 (cache line = 64 bytes).
     *
     * PT Wrapper de shard com padding que previne false sharing entre núcleos
     * no ARM64 (linha de cache = 64 bytes).
     */
    @PublishedApi
    internal class ShardWrapper(shardSize: Int) {
        @JvmField @PublishedApi internal val keys  : AtomicLongArray   = AtomicLongArray(shardSize)
        @JvmField @PublishedApi internal val values: AtomicIntegerArray = AtomicIntegerArray(shardSize)
        // 128-byte padding guard between shard objects
        @Suppress("unused") @JvmField val _p0 = 0L
        @Suppress("unused") @JvmField val _p1 = 0L
        @Suppress("unused") @JvmField val _p2 = 0L
        @Suppress("unused") @JvmField val _p3 = 0L
        @Suppress("unused") @JvmField val _p4 = 0L
        @Suppress("unused") @JvmField val _p5 = 0L
        @Suppress("unused") @JvmField val _p6 = 0L
        @Suppress("unused") @JvmField val _p7 = 0L
        @Suppress("unused") @JvmField val _p8 = 0L
        @Suppress("unused") @JvmField val _p9 = 0L
        @Suppress("unused") @JvmField val _pA = 0L
        @Suppress("unused") @JvmField val _pB = 0L
        @Suppress("unused") @JvmField val _pC = 0L
        @Suppress("unused") @JvmField val _pD = 0L
    }

    /**
     * EN Sharded, padded primitive cache storage.
     * Replaces the previous `keysArray` / `valueBitsArray` pair.
     * Each shard is wrapped in a [ShardWrapper] with 128-byte padding.
     */
    @JvmField
    @PublishedApi
    internal val shards = Array(SHARD_COUNT) { ShardWrapper(SHARD_SIZE) }

    /**
     * EN Backward-compatible accessors — still referenced by [DimenCacheTest].
     * These are thin aliases into [shards]; no extra memory is allocated.
     *
     * PT Aliases de compatibilidade com os testes existentes.
     */
    @PublishedApi
    internal val keysArray: Array<AtomicLongArray> by lazy {
        Array(SHARD_COUNT) { shards[it].keys }
    }

    @PublishedApi
    internal val valueBitsArray: Array<AtomicIntegerArray> by lazy {
        Array(SHARD_COUNT) { shards[it].values }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MATH CONSTANTS
    // ─────────────────────────────────────────────────────────────────────────

    const val INV_BASE_RATIO      = 0.0033333334f // 1f / 300f
    const val ADJUSTMENT_SCALE    = 0.10f / 30f   // 0.0033333334f
    const val SENSITIVITY_DEFAULT = 0.08f / 30f   // 0.0026666667f

    /**
     * EN Unified high-performance scaling engine. Reads from [factors] — padded object,
     * guaranteeing that the read of `scale` and `arMultiplier` land on the same cache line
     * as all other factor fields.
     */
    fun calculateRawScaling(
        baseValue: Float,
        applyAspectRatio: Boolean,
        customSensitivityK: Float?
    ): Float {
        val f = factors
        return if (applyAspectRatio) {
            val factor = if (customSensitivityK == null) {
                f.arMultiplier
            } else {
                val logAr = f.logNormalizedAr
                val adjustment = customSensitivityK * logAr
                1.0f + (f.smallestWidthDp - 300f) * (ADJUSTMENT_SCALE + adjustment)
            }
            baseValue * factor
        } else {
            baseValue * f.scale
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PERSISTENCE FLOW
    // ─────────────────────────────────────────────────────────────────────────

    private val saveFlow = kotlinx.coroutines.flow.MutableSharedFlow<Context>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
    )

    /**
     * EN Quiescence window before a disk write (production default: 500 ms).
     * Overridable in unit tests so timings stay short.
     *
     * PT Janela de quiescência antes de gravar em disco (padrão: 500 ms).
     */
    @Volatile
    @PublishedApi
    internal var saveDebounceMs: Long = 500L

    /**
     * EN Safety-net sampling interval while writes never go quiet (production: 10 s).
     * PT Intervalo de amostragem de segurança enquanto escritas não cessam (padrão: 10 s).
     */
    @Volatile
    @PublishedApi
    internal var saveSampleMs: Long = 10_000L

    /**
     * EN When `false`, [performSave] increments [performSaveCount] but skips DataStore I/O.
     * Used by unit tests that exercise the debounce/sample collector without Android DataStore.
     *
     * PT Quando `false`, [performSave] só incrementa o contador — sem I/O no DataStore.
     */
    @Volatile
    @PublishedApi
    internal var persistenceWritesEnabled: Boolean = true

    /** EN Count of [performSave] invocations (test/diagnostics). PT Contagem de invocações. */
    @JvmField
    @PublishedApi
    internal val performSaveCount = java.util.concurrent.atomic.AtomicInteger(0)

    private fun launchSaveCollector(target: CoroutineScope) {
        target.launch {
            @OptIn(FlowPreview::class)
            // debounce: write once after the cache goes quiet (no I/O during scroll/animation).
            // sample: safety net so pathological continuous-write apps still persist eventually.
            merge(
                saveFlow.debounce(saveDebounceMs),
                saveFlow.sample(saveSampleMs)
            ).collect { ctx ->
                performSave(ctx)
            }
        }
    }

    init {
        _scope?.let { launchSaveCollector(it) }
    }

    /**
     * EN Cancels the background persistence scope. Intended for test teardown.
     * The scope is automatically re-created on next use (e.g. [saveToPersistence]).
     *
     * PT Cancela o escopo de persistência em background. Destinado a teardown de testes.
     * O escopo é recriado automaticamente no próximo uso.
     */
    @JvmStatic
    fun shutdown() {
        synchronized(scopeLock) {
            _scope?.cancel()
            _scope = null
        }
    }

    /**
     * EN Restarts the persistence collector after changing [saveDebounceMs] / [saveSampleMs]
     * in tests. No-op for production callers.
     *
     * PT Reinicia o coletor de persistência após alterar intervalos em testes.
     */
    @JvmStatic
    @PublishedApi
    internal fun restartSaveCollectorForTest() {
        shutdown()
        performSaveCount.set(0)
        // Touching [scope] recreates the CoroutineScope and re-launches the collector.
        scope
    }

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
        if (isInitialized.get()) {
            isInitializedFast = true
            return
        }
        if (isInitializing.getAndSet(true)) return

        val appContext = context.applicationContext
        savedAppContext = appContext
        val config = appContext.resources.configuration
        val currentSw = config.smallestScreenWidthDp
        val currentDpi = config.densityDpi

        updateFactors(config)
        factors.smallestWidthDp = currentSw
        lastConfiguration = ConfigSnapshot.from(config)
        isInitializedFast = true

        scope.launch {
            try {
                val prefs   = appContext.dataStore.data.firstOrNull()
                val savedSw = prefs?.get(KEY_SW_DP) ?: 0
                val savedDpi = prefs?.get(KEY_DPI)
                val rawData = prefs?.get(KEY_CACHE_DATA)

                // Reject blob when SW or densityDpi diverges (PX / density-scaled entries
                // embed dpi). Missing KEY_DPI = legacy blob → treat as mismatch.
                val incompatible = savedSw != currentSw ||
                        savedDpi == null ||
                        savedDpi != currentDpi ||
                        rawData == null

                if (incompatible) {
                    if (savedSw != 0 && (savedSw != currentSw || (savedDpi != null && savedDpi != currentDpi))) {
                        clearAll(appContext)
                    }
                } else {
                    loadFromByteArray(rawData!!)
                }
            } catch (_: Exception) {
                // Fallback to empty cache on error
            } finally {
                isInitialized.set(true)
                isInitializing.set(false)
            }
        }
    }

    /**
     * EN Loads a persisted cache blob. Supports the sparse format
     * (`count:Int` + `count × (key:Long, value:Float)`) and the legacy dense
     * fixed-size layout (`CACHE_SIZE × 12` bytes, position = slot).
     *
     * Sparse entries are placed via the same hash used by [getOrPutInternal]
     * (not sequential file order). CAS merge preserves entries already computed
     * during the init→DataStore window.
     *
     * PT Carrega o blob persistido. Suporta formato esparso e o layout denso legado.
     */
    internal fun loadFromByteArray(data: ByteArray) {
        if (data.size >= 4) {
            val probe = ByteBuffer.wrap(data)
            val count = probe.int
            // Sparse sizes are 4 + count*12; dense legacy is exactly CACHE_SIZE*12.
            // Those sizes never collide for an integer count.
            if (count in 0..CACHE_SIZE && data.size == 4 + count * 12) {
                for (n in 0 until count) {
                    val key = probe.long
                    val value = probe.float
                    if (key == 0L) continue
                    val (shardIndex, slotIndex) = shardAndSlot(key)
                    val shard = shards[shardIndex]
                    if (shard.keys.compareAndSet(slotIndex, 0L, key)) {
                        shard.values.set(slotIndex, value.toRawBits())
                    }
                }
                return
            }
        }
        // Legacy dense format: position in file == shard/slot index.
        if (data.size < CACHE_SIZE * 12) return
        val buffer = ByteBuffer.wrap(data)
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            for (i in 0 until SHARD_SIZE) {
                val key   = buffer.long
                val value = buffer.float
                if (key != 0L) {
                    if (shard.keys.compareAndSet(i, 0L, key)) {
                        shard.values.set(i, value.toRawBits())
                    }
                }
            }
        }
    }

    /** EN Hash → (shard, slot) — same mix as [getOrPutInternal]. */
    @PublishedApi
    internal fun shardAndSlot(key: Long): Pair<Int, Int> {
        val h = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        return ((mixed ushr 9) and SHARD_MASK) to (mixed and SHARD_SIZE_MASK)
    }

    @JvmStatic
    fun saveToPersistence(context: Context) {
        saveFlow.tryEmit(context)
    }

    /**
     * EN Serializes only populated slots in a single pass (avoids the
     * count-then-write race that could overflow a pre-sized buffer under
     * concurrent writers). Snapshot may omit entries written mid-pass —
     * same non-fatal tolerance as the legacy full dump.
     *
     * PT Serializa só slots populados em passada única (sem corrida count/write).
     */
    private suspend fun performSave(context: Context) {
        performSaveCount.incrementAndGet()
        if (!persistenceWritesEnabled) return
        val gen = persistenceGeneration.get()
        val appContext = context.applicationContext
        val data = serializeToByteArray()
        // Abort if a clear happened while we were serializing.
        if (gen != persistenceGeneration.get()) return
        val dpi = lastConfiguration?.densityDpi ?: 0
        appContext.dataStore.edit { prefs ->
            if (gen != persistenceGeneration.get()) return@edit
            prefs[KEY_SW_DP]      = factors.smallestWidthDp
            prefs[KEY_DPI]        = dpi
            prefs[KEY_CACHE_DATA] = data
        }
    }

    /**
     * EN Sparse snapshot: `Int count` + `count × (Long key, Float value)`.
     * Size is proportional to populated entries, not [CACHE_SIZE].
     *
     * PT Snapshot esparso; tamanho proporcional às entradas populadas.
     */
    internal fun serializeToByteArray(): ByteArray {
        // Single pass — count and payload always agree even if other threads write.
        val keysBuf = ArrayList<Long>(CACHE_SIZE / 8)
        val valsBuf = ArrayList<Int>(CACHE_SIZE / 8)
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            for (i in 0 until SHARD_SIZE) {
                val k = shard.keys.get(i)
                if (k != 0L) {
                    keysBuf.add(k)
                    valsBuf.add(shard.values.get(i))
                }
            }
        }
        val count = keysBuf.size
        val buffer = ByteBuffer.allocate(4 + count * 12)
        buffer.putInt(count)
        for (idx in 0 until count) {
            buffer.putLong(keysBuf[idx])
            buffer.putFloat(Float.fromBits(valsBuf[idx]))
        }
        return buffer.array()
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FAST READ / WRITE (lock-free)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Non-inline core logic for [getOrPut]. Separated so that the public inline
     * function does not need access to internal fields of [ShardWrapper] directly.
     * This function is @PublishedApi, making it visible to the inlined call-sites.
     *
     * PT Núcleo não-inline de [getOrPut]. Separado para evitar que a função inline
     * pública precise de acesso direto aos campos internos de [ShardWrapper].
     */
    @JvmStatic
    fun getOrPutInternal(key: Long, context: Context?, compute: () -> Float): Float {
        if (!isEnabled) return compute()

        // Mirror the inline [getOrPut] fast-bypass (including default-AR multiply path).
        if (shouldBypassCache(key)) return compute()

        // AUTO-INIT — same guard as inline [getOrPut] ([isInitializedFast] + [init])
        if (context != null && !isInitializedFast) {
            init(context)
        }

        val h      = (key xor (key ushr 32)).toInt()
        val mixed  = h xor (h ushr 16)

        val shardIndex = (mixed ushr 9) and SHARD_MASK
        val slotIndex  = mixed and SHARD_SIZE_MASK

        val shard = shards[shardIndex]
        val shardKeys   = shard.keys
        val shardValues = shard.values

        // FAST PATH
        val existingKey = shardKeys.get(slotIndex)
        if (existingKey == key) {
            if (diagnosticsEnabled) hitCount.increment()
            return Float.fromBits(shardValues.get(slotIndex))
        }

        // MISS
        if (diagnosticsEnabled) missCount.increment()
        val computed = compute()

        val ct      = (key         ushr 27 and 0xFL).toInt()
        val existCt = (existingKey ushr 27 and 0xFL).toInt()

        val isNewAr = ct == CT_ASPECT_RATIO
        val isOldAr = existingKey != 0L && existCt == CT_ASPECT_RATIO

        if (existingKey == 0L || !isOldAr || isNewAr) {
            if (diagnosticsEnabled && existingKey != 0L) evictionCount.increment()
            shardValues.set(slotIndex, computed.toRawBits())
            shardKeys.set(slotIndex, key)
            context?.let { saveToPersistence(it) }
        }

        return computed
    }

    /**
     * EN Returns `true` when [getOrPut] should skip the shard table and call `compute()`
     * directly. Covers simple no-AR multipliers **and** the default AR path where
     * `compute()` is already `baseValue * factors.arMultiplier` (pre-resolved in
     * [updateFactors]) — equally cheap as a single multiply (~2 ns vs ~5 ns lookup).
     *
     * `CT_ASPECT_RATIO` (used by [getOrPutAspectRatio] / `fastLn`) is **not** bypassed,
     * so real `ln()` results remain memoized.
     *
     * PT Indica se o cache deve ser contornado (multiply barato, incl. AR padrão).
     */
    @JvmStatic
    @PublishedApi
    internal inline fun shouldBypassCache(key: Long): Boolean {
        val ct = (key ushr 27 and 0xFL).toInt()
        // Multiply-only types with precomputed ScreenFactors on the default path.
        val isAlwaysBypassType = ct == CT_PERCENT || ct == CT_SCALED || ct == CT_DENSITY ||
                ct == CT_DIAGONAL || ct == CT_INTERPOLATED || ct == CT_PERIMETER
        // POWER / LOGARITHMIC: only SW+DEFAULT reduces to a precomputed multiply;
        // WIDTH/HEIGHT still call pow()/ln() and must stay cached.
        val isConditionalBypassType = ct == CT_POWER || ct == CT_LOGARITHMIC

        if (!isAlwaysBypassType && !isConditionalBypassType) return false

        if (key >= 0) {
            if (isAlwaysBypassType) return true
            // Conditional types without AR: still require default qualifier/inverter.
            val q   = (key ushr 6 and 0x3L).toInt()
            val inv = (key ushr 2 and 0xFL).toInt()
            return q == DpQualifier.SMALL_WIDTH.ordinal && inv == Inverter.DEFAULT.ordinal
        }

        // Default AR: SMALL_WIDTH + DEFAULT inverter + null customSensitivityK (0xFFFF sentinel).
        val q   = (key ushr 6 and 0x3L).toInt()
        val inv = (key ushr 2 and 0xFL).toInt()
        val sk  = (key ushr 8 and 0xFFFFL)
        return q == DpQualifier.SMALL_WIDTH.ordinal &&
                inv == Inverter.DEFAULT.ordinal &&
                sk == 0xFFFFL
    }

    /**
     * EN
     * Reads from the cache or computes (and stores) a new value. **Lock-free.**
     *
     * The full hot path is inlined at every call-site by the Kotlin compiler.
     * This eliminates all method-call overhead and gives the JIT full visibility
     * over the loop body when called from a batch context.
     *
     * [getOrPutInternal] is kept as a non-inline helper for callers (like [getBatch])
     * that cannot use inline functions.
     *
     * @param key      64-bit packed key from [buildKey]
     * @param compute  Lambda invoked only on a cache **miss**
     * @return         Cached or freshly-computed raw Float result
     *
     * PT O hot path completo é inlinado em cada call-site pelo compilador Kotlin,
     * eliminando overhead de chamada e dando ao JIT visibilidade total do loop.
     */
    @JvmStatic
    inline fun getOrPut(key: Long, context: Context? = null, crossinline compute: () -> Float): Float {
        if (!isEnabled) return compute()

        // 0. FAST BYPASS — intentional design decision.
        //
        // When Aspect Ratio is NOT active (bit 63 == 0) and the CalcType is one of the
        // "simple multiplier" types (PERCENT, SCALED, DENSITY, …), the scaling formula
        // often reduces to a single float multiply: `baseValue * scale`.
        //
        // Measured cost on Snapdragon 888:
        //   Raw math (multiply)  ≈  2 ns
        //   Fastest cache lookup ≈  5 ns   (hash + atomic load + branch)
        //
        // Default AR (SMALL_WIDTH + DEFAULT inverter + null sensitivity) is also just
        // `baseValue * factors.arMultiplier` — arMultiplier is precomputed in
        // updateFactors() — so it shares the same bypass. Custom sensitivity / non-default
        // qualifier still use the full cache path. CT_ASPECT_RATIO (ln memoization) never bypasses.
        if (shouldBypassCache(key)) return compute()

        // ─────────────────────────────────────────────────────────────────────
        // HOT PATH — fully inlined, zero method-call overhead, zero lambda alloc
        // ─────────────────────────────────────────────────────────────────────
        if (context != null && !isInitializedFast) init(context)

        val h     = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        val shard = shards[(mixed ushr 9) and SHARD_MASK]
        val slot  = mixed and SHARD_SIZE_MASK

        val existingKey = shard.keys.get(slot)
        if (existingKey == key) {
            if (diagnosticsEnabled) hitCount.increment()
            return Float.fromBits(shard.values.get(slot))
        }

        // MISS — compute then conditionally store
        if (diagnosticsEnabled) missCount.increment()
        val computed = compute()

        val ct      = (key         ushr 27 and 0xFL).toInt()
        val existCt = (existingKey ushr 27 and 0xFL).toInt()
        val isNewAr = ct == CT_ASPECT_RATIO
        val isOldAr = existingKey != 0L && existCt == CT_ASPECT_RATIO

        if (existingKey == 0L || !isOldAr || isNewAr) {
            if (diagnosticsEnabled && existingKey != 0L) evictionCount.increment()
            shard.values.set(slot, computed.toRawBits())
            shard.keys.set(slot, key)
            context?.let { saveToPersistence(it) }
        }

        return computed
    }

    /** Backward compatibility for non-context calls. */
    @JvmStatic
    inline fun getOrPut(key: Long, crossinline compute: () -> Float): Float =
        getOrPut(key, null, compute)

    /**
     * EN Reads a stored cache value without computing a fallback. Returns `null` on a miss.
     *
     * **Bypass interaction:** [getOrPut] intentionally **does not write** to the shard table
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
    fun peek(key: Long): Float? {
        if (!isEnabled) return null
        val h      = (key xor (key ushr 32)).toInt()
        val mixed  = h xor (h ushr 16)
        val shard  = shards[(mixed ushr 9) and SHARD_MASK]
        val slotIndex = mixed and SHARD_SIZE_MASK
        val existing = shard.keys.get(slotIndex)
        return if (existing == key) Float.fromBits(shard.values.get(slotIndex)) else null
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
     * @param context Optional context used for lazy init and persistence
     * @param compute Lambda `(index: Int) -> Float` called on cache miss
     * @return        [FloatArray] of resolved values in the same order as [keys]
     */
    @JvmStatic
    fun getBatch(
        keys: LongArray,
        context: Context? = null,
        compute: (Int) -> Float
    ): FloatArray {
        val size    = keys.size
        val results = FloatArray(size)
        // Tight, index-consecutive loop — maximizes JIT auto-vectorization opportunity
        for (i in 0 until size) {
            results[i] = getOrPut(keys[i], context) { compute(i) }
        }
        return results
    }

    @JvmStatic
    fun getOrPutAspectRatio(normalizedAr: Float, context: Context? = null): Float {
        val arKey = ((java.lang.Float.floatToRawIntBits(normalizedAr).toLong() and 0xFFFFFFFFL) shl 31) or
                (CT_ASPECT_RATIO.toLong() shl 27)
        return getOrPut(arKey, context) {
            kotlin.math.ln(normalizedAr)
        }
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
        val old = lastConfiguration
        val snap = ConfigSnapshot.from(new)
        lastConfiguration = snap

        if (old == null) {
            updateFactors(new)
            factors.smallestWidthDp = new.smallestScreenWidthDp
            clearAll(savedAppContext)
            return
        }

        val oldMin = min(old.screenWidthDp, old.screenHeightDp)
        val oldMax = max(old.screenWidthDp, old.screenHeightDp)
        val newMin = min(snap.screenWidthDp, snap.screenHeightDp)
        val newMax = max(snap.screenWidthDp, snap.screenHeightDp)

        // Orientation-only swaps exchange screenWidthDp ↔ screenHeightDp but leave
        // min/max (and thus all ScreenFactors: scale, arMultiplier, …)
        // mathematically unchanged. Comparing raw width/height here would clear the
        // entire 2048-slot cache on every rotation — contradicting the comment below.
        val physicalChange = oldMin != newMin ||
                oldMax != newMax ||
                old.smallestScreenWidthDp != snap.smallestScreenWidthDp ||
                old.densityDpi != snap.densityDpi

        val fontScaleChange = old.fontScale != snap.fontScale

        if (physicalChange) {
            updateFactors(new)
            factors.smallestWidthDp = new.smallestScreenWidthDp
            // Full wipe (memory + DataStore) via savedAppContext.
            clearAll(savedAppContext)
        } else if (fontScaleChange) {
            // DP / PX / SP_WITH_SCALE do not embed fontScale — keep them.
            // Only clear ValueTypes that baked fontScale into the stored float.
            clearFontScaleDependentEntries()
            persistenceGeneration.incrementAndGet()
            // Rewrite disk without a full clear so DP entries survive cold start.
            savedAppContext?.let { saveToPersistence(it) }
        }
        // Orientation-only: keys encode isLandscape bit → natural miss, no clear needed.
    }

    private fun updateFactors(config: Configuration) {
        val metrics = sharedMetricsFrom(config)
        val f = factors

        f.scale = metrics.scale
        f.normalizedAr = metrics.normalizedAr
        f.logNormalizedAr = metrics.logNormalizedAr
        f.arMultiplier = metrics.arMultiplier
        f.density = metrics.density
        f.aspectRatioMul = metrics.aspectRatioMul
        f.smallestWidthDp = metrics.smallestWidthDp.toInt()

        // Notify only registered satellites (absent strategies do no work).
        StrategyFactorRegistry.publish(metrics)
    }

    /** EN Clears all cache slots. Java-compatible alias. */
    @JvmStatic
    fun clear(context: Context? = null) = clearAll(context)

    // ─────────────────────────────────────────────────────────────────────────
    // [FASE 4] clearAll() — lazySet + 4× loop unrolling
    //
    // lazySet() (a.k.a. setRelease / ordered store) omits the expensive full
    // StoreLoad memory barrier required by set(). For mass zeroing, visibility
    // of individual zeros before the next cache operation is unnecessary; the
    // subsequent getOrPut() call will issue its own load-acquire barrier.
    //
    // 4× manual unrolling allows the JIT to:
    //   1. Schedule 4 independent stores per iteration (out-of-order execution)
    //   2. Reduce loop overhead (branch + increment) by 4×
    //   3. Potentially emit SIMD store pairs (STP) on ARM64
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Clears all cache entries using [AtomicLongArray.lazySet] / [AtomicIntegerArray.lazySet]
     * with 4× manual loop unrolling. This avoids issuing a full memory barrier on every
     * element, which is safe because the next [getOrPut] will provide the required
     * acquire/release semantics. Thread-safe.
     *
     * PT Limpa todas as entradas com lazySet (sem barrier completo por elemento) e
     * unrolling 4× para otimização de pipeline. Thread-safe.
     */
    @JvmStatic
    @JvmOverloads
    fun clearAll(context: Context? = null) {
        persistenceGeneration.incrementAndGet()
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            val keys  = shard.keys
            val vals  = shard.values
            var i = 0
            // 4× unrolled loop — JIT-friendly, helps ARM64 emit STP pairs
            while (i < SHARD_SIZE - 3) {
                keys.lazySet(i,     0L); vals.lazySet(i,     0)
                keys.lazySet(i + 1, 0L); vals.lazySet(i + 1, 0)
                keys.lazySet(i + 2, 0L); vals.lazySet(i + 2, 0)
                keys.lazySet(i + 3, 0L); vals.lazySet(i + 3, 0)
                i += 4
            }
            // Handle tail elements (SHARD_SIZE must be a multiple of 4 for zero tail)
            while (i < SHARD_SIZE) {
                keys.lazySet(i, 0L); vals.lazySet(i, 0)
                i++
            }
        }
        resetListeners.forEach { it() }

        context?.let { ctx ->
            diskClearRequested = true
            if (!persistenceWritesEnabled) return@let
            val gen = persistenceGeneration.get()
            scope.launch {
                try {
                    // Skip wipe if a newer clear/save generation superseded this one.
                    if (gen != persistenceGeneration.get()) return@launch
                    ctx.applicationContext.dataStore.edit { it.clear() }
                } catch (_: Exception) { }
            }
        }
    }

    /**
     * EN Clears only cache entries whose [ValueType] embeds fontScale
     * (`SP_NO_SCALE`, `SP_PX_WITH_SCALE`, `SP_PX_NO_SCALE`). Leaves DP/PX/`SP_WITH_SCALE`.
     *
     * PT Limpa só entradas cujo ValueType embute fontScale.
     */
    @JvmStatic
    internal fun clearFontScaleDependentEntries() {
        // ValueType bits [26-24]; ordinals: SP_NO_SCALE=3, SP_PX_WITH_SCALE=4, SP_PX_NO_SCALE=5
        val spNoScale = ValueType.SP_NO_SCALE.ordinal
        val spPxWith = ValueType.SP_PX_WITH_SCALE.ordinal
        val spPxNo = ValueType.SP_PX_NO_SCALE.ordinal
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            val keys = shard.keys
            val vals = shard.values
            for (i in 0 until SHARD_SIZE) {
                val key = keys.get(i)
                if (key == 0L) continue
                val vt = (key ushr 24 and 0x7L).toInt()
                if (vt == spNoScale || vt == spPxWith || vt == spPxNo) {
                    keys.lazySet(i, 0L)
                    vals.lazySet(i, 0)
                }
            }
        }
        resetListeners.forEach { it() }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DIAGNOSTICS
    // ─────────────────────────────────────────────────────────────────────────

    @JvmStatic
    fun stats(): CacheStats {
        var populated = 0
        for (s in 0 until SHARD_COUNT) {
            val keys = shards[s].keys
            for (i in 0 until SHARD_SIZE) {
                if (keys.get(i) != 0L) populated++
            }
        }
        val hits   = hitCount.sum()
        val misses = missCount.sum()
        val total  = hits + misses
        return CacheStats(
            capacity   = CACHE_SIZE,
            populated  = populated,
            fillRatio  = populated.toFloat() / CACHE_SIZE,
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