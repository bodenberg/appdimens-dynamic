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
package com.appdimens.dynamic.code.percent

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
object DimenPercentSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (pssp).
     * PT Resolução rápida para Smallest Width (pssp).
     */
    @JvmStatic
    fun pssp(context: Context, value: Int): Float = value.pssp(context)

    @JvmStatic
    fun psspa(context: Context, value: Int): Float = value.psspa(context)

    @JvmStatic
    fun psspi(context: Context, value: Int): Float = value.psspi(context)

    @JvmStatic
    fun psspia(context: Context, value: Int): Float = value.psspia(context)

    /**
     * EN Quick resolution for Smallest Width (pssp), but in portrait orientation it acts as Screen Height (phsp).
     */
    @JvmStatic
    fun psspPh(context: Context, value: Int): Float = value.psspPh(context)

    @JvmStatic
    fun psspPha(context: Context, value: Int): Float = value.psspPha(context)

    @JvmStatic
    fun psspPhi(context: Context, value: Int): Float = value.psspPhi(context)

    @JvmStatic
    fun psspPhia(context: Context, value: Int): Float = value.psspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (pssp), but in landscape orientation it acts as Screen Height (phsp).
     */
    @JvmStatic
    fun psspLh(context: Context, value: Int): Float = value.psspLh(context)

    @JvmStatic
    fun psspLha(context: Context, value: Int): Float = value.psspLha(context)

    @JvmStatic
    fun psspLhi(context: Context, value: Int): Float = value.psspLhi(context)

    @JvmStatic
    fun psspLhia(context: Context, value: Int): Float = value.psspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (pssp), but in portrait orientation it acts as Screen Width (pwsp).
     */
    @JvmStatic
    fun psspPw(context: Context, value: Int): Float = value.psspPw(context)

    @JvmStatic
    fun psspPwa(context: Context, value: Int): Float = value.psspPwa(context)

    @JvmStatic
    fun psspPwi(context: Context, value: Int): Float = value.psspPwi(context)

    @JvmStatic
    fun psspPwia(context: Context, value: Int): Float = value.psspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (pssp), but in landscape orientation it acts as Screen Width (pwsp).
     */
    @JvmStatic
    fun psspLw(context: Context, value: Int): Float = value.psspLw(context)

    @JvmStatic
    fun psspLwa(context: Context, value: Int): Float = value.psspLwa(context)

    @JvmStatic
    fun psspLwi(context: Context, value: Int): Float = value.psspLwi(context)

    @JvmStatic
    fun psspLwia(context: Context, value: Int): Float = value.psspLwia(context)

    /**
     * EN Quick resolution for Screen Height (phsp).
     * PT Resolução rápida para Altura da Tela (phsp).
     */
    @JvmStatic
    fun phsp(context: Context, value: Int): Float = value.phsp(context)

    @JvmStatic
    fun phspa(context: Context, value: Int): Float = value.phspa(context)

    @JvmStatic
    fun phspi(context: Context, value: Int): Float = value.phspi(context)

    @JvmStatic
    fun phspia(context: Context, value: Int): Float = value.phspia(context)

    /**
     * EN Quick resolution for Screen Height (phsp), but in landscape orientation it acts as Screen Width (pwsp).
     */
    @JvmStatic
    fun phspLw(context: Context, value: Int): Float = value.phspLw(context)

    @JvmStatic
    fun phspLwa(context: Context, value: Int): Float = value.phspLwa(context)

    @JvmStatic
    fun phspLwi(context: Context, value: Int): Float = value.phspLwi(context)

    @JvmStatic
    fun phspLwia(context: Context, value: Int): Float = value.phspLwia(context)

    /**
     * EN Quick resolution for Screen Height (phsp), but in portrait orientation it acts as Screen Width (pwsp).
     */
    @JvmStatic
    fun phspPw(context: Context, value: Int): Float = value.phspPw(context)

    @JvmStatic
    fun phspPwa(context: Context, value: Int): Float = value.phspPwa(context)

    @JvmStatic
    fun phspPwi(context: Context, value: Int): Float = value.phspPwi(context)

    @JvmStatic
    fun phspPwia(context: Context, value: Int): Float = value.phspPwia(context)

    /**
     * EN Quick resolution for Screen Width (pwsp).
     * PT Resolução rápida para Largura da Tela (pwsp).
     */
    @JvmStatic
    fun pwsp(context: Context, value: Int): Float = value.pwsp(context)

    @JvmStatic
    fun pwspa(context: Context, value: Int): Float = value.pwspa(context)

    @JvmStatic
    fun pwspi(context: Context, value: Int): Float = value.pwspi(context)

    @JvmStatic
    fun pwspia(context: Context, value: Int): Float = value.pwspia(context)

    /**
     * EN Quick resolution for Screen Width (pwsp), but in landscape orientation it acts as Screen Height (phsp).
     */
    @JvmStatic
    fun pwspLh(context: Context, value: Int): Float = value.pwspLh(context)

    @JvmStatic
    fun pwspLha(context: Context, value: Int): Float = value.pwspLha(context)

    @JvmStatic
    fun pwspLhi(context: Context, value: Int): Float = value.pwspLhi(context)

    @JvmStatic
    fun pwspLhia(context: Context, value: Int): Float = value.pwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (pwsp), but in portrait orientation it acts as Screen Height (phsp).
     */
    @JvmStatic
    fun pwspPh(context: Context, value: Int): Float = value.pwspPh(context)

    @JvmStatic
    fun pwspPha(context: Context, value: Int): Float = value.pwspPha(context)

    @JvmStatic
    fun pwspPhi(context: Context, value: Int): Float = value.pwspPhi(context)

    @JvmStatic
    fun pwspPhia(context: Context, value: Int): Float = value.pwspPhia(context)


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
    fun pwemPh(context: Context, value: Int): Float = value.pwemPh(context)

    @JvmStatic
    fun pwemPha(context: Context, value: Int): Float = value.pwemPha(context)

    @JvmStatic
    fun pwemPhi(context: Context, value: Int): Float = value.pwemPhi(context)

    @JvmStatic
    fun pwemPhia(context: Context, value: Int): Float = value.pwemPhia(context)

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
    ): Float = value.toDynamicPercentSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPercentSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension PercentSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada PercentSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): PercentSp = PercentSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension PercentSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada PercentSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): PercentSp = PercentSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun psspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun phspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun psspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun phspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (pssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun psspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (phsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun phspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (pwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (pssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun psspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (phsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun phspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (pwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}