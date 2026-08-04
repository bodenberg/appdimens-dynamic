package com.appdimens.dynamic.compose.auto

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ln

/** Isolated formula checks for the auto satellite. */
class AutoFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
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
