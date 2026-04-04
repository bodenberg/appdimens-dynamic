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
package com.appdimens.dynamic.code.diagonal

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.DpQualifierEntry
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.DimenCache

/**
 * EN
 * Represents a custom Sp entry with qualifiers and priority, for the non-Compose Sp builder.
 *
 * PT
 * Representa uma entrada de Sp customizada com qualificadores e prioridade, para o builder Sp fora do Compose.
 *
 * @param uiModeType The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.
 * @param dpQualifierEntry The Dp qualifier entry (type and value). Null if only UI mode is used.
 * @param orientation The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).
 * @param customValue The base Int Sp value to be used if the condition is met.
 * @param finalQualifierResolver Optional override for the scaling qualifier at resolution time.
 * @param priority The resolution priority. 1 is most specific (UI + Qualifier), 4 is least specific.
 * @param inverter The inverter type to adapt scaling on rotation changes.
 * @param fontScale Whether to respect the system font scale (default true).
 */
data class CustomSpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation = Orientation.DEFAULT,
    val customValue: Number,
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter = Inverter.DEFAULT,
    val fontScale: Boolean = true
)

// EN Methods for creating the DiagonalSp class.
// PT Métodos de criação da classe DiagonalSp.

/**
 * EN Starts the build chain for DiagonalSp from a base Float (treated as sp).
 * PT Inicia a cadeia de construção para DiagonalSp a partir de um Float base (tratado como sp).
 */
fun Float.diagonalSp(): DiagonalSp = DiagonalSp(this.toInt())

/**
 * EN Starts the build chain for DiagonalSp from a base Int (treated as sp).
 * PT Inicia a cadeia de construção para DiagonalSp a partir de um Int base (tratado como sp).
 */
fun Number.diagonalSp(): DiagonalSp = DiagonalSp(this)

/**
 * EN
 * A class that allows defining custom Sp text dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The value is resolved using a Context and uses the base value or a custom value,
 * applying dynamic scaling.
 *
 * PT
 * Classe que permite definir dimensões de texto Sp customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 */
