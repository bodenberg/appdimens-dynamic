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
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.sdp
import com.appdimens.dynamic.code.auto.DimenAutoDp
import com.appdimens.dynamic.code.auto.toDynamicAutoPx
import com.appdimens.dynamic.code.density.DimenDensityDp
import com.appdimens.dynamic.code.density.toDynamicDensityPx
import com.appdimens.dynamic.code.diagonal.DimenDiagonalDp
import com.appdimens.dynamic.code.diagonal.toDynamicDiagonalPx
import com.appdimens.dynamic.code.fill.DimenFillDp
import com.appdimens.dynamic.code.fill.toDynamicFillPx
import com.appdimens.dynamic.code.fit.DimenFitDp
import com.appdimens.dynamic.code.fit.toDynamicFitPx
import com.appdimens.dynamic.code.fluid.DimenFluidDp
import com.appdimens.dynamic.code.fluid.toDynamicFluidPx
import com.appdimens.dynamic.code.interpolated.DimenInterpolatedDp
import com.appdimens.dynamic.code.interpolated.toDynamicInterpolatedPx
import com.appdimens.dynamic.code.logarithmic.DimenLogarithmicDp
import com.appdimens.dynamic.code.logarithmic.toDynamicLogarithmicPx
import com.appdimens.dynamic.code.percent.DimenPercentDp
import com.appdimens.dynamic.code.percent.toDynamicPercentPx
import com.appdimens.dynamic.code.perimeter.DimenPerimeterDp
import com.appdimens.dynamic.code.perimeter.toDynamicPerimeterPx
import com.appdimens.dynamic.code.power.DimenPowerDp
import com.appdimens.dynamic.code.power.toDynamicPowerPx
import com.appdimens.dynamic.code.toDynamicScaledPx
import com.appdimens.dynamic.core.DimenCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "APPDIMENS_MICRO"

/**
 * EN On-device bit-exact parity probe: fast-lane entries vs the untouched legacy
 *    toDynamic*Px call path. Any raw-bits mismatch is logged; total is reported.
 * PT Probe de paridade bit-exata no device: entries fast-lane vs o caminho legado
 *    toDynamic*Px intacto. Qualquer divergência de raw bits é logada; total é reportado.
 */
