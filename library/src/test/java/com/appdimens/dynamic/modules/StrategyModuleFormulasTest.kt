package com.appdimens.dynamic.modules

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.compose.auto.calculateAutoDpCompose
import com.appdimens.dynamic.compose.diagonal.calculateDiagonalDpCompose
import com.appdimens.dynamic.compose.percent.calculatePercentDpCompose
import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ln
import kotlin.math.sqrt

/**
 * Isolated formula checks per strategy module (no cross-strategy imports in production code).
 */
class StrategyModuleFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
        }

    @Test
    fun percent_smallWidth_noAr_multipliesByScreenOverBase() {
        val cfg = config(400)
        val out = calculatePercentDpCompose(
            100f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(100f * 400f * DimenCache.INV_BASE_RATIO, out, 0.001f)
    }

    @Test
    fun diagonal_usesDesignDiagonalConstant() {
        val cfg = config(300, w = 400, h = 300)
        DimenCache.invalidateOnConfigChange(null, cfg)
        val sm = 300f
        val lg = 400f
        val diag = sqrt((sm * sm + lg * lg).toDouble()).toFloat()
        val expected = 50f * (diag / DesignScaleConstants.BASE_DIAGONAL_DP)
        val out = calculateDiagonalDpCompose(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(expected, out, 0.05f)
    }

    @Test
    fun auto_piecewiseAboveTransitionUsesLog() {
        val cfg = config(600)
        val dim = 600f
        val inv = DimenCache.INV_BASE_RATIO
        val transition = 480f
        val sensitivity = 0.4f
        val scale =
            (transition * inv) + sensitivity * ln(1.0 + (dim - transition) * inv).toFloat()
        val expected = 40f * scale
        val out = calculateAutoDpCompose(
            40f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(expected, out, 0.001f)
    }

    @Test
    fun percent_fractionalBaseValue_preserved() {
        val cfg = config(400)
        val out = calculatePercentDpCompose(
            15.5f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(15.5f * 400f * DimenCache.INV_BASE_RATIO, out, 0.001f)
    }

    @Test
    fun auto_fractionalBaseValue_preserved() {
        val cfg = config(300)
        val out = calculateAutoDpCompose(
            0.7f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        val scale = 300f * DimenCache.INV_BASE_RATIO
        assertEquals(0.7f * scale, out, 0.001f)
    }
}
