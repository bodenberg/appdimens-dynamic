package com.appdimens.dynamic.core

import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
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
                        val result = DimenCache.getOrPut(key) { baseValue * 2f }
                        if (result != baseValue * 2f) {
                            val peeked = DimenCache.peek(key)
                            if (peeked != null && peeked != baseValue * 2f) {
                                wrongCount.incrementAndGet()
                            }
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
        val targetKey1 = DimenCache.buildKey(
            100f, false, false, DimenCache.CalcType.FLUID,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
        )
        val h1 = (targetKey1 xor (targetKey1 ushr 32)).toInt()
        val mixed1 = h1 xor (h1 ushr 16)
        val targetShard = (mixed1 ushr 9) and DimenCache.SHARD_MASK
        val targetSlot = mixed1 and DimenCache.SHARD_SIZE_MASK

        var collidingKey = 0L
        for (bv in 101..2000000) {
            val k = DimenCache.buildKey(
                bv.toFloat(), false, false, DimenCache.CalcType.FLUID,
                DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
            )
            val h = (k xor (k ushr 32)).toInt()
            val m = h xor (h ushr 16)
            if (((m ushr 9) and DimenCache.SHARD_MASK) == targetShard &&
                (m and DimenCache.SHARD_SIZE_MASK) == targetSlot
            ) {
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
                        val result = DimenCache.getOrPut(keys[idx]) { values[idx] }
                        if (result != values[0] && result != values[1]) {
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
}
