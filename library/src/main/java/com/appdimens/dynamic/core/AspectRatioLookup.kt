/**
 * Exact aspect-ratio math.
 *
 * Aspect-ratio factors are created once in [DimenMetrics], outside the normal rendering
 * path. A hand-maintained lookup table would make two nearby window sizes produce the same
 * approximated result and is slower to maintain than one deterministic `ln` at snapshot time.
 */
package com.appdimens.dynamic.core

import kotlin.math.ln

/**
 * Kept for source compatibility. The function deliberately does not approximate: a dimension
 * resolver must be deterministic for every valid window ratio.
 */
object AspectRatioLookup {
    @JvmStatic
    fun lookup(normalizedAr: Float): Float? =
        if (normalizedAr.isFinite() && normalizedAr > 0f) ln(normalizedAr.toDouble()).toFloat() else null
}

/** Exact natural logarithm with a safe neutral fallback for invalid configuration input. */
fun fastLn(normalizedAr: Float): Float = AspectRatioLookup.lookup(normalizedAr) ?: 0f
