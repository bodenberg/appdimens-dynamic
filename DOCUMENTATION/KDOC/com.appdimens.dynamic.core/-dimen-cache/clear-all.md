//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[clearAll](clear-all.md)

# clearAll

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [clearAll](clear-all.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Clears all cache entries. Since 3.1.7 this detaches all snapshot partitions atomically (no disk I/O); the legacy shard arrays are still zeroed with `lazySet` + 4× unrolling for source compatibility. Thread-safe.

PT Limpa todas as entradas. Desde a 3.1.7, remove todas as partições de snapshot atomicamente (sem I/O de disco); os arrays legados de shards ainda são zerados com `lazySet` + unrolling 4× por compatibilidade. Thread-safe.
