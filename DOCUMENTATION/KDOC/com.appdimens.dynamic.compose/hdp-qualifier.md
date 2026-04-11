//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[hdpQualifier](hdp-qualifier.md)

# hdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[hdpQualifier](hdp-qualifier.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the screen metric for [qualifierType](hdp-qualifier.md) is >= [qualifierValue](hdp-qualifier.md), it uses [qualifiedValue](hdp-qualifier.md) instead. Usage example: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)` → 30.hdp by default, 50.hdp when screenHeightDp >= 800.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](hdp-qualifier.md) é >= [qualifierValue](hdp-qualifier.md), usa [qualifiedValue](hdp-qualifier.md) no lugar. Exemplo de uso: `30.hdpQualifier(50, DpQualifier.HEIGHT, 800)` → 30.hdp por padrão, 50.hdp quando screenHeightDp >= 800.

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the screen metric for [qualifierType](hdp-qualifier.md) is >= [qualifierValue](hdp-qualifier.md), it uses [qualifiedValue](hdp-qualifier.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando a métrica de tela para [qualifierType](hdp-qualifier.md) é >= [qualifierValue](hdp-qualifier.md), usa [qualifiedValue](hdp-qualifier.md) no lugar.
