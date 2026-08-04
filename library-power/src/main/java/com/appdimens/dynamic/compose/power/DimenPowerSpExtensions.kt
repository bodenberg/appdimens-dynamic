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
package com.appdimens.dynamic.compose.power

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.getCurrentUiModeType
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp

// EN Rotation facilitator extensions for Sp.
// PT Extensões facilitadoras para rotação (Sp).

// Removed duplicate Int.pwsspRotate (kept in DimenPowerSp.kt)

/**
 * EN Pixel (Float) variant of [pwsspRotate].
 * PT Variante em Pixel (Float) de [pwsspRotate].
 */
@Composable
fun Number.pwsspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwsspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwsspRotate].
 * PT Variante em Pixel (Float) de [pwsspRotate].
 */
@Composable
fun TextUnit.pwsspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwsspRotatePlain(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwsspRotatePlain].
 * PT Variante em Pixel (Float) de [pwsspRotatePlain].
 */
@Composable
fun TextUnit.pwsspRotatePlainPx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwsspRotatePlainPx.toPx() }
    }
}

/**
 * EN Plain pwssp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação pwssp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwsspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [pwsspRotatePlain] with [rotation] as [TextUnit] (no scaling).
 * PT Variante em px de [pwsspRotatePlain] com [rotation] em [TextUnit] (pwsem escala).
 */
@Composable
fun TextUnit.pwsspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@pwsspRotatePlainPx.toPx() }
}

/**
 * EN Plain pwhsp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação pwhsp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwhspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [pwhspRotatePlain] with [rotation] as [TextUnit].
 * PT Variante em px de [pwhspRotatePlain] com [rotation] em [TextUnit].
 */
@Composable
fun TextUnit.pwhspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@pwhspRotatePlainPx.toPx() }
}

/**
 * EN Plain pwwsp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação pwwsp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwwspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [pwwspRotatePlain] with [rotation] as [TextUnit].
 * PT Variante em px de [pwwspRotatePlain] com [rotation] em [TextUnit].
 */
@Composable
fun TextUnit.pwwspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@pwwspRotatePlainPx.toPx() }
}

/**
 * EN Plain pwssp mode: [mode] and receiver already scaled; logic only.
 * PT Modo pwssp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwsspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [pwsspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [pwsspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.pwsspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@pwsspModePlainPx.toPx() }
}

/**
 * EN Plain pwhsp mode: [mode] and receiver already scaled; logic only.
 * PT Modo pwhsp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwhspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [pwhspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [pwhspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.pwhspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@pwhspModePlainPx.toPx() }
}

/**
 * EN Plain pwwsp mode: [mode] and receiver already scaled; logic only.
 * PT Modo pwwsp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.pwwspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [pwwspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [pwwspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.pwwspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@pwwspModePlainPx.toPx() }
}

/**
 * EN Plain pwssp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador pwssp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwsspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [pwsspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [pwsspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwsspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@pwsspQualifierPlainPx.toPx() }
}

/**
 * EN Plain pwhsp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador pwhsp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwhspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [pwhspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [pwhspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwhspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@pwhspQualifierPlainPx.toPx() }
}

/**
 * EN Plain pwwsp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador pwwsp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwwspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [pwwspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [pwwspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwwspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@pwwspQualifierPlainPx.toPx() }
}

/**
 * EN Plain pwssp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã pwssp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwsspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [pwsspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [pwsspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwsspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@pwsspScreenPlainPx.toPx() }
}

/**
 * EN Plain pwhsp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã pwhsp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwhspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [pwhspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [pwhspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwhspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@pwhspScreenPlainPx.toPx() }
}

/**
 * EN Plain pwwsp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã pwwsp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwwspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [pwwspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [pwwspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.pwwspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@pwwspScreenPlainPx.toPx() }
}
// Removed duplicate Int.pwhspRotate (kept in DimenPowerSp.kt)

/**
 * EN Pixel (Float) variant of [pwhspRotate].
 * PT Variante em Pixel (Float) de [pwhspRotate].
 */
@Composable
fun Number.pwhspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwhspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwhspRotate].
 * PT Variante em Pixel (Float) de [pwhspRotate].
 */
@Composable
fun TextUnit.pwhspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwhspRotatePlain(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwhspRotatePlain].
 * PT Variante em Pixel (Float) de [pwhspRotatePlain].
 */
