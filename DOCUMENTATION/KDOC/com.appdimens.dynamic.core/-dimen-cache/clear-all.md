//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[clearAll](clear-all.md)

# clearAll

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [clearAll](clear-all.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Clears all cache entries using [AtomicLongArray.lazySet](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicLongArray.html#lazyset) / [AtomicIntegerArray.lazySet](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicIntegerArray.html#lazyset) with 4× manual loop unrolling. This avoids issuing a full memory barrier on every element, which is safe because the next [getOrPut](get-or-put.md) will provide the required acquire/release semantics. Thread-safe.

PT Limpa todas as entradas com lazySet (sem barrier completo por elemento) e unrolling 4× para otimização de pipeline. Thread-safe.
