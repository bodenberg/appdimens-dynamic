/**
 * EN Step table + binary search for auto-resize (largest size that still “fits”).
 * PT Tabela de passos + busca binária para auto-resize.
 */
package com.appdimens.dynamic.core

import kotlin.math.max
import kotlin.math.min

/**
 * EN Builds ascending px samples from [minPx] to [maxPx] inclusive, advancing by [stepPx].
 * If [stepPx] <= 0, returns `[minPx.coerceIn(minPx, maxPx)]` (single sample).
 */
private const val MAX_RESIZE_STEPS = 4096

fun buildResizeStepsPx(minPx: Float, maxPx: Float, stepPx: Float): FloatArray {
    val lo = min(minPx, maxPx)
    val hi = max(minPx, maxPx)
    if (stepPx <= 0f) {
        return floatArrayOf(lo)
    }
    val capacity = (((hi - lo) / stepPx).toInt() + 2).coerceIn(1, MAX_RESIZE_STEPS)
    val buf = FloatArray(capacity)
    var x = lo
    val epsilon = stepPx * 1e-4f
    var count = 0
    while (x <= hi + epsilon && count < capacity) {
        buf[count] = min(x, hi)
        x += stepPx
        count++
    }
    if (count == 0) { buf[0] = lo; count = 1 }
    if (buf[count - 1] < hi - epsilon && count < capacity) { buf[count] = hi; count++ }
    return if (count == capacity) buf else buf.copyOf(count)
}

/**
 * EN Largest step in [sortedStepsPx] (ascending) for which [fits] returns true.
 * If none fit, returns `0f`.
 */
fun findLargestFittingResizePx(
    sortedStepsPx: FloatArray,
    fits: (candidatePx: Float) -> Boolean,
): Float {
    if (sortedStepsPx.isEmpty()) return 0f
    if (sortedStepsPx.size == 1) return if (fits(sortedStepsPx[0])) sortedStepsPx[0] else 0f
    var left = 0
    var right = sortedStepsPx.size - 1
    var best = 0f
    while (left <= right) {
        val mid = (left + right) ushr 1
        val v = sortedStepsPx[mid]
        if (fits(v)) {
            best = v
            left = mid + 1
        } else {
            right = mid - 1
        }
    }
    return best
}

/**
 * EN Normalizes min/max order; [steps] covers [[lowPx],[highPx]] with [stepPx] granularity.
 */
data class ResizeRangePx(
    val minPx: Float,
    val maxPx: Float,
    val stepPx: Float,
) {
    val lowPx: Float = min(minPx, maxPx)
    val highPx: Float = max(minPx, maxPx)
    val steps: FloatArray = buildResizeStepsPx(lowPx, highPx, stepPx)

    fun resolveFitting(fits: (Float) -> Boolean): Float =
        findLargestFittingResizePx(steps, fits)
}
