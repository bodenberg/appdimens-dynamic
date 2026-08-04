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
package com.appdimens.dynamic.compose.fit

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
fun Number.ftsspRotate(
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
        rotationValue.toDynamicFitSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.fthspRotate(
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
        rotationValue.toDynamicFitSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.ftwspRotate(
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
        rotationValue.toDynamicFitSp(finalQualifierResolver, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
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
fun Number.ftsspMode(
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
        modeValue.toDynamicFitSp(finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@Composable
fun Number.fthspMode(
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
        modeValue.toDynamicFitSp(finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@Composable
fun Number.ftwspMode(
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
        modeValue.toDynamicFitSp(finalQualifierResolver ?: DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Standard Compose extensions for quick dynamic scaling.
// PT Extensões Compose padrão para dimensionamento dinâmico rápido.

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.ftssp`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.ftssp`.
 */
@get:Composable
val Number.ftssp: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsspa: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsspi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsspia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsspPx: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true)
@get:Composable
val Number.ftsspPxa: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.ftsspPxi: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.ftsspPxia: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**, but
 * without respecting the system font scale.
 * Usage example: `16.ftsem`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**,
 * mas ftsem respeitar a escala de fonte do sistema.
 * Exemplo de uso: `16.ftsem`.
 */
@get:Composable
val Number.ftsem: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsema: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsemi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsemia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsemPx: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = false)
@get:Composable
val Number.ftsemPxa: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.ftsemPxi: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.ftsemPxia: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.ftsspPh`.
 */
@get:Composable
val Number.ftsspPh: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsspPha: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsspPhi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsspPhia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsspPxPh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
@get:Composable
val Number.ftsspPxaPh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.ftsspPxiPh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.ftsspPxiaPh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.ftsspLh`.
 */
@get:Composable
val Number.ftsspLh: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsspLha: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsspLhi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsspLhia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsspPxLh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
@get:Composable
val Number.ftsspPxaLh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.ftsspPxiLh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.ftsspPxiaLh: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.ftsspPw`.
 */
@get:Composable
val Number.ftsspPw: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsspPwa: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsspPwi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsspPwia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsspPxPw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
@get:Composable
val Number.ftsspPxaPw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.ftsspPxiPw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.ftsspPxiaPw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.ftsspLw`.
 */
@get:Composable
val Number.ftsspLw: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftsspLwa: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftsspLwi: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftsspLwia: TextUnit get() = this.toDynamicFitSp(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftsspPxLw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
@get:Composable
val Number.ftsspPxaLw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.ftsspPxiLw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.ftsspPxiaLw: Float get() = this.toDynamicFitPx(DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.fthsp`.
 */
@get:Composable
val Number.fthsp: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.fthspa: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.fthspi: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.fthspia: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.fthspPx: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true)
@get:Composable
val Number.fthspPxa: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.fthspPxi: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.fthspPxia: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * without respecting the system font scale.
 * Usage example: `32.fthem`.
 */
@get:Composable
val Number.fthem: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.fthema: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.fthemi: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.fthemia: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.fthemPx: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = false)
@get:Composable
val Number.fthemPxa: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.fthemPxi: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.fthemPxia: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.fthspLw`.
 */
@get:Composable
val Number.fthspLw: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.fthspLwa: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.fthspLwi: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.fthspLwia: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.fthspPxLw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
@get:Composable
val Number.fthspPxaLw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
@get:Composable
val Number.fthspPxiLw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
@get:Composable
val Number.fthspPxiaLw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.fthspPw`.
 */
@get:Composable
val Number.fthspPw: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.fthspPwa: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.fthspPwi: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.fthspPwia: TextUnit get() = this.toDynamicFitSp(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.fthspPxPw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
@get:Composable
val Number.fthspPxaPw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
@get:Composable
val Number.fthspPxiPw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
@get:Composable
val Number.fthspPxiaPw: Float get() = this.toDynamicFitPx(DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.ftwsp`.
 */
@get:Composable
val Number.ftwsp: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftwspa: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftwspi: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftwspia: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftwspPx: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true)
@get:Composable
val Number.ftwspPxa: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
@get:Composable
val Number.ftwspPxi: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
@get:Composable
val Number.ftwspPxia: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale.
 * Usage example: `100.ftwem`.
 */
@get:Composable
val Number.ftwem: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftwema: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftwemi: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftwemia: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftwemPx: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false)
@get:Composable
val Number.ftwemPxa: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
@get:Composable
val Number.ftwemPxi: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
@get:Composable
val Number.ftwemPxia: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.ftwspLh`.
 */
@get:Composable
val Number.ftwspLh: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftwspLha: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftwspLhi: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftwspLhia: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftwspPxLh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
@get:Composable
val Number.ftwspPxaLh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
@get:Composable
val Number.ftwspPxiLh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
@get:Composable
val Number.ftwspPxiaLh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.ftwspPh`.
 */
@get:Composable
val Number.ftwspPh: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftwspPha: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftwspPhi: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftwspPhia: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftwspPxPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.ftwspPxaPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.ftwspPxiPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.ftwspPxiaPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * without respecting the system font scale, and
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.ftwemPh`.
 */
@get:Composable
val Number.ftwemPh: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
/** a variant explicitly with applyAspectRatio */
@get:Composable
val Number.ftwemPha: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
/** i variant explicitly with ignoreMultiWindows */
@get:Composable
val Number.ftwemPhi: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
/** ia variant explicitly with both */
@get:Composable
val Number.ftwemPhia: TextUnit get() = this.toDynamicFitSp(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

@get:Composable
val Number.ftwemPxPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
@get:Composable
val Number.ftwemPxaPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
@get:Composable
val Number.ftwemPxiPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
@get:Composable
val Number.ftwemPxiaPh: Float get() = this.toDynamicFitPx(DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled [TextUnit] (Sp) for Jetpack Compose.
 *
 * Scaling logic:
 * 1. Builds a 64-bit packed cache key.
 * 2. If [fontScale] is `true`, the result respects the system font size setting.
 * 3. If [fontScale] is `false` (e.g. via [.ftsem]), the system font scale is stripped.
 * 4. Checks [DimenCache] globally.
 *
 * PT
 * Converte um [Number] (valor base) em um [TextUnit] (Sp) dinamicamente escalado para Compose.
 *
 * Lógica de escalonamento:
 * 1. Constrói uma chave de cache de 64 bits.
 * 2. Se [fontScale] for `true`, o resultado respeita a configuração de tamanho de fonte do sistema.
 * 3. Se [fontScale] for `false` (ex: via [.ftsem]), a escala de fonte do sistema é removida.
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
fun Number.toDynamicFitSp(
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
        calcType = DimenCache.CalcType.FIT,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val spStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberFitSp(
        cacheKey, spStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}

/**
 * EN Internal logic to calculate the scaled SP value before density/font-scale adjustment.
 * PT Lógica interna para calcular o valor SP escalado antes do ajuste de densidade/fonte.
 */
internal fun calculateFitSpValueCompose(
    baseValue: Float,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?,
    configuration: Configuration
): Float = calculateFitDpCompose(baseValue, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)


/**
 * EN [remember]s the cached [TextUnit] for [toDynamicFitSp] using [cacheKey] and [spStamp].
 * PT [remember] do [TextUnit] em cache para [toDynamicFitSp] usando [cacheKey] e [spStamp].
 */
@Composable
internal fun rememberFitSp(
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
        val raw = calculateFitSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        if (fontScale) raw else (raw / density.fontScale)
    }.sp
}

/**
 * EN [remember]s the cached px [Float] for [toDynamicFitPx] using [cacheKey] and [ftsspPxStamp].
 * PT [remember] do [Float] em px para [toDynamicFitPx] usando [cacheKey] e [ftsspPxStamp].
 */
@Composable
internal fun rememberFitSpPx(
    cacheKey: Long,
    ftsspPxStamp: Long,
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
): Float = remember(cacheKey, ftsspPxStamp) {
    DimenCache.getOrPut(cacheKey, androidContext) {
        val scaledVal = calculateFitSpValueCompose(baseValue, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK, configuration)
        val spValue = if (fontScale) scaledVal.sp else (scaledVal / density.fontScale).sp
        density.run { spValue.toPx() }
    }
}

/**
 * EN
 * Converts a [Number] (base value) into a dynamically scaled pixel [Float] for Compose.
 *
 * Similar to [toDynamicFitSp], but the result is multiplied by density to return
 * raw pixels. Often used for direct Canvas operations or custom Modifier logic.
 *
 * PT
 * Converte um [Number] (valor base) em um [Float] em pixels dinamicamente escalado para Compose.
 *
 * Similar a [toDynamicFitSp], mas o resultado é multiplicado pela densidade para retornar
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
fun Number.toDynamicFitPx(
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
        calcType = DimenCache.CalcType.FIT,
        qualifier = qualifier,
        inverter = inverter,
        applyAspectRatio = applyAspectRatio,
        valueType = if (fontScale) DimenCache.ValueType.SP_PX_WITH_SCALE else DimenCache.ValueType.SP_PX_NO_SCALE,
        customSensitivityK = customSensitivityK
    )
    val ftsspPxStamp = pxRememberStamp(layoutRememberStamp(configuration, androidContext), density)

    return rememberFitSpPx(
        cacheKey, ftsspPxStamp, androidContext, density, this.toFloat(), configuration,
        qualifier, inverter, fontScale, ignoreMultiWindows, applyAspectRatio, customSensitivityK
    )
}