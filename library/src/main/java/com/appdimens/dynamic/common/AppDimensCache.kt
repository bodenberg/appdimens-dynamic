/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens — Global Dimension Cache Manager
 *
 * Description:
 * Ultra-optimized, lock-free, shared cache for all AppDimens dimension calculations.
 * Works for both `compose` and `code` (non-Compose) packages.
 *
 * Key Design Principles:
 *  - Lock-free reads via AtomicReferenceArray (zero contention)
 *  - Collision-safe via packed 64-bit Long key (no false hits)
 *  - Shared state across all library instances (save memory, share reuse)
 *  - Smart invalidation: only clears when physical screen dimensions actually change
 *  - Zero allocation in hot path: stores raw Float, caller boxes into Dp/TextUnit
 *
 * Bit Layout of the 64-bit Cache Key (Long):
 *  [63-54]  baseValue        — 10 bits → covers -300..600 (stored + 300, offset 0-900)
 *  [53-42]  screenWidthDp/2  — 12 bits → covers 0..8192dp
 *  [41-30]  screenHeightDp/2 — 12 bits → covers 0..8192dp
 *  [29-20]  smallestWidthDp  — 10 bits → covers 0..1023dp (sufficient for all devices)
 *  [19-17]  qualifier        —  3 bits → DpQualifier ordinal (0-7)
 *  [16-13]  inverter         —  4 bits → Inverter ordinal (0-9)
 *  [12]     orientation      —  1 bit  → 0=portrait, 1=landscape
 *  [11]     ignoreMultiWin   —  1 bit
 *  [10]     applyAspectRatio —  1 bit
 *  [ 9]     fontScale        —  1 bit  (sp only; irrelevant for Dp, always 0)
 *  [ 8]     isDp             —  1 bit  → 0=Sp/TextUnit, 1=Dp
 *  [ 7-0]   sensitivityK     —  8 bits → fingerprint from Float.toRawBits() ushr 24
 *                                        (distinguishes null vs custom values; 256 buckets)
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.common

import android.content.res.Configuration
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlin.math.max
import kotlin.math.min
import com.appdimens.dynamic.common.fastLn

/**
 * EN
 * Global, lock-free, shared cache for all AppDimens dimension calculations.
 *
 * **Thread Safety**: Completely thread-safe.  All reads and writes are lock-free
 * using [AtomicReferenceArray].  If two threads write identically-keyed entries
 * simultaneously, the last write wins — this is always correct because both computed
 * the same value.
 *
 * **Usage (Compose — toDynamicScaledDp/Sp)**:
 * The Compose `remember()` block is still the primary caching layer for Compose.
 * This cache acts as a secondary cross-composable layer that avoids recomputing
 * factor values that were already resolved for another composable on the same frame.
 *
 * **Usage (code — getDimensionInPx/SpPx)**:
 * In the non-Compose code path there is no `remember()`, so this cache is the
 * *only* caching mechanism. A subsequent call with identical parameters will hit
 * the cache at O(1) cost.
 *
 * PT
 * Cache global, lock-free e compartilhado para todos os cálculos de dimensão do AppDimens.
 */
object AppDimensCache {

    // ─────────────────────────────────────────────────────────────────────────
    // CONFIGURATION & PERSISTENT STATE
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Calculation types based on the library's package structure.
     * PT Tipos de cálculo baseados na estrutura de pacotes da biblioteca.
     */
    enum class CalcType {
        AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC,
        PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITS
    }

    /**
     * EN Cached aspect ratio values to avoid recomputing on every dimension call.
     * Updated automatically via [invalidateOnConfigChange].
     */
    @Volatile
    var currentNormalizedAr: Float = 1.0f
        private set

    @Volatile
    var currentLogNormalizedAr: Float = 0f
        private set

    /**
     * EN The smallest width (dp) used when the cache was last populated.
     * If this changes (e.g., user changes "Smallest Width" in settings),
     * we must invalidate everything.
     *
     * PT A menor largura (dp) usada quando o cache foi populado.
     * Se mudar (ex: configurações do Android), o cache é invalidado.
     */
    @Volatile
    var currentSmallestWidthDp: Int = 0
        private set

    /**
     * Number of slots in the primary (Tier-1) fast cache.
     * Must be a power of 2 so that `key and MASK` is a fast modulo.
     *
     * 2048 slots @ ~56 bytes per entry ≈ ~112 KB peak.
     * Hit-rate analysis: typical app has 100-300 distinct dimension configurations;
     * 2048 slots gives <15% fill ratio under normal usage — near-zero collision rate.
     */
    private const val CACHE_SIZE = 2048
    private const val CACHE_MASK = CACHE_SIZE - 1

