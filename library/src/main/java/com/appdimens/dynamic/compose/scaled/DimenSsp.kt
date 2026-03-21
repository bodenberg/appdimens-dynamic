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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter

// EN Composable extensions for quick dynamic text scaling (Sp) using the DP XML resources.
// PT Extensões Composable para escalonamento dinâmico rápido de texto (Sp) usando os recursos XML de DP.

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Smallest Width (swDP)**.
 * Reads the pre-calculated DP resource (e.g., `_16sdp`) and converts it to Sp, respecting
 * the user's font scale setting.
 * Usage example: `16.ssp`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Lê o recurso DP pré-calculado (ex: `_16sdp`) e converte para Sp, respeitando
 * a configuração de escala de fonte do usuário.
 * Exemplo de uso: `16.ssp`.
 */
@get:Composable
val Int.ssp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true)
@get:Composable
val Int.sspaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.sspiPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.sspiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspPh`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sspPh`.
 */
@get:Composable
val Int.sspPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
@get:Composable
val Int.sspPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.sspPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspLh`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sspLh`.
 */
@get:Composable
val Int.sspLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
@get:Composable
val Int.sspPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.sspPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspPw`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sspPw`.
 */
@get:Composable
val Int.sspPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
@get:Composable
val Int.sspPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.sspPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.sspPwiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspLw`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.sspLw`.
 */
@get:Composable
val Int.sspLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
@get:Composable
val Int.sspPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.sspPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hsp`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.hsp`.
 */
@get:Composable
val Int.hsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true)
@get:Composable
val Int.hspaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.hspiPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.hspiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspLw`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação paisagem atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hspLw`.
 */
@get:Composable
val Int.hspLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
@get:Composable
val Int.hspPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.hspPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.hspPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspPw`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**, mas
 * na orientação retrato atua como **Largura da Tela (wDP)**.
 * Exemplo de uso: `32.hspPw`.
 */
@get:Composable
val Int.hspPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
@get:Composable
val Int.hspPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.hspPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.hspPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wsp`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Exemplo de uso: `100.wsp`.
 */
@get:Composable
val Int.wsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true)
@get:Composable
val Int.wspaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.wspiPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.wspiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspLh`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação paisagem atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wspLh`.
 */
@get:Composable
val Int.wspLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
@get:Composable
val Int.wspPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.wspPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.wspPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspPh`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `100.wspPh`.
 */
@get:Composable
val Int.wspPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
@get:Composable
val Int.wspPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.wspPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.wspPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN WITHOUT FONT SCALE variants (sem escala de fonte)
// PT Variantes SEM ESCALA DE FONTE

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE).
 * Usage example: `16.sem`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)** (SEM ESCALA DE FONTE).
 * Exemplo de uso: `16.sem`.
 */
@get:Composable
val Int.sei: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.seia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.seii: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.seiia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.seiPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false)
@get:Composable
val Int.seiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.seiiPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.seiiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.semPh`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) com dimensionamento baseado na **Smallest Width**, mas
 * na orientação retrato atua como Altura da Tela.
 * Exemplo de uso: `32.semPh`.
 */
@get:Composable
val Int.semPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.semPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.semPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.semPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.semPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH)
@get:Composable
val Int.semPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.semPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.semPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.semLh`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) com dimensionamento baseado na **Smallest Width**, mas
 * na orientação paisagem atua como Altura da Tela.
 * Exemplo de uso: `32.semLh`.
 */
@get:Composable
val Int.semLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.semLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.semLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.semLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.semPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH)
@get:Composable
val Int.semPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.semPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.semPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.semPw`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) com dimensionamento baseado na **Smallest Width**, mas
 * na orientação retrato atua como Largura da Tela.
 * Exemplo de uso: `32.semPw`.
 */
