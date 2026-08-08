package com.appdimens.dynamic.interpolated

import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object InterpolatedFactors {
    /** Memoized on the snapshot; identical math, computed once per DimenMetrics. */
    val scale: Float
        get() = DimenCache.currentMetrics.interpolatedScale
}
