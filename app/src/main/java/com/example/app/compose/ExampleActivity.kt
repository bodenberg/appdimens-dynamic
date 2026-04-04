/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 *
 * EN Compose ExampleActivity — Sections 1–4 mirror `kotlin`/`java` ExampleActivity (scaled/percent/… via strategy menu).
 *    §5 Auto-resize is Compose UI (`compose.resize`); the View/code twin is [com.appdimens.dynamic.code.resize.DimenResize] (see §6 there).
 * PT Compose — Secções 1–4 alinhadas às ExampleActivity Kotlin/Java (menu de estratégia).
 *    §5 Auto-resize é UI Compose; equivalente em código/views é [DimenResize] (secção 6 nas outras activities).
 */
package com.example.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.resize.autoResizeHeightSize
import com.appdimens.dynamic.compose.resize.autoResizeSquareSize
import com.appdimens.dynamic.compose.resize.autoResizeTextSp
import com.appdimens.dynamic.compose.resize.autoResizeWidthSize
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.compose.ssp

// EN Demo routes the same patterns to scaled / percent / … via [LocalDemoCalcStrategy] (default: Scaled).
// PT O demo encaminha os mesmos padrões para scaled / percent / … via [LocalDemoCalcStrategy] (padrão: Scaled).
import com.appdimens.dynamic.core.AppDimensProvider

/**
 * EN An activity that demonstrates all AppDimens SDP features in Jetpack Compose.
 * PT Uma atividade que demonstra todos os recursos do AppDimens SDP no Jetpack Compose.
 */
class ExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDimensProvider {
                SdpDemoScreen()
            }
        }
    }
}

