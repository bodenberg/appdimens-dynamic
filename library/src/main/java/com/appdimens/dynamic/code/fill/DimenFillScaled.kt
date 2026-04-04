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
package com.appdimens.dynamic.code.fill

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
 * Represents a custom dimension entry with qualifiers and priority.
 * Used by the DimenFillScaled class to define specific values for screen conditions.
 *
 * PT
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade.
 * Usada pela classe DimenFillScaled para definir valores específicos para condições de tela.
 */
data class CustomDpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation? = Orientation.DEFAULT,
    val customValue: Float, // Representing DP
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter? = Inverter.DEFAULT
)

// EN Methods for creating the DimenFillScaled class.
// PT Métodos de criação da classe DimenFillScaled.

/**
 * EN Starts the build chain for the custom dimension DimenFillScaled from a base Float (Dp).
 * PT Inicia a cadeia de construção para a dimensão customizada DimenFillScaled a partir de um Float (Dp) base.
 */
fun Float.fillScaledDp(): DimenFillScaled = DimenFillScaled(this)

/**
 * EN Starts the build chain for the custom dimension DimenFillScaled from a base Int (Dp).
 * PT Inicia a cadeia de construção para a dimensão customizada DimenFillScaled a partir de um Int (Dp) base.
 */
fun Number.fillScaledDp(): DimenFillScaled = this.toFloat().fillScaledDp()

/**
 * EN
 * A class that allows defining custom dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The value is resolved using a Context and uses the base value or a
 * custom value, applying dynamic scaling at the end.
 *
 * PT
 * Classe que permite a definição de dimensões customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 */
class DimenFillScaled private constructor(
    private val initialBaseDp: Float,
    private val sortedCustomEntries: List<CustomDpEntry> = emptyList(),
    private val ignoreMultiWindows: Boolean = false,
    private val applyAspectRatio: Boolean = false,
    private val customSensitivityK: Float? = null
) {

    // EN Main constructor to start the chain.
    constructor(initialBaseDp: Float) : this(initialBaseDp, emptyList(), false, false, null)


    /**
     * EN Allow applying aspect ratio based constraint scaling.
     * PT Permite aplicar o escalonamento restrito baseado na proporção da tela (aspect ratio).
     */
    @JvmOverloads
    fun applyAspectRatio(apply: Boolean = true): DimenFillScaled {
        return DimenFillScaled(initialBaseDp, sortedCustomEntries, ignoreMultiWindows, apply, customSensitivityK)
    }

    /**
     * EN Allow ignoring the constraint scaling based on multi-window resizing properties.
     * PT Permite ignorar o escalonamento restrito baseado nas propriedades de redimensionamento de multi-janelas.
     */
    @JvmOverloads
    fun ignoreMultiWindows(ignore: Boolean = true): DimenFillScaled {
        return DimenFillScaled(initialBaseDp, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK)
    }

    private fun reorderEntries(newEntry: CustomDpEntry): List<CustomDpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomDpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value?.toFloat() ?: 0f }
        )
    }

    // EN Builder methods.

    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        orientation: Orientation? = Orientation.DEFAULT,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return DimenFillScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled = screen(uiModeType, qualifierType, qualifierValue, orientation, customValue.toFloat(), finalQualifierResolver, inverter)

    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return DimenFillScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        type: UiModeType,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled = screen(type, customValue.toFloat(), finalQualifierResolver, orientation, inverter)

    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return DimenFillScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled = screen(type, value, customValue.toFloat(), finalQualifierResolver, orientation, inverter)

    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Float,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled {
        val entry = CustomDpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return DimenFillScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @JvmOverloads
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenFillScaled = screen(orientation, customValue.toFloat(), finalQualifierResolver, inverter)

    // EN Resolution logic.

    private fun resolveDp(context: Context, qualifier: DpQualifier): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        return resolveDpInternal(context, qualifier, configuration, currentUiModeType)
    }

    /**
     * EN Resolves sdp, hdp, and wdp in one pass (single [UiModeType.fromConfiguration] and config read).
     * PT Resolve sdp, hdp e wdp numa só passagem.
     */
    fun sdpHdpWdpPx(context: Context): Triple<Float, Float, Float> {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        val density = context.resources.displayMetrics.density
        val sdp = resolveDpInternal(context, DpQualifier.SMALL_WIDTH, configuration, currentUiModeType) * density
        val hdp = resolveDpInternal(context, DpQualifier.HEIGHT, configuration, currentUiModeType) * density
        val wdp = resolveDpInternal(context, DpQualifier.WIDTH, configuration, currentUiModeType) * density
        return Triple(sdp, hdp, wdp)
    }

    private fun resolveDpInternal(
        context: Context,
        qualifier: DpQualifier,
        configuration: Configuration,
        currentUiModeType: UiModeType
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

        val dpToUse = foundEntry?.customValue ?: initialBaseDp
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        return dpToUse.toInt().toDynamicFillDp(
            context,
            finalQualifier,
            foundEntry?.inverter ?: Inverter.DEFAULT,
            ignoreMultiWindows,
            applyAspectRatio,
            customSensitivityK
        )
    }

    /**
     * EN Resolves the final value in pixels (Float).
     */
    fun px(context: Context, qualifier: DpQualifier): Float {
        val configuration = context.resources.configuration
        val currentUiModeType = UiModeType.fromConfiguration(context, null)
        val dpValue = resolveDpInternal(context, qualifier, configuration, currentUiModeType)
        return dpValue * context.resources.displayMetrics.density
    }

    // EN Convenience properties/methods similar to Compose version.

    fun sdp(context: Context): Float = px(context, DpQualifier.SMALL_WIDTH)
    fun hdp(context: Context): Float = px(context, DpQualifier.HEIGHT)
    fun wdp(context: Context): Float = px(context, DpQualifier.WIDTH)

    /** EN Get the resolved value in DP (as Float). */
    fun sdpBase(context: Context): Float = resolveDp(context, DpQualifier.SMALL_WIDTH)
    fun hdpBase(context: Context): Float = resolveDp(context, DpQualifier.HEIGHT)
    fun wdpBase(context: Context): Float = resolveDp(context, DpQualifier.WIDTH)
}