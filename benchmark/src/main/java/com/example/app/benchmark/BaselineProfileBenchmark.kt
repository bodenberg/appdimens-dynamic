/**
 * EN Baseline profile generator for the AppDimens demo app.
 *
 *    The app's BenchmarkActivity already runs the full Micro + Macro workload on
 *    AUTO_START_FULL, so simply cold-starting it exercises the library's hot paths
 *    (fast lane, gates, getOrPut, buildKey) plus the 1000-item LazyColumn. A few
 *    extra flings cover direction variants and composition.
 *
 *    Run: ./gradlew :app:generateBaselineProfile  (device connected, API 30+)
 *
 * PT Gerador de baseline profile para o app demo AppDimens.
 *    O BenchmarkActivity do app já executa o workload Micro + Macro completo no
 *    AUTO_START_FULL; basta um cold start para exercitar os caminhos quentes da
 *    library (fast lane, gates, getOrPut, buildKey) e a LazyColumn de 1000 itens.
 *    Alguns flings extras cobrem variantes de direção e composição.
 */
package com.example.app.benchmark

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileBenchmark {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun benchmarkDashboardColdStartAndScroll() = baselineProfileRule.collect(
        packageName = "com.example.app"
    ) {
        pressHome()
        startActivityAndWait()
        device.wait(Until.hasObject(By.pkg("com.example.app")), 10_000)

        val list = device.wait(
            Until.findObject(By.scrollable(true)),
            10_000
        )
        if (list != null) {
            repeat(8) { list.fling(Direction.DOWN, 1) }
            repeat(8) { list.fling(Direction.UP, 1) }
        }
        device.waitForIdle()
    }
}