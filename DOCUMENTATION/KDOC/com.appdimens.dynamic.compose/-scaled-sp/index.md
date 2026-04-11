//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose](../index.md)/[ScaledSp](index.md)

# ScaledSp

class [ScaledSp](index.md)(val initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), val defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A Stable Compose class that allows defining custom Sp text dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The TextUnit is resolved at composition and uses the base value or a custom value, applying dynamic scaling via the existing XML DP resources.

PT Classe Stable do Compose que permite definir dimensões de texto Sp customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

O TextUnit é resolvido na composição e usa o valor base ou um valor customizado, aplicando o escalonamento dinâmico via os recursos XML de DP existentes.

private constructor(initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [defaultFontScale](default-font-scale.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

val [hem](hem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Height (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).

val [hemPx](hem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Height (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Altura da Tela (SEM ESCALA DE FONTE).

val [hsp](hsp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Height (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Altura da Tela (COM escala de fonte).

val [hspPx](hsp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Height (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Altura da Tela (COM escala de fonte).

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseValue](initial-base-value.md): [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)

val [sem](sem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Smallest Width (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Smallest Width (SEM ESCALA DE FONTE).

val [semPx](sem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Smallest Width (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Smallest Width (SEM ESCALA DE FONTE).

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

val [ssp](ssp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Smallest Width (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Smallest Width (COM escala de fonte).

val [sspPx](ssp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Smallest Width (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Smallest Width (COM escala de fonte).

val [wem](wem.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Width (WITHOUT FONT SCALE). PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).

val [wemPx](wem-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Width (WITHOUT FONT SCALE). PT O valor final em Pixels (Float) resolvido usando Largura da Tela (SEM ESCALA DE FONTE).

val [wsp](wsp.md): ERROR CLASS: Symbol not found for TextUnit

EN The final TextUnit (Sp) value resolved using Screen Width (WITH font scale). PT O valor final TextUnit (Sp) resolvido usando Largura da Tela (COM escala de fonte).

val [wspPx](wsp-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN The final Pixel (Float) value resolved using Screen Width (WITH font scale). PT O valor final em Pixels (Float) resolvido usando Largura da Tela (COM escala de fonte).

fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [ScaledSp](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o redimensionamento baseado na proporção da tela.

fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [ScaledSp](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomSpEntry](../-custom-sp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

EN Adds a new entry and re-sorts the list by priority, then by qualifier value (descending).

private fun [resolve](resolve.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): ERROR CLASS: Symbol not found for TextUnit

EN Resolves the matching [CustomSpEntry](../-custom-sp-entry/index.md) and returns scaled TextUnit (Sp) for [qualifier](resolve.md). PT Resolve a [CustomSpEntry](../-custom-sp-entry/index.md) correspondente e retorna TextUnit (Sp) escalonado para [qualifier](resolve.md).

private fun [resolveNoFontScale](resolve-no-font-scale.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): ERROR CLASS: Symbol not found for TextUnit

EN Like [resolve](resolve.md) but forces `fontScale = false` (fixed Sp, same idea as `sem` / `wem` accessors). PT Como [resolve](resolve.md), mas força `fontScale = false` (Sp fixo, mesmo propósito dos acessores `sem` / `wem`).

private fun [resolveNoFontScalePx](resolve-no-font-scale-px.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Like [resolvePx](resolve-px.md) with `fontScale = false`. PT Como [resolvePx](resolve-px.md) com `fontScale = false`.

private fun [resolvePx](resolve-px.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Same as [resolve](resolve.md) but returns density-scaled pixels ([toDynamicScaledPx](../to-dynamic-scaled-px.md)). PT Igual a [resolve](resolve.md), mas retorna pixels já escalados pela densidade ([toDynamicScaledPx](../to-dynamic-scaled-px.md)).

fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = defaultFontScale): [ScaledSp](index.md)

EN Priority 4: Orientation only. PT Prioridade 4: Apenas Orientação.

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).

## Constructors

| Name | Summary |
|---|---|
| [ScaledSp](-scaled-sp.md) |  |


## Properties

| Name | Summary |
|---|---|
| [applyAspectRatio](apply-aspect-ratio.md) |  |
| [customSensitivityK](custom-sensitivity-k.md) |  |
| [defaultFontScale](default-font-scale.md) |  |
| [hem](hem.md) |  |
| [hemPx](hem-px.md) |  |
| [hsp](hsp.md) |  |
| [hspPx](hsp-px.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [initialBaseValue](initial-base-value.md) |  |
| [sem](sem.md) |  |
| [semPx](sem-px.md) |  |
| [sortedCustomEntries](sorted-custom-entries.md) |  |
| [ssp](ssp.md) |  |
| [sspPx](ssp-px.md) |  |
| [wem](wem.md) |  |
| [wemPx](wem-px.md) |  |
| [wsp](wsp.md) |  |
| [wspPx](wsp-px.md) |  |


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
