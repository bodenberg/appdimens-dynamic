package com.appdimens.dynamic.core

import org.junit.Before
import org.junit.Test
import kotlin.system.measureNanoTime
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter

class DimenPerformanceTest {

    private val iterations = 1000000
    private val batchSize = 100
    private val batchIterations = 10000
    
    // Keys using actual library buildKey logic
    private val batchKeysNoAr = LongArray(batchSize) { 
        DimenCache.buildKey(10 + it, 360, 640, 360, false, false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.PX)
    }
    private val batchKeysAr = LongArray(batchSize) { 
        DimenCache.buildKey(10 + it, 360, 640, 360, false, false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.PX)
    }
    
    private val batchValues = FloatArray(batchSize) { (10 + it) * 1.5f }
    private val rawValues = FloatArray(iterations) { (10 + (it % batchSize)).toFloat() }

    private var checksum = 0f

    @Before
    fun setup() {
        DimenCache.isEnabled = true
        DimenCache.isInitialized.set(true)
        for (i in 0 until batchSize) {
            DimenCache.getOrPut(batchKeysNoAr[i]) { batchValues[i] }
            DimenCache.getOrPut(batchKeysAr[i]) { batchValues[i] }
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
        
        // --- PRE-POPULATE CACHE FOR HIT TESTS ---
        for (i in 0 until batchSize) {
            DimenCache.getOrPut(batchKeysNoAr[i]) { batchValues[i] }
            DimenCache.getOrPut(batchKeysAr[i]) { batchValues[i] }
        }
        
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

        // 3. Single Cache Lookup (No AR)
        val timeC1 = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeysNoAr[i % batchSize]) { 0f }
                }
                checksum += localSum
            }
        }

        // 3b. Single Cache Lookup (With AR)
        val timeC2 = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeysAr[i % batchSize]) { 0f }
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

        // 6. Batch Cache Resolution (No AR)
        val batchC1 = runWithMin(warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    for (j in 0 until batchSize) {
                        localSum += DimenCache.getOrPut(batchKeysNoAr[j]) { 0f }
                    }
                }
                checksum += localSum
            }
        }

        // 6b. Batch Cache Resolution (With AR)
        // Protocol: Clear -> Wait -> Prime -> Wait -> Measure
        DimenCache.clearAll()
        Thread.sleep(500)
        for (i in 0 until batchSize) {
            DimenCache.getOrPut(batchKeysAr[i]) { batchValues[i] }
        }
        Thread.sleep(500)
        
        val batchC2 = runWithMin(trials = 5, warmup = 10000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    for (j in 0 until batchSize) {
                        localSum += DimenCache.getOrPut(batchKeysAr[j]) { 0f }
                    }
                }
                checksum += localSum
            }
        }

        // 6c. Batch Cache Resolution (Mixed 50/50)
        DimenCache.clearAll()
        Thread.sleep(500)
        for (j in 0 until 50) {
            DimenCache.getOrPut(batchKeysAr[j]) { batchValues[j] }
        }
        for (j in 50 until 100) {
            DimenCache.getOrPut(batchKeysNoAr[j]) { batchValues[j] }
        }
        Thread.sleep(500)

        val batchC3 = runWithMin(trials = 5, warmup = 10000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 100
            measureNanoTime {
                var localSum = 0f
                repeat(actualBatchIter) {
                    for (j in 0 until 50) {
                        localSum += DimenCache.getOrPut(batchKeysAr[j]) { 0f }
                    }
                    for (j in 50 until 100) {
                        localSum += DimenCache.getOrPut(batchKeysNoAr[j]) { 0f }
                    }
                }
                checksum += localSum
            }
        }

        // 6d. getBatch Resolution
        val batchD1 = runWithMin(trials = 5, warmup = 10000) { count ->
            measureNanoTime {
                var localSum = 0f
                repeat(batchIterations) {
                    val res = DimenCache.getBatch(batchKeysNoAr) { 0f }
                    for (v in res) localSum += v
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
        println("raw_single_cache_no_ar: ${timeC1 / iterations}")
        println("raw_single_cache_ar: ${timeC2 / iterations}")
        println("raw_batch_calc_math: ${batchA / batchIterations}")
        println("raw_batch_calc_ar: ${batchB / batchIterations}")
        println("raw_batch_cache_no_ar: ${batchC1 / batchIterations}")
        println("raw_batch_cache_ar: ${batchC2 / batchIterations}")
        println("raw_batch_cache_mixed: ${batchC3 / batchIterations}")
        println("raw_get_batch: ${batchD1 / batchIterations}")
        println("raw_persistence_load: ${timeD}")
        println("checksum_validation: $checksum") 
        println("--- PERFORMANCE_REPORT_END ---")
    }
}
