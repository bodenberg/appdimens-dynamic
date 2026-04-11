//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.diagonal](index.md)/[hspScreen](hsp-screen.md)

# hspScreen

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[hspScreen](hsp-screen.md)(screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the device matches [uiModeType](hsp-screen.md) AND the screen metric for [qualifierType](hsp-screen.md) is >= [qualifierValue](hsp-screen.md), it uses [screenValue](hsp-screen.md) instead. Usage example: `30.hspScreen(50, UiModeType.TELEVISION, DpQualifier.HEIGHT, 800)` → 30.dghsp by default, 50.dghsp on television with height >= 800.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Height (hDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches [uiModeType](hsp-screen.md) AND the screen metric for [qualifierType](hsp-screen.md) is >= [qualifierValue](hsp-screen.md), it uses [screenValue](hsp-screen.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](hsp-screen.md) E a métrica de tela para [qualifierType](hsp-screen.md) é >= [qualifierValue](hsp-screen.md), usa [screenValue](hsp-screen.md) no lugar.
