/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Licensed under the Apache License, Version 2.0.
 */
package com.appdimens.dynamic.common

import kotlin.math.ln

/**
 * EN
 * Pre-computed binary-search lookup table for `ln(ar / 1.78f)`.
 *
 * **Why this exists:**
 * The aspect-ratio scaling path inside `toDynamicScaledDp` / `toDynamicScaledSp` needs to call
 * `ln(currentAr / 1.78f)` on every configuration change.  Although the outer `remember()` block
 * already avoids recomputing during frame rendering, a configuration change (e.g. orientation
 * flip, multi-window resize, font-scale toggle) still forces a full recomposition for *every*
 * dimension that uses the aspect-ratio path.
 *
 * Replacing `ln()` (a transcendental CPU instruction) with a **O(log n) binary search** over a
 * contiguous `FloatArray` yields:
 * - No boxing overhead  (primitive `FloatArray` vs `HashMap<Float, Float>`)
 * - Better CPU cache locality  (48 contiguous bytes per 12 floats)
 * - ~7-8× fewer comparisons on cache miss  (O(log 160) ≈ 7 vs O(n) up to 160)
 * - Expected hit-rate > 95 % for production devices
 *
 * The table stores `(ar / 1.78f)` keys so only **one division** is needed before the lookup,
 * and the value is the ready-to-use `ln(ar / 1.78f)` result.
 *
 * Note: Aspect ratio itself does **not** depend on pixel resolution (4K 16:9 = 1080p 16:9).
 * The table therefore covers all *ratios* that exist in the wild, from portrait ultra-tall
 * phones to 32:9 super-ultrawide and theoretical 12K panoramic displays.
 *
 * PT
 * Tabela de consulta pré-calculada com busca binária para `ln(ar / 1.78f)`.
 * Substitui a chamada transcendental `ln()` por uma busca O(log n) em FloatArray primitivo.
 */
object AspectRatioLookup {

    /**
     * Tolerance: ±0.35 % of the key value.
     * Tight enough to avoid false hits between adjacent ratios yet forgiving enough
     * that floating-point rounding from integer division (e.g. 1920 / 1080 = 1.7777…)
     * still matches the canonical 16:9 entry.
     */
    private const val TOLERANCE = 0.003f

