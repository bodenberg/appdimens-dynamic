//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.core](../../index.md)/[DimenCache](../index.md)/[ShardWrapper](index.md)

# ShardWrapper

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal class [ShardWrapper](index.md)(shardSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Padded cache shard wrapper that prevents false sharing between shards across CPU cores on ARM64 (cache line = 64 bytes).

PT Wrapper de shard com padding que previne false sharing entre núcleos no ARM64 (linha de cache = 64 bytes).

constructor(shardSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p0](_p0.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p1](_p1.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p2](_p2.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p3](_p3.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p4](_p4.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p5](_p5.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p6](_p6.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p7](_p7.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p8](_p8.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_p9](_p9.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_pA](_p-a.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_pB](_p-b.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_pC](_p-c.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [_pD](_p-d.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [keys](keys.md): [AtomicLongArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicLongArray.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [values](values.md): [AtomicIntegerArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicIntegerArray.html)

## Constructors

| Name | Summary |
|---|---|
| [ShardWrapper](-shard-wrapper.md) |  |


## Properties

| Name | Summary |
|---|---|
| [_p0](_p0.md) |  |
| [_p1](_p1.md) |  |
| [_p2](_p2.md) |  |
| [_p3](_p3.md) |  |
| [_p4](_p4.md) |  |
| [_p5](_p5.md) |  |
| [_p6](_p6.md) |  |
| [_p7](_p7.md) |  |
| [_p8](_p8.md) |  |
| [_p9](_p9.md) |  |
| [_pA](_p-a.md) |  |
| [_pB](_p-b.md) |  |
| [_pC](_p-c.md) |  |
| [_pD](_p-d.md) |  |
| [keys](keys.md) |  |
| [values](values.md) |  |
