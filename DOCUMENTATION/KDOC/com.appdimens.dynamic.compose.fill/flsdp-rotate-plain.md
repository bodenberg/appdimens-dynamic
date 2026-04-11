//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fill](index.md)/[flsdpRotatePlain](flsdp-rotate-plain.md)

# flsdpRotatePlain

fun ERROR CLASS: Symbol not found for Dp.[flsdpRotatePlain](flsdp-rotate-plain.md)(rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device is in the specified [orientation](flsdp-rotate-plain.md), it uses [rotationValue](flsdp-rotate-plain.md) scaled with the given [finalQualifierResolver](flsdp-rotate-plain.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo está na [orientation](flsdp-rotate-plain.md) especificada, usa [rotationValue](flsdp-rotate-plain.md) escalado com o [finalQualifierResolver](flsdp-rotate-plain.md) dado.

EN Plain rotation with **already scaled** [rotation](flsdp-rotate-plain.md) and receiver: no further scaling, only the orientation branch. Use when both sides come from the same strategy (e.g. `30.flsdp.flsdpRotatePlain(20.flsdp)`).

PT Rotação Plain com [rotation](flsdp-rotate-plain.md) e recetor **já escalados**: sem nova conversão, só o ramo de orientação. Use quando ambos os lados vêm da mesma estratégia (ex.: `30.flsdp.flsdpRotatePlain(20.flsdp)`).
