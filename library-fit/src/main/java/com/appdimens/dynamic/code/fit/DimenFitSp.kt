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
package com.appdimens.dynamic.code.fit

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
object DimenFitSp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (ftssp).
     * PT Resolução rápida para Smallest Width (ftssp).
     */
    @JvmStatic
    fun ftssp(context: Context, value: Int): Float = value.ftssp(context)

    @JvmStatic
    fun ftsspa(context: Context, value: Int): Float = value.ftsspa(context)

    @JvmStatic
    fun ftsspi(context: Context, value: Int): Float = value.ftsspi(context)

    @JvmStatic
    fun ftsspia(context: Context, value: Int): Float = value.ftsspia(context)

    /**
     * EN Quick resolution for Smallest Width (ftssp), but in portrait orientation it acts as Screen Height (fthsp).
     */
    @JvmStatic
    fun ftsspPh(context: Context, value: Int): Float = value.ftsspPh(context)

    @JvmStatic
    fun ftsspPha(context: Context, value: Int): Float = value.ftsspPha(context)

    @JvmStatic
    fun ftsspPhi(context: Context, value: Int): Float = value.ftsspPhi(context)

    @JvmStatic
    fun ftsspPhia(context: Context, value: Int): Float = value.ftsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (ftssp), but in landscape orientation it acts as Screen Height (fthsp).
     */
    @JvmStatic
    fun ftsspLh(context: Context, value: Int): Float = value.ftsspLh(context)

    @JvmStatic
    fun ftsspLha(context: Context, value: Int): Float = value.ftsspLha(context)

    @JvmStatic
    fun ftsspLhi(context: Context, value: Int): Float = value.ftsspLhi(context)

    @JvmStatic
    fun ftsspLhia(context: Context, value: Int): Float = value.ftsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (ftssp), but in portrait orientation it acts as Screen Width (ftwsp).
     */
    @JvmStatic
    fun ftsspPw(context: Context, value: Int): Float = value.ftsspPw(context)

    @JvmStatic
    fun ftsspPwa(context: Context, value: Int): Float = value.ftsspPwa(context)

    @JvmStatic
    fun ftsspPwi(context: Context, value: Int): Float = value.ftsspPwi(context)

    @JvmStatic
    fun ftsspPwia(context: Context, value: Int): Float = value.ftsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (ftssp), but in landscape orientation it acts as Screen Width (ftwsp).
     */
    @JvmStatic
    fun ftsspLw(context: Context, value: Int): Float = value.ftsspLw(context)

    @JvmStatic
    fun ftsspLwa(context: Context, value: Int): Float = value.ftsspLwa(context)

    @JvmStatic
    fun ftsspLwi(context: Context, value: Int): Float = value.ftsspLwi(context)

    @JvmStatic
    fun ftsspLwia(context: Context, value: Int): Float = value.ftsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (fthsp).
     * PT Resolução rápida para Altura da Tela (fthsp).
     */
    @JvmStatic
    fun fthsp(context: Context, value: Int): Float = value.fthsp(context)

    @JvmStatic
    fun fthspa(context: Context, value: Int): Float = value.fthspa(context)

    @JvmStatic
    fun fthspi(context: Context, value: Int): Float = value.fthspi(context)

    @JvmStatic
    fun fthspia(context: Context, value: Int): Float = value.fthspia(context)

    /**
     * EN Quick resolution for Screen Height (fthsp), but in landscape orientation it acts as Screen Width (ftwsp).
     */
    @JvmStatic
    fun fthspLw(context: Context, value: Int): Float = value.fthspLw(context)

    @JvmStatic
    fun fthspLwa(context: Context, value: Int): Float = value.fthspLwa(context)

    @JvmStatic
    fun fthspLwi(context: Context, value: Int): Float = value.fthspLwi(context)

    @JvmStatic
    fun fthspLwia(context: Context, value: Int): Float = value.fthspLwia(context)

    /**
     * EN Quick resolution for Screen Height (fthsp), but in portrait orientation it acts as Screen Width (ftwsp).
     */
    @JvmStatic
    fun fthspPw(context: Context, value: Int): Float = value.fthspPw(context)

    @JvmStatic
    fun fthspPwa(context: Context, value: Int): Float = value.fthspPwa(context)

    @JvmStatic
    fun fthspPwi(context: Context, value: Int): Float = value.fthspPwi(context)

    @JvmStatic
    fun fthspPwia(context: Context, value: Int): Float = value.fthspPwia(context)

    /**
     * EN Quick resolution for Screen Width (ftwsp).
     * PT Resolução rápida para Largura da Tela (ftwsp).
     */
    @JvmStatic
    fun ftwsp(context: Context, value: Int): Float = value.ftwsp(context)

    @JvmStatic
    fun ftwspa(context: Context, value: Int): Float = value.ftwspa(context)

    @JvmStatic
    fun ftwspi(context: Context, value: Int): Float = value.ftwspi(context)

    @JvmStatic
    fun ftwspia(context: Context, value: Int): Float = value.ftwspia(context)

    /**
     * EN Quick resolution for Screen Width (ftwsp), but in landscape orientation it acts as Screen Height (fthsp).
     */
    @JvmStatic
    fun ftwspLh(context: Context, value: Int): Float = value.ftwspLh(context)

    @JvmStatic
    fun ftwspLha(context: Context, value: Int): Float = value.ftwspLha(context)

    @JvmStatic
    fun ftwspLhi(context: Context, value: Int): Float = value.ftwspLhi(context)

    @JvmStatic
    fun ftwspLhia(context: Context, value: Int): Float = value.ftwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (ftwsp), but in portrait orientation it acts as Screen Height (fthsp).
     */
    @JvmStatic
    fun ftwspPh(context: Context, value: Int): Float = value.ftwspPh(context)

    @JvmStatic
    fun ftwspPha(context: Context, value: Int): Float = value.ftwspPha(context)

    @JvmStatic
    fun ftwspPhi(context: Context, value: Int): Float = value.ftwspPhi(context)

    @JvmStatic
    fun ftwspPhia(context: Context, value: Int): Float = value.ftwspPhia(context)


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
    fun ftwemPh(context: Context, value: Int): Float = value.ftwemPh(context)

    @JvmStatic
    fun ftwemPha(context: Context, value: Int): Float = value.ftwemPha(context)

    @JvmStatic
    fun ftwemPhi(context: Context, value: Int): Float = value.ftwemPhi(context)

    @JvmStatic
    fun ftwemPhia(context: Context, value: Int): Float = value.ftwemPhia(context)

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
    ): Float = value.toDynamicFitSpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicFitSp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension FitSp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada FitSp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): FitSp = FitSp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension FitSp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada FitSp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): FitSp = FitSp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fthspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fthspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (ftssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (fthsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fthspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (ftwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (ftssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (fthsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fthspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (ftwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}