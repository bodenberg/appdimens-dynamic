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
package com.appdimens.dynamic.compose.diagonal

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

// Removed duplicate Int.dgsspRotate (kept in DimenDiagonalSp.kt)

/**
 * EN Pixel (Float) variant of [dgsspRotate].
 * PT Variante em Pixel (Float) de [dgsspRotate].
 */
@Composable
fun Number.dgsspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspRotate(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgsspRotate].
 * PT Variante em Pixel (Float) de [dgsspRotate].
 */
@Composable
fun TextUnit.dgsspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspRotatePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgsspRotatePlain].
 * PT Variante em Pixel (Float) de [dgsspRotatePlain].
 */
@Composable
fun TextUnit.dgsspRotatePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgsspRotatePlainPx.toPx() }
    }
}

/**
 * EN Plain dgssp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação dgssp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dgsspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [dgsspRotatePlain] with [rotation] as [TextUnit] (no scaling).
 * PT Variante em px de [dgsspRotatePlain] com [rotation] em [TextUnit] (dgsem escala).
 */
@Composable
fun TextUnit.dgsspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@dgsspRotatePlainPx.toPx() }
}

/**
 * EN Plain dghsp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação dghsp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dghspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [dghspRotatePlain] with [rotation] as [TextUnit].
 * PT Variante em px de [dghspRotatePlain] com [rotation] em [TextUnit].
 */
@Composable
fun TextUnit.dghspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@dghspRotatePlainPx.toPx() }
}

/**
 * EN Plain dgwsp rotation: [rotation] and receiver already scaled; logic only.
 * PT Rotação dgwsp Plain: [rotation] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dgwspRotatePlain(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) rotation else this
}

/**
 * EN Pixel variant of [dgwspRotatePlain] with [rotation] as [TextUnit].
 * PT Variante em px de [dgwspRotatePlain] com [rotation] em [TextUnit].
 */
@Composable
fun TextUnit.dgwspRotatePlainPx(rotation: TextUnit, orientation: Orientation = Orientation.LANDSCAPE): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) density.run { rotation.toPx() } else density.run { this@dgwspRotatePlainPx.toPx() }
}

/**
 * EN Plain dgssp mode: [mode] and receiver already scaled; logic only.
 * PT Modo dgssp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dgsspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [dgsspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [dgsspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.dgsspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@dgsspModePlainPx.toPx() }
}

/**
 * EN Plain dghsp mode: [mode] and receiver already scaled; logic only.
 * PT Modo dghsp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dghspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [dghspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [dghspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.dghspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@dghspModePlainPx.toPx() }
}

/**
 * EN Plain dgwsp mode: [mode] and receiver already scaled; logic only.
 * PT Modo dgwsp Plain: [mode] e recetor já escalados; só a lógica.
 */
@Composable
fun TextUnit.dgwspModePlain(mode: TextUnit, uiModeType: UiModeType): TextUnit {
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) mode else this
}

/**
 * EN Pixel variant of [dgwspModePlain] with [mode] as [TextUnit].
 * PT Variante em px de [dgwspModePlain] com [mode] em [TextUnit].
 */
@Composable
fun TextUnit.dgwspModePlainPx(mode: TextUnit, uiModeType: UiModeType): Float {
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    return if (currentUiModeType == uiModeType) density.run { mode.toPx() } else density.run { this@dgwspModePlainPx.toPx() }
}

