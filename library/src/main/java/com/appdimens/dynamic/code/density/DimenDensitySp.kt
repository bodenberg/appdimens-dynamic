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
package com.appdimens.dynamic.code.density

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
object DimenDensitySp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (dssp).
     * PT Resolução rápida para Smallest Width (dssp).
     */
    @JvmStatic
    fun dssp(context: Context, value: Int): Float = value.dssp(context)

    @JvmStatic
    fun dsspa(context: Context, value: Int): Float = value.dsspa(context)

    @JvmStatic
    fun dsspi(context: Context, value: Int): Float = value.dsspi(context)

    @JvmStatic
    fun dsspia(context: Context, value: Int): Float = value.dsspia(context)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in portrait orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun dsspPh(context: Context, value: Int): Float = value.dsspPh(context)

    @JvmStatic
    fun dsspPha(context: Context, value: Int): Float = value.dsspPha(context)

    @JvmStatic
    fun dsspPhi(context: Context, value: Int): Float = value.dsspPhi(context)

    @JvmStatic
    fun dsspPhia(context: Context, value: Int): Float = value.dsspPhia(context)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in landscape orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun dsspLh(context: Context, value: Int): Float = value.dsspLh(context)

    @JvmStatic
    fun dsspLha(context: Context, value: Int): Float = value.dsspLha(context)

    @JvmStatic
    fun dsspLhi(context: Context, value: Int): Float = value.dsspLhi(context)

    @JvmStatic
    fun dsspLhia(context: Context, value: Int): Float = value.dsspLhia(context)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in portrait orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun dsspPw(context: Context, value: Int): Float = value.dsspPw(context)

    @JvmStatic
    fun dsspPwa(context: Context, value: Int): Float = value.dsspPwa(context)

    @JvmStatic
    fun dsspPwi(context: Context, value: Int): Float = value.dsspPwi(context)

    @JvmStatic
    fun dsspPwia(context: Context, value: Int): Float = value.dsspPwia(context)

    /**
     * EN Quick resolution for Smallest Width (dssp), but in landscape orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun dsspLw(context: Context, value: Int): Float = value.dsspLw(context)

    @JvmStatic
    fun dsspLwa(context: Context, value: Int): Float = value.dsspLwa(context)

    @JvmStatic
    fun dsspLwi(context: Context, value: Int): Float = value.dsspLwi(context)

    @JvmStatic
    fun dsspLwia(context: Context, value: Int): Float = value.dsspLwia(context)

    /**
     * EN Quick resolution for Screen Height (dhsp).
     * PT Resolução rápida para Altura da Tela (dhsp).
     */
    @JvmStatic
    fun dhsp(context: Context, value: Int): Float = value.dhsp(context)

    @JvmStatic
    fun dhspa(context: Context, value: Int): Float = value.dhspa(context)

    @JvmStatic
    fun dhspi(context: Context, value: Int): Float = value.dhspi(context)

    @JvmStatic
    fun dhspia(context: Context, value: Int): Float = value.dhspia(context)

    /**
     * EN Quick resolution for Screen Height (dhsp), but in landscape orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun dhspLw(context: Context, value: Int): Float = value.dhspLw(context)

    @JvmStatic
    fun dhspLwa(context: Context, value: Int): Float = value.dhspLwa(context)

    @JvmStatic
    fun dhspLwi(context: Context, value: Int): Float = value.dhspLwi(context)

    @JvmStatic
    fun dhspLwia(context: Context, value: Int): Float = value.dhspLwia(context)

    /**
     * EN Quick resolution for Screen Height (dhsp), but in portrait orientation it acts as Screen Width (dwsp).
     */
    @JvmStatic
    fun dhspPw(context: Context, value: Int): Float = value.dhspPw(context)

    @JvmStatic
    fun dhspPwa(context: Context, value: Int): Float = value.dhspPwa(context)

    @JvmStatic
    fun dhspPwi(context: Context, value: Int): Float = value.dhspPwi(context)

    @JvmStatic
    fun dhspPwia(context: Context, value: Int): Float = value.dhspPwia(context)

    /**
     * EN Quick resolution for Screen Width (dwsp).
     * PT Resolução rápida para Largura da Tela (dwsp).
     */
    @JvmStatic
    fun dwsp(context: Context, value: Int): Float = value.dwsp(context)

    @JvmStatic
    fun dwspa(context: Context, value: Int): Float = value.dwspa(context)

    @JvmStatic
    fun dwspi(context: Context, value: Int): Float = value.dwspi(context)

    @JvmStatic
    fun dwspia(context: Context, value: Int): Float = value.dwspia(context)

    /**
     * EN Quick resolution for Screen Width (dwsp), but in landscape orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun dwspLh(context: Context, value: Int): Float = value.dwspLh(context)

    @JvmStatic
    fun dwspLha(context: Context, value: Int): Float = value.dwspLha(context)

    @JvmStatic
    fun dwspLhi(context: Context, value: Int): Float = value.dwspLhi(context)

    @JvmStatic
    fun dwspLhia(context: Context, value: Int): Float = value.dwspLhia(context)

    /**
     * EN Quick resolution for Screen Width (dwsp), but in portrait orientation it acts as Screen Height (dhsp).
     */
    @JvmStatic
    fun dwspPh(context: Context, value: Int): Float = value.dwspPh(context)

    @JvmStatic
    fun dwspPha(context: Context, value: Int): Float = value.dwspPha(context)

    @JvmStatic
    fun dwspPhi(context: Context, value: Int): Float = value.dwspPhi(context)

    @JvmStatic
    fun dwspPhia(context: Context, value: Int): Float = value.dwspPhia(context)


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
    fun dwemPh(context: Context, value: Int): Float = value.dwemPh(context)

    @JvmStatic
    fun dwemPha(context: Context, value: Int): Float = value.dwemPha(context)

    @JvmStatic
    fun dwemPhi(context: Context, value: Int): Float = value.dwemPhi(context)

    @JvmStatic
    fun dwemPhia(context: Context, value: Int): Float = value.dwemPhia(context)

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
    ): Float = value.toDynamicDensitySpPx(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicDensitySp(context, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DensitySp from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DensitySp a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DensitySp = DensitySp(initialBaseValue)

    /**
     * EN Starts the build chain for the custom dimension DensitySp from a base Float.
     * PT Inicia a cadeia de construção para a dimensão customizada DensitySp a partir de um Float base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DensitySp = DensitySp(initialBaseValue)

    // EN Qualifier-based conditional dynamic scaling for Sp.
    // PT Escalonamento condicional baseado em qualificador para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dsspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dhspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dwspQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwspQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions for Sp.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

    /**
     * EN Quick resolution for Smallest Width (swSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dsspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dhspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wSP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dwspScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwspScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (dssp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dsspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dhsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dhspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dwsp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dwspRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwspRotate(context, rotationValue, finalQualifierResolver, orientation, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (dssp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dsspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (dhsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dhspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (dwsp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dwspMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwspMode(context, modeValue, uiModeType, finalQualifierResolver, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}