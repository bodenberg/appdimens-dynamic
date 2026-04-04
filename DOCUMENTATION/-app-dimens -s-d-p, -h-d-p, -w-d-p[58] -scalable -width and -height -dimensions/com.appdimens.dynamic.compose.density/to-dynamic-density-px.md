//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.density](index.md)/[toDynamicDensityPx](to-dynamic-density-px.md)

# toDynamicDensityPx

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[toDynamicDensityPx](to-dynamic-density-px.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (base Dp value) into a dynamically scaled pixel [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) for Jetpack Compose.

Same semantics as [toDynamicDensityDp](to-dynamic-density-dp.md), but the result is multiplied by the current display density (LocalDensity).

PT Converte um [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (valor Dp base) em um [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) em pixels dinamicamente escalado para Compose.

Mesma semântica de cache e bypass de [toDynamicDensityDp](to-dynamic-density-dp.md), mas o resultado é multiplicado pela densidade do display atual (LocalDensity) para entregar um valor pronto em pixels. Útil quando um valor em pixels brutos é necessário (ex: desenho em `Canvas` ou `Modifier.offset`).

#### Return

Dynamically scaled pixel value as [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html).

#### Parameters

jvm

| | |
|---|---|
| qualifier | Screen dimension qualifier: [DpQualifier.SMALL_WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-s-m-a-l-l_-w-i-d-t-h/index.md), [DpQualifier.HEIGHT](../com.appdimens.dynamic.common/-dp-qualifier/-h-e-i-g-h-t/index.md), or [DpQualifier.WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-w-i-d-t-h/index.md). |
| inverter | Orientation-based dimension swap rule (default: [Inverter.DEFAULT](../com.appdimens.dynamic.common/-inverter/-d-e-f-a-u-l-t/index.md)). |
| ignoreMultiWindows | If `true`, returns base value in pixels unscaled when in split-screen. |
| applyAspectRatio | If `true`, applies the aspect-ratio multiplier. |
| customSensitivityK | Override for the AR sensitivity constant (null = library default). |

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[toDynamicDensityPx](to-dynamic-density-px.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (base value) into a dynamically scaled pixel [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) for Compose.

Similar to [toDynamicDensitySp](to-dynamic-density-sp.md), but the result is multiplied by density to return raw pixels. Often used for direct Canvas operations or custom Modifier logic.

PT Converte um [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (valor base) em um [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) em pixels dinamicamente escalado para Compose.

Similar a [toDynamicDensitySp](to-dynamic-density-sp.md), mas o resultado é multiplicado pela densidade para retornar pixels brutos. Frequentemente usado para operações diretas de Canvas ou lógica de Modifier.

#### Return

Dynamically scaled pixel value.

#### Parameters

jvm

| | |
|---|---|
| qualifier | Screen dimension qualifier. |
| fontScale | Whether to respect the user's system font scale. |
| inverter | Orientation-based swap rule. |
| ignoreMultiWindows | If `true`, returns base value unscaled in pixels in split-screen. |
| applyAspectRatio | If `true`, applies the aspect-ratio multiplier. |
| customSensitivityK | Custom AR sensitivity constant. |