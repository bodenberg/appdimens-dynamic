//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)/[TOLERANCE](-t-o-l-e-r-a-n-c-e.md)

# TOLERANCE

private const val [TOLERANCE](-t-o-l-e-r-a-n-c-e.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.003f

Tolerance: ±0.35 % of the key value. Tight enough to avoid false hits between adjacent ratios yet forgiving enough that floating-point rounding from integer division (e.g. 1920 / 1080 = 1.7777…) still matches the canonical 16:9 entry.
