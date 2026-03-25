package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DimenCacheTest {

    @Test
    fun testBuildKeyBoundaries() {
        // Test min boundary
        val keyMin = DimenCache.buildKey(
            baseValue = -1023,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP
        )
        
        // Test zero
        val keyZero = DimenCache.buildKey(
            baseValue = 0,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP
        )
        
        // Test max boundary
        val keyMax = DimenCache.buildKey(
            baseValue = 1024,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP
        )

        assertNotEquals("Key for -1023 should differ from 0", keyMin, keyZero)
        assertNotEquals("Key for 1024 should differ from 0", keyMax, keyZero)
        assertNotEquals("Key for -1023 should differ from 1024", keyMin, keyMax)
    }

    @Test
    fun testKeyUniquenessForBaseValue() {
        val keys = mutableSetOf<Long>()
        for (i in -1023..1024) {
            val key = DimenCache.buildKey(
                baseValue = i,
                screenWidthDp = 360,
                screenHeightDp = 640,
                smallestWidthDp = 360,
                isLandscape = false,
                ignoreMultiWindows = false,
                calcType = DimenCache.CalcType.SCALED,
                qualifier = DpQualifier.SMALL_WIDTH,
                inverter = Inverter.DEFAULT,
                applyAspectRatio = false,
                valueType = DimenCache.ValueType.DP
            )
            keys.add(key)
        }
        assertEquals("Should have 2048 unique keys for the full range", 2048, keys.size)
    }

    @Test
    fun testKeySensitivityK() {
        val key1 = DimenCache.buildKey(
            baseValue = 10,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = 1.0f
        )
        val key2 = DimenCache.buildKey(
            baseValue = 10,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = 2.0f
        )
        val keyNull = DimenCache.buildKey(
            baseValue = 10,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = null
        )

        assertNotEquals("Keys with different sensitivity should differ", key1, key2)
        assertNotEquals("Keys with custom vs null sensitivity should differ", key1, keyNull)
    }

    @Test
    fun testKeyContextChanges() {
        val keyPortrait = DimenCache.buildKey(
            baseValue = 10,
            screenWidthDp = 360,
            screenHeightDp = 640,
            smallestWidthDp = 360,
            isLandscape = false,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP
        )
        val keyLandscape = DimenCache.buildKey(
            baseValue = 10,
            screenWidthDp = 640, // Swapped for landscape
            screenHeightDp = 360,
            smallestWidthDp = 360,
            isLandscape = true,
            ignoreMultiWindows = false,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = DpQualifier.SMALL_WIDTH,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = false,
            valueType = DimenCache.ValueType.DP
        )

        assertNotEquals("Keys for different orientations should differ", keyPortrait, keyLandscape)
    }

    @Test
    fun testCacheBypass() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
        
        // AUTO is bypassed when AR=false
        val keyAuto = DimenCache.buildKey(10, 360, 640, 360, false, false, DimenCache.CalcType.AUTO, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        DimenCache.getOrPut(keyAuto) { 10f }
        assertEquals("AUTO without AR should bypass cache", 0, DimenCache.stats().populated)

        // DIAGONAL is NOT bypassed (CalcType > 3)
        val keyDiag = DimenCache.buildKey(10, 360, 640, 360, false, false, DimenCache.CalcType.DIAGONAL, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        DimenCache.getOrPut(keyDiag) { 20f }
        assertEquals("DIAGONAL should hit cache", 1, DimenCache.stats().populated)
    }

    @Test
    fun testAspectRatioProtection() {
        DimenCache.clearAll()
        
        // 1. Put an AR entry
        val expectedArValue = kotlin.math.ln(1.78f)
        val arResult = DimenCache.getOrPutAspectRatio(1.78f)
        assertEquals(expectedArValue, arResult, 0.001f)
        assertEquals(1, DimenCache.stats().populated)
        
        // Find the index for this AR key using NEW sharding logic
        val arKey = (13L shl 19) or (java.lang.Float.floatToRawIntBits(1.78f).toLong() and 0xFFFFFFFFL)
        val hAr = (arKey xor (arKey ushr 32)).toInt()
        val mixedAr = hAr xor (hAr ushr 16)
        val shardIndex = (mixedAr ushr 9) and DimenCache.SHARD_MASK
        val slotIndex = mixedAr and DimenCache.SHARD_SIZE_MASK
        
        // 2. Synthesize a collision: a normal key that hashes to the same shard and slot
        DimenCache.keysArray[shardIndex].set(slotIndex, arKey)
        DimenCache.valueBitsArray[shardIndex].set(slotIndex, expectedArValue.toRawBits())
        
        // Try to put a normal entry that would map to the same shard/slot
        var collisionKey = 0L
        for (k in 1L..2000000L) {
            val h = (k xor (k ushr 32)).toInt()
            val m = h xor (h ushr 16)
            val s = (m ushr 9) and DimenCache.SHARD_MASK
            val i = m and DimenCache.SHARD_SIZE_MASK
            if (s == shardIndex && i == slotIndex) {
                // Ensure it's not an AR key (CalcType != 13)
                if ((k shr 19 and 0xFL) != 13L) {
                    collisionKey = k
                    break
                }
            }
        }
        
        assertNotEquals(0L, collisionKey)
        
        // Attempt to store the colliding normal key
        DimenCache.getOrPut(collisionKey) { 999f }
        
        // AR key should still be there!
        assertEquals("AR key should be protected from collision", arKey, DimenCache.keysArray[shardIndex].get(slotIndex))
        assertEquals(expectedArValue, Float.fromBits(DimenCache.valueBitsArray[shardIndex].get(slotIndex)), 0.001f)
    }
}
