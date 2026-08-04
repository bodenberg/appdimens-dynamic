/**
 * Strategy module: DENSITY — calculation logic lives in this package only.
 */
package com.appdimens.dynamic.compose.density

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
val Number.dsdp: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dsdpa: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dsdpi: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dsdpia: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dsdpPx: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Number.dsdpaPx: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Number.dsdpiPx: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.dsdpiaPx: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dsdpPh: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dsdpPha: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dsdpPhi: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dsdpPhia: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dsdpPxPh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Number.dsdpPxaPh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.dsdpPxiPh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.dsdpPxiaPh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dsdpLh: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dsdpLha: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dsdpLhi: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dsdpLhia: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dsdpPxLh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Number.dsdpPxaLh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.dsdpPxiLh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.dsdpPxiaLh: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dsdpPw: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dsdpPwa: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dsdpPwi: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dsdpPwia: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dsdpPxPw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Number.dsdpPxaPw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.dsdpPxiPw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.dsdpPwiaPx: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dsdpLw: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dsdpLwa: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dsdpLwi: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dsdpLwia: Dp get() = this.toDynamicDensityDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dsdpPxLw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Number.dsdpPxaLw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.dsdpPxiLw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.dsdpPxiaLw: Float get() = this.toDynamicDensityPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dhdp: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dhdpa: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dhdpi: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dhdpia: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dhdpPx: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT)
@get:Composable
val Number.dhdpaPx: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Number.dhdpiPx: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Number.dhdpiaPx: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dhdpLw: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dhdpLwa: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dhdpLwi: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dhdpLwia: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dhdpPxLw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Number.dhdpPxaLw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.dhdpPxiLw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.dhdpPxiaLw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dhdpPw: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dhdpPwa: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dhdpPwi: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dhdpPwia: Dp get() = this.toDynamicDensityDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dhdpPxPw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Number.dhdpPxaPw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.dhdpPxiPw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.dhdpPxiaPw: Float get() = this.toDynamicDensityPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dwdp: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dwdpa: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dwdpi: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dwdpia: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dwdpPx: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH)
@get:Composable
val Number.dwdpaPx: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Number.dwdpiPx: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.dwdpiaPx: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dwdpLh: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dwdpLha: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dwdpLhi: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dwdpLhia: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dwdpPxLh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Number.dwdpPxaLh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.dwdpPxiLh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.dwdpPxiaLh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.dwdpPh: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dwdpPha: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dwdpPhi: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dwdpPhia: Dp get() = this.toDynamicDensityDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dwdpPxPh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Number.dwdpPxaPh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.dwdpPxiPh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.dwdpPxiaPh: Float get() = this.toDynamicDensityPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)




internal fun calculateDensityDpCompose(
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
    val densityScale = configuration.densityDpi / 160f
    var out = baseValue * densityScale
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
 *    otherwise, computes via [calculateDensityDpCompose] and stores it.
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
 *    no miss, calcula via [calculateDensityDpCompose] e armazena.
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
fun Number.toDynamicDensityDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.DENSITY,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)

    return rememberDensityDp(
        cacheKey, layoutStamp, androidContext, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}


@Composable
internal fun rememberDensityDp(
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
    calculateDensityDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

@Composable
internal fun rememberDensityPxFromDp(
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
    calculateDensityDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for Jetpack Compose.
 *
 * Same semantics as [toDynamicDensityDp], but the result is multiplied by
 * the current display density ([LocalDensity]).
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Mesma semântica de cache e bypass de [toDynamicDensityDp], mas o resultado é multiplicado pela
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
fun Number.toDynamicDensityPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.DENSITY,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberDensityPxFromDp(
        cacheKey, pxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}