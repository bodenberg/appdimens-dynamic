/**
 * EN Strategy factor registry — satellites register config-change callbacks so the
 * core [DimenCache] never pre-computes scales for strategies absent from the classpath.
 *
 * PT Registry de fatores: satélites registram callbacks; o core não pré-calcula
 * escalas de estratégias que não estão no APK.
 */
package com.appdimens.dynamic.core

import android.content.res.Configuration
import java.util.concurrent.CopyOnWriteArrayList

/**
 * EN Shared screen metrics computed once in [DimenCache] on configuration change.
 * PT Métricas compartilhadas calculadas uma vez no core em mudança de configuração.
 */
data class SharedScreenMetrics(
    val smallestWidthDp: Float,
    val minDimDp: Float,
    val maxDimDp: Float,
    val density: Float,
    val scale: Float,
    val normalizedAr: Float,
    val logNormalizedAr: Float,
    val arMultiplier: Float,
    val aspectRatioMul: Float,
)

fun interface StrategyFactorContributor {
    fun onScreenFactorsUpdated(metrics: SharedScreenMetrics)
}

/**
 * EN Process-wide registry. [register] is idempotent per instance and immediately
 * replays the last metrics so late class-loading still receives current scales.
 *
 * PT Registry de processo; [register] reaplica as últimas métricas (class-load tardio).
 */
object StrategyFactorRegistry {
    private val contributors = CopyOnWriteArrayList<StrategyFactorContributor>()

    @Volatile
    private var lastMetrics: SharedScreenMetrics? = null

    @JvmStatic
    fun register(contributor: StrategyFactorContributor) {
        if (!contributors.contains(contributor)) {
            contributors.add(contributor)
        }
        lastMetrics?.let { contributor.onScreenFactorsUpdated(it) }
    }

    @JvmStatic
    fun unregister(contributor: StrategyFactorContributor) {
        contributors.remove(contributor)
    }

    @JvmStatic
    internal fun publish(metrics: SharedScreenMetrics) {
        lastMetrics = metrics
        for (contributor in contributors) {
            contributor.onScreenFactorsUpdated(metrics)
        }
    }

    /** EN Test helper — clears contributors and last metrics. PT Auxiliar de teste. */
    @JvmStatic
    internal fun resetForTest() {
        contributors.clear()
        lastMetrics = null
    }

    @JvmStatic
    internal fun lastMetricsForTest(): SharedScreenMetrics? = lastMetrics
}

/**
 * EN Builds [SharedScreenMetrics] from a [Configuration] using the same formulas as
 * the former monolithic [DimenCache] shared path (scale / AR / density).
 *
 * PT Constrói métricas compartilhadas a partir de [Configuration].
 */
internal fun sharedMetricsFrom(config: Configuration): SharedScreenMetrics {
    val sw = config.smallestScreenWidthDp.toFloat()
    val maxDim = maxOf(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())
    val minDim = minOf(config.screenWidthDp.toFloat(), config.screenHeightDp.toFloat())
    val scale = sw * DimenCache.INV_BASE_RATIO
    val rawAr = if (minDim > 0) maxDim / minDim else 1.0f
    val normalizedAr = rawAr / 1.78f
    val logNormalizedAr = fastLn(normalizedAr)
    val diff = sw - 300f
    val adjustment = DimenCache.SENSITIVITY_DEFAULT * logNormalizedAr
    val arMultiplier = 1.0f + diff * (DimenCache.ADJUSTMENT_SCALE + adjustment)
    val density = config.densityDpi.toFloat() / 160f
    val aspectRatioMul = 1f + DimenCache.SENSITIVITY_DEFAULT * logNormalizedAr
    return SharedScreenMetrics(
        smallestWidthDp = sw,
        minDimDp = minDim,
        maxDimDp = maxDim,
        density = density,
        scale = scale,
        normalizedAr = normalizedAr,
        logNormalizedAr = logNormalizedAr,
        arMultiplier = arMultiplier,
        aspectRatioMul = aspectRatioMul,
    )
}
