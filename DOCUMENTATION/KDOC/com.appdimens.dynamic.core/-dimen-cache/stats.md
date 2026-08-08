//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[stats](stats.md)

# stats

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [stats](stats.md)(): [DimenCache.CacheStats](-cache-stats/index.md)

EN Cache usage statistics over the active snapshot partitions: capacity = active partitions × 512 slots (bounded budget of 4 × 512 = 2048), populated slots, fill ratio, and (when [diagnosticsEnabled](diagnostics-enabled.md)) hit/miss/eviction counters.

PT Estatísticas de uso do cache sobre as partições de snapshot ativas: capacidade = partições ativas × 512 slots (orçamento 4 × 512 = 2048).
