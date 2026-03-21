/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 *
 * EN Compose ExampleActivity — Comprehensive demo of all AppDimens SDP features.
 * PT Compose ExampleActivity — Demonstração completa de todos os recursos do AppDimens SDP.
 */
package com.example.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType

// EN Core extensions: .sdp, .hdp, .wdp (based on smallest width, height, width)
// PT Extensões principais: .sdp, .hdp, .wdp (baseadas em menor largura, altura, largura)
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp

// EN Inverter shortcuts: orientation-aware dimension switching
// PT Atalhos inversores: troca de dimensão com base na orientação
import com.appdimens.dynamic.compose.sdpPh
import com.appdimens.dynamic.compose.sdpLw
import com.appdimens.dynamic.compose.hdpLw
import com.appdimens.dynamic.compose.wdpLh

// EN Facilitator extensions: conditional dimension resolution
// PT Extensões facilitadoras: resolução condicional de dimensão
import com.appdimens.dynamic.compose.sdpRotate
import com.appdimens.dynamic.compose.sdpMode
import com.appdimens.dynamic.compose.sdpQualifier
import com.appdimens.dynamic.compose.sdpScreen

// EN DimenScaled builder for complex conditional dimensions
// PT Builder DimenScaled para dimensões condicionais complexas
import com.appdimens.dynamic.compose.scaledDp

/**
 * EN An activity that demonstrates all AppDimens SDP features in Jetpack Compose.
 * PT Uma atividade que demonstra todos os recursos do AppDimens SDP no Jetpack Compose.
 */
class ExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SdpDemoScreen() }
    }
}

