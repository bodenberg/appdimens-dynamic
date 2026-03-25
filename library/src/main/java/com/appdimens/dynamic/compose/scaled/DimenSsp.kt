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
package com.appdimens.dynamic.compose

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.LocalUiModeType

// EN Rotation facilitator extensions for Compose.
// PT Extensões facilitadoras para rotação em Compose.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 */
@Composable
fun Int.sspRotate(
    rotationValue: Int,
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
        rotationValue.toDynamicScaledSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Int.hspRotate(
    rotationValue: Int,
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
        rotationValue.toDynamicScaledSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Int.wspRotate(
    rotationValue: Int,
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
        rotationValue.toDynamicScaledSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
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
fun Int.sspMode(
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Int.hspMode(
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSp(finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Int.wspMode(
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): TextUnit {
    val currentUiModeType = LocalUiModeType.current
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSp(finalQualifierResolver ?: DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Standard Compose extensions for quick dynamic scaling.
// PT Extensões Compose padrão para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.ssp`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.ssp`.
 */
@get:Composable
val Int.ssp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true)
@get:Composable
val Int.sspPxa: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.sspPxi: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxia: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**, but
 * without respecting the system font scale.
 * Usage example: `16.nem`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**,
 * mas sem respeitar a escala de fonte do sistema.
 * Exemplo de uso: `16.nem`.
 */
@get:Composable
val Int.nem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.nema: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.nemi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.nemia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.nemPx: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false)
@get:Composable
val Int.nemPxa: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.nemPxi: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.nemPxia: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspPh`.
 */
@get:Composable
val Int.sspPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
@get:Composable
val Int.sspPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.sspPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspLh`.
 */
@get:Composable
val Int.sspLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
@get:Composable
val Int.sspPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.sspPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspPw`.
 */
@get:Composable
val Int.sspPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
@get:Composable
val Int.sspPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.sspPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspLw`.
 */
@get:Composable
val Int.sspLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.sspLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.sspLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.sspLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.sspPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
@get:Composable
val Int.sspPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.sspPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.sspPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hsp`.
 */
@get:Composable
val Int.hsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true)
@get:Composable
val Int.hspPxa: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.hspPxi: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.hspPxia: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * without respecting the system font scale.
 * Usage example: `32.hem`.
 */
@get:Composable
val Int.hem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hema: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hemi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hemia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hemPx: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false)
@get:Composable
val Int.hemPxa: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.hemPxi: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.hemPxia: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspLw`.
 */
@get:Composable
val Int.hspLw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspLwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspLwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspLwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPxLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
@get:Composable
val Int.hspPxaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Int.hspPxiLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Int.hspPxiaLw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspPw`.
 */
@get:Composable
val Int.hspPw: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.hspPwa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.hspPwi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.hspPwia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.hspPxPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
@get:Composable
val Int.hspPxaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Int.hspPxiPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Int.hspPxiaPw: Float get() = this.toDynamicScaledPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wsp`.
 */
@get:Composable
val Int.wsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspa: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true)
@get:Composable
val Int.wspPxa: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Int.wspPxi: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Int.wspPxia: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale.
 * Usage example: `100.wem`.
 */
@get:Composable
val Int.wem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wema: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wemi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wemia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wemPx: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false)
@get:Composable
val Int.wemPxa: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Int.wemPxi: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Int.wemPxia: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspLh`.
 */
@get:Composable
val Int.wspLh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspLha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspLhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspLhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPxLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
@get:Composable
val Int.wspPxaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Int.wspPxiLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Int.wspPxiaLh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspPh`.
 */
@get:Composable
val Int.wspPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wspPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wspPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wspPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wspPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
@get:Composable
val Int.wspPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.wspPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.wspPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale, and
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemPh`.
 */
@get:Composable
val Int.wemPh: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Int.wemPha: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Int.wemPhi: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Int.wemPhia: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Int.wemPxPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
@get:Composable
val Int.wemPxaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Int.wemPxiPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Int.wemPxiaPh: Float get() = this.toDynamicScaledPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN Dynamic scaling function for Sp (Resource-based, reuses DP XML resources).
// PT Função de dimensionamento dinâmico para Sp (baseada em recursos, reutiliza os recursos XML de DP).

/**
 * EN
 * Converts an Int (the base Sp value) into a dynamically scaled TextUnit (Sp).
 * This function reuses the existing DP XML resources (`_Nsdp`, `_Nhdp`, `_Nwdp`) as the
 * dimension values, then converts them to Sp. This means the scaling system is the same as
 * the DP system — the raw dp value from the resource is used directly as an sp number.
 *
 * 1. Constructs the resource name based on the value and the qualifier (e.g., `_16sdp`).
 * 2. Loads the dimension value in dp from that resource.
 * 3. Converts it to Sp, optionally stripping the system font scale.
 *
 * PT
 * Converte um Int (o valor Sp base) em um TextUnit (Sp) escalado dinamicamente.
 * Esta função reutiliza os recursos XML de DP existentes (`_Nsdp`, `_Nhdp`, `_Nwdp`) como
 * valores de dimensão, convertendo-os para Sp. O sistema de escalonamento é o mesmo do DP —
 * o valor dp bruto do recurso é usado diretamente como número sp.
 *
 * 1. Constrói o nome do recurso baseado no valor e no qualificador (ex: `_16sdp`).
 * 2. Carrega o valor de dimensão em dp daquele recurso.
 * 3. Converte para Sp, opcionalmente removendo a escala de fonte do sistema.
 *
 * @param qualifier The screen qualifier used to determine the resource name (sdp, hdp, wdp).
 * @param fontScale Whether to respect the user's font scale setting.
 * @param inverter Inverter to swap qualifier when orientation changes.
 * @return The TextUnit (Sp) value loaded from the resource, or the base sp value as fallback.
 */
@Composable
fun Int.toDynamicScaledSp(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null,
    enableCache: Boolean = true
): TextUnit {
    require(this in 1..1024) { "Value must be between 1 and 1024. Current: $this" }

    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    return remember(
        this, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext, density
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this,
            screenWidthDp = configuration.screenWidthDp,
            screenHeightDp = configuration.screenHeightDp,
            smallestWidthDp = configuration.smallestScreenWidthDp,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE,
            customSensitivityK = customSensitivityK
        )

        if (!enableCache) {
            val scaledValue = calculateSspValueCompose(this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
            return@remember if (fontScale) scaledValue.sp else (scaledValue / density.fontScale).sp
        }

        DimenCache.getOrPut(cacheKey, androidContext) {
            val raw = calculateSspValueCompose(this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
            if (fontScale) raw else (raw / density.fontScale)
        }.sp
    }
}

private fun calculateSspValueCompose(
    baseValue: Int,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    configuration: Configuration
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var actualQualifier = qualifier
    when (inverter) {
        Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
        Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
        Inverter.LH_TO_PW -> if (isPortrait  && qualifier == DpQualifier.HEIGHT)      actualQualifier = DpQualifier.WIDTH
        Inverter.LW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.WIDTH)       actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.SW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_PW -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.DEFAULT  -> {}
    }

    val isMultiWindow = if (ignoreMultiWindows) {
        val swDp = configuration.smallestScreenWidthDp.toFloat()
        val cwDp = configuration.screenWidthDp.toFloat()
        val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
        val isSmallDiff = (swDp - cwDp) < (swDp * 0.1f)
        isLayoutSplit && !isSmallDiff
    } else false

    return if (isMultiWindow) {
        baseValue.toFloat()
    } else {
        val isDefaultSw = (qualifier == DpQualifier.SMALL_WIDTH) && (inverter == Inverter.DEFAULT)
        if (isDefaultSw && customSensitivityK == null) {
            DimenCache.calculateRawScaling(baseValue, applyAspectRatio, null)
        } else {
            val screenDim = when (actualQualifier) {
                DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
                DpQualifier.WIDTH  -> configuration.screenWidthDp.toFloat()
                else               -> configuration.smallestScreenWidthDp.toFloat()
            }
            val scale = screenDim * 0.0033333334f
            if (applyAspectRatio) {
                val diff = screenDim - 300f
                val adj = (customSensitivityK ?: 0.0026666667f) * DimenCache.currentLogNormalizedAr
                baseValue.toFloat() * (1.0f + diff * (0.0033333334f + adj))
            } else {
                baseValue.toFloat() * scale
            }
        }
    }
}

/**
 * EN Converts an Int (base Sp) to a dynamically scaled Float (in pixels).
 * PT Converte um Int (base Sp) para um Float (em pixels) escalado dinamicamente.
 */
@Composable
fun Int.toDynamicScaledPx(
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current

    return remember(
        this, qualifier, fontScale, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK,
        configuration.orientation, configuration.screenWidthDp, configuration.screenHeightDp, 
        configuration.smallestScreenWidthDp, androidContext, density
    ) {
        val cacheKey = DimenCache.buildKey(
            baseValue = this,
            screenWidthDp = configuration.screenWidthDp,
            screenHeightDp = configuration.screenHeightDp,
            smallestWidthDp = configuration.smallestScreenWidthDp,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE,
            ignoreMultiWindows = ignoreMultiWindows,
            calcType = DimenCache.CalcType.SCALED,
            qualifier = qualifier,
            inverter = inverter,
            applyAspectRatio = applyAspectRatio,
            valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE,
            customSensitivityK = customSensitivityK
        )

        if (!enableCache) {
            val scaledVal = calculateSspValueCompose(this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
            val spValue = if (fontScale) scaledVal.sp else (scaledVal / density.fontScale).sp
            return@remember density.run { spValue.toPx() }
        }

        DimenCache.getOrPut(cacheKey, androidContext) {
            val scaledVal = calculateSspValueCompose(this, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
            val spValue = if (fontScale) scaledVal.sp else (scaledVal / density.fontScale).sp
            density.run { spValue.toPx() }
        }
    }
}
