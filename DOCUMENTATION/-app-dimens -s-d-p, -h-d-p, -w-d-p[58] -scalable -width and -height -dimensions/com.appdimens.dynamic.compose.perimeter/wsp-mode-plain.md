//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[wspModePlain](wsp-mode-plain.md)

# wspModePlain

[jvm]
fun <Error class: unknown class>.[wspModePlain](wsp-mode-plain.md)(modeValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**. Returns the original raw TextUnit value if the condition is not met. When the device matches the specified [uiModeType](wsp-mode-plain.md), it uses [modeValue](wsp-mode-plain.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original de TextUnit bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](wsp-mode-plain.md) especificado, usa [modeValue](wsp-mode-plain.md) no lugar.