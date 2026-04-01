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
package com.appdimens.dynamic.code

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import kotlin.math.max
import kotlin.math.min

/**
 * EN
 * Utility object for handling SSP (Scalable Sp) dimensions from Java.
 *
 * PT
 * Objeto utilitário para manipulação de dimensões SSP (Scalable Sp) no Java.
 */
object DimenSsp {

    /**
     * EN Quick resolution for Smallest Width (ssp).
     * PT Resolução rápida para Smallest Width (ssp).
     */
    @JvmStatic
    fun ssp(context: Context, value: Int): Float = value.ssp(context)

    @JvmStatic
    fun sspa(context: Context, value: Int): Float = value.sspa(context)

    @JvmStatic
    fun sspi(context: Context, value: Int): Float = value.sspi(context)

    @JvmStatic
    fun sspia(context: Context, value: Int): Float = value.sspia(context)

    /**
     * EN Quick resolution for Smallest Width (ssp), but in portrait orientation it acts as Screen Height (hsp).
     */
    @JvmStatic
    fun sspPh(context: Context, value: Int): Float = value.sspPh(context)

    @JvmStatic
    fun sspPha(context: Context, value: Int): Float = value.sspPha(context)

    @JvmStatic
    fun sspPhi(context: Context, value: Int): Float = value.sspPhi(context)

    @JvmStatic
    fun sspPhia(context: Context, value: Int): Float = value.sspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (ssp), but in landscape orientation it acts as Screen Height (hsp).
     */
    @JvmStatic
    fun sspLh(context: Context, value: Int): Float = value.sspLh(context)

    @JvmStatic
    fun sspLha(context: Context, value: Int): Float = value.sspLha(context)

    @JvmStatic
    fun sspLhi(context: Context, value: Int): Float = value.sspLhi(context)

    @JvmStatic
    fun sspLhia(context: Context, value: Int): Float = value.sspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (ssp), but in portrait orientation it acts as Screen Width (wsp).
     */
    @JvmStatic
    fun sspPw(context: Context, value: Int): Float = value.sspPw(context)

    @JvmStatic
    fun sspPwa(context: Context, value: Int): Float = value.sspPwa(context)

    @JvmStatic
    fun sspPwi(context: Context, value: Int): Float = value.sspPwi(context)

    @JvmStatic
    fun sspPwia(context: Context, value: Int): Float = value.sspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (ssp), but in landscape orientation it acts as Screen Width (wsp).
     */
    @JvmStatic
    fun sspLw(context: Context, value: Int): Float = value.sspLw(context)

    @JvmStatic
    fun sspLwa(context: Context, value: Int): Float = value.sspLwa(context)

    @JvmStatic
    fun sspLwi(context: Context, value: Int): Float = value.sspLwi(context)

    @JvmStatic
    fun sspLwia(context: Context, value: Int): Float = value.sspLwia(context)

    /**
     * EN Quick resolution for Screen Height (hsp).
     * PT Resolução rápida para Altura da Tela (hsp).
     */
    @JvmStatic
    fun hsp(context: Context, value: Int): Float = value.hsp(context)

    @JvmStatic
    fun hspa(context: Context, value: Int): Float = value.hspa(context)

    @JvmStatic
    fun hspi(context: Context, value: Int): Float = value.hspi(context)

    @JvmStatic
    fun hspia(context: Context, value: Int): Float = value.hspia(context)

    /**
     * EN Quick resolution for Screen Height (hsp), but in landscape orientation it acts as Screen Width (wsp).
     */
    @JvmStatic
    fun hspLw(context: Context, value: Int): Float = value.hspLw(context)

    @JvmStatic
    fun hspLwa(context: Context, value: Int): Float = value.hspLwa(context)

    @JvmStatic
    fun hspLwi(context: Context, value: Int): Float = value.hspLwi(context)

    @JvmStatic
    fun hspLwia(context: Context, value: Int): Float = value.hspLwia(context)

    /**
     * EN Quick resolution for Screen Height (hsp), but in portrait orientation it acts as Screen Width (wsp).
     */
    @JvmStatic
    fun hspPw(context: Context, value: Int): Float = value.hspPw(context)

