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
object DimenLogarithmic {

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
    fun logsdp(context: Context, value: Int): Float = value.logsdp(context)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun logsdpa(context: Context, value: Int): Float = value.logsdpa(context)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun logsdpi(context: Context, value: Int): Float = value.logsdpi(context)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun logsdpia(context: Context, value: Int): Float = value.logsdpia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun logsdpPh(context: Context, value: Int): Float = value.logsdpPh(context)

    @JvmStatic
    fun logsdpPha(context: Context, value: Int): Float = value.logsdpPha(context)

    @JvmStatic
    fun logsdpPhi(context: Context, value: Int): Float = value.logsdpPhi(context)

    @JvmStatic
    fun logsdpPhia(context: Context, value: Int): Float = value.logsdpPhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun logsdpLh(context: Context, value: Int): Float = value.logsdpLh(context)

    @JvmStatic
    fun logsdpLha(context: Context, value: Int): Float = value.logsdpLha(context)

    @JvmStatic
    fun logsdpLhi(context: Context, value: Int): Float = value.logsdpLhi(context)

    @JvmStatic
    fun logsdpLhia(context: Context, value: Int): Float = value.logsdpLhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun logsdpPw(context: Context, value: Int): Float = value.logsdpPw(context)

    @JvmStatic
    fun logsdpPwa(context: Context, value: Int): Float = value.logsdpPwa(context)

    @JvmStatic
    fun logsdpPwi(context: Context, value: Int): Float = value.logsdpPwi(context)

    @JvmStatic
    fun logsdpPwia(context: Context, value: Int): Float = value.logsdpPwia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun logsdpLw(context: Context, value: Int): Float = value.logsdpLw(context)

    @JvmStatic
    fun logsdpLwa(context: Context, value: Int): Float = value.logsdpLwa(context)

    @JvmStatic
    fun logsdpLwi(context: Context, value: Int): Float = value.logsdpLwi(context)

    @JvmStatic
    fun logsdpLwia(context: Context, value: Int): Float = value.logsdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun loghdp(context: Context, value: Int): Float = value.loghdp(context)

    @JvmStatic
    fun loghdpa(context: Context, value: Int): Float = value.loghdpa(context)

    @JvmStatic
    fun loghdpi(context: Context, value: Int): Float = value.loghdpi(context)

    @JvmStatic
    fun loghdpia(context: Context, value: Int): Float = value.loghdpia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun loghdpLw(context: Context, value: Int): Float = value.loghdpLw(context)

    @JvmStatic
    fun loghdpLwa(context: Context, value: Int): Float = value.loghdpLwa(context)

    @JvmStatic
    fun loghdpLwi(context: Context, value: Int): Float = value.loghdpLwi(context)

    @JvmStatic
    fun loghdpLwia(context: Context, value: Int): Float = value.loghdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun loghdpPw(context: Context, value: Int): Float = value.loghdpPw(context)

    @JvmStatic
    fun loghdpPwa(context: Context, value: Int): Float = value.loghdpPwa(context)

    @JvmStatic
    fun loghdpPwi(context: Context, value: Int): Float = value.loghdpPwi(context)

    @JvmStatic
    fun loghdpPwia(context: Context, value: Int): Float = value.loghdpPwia(context)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun logwdp(context: Context, value: Int): Float = value.logwdp(context)

    @JvmStatic
    fun logwdpa(context: Context, value: Int): Float = value.logwdpa(context)

    @JvmStatic
    fun logwdpi(context: Context, value: Int): Float = value.logwdpi(context)

    @JvmStatic
    fun logwdpia(context: Context, value: Int): Float = value.logwdpia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun logwdpLh(context: Context, value: Int): Float = value.logwdpLh(context)

    @JvmStatic
    fun logwdpLha(context: Context, value: Int): Float = value.logwdpLha(context)

    @JvmStatic
    fun logwdpLhi(context: Context, value: Int): Float = value.logwdpLhi(context)

    @JvmStatic
    fun logwdpLhia(context: Context, value: Int): Float = value.logwdpLhia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun logwdpPh(context: Context, value: Int): Float = value.logwdpPh(context)

    @JvmStatic
    fun logwdpPha(context: Context, value: Int): Float = value.logwdpPha(context)

    @JvmStatic
    fun logwdpPhi(context: Context, value: Int): Float = value.logwdpPhi(context)

    @JvmStatic
    fun logwdpPhia(context: Context, value: Int): Float = value.logwdpPhia(context)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logsdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun loghdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logwdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logsdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun loghdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun logwdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicLogarithmicPx(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicLogarithmicDp(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenLogarithmicScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenLogarithmicScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenLogarithmicScaled = DimenLogarithmicScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenLogarithmicScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenLogarithmicScaled = DimenLogarithmicScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun logsdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun loghdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun logwdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun logsdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logsdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun loghdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.loghdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun logwdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.logwdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
}