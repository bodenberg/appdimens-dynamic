//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getOrPut](get-or-put.md)

# getOrPut

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

inline fun [getOrPut](get-or-put.md)(key: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, crossinline compute: () -> [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Reads from the cache or computes (and stores) a new value. **Lock-free.**

The full hot path is inlined at every call-site by the Kotlin compiler. This eliminates all method-call overhead and gives the JIT full visibility over the loop body when called from a batch context.

[getOrPutInternal](get-or-put-internal.md) is kept as a non-inline helper for callers (like [getBatch](get-batch.md)) that cannot use inline functions.

#### Return

Cached or freshly-computed raw Float result

PT O hot path completo é inlinado em cada call-site pelo compilador Kotlin, eliminando overhead de chamada e dando ao JIT visibilidade total do loop.

#### Parameters

jvm

| | |
|---|---|
| key | 64-bit packed key from [buildKey](build-key.md) |
| compute | Lambda invoked only on a cache **miss** |

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

inline fun [getOrPut](get-or-put.md)(key: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html), crossinline compute: () -> [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

Backward compatibility for non-context calls.