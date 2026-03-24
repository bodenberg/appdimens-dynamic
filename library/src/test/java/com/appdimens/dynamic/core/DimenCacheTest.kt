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
}
