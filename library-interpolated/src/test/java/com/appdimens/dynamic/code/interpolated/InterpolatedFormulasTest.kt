package com.appdimens.dynamic.code.interpolated

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Formula checks for the interpolated satellite. The default-SW path must read the
 * memoized [DimenMetrics.interpolatedScale] snapshot factor.
 */
class InterpolatedFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
        }

    @Test
    fun interpolatedFactor_matchesMemoizedSnapshotFactor() {
        val cfg = config(420, 800, 1200)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val expectedScale = 1f + (420f * DimenCache.INV_BASE_RATIO - 1f) * 0.5f
        val out = calculateInterpolatedDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(50f * expectedScale, out, 0.001f)
        assertEquals(metrics.interpolatedScale, expectedScale, 0f)
    }
}