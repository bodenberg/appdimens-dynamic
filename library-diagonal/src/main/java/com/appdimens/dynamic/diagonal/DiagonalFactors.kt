package com.appdimens.dynamic.diagonal

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object DiagonalFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() {
            val m = DimenCache.currentMetrics
            val diag = kotlin.math.sqrt(
                (m.minDimensionDp * m.minDimensionDp + m.maxDimensionDp * m.maxDimensionDp).toDouble()
            ).toFloat()
            return diag / DesignScaleConstants.BASE_DIAGONAL_DP
        }
}
