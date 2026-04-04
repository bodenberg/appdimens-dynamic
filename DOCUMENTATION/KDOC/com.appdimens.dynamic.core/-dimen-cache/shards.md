//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[shards](shards.md)

# shards

[jvm]
@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

@[JvmField](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)

internal val [shards](shards.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-array/index.html)<[DimenCache.ShardWrapper](-shard-wrapper/index.md)>

EN Sharded, padded primitive cache storage. Replaces the previous `keysArray` / `valueBitsArray` pair. Each shard is wrapped in a [ShardWrapper](-shard-wrapper/index.md) with 128-byte padding.