    @JvmStatic
    fun hspPwa(context: Context, value: Int): Float = value.hspPwa(context)

    @JvmStatic
    fun hspPwi(context: Context, value: Int): Float = value.hspPwi(context)

    @JvmStatic
    fun hspPwia(context: Context, value: Int): Float = value.hspPwia(context)

    /**
     * EN Quick resolution for Screen Width (wsp).
     * PT Resolução rápida para Largura da Tela (wsp).
     */
    @JvmStatic
    fun wsp(context: Context, value: Int): Float = value.wsp(context)

    @JvmStatic
    fun wspa(context: Context, value: Int): Float = value.wspa(context)

    @JvmStatic
    fun wspi(context: Context, value: Int): Float = value.wspi(context)

    @JvmStatic
    fun wspia(context: Context, value: Int): Float = value.wspia(context)

    /**
     * EN Quick resolution for Screen Width (wsp), but in landscape orientation it acts as Screen Height (hsp).
     */
    @JvmStatic
    fun wspLh(context: Context, value: Int): Float = value.wspLh(context)

    @JvmStatic
    fun wspLha(context: Context, value: Int): Float = value.wspLha(context)

    @JvmStatic
    fun wspLhi(context: Context, value: Int): Float = value.wspLhi(context)

    @JvmStatic
    fun wspLhia(context: Context, value: Int): Float = value.wspLhia(context)

    /**
     * EN Quick resolution for Screen Width (wsp), but in portrait orientation it acts as Screen Height (hsp).
     */
    @JvmStatic
    fun wspPh(context: Context, value: Int): Float = value.wspPh(context)

    @JvmStatic
    fun wspPha(context: Context, value: Int): Float = value.wspPha(context)

    @JvmStatic
    fun wspPhi(context: Context, value: Int): Float = value.wspPhi(context)

    @JvmStatic
    fun wspPhia(context: Context, value: Int): Float = value.wspPhia(context)


    // EN WITHOUT FONT SCALE variants
    // PT Variantes SEM ESCALA DE FONTE

    /**
     * EN Quick resolution for Smallest Width (sei) - Without font scale.
     * PT Resolução rápida para Smallest Width (sei) - Sem escala de fonte.
     */
    @JvmStatic
    fun sei(context: Context, value: Int): Float = value.sei(context)

    @JvmStatic
    fun seia(context: Context, value: Int): Float = value.seia(context)

    @JvmStatic
    fun seii(context: Context, value: Int): Float = value.seii(context)

    @JvmStatic
    fun seiia(context: Context, value: Int): Float = value.seiia(context)

    /**
     * EN Quick resolution for Smallest Width without font scale, portrait is Screen Height.
     */
    @JvmStatic
    fun semPh(context: Context, value: Int): Float = value.semPh(context)

    @JvmStatic
    fun semPha(context: Context, value: Int): Float = value.semPha(context)

    @JvmStatic
    fun semPhi(context: Context, value: Int): Float = value.semPhi(context)

    @JvmStatic
    fun semPhia(context: Context, value: Int): Float = value.semPhia(context)

    /**
     * EN Quick resolution for Smallest Width without font scale, landscape is Screen Height.
     */
    @JvmStatic
    fun semLh(context: Context, value: Int): Float = value.semLh(context)

    @JvmStatic
    fun semLha(context: Context, value: Int): Float = value.semLha(context)

    @JvmStatic
    fun semLhi(context: Context, value: Int): Float = value.semLhi(context)

    @JvmStatic
    fun semLhia(context: Context, value: Int): Float = value.semLhia(context)

    /**
     * EN Quick resolution for Smallest Width without font scale, portrait is Screen Width.
     */
    @JvmStatic
    fun semPw(context: Context, value: Int): Float = value.semPw(context)

    @JvmStatic
    fun semPwa(context: Context, value: Int): Float = value.semPwa(context)

    @JvmStatic
    fun semPwi(context: Context, value: Int): Float = value.semPwi(context)

    @JvmStatic
    fun semPwia(context: Context, value: Int): Float = value.semPwia(context)

