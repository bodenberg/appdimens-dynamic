//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[wspScreenPlain](wsp-screen-plain.md)

# wspScreenPlain

fun ERROR CLASS: Symbol not found for TextUnit.[wspScreenPlain](wsp-screen-plain.md)(screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Screen Width (wDP)**. Returns the original raw TextUnit value if the condition is not met. When the device matches [uiModeType](wsp-screen-plain.md) AND the screen metric for [qualifierType](wsp-screen-plain.md) is >= [qualifierValue](wsp-screen-plain.md), it uses [screenValue](wsp-screen-plain.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original de TextUnit bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](wsp-screen-plain.md) E a métrica de tela para [qualifierType](wsp-screen-plain.md) é >= [qualifierValue](wsp-screen-plain.md), usa [screenValue](wsp-screen-plain.md) no lugar.

EN Plain wsp screen: [screen](wsp-screen-plain.md) and receiver already scaled; logic only. PT Ecrã wsp Plain: [screen](wsp-screen-plain.md) e recetor já escalados; só a lógica.
