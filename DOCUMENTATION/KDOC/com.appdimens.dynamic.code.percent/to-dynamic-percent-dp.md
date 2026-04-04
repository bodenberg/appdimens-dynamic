//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.percent](index.md)/[toDynamicPercentDp](to-dynamic-percent-dp.md)

# toDynamicPercentDp

[jvm]
@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[toDynamicPercentDp](to-dynamic-percent-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (base Dp value) into a dynamically scaled Dp [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) for View-based (non-Compose) code.

Unlike [toDynamicPercentPx](to-dynamic-percent-px.md), the result is returned in Dp units — no density conversion is applied. This is useful for APIs that accept logical Dp values directly (e.g. `View.setPadding` with a custom Dp-aware layout engine).

Same caching, validation, and bypass semantics as [toDynamicPercentPx](to-dynamic-percent-px.md).

PT Converte um [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (valor Dp base) em um [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) em Dp dinamicamente escalado para código View-based.

Ao contrário de [toDynamicPercentPx](to-dynamic-percent-px.md), o resultado é retornado em unidades Dp — sem conversão de densidade. Útil para APIs que aceitam valores Dp lógicos diretamente.

Mesma semântica de cache, validação e bypass de [toDynamicPercentPx](to-dynamic-percent-px.md).

#### Return

Dynamically scaled Dp value as [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html).

#### Parameters

jvm

| | |
|---|---|
| context | Android [android.content.Context](https://developer.android.com/reference/kotlin/android/content/Context.html) for configuration access. |
| qualifier | Screen dimension qualifier. |
| inverter | Orientation-based dimension swap rule (default: [Inverter.DEFAULT](../com.appdimens.dynamic.common/-inverter/-d-e-f-a-u-l-t/index.md)). |
| ignoreMultiWindows | If `true`, returns the base Dp value unscaled when in split-screen. |
| applyAspectRatio | If `true`, applies the aspect-ratio multiplier. |
| customSensitivityK | Override for the AR sensitivity constant (null = library default). |