//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getOrPut](get-or-put.md)

# getOrPut

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPut](get-or-put.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

Additional overloads:

- `getOrPut(key: Long, metrics: [DimenMetrics](../-dimen-metrics/index.md), compute: () -> Float): Float` — explicit snapshot for callers that already hold the configuration (Compose providers, custom containers).
- `getOrPut(key: Long, configuration: Configuration, context: Context? = null, compute: () -> Float): Float` — preserves the exact `Configuration` observed by the caller.
- `getOrPut(key: Long, compute: () -> Float): Float` — backward compatibility for non-context calls.

EN Reads from the cache or computes (and stores) a new value. **Lock-free.**

Since 3.1.7 the lookup is resolved against the window snapshot derived from [context]; a key is never served from a partition of a different size, density, font scale, or multi-window state. Non-finite results are never stored.

Cached or freshly-computed raw Float result

64-bit packed key from [buildKey](build-key.md)

Lambda invoked only on a cache **miss**

Optional context used to derive the window snapshot partition

PT O hot path é resolvido contra o snapshot da janela corrente; uma chave nunca é servida de uma partição de outro tamanho/densidade/fontScale/multi-window.
