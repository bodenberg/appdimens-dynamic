//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.diagonal](index.md)/[dgwdpQualifierPlain](dgwdp-qualifier-plain.md)

# dgwdpQualifierPlain

[jvm]
fun <Error class: unknown class>.[dgwdpQualifierPlain](dgwdp-qualifier-plain.md)(qualifiedValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Returns the original raw Dp value if the condition is not met. When the screen metric for [qualifierType](dgwdp-qualifier-plain.md) is >= [qualifierValue](dgwdp-qualifier-plain.md), it uses [qualifiedValue](dgwdp-qualifier-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando a métrica de tela para [qualifierType](dgwdp-qualifier-plain.md) é >= [qualifierValue](dgwdp-qualifier-plain.md), usa [qualifiedValue](dgwdp-qualifier-plain.md) no lugar.