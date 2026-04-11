//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.diagonal](index.md)/[dgsdpScreenPlain](dgsdp-screen-plain.md)

# dgsdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[dgsdpScreenPlain](dgsdp-screen-plain.md)(screen: ERROR CLASS: Symbol not found for Dp, uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)): ERROR CLASS: Symbol not found for Dp

EN Plain sdp screen: [screen](dgsdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](dgsdp-screen-plain.md) e recetor já escalados; só a lógica.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](dgsdp-screen-plain.md) AND the screen metric for [qualifierType](dgsdp-screen-plain.md) is >= [qualifierValue](dgsdp-screen-plain.md), it uses [screenValue](dgsdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](dgsdp-screen-plain.md) E a métrica de tela para [qualifierType](dgsdp-screen-plain.md) é >= [qualifierValue](dgsdp-screen-plain.md), usa [screenValue](dgsdp-screen-plain.md) no lugar.
