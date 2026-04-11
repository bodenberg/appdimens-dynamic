//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getOrPutInternal](get-or-put-internal.md)

# getOrPutInternal

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [getOrPutInternal](get-or-put-internal.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)?, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Non-inline core logic for [getOrPut](get-or-put.md). Separated so that the public inline function does not need access to internal fields of [ShardWrapper](-shard-wrapper/index.md) directly. This function is @PublishedApi, making it visible to the inlined call-sites.

PT Núcleo não-inline de [getOrPut](get-or-put.md). Separado para evitar que a função inline pública precise de acesso direto aos campos internos de [ShardWrapper](-shard-wrapper/index.md).
