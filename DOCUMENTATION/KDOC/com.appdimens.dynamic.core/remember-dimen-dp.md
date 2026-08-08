//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[rememberDimenDp](remember-dimen-dp.md)

# rememberDimenDp

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable)fun [rememberDimenDp](remember-dimen-dp.md)(cacheKey: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), layoutStamp: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), androidContext: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), match: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, passthrough: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp) = Dp.Unspecified, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp)

EN Remembers a scaled [Dp]. When [match](remember-dimen-dp.md) is false, returns [passthrough](remember-dimen-dp.md) without touching [DimenCache](-dimen-cache/index.md) — used by `*Plain` APIs so [remember](https://developer.android.com/reference/kotlin/androidx/compose/runtime/remember) is always called (stable Compose slots) while the miss branch stays a true no-op.

The value is cached under two keys — `cacheKey` + `layoutStamp` — and is resolved against the [LocalDimenMetrics](local-dimen-metrics.md) snapshot when the [AppDimensProvider](-app-dimens-provider.md) is present, falling back to the window snapshot derived from [androidContext](remember-dimen-dp.md).

PT Lembra um [Dp] escalado. Com [match](remember-dimen-dp.md) falso devolve [passthrough](remember-dimen-dp.md) sem cache (API `*Plain`).
