/**
 * EN Literal percent of screen metrics or a reference [Dp] length (e.g. `10.spaceW` → 10% of screen width).
 * PT Percentual literal de métricas da tela ou de um comprimento [Dp] de referência.
 */
package com.appdimens.dynamic.compose.percent

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.literalPercentOfReferenceDp
import com.appdimens.dynamic.core.literalPercentOfScreenDp
import com.appdimens.dynamic.core.pxRememberStamp

@Composable
private fun rememberLiteralSpaceScreenDp(
    percent: Float,
    qualifier: DpQualifier,
    ignoreMultiWindows: Boolean,
): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val stamp = layoutRememberStamp(configuration, androidContext)
    return remember(stamp, ignoreMultiWindows) {
        literalPercentOfScreenDp(percent, qualifier, configuration, ignoreMultiWindows).dp
    }
}

@Composable
private fun rememberLiteralSpaceScreenPx(
    percent: Float,
    qualifier: DpQualifier,
    ignoreMultiWindows: Boolean,
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val stamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return remember(stamp, ignoreMultiWindows) {
        val dp = literalPercentOfScreenDp(percent, qualifier, configuration, ignoreMultiWindows)
        dp * density.density
    }
}

@Composable
private fun rememberLiteralSpaceReferenceDp(
    percent: Float,
    referenceDp: Float,
    ignoreMultiWindows: Boolean,
): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val stamp = layoutRememberStamp(configuration, androidContext)
    return remember(stamp, referenceDp, ignoreMultiWindows) {
        literalPercentOfReferenceDp(percent, referenceDp, configuration, ignoreMultiWindows).dp
    }
}

@Composable
private fun rememberLiteralSpaceReferencePx(
    percent: Float,
    referenceDp: Float,
    ignoreMultiWindows: Boolean,
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val stamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return remember(stamp, referenceDp, ignoreMultiWindows) {
        val dp = literalPercentOfReferenceDp(percent, referenceDp, configuration, ignoreMultiWindows)
        dp * density.density
    }
}

// ─── Width (screen) ──────────────────────────────────────────────────────────

@get:Composable
val Number.spaceW: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.WIDTH, false)

@get:Composable
val Number.spaceWi: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.WIDTH, true)

@get:Composable
val Number.spaceWPx: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.WIDTH, false)

@get:Composable
val Number.spaceWPxi: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.WIDTH, true)

// ─── Smallest width ──────────────────────────────────────────────────────────

@get:Composable
val Number.spaceSw: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, false)

@get:Composable
val Number.spaceSwi: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, true)

@get:Composable
val Number.spaceSwPx: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.SMALL_WIDTH, false)

@get:Composable
val Number.spaceSwPxi: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.SMALL_WIDTH, true)

// ─── Height ──────────────────────────────────────────────────────────────────

@get:Composable
val Number.spaceH: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.HEIGHT, false)

@get:Composable
val Number.spaceHi: Dp get() = rememberLiteralSpaceScreenDp(toFloat(), DpQualifier.HEIGHT, true)

@get:Composable
val Number.spaceHPx: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.HEIGHT, false)

@get:Composable
val Number.spaceHPxi: Float get() = rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.HEIGHT, true)

// ─── Reference length ────────────────────────────────────────────────────────

@Composable
fun Number.space(reference: Dp, ignoreMultiWindows: Boolean = false): Dp =
    rememberLiteralSpaceReferenceDp(toFloat(), reference.value, ignoreMultiWindows)

@Composable
fun Number.spacePx(reference: Dp, ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.value, ignoreMultiWindows)

@Composable
fun Number.space(reference: Number, ignoreMultiWindows: Boolean = false): Dp =
    rememberLiteralSpaceReferenceDp(toFloat(), reference.toFloat(), ignoreMultiWindows)

@Composable
fun Number.spacePx(reference: Number, ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.toFloat(), ignoreMultiWindows)

@Composable
fun Number.spaceI(reference: Dp): Dp =
    rememberLiteralSpaceReferenceDp(toFloat(), reference.value, ignoreMultiWindows = true)

@Composable
fun Number.spacePxi(reference: Dp): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.value, ignoreMultiWindows = true)

@Composable
fun Number.spaceI(reference: Number): Dp =
    rememberLiteralSpaceReferenceDp(toFloat(), reference.toFloat(), ignoreMultiWindows = true)

