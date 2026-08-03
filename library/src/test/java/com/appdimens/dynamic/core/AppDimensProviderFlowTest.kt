package com.appdimens.dynamic.core

import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * EN Guards the unconditional-[collectAsState] contract in [AppDimensProvider].
 * When [Activity] is null, [windowLayoutInfoFlowOrEmpty] must still return a live
 * [Flow] (empty) so Compose never skips the composable call across recompositions.
 *
 * PT Garante o contrato de collectAsState incondicional do [AppDimensProvider].
 */
class AppDimensProviderFlowTest {

    @Test
    fun nullActivity_returnsEmptyFlow_neverNull() {
        val flow = windowLayoutInfoFlowOrEmpty(null)
        assertTrue(flow !== null)
        val emissions = runBlocking { flow.toList() }
        assertEquals(emptyList<Any>(), emissions)
        // Same instance family as emptyFlow() — never emits, stable across recompositions.
        assertTrue(flow is kotlinx.coroutines.flow.Flow<*>)
    }

    @Test
    fun nullActivity_flowIsReusableAcrossCalls() {
        val a = windowLayoutInfoFlowOrEmpty(null)
        val b = windowLayoutInfoFlowOrEmpty(null)
        // Both must be collectable without throwing (Compose calls collectAsState every frame).
        runBlocking {
            assertEquals(0, a.toList().size)
            assertEquals(0, b.toList().size)
        }
        // emptyFlow() is a shared singleton in kotlinx.coroutines.
        assertTrue(a === emptyFlow<Any>() || a.toString().contains("Empty"))
    }
}
