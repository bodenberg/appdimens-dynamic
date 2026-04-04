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

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.DpQualifierEntry
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.core.getCurrentUiModeType
import com.appdimens.dynamic.core.scaledEntryRememberStamp

/**
 * EN
 * Represents a custom dimension entry with qualifiers and priority.
 * Used by the DimenDiagonalScaled class to define specific values for screen conditions.
 *
 * PT
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade.
 * Usada pela classe DimenDiagonalScaled para definir valores específicos para condições de tela.
 *
 * @param uiModeType The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.
 * @param dpQualifierEntry The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used.
 * @param orientation The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).
 * @param customValue The Dp value to be used if the condition is met.
 * @param finalQualifierResolver Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time.
 * @param priority The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only).
 * @param inverter The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT).
 */
data class CustomDpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val orientation: Orientation? = Orientation.DEFAULT,
    val customValue: Dp,
    val finalQualifierResolver: DpQualifier? = null,
    val priority: Int,
    val inverter: Inverter? = Inverter.DEFAULT
)

// EN Methods for creating the DimenDiagonalScaled class.
// PT Métodos de criação da classe DimenDiagonalScaled.

/**
 * EN Starts the build chain for the custom dimension DimenDiagonalScaled from a base Dp.
 * PT Inicia a cadeia de construção para a dimensão customizada DimenDiagonalScaled a partir de um Dp base.
 */
@Composable
fun Dp.diagonalDp(): DimenDiagonalScaled = DimenDiagonalScaled(this@diagonalDp)

/**
 * EN
 * Starts the build chain for the custom dimension DimenDiagonalScaled from a base Int.
 * Usage example: `100.scaled().screen(...)`.
 *
 * PT
 * Inicia a cadeia de construção para a dimensão customizada DimenDiagonalScaled a partir de um Int base.
 * Exemplo de uso: `100.scaled().screen(...)`.
 */
@Composable
fun Number.diagonalDp(): DimenDiagonalScaled = this.toFloat().dp.diagonalDp()



// EN Main class for conditional scaling.
// PT Classe principal de dimensionamento condicional.

/**
 * EN
 * A Stable class that allows defining custom dimensions
 * based on screen qualifiers (UiModeType, Width, Height, Smallest Width).
 *
 * The Dp value is resolved at composition (Compose) and uses the base value or a
 * custom value, applying dynamic scaling at the end.
 *
 * PT
 * Classe Stable que permite a definição de dimensões customizadas
 * baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).
 *
 * O valor Dp é resolvido na composição (Compose) e usa o valor base ou um
 * valor customizado, aplicando o dimensionamento dinâmico no final.
 */
