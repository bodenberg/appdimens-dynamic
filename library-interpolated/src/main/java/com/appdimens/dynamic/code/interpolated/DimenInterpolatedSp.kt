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
package com.appdimens.dynamic.code.interpolated

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
object DimenInterpolatedSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (issp).
     * PT Resolução rápida para Smallest Width (issp).
     */
    @JvmStatic
    fun issp(context: Context, value: Int): Float = value.issp(context)

    @JvmStatic
    fun isspa(context: Context, value: Int): Float = value.isspa(context)

    @JvmStatic
    fun isspi(context: Context, value: Int): Float = value.isspi(context)

    @JvmStatic
    fun isspia(context: Context, value: Int): Float = value.isspia(context)

    /**
     * EN Quick resolution for Smallest Width (issp), but in portrait orientation it acts as Screen Height (ihsp).
     */
    @JvmStatic
    fun isspPh(context: Context, value: Int): Float = value.isspPh(context)

    @JvmStatic
    fun isspPha(context: Context, value: Int): Float = value.isspPha(context)

    @JvmStatic
    fun isspPhi(context: Context, value: Int): Float = value.isspPhi(context)

    @JvmStatic
    fun isspPhia(context: Context, value: Int): Float = value.isspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (issp), but in landscape orientation it acts as Screen Height (ihsp).
     */
    @JvmStatic
    fun isspLh(context: Context, value: Int): Float = value.isspLh(context)

    @JvmStatic
    fun isspLha(context: Context, value: Int): Float = value.isspLha(context)

    @JvmStatic
    fun isspLhi(context: Context, value: Int): Float = value.isspLhi(context)

    @JvmStatic
    fun isspLhia(context: Context, value: Int): Float = value.isspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (issp), but in portrait orientation it acts as Screen Width (iwsp).
     */
    @JvmStatic
    fun isspPw(context: Context, value: Int): Float = value.isspPw(context)

    @JvmStatic
    fun isspPwa(context: Context, value: Int): Float = value.isspPwa(context)

    @JvmStatic
    fun isspPwi(context: Context, value: Int): Float = value.isspPwi(context)

    @JvmStatic
    fun isspPwia(context: Context, value: Int): Float = value.isspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (issp), but in landscape orientation it acts as Screen Width (iwsp).
     */
    @JvmStatic
    fun isspLw(context: Context, value: Int): Float = value.isspLw(context)

    @JvmStatic
    fun isspLwa(context: Context, value: Int): Float = value.isspLwa(context)

    @JvmStatic
    fun isspLwi(context: Context, value: Int): Float = value.isspLwi(context)

    @JvmStatic
    fun isspLwia(context: Context, value: Int): Float = value.isspLwia(context)

    /**
     * EN Quick resolution for Screen Height (ihsp).
     * PT Resolução rápida para Altura da Tela (ihsp).
     */
    @JvmStatic
    fun ihsp(context: Context, value: Int): Float = value.ihsp(context)

    @JvmStatic
    fun ihspa(context: Context, value: Int): Float = value.ihspa(context)

    @JvmStatic
    fun ihspi(context: Context, value: Int): Float = value.ihspi(context)

    @JvmStatic
    fun ihspia(context: Context, value: Int): Float = value.ihspia(context)

    /**
     * EN Quick resolution for Screen Height (ihsp), but in landscape orientation it acts as Screen Width (iwsp).
     */
    @JvmStatic
    fun ihspLw(context: Context, value: Int): Float = value.ihspLw(context)

    @JvmStatic
    fun ihspLwa(context: Context, value: Int): Float = value.ihspLwa(context)

    @JvmStatic
    fun ihspLwi(context: Context, value: Int): Float = value.ihspLwi(context)

    @JvmStatic
    fun ihspLwia(context: Context, value: Int): Float = value.ihspLwia(context)

    /**
     * EN Quick resolution for Screen Height (ihsp), but in portrait orientation it acts as Screen Width (iwsp).
     */
    @JvmStatic
    fun ihspPw(context: Context, value: Int): Float = value.ihspPw(context)

    @JvmStatic
    fun ihspPwa(context: Context, value: Int): Float = value.ihspPwa(context)

    @JvmStatic
    fun ihspPwi(context: Context, value: Int): Float = value.ihspPwi(context)

    @JvmStatic
    fun ihspPwia(context: Context, value: Int): Float = value.ihspPwia(context)

    /**
     * EN Quick resolution for Screen Width (iwsp).
     * PT Resolução rápida para Largura da Tela (iwsp).
     */
    @JvmStatic
    fun iwsp(context: Context, value: Int): Float = value.iwsp(context)

    @JvmStatic
    fun iwspa(context: Context, value: Int): Float = value.iwspa(context)

    @JvmStatic
    fun iwspi(context: Context, value: Int): Float = value.iwspi(context)

    @JvmStatic
    fun iwspia(context: Context, value: Int): Float = value.iwspia(context)

    /**
     * EN Quick resolution for Screen Width (iwsp), but in landscape orientation it acts as Screen Height (ihsp).
     */
    @JvmStatic
    fun iwspLh(context: Context, value: Int): Float = value.iwspLh(context)

    @JvmStatic
    fun iwspLha(context: Context, value: Int): Float = value.iwspLha(context)

    @JvmStatic
    fun iwspLhi(context: Context, value: Int): Float = value.iwspLhi(context)

    @JvmStatic
    fun iwspLhia(context: Context, value: Int): Float = value.iwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (iwsp), but in portrait orientation it acts as Screen Height (ihsp).
     */
    @JvmStatic
    fun iwspPh(context: Context, value: Int): Float = value.iwspPh(context)

    @JvmStatic
    fun iwspPha(context: Context, value: Int): Float = value.iwspPha(context)

    @JvmStatic
    fun iwspPhi(context: Context, value: Int): Float = value.iwspPhi(context)

    @JvmStatic
    fun iwspPhia(context: Context, value: Int): Float = value.iwspPhia(context)


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
    fun iwemPh(context: Context, value: Int): Float = value.iwemPh(context)

    @JvmStatic
    fun iwemPha(context: Context, value: Int): Float = value.iwemPha(context)

    @JvmStatic
    fun iwemPhi(context: Context, value: Int): Float = value.iwemPhi(context)

    @JvmStatic
    fun iwemPhia(context: Context, value: Int): Float = value.iwemPhia(context)

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
    ): Float = value.toDynamicInterpolatedSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicInterpolatedSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension InterpolatedSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada InterpolatedSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): InterpolatedSp = InterpolatedSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension InterpolatedSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada InterpolatedSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): InterpolatedSp = InterpolatedSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun isspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ihspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun iwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun isspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ihspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun iwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (issp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun isspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (ihsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ihspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (iwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun iwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (issp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun isspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.isspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (ihsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ihspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ihspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (iwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun iwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.iwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}