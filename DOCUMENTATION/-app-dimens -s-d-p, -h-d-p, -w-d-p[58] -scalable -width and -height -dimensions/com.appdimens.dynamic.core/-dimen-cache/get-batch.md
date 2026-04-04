//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getBatch](get-batch.md)

# getBatch

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

fun [getBatch](get-batch.md)(keys: [LongArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long-array/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, compute: ([Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)) -> [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)): [FloatArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float-array/index.html)

EN SIMD-friendly batch resolution.

Resolves [keys](get-batch.md).size cache entries in a single tight loop. On a cache miss, the provided [compute](get-batch.md) lambda is called with the index; the result is stored and returned. The loop structure is intentionally simple to help the ART JIT emit vectorized (NEON) instructions for the computation body when all items compute the same formula.

This is a **public** API — callers outside the library can use it to batch-resolve any set of pre-built keys.

PT Resolução em lote amigável ao SIMD / JIT auto-vetorização. API pública — pode ser chamada por código fora da biblioteca.

#### Return

[FloatArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float-array/index.html) of resolved values in the same order as [keys](get-batch.md)

#### Parameters

jvm

| | |
|---|---|
| keys | Array of 64-bit keys built via [buildKey](build-key.md) |
| context | Optional context used for lazy init and persistence |
| compute | Lambda `(index: Int) -> Float` called on cache miss |