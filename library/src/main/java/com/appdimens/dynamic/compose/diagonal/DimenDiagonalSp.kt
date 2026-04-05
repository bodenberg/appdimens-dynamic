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
fun Number.sspRotate(
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
        rotationValue.toDynamicDiagonalSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.hspRotate(
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
        rotationValue.toDynamicDiagonalSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.wspRotate(
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
        rotationValue.toDynamicDiagonalSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
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
fun Number.sspMode(
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
        modeValue.toDynamicDiagonalSp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.hspMode(
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
        modeValue.toDynamicDiagonalSp(finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.wspMode(
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
        modeValue.toDynamicDiagonalSp(finalQualifierResolver ?: DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Standard Compose extensions for quick dynamic scaling.
// PT Extensões Compose padrão para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.dgssp`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.dgssp`.
 */
@get:Composable
val Number.dgssp: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sspa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sspi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sspia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sspPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true)
@get:Composable
val Number.sspPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.sspPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.sspPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**, but
 * without respecting the system font scale.
 * Usage example: `16.dgsem`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**,
 * mas sem respeitar a escala de fonte do sistema.
 * Exemplo de uso: `16.dgsem`.
 */
@get:Composable
val Number.dgsem: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.dgsema: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.dgsemi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.dgsemia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.dgsemPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = false)
@get:Composable
val Number.dgsemPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.dgsemPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.dgsemPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspPh`.
 */
@get:Composable
val Number.sspPh: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sspPha: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sspPhi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sspPhia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sspPxPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
@get:Composable
val Number.sspPxaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.sspPxiPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.sspPxiaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspLh`.
 */
@get:Composable
val Number.sspLh: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sspLha: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sspLhi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sspLhia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sspPxLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
@get:Composable
val Number.sspPxaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.sspPxiLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.sspPxiaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspPw`.
 */
@get:Composable
val Number.sspPw: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sspPwa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sspPwi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sspPwia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sspPxPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
@get:Composable
val Number.sspPxaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.sspPxiPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.sspPxiaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspLw`.
 */
@get:Composable
val Number.sspLw: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.sspLwa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.sspLwi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.sspLwia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.sspPxLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
@get:Composable
val Number.sspPxaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.sspPxiLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.sspPxiaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.dghsp`.
 */
@get:Composable
val Number.dghsp: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hspa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hspi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hspia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hspPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true)
@get:Composable
val Number.hspPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.hspPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.hspPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * without respecting the system font scale.
 * Usage example: `32.dghem`.
 */
@get:Composable
val Number.dghem: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hema: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hemi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hemia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hemPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = false)
@get:Composable
val Number.hemPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.hemPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.hemPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspLw`.
 */
@get:Composable
val Number.hspLw: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hspLwa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hspLwi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hspLwia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hspPxLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
@get:Composable
val Number.hspPxaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.hspPxiLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.hspPxiaLw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspPw`.
 */
@get:Composable
val Number.hspPw: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.hspPwa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.hspPwi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.hspPwia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.hspPxPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
@get:Composable
val Number.hspPxaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.hspPxiPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.hspPxiaPw: Float get() = this.toDynamicDiagonalPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.dgwsp`.
 */
@get:Composable
val Number.dgwsp: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wspa: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wspi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wspia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wspPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true)
@get:Composable
val Number.wspPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.wspPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.wspPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale.
 * Usage example: `100.dgwem`.
 */
@get:Composable
val Number.dgwem: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wema: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wemi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wemia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wemPx: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false)
@get:Composable
val Number.wemPxa: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.wemPxi: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.wemPxia: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspLh`.
 */
@get:Composable
val Number.wspLh: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wspLha: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wspLhi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wspLhia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wspPxLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
@get:Composable
val Number.wspPxaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.wspPxiLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.wspPxiaLh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspPh`.
 */
@get:Composable
val Number.wspPh: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wspPha: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wspPhi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wspPhia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wspPxPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.wspPxaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.wspPxiPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.wspPxiaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale, and
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemPh`.
 */
@get:Composable
val Number.wemPh: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.wemPha: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.wemPhi: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.wemPhia: TextUnit get() = this.toDynamicDiagonalSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.wemPxPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.wemPxaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.wemPxiPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.wemPxiaPh: Float get() = this.toDynamicDiagonalPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled [TextUnit] (Sp) for Jetpack Compose.
 *
 * Scaling logic:
 * 1. Builds a 64-bit packed cache key.
 * 2. If [fontScale] is `true`, the result respects the system font size setting.
 * 3. If [fontScale] is `false` (e.g. via [.dgsem]), the system font scale is stripped.
 * 4. Checks [DimenCache] globally.
 *
 * PT
 * Converte um [Number] (valor base) em um [TextUnit] (Sp) dinamicamente escalado para Compose.
 *
 * Lógica de escalonamento:
 * 1. Constrói uma chave de cache de 64 bits.
 * 2. Se [fontScale] for `true`, o resultado respeita a configuração de tamanho de fonte do sistema.
 * 3. Se [fontScale] for `false` (ex: via [.dgsem]), a escala de fonte do sistema é removida.
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
fun Number.toDynamicDiagonalSp(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberDiagonalSp(
        cacheKey, spStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Internal logic to calculate the scaled SP value before density/font-scale adjustment.
 * PT Lógica interna para calcular o valor SP escalado antes do ajuste de densidade/fonte.
 */
internal fun calculateDiagonalSpValueCompose(
    baseValue: Float,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    configuration: Configuration
): Float = calculateDiagonalDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)


/**
 * EN [remember]s the cached [TextUnit] for [toDynamicDiagonalSp] using [cacheKey] and [spStamp].
 * PT [remember] do [TextUnit] em cache para [toDynamicDiagonalSp] usando [cacheKey] e [spStamp].
 */
@Composable
internal fun rememberDiagonalSp(
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
        val raw = calculateDiagonalSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        if (fontScale) raw else (raw / density.fontScale)
    }.sp
}

/**
 * EN [remember]s the cached px [Float] for [toDynamicDiagonalPx] using [cacheKey] and [sspPxStamp].
 * PT [remember] do [Float] em px para [toDynamicDiagonalPx] usando [cacheKey] e [sspPxStamp].
 */
@Composable
internal fun rememberDiagonalSpPx(
    cacheKey: Long,
    sspPxStamp: Long,
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
): Float = remember(cacheKey, sspPxStamp) {
    DimenCache.getOrPut(cacheKey, androidContext) {
        val scaledVal = calculateDiagonalSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        val spValue = if (fontScale) scaledVal.sp else (scaledVal / density.fontScale).sp
        density.run { spValue.toPx() }
    }
}

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled pixel [Float] for Compose.
 *
 * Similar to [toDynamicDiagonalSp], but the result is multiplied by density to return
 * raw pixels. Often used for direct Canvas operations or custom Modifier logic.
 *
 * PT
 * Converte um [Number] (valor base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Similar a [toDynamicDiagonalSp], mas o resultado é multiplicado pela densidade para retornar
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
fun Number.toDynamicDiagonalPx(
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
        calcType = DimenCache.CalcType.DIAGONAL,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val sspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberDiagonalSpPx(
        cacheKey, sspPxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}