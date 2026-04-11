//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.perimeter](index.md)/[prwdpQualifier](prwdp-qualifier.md)

# prwdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[prwdpQualifier](prwdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Screen Width (wDP)**. Uses the base value by default, but when the screen metric for [qualifierType](prwdp-qualifier.md) is >= [qualifierValue](prwdp-qualifier.md), it uses [qualifiedValue](prwdp-qualifier.md) instead.

PT Extensão para Int com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](prwdp-qualifier.md) é >= [qualifierValue](prwdp-qualifier.md), usa [qualifiedValue](prwdp-qualifier.md) no lugar.
