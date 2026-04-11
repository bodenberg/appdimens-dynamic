//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[sdpScreenPlain](sdp-screen-plain.md)

# sdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[sdpScreenPlain](sdp-screen-plain.md)(screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](sdp-screen-plain.md) AND the screen metric for [qualifierType](sdp-screen-plain.md) is >= [qualifierValue](sdp-screen-plain.md), it uses [screenValue](sdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](sdp-screen-plain.md) E a métrica de tela para [qualifierType](sdp-screen-plain.md) é >= [qualifierValue](sdp-screen-plain.md), usa [screenValue](sdp-screen-plain.md) no lugar.

EN Plain sdp screen: [screen](sdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](sdp-screen-plain.md) e recetor já escalados; só a lógica.
