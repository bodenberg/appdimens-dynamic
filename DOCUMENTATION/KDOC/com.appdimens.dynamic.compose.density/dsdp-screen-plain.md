//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.density](index.md)/[dsdpScreenPlain](dsdp-screen-plain.md)

# dsdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[dsdpScreenPlain](dsdp-screen-plain.md)(screen: ERROR CLASS: Symbol not found for Dp, uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)): ERROR CLASS: Symbol not found for Dp

EN Plain sdp screen: [screen](dsdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](dsdp-screen-plain.md) e recetor já escalados; só a lógica.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](dsdp-screen-plain.md) AND the screen metric for [qualifierType](dsdp-screen-plain.md) is >= [qualifierValue](dsdp-screen-plain.md), it uses [screenValue](dsdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](dsdp-screen-plain.md) E a métrica de tela para [qualifierType](dsdp-screen-plain.md) é >= [qualifierValue](dsdp-screen-plain.md), usa [screenValue](dsdp-screen-plain.md) no lugar.