class DiagonalSp private constructor(
    private val initialBaseValue: Number,
    private val defaultFontScale: Boolean = true,
    private val sortedCustomEntries: List<CustomSpEntry> = emptyList(),
    private val ignoreMultiWindows: Boolean = false,
    private val applyAspectRatio: Boolean = false,
    private val customSensitivityK: Float? = null
) {
    constructor(initialBaseValue: Number) : this(initialBaseValue, true, emptyList(), false, false, null)


    /**
     * EN Allow ignoring the constraint scaling based on multi-window resizing properties.
     * PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).
     */
    @JvmOverloads
    fun ignoreMultiWindows(ignore: Boolean = true): DiagonalSp {
        return DiagonalSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Allow applying aspect ratio based constraint scaling.
     * PT Permite aplicar o redimensionamento baseado na proporção da tela.
     */
    @JvmOverloads
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): DiagonalSp {
        return DiagonalSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignoreMultiWindows, enable, sensitivityK)
    }

    /**
     * EN
     * Adds a new entry and re-sorts the list by priority, then by qualifier value (descending).
     *
     * PT
     * Adiciona uma nova entrada e reordena por prioridade e depois por valor de qualificador (decrescente).
     */
    private fun reorderEntries(newEntry: CustomSpEntry): List<CustomSpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value?.toFloat() ?: 0f }
        )
    }

    // EN Fluent methods for construction.
    // PT Métodos fluentes para construção.

    /**
     * EN Priority 1: Most specific qualifier — combines [UiModeType] and Dp qualifier (sw, h, w).
     * PT Prioridade 1: qualificador mais específico — combina [UiModeType] e qualificador Dp (sw, h, w).
     */
    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): DiagonalSp {
        val entry = CustomSpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter,
            fontScale = fontScale
        )
        return DiagonalSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 2: [UiModeType] only (e.g. TELEVISION, WATCH).
     * PT Prioridade 2: apenas [UiModeType] (ex.: TELEVISION, WATCH).
     */
    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): DiagonalSp {
        val entry = CustomSpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter,
            fontScale = fontScale
        )
        return DiagonalSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 3: Dp qualifier (sw, h, w) without [UiModeType] restriction.
     * PT Prioridade 3: qualificador Dp (sw, h, w) sem restrição de [UiModeType].
     */
    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): DiagonalSp {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter,
            fontScale = fontScale
        )
        return DiagonalSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 4: orientation only.
     * PT Prioridade 4: apenas orientação.
     */
    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): DiagonalSp {
        val entry = CustomSpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter,
            fontScale = fontScale
        )
        return DiagonalSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    // EN Resolution logic.
    // PT Lógica de resolução.

    /**
     * EN Resolves [qualifier] to px using the first matching [CustomSpEntry], optionally overriding font scale.
     * PT Resolve [qualifier] em px usando a primeira [CustomSpEntry] correspondente, com override opcional da escala de fonte.
     */
    private fun resolvePx(context: Context, qualifier: DpQualifier, fontScaleOverride: Boolean? = null): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        return resolvePxInternal(context, qualifier, configuration, currentUiModeType, fontScaleOverride)
    }

    /**
     * EN Resolves dgssp, dghsp, and dgwsp in one pass (single [UiModeType.fromConfiguration] read).
     * PT Resolve dgssp, dghsp e dgwsp numa só passagem.
     */
    fun sspHspWspPx(context: Context): Triple<Float, Float, Float> {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        return Triple(
            resolvePxInternal(context, DpQualifier.SMALL_WIDTH, configuration, currentUiModeType, null),
            resolvePxInternal(context, DpQualifier.HEIGHT, configuration, currentUiModeType, null),
            resolvePxInternal(context, DpQualifier.WIDTH, configuration, currentUiModeType, null)
        )
    }

    /**
     * EN Resolves sei, hei, and wei in one pass (fixed Sp / no font-scale path).
     * PT Resolve sei, hei e wei numa só passagem (Sp fixo / sem escala de fonte).
     */
    fun seiHeiWeiPx(context: Context): Triple<Float, Float, Float> {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        return Triple(
            resolvePxInternal(context, DpQualifier.SMALL_WIDTH, configuration, currentUiModeType, false),
            resolvePxInternal(context, DpQualifier.HEIGHT, configuration, currentUiModeType, false),
            resolvePxInternal(context, DpQualifier.WIDTH, configuration, currentUiModeType, false)
        )
    }

    /**
     * EN Shared implementation for [resolvePx], [sspHspWspPx], and [seiHeiWeiPx].
     * PT Implementação compartilhada para [resolvePx], [sspHspWspPx] e [seiHeiWeiPx].
     */
    private fun resolvePxInternal(
        context: Context,
        qualifier: DpQualifier,
        configuration: Configuration,
        currentUiModeType: UiModeType,
        fontScaleOverride: Boolean?
    ): Float {
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType
            val orientationMatch = when (entry.orientation) {
                Orientation.LANDSCAPE -> isLandscape
                Orientation.PORTRAIT -> isPortrait
                else -> true
            }

            if (qualifierEntry != null) {
                val qualifierMatch = getQualifierValue(qualifierEntry.type, configuration) >= qualifierEntry.value.toFloat()
                if (entry.priority == 1 && uiModeMatch && qualifierMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 3 && qualifierMatch && orientationMatch) return@firstOrNull true
                false
            } else {
                if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                false
            }
        }

        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        val finalFontScale = fontScaleOverride ?: foundEntry?.fontScale ?: defaultFontScale

        return valueToUse.toDynamicDiagonalSpPx(
            context,
            finalQualifier,
            finalFontScale,
            foundEntry?.inverter ?: Inverter.DEFAULT,
            ignoreMultiWindows,
            applyAspectRatio,
            customSensitivityK
        )
    }

    /** EN Resolve final value in pixels (WITH font scale). */
    fun dgssp(context: Context): Float = resolvePx(context, DpQualifier.SMALL_WIDTH)
    fun dghsp(context: Context): Float = resolvePx(context, DpQualifier.HEIGHT)
    fun dgwsp(context: Context): Float = resolvePx(context, DpQualifier.WIDTH)

    /** EN Resolve final value in pixels (WITHOUT font scale). */
    fun sei(context: Context): Float = resolvePx(context, DpQualifier.SMALL_WIDTH, fontScaleOverride = false)
    fun hei(context: Context): Float = resolvePx(context, DpQualifier.HEIGHT, fontScaleOverride = false)
    fun wei(context: Context): Float = resolvePx(context, DpQualifier.WIDTH, fontScaleOverride = false)
}