//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.core](../../index.md)/[DimenCache](../index.md)/[CacheStats](index.md)

# CacheStats

data class [CacheStats](index.md)(val capacity: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val populated: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val fillRatio: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val hits: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val misses: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val evictions: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val hitRate: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f)

EN Cache usage statistics snapshot. The hits, misses, evictions, and hitRate fields are only meaningful when [diagnosticsEnabled](../diagnostics-enabled.md) is `true`.

PT Snapshot de métricas de uso do cache. hits, misses, evictions e hitRate só são significativos quando [diagnosticsEnabled](../diagnostics-enabled.md) está `true`.

constructor(capacity: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), populated: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), fillRatio: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), hits: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, misses: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, evictions: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, hitRate: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f)

val [capacity](capacity.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

val [evictions](evictions.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

val [fillRatio](fill-ratio.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [hitRate](hit-rate.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [hits](hits.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

val [misses](misses.md): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

val [populated](populated.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)

## Constructors

| Name | Summary |
|---|---|
| [CacheStats](-cache-stats.md) |  |


## Properties

| Name | Summary |
|---|---|
| [capacity](capacity.md) |  |
| [evictions](evictions.md) |  |
| [fillRatio](fill-ratio.md) |  |
| [hitRate](hit-rate.md) |  |
| [hits](hits.md) |  |
| [misses](misses.md) |  |
| [populated](populated.md) |  |


## Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) |  |
