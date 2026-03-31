package com.appdimens.dynamic.core

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import kotlin.system.measureNanoTime

@RunWith(AndroidJUnit4::class)
class DimenAndroidPerformanceTest {

    private lateinit var targetContext: Context
    private val iterations = 100000 
    private val batchSize = 100
    private val batchIterations = 1000
    
    // Keys using actual library buildKey logic
    private val batchKeysNoAr = LongArray(batchSize) { 
        DimenCache.buildKey(10 + it, false, false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.PX)
    }
    private val batchKeysAr = LongArray(batchSize) { 
        DimenCache.buildKey(10 + it, false, false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.PX)
    }
    
    private val batchValues = FloatArray(batchSize) { (10 + it) * 2.0f }
    private val rawValues = FloatArray(iterations) { (10 + (it % batchSize)).toFloat() }

    private var checksum = 0f

    @Before
    fun setup() {
        targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        DimenCache.isEnabled = true
        DimenCache.init(targetContext)
        DimenCache.isInitialized.set(true)
        for (i in 0 until batchSize) {
            DimenCache.getOrPut(batchKeysNoAr[i]) { batchValues[i] }
            DimenCache.getOrPut(batchKeysAr[i]) { batchValues[i] }
        }
    }

    private fun runWithMin(trials: Int = 3, warmup: Int = 10000, block: (Int) -> Long): Long {
        // Warmup
        var dummy = 0f
        repeat(warmup) { dummy += (block(1).toFloat() / 1e9f) }
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
        Thread.sleep(200) // Slightly longer for Android stability
        
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

        // 3. Single Cache Hit (No AR)
        val timeC1 = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeysNoAr[i % batchSize]) { 0f }
                }
                checksum += localSum
            }
        }

        // 3b. Single Cache Hit (With AR)
        val timeC2 = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeysAr[i % batchSize]) { 0f }
                }
                checksum += localSum
            }
        }

        // 4. Batch Calc (Math Only)
        val batchA = runWithMin(warmup = 100) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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
        val batchB = runWithMin(warmup = 100) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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
        val batchC1 = runWithMin(warmup = 100) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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

        val batchC2 = runWithMin(trials = 3, warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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

        val batchC3 = runWithMin(trials = 3, warmup = 1000) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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

        // 7. Persistence Load
        val serializedData = DimenCache.serializeToByteArray()
        val timeD = runWithMin(trials = 5, warmup = 3) {
            measureNanoTime {
                DimenCache.loadFromByteArray(serializedData)
            }
        }

        println("--- ANDROID_PERFORMANCE_START ---")
        println("raw_single_calc_math: ${timeA / iterations}")
        println("raw_single_calc_ar: ${timeB / iterations}")
        println("raw_single_cache_no_ar: ${timeC1 / iterations}")
        println("raw_single_cache_ar: ${timeC2 / iterations}")
        println("raw_batch_calc_math: ${batchA / batchIterations}")
        println("raw_batch_calc_ar: ${batchB / batchIterations}")
        println("raw_batch_cache_no_ar: ${batchC1 / batchIterations}")
        println("raw_batch_cache_ar: ${batchC2 / batchIterations}")
        println("raw_batch_cache_mixed: ${batchC3 / batchIterations}")
        println("raw_persistence_load: ${timeD}")
        println("checksum_validation: $checksum") 
        println("--- ANDROID_PERFORMANCE_END ---")
    }
}
