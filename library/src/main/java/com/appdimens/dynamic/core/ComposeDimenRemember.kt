/**
 * Shared Compose remember + [DimenCache] wiring (no scaling formulas).
 */
package com.appdimens.dynamic.core

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * EN Remembers a scaled [Dp]. When [match] is false, returns [passthrough] without
 * touching [DimenCache] — used by `*Plain` APIs so [remember] is always called
 * (stable Compose slots) while the miss branch stays a true no-op.
 *
 * PT Lembra um [Dp] escalado. Com [match] falso devolve [passthrough] sem cache.
 */
@Composable
fun rememberDimenDp(
    cacheKey: Long,
    layoutStamp: Long,
    androidContext: Context,
    match: Boolean = true,
    passthrough: Dp = Dp.Unspecified,
    compute: () -> Float,
): Dp {
    val metrics = LocalDimenMetrics.current
    return if (match) {
        remember(cacheKey, layoutStamp, metrics) {
            resolveCachedFloat(cacheKey, metrics, androidContext, compute).dp
        }
    } else {
        remember(match, cacheKey, layoutStamp, passthrough) { passthrough }
    }
}

/**
 * EN Remembers scaled Dp→Px. When [match] is false, returns [passthrough] unchanged.
 * PT Lembra Dp→Px; com [match] falso devolve [passthrough].
 */
@Composable
fun rememberDimenPxFromDp(
    cacheKey: Long,
    pxStamp: Long,
    androidContext: Context,
    density: Density,
    match: Boolean = true,
    passthrough: Float = Float.NaN,
    compute: () -> Float,
): Float {
    val metrics = LocalDimenMetrics.current
    return if (match) {
        remember(cacheKey, pxStamp, metrics) {
            val scaledDp = resolveCachedFloat(cacheKey, metrics, androidContext, compute)
            density.run { scaledDp.dp.toPx() }
        }
    } else {
        remember(match, cacheKey, pxStamp, passthrough) { passthrough }
    }
}

/**
 * EN Remembers a scaled [TextUnit] (Sp path). Passthrough when [match] is false.
 * PT Lembra um [TextUnit]; passthrough quando [match] é falso.
 */
@Composable
fun rememberDimenSp(
    cacheKey: Long,
    spStamp: Long,
    match: Boolean = true,
    passthrough: TextUnit = TextUnit.Unspecified,
    compute: () -> TextUnit,
): TextUnit = if (match) {
    val metrics = LocalDimenMetrics.current
    remember(cacheKey, spStamp, metrics) { compute() }
} else {
    remember(match, cacheKey, spStamp, passthrough) { passthrough }
}

/**
 * EN Remembers Sp→Px. Passthrough when [match] is false.
 * PT Lembra Sp→Px; passthrough quando [match] é falso.
 */
@Composable
fun rememberDimenSpPx(
    cacheKey: Long,
    sspPxStamp: Long,
    match: Boolean = true,
    passthrough: Float = Float.NaN,
    compute: () -> Float,
): Float = if (match) {
    val metrics = LocalDimenMetrics.current
    remember(cacheKey, sspPxStamp, metrics) { compute() }
} else {
    remember(match, cacheKey, sspPxStamp, passthrough) { passthrough }
}

private fun resolveCachedFloat(
    cacheKey: Long,
    metrics: DimenMetrics?,
    androidContext: Context,
    compute: () -> Float,
): Float = when {
    metrics != null -> DimenCache.getOrPut(cacheKey, metrics, compute)
    else -> DimenCache.getOrPut(cacheKey, androidContext, compute)
}
