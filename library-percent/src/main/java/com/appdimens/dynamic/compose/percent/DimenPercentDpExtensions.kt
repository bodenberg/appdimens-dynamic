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
package com.appdimens.dynamic.compose.percent

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.getCurrentUiModeType
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp

// EN Rotation facilitator extensions.
// PT Extensões facilitadoras para rotação.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`
 * → 30.sdp by default, 45 scaled by SMALL_WIDTH in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)`
 * → 30.sdp por padrão, 45 escalado por SMALL_WIDTH no paisagem.
 */
@Composable
fun Int.psdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [sdpRotate].
 * PT Variante em Pixel (Float) de [sdpRotate].
 */
@Composable
fun Int.psdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.psdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [sdpRotate].
 * PT Variante em Pixel (Float) de [sdpRotate].
 */
@Composable
fun Dp.psdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.psdpRotatePlain(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [sdpRotatePlain].
 * PT Variante em Pixel (Float) de [sdpRotatePlain].
 */
@Composable
fun Dp.psdpRotatePlainPx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@psdpRotatePlainPx.toPx() }
    }
}

/**
 * EN
 * Plain rotation with **already scaled** [rotation] and receiver: no further scaling, only the orientation branch.
 * Use when both sides come from the same strategy (e.g. `30.psdp.psdpRotatePlain(20.psdp)`).
 *
 * PT
 * Rotação Plain com [rotation] e recetor **já escalados**: sem nova conversão, só o ramo de orientação.
 * Use quando ambos os lados vêm da mesma estratégia (ex.: `30.psdp.psdpRotatePlain(20.psdp)`).
 */
@Composable
fun Dp.psdpRotatePlain(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel (Float) variant of [psdpRotatePlain] with [rotation] as [Dp] (no scaling).
 * PT Variante em Pixel (Float) de [psdpRotatePlain] com [rotation] em [Dp] (sem escala).
 */
@Composable
fun Dp.psdpRotatePlainPx(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@psdpRotatePlainPx.toPx() }
}

/**
 * EN Plain hdp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação hdp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun Dp.phdpRotatePlain(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [phdpRotatePlain] with [rotation] as [Dp].
 * PT Variante em px de [phdpRotatePlain] com [rotation] em [Dp].
 */
@Composable
fun Dp.phdpRotatePlainPx(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@phdpRotatePlainPx.toPx() }
}

/**
 * EN Plain wdp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação wdp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun Dp.pwdpRotatePlain(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Dp {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [pwdpRotatePlain] with [rotation] as [Dp].
 * PT Variante em px de [pwdpRotatePlain] com [rotation] em [Dp].
 */
@Composable
fun Dp.pwdpRotatePlainPx(rotation: Dp, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@pwdpRotatePlainPx.toPx() }
}

/**
 * EN Plain sdp mode: [mode] and receiver already scaled; logic only.
 * PT Modo sdp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun Dp.psdpModePlain(mode: Dp, uiModeType: UiModeType): Dp {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [psdpModePlain] with [mode] as [Dp].
 * PT Variante em px de [psdpModePlain] com [mode] em [Dp].
 */
@Composable
fun Dp.psdpModePlainPx(mode: Dp, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@psdpModePlainPx.toPx() }
}

/**
 * EN Plain hdp mode: [mode] and receiver already scaled; logic only.
 * PT Modo hdp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun Dp.phdpModePlain(mode: Dp, uiModeType: UiModeType): Dp {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [phdpModePlain] with [mode] as [Dp].
 * PT Variante em px de [phdpModePlain] com [mode] em [Dp].
 */
@Composable
fun Dp.phdpModePlainPx(mode: Dp, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@phdpModePlainPx.toPx() }
}

/**
 * EN Plain wdp mode: [mode] and receiver already scaled; logic only.
 * PT Modo wdp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun Dp.pwdpModePlain(mode: Dp, uiModeType: UiModeType): Dp {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [pwdpModePlain] with [mode] as [Dp].
 * PT Variante em px de [pwdpModePlain] com [mode] em [Dp].
 */
@Composable
fun Dp.pwdpModePlainPx(mode: Dp, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@pwdpModePlainPx.toPx() }
}

/**
 * EN Plain sdp qualifier: [qualified] and receiver already scaled; logic only ([qualifierValue] is config threshold).
 * PT Qualificador sdp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpQualifierPlain(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [psdpQualifierPlain] with [qualified] as [Dp].
 * PT Variante em px de [psdpQualifierPlain] com [qualified] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpQualifierPlainPx(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@psdpQualifierPlainPx.toPx() }
}

/**
 * EN Plain hdp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador hdp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpQualifierPlain(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [phdpQualifierPlain] with [qualified] as [Dp].
 * PT Variante em px de [phdpQualifierPlain] com [qualified] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpQualifierPlainPx(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@phdpQualifierPlainPx.toPx() }
}

/**
 * EN Plain wdp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador wdp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpQualifierPlain(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [pwdpQualifierPlain] with [qualified] as [Dp].
 * PT Variante em px de [pwdpQualifierPlain] com [qualified] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpQualifierPlainPx(qualified: Dp, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@pwdpQualifierPlainPx.toPx() }
}

/**
 * EN Plain sdp screen: [screen] and receiver already scaled; logic only (ui mode + qualifier threshold).
 * PT Ecrã sdp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpScreenPlain(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [psdpScreenPlain] with [screen] as [Dp].
 * PT Variante em px de [psdpScreenPlain] com [screen] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpScreenPlainPx(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@psdpScreenPlainPx.toPx() }
}

/**
 * EN Plain hdp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã hdp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpScreenPlain(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [phdpScreenPlain] with [screen] as [Dp].
 * PT Variante em px de [phdpScreenPlain] com [screen] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpScreenPlainPx(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@phdpScreenPlainPx.toPx() }
}

/**
 * EN Plain wdp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã wdp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpScreenPlain(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Dp {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [pwdpScreenPlain] with [screen] as [Dp].
 * PT Variante em px de [pwdpScreenPlain] com [screen] em [Dp].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpScreenPlainPx(screen: Dp, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@pwdpScreenPlainPx.toPx() }
}
/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)`
 * → 30.hdp by default, 45 scaled by HEIGHT in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)`
 * → 30.hdp por padrão, 45 escalado por HEIGHT no paisagem.
 */
@Composable
fun Int.phdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [hdpRotate].
 * PT Variante em Pixel (Float) de [hdpRotate].
 */
@Composable
fun Int.phdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.phdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [hdpRotate].
 * PT Variante em Pixel (Float) de [hdpRotate].
 */
@Composable
fun Dp.phdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.phdpRotatePlain(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [hdpRotatePlain].
 * PT Variante em Pixel (Float) de [hdpRotatePlain].
 */
@Composable
fun Dp.phdpRotatePlainPx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@phdpRotatePlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 * Usage example: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)`
 * → 30.wdp by default, 45 scaled by WIDTH in landscape.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo está na [orientation] especificada,
 * usa [rotationValue] escalado com o [finalQualifierResolver] dado.
 * Exemplo de uso: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)`
 * → 30.wdp por padrão, 45 escalado por WIDTH no paisagem.
 */
@Composable
fun Int.pwdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [wdpRotate].
 * PT Variante em Pixel (Float) de [wdpRotate].
 */
@Composable
fun Int.pwdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.toFloat()
    val resQualifier = if (isTargetOrientation) finalQualifierResolver else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.pwdpRotate(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [wdpRotate].
 * PT Variante em Pixel (Float) de [wdpRotate].
 */
@Composable
fun Dp.pwdpRotatePx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = finalQualifierResolver,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun Dp.pwdpRotatePlain(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [wdpRotatePlain].
 * PT Variante em Pixel (Float) de [wdpRotatePlain].
 */
@Composable
fun Dp.pwdpRotatePlainPx(rotationValue: Number, finalQualifierResolver: DpQualifier = DpQualifier.WIDTH, orientation: Orientation = Orientation.LANDSCAPE, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        val baseValue = rotationValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = finalQualifierResolver,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, finalQualifierResolver, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwdpRotatePlainPx.toPx() }
    }
}


// EN UiModeType facilitator extensions.
// PT Extensões facilitadoras para UiModeType.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.sdpMode(50, UiModeType.TELEVISION)`
 * → 30.sdp by default, 50.sdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.sdpMode(50, UiModeType.TELEVISION)`
 * → 30.sdp por padrão, 50.sdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.psdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpMode].
 * PT Variante em Pixel (Float) de [sdpMode].
 */
@Composable
fun Int.psdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun Dp.psdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [sdpMode].
 * PT Variante em Pixel (Float) de [sdpMode].
 */
@Composable
fun Dp.psdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun Dp.psdpModePlain(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [sdpModePlain].
 * PT Variante em Pixel (Float) de [sdpModePlain].
 */
@Composable
fun Dp.psdpModePlainPx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@psdpModePlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.hdpMode(50, UiModeType.TELEVISION)`
 * → 30.hdp by default, 50.hdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.hdpMode(50, UiModeType.TELEVISION)`
 * → 30.hdp por padrão, 50.hdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.phdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpMode].
 * PT Variante em Pixel (Float) de [hdpMode].
 */
@Composable
fun Int.phdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [hdpMode].
 * PT Variante em Pixel (Float) de [hdpMode].
 */
@Composable
fun Dp.phdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpModePlain(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [hdpModePlain].
 * PT Variante em Pixel (Float) de [hdpModePlain].
 */
@Composable
fun Dp.phdpModePlainPx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@phdpModePlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 * Usage example: `30.wdpMode(50, UiModeType.TELEVISION)`
 * → 30.wdp by default, 50.wdp on television.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] especificado,
 * usa [modeValue] no lugar.
 * Exemplo de uso: `30.wdpMode(50, UiModeType.TELEVISION)`
 * → 30.wdp por padrão, 50.wdp na televisão.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Int.pwdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpMode].
 * PT Variante em Pixel (Float) de [wdpMode].
 */
@Composable
fun Int.pwdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpMode(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [wdpMode].
 * PT Variante em Pixel (Float) de [wdpMode].
 */
@Composable
fun Dp.pwdpModePx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.value
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpModePlain(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [wdpModePlain].
 * PT Variante em Pixel (Float) de [wdpModePlain].
 */
@Composable
fun Dp.pwdpModePlainPx(modeValue: Number, uiModeType: UiModeType, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwdpModePlainPx.toPx() }
    }
}

// EN DpQualifier facilitator extensions.
// PT Extensões facilitadoras para DpQualifier.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp by default, 50.sdp when smallestScreenWidthDp >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp por padrão, 50.sdp quando smallestScreenWidthDp >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.psdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpQualifier].
 * PT Variante em Pixel (Float) de [sdpQualifier].
 */
@Composable
fun Number.psdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpQualifier].
 * PT Variante em Pixel (Float) de [sdpQualifier].
 */
@Composable
fun Dp.psdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpQualifierPlain(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpQualifierPlain].
 * PT Variante em Pixel (Float) de [sdpQualifierPlain].
 */
@Composable
fun Dp.psdpQualifierPlainPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@psdpQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.hdp by default, 50.hdp when screenHeightDp >= 800.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.hdp por padrão, 50.hdp quando screenHeightDp >= 800.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.phdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpQualifier].
 * PT Variante em Pixel (Float) de [hdpQualifier].
 */
@Composable
fun Number.phdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpQualifier].
 * PT Variante em Pixel (Float) de [hdpQualifier].
 */
@Composable
fun Dp.phdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpQualifierPlain(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpQualifierPlain].
 * PT Variante em Pixel (Float) de [hdpQualifierPlain].
 */
@Composable
fun Dp.phdpQualifierPlainPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@phdpQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.wdp by default, 50.wdp when screenWidthDp >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 * Exemplo de uso: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.wdp por padrão, 50.wdp quando screenWidthDp >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpQualifier].
 * PT Variante em Pixel (Float) de [wdpQualifier].
 */
@Composable
fun Number.pwdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpQualifier(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpQualifier].
 * PT Variante em Pixel (Float) de [wdpQualifier].
 */
@Composable
fun Dp.pwdpQualifierPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpQualifierPlain(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpQualifierPlain].
 * PT Variante em Pixel (Float) de [wdpQualifierPlain].
 */
@Composable
fun Dp.pwdpQualifierPlainPx(qualifiedValue: Number, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwdpQualifierPlainPx.toPx() }
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.sdpScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp by default, 50.sdp on television with sw >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.sdpScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.sdp por padrão, 50.sdp na televisão com sw >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.psdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpScreen].
 * PT Variante em Pixel (Float) de [sdpScreen].
 */
@Composable
fun Number.psdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpScreen].
 * PT Variante em Pixel (Float) de [sdpScreen].
 */
@Composable
fun Dp.psdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.psdpScreenPlain(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [sdpScreenPlain].
 * PT Variante em Pixel (Float) de [sdpScreenPlain].
 */
@Composable
fun Dp.psdpScreenPlainPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@psdpScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.hdpScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.hdp by default, 50.hdp on television with height >= 800.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.hdpScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.hdp por padrão, 50.hdp na televisão com height >= 800.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.phdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpScreen].
 * PT Variante em Pixel (Float) de [hdpScreen].
 */
@Composable
fun Number.phdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpScreen].
 * PT Variante em Pixel (Float) de [hdpScreen].
 */
@Composable
fun Dp.phdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.phdpScreenPlain(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [hdpScreenPlain].
 * PT Variante em Pixel (Float) de [hdpScreenPlain].
 */
@Composable
fun Dp.phdpScreenPlainPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@phdpScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.wdpScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.wdp by default, 50.wdp on television with width >= 600.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 * Exemplo de uso: `30.wdpScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.wdp por padrão, 50.wdp na televisão com width >= 600.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpScreen].
 * PT Variante em Pixel (Float) de [wdpScreen].
 */
