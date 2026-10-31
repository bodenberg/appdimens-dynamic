package com.appdimens.dynamic.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [DimenCache.buildCustomKey]: strategy ids live in the sensitivity bits, so distinct
 * custom strategies never alias, and bit 2 selects bypass (factor-only) vs. stored.
 */
class DimenCacheCustomKeyTest {

    private fun key(base: Float, id: Int, cacheable: Boolean = true, vt: DimenCache.ValueType = DimenCache.ValueType.DP) =
        DimenCache.buildCustomKey(base, id, isLandscape = false, ignoreMultiWindows = false, valueType = vt, cacheable = cacheable)

    @Test
    fun customOccupiesLastCalcTypeSlot() {
        assertEquals(15, DimenCache.CalcType.CUSTOM.ordinal)
        assertEquals(15, (key(16f, 1) ushr 27 and 0xFL).toInt())
    }

    @Test
    fun differentStrategies_neverShareAKey() {
        val keys = (0..DimenCache.MAX_CUSTOM_STRATEGY_KEY step 257).map { key(16f, it) }.toSet()
        assertEquals((0..DimenCache.MAX_CUSTOM_STRATEGY_KEY step 257).count(), keys.size)
        assertNotEquals(key(16f, 1), key(16f, 2))
        assertNotEquals(key(16f, 1), key(16f, 1, vt = DimenCache.ValueType.PX))
        assertNotEquals(key(16f, 1), key(17f, 1))
    }

    @Test
    fun strategyIdIsNotMistakenForCustomSensitivity() {
        assertFalse(DimenCache.hasCustomSensitivityKey(key(16f, 7)))
        assertFalse(DimenCache.hasCustomSensitivityKey(key(16f, 0xFFFF)))
    }

    @Test
    fun cacheableBitControlsBypass() {
        assertTrue(DimenCache.shouldBypassCache(key(16f, 3, cacheable = false)))
        assertFalse(DimenCache.shouldBypassCache(key(16f, 3, cacheable = true)))
    }

    @Test
    fun storedOnlyWhenCacheable() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
        val stored = key(21f, 42, cacheable = true)
        assertEquals(5f, DimenCache.getOrPut(stored) { 5f }, 0f)
        assertEquals(5f, DimenCache.peek(stored) ?: -1f, 0f)
        // A different strategy with the same base value misses instead of reading 5f.
        assertEquals(9f, DimenCache.getOrPut(key(21f, 43, cacheable = true)) { 9f }, 0f)

        val bypass = key(21f, 44, cacheable = false)
        assertEquals(6f, DimenCache.getOrPut(bypass) { 6f }, 0f)
        assertNull(DimenCache.peek(bypass))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsOutOfRangeId() {
        key(1f, DimenCache.MAX_CUSTOM_STRATEGY_KEY + 1)
    }
}
