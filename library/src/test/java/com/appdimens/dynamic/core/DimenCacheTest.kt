package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DimenCacheTest {

    @Test
    fun testBuildKeyBoundaries() {
        // Test min boundary
        val keyMin = DimenCache.buildKey(
            baseValue = -1023,
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
    fun testPeekNullWhenBypassDoesNotStore() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
        val keyScaled = DimenCache.buildKey(
            10f, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val v = DimenCache.getOrPut(keyScaled) { 42f }
        assertEquals(42f, v, 0f)
        assertEquals(null, DimenCache.peek(keyScaled))
    }

    @Test
    fun testCacheBypass() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true

        // PERCENT, SCALED, DENSITY bypass shard storage when AR is off (see DimenCache.getOrPut KDoc).
        val keyPercent = DimenCache.buildKey(10, false, false, DimenCache.CalcType.PERCENT, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(7f, DimenCache.getOrPut(keyPercent) { 7f }, 0f)
        assertNull("PERCENT no-AR bypass: nothing stored", DimenCache.peek(keyPercent))

        val keyScaled = DimenCache.buildKey(11, false, false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(110f, DimenCache.getOrPut(keyScaled) { 110f }, 0f)
        assertNull("SCALED no-AR bypass: nothing stored", DimenCache.peek(keyScaled))

        val keyDensity = DimenCache.buildKey(12, false, false, DimenCache.CalcType.DENSITY, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(14f, DimenCache.getOrPut(keyDensity) { 14f }, 0f)
        assertNull("DENSITY no-AR bypass: nothing stored", DimenCache.peek(keyDensity))

        // AUTO is not bypassed; value must be readable via peek at its slot.
        val keyAuto = DimenCache.buildKey(13, false, false, DimenCache.CalcType.AUTO, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(10f, DimenCache.getOrPut(keyAuto) { 10f }, 0f)
        assertEquals(10f, DimenCache.peek(keyAuto) ?: -1f, 0f)

        val keyDiag = DimenCache.buildKey(14, false, false, DimenCache.CalcType.DIAGONAL, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(20f, DimenCache.getOrPut(keyDiag) { 20f }, 0f)
        assertNull("DIAGONAL no-AR bypass: nothing stored", DimenCache.peek(keyDiag))

        val keyPower = DimenCache.buildKey(14, false, false, DimenCache.CalcType.POWER, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(20f, DimenCache.getOrPut(keyPower) { 20f }, 0f)
        assertNull("POWER SW default bypass: nothing stored", DimenCache.peek(keyPower))

        val keyFluid = DimenCache.buildKey(14, false, false, DimenCache.CalcType.FLUID, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        assertEquals(20f, DimenCache.getOrPut(keyFluid) { 20f }, 0f)
        assertEquals(20f, DimenCache.peek(keyFluid) ?: -1f, 0f)

        val keyAutoAr = DimenCache.buildKey(15, false, false, DimenCache.CalcType.AUTO, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP)
        assertEquals(15f, DimenCache.getOrPut(keyAutoAr) { 15f }, 0f)
        assertEquals(15f, DimenCache.peek(keyAutoAr) ?: -1f, 0f)
    }

    @Test
    fun testDefaultAspectRatioBypass() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true

        // Default AR on SCALED → single multiply (arMultiplier) → bypass, not stored.
        val keyScaledAr = DimenCache.buildKey(
            16, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        assertEquals(16f, DimenCache.getOrPut(keyScaledAr) { 16f }, 0f)
        assertNull("default AR SCALED must bypass shard storage", DimenCache.peek(keyScaledAr))

        // Custom sensitivity → compute-always (never stored). Only 16 bits of the
        // 32-bit K fit the packed key, so caching could alias two different K values.
        val keyCustomSens = DimenCache.buildKey(
            17, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP,
            customSensitivityK = 0.5f
        )
        assertEquals(17f, DimenCache.getOrPut(keyCustomSens) { 17f }, 0f)
        assertNull(
            "custom-K results must never be cached (16-bit key aliasing)",
            DimenCache.peek(keyCustomSens)
        )

        // Non-default qualifier → full cache path.
        val keyWidthAr = DimenCache.buildKey(
            18, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        assertEquals(18f, DimenCache.getOrPut(keyWidthAr) { 18f }, 0f)
        assertEquals(18f, DimenCache.peek(keyWidthAr) ?: -1f, 0f)

        // CT_ASPECT_RATIO ln is computed exactly at snapshot time — not result-cached.
        val arLn = DimenCache.getOrPutAspectRatio(1.5f)
        assertEquals(kotlin.math.ln(1.5f), arLn, 0.001f)
    }

    @Test
    fun testCustomSensitivityCollisionReturnsExactResult() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true

        // Two DIFFERENT K values whose 32-bit float representations share the same
        // top 16 bits → identical packed cache keys (the 16-bit key aliasing problem).
        val k1 = Float.fromBits(0x3F800000) // 1.0f
        val k2 = Float.fromBits(0x3F800100) // 1.0f + 1/32768 ≈ 1.0000305f (same top 16 bits)
        fun key(k: Float) = DimenCache.buildKey(
            10f, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP,
            customSensitivityK = k
        )
        val key1 = key(k1)
        val key2 = key(k2)
        assertEquals(
            "fixture assumption: both K values must alias one 16-bit key",
            key1, key2
        )

        // Each call must compute with ITS OWN K — never answer with the other's result.
        val r1 = DimenCache.getOrPut(key1) { 10f * k1 }
        val r2 = DimenCache.getOrPut(key2) { 10f * k2 }
        assertEquals(10f * k1, r1, 0f)
        assertEquals(10f * k2, r2, 0f)
        assertNotEquals("collided keys must not alias results", r1, r2)
        assertNull("custom-K values are never cached", DimenCache.peek(key1))
        assertNull("custom-K values are never cached", DimenCache.peek(key2))
    }

    @Test
    fun testPowerLogDefaultBypass() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true

        val keyPower = DimenCache.buildKey(
            20, false, false, DimenCache.CalcType.POWER,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        assertEquals(20f, DimenCache.getOrPut(keyPower) { 20f }, 0f)
        assertNull("POWER SW default must bypass", DimenCache.peek(keyPower))

        val keyLog = DimenCache.buildKey(
            21, false, false, DimenCache.CalcType.LOGARITHMIC,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        assertEquals(21f, DimenCache.getOrPut(keyLog) { 21f }, 0f)
        assertNull("LOGARITHMIC SW default must bypass", DimenCache.peek(keyLog))

        // WIDTH + POWER still caches (pow path).
        val keyPowerW = DimenCache.buildKey(
            22, false, false, DimenCache.CalcType.POWER,
            DpQualifier.WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        assertEquals(22f, DimenCache.getOrPut(keyPowerW) { 22f }, 0f)
        assertEquals(22f, DimenCache.peek(keyPowerW) ?: -1f, 0f)
    }

    @Test
    fun testOrientationInvariantKeysShareSlot() {
        val portrait = DimenCache.buildKey(
            10f, false, false, DimenCache.CalcType.DIAGONAL,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val landscape = DimenCache.buildKey(
            10f, true, false, DimenCache.CalcType.DIAGONAL,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        assertEquals(
            "DIAGONAL keys must ignore isLandscape bit",
            portrait,
            landscape
        )
    }

    @Test
    fun testReadyToUseValues() {
        DimenCache.clearAll()
        // Use AUTO because it is NOT bypassed (POWER default SW is now bypassed like SCALED)
        val key = DimenCache.buildKey(10, false, false, DimenCache.CalcType.AUTO, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP)
        
        val computedValue = 15.5f
        val result1 = DimenCache.getOrPut(key) { computedValue }
        assertEquals(computedValue, result1)
        
        // Second call should be a hit and return exactly the same value
        val result2 = DimenCache.getOrPut(key) { 999f } // Dummy compute
        assertEquals("Retrieved value must be identical to stored value", computedValue, result2)
    }

    @Test
    fun testAspectRatioProtection() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true

        // Snapshot cache: a slot stores one (key,value) atomically. A second key mapping
        // to the same slot must not make peek() return the first key's value.
        val metrics = DimenMetrics.DEFAULT
        val keyA = DimenCache.buildKey(
            10f, false, false, DimenCache.CalcType.AUTO,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val keyB = DimenCache.buildKey(
            11f, false, false, DimenCache.CalcType.AUTO,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )

        assertEquals(100f, DimenCache.getOrPut(keyA, metrics) { 100f }, 0f)
        assertEquals(100f, DimenCache.peek(keyA, metrics) ?: -1f, 0f)

        assertEquals(200f, DimenCache.getOrPut(keyB, metrics) { 200f }, 0f)
        assertEquals(200f, DimenCache.peek(keyB, metrics) ?: -1f, 0f)

        // Whichever key currently owns the shared slot must peek consistently.
        val peekA = DimenCache.peek(keyA, metrics)
        val peekB = DimenCache.peek(keyB, metrics)
        if (peekA != null) assertEquals(100f, peekA, 0f)
        if (peekB != null) assertEquals(200f, peekB, 0f)
        assertTrue(peekA != null || peekB != null)
    }
}
