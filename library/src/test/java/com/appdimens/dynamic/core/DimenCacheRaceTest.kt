package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import android.content.res.Configuration
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

class DimenCacheRaceTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
    }

    @Test
    fun concurrentWrites_noIncorrectValues() {
        val threads = 8
        val iterations = 5000
        val wrongCount = AtomicInteger(0)
        val latch = CountDownLatch(threads)

        val tasks = (0 until threads).map { t ->
            Thread {
                try {
                    for (i in 0 until iterations) {
                        val baseValue = (t * iterations + i).toFloat()
                        val key = DimenCache.buildKey(
                            baseValue, false, false, DimenCache.CalcType.FLUID,
                            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
                        )
                        val expected = baseValue * 2f
                        val result = DimenCache.getOrPut(key) { expected }
                        // CRITICAL FIX: Any incorrect return is a bug — do not allow
                        // transient wrong values to pass via a subsequent peek().
                        if (result != expected) {
                            wrongCount.incrementAndGet()
                        }
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        tasks.forEach { it.start() }
        latch.await()

        assertTrue(
            "Expected zero wrong values but got ${wrongCount.get()}",
            wrongCount.get() == 0
        )
    }

    @Test
    fun concurrentWrites_sameSlotCollision() {
        val metrics = DimenMetrics.DEFAULT
        val targetKey1 = DimenCache.buildKey(
            100f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val h1 = (targetKey1 xor (targetKey1 ushr 32)).toInt()
        val targetSlot = (h1 xor (h1 ushr 16)) and (2048 / 4 - 1)

        var collidingKey = 0L
        for (bv in 101..2000000) {
            val k = DimenCache.buildKey(
                bv.toFloat(), false, false, DimenCache.CalcType.FLUID,
                DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
            )
            val h = (k xor (k ushr 32)).toInt()
            val m = h xor (h ushr 16)
            if ((m and (2048 / 4 - 1)) == targetSlot) {
                collidingKey = k
                break
            }
        }

        if (collidingKey == 0L) return

        val threads = 4
        val iterations = 10000
        val wrongCount = AtomicInteger(0)
        val latch = CountDownLatch(threads)

        val keys = longArrayOf(targetKey1, collidingKey)
        val values = floatArrayOf(200f, 777f)

        val tasks = (0 until threads).map { t ->
            Thread {
                try {
                    val idx = t % 2
                    for (i in 0 until iterations) {
                        val result = DimenCache.getOrPut(keys[idx], metrics) { values[idx] }
                        // CRITICAL FIX: Each thread must get its OWN value back,
                        // not merely "one of the two valid values". Accepting the
                        // other key's value hides linearizability violations.
                        if (result != values[idx]) {
                            wrongCount.incrementAndGet()
                        }
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        tasks.forEach { it.start() }
        latch.await()

        assertTrue(
            "Same-slot collision should never produce a value other than the two expected ones, got ${wrongCount.get()} wrong",
            wrongCount.get() == 0
        )
    }

    /**
     * Verifies that the fastPartitionSlot fix correctly isolates two metrics snapshots.
     * Each snapshot has its own compute function returning a distinct value; under
     * heavy interleaving, a reader of snapshot A must never see snapshot B's result.
     */
    @Test
    fun concurrentSnapshots_neverReturnValueFromAnotherSnapshot() {
        val metricsA = DimenMetrics(
            screenWidthDp = 300, screenHeightDp = 533,
            smallestScreenWidthDp = 300, densityDpi = 160,
            fontScaleBits = 1f.toRawBits(),
            orientation = Configuration.ORIENTATION_PORTRAIT,
            uiMode = 0, isInMultiWindowMode = false
        )
        val metricsB = DimenMetrics(
            screenWidthDp = 600, screenHeightDp = 960,
            smallestScreenWidthDp = 600, densityDpi = 320,
            fontScaleBits = 1f.toRawBits(),
            orientation = Configuration.ORIENTATION_PORTRAIT,
            uiMode = 0, isInMultiWindowMode = false
        )

        val sameKey = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )

        val failures = AtomicInteger(0)
        val iterations = 20000
        val threads = 8
        val latch = CountDownLatch(threads)

        val tasks = (0 until threads).map { worker ->
            Thread {
                try {
                    repeat(iterations) {
                        val useA = (worker + it) % 2 == 0
                        val metrics = if (useA) metricsA else metricsB
                        val expected = if (useA) 111f else 999f

                        val actual = DimenCache.getOrPut(sameKey, metrics) {
                            expected
                        }

                        if (actual != expected) {
                            failures.incrementAndGet()
                        }
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        tasks.forEach { it.start() }
        latch.await()

assertTrue(
            "Cross-snapshot contamination: wrong values detected",
            failures.get() == 0
        )
    }
}
