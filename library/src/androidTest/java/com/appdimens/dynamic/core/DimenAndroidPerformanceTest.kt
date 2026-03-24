package com.appdimens.dynamic.core

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureNanoTime

@RunWith(AndroidJUnit4::class)
class DimenAndroidPerformanceTest {

    private lateinit var targetContext: Context
    private val iterations = 100000 
    private val batchSize = 100
    private val batchIterations = 1000
    
    private val batchKeys = LongArray(batchSize) { (10 + it).toLong() }
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
            DimenCache.getOrPut(batchKeys[i]) { batchValues[i] }
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

        // 3. Single Cache Hit (L1)
        val timeC = runWithMin { count ->
            measureNanoTime {
                var localSum = 0f
                for (i in 0 until count) {
                    localSum += DimenCache.getOrPut(batchKeys[i % batchSize]) { 0f }
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

        // 6. Batch Cache Resolution (L1)
        val batchC = runWithMin(warmup = 100) { count ->
            val actualBatchIter = if (count > 1) batchIterations else 10
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
        val timeD = runWithMin(trials = 5, warmup = 3) {
            measureNanoTime {
                DimenCache.loadFromByteArray(serializedData)
            }
        }

        println("--- ANDROID_PERFORMANCE_START ---")
        println("raw_single_calc_math: ${timeA / iterations}")
        println("raw_single_calc_ar: ${timeB / iterations}")
        println("raw_single_cache_l1: ${timeC / iterations}")
        println("raw_batch_calc_math: ${batchA / batchIterations}")
        println("raw_batch_calc_ar: ${batchB / batchIterations}")
        println("raw_batch_cache_l1: ${batchC / batchIterations}")
        println("raw_persistence_load: ${timeD}")
        println("checksum_validation: $checksum") 
        println("--- ANDROID_PERFORMANCE_END ---")
    }
}