// ╔══════════════════════════════════════════════════════════════════════╗
// ║                        MAIN SCREEN                                ║
// ╚══════════════════════════════════════════════════════════════════════╝

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.sdp),
                verticalArrangement = Arrangement.spacedBy(20.sdp),
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
                    "Comprehensive examples of .sdp, .hdp, .wdp, inverters, facilitators, and DimenScaled",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // ── 1. CORE EXTENSIONS (sdp / hdp / wdp) ───────────────────
                SectionTitle("1. Core Extensions")

                // EN .sdp — scales based on smallest width (swDP)
                // PT .sdp — escala baseada na menor largura (swDP)
                ExampleCard(
                    title = ".sdp (Smallest Width)",
                    description = "60.sdp → adapts proportionally to the screen's smallest width qualifier.",
                    boxSize = 60.sdp,
                    boxColor = Color(0xFF42A5F5)
                )

                // EN .hdp — scales based on screen height (hDP)
                // PT .hdp — escala baseada na altura da tela (hDP)
                ExampleCard(
                    title = ".hdp (Screen Height)",
                    description = "80.hdp → adapts according to the screen height. Rotate to see the effect.",
                    boxSize = 80.hdp,
                    boxColor = Color(0xFFEF5350)
                )

                // EN .wdp — scales based on screen width (wDP)
                // PT .wdp — escala baseada na largura da tela (wDP)
                ExampleCard(
                    title = ".wdp (Screen Width)",
                    description = "100.wdp → adapts according to the screen width.",
                    boxSize = 100.wdp,
                    boxColor = Color(0xFF66BB6A)
                )

                // ── 2. INVERTER SHORTCUTS ───────────────────────────────────
                SectionTitle("2. Inverter Shortcuts")

                // EN .sdpPh — uses swDP normally, but in PORTRAIT acts as hDP
                // PT .sdpPh — usa swDP normalmente, mas em RETRATO atua como hDP
                ExampleCard(
                    title = ".sdpPh (SW → Portrait Height)",
                    description = "70.sdpPh → smallest width by default, but in portrait it resolves as height (hDP).",
                    boxSize = 70.sdpPh,
                    boxColor = Color(0xFFAB47BC)
                )

                // EN .sdpLw — uses swDP normally, but in LANDSCAPE acts as wDP
                // PT .sdpLw — usa swDP normalmente, mas em PAISAGEM atua como wDP
                ExampleCard(
                    title = ".sdpLw (SW → Landscape Width)",
                    description = "70.sdpLw → smallest width by default, but in landscape it resolves as width (wDP).",
                    boxSize = 70.sdpLw,
                    boxColor = Color(0xFF7E57C2)
                )

                // EN .hdpLw — uses hDP normally, but in LANDSCAPE acts as wDP
                // PT .hdpLw — usa hDP normalmente, mas em PAISAGEM atua como wDP
                ExampleCard(
                    title = ".hdpLw (Height → Landscape Width)",
                    description = "80.hdpLw → height by default, but in landscape it resolves as width (wDP).",
                    boxSize = 80.hdpLw,
                    boxColor = Color(0xFF5C6BC0)
                )

                // EN .wdpLh — uses wDP normally, but in LANDSCAPE acts as hDP
                // PT .wdpLh — usa wDP normalmente, mas em PAISAGEM atua como hDP
                ExampleCard(
                    title = ".wdpLh (Width → Landscape Height)",
                    description = "90.wdpLh → width by default, but in landscape it resolves as height (hDP).",
                    boxSize = 90.wdpLh,
                    boxColor = Color(0xFF26A69A)
                )

                // ── 3. FACILITATOR EXTENSIONS ──────────────────────────────
                SectionTitle("3. Facilitator Extensions")

                // EN sdpRotate — use a different value when in landscape
                // PT sdpRotate — usa um valor diferente quando em paisagem
                ExampleCard(
                    title = "sdpRotate (Rotation Override)",
                    description = "80.sdpRotate(50) → 80.sdp in portrait, but 50.sdp in landscape.",
                    boxSize = 80.sdpRotate(50),
                    boxColor = Color(0xFFFF7043)
                )

                // EN sdpRotate with custom qualifier and orientation
                // PT sdpRotate com qualificador e orientação customizados
                ExampleCard(
                    title = "sdpRotate (Custom Qualifier)",
                    description = "60.sdpRotate(40, DpQualifier.HEIGHT, Orientation.PORTRAIT) → 60.sdp default, 40.hdp in portrait.",
                    boxSize = 60.sdpRotate(
                        rotationValue = 40,
                        finalQualifierResolver = DpQualifier.HEIGHT,
                        orientation = Orientation.PORTRAIT
                    ),
                    boxColor = Color(0xFFFF8A65)
                )

                // EN sdpMode — change value for specific UI modes (TV, Watch, etc.)
                // PT sdpMode — altera o valor para modos de UI específicos (TV, Watch, etc.)
                ExampleCard(
                    title = "sdpMode (UiModeType Override)",
                    description = "80.sdpMode(200, UiModeType.TELEVISION) → 80.sdp by default, 200.sdp on TV.",
                    boxSize = 80.sdpMode(200, UiModeType.TELEVISION),
                    boxColor = Color(0xFFEC407A)
                )

                // EN sdpQualifier — change value when screen metric meets a threshold
                // PT sdpQualifier — altera o valor quando a métrica da tela atinge um limite
                ExampleCard(
                    title = "sdpQualifier (Dp Qualifier Override)",
                    description = "60.sdpQualifier(120, DpQualifier.SMALL_WIDTH, 600) → 60.sdp default, 120.sdp when sw ≥ 600.",
                    boxSize = 60.sdpQualifier(
                        qualifiedValue = 120,
                        qualifierType = DpQualifier.SMALL_WIDTH,
                        qualifierValue = 600
                    ),
                    boxColor = Color(0xFF26C6DA)
                )

                // EN sdpScreen — combined UiModeType + DpQualifier condition
                // PT sdpScreen — condição combinada UiModeType + DpQualifier
                ExampleCard(
                    title = "sdpScreen (Combined Override)",
                    description = "70.sdpScreen(150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600) → 70.sdp default, 150.sdp on TV with sw ≥ 600.",
                    boxSize = 70.sdpScreen(
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
        modifier = Modifier.padding(vertical = 4.sdp),
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
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
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
    // EN The DimenScaled builder: define base value + conditional overrides.
    // PT O builder DimenScaled: define valor base + substituições condicionais.
    val dynamicDp = 100.scaledDp()
        // EN Priority 1: TV with smallest width >= 600 → use 250dp
        // PT Prioridade 1: TV com menor largura >= 600 → usar 250dp
        .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, 250)
        // EN Priority 2: Any TV → use 500dp
        // PT Prioridade 2: Qualquer TV → usar 500dp
        .screen(UiModeType.TELEVISION, 500)
        // EN Priority 2: Foldable open → use 200dp
        // PT Prioridade 2: Dobrável aberto → usar 200dp
        .screen(UiModeType.FOLD_OPEN, 200)
        // EN Priority 3: Any device with smallest width >= 600 → use 150dp
        // PT Prioridade 3: Qualquer dispositivo com menor largura >= 600 → usar 150dp
        .screen(DpQualifier.SMALL_WIDTH, 600, 150)
        // EN Priority 4: Landscape orientation → use 120dp
        // PT Prioridade 4: Orientação paisagem → usar 120dp
        .screen(Orientation.LANDSCAPE, 120)
        // EN Resolve using .sdp (smallest width) adaptation
        // PT Resolve usando adaptação .sdp (menor largura)
        .sdp

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(10.sdp)
        ) {
            Text(
                "DimenScaled Builder",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                buildString {
                    appendLine("100.scaledDp()")
                    appendLine("  .screen(TV + sw>=600 → 250)")
                    appendLine("  .screen(TV → 500)")
                    appendLine("  .screen(FOLD_OPEN → 200)")
                    appendLine("  .screen(sw>=600 → 150)")
                    appendLine("  .screen(LANDSCAPE → 120)")
                    appendLine("  .sdp")
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