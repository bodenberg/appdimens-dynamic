//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)

# AspectRatioLookup

object [AspectRatioLookup](index.md)

EN Exact aspect-ratio math.

Aspect-ratio factors are created once in [DimenMetrics](../-dimen-metrics/index.md), outside the normal rendering path. A hand-maintained lookup table would make two nearby window sizes produce the same approximated result and is slower to maintain than one deterministic `ln` at snapshot time.

PT Matemática exata de aspect ratio. Os fatores são calculados uma vez por snapshot de [DimenMetrics](../-dimen-metrics/index.md), fora do caminho de renderização.

fun [lookup](lookup.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

Exact natural logarithm for a valid input; `null` for non-finite or non-positive values.

## Functions

| Name | Summary |
|---|---|
| [lookup](lookup.md) |  |
