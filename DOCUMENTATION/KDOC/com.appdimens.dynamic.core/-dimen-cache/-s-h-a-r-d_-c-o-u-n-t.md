//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[SHARD_COUNT](-s-h-a-r-d_-c-o-u-n-t.md)

# SHARD_COUNT

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SHARD_COUNT](-s-h-a-r-d_-c-o-u-n-t.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 4

EN Cache Sharding (Concurrency Partitioning) Split the cache into 4 shards to reduce false sharing and bus contention.