@Composable
fun Number.pwdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQualifier, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpScreen(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    return rememberPercentDp(
        cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpScreen].
 * PT Variante em Pixel (Float) de [wdpScreen].
 */
@Composable
fun Dp.pwdpScreenPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.PERCENT,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = DimenCache.ValueType.PX,
        customSensitivityK = customSensitivityK
    )
    val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPercentPxFromDp(
        cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
        ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for Dp with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw Dp value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de Dp bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Dp.pwdpScreenPlain(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Dp {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.DP,
            customSensitivityK = customSensitivityK
        )
        val layoutStamp = layoutRememberStamp(configuration, androidContext)
        rememberPercentDp(
            cacheKey, layoutStamp, androidContext, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [wdpScreenPlain].
 * PT Variante em Pixel (Float) de [wdpScreenPlain].
 */
@Composable
fun Dp.pwdpScreenPlainPx(screenValue: Number, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    return if (match) {
        val baseValue = screenValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.PERCENT,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = DimenCache.ValueType.PX,
            customSensitivityK = customSensitivityK
        )
        val pxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPercentPxFromDp(
            cacheKey, pxStamp, androidContext, density, baseValue, configuration, resQ, Inverter.DEFAULT,
            ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwdpScreenPlainPx.toPx() }
    }
}
