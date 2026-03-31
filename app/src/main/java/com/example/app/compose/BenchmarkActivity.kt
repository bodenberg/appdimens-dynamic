/**
 * @author Bodenberg
 *
 * EN Performance results activity — visual stress test and throughput metrics.
 * PT Atividade de resultados de performance — teste de estresse visual e métricas de taxa de transferência.
 */
package com.example.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp
import kotlinx.coroutines.delay
import kotlin.system.measureNanoTime

/**
 * EN Activity that runs automated and visual benchmarks for the AppDimens library.
 * PT Atividade que executa benchmarks automatizados e visuais para a biblioteca AppDimens.
 */
class BenchmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BenchmarkScreen()
        }
    }
}

/**
 * EN Main screen of the benchmark, coordinating the stress test and displaying the UI list.
 * PT Tela principal do benchmark, coordenando o teste de estresse e exibindo a lista de UI.
 */
@Composable
fun BenchmarkScreen() {
    var benchmarkResult by remember { mutableStateOf("Pending...") }
    val itemsToRender = (1..1000).toList()

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "AppDimens UI Benchmark",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "Performance: $benchmarkResult",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(itemsToRender) { id ->
                    BenchmarkItem(id)
                }
            }
            
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                benchmarkResult = "Running stress test..."
                delay(1500)
                
                val repeatCount = 10000
                val totalNs = measureNanoTime {
                    repeat(repeatCount) {
                        // NOTE: sdp/hdp/wdp without AR hit the FAST BYPASS in DimenCache.getOrPut()
                        // (CalcType.SCALED, bit 63 == 0 → bypasses cache entirely ~2 ns raw math).
                        // sdpa (with AR) uses the real cache lookup path (~5–35 ns).
                        // This benchmark therefore measures:
                        //   3 × ~2ns (raw math bypass) + 1 × ~35ns (cache hit with AR)
                        // NOT "4 × cache hit". The reported average will be dominated by
                        // the AR path and will be significantly higher than 5 ns per call.
                        DimenSdp.sdp(context, 100)   // bypass (~2 ns)
                        DimenSdp.hdp(context, 50)    // bypass (~2 ns)
                        DimenSdp.wdp(context, 30)    // bypass (~2 ns)
                        DimenSdp.sdpa(context, 40)   // cache  (~35 ns on warm cache)
                    }
                }
                
                // Total resolutions = repeatCount * 4
                // Average is skewed by the 3 bypassed calls; use with caution.
                val avg = totalNs / (repeatCount * 4)
                benchmarkResult = "$avg ns avg/resolution · sdp×3 bypass + sdpa×1 cached (${repeatCount*4} total)"
                println("--- UI_BENCHMARK_RESULT: $avg ns (3×bypass + 1×AR-cache per iteration) ---")
            }
        }
    }
}

/**
 * EN A single list item used for visual stress testing, resolving multiple dimensions.
 * PT Um único item da lista usado para teste de estresse visual, resolvendo múltiplas dimensões.
 *
 * @param id EN Item identifier. PT Identificador do item.
 */
@Composable
fun BenchmarkItem(id: Int) {
    val density = LocalDensity.current
    
    // Cache the resolved values to avoid multiple @Composable calls in the modifier chain
    val sdp4 = 4.sdp
    val sdp8 = 8.sdp
    val sdp40 = 40.sdp
    val wdp12 = 12.wdp
    val sdp14 = 14.sdp
    val hdp80 = 80.hdp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = sdp4)
            .background(Color.LightGray.copy(alpha = 0.2f))
            .padding(sdp8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(sdp40)
                .background(if (id % 2 == 0) Color.Cyan else Color.Magenta)
        )
        
        Spacer(modifier = Modifier.width(wdp12))
        
        Column {
            Text("Item #$id", fontSize = with(density) { sdp14.toSp() })
            Text("Scaling: ${hdp80.value}dp height-based", style = MaterialTheme.typography.bodySmall)
        }
    }
}
