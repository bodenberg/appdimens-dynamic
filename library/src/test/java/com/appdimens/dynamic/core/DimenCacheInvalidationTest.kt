package com.appdimens.dynamic.core

import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Correctness no longer depends on [DimenCache.invalidateOnConfigChange] wiping RAM.
 * Each [DimenMetrics] snapshot owns an isolated cache partition; callers that pass the
 * current window snapshot never observe values from another configuration.
 */
class DimenCacheInvalidationTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    private fun config(
        sw: Int = 400,
        w: Int = 400,
        h: Int = 800,
        dpi: Int = 420,
        fontScale: Float = 1f,
        orientation: Int = Configuration.ORIENTATION_PORTRAIT,
    ): Configuration = Configuration().apply {
        smallestScreenWidthDp = sw
        screenWidthDp = w
        screenHeightDp = h
        densityDpi = dpi
        this.fontScale = fontScale
        this.orientation = orientation
    }

    private fun autoKey(base: Float) = DimenCache.buildKey(
        base, false, false, DimenCache.CalcType.AUTO,
        DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
    )

    @Test
    fun differentMetrics_useDifferentPartitions() {
        val metrics400 = DimenMetrics.from(config(sw = 400, w = 400, h = 800))
        val metrics300 = DimenMetrics.from(config(sw = 300, w = 300, h = 800))
        val key = autoKey(10f)

        assertEquals(42f, DimenCache.getOrPut(key, metrics400) { 42f }, 0f)
        assertEquals(42f, DimenCache.peek(key, metrics400) ?: -1f, 0f)
        assertNull(DimenCache.peek(key, metrics300))
    }

    @Test
    fun orientationSwap_keepsBothPartitions() {
        val portrait = DimenMetrics.from(config(w = 400, h = 800))
        val landscape = DimenMetrics.from(
            config(w = 800, h = 400, orientation = Configuration.ORIENTATION_LANDSCAPE)
        )
        val key = autoKey(31f)

        assertEquals(88f, DimenCache.getOrPut(key, portrait) { 88f }, 0f)
        assertEquals(99f, DimenCache.getOrPut(key, landscape) { 99f }, 0f)
        assertEquals(88f, DimenCache.peek(key, portrait) ?: -1f, 0f)
        assertEquals(99f, DimenCache.peek(key, landscape) ?: -1f, 0f)
    }

    @Test
    fun fontScaleChange_usesSeparatePartition() {
        val normal = DimenMetrics.from(config())
        val largeText = DimenMetrics.from(config().apply { fontScale = 1.5f })
        val key = autoKey(12f)

        assertEquals(3f, DimenCache.getOrPut(key, normal) { 3f }, 0f)
        assertNull(DimenCache.peek(key, largeText))
        assertEquals(4f, DimenCache.getOrPut(key, largeText) { 4f }, 0f)
        assertEquals(3f, DimenCache.peek(key, normal) ?: -1f, 0f)
    }

    @Test
    fun invalidate_updatesFallbackMetrics_withoutErasingOtherPartitions() {
        val oldMetrics = DimenMetrics.from(config(sw = 400, w = 400, h = 800))
        val key = autoKey(32f)
        assertEquals(66f, DimenCache.getOrPut(key, oldMetrics) { 66f }, 0f)

        DimenCache.invalidateOnConfigChange(config(sw = 300, w = 300, h = 800))

        assertNull(DimenCache.peek(key))
        assertEquals(66f, DimenCache.peek(key, oldMetrics) ?: -1f, 0f)
    }
}
