/**
 * @author Bodenberg
 *
 * EN CPU-bound microbenchmark runner for AppDimens dimension resolution calls.
 *    Runs entirely OFF the main thread (Dispatchers.Default).
 *    Uses warmup + measurement phases with an accumulator to prevent dead-code elimination.
 *    Each call type is timed INDIVIDUALLY to expose bypass vs cache path differences.
 *
 * PT Runner de microbenchmark vinculado à CPU para chamadas de resolução de dimensão AppDimens.
 *    Executa completamente FORA da thread principal (Dispatchers.Default).
 *    Usa fases de aquecimento + medição com acumulador para prevenir eliminação de código morto.
 *    Cada tipo de chamada é cronometrado INDIVIDUALMENTE para expor diferenças bypass vs cache.
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "APPDIMENS_MICRO"

/**
 * EN Warmup iterations — results are discarded. Primes the JIT compiler.
 * PT Iterações de aquecimento — resultados são descartados. Aquece o compilador JIT.
 */
private const val WARMUP_ITERATIONS = 10_000

/**
 * EN Measurement iterations per call type. 4 types × this = total ops measured.
 * PT Iterações de medição por tipo de chamada. 4 tipos × este valor = ops totais medidas.
 */
private const val MEASURE_ITERATIONS = 100_000

/**
 * EN Runs the full microbenchmark suite off the main thread and returns structured results.
 *    Sequence: warmup (discarded) → sdp timing → hdp timing → wdp timing → sdpa timing.
 *
 * PT Executa a suíte completa de microbenchmark fora da thread principal e retorna resultados estruturados.
 *    Sequência: aquecimento (descartado) → tempo sdp → tempo hdp → tempo wdp → tempo sdpa.
 *
 * @param context EN Android context needed for dimension resolution. PT Contexto Android para resolução de dimensão.
 * @param mode EN Calculation family (default scaled). PT Família de cálculo (padrão scaled).
 * @param onPhaseChange EN Callback invoked when phase transitions occur. PT Callback invocado nas transições de fase.
 */
suspend fun runMicroBenchmark(
    context: Context,
    mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
    onPhaseChange: (BenchmarkPhase) -> Unit
): MicroBenchmarkResult = withContext(Dispatchers.Default) {

    val ops = mode.ops()

    // ── WARMUP PHASE ──────────────────────────────────────────────────────────
    // EN Discard all results. This primes JIT, branch predictors, and cache lines.
    // PT Descarta todos os resultados. Aquece JIT, preditores de branch e linhas de cache.
    onPhaseChange(BenchmarkPhase.MICRO_WARMUP)

    var warmupAcc = 0f
    repeat(WARMUP_ITERATIONS) {
        warmupAcc += ops.sdp(context, 100)
        warmupAcc += ops.hdp(context, 50)
        warmupAcc += ops.wdp(context, 30)
        warmupAcc += ops.sdpa(context, 40)
    }
    // Consume accumulator to prevent dead-code elimination of warmup block
    Log.v(TAG, "Warmup complete (acc=$warmupAcc, ${WARMUP_ITERATIONS} iters discarded)")

    // ── MEASUREMENT PHASE ─────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.MICRO_RUN)

    val startWall = System.currentTimeMillis()

    // ── sdp (bypass path) ────────────────────────────────────────────────────
    // EN sw-qualifier call without AR — may bypass cache for cheap calc types (see DimenCache.getOrPut).
    // PT chamada sw sem AR — pode fazer bypass de cache para tipos baratos (ver DimenCache.getOrPut).
    var sdpAcc = 0f
    val sdpStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        sdpAcc += ops.sdp(context, 100)
    }
    val sdpElapsedNs = System.nanoTime() - sdpStartNs
    val sdpAvgNs = sdpElapsedNs / MEASURE_ITERATIONS

    // ── hdp (bypass path) ────────────────────────────────────────────────────
    var hdpAcc = 0f
    val hdpStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        hdpAcc += ops.hdp(context, 50)
    }
    val hdpElapsedNs = System.nanoTime() - hdpStartNs
    val hdpAvgNs = hdpElapsedNs / MEASURE_ITERATIONS

    // ── wdp (bypass path) ────────────────────────────────────────────────────
    var wdpAcc = 0f
    val wdpStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        wdpAcc += ops.wdp(context, 30)
    }
    val wdpElapsedNs = System.nanoTime() - wdpStartNs
    val wdpAvgNs = wdpElapsedNs / MEASURE_ITERATIONS

    // ── sdpa (cache path) ────────────────────────────────────────────────────
    // EN +AR smallest-width path → typically full cache / heavier work.
    // PT caminho sw+AR → tipicamente cache completo / trabalho mais pesado.
    var sdpaAcc = 0f
    val sdpaStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        sdpaAcc += ops.sdpa(context, 40)
    }
    val sdpaElapsedNs = System.nanoTime() - sdpaStartNs
    val sdpaAvgNs = sdpaElapsedNs / MEASURE_ITERATIONS

    val endWall = System.currentTimeMillis()
    val totalWallMs = endWall - startWall

    // ── Combined average across all 4 types ──────────────────────────────────
    val totalOps = MEASURE_ITERATIONS * 4
    val combinedAvgNs = (sdpElapsedNs + hdpElapsedNs + wdpElapsedNs + sdpaElapsedNs) / totalOps

    // ── Anti-dead-code accumulator checksum ──────────────────────────────────
    val checksum = sdpAcc + hdpAcc + wdpAcc + sdpaAcc

    // ── Logcat export ─────────────────────────────────────────────────────────
    Log.i(TAG, "╔══════════════════ MICRO BENCHMARK RESULT ══════════════════╗")
    Log.i(TAG, "║ Mode: ${mode.name}")
    Log.i(TAG, "║ Combined avg: ${combinedAvgNs.formatNs()}/op · Total ops: $totalOps")
    Log.i(TAG, "║ sdp  (bypass): ${sdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ hdp  (bypass): ${hdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ wdp  (bypass): ${wdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ sdpa (cache) : ${sdpaAvgNs.formatNs()}/op")
    Log.i(TAG, "║ Total wall time: ${totalWallMs}ms")
    Log.i(TAG, "║ Accumulator checksum: $checksum")
    Log.i(TAG, "╚════════════════════════════════════════════════════════════╝")

    MicroBenchmarkResult(
        avgNsPerOp      = combinedAvgNs,
        totalOps        = totalOps,
        totalTimeMs     = totalWallMs,
        sdpBypassAvgNs  = sdpAvgNs,
        hdpBypassAvgNs  = hdpAvgNs,
        wdpBypassAvgNs  = wdpAvgNs,
        sdpaCacheAvgNs  = sdpaAvgNs,
        accumulatorChecksum = checksum,
        mode            = mode,
    )
}
