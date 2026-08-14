/**
 * @author Bodenberg
 *
 * EN Legacy composable probe for Concorrente 2 (Lib #2) — original code.
 *    Unlike AppDimens/Concorrente 1, the Lib #2 `.sdp` extension is `@Composable`
 *    (it reads LocalConfiguration to scale by min(w,h)/300), so its cost can only
 *    be measured inside composition. The probe runs on the main thread:
 *    - warms up the extension call site,
 *    - times a tight loop of `100.sdp` resolutions,
 *    - resolves raw px for 1dp, 10dp, 100dp (precision input),
 *    and reports the result once via [onResult].
 *    Kept for the legacy T1/T2/T3 tests (continuity with previous reports).
 *
 * PT Sonda composable legada para a Concorrente 2 (Lib #2) — código original.
 *    Diferente de AppDimens/Concorrente 1, a extensão `.sdp` da Lib #2 é `@Composable`
 *    (lê LocalConfiguration para escalar por min(w,h)/300), então seu custo só pode
 *    ser medido dentro da composição. A sonda roda na main thread:
 *    - aquece o call site da extensão,
 *    - cronometra um loop fechado de resoluções `100.sdp`,
 *    - resolve px brutos para 1dp, 10dp, 100dp (entrada de precisão),
 *    e reporta o resultado uma vez via [onResult].
 *    Mantida para os testes legados T1/T2/T3 (continuidade com relatórios anteriores).
 */
package com.example.benchlab.benchmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import network.chaintech.sdpcomposemultiplatform.sdp

private const val PROBE_REPEAT = 10_000
private const val PROBE_WARMUP = 1_000

/**
 * EN Measures the Lib #2 extension inside composition (legacy). Compose only this
 *    probe while [active] is true; it self-guards so the timed loop runs exactly once.
 *
 * @param active EN Whether to run the probe. PT Se a sonda deve rodar.
 * @param onResult EN Callback with the measured result. PT Callback com o resultado medido.
 */
@Composable
fun Concorrente2Probe(
    active: Boolean,
    onResult: (Concorrente2ProbeResult) -> Unit,
) {
    val density = LocalDensity.current
    var measured by remember(active) { mutableStateOf(false) }

    if (active && !measured) {
        // Warmup
        var warmAcc = 0f
        repeat(PROBE_WARMUP) {
            warmAcc += 100.sdp.value
        }

        // Timed loop
        var acc = 0f
        val start = System.nanoTime()
        repeat(PROBE_REPEAT) {
            acc += 100.sdp.value
        }
        val elapsedNs = System.nanoTime() - start
        val sdpAvgNs = elapsedNs / PROBE_REPEAT

        // Raw px for 1dp, 10dp, 100dp
        val dp1Px  = with(density) { 1.sdp.toPx() }
        val dp10Px = with(density) { 10.sdp.toPx() }
        val dp100Px = with(density) { 100.sdp.toPx() }

        measured = true
        SideEffect {
            onResult(Concorrente2ProbeResult(
                sdpAvgNs = sdpAvgNs,
                dp1Px = dp1Px,
                dp10Px = dp10Px,
                dp100Px = dp100Px,
                checksum = acc + warmAcc,
            ))
        }
    }
}