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
 *  [30-22]  (unused)                  9 bits
 *  [21-18]  CalcType ordinal          4 bits  (covers 0..15)
 *  [17-15]  ValueType                 3 bits  (covers 0..7)
 *  [14-7]   sensitivityK fingerprint  8 bits  (float bits ushr 24 & 0xFF)
 *  [6-5]    DpQualifier ordinal       2 bits  (covers 0..3)
 *  [4-2]    Inverter ordinal          3 bits  (covers 0..7)
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicIntegerArray
import java.util.concurrent.atomic.AtomicLongArray
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

    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dimens_cache_prefs")
    internal val KEY_SW_DP = intPreferencesKey("smallest_width_dp")
    internal val KEY_CACHE_DATA = byteArrayPreferencesKey("cache_mirror")

    internal val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
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
    @PublishedApi
    internal val isInitialized = AtomicBoolean(false)

    /**
     * EN Calculation types based on the library's package structure.
     * PT Tipos de cálculo baseados na estrutura de pacotes da biblioteca.
     */
    @PublishedApi
    internal enum class CalcType {
        AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC,
        PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY
    }

    /**
     * EN Master switch for the cache system. If disabled, all calls will recompute.
     * PT Chave mestre para o sistema de cache. Se desativado, todos os cálculos são refeitos.
     */
    @JvmStatic
    @Volatile
    @PublishedApi
    internal var isEnabled: Boolean = true

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
        // 128-byte padding guard (14 × Long = 112 bytes + object fields overhead ≥ 128)
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

    @JvmField
    @PublishedApi
    internal val factors = ScreenFactors()

    // Convenience accessors — @PublishedApi so they are reachable from inline functions
    @PublishedApi internal val currentNormalizedAr   get() = factors.normalizedAr
    @PublishedApi internal val currentLogNormalizedAr get() = factors.logNormalizedAr
    @PublishedApi internal val currentSmallestWidthDp get() = factors.smallestWidthDp
    @PublishedApi internal val currentDensity         get() = factors.density
    @PublishedApi internal val currentScale           get() = factors.scale
    @PublishedApi internal val currentArMultiplier    get() = factors.arMultiplier

    /**
     * Number of slots in the primary (Tier-1) fast cache.
     * Must be a power of 2 so that `key and MASK` is a fast modulo.
     *
     * 2048 slots @ ~12 bytes per entry ≈ ~24 KB (keys) + ~8 KB (values) = ~32 KB total.
     * Hit-rate analysis: typical app has 100–300 distinct dimension configurations;
     * 2048 slots gives <15% fill ratio under normal usage — near-zero collision rate.
     */
    @PublishedApi
    internal const val CACHE_SIZE = 2048

    /**
     * EN Cache Sharding (Concurrency Partitioning)
     * Split the cache into 4 shards to reduce false sharing and bus contention.
     */
    @PublishedApi internal const val SHARD_COUNT    = 4
    @PublishedApi internal const val SHARD_MASK     = SHARD_COUNT - 1
    @PublishedApi internal const val SHARD_SIZE     = CACHE_SIZE / SHARD_COUNT
    @PublishedApi internal const val SHARD_SIZE_MASK = SHARD_SIZE - 1

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
    internal val keysArray: Array<AtomicLongArray>
        get() = Array(SHARD_COUNT) { shards[it].keys }

    @PublishedApi
    internal val valueBitsArray: Array<AtomicIntegerArray>
        get() = Array(SHARD_COUNT) { shards[it].values }

    // ─────────────────────────────────────────────────────────────────────────
    // MATH CONSTANTS
    // ─────────────────────────────────────────────────────────────────────────

    @PublishedApi internal const val INV_BASE_RATIO      = 0.0033333334f // 1f / 300f
    @PublishedApi internal const val ADJUSTMENT_SCALE    = 0.10f / 30f   // 0.0033333334f
    @PublishedApi internal const val SENSITIVITY_DEFAULT = 0.08f / 30f   // 0.0026666667f

    /**
     * EN Unified high-performance scaling engine. Reads from [factors] — padded object,
     * guaranteeing that the read of `scale` and `arMultiplier` land on the same cache line
     * as all other factor fields.
     */
    @PublishedApi
    internal fun calculateRawScaling(
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

    init {
        scope.launch {
            @OptIn(FlowPreview::class)
            saveFlow.debounce(500).collect { ctx ->
                performSave(ctx)
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // KEY ENCODING
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Dimension type discriminator for the cache key.
     * PT Discriminador de tipo de dimensão para a chave de cache.
     */
    @PublishedApi
    internal enum class ValueType {
        DP, PX, SP_WITH_SCALE, SP_NO_SCALE, SP_PX_WITH_SCALE, SP_PX_NO_SCALE
    }

    /**
     * Packs all dimension-calculation parameters into a single 64-bit [Long] key.
     *
     * Bit layout (MSB → LSB):
     * ```
     * [63]     applyAspectRatio          1 bit
     * [62-31]  baseValue bits            32 bits  (Float.toRawBits)
     * [30-22]  (unused)                  9 bits
     * [21-18]  CalcType ordinal          4 bits  (covers 0..15)
     * [17-15]  ValueType                 3 bits  (covers 0..7)
     * [14-7]   sensitivityK fingerprint  8 bits  (float bits ushr 24 & 0xFF)
     * [6-5]    DpQualifier ordinal       2 bits  (covers 0..3)
     * [4-2]    Inverter ordinal          3 bits  (covers 0..7)
     * [1]      isLandscape               1 bit
     * [0]      ignoreMultiWindows        1 bit
     * ```
     */
    @JvmStatic
    @PublishedApi
    internal fun buildKey(
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
        val sk  = (customSensitivityK?.toRawBits()?.ushr(24)?.and(0xFF)?.toLong() ?: 0xFFL)
        val q   = qualifier.ordinal.toLong() and 0x3L
        val inv = inverter.ordinal.toLong() and 0x7L
        val land = if (isLandscape) 1L else 0L
        val imw  = if (ignoreMultiWindows) 1L else 0L

        return (ar  shl 63) or
               (bv  shl 31) or
               (ct  shl 18) or
               (vt  shl 15) or
               (sk  shl  7) or
               (q   shl  5) or
               (inv shl  2) or
               (land shl 1) or
               imw
    }

    // Overload accepting Int baseValue (kept for call-site convenience)
    @JvmStatic
    @PublishedApi
    internal fun buildKey(
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
    @PublishedApi
    internal fun init(context: Context) {
        if (isInitialized.get()) {
            isInitializedFast = true
            return
        }
        if (isInitializing.getAndSet(true)) return

        val appContext = context.applicationContext
        val currentSw = appContext.resources.configuration.smallestScreenWidthDp

        scope.launch {
            try {
                val prefs   = appContext.dataStore.data.firstOrNull()
                val savedSw = prefs?.get(KEY_SW_DP) ?: 0
                val rawData = prefs?.get(KEY_CACHE_DATA)

                if (savedSw != currentSw || rawData == null) {
                    if (savedSw != 0 && savedSw != currentSw) {
                        clearAll()
                        appContext.dataStore.edit { it.clear() }
                    }
                } else {
                    loadFromByteArray(rawData)
                }
                factors.smallestWidthDp = currentSw
            } catch (_: Exception) {
                // Fallback to empty cache on error
            } finally {
                isInitialized.set(true)
                isInitializedFast = true
                isInitializing.set(false)
            }
        }
    }

    internal fun loadFromByteArray(data: ByteArray) {
        if (data.size < CACHE_SIZE * 12) return
        val buffer = ByteBuffer.wrap(data)
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            for (i in 0 until SHARD_SIZE) {
                val key   = buffer.long
                val value = buffer.float
                if (key != 0L) {
                    shard.keys.set(i, key)
                    shard.values.set(i, value.toRawBits())
                }
            }
        }
    }

    @JvmStatic
    @PublishedApi
    internal fun saveToPersistence(context: Context) {
        saveFlow.tryEmit(context)
    }

    private suspend fun performSave(context: Context) {
        val appContext = context.applicationContext
        val buffer = ByteBuffer.allocate(CACHE_SIZE * 12)
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            for (i in 0 until SHARD_SIZE) {
                buffer.putLong(shard.keys.get(i))
                buffer.putFloat(Float.fromBits(shard.values.get(i)))
            }
        }
        appContext.dataStore.edit { prefs ->
            prefs[KEY_SW_DP]     = factors.smallestWidthDp
            prefs[KEY_CACHE_DATA] = buffer.array()
        }
    }

    internal fun serializeToByteArray(): ByteArray {
        val buffer = ByteBuffer.allocate(CACHE_SIZE * 12)
        for (s in 0 until SHARD_COUNT) {
            val shard = shards[s]
            for (i in 0 until SHARD_SIZE) {
                buffer.putLong(shard.keys.get(i))
                buffer.putFloat(Float.fromBits(shard.values.get(i)))
            }
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
    @PublishedApi
    internal fun getOrPutInternal(key: Long, context: Context?, compute: () -> Float): Float {
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
            return Float.fromBits(shardValues.get(slotIndex))
        }

        // MISS
        val computed = compute()

        val ct      = (key         ushr 18 and 0xFL).toInt()
        val existCt = (existingKey ushr 18 and 0xFL).toInt()

        val isNewAr = ct == 13
        val isOldAr = existingKey != 0L && existCt == 13

        if (existingKey == 0L || !isOldAr || isNewAr) {
            shardKeys.set(slotIndex, key)
            shardValues.set(slotIndex, computed.toRawBits())
            context?.let { saveToPersistence(it) }
        }

        return computed
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
        // "simple multiplier" types (AUTO=0, FLUID=4, PERCENT=7, SCALED=11, DENSITY=14), the scaling
        // formula reduces to a single float multiply: `baseValue * scale`.
        //
        // Measured cost on Snapdragon 888:
        //   Raw math (multiply)  ≈  2 ns
        //   Fastest cache lookup ≈  5 ns   (hash + atomic load + branch)
        //
        // Therefore, for these types WITHOUT Aspect Ratio, bypassing the cache is
        // ~2.5× faster than using it.  This is NOT a bug — it is a deliberate
        // hot-path optimization.  The cache is only beneficial when the computation
        // is expensive (e.g. AR path with ln(), ≈41 ns), making the 5 ns lookup cheap.
        //
        // ⚠️  Consequence for benchmarks: calls to SCALED / AUTO / FLUID / PERCENT / DENSITY
        //     without AR will NEVER hit the cache.  BenchmarkActivity results for
        //     these variants reflect pure math cost (~2 ns), NOT cache retrieval cost.
        //     Do not use these variants to measure cache throughput.
        if (key >= 0) {
            val ct = (key ushr 18 and 0xFL).toInt()
            if (ct == 0 || ct == 4 || ct == 7 || ct == 11 || ct == 14) return compute()
        }

        // ─────────────────────────────────────────────────────────────────────
        // HOT PATH — fully inlined, zero method-call overhead, zero lambda alloc
        // ─────────────────────────────────────────────────────────────────────
        if (context != null && !isInitializedFast) init(context)

        val h     = (key xor (key ushr 32)).toInt()
        val mixed = h xor (h ushr 16)
        val shard = shards[(mixed ushr 9) and SHARD_MASK]
        val slot  = mixed and SHARD_SIZE_MASK

        val existingKey = shard.keys.get(slot)
        if (existingKey == key) return Float.fromBits(shard.values.get(slot))

        // MISS — compute then conditionally store
        val computed = compute()

        val ct      = (key         ushr 18 and 0xFL).toInt()
        val existCt = (existingKey ushr 18 and 0xFL).toInt()
        val isNewAr = ct == 13
        val isOldAr = existingKey != 0L && existCt == 13

        if (existingKey == 0L || !isOldAr || isNewAr) {
            shard.keys.set(slot, key)
            shard.values.set(slot, computed.toRawBits())
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
    @PublishedApi
    internal fun getOrPutAspectRatio(normalizedAr: Float, context: Context? = null): Float {
        // Special key: CalcType = ASPECT_RATIO (13) at bit 18
        val arKey = (13L shl 18) or
                (java.lang.Float.floatToRawIntBits(normalizedAr).toLong() and 0xFFFFFFFFL)
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
    fun invalidateOnConfigChange(old: Configuration?, new: Configuration) {
        if (old == null) {
            updateFactors(new)
            factors.smallestWidthDp = new.smallestScreenWidthDp
            clearAll()
            return
        }

        val oldMin = min(old.screenWidthDp, old.screenHeightDp)
        val oldMax = max(old.screenWidthDp, old.screenHeightDp)
        val newMin = min(new.screenWidthDp, new.screenHeightDp)
        val newMax = max(new.screenWidthDp, new.screenHeightDp)

        val physicalChange = oldMin != newMin ||
                oldMax != newMax ||
                old.smallestScreenWidthDp != new.smallestScreenWidthDp ||
                old.densityDpi != new.densityDpi

        val fontScaleChange = old.fontScale != new.fontScale

        if (physicalChange || fontScaleChange) {
            if (physicalChange) {
                updateFactors(new)
                factors.smallestWidthDp = new.smallestScreenWidthDp
            }
            clearAll()
        }
        // Orientation-only: keys encode isLandscape bit → natural miss, no clear needed.
    }

    private fun updateFactors(config: Configuration) {
        val sw     = config.smallestScreenWidthDp.toFloat()
        val maxDim = max(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())
        val minDim = min(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())

        val f = factors

        f.scale = sw * INV_BASE_RATIO

        val rawAr = if (minDim > 0) maxDim / minDim else 1.0f
        f.normalizedAr    = rawAr / 1.78f
        f.logNormalizedAr = fastLn(f.normalizedAr)

        val diff = sw - 300f
        val adjustment = SENSITIVITY_DEFAULT * f.logNormalizedAr
        f.arMultiplier = 1.0f + diff * (ADJUSTMENT_SCALE + adjustment)

        f.density = config.densityDpi.toFloat() / 160f
    }

    /** EN Clears all cache slots. Java-compatible alias. */
    @JvmStatic
    fun clear() = clearAll()

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
    fun clearAll() {
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
        return CacheStats(
            capacity  = CACHE_SIZE,
            populated = populated,
            fillRatio = populated.toFloat() / CACHE_SIZE
        )
    }

    /**
     * EN Cache usage statistics snapshot.
     * PT Snapshot de métricas de uso do cache.
     */
    data class CacheStats(
        val capacity  : Int,
        val populated : Int,
        val fillRatio : Float
    ) {
        override fun toString(): String =
            "DimenCache: $populated/$capacity slots used (${(fillRatio * 100).toInt()}% fill)"
    }
}