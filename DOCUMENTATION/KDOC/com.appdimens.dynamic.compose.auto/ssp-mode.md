//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.auto](index.md)/[sspMode](ssp-mode.md)

# sspMode

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[sspMode](ssp-mode.md)(modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for Int with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the device matches the specified [uiModeType](ssp-mode.md), it uses [modeValue](ssp-mode.md) instead.

EN Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches the specified [uiModeType](ssp-mode.md), it uses [modeValue](ssp-mode.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](ssp-mode.md) especificado, usa [modeValue](ssp-mode.md) no lugar.
