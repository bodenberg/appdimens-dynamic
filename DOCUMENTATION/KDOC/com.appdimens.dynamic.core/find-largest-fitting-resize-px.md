//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[findLargestFittingResizePx](find-largest-fitting-resize-px.md)

# findLargestFittingResizePx

fun [findLargestFittingResizePx](find-largest-fitting-resize-px.md)(sortedStepsPx: [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html), fits: (candidatePx: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) -> [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Largest step in [sortedStepsPx](find-largest-fitting-resize-px.md) (ascending) for which [fits](find-largest-fitting-resize-px.md) returns true. If none fit, returns `0f`.
