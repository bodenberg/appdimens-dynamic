/**
 * @author Bodenberg
 *
 * EN Off-main benchmark core for the 3-way comparison.
 *    Runs 3 independent test passes, each measuring:
 *    - Dp resolution values for 1dp, 10dp, 100dp in all three libraries
 *    - Time per single dp call in AppDimens + SDPS (Chaintech measured by probe)
 *
 * PT Núcleo off-main do benchmark de 3 vias.
 *    Executa 3 passes de teste independentes, cada um medindo:
 *    - Valores de resolução dp para 1dp, 10dp, 100dp nas três bibliotecas
 *    - Tempo por chamada única de dp em AppDimens + SDPS (Chaintech medido pela sonda)
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
 * @param chaintechResult EN Pre-measured Chaintech data from the composable probe. PT Dados pré-mensurados da Chaintech da sonda composable.
 * @param onPhaseChange EN Callback for phase transitions. PT Callback para transições de fase.
 */
suspend fun runCompetitorBenchmark(
    context: Context,
    chaintechResult: ChaintechProbeResult,
    onPhaseChange: (BenchPhase) -> Unit
): CompetitorBenchmarkResult = withContext(Dispatchers.Default) {

    val app = com.appdimens.dynamic.code.DimenSdp
    val legacy = com.appdimens.sdps.code.DimenSdp

    // ── WARMUP ──────────────────────────────────────────────────────────────
    onPhaseChange(BenchPhase.WARMUP)
    repeat(WARMUP_COUNT) {
        for (dp in DP_VALUES) {
            app.sdp(context, dp); legacy.sdp(context, dp)
            app.hdp(context, dp); legacy.hdp(context, dp)
            app.wdp(context, dp); legacy.wdp(context, dp)
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
        val dp1Leg  = legacy.sdp(context, 1)
        val dp10App = app.sdp(context, 10)
        val dp10Leg = legacy.sdp(context, 10)
        val dp100App = app.sdp(context, 100)
        val dp100Leg = legacy.sdp(context, 100)

        // ── Dp resolution values: sdpa (with AR) ────────────────────────
        val dp1AppAr  = app.sdpa(context, 1)
        val dp1LegAr  = legacy.sdpa(context, 1)
        val dp10AppAr = app.sdpa(context, 10)
        val dp10LegAr = legacy.sdpa(context, 10)
        val dp100AppAr = app.sdpa(context, 100)
        val dp100LegAr = legacy.sdpa(context, 100)

        testResults += DpResolution3(
            dp1AppDimens = dp1App, dp1Sdps = dp1Leg, dp1Chaintech = chaintechResult.dp1Px,
            dp10AppDimens = dp10App, dp10Sdps = dp10Leg, dp10Chaintech = chaintechResult.dp10Px,
            dp100AppDimens = dp100App, dp100Sdps = dp100Leg, dp100Chaintech = chaintechResult.dp100Px,
            dp1AppDimensAr = dp1AppAr, dp1SdpsAr = dp1LegAr,
            dp10AppDimensAr = dp10AppAr, dp10SdpsAr = dp10LegAr,
            dp100AppDimensAr = dp100AppAr, dp100SdpsAr = dp100LegAr,
        )

        // ── Time per single dp call: sdp (no AR) ────────────────────────
        val appSingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { app.sdp(context, 1) }
        } / MEASURE_COUNT

        val sdpsSingleNs = measureNanoTime {
            repeat(MEASURE_COUNT) { legacy.sdp(context, 1) }
        } / MEASURE_COUNT

        // ── Time per single dp call: sdpa (with AR) ─────────────────────
        val appSingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { app.sdpa(context, 1) }
        } / MEASURE_COUNT

        val sdpsSingleArNs = measureNanoTime {
            repeat(MEASURE_COUNT) { legacy.sdpa(context, 1) }
        } / MEASURE_COUNT

        // Chaintech time is fixed from the probe (already measured once)
        timeResults += SingleDpTiming3(
            appDimensNs = appSingleNs,
            sdpsNs = sdpsSingleNs,
            chaintechNs = chaintechResult.sdpAvgNs,
            appDimensArNs = appSingleArNs,
            sdpsArNs = sdpsSingleArNs,
        )

        Log.i(TAG, "Test $run: dp1=app=$dp1App sdps=$dp1Leg chain=${chaintechResult.dp1Px} " +
            "dp10=app=$dp10App sdps=$dp10Leg chain=${chaintechResult.dp10Px} " +
            "dp100=app=$dp100App sdps=$dp100Leg chain=${chaintechResult.dp100Px} " +
            "ar: dp1=app=$dp1AppAr sdps=$dp1LegAr dp10=app=$dp10AppAr sdps=$dp10LegAr " +
            "dp100=app=$dp100AppAr sdps=$dp100LegAr " +
            "time: app=${appSingleNs}ns sdps=${sdpsSingleNs}ns chain=${chaintechResult.sdpAvgNs}ns " +
            "ar: app=${appSingleArNs}ns sdps=${sdpsSingleArNs}ns")
    }

    val avgApp = timeResults.map { it.appDimensNs }.average().toLong()
    val avgSdps = timeResults.map { it.sdpsNs }.average().toLong()
    val avgChain = timeResults.map { it.chaintechNs }.average().toLong()
    val avgAppAr = timeResults.map { it.appDimensArNs }.average().toLong()
    val avgSdpsAr = timeResults.map { it.sdpsArNs }.average().toLong()

    Log.i(TAG, "Compare avg: appDimens=${avgApp}ns sdps=${avgSdps}ns chaintech=${avgChain}ns " +
        "appDimensAr=${avgAppAr}ns sdpsAr=${avgSdpsAr}ns")

    CompetitorBenchmarkResult(
        test1 = testResults[0],
        test2 = testResults[1],
        test3 = testResults[2],
        timeTest1 = timeResults[0],
        timeTest2 = timeResults[1],
        timeTest3 = timeResults[2],
        avgAppDimensNs = avgApp,
        avgSdpsNs = avgSdps,
        avgChaintechNs = avgChain,
        avgAppDimensArNs = avgAppAr,
        avgSdpsArNs = avgSdpsAr,
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
