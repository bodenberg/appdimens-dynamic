//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[prsdpQualifier](prsdp-qualifier.md)

# prsdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[prsdpQualifier](prsdp-qualifier.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the screen metric for [qualifierType](prsdp-qualifier.md) is >= [qualifierValue](prsdp-qualifier.md), it uses [qualifiedValue](prsdp-qualifier.md) instead. Usage example: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)` → 30.sdp by default, 50.sdp when smallestScreenWidthDp >= 600.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](prsdp-qualifier.md) é >= [qualifierValue](prsdp-qualifier.md), usa [qualifiedValue](prsdp-qualifier.md) no lugar. Exemplo de uso: `30.sdpQualifier(50, DpQualifier.SMALL_WIDTH, 600)` → 30.sdp por padrão, 50.sdp quando smallestScreenWidthDp >= 600.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the screen metric for [qualifierType](prsdp-qualifier.md) is >= [qualifierValue](prsdp-qualifier.md), it uses [qualifiedValue](prsdp-qualifier.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando a métrica de tela para [qualifierType](prsdp-qualifier.md) é >= [qualifierValue](prsdp-qualifier.md), usa [qualifiedValue](prsdp-qualifier.md) no lugar.
