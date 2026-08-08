package com.appdimens.dynamic.diagonal

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
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
