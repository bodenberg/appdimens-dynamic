/**
 * @author Bodenberg
 *
 * EN On-device comparison benchmark: Dynamic 3.1.8 (AppDimens library) vs SDPS 3.1.6
 *    (legacy published artifact). Runs 2 independent test passes, measuring:
 *    - Dp resolution values for 1dp, 10dp, 100dp in each library (sdp + sdpa)
 *    - Time per single dp call in each library (sdp + sdpa)
 *
 * PT Comparativo no device: Dynamic 3.1.8 (biblioteca AppDimens) vs SDPS 3.1.6
 *    (artefato legado publicado). Executa 2 passes de teste independentes, medindo:
 *    - Valores de resolução dp para 1dp, 10dp, 100dp em cada biblioteca (sdp + sdpa)
 *    - Tempo por chamada única de dp em cada biblioteca (sdp + sdpa)
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime

private const val TAG = "APPDIMENS_COMPARE"
private const val TEST_RUNS = 2
private const val WARMUP_COUNT = 5_000
private const val MEASURE_COUNT = 50_000

/** DP values to resolve in each test pass. */
private val DP_VALUES = intArrayOf(1, 10, 100)

/**
 * EN Runs the comparison benchmark off the main thread.
 * PT Executa o benchmark comparativo fora da thread principal.
 */
suspend fun runComparisonBenchmark(
    context: Context,
    mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
    onPhaseChange: (BenchmarkPhase) -> Unit
): ComparisonBenchmarkResult = withContext(Dispatchers.Default) {

    val ops = mode.ops()
    val legacy = com.appdimens.sdps.code.DimenSdp
    val window = Legacy316Window.from(context)

    // ── WARMUP ──────────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.COMPARE_WARMUP)
    repeat(WARMUP_COUNT) {
        for (dp in DP_VALUES) {
            ops.sdp(context, dp); legacy.sdp(context, dp)
            ops.hdp(context, dp); legacy.hdp(context, dp)
            ops.wdp(context, dp); legacy.wdp(context, dp)
            ops.sdpa(context, dp); legacy.sdpa(context, dp)
        }
    }
    delay(100)

    // ── MEASURE 2 TEST RUNS ─────────────────────────────────────────────────
    val testResults = mutableListOf<DpResolution>()
    val timeResults = mutableListOf<SingleDpTiming>()

    for (run in 1..TEST_RUNS) {
        onPhaseChange(
            when (run) {
                1 -> BenchmarkPhase.COMPARE_LIB_RUN
                else -> BenchmarkPhase.COMPARE_LEGACY_RUN
            }
        )

        // ── Dp resolution values: sdp (no AR) ──────────────────────────
        val dp1Cur  = ops.sdp(context, 1)
        val dp1Leg  = legacy.sdp(context, 1)
        val dp10Cur = ops.sdp(context, 10)
        val dp10Leg = legacy.sdp(context, 10)
        val dp100Cur = ops.sdp(context, 100)
        val dp100Leg = legacy.sdp(context, 100)

        // ── Dp resolution values: sdpa (with AR) ────────────────────────
        val dp1CurAr  = ops.sdpa(context, 1)
        val dp1LegAr  = legacy.sdpa(context, 1)
        val dp10CurAr = ops.sdpa(context, 10)
        val dp10LegAr = legacy.sdpa(context, 10)
        val dp100CurAr = ops.sdpa(context, 100)
        val dp100LegAr = legacy.sdpa(context, 100)

        testResults += DpResolution(
            dp1Current = dp1Cur, dp1Legacy = dp1Leg,
            dp10Current = dp10Cur, dp10Legacy = dp10Leg,
            dp100Current = dp100Cur, dp100Legacy = dp100Leg,
            dp1CurrentAr = dp1CurAr, dp1LegacyAr = dp1LegAr,
            dp10CurrentAr = dp10CurAr, dp10LegacyAr = dp10LegAr,
            dp100CurrentAr = dp100CurAr, dp100LegacyAr = dp100LegAr,
        )

        // ── Time per single dp call: sdp (no AR) ────────────────────────
        val currentSingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { ops.sdp(context, 1) }
        } / MEASURE_COUNT

        val legacySingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { legacy.sdp(context, 1) }
        } / MEASURE_COUNT

        // ── Time per single dp call: sdpa (with AR) ─────────────────────
        val currentSingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { ops.sdpa(context, 1) }
        } / MEASURE_COUNT

        val legacySingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { legacy.sdpa(context, 1) }
        } / MEASURE_COUNT

        timeResults += SingleDpTiming(
            currentNsPerDp = currentSingleNs,
            legacyNsPerDp  = legacySingleNs,
            currentArNsPerDp = currentSingleArNs,
            legacyArNsPerDp  = legacySingleArNs,
        )

        Log.i(TAG, "Test $run: sdp=${dp1Cur}/${dp1Leg} sdpa=${dp1CurAr}/${dp1LegAr} " +
            "time: dyn=${currentSingleNs}ns sdps=${legacySingleNs}ns dynAr=${currentSingleArNs}ns sdpsAr=${legacySingleArNs}ns")
    }

    val avgCurrent = timeResults.map { it.currentNsPerDp }.average().toLong()
    val avgLegacy  = timeResults.map { it.legacyNsPerDp }.average().toLong()
    val avgCurrentAr = timeResults.map { it.currentArNsPerDp }.average().toLong()
    val avgLegacyAr  = timeResults.map { it.legacyArNsPerDp }.average().toLong()

    Log.i(TAG, "Compare avg: dynamic=${avgCurrent}ns sdps=${avgLegacy}ns ratio=${"%.2f".format(avgLegacy.toFloat() / avgCurrent.toFloat())}")

    ComparisonBenchmarkResult(
        test1 = testResults[0],
        test2 = testResults[1],
        timeTest1 = timeResults[0],
        timeTest2 = timeResults[1],
        avgCurrentNsPerDp = avgCurrent,
        avgLegacyNsPerDp  = avgLegacy,
        avgCurrentArNsPerDp = avgCurrentAr,
        avgLegacyArNsPerDp  = avgLegacyAr,
        windowSw = window.sw,
        windowW  = window.w,
        windowH  = window.h,
        density  = window.density,
    )
}
