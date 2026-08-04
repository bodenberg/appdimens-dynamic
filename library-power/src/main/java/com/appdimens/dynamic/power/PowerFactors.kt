package com.appdimens.dynamic.power

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.StrategyFactorRegistry

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object PowerFactors {
    @JvmField @Volatile
    var scale: Float = 1.0f

    init {
        StrategyFactorRegistry.register { m ->
        val ratio = m.smallestWidthDp / DesignScaleConstants.BASE_WIDTH_DP
        scale = Math.pow(ratio.toDouble(), 0.75).toFloat()
        }
    }
}
