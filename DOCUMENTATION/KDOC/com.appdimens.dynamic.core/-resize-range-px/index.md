//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[ResizeRangePx](index.md)

# ResizeRangePx

data class [ResizeRangePx](index.md)(val minPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val maxPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val stepPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

EN Normalizes min/max order; [steps](steps.md) covers [[lowPx](low-px.md),[highPx](high-px.md)] with stepPx granularity.

constructor(minPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), maxPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), stepPx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

val [highPx](high-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [lowPx](low-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [maxPx](max-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [minPx](min-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [stepPx](step-px.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [steps](steps.md): [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html)

fun [ResizeRangePx](index.md).[fittingPx](../../com.appdimens.dynamic.code.resize/fitting-px.md)(fits: (candidatePx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) -> [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

private fun [ResizeRangePx](index.md).[requireFiniteRange](../../com.appdimens.dynamic.code.resize/require-finite-range.md)(): [ResizeRangePx](index.md)

fun [resolveFitting](resolve-fitting.md)(fits: ([Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) -> [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

## Constructors

| Name | Summary |
|---|---|
| [ResizeRangePx](-resize-range-px.md) |  |


## Properties

| Name | Summary |
|---|---|
| [highPx](high-px.md) |  |
| [lowPx](low-px.md) |  |
| [maxPx](max-px.md) |  |
| [minPx](min-px.md) |  |
| [stepPx](step-px.md) |  |
| [steps](steps.md) |  |


## Functions

| Name | Summary |
|---|---|
| [fittingPx](../../com.appdimens.dynamic.code.resize/fitting-px.md) |  |
| [requireFiniteRange](../../com.appdimens.dynamic.code.resize/require-finite-range.md) |  |
| [resolveFitting](resolve-fitting.md) |  |