// ╔══════════════════════════════════════════════════════════════════════╗
// ║                        MAIN SCREEN                                ║
// ╚══════════════════════════════════════════════════════════════════════╝

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    var calcStrategy by remember { mutableStateOf(DemoCalcStrategy.Scaled) }
    var calcMenuExpanded by remember { mutableStateOf(false) }
    MaterialTheme(colorScheme = lightColorScheme()) {
        CompositionLocalProvider(LocalDemoCalcStrategy provides calcStrategy) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.demoSwDp),
                    verticalArrangement = Arrangement.spacedBy(20.demoSwDp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // EN Main title
                    // PT Título principal
                    Text(
                        "AppDimens SDP Demo",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        "Examples follow the calculation type below (default: Scaled). Same APIs as compose.scaled, routed per strategy.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Os exemplos usam o tipo de cálculo abaixo (padrão: Scaled). Mesmos padrões que compose.scaled, por estratégia.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )

                    // EN / PT Calculation type selector (default Scaled)
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { calcMenuExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.demoSwDp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = buildString {
                                    append(calcStrategy.labelEn)
                                    append(" — ")
                                    append(calcStrategy.labelPt)
                                },
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        DropdownMenu(
                            expanded = calcMenuExpanded,
                            onDismissRequest = { calcMenuExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            DemoCalcStrategy.entries.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Text("${option.labelEn} · ${option.labelPt}")
                                    },
                                    onClick = {
                                        calcStrategy = option
                                        calcMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // EN Navigation to BenchmarkActivity
                    // PT Navegação para BenchmarkActivity
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            context.startActivity(Intent(context, BenchmarkActivity::class.java))
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.demoSwDp),
                        shape = RoundedCornerShape(12.demoSwDp),
                        contentPadding = PaddingValues(12.demoSwDp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Speed,
                            contentDescription = null,
                            modifier = Modifier.size(24.demoSwDp)
                        )
                        Spacer(modifier = Modifier.width(12.demoSwDp))
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Run Performance Benchmark",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Executar Benchmark de Performance",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.demoSwDp)
                        )
                    }

                    // ── 1. CORE EXTENSIONS (sdp / hdp / wdp) ───────────────────
                    SectionTitle("1. Core Extensions")

                    // EN Smallest-width style dimension for the selected strategy (.sdp in scaled, .psdp in percent, …)
                    // PT Dimensão estilo menor largura para a estratégia escolhida (.sdp em scaled, .psdp em percent, …)
                    ExampleCard(
                        title = "Smallest width (strategy dp)",
                        description = "60 × selected formula (e.g. 60.sdp in Scaled, 60.psdp in Percent).",
                        boxSize = 60.demoSwDp,
                        boxColor = Color(0xFF42A5F5)
                    )

                    ExampleCard(
                        title = "Smallest width + aspect ratio",
                        description = "60 × selected formula with AR (e.g. 60.sdpa in Scaled).",
                        boxSize = 60.demoSwDpa,
                        boxColor = Color(0xFF12D5B5)
                    )

                    // EN .hdp — scales based on screen height (hDP)
                    // PT .hdp — escala baseada na altura da tela (hDP)
                    ExampleCard(
                        title = ".hdp equivalent (Screen Height)",
                        description = "80 × height qualifier for the selected strategy.",
                        boxSize = 80.demoHdp,
                        boxColor = Color(0xFFEF5350)
                    )

                    // EN .wdp — scales based on screen width (wDP)
                    // PT .wdp — escala baseada na largura da tela (wDP)
                    ExampleCard(
                        title = ".wdp equivalent (Screen Width)",
                        description = "100 × width qualifier for the selected strategy.",
                        boxSize = 100.demoWdp,
                        boxColor = Color(0xFF66BB6A)
                    )

                    // ── 2. INVERTER SHORTCUTS ───────────────────────────────────
                    SectionTitle("2. Inverter Shortcuts")

                    ExampleCard(
                        title = ".sdpPh equivalent (SW → Portrait Height)",
                        description = "70 with SW→PH inverter for the selected strategy.",
                        boxSize = 70.demoSwPh,
                        boxColor = Color(0xFFAB47BC)
                    )

                    ExampleCard(
                        title = ".sdpLw equivalent (SW → Landscape Width)",
                        description = "70 with SW→LW inverter for the selected strategy.",
                        boxSize = 70.demoSwLw,
                        boxColor = Color(0xFF7E57C2)
                    )

                    ExampleCard(
                        title = ".hdpLw equivalent (Height → Landscape Width)",
                        description = "80 with height→landscape-width inverter.",
                        boxSize = 80.demoHLw,
                        boxColor = Color(0xFF5C6BC0)
                    )

                    ExampleCard(
                        title = ".wdpLh equivalent (Width → Landscape Height)",
                        description = "90 with width→landscape-height inverter.",
                        boxSize = 90.demoWLh,
                        boxColor = Color(0xFF26A69A)
                    )

                    // ── 3. FACILITATOR EXTENSIONS ──────────────────────────────
                    SectionTitle("3. Facilitator Extensions")

                    ExampleCard(
                        title = "sdpRotate (Rotation Override)",
                        description = "80 base, 50 when rotation rule matches (per strategy).",
                        boxSize = 80.demoSdpRotate(50),
                        boxColor = Color(0xFFFF7043)
                    )

                    ExampleCard(
                        title = "sdpRotate (Custom Qualifier)",
                        description = "60 with custom qualifier/orientation (per strategy).",
                        boxSize = 60.demoSdpRotate(
                            rotationValue = 40,
                            finalQualifierResolver = DpQualifier.HEIGHT,
                            orientation = Orientation.PORTRAIT
                        ),
                        boxColor = Color(0xFFFF8A65)
                    )

                    ExampleCard(
                        title = "sdpMode (UiModeType Override)",
                        description = "80 default, 200 on TELEVISION (per strategy).",
                        boxSize = 80.demoSdpMode(200, UiModeType.TELEVISION),
                        boxColor = Color(0xFFEC407A)
                    )

                    ExampleCard(
                        title = "sdpQualifier (Dp Qualifier Override)",
                        description = "60 default, 120 when sw ≥ 600 (per strategy).",
                        boxSize = 60.demoSdpQualifier(
                            qualifiedValue = 120,
                            qualifierType = DpQualifier.SMALL_WIDTH,
                            qualifierValue = 600
                        ),
                        boxColor = Color(0xFF26C6DA)
                    )

                    ExampleCard(
                        title = "sdpScreen (Combined Override)",
                        description = "70 default, 150 on TV with sw ≥ 600 (per strategy).",
                        boxSize = 70.demoSdpScreen(
                            screenValue = 150,
                            uiModeType = UiModeType.TELEVISION,
                            qualifierType = DpQualifier.SMALL_WIDTH,
                            qualifierValue = 600
                        ),
                        boxColor = Color(0xFF78909C)
                    )

                    // ── 4. DimenScaled BUILDER (Complex Conditions) ────────────
                    SectionTitle("4. DimenScaled Builder")

                    DimenScaledExampleCard()

                    // ── 5. AUTO-RESIZE (BoxWithConstraints) ───────────────────
                    // EN Like TextView auto-size: pick the largest size in min…max (step) that still fits.
                    // PT Como autoSize do TextView: escolhe o maior tamanho no intervalo que ainda cabe.
                    SectionTitle("5. Auto-resize (DimenResize)")

                    AutoResizeExamplesCard()
                }
            }
        }
    }
}

