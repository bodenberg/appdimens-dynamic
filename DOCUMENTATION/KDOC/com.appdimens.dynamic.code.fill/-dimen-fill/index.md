//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.fill](../index.md)/[DimenFill](index.md)

# DimenFill

class [DimenFill](index.md)(val initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), val ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

EN A class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The value is resolved using a Context and uses the base value or a custom value, applying dynamic scaling at the end.

PT Classe que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

private constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), sortedCustomEntries: [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)> = emptyList(), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null)

private val [applyAspectRatio](apply-aspect-ratio.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [customSensitivityK](custom-sensitivity-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

private val [ignoreMultiWindows](ignore-multi-windows.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

private val [initialBaseDp](initial-base-dp.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

private val [sortedCustomEntries](sorted-custom-entries.md): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [applyAspectRatio](apply-aspect-ratio.md)(apply: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DimenFill](index.md)

EN Allow applying aspect ratio based constraint scaling. PT Permite aplicar o escalonamento restrito baseado na proporção da tela (aspect ratio).

fun [flhdp](flhdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [flhdpBase](flhdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [flsdp](flsdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [flsdpBase](flsdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Get the resolved value in DP (as Float).

fun [flsdpFlhdpFlwdpPx](flsdp-flhdp-flwdp-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Triple](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-triple/index.html)<[Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)>

EN Resolves flsdp, flhdp, and flwdp in one pass (single [UiModeType.fromConfiguration](../../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) and config read). PT Resolve flsdp, flhdp e flwdp numa só passagem.

fun [flwdp](flwdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [flwdpBase](flwdp-base.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ignoreMultiWindows](ignore-multi-windows.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [DimenFill](index.md)

EN Allow ignoring the constraint scaling based on multi-window resizing properties. PT Permite ignorar o escalonamento restrito baseado nas propriedades de redimensionamento de multi-janelas.

fun [px](px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolves the final value in pixels (Float).

private fun [reorderEntries](reorder-entries.md)(newEntry: [CustomDpEntry](../-custom-dp-entry/index.md)): [List](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-list/index.html)<[CustomDpEntry](../-custom-dp-entry/index.md)>

private fun [resolveDp](resolve-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

private fun [resolveDpInternal](resolve-dp-internal.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), currentUiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [screen](screen.md)(orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT): [DimenFill](index.md)

## Constructors

| Name | Summary |
|---|---|
| [DimenFill](-dimen-fill.md) |  |


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
| [flhdp](flhdp.md) |  |
| [flhdpBase](flhdp-base.md) |  |
| [flsdp](flsdp.md) |  |
| [flsdpBase](flsdp-base.md) |  |
| [flsdpFlhdpFlwdpPx](flsdp-flhdp-flwdp-px.md) |  |
| [flwdp](flwdp.md) |  |
| [flwdpBase](flwdp-base.md) |  |
| [ignoreMultiWindows](ignore-multi-windows.md) |  |
| [px](px.md) |  |
| [reorderEntries](reorder-entries.md) |  |
| [resolveDp](resolve-dp.md) |  |
| [resolveDpInternal](resolve-dp-internal.md) |  |
| [screen](screen.md) |  |
