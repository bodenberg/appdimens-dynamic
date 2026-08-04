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
package com.appdimens.dynamic.code.auto

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
object DimenAutoSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (assp).
     * PT Resolução rápida para Smallest Width (assp).
     */
    @JvmStatic
    fun assp(context: Context, value: Int): Float = value.assp(context)

    @JvmStatic
    fun asspa(context: Context, value: Int): Float = value.asspa(context)

    @JvmStatic
    fun asspi(context: Context, value: Int): Float = value.asspi(context)

    @JvmStatic
    fun asspia(context: Context, value: Int): Float = value.asspia(context)

    /**
     * EN Quick resolution for Smallest Width (assp), but in portrait orientation it acts as Screen Height (ahsp).
     */
    @JvmStatic
    fun asspPh(context: Context, value: Int): Float = value.asspPh(context)

    @JvmStatic
    fun asspPha(context: Context, value: Int): Float = value.asspPha(context)

    @JvmStatic
    fun asspPhi(context: Context, value: Int): Float = value.asspPhi(context)

    @JvmStatic
    fun asspPhia(context: Context, value: Int): Float = value.asspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (assp), but in landscape orientation it acts as Screen Height (ahsp).
     */
    @JvmStatic
    fun asspLh(context: Context, value: Int): Float = value.asspLh(context)

    @JvmStatic
    fun asspLha(context: Context, value: Int): Float = value.asspLha(context)

    @JvmStatic
    fun asspLhi(context: Context, value: Int): Float = value.asspLhi(context)

    @JvmStatic
    fun asspLhia(context: Context, value: Int): Float = value.asspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (assp), but in portrait orientation it acts as Screen Width (awsp).
     */
    @JvmStatic
    fun asspPw(context: Context, value: Int): Float = value.asspPw(context)

    @JvmStatic
    fun asspPwa(context: Context, value: Int): Float = value.asspPwa(context)

    @JvmStatic
    fun asspPwi(context: Context, value: Int): Float = value.asspPwi(context)

    @JvmStatic
    fun asspPwia(context: Context, value: Int): Float = value.asspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (assp), but in landscape orientation it acts as Screen Width (awsp).
     */
    @JvmStatic
    fun asspLw(context: Context, value: Int): Float = value.asspLw(context)

    @JvmStatic
    fun asspLwa(context: Context, value: Int): Float = value.asspLwa(context)

    @JvmStatic
    fun asspLwi(context: Context, value: Int): Float = value.asspLwi(context)

    @JvmStatic
    fun asspLwia(context: Context, value: Int): Float = value.asspLwia(context)

    /**
     * EN Quick resolution for Screen Height (ahsp).
     * PT Resolução rápida para Altura da Tela (ahsp).
     */
    @JvmStatic
    fun ahsp(context: Context, value: Int): Float = value.ahsp(context)

    @JvmStatic
    fun ahspa(context: Context, value: Int): Float = value.ahspa(context)

    @JvmStatic
    fun ahspi(context: Context, value: Int): Float = value.ahspi(context)

    @JvmStatic
    fun ahspia(context: Context, value: Int): Float = value.ahspia(context)

    /**
     * EN Quick resolution for Screen Height (ahsp), but in landscape orientation it acts as Screen Width (awsp).
     */
    @JvmStatic
    fun ahspLw(context: Context, value: Int): Float = value.ahspLw(context)

    @JvmStatic
    fun ahspLwa(context: Context, value: Int): Float = value.ahspLwa(context)

    @JvmStatic
    fun ahspLwi(context: Context, value: Int): Float = value.ahspLwi(context)

    @JvmStatic
    fun ahspLwia(context: Context, value: Int): Float = value.ahspLwia(context)

    /**
     * EN Quick resolution for Screen Height (ahsp), but in portrait orientation it acts as Screen Width (awsp).
     */
    @JvmStatic
    fun ahspPw(context: Context, value: Int): Float = value.ahspPw(context)

    @JvmStatic
    fun ahspPwa(context: Context, value: Int): Float = value.ahspPwa(context)

    @JvmStatic
    fun ahspPwi(context: Context, value: Int): Float = value.ahspPwi(context)

    @JvmStatic
    fun ahspPwia(context: Context, value: Int): Float = value.ahspPwia(context)

    /**
     * EN Quick resolution for Screen Width (awsp).
     * PT Resolução rápida para Largura da Tela (awsp).
     */
    @JvmStatic
    fun awsp(context: Context, value: Int): Float = value.awsp(context)

    @JvmStatic
    fun awspa(context: Context, value: Int): Float = value.awspa(context)

    @JvmStatic
    fun awspi(context: Context, value: Int): Float = value.awspi(context)

    @JvmStatic
    fun awspia(context: Context, value: Int): Float = value.awspia(context)

    /**
     * EN Quick resolution for Screen Width (awsp), but in landscape orientation it acts as Screen Height (ahsp).
     */
    @JvmStatic
    fun awspLh(context: Context, value: Int): Float = value.awspLh(context)

    @JvmStatic
    fun awspLha(context: Context, value: Int): Float = value.awspLha(context)

    @JvmStatic
    fun awspLhi(context: Context, value: Int): Float = value.awspLhi(context)

    @JvmStatic
    fun awspLhia(context: Context, value: Int): Float = value.awspLhia(context)

    /**
     * EN Quick resolution for Screen Width (awsp), but in portrait orientation it acts as Screen Height (ahsp).
     */
    @JvmStatic
    fun awspPh(context: Context, value: Int): Float = value.awspPh(context)

    @JvmStatic
    fun awspPha(context: Context, value: Int): Float = value.awspPha(context)

    @JvmStatic
    fun awspPhi(context: Context, value: Int): Float = value.awspPhi(context)

    @JvmStatic
    fun awspPhia(context: Context, value: Int): Float = value.awspPhia(context)


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
    fun awemPh(context: Context, value: Int): Float = value.awemPh(context)

    @JvmStatic
    fun awemPha(context: Context, value: Int): Float = value.awemPha(context)

    @JvmStatic
    fun awemPhi(context: Context, value: Int): Float = value.awemPhi(context)

    @JvmStatic
    fun awemPhia(context: Context, value: Int): Float = value.awemPhia(context)

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
    ): Float = value.toDynamicAutoSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicAutoSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension AutoSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada AutoSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): AutoSp = AutoSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension AutoSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada AutoSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): AutoSp = AutoSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun asspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ahspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun awspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun asspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ahspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun awspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (assp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun asspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (ahsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ahspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (awsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun awspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (assp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun asspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.asspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (ahsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ahspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ahspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (awsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun awspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.awspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}