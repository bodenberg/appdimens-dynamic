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

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.DpQualifierEntry
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.getCurrentUiModeType
import com.appdimens.dynamic.core.scaledEntryRememberStamp

/**
 * EN
 * Represents a custom Sp entry with qualifiers and priority, for the Compose Sp builder.
 *
 * PT
 * Representa uma entrada de Sp customizada com qualificadores e prioridade, para o builder Sp do Compose.
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

// EN Methods for creating the AutoSp class.
// PT Métodos de criação da classe AutoSp.

/**
 * EN Starts the build chain for AutoSp from a base TextUnit (Sp).
 * Usage example: `16.sp.autoSp().screen(...)`.
 *
 * PT Inicia a cadeia de construção para AutoSp a partir de um TextUnit (Sp) base.
 * Exemplo de uso: `16.sp.autoSp().screen(...)`.
 */
@Composable
fun TextUnit.autoSp(): AutoSp = AutoSp(this.value)

/**
 * EN Starts the build chain for AutoSp from a base Int (treated as sp).
 * Usage example: `16.autoSp().screen(...)`.
 *
 * PT Inicia a cadeia de construção para AutoSp a partir de um Int base (tratado como sp).
 * Exemplo de uso: `16.autoSp().screen(...)`.
 */
@Composable
fun Number.autoSp(): AutoSp = AutoSp(this)



/**
 * EN
 * A Stable Compose class that allows defining custom Sp text dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The TextUnit is resolved at composition and uses the base value or a custom value,
 * applying dynamic scaling via the existing XML DP resources.
 *
 * PT
 * Classe Stable do Compose que permite definir dimensões de texto Sp customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 *
 * O TextUnit é resolvido na composição e usa o valor base ou um valor customizado,
 * aplicando o escalonamento dinâmico via os recursos XML de DP existentes.
 */
