package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DimenCacheSparseSerializationTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    @Test
    fun serializeAndLoad_areNoOps() {
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key) { 99f }

        val blob = DimenCache.serializeToByteArray()
        assertEquals(4, blob.size)

        DimenCache.clearAll()
        DimenCache.loadFromByteArray(blob)
        assertEquals(null, DimenCache.peek(key))
    }
}
