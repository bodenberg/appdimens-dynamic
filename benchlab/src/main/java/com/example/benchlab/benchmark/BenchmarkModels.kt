/**
 * @author Bodenberg
 *
 * EN Data models + phases for the 3-way competitor benchmark
 *    (AppDimens Dynamic 3.1.8 vs Concorrente 1 vs Concorrente 2).
 *    Each test run captures dp resolution values
 *    (1dp, 10dp, 100dp) and time per single dp call for all three libraries.
 *
 * PT Modelos de dados + fases do benchmark de 3 vias
 *    (AppDimens Dynamic 3.1.8 vs Concorrente 1 vs Concorrente 2).
 *    Cada teste captura valores de resolução dp (1dp, 10dp, 100dp) e
 *    tempo por chamada única de dp para as três bibliotecas.
 */
package com.example.benchlab.benchmark

/** EN Phases of the benchmark pipeline (3 test runs). PT Fases do pipeline do benchmark (3 testes). */
enum class BenchPhase {
    IDLE,
    WARMUP,
    TEST1,
    TEST2,
    TEST3,
    DONE;

    val displayLabel: String
        get() = when (this) {
            IDLE   -> "Idle — toque em Run"
            WARMUP -> "Aquecendo as 3 bibliotecas…"
            TEST1  -> "Teste 1/3 — medindo valores e tempos…"
            TEST2  -> "Teste 2/3 — medindo valores e tempos…"
            TEST3  -> "Teste 3/3 — medindo valores e tempos…"
            DONE   -> "Pronto"
        }

    val progressFraction: Float
        get() = when (this) {
            IDLE   -> 0f
            WARMUP -> 0.10f
            TEST1  -> 0.35f
            TEST2  -> 0.60f
            TEST3  -> 0.85f
            DONE   -> 1f
        }
}

/**
 * EN Dp resolution values from all three libraries for a single test run.
 *    Includes both sdp (no AR) and sdpa (with aspect ratio) values.
 * PT Valores de resolução dp de todas as três bibliotecas em um único teste.
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
 * EN Time per single dp call from all three libraries for a single test run.
 *    Includes both sdp (no AR) and sdpa (with aspect ratio) timing.
 * PT Tempo por chamada única de dp de todas as três bibliotecas em um único teste.
 *    Inclui tempo sdp (sem AR) e sdpa (com aspect ratio).
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
 * EN Full 3-way comparison benchmark result with 3 independent test runs.
 * PT Resultado completo do benchmark comparativo de 3 vias com 3 testes independentes.
 *
 * @param test1/test2/test3 EN Resolution results for each test run. PT Resultados de resolução de cada teste.
 * @param timeTest1/timeTest2/timeTest3 EN Timing results for each test run. PT Resultados de tempo de cada teste.
 * @param avgAppDimensNs EN Average ns per dp (sdp) across 3 tests. PT Média ns por dp (sdp) nos 3 testes.
 * @param avgConcorrente1Ns EN Average ns per dp (sdp) across 3 tests. PT Média ns por dp (sdp) nos 3 testes.
 * @param avgConcorrente2Ns EN Average ns per dp (sdp) across 3 tests. PT Média ns por dp (sdp) nos 3 testes.
 * @param avgAppDimensArNs EN Average ns per dp (sdpa/AR) across 3 tests. PT Média ns por dp (sdpa/AR) nos 3 testes.
 * @param avgConcorrente1ArNs EN Average ns per dp (sdpa/AR) across 3 tests. PT Média ns por dp (sdpa/AR) nos 3 testes.
 * @param windowSw/windowW/windowH EN Device window at capture time. PT Janela do device na captura.
 * @param density EN Display density at capture time. PT Densidade do display na captura.
 */
data class CompetitorBenchmarkResult(
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
    val windowSw: Int,
    val windowW: Int,
    val windowH: Int,
    val density: Float,
)

/**
 * EN Raw measurements captured by the composable Concorrente 2 probe (main thread).
 * PT Medições brutas capturadas pela sonda composable da Concorrente 2 (main thread).
 */
data class Concorrente2ProbeResult(
    val sdpAvgNs: Long,
    val dp1Px: Float,
    val dp10Px: Float,
    val dp100Px: Float,
    val checksum: Float,
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
