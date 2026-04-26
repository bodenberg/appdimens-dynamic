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
package com.appdimens.dynamic.code.power

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
object DimenPowerSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (pwssp).
     * PT Resolução rápida para Smallest Width (pwssp).
     */
    @JvmStatic
    fun pwssp(context: Context, value: Int): Float = value.pwssp(context)

    @JvmStatic
    fun pwsspa(context: Context, value: Int): Float = value.pwsspa(context)

    @JvmStatic
    fun pwsspi(context: Context, value: Int): Float = value.pwsspi(context)

    @JvmStatic
    fun pwsspia(context: Context, value: Int): Float = value.pwsspia(context)

    /**
     * EN Quick resolution for Smallest Width (pwssp), but in portrait orientation it acts as Screen Height (pwhsp).
     */
    @JvmStatic
    fun pwsspPh(context: Context, value: Int): Float = value.pwsspPh(context)

    @JvmStatic
    fun pwsspPha(context: Context, value: Int): Float = value.pwsspPha(context)

    @JvmStatic
    fun pwsspPhi(context: Context, value: Int): Float = value.pwsspPhi(context)

    @JvmStatic
    fun pwsspPhia(context: Context, value: Int): Float = value.pwsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (pwssp), but in landscape orientation it acts as Screen Height (pwhsp).
     */
    @JvmStatic
    fun pwsspLh(context: Context, value: Int): Float = value.pwsspLh(context)

    @JvmStatic
    fun pwsspLha(context: Context, value: Int): Float = value.pwsspLha(context)

    @JvmStatic
    fun pwsspLhi(context: Context, value: Int): Float = value.pwsspLhi(context)

    @JvmStatic
    fun pwsspLhia(context: Context, value: Int): Float = value.pwsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (pwssp), but in portrait orientation it acts as Screen Width (pwwsp).
     */
    @JvmStatic
    fun pwsspPw(context: Context, value: Int): Float = value.pwsspPw(context)

    @JvmStatic
    fun pwsspPwa(context: Context, value: Int): Float = value.pwsspPwa(context)

    @JvmStatic
    fun pwsspPwi(context: Context, value: Int): Float = value.pwsspPwi(context)

    @JvmStatic
    fun pwsspPwia(context: Context, value: Int): Float = value.pwsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (pwssp), but in landscape orientation it acts as Screen Width (pwwsp).
     */
    @JvmStatic
    fun pwsspLw(context: Context, value: Int): Float = value.pwsspLw(context)

    @JvmStatic
    fun pwsspLwa(context: Context, value: Int): Float = value.pwsspLwa(context)

    @JvmStatic
    fun pwsspLwi(context: Context, value: Int): Float = value.pwsspLwi(context)

    @JvmStatic
    fun pwsspLwia(context: Context, value: Int): Float = value.pwsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (pwhsp).
     * PT Resolução rápida para Altura da Tela (pwhsp).
     */
    @JvmStatic
    fun pwhsp(context: Context, value: Int): Float = value.pwhsp(context)

    @JvmStatic
    fun pwhspa(context: Context, value: Int): Float = value.pwhspa(context)

    @JvmStatic
    fun pwhspi(context: Context, value: Int): Float = value.pwhspi(context)

    @JvmStatic
    fun pwhspia(context: Context, value: Int): Float = value.pwhspia(context)

    /**
     * EN Quick resolution for Screen Height (pwhsp), but in landscape orientation it acts as Screen Width (pwwsp).
     */
    @JvmStatic
    fun pwhspLw(context: Context, value: Int): Float = value.pwhspLw(context)

    @JvmStatic
    fun pwhspLwa(context: Context, value: Int): Float = value.pwhspLwa(context)

    @JvmStatic
    fun pwhspLwi(context: Context, value: Int): Float = value.pwhspLwi(context)

    @JvmStatic
    fun pwhspLwia(context: Context, value: Int): Float = value.pwhspLwia(context)

    /**
     * EN Quick resolution for Screen Height (pwhsp), but in portrait orientation it acts as Screen Width (pwwsp).
     */
    @JvmStatic
    fun pwhspPw(context: Context, value: Int): Float = value.pwhspPw(context)

    @JvmStatic
    fun pwhspPwa(context: Context, value: Int): Float = value.pwhspPwa(context)

    @JvmStatic
    fun pwhspPwi(context: Context, value: Int): Float = value.pwhspPwi(context)

    @JvmStatic
    fun pwhspPwia(context: Context, value: Int): Float = value.pwhspPwia(context)

    /**
     * EN Quick resolution for Screen Width (pwwsp).
     * PT Resolução rápida para Largura da Tela (pwwsp).
     */
    @JvmStatic
    fun pwwsp(context: Context, value: Int): Float = value.pwwsp(context)

    @JvmStatic
    fun pwwspa(context: Context, value: Int): Float = value.pwwspa(context)

    @JvmStatic
    fun pwwspi(context: Context, value: Int): Float = value.pwwspi(context)

    @JvmStatic
    fun pwwspia(context: Context, value: Int): Float = value.pwwspia(context)

    /**
     * EN Quick resolution for Screen Width (pwwsp), but in landscape orientation it acts as Screen Height (pwhsp).
     */
    @JvmStatic
    fun pwwspLh(context: Context, value: Int): Float = value.pwwspLh(context)

    @JvmStatic
    fun pwwspLha(context: Context, value: Int): Float = value.pwwspLha(context)

    @JvmStatic
    fun pwwspLhi(context: Context, value: Int): Float = value.pwwspLhi(context)

    @JvmStatic
    fun pwwspLhia(context: Context, value: Int): Float = value.pwwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (pwwsp), but in portrait orientation it acts as Screen Height (pwhsp).
     */
    @JvmStatic
    fun pwwspPh(context: Context, value: Int): Float = value.pwwspPh(context)

    @JvmStatic
    fun pwwspPha(context: Context, value: Int): Float = value.pwwspPha(context)

    @JvmStatic
    fun pwwspPhi(context: Context, value: Int): Float = value.pwwspPhi(context)

    @JvmStatic
    fun pwwspPhia(context: Context, value: Int): Float = value.pwwspPhia(context)


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
    fun pwwemPh(context: Context, value: Int): Float = value.pwwemPh(context)

    @JvmStatic
    fun pwwemPha(context: Context, value: Int): Float = value.pwwemPha(context)

    @JvmStatic
    fun pwwemPhi(context: Context, value: Int): Float = value.pwwemPhi(context)

    @JvmStatic
    fun pwwemPhia(context: Context, value: Int): Float = value.pwwemPhia(context)

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
    ): Float = value.toDynamicPowerSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPowerSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension PowerSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada PowerSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): PowerSp = PowerSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension PowerSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada PowerSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): PowerSp = PowerSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwhspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwhspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwhspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwhspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (pwssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (pwhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwhspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwhspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (pwwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (pwssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (pwhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwhspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwhspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (pwwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}