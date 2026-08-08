package com.appdimens.dynamic.power

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object PowerFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() {
            val ratio = DimenCache.currentMetrics.smallestWidthDp / DesignScaleConstants.BASE_WIDTH_DP
            return Math.pow(ratio.toDouble(), 0.75).toFloat()
        }
}
