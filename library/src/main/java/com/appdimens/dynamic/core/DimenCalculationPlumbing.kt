/**
 * Strategy-agnostic screen plumbing: inverter resolution, multi-window heuristic, dp reads.
 * Each strategy module applies its own formula on top of this.
 */
package com.appdimens.dynamic.core

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

    fun isMultiWindowConstrained(configuration: Configuration, ignoreMultiWindows: Boolean): Boolean {
        if (!ignoreMultiWindows) return false
        val swDp = configuration.smallestScreenWidthDp.toFloat()
        val cwDp = configuration.screenWidthDp.toFloat()
        val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
        val isSmallDiff = (swDp - cwDp) < (swDp * 0.1f)
        return isLayoutSplit && !isSmallDiff
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
        return 1f + sensitivity * ln(ar * DesignScaleConstants.INV_REFERENCE_ASPECT_RATIO)
    }
}
