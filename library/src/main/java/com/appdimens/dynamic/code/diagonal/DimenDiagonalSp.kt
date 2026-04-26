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
package com.appdimens.dynamic.code.diagonal

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
object DimenDiagonalSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (dgssp).
     * PT Resolução rápida para Smallest Width (dgssp).
     */
    @JvmStatic
    fun dgssp(context: Context, value: Int): Float = value.dgssp(context)

    @JvmStatic
    fun dgsspa(context: Context, value: Int): Float = value.dgsspa(context)

    @JvmStatic
    fun dgsspi(context: Context, value: Int): Float = value.dgsspi(context)

    @JvmStatic
    fun dgsspia(context: Context, value: Int): Float = value.dgsspia(context)

    /**
     * EN Quick resolution for Smallest Width (dgssp), but in portrait orientation it acts as Screen Height (dghsp).
     */
    @JvmStatic
    fun dgsspPh(context: Context, value: Int): Float = value.dgsspPh(context)

    @JvmStatic
    fun dgsspPha(context: Context, value: Int): Float = value.dgsspPha(context)

    @JvmStatic
    fun dgsspPhi(context: Context, value: Int): Float = value.dgsspPhi(context)

    @JvmStatic
    fun dgsspPhia(context: Context, value: Int): Float = value.dgsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (dgssp), but in landscape orientation it acts as Screen Height (dghsp).
     */
    @JvmStatic
    fun dgsspLh(context: Context, value: Int): Float = value.dgsspLh(context)

    @JvmStatic
    fun dgsspLha(context: Context, value: Int): Float = value.dgsspLha(context)

    @JvmStatic
    fun dgsspLhi(context: Context, value: Int): Float = value.dgsspLhi(context)

    @JvmStatic
    fun dgsspLhia(context: Context, value: Int): Float = value.dgsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (dgssp), but in portrait orientation it acts as Screen Width (dgwsp).
     */
    @JvmStatic
    fun dgsspPw(context: Context, value: Int): Float = value.dgsspPw(context)

    @JvmStatic
    fun dgsspPwa(context: Context, value: Int): Float = value.dgsspPwa(context)

    @JvmStatic
    fun dgsspPwi(context: Context, value: Int): Float = value.dgsspPwi(context)

    @JvmStatic
    fun dgsspPwia(context: Context, value: Int): Float = value.dgsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (dgssp), but in landscape orientation it acts as Screen Width (dgwsp).
     */
    @JvmStatic
    fun dgsspLw(context: Context, value: Int): Float = value.dgsspLw(context)

    @JvmStatic
    fun dgsspLwa(context: Context, value: Int): Float = value.dgsspLwa(context)

    @JvmStatic
    fun dgsspLwi(context: Context, value: Int): Float = value.dgsspLwi(context)

    @JvmStatic
    fun dgsspLwia(context: Context, value: Int): Float = value.dgsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (dghsp).
     * PT Resolução rápida para Altura da Tela (dghsp).
     */
    @JvmStatic
    fun dghsp(context: Context, value: Int): Float = value.dghsp(context)

    @JvmStatic
    fun dghspa(context: Context, value: Int): Float = value.dghspa(context)

    @JvmStatic
    fun dghspi(context: Context, value: Int): Float = value.dghspi(context)

    @JvmStatic
    fun dghspia(context: Context, value: Int): Float = value.dghspia(context)

    /**
     * EN Quick resolution for Screen Height (dghsp), but in landscape orientation it acts as Screen Width (dgwsp).
     */
    @JvmStatic
    fun dghspLw(context: Context, value: Int): Float = value.dghspLw(context)

    @JvmStatic
    fun dghspLwa(context: Context, value: Int): Float = value.dghspLwa(context)

    @JvmStatic
    fun dghspLwi(context: Context, value: Int): Float = value.dghspLwi(context)

    @JvmStatic
    fun dghspLwia(context: Context, value: Int): Float = value.dghspLwia(context)

    /**
     * EN Quick resolution for Screen Height (dghsp), but in portrait orientation it acts as Screen Width (dgwsp).
     */
    @JvmStatic
    fun dghspPw(context: Context, value: Int): Float = value.dghspPw(context)

    @JvmStatic
    fun dghspPwa(context: Context, value: Int): Float = value.dghspPwa(context)

    @JvmStatic
    fun dghspPwi(context: Context, value: Int): Float = value.dghspPwi(context)

    @JvmStatic
    fun dghspPwia(context: Context, value: Int): Float = value.dghspPwia(context)

    /**
     * EN Quick resolution for Screen Width (dgwsp).
     * PT Resolução rápida para Largura da Tela (dgwsp).
     */
    @JvmStatic
    fun dgwsp(context: Context, value: Int): Float = value.dgwsp(context)

    @JvmStatic
    fun dgwspa(context: Context, value: Int): Float = value.dgwspa(context)

    @JvmStatic
    fun dgwspi(context: Context, value: Int): Float = value.dgwspi(context)

    @JvmStatic
    fun dgwspia(context: Context, value: Int): Float = value.dgwspia(context)

    /**
     * EN Quick resolution for Screen Width (dgwsp), but in landscape orientation it acts as Screen Height (dghsp).
     */
    @JvmStatic
    fun dgwspLh(context: Context, value: Int): Float = value.dgwspLh(context)

    @JvmStatic
    fun dgwspLha(context: Context, value: Int): Float = value.dgwspLha(context)

    @JvmStatic
    fun dgwspLhi(context: Context, value: Int): Float = value.dgwspLhi(context)

    @JvmStatic
    fun dgwspLhia(context: Context, value: Int): Float = value.dgwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (dgwsp), but in portrait orientation it acts as Screen Height (dghsp).
     */
    @JvmStatic
    fun dgwspPh(context: Context, value: Int): Float = value.dgwspPh(context)

    @JvmStatic
    fun dgwspPha(context: Context, value: Int): Float = value.dgwspPha(context)

    @JvmStatic
    fun dgwspPhi(context: Context, value: Int): Float = value.dgwspPhi(context)

    @JvmStatic
    fun dgwspPhia(context: Context, value: Int): Float = value.dgwspPhia(context)


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
    fun dgwemPh(context: Context, value: Int): Float = value.dgwemPh(context)

    @JvmStatic
    fun dgwemPha(context: Context, value: Int): Float = value.dgwemPha(context)

    @JvmStatic
    fun dgwemPhi(context: Context, value: Int): Float = value.dgwemPhi(context)

    @JvmStatic
    fun dgwemPhia(context: Context, value: Int): Float = value.dgwemPhia(context)

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
    ): Float = value.toDynamicDiagonalSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicDiagonalSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DiagonalSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DiagonalSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DiagonalSp = DiagonalSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension DiagonalSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada DiagonalSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DiagonalSp = DiagonalSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dghspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dghspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (dgssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dghsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dghspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dgwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (dgssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dghsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dghspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dghspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dgwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dgwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dgwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}