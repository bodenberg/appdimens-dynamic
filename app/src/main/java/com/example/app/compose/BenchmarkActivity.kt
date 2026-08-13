/**
 * @author Bodenberg
 *
 * EN Production-grade benchmark dashboard for the AppDimens library.
 *    Hosts a BenchmarkController that drives sequential Micro → Macro execution,
 *    and displays a structured three-section results panel with premium UI.
 *
 * PT Dashboard de benchmark de nível de produção para a biblioteca AppDimens.
 *    Hospeda um BenchmarkController que conduz a execução sequencial Micro → Macro,
 *    e exibe um painel de resultados estruturado em três seções com UI premium.
 */
package com.example.app.compose

import android.os.Bundle
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.wdp
import com.appdimens.dynamic.core.AppDimensProvider
import com.example.app.compose.benchmark.*

// ═══════════════════════════════════════════════════════════════════════════════
// COLOUR PALETTE
// ═══════════════════════════════════════════════════════════════════════════════

private val DarkBg       = Color(0xFF0D0F14)
private val SurfaceCard  = Color(0xFF161B24)
private val SurfaceBorder= Color(0xFF252D3D)
private val AccentCyan   = Color(0xFF00E5FF)
private val AccentGreen  = Color(0xFF69FF47)
private val AccentAmber  = Color(0xFFFFD740)
private val AccentPurple = Color(0xFFB388FF)
private val TextPrimary  = Color(0xFFECF0F8)
private val TextSecondary= Color(0xFF8A95A8)
private val AccentRed    = Color(0xFFFF5252)

// ═══════════════════════════════════════════════════════════════════════════════
// ACTIVITY
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Activity that hosts the full production-grade benchmark dashboard.
 * PT Atividade que hospeda o dashboard completo de benchmark de nível de produção.
 */
class BenchmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val autoStart = intent.getBooleanExtra("AUTO_START_FULL", false)
        val autoStartMicro = intent.getBooleanExtra("AUTO_START_MICRO", false)
        setContent {
            AppDimensProvider {
                BenchmarkDashboardScreen(
                    autoStart = autoStart,
                    autoStartMicro = autoStartMicro
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ROOT SCREEN
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN Root composable for the benchmark dashboard.
 *    Wires together the BenchmarkController, state observation, the results panel,
 *    and the 1000-item stress list.
 * PT Composable raiz do dashboard de benchmark.
 *    Conecta o BenchmarkController, observação de estado, o painel de resultados
 *    e a lista de estresse de 1000 itens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchmarkDashboardScreen(autoStart: Boolean = false, autoStartMicro: Boolean = false) {
    val context     = LocalContext.current
    val listState   = rememberLazyListState()
    val scope       = rememberCoroutineScope()
    val activity    = context as? BenchmarkActivity

    // EN Calculation family for Micro + Calculation benchmarks (Macro list stays scaled sdp).
    // PT Família de cálculo para benchmarks Micro + Cálculo (lista Macro permanece sdp scaled).
    var calculationMode by remember { mutableStateOf(BenchmarkCalculationMode.SCALED) }

    // EN Instantiate controller — stable across recompositions
    // PT Instanciar controlador — estável entre recomposições
    val controller = remember { BenchmarkController(scope, context.applicationContext, listState) }

    val phase  by controller.phase.collectAsState()
    val result by controller.result.collectAsState()
    val isRunning = phase != BenchmarkPhase.IDLE && phase != BenchmarkPhase.DONE

    // EN Automated start trigger for headless/automation runs
    // PT Gatilho de início automatizado para execuções headless/automação
    LaunchedEffect(Unit) {
        if (autoStartMicro) {
            android.util.Log.i("APPDIMENS_AUTO", "Auto-start MICRO triggered via Intent extra.")
            controller.runMicroOnly(calculationMode)
        } else if (autoStart) {
            android.util.Log.i("APPDIMENS_AUTO", "Auto-start triggered via Intent extra.")
            controller.runFull(calculationMode)
        }
    }

    MaterialTheme(
        colorScheme = darkColorScheme().copy(
            background = DarkBg,
            surface    = SurfaceCard,
            primary    = AccentCyan
        )
    ) {
        Scaffold(
            containerColor = DarkBg,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "AppDimens Benchmark",
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { activity?.finish() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = TextPrimary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceCard)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // ── Control Panel ──────────────────────────────────────────
                item {
                    ControlPanel(
                        phase          = phase,
                        isRunning      = isRunning,
                        selectedMode   = calculationMode,
                        onModeChange   = { calculationMode = it },
                        onFull         = { controller.runFull(calculationMode) },
                        onCalc         = { controller.runCalculationOnly(calculationMode) },
                        onMicro        = { controller.runMicroOnly(calculationMode) },
                        onMacro        = { controller.runMacroOnly() },
                        onCompare      = { controller.runComparison(calculationMode) }
                    )
                }

                // ── Status Section ─────────────────────────────────────────
                item { StatusSection(phase = phase, isRunning = isRunning) }

                // ── Calculation Results Section ─────────────────────────────
                item {
                    CalculationResultSection(
                        result      = result.calculation,
                        pendingMode = calculationMode
                    )
                }

                // ── Micro Results Section ──────────────────────────────────
                item {
                    MicroResultSection(
                        result      = result.micro,
                        pendingMode = calculationMode
                    )
                }

                // ── Macro Results Section ──────────────────────────────────
                item { MacroResultSection(result = result.macro) }

                // ── Comparison Results Section (3.1.8 vs 3.1.6) ───────────
                item { ComparisonResultSection(result = result.comparison) }

                // ── Divider before stress list ─────────────────────────────
                item {
                    DashboardSectionHeader(
                        icon  = "🗂️",
                        label = "UI Stress List — ${MACRO_ITEM_COUNT} Items (scaled sdp)",
                        color = TextSecondary
                    )
                }

                // ── 1000-item stress LazyColumn ───────────────────────────
                items((1..MACRO_ITEM_COUNT).toList(), key = { it }) { id ->
                    BenchmarkItem(id)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// CONTROL PANEL
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ControlPanel(
    phase: BenchmarkPhase,
    isRunning: Boolean,
    selectedMode: BenchmarkCalculationMode,
    onModeChange: (BenchmarkCalculationMode) -> Unit,
    onFull: () -> Unit,
    onCalc: () -> Unit,
    onMicro: () -> Unit,
    onMacro: () -> Unit,
    onCompare: () -> Unit
) {
    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            "🚀 Benchmark Controls",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        var modeMenuExpanded by remember { mutableStateOf(false) }
        Text(
            "Calculation type (Micro + Calc)",
            color = TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { modeMenuExpanded = true },
                enabled = !isRunning,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AccentCyan
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        selectedMode.displayLabel,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
            DropdownMenu(
                expanded = modeMenuExpanded,
                onDismissRequest = { modeMenuExpanded = false },
                modifier = Modifier.align(Alignment.TopStart).fillMaxWidth(),
                containerColor = SurfaceCard
            ) {
                BenchmarkCalculationMode.entries.forEach { mode ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                mode.displayLabel,
                                color = if (mode == selectedMode) AccentCyan else TextPrimary,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            onModeChange(mode)
                            modeMenuExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BenchmarkButton(
                label     = "Full Run",
                subLabel  = "All tests",
                color     = AccentCyan,
                enabled   = !isRunning,
                modifier  = Modifier.weight(1f),
                onClick   = onFull
            )
            BenchmarkButton(
                label     = "Calc",
                subLabel  = "Mixed",
                color     = AccentGreen,
                enabled   = !isRunning,
                modifier  = Modifier.weight(1f),
                onClick   = onCalc
            )
            BenchmarkButton(
                label     = "Micro",
                subLabel  = "CPU ops",
                color     = AccentPurple,
                enabled   = !isRunning,
                modifier  = Modifier.weight(1f),
                onClick   = onMicro
            )
            BenchmarkButton(
                label     = "Macro",
                subLabel  = "UI scroll",
                color     = AccentAmber,
                enabled   = !isRunning,
                modifier  = Modifier.weight(1f),
                onClick   = onMacro
            )
        }

        Spacer(Modifier.height(8.dp))

        // EN Side-by-side comparison: library 3.1.8 vs legacy SDPS 3.1.6.
        // PT Comparativo lado a lado: biblioteca 3.1.8 vs SDPS 3.1.6 legado.
        BenchmarkButton(
            label    = "Compare",
            subLabel = "3.1.8 × 3.1.6 · speed + precision",
            color    = AccentRed,
            enabled  = !isRunning,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            onClick  = onCompare
        )
    }
}

@Composable
private fun BenchmarkButton(
    label: String,
    subLabel: String,
    color: Color,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick   = onClick,
        enabled   = enabled,
        modifier  = modifier.height(56.dp),
        shape     = RoundedCornerShape(12.dp),
        colors    = ButtonDefaults.buttonColors(
            containerColor         = color.copy(alpha = 0.18f),
            disabledContainerColor = color.copy(alpha = 0.06f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (enabled) color.copy(alpha = 0.6f) else color.copy(alpha = 0.2f)
        ),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label,    color = if (enabled) color else color.copy(alpha = 0.4f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(subLabel, color = if (enabled) TextSecondary else TextSecondary.copy(alpha = 0.3f), fontSize = 10.sp)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// STATUS SECTION
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun StatusSection(phase: BenchmarkPhase, isRunning: Boolean) {
    val progress by animateFloatAsState(
        targetValue  = phase.progressFraction,
        animationSpec = tween(durationMillis = 600),
        label        = "progress"
    )

    DashboardSectionHeader(icon = "📊", label = "Status", color = AccentCyan)

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Phase indicator dot
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        when {
                            phase == BenchmarkPhase.DONE  -> AccentGreen
                            isRunning                     -> AccentCyan
                            else                          -> TextSecondary
                        }
                    )
            )
            Spacer(Modifier.width(10.dp))
            AnimatedContent(
                targetState  = phase.displayLabel,
                transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(200)) },
                label        = "phaseLabel"
            ) { label ->
                Text(label, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(10.dp))

        LinearProgressIndicator(
            progress        = { progress },
            modifier        = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color           = when {
                phase == BenchmarkPhase.DONE   -> AccentGreen
                phase.name.startsWith("CALC")  -> AccentGreen
                phase.name.startsWith("MICRO") -> AccentPurple
                else                           -> AccentCyan
            },
            trackColor      = SurfaceBorder
        )

        if (phase == BenchmarkPhase.DONE) {
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, null, tint = AccentGreen, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(6.dp))
                Text("Benchmark complete — results ready", color = AccentGreen, fontSize = 12.sp)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// CALCULATION RESULTS SECTION
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun CalculationResultSection(
    result: CalculationBenchmarkResult?,
    pendingMode: BenchmarkCalculationMode
) {
    val modeLabel = result?.mode?.displayLabel ?: pendingMode.displayLabel
    DashboardSectionHeader(
        icon  = "🧪",
        label = "Calculation Test — $modeLabel",
        color = AccentGreen
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text(
                "Run Calculation or Full benchmark to see results. Selected: $modeLabel",
                color    = TextSecondary,
                fontSize = 12.sp
            )
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    MetricRow("Mode",           r.mode.displayLabel, AccentGreen)
                    MetricRow("Avg resolution", r.avgNsPerOp.formatNs(), AccentGreen, isHighlight = true)
                    MetricRow("Total calls",    "${"%,d".format(r.totalOps)}",   AccentGreen)
                    
                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(color = SurfaceBorder)
                    Text(
                        "sdp=${"%.4f".format(r.sdpPx)}  hdp=${"%.4f".format(r.hdpPx)}  wdp=${"%.4f".format(r.wdpPx)}  sdpa=${"%.4f".format(r.sdpaPx)}",
                        color      = TextSecondary,
                        fontSize   = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier   = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// MICRO RESULTS SECTION
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MicroResultSection(
    result: MicroBenchmarkResult?,
    pendingMode: BenchmarkCalculationMode
) {
    val modeLabel = result?.mode?.displayLabel ?: pendingMode.displayLabel
    DashboardSectionHeader(
        icon  = "⚙️",
        label = "Microbenchmark — $modeLabel",
        color = AccentPurple
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text(
                "Run Micro or Full benchmark to see results. Selected: $modeLabel",
                color    = TextSecondary,
                fontSize = 12.sp
            )
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    MetricRow("Mode",          r.mode.displayLabel, AccentPurple)
                    MetricRow("Combined avg",  r.combinedAvgNs.formatNs(), AccentPurple, isHighlight = true)

                    Spacer(Modifier.height(4.dp))
                    Text("Call-type breakdown", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    HorizontalDivider(color = SurfaceBorder)

                    PathRow(label = "sw   (no AR)", avgNs = r.sdpAvgNs, color = AccentGreen)
                    PathRow(label = "h    (no AR)", avgNs = r.hdpAvgNs, color = AccentGreen)
                    PathRow(label = "w    (no AR)", avgNs = r.wdpAvgNs, color = AccentGreen)
                    PathRow(label = "sw+a (AR)", avgNs = r.sdpaAvgNs, color = AccentAmber)

                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(color = SurfaceBorder)
                    Text(
                        "Single value ${r.singleValue.toInt()} — with vs without AR",
                        color      = TextSecondary,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier   = Modifier.padding(top = 4.dp)
                    )
                    PathRow(label = "no AR", avgNs = r.singleNoArAvgNs, color = AccentGreen)
                    PathRow(label = "with AR", avgNs = r.singleWithArAvgNs, color = AccentAmber)

                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Checksum: ${"%.0f".format(r.accumulatorChecksum)} (anti dead-code proof)",
                        color    = TextSecondary,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// MACRO RESULTS SECTION
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun MacroResultSection(result: MacroBenchmarkResult?) {
    DashboardSectionHeader(icon = "🎮", label = "Macrobenchmark — UI Scroll Performance", color = AccentCyan)

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text(
                "Run Macro or Full benchmark to see results.",
                color    = TextSecondary,
                fontSize = 12.sp
            )
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    MetricRow("Scroll duration",  "${r.scrollDurationMs} ms",         AccentCyan, isHighlight = true)
                    MetricRow("Total frames",     "${"%,d".format(r.totalFrames)}",  AccentCyan)
                    MetricRow("Cost per item",    "~${"%.1f".format(r.avgFrameMs * 1000 / 1000)} µs", AccentAmber)
                    MetricRow("Est. frames @60fps","~${"%d".format((r.scrollDurationMs * 60 / 1000).toInt())} frames", AccentGreen)

                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(color = SurfaceBorder)
                    Text(
                        r.notes,
                        color      = TextSecondary,
                        fontSize   = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier   = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// COMPARISON RESULTS SECTION (Dynamic 3.1.8 vs SDPS 3.1.6)
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun ComparisonResultSection(result: ComparisonBenchmarkResult?) {
    DashboardSectionHeader(
        icon  = "⚖️",
        label = "Comparativo — Dynamic (ref) × SDPS 3.1.6",
        color = AccentRed
    )

    DashboardCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        AnimatedVisibility(visible = result == null) {
            Text(
                "Clique em Compare para rodar os 3 testes e ver a comparação.",
                color    = TextSecondary,
                fontSize = 12.sp
            )
        }
        AnimatedVisibility(visible = result != null) {
            result?.let { r ->
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    // ── Device info (compact) ──────────────────────────────
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceBorder.copy(alpha = 0.3f))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("sw=${r.windowSw}dp  w=${r.windowW}dp  h=${r.windowH}dp", color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                        Text("density=${"%.2f".format(r.density)}", color = TextSecondary, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    }

                    // ── TABLE 1: Dp Resolution Values (1dp, 10dp, 100dp) ──
                    Text("Valores de resolução (sdp)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    DpResolutionTable(r)

                    // ── TABLE 2: Time per Single Dp Call ───────────────────
                    Text("Tempo por chamada única de 1dp", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    TimingTable(r)

                    // ── Average banner ─────────────────────────────────────
                    val ratioFaster = r.avgLegacyNsPerDp > r.avgCurrentNsPerDp
                    val ratioColor = if (ratioFaster) AccentGreen else AccentRed
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ratioColor.copy(alpha = 0.10f))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (ratioFaster) "⚡" else "🐢", fontSize = 14.sp)
                        Spacer(Modifier.width(8.dp))
                        val ratio = if (r.avgCurrentNsPerDp > 0) r.avgLegacyNsPerDp.toFloat() / r.avgCurrentNsPerDp.toFloat() else 1f
                        Text(
                            if (ratioFaster) "Dynamic é ×${"%.1f".format(ratio)} mais rápido"
                            else "Dynamic é ×${"%.1f".format(1f / ratio)} mais lento",
                            color      = ratioColor,
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

// ── Dp Resolution Table ──────────────────────────────────────────────────────

@Composable
private fun DpResolutionTable(r: ComparisonBenchmarkResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(10.dp))
    ) {
        // Header row
        TableRow(
            cells = listOf("dp" to 0.15f, "Dynamic" to 0.425f, "SDPS" to 0.425f),
            isHeader = true
        )
        HorizontalDivider(color = SurfaceBorder)

        // 1dp row — 2 tests
        DpGroup(dp = "1", test1Cur = r.test1.dp1Current, test1Leg = r.test1.dp1Legacy,
            test2Cur = r.test2.dp1Current, test2Leg = r.test2.dp1Legacy)
        HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))

        // 10dp row — 2 tests
        DpGroup(dp = "10", test1Cur = r.test1.dp10Current, test1Leg = r.test1.dp10Legacy,
            test2Cur = r.test2.dp10Current, test2Leg = r.test2.dp10Legacy)
        HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))

        // 100dp row — 2 tests
        DpGroup(dp = "100", test1Cur = r.test1.dp100Current, test1Leg = r.test1.dp100Legacy,
            test2Cur = r.test2.dp100Current, test2Leg = r.test2.dp100Legacy)
    }
}

@Composable
private fun DpGroup(
    dp: String,
    test1Cur: Float, test1Leg: Float,
    test2Cur: Float, test2Leg: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AccentCyan.copy(alpha = 0.04f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        // dp label + values for 2 tests
        Row(Modifier.fillMaxWidth()) {
            Text(dp, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace, modifier = Modifier.weight(0.15f))
            Column(modifier = Modifier.weight(0.425f)) {
                TestValue("T1", test1Cur, AccentCyan)
                TestValue("T2", test2Cur, AccentCyan)
            }
            Column(modifier = Modifier.weight(0.425f)) {
                TestValue("T1", test1Leg, AccentAmber)
                TestValue("T2", test2Leg, AccentAmber)
            }
        }
    }
}

@Composable
private fun TestValue(label: String, value: Float, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
        Text("${"%.4f".format(value)} px", color = color, fontSize = 10.sp,
            fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
    }
}

// ── Timing Table ──────────────────────────────────────────────────────────────

@Composable
private fun TimingTable(r: ComparisonBenchmarkResult) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, SurfaceBorder, RoundedCornerShape(10.dp))
    ) {
        // Header row
        TableRow(
            cells = listOf("Teste" to 0.2f, "Dynamic" to 0.4f, "SDPS" to 0.4f),
            isHeader = true
        )
        HorizontalDivider(color = SurfaceBorder)

        // Test 1
        TableRow(cells = listOf(
            "#1" to 0.2f,
            r.timeTest1.currentNsPerDp.formatNs() to 0.4f,
            r.timeTest1.legacyNsPerDp.formatNs() to 0.4f
        ), rowColor = AccentCyan.copy(alpha = 0.04f))
        HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))

        // Test 2
        TableRow(cells = listOf(
            "#2" to 0.2f,
            r.timeTest2.currentNsPerDp.formatNs() to 0.4f,
            r.timeTest2.legacyNsPerDp.formatNs() to 0.4f
        ))
        HorizontalDivider(color = SurfaceBorder.copy(alpha = 0.5f))

        HorizontalDivider(color = SurfaceBorder)

        // Average row
        TableRow(cells = listOf(
            "Média" to 0.2f,
            r.avgCurrentNsPerDp.formatNs() to 0.4f,
            r.avgLegacyNsPerDp.formatNs() to 0.4f
        ), isHighlight = true)
    }
}

@Composable
private fun TableRow(
    cells: List<Pair<String, Float>>,
    isHeader: Boolean = false,
    isHighlight: Boolean = false,
    rowColor: Color = Color.Transparent
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(rowColor)
            .padding(horizontal = 10.dp, vertical = if (isHeader) 6.dp else 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        cells.forEach { (text, weight) ->
            Text(
                text,
                color = when {
                    isHeader -> TextSecondary
                    isHighlight -> AccentGreen
                    else -> TextPrimary
                },
                fontSize = if (isHeader) 10.sp else 11.sp,
                fontWeight = if (isHeader || isHighlight) FontWeight.Bold else FontWeight.Medium,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.weight(weight)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SHARED DASHBOARD COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun DashboardSectionHeader(icon: String, label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(icon, fontSize = 16.sp)
        Spacer(Modifier.width(8.dp))
        Text(
            label,
            color      = color,
            fontSize   = 13.sp,
            fontWeight = FontWeight.Bold
        )
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
        Column(
            modifier = Modifier.padding(16.dp),
            content  = content
        )
    }
}

@Composable
private fun MetricRow(label: String, value: String, color: Color, isHighlight: Boolean = false) {
    Row(
        modifier            = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment   = Alignment.CenterVertically
    ) {
        Text(
            label,
            color      = if (isHighlight) TextPrimary else TextSecondary,
            fontSize   = if (isHighlight) 13.sp else 12.sp,
            fontWeight = if (isHighlight) FontWeight.SemiBold else FontWeight.Normal
        )
        Text(
            value,
            color      = if (isHighlight) color else color.copy(alpha = 0.85f),
            fontSize   = if (isHighlight) 14.sp else 12.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun PathRow(label: String, avgNs: Long, color: Color) {
    Row(
        modifier            = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.07f))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment   = Alignment.CenterVertically
    ) {
        Text(label, color = TextSecondary, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
        Text(avgNs.formatNs(), color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// STRESS LIST ITEM
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * EN A single stress-test list item resolving multiple AppDimens dimension types.
 *    Dimensions are resolved ONCE and cached to avoid redundant Composable calls.
 * PT Um único item da lista de teste de estresse resolvendo múltiplos tipos de dimensão AppDimens.
 *    As dimensões são resolvidas UMA VEZ e armazenadas para evitar chamadas Composable redundantes.
 *
 * @param id EN Item identifier. PT Identificador do item.
 */
@Composable
fun BenchmarkItem(id: Int) {
    val density = LocalDensity.current

    val pad4  = 4.sdp
    val pad8  = 8.sdp
    val box40 = 40.sdp
    val sp12  = 12.wdp
    val fs14  = 14.sdp
    val h80   = 80.hdp

    val even = id % 2 == 0
    val cardColor = if (even) SurfaceCard else SurfaceCard.copy(alpha = 0.7f)
    val shape = remember { RoundedCornerShape(10.dp) }
    val boxShape = remember { RoundedCornerShape(8.dp) }
    val boxBrush = remember(even) {
        Brush.linearGradient(
            colors = if (even)
                listOf(AccentCyan.copy(alpha = 0.7f), AccentPurple.copy(alpha = 0.5f))
            else
                listOf(AccentAmber.copy(alpha = 0.7f), AccentRed.copy(alpha = 0.5f))
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = pad4)
            .clip(shape)
            .background(cardColor)
            .padding(pad8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(box40)
                .clip(boxShape)
                .background(boxBrush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${id % 100}",
                color      = Color.White,
                fontSize   = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.width(sp12))

        Column {
            Text(
                "Item #$id",
                color      = TextPrimary,
                fontSize   = with(density) { fs14.toSp() },
                fontWeight = FontWeight.Medium
            )
            Text(
                "h:${h80.value.toInt()}dp · w:${sp12.value.toInt()}dp · s:${box40.value.toInt()}dp",
                color    = TextSecondary,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}