@Composable
fun TextUnit.pwhspRotatePlainPx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwhspRotatePlainPx.toPx() }
    }
}

// Removed duplicate Int.pwwspRotate (kept in DimenPowerSp.kt)

/**
 * EN Pixel (Float) variant of [pwwspRotate].
 * PT Variante em Pixel (Float) de [pwwspRotate].
 */
@Composable
fun Number.pwwspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwwspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwwspRotate].
 * PT Variante em Pixel (Float) de [pwwspRotate].
 */
@Composable
fun TextUnit.pwwspRotatePx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    val baseValue = if (isTargetOrientation) rotationValue.toFloat() else this.value
    val resQ = finalQualifierResolver
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device is in the specified [orientation], it uses [rotationValue]
 * scaled with the given [finalQualifierResolver].
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo está na [orientation] especificada, usa [rotationValue]
 * escalado com o [finalQualifierResolver] dado.
 */
@Composable
fun TextUnit.pwwspRotatePlain(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwwspRotatePlain].
 * PT Variante em Pixel (Float) de [pwwspRotatePlain].
 */
@Composable
fun TextUnit.pwwspRotatePlainPx(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
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
        val resQ = finalQualifierResolver
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwwspRotatePlainPx.toPx() }
    }
}


// EN UiModeType facilitator extensions for Sp.
// PT Extensões facilitadoras para UiModeType (Sp).

// Removed duplicate Int.pwsspMode (kept in DimenPowerSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwsspMode].
 * PT Variante em Pixel (Float) de [pwsspMode].
 */
@Composable
fun Number.pwsspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwsspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwsspMode].
 * PT Variante em Pixel (Float) de [pwsspMode].
 */
@Composable
fun TextUnit.pwsspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwsspModePlain(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.pwsspModePlainPx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwsspModePlainPx.toPx() }
    }
}

// Removed duplicate Int.pwhspMode (kept in DimenPowerSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwhspMode].
 * PT Variante em Pixel (Float) de [pwhspMode].
 */
@Composable
fun Number.pwhspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwhspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwhspMode].
 * PT Variante em Pixel (Float) de [pwhspMode].
 */
@Composable
fun TextUnit.pwhspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwhspModePlain(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.pwhspModePlainPx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwhspModePlainPx.toPx() }
    }
}

// Removed duplicate Int.pwwspMode (kept in DimenPowerSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwwspMode].
 * PT Variante em Pixel (Float) de [pwwspMode].
 */
@Composable
fun Number.pwwspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val baseValue = if (match) modeValue.toFloat() else this.toFloat()
    val resQualifier = if (match) resQ else DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwwspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwwspMode].
 * PT Variante em Pixel (Float) de [pwwspMode].
 */
@Composable
fun TextUnit.pwwspModePx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val baseValue = if (match) modeValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches the specified [uiModeType], it uses [modeValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] especificado, usa [modeValue] no lugar.
 */
@Composable
fun TextUnit.pwwspModePlain(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.pwwspModePlainPx(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val match = currentUiModeType == uiModeType
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = modeValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwwspModePlainPx.toPx() }
    }
}

// EN DpQualifier facilitator extensions for Sp.
// PT Extensões facilitadoras para DpQualifier (Sp).

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.pwsspQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.pwssp by default, 50.pwssp when smallestScreenWidthDp >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwsspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwsspQualifier].
 * PT Variante em Pixel (Float) de [pwsspQualifier].
 */
@Composable
fun Number.pwsspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwsspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwsspQualifier].
 * PT Variante em Pixel (Float) de [pwsspQualifier].
 */
@Composable
fun TextUnit.pwsspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwsspQualifierPlain(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwsspQualifierPlain].
 * PT Variante em Pixel (Float) de [pwsspQualifierPlain].
 */
@Composable
fun TextUnit.pwsspQualifierPlainPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwsspQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.pwhspQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.pwhsp by default, 50.pwhsp when screenHeightDp >= 800.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwhspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwhspQualifier].
 * PT Variante em Pixel (Float) de [pwhspQualifier].
 */
@Composable
fun Number.pwhspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwhspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwhspQualifier].
 * PT Variante em Pixel (Float) de [pwhspQualifier].
 */
@Composable
fun TextUnit.pwhspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwhspQualifierPlain(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwhspQualifierPlain].
 * PT Variante em Pixel (Float) de [pwhspQualifierPlain].
 */
