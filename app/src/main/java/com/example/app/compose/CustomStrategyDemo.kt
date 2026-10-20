/**
 * EN Custom scaling strategies demo (`appdimens-dynamic-custom`): DSL, presets, scope, explain().
 * PT Demo de estratégias de escala personalizadas: DSL, presets, escopo, explain().
 */
package com.example.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.custom.ProvideScalingStrategy
import com.appdimens.dynamic.compose.custom.cdp
import com.appdimens.dynamic.compose.custom.csp
import com.appdimens.dynamic.compose.custom.explainCurrent
import com.appdimens.dynamic.custom.CustomPresets
import com.appdimens.dynamic.custom.DeviceClass
import com.appdimens.dynamic.custom.ScalingStrategy
import com.appdimens.dynamic.custom.scalingStrategy

/**
 * EN App-specific strategy: designed on a 360 dp phone, sublinear growth, damped on tablets.
 * PT Estratégia do app: desenhada em phone de 360 dp, crescimento sublinear, amortecida em tablets.
 */
val DemoBrand: ScalingStrategy = scalingStrategy("demoBrand") {
    reference(width = 360, height = 640)
    curve = power(0.8f)
    clamp(min = 0.85f, max = 1.6f)
    clampFontScale(1.3f)
    on(DeviceClass.TABLET, DeviceClass.DESKTOP, name = "large") { curve = logarithmic(0.4f) }
    landscape { multiply(0.95f) }
}

@Composable
fun CustomStrategyExampleCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.cdp(DemoBrand)),
            verticalArrangement = Arrangement.spacedBy(10.cdp(DemoBrand))
        ) {
            Text("Custom strategies (cdp / csp)", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(DemoBrand.explainCurrent(), fontSize = 11.csp(DemoBrand), color = Color(0xFF6A1B9A))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                DemoBox("demoBrand", 48.cdp(DemoBrand))
                DemoBox("tabletFriendly", 48.cdp(CustomPresets.TABLET_FRIENDLY))
                DemoBox("gameHud", 48.cdp(CustomPresets.GAME_HUD))
            }
            // EN Every cdp/csp without an explicit strategy inside the scope uses DemoBrand.
            // PT Todo cdp/csp sem estratégia explícita dentro do escopo usa DemoBrand.
            ProvideScalingStrategy(DemoBrand) {
                Text("Scoped: 14.csp / 24.cdp", fontSize = 14.csp, modifier = Modifier.padding(start = 24.cdp))
            }
        }
    }
}

@Composable
private fun DemoBox(label: String, size: androidx.compose.ui.unit.Dp) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(size).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)))
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
