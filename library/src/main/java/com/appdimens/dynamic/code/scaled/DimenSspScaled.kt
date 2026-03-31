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

// EN Methods for creating the ScaledSp class.
// PT Métodos de criação da classe ScaledSp.

/**
 * EN Starts the build chain for ScaledSp from a base Float (treated as sp).
 */
fun Float.scaledSp(): ScaledSp = ScaledSp(this.toInt())

/**
 * EN Starts the build chain for ScaledSp from a base Int (treated as sp).
 */
fun Number.scaledSp(): ScaledSp = ScaledSp(this)

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
class ScaledSp private constructor(
    private val initialBaseValue: Number,
    private val defaultFontScale: Boolean = true,
    private val sortedCustomEntries: List<CustomSpEntry> = emptyList(),
    private val ignoreMultiWindows: Boolean = false,
    private val applyAspectRatio: Boolean = false,
    private val customSensitivityK: Float? = null,
    private val isCacheEnabled: Boolean = true
) {
    constructor(initialBaseValue: Number) : this(initialBaseValue, true, emptyList(), false, false, null, true)

    /**
     * EN Enable or disable the cache for this specific calculation chain.
     */
    @JvmOverloads
    fun setEnableCache(enable: Boolean = true): ScaledSp {
        return ScaledSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignoreMultiWindows, applyAspectRatio, customSensitivityK, enable)
    }

    /**
     * EN Allow applying aspect ratio based constraint scaling.
     */
    @JvmOverloads
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): ScaledSp {
        return ScaledSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignoreMultiWindows, enable, sensitivityK, isCacheEnabled)
    }

    /**
     * EN Allow ignoring the constraint scaling based on multi-window resizing properties.
     */
    @JvmOverloads
    fun ignoreMultiWindows(ignore: Boolean = true): ScaledSp {
        return ScaledSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK, isCacheEnabled)
    }

    private fun reorderEntries(newEntry: CustomSpEntry): List<CustomSpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value?.toFloat() ?: 0f }
        )
    }

    // EN Fluent methods for construction.

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
    ): ScaledSp {
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
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK, isCacheEnabled)
    }

    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK, isCacheEnabled)
    }

    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK, isCacheEnabled)
    }

    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): ScaledSp {
        val entry = CustomSpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter,
            fontScale = fontScale
        )
        return ScaledSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK, isCacheEnabled)
    }

    // EN Resolution logic.

    private fun resolvePx(context: Context, qualifier: DpQualifier, fontScaleOverride: Boolean? = null): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)

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

        return valueToUse.toDynamicScaledSpPx(
            context,
            finalQualifier,
            finalFontScale,
            foundEntry?.inverter ?: Inverter.DEFAULT,
            ignoreMultiWindows,
            applyAspectRatio,
            customSensitivityK,
            isCacheEnabled
        )
    }

    /** EN Resolve final value in pixels (WITH font scale). */
    fun ssp(context: Context): Float = resolvePx(context, DpQualifier.SMALL_WIDTH)
    fun hsp(context: Context): Float = resolvePx(context, DpQualifier.HEIGHT)
    fun wsp(context: Context): Float = resolvePx(context, DpQualifier.WIDTH)

    /** EN Resolve final value in pixels (WITHOUT font scale). */
    fun sei(context: Context): Float = resolvePx(context, DpQualifier.SMALL_WIDTH, fontScaleOverride = false)
    fun hei(context: Context): Float = resolvePx(context, DpQualifier.HEIGHT, fontScaleOverride = false)
    fun wei(context: Context): Float = resolvePx(context, DpQualifier.WIDTH, fontScaleOverride = false)
}