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
val Int.sdp: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH)
@get:Composable
val Int.sdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
@get:Composable
val Int.sdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
@get:Composable
val Int.sdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.sdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sdpPhi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sdpPhia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sdpPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
@get:Composable
val Int.sdpPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.sdpPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.sdpPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.sdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sdpLhi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sdpLhia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sdpPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
@get:Composable
val Int.sdpPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.sdpPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.sdpPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.sdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sdpPwi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sdpPwia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sdpPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
@get:Composable
val Int.sdpPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.sdpPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.sdpPwiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.sdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sdpLwi: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sdpLwia: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sdpPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
@get:Composable
val Int.sdpPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.sdpPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.sdpPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.hdp: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT)
@get:Composable
val Int.hdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, applyAspectRatio = true)
@get:Composable
val Int.hdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, ignoreMultiWindows = true)
@get:Composable
val Int.hdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.hdpLw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hdpLwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hdpLwi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hdpLwia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hdpPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW)
@get:Composable
val Int.hdpPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.hdpPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.hdpPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.hdpPw: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hdpPwa: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hdpPwi: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hdpPwia: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hdpPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW)
@get:Composable
val Int.hdpPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.hdpPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.hdpPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.wdp: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wdpa: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wdpi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wdpia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wdpPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH)
@get:Composable
val Int.wdpaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, applyAspectRatio = true)
@get:Composable
val Int.wdpiPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, ignoreMultiWindows = true)
@get:Composable
val Int.wdpiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.wdpLh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wdpLha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wdpLhi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wdpLhia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wdpPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH)
@get:Composable
val Int.wdpPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.wdpPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.wdpPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

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
val Int.wdpPh: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wdpPha: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wdpPhi: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wdpPhia: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wdpPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH)
@get:Composable
val Int.wdpPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.wdpPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.wdpPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)


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
fun Int.toDynamicScaledDp(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null, enableCache: Boolean = true): Dp {
    // EN Validation requirement (limits usage to avoid creating thousands of dimens files).
    // PT Requisito de validação (limita o uso para evitar a criação de milhares de arquivos dimens).
    require(this in -300..600) { "Value must be between -300 and 600 to use the dynamic scaling dimension logic. Current value:: $this" }

    val configuration = LocalConfiguration.current
    val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
    val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
    val aspectRatio = if (currentScreenWidthDp > 0 && currentScreenHeightDp > 0) {
        max(currentScreenWidthDp, currentScreenHeightDp) / min(currentScreenWidthDp, currentScreenHeightDp)
    } else 1f

    return remember(
        this,
        qualifier,
        inverter,
        ignoreMultiWindows,
        applyAspectRatio,
        customSensitivityK,
        configuration.orientation,
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.smallestScreenWidthDp,
        configuration.screenLayout,
        aspectRatio
    ) {
        val context = DimenCache.CacheContext(
            screenWidthDp        = configuration.screenWidthDp,
            screenHeightDp       = configuration.screenHeightDp,
            smallestScreenWidthDp = configuration.smallestScreenWidthDp,
            isLandscape          = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows   = ignoreMultiWindows
        )

        val cacheKey = DimenCache.buildKey(
            baseValue            = this,
            context              = context,
            calcType             = DimenCache.CalcType.SCALED,
            qualifier            = qualifier,
            inverter             = inverter,
            applyAspectRatio     = applyAspectRatio,
            valueType            = DimenCache.ValueType.DP,
            customSensitivityK   = customSensitivityK
        )

        if (!enableCache) {
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val isPortrait  = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

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

            var isMultiWindow = false
            if (ignoreMultiWindows) {
                val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
                val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
                val isSmallDiff   = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1f)
                isMultiWindow = isLayoutSplit && !isSmallDiff
            }

            val factor: Float = if (ignoreMultiWindows && isMultiWindow) {
                1.00f
            } else {
                val screenDimension = when (actualQualifier) {
                    DpQualifier.HEIGHT      -> configuration.screenHeightDp.toFloat()
                    DpQualifier.WIDTH       -> configuration.screenWidthDp.toFloat()
                    else                    -> configuration.smallestScreenWidthDp.toFloat()
                }
                if (applyAspectRatio) {
                    val difference    = screenDimension - 300f
                    val logAr         = DimenCache.currentLogNormalizedAr
                    val adjustment    = (customSensitivityK ?: (0.08f / 30f)) * logAr
                    1.0f + difference * ((0.10f / 30f) + adjustment)
                } else {
                    screenDimension / 300f
                }
            }

            return@remember (this.toFloat() * factor).dp
        }

        val context = LocalContext.current
        val scaledFloat = DimenCache.getOrPut(cacheKey, context) {
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val isPortrait  = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

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

            var isMultiWindow = false
            if (ignoreMultiWindows) {
                val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
                val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
                val isSmallDiff   = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1f)
                isMultiWindow = isLayoutSplit && !isSmallDiff
            }

            val factor: Float = if (ignoreMultiWindows && isMultiWindow) {
                1.00f
            } else {
                val screenDimension = when (actualQualifier) {
                    DpQualifier.HEIGHT      -> configuration.screenHeightDp.toFloat()
                    DpQualifier.WIDTH       -> configuration.screenWidthDp.toFloat()
                    else                    -> configuration.smallestScreenWidthDp.toFloat()
                }
                if (applyAspectRatio) {
                    val difference    = screenDimension - 300f
                    val logAr         = DimenCache.currentLogNormalizedAr
                    val adjustment    = (customSensitivityK ?: (0.08f / 30f)) * logAr
                    1.0f + difference * ((0.10f / 30f) + adjustment)
                } else {
                    screenDimension / 300f
                }
            }

            this@toDynamicScaledDp * factor
        }

        scaledFloat.dp
    }
}

/**
 * EN Converts an Int (base Dp) to a dynamically scaled Float (in pixels).
 * PT Converte um Int (base Dp) para um Float (em pixels) escalado dinamicamente.
 */
@Composable
fun Int.toDynamicScaledPx(qualifier: DpQualifier, inverter: Inverter = Inverter.DEFAULT, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null, enableCache: Boolean = true): Float {
    val density = LocalDensity.current
    val dpValue = this.toDynamicScaledDp(qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, enableCache)
    return density.run { dpValue.toPx() }
}
