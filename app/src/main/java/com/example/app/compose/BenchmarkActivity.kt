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

class BenchmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BenchmarkScreen()
        }
    }
}

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
                        // Using public non-composable API for the stress test
                        DimenSdp.sdp(context, 100)
                        DimenSdp.hdp(context, 50)
                        DimenSdp.wdp(context, 30)
                        DimenSdp.sdpa(context, 40)
                    }
                }
                
                // Total resolutions = repeatCount * 4
                val avg = totalNs / (repeatCount * 4)
                benchmarkResult = "$avg ns per resolution (Avg of ${repeatCount * 4})"
                println("--- UI_BENCHMARK_RESULT: $avg ns ---")
            }
        }
    }
}

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
