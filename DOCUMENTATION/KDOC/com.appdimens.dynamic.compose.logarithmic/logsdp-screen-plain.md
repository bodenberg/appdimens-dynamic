//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.logarithmic](index.md)/[logsdpScreenPlain](logsdp-screen-plain.md)

# logsdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[logsdpScreenPlain](logsdp-screen-plain.md)(screen: ERROR CLASS: Symbol not found for Dp, uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)): ERROR CLASS: Symbol not found for Dp

EN Plain sdp screen: [screen](logsdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](logsdp-screen-plain.md) e recetor já escalados; só a lógica.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](logsdp-screen-plain.md) AND the screen metric for [qualifierType](logsdp-screen-plain.md) is >= [qualifierValue](logsdp-screen-plain.md), it uses [screenValue](logsdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](logsdp-screen-plain.md) E a métrica de tela para [qualifierType](logsdp-screen-plain.md) é >= [qualifierValue](logsdp-screen-plain.md), usa [screenValue](logsdp-screen-plain.md) no lugar.
