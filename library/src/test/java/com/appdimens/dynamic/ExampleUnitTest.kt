package com.appdimens.dynamic

import com.appdimens.dynamic.core.AspectRatioLookup
import com.appdimens.dynamic.core.ResizeBound
import com.appdimens.dynamic.core.buildResizeStepsPx
import com.appdimens.dynamic.core.findLargestFittingResizePx
import com.appdimens.dynamic.core.resolveToPx
import org.junit.Assert.*
import org.junit.Test

class CoreMathTests {

    @Test
    fun findLargestFitting_noneFit_returnsZero() {
        val steps = buildResizeStepsPx(10f, 50f, 5f)
        val result = findLargestFittingResizePx(steps) { false }
        assertEquals(0f, result, 0f)
    }

    @Test
    fun findLargestFitting_allFit_returnsLargest() {
        val steps = buildResizeStepsPx(10f, 50f, 10f)
        val result = findLargestFittingResizePx(steps) { true }
        assertEquals(50f, result, 0f)
    }

    @Test
    fun findLargestFitting_partialFit_returnsCorrectBoundary() {
        val steps = buildResizeStepsPx(10f, 100f, 10f)
        val result = findLargestFittingResizePx(steps) { it <= 40f }
        assertEquals(40f, result, 0f)
    }

    @Test
    fun buildResizeSteps_negativeStep_returnsSingleElement() {
        val steps = buildResizeStepsPx(10f, 50f, -1f)
        assertEquals(1, steps.size)
        assertEquals(10f, steps[0], 0f)
    }

    @Test
    fun aspectRatioLookup_referenceRatio_returnsZero() {
        val result = AspectRatioLookup.lookup(1.0f)
        assertNotNull(result)
        assertEquals(0f, result!!, 0.01f)
    }

    @Test
    fun aspectRatioLookup_unknownValue_returnsNull() {
        val result = AspectRatioLookup.lookup(99.99f)
        assertNull(result)
    }

    @Test
    fun resizeBound_fixedDp_requiresPositiveDensity() {
        val bound = ResizeBound.FixedDp(16f)
        try {
            @Suppress("DEPRECATION")
            bound.resolveToPx(
                android.content.res.Configuration(),
                density = 0f,
                fontScale = 1f
            )
            fail("Expected IllegalArgumentException for density=0")
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("density"))
        }
    }

    @Test
    fun sensitivityK_closeValues_differentKeys() {
        val key15 = com.appdimens.dynamic.core.DimenCache.buildKey(
            10f, false, false, com.appdimens.dynamic.core.DimenCache.CalcType.SCALED,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false,
            com.appdimens.dynamic.core.DimenCache.ValueType.DP,
            customSensitivityK = 1.5f
        )
        val key17 = com.appdimens.dynamic.core.DimenCache.buildKey(
            10f, false, false, com.appdimens.dynamic.core.DimenCache.CalcType.SCALED,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false,
            com.appdimens.dynamic.core.DimenCache.ValueType.DP,
            customSensitivityK = 1.7f
        )
        assertNotEquals("K=1.5 and K=1.7 must produce different keys with 16-bit fingerprint", key15, key17)
    }
}
