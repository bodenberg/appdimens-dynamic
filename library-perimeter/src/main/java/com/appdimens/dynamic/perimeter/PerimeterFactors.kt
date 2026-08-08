package com.appdimens.dynamic.perimeter

import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object PerimeterFactors {
    /** Memoized on the snapshot; one div per DimenMetrics instead of per call. */
    val scale: Float
        get() = DimenCache.currentMetrics.perimeterScale
}
