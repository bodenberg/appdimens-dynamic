package com.appdimens.dynamic.logarithmic

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object LogarithmicFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() {
            val sw = DimenCache.currentMetrics.smallestWidthDp
            return when {
                sw > DesignScaleConstants.BASE_WIDTH_DP ->
                    1f + 0.4f * kotlin.math.ln(sw * DimenCache.INV_BASE_RATIO)
                sw > 0f ->
                    1f - 0.4f * kotlin.math.ln(DesignScaleConstants.BASE_WIDTH_DP / sw)
                else -> 1f
            }
        }
}
