//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.interpolated](index.md)/[toDynamicInterpolatedSp](to-dynamic-interpolated-sp.md)

# toDynamicInterpolatedSp

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[toDynamicInterpolatedSp](to-dynamic-interpolated-sp.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Converts a [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (base value) into a dynamically scaled TextUnit (Sp) for Jetpack Compose.

Scaling logic:

1. 
   Builds a 64-bit packed cache key.
2. 
   If [fontScale](to-dynamic-interpolated-sp.md) is `true`, the result respects the system font size setting.
3. 
   If [fontScale](to-dynamic-interpolated-sp.md) is `false` (e.g. via .isem), the system font scale is stripped.
4. 
   Checks [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) globally.

PT Converte um [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) (valor base) em um TextUnit (Sp) dinamicamente escalado para Compose.

Lógica de escalonamento:

1. 
   Constrói uma chave de cache de 64 bits.
2. 
   Se [fontScale](to-dynamic-interpolated-sp.md) for `true`, o resultado respeita a configuração de tamanho de fonte do sistema.
3. 
   Se [fontScale](to-dynamic-interpolated-sp.md) for `false` (ex: via .isem), a escala de fonte do sistema é removida.
4. 
   Consulta o [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) globalmente.

#### Return

Dynamically scaled TextUnit value.

#### Parameters

jvm

| | |
|---|---|
| qualifier | Screen dimension qualifier. |
| fontScale | Whether to respect the user's system font scale. |
| inverter | Orientation-based dimension swap rule. |
| ignoreMultiWindows | If `true`, returns base value unscaled when in split-screen. |
| applyAspectRatio | If `true`, applies the aspect-ratio multiplier. |
| customSensitivityK | Custom AR sensitivity constant. |