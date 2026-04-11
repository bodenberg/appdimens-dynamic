//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fit](index.md)/[ftsdpScreenPlain](ftsdp-screen-plain.md)

# ftsdpScreenPlain

fun ERROR CLASS: Symbol not found for Dp.[ftsdpScreenPlain](ftsdp-screen-plain.md)(screen: ERROR CLASS: Symbol not found for Dp, uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)): ERROR CLASS: Symbol not found for Dp

EN Plain sdp screen: [screen](ftsdp-screen-plain.md) and receiver already scaled; logic only (ui mode + qualifier threshold). PT Ecrã sdp Plain: [screen](ftsdp-screen-plain.md) e recetor já escalados; só a lógica.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device matches [uiModeType](ftsdp-screen-plain.md) AND the screen metric for [qualifierType](ftsdp-screen-plain.md) is >= [qualifierValue](ftsdp-screen-plain.md), it uses [screenValue](ftsdp-screen-plain.md) instead.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo corresponde ao [uiModeType](ftsdp-screen-plain.md) E a métrica de tela para [qualifierType](ftsdp-screen-plain.md) é >= [qualifierValue](ftsdp-screen-plain.md), usa [screenValue](ftsdp-screen-plain.md) no lugar.