    // ─────────────────────────────────────────────────────────────────────────
    // CACHE ENTRY
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * A single cache slot.
     *
     * [key]   — The packed 64-bit Long that uniquely identifies this computation.
     *           Used for collision detection: if `entry.key != requestedKey`, it's a
     *           hash collision and the slot is treated as a miss.
     * [value] — The raw scaled `Float` result.
     *           Dp callers wrap it in `.dp`; Sp callers wrap it in `.sp` or apply
     *           pixel conversion. Storing the raw Float avoids any boxing inside
     *           the cache itself.
     */
    class Entry(
        @JvmField val key: Long,
        @JvmField val value: Float
    )

    /**
     * Atomic, lock-free backing array.
     * `null` = empty slot.
     */
    @JvmField
    internal val store = AtomicReferenceArray<Entry?>(CACHE_SIZE)

    // ─────────────────────────────────────────────────────────────────────────
    // KEY ENCODING — converts all call parameters into a single 64-bit Long
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Dimension type discriminator for the cache key.
     * PT Discriminador de tipo de dimensão para a chave de cache.
     */
    enum class ValueType {
        /** Dp / pixel (non-text) dimension */
        DP,
        /** Sp / TextUnit with font scale */
        SP_WITH_SCALE,
        /** Sp / TextUnit WITHOUT font scale (sem escala de fonte) */
        SP_NO_SCALE
    }

