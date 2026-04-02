/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.compose

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import kotlin.math.max
import kotlin.math.min

private const val BASE_RATIO_STEP = 300f
private const val ADJUSTMENT_SCALE = 0.10f / 30f
private const val SENSITIVITY_DEFAULT = 0.08f / 30f

/**
 * EN Internal cache to minimize Local*.current lookups in hot paths.
 * PT Cache interno para minimizar lookups de Local*.current em caminhos críticos.
 *
 * @see [DimenCache.addResetListener] for how this is invalidated.
 */
internal object InternalComposeResources {
    @JvmField @Volatile var configuration: android.content.res.Configuration? = null
    @JvmField @Volatile var context: android.content.Context? = null
    @JvmField @Volatile var density: androidx.compose.ui.unit.Density? = null

    fun reset() {
        configuration = null
        context = null
        density = null
    }

    init {
        DimenCache.addResetListener { reset() }
    }
}


/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 *
 * PT
 * Obtém o valor real da configuração (Configuration) para o DpQualifier dado.
 *
 * @param qualifier The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration The current resource configuration.
 * @return The numeric value (in Dp) of the screen metric.
 */
internal fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
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
val Number.sdp: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Number.sdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Number.sdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.sdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.sdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sdpPhi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sdpPhia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sdpPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Number.sdpPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.sdpPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.sdpPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.sdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sdpLhi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sdpLhia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sdpPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Number.sdpPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.sdpPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.sdpPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.sdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sdpPwi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sdpPwia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sdpPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Number.sdpPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.sdpPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.sdpPwiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.sdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sdpLwi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sdpLwia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sdpPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Number.sdpPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.sdpPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.sdpPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.hdp: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT)
@get:Composable
val Number.hdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Number.hdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Number.hdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.hdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hdpLwi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hdpLwia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hdpPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Number.hdpPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.hdpPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.hdpPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.hdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hdpPwi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hdpPwia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hdpPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Number.hdpPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.hdpPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.hdpPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.wdp: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH)
@get:Composable
val Number.wdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Number.wdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Number.wdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.wdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wdpLhi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wdpLhia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wdpPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Number.wdpPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.wdpPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.wdpPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Number.wdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wdpPhi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wdpPhia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wdpPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Number.wdpPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.wdpPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.wdpPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)


// EN Dynamic scaling functions (pure-math approach).
// PT Funções de dimensionamento dinâmico (abordagem puramente matemática).

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled [Dp] for use in Jetpack Compose.
 *
 * The scaling logic:
 * 1. Checks [DimenCache] first. On a cache hit, returns the precomputed value;
 *    otherwise, computes via [calculateScaledDpCompose] and stores it.
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
 *    no miss, calcula via [calculateScaledDpCompose] e armazena.
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
fun Number.toDynamicScaledDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    if (InternalComposeResources.context == null) {
        InternalComposeResources.configuration = LocalConfiguration.current
        InternalComposeResources.context = LocalContext.current
    }
    val configuration = InternalComposeResources.configuration!!
    val androidContext = InternalComposeResources.context!!

    return remember(
        this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this.toFloat(),
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )

        DimenCache.getOrPut(cacheKey, androidContext) {
            calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }.dp
    }
}

