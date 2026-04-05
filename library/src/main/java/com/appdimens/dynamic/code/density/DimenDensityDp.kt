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

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
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
 * Utility object for handling SDP (Scalable Dp) dimensions from Java.
 *
 * PT
 * Objeto utilitário para manipulação de dimensões SDP (Scalable Dp) no Java.
 */
object DimenDensityDp {

    /**
     * EN Eagerly initializes [DimenCache] (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work.
     * PT Inicializa o [DimenCache] antecipadamente para evitar custo lazy no primeiro uso.
     */
    @JvmStatic
    fun warmupCache(context: Context) {
        DimenCache.init(context)
    }

    /**
     * EN Quick resolution for Smallest Width (sdp).
     * PT Resolução rápida para Smallest Width (sdp).
     */
    @JvmStatic
    fun dsdp(context: Context, value: Int): Float = value.dsdp(context)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun dsdpa(context: Context, value: Int): Float = value.dsdpa(context)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun dsdpi(context: Context, value: Int): Float = value.dsdpi(context)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun dsdpia(context: Context, value: Int): Float = value.dsdpia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun dsdpPh(context: Context, value: Int): Float = value.dsdpPh(context)

    @JvmStatic
    fun dsdpPha(context: Context, value: Int): Float = value.dsdpPha(context)

    @JvmStatic
    fun dsdpPhi(context: Context, value: Int): Float = value.dsdpPhi(context)

    @JvmStatic
    fun dsdpPhia(context: Context, value: Int): Float = value.dsdpPhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun dsdpLh(context: Context, value: Int): Float = value.dsdpLh(context)

    @JvmStatic
    fun dsdpLha(context: Context, value: Int): Float = value.dsdpLha(context)

    @JvmStatic
    fun dsdpLhi(context: Context, value: Int): Float = value.dsdpLhi(context)

    @JvmStatic
    fun dsdpLhia(context: Context, value: Int): Float = value.dsdpLhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun dsdpPw(context: Context, value: Int): Float = value.dsdpPw(context)

    @JvmStatic
    fun dsdpPwa(context: Context, value: Int): Float = value.dsdpPwa(context)

    @JvmStatic
    fun dsdpPwi(context: Context, value: Int): Float = value.dsdpPwi(context)

    @JvmStatic
    fun dsdpPwia(context: Context, value: Int): Float = value.dsdpPwia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun dsdpLw(context: Context, value: Int): Float = value.dsdpLw(context)

    @JvmStatic
    fun dsdpLwa(context: Context, value: Int): Float = value.dsdpLwa(context)

    @JvmStatic
    fun dsdpLwi(context: Context, value: Int): Float = value.dsdpLwi(context)

    @JvmStatic
    fun dsdpLwia(context: Context, value: Int): Float = value.dsdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun dhdp(context: Context, value: Int): Float = value.dhdp(context)

    @JvmStatic
    fun dhdpa(context: Context, value: Int): Float = value.dhdpa(context)

    @JvmStatic
    fun dhdpi(context: Context, value: Int): Float = value.dhdpi(context)

    @JvmStatic
    fun dhdpia(context: Context, value: Int): Float = value.dhdpia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun dhdpLw(context: Context, value: Int): Float = value.dhdpLw(context)

    @JvmStatic
    fun dhdpLwa(context: Context, value: Int): Float = value.dhdpLwa(context)

    @JvmStatic
    fun dhdpLwi(context: Context, value: Int): Float = value.dhdpLwi(context)

    @JvmStatic
    fun dhdpLwia(context: Context, value: Int): Float = value.dhdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun dhdpPw(context: Context, value: Int): Float = value.dhdpPw(context)

    @JvmStatic
    fun dhdpPwa(context: Context, value: Int): Float = value.dhdpPwa(context)

    @JvmStatic
    fun dhdpPwi(context: Context, value: Int): Float = value.dhdpPwi(context)

    @JvmStatic
    fun dhdpPwia(context: Context, value: Int): Float = value.dhdpPwia(context)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun dwdp(context: Context, value: Int): Float = value.dwdp(context)

    @JvmStatic
    fun dwdpa(context: Context, value: Int): Float = value.dwdpa(context)

    @JvmStatic
    fun dwdpi(context: Context, value: Int): Float = value.dwdpi(context)

    @JvmStatic
    fun dwdpia(context: Context, value: Int): Float = value.dwdpia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun dwdpLh(context: Context, value: Int): Float = value.dwdpLh(context)

    @JvmStatic
    fun dwdpLha(context: Context, value: Int): Float = value.dwdpLha(context)

    @JvmStatic
    fun dwdpLhi(context: Context, value: Int): Float = value.dwdpLhi(context)

    @JvmStatic
    fun dwdpLhia(context: Context, value: Int): Float = value.dwdpLhia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun dwdpPh(context: Context, value: Int): Float = value.dwdpPh(context)

    @JvmStatic
    fun dwdpPha(context: Context, value: Int): Float = value.dwdpPha(context)

    @JvmStatic
    fun dwdpPhi(context: Context, value: Int): Float = value.dwdpPhi(context)

    @JvmStatic
    fun dwdpPhia(context: Context, value: Int): Float = value.dwdpPhia(context)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dsdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dhdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dwdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dsdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dhdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun dwdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic scaling function for Java.
     * PT Função de escala genérica para Java.
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInPx(
        context: Context,
        qualifier: DpQualifier,
        value: Int,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicDensityPx(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Generic DP scaling function for Java.
     * PT Função de escala DP genérica para Java.
     */
    @JvmStatic
    @JvmOverloads
    fun getDimensionInDp(
        context: Context,
        qualifier: DpQualifier,
        value: Int,
        inverter: Inverter = Inverter.DEFAULT,
        ignoreMultiWindows: Boolean = false,
        applyAspectRatio: Boolean = false,
        customSensitivityK: Float? = null
    ): Float = value.toDynamicDensityDp(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenDensity from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenDensity a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenDensity = DimenDensity(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenDensity from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenDensity = DimenDensity(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dsdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dhdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun dwdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dsdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dsdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dhdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dhdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun dwdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.dwdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}