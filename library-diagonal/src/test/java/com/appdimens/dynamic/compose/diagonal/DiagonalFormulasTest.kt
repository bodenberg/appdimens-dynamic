package com.appdimens.dynamic.compose.diagonal

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

/** Isolated formula checks for the diagonal satellite. */
class DiagonalFormulasTest {

    private fun config(sw: Int, w: Int = sw, h: Int = 800): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
        }

    @Test
    fun diagonal_usesDesignDiagonalConstant() {
        val cfg = config(300, w = 400, h = 300)
        DimenCache.invalidateOnConfigChange(cfg)
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
}
