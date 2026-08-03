package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.nio.ByteBuffer

class DimenCacheSparseSerializationTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    @Test
    fun sparseRoundTrip_preservesPartialCache() {
        val expected = mutableMapOf<Long, Float>()
        for (i in 0 until 50) {
            val key = DimenCache.buildKey(
                (100 + i).toFloat(), false, false, DimenCache.CalcType.FLUID,
                DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
            )
            val value = i * 1.5f
            DimenCache.getOrPut(key) { value }
            expected[key] = value
        }
        assertEquals(50, DimenCache.stats().populated)

        val blob = DimenCache.serializeToByteArray()
        // Sparse: 4 + populated * 12 — not the full CACHE_SIZE * 12 dump.
        assertEquals(4 + 50 * 12, blob.size)
        assertTrue(blob.size < DimenCache.CACHE_SIZE * 12)

        val countHeader = ByteBuffer.wrap(blob).int
        assertEquals(50, countHeader)

        DimenCache.clearAll()
        assertEquals(0, DimenCache.stats().populated)

        DimenCache.loadFromByteArray(blob)
        assertEquals(50, DimenCache.stats().populated)
        for ((key, value) in expected) {
            assertEquals(value, DimenCache.peek(key) ?: -1f, 0f)
        }
    }

    @Test
    fun legacyDenseFormat_stillLoads() {
        // Build a synthetic dense blob (position = slot) with one entry.
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.POWER,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val (shard, slot) = DimenCache.shardAndSlot(key)
        val dense = ByteBuffer.allocate(DimenCache.CACHE_SIZE * 12)
        // Zero-fill then write at the linear index matching legacy layout.
        val linearIndex = shard * DimenCache.SHARD_SIZE + slot
        dense.position(linearIndex * 12)
        dense.putLong(key)
        dense.putFloat(99f)

        DimenCache.loadFromByteArray(dense.array())
        assertEquals(99f, DimenCache.peek(key) ?: -1f, 0f)
    }
}
