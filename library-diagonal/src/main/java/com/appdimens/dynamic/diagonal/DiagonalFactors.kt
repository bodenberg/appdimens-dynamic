package com.appdimens.dynamic.diagonal

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.StrategyFactorRegistry

/**
 * EN Precomputed default-path scale for this satellite; updated only when the
 * strategy module is on the classpath and [StrategyFactorRegistry] publishes.
 * PT Escala pré-computada deste satélite — só atualiza se o módulo estiver no APK.
 */
internal object DiagonalFactors {
    @JvmField @Volatile
    var scale: Float = 1.0f

    init {
        StrategyFactorRegistry.register { m ->
        val diag = kotlin.math.sqrt(
            (m.minDimDp * m.minDimDp + m.maxDimDp * m.maxDimDp).toDouble()
        ).toFloat()
        scale = diag / DesignScaleConstants.BASE_DIAGONAL_DP
        }
    }
}
