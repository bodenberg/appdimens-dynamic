/**
 * @author Bodenberg
 *
 * EN Off-main benchmark core for the 3-way comparison.
 *    Runs 3 independent test passes, each measuring:
 *    - Dp resolution values for 1dp, 10dp, 100dp in all three libraries
 *    - Time per single dp call in AppDimens + Concorrente 1 (Concorrente 2 measured by probe)
 *
 * PT Núcleo off-main do benchmark de 3 vias.
 *    Executa 3 passes de teste independentes, cada um medindo:
 *    - Valores de resolução dp para 1dp, 10dp, 100dp nas três bibliotecas
 *    - Tempo por chamada única de dp em AppDimens + Concorrente 1 (Concorrente 2 medido pela sonda)
 */
package com.example.benchlab.benchmark

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime

private const val TAG = "BENCHLAB"
private const val TEST_RUNS = 3
private const val WARMUP_COUNT = 5_000
private const val MEASURE_COUNT = 50_000

/** DP values to resolve in each test pass. */
private val DP_VALUES = intArrayOf(1, 10, 100)

/**
 * EN Runs the full comparison benchmark off the main thread.
 * PT Executa o benchmark comparativo completo fora da thread principal.
 *
 * @param context EN Android context. PT Contexto Android.
 * @param concorrente2Result EN Pre-measured Concorrente 2 data from the composable probe. PT Dados pré-mensurados da Concorrente 2 da sonda composable.
 * @param onPhaseChange EN Callback for phase transitions. PT Callback para transições de fase.
 */