// ╔══════════════════════════════════════════════════════════════════════╗
// ║                       COMPOSABLE COMPONENTS                       ║
// ╚══════════════════════════════════════════════════════════════════════╝

/**
 * EN Section divider title.
 * PT Título divisor de seção.
 */
@Composable
fun SectionTitle(title: String) {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 4.demoSwDp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Text(
        title,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * EN Generic card demonstrating a dimension example.
 * PT Cartão genérico demonstrando um exemplo de dimensão.
 *
 * @param title EN The example title.   PT O título do exemplo.
 * @param description EN The description.   PT A descrição.
 * @param boxSize EN The adaptive Dp value.   PT O valor adaptável em Dp.
 * @param boxColor EN The box background color.   PT A cor de fundo da caixa.
 */
@Composable
fun ExampleCard(title: String, description: String, boxSize: Dp, boxColor: Color = MaterialTheme.colorScheme.primary) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.demoSwDp),
            verticalArrangement = Arrangement.spacedBy(10.demoSwDp)
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodySmall, color = Color(0xFF616161))

            // EN Demonstration Box showing the resolved dimension
            // PT Caixa de demonstração mostrando a dimensão resolvida
            Box(
                modifier = Modifier
                    .size(boxSize)
                    .background(boxColor, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${boxSize.value.toInt()}dp",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * EN Shows [com.appdimens.dynamic.compose.resize] inside [BoxWithConstraints]: text sp range, square side, width bar.
 * PT Mostra resize no [BoxWithConstraints]: texto (sp), quadrado (lado), barra (largura).
 */
@Composable
fun AutoResizeExamplesCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.demoSwDp),
            verticalArrangement = Arrangement.spacedBy(16.demoSwDp)
        ) {
            Text(
                "Auto-resize — fits content to the box",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                buildString {
                    appendLine("EN: Use inside BoxWithConstraints. APIs: autoResizeTextSp, autoResizeSquareSize, autoResizeWidthSize, autoResizeHeightSize.")
                    appendLine("PT: Use dentro de BoxWithConstraints. Texto aceita Number (sp), .sp ou .dp (valor numérico = sp).")
                    appendLine("Sizes: Dp ou Number (interpretado como dp).")
                },
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF2E7D32)
            )

            // EN Text: largest sp in range that fits width/height of this box
            // PT Texto: maior sp no intervalo que cabe na largura/altura desta caixa
            Text("Text (autoResizeTextSp)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .border(1.dp, Color(0xFF81C784), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                val sample = "This sentence scales between min and max sp so it fits the box (try rotation)."
                val fontSize = autoResizeTextSp(
                    text = sample,
                    minSp = 10,
                    maxSp = 22,
                    stepSp = 1,
                    maxLines = 3,
                )
                Text(
                    text = sample,
                    fontSize = fontSize,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // EN Same API with TextUnit overload (explicit .sp)
            // PT Mesma API com overload TextUnit (.sp explícito)
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 44.dp)
                    .border(1.dp, Color(0xFF66BB6A), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                val short = "Short · 8.sp…18.sp, test - .............................."
                val fontSizeSp = autoResizeTextSp(
                    text = short,
                    minSp = 8.ssp,
                    maxSp = 18.ssp,
                    stepSp = 1.sp,
                    maxLines = 1
                )
                Text(text = short, fontSize = fontSizeSp, color = MaterialTheme.colorScheme.onSurface)
            }

            // EN Square: largest side min…max that fits inside constraints
            // PT Quadrado: maior lado min…max que cabe nas restrições
            Text("Square (autoResizeSquareSize)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color(0xFFA5D6A7), RoundedCornerShape(8.dp))
            ) {
                val side = autoResizeSquareSize(min = 16, max = 100, step = 4)
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(side)
                        .background(Color(0xFF43A047), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${side.value.toInt()}dp",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // EN Width + height bars (separate axes)
            // PT Barras de largura e altura (eixos separados)
            Text("Width / height (autoResizeWidthSize · autoResizeHeightSize)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.demoSwDp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .border(1.dp, Color(0xFFC8E6C9), RoundedCornerShape(8.dp))
                ) {
                    val w = autoResizeWidthSize(min = 20.dp, max = maxWidth, step = 4.dp)
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(w)
                            .height(36.dp)
                            .background(Color(0xFF1B5E20), RoundedCornerShape(6.dp))
                    )
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp)
                        .border(1.dp, Color(0xFFC8E6C9), RoundedCornerShape(8.dp))
                ) {
                    val h = autoResizeHeightSize(min = 16, max = 56, step = 4)
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(40.dp)
                            .height(h)
                            .background(Color(0xFF33691E), RoundedCornerShape(6.dp))
                    )
                }
            }
        }
    }
}

