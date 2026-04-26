//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.power](../index.md)/[PowerSp](index.md)

# PowerSp

class [PowerSp](index.md)(val initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), val defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A Stable Compose class that allows defining custom Sp text dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The TextUnit is resolved at composition and uses the base value or a custom value, applying dynamic scaling via the existing XML DP resources.

PT Classe Stable do Compose que permite definir dimensões de texto Sp customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

O TextUnit é resolvido na composição e usa o valor base ou um valor customizado, aplicando o escalonamento dinâmico via os recursos XML de DP existentes.

private constructor(initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [defaultFontScale](default-font-scale.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseValue](initial-base-value.md): [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)

val [pwhem](pwhem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Height (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).

val [pwhemPx](pwhem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Height (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).

val [pwhsp](pwhsp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Height (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (COM escala de fonte).

val [pwhspPx](pwhsp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Height (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Altura da Tela (COM escala de fonte).

val [pwsem](pwsem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Smallest Width (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Smallest Width (SEM ESCALA DE FONTE).

val [pwsemPx](pwsem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Smallest Width (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Smallest Width (SEM ESCALA DE FONTE).

val [pwssp](pwssp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Smallest Width (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Smallest Width (COM escala de fonte).

val [pwsspPx](pwssp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Smallest Width (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Smallest Width (COM escala de fonte).

val [pwwem](pwwem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Width (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).

val [pwwemPx](pwwem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Width (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).

val [pwwsp](pwwsp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Width (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (COM escala de fonte).

val [pwwspPx](pwwsp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Width (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Largura da Tela (COM escala de fonte).

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [PowerSp](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o redimensionamento baseado na proporção da tela.

fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [PowerSp](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomSpEntry](../-custom-sp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

EN Adds a new entry and re-sorts the list by priority, then by qualifier value (descending).

private fun [resolve](resolve.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): ERROR CLASS: Symbol not found for TextUnit

EN Resolves the matching [CustomSpEntry](../-custom-sp-entry/index.md) and returns scaled TextUnit (Sp) for [qualifier](resolve.md). PT Resolve a [CustomSpEntry](../-custom-sp-entry/index.md) correspondente e retorna TextUnit (Sp) escalonado para [qualifier](resolve.md).

private fun [resolveNoFontScale](resolve-no-font-scale.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): ERROR CLASS: Symbol not found for TextUnit

EN Like [resolve](resolve.md) but forces `fontScale = false` (fixed Sp, same idea as `pwsem` / `pwwem` accessors). PT Como [resolve](resolve.md), mas força `fontScale = false` (Sp fixo, mesmo propósito dos acessores `pwsem` / `pwwem`).

private fun [resolveNoFontScalePx](resolve-no-font-scale-px.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Like [resolvePx](resolve-px.md) with `fontScale = false`. PT Como [resolvePx](resolve-px.md) com `fontScale = false`.

private fun [resolvePx](resolve-px.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Same as [resolve](resolve.md) but returns density-scaled pixels ([toDynamicPowerPx](../to-dynamic-power-px.md)). PT Igual a [resolve](resolve.md), mas retorna pixels já escalados pela densidade ([toDynamicPowerPx](../to-dynamic-power-px.md)).

fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = defaultFontScale): [PowerSp](index.md)

EN Priority 4: Orientation only. PT Prioridade 4: Apenas Orientação.

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).

## Constructors

| Name | Summary |
|---|---|
| [PowerSp](-power-sp.md) |  |


## Properties

| Name | Summary |
|---|---|
| [applyAspectRatio](apply-aspect-ratio.md) |  |
| [customSensitivityK](custom-sensitivity-k.md) |  |
| [defaultFontScale](default-font-scale.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [initialBaseValue](initial-base-value.md) |  |
| [pwhem](pwhem.md) |  |
| [pwhemPx](pwhem-px.md) |  |
| [pwhsp](pwhsp.md) |  |
| [pwhspPx](pwhsp-px.md) |  |
| [pwsem](pwsem.md) |  |
| [pwsemPx](pwsem-px.md) |  |
| [pwssp](pwssp.md) |  |
| [pwsspPx](pwssp-px.md) |  |
| [pwwem](pwwem.md) |  |
| [pwwemPx](pwwem-px.md) |  |
| [pwwsp](pwwsp.md) |  |
| [pwwspPx](pwwsp-px.md) |  |
| [sortedCustomEntries](sorted-custom-entries.md) |  |


## Functions

| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [reorderEntries](reorder-entries.md) |  |
| [resolve](resolve.md) |  |
| [resolveNoFontScale](resolve-no-font-scale.md) |  |
| [resolveNoFontScalePx](resolve-no-font-scale-px.md) |  |
| [resolvePx](resolve-px.md) |  |
| [screen](screen.md) |  |