suspend fun runCompetitorBenchmark(
    context: Context,
    concorrente2Result: Concorrente2ProbeResult,
    onPhaseChange: (BenchPhase) -> Unit
): CompetitorBenchmarkResult = withContext(Dispatchers.Default) {

    val app = com.appdimens.dynamic.code.DimenSdp
    val concorrente1 = com.appdimens.sdps.code.DimenSdp

    // ── WARMUP ──────────────────────────────────────────────────────────────
    onPhaseChange(BenchPhase.WARMUP)
    repeat(WARMUP_COUNT) {
        for (dp in DP_VALUES) {
            app.sdp(context, dp); concorrente1.sdp(context, dp)
            app.hdp(context, dp); concorrente1.hdp(context, dp)
            app.wdp(context, dp); concorrente1.wdp(context, dp)
        }
    }
    delay(100)

    // ── MEASURE 3 TEST RUNS ─────────────────────────────────────────────────
    val testResults = mutableListOf<DpResolution3>()
    val timeResults = mutableListOf<SingleDpTiming3>()

    for (run in 1..TEST_RUNS) {
        onPhaseChange(
            when (run) {
                1 -> BenchPhase.TEST1
                2 -> BenchPhase.TEST2
                else -> BenchPhase.TEST3
            }
        )

        // ── Dp resolution values: sdp (no AR) ─────────────────────────
        val dp1App  = app.sdp(context, 1)
        val dp1Conc1  = concorrente1.sdp(context, 1)
        val dp10App = app.sdp(context, 10)
        val dp10Conc1 = concorrente1.sdp(context, 10)
        val dp100App = app.sdp(context, 100)
        val dp100Conc1 = concorrente1.sdp(context, 100)

        // ── Dp resolution values: sdpa (with AR) ────────────────────────
        val dp1AppAr  = app.sdpa(context, 1)
        val dp1Conc1Ar  = concorrente1.sdpa(context, 1)
        val dp10AppAr = app.sdpa(context, 10)
        val dp10Conc1Ar = concorrente1.sdpa(context, 10)
        val dp100AppAr = app.sdpa(context, 100)
        val dp100Conc1Ar = concorrente1.sdpa(context, 100)

        testResults += DpResolution3(
            dp1AppDimens = dp1App, dp1Concorrente1 = dp1Conc1, dp1Concorrente2 = concorrente2Result.dp1Px,
            dp10AppDimens = dp10App, dp10Concorrente1 = dp10Conc1, dp10Concorrente2 = concorrente2Result.dp10Px,
            dp100AppDimens = dp100App, dp100Concorrente1 = dp100Conc1, dp100Concorrente2 = concorrente2Result.dp100Px,
            dp1AppDimensAr = dp1AppAr, dp1Concorrente1Ar = dp1Conc1Ar,
            dp10AppDimensAr = dp10AppAr, dp10Concorrente1Ar = dp10Conc1Ar,
            dp100AppDimensAr = dp100AppAr, dp100Concorrente1Ar = dp100Conc1Ar,
        )

        // ── Time per single dp call: sdp (no AR) ────────────────────────
        val appSingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { app.sdp(context, 1) }
        } / MEASURE_COUNT

        val concorrente1SingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { concorrente1.sdp(context, 1) }
        } / MEASURE_COUNT

        // ── Time per single dp call: sdpa (with AR) ─────────────────────
        val appSingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { app.sdpa(context, 1) }
        } / MEASURE_COUNT

        val concorrente1SingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { concorrente1.sdpa(context, 1) }
        } / MEASURE_COUNT

        // Concorrente 2 time is fixed from the probe (already measured once)
        timeResults += SingleDpTiming3(
            appDimensNs = appSingleNs,
            concorrente1Ns = concorrente1SingleNs,
            concorrente2Ns = concorrente2Result.sdpAvgNs,
            appDimensArNs = appSingleArNs,
            concorrente1ArNs = concorrente1SingleArNs,
        )

        Log.i(TAG, "Test $run: dp1=app=$dp1App conc1=$dp1Conc1 conc2=${concorrente2Result.dp1Px} " +
            "dp10=app=$dp10App conc1=$dp10Conc1 conc2=${concorrente2Result.dp10Px} " +
            "dp100=app=$dp100App conc1=$dp100Conc1 conc2=${concorrente2Result.dp100Px} " +
            "ar: dp1=app=$dp1AppAr conc1=$dp1Conc1Ar dp10=app=$dp10AppAr conc1=$dp10Conc1Ar " +
            "dp100=app=$dp100AppAr conc1=$dp100Conc1Ar " +
            "time: app=${appSingleNs}ns conc1=${concorrente1SingleNs}ns conc2=${concorrente2Result.sdpAvgNs}ns " +
            "ar: app=${appSingleArNs}ns conc1=${concorrente1SingleArNs}ns")
    }

    val avgApp = timeResults.map { it.appDimensNs }.average().toLong()
    val avgConcorrente1 = timeResults.map { it.concorrente1Ns }.average().toLong()
    val avgConcorrente2 = timeResults.map { it.concorrente2Ns }.average().toLong()
    val avgAppAr = timeResults.map { it.appDimensArNs }.average().toLong()
    val avgConcorrente1Ar = timeResults.map { it.concorrente1ArNs }.average().toLong()

    Log.i(TAG, "Compare avg: appDimens=${avgApp}ns conc1=${avgConcorrente1}ns conc2=${avgConcorrente2}ns " +
        "appDimensAr=${avgAppAr}ns conc1Ar=${avgConcorrente1Ar}ns")

    CompetitorBenchmarkResult(
        test1 = testResults[0],
        test2 = testResults[1],
        test3 = testResults[2],
        timeTest1 = timeResults[0],
        timeTest2 = timeResults[1],
        timeTest3 = timeResults[2],
        avgAppDimensNs = avgApp,
        avgConcorrente1Ns = avgConcorrente1,
        avgConcorrente2Ns = avgConcorrente2,
        avgAppDimensArNs = avgAppAr,
        avgConcorrente1ArNs = avgConcorrente1Ar,
        windowSw = 0,
        windowW = 0,
        windowH = 0,
        density = 0f,
    ).let { result ->
        val config = context.resources.configuration
        val dm = context.resources.displayMetrics
        val sw = config.smallestScreenWidthDp.takeIf { it > 0 }
            ?: minOf(config.screenWidthDp, config.screenHeightDp).coerceAtLeast(0)
        result.copy(
            windowSw = sw,
            windowW = config.screenWidthDp.coerceAtLeast(0),
            windowH = config.screenHeightDp.coerceAtLeast(0),
            density = dm.density,
        ).also { r ->
            Log.i(TAG, "Device: sw=${r.windowSw}dp w=${r.windowW}dp h=${r.windowH}dp density=${"%.2f".format(r.density)}")
        }
    }
}
