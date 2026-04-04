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
object DimenFit {

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
    fun ftsdp(context: Context, value: Int): Float = value.ftsdp(context)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun ftsdpa(context: Context, value: Int): Float = value.ftsdpa(context)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun ftsdpi(context: Context, value: Int): Float = value.ftsdpi(context)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun ftsdpia(context: Context, value: Int): Float = value.ftsdpia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun ftsdpPh(context: Context, value: Int): Float = value.ftsdpPh(context)

    @JvmStatic
    fun ftsdpPha(context: Context, value: Int): Float = value.ftsdpPha(context)

    @JvmStatic
    fun ftsdpPhi(context: Context, value: Int): Float = value.ftsdpPhi(context)

    @JvmStatic
    fun ftsdpPhia(context: Context, value: Int): Float = value.ftsdpPhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun ftsdpLh(context: Context, value: Int): Float = value.ftsdpLh(context)

    @JvmStatic
    fun ftsdpLha(context: Context, value: Int): Float = value.ftsdpLha(context)

    @JvmStatic
    fun ftsdpLhi(context: Context, value: Int): Float = value.ftsdpLhi(context)

    @JvmStatic
    fun ftsdpLhia(context: Context, value: Int): Float = value.ftsdpLhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun ftsdpPw(context: Context, value: Int): Float = value.ftsdpPw(context)

    @JvmStatic
    fun ftsdpPwa(context: Context, value: Int): Float = value.ftsdpPwa(context)

    @JvmStatic
    fun ftsdpPwi(context: Context, value: Int): Float = value.ftsdpPwi(context)

    @JvmStatic
    fun ftsdpPwia(context: Context, value: Int): Float = value.ftsdpPwia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun ftsdpLw(context: Context, value: Int): Float = value.ftsdpLw(context)

    @JvmStatic
    fun ftsdpLwa(context: Context, value: Int): Float = value.ftsdpLwa(context)

    @JvmStatic
    fun ftsdpLwi(context: Context, value: Int): Float = value.ftsdpLwi(context)

    @JvmStatic
    fun ftsdpLwia(context: Context, value: Int): Float = value.ftsdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun fthdp(context: Context, value: Int): Float = value.fthdp(context)

    @JvmStatic
    fun fthdpa(context: Context, value: Int): Float = value.fthdpa(context)

    @JvmStatic
    fun fthdpi(context: Context, value: Int): Float = value.fthdpi(context)

    @JvmStatic
    fun fthdpia(context: Context, value: Int): Float = value.fthdpia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun fthdpLw(context: Context, value: Int): Float = value.fthdpLw(context)

    @JvmStatic
    fun fthdpLwa(context: Context, value: Int): Float = value.fthdpLwa(context)

    @JvmStatic
    fun fthdpLwi(context: Context, value: Int): Float = value.fthdpLwi(context)

    @JvmStatic
    fun fthdpLwia(context: Context, value: Int): Float = value.fthdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun fthdpPw(context: Context, value: Int): Float = value.fthdpPw(context)

    @JvmStatic
    fun fthdpPwa(context: Context, value: Int): Float = value.fthdpPwa(context)

    @JvmStatic
    fun fthdpPwi(context: Context, value: Int): Float = value.fthdpPwi(context)

    @JvmStatic
    fun fthdpPwia(context: Context, value: Int): Float = value.fthdpPwia(context)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun ftwdp(context: Context, value: Int): Float = value.ftwdp(context)

    @JvmStatic
    fun ftwdpa(context: Context, value: Int): Float = value.ftwdpa(context)

    @JvmStatic
    fun ftwdpi(context: Context, value: Int): Float = value.ftwdpi(context)

    @JvmStatic
    fun ftwdpia(context: Context, value: Int): Float = value.ftwdpia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun ftwdpLh(context: Context, value: Int): Float = value.ftwdpLh(context)

    @JvmStatic
    fun ftwdpLha(context: Context, value: Int): Float = value.ftwdpLha(context)

    @JvmStatic
    fun ftwdpLhi(context: Context, value: Int): Float = value.ftwdpLhi(context)

    @JvmStatic
    fun ftwdpLhia(context: Context, value: Int): Float = value.ftwdpLhia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun ftwdpPh(context: Context, value: Int): Float = value.ftwdpPh(context)

    @JvmStatic
    fun ftwdpPha(context: Context, value: Int): Float = value.ftwdpPha(context)

    @JvmStatic
    fun ftwdpPhi(context: Context, value: Int): Float = value.ftwdpPhi(context)

    @JvmStatic
    fun ftwdpPhia(context: Context, value: Int): Float = value.ftwdpPhia(context)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fthdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun fthdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicFitPx(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicFitDp(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenFitScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenFitScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenFitScaled = DimenFitScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenFitScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenFitScaled = DimenFitScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun fthdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftsdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftsdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun fthdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.fthdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun ftwdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.ftwdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}