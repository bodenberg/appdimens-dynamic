/**
 * Literal percentage of screen or reference length (e.g. 10 → 10%).
 * Used by percent `space*` APIs; separate from [calculatePercentDp] sdp-style scaling.
 */
package com.appdimens.dynamic.core

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier

fun literalPercentOfScreenDp(
    percent: Float,
    qualifier: DpQualifier,
    configuration: Configuration,
    ignoreMultiWindows: Boolean,
): Float {
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, qualifier)
    return (percent / 100f) * dim
}

fun literalPercentOfReferenceDp(
    percent: Float,
    referenceDp: Float,
    configuration: Configuration,
    ignoreMultiWindows: Boolean,
): Float {
    return (percent / 100f) * referenceDp
}
