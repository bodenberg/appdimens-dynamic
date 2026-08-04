/**
 * Author & Developer: Jean Bodenberg
 *
 * EN Packed [Long] stamps for [androidx.compose.runtime.remember] keys — zero allocation.
 * PT Carimbos [Long] empacotados para chaves de [remember] — zero alocação.
 */
package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.unit.Density

/**
 * EN Mix [densityDpi] into a layout-only packed long without overlapping SW/W/H bit fields.
 * A plain `or (dpi shl 4)` collided with the low bits of height and could produce false hits.
 *
 * PT Mistura densityDpi sem sobrepor os campos de SW/W/H.
 */
private fun mixDpi(packedLayout: Long, densityDpi: Int): Long {
    // Spread 16-bit dpi across the 64-bit word (Knuth multiplicative hash fragment).
    val dpi = densityDpi.toLong() and 0xFFFFL
    return packedLayout xor (dpi * 0x0001000100010001L)
}

/**
 * EN Packs orientation + SW + W + H into non-overlapping bit fields (4+20+20+20 = 64).
 * PT Empacota orientação + SW + W + H em campos sem sobreposição.
 */
private fun packLayoutFields(configuration: Configuration): Long {
    val sw = configuration.smallestScreenWidthDp.toLong() and 0xFFFFFL
    val w = configuration.screenWidthDp.toLong() and 0xFFFFFL
    val h = configuration.screenHeightDp.toLong() and 0xFFFFFL
    val o = configuration.orientation.toLong() and 0xFL
    return (o shl 60) or (sw shl 40) or (w shl 20) or h
}

/**
 * EN Layout stamp for Dp [remember] keys.
 * Packs only fields that affect layout scaling (orientation, SW, W, H, densityDpi).
 * Deliberately **excludes** [Configuration.hashCode] so locale / fontScale / keyboard
 * changes do not force every `.sdp` to recompute.
 *
 * PT Carimbo de layout para chaves de [remember] de Dp — sem hashCode completo.
 */
@Suppress("UNUSED_PARAMETER")
fun layoutRememberStamp(configuration: Configuration, context: Context): Long {
    return mixDpi(packLayoutFields(configuration), configuration.densityDpi)
}

/**
 * EN Stamp for Dp→Px paths: layout fields xor [Density.density] only.
 * Font scale does **not** affect Dp→Px conversion.
 *
 * PT Carimbo Dp→Px: só densidade física (sem fontScale).
 */
fun pxRememberStamp(layoutStamp: Long, density: Density): Long {
    val d = density.density.toRawBits().toLong() and 0xFFFFFFFFL
    return layoutStamp xor (d shl 32)
}

/**
 * EN Stamp for Sp [remember] paths: layout xor density xor fontScale
 * (Sp values may embed or be divided by fontScale depending on the flag).
 *
 * PT Carimbo Sp: inclui fontScale.
 */
fun spRememberStamp(layoutStamp: Long, density: Density): Long {
    val d = density.density.toRawBits().toLong() and 0xFFFFFFFFL
    val f = density.fontScale.toRawBits().toLong() and 0xFFFFFFFFL
    return layoutStamp xor (d shl 32) xor f
}

/**
 * EN Stamp for custom scaled-entry resolution ([com.appdimens.dynamic.compose.DimenScaled] / ScaledSp).
 * Keys only matcher inputs: SW/W/H/orientation + uiMode + ignoreMultiWindows.
 * Omits densityDpi and aspectRatio (AR is derived from W/H) to avoid extra invalidation.
 *
 * PT Carimbo para resolução de entradas customizadas — só inputs do matcher.
 */
@Suppress("UNUSED_PARAMETER")
fun scaledEntryRememberStamp(
    uiModeOrdinal: Int,
    configuration: Configuration,
    aspectRatio: Float,
    ignoreMultiWindows: Boolean
): Long {
    val packed = packLayoutFields(configuration)
    val imw = if (ignoreMultiWindows) 0x13579BDFL else 0L
    return packed xor uiModeOrdinal.toLong() xor imw
}
