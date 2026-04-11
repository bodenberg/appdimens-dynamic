//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fit](index.md)/[wspScreen](wsp-screen.md)

# wspScreen

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[wspScreen](wsp-screen.md)(screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**. Uses the base value by default, but when the device matches [uiModeType](wsp-screen.md) AND the screen metric for [qualifierType](wsp-screen.md) is >= [qualifierValue](wsp-screen.md), it uses [screenValue](wsp-screen.md) instead. Usage example: `30.wspScreen(50, UiModeType.TELEVISION, DpQualifier.WIDTH, 600)` → 30.ftwsp by default, 50.ftwsp on television with width >= 600.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device matches [uiModeType](wsp-screen.md) AND the screen metric for [qualifierType](wsp-screen.md) is >= [qualifierValue](wsp-screen.md), it uses [screenValue](wsp-screen.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](wsp-screen.md) E a métrica de tela para [qualifierType](wsp-screen.md) é >= [qualifierValue](wsp-screen.md), usa [screenValue](wsp-screen.md) no lugar.
