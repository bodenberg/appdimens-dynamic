package com.appdimens.dynamic.code.perimeter

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Formula checks for the perimeter satellite. The default-SW path must read the
 * memoized [DimenMetrics.perimeterScale] snapshot factor.
 */
class PerimeterFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
        }

    @Test
    fun perimeterFactor_matchesMemoizedSnapshotFactor() {
        val cfg = config(360, 800, 1200)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val expectedScale = (800f + 1200f) / 833f
        val out = calculatePerimeterDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(50f * expectedScale, out, 0.001f)
        assertEquals(metrics.perimeterScale, expectedScale, 0f)
    }
}