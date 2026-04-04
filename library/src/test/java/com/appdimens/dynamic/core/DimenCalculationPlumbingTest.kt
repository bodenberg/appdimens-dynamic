package com.appdimens.dynamic.core

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DimenCalculationPlumbingTest {

    private fun config(sw: Int = 400, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
        }

    @Test
    fun multiWindow_ignoreDisabled_alwaysFalse() {
        val cfg = config(sw = 400, w = 200)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = false))
    }

    @Test
    fun multiWindow_fullscreen_noSplit_returnsFalse() {
        val cfg = config(sw = 400, w = 400)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_dpFallback_largeDiff_returnsTrue() {
        val cfg = config(sw = 400, w = 200)
        assertTrue(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_dpFallback_smallDiff_returnsFalse() {
        val cfg = config(sw = 400, w = 380)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_swZero_returnsFalse() {
        val cfg = config(sw = 0, w = 0)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_swNegative_returnsFalse() {
        val cfg = config(sw = -1, w = 100)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_exactThreshold_returnsFalse() {
        val cfg = config(sw = 400, w = 361)
        assertFalse(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun multiWindow_justOverThreshold_returnsTrue() {
        val cfg = config(sw = 400, w = 359)
        assertTrue(DimenCalculationPlumbing.isMultiWindowConstrained(cfg, ignoreMultiWindows = true))
    }

    @Test
    fun effectiveQualifier_default_unchanged() {
        assertEquals(
            DpQualifier.WIDTH,
            DimenCalculationPlumbing.effectiveQualifier(DpQualifier.WIDTH, Inverter.DEFAULT, true, false)
        )
    }

    @Test
    fun effectiveQualifier_phToLw_landscapeSwaps() {
        assertEquals(
            DpQualifier.WIDTH,
            DimenCalculationPlumbing.effectiveQualifier(DpQualifier.HEIGHT, Inverter.PH_TO_LW, true, false)
        )
    }

    @Test
    fun effectiveQualifier_phToLw_portraitNoSwap() {
        assertEquals(
            DpQualifier.HEIGHT,
            DimenCalculationPlumbing.effectiveQualifier(DpQualifier.HEIGHT, Inverter.PH_TO_LW, false, true)
        )
    }

    @Test
    fun readScreenDp_returnsCorrectValues() {
        val cfg = config(sw = 300, w = 400, h = 800)
        assertEquals(400f, DimenCalculationPlumbing.readScreenDp(cfg, DpQualifier.WIDTH), 0f)
        assertEquals(800f, DimenCalculationPlumbing.readScreenDp(cfg, DpQualifier.HEIGHT), 0f)
        assertEquals(300f, DimenCalculationPlumbing.readScreenDp(cfg, DpQualifier.SMALL_WIDTH), 0f)
    }

    @Test
    fun smallestSideDp_returnsMin() {
        val cfg = config(sw = 300, w = 400, h = 800)
        assertEquals(400f, DimenCalculationPlumbing.smallestSideDp(cfg), 0f)
    }

    @Test
    fun largestSideDp_returnsMax() {
        val cfg = config(sw = 300, w = 400, h = 800)
        assertEquals(800f, DimenCalculationPlumbing.largestSideDp(cfg), 0f)
    }

    @Test
    fun aspectRatioMultiplier_zeroSmallSide_returnsOne() {
        val cfg = config(sw = 0, w = 0, h = 0)
        assertEquals(1f, DimenCalculationPlumbing.aspectRatioMultiplier(cfg, 0.5f), 0f)
    }
}
