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

// EN Rotation facilitator extensions for non-Compose (Views).
// PT Extensões facilitadoras para rotação em não-Compose (Views).

private const val BASE_RATIO_STEP = 300f

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device is in the specified [orientation],
 * it uses [rotationValue] scaled with the given [finalQualifierResolver].
 */
@JvmOverloads
fun Int.sdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.SMALL_WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
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
        rotationValue.toDynamicScaledPx(context, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Int.hdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.HEIGHT,
    orientation: Orientation = Orientation.LANDSCAPE,
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
        rotationValue.toDynamicScaledPx(context, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Int.wdpRotate(
    context: Context,
    rotationValue: Int,
    finalQualifierResolver: DpQualifier = DpQualifier.WIDTH,
    orientation: Orientation = Orientation.LANDSCAPE,
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
        rotationValue.toDynamicScaledPx(context, finalQualifierResolver, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType facilitator extensions for non-Compose.
// PT Extensões facilitadoras para UiModeType em não-Compose.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches the specified [uiModeType],
 * it uses [modeValue] instead.
 */
@JvmOverloads
fun Int.sdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null) // In non-Compose we could try to find activity but usually context is enough
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 */
@JvmOverloads
fun Int.hdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 */
@JvmOverloads
fun Int.wdpMode(
    context: Context,
    modeValue: Int,
    uiModeType: UiModeType,
    finalQualifierResolver: DpQualifier? = null,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null
): Float {
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    return if (currentUiModeType == uiModeType) {
        modeValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}


/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 *
 * PT
 * Obtém o valor real da configuração (Configuration) para o DpQualifier dado.
 *
 * @param qualifier The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration The current resource configuration.
 * @return The numeric value (in Dp) of the screen metric.
 */
internal fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
}

// EN Standard Android extensions for quick dynamic scaling (View-based).
// PT Extensões Android padrão para dimensionamento dinâmico rápido (baseado em Views).

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Smallest Width (swDP)**.
 * Usage example: `16.sdp(context)`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.sdp(context)`.
 */
fun Int.sdp(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH)
fun Int.sdpa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
fun Int.sdpi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true)
fun Int.sdpia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpPh(context)`.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**, mas
 * na orientação retrato atua como **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.sdpPh(context)`.
 */
fun Int.sdpPh(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH)
fun Int.sdpPha(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, applyAspectRatio = true)
fun Int.sdpPhi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true)
fun Int.sdpPhia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `32.sdpLh(context)`.
 */
fun Int.sdpLh(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH)
fun Int.sdpLha(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, applyAspectRatio = true)
fun Int.sdpLhi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true)
fun Int.sdpLhia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpPw(context)`.
 */
fun Int.sdpPw(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW)
fun Int.sdpPwa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, applyAspectRatio = true)
fun Int.sdpPwi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true)
fun Int.sdpPwia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.sdpLw(context)`.
 */
fun Int.sdpLw(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW)
fun Int.sdpLwa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, applyAspectRatio = true)
fun Int.sdpLwi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true)
fun Int.sdpLwia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, Inverter.SW_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**.
 * Usage example: `32.hdp(context)`.
 */
fun Int.hdp(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT)
fun Int.hdpa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, applyAspectRatio = true)
fun Int.hdpi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = true)
fun Int.hdpia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in landscape orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpLw(context)`.
 */
fun Int.hdpLw(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.PH_TO_LW)
fun Int.hdpLwa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.PH_TO_LW, applyAspectRatio = true)
fun Int.hdpLwi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true)
fun Int.hdpLwia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.PH_TO_LW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Height (hDP)**, but
 * in portrait orientation it acts as **Screen Width (wDP)**.
 * Usage example: `32.hdpPw(context)`.
 */
fun Int.hdpPw(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.LH_TO_PW)
fun Int.hdpPwa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.LH_TO_PW, applyAspectRatio = true)
fun Int.hdpPwi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true)
fun Int.hdpPwia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.HEIGHT, Inverter.LH_TO_PW, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**.
 * Usage example: `100.wdp(context)`.
 */
