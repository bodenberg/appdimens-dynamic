/**
 * @author Bodenberg
 *
 * EN Data models + phases for the 3-way competitor benchmark
 *    (AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 vs Lib #2).
 *
 *    NEW methodology (headline):
 *    - Benchmark A (Compose API): all three libraries measured together
 *      on the main thread inside composition, identical warm-up,
 *      identical operation count, order rotation, anti-DCE checksum,
 *      chunked across frames so the UI never freezes.
 *    - Benchmark B (Engine): Dynamic vs SDPS only, off main thread
 *      (Dispatchers.Default). Lib #2 has no non-Compose API.
 *    Headline number is the MEDIAN of N samples (steady-state ns/op),
 *    with min/P90/max as spread. All timing stored as Double ns.
 *
 *    LEGACY methodology (kept for continuity with previously published
 *    results): 3 test runs (T1/T2/T3) measuring dp resolution values and
 *    time per single call (Long ns, average of 3 runs) — original code.
 *
 * PT Modelos de dados + fases do benchmark de 3 vias
 *    (AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 vs Lib #2).
 *
 *    Metodologia NOVA (principal):
 *    - Benchmark A (API Compose): as três bibliotecas medidas juntas
 *      na main thread dentro da composição, warm-up idêntico, mesma
 *      contagem de operações, rotação de ordem, checksum anti-DCE,
 *      fatiado entre frames para a UI nunca congelar.
 *    - Benchmark B (Motor): Dynamic vs SDPS apenas, fora da main thread
 *      (Dispatchers.Default). A Lib #2 não possui API não-Compose.
 *    O número principal é a MEDIANA de N amostras (ns/op steady-state),
 *    com min/P90/max como dispersão. Todo tempo armazenado como Double ns.
 *
 *    Metodologia LEGADA (mantida por continuidade com resultados já
 *    publicados): 3 execuções de teste (T1/T2/T3) medindo valores de
 *    resolução dp e tempo por chamada única (Long ns, média de 3) — código original.
 */
package com.example.benchlab.benchmark

import kotlin.math.roundToInt

/** EN Phases of the benchmark pipeline. PT Fases do pipeline do benchmark. */
enum class BenchPhase {
    IDLE,
    WARMUP,
    CORE,
    TEST1,
    TEST2,
    TEST3,
    DONE;

    val displayLabel: String
        get() = when (this) {
            IDLE   -> "Idle — toque em Run"
            WARMUP -> "Benchmark A — medindo API Compose (main thread)…"
            CORE   -> "Benchmark B — medindo o motor (Dispatchers.Default)…"
            TEST1  -> "Testes legados 1/3…"
            TEST2  -> "Testes legados 2/3…"
            TEST3  -> "Testes legados 3/3…"
            DONE   -> "Pronto"
        }

