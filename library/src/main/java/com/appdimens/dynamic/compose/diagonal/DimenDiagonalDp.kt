/**
 * Strategy module: DIAGONAL — calculation logic lives in this package only.
 */
package com.appdimens.dynamic.compose.diagonal

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenCalculationPlumbing
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp
import com.appdimens.dynamic.core.rememberDimenDp
import com.appdimens.dynamic.core.rememberDimenPxFromDp

/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 */
internal fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return DimenCalculationPlumbing.readScreenDp(configuration, qualifier)
}

// EN Composable extensions for quick dynamic scaling.
// PT Extensões Composable para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.sdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.sdp`.
 */
@get:Composable
val Number.dgsdp: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsdpa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsdpi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsdpia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsdpPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Number.dgsdpaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Number.dgsdpiPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.dgsdpiaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpPh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpPh`.
 */
@get:Composable
val Number.dgsdpPh: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsdpPha: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsdpPhi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsdpPhia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsdpPxPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Number.dgsdpPxaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.dgsdpPxiPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.dgsdpPxiaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpLh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpLh`.
 */
@get:Composable
val Number.dgsdpLh: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsdpLha: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsdpLhi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsdpLhia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsdpPxLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Number.dgsdpPxaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.dgsdpPxiLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.dgsdpPxiaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpPw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sdpPw`.
 */
@get:Composable
val Number.dgsdpPw: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsdpPwa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsdpPwi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsdpPwia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsdpPxPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Number.dgsdpPxaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.dgsdpPxiPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.dgsdpPwiaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpLw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sdpLw`.
 */
@get:Composable
val Number.dgsdpLw: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsdpLwa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsdpLwi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsdpLwia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsdpPxLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Number.dgsdpPxaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.dgsdpPxiLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.dgsdpPxiaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.hdp`.
 */
@get:Composable
val Number.dghdp: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dghdpa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dghdpi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dghdpia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dghdpPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT)
@get:Composable
val Number.dghdpaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Number.dghdpiPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Number.dghdpiaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpLw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdpLw`.
 */
@get:Composable
val Number.dghdpLw: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dghdpLwa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dghdpLwi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dghdpLwia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dghdpPxLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Number.dghdpPxaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.dghdpPxiLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.dghdpPxiaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpPw`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hdpPw`.
 */
@get:Composable
val Number.dghdpPw: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dghdpPwa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dghdpPwi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dghdpPwia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dghdpPxPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Number.dghdpPxaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.dghdpPxiPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.dghdpPxiaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wdp`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Exemplo de uso: `100.wdp`.
 */
@get:Composable
val Number.dgwdp: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgwdpa: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgwdpi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgwdpia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgwdpPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH)
@get:Composable
val Number.dgwdpaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Number.dgwdpiPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.dgwdpiaPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpLh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdpLh`.
 */
@get:Composable
val Number.dgwdpLh: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgwdpLha: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgwdpLhi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgwdpLhia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgwdpPxLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Number.dgwdpPxaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.dgwdpPxiLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.dgwdpPxiaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpPh`.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wdpPh`.
 */
@get:Composable
val Number.dgwdpPh: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgwdpPha: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgwdpPhi: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgwdpPhia: Dp get() = this.toDynamicDiagonalDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgwdpPxPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Number.dgwdpPxaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.dgwdpPxiPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.dgwdpPxiaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)




internal fun calculateDiagonalDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    context: android.content.Context? = null
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows, context)) return baseValue
    var out = baseValue * DimenCache.currentDiagonalScale
    if (applyAspectRatio) {
        out *= if (customSensitivityK == null) {
            DimenCache.currentAspectRatioMul
        } else {
            1f + customSensitivityK * DimenCache.currentLogNormalizedAr
        }
    }
    return out
}

// PT Funções de dimensionamento dinâmico (abordagem puramente matemática).

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled [Dp] for use in Jetpack Compose.
 *
 * The scaling logic:
 * 1. Checks [DimenCache] first. On a cache hit, returns the precomputed value;
 *    otherwise, computes via [calculateDiagonalDpCompose] and stores it.
 * 2. Uses the internal bypass mechanism in [DimenCache] for sub-nanosecond
 *    latency on common width-scaling paths.
 * 3. The [remember] block ensures recalculation only when configuration changes.
 *
 * > ⚠️ **Bypass note**: when [applyAspectRatio] is `false` and [qualifier] is `SMALL_WIDTH`
 * > with `DEFAULT` inverter, the cache is bypassed internally because a raw multiply (~2 ns)
 * > is faster than the cache lookup (~5 ns). This is intentional and not a bug.
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Dp] dinamicamente escalado para uso no Jetpack Compose.
 *
 * A lógica de escalonamento:
 * 1. Consulta o [DimenCache] primeiro. No acerto, retorna o Float cacheado;
 *    no miss, calcula via [calculateDiagonalDpCompose] e armazena.
 * 2. O bloco [remember] garante que o valor só seja recalculado quando um parâmetro de
 *    configuração realmente muda.
 *
 * @param qualifier    Screen dimension qualifier: [DpQualifier.SMALL_WIDTH], [DpQualifier.HEIGHT], or [DpQualifier.WIDTH].
 * @param inverter     Orientation-based dimension swap rule (default: [Inverter.DEFAULT]).
 * @param ignoreMultiWindows If `true`, returns the base value unscaled when the app is in split-screen.
 * @param applyAspectRatio   If `true`, applies aspect-ratio multiplier for more aggressive scaling.
 * @param customSensitivityK Override for the AR sensitivity constant (null = library default).
 * @return Dynamically scaled [Dp] value.
 */
@Composable
fun Number.toDynamicDiagonalDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)

    return rememberDiagonalDp(
        cacheKey, layoutStamp, androidContext, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}


@Composable
internal fun rememberDiagonalDp(
    cacheKey: Long,
    layoutStamp: Long,
    androidContext: android.content.Context,
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
): Dp = rememberDimenDp(cacheKey, layoutStamp, androidContext) {
    calculateDiagonalDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

@Composable
internal fun rememberDiagonalPxFromDp(
    cacheKey: Long,
    pxStamp: Long,
    androidContext: android.content.Context,
    density: Density,
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
): Float = rememberDimenPxFromDp(cacheKey, pxStamp, androidContext, density) {
    calculateDiagonalDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for Jetpack Compose.
 *
 * Same semantics as [toDynamicDiagonalDp], but the result is multiplied by
 * the current display density ([LocalDensity]).
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Mesma semântica de cache e bypass de [toDynamicDiagonalDp], mas o resultado é multiplicado pela
 * densidade do display atual ([LocalDensity]) para entregar um valor pronto em pixels.
 * Útil quando um valor em pixels brutos é necessário (ex: desenho em `Canvas` ou `Modifier.offset`).
 *
 * @param qualifier    Screen dimension qualifier: [DpQualifier.SMALL_WIDTH], [DpQualifier.HEIGHT], or [DpQualifier.WIDTH].
 * @param inverter     Orientation-based dimension swap rule (default: [Inverter.DEFAULT]).
 * @param ignoreMultiWindows If `true`, returns base value in pixels unscaled when in split-screen.
 * @param applyAspectRatio   If `true`, applies the aspect-ratio multiplier.
 * @param customSensitivityK Override for the AR sensitivity constant (null = library default).
 * @return Dynamically scaled pixel value as [Float].
 */
@Composable
fun Number.toDynamicDiagonalPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberDiagonalPxFromDp(
        cacheKey, pxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}