package com.appdimens.dynamic.core

import org.junit.Before
import org.junit.Test
import kotlin.system.measureNanoTime

class DimenPerformanceTest {

    private val iterations = 1000000
    private val batchSize = 100
    private val batchIterations = 10000
    
    private val batchKeys = LongArray(batchSize) { (10 + it).toLong() }
    private val batchValues = FloatArray(batchSize) { (10 + it) * 1.5f }
    private val rawValues = FloatArray(iterations) { (10 + (it % batchSize)).toFloat() }

    private var checksum = 0f

    @Before
    fun setup() {
        DimenCache.isEnabled = true
        DimenCache.isInitialized.set(true)
        for (i in 0 until batchSize) {
            DimenCache.getOrPut(batchKeys[i]) { batchValues[i] }
        }
    }

    private fun runWithMin(trials: Int = 5, warmup: Int = 100000, block: (Int) -> Long): Long {
        // Warmup
        val warmupIterations = warmup
        var dummy = 0f
        repeat(warmupIterations) { dummy += (block(1).toFloat() / 1e9f) }
        checksum += dummy

        var minTime = Long.MAX_VALUE
        repeat(trials) {
            val t = block(iterations)
            if (t < minTime) minTime = t
        }
        return minTime
    }

    @Test
    fun benchmarkAll() {
        // --- INITIAL CLEAN (Once per run) ---
        DimenCache.clearAll()
        checksum = 0f
        Thread.sleep(100)
        
        // 1. Single Calc (Math Only)
        val timeA = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += (rawValues[i % iterations] * 0.0033333334f) * 360f 
                }
                checksum += localSum
            }
        }

        // 2. Single Calc (Math + AR)
        val timeB = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    val ar = AspectRatioLookup.lookup(0.5625f) ?: 1f
                    localSum += (rawValues[i % iterations] * 0.0033333334f) * 360f * ar
                }
                checksum += localSum
            }
        }

        // 3. Single Cache Lookup (L1)
        val timeC = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeys[i % batchSize]) { 0f }
                }
                checksum += localSum
            }
        }

        // 4. Batch Calc (Math Only) - Measure per 100
        val batchA = runWithMin(warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    for (j in 0 until batchSize) {
                        localSum += (batchValues[j] * 0.0033333334f) * 360f
                    }
                }
                checksum += localSum
            }
        }

        // 5. Batch Calc (Math + AR)
        val batchB = runWithMin(warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    val ar = AspectRatioLookup.lookup(0.5625f) ?: 1f
                    for (j in 0 until batchSize) {
                        localSum += (batchValues[j] * 0.0033333334f) * 360f * ar
                    }
                }
                checksum += localSum
            }
        }

        // 6. Batch Cache Resolution (L1)
        val batchC = runWithMin(warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    for (j in 0 until batchSize) {
                        localSum += DimenCache.getOrPut(batchKeys[j]) { 0f }
                    }
                }
                checksum += localSum
            }
        }

        // 7. Persistence Load
        val serializedData = DimenCache.serializeToByteArray()
        val timeD = runWithMin(trials = 10, warmup = 10) { 
            measureNanoTime {
                DimenCache.loadFromByteArray(serializedData)
            }
        }

        println("--- PERFORMANCE_REPORT_START ---")
        println("raw_single_calc_math: ${timeA / iterations}")
        println("raw_single_calc_ar: ${timeB / iterations}")
        println("raw_single_cache_l1: ${timeC / iterations}")
        println("raw_batch_calc_math: ${batchA / batchIterations}")
        println("raw_batch_calc_ar: ${batchB / batchIterations}")
        println("raw_batch_cache_l1: ${batchC / batchIterations}")
        println("raw_persistence_load: ${timeD}")
        println("checksum_validation: $checksum") 
        println("--- PERFORMANCE_REPORT_END ---")
    }
}
