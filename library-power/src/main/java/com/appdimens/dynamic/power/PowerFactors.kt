package com.appdimens.dynamic.power

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object PowerFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() {
            val ratio = DimenCache.currentMetrics.smallestWidthDp / DesignScaleConstants.BASE_WIDTH_DP
            return Math.pow(ratio.toDouble(), 0.75).toFloat()
        }
}
