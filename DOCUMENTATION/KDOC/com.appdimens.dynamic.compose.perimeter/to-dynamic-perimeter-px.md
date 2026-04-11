//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[toDynamicPerimeterPx](to-dynamic-perimeter-px.md)

# toDynamicPerimeterPx

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicPerimeterPx](to-dynamic-perimeter-px.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (base Dp value) into a dynamically scaled pixel [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) for Jetpack Compose.

Same semantics as [toDynamicPerimeterDp](to-dynamic-perimeter-dp.md), but the result is multiplied by the current display density (LocalDensity).

PT Converte um [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (valor Dp base) em um [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) em pixels dinamicamente escalado para Compose.

Mesma semântica de cache e bypass de [toDynamicPerimeterDp](to-dynamic-perimeter-dp.md), mas o resultado é multiplicado pela densidade do display atual (LocalDensity) para entregar um valor pronto em pixels. Útil quando um valor em pixels brutos é necessário (ex: desenho em `Canvas` ou `Modifier.offset`).

Dynamically scaled pixel value as [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).

Screen dimension qualifier: [DpQualifier.SMALL_WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-s-m-a-l-l_-w-i-d-t-h/index.md), [DpQualifier.HEIGHT](../com.appdimens.dynamic.common/-dp-qualifier/-h-e-i-g-h-t/index.md), or [DpQualifier.WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-w-i-d-t-h/index.md).

Orientation-based dimension swap rule (default: [Inverter.DEFAULT](../com.appdimens.dynamic.common/-inverter/-d-e-f-a-u-l-t/index.md)).

If `true`, returns base value in pixels unscaled when in split-screen.

If `true`, applies the aspect-ratio multiplier.

Override for the AR sensitivity constant (null = library default).

EN Converts a [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (base value) into a dynamically scaled pixel [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) for Compose.

Similar to [toDynamicPerimeterSp](to-dynamic-perimeter-sp.md), but the result is multiplied by density to return raw pixels. Often used for direct Canvas operations or custom Modifier logic.

PT Converte um [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (valor base) em um [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) em pixels dinamicamente escalado para Compose.

Similar a [toDynamicPerimeterSp](to-dynamic-perimeter-sp.md), mas o resultado é multiplicado pela densidade para retornar pixels brutos. Frequentemente usado para operações diretas de Canvas ou lógica de Modifier.

Dynamically scaled pixel value.

Screen dimension qualifier.

Whether to respect the user's system font scale.

Orientation-based swap rule.

If `true`, returns base value unscaled in pixels in split-screen.

If `true`, applies the aspect-ratio multiplier.

Custom AR sensitivity constant.
