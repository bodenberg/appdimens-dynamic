//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.interpolated](index.md)/[isdpRotate](isdp-rotate.md)

# isdpRotate

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[isdpRotate](isdp-rotate.md)(rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the device is in the specified [orientation](isdp-rotate.md), it uses [rotationValue](isdp-rotate.md) scaled with the given [finalQualifierResolver](isdp-rotate.md). Usage example: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)` → 30.sdp by default, 45 scaled by SMALL_WIDTH in landscape.

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Usa o valor base por padrão, mas quando o dispositivo está na [orientation](isdp-rotate.md) especificada, usa [rotationValue](isdp-rotate.md) escalado com o [finalQualifierResolver](isdp-rotate.md) dado. Exemplo de uso: `30.sdpRot(45, DpQualifier.SMALL_WIDTH, Orientation.LANDSCAPE)` → 30.sdp por padrão, 45 escalado por SMALL_WIDTH no paisagem.

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original value **auto-scaled** using the specified qualifier if the condition is not met. When the device is in the specified [orientation](isdp-rotate.md), it uses [rotationValue](isdp-rotate.md) scaled with the given [finalQualifierResolver](isdp-rotate.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original **auto-escalonado** usando o qualificador especificado se a condição não for atendida. Quando o dispositivo está na [orientation](isdp-rotate.md) especificada, usa [rotationValue](isdp-rotate.md) escalado com o [finalQualifierResolver](isdp-rotate.md) dado.
