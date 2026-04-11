//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.diagonal](index.md)/[dgwdpQualifier](dgwdp-qualifier.md)

# dgwdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[dgwdpQualifier](dgwdp-qualifier.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Uses the base value by default, but when the screen metric for [qualifierType](dgwdp-qualifier.md) is >= [qualifierValue](dgwdp-qualifier.md), it uses [qualifiedValue](dgwdp-qualifier.md) instead. Usage example: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)` → 30.wdp by default, 50.wdp when screenWidthDp >= 600.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](dgwdp-qualifier.md) é >= [qualifierValue](dgwdp-qualifier.md), usa [qualifiedValue](dgwdp-qualifier.md) no lugar. Exemplo de uso: `30.wdpQualifier(50, DpQualifier.WIDTH, 600)` → 30.wdp por padrão, 50.wdp quando screenWidthDp >= 600.

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the screen metric for [qualifierType](dgwdp-qualifier.md) is >= [qualifierValue](dgwdp-qualifier.md), it uses [qualifiedValue](dgwdp-qualifier.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando a métrica de tela para [qualifierType](dgwdp-qualifier.md) é >= [qualifierValue](dgwdp-qualifier.md), usa [qualifiedValue](dgwdp-qualifier.md) no lugar.
