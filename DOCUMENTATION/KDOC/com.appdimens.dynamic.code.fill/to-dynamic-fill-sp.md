//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.fill](index.md)/[toDynamicFillSp](to-dynamic-fill-sp.md)

# toDynamicFillSp

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicFillSp](to-dynamic-fill-sp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts an Int (base Sp) to a dynamically scaled Sp value (as Float).

The scaled Sp value as [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).

**Bulk / init:** see [toDynamicFillSpPx](to-dynamic-fill-sp-px.md) for [DimenCache.getBatch](../com.appdimens.dynamic.core/-dimen-cache/get-batch.md) and [DimenFillSp.warmupCache](-dimen-fill-sp/warmup-cache.md).

The Android context to access configuration and density.

The screen qualifier used for scaling (sw, h, w).

Whether to respect the system font scale.

The inverter logic to apply.

Whether to ignore multi-window mode.

If `true`, applies the aspect-ratio multiplier.

Override for the AR sensitivity constant (null = library default).
