package com.appdimens.dynamic.core

import android.content.Context
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

/**
 * EN Verifies quiescence-based persistence (debounce + safety-net sample).
 * Intervals are scaled down so the suite stays fast; production defaults remain
 * 500 ms debounce / 10_000 ms sample.
 *
 * PT Verifica persistência por quiescência (debounce + sample de segurança).
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
        DimenCache.persistenceWritesEnabled = true
        DimenCache.saveDebounceMs = 500L
        DimenCache.saveSampleMs = 10_000L
        DimenCache.shutdown()
        DimenCache.performSaveCount.set(0)
    }

    @Test
    fun continuousWrites_doNotFlushUntilQuiet() {
        // Emit every 30 ms for ~900 ms — never quieter than debounce (80 ms).
        repeat(30) {
            DimenCache.saveToPersistence(ctx)
            Thread.sleep(30)
        }
        assertEquals(
            "performSave must not run during continuous cache writes",
            0,
            DimenCache.performSaveCount.get()
        )

        // Quiet window ≥ debounce → exactly one flush.
        Thread.sleep(150)
        assertEquals(
            "performSave must fire once after quiescence",
            1,
            DimenCache.performSaveCount.get()
        )
    }

    @Test
    fun continuousWrites_safetyNetSampleEventuallyFlushes() {
        DimenCache.saveDebounceMs = 10_000L // never quiesce during this test
        DimenCache.saveSampleMs = 250L
        DimenCache.restartSaveCollectorForTest()

        val endAt = System.currentTimeMillis() + 700L
        while (System.currentTimeMillis() < endAt) {
            DimenCache.saveToPersistence(ctx)
            Thread.sleep(20)
        }
        // Allow the in-flight sample tick to land.
        Thread.sleep(80)

        assertTrue(
            "safety-net sample must invoke performSave at least once under continuous writes " +
                "(got ${DimenCache.performSaveCount.get()})",
            DimenCache.performSaveCount.get() >= 1
        )
    }
}
