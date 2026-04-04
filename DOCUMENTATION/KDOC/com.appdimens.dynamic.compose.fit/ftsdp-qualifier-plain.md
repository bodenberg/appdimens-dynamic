//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fit](index.md)/[ftsdpQualifierPlain](ftsdp-qualifier-plain.md)

# ftsdpQualifierPlain

[jvm]
fun <Error class: unknown class>.[ftsdpQualifierPlain](ftsdp-qualifier-plain.md)(qualifiedValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the screen metric for [qualifierType](ftsdp-qualifier-plain.md) is >= [qualifierValue](ftsdp-qualifier-plain.md), it uses [qualifiedValue](ftsdp-qualifier-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando a métrica de tela para [qualifierType](ftsdp-qualifier-plain.md) é >= [qualifierValue](ftsdp-qualifier-plain.md), usa [qualifiedValue](ftsdp-qualifier-plain.md) no lugar.