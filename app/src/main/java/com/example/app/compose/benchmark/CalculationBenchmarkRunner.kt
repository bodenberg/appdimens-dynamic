/**
 * @author Bodenberg
 *
 * EN CPU-bound calculation benchmark runner for AppDimens.
 *    Measures mixed-type (bypass vs cache) resolution latency in a tight loop.
 *    Uses the specific logic and iteration count provided by the user.
 *
 * PT Runner de benchmark de cálculo vinculado à CPU para AppDimens.
 *    Mede a latência de resolução de tipos mistos (bypass vs cache) em um loop fechado.
 *    Usa a lógica específica e a contagem de iterações fornecidas pelo usuário.
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.util.Log
import com.appdimens.dynamic.code.DimenSdp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime

private const val TAG = "APPDIMENS_CALC"
private const val REPEAT_COUNT = 10_000
private const val CALLS_PER_BLOCK = 4

/**
 * EN Runs the calculation benchmark (mixed-path test) off the main thread.
 * PT Executa o benchmark de cálculo (teste de caminho misto) fora da thread principal.
 *
 * @param context EN Android context. PT Contexto Android.
 * @param onPhaseChange EN Callback for phase transitions. PT Callback para transições de fase.
 */
suspend fun runCalculationBenchmark(
    context: Context,
    onPhaseChange: (BenchmarkPhase) -> Unit
): CalculationBenchmarkResult = withContext(Dispatchers.Default) {

    // ── WARMUP ──────────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.CALC_WARMUP)
    
    // EN Brief JIT priming with 1/10th of iterations
    // PT Breve aquecimento do JIT com 1/10 das iterações
    repeat(1000) {
        DimenSdp.sdp(context, 100)
        DimenSdp.hdp(context, 50)
        DimenSdp.wdp(context, 30)
        DimenSdp.sdpa(context, 40)
    }
    delay(100) // EN Settling. PT Estabilização.

    // ── MEASUREMENT ─────────────────────────────────────────────────────────
    onPhaseChange(BenchmarkPhase.CALC_RUN)

    val totalNs = measureNanoTime {
        repeat(REPEAT_COUNT) {
            // NOTE: sdp/hdp/wdp without AR hit the FAST BYPASS in DimenCache.getOrPut()
            // (CalcType.SCALED, bit 63 == 0 → bypasses cache entirely ~2 ns raw math).
            // sdpa (with AR) uses the real cache lookup path (~5–35 ns).
            DimenSdp.sdp(context, 100)   // bypass (~2 ns)
            DimenSdp.hdp(context, 50)    // bypass (~2 ns)
            DimenSdp.wdp(context, 30)    // bypass (~2 ns)
            DimenSdp.sdpa(context, 40)   // cache  (~35 ns on warm cache)
        }
    }

    val totalOps = REPEAT_COUNT * CALLS_PER_BLOCK
    val avg = totalNs / totalOps
    
    val throughputStr = "sdp×3 bypass + sdpa×1 cached ($totalOps total)"
    
    // EN Logcat export exactly as requested
    // PT Exportação do Logcat exatamente como solicitado
    println("--- UI_BENCHMARK_RESULT: $avg ns ($throughputStr) ---")
    Log.i(TAG, "Calculation Result: $avg ns avg/resolution ($throughputStr)")

    CalculationBenchmarkResult(
        avgNsPerRes = avg,
        totalOps    = totalOps,
        throughput  = throughputStr
    )
}
