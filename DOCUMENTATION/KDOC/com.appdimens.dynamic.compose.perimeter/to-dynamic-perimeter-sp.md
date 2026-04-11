//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[toDynamicPerimeterSp](to-dynamic-perimeter-sp.md)

# toDynamicPerimeterSp

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicPerimeterSp](to-dynamic-perimeter-sp.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Converts a [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (base value) into a dynamically scaled TextUnit (Sp) for Jetpack Compose.

Scaling logic:

Builds a 64-bit packed cache key.

If [fontScale](to-dynamic-perimeter-sp.md) is `true`, the result respects the system font size setting.

If [fontScale](to-dynamic-perimeter-sp.md) is `false` (e.g. via .prsem), the system font scale is stripped.

Checks [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) globally.

PT Converte um [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (valor base) em um TextUnit (Sp) dinamicamente escalado para Compose.

Lógica de escalonamento:

Constrói uma chave de cache de 64 bits.

Se [fontScale](to-dynamic-perimeter-sp.md) for `true`, o resultado respeita a configuração de tamanho de fonte do sistema.

Se [fontScale](to-dynamic-perimeter-sp.md) for `false` (ex: via .prsem), a escala de fonte do sistema é removida.

Consulta o [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) globalmente.

Dynamically scaled TextUnit value.

Screen dimension qualifier.

Whether to respect the user's system font scale.

Orientation-based dimension swap rule.

If `true`, returns base value unscaled when in split-screen.

If `true`, applies the aspect-ratio multiplier.

Custom AR sensitivity constant.
