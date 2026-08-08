package com.appdimens.dynamic.interpolated

import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object InterpolatedFactors {
    /** Derived from the snapshot active for the current resolver call. */
    val scale: Float
        get() = 1f + (DimenCache.currentMetrics.smallestWidthDp * DimenCache.INV_BASE_RATIO - 1f) * 0.5f
}
