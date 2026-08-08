package com.appdimens.dynamic.code.logarithmic

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.ln

/**
 * Formula checks for the logarithmic satellite. The default-SW path must read the
 * memoized [DimenMetrics.logarithmicScale] — not recompute `ln` per call.
 */
class LogarithmicFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
        }

    @Test
    fun logarithmicFactor_matchesMemoizedSnapshotFactor() {
        val cfg = config(360, 800, 1200)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val expectedScale = 1f + 0.4f * ln(360f / 300f)
        val out = calculateLogarithmicDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(50f * expectedScale, out, 0.001f)
        assertEquals("snapshot memo must match the when-chain", metrics.logarithmicScale, expectedScale, 0f)
    }

    @Test
    fun logarithmic_belowBaseWidth_diminishes() {
        val cfg = config(240, 240, 800)
        val metrics = DimenMetrics.from(cfg)
        DimenCache.invalidateOnConfigChange(cfg)

        val expectedScale = 1f - 0.4f * ln(300f / 240f)
        val out = calculateLogarithmicDp(
            50f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT,
            ignoreMultiWindows = false, applyAspectRatio = false, customSensitivityK = null
        )
        assertEquals(50f * expectedScale, out, 0.001f)
        assertEquals(metrics.logarithmicScale, expectedScale, 0f)
    }
}