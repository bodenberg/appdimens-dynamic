//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[diagnosticsEnabled](diagnostics-enabled.md)

# diagnosticsEnabled

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [diagnosticsEnabled](diagnostics-enabled.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN When `true`, hit/miss/eviction counters are incremented on every cache operation. Uses [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html) for low-contention counting. Disabled by default so production apps pay zero overhead.

PT Quando `true`, contadores de hit/miss/eviction são incrementados a cada operação. Desativado por padrão para não penalizar apps em produção.
