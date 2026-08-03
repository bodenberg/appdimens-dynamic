package com.appdimens.dynamic.core

import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * EN Guards the unconditional-[collectAsState] contract in [AppDimensProvider].
 * When [Activity] is null, [windowLayoutInfoFlowOrEmpty] must still return a live
 * [Flow] (empty) so Compose never skips the composable call across recompositions
 * (activity null ↔ non-null swaps under Activity Embedding / multi-window).
 *
 * PT Garante o contrato de collectAsState incondicional do [AppDimensProvider].
 */
class AppDimensProviderFlowTest {

    @Test
    fun nullActivity_returnsNonNullEmptyFlow() {
        val flow = windowLayoutInfoFlowOrEmpty(null)
        assertNotNull(flow)
        val emissions = runBlocking { flow.toList() }
        assertEquals(emptyList<Any>(), emissions)
    }

    @Test
    fun nullActivity_stableAcrossRepeatedResolution() {
        // Simulates recomposition where activity stays null then flips — each
        // resolution must yield a collectable Flow (never a null that would skip
        // the @Composable collectAsState call).
        repeat(5) {
            val flow = windowLayoutInfoFlowOrEmpty(null)
            assertNotNull(flow)
            assertEquals(0, runBlocking { flow.toList().size })
        }
        assertTrue(windowLayoutInfoFlowOrEmpty(null) === emptyFlow<Any>())
    }
}
