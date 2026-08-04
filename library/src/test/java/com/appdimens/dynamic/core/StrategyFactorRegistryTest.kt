package com.appdimens.dynamic.core

import android.content.res.Configuration
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class StrategyFactorRegistryTest {

    @Before
    fun setUp() {
        StrategyFactorRegistry.resetForTest()
    }

    @Test
    fun register_replaysLastMetrics() {
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 400
            screenWidthDp = 400
            screenHeightDp = 800
            densityDpi = 420
        }
        StrategyFactorRegistry.publish(sharedMetricsFrom(cfg))

        var seenSw = -1f
        StrategyFactorRegistry.register { m -> seenSw = m.smallestWidthDp }
        assertEquals(400f, seenSw, 0.01f)
    }

    @Test
    fun publish_notifiesOnlyRegisteredContributors() {
        val hits = AtomicInteger(0)
        StrategyFactorRegistry.register { hits.incrementAndGet() }
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 360
            screenWidthDp = 360
            screenHeightDp = 640
            densityDpi = 320
        }
        StrategyFactorRegistry.publish(sharedMetricsFrom(cfg))
        assertEquals(1, hits.get())
        assertEquals(360f, StrategyFactorRegistry.lastMetricsForTest()!!.smallestWidthDp, 0.01f)
    }

    @Test
    fun reset_clearsState() {
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 300
            screenWidthDp = 300
            screenHeightDp = 600
            densityDpi = 160
        }
        StrategyFactorRegistry.publish(sharedMetricsFrom(cfg))
        StrategyFactorRegistry.resetForTest()
        assertNull(StrategyFactorRegistry.lastMetricsForTest())
    }

    @Test
    fun sharedMetrics_matchesCoreScaleFormula() {
        val cfg = Configuration().apply {
            smallestScreenWidthDp = 450
            screenWidthDp = 450
            screenHeightDp = 900
            densityDpi = 480
        }
        val m = sharedMetricsFrom(cfg)
        assertEquals(450f * DimenCache.INV_BASE_RATIO, m.scale, 0.0001f)
        assertTrue(m.arMultiplier > 0f)
        assertTrue(m.aspectRatioMul > 0f)
    }
}