/**
 * EN Demonstrates the `scaledDp()` DimenScaled builder pattern.
 * This allows defining a base dimension value and multiple overrides
 * for different screen conditions (UI mode, qualifiers, orientation).
 *
 * PT Demonstra o padrão builder `scaledDp()` DimenScaled.
 * Permite definir um valor base de dimensão e múltiplas substituições
 * para diferentes condições de tela (modo UI, qualificadores, orientação).
 */
@Composable
fun DimenScaledExampleCard() {
    // EN Same builder chain per strategy (scaledDp / percentDp / …), terminal .sdp on each builder type.
    // PT Mesma cadeia por estratégia (scaledDp / percentDp / …), terminal .sdp em cada builder.
    val dynamicDp = demoDimenScaledResultDp()

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.demoSwDp),
            verticalArrangement = Arrangement.spacedBy(10.demoSwDp)
        ) {
            Text(
                "DimenScaled-style builder (per strategy)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                buildString {
                    appendLine("100 × strategy builder + .screen(...) chain + .sdp")
                    appendLine("  .screen(TV + sw>=600 → 250)")
                    appendLine("  .screen(TV → 500)")
                    appendLine("  .screen(FOLD_OPEN → 200)")
                    appendLine("  .screen(sw>=600 → 150)")
                    appendLine("  .screen(LANDSCAPE → 120)")
                    append("Current: ${dynamicDp.value.toInt()}dp")
                },
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF616161)
            )

            Box(
                modifier = Modifier
                    .size(dynamicDp)
                    .background(Color(0xFFFF9800), RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${dynamicDp.value.toInt()}dp",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ╔══════════════════════════════════════════════════════════════════════╗
// ║                           PREVIEW                                 ║
// ╚══════════════════════════════════════════════════════════════════════╝

@Preview(showBackground = true, device = "id:pixel_xl", showSystemUi = true)
@Composable
fun PreviewSdpDemoPhone() {
    SdpDemoScreen()
}

@Preview(showBackground = true, device = "id:pixel_tablet", showSystemUi = true)
@Composable
fun PreviewSdpDemoTablet() {
    SdpDemoScreen()
}