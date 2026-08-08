package com.appdimens.dynamic.perimeter

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object PerimeterFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() = (DimenCache.currentMetrics.minDimensionDp + DimenCache.currentMetrics.maxDimensionDp) /
            DesignScaleConstants.BASE_PERIMETER_DP
}
