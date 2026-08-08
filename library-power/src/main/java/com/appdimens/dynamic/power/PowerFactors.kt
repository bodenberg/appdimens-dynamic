package com.appdimens.dynamic.power

import com.appdimens.dynamic.core.DimenCache

/**
 * EN Default-path scale for this satellite; evaluated when the
 * strategy module is on the classpath; derived from the current window snapshot.
 * PT Escala derivada do snapshot da janela corrente — só existe se o módulo estiver no APK.
 */
internal object PowerFactors {
    /**
     * Memoized on the snapshot: `Math.pow` runs at most once per DimenMetrics,
     * never once per call (previous behavior penalized the bypass path).
     */
    val scale: Float
        get() = DimenCache.currentMetrics.powerScale
}
