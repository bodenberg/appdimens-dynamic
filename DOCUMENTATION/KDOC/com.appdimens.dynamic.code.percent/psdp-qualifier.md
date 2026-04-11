//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.percent](index.md)/[psdpQualifier](psdp-qualifier.md)

# psdpQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[psdpQualifier](psdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the screen metric for [qualifierType](psdp-qualifier.md) is >= [qualifierValue](psdp-qualifier.md), it uses [qualifiedValue](psdp-qualifier.md) instead.

PT Extensão para Int com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](psdp-qualifier.md) é >= [qualifierValue](psdp-qualifier.md), usa [qualifiedValue](psdp-qualifier.md) no lugar.
