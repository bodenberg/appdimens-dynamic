//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[sdpQualifierPlain](sdp-qualifier-plain.md)

# sdpQualifierPlain

fun ERROR CLASS: Symbol not found for Dp.[sdpQualifierPlain](sdp-qualifier-plain.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the screen metric for [qualifierType](sdp-qualifier-plain.md) is >= [qualifierValue](sdp-qualifier-plain.md), it uses [qualifiedValue](sdp-qualifier-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando a métrica de tela para [qualifierType](sdp-qualifier-plain.md) é >= [qualifierValue](sdp-qualifier-plain.md), usa [qualifiedValue](sdp-qualifier-plain.md) no lugar.

EN Plain sdp qualifier: [qualified](sdp-qualifier-plain.md) and receiver already scaled; logic only ([qualifierValue](sdp-qualifier-plain.md) is config threshold). PT Qualificador sdp Plain: [qualified](sdp-qualifier-plain.md) e recetor já escalados; só a lógica.
