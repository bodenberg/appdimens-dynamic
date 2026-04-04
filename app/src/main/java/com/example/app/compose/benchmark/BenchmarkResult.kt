/**
 * @author Bodenberg
 *
 * EN Data models for benchmark results.
 *    MicroBenchmarkResult: per-call-type averages from CPU-bound measurement.
 *    MacroBenchmarkResult: scroll timing and cost estimates from UI-bound measurement.
 * PT Modelos de dados para resultados de benchmark.
 *    MicroBenchmarkResult: médias por tipo de chamada da medição vinculada à CPU.
 *    MacroBenchmarkResult: tempo de rolagem e estimativas de custo da medição vinculada à UI.
 */
package com.example.app.compose.benchmark

/**
 * EN Results from the Microbenchmark runner.
 *    Each call type (sdp, hdp, wdp = bypass path; sdpa = cache path) is timed individually
 *    to expose the performance difference between the fast bypass and the cache lookup paths.
 * PT Resultados do runner de Microbenchmark.
 *    Cada tipo de chamada (sdp, hdp, wdp = caminho bypass; sdpa = caminho cache) é cronometrado
 *    individualmente para expor a diferença de performance entre o bypass rápido e o caminho de cache.
 *
 * @param avgNsPerOp    EN Combined average ns per operation across all call types. PT Média combinada ns/op.
 * @param totalOps      EN Total operations measured. PT Total de operações medidas.
 * @param totalTimeMs   EN Total elapsed measurement time in milliseconds. PT Tempo total de medição em ms.
 * @param sdpBypassAvgNs EN Average ns per sdp() call (bypass path). PT Média ns por chamada sdp() (bypass).
 * @param hdpBypassAvgNs EN Average ns per hdp() call (bypass path). PT Média ns por chamada hdp() (bypass).
 * @param wdpBypassAvgNs EN Average ns per wdp() call (bypass path). PT Média ns por chamada wdp() (bypass).
 * @param sdpaCacheAvgNs EN Average ns per sdpa() call (cache path).  PT Média ns por chamada sdpa() (cache).
 * @param accumulatorChecksum EN Accumulator value to prove results were consumed (anti-dead-code). PT Valor acumulador.
 */
data class MicroBenchmarkResult(
    val avgNsPerOp: Long,
    val totalOps: Int,
    val totalTimeMs: Long,
    val sdpBypassAvgNs: Long,
    val hdpBypassAvgNs: Long,
    val wdpBypassAvgNs: Long,
    val sdpaCacheAvgNs: Long,
    val accumulatorChecksum: Float,
    val mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
)

/**
 * EN Results from the Calculation Benchmark runner.
 *    Measures the average latency of mixed dimension resolutions (sdp, hdp, wdp, sdpa)
 *    in a single tight loop to simulate real-world usage patterns.
 * PT Resultados do runner de Benchmark de Cálculo.
 *    Mede a latência média de resoluções de dimensão mistas (sdp, hdp, wdp, sdpa)
 *    em um único loop para simular padrões de uso do mundo real.
 *
 * @param avgNsPerRes   EN Average nanoseconds per resolution call. PT Média de nanossegundos por chamada.
 * @param totalOps      EN Total operations (calls) measured. PT Total de operações (chamadas) medidas.
 * @param throughput    EN Formatted string showing the call-type mix. PT String formatada mostrando o mix.
 */
data class CalculationBenchmarkResult(
    val avgNsPerRes: Long,
    val totalOps: Int,
    val throughput: String,
    val mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
)

/**
 * EN Results from the Macrobenchmark runner.
 *    Measures real UI scroll performance across 1,000 items using wall-clock timing.
 *    Does NOT use measureNanoTime — uses currentTimeMillis start/end deltas.
 * PT Resultados do runner de Macrobenchmark.
 *    Mede a performance real de rolagem da UI em 1.000 itens usando tempo de relógio.
 *    NÃO usa measureNanoTime — usa deltas de start/end com currentTimeMillis.
 *
 * @param scrollDurationMs       EN Full scroll pass duration in ms. PT Duração da passagem de rolagem em ms.
 * @param itemsRendered          EN Number of items in the LazyColumn. PT Número de itens no LazyColumn.
 * @param estimatedCostPerItemUs EN Estimated rendering cost per item in µs. PT Custo estimado por item em µs.
 * @param estimatedFrames        EN Estimated frame count at 60fps. PT Contagem de frames estimada a 60fps.
 * @param notes                  EN Additional context or observations. PT Contexto ou observações adicionais.
 */
data class MacroBenchmarkResult(
    val scrollDurationMs: Long,
    val itemsRendered: Int,
    val estimatedCostPerItemUs: Float,
    val estimatedFrames: Int,
    val notes: String
)

/**
 * EN Unified benchmark result container. Both fields are nullable since the user may
 *    choose to run only Micro or only Macro.
 * PT Contêiner unificado de resultados de benchmark. Ambos os campos são nulos pois o usuário
 *    pode optar por executar apenas Micro ou apenas Macro.
 *
 * @param calculation EN Calculation benchmark result, or null if not run. PT Resultado do benchmark de cálculo, ou null.
 * @param micro EN Microbenchmark result, or null if not run. PT Resultado do microbenchmark, ou null.
 * @param macro EN Macrobenchmark result, or null if not run. PT Resultado do macrobenchmark, ou null.
 */
data class BenchmarkResult(
    val calculation: CalculationBenchmarkResult? = null,
    val micro: MicroBenchmarkResult? = null,
    val macro: MacroBenchmarkResult? = null
)

// ─── Formatting helpers ────────────────────────────────────────────────────────

/** EN Formats a nanosecond value into a readable string with appropriate unit. */
fun Long.formatNs(): String = when {
    this < 1_000L    -> "$this ns"
    this < 1_000_000L -> "${"%.1f".format(this / 1_000.0)} µs"
    else              -> "${"%.2f".format(this / 1_000_000.0)} ms"
}

/** EN Formats a float microsecond value with 2 decimal places. */
fun Float.formatUs(): String = "${"%.2f".format(this)} µs"
