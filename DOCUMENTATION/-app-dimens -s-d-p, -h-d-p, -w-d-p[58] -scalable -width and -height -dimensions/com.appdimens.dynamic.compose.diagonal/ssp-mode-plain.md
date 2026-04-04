//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.diagonal](index.md)/[sspModePlain](ssp-mode-plain.md)

# sspModePlain

[jvm]
fun <Error class: unknown class>.[sspModePlain](ssp-mode-plain.md)(modeValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw TextUnit value if the condition is not met. When the device matches the specified [uiModeType](ssp-mode-plain.md), it uses [modeValue](ssp-mode-plain.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de TextUnit bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](ssp-mode-plain.md) especificado, usa [modeValue](ssp-mode-plain.md) no lugar.