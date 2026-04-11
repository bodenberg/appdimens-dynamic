//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCalculationPlumbing](index.md)

# DimenCalculationPlumbing

object [DimenCalculationPlumbing](index.md)

fun [aspectRatioMultiplier](aspect-ratio-multiplier.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), sensitivity: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

Multiplicative factor for optional aspect-ratio correction (perceptual / power-style paths).

fun [effectiveQualifier](effective-qualifier.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), isLandscape: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), isPortrait: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)

private fun [Context](https://developer.android.com/reference/kotlin/android/content/Context.html).[findActivityInternal](find-activity-internal.md)(): [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html)?

fun [isMultiWindowConstrained](is-multi-window-constrained.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

Returns `true` when the app is in a multi-window mode (split-screen, freeform, PiP) **and** the caller opted into suppressing scaling via [ignoreMultiWindows](is-multi-window-constrained.md).

fun [largestSideDp](largest-side-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [readScreenDp](read-screen-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), actualQualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [smallestSideDp](smallest-side-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

## Functions

| Name | Summary |
|---|---|
| [aspectRatioMultiplier](aspect-ratio-multiplier.md) |  |
| [effectiveQualifier](effective-qualifier.md) |  |
| [findActivityInternal](find-activity-internal.md) |  |
| [isMultiWindowConstrained](is-multi-window-constrained.md) |  |
| [largestSideDp](largest-side-dp.md) |  |
| [readScreenDp](read-screen-dp.md) |  |
| [smallestSideDp](smallest-side-dp.md) |  |
