package com.appdimens.dynamic.core

import android.content.Context
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

/**
 * Result-cache persistence was removed in 3.1.7. These tests lock in the no-op contract so
 * callers do not pay I/O or resurrect stale values after invalidation.
 */
class DimenCachePersistenceFlowTest {

    private lateinit var ctx: Context

    @Before
    fun setup() {
        ctx = mock()
        DimenCache.persistenceWritesEnabled = false
        DimenCache.saveDebounceMs = 80L
        DimenCache.saveSampleMs = 10_000L
        DimenCache.restartSaveCollectorForTest()
    }

    @After
    fun teardown() {
        DimenCache.persistenceWritesEnabled = false
        DimenCache.saveDebounceMs = 500L
        DimenCache.saveSampleMs = 10_000L
        DimenCache.shutdown()
        DimenCache.performSaveCount.set(0)
    }

    @Test
    fun saveToPersistence_isNoOp() {
        repeat(30) {
            DimenCache.saveToPersistence(ctx)
            Thread.sleep(5)
        }
        assertEquals(0, DimenCache.performSaveCount.get())
    }
}
