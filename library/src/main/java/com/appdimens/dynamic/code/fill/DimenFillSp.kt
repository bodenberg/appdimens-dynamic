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
package com.appdimens.dynamic.code.fill

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
object DimenFillSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (flssp).
     * PT Resolução rápida para Smallest Width (flssp).
     */
    @JvmStatic
    fun flssp(context: Context, value: Int): Float = value.flssp(context)

    @JvmStatic
    fun flsspa(context: Context, value: Int): Float = value.flsspa(context)

    @JvmStatic
    fun flsspi(context: Context, value: Int): Float = value.flsspi(context)

    @JvmStatic
    fun flsspia(context: Context, value: Int): Float = value.flsspia(context)

    /**
     * EN Quick resolution for Smallest Width (flssp), but in portrait orientation it acts as Screen Height (flhsp).
     */
    @JvmStatic
    fun flsspPh(context: Context, value: Int): Float = value.flsspPh(context)

    @JvmStatic
    fun flsspPha(context: Context, value: Int): Float = value.flsspPha(context)

    @JvmStatic
    fun flsspPhi(context: Context, value: Int): Float = value.flsspPhi(context)

    @JvmStatic
    fun flsspPhia(context: Context, value: Int): Float = value.flsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (flssp), but in landscape orientation it acts as Screen Height (flhsp).
     */
    @JvmStatic
    fun flsspLh(context: Context, value: Int): Float = value.flsspLh(context)

    @JvmStatic
    fun flsspLha(context: Context, value: Int): Float = value.flsspLha(context)

    @JvmStatic
    fun flsspLhi(context: Context, value: Int): Float = value.flsspLhi(context)

    @JvmStatic
    fun flsspLhia(context: Context, value: Int): Float = value.flsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (flssp), but in portrait orientation it acts as Screen Width (flwsp).
     */
    @JvmStatic
    fun flsspPw(context: Context, value: Int): Float = value.flsspPw(context)

    @JvmStatic
    fun flsspPwa(context: Context, value: Int): Float = value.flsspPwa(context)

    @JvmStatic
    fun flsspPwi(context: Context, value: Int): Float = value.flsspPwi(context)

    @JvmStatic
    fun flsspPwia(context: Context, value: Int): Float = value.flsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (flssp), but in landscape orientation it acts as Screen Width (flwsp).
     */
    @JvmStatic
    fun flsspLw(context: Context, value: Int): Float = value.flsspLw(context)

    @JvmStatic
    fun flsspLwa(context: Context, value: Int): Float = value.flsspLwa(context)

    @JvmStatic
    fun flsspLwi(context: Context, value: Int): Float = value.flsspLwi(context)

    @JvmStatic
    fun flsspLwia(context: Context, value: Int): Float = value.flsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (flhsp).
     * PT Resolução rápida para Altura da Tela (flhsp).
     */
    @JvmStatic
    fun flhsp(context: Context, value: Int): Float = value.flhsp(context)

    @JvmStatic
    fun flhspa(context: Context, value: Int): Float = value.flhspa(context)

    @JvmStatic
    fun flhspi(context: Context, value: Int): Float = value.flhspi(context)

    @JvmStatic
    fun flhspia(context: Context, value: Int): Float = value.flhspia(context)

    /**
     * EN Quick resolution for Screen Height (flhsp), but in landscape orientation it acts as Screen Width (flwsp).
     */
    @JvmStatic
    fun flhspLw(context: Context, value: Int): Float = value.flhspLw(context)

    @JvmStatic
    fun flhspLwa(context: Context, value: Int): Float = value.flhspLwa(context)

    @JvmStatic
    fun flhspLwi(context: Context, value: Int): Float = value.flhspLwi(context)

    @JvmStatic
    fun flhspLwia(context: Context, value: Int): Float = value.flhspLwia(context)

    /**
     * EN Quick resolution for Screen Height (flhsp), but in portrait orientation it acts as Screen Width (flwsp).
     */
    @JvmStatic
    fun flhspPw(context: Context, value: Int): Float = value.flhspPw(context)

    @JvmStatic
    fun flhspPwa(context: Context, value: Int): Float = value.flhspPwa(context)

    @JvmStatic
    fun flhspPwi(context: Context, value: Int): Float = value.flhspPwi(context)

    @JvmStatic
    fun flhspPwia(context: Context, value: Int): Float = value.flhspPwia(context)

    /**
     * EN Quick resolution for Screen Width (flwsp).
     * PT Resolução rápida para Largura da Tela (flwsp).
     */
    @JvmStatic
    fun flwsp(context: Context, value: Int): Float = value.flwsp(context)

    @JvmStatic
    fun flwspa(context: Context, value: Int): Float = value.flwspa(context)

    @JvmStatic
    fun flwspi(context: Context, value: Int): Float = value.flwspi(context)

    @JvmStatic
    fun flwspia(context: Context, value: Int): Float = value.flwspia(context)

    /**
     * EN Quick resolution for Screen Width (flwsp), but in landscape orientation it acts as Screen Height (flhsp).
     */
    @JvmStatic
    fun flwspLh(context: Context, value: Int): Float = value.flwspLh(context)

    @JvmStatic
    fun flwspLha(context: Context, value: Int): Float = value.flwspLha(context)

    @JvmStatic
    fun flwspLhi(context: Context, value: Int): Float = value.flwspLhi(context)

    @JvmStatic
    fun flwspLhia(context: Context, value: Int): Float = value.flwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (flwsp), but in portrait orientation it acts as Screen Height (flhsp).
     */
    @JvmStatic
    fun flwspPh(context: Context, value: Int): Float = value.flwspPh(context)

    @JvmStatic
    fun flwspPha(context: Context, value: Int): Float = value.flwspPha(context)

    @JvmStatic
    fun flwspPhi(context: Context, value: Int): Float = value.flwspPhi(context)

    @JvmStatic
    fun flwspPhia(context: Context, value: Int): Float = value.flwspPhia(context)


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
    fun flwemPh(context: Context, value: Int): Float = value.flwemPh(context)

    @JvmStatic
    fun flwemPha(context: Context, value: Int): Float = value.flwemPha(context)

    @JvmStatic
    fun flwemPhi(context: Context, value: Int): Float = value.flwemPhi(context)

    @JvmStatic
    fun flwemPhia(context: Context, value: Int): Float = value.flwemPhia(context)

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
    ): Float = value.toDynamicFillSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicFillSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension FillSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada FillSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): FillSp = FillSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension FillSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada FillSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): FillSp = FillSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flhspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flhspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flhspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flhspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun flwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (flssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun flsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (flhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun flhspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flhspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (flwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun flwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (flssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun flsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (flhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun flhspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flhspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (flwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun flwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.flwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}