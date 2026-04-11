//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.perimeter](index.md)/[prsdpScreenPlain](prsdp-screen-plain.md)

# prsdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[prsdpScreenPlain](prsdp-screen-plain.md)(screen: ERROR CLASS: Symbol not found for Dp, uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)): ERROR CLASS: Symbol not found for Dp

EN Plain sdp screen: [screen](prsdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](prsdp-screen-plain.md) e recetor já escalados; só a lógica.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](prsdp-screen-plain.md) AND the screen metric for [qualifierType](prsdp-screen-plain.md) is >= [qualifierValue](prsdp-screen-plain.md), it uses [screenValue](prsdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](prsdp-screen-plain.md) E a métrica de tela para [qualifierType](prsdp-screen-plain.md) é >= [qualifierValue](prsdp-screen-plain.md), usa [screenValue](prsdp-screen-plain.md) no lugar.
