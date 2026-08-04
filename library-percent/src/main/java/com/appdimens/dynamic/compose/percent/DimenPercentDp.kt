/**
 * Strategy module: PERCENT — calculation logic lives in this package only.
 */
package com.appdimens.dynamic.compose.percent

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
val Number.psdp: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.psdpa: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.psdpi: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.psdpia: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.psdpPx: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Number.psdpaPx: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Number.psdpiPx: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.psdpiaPx: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.psdpPh: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.psdpPha: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.psdpPhi: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.psdpPhia: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.psdpPxPh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Number.psdpPxaPh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.psdpPxiPh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.psdpPxiaPh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.psdpLh: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.psdpLha: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.psdpLhi: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.psdpLhia: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.psdpPxLh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Number.psdpPxaLh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.psdpPxiLh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.psdpPxiaLh: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.psdpPw: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.psdpPwa: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.psdpPwi: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.psdpPwia: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.psdpPxPw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Number.psdpPxaPw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.psdpPxiPw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.psdpPwiaPx: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.psdpLw: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.psdpLwa: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.psdpLwi: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.psdpLwia: Dp get() = this.toDynamicPercentDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.psdpPxLw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Number.psdpPxaLw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.psdpPxiLw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.psdpPxiaLw: Float get() = this.toDynamicPercentPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.phdp: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.phdpa: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.phdpi: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.phdpia: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.phdpPx: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT)
@get:Composable
val Number.phdpaPx: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Number.phdpiPx: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Number.phdpiaPx: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.phdpLw: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.phdpLwa: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.phdpLwi: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.phdpLwia: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.phdpPxLw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Number.phdpPxaLw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.phdpPxiLw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.phdpPxiaLw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.phdpPw: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.phdpPwa: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.phdpPwi: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.phdpPwia: Dp get() = this.toDynamicPercentDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.phdpPxPw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Number.phdpPxaPw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.phdpPxiPw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.phdpPxiaPw: Float get() = this.toDynamicPercentPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwdp: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwdpa: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwdpi: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwdpia: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwdpPx: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH)
@get:Composable
val Number.pwdpaPx: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Number.pwdpiPx: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.pwdpiaPx: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwdpLh: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwdpLha: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwdpLhi: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwdpLhia: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwdpPxLh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Number.pwdpPxaLh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.pwdpPxiLh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.pwdpPxiaLh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwdpPh: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwdpPha: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwdpPhi: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwdpPhia: Dp get() = this.toDynamicPercentDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwdpPxPh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Number.pwdpPxaPh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.pwdpPxiPh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.pwdpPxiaPh: Float get() = this.toDynamicPercentPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)




internal fun calculatePercentDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    context: android.content.Context? = null
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows, context)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    if (!applyAspectRatio) return baseValue * dim * DimenCache.INV_BASE_RATIO
    val diff = dim - DesignScaleConstants.BASE_WIDTH_DP
    val adj = (customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT) * DimenCache.currentLogNormalizedAr
    return baseValue * (1f + diff * (DimenCache.ADJUSTMENT_SCALE + adj))
}

// PT Funções de dimensionamento dinâmico (abordagem puramente matemática).

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled [Dp] for use in Jetpack Compose.
 *
 * The scaling logic:
 * 1. Checks [DimenCache] first. On a cache hit, returns the precomputed value;
 *    otherwise, computes via [calculatePercentDpCompose] and stores it.
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
 *    no miss, calcula via [calculatePercentDpCompose] e armazena.
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
fun Number.toDynamicPercentDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)

    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}


@Composable
internal fun rememberPercentDp(
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
    calculatePercentDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

@Composable
internal fun rememberPercentPxFromDp(
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
    calculatePercentDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for Jetpack Compose.
 *
 * Same semantics as [toDynamicPercentDp], but the result is multiplied by
 * the current display density ([LocalDensity]).
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Mesma semântica de cache e bypass de [toDynamicPercentDp], mas o resultado é multiplicado pela
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
fun Number.toDynamicPercentPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}