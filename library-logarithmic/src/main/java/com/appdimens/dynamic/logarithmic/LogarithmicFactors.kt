package com.appdimens.dynamic.logarithmic

import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object LogarithmicFactors {
    /** Memoized on the snapshot; `ln` runs once per DimenMetrics, not per call. */
    val scale: Float
        get() = DimenCache.currentMetrics.logarithmicScale
}
