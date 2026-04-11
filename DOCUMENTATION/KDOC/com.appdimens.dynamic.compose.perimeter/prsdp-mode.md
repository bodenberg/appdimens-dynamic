//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[prsdpMode](prsdp-mode.md)

# prsdpMode

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[prsdpMode](prsdp-mode.md)(modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the device matches the specified [uiModeType](prsdp-mode.md), it uses [modeValue](prsdp-mode.md) instead. Usage example: `30.sdpMode(50, UiModeType.TELEVISION)` → 30.sdp by default, 50.sdp on television.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Usa o valor base por padrão, mas quando o dispositivo corresponde ao [uiModeType](prsdp-mode.md) especificado, usa [modeValue](prsdp-mode.md) no lugar. Exemplo de uso: `30.sdpMode(50, UiModeType.TELEVISION)` → 30.sdp por padrão, 50.sdp na televisão.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches the specified [uiModeType](prsdp-mode.md), it uses [modeValue](prsdp-mode.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](prsdp-mode.md) especificado, usa [modeValue](prsdp-mode.md) no lugar.
