package com.appdimens.dynamic.code.resize

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.LayoutDirection
import com.appdimens.dynamic.core.AutoResizePercentBasis
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DimenResizeCodeUnitTest {

    @Test
    fun innerMaxDimensions_subtractsPadding() {
        val (w, h) = DimenResize.innerMaxDimensionsPx(200f, 100f, 10f, 10f, 20f, 20f)
        assertEquals(180f, w, 0.01f)
        assertEquals(60f, h, 0.01f)
    }

    @Test
    fun innerMaxDimensionsRelative_rtlSwapsStartEnd() {
        val (wLtr, _) = DimenResize.innerMaxDimensionsPxRelative(
            100f, 50f,
            paddingStartPx = 30f,
            paddingEndPx = 10f,
            layoutDirection = LayoutDirection.LTR,
        )
        val (wLtrDirect, _) = DimenResize.innerMaxDimensionsPx(100f, 50f, 30f, 10f, 0f, 0f)
        assertEquals(wLtrDirect, wLtr, 0.01f)

        val (wRtl, _) = DimenResize.innerMaxDimensionsPxRelative(
            100f, 50f,
            paddingStartPx = 30f,
            paddingEndPx = 10f,
            layoutDirection = LayoutDirection.RTL,
        )
        val (wRtlExpected, _) = DimenResize.innerMaxDimensionsPx(100f, 50f, 10f, 30f, 0f, 0f)
        assertEquals(wRtlExpected, wRtl, 0.01f)
    }

    @Test
    fun innerMaxDimensions_coercesAtLeastOnePx() {
        val (w, h) = DimenResize.innerMaxDimensionsPx(10f, 8f, 20f, 20f, 0f, 0f)
        assertEquals(1f, w, 0.01f)
        assertEquals(8f, h, 0.01f)
    }

    @Test
    fun percentOfBoxToFactor_coercesToZeroOne() {
        assertEquals(0f, percentOfBoxToFactor(-10), 0f)
        assertEquals(1f, percentOfBoxToFactor(200), 0f)
        assertEquals(0.25f, percentOfBoxToFactor(25), 0f)
    }

    @Test
    fun rangePxPercentOfInnerBox_matchesWidthBasis() {
        val ctx = mockContext(density = 2f)
        val innerW = 400f
        val innerH = 200f
        val r = DimenResize.rangePxPercentOfInnerBox(
            context = ctx,
            basis = AutoResizePercentBasis.WIDTH,
            minPercent = 10,
            maxPercent = 50,
            stepDp = 2f,
            innerWidthPx = innerW,
            innerHeightPx = innerH,
        )
        assertEquals(40f, r.minPx, 0.01f)
        assertEquals(200f, r.maxPx, 0.01f)
        assertEquals(4f, r.stepPx, 0.01f)
    }

    @Test
    fun rangePxTextSizePercent_usesFontScale() {
        val ctx = mockContext(density = 2f, fontScale = 1.25f)
        val r = DimenResize.rangePxTextSizePercentOfInnerBox(
            context = ctx,
            basis = AutoResizePercentBasis.HEIGHT,
            minPercent = 100,
            maxPercent = 100,
            stepSp = 2f,
            innerWidthPx = 100f,
            innerHeightPx = 300f,
        )
        assertEquals(300f, r.minPx, 0.01f)
        assertEquals(300f, r.maxPx, 0.01f)
        assertEquals(5f, r.stepPx, 0.01f)
    }

    @Test
    fun fittingTextSpPx_singleLineShortText_fitsLargeSize() {
        val paint = android.text.TextPaint().apply { isAntiAlias = true }
        val range = com.appdimens.dynamic.core.ResizeRangePx(8f, 200f, 4f)
        val px = DimenResize.fittingTextSpPx(
            text = "Hi",
            range = range,
            innerWidthPx = 500f,
            innerHeightPx = 200f,
            textPaint = paint,
            maxLines = 1,
            softWrap = false,
        )
        assertEquals(200f, px, 1f)
    }
}

private fun mockContext(density: Float, fontScale: Float = 1f): Context {
    val metrics = DisplayMetrics().apply { this.density = density }
    val configuration = Configuration().apply { this.fontScale = fontScale }
    val resources = mock<Resources>()
    whenever(resources.displayMetrics).thenReturn(metrics)
    whenever(resources.configuration).thenReturn(configuration)
    val ctx = mock<Context>()
    whenever(ctx.resources).thenReturn(resources)
    return ctx
}
