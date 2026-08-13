/**
 * @author Bodenberg
 *
 * EN Result data models for the on-device benchmark dashboard.
 *    Supports Calculation, Micro, Macro, and Comparison benchmarks.
 *
 * PT Modelos de dados de resultado do dashboard de benchmark no device.
 *    Suporta benchmarks de Cálculo, Micro, Macro e Comparativo.
 */
package com.example.app.compose.benchmark

// ═══════════════════════════════════════════════════════════════════════════════
// CALCULATION BENCHMARK
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Result of the Calculation benchmark: one or more 1dp resolution values
 *    in each family (sdp/hdp/wdp/sdpa), plus the combined average time.
 */
data class CalculationBenchmarkResult(
    val sdpPx: Float,
    val hdpPx: Float,
    val wdpPx: Float,
    val sdpaPx: Float,
    val avgNsPerOp: Long,
    val totalOps: Int,
    val wallMs: Long,
    val mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
)

// ═══════════════════════════════════════════════════════════════════════════════
// MICRO BENCHMARK
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Result of the Micro benchmark: per-family and combined average ns/op.
 */
data class MicroBenchmarkResult(
    val sdpAvgNs: Long,
    val hdpAvgNs: Long,
    val wdpAvgNs: Long,
    val sdpaAvgNs: Long,
    val combinedAvgNs: Long,
    val singleNoArAvgNs: Long,
    val singleWithArAvgNs: Long,
    val singleValue: Float,
    /** EN SCALED-only: average ns/op for the direct extension call `100.sdp(ctx)`. */
    val extSdpAvgNs: Long? = null,
    /** EN SCALED-only: average ns/op for the public API call `DimenSdp.sdp(ctx, 100)`. */
    val apiSdpAvgNs: Long? = null,
    val accumulatorChecksum: Float,
    val mode: BenchmarkCalculationMode = BenchmarkCalculationMode.SCALED,
)

// ═══════════════════════════════════════════════════════════════════════════════
// MACRO BENCHMARK
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Result of the Macro benchmark: scroll performance metrics.
 */
data class MacroBenchmarkResult(
    val avgFrameMs: Float,
    val p50FrameMs: Float,
    val p90FrameMs: Float,
    val p99FrameMs: Float,
    val totalFrames: Int,
    val droppedFrames: Int,
    val scrollDurationMs: Long,
    val notes: String
)

// ═══════════════════════════════════════════════════════════════════════════════
// COMPARISON BENCHMARK (3.1.8 vs 3.1.6)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Dp resolution values from both libraries for a single test run.
 *    Includes both sdp (no AR) and sdpa (with aspect ratio) values.
 * PT Valores de resolução dp de ambas as bibliotecas em um único teste.
 *    Inclui valores sdp (sem AR) e sdpa (com aspect ratio).
 */
data class DpResolution(
    // sdp — no AR
    val dp1Current: Float, val dp1Legacy: Float,
    val dp10Current: Float, val dp10Legacy: Float,
    val dp100Current: Float, val dp100Legacy: Float,
    // sdpa — with AR
    val dp1CurrentAr: Float, val dp1LegacyAr: Float,
    val dp10CurrentAr: Float, val dp10LegacyAr: Float,
    val dp100CurrentAr: Float, val dp100LegacyAr: Float,
)

/**
 * EN Time per single dp call from both libraries for a single test run.
 *    Includes both sdp (no AR) and sdpa (with aspect ratio) timing.
 * PT Tempo por chamada única de dp de ambas as bibliotecas em um único teste.
 *    Inclui tempo sdp (sem AR) e sdpa (com aspect ratio).
 */
data class SingleDpTiming(
    // sdp — no AR
    val currentNsPerDp: Long,
    val legacyNsPerDp: Long,
    // sdpa — with AR
    val currentArNsPerDp: Long,
    val legacyArNsPerDp: Long,
)

/**
 * EN Full comparison benchmark result (Dynamic 3.1.8 vs SDPS 3.1.6).
 *    2 independent test runs with resolution values and timing.
 * PT Resultado completo do benchmark comparativo (Dynamic 3.1.8 vs SDPS 3.1.6).
 *    2 testes independentes com valores de resolução e tempo.
 */
data class ComparisonBenchmarkResult(
    val test1: DpResolution,
    val test2: DpResolution,
    val timeTest1: SingleDpTiming,
    val timeTest2: SingleDpTiming,
    val avgCurrentNsPerDp: Long,
    val avgLegacyNsPerDp: Long,
    val avgCurrentArNsPerDp: Long,
    val avgLegacyArNsPerDp: Long,
    val windowSw: Int,
    val windowW: Int,
    val windowH: Int,
    val density: Float,
)

/**
 * EN Unified benchmark result container. Both fields are nullable since the user may
 *    choose to run only Micro or only Macro.
 * PT Contenedor unificado de resultados do benchmark. Ambos os campos são nullable.
 */
data class BenchmarkResult(
    val calculation: CalculationBenchmarkResult? = null,
    val micro: MicroBenchmarkResult? = null,
    val macro: MacroBenchmarkResult? = null,
    val comparison: ComparisonBenchmarkResult? = null
)

// ─── Formatting helpers ────────────────────────────────────────────────────────

/** EN Formats a nanosecond value into a readable string with appropriate unit. */
fun Long.formatNs(): String {
    return when {
        this < 1_000L     -> "$this ns"
        this < 1_000_000L -> "%.1f".format(this / 1_000.0) + " µs"
        else              -> "%.2f".format(this / 1_000_000.0) + " ms"
    }
}
