//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.power](index.md)/[pwwdpMode](pwwdp-mode.md)

# pwwdpMode

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[pwwdpMode](pwwdp-mode.md)(modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Uses the base value by default, but when the device matches the specified [uiModeType](pwwdp-mode.md), it uses [modeValue](pwwdp-mode.md) instead. Usage example: `30.wdpMode(50, UiModeType.TELEVISION)` → 30.wdp by default, 50.wdp on television.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType](pwwdp-mode.md) especificado, usa [modeValue](pwwdp-mode.md) no lugar. Exemplo de uso: `30.wdpMode(50, UiModeType.TELEVISION)` → 30.wdp por padrão, 50.wdp na televisão.

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches the specified [uiModeType](pwwdp-mode.md), it uses [modeValue](pwwdp-mode.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](pwwdp-mode.md) especificado, usa [modeValue](pwwdp-mode.md) no lugar.
