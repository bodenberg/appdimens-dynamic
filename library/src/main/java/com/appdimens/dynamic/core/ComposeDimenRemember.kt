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
internal fun rememberDimenDp(
    cacheKey: Long,
    layoutStamp: Long,
    androidContext: Context,
    match: Boolean = true,
    passthrough: Dp = Dp.Unspecified,
    compute: () -> Float,
): Dp = remember(match, cacheKey, layoutStamp, passthrough) {
    if (!match) passthrough
    else DimenCache.getOrPut(cacheKey, androidContext, compute).dp
}

/**
 * EN Remembers scaled Dp→Px. When [match] is false, returns [passthrough] unchanged.
 * PT Lembra Dp→Px; com [match] falso devolve [passthrough].
 */
@Composable
internal fun rememberDimenPxFromDp(
    cacheKey: Long,
    pxStamp: Long,
    androidContext: Context,
    density: Density,
    match: Boolean = true,
    passthrough: Float = Float.NaN,
    compute: () -> Float,
): Float = remember(match, cacheKey, pxStamp, passthrough) {
    if (!match) passthrough
    else DimenCache.getOrPut(cacheKey, androidContext) {
        val scaledDp = compute()
        density.run { scaledDp.dp.toPx() }
    }
}

/**
 * EN Remembers a scaled [TextUnit] (Sp path). Passthrough when [match] is false.
 * PT Lembra um [TextUnit]; passthrough quando [match] é falso.
 */
@Composable
internal fun rememberDimenSp(
    cacheKey: Long,
    spStamp: Long,
    match: Boolean = true,
    passthrough: TextUnit = TextUnit.Unspecified,
    compute: () -> TextUnit,
): TextUnit = remember(match, cacheKey, spStamp, passthrough) {
    if (!match) passthrough else compute()
}

/**
 * EN Remembers Sp→Px. Passthrough when [match] is false.
 * PT Lembra Sp→Px; passthrough quando [match] é falso.
 */
@Composable
internal fun rememberDimenSpPx(
    cacheKey: Long,
    sspPxStamp: Long,
    match: Boolean = true,
    passthrough: Float = Float.NaN,
    compute: () -> Float,
): Float = remember(match, cacheKey, sspPxStamp, passthrough) {
    if (!match) passthrough else compute()
}