fun Int.wdp(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH)
fun Int.wdpa(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, applyAspectRatio = true)
fun Int.wdpi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = true)
fun Int.wdpia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in landscape orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpLh(context)`.
 */
fun Int.wdpLh(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.PW_TO_LH)
fun Int.wdpLha(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.PW_TO_LH, applyAspectRatio = true)
fun Int.wdpLhi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true)
fun Int.wdpLhia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.PW_TO_LH, ignoreMultiWindows = true, applyAspectRatio = true)

/**
 * EN
 * Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but
 * in portrait orientation it acts as **Screen Height (hDP)**.
 * Usage example: `100.wdpPh(context)`.
 */
fun Int.wdpPh(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.LW_TO_PH)
fun Int.wdpPha(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.LW_TO_PH, applyAspectRatio = true)
fun Int.wdpPhi(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true)
fun Int.wdpPhia(context: Context): Float = this.toDynamicScaledPx(context, DpQualifier.WIDTH, Inverter.LW_TO_PH, ignoreMultiWindows = true, applyAspectRatio = true)

// EN Qualifier-based conditional dynamic scaling.
// PT Escalonamento condicional baseado em qualificador.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.sdpQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.hdpQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the screen metric for [qualifierType]
 * is >= [qualifierValue], it uses [qualifiedValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType]
 * é >= [qualifierValue], usa [qualifiedValue] no lugar.
 */
fun Int.wdpQualifier(context: Context, qualifiedValue: Int, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (qualifierMatch) {
        qualifiedValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN UiModeType + DpQualifier combined facilitator extensions.
// PT Extensões facilitadoras combinadas UiModeType + DpQualifier.

/**
 * EN
 * Extension for Int with dynamic scaling based on **Smallest Width (swDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.sdpScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.SMALL_WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Height (hDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.hdpScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.HEIGHT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

/**
 * EN
 * Extension for Int with dynamic scaling based on **Screen Width (wDP)**.
 * Uses the base value by default, but when the device matches [uiModeType] AND
 * the screen metric for [qualifierType] is >= [qualifierValue], it uses [screenValue] instead.
 *
 * PT
 * Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType] E
 * a métrica de tela para [qualifierType] é >= [qualifierValue], usa [screenValue] no lugar.
 */
fun Int.wdpScreen(context: Context, screenValue: Int, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, finalQualifierResolver: DpQualifier? = null, ignoreMultiWindows: Boolean = false, applyAspectRatio: Boolean = false, customSensitivityK: Float? = null): Float {
    val configuration = context.resources.configuration
    val currentUiModeType = UiModeType.fromConfiguration(context, null)
    val uiModeMatch = currentUiModeType == uiModeType
    val qualifierMatch = getQualifierValue(qualifierType, configuration) >= qualifierValue
    return if (uiModeMatch && qualifierMatch) {
        screenValue.toDynamicScaledPx(context, finalQualifierResolver ?: DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    } else {
        this.toDynamicScaledPx(context, DpQualifier.WIDTH, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }
}

// EN Dynamic scaling functions (Resource-based).
// PT Funções de dimensionamento dinâmico (baseadas em recursos).

/**
 * EN
 * Converts an Int (the base Dp value) into a dynamically scaled pixel value (Float).
 *
 * PT
 * Converte um Int (o valor base de Dp) em um valor de pixel dinamicamente escalado (Float).
 *
 * @param context The Android context to access configuration and density.
 * @param qualifier The screen qualifier used for scaling (sw, h, w).
 * @return The scaled pixel value.
 */
@JvmOverloads
fun Int.toDynamicScaledPx(
    context: Context,
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    // EN Validation requirement (limits usage to avoid creating thousands of dimens files).
    require(this in -1023..1024) { "Value must be between -1023 and 1024. Current value: $this" }

    val resources = context.resources
    val configuration = resources.configuration
    val displayMetrics = resources.displayMetrics
    
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
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )

    val scaledDp = if (enableCache) {
        DimenCache.getOrPut(cacheKey, context) {
            calculateScaledDp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }
    } else {
        calculateScaledDp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    // Convert DP to PX
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scaledDp, displayMetrics)
}

/**
 * EN Internal logic to calculate the scaled DP value.
 */
private fun calculateScaledDp(
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
        // Simple heuristic for multi-window detection in non-Compose
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
 * EN Converts an Int (base Dp) to a dynamically scaled Dp (as Float).
 * PT Converte um Int (base Dp) para um Dp escalado dinamicamente (como Float).
 */
@JvmOverloads
fun Int.toDynamicScaledDp(
    context: Context,
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
    enableCache: Boolean = true
): Float {
    val resources = context.resources
    val configuration = resources.configuration
    
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
        valueType = DimenCache.ValueType.DP,
        customSensitivityK = customSensitivityK
    )

    return if (enableCache) {
        DimenCache.getOrPut(cacheKey, context) {
            calculateScaledDp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
        }
    } else {
        calculateScaledDp(this, configuration, qualifier, inverter, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }
}