/**
 * EN
 * Shared pure-math scaling kernel used by [toDynamicScaledDp] and [toDynamicScaledPx].
 *
 * Algorithm summary:
 * 1. Applies [Inverter] rules to swap the effective [DpQualifier] based on orientation.
 * 2. If [ignoreMultiWindows] is `true`, detects split-screen via layout flags; if active,
 *    returns [baseValue] unchanged so the UI does not over-scale in a small window.
 * 3. For the common path (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity),
 *    delegates to [DimenCache.calculateRawScaling] which reads the pre-computed factors
 *    from [DimenCache.ScreenFactors] — one float multiply, zero extra allocations.
 * 4. For other qualifiers or custom sensitivity, reads the screen dimension from
 *    [Configuration] and performs the scaling formula inline.
 *
 * > **Performance**: Simple paths without Aspect Ratio complete in ~2 ns (single multiply).
 * > Paths with Aspect Ratio require ~41 ns on Snapdragon 888 (includes ln() fallback).
 * > Results are memoized by the surrounding [remember] block and [DimenCache].
 *
 * PT
 * Núcleo de escalonamento puro compartilhado por [toDynamicScaledDp] e [toDynamicScaledPx].
 *
 * Resumo do algoritmo:
 * 1. Aplica as regras de [Inverter] para trocar o [DpQualifier] efetivo conforme a orientação.
 * 2. Se [ignoreMultiWindows] for `true`, detecta split-screen via flags de layout;
 *    se ativo, retorna [baseValue] sem escalar.
 * 3. Para o caminho comum (SMALL_WIDTH + DEFAULT + sem sensibilidade customizada),
 *    delega para [DimenCache.calculateRawScaling] com os fatores pré-calculados.
 * 4. Para outros qualificadores ou sensibilidade customizada, lê a dimensão da tela
 *    do [Configuration] e executa a fórmula de escalonamento inline.
 *
 * @param baseValue          Raw Dp value to scale (e.g. `16f` for 16 dp).
 * @param configuration      Current [Configuration] snapshot from [LocalConfiguration.current].
 * @param qualifier          Original screen qualifier before inversion.
 * @param inverter           Orientation-swap rule.
 * @param ignoreMultiWindows Whether to suppress scaling in multi-window mode.
 * @param applyAspectRatio   Whether to apply the AR multiplier.
 * @param customSensitivityK Custom AR sensitivity constant, or `null` for the library default.
 * @return Scaled Dp value as a raw [Float] (caller converts to [Dp] or pixels).
 */
private fun calculateScaledDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var actualQualifier = qualifier
    when (inverter) {
        Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH)  actualQualifier = DpQualifier.HEIGHT
        Inverter.LH_TO_PW -> if (isPortrait  && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.LW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.WIDTH)  actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.SW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_PW -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.DEFAULT  -> {}
    }

    val isMultiWindow = if (ignoreMultiWindows) {
        val swDp = configuration.smallestScreenWidthDp.toFloat()
        val cwDp = configuration.screenWidthDp.toFloat()
        val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
        val isSmallDiff = (swDp - cwDp) < (swDp * 0.1f)
        isLayoutSplit && !isSmallDiff
    } else false

    return if (isMultiWindow) {
        baseValue
    } else {
        val isDefaultSw = (qualifier == DpQualifier.SMALL_WIDTH) && (inverter == Inverter.DEFAULT)
        if (isDefaultSw && customSensitivityK == null) {
            DimenCache.calculateRawScaling(baseValue, applyAspectRatio, null)
        } else {
            val screenDim = when (actualQualifier) {
                DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
                DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
                else -> configuration.smallestScreenWidthDp.toFloat()
            }
            val scale = screenDim * (1.0f / 300f)
            if (applyAspectRatio) {
                val diff = screenDim - 300f
                val adj = (customSensitivityK ?: 0.0026666667f) * DimenCache.currentLogNormalizedAr
                baseValue * (1.0f + diff * (0.0033333334f + adj))
            } else {
                baseValue * scale
            }
        }
    }
}

/**
 * EN
 * Converts a [Number] (base Dp value) into a dynamically scaled pixel [Float] for Jetpack Compose.
 *
 * Same semantics as [toDynamicScaledDp], but the result is multiplied by
 * the current display density ([LocalDensity]).
 *
 * PT
 * Converte um [Number] (valor Dp base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Mesma semântica de cache e bypass de [toDynamicScaledDp], mas o resultado é multiplicado pela
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
fun Number.toDynamicScaledPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    if (InternalComposeResources.density == null) {
        InternalComposeResources.configuration = LocalConfiguration.current
        InternalComposeResources.context = LocalContext.current
        InternalComposeResources.density = LocalDensity.current
    }
    val configuration = InternalComposeResources.configuration!!
    val androidContext = InternalComposeResources.context!!
    val density = InternalComposeResources.density!!

    return remember(
        this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext, density
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this.toFloat(),
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )

        DimenCache.getOrPut(cacheKey, androidContext) {
            val scaledDp = calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
            density.run { scaledDp.dp.toPx() }
        }
    }
}