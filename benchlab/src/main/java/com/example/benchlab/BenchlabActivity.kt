/**
 * @author Bodenberg
 *
 * EN Benchmark dashboard: AppDimens Dynamic 3.1.8 vs legacy SDPS
 *    3.1.6 vs Chaintech SDP-SSP Compose Multiplatform 1.0.7 — values + timing
 *    on real devices. Supports full-scroll screenshot and report export.
 *
 * PT Dashboard de benchmark: AppDimens Dynamic 3.1.8 vs SDPS 3.1.6 legado
 *    vs Chaintech SDP-SSP Compose Multiplatform 1.0.7 — valores + tempo em
 *    dispositivos reais. Suporta screenshot de scroll completo e exportação de relatório.
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
import androidx.compose.material.icons.filled.CheckCircle
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
private val ColorAppDimens = AccentCyan
private val ColorSdps      = AccentAmber
private val ColorChaintech = AccentPurple

// ═══════════════════════════════════════════════════════════════════════════════
// ACTIVITY
// ═══════════════════════════════════════════════════════════════════════════════

class BenchlabActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDimensProvider {
                BenchlabScreen()
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

    private var probeDeferred: CompletableDeferred<ChaintechProbeResult>? = null

    fun run() {
        scope.launch {
            reset()
            try {
                _phase.value = BenchPhase.WARMUP
                val deferred = CompletableDeferred<ChaintechProbeResult>()
                probeDeferred = deferred
                _probeActive.value = true
                val chain = withTimeoutOrNull(15_000) { deferred.await() }
                    ?: throw IllegalStateException("Chaintech probe timed out")

                val result = runCompetitorBenchmark(context, chain) { _phase.value = it }
                _result.value = result
                _phase.value = BenchPhase.DONE
            } catch (t: Throwable) {
                Log.e("BENCHLAB", "benchmark failed", t)
                _phase.value = BenchPhase.DONE
            } finally {
                _probeActive.value = false
                probeDeferred = null
            }
        }
    }

    fun onChaintechMeasured(r: ChaintechProbeResult) {
        probeDeferred?.complete(r)
    }

    private fun reset() {
        _phase.value = BenchPhase.IDLE
        _result.value = null
        _probeActive.value = false
        probeDeferred = null
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SCREEN
// ═══════════════════════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BenchlabScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val controller = remember { BenchlabController(scope, context.applicationContext) }
    val graphicsLayer = rememberGraphicsLayer()
    val view = LocalView.current

    val phase by controller.phase.collectAsState()
    val result by controller.result.collectAsState()
    val probeActive by controller.probeActive.collectAsState()
    val isRunning = phase != BenchPhase.IDLE && phase != BenchPhase.DONE

    var screenshotSaving by remember { mutableStateOf(false) }
    var reportSaving by remember { mutableStateOf(false) }

    ChaintechProbe(
        active = probeActive,
        onResult = controller::onChaintechMeasured,
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
                                    // Capture the graphics layer to bitmap
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

                // ── Dp Resolution Table (sdp — no AR) ──────────────────────
                item { DpResolutionSection(result = result, withAr = false) }

                // ── Dp Resolution Table (sdpa — with AR) ───────────────────
                item { DpResolutionSection(result = result, withAr = true) }

                // ── Timing Table ────────────────────────────────────────────
                item { TimingSection(result = result) }

                // ── Average Banner ──────────────────────────────────────────
                item { AverageBanner(result = result) }

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
            "Dynamic 3.1.8 × SDPS 3.1.6 × Chaintech 1.0.7 — 2 testes",
            color = TextSecondary, fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // ── Run Button ──────────────────────────────────────────────────
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
            Text("Rodar 2 testes", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        // ── Screenshot + Export Row ─────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Screenshot Button
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

            // Export Report Button
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
// DP RESOLUTION TABLE
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun DpResolutionSection(result: CompetitorBenchmarkResult?, withAr: Boolean) {
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
                        // ── sdp: per-library blocks ─────────────────────────
                        LibBlock(
                            libName = "Dynamic 3.1.8",
                            libColor = ColorAppDimens,
                            dp1T1 = r.test1.dp1AppDimens, dp1T2 = r.test2.dp1AppDimens,
                            dp10T1 = r.test1.dp10AppDimens, dp10T2 = r.test2.dp10AppDimens,
                            dp100T1 = r.test1.dp100AppDimens, dp100T2 = r.test2.dp100AppDimens,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "SDPS 3.1.6",
                            libColor = ColorSdps,
                            dp1T1 = r.test1.dp1Sdps, dp1T2 = r.test2.dp1Sdps,
                            dp10T1 = r.test1.dp10Sdps, dp10T2 = r.test2.dp10Sdps,
                            dp100T1 = r.test1.dp100Sdps, dp100T2 = r.test2.dp100Sdps,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "Chaintech 1.0.7",
                            libColor = ColorChaintech,
                            dp1T1 = r.test1.dp1Chaintech, dp1T2 = r.test2.dp1Chaintech,
                            dp10T1 = r.test1.dp10Chaintech, dp10T2 = r.test2.dp10Chaintech,
                            dp100T1 = r.test1.dp100Chaintech, dp100T2 = r.test2.dp100Chaintech,
                        )
                    } else {
                        // ── sdpa: per-library blocks ────────────────────────
                        LibBlock(
                            libName = "Dynamic 3.1.8 (AR)",
                            libColor = ColorAppDimens,
                            dp1T1 = r.test1.dp1AppDimensAr, dp1T2 = r.test2.dp1AppDimensAr,
                            dp10T1 = r.test1.dp10AppDimensAr, dp10T2 = r.test2.dp10AppDimensAr,
                            dp100T1 = r.test1.dp100AppDimensAr, dp100T2 = r.test2.dp100AppDimensAr,
                        )
                        HorizontalDivider(color = SurfaceBorder)
                        LibBlock(
                            libName = "SDPS 3.1.6 (AR)",
                            libColor = ColorSdps,
                            dp1T1 = r.test1.dp1SdpsAr, dp1T2 = r.test2.dp1SdpsAr,
                            dp10T1 = r.test1.dp10SdpsAr, dp10T2 = r.test2.dp10SdpsAr,
                            dp100T1 = r.test1.dp100SdpsAr, dp100T2 = r.test2.dp100SdpsAr,
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
    dp1T1: Float, dp1T2: Float,
    dp10T1: Float, dp10T2: Float,
    dp100T1: Float, dp100T2: Float,
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
        DpRow2("1dp", dp1T1, dp1T2, libColor)
        Spacer(Modifier.height(4.dp))
        DpRow2("10dp", dp10T1, dp10T2, libColor)
        Spacer(Modifier.height(4.dp))
        DpRow2("100dp", dp100T1, dp100T2, libColor)
    }
}

@Composable
private fun DpRow2(
    dpLabel: String,
    t1: Float, t2: Float,
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
            modifier = Modifier.weight(0.15f)
        )
        Column(modifier = Modifier.weight(0.425f)) {
            Text("T1", color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("%.4f px".format(t1), color = libColor, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
        }
        Column(modifier = Modifier.weight(0.425f)) {
            Text("T2", color = TextSecondary, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
            Text("%.4f px".format(t2), color = libColor, fontSize = 10.sp,
                fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TIMING TABLE
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TimingSection(result: CompetitorBenchmarkResult?) {
    SectionHeader(icon = "⏱️", label = "Tempo por chamada de 1dp (sdp + sdpa/AR)", color = AccentAmber)

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text("Tempo por chamada aparece após o teste.", color = TextSecondary, fontSize = 12.sp)
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // ── sdp (no AR) ──────────────────────────────────────
                    Text("sdp (sem AR)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    TableHeader(columns = listOf("Teste" to 0.15f, "Dynamic" to 0.28f, "SDPS" to 0.28f, "Chaintech" to 0.29f))
                    HorizontalDivider(color = SurfaceBorder)
                    TimingRow("T1", r.timeTest1.appDimensNs, r.timeTest1.sdpsNs, r.timeTest1.chaintechNs, AccentCyan.copy(alpha = 0.04f))
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    TimingRow("T2", r.timeTest2.appDimensNs, r.timeTest2.sdpsNs, r.timeTest2.chaintechNs, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder)
                    TimingRow("Média", r.avgAppDimensNs, r.avgSdpsNs, r.avgChaintechNs, Color.Transparent, isHighlight = true)

                    Spacer(Modifier.height(8.dp))

                    // ── sdpa (with AR) ────────────────────────────────────
                    Text("sdpa (com AR — Dynamic × SDPS)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    TableHeader(columns = listOf("Teste" to 0.15f, "Dynamic" to 0.425f, "SDPS" to 0.425f))
                    HorizontalDivider(color = SurfaceBorder)
                    TimingRow2("T1", r.timeTest1.appDimensArNs, r.timeTest1.sdpsArNs, AccentAmber.copy(alpha = 0.04f))
                    HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))
                    TimingRow2("T2", r.timeTest2.appDimensArNs, r.timeTest2.sdpsArNs, Color.Transparent)
                    HorizontalDivider(color = SurfaceBorder)
                    TimingRow2("Média", r.avgAppDimensArNs, r.avgSdpsArNs, Color.Transparent, isHighlight = true)
                }
            }
        }
    }
}

@Composable
private fun TimingRow(
    label: String,
    appNs: Long, sdpsNs: Long, chainNs: Long,
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
        Text(sdpsNs.formatNs(), color = ColorSdps, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.28f))
        Text(chainNs.formatNs(), color = ColorChaintech, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.29f))
    }
}

@Composable
private fun TimingRow2(
    label: String,
    appNs: Long, sdpsNs: Long,
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
        Text(sdpsNs.formatNs(), color = ColorSdps, fontSize = 11.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.425f))
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// AVERAGE BANNER
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun AverageBanner(result: CompetitorBenchmarkResult?) {
    result?.let { r ->
        DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
            // ── sdp (no AR) ──────────────────────────────────────────────
            Text("sdp (sem AR)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))

            val ratioVsSdps = if (r.avgAppDimensNs > 0) r.avgSdpsNs.toFloat() / r.avgAppDimensNs.toFloat() else 1f
            val fasterVsSdps = r.avgAppDimensNs < r.avgSdpsNs
            RatioBanner(
                emoji = if (fasterVsSdps) "⚡" else "🐢",
                text = if (fasterVsSdps) "Dynamic é ×%.1f mais rápido que SDPS".format(ratioVsSdps)
                       else "Dynamic é ×%.1f mais lento que SDPS".format(1f / ratioVsSdps),
                color = if (fasterVsSdps) AccentGreen else AccentRed
            )

            Spacer(Modifier.height(4.dp))

            val ratioVsChain = if (r.avgAppDimensNs > 0) r.avgChaintechNs.toFloat() / r.avgAppDimensNs.toFloat() else 1f
            val fasterVsChain = r.avgAppDimensNs < r.avgChaintechNs
            RatioBanner(
                emoji = if (fasterVsChain) "🚀" else "🐢",
                text = if (fasterVsChain) "Dynamic é ×%.1f mais rápido que Chaintech".format(ratioVsChain)
                       else "Dynamic é ×%.1f mais lento que Chaintech".format(1f / ratioVsChain),
                color = if (fasterVsChain) AccentGreen else AccentRed
            )

            Spacer(Modifier.height(8.dp))

            // ── sdpa (with AR) ───────────────────────────────────────────
            Text("sdpa (com AR)", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))

            val ratioArVsSdps = if (r.avgAppDimensArNs > 0) r.avgSdpsArNs.toFloat() / r.avgAppDimensArNs.toFloat() else 1f
            val fasterArVsSdps = r.avgAppDimensArNs < r.avgSdpsArNs
            RatioBanner(
                emoji = if (fasterArVsSdps) "⚡" else "🐢",
                text = if (fasterArVsSdps) "Dynamic é ×%.1f mais rápido que SDPS".format(ratioArVsSdps)
                       else "Dynamic é ×%.1f mais lento que SDPS".format(1f / ratioArVsSdps),
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
private fun SectionHeader(icon: String, label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(icon, fontSize = 16.sp)
        Spacer(Modifier.width(8.dp))
        Text(label, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
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
    val sb = StringBuilder()
    sb.appendLine("═══════════════════════════════════════════════════════")
    sb.appendLine("  BenchLab — Relatório de Benchmark")
    sb.appendLine("  Dynamic 3.1.8 × SDPS 3.1.6 × Chaintech 1.0.7")
    sb.appendLine("  2 testes independentes")
    sb.appendLine("═══════════════════════════════════════════════════════")
    sb.appendLine()
    sb.appendLine("Dispositivo: sw=${r.windowSw}dp w=${r.windowW}dp h=${r.windowH}dp density=${"%.2f".format(r.density)}")
    sb.appendLine()

    // Resolution values
    sb.appendLine("── Valores de resolução (sdp) ──")
    sb.appendLine()
    for (lib in listOf("Dynamic" to "Dynamic 3.1.8", "SDPS" to "SDPS 3.1.6", "Chaintech" to "Chaintech 1.0.7")) {
        sb.appendLine("  ${lib.second}:")
        for ((dpLabel, dp) in listOf("1dp" to 1, "10dp" to 10, "100dp" to 100)) {
            val t1 = when (dp) { 1 -> if (lib.first == "Dynamic") r.test1.dp1AppDimens else if (lib.first == "SDPS") r.test1.dp1Sdps else r.test1.dp1Chaintech
                                  10 -> if (lib.first == "Dynamic") r.test1.dp10AppDimens else if (lib.first == "SDPS") r.test1.dp10Sdps else r.test1.dp10Chaintech
                                  else -> if (lib.first == "Dynamic") r.test1.dp100AppDimens else if (lib.first == "SDPS") r.test1.dp100Sdps else r.test1.dp100Chaintech }
            val t2 = when (dp) { 1 -> if (lib.first == "Dynamic") r.test2.dp1AppDimens else if (lib.first == "SDPS") r.test2.dp1Sdps else r.test2.dp1Chaintech
                                  10 -> if (lib.first == "Dynamic") r.test2.dp10AppDimens else if (lib.first == "SDPS") r.test2.dp10Sdps else r.test2.dp10Chaintech
                                  else -> if (lib.first == "Dynamic") r.test2.dp100AppDimens else if (lib.first == "SDPS") r.test2.dp100Sdps else r.test2.dp100Chaintech }
            sb.appendLine("    $dpLabel: T1=%.4f px  T2=%.4f px".format(t1, t2))
        }
        sb.appendLine()
    }

    // Timing
    sb.appendLine("── Tempo por chamada de 1dp (sdp) ──")
    sb.appendLine("  Dynamic:  T1=${r.timeTest1.appDimensNs.formatNs()}  T2=${r.timeTest2.appDimensNs.formatNs()}  Média=${r.avgAppDimensNs.formatNs()}")
    sb.appendLine("  SDPS:     T1=${r.timeTest1.sdpsNs.formatNs()}  T2=${r.timeTest2.sdpsNs.formatNs()}  Média=${r.avgSdpsNs.formatNs()}")
    sb.appendLine("  Chaintech:T1=${r.timeTest1.chaintechNs.formatNs()}  T2=${r.timeTest2.chaintechNs.formatNs()}  Média=${r.avgChaintechNs.formatNs()}")
    sb.appendLine()

    sb.appendLine("── Tempo por chamada de 1dp (sdpa/AR) ──")
    sb.appendLine("  Dynamic:  T1=${r.timeTest1.appDimensArNs.formatNs()}  T2=${r.timeTest2.appDimensArNs.formatNs()}  Média=${r.avgAppDimensArNs.formatNs()}")
    sb.appendLine("  SDPS:     T1=${r.timeTest1.sdpsArNs.formatNs()}  T2=${r.timeTest2.sdpsArNs.formatNs()}  Média=${r.avgSdpsArNs.formatNs()}")
    sb.appendLine()

    // Ratios
    sb.appendLine("── Comparativo (sdp) ──")
    if (r.avgAppDimensNs > 0) {
        val ratioSdps = r.avgSdpsNs.toFloat() / r.avgAppDimensNs.toFloat()
        val ratioChain = r.avgChaintechNs.toFloat() / r.avgAppDimensNs.toFloat()
        sb.appendLine("  Dynamic vs SDPS:     ×${"%.1f".format(ratioSdps)} ${if (ratioSdps > 1) "mais rápido" else "mais lento"}")
        sb.appendLine("  Dynamic vs Chaintech: ×${"%.1f".format(ratioChain)} ${if (ratioChain > 1) "mais rápido" else "mais lento"}")
    }
    sb.appendLine()
    sb.appendLine("── Comparativo (sdpa/AR) ──")
    if (r.avgAppDimensArNs > 0) {
        val ratioArSdps = r.avgSdpsArNs.toFloat() / r.avgAppDimensArNs.toFloat()
        sb.appendLine("  Dynamic vs SDPS: ×${"%.1f".format(ratioArSdps)} ${if (ratioArSdps > 1) "mais rápido" else "mais lento"}")
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