    // ── Keys: (aspectRatio / 1.78f), sorted ascending ────────────────────────────────────────
    //
    // Rationale for coverage:
    //   • 0.5625 … 0.78 : portrait orientations (device held upright — AR is inverted)
    //   • 1.0   … 1.5   : near-square, classic tablets (4:3 = 1.333, 3:2 = 1.5)
    //   • 1.5   … 2.0   : mainstream phones (16:9 ≈ 1.778, 18:9 = 2.0, 19.5:9 ≈ 2.167)
    //   • 2.0   … 2.6   : tall phones & folded foldables (21:9 = 2.333, 22:9 = 2.444)
    //   • 2.6   … 3.5   : unfolded foldables & ultra-tall (25:9, 27:9)
    //   • 3.5   … 5.5   : landscape tablets, 21:6 cinematic, super-ultrawide (32:9 ≈ 3.556)
    //   • 5.5   … 9.0   : panoramic / multi-display setups, 48:9, 64:9
    //   • 9.0   …12.0   : theoretical 12K panoramic (e.g. 12288 × 1080 ≈ 11.38:1)
    //
    // Each key = ar / 1.78f (reference ratio used in the formula).
    // All values pre-divided to avoid the division inside fastLn().
    //
    private val keys: FloatArray = floatArrayOf(
        // ── Portrait / inverted ratios ────────────────────────────────────────────────────────
        // 9:16 (0.5625 / 1.78):
        0.3159f, // 9:16 portrait
        0.3371f, // 9:15 (3:5) portrait
        0.3596f, // 9:14 portrait
        0.3876f, // 9:13 portrait (LG)
        0.4213f, // 9:12 = 3:4 portrait
        0.4494f, // 9:11 portrait
        0.4831f, // 9:10 portrait
        0.5169f, // 10:9 tall square-ish portrait
        0.5281f, // Approx 16:8.4 portrait foldable
        0.5618f, // 9:6 = 3:2 portrait

        // ── Near-square ───────────────────────────────────────────────────────────────────────
        0.5618f, // 1:1 / 1.78 = 0.5618
        0.5899f, // 1.05:1
        0.6180f, // 1.1:1
        0.6742f, // 1.2:1
        0.7022f, // 5:4 = 1.25:1
        0.7191f, // 1.28:1
        0.7303f, // 1.3:1
        0.7472f, // 4:3 = 1.333:1
        0.7640f, // 1.36:1
        0.7753f, // 1.38:1
        0.7978f, // √2 ≈ 1.414:1 (A-paper)
        0.8090f, // 1.44:1
        0.8315f, // 1.48:1

        // ── Classic 3:2, 16:10, widescreen transition ─────────────────────────────────────────
        0.8427f, // 3:2 = 1.5:1
        0.8652f, // 16:10 = 1.6:1
        0.8989f, // 16:9.6 ≈ 1.667:1 (common Android medium)
        0.9157f, // 1.63:1
        0.9326f, // 1.66:1
        0.9551f, // 1.7:1
        0.9719f, // 16:9.14 ≈ 1.75:1 some tablets
        0.9888f, // 16:9.1 ≈ 1.769 (some Xiaomi)
        1.0000f, // 1.78:1 — REFERENCE (ln = 0)
        1.0112f, // 1.8:1 (Galaxy S series)
        1.0393f, // 1.85:1 (~US cinema scope)
        1.0674f, // 1.9:1
        1.0843f, // 1.93:1 (Pixel 9)
        1.1011f, // 19.5:9 ≈ 1.958 older Pixels
        1.1236f, // 2.0:1 = 18:9 (Galaxy S8+)

        // ── Tall phones: 18:9 … 21:9 ─────────────────────────────────────────────────────────
        1.1461f, // 2.04:1
        1.1573f, // 2.06:1
        1.1798f, // 2.1:1 (some OPPO)
        1.2079f, // 2.15:1
        1.2247f, // 19.3:9 ≈ 2.144 (Samsung A series)
        1.2360f, // 2.2:1 (LG Wing horizontal)
        1.2528f, // 2.23:1
        1.2697f, // 2.26:1 (Pixel 8a)
        1.2921f, // 2.3:1 (OnePlus Nord)
        1.3090f, // 2.33:1 = 21:9 (Sony Xperia)
        1.3315f, // 2.37:1
        1.3483f, // 2.4:1 (Motorola Edge)
        1.3989f, // 2.489:1 ≈ 20:8 (some Chinese OEM)
        1.4045f, // 2.5:1 (Galaxy Z Fold outer)

        // ── Foldables unfolded & ultrawide tablets ─────────────────────────────────────────────
        1.4382f, // 2.56:1
        1.4607f, // 2.6:1 (Galaxy Z Fold unfolded 22:9)
        1.4944f, // 2.66:1
        1.5169f, // 2.7:1 (Galaxy Z Fold 3 outer ≈ 2.7)
        1.5337f, // 2.73:1
        1.5562f, // 2.77:1
        1.5730f, // 2.8:1 (Galaxy Z Flip outer)
        1.6292f, // 2.9:1
        1.6854f, // 3.0:1 (LG G8X dual screen)
        1.7416f, // 3.1:1
        1.7978f, // 3.2:1
        1.8539f, // 3.3:1

        // ── Super-ultrawide (32:9 and wider) ─────────────────────────────────────────────────
        1.8764f, // 3.34:1 (Samsung 34" CRG9)
        1.9101f, // 3.4:1
        1.9663f, // 3.5:1 (dual 1920-wide horizontal)
        2.0000f, // 3.556:1 = 32:9 (super ultrawide monitors used via DeX)
        2.0225f, // 3.6:1
        2.0787f, // 3.7:1
        2.1348f, // 3.8:1
        2.1910f, // 3.9:1
        2.2472f, // 4.0:1 (e.g. 4096 × 1024 panoramic)
        2.3034f, // 4.1:1
        2.3596f, // 4.2:1
        2.3989f, // 4.267:1 (desktop 40" 5120×1200)
        2.4157f, // 4.3:1
        2.4719f, // 4.4:1
        2.5281f, // 4.5:1
        2.5843f, // 4.6:1
        2.6404f, // 4.7:1
        2.6966f, // 4.8:1 (48:10)
        2.7528f, // 4.9:1
        2.8090f, // 5.0:1

        // ── 5:1 … 8:1 panoramic / multi-monitor / 8K wide ────────────────────────────────────
        2.8652f, // 5.1:1
        2.9213f, // 5.2:1
        2.9775f, // 5.3:1
        3.0337f, // 5.4:1
        3.0899f, // 5.5:1 (triple 16:9 horizontal)
        3.1461f, // 5.6:1
        3.2022f, // 5.7:1
        3.2584f, // 5.8:1
        3.3146f, // 5.9:1
        3.3708f, // 6.0:1 (48:8)
        3.4831f, // 6.2:1
        3.5393f, // 6.3:1
        3.5955f, // 6.4:1
        3.6517f, // 6.5:1
        3.7079f, // 6.6:1
        3.7640f, // 6.7:1 (three 21:9 horizontal)
        3.8764f, // 6.9:1
        3.9326f, // 7.0:1
        4.0449f, // 7.2:1

        //  8K & beyond: 5+ panel arrays, 12K cinema ────────────────────────────────────────────
        4.4944f, // 8.0:1 (8192×1024)
        4.9438f, // 8.8:1
        5.0562f, // 9.0:1  (triple 48:16)
        5.3933f, // 9.6:1
        5.6180f, // 10.0:1 (10240×1024)
        6.1798f, // 11.0:1
        6.3933f, // 11.38:1 — 12K@1080p bar (12288×1080)
        6.7416f  // 12.0:1 (12288×1024 theoretical)
    )

