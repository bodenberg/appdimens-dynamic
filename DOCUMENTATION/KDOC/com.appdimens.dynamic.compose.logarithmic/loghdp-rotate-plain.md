//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.logarithmic](index.md)/[loghdpRotatePlain](loghdp-rotate-plain.md)

# loghdpRotatePlain

[jvm]
fun <Error class: unknown class>.[loghdpRotatePlain](loghdp-rotate-plain.md)(rotationValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.HEIGHT, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original raw Dp value if the condition is not met. When the device is in the specified [orientation](loghdp-rotate-plain.md), it uses [rotationValue](loghdp-rotate-plain.md) scaled with the given [finalQualifierResolver](loghdp-rotate-plain.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo está na [orientation](loghdp-rotate-plain.md) especificada, usa [rotationValue](loghdp-rotate-plain.md) escalado com o [finalQualifierResolver](loghdp-rotate-plain.md) dado.