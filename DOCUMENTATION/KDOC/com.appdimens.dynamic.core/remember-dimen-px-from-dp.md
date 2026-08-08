//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[rememberDimenPxFromDp](remember-dimen-px-from-dp.md)

# rememberDimenPxFromDp

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable)fun [rememberDimenPxFromDp](remember-dimen-px-from-dp.md)(cacheKey: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), pxStamp: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), androidContext: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), density: [Density](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Density), match: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, passthrough: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = Float.NaN, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Remembers scaled Dp→Px. When [match](remember-dimen-px-from-dp.md) is false, returns [passthrough](remember-dimen-px-from-dp.md) unchanged.

The scaled Dp is resolved against the [LocalDimenMetrics](local-dimen-metrics.md) snapshot (or the window snapshot derived from [androidContext](remember-dimen-px-from-dp.md)) and converted to pixels with the provided [density](remember-dimen-px-from-dp.md).

PT Lembra Dp→Px; com [match](remember-dimen-px-from-dp.md) falso devolve [passthrough](remember-dimen-px-from-dp.md).