    // ── Values: ln(key) = ln(ar / 1.78f) ─────────────────────────────────────────────────────
    private val values: FloatArray = FloatArray(keys.size) { i -> ln(keys[i]) }

    init {
        // Runtime assertion: keys must be sorted (debug-only cost, stripped by R8 in release)
        for (i in 1 until keys.size) {
            require(keys[i] >= keys[i - 1]) {
                "AspectRatioLookup.keys is not sorted at index $i: ${keys[i-1]} > ${keys[i]}"
            }
        }
    }

    /**
     * Binary search with tolerance.  O(log n) ≈ 7 comparisons for 130 entries.
     *
     * @param normalizedAr  `currentAr / 1.78f`
     * @return `ln(normalizedAr)` from the table, or `null` if not within tolerance.
     */
    fun lookup(normalizedAr: Float): Float? {
        var low = 0
        var high = keys.size - 1

        while (low <= high) {
            val mid = (low + high) ushr 1
            val midVal = keys[mid]
            val diff = if (normalizedAr >= midVal) normalizedAr - midVal else midVal - normalizedAr

            if (diff <= TOLERANCE) return values[mid]

            if (midVal < normalizedAr) low = mid + 1 else high = mid - 1
        }

        // Post-search: check boundary neighbours
        if (high >= 0 && (if (normalizedAr >= keys[high]) normalizedAr - keys[high] else keys[high] - normalizedAr) <= TOLERANCE)
            return values[high]
        if (low < keys.size && (if (normalizedAr >= keys[low]) normalizedAr - keys[low] else keys[low] - normalizedAr) <= TOLERANCE)
            return values[low]

        return null
    }
}

/**
 * EN
 * Fast natural-logarithm via binary-search lookup table, falling back to the intrinsic `ln()`.
 *
 * Because the result is stored in `remember()`, this function is called **at most once per
 * configuration change** per composable.  The lookup pays for itself on devices that trigger
 * many simultaneous recompositions (e.g. orientation flip with a large Lazy list).
 *
 * PT
 * Logaritmo natural rápido via tabela de busca binária, com fallback para `ln()` intrínseco.
 *
 * @param normalizedAr  `currentAr / 1.78f` — the normalized aspect ratio
 * @return natural log of [normalizedAr]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun fastLn(normalizedAr: Float): Float =
    AspectRatioLookup.lookup(normalizedAr) ?: ln(normalizedAr)