@Stable
class AutoSp private constructor(
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
    fun ignoreMultiWindows(ignore: Boolean = true): AutoSp {
        return AutoSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Allow applying aspect ratio based constraint scaling.
     * PT Permite aplicar o redimensionamento baseado na proporção da tela.
     */
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): AutoSp {
        return AutoSp(initialBaseValue, defaultFontScale, sortedCustomEntries, ignoreMultiWindows, enable, sensitivityK)
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
     * EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).
     * PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): AutoSp {
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
        return AutoSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).
     * PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).
     */
    fun screen(
        type: UiModeType,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): AutoSp {
        val entry = CustomSpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter,
            fontScale = fontScale
        )
        return AutoSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.
     * PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.
     */
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation = Orientation.DEFAULT,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): AutoSp {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter,
            fontScale = fontScale
        )
        return AutoSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Priority 4: Orientation only.
     * PT Prioridade 4: Apenas Orientação.
     */
    fun screen(
        orientation: Orientation = Orientation.DEFAULT,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter = Inverter.DEFAULT,
        fontScale: Boolean = defaultFontScale
    ): AutoSp {
        val entry = CustomSpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter,
            fontScale = fontScale
        )
        return AutoSp(initialBaseValue, defaultFontScale, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    // EN Resolution logic.
    // PT Lógica de resolução.

    /**
     * EN Resolves the matching [CustomSpEntry] and returns scaled [TextUnit] (Sp) for [qualifier].
     * PT Resolve a [CustomSpEntry] correspondente e retorna [TextUnit] (Sp) escalonado para [qualifier].
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun resolve(qualifier: DpQualifier): TextUnit {
        val configuration = LocalConfiguration.current

        val currentUiModeType = getCurrentUiModeType()

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val aspectRatio = if (currentScreenWidthDp > 0 && currentScreenHeightDp > 0) {
            kotlin.math.max(currentScreenWidthDp, currentScreenHeightDp) / kotlin.math.min(currentScreenWidthDp, currentScreenHeightDp)
        } else 1f

        val entryStamp = scaledEntryRememberStamp(
            currentUiModeType.ordinal,
            configuration,
            aspectRatio,
            ignoreMultiWindows
        )

        val foundEntry = remember(
            entryStamp,
            sortedCustomEntries
        ) {
            sortedCustomEntries.firstOrNull { entry ->
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
                    return@firstOrNull false
                } else {
                    if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                    if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                    return@firstOrNull false
                }
            }
        }

        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        val finalFontScale = foundEntry?.fontScale ?: defaultFontScale

        return valueToUse.toDynamicAutoSp(finalQualifier, finalFontScale, foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Same as [resolve] but returns density-scaled pixels ([toDynamicAutoPx]).
     * PT Igual a [resolve], mas retorna pixels já escalados pela densidade ([toDynamicAutoPx]).
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun resolvePx(qualifier: DpQualifier): Float {
        val configuration = LocalConfiguration.current

        val currentUiModeType = getCurrentUiModeType()

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val aspectRatio = if (currentScreenWidthDp > 0 && currentScreenHeightDp > 0) {
            kotlin.math.max(currentScreenWidthDp, currentScreenHeightDp) / kotlin.math.min(currentScreenWidthDp, currentScreenHeightDp)
        } else 1f

        val entryStampPx = scaledEntryRememberStamp(
            currentUiModeType.ordinal,
            configuration,
            aspectRatio,
            ignoreMultiWindows
        )

        val foundEntry = remember(
            entryStampPx,
            sortedCustomEntries
        ) {
            sortedCustomEntries.firstOrNull { entry ->
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
                    return@firstOrNull false
                } else {
                    if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                    if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                    return@firstOrNull false
                }
            }
        }

        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        val finalFontScale = foundEntry?.fontScale ?: defaultFontScale

        return valueToUse.toDynamicAutoPx(finalQualifier, finalFontScale, foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Like [resolve] but forces `fontScale = false` (fixed Sp, same idea as `asem` / `awem` accessors).
     * PT Como [resolve], mas força `fontScale = false` (Sp fixo, mesmo propósito dos acessores `asem` / `awem`).
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun resolveNoFontScale(qualifier: DpQualifier): TextUnit {
        val configuration = LocalConfiguration.current

        val currentUiModeType = getCurrentUiModeType()

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val aspectRatio = if (currentScreenWidthDp > 0 && currentScreenHeightDp > 0) {
            kotlin.math.max(currentScreenWidthDp, currentScreenHeightDp) / kotlin.math.min(currentScreenWidthDp, currentScreenHeightDp)
        } else 1f

        val entryStampNfs = scaledEntryRememberStamp(
            currentUiModeType.ordinal,
            configuration,
            aspectRatio,
            ignoreMultiWindows
        )

        val foundEntry = remember(
            entryStampNfs,
            sortedCustomEntries
        ) {
            sortedCustomEntries.firstOrNull { entry ->
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
                    return@firstOrNull false
                } else {
                    if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                    if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                    return@firstOrNull false
                }
            }
        }

        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        return valueToUse.toDynamicAutoSp(finalQualifier, fontScale = false, foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Like [resolvePx] with `fontScale = false`.
     * PT Como [resolvePx] com `fontScale = false`.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun resolveNoFontScalePx(qualifier: DpQualifier): Float {
        val configuration = LocalConfiguration.current

        val currentUiModeType = getCurrentUiModeType()

        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val aspectRatio = if (currentScreenWidthDp > 0 && currentScreenHeightDp > 0) {
            kotlin.math.max(currentScreenWidthDp, currentScreenHeightDp) / kotlin.math.min(currentScreenWidthDp, currentScreenHeightDp)
        } else 1f

        val entryStampNfsPx = scaledEntryRememberStamp(
            currentUiModeType.ordinal,
            configuration,
            aspectRatio,
            ignoreMultiWindows
        )

        val foundEntry = remember(
            entryStampNfsPx,
            sortedCustomEntries
        ) {
            sortedCustomEntries.firstOrNull { entry ->
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
                    return@firstOrNull false
                } else {
                    if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                    if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                    return@firstOrNull false
                }
            }
        }

        val valueToUse = foundEntry?.customValue ?: initialBaseValue
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        return valueToUse.toDynamicAutoPx(finalQualifier, fontScale = false, inverter = foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows = ignoreMultiWindows, applyAspectRatio = applyAspectRatio, customSensitivityK = customSensitivityK)
    }

    /**
     * EN The final TextUnit (Sp) value resolved using Smallest Width (WITH font scale).
     * PT O valor final TextUnit (Sp) resolvido usando Smallest Width (COM escala de fonte).
     */
    @get:Composable
    val assp: TextUnit get() = resolve(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final TextUnit (Sp) value resolved using Screen Height (WITH font scale).
     * PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (COM escala de fonte).
     */
    @get:Composable
    val ahsp: TextUnit get() = resolve(DpQualifier.HEIGHT)

    /**
     * EN The final TextUnit (Sp) value resolved using Screen Width (WITH font scale).
     * PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (COM escala de fonte).
     */
    @get:Composable
    val awsp: TextUnit get() = resolve(DpQualifier.WIDTH)

    /**
     * EN The final TextUnit (Sp) value resolved using Smallest Width (WITHOUT FONT SCALE).
     * PT O valor final TextUnit (Sp) resolvido usando Smallest Width (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val asem: TextUnit get() = resolveNoFontScale(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final TextUnit (Sp) value resolved using Screen Height (WITHOUT FONT SCALE).
     * PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val ahem: TextUnit get() = resolveNoFontScale(DpQualifier.HEIGHT)

    /**
     * EN The final TextUnit (Sp) value resolved using Screen Width (WITHOUT FONT SCALE).
     * PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val awem: TextUnit get() = resolveNoFontScale(DpQualifier.WIDTH)

    /**
     * EN The final Pixel (Float) value resolved using Smallest Width (WITH font scale).
     * PT O valor final em Pixels (Float) resolvido usando Smallest Width (COM escala de fonte).
     */
    @get:Composable
    val asspPx: Float get() = resolvePx(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final Pixel (Float) value resolved using Screen Height (WITH font scale).
     * PT O valor final em Pixels (Float) resolvido usando Altura da Tela (COM escala de fonte).
     */
    @get:Composable
    val ahspPx: Float get() = resolvePx(DpQualifier.HEIGHT)

    /**
     * EN The final Pixel (Float) value resolved using Screen Width (WITH font scale).
     * PT O valor final em Pixels (Float) resolvido usando Largura da Tela (COM escala de fonte).
     */
    @get:Composable
    val awspPx: Float get() = resolvePx(DpQualifier.WIDTH)

    /**
     * EN The final Pixel (Float) value resolved using Smallest Width (WITHOUT FONT SCALE).
     * PT O valor final em Pixels (Float) resolvido usando Smallest Width (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val asemPx: Float get() = resolveNoFontScalePx(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final Pixel (Float) value resolved using Screen Height (WITHOUT FONT SCALE).
     * PT O valor final em Pixels (Float) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val ahemPx: Float get() = resolveNoFontScalePx(DpQualifier.HEIGHT)

    /**
     * EN The final Pixel (Float) value resolved using Screen Width (WITHOUT FONT SCALE).
     * PT O valor final em Pixels (Float) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val awemPx: Float get() = resolveNoFontScalePx(DpQualifier.WIDTH)
}