/**
 * EN Literal percent of screen width / smallest width / height, or of a reference length (dp).
 * PT Percentual literal da largura da tela, smallest width, altura ou de um comprimento de referência (dp).
 */
package com.appdimens.dynamic.code.percent

import android.content.Context
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.core.literalPercentOfReferenceDp
import com.appdimens.dynamic.core.literalPercentOfScreenDp

// ─── Screen fraction → px (same idea as psdp / pwdp returning px) ─────────────

@JvmOverloads
fun Number.spaceW(context: Context, ignoreMultiWindows: Boolean = false): Float {
    val c = context.resources.configuration
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.WIDTH, c, ignoreMultiWindows)
    return dp * context.resources.displayMetrics.density
}

@JvmOverloads
fun Number.spaceSw(context: Context, ignoreMultiWindows: Boolean = false): Float {
    val c = context.resources.configuration
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, c, ignoreMultiWindows)
    return dp * context.resources.displayMetrics.density
}

@JvmOverloads
fun Number.spaceH(context: Context, ignoreMultiWindows: Boolean = false): Float {
    val c = context.resources.configuration
    val dp = literalPercentOfScreenDp(toFloat(), DpQualifier.HEIGHT, c, ignoreMultiWindows)
    return dp * context.resources.displayMetrics.density
}

/** PT Igual a [spaceW] com `ignoreMultiWindows = true` (split-screen / multi-janela). */
fun Number.spaceWi(context: Context): Float = spaceW(context, ignoreMultiWindows = true)

/** PT Igual a [spaceSw] com `ignoreMultiWindows = true`. */
fun Number.spaceSwi(context: Context): Float = spaceSw(context, ignoreMultiWindows = true)

/** PT Igual a [spaceH] com `ignoreMultiWindows = true`. */
fun Number.spaceHi(context: Context): Float = spaceH(context, ignoreMultiWindows = true)

// ─── Mesmo valor em px com sufixo explícito (paridade com Compose: spaceWPx, psdpPx, etc.) ─

@JvmOverloads
fun Number.spaceWPx(context: Context, ignoreMultiWindows: Boolean = false): Float =
    spaceW(context, ignoreMultiWindows)