@Composable
fun Number.spacePxi(reference: Number): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.toFloat(), ignoreMultiWindows = true)

// ─── Sp (TextUnit) — same physical size as the Dp result when [fontScale] is true ─

@Composable
fun Number.spaceWSp(fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.WIDTH, fontScale, ignoreMultiWindows)

@Composable
fun Number.spaceWSpi(fontScale: Boolean = true): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.WIDTH, fontScale, ignoreMultiWindows = true)

@Composable
fun Number.spaceWSpPx(ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.WIDTH, ignoreMultiWindows)

@Composable
fun Number.spaceWSpiPx(): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.WIDTH, ignoreMultiWindows = true)

@Composable
fun Number.spaceSwSp(fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows)

@Composable
fun Number.spaceSwSpi(fontScale: Boolean = true): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = true)

@Composable
fun Number.spaceSwSpPx(ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.SMALL_WIDTH, ignoreMultiWindows)

@Composable
fun Number.spaceSwSpiPx(): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)

@Composable
fun Number.spaceHSp(fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.HEIGHT, fontScale, ignoreMultiWindows)

@Composable
fun Number.spaceHSpi(fontScale: Boolean = true): TextUnit =
    rememberLiteralSpaceScreenSp(toFloat(), DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = true)

@Composable
fun Number.spaceHSpPx(ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.HEIGHT, ignoreMultiWindows)

@Composable
fun Number.spaceHSpiPx(): Float =
    rememberLiteralSpaceScreenPx(toFloat(), DpQualifier.HEIGHT, ignoreMultiWindows = true)

@Composable
fun Number.spaceSp(reference: Dp, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): TextUnit =
    rememberLiteralSpaceReferenceSp(toFloat(), reference.value, fontScale, ignoreMultiWindows)

@Composable
fun Number.spaceSpi(reference: Dp, fontScale: Boolean = true): TextUnit =
    rememberLiteralSpaceReferenceSp(toFloat(), reference.value, fontScale, ignoreMultiWindows = true)

@Composable
fun Number.spaceSpPx(reference: Dp, ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.value, ignoreMultiWindows)

@Composable
fun Number.spaceSpiPx(reference: Dp): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.value, ignoreMultiWindows = true)

@Composable
fun Number.spaceSp(reference: Number, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): TextUnit =
    rememberLiteralSpaceReferenceSp(toFloat(), reference.toFloat(), fontScale, ignoreMultiWindows)

@Composable
fun Number.spaceSpi(reference: Number, fontScale: Boolean = true): TextUnit =
    rememberLiteralSpaceReferenceSp(toFloat(), reference.toFloat(), fontScale, ignoreMultiWindows = true)

@Composable
fun Number.spaceSpPx(reference: Number, ignoreMultiWindows: Boolean = false): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.toFloat(), ignoreMultiWindows)

@Composable
fun Number.spaceSpiPx(reference: Number): Float =
    rememberLiteralSpaceReferencePx(toFloat(), reference.toFloat(), ignoreMultiWindows = true)

@Composable
private fun rememberLiteralSpaceScreenSp(
    percent: Float,
    qualifier: DpQualifier,
    fontScale: Boolean,
    ignoreMultiWindows: Boolean,
): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val resultDp = literalPercentOfScreenDp(percent, qualifier, configuration, ignoreMultiWindows)
    val stamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return remember(stamp, fontScale, ignoreMultiWindows, resultDp) {
        val fs = configuration.fontScale.coerceAtLeast(1e-6f)
        if (fontScale) {
            (resultDp / fs).sp
        } else {
            (resultDp / density.fontScale.coerceAtLeast(1e-6f)).sp
        }
    }
}

@Composable
private fun rememberLiteralSpaceReferenceSp(
    percent: Float,
    referenceDp: Float,
    fontScale: Boolean,
    ignoreMultiWindows: Boolean,
): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val resultDp = literalPercentOfReferenceDp(percent, referenceDp, configuration, ignoreMultiWindows)
    val stamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return remember(stamp, fontScale, ignoreMultiWindows, resultDp, referenceDp) {
        val fs = configuration.fontScale.coerceAtLeast(1e-6f)
        if (fontScale) {
            (resultDp / fs).sp
        } else {
            (resultDp / density.fontScale.coerceAtLeast(1e-6f)).sp
        }
    }
}
