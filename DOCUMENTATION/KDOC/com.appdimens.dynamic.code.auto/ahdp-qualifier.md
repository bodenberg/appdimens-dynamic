//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.auto](index.md)/[ahdpQualifier](ahdp-qualifier.md)

# ahdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[ahdpQualifier](ahdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the screen metric for [qualifierType](ahdp-qualifier.md) is >= [qualifierValue](ahdp-qualifier.md), it uses [qualifiedValue](ahdp-qualifier.md) instead.

PT Extensão para Int com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](ahdp-qualifier.md) é >= [qualifierValue](ahdp-qualifier.md), usa [qualifiedValue](ahdp-qualifier.md) no lugar.
