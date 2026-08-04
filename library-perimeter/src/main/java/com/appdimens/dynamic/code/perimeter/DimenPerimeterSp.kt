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
package com.appdimens.dynamic.code.perimeter

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
object DimenPerimeterSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (prssp).
     * PT Resolução rápida para Smallest Width (prssp).
     */
    @JvmStatic
    fun prssp(context: Context, value: Int): Float = value.prssp(context)

    @JvmStatic
    fun prsspa(context: Context, value: Int): Float = value.prsspa(context)

    @JvmStatic
    fun prsspi(context: Context, value: Int): Float = value.prsspi(context)

    @JvmStatic
    fun prsspia(context: Context, value: Int): Float = value.prsspia(context)

    /**
     * EN Quick resolution for Smallest Width (prssp), but in portrait orientation it acts as Screen Height (prhsp).
     */
    @JvmStatic
    fun prsspPh(context: Context, value: Int): Float = value.prsspPh(context)

    @JvmStatic
    fun prsspPha(context: Context, value: Int): Float = value.prsspPha(context)

    @JvmStatic
    fun prsspPhi(context: Context, value: Int): Float = value.prsspPhi(context)

    @JvmStatic
    fun prsspPhia(context: Context, value: Int): Float = value.prsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (prssp), but in landscape orientation it acts as Screen Height (prhsp).
     */
    @JvmStatic
    fun prsspLh(context: Context, value: Int): Float = value.prsspLh(context)

    @JvmStatic
    fun prsspLha(context: Context, value: Int): Float = value.prsspLha(context)

    @JvmStatic
    fun prsspLhi(context: Context, value: Int): Float = value.prsspLhi(context)

    @JvmStatic
    fun prsspLhia(context: Context, value: Int): Float = value.prsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (prssp), but in portrait orientation it acts as Screen Width (prwsp).
     */
    @JvmStatic
    fun prsspPw(context: Context, value: Int): Float = value.prsspPw(context)

    @JvmStatic
    fun prsspPwa(context: Context, value: Int): Float = value.prsspPwa(context)

    @JvmStatic
    fun prsspPwi(context: Context, value: Int): Float = value.prsspPwi(context)

    @JvmStatic
    fun prsspPwia(context: Context, value: Int): Float = value.prsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (prssp), but in landscape orientation it acts as Screen Width (prwsp).
     */
    @JvmStatic
    fun prsspLw(context: Context, value: Int): Float = value.prsspLw(context)

    @JvmStatic
    fun prsspLwa(context: Context, value: Int): Float = value.prsspLwa(context)

    @JvmStatic
    fun prsspLwi(context: Context, value: Int): Float = value.prsspLwi(context)

    @JvmStatic
    fun prsspLwia(context: Context, value: Int): Float = value.prsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (prhsp).
     * PT Resolução rápida para Altura da Tela (prhsp).
     */
    @JvmStatic
    fun prhsp(context: Context, value: Int): Float = value.prhsp(context)

    @JvmStatic
    fun prhspa(context: Context, value: Int): Float = value.prhspa(context)

    @JvmStatic
    fun prhspi(context: Context, value: Int): Float = value.prhspi(context)

    @JvmStatic
    fun prhspia(context: Context, value: Int): Float = value.prhspia(context)

    /**
     * EN Quick resolution for Screen Height (prhsp), but in landscape orientation it acts as Screen Width (prwsp).
     */
    @JvmStatic
    fun prhspLw(context: Context, value: Int): Float = value.prhspLw(context)

    @JvmStatic
    fun prhspLwa(context: Context, value: Int): Float = value.prhspLwa(context)

    @JvmStatic
    fun prhspLwi(context: Context, value: Int): Float = value.prhspLwi(context)

    @JvmStatic
    fun prhspLwia(context: Context, value: Int): Float = value.prhspLwia(context)

    /**
     * EN Quick resolution for Screen Height (prhsp), but in portrait orientation it acts as Screen Width (prwsp).
     */
    @JvmStatic
    fun prhspPw(context: Context, value: Int): Float = value.prhspPw(context)

    @JvmStatic
    fun prhspPwa(context: Context, value: Int): Float = value.prhspPwa(context)

    @JvmStatic
    fun prhspPwi(context: Context, value: Int): Float = value.prhspPwi(context)

    @JvmStatic
    fun prhspPwia(context: Context, value: Int): Float = value.prhspPwia(context)

    /**
     * EN Quick resolution for Screen Width (prwsp).
     * PT Resolução rápida para Largura da Tela (prwsp).
     */
    @JvmStatic
    fun prwsp(context: Context, value: Int): Float = value.prwsp(context)

    @JvmStatic
    fun prwspa(context: Context, value: Int): Float = value.prwspa(context)

    @JvmStatic
    fun prwspi(context: Context, value: Int): Float = value.prwspi(context)

    @JvmStatic
    fun prwspia(context: Context, value: Int): Float = value.prwspia(context)

    /**
     * EN Quick resolution for Screen Width (prwsp), but in landscape orientation it acts as Screen Height (prhsp).
     */
    @JvmStatic
    fun prwspLh(context: Context, value: Int): Float = value.prwspLh(context)

    @JvmStatic
    fun prwspLha(context: Context, value: Int): Float = value.prwspLha(context)

    @JvmStatic
    fun prwspLhi(context: Context, value: Int): Float = value.prwspLhi(context)

    @JvmStatic
    fun prwspLhia(context: Context, value: Int): Float = value.prwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (prwsp), but in portrait orientation it acts as Screen Height (prhsp).
     */
    @JvmStatic
    fun prwspPh(context: Context, value: Int): Float = value.prwspPh(context)

    @JvmStatic
    fun prwspPha(context: Context, value: Int): Float = value.prwspPha(context)

    @JvmStatic
    fun prwspPhi(context: Context, value: Int): Float = value.prwspPhi(context)

    @JvmStatic
    fun prwspPhia(context: Context, value: Int): Float = value.prwspPhia(context)


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
    fun prwemPh(context: Context, value: Int): Float = value.prwemPh(context)

    @JvmStatic
    fun prwemPha(context: Context, value: Int): Float = value.prwemPha(context)

    @JvmStatic
    fun prwemPhi(context: Context, value: Int): Float = value.prwemPhi(context)

    @JvmStatic
    fun prwemPhia(context: Context, value: Int): Float = value.prwemPhia(context)

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
    ): Float = value.toDynamicPerimeterSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPerimeterSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension PerimeterSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada PerimeterSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): PerimeterSp = PerimeterSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension PerimeterSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada PerimeterSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): PerimeterSp = PerimeterSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prhspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prhspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun prwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (prssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (prhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prhspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (prwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun prwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (prssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (prhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prhspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prhspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (prwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun prwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.prwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}