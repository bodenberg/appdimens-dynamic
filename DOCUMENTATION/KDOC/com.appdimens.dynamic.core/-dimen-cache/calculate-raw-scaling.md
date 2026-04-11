//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[calculateRawScaling](calculate-raw-scaling.md)

# calculateRawScaling

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [calculateRawScaling](calculate-raw-scaling.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Unified high-performance scaling engine. Reads from [factors](factors.md) — padded object, guaranteeing that the read of `scale` and `arMultiplier` land on the same cache line as all other factor fields.