@Stable
class DimenDiagonalScaled private constructor(
    private val initialBaseDp: Dp,
    // EN Custom entries are always kept sorted by priority.
    // PT As entradas customizadas são sempre mantidas ordenadas por prioridade.
    private val sortedCustomEntries: List<CustomDpEntry> = emptyList(),
    private val ignoreMultiWindows: Boolean = false,
    private val applyAspectRatio: Boolean = false,
    private val customSensitivityK: Float? = null
) {

    // EN Main constructor to start the chain.
    // PT Construtor principal para iniciar a cadeia.
    constructor(initialBaseDp: Dp) : this(initialBaseDp, emptyList(), false, false, null)


    /**
     * EN Allow ignoring the constraint scaling based on multi-window resizing properties.
     * PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).
     */
    fun ignoreMultiWindows(ignore: Boolean = true): DimenDiagonalScaled {
        return DimenDiagonalScaled(initialBaseDp, sortedCustomEntries, ignore, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN Allow applying aspect ratio based constraint scaling.
     * PT Permite aplicar o redimensionamento baseado na proporção da tela.
     */
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): DimenDiagonalScaled {
        return DimenDiagonalScaled(initialBaseDp, sortedCustomEntries, ignoreMultiWindows, enable, sensitivityK)
    }

    /**
     * EN
     * Adds a new entry and re-sorts the list.
     * Sorting is crucial: first by priority (ascending),
     * and then by dpQualifierEntry.value (descending) so that larger qualifiers
     * (e.g., sw600dp) are checked before smaller qualifiers (e.g., sw320dp).
     *
     * PT
     * Adiciona uma nova entrada e reordena a lista.
     * A ordenação é crucial: primeiro por priority (crescente),
     * e depois por dpQualifierEntry.value (decrescente) para que qualificadores
     * maiores (e.g., sw600dp) sejam verificados antes de qualificadores menores (e.g., sw320dp).
     */
    private fun reorderEntries(newEntry: CustomDpEntry): List<CustomDpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomDpEntry> { it.priority } // EN Priority 1 (most specific) first. / PT Prioridade 1 (mais específico) primeiro.
                .thenByDescending { it.dpQualifierEntry?.value?.toFloat() ?: 0f } // EN Higher value qualifier first. / PT Qualificador de valor maior primeiro.
        )
    }

    // EN Fluent methods for construction (Chain of Responsibility).
    // PT Métodos fluentes para construção (Chain of Responsibility).

    /**
     * EN
     * Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).
     *
     * PT
     * Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        orientation: Orientation? = Orientation.DEFAULT,
        customValue: Dp,
        finalQualifierResolver: DpQualifier? = null,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Number,
        customValue: Number,
        finalQualifierResolver: DpQualifier? = null,
        orientation: Orientation? = Orientation.DEFAULT,
        inverter: Inverter? = Inverter.DEFAULT
    ): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            orientation = orientation,
            customValue = customValue.toFloat().dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 1,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).
     *
     * PT
     * Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).
     */
    fun screen(type: UiModeType,
               customValue: Dp,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(type: UiModeType,
               customValue: Number,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            orientation = orientation,
            customValue = customValue.toFloat().dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 2,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.
     *
     * PT
     * Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.
     */
    fun screen(type: DpQualifier,
               value: Int,
               customValue: Dp,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT
    ): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(type: DpQualifier,
               value: Int,
               customValue: Number,
               finalQualifierResolver: DpQualifier? = null,
               orientation: Orientation? = Orientation.DEFAULT,
               inverter: Inverter? = Inverter.DEFAULT): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            orientation = orientation,
            customValue = customValue.toFloat().dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 3,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 4: Orientation.
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 4: Orientation.
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(orientation: Orientation = Orientation.DEFAULT,
               customValue: Dp,
               finalQualifierResolver: DpQualifier? = null,
               inverter: Inverter? = Inverter.DEFAULT): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            orientation = orientation,
            customValue = customValue,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN
     * Priority 4: Orientation.
     * This is an overload that accepts an Int for `customValue`.
     *
     * PT
     * Prioridade 4: Orientation.
     * Esta é uma sobrecarga que aceita um Int para `customValue`.
     */
    fun screen(orientation: Orientation = Orientation.DEFAULT,
               customValue: Number,
               finalQualifierResolver: DpQualifier? = null,
               inverter: Inverter? = Inverter.DEFAULT): DimenDiagonalScaled {
        val entry = CustomDpEntry(
            orientation = orientation,
            customValue = customValue.toFloat().dp,
            finalQualifierResolver = finalQualifierResolver,
            priority = 4,
            inverter = inverter
        )
        return DimenDiagonalScaled(initialBaseDp, reorderEntries(entry), ignoreMultiWindows)
    }

    // EN Composable resolution logic.
    // PT Lógica de resolução Composable.

    /**
     * EN
     * Resolves the final dimension. This is the Composable part that reads the current configuration
     * and decides which Dp to use.
     *
     * PT
     * Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual
     * e decide qual Dp usar.
     *
     * @param qualifier The dynamic scaling qualifier that will be applied at the end
     * (SMALL_WIDTH, HEIGHT, or WIDTH), determined by the access property (.sdp, .hdp, .wdp).
     */
    @SuppressLint("ConfigurationScreenWidthHeight") // EN The annotation is necessary as we access screen metrics. / PT A anotação é necessária, pois acessamos métricas da tela.
    @Composable
    private fun resolve(qualifier: DpQualifier): Dp {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current

        // EN Extract FoldingFeature dynamically if context is an Activity
        // PT Extrai o FoldingFeature dinamicamente se o contexto for uma Activity
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

        // EN Tries to find the first custom entry that qualifies using remember cache.
        // PT Tenta encontrar a primeira entrada customizada usando cache do remember.
        val foundEntry = remember(
            entryStamp,
            sortedCustomEntries
        ) {
            sortedCustomEntries.firstOrNull { entry ->
                val qualifierEntry = entry.dpQualifierEntry
                val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType
                
                // EN Checks if the entry orientation matches the actual screen orientation
                // PT Verifica se a orientação da entrada bate com a orientação atual da tela
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
        }

        // EN Determines the Dp value to be used: custom or the initial base.
        // PT Determina o valor de Dp a ser usado: customizado ou o base inicial.
        val dpToUse: Dp = foundEntry?.customValue ?: initialBaseDp

        // EN Applies dynamic scaling to the base/custom value,
        // using the 'qualifier' passed explicitly by the access property
        // or the specific 'finalQualifierResolver' from the custom entry if defined.
        // PT Aplica o dimensionamento dinâmico ao valor base/customizado,
        // usando o 'qualifier' passado explicitamente pela propriedade de acesso
        // ou o 'finalQualifierResolver' específico da entrada customizada, se definido.
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier

        val baseIntDp = dpToUse.value.toInt()
        return baseIntDp.toDynamicDiagonalDp(finalQualifier, foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun resolvePx(qualifier: DpQualifier): Float {
        val context = LocalContext.current
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
                    false
                } else {
                    if (entry.priority == 2 && uiModeMatch && orientationMatch) return@firstOrNull true
                    if (entry.priority == 4 && orientationMatch) return@firstOrNull true
                    false
                }
            }
        }

        val dpToUse: Dp = foundEntry?.customValue ?: initialBaseDp
        val finalQualifier = foundEntry?.finalQualifierResolver ?: qualifier
        val baseIntDp = dpToUse.value.toInt()
        return baseIntDp.toDynamicDiagonalPx(finalQualifier, foundEntry?.inverter ?: Inverter.DEFAULT, ignoreMultiWindows, applyAspectRatio, customSensitivityK)
    }

    /**
     * EN The final Dp value resolved in Compose (Smallest Width).
     * PT O valor final Dp resolvido no Compose (Smallest Width).
     */
    @get:Composable
    val sdp: Dp get() = resolve(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final Dp value resolved in Compose (Screen Height).
     * PT O valor final Dp resolvido no Compose (Altura da Tela).
     */
    @get:Composable
    val hdp: Dp get() = resolve(DpQualifier.HEIGHT)

    /**
     * EN The final Dp value resolved in Compose (Screen Width).
     * PT O valor final Dp resolvido no Compose (Largura da Tela).
     */
    @get:Composable
    val wdp: Dp get() = resolve(DpQualifier.WIDTH)

    /**
     * EN The final Pixel (Float) value resolved in Compose (Smallest Width).
     * PT O valor final em Pixels (Float) resolvido no Compose (Smallest Width).
     */
    @get:Composable
    val sdpPx: Float get() = resolvePx(DpQualifier.SMALL_WIDTH)

    /**
     * EN The final Pixel (Float) value resolved in Compose (Screen Height).
     * PT O valor final em Pixels (Float) resolvido no Compose (Altura da Tela).
     */
    @get:Composable
    val hdpPx: Float get() = resolvePx(DpQualifier.HEIGHT)

    /**
     * EN The final Pixel (Float) value resolved in Compose (Screen Width).
     * PT O valor final em Pixels (Float) resolvido no Compose (Largura da Tela).
     */
    @get:Composable
    val wdpPx: Float get() = resolvePx(DpQualifier.WIDTH)
}