@Composable
fun TextUnit.pwhspQualifierPlainPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwhspQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.pwwspQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.pwwsp by default, 50.pwwsp when screenWidthDp >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwwspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwwspQualifier].
 * PT Variante em Pixel (Float) de [pwwspQualifier].
 */
@Composable
fun Number.pwwspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.toFloat()
    val resQualifier = if (qualifierMatch) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwwspQualifier(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwwspQualifier].
 * PT Variante em Pixel (Float) de [pwwspQualifier].
 */
@Composable
fun TextUnit.pwwspQualifierPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val baseValue = if (qualifierMatch) qualifiedValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the screen metric for [qualifierType] is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando a métrica de tela para [qualifierType] é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@Composable
fun TextUnit.pwwspQualifierPlain(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwwspQualifierPlain].
 * PT Variante em Pixel (Float) de [pwwspQualifierPlain].
 */
@Composable
fun TextUnit.pwwspQualifierPlainPx(
    qualifiedValue: Number,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) {
        val baseValue = qualifiedValue.toFloat()
        val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwwspQualifierPlainPx.toPx() }
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions for Sp.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.pwsspScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.pwssp by default, 50.pwssp on television with sw >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwsspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwsspScreen].
 * PT Variante em Pixel (Float) de [pwsspScreen].
 */
@Composable
fun Number.pwsspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.SMALL_WIDTH) else DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwsspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwsspScreen].
 * PT Variante em Pixel (Float) de [pwsspScreen].
 */
@Composable
fun TextUnit.pwsspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwsspScreenPlain(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwsspScreenPlain].
 * PT Variante em Pixel (Float) de [pwsspScreenPlain].
 */
@Composable
fun TextUnit.pwsspScreenPlainPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.SMALL_WIDTH
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwsspScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.pwhspScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.pwhsp by default, 50.pwhsp on television with height >= 800.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwhspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwhspScreen].
 * PT Variante em Pixel (Float) de [pwhspScreen].
 */
@Composable
fun Number.pwhspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.HEIGHT) else DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwhspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwhspScreen].
 * PT Variante em Pixel (Float) de [pwhspScreen].
 */
@Composable
fun TextUnit.pwhspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwhspScreenPlain(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwhspScreenPlain].
 * PT Variante em Pixel (Float) de [pwhspScreenPlain].
 */
@Composable
fun TextUnit.pwhspScreenPlainPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.HEIGHT
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwhspScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.pwwspScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.pwwsp by default, 50.pwwsp on television with width >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.pwwspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [pwwspScreen].
 * PT Variante em Pixel (Float) de [pwwspScreen].
 */
@Composable
fun Number.pwwspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.toFloat()
    val resQualifier = if (match) (finalQualifierResolver ?: DpQualifier.WIDTH) else DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original value **auto-scaled** using the specified qualifier if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwwspScreen(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [pwwspScreen].
 * PT Variante em Pixel (Float) de [pwwspScreen].
 */
@Composable
fun TextUnit.pwwspScreenPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val baseValue = if (match) screenValue.toFloat() else this.value
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
    val cacheKey = DimenCache.buildKey(
        baseValue = baseValue,
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.POWER,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val pwsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberPowerSpPx(
        cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Returns the original raw TextUnit value if the condition is not met.
 * When the device matches [uiModeType] AND the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Retorna o valor original de TextUnit bruto se a condição não for atendida.
 * Quando o dispositivo corresponde ao [uiModeType] E a métrica de tela para
 * [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@Composable
fun TextUnit.pwwspScreenPlain(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true
, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): TextUnit {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [pwwspScreenPlain].
 * PT Variante em Pixel (Float) de [pwwspScreenPlain].
 */
@Composable
fun TextUnit.pwwspScreenPlainPx(
    screenValue: Number,
    uiModeType: UiModeType,
    qualifierType: DpQualifier,
    qualifierValue: Number,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val androidContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    val match = uiModeMatch && qualifierMatch
    val resQ = finalQualifierResolver ?: DpQualifier.WIDTH
    return if (match) {
        val baseValue = screenValue.toFloat()
        val valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE
        val cacheKey = DimenCache.buildKey(
            baseValue = baseValue,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.POWER,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val pwsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberPowerSpPx(
            cacheKey, pwsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@pwwspScreenPlainPx.toPx() }
    }
}
