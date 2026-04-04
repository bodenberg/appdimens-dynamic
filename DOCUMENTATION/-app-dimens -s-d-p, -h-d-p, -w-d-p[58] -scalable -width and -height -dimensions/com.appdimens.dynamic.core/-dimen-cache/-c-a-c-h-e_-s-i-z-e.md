//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[CACHE_SIZE](-c-a-c-h-e_-s-i-z-e.md)

# CACHE_SIZE

[jvm]
@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

internal const val [CACHE_SIZE](-c-a-c-h-e_-s-i-z-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) = 2048

Number of slots in the primary (Tier-1) fast cache. Must be a power of 2 so that `key and MASK` is a fast modulo.

2048 slots @ ~12 bytes per entry ≈ ~24 KB (keys) + ~8 KB (values) = ~32 KB total. Hit-rate analysis: typical app has 100–300 distinct dimension configurations; 2048 slots gives <15% fill ratio under normal usage — near-zero collision rate.