//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.logarithmic](../index.md)/[DimenLogarithmic](index.md)

# DimenLogarithmic

class [DimenLogarithmic](index.md)(val initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The value is resolved using a Context and uses the base value or a custom value, applying dynamic scaling at the end.

PT Classe que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

private constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseDp](initial-base-dp.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [applyAspectRatio](apply-aspect-ratio.md)(apply: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DimenLogarithmic](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o escalonamento restrito baseado na proporção da tela (aspect ratio).

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DimenLogarithmic](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o escalonamento restrito baseado nas propriedades de redimensionamento de multi-janelas.

fun [loghdp](loghdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [loghdpBase](loghdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [logsdp](logsdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [logsdpBase](logsdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Get the resolved value in DP (as Float).

fun [logsdpLoghdpLogwdpPx](logsdp-loghdp-logwdp-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Triple](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/index.html)<[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)>

EN Resolves logsdp, loghdp, and logwdp in one pass (single [UiModeType.fromConfiguration](../../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) and config read). PT Resolve logsdp, loghdp e logwdp numa só passagem.

fun [logwdp](logwdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [logwdpBase](logwdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [px](px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolves the final value in pixels (Float).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomDpEntry](../-custom-dp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

private fun [resolveDp](resolve-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

private fun [resolveDpInternal](resolve-dp-internal.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), currentUiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT): [DimenLogarithmic](index.md)

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
| [sortedCustomEntries](sorted-custom-entries.md) |  |


## Functions

| Name | Summary |
|---|---|
| [applyAspectRatio](apply-aspect-ratio.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [loghdp](loghdp.md) |  |
| [loghdpBase](loghdp-base.md) |  |
| [logsdp](logsdp.md) |  |
| [logsdpBase](logsdp-base.md) |  |
| [logsdpLoghdpLogwdpPx](logsdp-loghdp-logwdp-px.md) |  |
| [logwdp](logwdp.md) |  |
| [logwdpBase](logwdp-base.md) |  |
| [px](px.md) |  |
| [reorderEntries](reorder-entries.md) |  |
| [resolveDp](resolve-dp.md) |  |
| [resolveDpInternal](resolve-dp-internal.md) |  |
| [screen](screen.md) |  |