private fun runParityProbe(context: Context) {
    val bases = intArrayOf(1, 2, 4, 8, 16, 32, 64, 128, 256, 512)
    val famTotal = linkedMapOf<String, Int>()
    val famBad = linkedMapOf<String, Int>()
    fun probe(label: String, fast: Float, legacy: Float) {
        val fam = label.substringBefore('-')
        famTotal[fam] = (famTotal[fam] ?: 0) + 1
        if (fast.toRawBits() != legacy.toRawBits()) {
            famBad[fam] = (famBad[fam] ?: 0) + 1
        }
    }
    for (b in bases) {
        val f = b.toFloat()
        probe("scaled-sdp-$b", DimenSdp.sdp(context, b), f.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH))
        probe("scaled-hdp-$b", DimenSdp.hdp(context, b), f.toDynamicScaledPx(context, DpQualifier.HEIGHT))
        probe("scaled-wdp-$b", DimenSdp.wdp(context, b), f.toDynamicScaledPx(context, DpQualifier.WIDTH))
        probe("scaled-sdpa-$b", DimenSdp.sdpa(context, b), f.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("density-sdp-$b", DimenDensityDp.dsdp(context, b), f.toDynamicDensityPx(context, DpQualifier.SMALL_WIDTH))
        probe("density-hdp-$b", DimenDensityDp.dhdp(context, b), f.toDynamicDensityPx(context, DpQualifier.HEIGHT))
        probe("density-wdp-$b", DimenDensityDp.dwdp(context, b), f.toDynamicDensityPx(context, DpQualifier.WIDTH))
        probe("density-sdpa-$b", DimenDensityDp.dsdpa(context, b), f.toDynamicDensityPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("fill-sdp-$b", DimenFillDp.flsdp(context, b), f.toDynamicFillPx(context, DpQualifier.SMALL_WIDTH))
        probe("fill-hdp-$b", DimenFillDp.flhdp(context, b), f.toDynamicFillPx(context, DpQualifier.HEIGHT))
        probe("fill-wdp-$b", DimenFillDp.flwdp(context, b), f.toDynamicFillPx(context, DpQualifier.WIDTH))
        probe("fill-sdpa-$b", DimenFillDp.flsdpa(context, b), f.toDynamicFillPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("fit-sdp-$b", DimenFitDp.ftsdp(context, b), f.toDynamicFitPx(context, DpQualifier.SMALL_WIDTH))
        probe("fit-hdp-$b", DimenFitDp.fthdp(context, b), f.toDynamicFitPx(context, DpQualifier.HEIGHT))
        probe("fit-wdp-$b", DimenFitDp.ftwdp(context, b), f.toDynamicFitPx(context, DpQualifier.WIDTH))
        probe("fit-sdpa-$b", DimenFitDp.ftsdpa(context, b), f.toDynamicFitPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("fluid-sdp-$b", DimenFluidDp.fsdp(context, b), f.toDynamicFluidPx(context, DpQualifier.SMALL_WIDTH))
        probe("fluid-hdp-$b", DimenFluidDp.fhdp(context, b), f.toDynamicFluidPx(context, DpQualifier.HEIGHT))
        probe("fluid-wdp-$b", DimenFluidDp.fwdp(context, b), f.toDynamicFluidPx(context, DpQualifier.WIDTH))
        probe("fluid-sdpa-$b", DimenFluidDp.fsdpa(context, b), f.toDynamicFluidPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("diagonal-sdp-$b", DimenDiagonalDp.dgsdp(context, b), f.toDynamicDiagonalPx(context, DpQualifier.SMALL_WIDTH))
        probe("diagonal-hdp-$b", DimenDiagonalDp.dghdp(context, b), f.toDynamicDiagonalPx(context, DpQualifier.HEIGHT))
        probe("diagonal-wdp-$b", DimenDiagonalDp.dgwdp(context, b), f.toDynamicDiagonalPx(context, DpQualifier.WIDTH))
        probe("diagonal-sdpa-$b", DimenDiagonalDp.dgsdpa(context, b), f.toDynamicDiagonalPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("interpolated-sdp-$b", DimenInterpolatedDp.isdp(context, b), f.toDynamicInterpolatedPx(context, DpQualifier.SMALL_WIDTH))
        probe("interpolated-hdp-$b", DimenInterpolatedDp.ihdp(context, b), f.toDynamicInterpolatedPx(context, DpQualifier.HEIGHT))
        probe("interpolated-wdp-$b", DimenInterpolatedDp.iwdp(context, b), f.toDynamicInterpolatedPx(context, DpQualifier.WIDTH))
        probe("interpolated-sdpa-$b", DimenInterpolatedDp.isdpa(context, b), f.toDynamicInterpolatedPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("logarithmic-sdp-$b", DimenLogarithmicDp.logsdp(context, b), f.toDynamicLogarithmicPx(context, DpQualifier.SMALL_WIDTH))
        probe("logarithmic-hdp-$b", DimenLogarithmicDp.loghdp(context, b), f.toDynamicLogarithmicPx(context, DpQualifier.HEIGHT))
        probe("logarithmic-wdp-$b", DimenLogarithmicDp.logwdp(context, b), f.toDynamicLogarithmicPx(context, DpQualifier.WIDTH))
        probe("logarithmic-sdpa-$b", DimenLogarithmicDp.logsdpa(context, b), f.toDynamicLogarithmicPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("percent-sdp-$b", DimenPercentDp.psdp(context, b), f.toDynamicPercentPx(context, DpQualifier.SMALL_WIDTH))
        probe("percent-hdp-$b", DimenPercentDp.phdp(context, b), f.toDynamicPercentPx(context, DpQualifier.HEIGHT))
        probe("percent-wdp-$b", DimenPercentDp.pwdp(context, b), f.toDynamicPercentPx(context, DpQualifier.WIDTH))
        probe("percent-sdpa-$b", DimenPercentDp.psdpa(context, b), f.toDynamicPercentPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("perimeter-sdp-$b", DimenPerimeterDp.prsdp(context, b), f.toDynamicPerimeterPx(context, DpQualifier.SMALL_WIDTH))
        probe("perimeter-hdp-$b", DimenPerimeterDp.prhdp(context, b), f.toDynamicPerimeterPx(context, DpQualifier.HEIGHT))
        probe("perimeter-wdp-$b", DimenPerimeterDp.prwdp(context, b), f.toDynamicPerimeterPx(context, DpQualifier.WIDTH))
        probe("perimeter-sdpa-$b", DimenPerimeterDp.prsdpa(context, b), f.toDynamicPerimeterPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("power-sdp-$b", DimenPowerDp.pwsdp(context, b), f.toDynamicPowerPx(context, DpQualifier.SMALL_WIDTH))
        probe("power-hdp-$b", DimenPowerDp.pwhdp(context, b), f.toDynamicPowerPx(context, DpQualifier.HEIGHT))
        probe("power-wdp-$b", DimenPowerDp.pwwdp(context, b), f.toDynamicPowerPx(context, DpQualifier.WIDTH))
        probe("power-sdpa-$b", DimenPowerDp.pwsdpa(context, b), f.toDynamicPowerPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
        probe("auto-sdp-$b", DimenAutoDp.asdp(context, b), f.toDynamicAutoPx(context, DpQualifier.SMALL_WIDTH))
        probe("auto-hdp-$b", DimenAutoDp.ahdp(context, b), f.toDynamicAutoPx(context, DpQualifier.HEIGHT))
        probe("auto-wdp-$b", DimenAutoDp.awdp(context, b), f.toDynamicAutoPx(context, DpQualifier.WIDTH))
        probe("auto-sdpa-$b", DimenAutoDp.asdpa(context, b), f.toDynamicAutoPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true))
    }
    for (fam in famTotal.keys) {
        Log.i("APPDIMENS_MICRO", "PARITY-FAMILY $fam bad=${famBad[fam] ?: 0}/total=${famTotal[fam]}")
    }
}

/**
 * EN Temporary per-stage diagnostic: isolates buildKey / metricsFor / getOrPut /
 *    ThreadLocal / full-call costs (Device ADB only).
 * PT Diagnóstico temporário por etapa: isola custos de buildKey / metricsFor /
 *    getOrPut / ThreadLocal / chamada completa (somente device ADB).
 */
private suspend fun runDiagStages(context: Context, ops: BenchmarkDimenOps) {
    val iters = 200_000
    var acc = 0f

    // 1) raw loop overhead
    var t = System.nanoTime()
    repeat(iters) { acc += 1f }
    val rawNs = (System.nanoTime() - t) / iters

    // 2) buildKey
    t = System.nanoTime()
    repeat(iters) {
        acc += DimenCache.buildKey(
            100f, false, false, DimenCache.CalcType.SCALED,
            DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, true, DimenCache.ValueType.DP
        ).toFloat()
    }
    val buildKeyNs = (System.nanoTime() - t) / iters

    // 3) resources.configuration access only
    t = System.nanoTime()
    repeat(iters) { acc += context.resources.configuration.screenWidthDp }
    val configNs = (System.nanoTime() - t) / iters

    // 4) currentMetrics (ThreadLocal + fallback) read
    t = System.nanoTime()
    repeat(iters) { acc += DimenCache.currentMetrics.scale }
    val metricsNs = (System.nanoTime() - t) / iters

    // 5) getOrPut with an empty compute (cache hit after first)
    val keyHit = DimenCache.buildKey(
        100f, false, false, DimenCache.CalcType.SCALED,
        DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, DimenCache.ValueType.DP
    )
    DimenCache.getOrPut(keyHit, context) { 100f }
    t = System.nanoTime()
    repeat(iters) {
        acc += DimenCache.getOrPut(keyHit, context) { 100f }
    }
    val getOrPutHitNs = (System.nanoTime() - t) / iters

    // 5a) getOrPut with explicit metrics (resolve only — no metricsFor)
    val m = DimenCache.currentMetrics
    DimenCache.getOrPut(keyHit, m) { 100f }
    t = System.nanoTime()
    repeat(iters) { acc += DimenCache.getOrPut(keyHit, m) { 100f } }
    val getOrPutMetricsNs = (System.nanoTime() - t) / iters

    // 5b) peek(key, context) — metricsFor + slot probe only
    t = System.nanoTime()
    repeat(iters) { acc += DimenCache.peek(keyHit, context) ?: 0f }
    val peekNs = (System.nanoTime() - t) / iters

    // 6) full ops.sdp(context, 100)
    t = System.nanoTime()
    repeat(iters) { acc += ops.sdp(context, 100) }
    val fullSdpNs = (System.nanoTime() - t) / iters

    // 7) Same-position probes: isolate path cost from block-order artifacts.
    //    P0 = kernel floor (public API, no wrapper); P1..P4 = entry wrappers.
    //    If P2(wdp)/P3(sdpa) match P1(sdp), the 65ns rows are a JIT/order artifact.
    t = System.nanoTime()
    repeat(iters) { acc += DimenCache.calculateRawScaling(64f, false, null) }
    val p0KernelNs = (System.nanoTime() - t) / iters

    t = System.nanoTime()
    repeat(iters) { acc += ops.sdp(context, 100) }
    val p1SdpNs = (System.nanoTime() - t) / iters

    t = System.nanoTime()
    repeat(iters) { acc += ops.wdp(context, 30) }
    val p2WdpNs = (System.nanoTime() - t) / iters

    t = System.nanoTime()
    repeat(iters) { acc += ops.sdpa(context, 40) }
    val p3SdpaNs = (System.nanoTime() - t) / iters

    t = System.nanoTime()
    repeat(iters) { acc += ops.hdp(context, 50) }
    val p4HdpNs = (System.nanoTime() - t) / iters

    Log.i("APPDIMENS_DIAG", "raw_loop:$rawNs ns  buildKey:$buildKeyNs ns  config_access:$configNs ns  currentMetrics:$metricsNs ns")
    Log.i("APPDIMENS_DIAG", "getOrPut_ctx:$getOrPutHitNs ns  getOrPut_metrics:$getOrPutMetricsNs ns  peek_ctx:$peekNs ns  full_sdp:$fullSdpNs ns  checksum=$acc")
    Log.i("APPDIMENS_DIAG", "P0_kernel:$p0KernelNs ns  P1_sdp:$p1SdpNs ns  P2_wdp:$p2WdpNs ns  P3_sdpa:$p3SdpaNs ns  P4_hdp:$p4HdpNs ns")
}

/**
 * EN Forces the CPU governor to ramp the current core to its peak frequency BEFORE any
 *    measurement, so first-benchmark-family artifacts (e.g. sdp 220ns vs sdpa 108ns on a
 *    cold core) disappear and run-to-run spread collapses. Runs a few FP-heavy iterations
 *    for ~1.5s; the caller should hold URGENT_AUDIO priority for the whole measurement
 *    window so the measuring thread stays pinned to the boosted core.
 * PT Força o governor da CPU a subir o núcleo atual à frequência de pico ANTES de qualquer
 *    medição, eliminando o artefato de primeira família (ex.: sdp 220ns vs sdpa 108ns em
 *    núcleo frio) e reduzindo a dispersão entre execuções. Roda iterações FP por ~1,5s; o
 *    chamador deve manter a prioridade URGENT_AUDIO durante toda a janela de medição para
 *    que a thread permaneça no núcleo com boost.
 */
private fun thermalRamp(millis: Long = 1_500L) {
    var acc = 0f
    val deadline = System.nanoTime() + millis * 1_000_000L
    var i = 0
    do {
        val x = (i++ and 0xFF) + 1
        acc += kotlin.math.sqrt(x.toDouble()).toFloat()
    } while (System.nanoTime() < deadline)
    Log.v(TAG, "Thermal ramp done (acc=$acc, ${millis}ms)")
}

/** EN Warmup iterations — results are discarded. Primes the JIT compiler.
 * PT Iterações de aquecimento — resultados são descartados. Aquece o compilador JIT.
 */
private const val WARMUP_ITERATIONS = 10_000

/**
 * EN Measurement iterations per call type. 4 types × this = total ops measured.
 * PT Iterações de medição por tipo de chamada. 4 tipos × este valor = ops totais medidas.
 */
private const val MEASURE_ITERATIONS = 100_000

/**
 * EN Value used for the single-value with/without aspect-ratio comparison.
 *    Distinct from the per-type values (100/50/30/40) so both measurements start cold.
 * PT Valor usado na comparação de valor único com/sem aspect ratio.
 *    Diferente dos valores por tipo (100/50/30/40) para que ambas medições comecem frias.
 */
private const val SINGLE_VALUE = 64f

/**
 * EN Warmup iterations executed at each measurement-block boundary, discarded.
 *    Absorbs JIT/OSR and inline-cache transients that otherwise inflate the block's
 *    average (observed: wdp/sdpa blocks read 65ns vs 48-50ns at an identical call
 *    position, purely from block-order artifacts).
 * PT Iterações de aquecimento na fronteira de cada bloco de medição, descartadas.
 *    Absorve transientes de JIT/OSR e inline-cache que inflam a média do bloco
 *    (observado: blocos wdp/sdpa marcavam 65ns vs 48-50ns na mesma posição de
 *    chamada, apenas por artefato de ordem de bloco).
 */
private const val BLOCK_WARMUP_ITERATIONS = 10_000

/** EN Discards the call-site warmup transients before a timed block.
 * PT Descarta os transientes de aquecimento do call-site antes de um bloco cronometrado. */
private fun warmCallSite(call: () -> Float) {
    var acc = 0f
    repeat(BLOCK_WARMUP_ITERATIONS) { acc += call() }
    Log.v(TAG, "Call-site warmup done (acc=$acc)")
}

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
    // EN Hold real-time-ish priority for the whole measurement window: keeps the thread
    //    on the boosted core and the reported numbers at the hardware ceiling.
    // PT Mantém prioridade quase-real para toda a janela de medição: a thread permanece
    //    no núcleo com boost e os números ficam no teto do hardware.
    try {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO)
    } catch (_: SecurityException) {
    }

    val startWall = System.currentTimeMillis()

    // ── DIAG: per-stage overhead split (temporary instrumentation) ───────────
    thermalRamp()
    runParityProbe(context)
    runDiagStages(context, ops)

    // ── sdp (bypass path) ────────────────────────────────────────────────────
    // EN sw-qualifier call without AR — may bypass cache for cheap calc types (see DimenCache.getOrPut).
    // PT chamada sw sem AR — pode fazer bypass de cache para tipos baratos (ver DimenCache.getOrPut).
    var sdpAcc = 0f
    warmCallSite { ops.sdp(context, 100) }
    val sdpStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        sdpAcc += ops.sdp(context, 100)
    }
    val sdpElapsedNs = System.nanoTime() - sdpStartNs
    val sdpAvgNs = sdpElapsedNs / MEASURE_ITERATIONS

    // ── hdp (bypass path) ────────────────────────────────────────────────────
    var hdpAcc = 0f
    warmCallSite { ops.hdp(context, 50) }
    val hdpStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        hdpAcc += ops.hdp(context, 50)
    }
    val hdpElapsedNs = System.nanoTime() - hdpStartNs
    val hdpAvgNs = hdpElapsedNs / MEASURE_ITERATIONS

    // ── wdp (bypass path) ────────────────────────────────────────────────────
    var wdpAcc = 0f
    warmCallSite { ops.wdp(context, 30) }
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
    warmCallSite { ops.sdpa(context, 40) }
    val sdpaStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        sdpaAcc += ops.sdpa(context, 40)
    }
    val sdpaElapsedNs = System.nanoTime() - sdpaStartNs
    val sdpaAvgNs = sdpaElapsedNs / MEASURE_ITERATIONS

    // ── Single value: same value, with vs without AR ─────────────────────────
    // EN Back-to-back timing of ONE value through both paths — the direct cost
    //    comparison a developer sees for a single sdp call with/without AR.
    // PT Cronometragem consecutiva de UM valor em ambos os caminhos — a comparação
    //    direta de custo que o desenvolvedor vê numa única chamada sdp com/sem AR.
    var singleNoArAcc = 0f
    warmCallSite { ops.sdp(context, SINGLE_VALUE.toInt()) }
    val singleNoArStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        singleNoArAcc += ops.sdp(context, SINGLE_VALUE.toInt())
    }
    val singleNoArElapsedNs = System.nanoTime() - singleNoArStartNs
    val singleNoArAvgNs = singleNoArElapsedNs / MEASURE_ITERATIONS

    var singleWithArAcc = 0f
    warmCallSite { ops.sdpa(context, SINGLE_VALUE.toInt()) }
    val singleWithArStartNs = System.nanoTime()
    repeat(MEASURE_ITERATIONS) {
        singleWithArAcc += ops.sdpa(context, SINGLE_VALUE.toInt())
    }
    val singleWithArElapsedNs = System.nanoTime() - singleWithArStartNs
    val singleWithArAvgNs = singleWithArElapsedNs / MEASURE_ITERATIONS

    // ── Direct-call probes (SCALED only) ─────────────────────────────────────
    // EN Isolates the wrapper overhead: extension `100.sdp(ctx)` (kernel inlined
    //    into the loop) vs the public API `DimenSdp.sdp(ctx, 100)` (one non-inline
    //    hop). Other families keep the Function2 path only.
    // PT Isola o overhead do wrapper: extensão `100.sdp(ctx)` (kernel inlined no
    //    loop) vs a API pública `DimenSdp.sdp(ctx, 100)` (um salto não-inline).
    //    As demais famílias mantêm apenas o caminho Function2.
    var extSdpAcc = 0f
    var apiSdpAcc = 0f
    var extSdpAvgNs: Long? = null
    var apiSdpAvgNs: Long? = null
    if (mode == BenchmarkCalculationMode.SCALED) {
        warmCallSite { 100.sdp(context) }
        val extStartNs = System.nanoTime()
        repeat(MEASURE_ITERATIONS) {
            extSdpAcc += 100.sdp(context)
        }
        val extElapsedNs = System.nanoTime() - extStartNs
        extSdpAvgNs = extElapsedNs / MEASURE_ITERATIONS

        warmCallSite { DimenSdp.sdp(context, 100) }
        val apiStartNs = System.nanoTime()
        repeat(MEASURE_ITERATIONS) {
            apiSdpAcc += DimenSdp.sdp(context, 100)
        }
        val apiElapsedNs = System.nanoTime() - apiStartNs
        apiSdpAvgNs = apiElapsedNs / MEASURE_ITERATIONS
    }

    val endWall = System.currentTimeMillis()
    val totalWallMs = endWall - startWall

    try {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DEFAULT)
    } catch (_: SecurityException) {
    }

    // ── Combined average across all measured blocks ──────────────────────────
    val totalOps = MEASURE_ITERATIONS * 6
    val combinedNs = sdpElapsedNs + hdpElapsedNs + wdpElapsedNs + sdpaElapsedNs +
        singleNoArElapsedNs + singleWithArElapsedNs
    val combinedAvgNs = combinedNs / totalOps

    // ── Anti-dead-code accumulator checksum ──────────────────────────────────
    val checksum = sdpAcc + hdpAcc + wdpAcc + sdpaAcc + singleNoArAcc + singleWithArAcc +
        extSdpAcc + apiSdpAcc

    // ── Logcat export ─────────────────────────────────────────────────────────
    Log.i(TAG, "╔══════════════════ MICRO BENCHMARK RESULT ══════════════════╗")
    Log.i(TAG, "║ Mode: ${mode.name}")
    Log.i(TAG, "║ Combined avg: ${combinedAvgNs.formatNs()}/op · Total ops: $totalOps")
    Log.i(TAG, "║ sdp  (bypass): ${sdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ hdp  (bypass): ${hdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ wdp  (bypass): ${wdpAvgNs.formatNs()}/op")
    Log.i(TAG, "║ sdpa (cache) : ${sdpaAvgNs.formatNs()}/op")
    Log.i(TAG, "║ single $SINGLE_VALUE no-AR: ${singleNoArAvgNs.formatNs()}/op")
    Log.i(TAG, "║ single $SINGLE_VALUE +AR  : ${singleWithArAvgNs.formatNs()}/op")
    if (mode == BenchmarkCalculationMode.SCALED) {
        Log.i(TAG, "║ direct ext 100.sdp(ctx) : ${extSdpAvgNs?.formatNs()}/op")
        Log.i(TAG, "║ direct api DimenSdp.sdp : ${apiSdpAvgNs?.formatNs()}/op")
    }
    Log.i(TAG, "║ Total wall time: ${totalWallMs}ms")
    Log.i(TAG, "║ Accumulator checksum: $checksum")
    Log.i(TAG, "╚════════════════════════════════════════════════════════════╝")

    MicroBenchmarkResult(
        sdpAvgNs        = sdpAvgNs,
        hdpAvgNs        = hdpAvgNs,
        wdpAvgNs        = wdpAvgNs,
        sdpaAvgNs       = sdpaAvgNs,
        combinedAvgNs   = combinedAvgNs,
        singleNoArAvgNs = singleNoArAvgNs,
        singleWithArAvgNs = singleWithArAvgNs,
        singleValue     = SINGLE_VALUE,
        extSdpAvgNs     = extSdpAvgNs,
        apiSdpAvgNs     = apiSdpAvgNs,
        accumulatorChecksum = checksum,
        mode            = mode,
    )
}
