//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCalculationPlumbing](index.md)

# DimenCalculationPlumbing

[jvm]
object [DimenCalculationPlumbing](index.md)

## Functions

| Name | Summary |
|---|---|
| [aspectRatioMultiplier](aspect-ratio-multiplier.md) | [jvm] fun [aspectRatioMultiplier](aspect-ratio-multiplier.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), sensitivity: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) Multiplicative factor for optional aspect-ratio correction (perceptual / power-style paths). |
| [effectiveQualifier](effective-qualifier.md) | [jvm] fun [effectiveQualifier](effective-qualifier.md)(qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), isLandscape: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), isPortrait: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) |
| [isMultiWindowConstrained](is-multi-window-constrained.md) | [jvm] fun [isMultiWindowConstrained](is-multi-window-constrained.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [largestSideDp](largest-side-dp.md) | [jvm] fun [largestSideDp](largest-side-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [readScreenDp](read-screen-dp.md) | [jvm] fun [readScreenDp](read-screen-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), actualQualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |
| [smallestSideDp](smallest-side-dp.md) | [jvm] fun [smallestSideDp](smallest-side-dp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) |