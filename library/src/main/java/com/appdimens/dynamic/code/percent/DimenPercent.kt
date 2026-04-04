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
object DimenPercent {

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
    fun psdp(context: Context, value: Int): Float = value.psdp(context)

    /**
     * EN Quick resolution for Smallest Width (sdpa) with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.
     */
    @JvmStatic
    fun psdpa(context: Context, value: Int): Float = value.psdpa(context)

    /**
     * EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows.
     * PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.
     */
    @JvmStatic
    fun psdpi(context: Context, value: Int): Float = value.psdpi(context)

    /**
     * EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio.
     * PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.
     */
    @JvmStatic
    fun psdpia(context: Context, value: Int): Float = value.psdpia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun psdpPh(context: Context, value: Int): Float = value.psdpPh(context)

    @JvmStatic
    fun psdpPha(context: Context, value: Int): Float = value.psdpPha(context)

    @JvmStatic
    fun psdpPhi(context: Context, value: Int): Float = value.psdpPhi(context)

    @JvmStatic
    fun psdpPhia(context: Context, value: Int): Float = value.psdpPhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).
     */
    @JvmStatic
    fun psdpLh(context: Context, value: Int): Float = value.psdpLh(context)

    @JvmStatic
    fun psdpLha(context: Context, value: Int): Float = value.psdpLha(context)

    @JvmStatic
    fun psdpLhi(context: Context, value: Int): Float = value.psdpLhi(context)

    @JvmStatic
    fun psdpLhia(context: Context, value: Int): Float = value.psdpLhia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun psdpPw(context: Context, value: Int): Float = value.psdpPw(context)

    @JvmStatic
    fun psdpPwa(context: Context, value: Int): Float = value.psdpPwa(context)

    @JvmStatic
    fun psdpPwi(context: Context, value: Int): Float = value.psdpPwi(context)

    @JvmStatic
    fun psdpPwia(context: Context, value: Int): Float = value.psdpPwia(context)

    /**
     * EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp).
     * PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).
     */
    @JvmStatic
    fun psdpLw(context: Context, value: Int): Float = value.psdpLw(context)

    @JvmStatic
    fun psdpLwa(context: Context, value: Int): Float = value.psdpLwa(context)

    @JvmStatic
    fun psdpLwi(context: Context, value: Int): Float = value.psdpLwi(context)

    @JvmStatic
    fun psdpLwia(context: Context, value: Int): Float = value.psdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp).
     * PT Resolução rápida para Altura da Tela (hdp).
     */
    @JvmStatic
    fun phdp(context: Context, value: Int): Float = value.phdp(context)

    @JvmStatic
    fun phdpa(context: Context, value: Int): Float = value.phdpa(context)

    @JvmStatic
    fun phdpi(context: Context, value: Int): Float = value.phdpi(context)

    @JvmStatic
    fun phdpia(context: Context, value: Int): Float = value.phdpia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun phdpLw(context: Context, value: Int): Float = value.phdpLw(context)

    @JvmStatic
    fun phdpLwa(context: Context, value: Int): Float = value.phdpLwa(context)

    @JvmStatic
    fun phdpLwi(context: Context, value: Int): Float = value.phdpLwi(context)

    @JvmStatic
    fun phdpLwia(context: Context, value: Int): Float = value.phdpLwia(context)

    /**
     * EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).
     */
    @JvmStatic
    fun phdpPw(context: Context, value: Int): Float = value.phdpPw(context)

    @JvmStatic
    fun phdpPwa(context: Context, value: Int): Float = value.phdpPwa(context)

    @JvmStatic
    fun phdpPwi(context: Context, value: Int): Float = value.phdpPwi(context)

    @JvmStatic
    fun phdpPwia(context: Context, value: Int): Float = value.phdpPwia(context)

    /**
     * EN Quick resolution for Screen Width (wdp).
     * PT Resolução rápida para Largura da Tela (wdp).
     */
    @JvmStatic
    fun pwdp(context: Context, value: Int): Float = value.pwdp(context)

    @JvmStatic
    fun pwdpa(context: Context, value: Int): Float = value.pwdpa(context)

    @JvmStatic
    fun pwdpi(context: Context, value: Int): Float = value.pwdpi(context)

    @JvmStatic
    fun pwdpia(context: Context, value: Int): Float = value.pwdpia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun pwdpLh(context: Context, value: Int): Float = value.pwdpLh(context)

    @JvmStatic
    fun pwdpLha(context: Context, value: Int): Float = value.pwdpLha(context)

    @JvmStatic
    fun pwdpLhi(context: Context, value: Int): Float = value.pwdpLhi(context)

    @JvmStatic
    fun pwdpLhia(context: Context, value: Int): Float = value.pwdpLhia(context)

    /**
     * EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).
     */
    @JvmStatic
    fun pwdpPh(context: Context, value: Int): Float = value.pwdpPh(context)

    @JvmStatic
    fun pwdpPha(context: Context, value: Int): Float = value.pwdpPha(context)

    @JvmStatic
    fun pwdpPhi(context: Context, value: Int): Float = value.pwdpPhi(context)

    @JvmStatic
    fun pwdpPhia(context: Context, value: Int): Float = value.pwdpPhia(context)

    // EN Qualifier-based conditional dynamic scaling.
    // PT Escalonamento condicional baseado em qualificador.

    /**
     * EN Quick resolution for Smallest Width (swDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun psdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun phdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwdpQualifier(context: Context, value: Int, qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwdpQualifier(context, qualifiedValue, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType + DpQualifier combined facilitator extensions.
    // PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

    /**
     * EN Quick resolution for Smallest Width (swDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun psdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Height (hDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun phdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Quick resolution for Screen Width (wDP) context conditional scaling.
     */
    @JvmStatic
    @JvmOverloads
    fun pwdpScreen(context: Context, value: Int, screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwdpScreen(context, screenValue, uiModeType, qualifierType, qualifierValue, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPercentPx(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

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
    ): Float = value.toDynamicPercentDp(context, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Starts the build chain for the custom dimension DimenPercentScaled from a base Int.
     * PT Inicia a cadeia de construção para a dimensão customizada DimenPercentScaled a partir de um Int base.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Int): DimenPercentScaled = DimenPercentScaled(initialBaseValue.toFloat())

    /**
     * EN Starts the build chain for the custom dimension DimenPercentScaled from a base Float.
     */
    @JvmStatic
    fun scaled(initialBaseValue: Float): DimenPercentScaled = DimenPercentScaled(initialBaseValue)

    // EN Rotation facilitator functions for Java.
    // PT Funções facilitadoras de rotação para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun psdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun phdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with rotation override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwdpRotate(context: Context, value: Int, rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwdpRotate(context, rotationValue, finalQualifierResolver, orientation, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN UiModeType facilitator functions for Java.
    // PT Funções facilitadoras de UiModeType para Java.

    /**
     * EN Facilitator for Smallest Width (sdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun psdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.psdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Height (hdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun phdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.phdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    /**
     * EN Facilitator for Screen Width (wdp) with UiModeType override.
     */
    @JvmStatic
    @JvmOverloads
    fun pwdpMode(context: Context, value: Int, modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float =
        value.pwdpMode(context, modeValue, uiModeType, finalQualifierResolver, ignoreMultiWindows, applyAspectRatio, customSensitivityK)

    // EN Literal % of screen / reference (px, dp). PT Percentual literal da tela / referência.

    @JvmStatic
    @JvmOverloads
    fun spaceW(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceW(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceWDp(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceWDp(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceSw(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceSw(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceSwDp(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceSwDp(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceH(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceH(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceHDp(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceHDp(context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun space(context: Context, percent: Int, referenceDp: Float, ignoreMultiWindows: Boolean = false): Float =
        percent.space(referenceDp, context, ignoreMultiWindows)

    @JvmStatic
    @JvmOverloads
    fun spaceDp(context: Context, percent: Int, referenceDp: Float, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceDp(referenceDp, context, ignoreMultiWindows)

    @JvmStatic
    fun spaceWi(context: Context, percent: Int): Float = percent.spaceWi(context)

    @JvmStatic
    fun spaceWDpi(context: Context, percent: Int): Float = percent.spaceWDpi(context)

    @JvmStatic
    fun spaceSwi(context: Context, percent: Int): Float = percent.spaceSwi(context)

    @JvmStatic
    fun spaceSwDpi(context: Context, percent: Int): Float = percent.spaceSwDpi(context)

    @JvmStatic
    fun spaceHi(context: Context, percent: Int): Float = percent.spaceHi(context)

    @JvmStatic
    fun spaceHDpi(context: Context, percent: Int): Float = percent.spaceHDpi(context)

    @JvmStatic
    fun spaceI(context: Context, percent: Int, referenceDp: Float): Float =
        percent.spaceI(referenceDp, context)

    @JvmStatic
    fun spaceDpi(context: Context, percent: Int, referenceDp: Float): Float =
        percent.spaceDpi(referenceDp, context)

    @JvmStatic
    @JvmOverloads
    fun spaceWPx(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceWPx(context, ignoreMultiWindows)

    @JvmStatic
    fun spaceWPxi(context: Context, percent: Int): Float = percent.spaceWPxi(context)

    @JvmStatic
    @JvmOverloads
    fun spaceSwPx(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceSwPx(context, ignoreMultiWindows)

    @JvmStatic
    fun spaceSwPxi(context: Context, percent: Int): Float = percent.spaceSwPxi(context)

    @JvmStatic
    @JvmOverloads
    fun spaceHPx(context: Context, percent: Int, ignoreMultiWindows: Boolean = false): Float =
        percent.spaceHPx(context, ignoreMultiWindows)

    @JvmStatic
    fun spaceHPxi(context: Context, percent: Int): Float = percent.spaceHPxi(context)

    @JvmStatic
    @JvmOverloads
    fun spacePx(context: Context, percent: Int, referenceDp: Float, ignoreMultiWindows: Boolean = false): Float =
        percent.spacePx(referenceDp, context, ignoreMultiWindows)

    @JvmStatic
    fun spacePxi(context: Context, percent: Int, referenceDp: Float): Float =
        percent.spacePxi(referenceDp, context)
}