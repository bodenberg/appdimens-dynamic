//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.core](../../index.md)/[DimenCache](../index.md)/[ScreenFactors](index.md)

# ScreenFactors

[jvm]
internal class [ScreenFactors](index.md)

EN Holds all screen-derived scaling factors in an object padded to exceed two ARM64 cache lines (2 × 64 bytes = 128 bytes), ensuring that writes during [updateFactors](../update-factors.md) do not invalidate unrelated reads on sibling CPU cores.

PT Agrupa todos os fatores de escala derivados da tela em um objeto com padding de 128 bytes, prevenindo false sharing entre núcleos durante [updateFactors](../update-factors.md).

## Constructors

| | |
|---|---|
| [ScreenFactors](-screen-factors.md) | [jvm] constructor() |

## Properties

| Name | Summary |
|---|---|
| [_p0](_p0.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p0](_p0.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p1](_p1.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p1](_p1.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p2](_p2.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p2](_p2.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p3](_p3.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p3](_p3.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p4](_p4.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p4](_p4.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p5](_p5.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p5](_p5.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p6](_p6.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p6](_p6.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p7](_p7.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p7](_p7.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p8](_p8.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p8](_p8.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_p9](_p9.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_p9](_p9.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_pA](_p-a.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_pA](_p-a.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_pB](_p-b.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_pB](_p-b.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_pC](_p-c.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_pC](_p-c.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [_pD](_p-d.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) val [_pD](_p-d.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) = 0 |
| [arMultiplier](ar-multiplier.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [arMultiplier](ar-multiplier.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [density](density.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [density](density.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [logNormalizedAr](log-normalized-ar.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [logNormalizedAr](log-normalized-ar.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [normalizedAr](normalized-ar.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [normalizedAr](normalized-ar.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [scale](scale.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [scale](scale.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [smallestWidthDp](smallest-width-dp.md) | [jvm] @[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html) @[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html) var [smallestWidthDp](smallest-width-dp.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |