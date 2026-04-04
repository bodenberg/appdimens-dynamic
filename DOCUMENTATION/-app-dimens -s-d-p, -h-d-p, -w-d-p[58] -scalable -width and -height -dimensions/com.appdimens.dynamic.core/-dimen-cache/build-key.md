//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[buildKey](build-key.md)

# buildKey

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

internal fun [buildKey](build-key.md)(baseValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), isLandscape: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), calcType: [DimenCache.CalcType](-calc-type/index.md), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), valueType: [DimenCache.ValueType](-value-type/index.md), customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html)

Packs all dimension-calculation parameters into a single 64-bit [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) key.

Bit layout (MSB → LSB):

```kotlin
[63]     applyAspectRatio          1 bit
[62-31]  baseValue bits            32 bits  (Float.toRawBits)
[30-22]  (unused)                  9 bits
[21-18]  CalcType ordinal          4 bits  (covers 0..15)
[17-15]  ValueType                 3 bits  (covers 0..7)
[14-7]   sensitivityK fingerprint  8 bits  (float bits ushr 24 & 0xFF)
[6-5]    DpQualifier ordinal       2 bits  (covers 0..3)
[4-2]    Inverter ordinal          3 bits  (covers 0..7)
[1]      isLandscape               1 bit
[0]      ignoreMultiWindows        1 bit
```

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

internal fun [buildKey](build-key.md)(baseValue: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), isLandscape: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), calcType: [DimenCache.CalcType](-calc-type/index.md), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), valueType: [DimenCache.ValueType](-value-type/index.md), customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)? = null): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html)