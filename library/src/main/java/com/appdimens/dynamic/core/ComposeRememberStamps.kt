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
 * EN Layout stamp for Dp [remember] keys.
 * Packs only fields that affect layout scaling (orientation, SW, W, H, densityDpi).
 * Deliberately **excludes** [Configuration.hashCode] so locale / fontScale / keyboard
 * changes do not force every `.sdp` to recompute.
 *
 * PT Carimbo de layout para chaves de [remember] de Dp — sem hashCode completo.
 */
@Suppress("UNUSED_PARAMETER")
internal fun layoutRememberStamp(configuration: Configuration, context: Context): Long {
    val sw = configuration.smallestScreenWidthDp.toLong() and 0xFFFFFL
    val w = configuration.screenWidthDp.toLong() and 0xFFFFFL
    val h = configuration.screenHeightDp.toLong() and 0xFFFFFL
    val o = configuration.orientation.toLong() and 0xFL
    val dpi = configuration.densityDpi.toLong() and 0xFFFFL
    return (o shl 60) or (sw shl 40) or (w shl 20) or h or (dpi shl 4)
}

/**
 * EN Stamp for Dp→Px paths: layout fields xor [Density.density] only.
 * Font scale does **not** affect Dp→Px conversion.
 *
 * PT Carimbo Dp→Px: só densidade física (sem fontScale).
 */
internal fun pxRememberStamp(layoutStamp: Long, density: Density): Long {
    val d = density.density.toRawBits().toLong() and 0xFFFFFFFFL
    return layoutStamp xor (d shl 32)
}

/**
 * EN Stamp for Sp [remember] paths: layout xor density xor fontScale
 * (Sp values may embed or be divided by fontScale depending on the flag).
 *
 * PT Carimbo Sp: inclui fontScale.
 */
internal fun spRememberStamp(layoutStamp: Long, density: Density): Long {
    val d = density.density.toRawBits().toLong() and 0xFFFFFFFFL
    val f = density.fontScale.toRawBits().toLong() and 0xFFFFFFFFL
    return layoutStamp xor (d shl 32) xor f
}

/**
 * EN Stamp equivalent to the former multi-key [remember] for [com.appdimens.dynamic.compose.DimenScaled] / [com.appdimens.dynamic.compose.ScaledSp] custom entry resolution.
 * PT Carimbo equivalente ao antigo [remember] multi-chave para resolução de entradas customizadas.
 */
internal fun scaledEntryRememberStamp(
    uiModeOrdinal: Int,
    configuration: Configuration,
    aspectRatio: Float,
    ignoreMultiWindows: Boolean
): Long {
    val sw = configuration.smallestScreenWidthDp.toLong() and 0xFFFFFL
    val w = configuration.screenWidthDp.toLong() and 0xFFFFFL
    val h = configuration.screenHeightDp.toLong() and 0xFFFFFL
    val o = configuration.orientation.toLong() and 0xFL
    val dpi = configuration.densityDpi.toLong() and 0xFFFFL
    val packed = (o shl 60) or (sw shl 40) or (w shl 20) or h or (dpi shl 4)
    val ar = aspectRatio.toRawBits().toLong()
    val imw = if (ignoreMultiWindows) 0x13579BDFL else 0L
    return packed xor ar xor uiModeOrdinal.toLong() xor imw
}
