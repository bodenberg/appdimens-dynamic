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
package com.appdimens.dynamic.code.fluid

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
object DimenFluidSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (fssp).
     * PT Resolução rápida para Smallest Width (fssp).
     */
    @JvmStatic
    fun fssp(context: Context, value: Int): Float = value.fssp(context)

    @JvmStatic
    fun fsspa(context: Context, value: Int): Float = value.fsspa(context)

    @JvmStatic
    fun fsspi(context: Context, value: Int): Float = value.fsspi(context)

    @JvmStatic
    fun fsspia(context: Context, value: Int): Float = value.fsspia(context)

    /**
     * EN Quick resolution for Smallest Width (fssp), but in portrait orientation it acts as Screen Height (fhsp).
     */
    @JvmStatic
    fun fsspPh(context: Context, value: Int): Float = value.fsspPh(context)

    @JvmStatic
    fun fsspPha(context: Context, value: Int): Float = value.fsspPha(context)

    @JvmStatic
    fun fsspPhi(context: Context, value: Int): Float = value.fsspPhi(context)

    @JvmStatic
    fun fsspPhia(context: Context, value: Int): Float = value.fsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (fssp), but in landscape orientation it acts as Screen Height (fhsp).
     */
    @JvmStatic
    fun fsspLh(context: Context, value: Int): Float = value.fsspLh(context)

    @JvmStatic
    fun fsspLha(context: Context, value: Int): Float = value.fsspLha(context)

    @JvmStatic
    fun fsspLhi(context: Context, value: Int): Float = value.fsspLhi(context)

    @JvmStatic
    fun fsspLhia(context: Context, value: Int): Float = value.fsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (fssp), but in portrait orientation it acts as Screen Width (fwsp).
     */
    @JvmStatic
    fun fsspPw(context: Context, value: Int): Float = value.fsspPw(context)

    @JvmStatic
    fun fsspPwa(context: Context, value: Int): Float = value.fsspPwa(context)

    @JvmStatic
    fun fsspPwi(context: Context, value: Int): Float = value.fsspPwi(context)

    @JvmStatic
    fun fsspPwia(context: Context, value: Int): Float = value.fsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (fssp), but in landscape orientation it acts as Screen Width (fwsp).
     */
    @JvmStatic
    fun fsspLw(context: Context, value: Int): Float = value.fsspLw(context)

    @JvmStatic
    fun fsspLwa(context: Context, value: Int): Float = value.fsspLwa(context)

    @JvmStatic
    fun fsspLwi(context: Context, value: Int): Float = value.fsspLwi(context)

    @JvmStatic
    fun fsspLwia(context: Context, value: Int): Float = value.fsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (fhsp).
     * PT Resolução rápida para Altura da Tela (fhsp).
     */
    @JvmStatic
    fun fhsp(context: Context, value: Int): Float = value.fhsp(context)

    @JvmStatic
    fun fhspa(context: Context, value: Int): Float = value.fhspa(context)

    @JvmStatic
    fun fhspi(context: Context, value: Int): Float = value.fhspi(context)

    @JvmStatic
    fun fhspia(context: Context, value: Int): Float = value.fhspia(context)

    /**
     * EN Quick resolution for Screen Height (fhsp), but in landscape orientation it acts as Screen Width (fwsp).
     */
    @JvmStatic
    fun fhspLw(context: Context, value: Int): Float = value.fhspLw(context)

    @JvmStatic
    fun fhspLwa(context: Context, value: Int): Float = value.fhspLwa(context)

    @JvmStatic
    fun fhspLwi(context: Context, value: Int): Float = value.fhspLwi(context)

    @JvmStatic
    fun fhspLwia(context: Context, value: Int): Float = value.fhspLwia(context)

    /**
     * EN Quick resolution for Screen Height (fhsp), but in portrait orientation it acts as Screen Width (fwsp).
     */
    @JvmStatic
    fun fhspPw(context: Context, value: Int): Float = value.fhspPw(context)

    @JvmStatic
    fun fhspPwa(context: Context, value: Int): Float = value.fhspPwa(context)

    @JvmStatic
    fun fhspPwi(context: Context, value: Int): Float = value.fhspPwi(context)

    @JvmStatic
    fun fhspPwia(context: Context, value: Int): Float = value.fhspPwia(context)

    /**
     * EN Quick resolution for Screen Width (fwsp).
     * PT Resolução rápida para Largura da Tela (fwsp).
     */
    @JvmStatic
    fun fwsp(context: Context, value: Int): Float = value.fwsp(context)

    @JvmStatic
    fun fwspa(context: Context, value: Int): Float = value.fwspa(context)

    @JvmStatic
    fun fwspi(context: Context, value: Int): Float = value.fwspi(context)

    @JvmStatic
    fun fwspia(context: Context, value: Int): Float = value.fwspia(context)

    /**
     * EN Quick resolution for Screen Width (fwsp), but in landscape orientation it acts as Screen Height (fhsp).
     */
    @JvmStatic
    fun fwspLh(context: Context, value: Int): Float = value.fwspLh(context)

    @JvmStatic
    fun fwspLha(context: Context, value: Int): Float = value.fwspLha(context)

    @JvmStatic
    fun fwspLhi(context: Context, value: Int): Float = value.fwspLhi(context)

    @JvmStatic
    fun fwspLhia(context: Context, value: Int): Float = value.fwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (fwsp), but in portrait orientation it acts as Screen Height (fhsp).
     */
    @JvmStatic
    fun fwspPh(context: Context, value: Int): Float = value.fwspPh(context)

    @JvmStatic
    fun fwspPha(context: Context, value: Int): Float = value.fwspPha(context)

    @JvmStatic
    fun fwspPhi(context: Context, value: Int): Float = value.fwspPhi(context)

    @JvmStatic
    fun fwspPhia(context: Context, value: Int): Float = value.fwspPhia(context)


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
    fun fwemPh(context: Context, value: Int): Float = value.fwemPh(context)

    @JvmStatic
    fun fwemPha(context: Context, value: Int): Float = value.fwemPha(context)

    @JvmStatic
    fun fwemPhi(context: Context, value: Int): Float = value.fwemPhi(context)

    @JvmStatic
    fun fwemPhia(context: Context, value: Int): Float = value.fwemPhia(context)

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
    ): Float = value.toDynamicFluidSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicFluidSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension FluidSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada FluidSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): FluidSp = FluidSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension FluidSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada FluidSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): FluidSp = FluidSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fhspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fhspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (fssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (fhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fhspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (fwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (fssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (fhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fhspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fhspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (fwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}