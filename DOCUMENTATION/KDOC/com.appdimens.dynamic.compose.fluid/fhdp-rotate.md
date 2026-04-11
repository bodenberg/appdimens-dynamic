//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fluid](index.md)/[fhdpRotate](fhdp-rotate.md)

# fhdpRotate

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[fhdpRotate](fhdp-rotate.md)(rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.HEIGHT, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Uses the base value by default, but when the device is in the specified [orientation](fhdp-rotate.md), it uses [rotationValue](fhdp-rotate.md) scaled with the given [finalQualifierResolver](fhdp-rotate.md). Usage example: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)` → 30.hdp by default, 45 scaled by HEIGHT in landscape.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Usa o valor base por padrão, mas quando o dispositivo está na [orientation](fhdp-rotate.md) especificada, usa [rotationValue](fhdp-rotate.md) escalado com o [finalQualifierResolver](fhdp-rotate.md) dado. Exemplo de uso: `30.hdpRot(45, DpQualifier.HEIGHT, Orientation.LANDSCAPE)` → 30.hdp por padrão, 45 escalado por HEIGHT no paisagem.

EN Extension for Dp with dynamic scaling based on **Screen Height (hDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device is in the specified [orientation](fhdp-rotate.md), it uses [rotationValue](fhdp-rotate.md) scaled with the given [finalQualifierResolver](fhdp-rotate.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo está na [orientation](fhdp-rotate.md) especificada, usa [rotationValue](fhdp-rotate.md) escalado com o [finalQualifierResolver](fhdp-rotate.md) dado.
