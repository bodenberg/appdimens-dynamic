//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[isInitializedFast](is-initialized-fast.md)

# isInitializedFast

[jvm]
@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

@[Volatile](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-volatile/index.html)

internal var [isInitializedFast](is-initialized-fast.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)

Internal flag to avoid [AtomicBoolean.get](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html#get) overhead on every hot-path call.

**Thread Safety**: marked `@Volatile` so that the `true` written by the background coroutine in [init](init.md) is immediately visible to all other threads without requiring a full memory barrier on every read.  Without `@Volatile` a thread that reads this field on a different CPU core may observe stale `false` indefinitely (data race / visibility bug on ARM64 weak memory model).