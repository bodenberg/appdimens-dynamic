/**
 * EN Internal branching helpers for View-side **Plain** APIs: [plainRotatePx], [plainModePx],
 * [plainQualifierPx], [plainScreenPx]. Receiver and alternate values are already in **layout/text px**;
 * [android.content.Context] is only used to read [android.content.res.Configuration] and UI mode cache.
 *
 * PT Funções internas de ramificação para APIs **Plain** no lado View: [plainRotatePx], [plainModePx],
 * [plainQualifierPx], [plainScreenPx]. O recetor e o valor alternativo já estão em **px** de layout/texto;
 * o [android.content.Context] serve só para ler [android.content.res.Configuration] e a cache de modo de UI.
 *
 * EN Strategy-facing entry points: `Dimen*PlainPx.kt` files under each `com.appdimens.dynamic.code.<strategy>` package
 * (for example `DimenPercentPlainPx.kt` in `com.appdimens.dynamic.code.percent`).
 * PT Pontos de entrada por estratégia: ficheiros `Dimen*PlainPx.kt` em `com.appdimens.dynamic.code.<estratégia>`
 * (por exemplo `DimenPercentPlainPx.kt` em `com.appdimens.dynamic.code.percent`).
 */
package com.appdimens.dynamic.code.plain

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache

internal fun isOrientationMatch(configuration: Configuration, orientation: Orientation): Boolean =
    when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }

@SuppressLint("ConfigurationScreenWidthHeight")
internal fun qualifierMeetsThreshold(
    configuration: Configuration,
    qualifierType: DpQualifier,
    qualifierValue: Number,
): Boolean {
    val v = when (qualifierType) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
    return v >= qualifierValue.toFloat()
}

internal fun plainRotatePx(
    context: Context,
    receiverPx: Float,
    branchPx: Float,
    orientation: Orientation,
): Float {
    val c = context.resources.configuration
    return if (isOrientationMatch(c, orientation)) branchPx else receiverPx
}

internal fun plainModePx(
    context: Context,
    receiverPx: Float,
    branchPx: Float,
    uiModeType: UiModeType,
): Float =
    if (DimenCache.getCachedUiModeType(context) == uiModeType) branchPx else receiverPx

@SuppressLint("ConfigurationScreenWidthHeight")
internal fun plainQualifierPx(
    context: Context,
    receiverPx: Float,
    qualifiedPx: Float,
    qualifierType: DpQualifier,
    qualifierValue: Number,
): Float {
    val c = context.resources.configuration
    return if (qualifierMeetsThreshold(c, qualifierType, qualifierValue)) qualifiedPx else receiverPx
}

@SuppressLint("ConfigurationScreenWidthHeight")
internal fun plainScreenPx(
    context: Context,
    receiverPx: Float,
    screenPx: Float,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
): Float {
    val c = context.resources.configuration
    val uiOk = DimenCache.getCachedUiModeType(context) == uiModeType
    val qOk = qualifierMeetsThreshold(c, qualifierType, qualifierValue)
    return if (uiOk && qOk) screenPx else receiverPx
}
