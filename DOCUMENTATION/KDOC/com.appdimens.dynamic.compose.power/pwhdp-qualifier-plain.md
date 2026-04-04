//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.power](index.md)/[pwhdpQualifierPlain](pwhdp-qualifier-plain.md)

# pwhdpQualifierPlain

[jvm]
fun <Error class: unknown class>.[pwhdpQualifierPlain](pwhdp-qualifier-plain.md)(qualifiedValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original raw Dp value if the condition is not met. When the screen metric for [qualifierType](pwhdp-qualifier-plain.md) is >= [qualifierValue](pwhdp-qualifier-plain.md), it uses [qualifiedValue](pwhdp-qualifier-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando a métrica de tela para [qualifierType](pwhdp-qualifier-plain.md) é >= [qualifierValue](pwhdp-qualifier-plain.md), usa [qualifiedValue](pwhdp-qualifier-plain.md) no lugar.