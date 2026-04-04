//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[ResizeRangePx](index.md)

# ResizeRangePx

[jvm]
data class [ResizeRangePx](index.md)(val minPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), val maxPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), val stepPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html))

EN Normalizes min/max order; [steps](steps.md) covers [[lowPx](low-px.md),[highPx](high-px.md)] with [stepPx](step-px.md) granularity.

## Constructors

| | |
|---|---|
| [ResizeRangePx](-resize-range-px.md) | [jvm] constructor(minPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), maxPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), stepPx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [highPx](high-px.md) | [jvm] val [highPx](high-px.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [lowPx](low-px.md) | [jvm] val [lowPx](low-px.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [maxPx](max-px.md) | [jvm] val [maxPx](max-px.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [minPx](min-px.md) | [jvm] val [minPx](min-px.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [stepPx](step-px.md) | [jvm] val [stepPx](step-px.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [steps](steps.md) | [jvm] val [steps](steps.md): [FloatArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float-array/index.html) |

## Functions

| Name | Summary |
|---|---|
| [fittingPx](../../com.appdimens.dynamic.code.resize/fitting-px.md) | [jvm] fun [ResizeRangePx](index.md).[fittingPx](../../com.appdimens.dynamic.code.resize/fitting-px.md)(fits: (candidatePx: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)) -> [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [resolveFitting](resolve-fitting.md) | [jvm] fun [resolveFitting](resolve-fitting.md)(fits: ([Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)) -> [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |