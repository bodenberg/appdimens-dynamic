package com.appdimens.dynamic.core

import android.content.Context
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

/**
 * Result-cache persistence was removed in 3.1.7. The public entry points are kept as
 * binary-compatibility no-ops: these tests lock in the contract that they never perform
 * I/O, never resurrect stale values, and never disturb the in-memory snapshot cache.
 */
class DimenCachePersistenceFlowTest {

    private lateinit var ctx: Context

    @Before
    fun setup() {
        ctx = mock()
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    @Test
    fun saveToPersistence_isNoOp_andKeepsCacheIntact() {
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        assertEquals(99f, DimenCache.getOrPut(key) { 99f }, 0f)
        DimenCache.saveToPersistence(ctx)
        assertEquals(99f, DimenCache.peek(key)!!, 0f)
    }

    @Test
    fun shutdown_isNoOp() {
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        assertEquals(99f, DimenCache.getOrPut(key) { 99f }, 0f)
        DimenCache.shutdown()
        assertEquals(99f, DimenCache.peek(key)!!, 0f)
    }
}