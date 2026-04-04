/**
 * Strategy-agnostic screen plumbing: inverter resolution, multi-window detection, dp reads.
 * Each strategy module applies its own formula on top of this.
 */
package com.appdimens.dynamic.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import kotlin.math.ln

object DimenCalculationPlumbing {

    fun effectiveQualifier(
        qualifier: DpQualifier,
        inverter: Inverter,
        isLandscape: Boolean,
        isPortrait: Boolean,
    ): DpQualifier {
        var actual = qualifier
        when (inverter) {
            Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT) actual = DpQualifier.WIDTH
            Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH) actual = DpQualifier.HEIGHT
            Inverter.LH_TO_PW -> if (isPortrait && qualifier == DpQualifier.HEIGHT) actual = DpQualifier.WIDTH
            Inverter.LW_TO_PH -> if (isPortrait && qualifier == DpQualifier.WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.WIDTH
            Inverter.SW_TO_PH -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.HEIGHT
            Inverter.SW_TO_PW -> if (isPortrait && qualifier == DpQualifier.SMALL_WIDTH) actual = DpQualifier.WIDTH
            Inverter.DEFAULT -> Unit
        }
        return actual
    }

    /**
     * Returns `true` when the app is in a multi-window mode (split-screen, freeform, PiP)
     * **and** the caller opted into suppressing scaling via [ignoreMultiWindows].
     *
     * Primary detection uses the public [Activity.isInMultiWindowMode] API (available since
     * API 24, which matches the library's minSdk). When no [Activity] can be resolved from
     * the supplied [context], a heuristic based on [Configuration] dimensions is used as
     * a best-effort fallback.
     *
     * @param context Optional [Context] used to resolve the hosting [Activity].
     *                Pass this whenever available for reliable multi-window detection.
     */
    fun isMultiWindowConstrained(
        configuration: Configuration,
        ignoreMultiWindows: Boolean,
        context: Context? = null,
    ): Boolean {
        if (!ignoreMultiWindows) return false
        val activity = context?.findActivityInternal()
        if (activity != null) return activity.isInMultiWindowMode
        val swDp = configuration.smallestScreenWidthDp.toFloat()
        if (swDp <= 0f) return false
        val cwDp = configuration.screenWidthDp.toFloat()
        return (swDp - cwDp) >= (swDp * 0.1f)
    }

    private fun Context.findActivityInternal(): Activity? {
        var ctx: Context = this
        while (ctx is ContextWrapper) {
            if (ctx is Activity) return ctx
            ctx = ctx.baseContext
        }
        return null
    }

    fun readScreenDp(configuration: Configuration, actualQualifier: DpQualifier): Float =
        when (actualQualifier) {
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
            DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        }

    fun smallestSideDp(configuration: Configuration): Float =
        minOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())

    fun largestSideDp(configuration: Configuration): Float =
        maxOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())

    /**
     * Multiplicative factor for optional aspect-ratio correction (perceptual / power-style paths).
     */
    fun aspectRatioMultiplier(configuration: Configuration, sensitivity: Float): Float {
        val sm = smallestSideDp(configuration)
        val lg = largestSideDp(configuration)
        if (sm <= 0f) return 1f
        val ar = lg / sm
        if (!ar.isFinite()) return 1f
        return 1f + sensitivity * ln(ar * DesignScaleConstants.INV_REFERENCE_ASPECT_RATIO)
    }
}