/** PT Igual a [spaceWPx] com `ignoreMultiWindows = true`. */
fun Number.spaceWPxi(context: Context): Float = spaceWPx(context, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwPx(context: Context, ignoreMultiWindows: Boolean = false): Float =
    spaceSw(context, ignoreMultiWindows)

/** PT Igual a [spaceSwPx] com `ignoreMultiWindows = true`. */
fun Number.spaceSwPxi(context: Context): Float = spaceSwPx(context, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHPx(context: Context, ignoreMultiWindows: Boolean = false): Float =
    spaceH(context, ignoreMultiWindows)

/** PT Igual a [spaceHPx] com `ignoreMultiWindows = true`. */
fun Number.spaceHPxi(context: Context): Float = spaceHPx(context, ignoreMultiWindows = true)

// ─── Screen fraction → dp (raw) ─────────────────────────────────────────────

@JvmOverloads
fun Number.spaceWDp(context: Context, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.WIDTH, context.resources.configuration, ignoreMultiWindows)

@JvmOverloads
fun Number.spaceSwDp(context: Context, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.SMALL_WIDTH, context.resources.configuration, ignoreMultiWindows)

@JvmOverloads
fun Number.spaceHDp(context: Context, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfScreenDp(toFloat(), DpQualifier.HEIGHT, context.resources.configuration, ignoreMultiWindows)

/** PT Igual a [spaceWDp] com `ignoreMultiWindows = true`. */
fun Number.spaceWDpi(context: Context): Float = spaceWDp(context, ignoreMultiWindows = true)

/** PT Igual a [spaceSwDp] com `ignoreMultiWindows = true`. */
fun Number.spaceSwDpi(context: Context): Float = spaceSwDp(context, ignoreMultiWindows = true)

/** PT Igual a [spaceHDp] com `ignoreMultiWindows = true`. */
fun Number.spaceHDpi(context: Context): Float = spaceHDp(context, ignoreMultiWindows = true)

// ─── Reference length (dp) ──────────────────────────────────────────────────

@JvmOverloads
fun Number.space(referenceDp: Number, context: Context, ignoreMultiWindows: Boolean = false): Float {
    val c = context.resources.configuration
    val dp = literalPercentOfReferenceDp(toFloat(), referenceDp.toFloat(), c, ignoreMultiWindows)
    return dp * context.resources.displayMetrics.density
}

@JvmOverloads
fun Number.spaceDp(referenceDp: Number, context: Context, ignoreMultiWindows: Boolean = false): Float =
    literalPercentOfReferenceDp(toFloat(), referenceDp.toFloat(), context.resources.configuration, ignoreMultiWindows)

/** PT Igual a [space] com `ignoreMultiWindows = true`. */
fun Number.spaceI(referenceDp: Number, context: Context): Float =
    space(referenceDp, context, ignoreMultiWindows = true)

/** PT Igual a [spaceDp] com `ignoreMultiWindows = true`. */
fun Number.spaceDpi(referenceDp: Number, context: Context): Float =
    spaceDp(referenceDp, context, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spacePx(referenceDp: Number, context: Context, ignoreMultiWindows: Boolean = false): Float =
    space(referenceDp, context, ignoreMultiWindows)

/** PT Igual a [spacePx] com `ignoreMultiWindows = true`. */
fun Number.spacePxi(referenceDp: Number, context: Context): Float =
    spacePx(referenceDp, context, ignoreMultiWindows = true)

// ─── Sp: numeric sp for View APIs; SpPx matches dp pixel size when fontScale respected ─

@JvmOverloads
fun Number.spaceWSp(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceWDp(context, ignoreMultiWindows)
    return literalPercentSpValue(context, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceSwSp(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceSwDp(context, ignoreMultiWindows)
    return literalPercentSpValue(context, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceHSp(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceHDp(context, ignoreMultiWindows)
    return literalPercentSpValue(context, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceWSpPx(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val res = context.resources
    val sp = spaceWSp(context, fontScale, ignoreMultiWindows)
    val density = res.displayMetrics.density
    val fs = res.configuration.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceSwSpPx(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val res = context.resources
    val sp = spaceSwSp(context, fontScale, ignoreMultiWindows)
    val density = res.displayMetrics.density
    val fs = res.configuration.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceHSpPx(context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val res = context.resources
    val sp = spaceHSp(context, fontScale, ignoreMultiWindows)
    val density = res.displayMetrics.density
    val fs = res.configuration.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceSp(referenceDp: Number, context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val resultDp = spaceDp(referenceDp, context, ignoreMultiWindows)
    return literalPercentSpValue(context, resultDp, fontScale)
}

@JvmOverloads
fun Number.spaceSpPx(referenceDp: Number, context: Context, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false): Float {
    val res = context.resources
    val sp = spaceSp(referenceDp, context, fontScale, ignoreMultiWindows)
    val density = res.displayMetrics.density
    val fs = res.configuration.fontScale.coerceAtLeast(1e-6f)
    return if (fontScale) sp * density * fs else sp * density
}

@JvmOverloads
fun Number.spaceWSpi(context: Context, fontScale: Boolean = true): Float =
    spaceWSp(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwSpi(context: Context, fontScale: Boolean = true): Float =
    spaceSwSp(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHSpi(context: Context, fontScale: Boolean = true): Float =
    spaceHSp(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceWSpiPx(context: Context, fontScale: Boolean = true): Float =
    spaceWSpPx(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSwSpiPx(context: Context, fontScale: Boolean = true): Float =
    spaceSwSpPx(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceHSpiPx(context: Context, fontScale: Boolean = true): Float =
    spaceHSpPx(context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSpi(referenceDp: Number, context: Context, fontScale: Boolean = true): Float =
    spaceSp(referenceDp, context, fontScale, ignoreMultiWindows = true)

@JvmOverloads
fun Number.spaceSpiPx(referenceDp: Number, context: Context, fontScale: Boolean = true): Float =
    spaceSpPx(referenceDp, context, fontScale, ignoreMultiWindows = true)

/** Sp value for [android.util.TypedValue.COMPLEX_UNIT_SP] when [fontScale] is true; else dp-like value for COMPLEX_UNIT_DIP. */
private fun literalPercentSpValue(context: Context, resultDp: Float, fontScale: Boolean): Float {
    if (!fontScale) return resultDp
    val fs = context.resources.configuration.fontScale
    return if (fs > 0f) resultDp / fs else resultDp
}
