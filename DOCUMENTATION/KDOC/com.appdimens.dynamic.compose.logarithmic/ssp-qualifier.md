//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.logarithmic](index.md)/[sspQualifier](ssp-qualifier.md)

# sspQualifier

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[sspQualifier](ssp-qualifier.md)(qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the screen metric for [qualifierType](ssp-qualifier.md) is >= [qualifierValue](ssp-qualifier.md), it uses [qualifiedValue](ssp-qualifier.md) instead. Usage example: `30.sspQualifier(50, DpQualifier.SMALL_WIDTH, 600)` → 30.logssp by default, 50.logssp when smallestScreenWidthDp >= 600.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Usa o valor base por padrão, mas quando a métrica de tela para [qualifierType](ssp-qualifier.md) é >= [qualifierValue](ssp-qualifier.md), usa [qualifiedValue](ssp-qualifier.md) no lugar.

EN Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the screen metric for [qualifierType](ssp-qualifier.md) is >= [qualifierValue](ssp-qualifier.md), it uses [qualifiedValue](ssp-qualifier.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando a métrica de tela para [qualifierType](ssp-qualifier.md) é >= [qualifierValue](ssp-qualifier.md), usa [qualifiedValue](ssp-qualifier.md) no lugar.
