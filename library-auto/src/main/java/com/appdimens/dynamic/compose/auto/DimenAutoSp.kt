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
package com.appdimens.dynamic.compose.auto

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenCalculationPlumbing
import com.appdimens.dynamic.core.LocalUiModeType
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp

// EN Rotation facilitator extensions for Compose.
// PT Extensões facilitadoras para rotação em Compose.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 */
@Composable
fun Number.asspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicAutoSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.ahspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicAutoSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.awspRotate(
    rotationValue: Number,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicAutoSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType facilitator extensions for Compose.
// PT Extensões facilitadoras para UiModeType em Compose.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 */
@Composable
fun Number.asspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicAutoSp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.ahspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicAutoSp(finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.awspMode(
    modeValue: Number,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicAutoSp(finalQualifierResolver ?: DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Standard Compose extensions for quick dynamic scaling.
// PT Extensões Compose padrão para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.assp`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.assp`.
 */
@get:Composable
val Number.assp: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asspa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asspi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asspia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asspPx: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true)
@get:Composable
val Number.asspPxa: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.asspPxi: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.asspPxia: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**, but
 * without respecting the system font scale.
 * Usage example: `16.asem`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**,
 * mas asem respeitar a escala de fonte do sistema.
 * Exemplo de uso: `16.asem`.
 */
@get:Composable
val Number.asem: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asema: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asemi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asemia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asemPx: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = false)
@get:Composable
val Number.asemPxa: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.asemPxi: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.asemPxia: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.asspPh`.
 */
@get:Composable
val Number.asspPh: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asspPha: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asspPhi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asspPhia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asspPxPh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
@get:Composable
val Number.asspPxaPh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.asspPxiPh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.asspPxiaPh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.asspLh`.
 */
@get:Composable
val Number.asspLh: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asspLha: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asspLhi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asspLhia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asspPxLh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
@get:Composable
val Number.asspPxaLh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.asspPxiLh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.asspPxiaLh: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.asspPw`.
 */
@get:Composable
val Number.asspPw: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asspPwa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asspPwi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asspPwia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asspPxPw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
@get:Composable
val Number.asspPxaPw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.asspPxiPw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.asspPxiaPw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.asspLw`.
 */
@get:Composable
val Number.asspLw: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.asspLwa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.asspLwi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.asspLwia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.asspPxLw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
@get:Composable
val Number.asspPxaLw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.asspPxiLw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.asspPxiaLw: Float get() = this.toDynamicAutoPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.ahsp`.
 */
@get:Composable
val Number.ahsp: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ahspa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ahspi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ahspia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ahspPx: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true)
@get:Composable
val Number.ahspPxa: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.ahspPxi: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.ahspPxia: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * without respecting the system font scale.
 * Usage example: `32.ahem`.
 */
@get:Composable
val Number.ahem: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ahema: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ahemi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ahemia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ahemPx: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = false)
@get:Composable
val Number.ahemPxa: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.ahemPxi: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.ahemPxia: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.ahspLw`.
 */
@get:Composable
val Number.ahspLw: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ahspLwa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ahspLwi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ahspLwia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ahspPxLw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
@get:Composable
val Number.ahspPxaLw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.ahspPxiLw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.ahspPxiaLw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.ahspPw`.
 */
@get:Composable
val Number.ahspPw: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ahspPwa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ahspPwi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ahspPwia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ahspPxPw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
@get:Composable
val Number.ahspPxaPw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.ahspPxiPw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.ahspPxiaPw: Float get() = this.toDynamicAutoPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.awsp`.
 */
@get:Composable
val Number.awsp: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.awspa: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.awspi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.awspia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.awspPx: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true)
@get:Composable
val Number.awspPxa: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.awspPxi: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.awspPxia: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale.
 * Usage example: `100.awem`.
 */
@get:Composable
val Number.awem: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.awema: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.awemi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.awemia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.awemPx: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false)
@get:Composable
val Number.awemPxa: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.awemPxi: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.awemPxia: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.awspLh`.
 */
@get:Composable
val Number.awspLh: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.awspLha: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.awspLhi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.awspLhia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.awspPxLh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
@get:Composable
val Number.awspPxaLh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.awspPxiLh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.awspPxiaLh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.awspPh`.
 */
