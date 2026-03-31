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
package com.appdimens.dynamic.code

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
object DimenSdp {

    /**
     * EN Quick resolution for Smallest Width (sdp).
     * PT Resolução rápida para Smallest Width (sdp).
     */
    @JvmStatic
    fun sdp(context: Context, value: Int): Float = value.sdp(context)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun sdpa(context: Context, value: Int): Float = value.sdpa(context)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun sdpi(context: Context, value: Int): Float = value.sdpi(context)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun sdpia(context: Context, value: Int): Float = value.sdpia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun sdpPh(context: Context, value: Int): Float = value.sdpPh(context)

    @JvmStatic
    fun sdpPha(context: Context, value: Int): Float = value.sdpPha(context)

    @JvmStatic
    fun sdpPhi(context: Context, value: Int): Float = value.sdpPhi(context)

    @JvmStatic
    fun sdpPhia(context: Context, value: Int): Float = value.sdpPhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun sdpLh(context: Context, value: Int): Float = value.sdpLh(context)

    @JvmStatic
    fun sdpLha(context: Context, value: Int): Float = value.sdpLha(context)

    @JvmStatic
    fun sdpLhi(context: Context, value: Int): Float = value.sdpLhi(context)

    @JvmStatic
    fun sdpLhia(context: Context, value: Int): Float = value.sdpLhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun sdpPw(context: Context, value: Int): Float = value.sdpPw(context)

    @JvmStatic
    fun sdpPwa(context: Context, value: Int): Float = value.sdpPwa(context)

    @JvmStatic
    fun sdpPwi(context: Context, value: Int): Float = value.sdpPwi(context)

    @JvmStatic
    fun sdpPwia(context: Context, value: Int): Float = value.sdpPwia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun sdpLw(context: Context, value: Int): Float = value.sdpLw(context)

    @JvmStatic
    fun sdpLwa(context: Context, value: Int): Float = value.sdpLwa(context)

    @JvmStatic
    fun sdpLwi(context: Context, value: Int): Float = value.sdpLwi(context)

    @JvmStatic
    fun sdpLwia(context: Context, value: Int): Float = value.sdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun hdp(context: Context, value: Int): Float = value.hdp(context)

    @JvmStatic
    fun hdpa(context: Context, value: Int): Float = value.hdpa(context)

    @JvmStatic
    fun hdpi(context: Context, value: Int): Float = value.hdpi(context)

    @JvmStatic
    fun hdpia(context: Context, value: Int): Float = value.hdpia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun hdpLw(context: Context, value: Int): Float = value.hdpLw(context)

    @JvmStatic
    fun hdpLwa(context: Context, value: Int): Float = value.hdpLwa(context)

    @JvmStatic
    fun hdpLwi(context: Context, value: Int): Float = value.hdpLwi(context)

    @JvmStatic
    fun hdpLwia(context: Context, value: Int): Float = value.hdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun hdpPw(context: Context, value: Int): Float = value.hdpPw(context)

    @JvmStatic
    fun hdpPwa(context: Context, value: Int): Float = value.hdpPwa(context)

    @JvmStatic
    fun hdpPwi(context: Context, value: Int): Float = value.hdpPwi(context)

    @JvmStatic
    fun hdpPwia(context: Context, value: Int): Float = value.hdpPwia(context)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun wdp(context: Context, value: Int): Float = value.wdp(context)

    @JvmStatic
    fun wdpa(context: Context, value: Int): Float = value.wdpa(context)

    @JvmStatic
    fun wdpi(context: Context, value: Int): Float = value.wdpi(context)

    @JvmStatic
    fun wdpia(context: Context, value: Int): Float = value.wdpia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun wdpLh(context: Context, value: Int): Float = value.wdpLh(context)

    @JvmStatic
    fun wdpLha(context: Context, value: Int): Float = value.wdpLha(context)

    @JvmStatic
    fun wdpLhi(context: Context, value: Int): Float = value.wdpLhi(context)

    @JvmStatic
    fun wdpLhia(context: Context, value: Int): Float = value.wdpLhia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun wdpPh(context: Context, value: Int): Float = value.wdpPh(context)

    @JvmStatic
    fun wdpPha(context: Context, value: Int): Float = value.wdpPha(context)

    @JvmStatic
    fun wdpPhi(context: Context, value: Int): Float = value.wdpPhi(context)

    @JvmStatic
    fun wdpPhia(context: Context, value: Int): Float = value.wdpPhia(context)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
        customSensitivityK: Float? = null,
        enableCache: Boolean = true
    ): Float = value.toDynamicScaledPx(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, enableCache)

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
        customSensitivityK: Float? = null,
        enableCache: Boolean = true
    ): Float = value.toDynamicScaledDp(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, enableCache)

    /**
     * EN Starts the build chain for the custom dimension DimenScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenScaled = DimenScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenScaled = DimenScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun sdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.sdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun hdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.hdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun wdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.wdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}