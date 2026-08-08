package com.appdimens.dynamic.interpolated

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object InterpolatedFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() = 1f + (DimenCache.currentMetrics.smallestWidthDp * DimenCache.INV_BASE_RATIO - 1f) * 0.5f
}
