//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getOrPutInternal](get-or-put-internal.md)

# getOrPutInternal

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPutInternal](get-or-put-internal.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)?, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Compatibility entry point for callers that cannot use the public overload. The context is converted to an immutable window snapshot before any cache lookup, so the same per-snapshot guarantees of [getOrPut](get-or-put.md) apply.

PT Ponto de entrada de compatibilidade; o contexto é convertido em snapshot imutável de janela antes de qualquer consulta ao cache.
