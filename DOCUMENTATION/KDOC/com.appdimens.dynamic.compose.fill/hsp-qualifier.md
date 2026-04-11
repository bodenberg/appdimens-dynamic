//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fill](index.md)/[hspQualifier](hsp-qualifier.md)

# hspQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[hspQualifier](hsp-qualifier.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the screen metric for [qualifierType](hsp-qualifier.md) is >= [qualifierValue](hsp-qualifier.md), it uses [qualifiedValue](hsp-qualifier.md) instead. Usage example: `30.hspQualifier(50, DpQualifier.HEIGHT, 800)` → 30.flhsp by default, 50.flhsp when screenHeightDp >= 800.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the screen metric for [qualifierType](hsp-qualifier.md) is >= [qualifierValue](hsp-qualifier.md), it uses [qualifiedValue](hsp-qualifier.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando a métrica de tela para [qualifierType](hsp-qualifier.md) é >= [qualifierValue](hsp-qualifier.md), usa [qualifiedValue](hsp-qualifier.md) no lugar.
