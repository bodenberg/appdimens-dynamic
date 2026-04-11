//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[wdpRotate](wdp-rotate.md)

# wdpRotate

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wdpRotate](wdp-rotate.md)(rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.WIDTH, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Uses the base value by default, but when the device is in the specified [orientation](wdp-rotate.md), it uses [rotationValue](wdp-rotate.md) scaled with the given [finalQualifierResolver](wdp-rotate.md). Usage example: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)` → 30.wdp by default, 45 scaled by WIDTH in landscape.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Usa o valor base por padrão, mas quando o dispositivo está na [orientation](wdp-rotate.md) especificada, usa [rotationValue](wdp-rotate.md) escalado com o [finalQualifierResolver](wdp-rotate.md) dado. Exemplo de uso: `30.wdpRot(45, DpQualifier.WIDTH, Orientation.LANDSCAPE)` → 30.wdp por padrão, 45 escalado por WIDTH no paisagem.

EN Extension for Dp with dynamic scaling based on **Screen Width (wDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device is in the specified [orientation](wdp-rotate.md), it uses [rotationValue](wdp-rotate.md) scaled with the given [finalQualifierResolver](wdp-rotate.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo está na [orientation](wdp-rotate.md) especificada, usa [rotationValue](wdp-rotate.md) escalado com o [finalQualifierResolver](wdp-rotate.md) dado.
