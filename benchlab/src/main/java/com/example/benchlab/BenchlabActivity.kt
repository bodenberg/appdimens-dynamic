/**
 * @author Bodenberg
 *
 * EN Benchmark dashboard: AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 vs
 *    Lib #2.
 *    Shows:
 *    - NEW methodology: Benchmark A (Compose API, main thread) and
 *      Benchmark B (Engine, off-main) with median/min/P90/max stats,
 *      order rotation, anti-DCE checksums and two workloads;
 *    - LEGACY methodology: original T1/T2/T3 tests (resolution values +
 *      time per single call) kept for continuity with previous reports.
 *    Supports full-scroll screenshot and report export.
 *
 * PT Dashboard de benchmark: AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 vs
 *    Lib #2.
 *    Exibe:
 *    - Metodologia NOVA: Benchmark A (API Compose, main thread) e
 *      Benchmark B (Motor, off-main) com estatísticas mediana/min/P90/max,
 *      rotação de ordem, checksums anti-DCE e dois workloads;
 *    - Metodologia LEGADA: testes originais T1/T2/T3 (valores de resolução +
 *      tempo por chamada única) mantidos por continuidade com relatórios anteriores.
 *    Suporta screenshot de scroll completo e exportação de relatório.
 */
package com.example.benchlab

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.core.AppDimensProvider
import com.example.benchlab.benchmark.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ═══════════════════════════════════════════════════════════════════════════════
// COLOUR PALETTE
// ═══════════════════════════════════════════════════════════════════════════════

private val DarkBg        = Color(0xFF0D0F14)
private val SurfaceCard   = Color(0xFF161B24)
private val SurfaceBorder = Color(0xFF252D3D)
private val AccentCyan    = Color(0xFF00E5FF)
private val AccentGreen   = Color(0xFF69FF47)
private val AccentAmber   = Color(0xFFFFD740)
private val AccentPurple  = Color(0xFFB388FF)
private val TextPrimary   = Color(0xFFECF0F8)
private val TextSecondary = Color(0xFF8A95A8)
private val AccentRed     = Color(0xFFFF5252)

// Library colors
private val ColorAppDimens    = AccentCyan
private val ColorSdps         = AccentAmber
private val ColorLib2    = AccentPurple

// ═══════════════════════════════════════════════════════════════════════════════
// ACTIVITY
// ═══════════════════════════════════════════════════════════════════════════════

class BenchlabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // EN Headless automation: AUTO_START=true triggers the benchmark immediately.
        // PT Automação headless: AUTO_START=true dispara o benchmark imediatamente.
        val autoStart = intent.getBooleanExtra("AUTO_START", false)
        setContent {
            AppDimensProvider {
                BenchlabScreen(autoStart = autoStart)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// CONTROLLER
// ═══════════════════════════════════════════════════════════════════════════════

private class BenchlabController(
    private val scope: CoroutineScope,
    private val context: android.content.Context,
) {
    private val _phase = MutableStateFlow(BenchPhase.IDLE)
    val phase: StateFlow<BenchPhase> = _phase.asStateFlow()

    private val _result = MutableStateFlow<CompetitorBenchmarkResult?>(null)
    val result: StateFlow<CompetitorBenchmarkResult?> = _result.asStateFlow()

    private val _probeActive = MutableStateFlow(false)
    val probeActive: StateFlow<Boolean> = _probeActive.asStateFlow()

    private val _legacyProbeActive = MutableStateFlow(false)
    val legacyProbeActive: StateFlow<Boolean> = _legacyProbeActive.asStateFlow()

    private var probeDeferred: CompletableDeferred<ComposeProbeResult>? = null
    private var legacyProbeDeferred: CompletableDeferred<Concorrente2ProbeResult>? = null

    fun run() {
        scope.launch {
            reset()
            try {
                Log.i("BENCHLAB", "run(): started (AUTO_START path)")
                // Benchmark A — Compose API (chunked probe, main thread)
                _phase.value = BenchPhase.WARMUP
                val deferred = CompletableDeferred<ComposeProbeResult>()
                probeDeferred = deferred
                _probeActive.value = true
                val compose = withTimeoutOrNull(120_000) { deferred.await() }
                    ?: throw IllegalStateException("Compose probe timed out")

                // Benchmark B — Engine (off main thread)
                val core = runCoreEngineBenchmark(context) { _phase.value = it }

                // Legacy T1/T2/T3 tests (original methodology)
                val legacyDeferred = CompletableDeferred<Concorrente2ProbeResult>()
                legacyProbeDeferred = legacyDeferred
                _legacyProbeActive.value = true
                val conc2 = withTimeoutOrNull(15_000) { legacyDeferred.await() }
                    ?: throw IllegalStateException("Legacy probe timed out")
                val legacy = runLegacyBenchmark(context, conc2) { _phase.value = it }

                _result.value = assembleResult(compose, core, legacy, context)
                _phase.value = BenchPhase.DONE
            } catch (t: Throwable) {
                Log.e("BENCHLAB", "benchmark failed", t)
                _phase.value = BenchPhase.DONE
            } finally {
                _probeActive.value = false
                _legacyProbeActive.value = false
                probeDeferred = null
                legacyProbeDeferred = null
            }
        }
    }

    fun onComposeMeasured(r: ComposeProbeResult) {
        probeDeferred?.complete(r)
    }

    fun onLegacyMeasured(r: Concorrente2ProbeResult) {
        legacyProbeDeferred?.complete(r)
    }

    private fun reset() {
        _phase.value = BenchPhase.IDLE
        _result.value = null
        _probeActive.value = false
        _legacyProbeActive.value = false
        probeDeferred = null
        legacyProbeDeferred = null
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SCREEN
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BenchlabScreen(autoStart: Boolean = false) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val controller = remember { BenchlabController(scope, context.applicationContext) }
    val graphicsLayer = rememberGraphicsLayer()
    val view = LocalView.current

    val phase by controller.phase.collectAsState()
    val result by controller.result.collectAsState()
    val probeActive by controller.probeActive.collectAsState()
    val legacyProbeActive by controller.legacyProbeActive.collectAsState()
    val isRunning = phase != BenchPhase.IDLE && phase != BenchPhase.DONE

    // EN Headless automation: run the benchmark as soon as the screen is composed.
    // PT Automação headless: executa o benchmark assim que a tela é composta.
    LaunchedEffect(Unit) {
        if (autoStart) controller.run()
    }

    var screenshotSaving by remember { mutableStateOf(false) }
    var reportSaving by remember { mutableStateOf(false) }

    // EN New-methodology 3-way probe (chunked, main thread).
    // PT Sonda 3-vias da metodologia nova (fatiada, main thread).
    ComposeCompetitorProbe(
        active = probeActive,
        onResult = controller::onComposeMeasured,
    )

    // EN Legacy Lib #2 probe (used by the T1/T2/T3 tests).
    // PT Sonda legada da Lib #2 (usada pelos testes T1/T2/T3).
    Concorrente2Probe(
        active = legacyProbeActive,
        onResult = controller::onLegacyMeasured,
    )

    MaterialTheme(
        colorScheme = darkColorScheme().copy(
            background = DarkBg,
            surface = SurfaceCard,
            primary = AccentCyan,
        )
    ) {
        Scaffold(
            containerColor = DarkBg,
            topBar = {
                TopAppBar(
                    title = { Text("BenchLab", fontWeight = FontWeight.Bold, color = TextPrimary) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceCard)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // ── Action Buttons ─────────────────────────────────────────
                item {
                    ActionPanel(
                        isRunning = isRunning,
                        hasResult = result != null,
                        screenshotSaving = screenshotSaving,
                        onRun = controller::run,
                        onScreenshot = {
                            screenshotSaving = true
                            scope.launch {
                                try {
                                    val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
                                        .copy(Bitmap.Config.ARGB_8888, false)
                                    saveBitmapToGallery(context, bitmap, "benchlab_${System.currentTimeMillis()}.png")
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Screenshot salva na galeria", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("BENCHLAB", "Screenshot failed", e)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Erro ao salvar screenshot", Toast.LENGTH_SHORT).show()
                                    }
                                } finally {
                                    screenshotSaving = false
                                }
                            }
                        },
                        onExportReport = {
                            val r = result ?: return@ActionPanel
                            reportSaving = true
                            scope.launch {
                                try {
                                    val report = generateReport(r)
                                    saveReportToFile(context, report)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Relatório salvo em Documents/BenchLab", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("BENCHLAB", "Export failed", e)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Erro ao exportar relatório", Toast.LENGTH_SHORT).show()
                                    }
                                } finally {
                                    reportSaving = false
                                }
                            }
                        }
                    )
                }

                // ── Status ──────────────────────────────────────────────────
                item { StatusPanel(phase = phase, isRunning = isRunning) }

                // ── Methodology explanation ────────────────────────────────
                item { MethodologyCard() }

                // ── NEW: Benchmark A — Compose API ─────────────────────────
                item { ComposeApiSection(result = result) }

                // ── NEW: Benchmark B — Engine ───────────────────────────────
                item { CoreEngineSection(result = result) }

                // ── NEW: Ratio banners (medians) ────────────────────────────
                item { RatioSection(result = result) }

                // ── NEW: Anti-DCE checksums ─────────────────────────────────
                item { ChecksumSection(result = result) }

                // ── LEGACY: group divider ──────────────────────────────────
                item {
                    SectionHeader(
                        icon = "📜",
                        label = "Testes legados T1–T3 (metodologia original)",
                        color = TextSecondary,
                        caption = "Execuções originais preservadas para continuidade com relatórios anteriores (média de 3 testes, tempo por chamada única)."
                    )
                }

                // ── LEGACY: Dp Resolution (sdp — no AR) ───────────────────
                item { LegacyDpResolutionSection(result = result, withAr = false) }

                // ── LEGACY: Dp Resolution (sdpa — with AR) ─────────────────
                item { LegacyDpResolutionSection(result = result, withAr = true) }

                // ── LEGACY: Timing table ────────────────────────────────────
                item { LegacyTimingSection(result = result) }

                // ── LEGACY: Average banner ──────────────────────────────────
                item { LegacyAverageBanner(result = result) }

                // ── Device Info ─────────────────────────────────────────────
                item { DeviceInfo(result = result) }
            }

            // Hidden overlay for full-scroll screenshot capture
            if (screenshotSaving) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBg.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = AccentCyan)
                        Spacer(Modifier.height(8.dp))
                        Text("Capturando screenshot…", color = TextPrimary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ACTION PANEL
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ActionPanel(
    isRunning: Boolean,
    hasResult: Boolean,
    screenshotSaving: Boolean,
    onRun: () -> Unit,
    onScreenshot: () -> Unit,
    onExportReport: () -> Unit,
) {
    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            "Comparativo de 3 bibliotecas",
            color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            "Dynamic 3.1.8 × SDPS 3.1.6 × Lib #2 — 2 benchmarks + testes legados",
            color = TextSecondary, fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = onRun,
            enabled = !isRunning,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentCyan.copy(alpha = 0.18f),
                disabledContainerColor = AccentCyan.copy(alpha = 0.06f),
                contentColor = AccentCyan,
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, AccentCyan.copy(alpha = 0.6f))
        ) {
            Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text("Rodar benchmark completo", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onScreenshot,
                enabled = hasResult && !screenshotSaving,
                modifier = Modifier.weight(1f).height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AccentGreen,
                    disabledContentColor = AccentGreen.copy(alpha = 0.3f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentGreen.copy(alpha = if (hasResult) 0.6f else 0.15f))
            ) {
                Text("📷 Screenshot", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }

            OutlinedButton(
                onClick = onExportReport,
                enabled = hasResult,
                modifier = Modifier.weight(1f).height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AccentAmber,
                    disabledContentColor = AccentAmber.copy(alpha = 0.3f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber.copy(alpha = if (hasResult) 0.6f else 0.15f))
            ) {
                Text("📄 Relatório", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// STATUS PANEL
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StatusPanel(phase: BenchPhase, isRunning: Boolean) {
    val progress by animateFloatAsState(
        targetValue = phase.progressFraction,
        animationSpec = tween(durationMillis = 600),
        label = "progress"
    )
    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        when {
                            phase == BenchPhase.DONE -> AccentGreen
                            isRunning -> AccentCyan
                            else -> TextSecondary
                        }
                    )
            )
            Spacer(Modifier.width(10.dp))
            AnimatedContent(
                targetState = phase.displayLabel,
                transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(200)) },
                label = "phaseLabel"
            ) { label ->
                Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
        Spacer(Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = if (phase == BenchPhase.DONE) AccentGreen else AccentCyan,
            trackColor = SurfaceBorder
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// METHODOLOGY EXPLANATION
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MethodologyCard() {
    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Text("Como ler estes resultados", color = AccentGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(
            "Benchmark A — API Compose (main thread): as 3 bibliotecas rodam no mesmo composable, " +
                "com warm-up idêntico (20.000), 9 amostras de 50.000 iterações, ordem rotacionada e " +
                "checksum anti-DCE em todos os loops. A medição é fatiada em 5.000 ops/frame para a " +
                "UI não congelar; o tempo por fatia exclui os gaps entre frames.",
            color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Mediana = custo steady-state amortizado (ns/op). Interrupções isoladas do scheduler/GC " +
                "não afetam a mediana — por isso ela é o número principal; min/P90/max mostram a dispersão.",
            color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Benchmark B — Motor (Dispatchers.Default): Dynamic × SDPS fora da UI. " +
                "Lib #2 não possui API não-Compose → N/A.",
            color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Workloads: constant 1dp (call site quente) e mixed values (12 dimensões pré-definidas, " +
                "espelha uma tela real). Testes legados T1–T3: metodologia original, mantida por continuidade.",
            color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// BENCHMARK A — COMPOSE API (main thread)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ComposeApiSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(
        icon = "🧩",
        label = "Benchmark A — API Compose (main thread)",
        color = AccentCyan,
        caption = "As 3 bibliotecas no mesmo composable · warm-up 20.000 idêntico · 9 amostras × 50.000 · ordem rotacionada · mediana como número principal"
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Medição das 3 bibliotecas no mesmo composable (ordem rotacionada).", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WorkloadBlock(
                        title = "Constant 1dp — hot",
                        caption = "50.000 resoluções repetidas do mesmo valor (teto absoluto)",
                        items = listOf(
                            Triple("Dynamic 3.1.8", ColorAppDimens, r.composeApi.dynamic.constant1dp),
                            Triple("SDPS 3.1.6", ColorSdps, r.composeApi.sdps.constant1dp),
                            Triple("Lib #2", ColorLib2, r.composeApi.chaintech.constant1dp),
                        )
                    )
                    WorkloadBlock(
                        title = "Mixed values (12 dimensões)",
                        caption = "Valores 1–100dp pré-definidos, espelha uma tela real",
                        items = listOf(
                            Triple("Dynamic 3.1.8", ColorAppDimens, r.composeApi.dynamic.mixedValues),
                            Triple("SDPS 3.1.6", ColorSdps, r.composeApi.sdps.mixedValues),
                            Triple("Lib #2", ColorLib2, r.composeApi.chaintech.mixedValues),
                        )
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// BENCHMARK B — ENGINE (off main thread)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun CoreEngineSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(
        icon = "⚙️",
        label = "Benchmark B — Motor (Dispatchers.Default)",
        color = AccentAmber,
        caption = "Dynamic × SDPS fora da composição · mesma metodologia (9 × 50.000, rotação, anti-DCE) · Lib #2: N/A — API exige Composition"
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Dynamic × SDPS fora da composição. Lib #2: N/A (exige Composition).", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    WorkloadBlock(
                        title = "Constant 1dp — hot",
                        caption = "50.000 resoluções repetidas do mesmo valor (teto absoluto)",
                        items = listOf(
                            Triple("Dynamic 3.1.8", ColorAppDimens, r.coreEngine.dynamic.constant1dp),
                            Triple("SDPS 3.1.6", ColorSdps, r.coreEngine.sdps.constant1dp),
                        )
                    )
                    WorkloadBlock(
                        title = "Mixed values (12 dimensões)",
                        caption = "Valores 1–100dp pré-definidos, espelha uma tela real",
                        items = listOf(
                            Triple("Dynamic 3.1.8", ColorAppDimens, r.coreEngine.dynamic.mixedValues),
                            Triple("SDPS 3.1.6", ColorSdps, r.coreEngine.sdps.mixedValues),
                        )
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// WORKLOAD BLOCKS (stats cards)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun WorkloadBlock(
    title: String,
    caption: String,
    items: List<Triple<String, Color, TimingStats>>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceBorder.copy(alpha = 0.15f))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(caption, color = TextSecondary, fontSize = 9.sp)
        Spacer(Modifier.height(8.dp))
        items.forEachIndexed { index, (name, color, stats) ->
            if (index > 0) {
                HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                Spacer(Modifier.height(6.dp))
            }
            LibStatsCard(name = name, color = color, stats = stats)
        }
    }
}

@Composable
private fun LibStatsCard(name: String, color: Color, stats: TimingStats) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.05f))
            .border(1.dp, color.copy(alpha = 0.18f), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Text(name, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        Spacer(Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            StatCell(label = "Mediana", value = stats.medianNs.formatNs(), color = TextPrimary, isHighlight = true, modifier = Modifier.weight(1f))
            StatCell(label = "Min", value = stats.minNs.formatNs(), color = TextSecondary, modifier = Modifier.weight(1f))
            StatCell(label = "P90", value = stats.p90Ns.formatNs(), color = TextSecondary, modifier = Modifier.weight(1f))
            StatCell(label = "Max", value = stats.maxNs.formatNs(), color = TextSecondary, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCell(label: String, value: String, color: Color, modifier: Modifier, isHighlight: Boolean = false) {
    Column(modifier = modifier) {
        Text(label, color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
        Text(value, color = if (isHighlight) AccentGreen else color, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// RATIO BANNERS (based on new-methodology medians)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun RatioSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(
        icon = "⚡",
        label = "Comparativo (mediana dos novos benchmarks)",
        color = AccentGreen,
        caption = "Razões calculadas sobre as medianas — não sobre médias"
    )

    result?.let { r ->
        DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text("Benchmark A — API Compose", color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.composeApi.dynamic.constant1dp.medianNs, r.composeApi.sdps.constant1dp.medianNs, "SDPS 3.1.6", "Compose · constant 1dp")
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.composeApi.dynamic.constant1dp.medianNs, r.composeApi.chaintech.constant1dp.medianNs, "Lib #2", "Compose · constant 1dp")
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.composeApi.dynamic.mixedValues.medianNs, r.composeApi.sdps.mixedValues.medianNs, "SDPS 3.1.6", "Compose · mixed values")
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.composeApi.dynamic.mixedValues.medianNs, r.composeApi.chaintech.mixedValues.medianNs, "Lib #2", "Compose · mixed values")

            Spacer(Modifier.height(8.dp))

            Text("Benchmark B — Motor", color = AccentAmber, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.coreEngine.dynamic.constant1dp.medianNs, r.coreEngine.sdps.constant1dp.medianNs, "SDPS 3.1.6", "Motor · constant 1dp")
            Spacer(Modifier.height(4.dp))
            DynamicRatioLine(r.coreEngine.dynamic.mixedValues.medianNs, r.coreEngine.sdps.mixedValues.medianNs, "SDPS 3.1.6", "Motor · mixed values")
        }
    }
}

@Composable
private fun DynamicRatioLine(dynamicMedian: Double, otherMedian: Double, otherName: String, context: String) {
    if (dynamicMedian <= 0.0 || otherMedian <= 0.0) return
    val faster = dynamicMedian < otherMedian
    val ratio = if (faster) otherMedian / dynamicMedian else dynamicMedian / otherMedian
    val text = if (faster) {
        "Dynamic é ×%.1f mais rápido que %s (%s)".format(ratio, otherName, context)
    } else {
        "Dynamic é ×%.1f mais lento que %s (%s)".format(ratio, otherName, context)
    }
    RatioBanner(
        emoji = if (faster) "⚡" else "🐢",
        text = text,
        color = if (faster) AccentGreen else AccentRed
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// ANTI-DCE CHECKSUMS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ChecksumSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(
        icon = "🔢",
        label = "Anti-DCE checksums",
        color = AccentGreen,
        caption = "Soma acumulada das resoluções nos loops cronometrados — prova de que as chamadas foram executadas e consumidas. Dynamic/SDPS somam px; Lib #2 soma dp (.value)."
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Checksum dos acumuladores (prova que as chamadas foram executadas).", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    ChecksumRow("Compose · Dynamic 3.1.8", r.composeApi.dynamic, ColorAppDimens)
                    ChecksumRow("Compose · SDPS 3.1.6", r.composeApi.sdps, ColorSdps)
                    ChecksumRow("Compose · Lib #2", r.composeApi.chaintech, ColorLib2)
                    HorizontalDivider(color = SurfaceBorder)
                    ChecksumRow("Motor · Dynamic 3.1.8", r.coreEngine.dynamic, ColorAppDimens)
                    ChecksumRow("Motor · SDPS 3.1.6", r.coreEngine.sdps, ColorSdps)
                }
            }
        }
    }
}

@Composable
private fun ChecksumRow(label: String, lib: LibraryTiming, color: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = color, fontSize = 10.sp, fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.45f))
        Text("1dp: %.1f".format(lib.constantChecksum), color = TextSecondary, fontSize = 10.sp,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.28f))
        Text("mix: %.1f".format(lib.mixedChecksum), color = TextSecondary, fontSize = 10.sp,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.27f))
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// LEGACY: DP RESOLUTION TABLE (T1/T2/T3)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun LegacyDpResolutionSection(result: CompetitorBenchmarkResult?, withAr: Boolean) {
    val label = if (withAr) "Valores de resolução sdpa (com AR → px)" else "Valores de resolução sdp (sem AR → px)"
    val color = if (withAr) AccentAmber else AccentGreen
    SectionHeader(icon = if (withAr) "📐AR" else "📐", label = label, color = color)

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Clique em Rodar para ver os valores.", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (!withAr) {
                        LibBlock(
                            libName = "Dynamic 3.1.8",
                            libColor = ColorAppDimens,
                            dp1T1 = r.legacy.test1.dp1AppDimens, dp1T2 = r.legacy.test2.dp1AppDimens, dp1T3 = r.legacy.test3.dp1AppDimens,
                            dp10T1 = r.legacy.test1.dp10AppDimens, dp10T2 = r.legacy.test2.dp10AppDimens, dp10T3 = r.legacy.test3.dp10AppDimens,
                            dp100T1 = r.legacy.test1.dp100AppDimens, dp100T2 = r.legacy.test2.dp100AppDimens, dp100T3 = r.legacy.test3.dp100AppDimens,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "SDPS 3.1.6",
                            libColor = ColorSdps,
                            dp1T1 = r.legacy.test1.dp1Concorrente1, dp1T2 = r.legacy.test2.dp1Concorrente1, dp1T3 = r.legacy.test3.dp1Concorrente1,
                            dp10T1 = r.legacy.test1.dp10Concorrente1, dp10T2 = r.legacy.test2.dp10Concorrente1, dp10T3 = r.legacy.test3.dp10Concorrente1,
                            dp100T1 = r.legacy.test1.dp100Concorrente1, dp100T2 = r.legacy.test2.dp100Concorrente1, dp100T3 = r.legacy.test3.dp100Concorrente1,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "Lib #2",
                            libColor = ColorLib2,
                            dp1T1 = r.legacy.test1.dp1Concorrente2, dp1T2 = r.legacy.test2.dp1Concorrente2, dp1T3 = r.legacy.test3.dp1Concorrente2,
                            dp10T1 = r.legacy.test1.dp10Concorrente2, dp10T2 = r.legacy.test2.dp10Concorrente2, dp10T3 = r.legacy.test3.dp10Concorrente2,
                            dp100T1 = r.legacy.test1.dp100Concorrente2, dp100T2 = r.legacy.test2.dp100Concorrente2, dp100T3 = r.legacy.test3.dp100Concorrente2,
                        )
                    } else {
                        LibBlock(
                            libName = "Dynamic 3.1.8 (AR)",
                            libColor = ColorAppDimens,
                            dp1T1 = r.legacy.test1.dp1AppDimensAr, dp1T2 = r.legacy.test2.dp1AppDimensAr, dp1T3 = r.legacy.test3.dp1AppDimensAr,
                            dp10T1 = r.legacy.test1.dp10AppDimensAr, dp10T2 = r.legacy.test2.dp10AppDimensAr, dp10T3 = r.legacy.test3.dp10AppDimensAr,
                            dp100T1 = r.legacy.test1.dp100AppDimensAr, dp100T2 = r.legacy.test2.dp100AppDimensAr, dp100T3 = r.legacy.test3.dp100AppDimensAr,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "SDPS 3.1.6 (AR)",
                            libColor = ColorSdps,
                            dp1T1 = r.legacy.test1.dp1Concorrente1Ar, dp1T2 = r.legacy.test2.dp1Concorrente1Ar, dp1T3 = r.legacy.test3.dp1Concorrente1Ar,
                            dp10T1 = r.legacy.test1.dp10Concorrente1Ar, dp10T2 = r.legacy.test2.dp10Concorrente1Ar, dp10T3 = r.legacy.test3.dp10Concorrente1Ar,
                            dp100T1 = r.legacy.test1.dp100Concorrente1Ar, dp100T2 = r.legacy.test2.dp100Concorrente1Ar, dp100T3 = r.legacy.test3.dp100Concorrente1Ar,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LibBlock(
    libName: String,
    libColor: Color,
    dp1T1: Float, dp1T2: Float, dp1T3: Float,
    dp10T1: Float, dp10T2: Float, dp10T3: Float,
    dp100T1: Float, dp100T2: Float, dp100T3: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(libColor.copy(alpha = 0.05f))
            .border(1.dp, libColor.copy(alpha = 0.18f), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            libName,
            color = libColor, fontSize = 12.sp, fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Spacer(Modifier.height(6.dp))
        DpRow3("1dp", dp1T1, dp1T2, dp1T3, libColor)
        Spacer(Modifier.height(4.dp))
        DpRow3("10dp", dp10T1, dp10T2, dp10T3, libColor)
        Spacer(Modifier.height(4.dp))
        DpRow3("100dp", dp100T1, dp100T2, dp100T3, libColor)
    }
}

@Composable
private fun DpRow3(
    dpLabel: String,
    t1: Float, t2: Float, t3: Float,
    libColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            dpLabel,
            color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.weight(0.13f)
        )
        Column(modifier = Modifier.weight(0.29f)) {
            Text("T1", color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("%.4f px".format(t1), color = libColor, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
        }
        Column(modifier = Modifier.weight(0.29f)) {
            Text("T2", color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("%.4f px".format(t2), color = libColor, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
        }
        Column(modifier = Modifier.weight(0.29f)) {
            Text("T3", color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("%.4f px".format(t3), color = libColor, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// LEGACY: TIMING TABLE (T1/T2/T3 + Média)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun LegacyTimingSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(icon = "⏱️", label = "Tempo por chamada de 1dp (legado — sdp + sdpa/AR)", color = AccentAmber)

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Tempo por chamada aparece após o teste.", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("sdp (sem AR)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    TableHeader(columns = listOf("Teste" to 0.15f, "Dynamic" to 0.28f, "SDPS" to 0.28f, "Lib #2" to 0.29f))
                    HorizontalDivider(color = SurfaceBorder)
                    LegacyTimingRow("T1", r.legacy.timeTest1.appDimensNs, r.legacy.timeTest1.concorrente1Ns, r.legacy.timeTest1.concorrente2Ns, AccentCyan.copy(alpha = 0.04f))
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    LegacyTimingRow("T2", r.legacy.timeTest2.appDimensNs, r.legacy.timeTest2.concorrente1Ns, r.legacy.timeTest2.concorrente2Ns, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    LegacyTimingRow("T3", r.legacy.timeTest3.appDimensNs, r.legacy.timeTest3.concorrente1Ns, r.legacy.timeTest3.concorrente2Ns, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder)
                    LegacyTimingRow("Média", r.legacy.avgAppDimensNs, r.legacy.avgConcorrente1Ns, r.legacy.avgConcorrente2Ns, Color.Transparent, isHighlight = true)

                    Spacer(Modifier.height(8.dp))

                    Text("sdpa (com AR — Dynamic × SDPS)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    TableHeader(columns = listOf("Teste" to 0.15f, "Dynamic" to 0.425f, "SDPS" to 0.425f))
                    HorizontalDivider(color = SurfaceBorder)
                    LegacyTimingRow2("T1", r.legacy.timeTest1.appDimensArNs, r.legacy.timeTest1.concorrente1ArNs, AccentAmber.copy(alpha = 0.04f))
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    LegacyTimingRow2("T2", r.legacy.timeTest2.appDimensArNs, r.legacy.timeTest2.concorrente1ArNs, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    LegacyTimingRow2("T3", r.legacy.timeTest3.appDimensArNs, r.legacy.timeTest3.concorrente1ArNs, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder)
                    LegacyTimingRow2("Média", r.legacy.avgAppDimensArNs, r.legacy.avgConcorrente1ArNs, Color.Transparent, isHighlight = true)
                }
            }
        }
    }
}

@Composable
private fun LegacyTimingRow(
    label: String,
    appNs: Long, conc1Ns: Long, conc2Ns: Long,
    rowColor: Color,
    isHighlight: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowColor)
            .padding(horizontal = 10.dp, vertical = if (isHighlight) 6.dp else 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = if (isHighlight) AccentGreen else TextSecondary,
            fontSize = if (isHighlight) 11.sp else 10.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.15f))
        Text(appNs.formatNs(), color = ColorAppDimens, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.28f))
        Text(conc1Ns.formatNs(), color = ColorSdps, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.28f))
        Text(conc2Ns.formatNs(), color = ColorLib2, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.29f))
    }
}

@Composable
private fun LegacyTimingRow2(
    label: String,
    appNs: Long, conc1Ns: Long,
    rowColor: Color,
    isHighlight: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowColor)
            .padding(horizontal = 10.dp, vertical = if (isHighlight) 6.dp else 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = if (isHighlight) AccentGreen else TextSecondary,
            fontSize = if (isHighlight) 11.sp else 10.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Normal,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.15f))
        Text(appNs.formatNs(), color = ColorAppDimens, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.425f))
        Text(conc1Ns.formatNs(), color = ColorSdps, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.425f))
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// LEGACY: AVERAGE BANNER (based on legacy averages)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun LegacyAverageBanner(result: CompetitorBenchmarkResult?) {
    result?.let { r ->
        DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text("Comparativo legado (média T1–T3)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))

            Text("sdp (sem AR)", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))

            val ratioVsSdps = if (r.legacy.avgAppDimensNs > 0) r.legacy.avgConcorrente1Ns.toFloat() / r.legacy.avgAppDimensNs.toFloat() else 1f
            val fasterVsSdps = r.legacy.avgAppDimensNs < r.legacy.avgConcorrente1Ns
            RatioBanner(
                emoji = if (fasterVsSdps) "⚡" else "🐢",
                text = if (fasterVsSdps) "Dynamic é ×%.1f mais rápido que SDPS".format(ratioVsSdps)
                       else "Dynamic é ×%.1f mais lento que SDPS".format(1f / ratioVsSdps),
                color = if (fasterVsSdps) AccentGreen else AccentRed
            )

            Spacer(Modifier.height(4.dp))

            val ratioVsLib2 = if (r.legacy.avgAppDimensNs > 0) r.legacy.avgConcorrente2Ns.toFloat() / r.legacy.avgAppDimensNs.toFloat() else 1f
            val fasterVsLib2 = r.legacy.avgAppDimensNs < r.legacy.avgConcorrente2Ns
            RatioBanner(
                emoji = if (fasterVsLib2) "🚀" else "🐢",
                text = if (fasterVsLib2) "Dynamic é ×%.1f mais rápido que Lib #2".format(ratioVsLib2)
                       else "Dynamic é ×%.1f mais lento que Lib #2".format(1f / ratioVsLib2),
                color = if (fasterVsLib2) AccentGreen else AccentRed
            )

            Spacer(Modifier.height(8.dp))

            Text("sdpa (com AR)", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))

            val ratioArVsSdps = if (r.legacy.avgAppDimensArNs > 0) r.legacy.avgConcorrente1ArNs.toFloat() / r.legacy.avgAppDimensArNs.toFloat() else 1f
            val fasterArVsSdps = r.legacy.avgAppDimensArNs < r.legacy.avgConcorrente1ArNs
            RatioBanner(
                emoji = if (fasterArVsSdps) "⚡" else "🐢",
                text = if (fasterArVsSdps) "Dynamic é ×%.1f mais rápido que SDPS (AR)".format(ratioArVsSdps)
                       else "Dynamic é ×%.1f mais lento que SDPS (AR)".format(1f / ratioArVsSdps),
                color = if (fasterArVsSdps) AccentGreen else AccentRed
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DEVICE INFO
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun DeviceInfo(result: CompetitorBenchmarkResult?) {
    result?.let { r ->
        DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            Text(
                "sw=${r.windowSw}dp  w=${r.windowW}dp  h=${r.windowH}dp  density=%.2f".format(r.density),
                color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SHARED COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SectionHeader(icon: String, label: String, color: Color, caption: String? = null) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp)
            Spacer(Modifier.width(8.dp))
            Text(label, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        if (caption != null) {
            Spacer(Modifier.height(3.dp))
            Text(caption, color = TextSecondary, fontSize = 10.sp, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun DashboardCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, SurfaceBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

@Composable
private fun TableHeader(columns: List<Pair<String, Float>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        columns.forEach { (text, weight) ->
            Text(
                text, color = TextSecondary, fontSize = 10.sp,
                fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(weight)
            )
        }
    }
}

@Composable
private fun RatioBanner(emoji: String, text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.10f))
            .padding(horizontal = 10.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 12.sp)
        Spacer(Modifier.width(6.dp))
        Text(
            text, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SCREENSHOT + REPORT EXPORT
// ═══════════════════════════════════════════════════════════════════════════════

private suspend fun saveBitmapToGallery(
    context: android.content.Context,
    bitmap: Bitmap,
    fileName: String,
) = withContext(Dispatchers.IO) {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val displayName = "BenchLab_${timestamp}.png"

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/BenchLab")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

    uri?.let {
        resolver.openOutputStream(it)?.use { os ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(it, values, null, null)
        }
    }
}

private fun generateReport(r: CompetitorBenchmarkResult): String {
    data class RatioLine(val label: String, val dynamicNs: Double, val otherNs: Double, val otherName: String)

    val sb = StringBuilder()
    sb.appendLine("═══════════════════════════════════════════════════════")
    sb.appendLine("  BenchLab — Relatório de Benchmark")
    sb.appendLine("  Dynamic 3.1.8 × SDPS 3.1.6 × Lib #2")
    sb.appendLine("  Benchmark A (Compose API) + Benchmark B (Motor) + Testes legados T1-T3")
    sb.appendLine("═══════════════════════════════════════════════════════")
    sb.appendLine()
    sb.appendLine("Dispositivo: sw=${r.windowSw}dp w=${r.windowW}dp h=${r.windowH}dp density=${"%.2f".format(r.density)}")
    sb.appendLine()
    sb.appendLine("Metodologia (novos benchmarks):")
    sb.appendLine("  Benchmark A — API Compose (main thread): as 3 bibliotecas no mesmo composable")
    sb.appendLine("  Benchmark B — Motor (Dispatchers.Default): Dynamic x SDPS apenas (Lib #2 N/A)")
    sb.appendLine("  Warm-up idêntico: ${"%,d".format(BENCH_WARMUP_COUNT).replace(',', '.')} resoluções de 1dp por biblioteca")
    sb.appendLine("  Amostras: $BENCH_SAMPLE_COUNT por workload")
    sb.appendLine("  Iterações/amostra: ${"%,d".format(BENCH_MEASURE_COUNT).replace(',', '.')}")
    sb.appendLine("  Fatiamento: ${"%,d".format(BENCH_CHUNK_OPS).replace(',', '.')} ops/frame (UI responsiva; tempo por fatia exclui gaps)")
    sb.appendLine("  Anti-DCE: acumulador de checksum em todos os loops cronometrados")
    sb.appendLine("  Rotação de ordem: Dynamic → SDPS → Lib #2 / SDPS → Lib #2 → Dynamic / Lib #2 → Dynamic → SDPS")
    sb.appendLine("  Número principal: mediana (ns/op steady-state amortizado); min/P90/max = dispersão")
    sb.appendLine("  Workloads: constant 1dp (call site quente) + mixed values (12 dimensões pré-definidas)")
    sb.appendLine("  Testes legados T1-T3: metodologia original (média de 3 execuções), por continuidade")
    sb.appendLine()

    sb.appendLine("── Benchmark A — API Compose (main thread) ──")
    sb.appendLine()
    for ((name, lib) in listOf("Dynamic 3.1.8" to r.composeApi.dynamic, "SDPS 3.1.6" to r.composeApi.sdps, "Lib #2" to r.composeApi.chaintech)) {
        sb.appendLine("  $name:")
        sb.appendLine("    Constant 1dp: mediana=${lib.constant1dp.medianNs.formatNs()}  min=${lib.constant1dp.minNs.formatNs()}  P90=${lib.constant1dp.p90Ns.formatNs()}  max=${lib.constant1dp.maxNs.formatNs()}")
        sb.appendLine("    Mixed values: mediana=${lib.mixedValues.medianNs.formatNs()}  min=${lib.mixedValues.minNs.formatNs()}  P90=${lib.mixedValues.p90Ns.formatNs()}  max=${lib.mixedValues.maxNs.formatNs()}")
        sb.appendLine()
    }

    sb.appendLine("── Benchmark B — Motor (Dispatchers.Default) ──")
    sb.appendLine("  Lib #2: N/A — API exige Composition")
    sb.appendLine()
    for ((name, lib) in listOf("Dynamic 3.1.8" to r.coreEngine.dynamic, "SDPS 3.1.6" to r.coreEngine.sdps)) {
        sb.appendLine("  $name:")
        sb.appendLine("    Constant 1dp: mediana=${lib.constant1dp.medianNs.formatNs()}  min=${lib.constant1dp.minNs.formatNs()}  P90=${lib.constant1dp.p90Ns.formatNs()}  max=${lib.constant1dp.maxNs.formatNs()}")
        sb.appendLine("    Mixed values: mediana=${lib.mixedValues.medianNs.formatNs()}  min=${lib.mixedValues.minNs.formatNs()}  P90=${lib.mixedValues.p90Ns.formatNs()}  max=${lib.mixedValues.maxNs.formatNs()}")
        sb.appendLine()
    }

    sb.appendLine("── Anti-DCE checksums ──")
    sb.appendLine("  Compose:  Dynamic=${"%.1f".format(r.composeApi.dynamic.constantChecksum)}/${"%.1f".format(r.composeApi.dynamic.mixedChecksum)}  SDPS=${"%.1f".format(r.composeApi.sdps.constantChecksum)}/${"%.1f".format(r.composeApi.sdps.mixedChecksum)}  Lib #2=${"%.1f".format(r.composeApi.chaintech.constantChecksum)}/${"%.1f".format(r.composeApi.chaintech.mixedChecksum)}")
    sb.appendLine("  Motor:    Dynamic=${"%.1f".format(r.coreEngine.dynamic.constantChecksum)}/${"%.1f".format(r.coreEngine.dynamic.mixedChecksum)}  SDPS=${"%.1f".format(r.coreEngine.sdps.constantChecksum)}/${"%.1f".format(r.coreEngine.sdps.mixedChecksum)}")
    sb.appendLine()

    sb.appendLine("── Comparativo (mediana dos novos benchmarks) ──")
    val comparisons = listOf(
        RatioLine("Compose · constant 1dp", r.composeApi.dynamic.constant1dp.medianNs, r.composeApi.sdps.constant1dp.medianNs, "SDPS 3.1.6"),
        RatioLine("Compose · constant 1dp", r.composeApi.dynamic.constant1dp.medianNs, r.composeApi.chaintech.constant1dp.medianNs, "Lib #2"),
        RatioLine("Compose · mixed values", r.composeApi.dynamic.mixedValues.medianNs, r.composeApi.sdps.mixedValues.medianNs, "SDPS 3.1.6"),
        RatioLine("Compose · mixed values", r.composeApi.dynamic.mixedValues.medianNs, r.composeApi.chaintech.mixedValues.medianNs, "Lib #2"),
        RatioLine("Motor · constant 1dp", r.coreEngine.dynamic.constant1dp.medianNs, r.coreEngine.sdps.constant1dp.medianNs, "SDPS 3.1.6"),
        RatioLine("Motor · mixed values", r.coreEngine.dynamic.mixedValues.medianNs, r.coreEngine.sdps.mixedValues.medianNs, "SDPS 3.1.6"),
    )
    for ((label, dyn, other, name) in comparisons) {
        if (dyn > 0.0 && other > 0.0) {
            val ratio = if (dyn < other) other / dyn else dyn / other
            val verdict = if (dyn < other) "mais rápido" else "mais lento"
            sb.appendLine("  Dynamic vs $name ($label): ×${"%.1f".format(ratio)} $verdict")
        }
    }
    sb.appendLine()

    sb.appendLine("── Testes legados T1–T3 (metodologia original) ──")
    sb.appendLine()
    sb.appendLine("  Valores de resolução (sdp):")
    val sdpGetters = listOf(
        "Dynamic 3.1.8" to Triple(
            { t: DpResolution3 -> t.dp1AppDimens }, { t: DpResolution3 -> t.dp10AppDimens }, { t: DpResolution3 -> t.dp100AppDimens }),
        "SDPS 3.1.6" to Triple(
            { t: DpResolution3 -> t.dp1Concorrente1 }, { t: DpResolution3 -> t.dp10Concorrente1 }, { t: DpResolution3 -> t.dp100Concorrente1 }),
        "Lib #2" to Triple(
            { t: DpResolution3 -> t.dp1Concorrente2 }, { t: DpResolution3 -> t.dp10Concorrente2 }, { t: DpResolution3 -> t.dp100Concorrente2 }),
    )
    for ((name, getters) in sdpGetters) {
        val (get1, get10, get100) = getters
        sb.appendLine("    $name: 1dp T1=%.4f T2=%.4f T3=%.4f | 10dp T1=%.4f T2=%.4f T3=%.4f | 100dp T1=%.4f T2=%.4f T3=%.4f".format(
            get1(r.legacy.test1), get1(r.legacy.test2), get1(r.legacy.test3),
            get10(r.legacy.test1), get10(r.legacy.test2), get10(r.legacy.test3),
            get100(r.legacy.test1), get100(r.legacy.test2), get100(r.legacy.test3)))
    }
    sb.appendLine()
    sb.appendLine("  Valores de resolução (sdpa/AR):")
    sb.appendLine("    Dynamic 3.1.8: 1dp T1=%.4f T2=%.4f T3=%.4f | 10dp T1=%.4f T2=%.4f T3=%.4f | 100dp T1=%.4f T2=%.4f T3=%.4f".format(
        r.legacy.test1.dp1AppDimensAr, r.legacy.test2.dp1AppDimensAr, r.legacy.test3.dp1AppDimensAr,
        r.legacy.test1.dp10AppDimensAr, r.legacy.test2.dp10AppDimensAr, r.legacy.test3.dp10AppDimensAr,
        r.legacy.test1.dp100AppDimensAr, r.legacy.test2.dp100AppDimensAr, r.legacy.test3.dp100AppDimensAr))
    sb.appendLine("    SDPS 3.1.6: 1dp T1=%.4f T2=%.4f T3=%.4f | 10dp T1=%.4f T2=%.4f T3=%.4f | 100dp T1=%.4f T2=%.4f T3=%.4f".format(
        r.legacy.test1.dp1Concorrente1Ar, r.legacy.test2.dp1Concorrente1Ar, r.legacy.test3.dp1Concorrente1Ar,
        r.legacy.test1.dp10Concorrente1Ar, r.legacy.test2.dp10Concorrente1Ar, r.legacy.test3.dp10Concorrente1Ar,
        r.legacy.test1.dp100Concorrente1Ar, r.legacy.test2.dp100Concorrente1Ar, r.legacy.test3.dp100Concorrente1Ar))
    sb.appendLine()
    sb.appendLine("  Tempo por chamada de 1dp (sdp):")
    sb.appendLine("    Dynamic:  T1=${r.legacy.timeTest1.appDimensNs.formatNs()}  T2=${r.legacy.timeTest2.appDimensNs.formatNs()}  T3=${r.legacy.timeTest3.appDimensNs.formatNs()}  Média=${r.legacy.avgAppDimensNs.formatNs()}")
    sb.appendLine("    SDPS:     T1=${r.legacy.timeTest1.concorrente1Ns.formatNs()}  T2=${r.legacy.timeTest2.concorrente1Ns.formatNs()}  T3=${r.legacy.timeTest3.concorrente1Ns.formatNs()}  Média=${r.legacy.avgConcorrente1Ns.formatNs()}")
    sb.appendLine("    Lib #2: T1=${r.legacy.timeTest1.concorrente2Ns.formatNs()}  T2=${r.legacy.timeTest2.concorrente2Ns.formatNs()}  T3=${r.legacy.timeTest3.concorrente2Ns.formatNs()}  Média=${r.legacy.avgConcorrente2Ns.formatNs()}")
    sb.appendLine()
    sb.appendLine("  Tempo por chamada de 1dp (sdpa/AR):")
    sb.appendLine("    Dynamic:  T1=${r.legacy.timeTest1.appDimensArNs.formatNs()}  T2=${r.legacy.timeTest2.appDimensArNs.formatNs()}  T3=${r.legacy.timeTest3.appDimensArNs.formatNs()}  Média=${r.legacy.avgAppDimensArNs.formatNs()}")
    sb.appendLine("    SDPS:     T1=${r.legacy.timeTest1.concorrente1ArNs.formatNs()}  T2=${r.legacy.timeTest2.concorrente1ArNs.formatNs()}  T3=${r.legacy.timeTest3.concorrente1ArNs.formatNs()}  Média=${r.legacy.avgConcorrente1ArNs.formatNs()}")
    sb.appendLine()
    sb.appendLine("  Comparativo legado (média T1–T3):")
    if (r.legacy.avgAppDimensNs > 0 && r.legacy.avgConcorrente1Ns > 0) {
        val ratioSdps = r.legacy.avgConcorrente1Ns.toFloat() / r.legacy.avgAppDimensNs.toFloat()
        sb.appendLine("    Dynamic vs SDPS (sdp): ×${"%.1f".format(ratioSdps)} ${if (ratioSdps > 1) "mais rápido" else "mais lento"}")
    }
    if (r.legacy.avgAppDimensNs > 0 && r.legacy.avgConcorrente2Ns > 0) {
        val ratioChain = r.legacy.avgConcorrente2Ns.toFloat() / r.legacy.avgAppDimensNs.toFloat()
        sb.appendLine("    Dynamic vs Lib #2 (sdp): ×${"%.1f".format(ratioChain)} ${if (ratioChain > 1) "mais rápido" else "mais lento"}")
    }
    if (r.legacy.avgAppDimensArNs > 0 && r.legacy.avgConcorrente1ArNs > 0) {
        val ratioAr = r.legacy.avgConcorrente1ArNs.toFloat() / r.legacy.avgAppDimensArNs.toFloat()
        sb.appendLine("    Dynamic vs SDPS (sdpa): ×${"%.1f".format(ratioAr)} ${if (ratioAr > 1) "mais rápido" else "mais lento"}")
    }

    sb.appendLine()
    sb.appendLine("═══════════════════════════════════════════════════════")
    sb.appendLine("  Gerado por BenchLab v${android.os.Build.VERSION.SDK_INT}")
    sb.appendLine("═══════════════════════════════════════════════════════")

    return sb.toString()
}

private suspend fun saveReportToFile(
    context: android.content.Context,
    report: String,
) = withContext(Dispatchers.IO) {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val fileName = "benchlab_report_${timestamp}.txt"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val values = ContentValues().apply {
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain")
            put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/BenchLab")
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), values)
        uri?.let {
            resolver.openOutputStream(it)?.use { os ->
                os.write(report.toByteArray())
            }
        }
    } else {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "BenchLab")
        dir.mkdirs()
        val file = File(dir, fileName)
        FileOutputStream(file).use { it.write(report.toByteArray()) }
    }
}