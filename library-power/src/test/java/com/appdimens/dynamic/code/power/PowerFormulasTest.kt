package com.appdimens.dynamic.code.power

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Formula checks for the power satellite (default-SW path reads the memoized
 * [DimenMetrics.powerScale] snapshot factor — never `Math.pow` per call).
 */
class PowerFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
        }

    @Test
    fun powerFactor_matchesMemoizedSnapshotFactor() {
        val cfg = config(360, 800, 1200)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val expected = 50f * Math.pow((360f / 300f).toDouble(), 0.75).toFloat()
        val out = calculatePowerDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(expected, out, 0.001f)
        assertEquals("snapshot memo must agree with direct formula", metrics.powerScale, expected / 50f, 0f)
    }

    @Test
    fun powerFactor_appliesMemoizedAspectRatioMultiplier() {
        val cfg = config(360, 800, 1200)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val out = calculatePowerDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = true, customSensitivityK = null
        )
        val expected = 50f * metrics.powerScale * metrics.defaultAspectRatioMultiplier
        assertEquals(expected, out, 0.001f)
    }
}