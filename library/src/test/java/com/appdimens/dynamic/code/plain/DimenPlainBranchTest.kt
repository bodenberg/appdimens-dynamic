package com.appdimens.dynamic.code.plain

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DimenPlainBranchTest {

    @Test
    fun plainRotatePx_landscapePicksBranch() {
        val ctx = contextWithOrientation(Configuration.ORIENTATION_LANDSCAPE)
        assertEquals(99f, plainRotatePx(ctx, 10f, 99f, Orientation.LANDSCAPE), 0f)
    }

    @Test
    fun plainRotatePx_landscapeKeepsReceiverInPortrait() {
        val ctx = contextWithOrientation(Configuration.ORIENTATION_PORTRAIT)
        assertEquals(10f, plainRotatePx(ctx, 10f, 99f, Orientation.LANDSCAPE), 0f)
    }

    @Test
    fun plainQualifierPx_matchesWhenThresholdMet() {
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 600
            screenWidthDp = 600
            screenHeightDp = 800
        }
        val ctx = mockContext(cfg)
        assertEquals(7f, plainQualifierPx(ctx, 3f, 7f, DpQualifier.SMALL_WIDTH, 400), 0f)
    }

    @Test
    fun plainQualifierPx_keepsReceiverWhenBelowThreshold() {
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 320
            screenWidthDp = 320
            screenHeightDp = 480
        }
        val ctx = mockContext(cfg)
        assertEquals(3f, plainQualifierPx(ctx, 3f, 7f, DpQualifier.SMALL_WIDTH, 400), 0f)
    }

    private fun contextWithOrientation(orientation: Int): Context {
        val cfg = Configuration().apply { this.orientation = orientation }
        return mockContext(cfg)
    }

    private fun mockContext(configuration: Configuration): Context {
        val resources = mock<Resources>()
        whenever(resources.configuration).thenReturn(configuration)
        val ctx = mock<Context>()
        whenever(ctx.resources).thenReturn(resources)
        return ctx
    }
}
