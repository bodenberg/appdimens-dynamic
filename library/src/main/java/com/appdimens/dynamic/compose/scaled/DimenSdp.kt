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

import android.annotation.SuppressLint
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


// EN Dynamic scaling functions (Resource-based).
// PT Funções de dimensionamento dinâmico (baseadas em recursos).

/**
 * EN
 * Finds the dimension resource ID (`dimen`) by the constructed name.
 * The SuppressLint annotation is used because getIdentifier is discouraged,
 * but it is necessary for this type of dynamic logic based on naming convention.
 *
 * PT
 * Encontra o ID de recurso de dimensão (`dimen`) pelo nome construído.
 * A anotação SuppressLint é usada porque getIdentifier é desencorajada,
 * mas é necessária para este tipo de lógica dinâmica baseada em convenção de nomenclatura.
 *
 * @param resourceName The expected name of the resource, e.g., `_s16dp`.
 * @return The resource ID or 0 (or -1) if not found.
 */
@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun findResourceIdByName(resourceName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        resourceName,
        "dimen", // EN The resource type is 'dimen'. / PT O tipo de recurso é 'dimen'.
        context.packageName
    )
}

/**
 * EN
 * Converts an Int (the base Dp value) into a dynamically scaled Dp.
 * The logic tries to find a corresponding dimension resource in the 'res/values/' folder.
 * 1. Constructs the resource name based on the value (this) and the qualifier (qualifier).
 * 2. Tries to load the resource via dimensionResource.
 * 3. If the resource is found (e.g., in `values-sw600dp/dimens.xml`), that value is used.
 * 4. If the resource is not found, the original value is used as a Dp (the default Compose Int.dp).
 *
 * PT
 * Converte um Int (o valor base de Dp) em um Dp dinamicamente escalado.
 * A lógica tenta encontrar um recurso de dimensão correspondente na pasta 'res/values/'.
 * 1. Constrói o nome do recurso baseado no valor (this) e no qualificador (qualifier).
 * 2. Tenta carregar o recurso via dimensionResource.
 * 3. Se o recurso for encontrado (e.g., em `values-sw600dp/dimens.xml`), esse valor é usado.
 * 4. Se o recurso não for encontrado, o valor original é usado como Dp (o Int.dp padrão do Compose).
 *
 * @param qualifier The screen qualifier used to construct the resource name (s, h, w).
 * @return The Dp value loaded from the resource or the base Dp value.
 */
@Composable
fun Number.toDynamicScaledDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null, enableCache: Boolean = true): Dp {
    require(this.toFloat() in -1023f..1024f) { "Value must be between -1023 and 1024. Current: $this" }

    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current

    return remember(
        this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this.toFloat(),
            screenWidthDp = configuration.screenWidthDp,
            screenHeightDp = configuration.screenHeightDp,
            smallestWidthDp = configuration.smallestScreenWidthDp,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )

        if (!enableCache) {
             return@remember calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK).dp
        }

        DimenCache.getOrPut(cacheKey, androidContext) {
            calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }.dp
    }
}

/** Internal helper for Compose scaling logic to avoid duplication */
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
 * EN Converts an Int (base Dp) to a dynamically scaled Float (in pixels).
 * PT Converte um Int (base Dp) para um Float (em pixels) escalado dinamicamente.
 */
@Composable
fun Number.toDynamicScaledPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null, enableCache: Boolean = true): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    return remember(
        this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext, density
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this.toFloat(),
            screenWidthDp = configuration.screenWidthDp,
            screenHeightDp = configuration.screenHeightDp,
            smallestWidthDp = configuration.smallestScreenWidthDp,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )

        if (!enableCache) {
            val scaledDp = calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
            return@remember density.run { scaledDp.dp.toPx() }
        }

        DimenCache.getOrPut(cacheKey, androidContext) {
            val scaledDp = calculateScaledDpCompose(this.toFloat(), configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
            density.run { scaledDp.dp.toPx() }
        }
    }
}