/**
 * EN Plain dgssp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador dgssp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgsspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [dgsspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [dgsspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgsspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@dgsspQualifierPlainPx.toPx() }
}

/**
 * EN Plain dghsp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador dghsp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dghspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [dghspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [dghspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dghspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@dghspQualifierPlainPx.toPx() }
}

/**
 * EN Plain dgwsp qualifier: [qualified] and receiver already scaled; logic only.
 * PT Qualificador dgwsp Plain: [qualified] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgwspQualifierPlain(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) qualified else this
}

/**
 * EN Pixel variant of [dgwspQualifierPlain] with [qualified] as [TextUnit].
 * PT Variante em px de [dgwspQualifierPlain] com [qualified] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgwspQualifierPlainPx(qualified: TextUnit, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (qualifierMatch) density.run { qualified.toPx() } else density.run { this@dgwspQualifierPlainPx.toPx() }
}

/**
 * EN Plain dgssp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã dgssp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgsspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [dgsspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [dgsspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgsspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@dgsspScreenPlainPx.toPx() }
}

/**
 * EN Plain dghsp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã dghsp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dghspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [dghspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [dghspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dghspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@dghspScreenPlainPx.toPx() }
}

/**
 * EN Plain dgwsp screen: [screen] and receiver already scaled; logic only.
 * PT Ecrã dgwsp Plain: [screen] e recetor já escalados; só a lógica.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgwspScreenPlain(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): TextUnit {
    val configuration = LocalConfiguration.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) screen else this
}

/**
 * EN Pixel variant of [dgwspScreenPlain] with [screen] as [TextUnit].
 * PT Variante em px de [dgwspScreenPlain] com [screen] em [TextUnit].
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun TextUnit.dgwspScreenPlainPx(screen: TextUnit, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val currentUiModeType = getCurrentUiModeType()
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue.toFloat()
    return if (uiModeMatch && qualifierMatch) density.run { screen.toPx() } else density.run { this@dgwspScreenPlainPx.toPx() }
}
// Removed duplicate Int.dghspRotate (kept in DimenDiagonalSp.kt)

/**
 * EN Pixel (Float) variant of [dghspRotate].
 * PT Variante em Pixel (Float) de [dghspRotate].
 */
@Composable
fun Number.dghspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspRotate(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dghspRotate].
 * PT Variante em Pixel (Float) de [dghspRotate].
 */
@Composable
fun TextUnit.dghspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspRotatePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dghspRotatePlain].
 * PT Variante em Pixel (Float) de [dghspRotatePlain].
 */
@Composable
fun TextUnit.dghspRotatePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dghspRotatePlainPx.toPx() }
    }
}

// Removed duplicate Int.dgwspRotate (kept in DimenDiagonalSp.kt)

/**
 * EN Pixel (Float) variant of [dgwspRotate].
 * PT Variante em Pixel (Float) de [dgwspRotate].
 */
@Composable
fun Number.dgwspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspRotate(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgwspRotate].
 * PT Variante em Pixel (Float) de [dgwspRotate].
 */
@Composable
fun TextUnit.dgwspRotatePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspRotatePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgwspRotatePlain].
 * PT Variante em Pixel (Float) de [dgwspRotatePlain].
 */
@Composable
fun TextUnit.dgwspRotatePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgwspRotatePlainPx.toPx() }
    }
}


// EN UiModeType facilitator extensions for Sp.
// PT Extensões facilitadoras para UiModeType (Sp).

// Removed duplicate Int.dgsspMode (kept in DimenDiagonalSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgsspMode].
 * PT Variante em Pixel (Float) de [dgsspMode].
 */
@Composable
fun Number.dgsspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspMode(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgsspMode].
 * PT Variante em Pixel (Float) de [dgsspMode].
 */
@Composable
fun TextUnit.dgsspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspModePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.dgsspModePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgsspModePlainPx.toPx() }
    }
}

// Removed duplicate Int.dghspMode (kept in DimenDiagonalSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dghspMode].
 * PT Variante em Pixel (Float) de [dghspMode].
 */
@Composable
fun Number.dghspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspMode(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dghspMode].
 * PT Variante em Pixel (Float) de [dghspMode].
 */
@Composable
fun TextUnit.dghspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspModePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.dghspModePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dghspModePlainPx.toPx() }
    }
}

// Removed duplicate Int.dgwspMode (kept in DimenDiagonalSp.kt)

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgwspMode].
 * PT Variante em Pixel (Float) de [dgwspMode].
 */
@Composable
fun Number.dgwspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspMode(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgwspMode].
 * PT Variante em Pixel (Float) de [dgwspMode].
 */
@Composable
fun TextUnit.dgwspModePx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspModePlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

@Composable
fun TextUnit.dgwspModePlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgwspModePlainPx.toPx() }
    }
}

// EN DpQualifier facilitator extensions for Sp.
// PT Extensões facilitadoras para DpQualifier (Sp).

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.dgsspQualifier(50, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.dgssp by default, 50.dgssp when smallestScreenWidthDp >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dgsspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgsspQualifier].
 * PT Variante em Pixel (Float) de [dgsspQualifier].
 */
@Composable
fun Number.dgsspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgsspQualifier].
 * PT Variante em Pixel (Float) de [dgsspQualifier].
 */
@Composable
fun TextUnit.dgsspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspQualifierPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgsspQualifierPlain].
 * PT Variante em Pixel (Float) de [dgsspQualifierPlain].
 */