@get:Composable
val Int.semPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.semPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.semPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.semPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.semPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW)
@get:Composable
val Int.semPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.semPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.semPwiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.semLw`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) com dimensionamento baseado na **Smallest Width**, mas
 * na orientação paisagem atua como Largura da Tela.
 * Exemplo de uso: `32.semLw`.
 */
@get:Composable
val Int.semLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.semLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.semLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.semLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.semPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW)
@get:Composable
val Int.semPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.semPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.semPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE).
 * Usage example: `32.hem`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)** (SEM ESCALA DE FONTE).
 * Exemplo de uso: `32.hem`.
 */
@get:Composable
val Int.hei: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.heia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.heii: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.heiia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.heiPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false)
@get:Composable
val Int.heiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.heiiPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.heiiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hemLw`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) baseado na Altura, mas na paisagem atua como Largura.
 * Exemplo de uso: `32.hemLw`.
 */
@get:Composable
val Int.hemLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hemLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hemLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hemLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hemPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW)
@get:Composable
val Int.hemPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.hemPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.hemPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hemPw`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) baseado na Altura, mas no retrato atua como Largura.
 * Exemplo de uso: `32.hemPw`.
 */
@get:Composable
val Int.hemPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hemPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hemPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hemPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hemPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW)
@get:Composable
val Int.hemPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.hemPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.hemPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE).
 * Usage example: `100.wem`.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)** (SEM ESCALA DE FONTE).
 * Exemplo de uso: `100.wem`.
 */
@get:Composable
val Int.wei: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.weia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.weii: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.weiia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.weiPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false)
@get:Composable
val Int.weiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.weiiPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.weiiaPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemLh`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) baseado na Largura, mas na paisagem atua como Altura.
 * Exemplo de uso: `100.wemLh`.
 */
