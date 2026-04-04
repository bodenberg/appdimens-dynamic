//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.auto](index.md)/[ahdpModePlain](ahdp-mode-plain.md)

# ahdpModePlain

[jvm]
fun <Error class: unknown class>.[ahdpModePlain](ahdp-mode-plain.md)(modeValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original raw Dp value if the condition is not met. When the device matches the specified [uiModeType](ahdp-mode-plain.md), it uses [modeValue](ahdp-mode-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](ahdp-mode-plain.md) especificado, usa [modeValue](ahdp-mode-plain.md) no lugar.