@Composable
fun TextUnit.dgsspQualifierPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgsspQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.dghspQualifier(50, DpQualifier.HEIGHT, 800)`
 * → 30.dghsp by default, 50.dghsp when screenHeightDp >= 800.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dghspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dghspQualifier].
 * PT Variante em Pixel (Float) de [dghspQualifier].
 */
@Composable
fun Number.dghspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dghspQualifier].
 * PT Variante em Pixel (Float) de [dghspQualifier].
 */
@Composable
fun TextUnit.dghspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspQualifierPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dghspQualifierPlain].
 * PT Variante em Pixel (Float) de [dghspQualifierPlain].
 */
@Composable
fun TextUnit.dghspQualifierPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dghspQualifierPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 * Usage example: `30.dgwspQualifier(50, DpQualifier.WIDTH, 600)`
 * → 30.dgwsp by default, 50.dgwsp when screenWidthDp >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dgwspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgwspQualifier].
 * PT Variante em Pixel (Float) de [dgwspQualifier].
 */
@Composable
fun Number.dgwspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspQualifier(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgwspQualifier].
 * PT Variante em Pixel (Float) de [dgwspQualifier].
 */
@Composable
fun TextUnit.dgwspQualifierPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspQualifierPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgwspQualifierPlain].
 * PT Variante em Pixel (Float) de [dgwspQualifierPlain].
 */
@Composable
fun TextUnit.dgwspQualifierPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgwspQualifierPlainPx.toPx() }
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions for Sp.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.dgsspScreen(50, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)`
 * → 30.dgssp by default, 50.dgssp on television with sw >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dgsspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgsspScreen].
 * PT Variante em Pixel (Float) de [dgsspScreen].
 */
@Composable
fun Number.dgsspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgsspScreen].
 * PT Variante em Pixel (Float) de [dgsspScreen].
 */
@Composable
fun TextUnit.dgsspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgsspScreenPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgsspScreenPlain].
 * PT Variante em Pixel (Float) de [dgsspScreenPlain].
 */
@Composable
fun TextUnit.dgsspScreenPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgsspScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.dghspScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)`
 * → 30.dghsp by default, 50.dghsp on television with height >= 800.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dghspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dghspScreen].
 * PT Variante em Pixel (Float) de [dghspScreen].
 */
@Composable
fun Number.dghspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dghspScreen].
 * PT Variante em Pixel (Float) de [dghspScreen].
 */
@Composable
fun TextUnit.dghspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dghspScreenPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dghspScreenPlain].
 * PT Variante em Pixel (Float) de [dghspScreenPlain].
 */
@Composable
fun TextUnit.dghspScreenPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dghspScreenPlainPx.toPx() }
    }
}

/**
 * EN
 * Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 * Usage example: `30.dgwspScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)`
 * → 30.dgwsp by default, 50.dgwsp on television with width >= 600.
 *
 * PT
 * Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Number.dgwspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQualifier, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
/**
 * EN Pixel (Float) variant of [dgwspScreen].
 * PT Variante em Pixel (Float) de [dgwspScreen].
 */
@Composable
fun Number.dgwspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQualifier,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspScreen(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, baseValue, configuration,
        resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Pixel (Float) variant of [dgwspScreen].
 * PT Variante em Pixel (Float) de [dgwspScreen].
 */
@Composable
fun TextUnit.dgwspScreenPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = resQ,
        inverter = Inverter.DEFAULT,
        applyAspectRatio = applyAspectRatio,
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )
    val dgsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
    return rememberDiagonalSpPx(
        cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
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
fun TextUnit.dgwspScreenPlain(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSp(
            cacheKey, spStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        this
    }
}

/**
 * EN Pixel (Float) variant of [dgwspScreenPlain].
 * PT Variante em Pixel (Float) de [dgwspScreenPlain].
 */
@Composable
fun TextUnit.dgwspScreenPlainPx(
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
            calcType = DimenCache.CalcType.DIAGONAL,
            qualifier = resQ,
            inverter = Inverter.DEFAULT,
            applyAspectRatio = applyAspectRatio,
            valueType = valueType,
            customSensitivityK = customSensitivityK
        )
        val dgsspPxStamp =
            pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)
        rememberDiagonalSpPx(
            cacheKey, dgsspPxStamp, androidContext, density, baseValue, configuration,
            resQ, Inverter.DEFAULT, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
        )
    } else {
        density.run { this@dgwspScreenPlainPx.toPx() }
    }
}
