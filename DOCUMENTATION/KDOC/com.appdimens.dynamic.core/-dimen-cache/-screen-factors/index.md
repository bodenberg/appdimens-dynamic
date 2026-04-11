//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.core](../../index.md)/[DimenCache](../index.md)/[ScreenFactors](index.md)

# ScreenFactors

internal class [ScreenFactors](index.md)

EN Holds all screen-derived scaling factors in an object padded to exceed two ARM64 cache lines (2 × 64 bytes = 128 bytes), ensuring that writes during [updateFactors](../update-factors.md) do not invalidate unrelated reads on sibling CPU cores.

PT Agrupa todos os fatores de escala derivados da tela em um objeto com padding de 128 bytes, prevenindo false sharing entre núcleos durante [updateFactors](../update-factors.md).

constructor()

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p0](_p0.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p1](_p1.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p2](_p2.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p3](_p3.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p4](_p4.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p5](_p5.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p6](_p6.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p7](_p7.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [arMultiplier](ar-multiplier.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [aspectRatioMul](aspect-ratio-mul.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [density](density.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [diagonalScale](diagonal-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [interpolatedScale](interpolated-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [logNormalizedAr](log-normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [logScale](log-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [normalizedAr](normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [perimeterScale](perimeter-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [powerScale](power-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [scale](scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)var [smallestWidthDp](smallest-width-dp.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

## Constructors

| Name | Summary |
|---|---|
| [ScreenFactors](-screen-factors.md) |  |


## Properties

| Name | Summary |
|---|---|
| [_p0](_p0.md) |  |
| [_p1](_p1.md) |  |
| [_p2](_p2.md) |  |
| [_p3](_p3.md) |  |
| [_p4](_p4.md) |  |
| [_p5](_p5.md) |  |
| [_p6](_p6.md) |  |
| [_p7](_p7.md) |  |
| [arMultiplier](ar-multiplier.md) |  |
| [aspectRatioMul](aspect-ratio-mul.md) |  |
| [density](density.md) |  |
| [diagonalScale](diagonal-scale.md) |  |
| [interpolatedScale](interpolated-scale.md) |  |
| [logNormalizedAr](log-normalized-ar.md) |  |
| [logScale](log-scale.md) |  |
| [normalizedAr](normalized-ar.md) |  |
| [perimeterScale](perimeter-scale.md) |  |
| [powerScale](power-scale.md) |  |
| [scale](scale.md) |  |
| [smallestWidthDp](smallest-width-dp.md) |  |
