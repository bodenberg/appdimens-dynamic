//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getOrPutAspectRatio](get-or-put-aspect-ratio.md)

# getOrPutAspectRatio

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPutAspectRatio](get-or-put-aspect-ratio.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Exact `ln()` computed once per [DimenMetrics](../-dimen-metrics/index.md) snapshot (since 3.1.7 — the memoized lookup table was removed). Requires a positive finite input; otherwise throws `IllegalArgumentException`.

PT `ln()` exato calculado uma vez por snapshot de [DimenMetrics](../-dimen-metrics/index.md) (desde a 3.1.7). Exige entrada positiva e finita.
