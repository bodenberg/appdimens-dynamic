/**
 * @author Bodenberg
 *
 * EN Off-main benchmark core (Benchmark B — Engine, Dispatchers.Default).
 *    Compares AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 only: Lib #2 has
 *    no non-Compose API (N/A outside composition), so it is excluded
 *    here instead of being measured under a different methodology.
 *    Same methodology as the Compose probe: identical warm-up, 9 samples,
 *    50,000 iterations per sample, order rotation, anti-DCE checksums,
 *    constant 1dp + mixed-value workloads, medians as headline.
 *    Also captures sdpa (aspect ratio) resolution values for both libs.
 *
 * PT Núcleo off-main do benchmark (Benchmark B — Motor, Dispatchers.Default).
 *    Compara AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 apenas: a Lib #2 não
 *    possui API não-Compose (N/A fora da composição), então é excluída aqui
 *    em vez de ser medida com metodologia diferente.
 *    Mesma metodologia da sonda Compose: warm-up idêntico, 9 amostras,
 *    50.000 iterações por amostra, rotação de ordem, checksums anti-DCE,
 *    workloads 1dp constante + valores mistos, medianas como principal.
 *    Também captura valores de resolução sdpa (aspect ratio) das duas libs.
 */
package com.example.benchlab.benchmark

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "BENCHLAB"

/**
 * EN Runs the off-main engine benchmark (Dynamic vs SDPS).
 * PT Executa o benchmark do motor off-main (Dynamic vs SDPS).
 *
 * @param context EN Android context. PT Contexto Android.
 * @param onPhaseChange EN Callback for phase transitions. PT Callback para transições de fase.
 */
suspend fun runCoreEngineBenchmark(
    context: Context,
    onPhaseChange: (BenchPhase) -> Unit,
): CoreBenchmarkResult = withContext(Dispatchers.Default) {

    onPhaseChange(BenchPhase.CORE)

    val dynamic = com.appdimens.dynamic.code.DimenSdp
    val sdps = com.appdimens.sdps.code.DimenSdp

    // ── Identical warm-up ──
    var warmAcc = 0f
    repeat(BENCH_WARMUP_COUNT) {
        warmAcc += dynamic.sdp(context, 1)
        warmAcc += sdps.sdp(context, 1)
    }

    val dConst = LongArray(BENCH_SAMPLE_COUNT)
    val sConst = LongArray(BENCH_SAMPLE_COUNT)
    val dMixed = LongArray(BENCH_SAMPLE_COUNT)
    val sMixed = LongArray(BENCH_SAMPLE_COUNT)

    var dConstAcc = warmAcc
    var sConstAcc = warmAcc
    var dMixedAcc = warmAcc
    var sMixedAcc = warmAcc

    // ── Workload 1: constant 1dp, order rotates every sample ──
    for (sample in 0 until BENCH_SAMPLE_COUNT) {
        if (sample % 2 == 0) {
            val t1 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { dConstAcc += dynamic.sdp(context, 1) }
            dConst[sample] = System.nanoTime() - t1

            val t2 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { sConstAcc += sdps.sdp(context, 1) }
            sConst[sample] = System.nanoTime() - t2
        } else {
            val t1 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { sConstAcc += sdps.sdp(context, 1) }
            sConst[sample] = System.nanoTime() - t1

            val t2 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { dConstAcc += dynamic.sdp(context, 1) }
            dConst[sample] = System.nanoTime() - t2
        }
    }

    // ── Workload 2: mixed values, same rotation ──
    for (sample in 0 until BENCH_SAMPLE_COUNT) {
        if (sample % 2 == 0) {
            val t1 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { i ->
                dMixedAcc += dynamic.sdp(context, BENCH_MIXED_VALUES[i % BENCH_MIXED_VALUES.size])
            }
            dMixed[sample] = System.nanoTime() - t1

            val t2 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { i ->
                sMixedAcc += sdps.sdp(context, BENCH_MIXED_VALUES[i % BENCH_MIXED_VALUES.size])
            }
            sMixed[sample] = System.nanoTime() - t2
        } else {
            val t1 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { i ->
                sMixedAcc += sdps.sdp(context, BENCH_MIXED_VALUES[i % BENCH_MIXED_VALUES.size])
            }
            sMixed[sample] = System.nanoTime() - t1

            val t2 = System.nanoTime()
            repeat(BENCH_MEASURE_COUNT) { i ->
                dMixedAcc += dynamic.sdp(context, BENCH_MIXED_VALUES[i % BENCH_MIXED_VALUES.size])
            }
            dMixed[sample] = System.nanoTime() - t2
        }
    }

    // ── sdpa resolution values (AR — not available in Lib #2) ──
    Log.i(TAG, "Engine: dynamic.const=" + statsOf(dConst, BENCH_MEASURE_COUNT.toLong()).medianNs +
        " sdps.const=" + statsOf(sConst, BENCH_MEASURE_COUNT.toLong()).medianNs +
        " dynamic.mixed=" + statsOf(dMixed, BENCH_MEASURE_COUNT.toLong()).medianNs +
        " sdps.mixed=" + statsOf(sMixed, BENCH_MEASURE_COUNT.toLong()).medianNs)

    CoreBenchmarkResult(
        coreEngine = CoreEngineResult(
            dynamic = LibraryTiming(
                constant1dp = statsOf(dConst, BENCH_MEASURE_COUNT.toLong()),
                mixedValues = statsOf(dMixed, BENCH_MEASURE_COUNT.toLong()),
                constantChecksum = dConstAcc + warmAcc,
                mixedChecksum = dMixedAcc + warmAcc,
            ),
            sdps = LibraryTiming(
                constant1dp = statsOf(sConst, BENCH_MEASURE_COUNT.toLong()),
                mixedValues = statsOf(sMixed, BENCH_MEASURE_COUNT.toLong()),
                constantChecksum = sConstAcc + warmAcc,
                mixedChecksum = sMixedAcc + warmAcc,
            ),
        ),
    )
}

/**
 * EN Combines the Compose probe result with the engine result, the legacy
 *    T1/T2/T3 tests and device info.
 * PT Combina o resultado da sonda Compose com o do motor, os testes legados
 *    T1/T2/T3 e as informações do device.
 */
fun assembleResult(
    probe: ComposeProbeResult,
    core: CoreBenchmarkResult,
    legacy: LegacyTestResult,
    context: Context,
): CompetitorBenchmarkResult {
    val config = context.resources.configuration
    val dm = context.resources.displayMetrics
    val sw = config.smallestScreenWidthDp.takeIf { it > 0 }
        ?: minOf(config.screenWidthDp, config.screenHeightDp).coerceAtLeast(0)

    return CompetitorBenchmarkResult(
        composeApi = probe.composeApi,
        coreEngine = core.coreEngine,
        legacy = legacy,
        windowSw = sw,
        windowW = config.screenWidthDp.coerceAtLeast(0),
        windowH = config.screenHeightDp.coerceAtLeast(0),
        density = dm.density,
    ).also { r ->
        Log.i(TAG, "Device: sw=${r.windowSw}dp w=${r.windowW}dp h=${r.windowH}dp density=${"%.2f".format(r.density)}")
    }
}