@get:Composable
val Number.awspPh: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.awspPha: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.awspPhi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.awspPhia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.awspPxPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.awspPxaPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.awspPxiPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.awspPxiaPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale, and
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.awemPh`.
 */
@get:Composable
val Number.awemPh: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.awemPha: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.awemPhi: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.awemPhia: TextUnit get() = this.toDynamicAutoSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.awemPxPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.awemPxaPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.awemPxiPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.awemPxiaPh: Float get() = this.toDynamicAutoPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled [TextUnit] (Sp) for Jetpack Compose.
 *
 * Scaling logic:
 * 1. Builds a 64-bit packed cache key.
 * 2. If [fontScale] is `true`, the result respects the system font size setting.
 * 3. If [fontScale] is `false` (e.g. via [.asem]), the system font scale is stripped.
 * 4. Checks [DimenCache] globally.
 *
 * PT
 * Converte um [Number] (valor base) em um [TextUnit] (Sp) dinamicamente escalado para Compose.
 *
 * Lógica de escalonamento:
 * 1. Constrói uma chave de cache de 64 bits.
 * 2. Se [fontScale] for `true`, o resultado respeita a configuração de tamanho de fonte do sistema.
 * 3. Se [fontScale] for `false` (ex: via [.asem]), a escala de fonte do sistema é removida.
 * 4. Consulta o [DimenCache] globalmente.
 *
 * @param qualifier    Screen dimension qualifier.
 * @param fontScale    Whether to respect the user's system font scale.
 * @param inverter     Orientation-based dimension swap rule.
 * @param ignoreMultiWindows If `true`, returns base value unscaled when in split-screen.
 * @param applyAspectRatio   If `true`, applies the aspect-ratio multiplier.
 * @param customSensitivityK Custom AR sensitivity constant.
 * @return Dynamically scaled [TextUnit] value.
 */
@Composable
fun Number.toDynamicAutoSp(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.AUTO,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberAutoSp(
        cacheKey, spStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Internal logic to calculate the scaled SP value before density/font-scale adjustment.
 * PT Lógica interna para calcular o valor SP escalado antes do ajuste de densidade/fonte.
 */
internal fun calculateAutoSpValueCompose(
    baseValue: Float,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    configuration: Configuration
): Float = calculateAutoDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)


/**
 * EN [remember]s the cached [TextUnit] for [toDynamicAutoSp] using [cacheKey] and [spStamp].
 * PT [remember] do [TextUnit] em cache para [toDynamicAutoSp] usando [cacheKey] e [spStamp].
 */
@Composable
internal fun rememberAutoSp(
    cacheKey: Long,
    spStamp: Long,
    androidContext: android.content.Context,
    density: Density,
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    fontScale: Boolean,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
): TextUnit = remember(cacheKey, spStamp) {
    DimenCache.getOrPut(cacheKey, androidContext) {
        val raw = calculateAutoSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        if (fontScale) raw else (raw / density.fontScale)
    }.sp
}

/**
 * EN [remember]s the cached px [Float] for [toDynamicAutoPx] using [cacheKey] and [asspPxStamp].
 * PT [remember] do [Float] em px para [toDynamicAutoPx] usando [cacheKey] e [asspPxStamp].
 */
@Composable
internal fun rememberAutoSpPx(
    cacheKey: Long,
    asspPxStamp: Long,
    androidContext: android.content.Context,
    density: Density,
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    fontScale: Boolean,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
): Float = remember(cacheKey, asspPxStamp) {
    DimenCache.getOrPut(cacheKey, androidContext) {
        val scaledVal = calculateAutoSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        val spValue = if (fontScale) scaledVal.sp else (scaledVal / density.fontScale).sp
        density.run { spValue.toPx() }
    }
}

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled pixel [Float] for Compose.
 *
 * Similar to [toDynamicAutoSp], but the result is multiplied by density to return
 * raw pixels. Often used for direct Canvas operations or custom Modifier logic.
 *
 * PT
 * Converte um [Number] (valor base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Similar a [toDynamicAutoSp], mas o resultado é multiplicado pela densidade para retornar
 * pixels brutos. Frequentemente usado para operações diretas de Canvas ou lógica de Modifier.
 *
 * @param qualifier    Screen dimension qualifier.
 * @param fontScale    Whether to respect the user's system font scale.
 * @param inverter     Orientation-based swap rule.
 * @param ignoreMultiWindows If `true`, returns base value unscaled in pixels in split-screen.
 * @param applyAspectRatio   If `true`, applies the aspect-ratio multiplier.
 * @param customSensitivityK Custom AR sensitivity constant.
 * @return Dynamically scaled pixel value.
 */
@Composable
fun Number.toDynamicAutoPx(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    val cacheKey = DimenCache.buildKey(
        baseValue = this.toFloat(),
        isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
        ignoreMultiWindows = ignoreMultiWindows,
        calcType = DimenCache.CalcType.AUTO,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val asspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberAutoSpPx(
        cacheKey, asspPxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}