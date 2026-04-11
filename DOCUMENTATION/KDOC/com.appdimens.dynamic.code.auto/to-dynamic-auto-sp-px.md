//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.auto](index.md)/[toDynamicAutoSpPx](to-dynamic-auto-sp-px.md)

# toDynamicAutoSpPx

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicAutoSpPx](to-dynamic-auto-sp-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts an Int (the base Sp value) into a dynamically scaled pixel value (Float).

Sp→px uses `scaledSp * density * fontScale` when respecting font scale (equivalent to [android.util.TypedValue.applyDimension](https://developer.android.com/reference/kotlin/android/util/TypedValue.html#applydimension) for `COMPLEX_UNIT_SP`), else `scaledSp * density` for the fixed-Sp path. For many lookups, prefer [DimenCache.getBatch](../com.appdimens.dynamic.core/-dimen-cache/get-batch.md); for early DataStore init, [DimenAutoSp.warmupCache](-dimen-auto-sp/warmup-cache.md).

PT Converte um Int (o valor base de Sp) em um valor de pixel dinamicamente escalado (Float).

The scaled pixel value.

The Android context to access configuration and density.

The screen qualifier used for scaling (sw, h, w).

Whether to respect the system font scale.
