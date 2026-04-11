//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.density](../index.md)/[DensitySp](index.md)

# DensitySp

class [DensitySp](index.md)(val initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), val defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A class that allows defining custom Sp text dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The value is resolved using a Context and uses the base value or a custom value, applying dynamic scaling.

PT Classe que permite definir dimensões de texto Sp customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

private constructor(initialBaseValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), defaultFontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [defaultFontScale](default-font-scale.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseValue](initial-base-value.md): [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [DensitySp](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o redimensionamento baseado na proporção da tela.

fun [dhem](dhem.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [dhsp](dhsp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [dsem](dsem.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolve final value in pixels (WITHOUT font scale).

fun [dsemDhemDwemPx](dsem-dhem-dwem-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Triple](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/index.html)<[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)>

EN Resolves dsem, dhem, and dwem in one pass (fixed Sp / no font-scale path). PT Resolve dsem, dhem e dwem numa só passagem (Sp fixo / sem escala de fonte).

fun [dssp](dssp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolve final value in pixels (WITH font scale).

fun [dsspDhspDwspPx](dssp-dhsp-dwsp-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Triple](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/index.html)<[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)>

EN Resolves dssp, dhsp, and dwsp in one pass (single [UiModeType.fromConfiguration](../../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) read). PT Resolve dssp, dhsp e dwsp numa só passagem.

fun [dwem](dwem.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [dwsp](dwsp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DensitySp](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o dimensionamento para os layouts de múltiplas janelas (divisão de tela).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomSpEntry](../-custom-sp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomSpEntry](../-custom-sp-entry/index.md)>

EN Adds a new entry and re-sorts the list by priority, then by qualifier value (descending).

private fun [resolvePx](resolve-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScaleOverride: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolves [qualifier](resolve-px.md) to px using the first matching [CustomSpEntry](../-custom-sp-entry/index.md), optionally overriding font scale. PT Resolve [qualifier](resolve-px.md) em px usando a primeira [CustomSpEntry](../-custom-sp-entry/index.md) correspondente, com override opcional da escala de fonte.

private fun [resolvePxInternal](resolve-px-internal.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), currentUiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), fontScaleOverride: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Shared implementation for [resolvePx](resolve-px.md), [dsspDhspDwspPx](dssp-dhsp-dwsp-px.md), and [dsemDhemDwemPx](dsem-dhem-dwem-px.md). PT Implementação compartilhada para [resolvePx](resolve-px.md), [dsspDhspDwspPx](dssp-dhsp-dwsp-px.md) e [dsemDhemDwemPx](dsem-dhem-dwem-px.md).

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = defaultFontScale): [DensitySp](index.md)

EN Priority 4: orientation only. PT Prioridade 4: apenas orientação.

EN Priority 2: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) only (e.g. TELEVISION, WATCH). PT Prioridade 2: apenas [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) (ex.: TELEVISION, WATCH).

EN Priority 3: Dp qualifier (sw, h, w) without [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) restriction. PT Prioridade 3: qualificador Dp (sw, h, w) sem restrição de [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md).

EN Priority 1: Most specific qualifier — combines [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) and Dp qualifier (sw, h, w). PT Prioridade 1: qualificador mais específico — combina [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) e qualificador Dp (sw, h, w).

## Constructors

| Name | Summary |
|---|---|
| [DensitySp](-density-sp.md) |  |


## Properties

| Name | Summary |
|---|---|
| [applyAspectRatio](apply-aspect-ratio.md) |  |
| [customSensitivityK](custom-sensitivity-k.md) |  |
| [defaultFontScale](default-font-scale.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [initialBaseValue](initial-base-value.md) |  |
| [sortedCustomEntries](sorted-custom-entries.md) |  |


## Functions

| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.md) |  |
| [dhem](dhem.md) |  |
| [dhsp](dhsp.md) |  |
| [dsem](dsem.md) |  |
| [dsemDhemDwemPx](dsem-dhem-dwem-px.md) |  |
| [dssp](dssp.md) |  |
| [dsspDhspDwspPx](dssp-dhsp-dwsp-px.md) |  |
| [dwem](dwem.md) |  |
| [dwsp](dwsp.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [reorderEntries](reorder-entries.md) |  |
| [resolvePx](resolve-px.md) |  |
| [resolvePxInternal](resolve-px-internal.md) |  |
| [screen](screen.md) |  |
