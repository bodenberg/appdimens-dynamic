//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.diagonal](index.md)/[toDynamicDiagonalSp](to-dynamic-diagonal-sp.md)

# toDynamicDiagonalSp

[jvm]
@[JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)

fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[toDynamicDiagonalSp](to-dynamic-diagonal-sp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true, inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Converts an Int (base Sp) to a dynamically scaled Sp value (as Float).

#### Return

The scaled Sp value as [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html).

**Bulk / init:** see [toDynamicDiagonalSpPx](to-dynamic-diagonal-sp-px.md) for [DimenCache.getBatch](../com.appdimens.dynamic.core/-dimen-cache/get-batch.md) and [DimenDiagonalSp.warmupCache](-dimen-diagonal-sp/warmup-cache.md).

#### Parameters

jvm

| | |
|---|---|
| context | The Android context to access configuration and density. |
| qualifier | The screen qualifier used for scaling (sw, h, w). |
| fontScale | Whether to respect the system font scale. |
| inverter | The inverter logic to apply. |
| ignoreMultiWindows | Whether to ignore multi-window mode. |
| applyAspectRatio | If `true`, applies the aspect-ratio multiplier. |
| customSensitivityK | Override for the AR sensitivity constant (null = library default). |