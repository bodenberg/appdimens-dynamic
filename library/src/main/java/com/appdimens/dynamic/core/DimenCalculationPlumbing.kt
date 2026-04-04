/**
 * Strategy-agnostic screen plumbing: inverter resolution, multi-window detection, dp reads.
 * Each strategy module applies its own formula on top of this.
 */
package com.appdimens.dynamic.core

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import java.lang.reflect.Method
import kotlin.math.ln

object DimenCalculationPlumbing {

    /**
     * `Configuration.windowConfiguration` and `WindowConfiguration` are @hide in the public SDK stub,
     * so windowing mode is read reflectively (minSdk 24 guarantees the field on devices).
     */
    private object WindowingModeAccess {
        private val readMode: (Configuration) -> Int? = run {
            try {
                val wcField = Configuration::class.java.getDeclaredField("windowConfiguration").apply {
                    isAccessible = true
                }
                val getWindowingMode: Method =
                    Class.forName("android.app.WindowConfiguration").getMethod("getWindowingMode")
                ({ cfg: Configuration ->
                    runCatching {
                        val wc = wcField.get(cfg) ?: return@runCatching null
                        getWindowingMode.invoke(wc) as Int
                    }.getOrNull()
                })
            } catch (_: ReflectiveOperationException) {
                { _ -> null }
            }
        }

        fun read(configuration: Configuration): Int? = readMode(configuration)
    }

    private const val WINDOWING_MODE_UNDEFINED = 0
    private const val WINDOWING_MODE_FULLSCREEN = 1

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
        val mode = WindowingModeAccess.read(configuration)
        val nonFullscreen = mode != null &&
            mode != WINDOWING_MODE_UNDEFINED &&
            mode != WINDOWING_MODE_FULLSCREEN
        if (nonFullscreen) return true
        val swDp = configuration.smallestScreenWidthDp.toFloat()
        if (swDp <= 0f) return false
        val cwDp = configuration.screenWidthDp.toFloat()
        return (swDp - cwDp) >= (swDp * 0.1f)
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