    val progressFraction: Float
        get() = when (this) {
            IDLE   -> 0f
            WARMUP -> 0.25f
            CORE   -> 0.45f
            TEST1  -> 0.62f
            TEST2  -> 0.75f
            TEST3  -> 0.88f
            DONE   -> 1f
        }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NEW METHODOLOGY MODELS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Spread statistics of one workload for one library, in ns/op.
 *    Median is the headline; min/P90/max describe the spread so a
 *    single scheduler/GC interruption does not dominate the report.
 * PT Estatísticas de dispersão de um workload para uma biblioteca, em ns/op.
 *    A mediana é o número principal; min/P90/max descrevem a dispersão para
 *    que uma única interrupção do scheduler/GC não domine o relatório.
 */
data class TimingStats(
    val medianNs: Double,
    val minNs: Double,
    val p90Ns: Double,
    val maxNs: Double,
)

/**
 * EN Per-library timing for both workloads plus anti-DCE checksums.
 * PT Tempo por biblioteca para os dois workloads + checksums anti-DCE.
 */
data class LibraryTiming(
    val constant1dp: TimingStats,
    val mixedValues: TimingStats,
    val constantChecksum: Float,
    val mixedChecksum: Float,
)

/**
 * EN Benchmark A — Compose API, main thread, all three libraries in the
 *    same composable with identical warm-up/counts and rotated order.
 * PT Benchmark A — API Compose, main thread, as três bibliotecas no mesmo
 *    composable com warm-up/contagens idênticos e ordem rotacionada.
 */
data class ComposeApiResult(
    val dynamic: LibraryTiming,
    val sdps: LibraryTiming,
    val chaintech: LibraryTiming,
)

/**
 * EN Benchmark B — Engine, off main thread (Dispatchers.Default),
 *    Dynamic vs SDPS only (Lib #2 has no non-Compose API → N/A).
 * PT Benchmark B — Motor, fora da main thread (Dispatchers.Default),
 *    apenas Dynamic vs SDPS (Lib #2 não possui API não-Compose → N/A).
 */
data class CoreEngineResult(
    val dynamic: LibraryTiming,
    val sdps: LibraryTiming,
)

/**
 * EN Raw result captured by the composable 3-way probe (main thread).
 * PT Resultado bruto capturado pela sonda composable 3-vias (main thread).
 */
data class ComposeProbeResult(
    val composeApi: ComposeApiResult,
)

/**
 * EN Result of the off-main engine benchmark.
 * PT Resultado do benchmark do motor off-main.
 */
data class CoreBenchmarkResult(
    val coreEngine: CoreEngineResult,
)

// ═══════════════════════════════════════════════════════════════════════════════
// LEGACY METHODOLOGY MODELS (original T1/T2/T3 tests, kept for continuity)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Dp resolution values from all three libraries for a single legacy test run.
 *    Includes both sdp (no AR) and sdpa (with aspect ratio) values.
 * PT Valores de resolução dp de todas as três bibliotecas em um teste legado.
 *    Inclui valores sdp (sem AR) e sdpa (com aspect ratio).
 */
data class DpResolution3(
    // sdp — no AR
    val dp1AppDimens: Float, val dp1Concorrente1: Float, val dp1Concorrente2: Float,
    val dp10AppDimens: Float, val dp10Concorrente1: Float, val dp10Concorrente2: Float,
    val dp100AppDimens: Float, val dp100Concorrente1: Float, val dp100Concorrente2: Float,
    // sdpa — with AR (Concorrente 2 doesn't support sdpa)
    val dp1AppDimensAr: Float, val dp1Concorrente1Ar: Float,
    val dp10AppDimensAr: Float, val dp10Concorrente1Ar: Float,
    val dp100AppDimensAr: Float, val dp100Concorrente1Ar: Float,
)

/**
 * EN Time per single dp call from all three libraries for a single legacy test run.
 * PT Tempo por chamada única de dp das três bibliotecas em um teste legado.
 */
data class SingleDpTiming3(
    // sdp — no AR
    val appDimensNs: Long,
    val concorrente1Ns: Long,
    val concorrente2Ns: Long,
    // sdpa — with AR (Concorrente 2 doesn't have sdpa)
    val appDimensArNs: Long,
    val concorrente1ArNs: Long,
)

/**
 * EN Full legacy 3-run result (original methodology, Long ns averages).
 * PT Resultado legado completo de 3 execuções (metodologia original, médias Long ns).
 */
data class LegacyTestResult(
    val test1: DpResolution3,
    val test2: DpResolution3,
    val test3: DpResolution3,
    val timeTest1: SingleDpTiming3,
    val timeTest2: SingleDpTiming3,
    val timeTest3: SingleDpTiming3,
    val avgAppDimensNs: Long,
    val avgConcorrente1Ns: Long,
    val avgConcorrente2Ns: Long,
    val avgAppDimensArNs: Long,
    val avgConcorrente1ArNs: Long,
)

/**
 * EN Raw measurements captured by the legacy composable Concorrente 2 probe (main thread).
 * PT Medições brutas capturadas pela sonda composable legada da Concorrente 2 (main thread).
 */
data class Concorrente2ProbeResult(
    val sdpAvgNs: Long,
    val dp1Px: Float,
    val dp10Px: Float,
    val dp100Px: Float,
    val checksum: Float,
)

// ═══════════════════════════════════════════════════════════════════════════════
// AGGREGATE RESULT
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Full benchmark result: new methodology (Compose API + Engine) +
 *    legacy tests (T1/T2/T3) + device info.
 * PT Resultado completo: metodologia nova (API Compose + Motor) +
 *    testes legados (T1/T2/T3) + informações do device.
 */
data class CompetitorBenchmarkResult(
    val composeApi: ComposeApiResult,
    val coreEngine: CoreEngineResult,
    val legacy: LegacyTestResult,
    val windowSw: Int,
    val windowW: Int,
    val windowH: Int,
    val density: Float,
)

// ─── Statistics helpers ───────────────────────────────────────────────────────

/**
 * EN Computes median/min/P90/max as ns/op from raw per-sample ns timings.
 *    Each sample covers [opsPerSample] iterations, so every statistic is
 *    divided by it (a sample of 50k iterations totals ~36ns x 50k = ~1.8ms).
 *    Median is robust against single scheduler/GC interruptions.
 * PT Calcula mediana/min/P90/max em ns/op a partir dos ns brutos por amostra.
 *    Cada amostra cobre [opsPerSample] iterações, então toda estatística é
 *    dividida por ele (uma amostra de 50k iterações totaliza ~36ns x 50k = ~1.8ms).
 *    A mediana é robusta contra interrupções isoladas do scheduler/GC.
 */
internal fun statsOf(samples: LongArray, opsPerSample: Long): TimingStats {
    val sorted = samples.sortedArray()
    val n = sorted.size
    val mid = n / 2
    val median = if (n % 2 == 1) sorted[mid].toDouble() else (sorted[mid - 1] + sorted[mid]) / 2.0
    return TimingStats(
        medianNs = median / opsPerSample,
        minNs = sorted.first().toDouble() / opsPerSample,
        p90Ns = sorted[((n - 1) * 0.9).roundToInt()].toDouble() / opsPerSample,
        maxNs = sorted.last().toDouble() / opsPerSample,
    )
}

// ─── Formatting helpers ────────────────────────────────────────────────────────

/** EN Formats a Double ns/op value into a readable string with appropriate unit. */
fun Double.formatNs(): String {
    return when {
        this < 1_000.0   -> "%.2f ns".format(this)
        this < 1_000_000.0 -> "%.1f µs".format(this / 1_000.0)
        else              -> "%.2f ms".format(this / 1_000_000.0)
    }
}

/** EN Formats a Long ns value into a readable string with appropriate unit (legacy). */
fun Long.formatNs(): String {
    return when {
        this < 1_000L     -> "$this ns"
        this < 1_000_000L -> "%.1f".format(this / 1_000.0) + " µs"
        else              -> "%.2f".format(this / 1_000_000.0) + " ms"
    }
}