@get:Composable
val Int.wemLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wemLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wemLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wemLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wemPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH)
@get:Composable
val Int.wemPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.wemPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.wemPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemPh`.
 *
 * PT
 * Extensão para TextUnit (Sp) (SEM ESCALA DE FONTE) baseado na Largura, mas no retrato atua como Altura.
 * Exemplo de uso: `100.wemPh`.
 */
@get:Composable
val Int.wemPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wemPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wemPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wemPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wemPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
@get:Composable
val Int.wemPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.wemPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.wemPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN Dynamic scaling function for Sp (Resource-based, reuses DP XML resources).
// PT Função de dimensionamento dinâmico para Sp (baseada em recursos, reutiliza os recursos XML de DP).

/**
 * EN
 * Converts an Int (the base Sp value) into a dynamically scaled TextUnit (Sp).
 * This function reuses the existing DP XML resources (`_Nsdp`, `_Nhdp`, `_Nwdp`) as the
 * dimension values, then converts them to Sp. This means the scaling system is the same as
 * the DP system — the raw dp value from the resource is used directly as an sp number.
 *
 * 1. Constructs the resource name based on the value and the qualifier (e.g., `_16sdp`).
 * 2. Loads the dimension value in dp from that resource.
 * 3. Converts it to Sp, optionally stripping the system font scale.
 *
 * PT
 * Converte um Int (o valor Sp base) em um TextUnit (Sp) escalado dinamicamente.
 * Esta função reutiliza os recursos XML de DP existentes (`_Nsdp`, `_Nhdp`, `_Nwdp`) como
 * valores de dimensão, convertendo-os para Sp. O sistema de escalonamento é o mesmo do DP —
 * o valor dp bruto do recurso é usado diretamente como número sp.
 *
 * 1. Constrói o nome do recurso baseado no valor e no qualificador (ex: `_16sdp`).
 * 2. Carrega o valor de dimensão em dp daquele recurso.
 * 3. Converte para Sp, opcionalmente removendo a escala de fonte do sistema.
 *
 * @param qualifier The screen qualifier used to determine the resource name (sdp, hdp, wdp).
 * @param fontScale Whether to respect the user's font scale setting.
 * @param inverter Inverter to swap qualifier when orientation changes.
 * @return The TextUnit (Sp) value loaded from the resource, or the base sp value as fallback.
 */
@Composable
fun Int.toDynamicScaledSp(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null,
    enableCache: Boolean = true
): TextUnit {
    require(this in 1..600) {
        "Value must be between 1 and 600 to use the dynamic scaling dimension logic. Current value: $this"
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val density = LocalDensity.current

    return remember(
        this,
        qualifier,
        fontScale,
        inverter,
        ignoreMultiWindows,
        applyAspectRatio,
        customSensitivityK,
        configuration.orientation,
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.smallestScreenWidthDp,
        configuration.screenLayout,
        density.fontScale
    ) {
        val context = DimenCache.CacheContext(
            screenWidthDp        = configuration.screenWidthDp,
            screenHeightDp       = configuration.screenHeightDp,
            smallestScreenWidthDp = configuration.smallestScreenWidthDp,
            isLandscape          = isLandscape,
            ignoreMultiWindows   = ignoreMultiWindows
        )

        val cacheKey = DimenCache.buildKey(
            baseValue             = this,
            context               = context,
            calcType              = DimenCache.CalcType.SCALED,
            qualifier             = qualifier,
            inverter              = inverter,
            applyAspectRatio      = applyAspectRatio,
            valueType             = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE
                                    else DimenCache.ValueType.SP_NO_SCALE,
            customSensitivityK    = customSensitivityK
        )

        if (!enableCache) {
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

            var actualQualifier = qualifier

            when (inverter) {
                Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
                Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
                Inverter.LH_TO_PW -> if (isPortrait  && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
                Inverter.LW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
                Inverter.SW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_PW -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
                Inverter.DEFAULT  -> {}
            }

            var isMultiWindow = false
            if (ignoreMultiWindows) {
                val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
                val currentWidthDp  = configuration.screenWidthDp.toFloat()
                val isLayoutSplit   = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
                val isSmallDiff     = (smallestWidthDp - currentWidthDp) < (smallestWidthDp * 0.1f)
                isMultiWindow = isLayoutSplit && !isSmallDiff
            }

            val factor: Float = if (ignoreMultiWindows && isMultiWindow) {
                1.00f
            } else {
                val screenDimension = when (actualQualifier) {
                    DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
                    DpQualifier.WIDTH  -> configuration.screenWidthDp.toFloat()
                    else               -> configuration.smallestScreenWidthDp.toFloat()
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

            val rawResult = this * factor
            return@remember if (fontScale) {
                rawResult.sp
            } else {
                (rawResult / density.fontScale).sp
            }
        }

        val scaledValue = DimenCache.getOrPut(cacheKey) {
            val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

            var actualQualifier = qualifier

            when (inverter) {
                Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
                Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
                Inverter.LH_TO_PW -> if (isPortrait  && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
                Inverter.LW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
                Inverter.SW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
                Inverter.SW_TO_PW -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
                Inverter.DEFAULT  -> {}
            }

            var isMultiWindow = false
            if (ignoreMultiWindows) {
                val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
                val currentWidthDp  = configuration.screenWidthDp.toFloat()
                val isLayoutSplit   = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
                val isSmallDiff     = (smallestWidthDp - currentWidthDp) < (smallestWidthDp * 0.1f)
                isMultiWindow = isLayoutSplit && !isSmallDiff
            }

            val factor: Float = if (ignoreMultiWindows && isMultiWindow) {
                1.00f
            } else {
                val screenDimension = when (actualQualifier) {
                    DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
                    DpQualifier.WIDTH  -> configuration.screenWidthDp.toFloat()
                    else               -> configuration.smallestScreenWidthDp.toFloat()
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

            this * factor
        }

        if (fontScale) {
            scaledValue.sp
        } else {
            (scaledValue / density.fontScale).sp
        }
    }
}

/**
 * EN Converts an Int (base Sp) to a dynamically scaled Float (in pixels).
 * PT Converte um Int (base Sp) para um Float (em pixels) escalado dinamicamente.
 */
@Composable
fun Int.toDynamicScaledPx(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    val density = LocalDensity.current
    val spValue = this.toDynamicScaledSp(qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, enableCache)
    return density.run { spValue.toPx() }
}
