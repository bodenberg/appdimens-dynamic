package com.appdimens.dynamic.interpolated

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.StrategyFactorRegistry

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object InterpolatedFactors {
    @JvmField @Volatile
    var scale: Float = 1.0f

    init {
        StrategyFactorRegistry.register { m ->
        scale = 1f + (m.smallestWidthDp * DimenCache.INV_BASE_RATIO - 1f) * 0.5f
        }
    }
}
