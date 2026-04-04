package com.appdimens.dynamic.core

import android.content.res.Configuration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class DimenCacheInvalidationTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    private fun config(sw: Int = 400, w: Int = 400, h: Int = 800, dpi: Int = 420): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
            densityDpi = dpi
        }

    @Test
    fun invalidate_screenWidthChange_clearsCache() {
        val old = config(sw = 400, w = 400, h = 800)
        DimenCache.invalidateOnConfigChange(null, old)

        val key = DimenCache.buildKey(
            10f, false, false, DimenCache.CalcType.AUTO,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 42f }
        assertEquals(42f, DimenCache.peek(key) ?: -1f, 0f)

        val new = config(sw = 400, w = 300, h = 800)
        DimenCache.invalidateOnConfigChange(old, new)

        assertEquals(null, DimenCache.peek(key))
    }

    @Test
    fun invalidate_screenHeightChange_clearsCache() {
        val old = config(sw = 400, w = 400, h = 800)
        DimenCache.invalidateOnConfigChange(null, old)

        val key = DimenCache.buildKey(
            20f, false, false, DimenCache.CalcType.AUTO,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 55f }
        assertEquals(55f, DimenCache.peek(key) ?: -1f, 0f)

        val new = config(sw = 400, w = 400, h = 600)
        DimenCache.invalidateOnConfigChange(old, new)

        assertEquals(null, DimenCache.peek(key))
    }

    @Test
    fun invalidate_nothingChanged_doesNotClear() {
        val old = config(sw = 400, w = 400, h = 800)
        DimenCache.invalidateOnConfigChange(null, old)

        val key = DimenCache.buildKey(
            30f, false, false, DimenCache.CalcType.DIAGONAL,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 77f }
        assertEquals(77f, DimenCache.peek(key) ?: -1f, 0f)

        val new = config(sw = 400, w = 400, h = 800)
        DimenCache.invalidateOnConfigChange(old, new)

        assertEquals(77f, DimenCache.peek(key) ?: -1f, 0f)
    }

    @Test
    fun invalidate_orientationSwap_clearsCache() {
        val old = config(sw = 400, w = 400, h = 800)
        DimenCache.invalidateOnConfigChange(null, old)

        val key = DimenCache.buildKey(
            31f, false, false, DimenCache.CalcType.DIAGONAL,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 88f }

        val new = config(sw = 400, w = 800, h = 400)
        DimenCache.invalidateOnConfigChange(old, new)

        assertEquals(null, DimenCache.peek(key))
    }

    @Test
    fun invalidate_dpiChange_clearsCache() {
        val old = config(dpi = 420)
        DimenCache.invalidateOnConfigChange(null, old)

        val key = DimenCache.buildKey(
            40f, false, false, DimenCache.CalcType.AUTO,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 99f }

        val new = config(dpi = 320)
        DimenCache.invalidateOnConfigChange(old, new)

        assertEquals(null, DimenCache.peek(key))
    }
}
