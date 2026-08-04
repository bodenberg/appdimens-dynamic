package com.appdimens.dynamic.compose.percent

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import org.junit.Assert.assertEquals
import org.junit.Test

/** Isolated formula checks for the percent satellite. */
class PercentFormulasTest {

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
    fun percent_fractionalBaseValue_preserved() {
        val cfg = config(400)
        val out = calculatePercentDpCompose(
            15.5f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(15.5f * 400f * DimenCache.INV_BASE_RATIO, out, 0.001f)
    }
}
