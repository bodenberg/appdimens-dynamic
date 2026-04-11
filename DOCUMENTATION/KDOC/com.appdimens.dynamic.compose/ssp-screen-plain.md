//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[sspScreenPlain](ssp-screen-plain.md)

# sspScreenPlain

fun ERROR CLASS: Symbol not found for TextUnit.[sspScreenPlain](ssp-screen-plain.md)(screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for TextUnit

EN Extension for TextUnit (Sp) with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw TextUnit value if the condition is not met. When the device matches [uiModeType](ssp-screen-plain.md) AND the screen metric for [qualifierType](ssp-screen-plain.md) is >= [qualifierValue](ssp-screen-plain.md), it uses [screenValue](ssp-screen-plain.md) instead.

PT Extensão para TextUnit (Sp) com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de TextUnit bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](ssp-screen-plain.md) E a métrica de tela para [qualifierType](ssp-screen-plain.md) é >= [qualifierValue](ssp-screen-plain.md), usa [screenValue](ssp-screen-plain.md) no lugar.

EN Plain ssp screen: [screen](ssp-screen-plain.md) and receiver already scaled; logic only. PT Ecrã ssp Plain: [screen](ssp-screen-plain.md) e recetor já escalados; só a lógica.