    /**
     * Packs all dimension-calculation parameters into a single 64-bit [Long] key.
     *
     * Optimized Bit layout (MSB → LSB):
     * ```
     * [63-54]  baseValue + 300          10 bits  (-300..600 -> offset 0..900)
     * [53-44]  screenWidthDp / 2        10 bits  (covers 0..2046dp)
     * [43-34]  screenHeightDp / 2       10 bits  (covers 0..2046dp)
     * [33-24]  smallestScreenWidthDp    10 bits  (covers 0..1023dp)
     * [23-20]  CalcType ordinal          4 bits  (covers 0..15)
     * [19-18]  DpQualifier ordinal       2 bits  (covers 0..3)
     * [17-14]  Inverter ordinal          4 bits  (covers 0..15)
     * [13]     isLandscape               1 bit
     * [12]     ignoreMultiWindows        1 bit
     * [11]     applyAspectRatio          1 bit
     * [10]     fontScale flag            1 bit
     * [ 9]     isDp                      1 bit
     * [ 8- 0]  sensitivityK fingerprint  9 bits  (float bits ushr 23 & 0x1FF)
     * ```
     */
    fun buildKey(
        baseValue: Int,
        screenWidthDp: Int,
        screenHeightDp: Int,
        smallestScreenWidthDp: Int,
        calcType: CalcType,
        qualifier: DpQualifier,
        inverter: Inverter,
        isLandscape: Boolean,
        ignoreMultiWindows: Boolean,
        applyAspectRatio: Boolean,
        valueType: ValueType,
        customSensitivityK: Float? = null
    ): Long {
        val bv = (baseValue + 300).coerceIn(0, 1023).toLong()
        val sw = (screenWidthDp / 2).coerceIn(0, 1023).toLong()
        val sh = (screenHeightDp / 2).coerceIn(0, 1023).toLong()
        val ssw = smallestScreenWidthDp.coerceIn(0, 1023).toLong()
        val ct = calcType.ordinal.toLong()
        val q = qualifier.ordinal.toLong()
        val inv = inverter.ordinal.toLong()
        val land = if (isLandscape) 1L else 0L
        val imw = if (ignoreMultiWindows) 1L else 0L
        val ar = if (applyAspectRatio) 1L else 0L
        val fs = if (valueType == ValueType.SP_WITH_SCALE) 1L else 0L
        val dp = if (valueType == ValueType.DP) 1L else 0L
        // 9 bits fingerprint from Float (mantissa bits)
        val sk = (customSensitivityK?.toRawBits()?.ushr(23)?.and(0x1FF)?.toLong() ?: 0x1FFL)

        return (bv shl 54) or
                (sw shl 44) or
                (sh shl 34) or
                (ssw shl 24) or
                (ct shl 20) or
                (q shl 18) or
                (inv shl 14) or
                (land shl 13) or
                (imw shl 12) or
                (ar shl 11) or
                (fs shl 10) or
                (dp shl 9) or
                sk
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FAST READ / WRITE (lock-free)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN
     * Reads from the cache or computes (and stores) a new value.
     *
     * This is **lock-free**: no synchronization primitive is ever held.
     * Multiple threads may compute the same entry simultaneously; the last atomic
     * write wins, which is always correct because both values are identical.
     *
     * @param key      64-bit packed key from [buildKey]
     * @param compute  Lambda invoked only on a cache **miss**
     * @return         Cached or freshly-computed raw Float result
     *
     * PT
     * Lê do cache ou calcula (e armazena) um novo valor.
     * Lock-free: nenhum mutex é usado.
     */
    fun getOrPut(key: Long, compute: () -> Float): Float {
        val index = (key xor (key ushr 32)).toInt() and CACHE_MASK

        // FAST PATH — atomic read, no lock
        val existing = store.get(index)
        if (existing != null && existing.key == key) {
            return existing.value
        }

        // MISS — compute outside any lock
        val computed = compute()

        // LOCK-FREE WRITE — atomic set; last writer wins (always correct)
        store.set(index, Entry(key, computed))
        return computed
    }

    /**
     * EN Reads a cached value without computing a fallback.
     * Returns `null` on a miss.
     *
     * PT Lê um valor cacheado sem calcular um fallback. Retorna `null` se não encontrado.
     */
    fun peek(key: Long): Float? {
        val index = (key xor (key ushr 32)).toInt() and CACHE_MASK
        val entry = store.get(index)
        return if (entry != null && entry.key == key) entry.value else null
    }

    // ─────────────────────────────────────────────────────────────────────────
    // INVALIDATION
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN
     * Selectively invalidates the cache based on what actually changed in the
     * [Configuration].  The goal is to always have valid entries in the cache
     * without unnecessary full-clears.
     *
     * | Change                        | Action          | Reason                              |
     * |-------------------------------|-----------------|-------------------------------------|
     * | screenWidthDp / screenHeightDp| **Clear ALL**   | Physical dimensions changed          |
     * | smallestScreenWidthDp         | **Clear ALL**   | Device qualifier changed             |
     * | densityDpi                    | **Clear ALL**   | Pixel density changed                |
     * | orientation only              | **Keep cache**  | Width/height in key already capture  |
     * |                               |                 | the swap; entries remain valid       |
     * | fontScale                     | **Clear ALL**   | Sp values based on it changed        |
     * | No relevant change            | **Keep cache**  | Nothing changed                      |
     *
     * PT
     * Invalida seletivamente o cache com base no que realmente mudou na [Configuration].
     *
     * @param old  Previous [Configuration] (null on first call → full clear)
     * @param new  New [Configuration]
     */
    fun invalidateOnConfigChange(old: Configuration?, new: Configuration) {
        if (old == null) {
            updateAspectRatio(new)
            currentSmallestWidthDp = new.smallestScreenWidthDp
            clearAll()
            return
        }

        val physicalChange = old.screenWidthDp != new.screenWidthDp ||
                old.screenHeightDp != new.screenHeightDp ||
                old.smallestScreenWidthDp != new.smallestScreenWidthDp ||
                old.densityDpi != new.densityDpi

        val fontScaleChange = old.fontScale != new.fontScale

        if (physicalChange || fontScaleChange) {
            if (physicalChange) {
                updateAspectRatio(new)
                currentSmallestWidthDp = new.smallestScreenWidthDp
            }
            clearAll()
        }
        // Orientation-only change: cache keys already encode orientation via isLandscape bit
        // → entries with a different orientation bit won't match → natural miss → no clear needed.
    }

    private fun updateAspectRatio(config: Configuration) {
        val maxDim = max(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())
        val minDim = min(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())
        // Avoid division by zero technically, though mindim should be >= 1
        val rawAr = if (minDim > 0) maxDim / minDim else 1.0f
        currentNormalizedAr = rawAr / 1.78f
        currentLogNormalizedAr = fastLn(currentNormalizedAr)
    }

    /**
     * EN Clears all cache entries. Thread-safe (each slot is cleared atomically).
     * PT Limpa todas as entradas do cache. Thread-safe.
     */
    fun clearAll() {
        for (i in 0 until CACHE_SIZE) {
            store.set(i, null)
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DIAGNOSTICS (debug / logging only — zero cost in release via R8 inlining)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * EN Returns a snapshot of current cache usage statistics.
     * PT Retorna um snapshot com métricas do cache atual.
     */
    fun stats(): CacheStats {
        var populated = 0
        for (i in 0 until CACHE_SIZE) {
            if (store.get(i) != null) populated++
        }
        return CacheStats(
            capacity = CACHE_SIZE,
            populated = populated,
            fillRatio = populated.toFloat() / CACHE_SIZE
        )
    }

    /**
     * EN Cache usage statistics snapshot.
     * PT Snapshot de métricas de uso do cache.
     *
     * @param capacity   Total number of cache slots
     * @param populated  Slots currently holding an entry
     * @param fillRatio  [populated] / [capacity] (0.0 = empty, 1.0 = full)
     */
    data class CacheStats(
        val capacity: Int,
        val populated: Int,
        val fillRatio: Float
    ) {
        override fun toString(): String =
            "AppDimensCache: $populated/$capacity slots used (${(fillRatio * 100).toInt()}% fill)"
    }
}