    /**
     * EN Quick resolution for Smallest Width without font scale, landscape is Screen Width.
     */
    @JvmStatic
    fun semLw(context: Context, value: Int): Float = value.semLw(context)

    @JvmStatic
    fun semLwa(context: Context, value: Int): Float = value.semLwa(context)

    @JvmStatic
    fun semLwi(context: Context, value: Int): Float = value.semLwi(context)

    @JvmStatic
    fun semLwia(context: Context, value: Int): Float = value.semLwia(context)

    /**
     * EN Quick resolution for Screen Height without font scale.
     */
    @JvmStatic
    fun hei(context: Context, value: Int): Float = value.hei(context)

    @JvmStatic
    fun heia(context: Context, value: Int): Float = value.heia(context)

    @JvmStatic
    fun heii(context: Context, value: Int): Float = value.heii(context)

    @JvmStatic
    fun heiia(context: Context, value: Int): Float = value.heiia(context)

    /**
     * EN Quick resolution for Screen Height without font scale, landscape is Screen Width.
     */
    @JvmStatic
    fun hemLw(context: Context, value: Int): Float = value.hemLw(context)

    @JvmStatic
    fun hemLwa(context: Context, value: Int): Float = value.hemLwa(context)

    @JvmStatic
    fun hemLwi(context: Context, value: Int): Float = value.hemLwi(context)

    @JvmStatic
    fun hemLwia(context: Context, value: Int): Float = value.hemLwia(context)

    /**
     * EN Quick resolution for Screen Height without font scale, portrait is Screen Width.
     */
    @JvmStatic
    fun hemPw(context: Context, value: Int): Float = value.hemPw(context)

    @JvmStatic
    fun hemPwa(context: Context, value: Int): Float = value.hemPwa(context)

    @JvmStatic
    fun hemPwi(context: Context, value: Int): Float = value.hemPwi(context)

    @JvmStatic
    fun hemPwia(context: Context, value: Int): Float = value.hemPwia(context)

    /**
     * EN Quick resolution for Screen Width without font scale.
     */
    @JvmStatic
    fun wei(context: Context, value: Int): Float = value.wei(context)

    @JvmStatic
    fun weia(context: Context, value: Int): Float = value.weia(context)

    @JvmStatic
    fun weii(context: Context, value: Int): Float = value.weii(context)

    @JvmStatic
    fun weiia(context: Context, value: Int): Float = value.weiia(context)

    /**
     * EN Quick resolution for Screen Width without font scale, landscape is Screen Height.
     */
    @JvmStatic
    fun wemLh(context: Context, value: Int): Float = value.wemLh(context)

    @JvmStatic
    fun wemLha(context: Context, value: Int): Float = value.wemLha(context)

    @JvmStatic
    fun wemLhi(context: Context, value: Int): Float = value.wemLhi(context)

    @JvmStatic
    fun wemLhia(context: Context, value: Int): Float = value.wemLhia(context)

    /**
     * EN Quick resolution for Screen Width without font scale, portrait is Screen Height.
     */
    @JvmStatic
    fun wemPh(context: Context, value: Int): Float = value.wemPh(context)

    @JvmStatic
    fun wemPha(context: Context, value: Int): Float = value.wemPha(context)

    @JvmStatic
    fun wemPhi(context: Context, value: Int): Float = value.wemPhi(context)

    @JvmStatic
    fun wemPhia(context: Context, value: Int): Float = value.wemPhia(context)

    /**
     * EN Generic scaling function for Java (PX).
     * PT Função de escala genérica para Java (PX).
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInPx(
        context: Context,
        qualifier: DpQualifier,
        value: Int,
        fontScale: Boolean = true,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicScaledSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic scaling function for Java (SP value).
     * PT Função de escala genérica para Java (valor SP).
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInSp(
        context: Context,
        qualifier: DpQualifier,
        value: Int,
        fontScale: Boolean = true,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicScaledSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension ScaledSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada ScaledSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): ScaledSp = ScaledSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension ScaledSp from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): ScaledSp = ScaledSp(initialBaseValue.toInt())

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (ssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun sspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun hspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun wspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (ssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun sspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun hspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun wspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}