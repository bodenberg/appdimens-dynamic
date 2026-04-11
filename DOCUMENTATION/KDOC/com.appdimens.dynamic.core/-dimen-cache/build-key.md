//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[buildKey](build-key.md)

# buildKey

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [buildKey](build-key.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), isLandscape: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), calcType: [DimenCache.CalcType](-calc-type/index.md), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), valueType: [DimenCache.ValueType](-value-type/index.md), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

Packs all dimension-calculation parameters into a single 64-bit [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) key.

Bit layout (MSB → LSB):
