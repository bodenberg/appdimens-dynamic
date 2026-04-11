//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.logarithmic](../index.md)/[DimenLogarithmic](index.md)

# DimenLogarithmic

class [DimenLogarithmic](index.md)(val initialBaseDp: ERROR CLASS: Symbol not found for Dp, val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A Stable class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The Dp value is resolved at composition (Compose) and uses the base value or a custom value, applying dynamic scaling at the end.

PT Classe Stable que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

O valor Dp é resolvido na composição (Compose) e usa o valor base ou um valor customizado, aplicando o dimensionamento dinâmico no final.

private constructor(initialBaseDp: ERROR CLASS: Symbol not found for Dp, sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseDp](initial-base-dp.md): ERROR CLASS: Symbol not found for Dp

val [loghdp](loghdp.md): ERROR CLASS: Symbol not found for Dp

EN The final Dp value resolved in Compose (Screen Height). PT O valor final Dp resolvido no Compose (Altura da Tela).

val [loghdpPx](loghdp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved in Compose (Screen Height). PT O valor final em Pixels (Float) resolvido no Compose (Altura da Tela).

val [logsdp](logsdp.md): ERROR CLASS: Symbol not found for Dp

EN The final Dp value resolved in Compose (Smallest Width). PT O valor final Dp resolvido no Compose (Smallest Width).

val [logsdpPx](logsdp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved in Compose (Smallest Width). PT O valor final em Pixels (Float) resolvido no Compose (Smallest Width).

val [logwdp](logwdp.md): ERROR CLASS: Symbol not found for Dp

EN The final Dp value resolved in Compose (Screen Width). PT O valor final Dp resolvido no Compose (Largura da Tela).

val [logwdpPx](logwdp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved in Compose (Screen Width). PT O valor final em Pixels (Float) resolvido no Compose (Largura da Tela).

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [DimenLogarithmic](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o redimensionamento baseado na proporção da tela.

fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DimenLogarithmic](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomDpEntry](../-custom-dp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

EN Adds a new entry and re-sorts the list. Sorting is crucial: first by priority (ascending), and then by dpQualifierEntry.value (descending) so that larger qualifiers (e.g., sw600dp) are checked before smaller qualifiers (e.g., sw320dp).

private fun [resolve](resolve.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): ERROR CLASS: Symbol not found for Dp

EN Resolves the final dimension. This is the Composable part that reads the current configuration and decides which Dp to use.

private fun [resolvePx](resolve-px.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: ERROR CLASS: Symbol not found for Dp, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT): [DimenLogarithmic](index.md)

EN Priority 4: Orientation. This is an overload that accepts an Int for `customValue`.

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). This is an overload that accepts an Int for `customValue`.

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. This is an overload that accepts an Int for `customValue`.

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). This is an overload that accepts an Int for `customValue`.

## Constructors

| Name | Summary |
|---|---|
| [DimenLogarithmic](-dimen-logarithmic.md) |  |


## Properties

| Name | Summary |
|---|---|
| [applyAspectRatio](apply-aspect-ratio.md) |  |
| [customSensitivityK](custom-sensitivity-k.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [initialBaseDp](initial-base-dp.md) |  |
| [loghdp](loghdp.md) |  |
| [loghdpPx](loghdp-px.md) |  |
| [logsdp](logsdp.md) |  |
| [logsdpPx](logsdp-px.md) |  |
| [logwdp](logwdp.md) |  |
| [logwdpPx](logwdp-px.md) |  |
| [sortedCustomEntries](sorted-custom-entries.md) |  |


## Functions

| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [reorderEntries](reorder-entries.md) |  |
| [resolve](resolve.md) |  |
| [resolvePx](resolve-px.md) |  |
| [screen](screen.md) |  |
