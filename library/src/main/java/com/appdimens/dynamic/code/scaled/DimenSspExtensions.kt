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

import android.content.Context
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import android.util.TypedValue
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import kotlin.math.max
import kotlin.math.min

// EN Rotation facilitator extensions for Sp (non-Compose).
// PT Extensões facilitadoras para rotação (Sp) (non-Compose).

private const val BASE_RATIO_STEP = 300f

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 */
@JvmOverloads
fun Int.sspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = context.resources.configuration
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledSpPx(context, finalQualifierResolver, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Int.hspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = context.resources.configuration
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledSpPx(context, finalQualifierResolver, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Int.wspRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val configuration = context.resources.configuration
    val isTargetOrientation = when (orientation) {
        Orientation.LANDSCAPE -> configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Orientation.PORTRAIT -> configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        else -> false
    }
    return if (isTargetOrientation) {
        rotationValue.toDynamicScaledSpPx(context, finalQualifierResolver, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType facilitator extensions for Sp (non-Compose).
// PT Extensões facilitadoras para UiModeType (Sp) (non-Compose).

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 */
@JvmOverloads
fun Int.sspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Int.hspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Int.wspMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    fontScale: Boolean = true,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}


// EN Standard Android extensions for quick dynamic text scaling (Sp) (View-based).
// PT Extensões Android padrão para escalonamento dinâmico rápido de texto (Sp) (baseado em Views).

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.ssp(context)`.
 */
fun Int.ssp(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true)
fun Int.sspa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, applyAspectRatio = true)
fun Int.sspi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true)
fun Int.sspia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspPh(context)`.
 */
fun Int.sspPh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH)
fun Int.sspPha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
fun Int.sspPhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
fun Int.sspPhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sspLh(context)`.
 */
fun Int.sspLh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH)
fun Int.sspLha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
fun Int.sspLhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
fun Int.sspLhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspPw(context)`.
 */
fun Int.sspPw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW)
fun Int.sspPwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
fun Int.sspPwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
fun Int.sspPwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sspLw(context)`.
 */
fun Int.sspLw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW)
fun Int.sspLwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
fun Int.sspLwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
fun Int.sspLwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = true, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hsp(context)`.
 */
fun Int.hsp(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true)
fun Int.hspa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, applyAspectRatio = true)
fun Int.hspi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true)
fun Int.hspia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspLw(context)`.
 */
fun Int.hspLw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW)
fun Int.hspLwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
fun Int.hspLwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
fun Int.hspLwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hspPw(context)`.
 */
fun Int.hspPw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW)
fun Int.hspPwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
fun Int.hspPwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
fun Int.hspPwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = true, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wsp(context)`.
 */
fun Int.wsp(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true)
fun Int.wspa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, applyAspectRatio = true)
fun Int.wspi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true)
fun Int.wspia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspLh(context)`.
 */
fun Int.wspLh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH)
fun Int.wspLha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
fun Int.wspLhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
fun Int.wspLhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wspPh(context)`.
 */
fun Int.wspPh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH)
fun Int.wspPha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
fun Int.wspPhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
fun Int.wspPhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = true, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN WITHOUT FONT SCALE variants (sem escala de fonte)
// PT Variantes SEM ESCALA DE FONTE

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE).
 * Usage example: `16.sei(context)`.
 */
fun Int.sei(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false)
fun Int.seia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, applyAspectRatio = true)
fun Int.seii(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true)
fun Int.seiia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.semPh(context)`.
 */
fun Int.semPh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH)
fun Int.semPha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, applyAspectRatio = true)
fun Int.semPhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true)
fun Int.semPhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.semLh(context)`.
 */
fun Int.semLh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH)
fun Int.semLha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, applyAspectRatio = true)
fun Int.semLhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true)
fun Int.semLhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.semPw(context)`.
 */
fun Int.semPw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW)
fun Int.semPwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, applyAspectRatio = true)
fun Int.semPwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true)
fun Int.semPwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.semLw(context)`.
 */
fun Int.semLw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW)
fun Int.semLwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, applyAspectRatio = true)
fun Int.semLwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true)
fun Int.semLwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale = false, inverter = Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE).
 * Usage example: `32.hei(context)`.
 */
fun Int.hei(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false)
fun Int.heia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, applyAspectRatio = true)
fun Int.heii(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true)
fun Int.heiia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hemLw(context)`.
 */
fun Int.hemLw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW)
fun Int.hemLwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, applyAspectRatio = true)
fun Int.hemLwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true)
fun Int.hemLwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hemPw(context)`.
 */
fun Int.hemPw(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW)
fun Int.hemPwa(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, applyAspectRatio = true)
fun Int.hemPwi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true)
fun Int.hemPwia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale = false, inverter = Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE).
 * Usage example: `100.wei(context)`.
 */
fun Int.wei(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false)
fun Int.weia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, applyAspectRatio = true)
fun Int.weii(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true)
fun Int.weiia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE), but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemLh(context)`.
 */
