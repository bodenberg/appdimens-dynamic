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
package com.appdimens.dynamic.code.logarithmic

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
object DimenLogarithmicSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (logssp).
     * PT Resolução rápida para Smallest Width (logssp).
     */
    @JvmStatic
    fun logssp(context: Context, value: Int): Float = value.logssp(context)

    @JvmStatic
    fun logsspa(context: Context, value: Int): Float = value.logsspa(context)

    @JvmStatic
    fun logsspi(context: Context, value: Int): Float = value.logsspi(context)

    @JvmStatic
    fun logsspia(context: Context, value: Int): Float = value.logsspia(context)

    /**
     * EN Quick resolution for Smallest Width (logssp), but in portrait orientation it acts as Screen Height (loghsp).
     */
    @JvmStatic
    fun logsspPh(context: Context, value: Int): Float = value.logsspPh(context)

    @JvmStatic
    fun logsspPha(context: Context, value: Int): Float = value.logsspPha(context)

    @JvmStatic
    fun logsspPhi(context: Context, value: Int): Float = value.logsspPhi(context)

    @JvmStatic
    fun logsspPhia(context: Context, value: Int): Float = value.logsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (logssp), but in landscape orientation it acts as Screen Height (loghsp).
     */
    @JvmStatic
    fun logsspLh(context: Context, value: Int): Float = value.logsspLh(context)

    @JvmStatic
    fun logsspLha(context: Context, value: Int): Float = value.logsspLha(context)

    @JvmStatic
    fun logsspLhi(context: Context, value: Int): Float = value.logsspLhi(context)

    @JvmStatic
    fun logsspLhia(context: Context, value: Int): Float = value.logsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (logssp), but in portrait orientation it acts as Screen Width (logwsp).
     */
    @JvmStatic
    fun logsspPw(context: Context, value: Int): Float = value.logsspPw(context)

    @JvmStatic
    fun logsspPwa(context: Context, value: Int): Float = value.logsspPwa(context)

    @JvmStatic
    fun logsspPwi(context: Context, value: Int): Float = value.logsspPwi(context)

    @JvmStatic
    fun logsspPwia(context: Context, value: Int): Float = value.logsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (logssp), but in landscape orientation it acts as Screen Width (logwsp).
     */
    @JvmStatic
    fun logsspLw(context: Context, value: Int): Float = value.logsspLw(context)

    @JvmStatic
    fun logsspLwa(context: Context, value: Int): Float = value.logsspLwa(context)

    @JvmStatic
    fun logsspLwi(context: Context, value: Int): Float = value.logsspLwi(context)

    @JvmStatic
    fun logsspLwia(context: Context, value: Int): Float = value.logsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (loghsp).
     * PT Resolução rápida para Altura da Tela (loghsp).
     */
    @JvmStatic
    fun loghsp(context: Context, value: Int): Float = value.loghsp(context)

    @JvmStatic
    fun loghspa(context: Context, value: Int): Float = value.loghspa(context)

    @JvmStatic
    fun loghspi(context: Context, value: Int): Float = value.loghspi(context)

    @JvmStatic
    fun loghspia(context: Context, value: Int): Float = value.loghspia(context)

    /**
     * EN Quick resolution for Screen Height (loghsp), but in landscape orientation it acts as Screen Width (logwsp).
     */
    @JvmStatic
    fun loghspLw(context: Context, value: Int): Float = value.loghspLw(context)

    @JvmStatic
    fun loghspLwa(context: Context, value: Int): Float = value.loghspLwa(context)

    @JvmStatic
    fun loghspLwi(context: Context, value: Int): Float = value.loghspLwi(context)

    @JvmStatic
    fun loghspLwia(context: Context, value: Int): Float = value.loghspLwia(context)

    /**
     * EN Quick resolution for Screen Height (loghsp), but in portrait orientation it acts as Screen Width (logwsp).
     */
    @JvmStatic
    fun loghspPw(context: Context, value: Int): Float = value.loghspPw(context)

    @JvmStatic
    fun loghspPwa(context: Context, value: Int): Float = value.loghspPwa(context)

    @JvmStatic
    fun loghspPwi(context: Context, value: Int): Float = value.loghspPwi(context)

    @JvmStatic
    fun loghspPwia(context: Context, value: Int): Float = value.loghspPwia(context)

    /**
     * EN Quick resolution for Screen Width (logwsp).
     * PT Resolução rápida para Largura da Tela (logwsp).
     */
    @JvmStatic
    fun logwsp(context: Context, value: Int): Float = value.logwsp(context)

    @JvmStatic
    fun logwspa(context: Context, value: Int): Float = value.logwspa(context)

    @JvmStatic
    fun logwspi(context: Context, value: Int): Float = value.logwspi(context)

    @JvmStatic
    fun logwspia(context: Context, value: Int): Float = value.logwspia(context)

    /**
     * EN Quick resolution for Screen Width (logwsp), but in landscape orientation it acts as Screen Height (loghsp).
     */
    @JvmStatic
    fun logwspLh(context: Context, value: Int): Float = value.logwspLh(context)

    @JvmStatic
    fun logwspLha(context: Context, value: Int): Float = value.logwspLha(context)

    @JvmStatic
    fun logwspLhi(context: Context, value: Int): Float = value.logwspLhi(context)

    @JvmStatic
    fun logwspLhia(context: Context, value: Int): Float = value.logwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (logwsp), but in portrait orientation it acts as Screen Height (loghsp).
     */
    @JvmStatic
    fun logwspPh(context: Context, value: Int): Float = value.logwspPh(context)

    @JvmStatic
    fun logwspPha(context: Context, value: Int): Float = value.logwspPha(context)

    @JvmStatic
    fun logwspPhi(context: Context, value: Int): Float = value.logwspPhi(context)

    @JvmStatic
    fun logwspPhia(context: Context, value: Int): Float = value.logwspPhia(context)


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
    fun logwemPh(context: Context, value: Int): Float = value.logwemPh(context)

    @JvmStatic
    fun logwemPha(context: Context, value: Int): Float = value.logwemPha(context)

    @JvmStatic
    fun logwemPhi(context: Context, value: Int): Float = value.logwemPhi(context)

    @JvmStatic
    fun logwemPhia(context: Context, value: Int): Float = value.logwemPhia(context)

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
    ): Float = value.toDynamicLogarithmicSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicLogarithmicSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension LogarithmicSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada LogarithmicSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): LogarithmicSp = LogarithmicSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension LogarithmicSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada LogarithmicSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): LogarithmicSp = LogarithmicSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun loghspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun loghspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (logssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun logsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (loghsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun loghspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (logwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun logwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (logssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun logsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (loghsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun loghspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (logwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun logwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}