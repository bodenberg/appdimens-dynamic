//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.power](index.md)/[pwhdpMode](pwhdp-mode.md)

# pwhdpMode

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[pwhdpMode](pwhdp-mode.md)(modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the device matches the specified [uiModeType](pwhdp-mode.md), it uses [modeValue](pwhdp-mode.md) instead. Usage example: `30.hdpMode(50, UiModeType.TELEVISION)` → 30.hdp by default, 50.hdp on television.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType](pwhdp-mode.md) especificado, usa [modeValue](pwhdp-mode.md) no lugar. Exemplo de uso: `30.hdpMode(50, UiModeType.TELEVISION)` → 30.hdp por padrão, 50.hdp na televisão.

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches the specified [uiModeType](pwhdp-mode.md), it uses [modeValue](pwhdp-mode.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](pwhdp-mode.md) especificado, usa [modeValue](pwhdp-mode.md) no lugar.
