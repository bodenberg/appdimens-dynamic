//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[sdpRotatePlain](sdp-rotate-plain.md)

# sdpRotatePlain

[jvm]
fun <Error class: unknown class>.[sdpRotatePlain](sdp-rotate-plain.md)(rotationValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): <Error class: unknown class>

EN Extension for Dp with dynamic scaling based on **Smallest Width (swDP)**. Returns the original raw Dp value if the condition is not met. When the device is in the specified [orientation](sdp-rotate-plain.md), it uses [rotationValue](sdp-rotate-plain.md) scaled with the given [finalQualifierResolver](sdp-rotate-plain.md).

PT Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Retorna o valor original de Dp bruto se a condição não for atendida. Quando o dispositivo está na [orientation](sdp-rotate-plain.md) especificada, usa [rotationValue](sdp-rotate-plain.md) escalado com o [finalQualifierResolver](sdp-rotate-plain.md) dado.

**Overload (same name):** `sdpRotatePlain(rotation: Dp, orientation = …)` — receiver and `rotation` must already be strategy-scaled `Dp`; only the orientation branch runs (no `rememberScaled*` / cache on that path). Same idea for `hdp` / `wdp` and for `*PlainPx` with `Dp` alternates. Nesting order for chained `*Plain` calls follows the expression; `DimenScaled.screen` uses internal priority instead — see [COMPOSE-API-CONVENTIONS.md](../../COMPOSE-API-CONVENTIONS.md).