fun Int.wemLh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH)
fun Int.wemLha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, applyAspectRatio = true)
fun Int.wemLhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true)
fun Int.wemLhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)** (WITHOUT FONT SCALE), but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wemPh(context)`.
 */
fun Int.wemPh(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH)
fun Int.wemPha(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, applyAspectRatio = true)
fun Int.wemPhi(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true)
fun Int.wemPhia(context: Context): Float = this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale = false, inverter = Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN Qualifier-based conditional dynamic scaling for Sp.
// PT Escalonamento condicional baseado em qualificador para Sp.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swSP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swSP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.sspQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hSP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hSP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.hspQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wSP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wSP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.wspQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions for Sp.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier para Sp.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swSP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swSP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.sspScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.SMALL_WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hSP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hSP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.hspScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.HEIGHT, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wSP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wSP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.wspScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, fontScale: Boolean = true, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledSpPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledSpPx(context, DpQualifier.WIDTH, fontScale, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Dynamic scaling function for Sp (Resource-based).
// PT Função de dimensionamento dinâmico para Sp (baseada em recursos).

/**
 * EN
 * Converts an Int (the base Sp value) into a dynamically scaled pixel value (Float).
 *
 * PT
 * Converte um Int (o valor base de Sp) em um valor de pixel dinamicamente escalado (Float).
 *
 * @param context The Android context to access configuration and density.
 * @param qualifier The screen qualifier used for scaling (sw, h, w).
 * @param fontScale Whether to respect the system font scale.
 * @return The scaled pixel value.
 */
@JvmOverloads
fun Int.toDynamicScaledSpPx(
    context: Context,
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    require(this in 1..1024) { "Value must be between 1 and 1024. Current value: $this" }

    val resources = context.resources
    val configuration = resources.configuration
    val displayMetrics = resources.displayMetrics

    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE

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
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )

    val scaledSp = if (enableCache) {
        DimenCache.getOrPut(cacheKey, context) {
            calculateScaledSp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }
    } else {
        calculateScaledSp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    // Convert SP to PX
    val unit = if (fontScale) TypedValue.COMPLEX_UNIT_SP else TypedValue.COMPLEX_UNIT_DIP // COMPLEX_UNIT_DIP used for NO_SCALE SP because it's effectively DP
    return TypedValue.applyDimension(unit, scaledSp, displayMetrics)
}

/**
 * EN Internal logic to calculate the scaled SP value (similar to DP calculation but conceptually for text).
 */
private fun calculateScaledSp(
    baseValue: Int,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var actualQualifier = qualifier

    when (inverter) {
        Inverter.PH_TO_LW -> if (isLandscape && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.PW_TO_LH -> if (isLandscape && qualifier == DpQualifier.WIDTH)  actualQualifier = DpQualifier.HEIGHT
        Inverter.LH_TO_PW -> if (isPortrait  && qualifier == DpQualifier.HEIGHT) actualQualifier = DpQualifier.WIDTH
        Inverter.LW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.WIDTH)  actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LH -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_LW -> if (isLandscape && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.SW_TO_PH -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.HEIGHT
        Inverter.SW_TO_PW -> if (isPortrait  && qualifier == DpQualifier.SMALL_WIDTH) actualQualifier = DpQualifier.WIDTH
        Inverter.DEFAULT  -> {}
    }

    val isMultiWindow = if (ignoreMultiWindows) {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
        val isSmallDiff = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1f)
        isLayoutSplit && !isSmallDiff
    } else false

    return if (isMultiWindow) {
        baseValue.toFloat()
    } else {
        val screenDimension = when (actualQualifier) {
            DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
            DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
            else -> configuration.smallestScreenWidthDp.toFloat()
        }
        DimenCache.calculateRawScaling(baseValue, screenDimension, applyAspectRatio, customSensitivityK)
    }
}

/**
 * EN Converts an Int (base Sp) to a dynamically scaled Sp value (as Float).
 */
@JvmOverloads
fun Int.toDynamicScaledSp(
    context: Context,
    qualifier: DpQualifier,
    fontScale: Boolean,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    val resources = context.resources
    val configuration = resources.configuration
    
    val valueType = if (fontScale) DimenCache.ValueType.SP_WITH_SCALE else DimenCache.ValueType.SP_NO_SCALE

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
        valueType = valueType,
        customSensitivityK = customSensitivityK
    )

    return if (enableCache) {
        DimenCache.getOrPut(cacheKey, context) {
            calculateScaledSp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }
    } else {
        calculateScaledSp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }
}

