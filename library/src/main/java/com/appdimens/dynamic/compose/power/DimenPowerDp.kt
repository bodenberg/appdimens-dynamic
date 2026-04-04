/**
 * Strategy module: POWER — calculation logic lives in this package only.
 */
package com.appdimens.dynamic.compose.power

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
import kotlin.math.pow

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
val Number.pwsdp: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwsdpa: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwsdpi: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwsdpia: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwsdpPx: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Number.pwsdpaPx: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Number.pwsdpiPx: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.pwsdpiaPx: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwsdpPh: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwsdpPha: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwsdpPhi: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwsdpPhia: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwsdpPxPh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Number.pwsdpPxaPh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.pwsdpPxiPh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.pwsdpPxiaPh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwsdpLh: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwsdpLha: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwsdpLhi: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwsdpLhia: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwsdpPxLh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Number.pwsdpPxaLh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.pwsdpPxiLh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.pwsdpPxiaLh: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwsdpPw: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwsdpPwa: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwsdpPwi: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwsdpPwia: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwsdpPxPw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Number.pwsdpPxaPw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.pwsdpPxiPw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.pwsdpPwiaPx: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwsdpLw: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwsdpLwa: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwsdpLwi: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwsdpLwia: Dp get() = this.toDynamicPowerDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwsdpPxLw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Number.pwsdpPxaLw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.pwsdpPxiLw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.pwsdpPxiaLw: Float get() = this.toDynamicPowerPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwhdp: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwhdpa: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwhdpi: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwhdpia: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwhdpPx: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT)
@get:Composable
val Number.pwhdpaPx: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Number.pwhdpiPx: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Number.pwhdpiaPx: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwhdpLw: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwhdpLwa: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwhdpLwi: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwhdpLwia: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwhdpPxLw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Number.pwhdpPxaLw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.pwhdpPxiLw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.pwhdpPxiaLw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwhdpPw: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwhdpPwa: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwhdpPwi: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwhdpPwia: Dp get() = this.toDynamicPowerDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwhdpPxPw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Number.pwhdpPxaPw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.pwhdpPxiPw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.pwhdpPxiaPw: Float get() = this.toDynamicPowerPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwwdp: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwwdpa: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwwdpi: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwwdpia: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwwdpPx: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH)
@get:Composable
val Number.pwwdpaPx: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Number.pwwdpiPx: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.pwwdpiaPx: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwwdpLh: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwwdpLha: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwwdpLhi: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwwdpLhia: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwwdpPxLh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Number.pwwdpPxaLh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.pwwdpPxiLh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.pwwdpPxiaLh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.pwwdpPh: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.pwwdpPha: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.pwwdpPhi: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.pwwdpPhia: Dp get() = this.toDynamicPowerDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.pwwdpPxPh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Number.pwwdpPxaPh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.pwwdpPxiPh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.pwwdpPxiaPh: Float get() = this.toDynamicPowerPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)




internal fun calculatePowerDpCompose(
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
    val ratio = dim / DesignScaleConstants.BASE_WIDTH_DP
    var out = baseValue * ratio.toDouble().pow(0.75).toFloat()
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
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
 *    otherwise, computes via [calculatePowerDpCompose] and stores it.
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
 *    no miss, calcula via [calculatePowerDpCompose] e armazena.
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
fun Number.toDynamicPowerDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)

    return rememberPowerDp(
        cacheKey, layoutStamp, androidContext, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}


@Composable
internal fun rememberPowerDp(
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
    calculatePowerDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

@Composable
internal fun rememberPowerPxFromDp(
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
    calculatePowerDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, androidContext)
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for Jetpack Compose.
 *
 * Same semantics as [toDynamicPowerDp], but the result is multiplied by
 * the current display density ([LocalDensity]).
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Mesma semântica de cache e bypass de [toDynamicPowerDp], mas o resultado é multiplicado pela
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
fun Number.toDynamicPowerPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberPowerPxFromDp(
        cacheKey, pxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}