//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)

# DimenCache

object [DimenCache](index.md)

EN Global, lock-free, shared cache for all AppDimens dimension calculations.

**Thread Safety**: Completely thread-safe. All reads and writes are lock-free using [AtomicLongArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicLongArray.html) / [AtomicIntegerArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicIntegerArray.html). If two threads write identically-keyed entries simultaneously, the last write wins — always correct because both computed the same value.

PT Cache global, lock-free e compartilhado para todos os cálculos de dimensão do AppDimens.

data class [CacheStats](-cache-stats/index.md)(val capacity: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val populated: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val fillRatio: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val hits: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val misses: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val evictions: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val hitRate: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f)

EN Cache usage statistics snapshot. The hits, misses, evictions, and hitRate fields are only meaningful when [diagnosticsEnabled](diagnostics-enabled.md) is `true`.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal enum [CalcType](-calc-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[DimenCache.CalcType](-calc-type/index.md)>

EN Calculation types based on the library's package structure. PT Tipos de cálculo baseados na estrutura de pacotes da biblioteca.

internal class [ScreenFactors](-screen-factors/index.md)

EN Holds all screen-derived scaling factors in an object padded to exceed two ARM64 cache lines (2 × 64 bytes = 128 bytes), ensuring that writes during [updateFactors](update-factors.md) do not invalidate unrelated reads on sibling CPU cores.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal class [ShardWrapper](-shard-wrapper/index.md)(shardSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Padded cache shard wrapper that prevents false sharing between shards across CPU cores on ARM64 (cache line = 64 bytes).

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal enum [ValueType](-value-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[DimenCache.ValueType](-value-type/index.md)>

EN Dimension type discriminator for the cache key. PT Discriminador de tipo de dimensão para a chave de cache.

@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)private var [_scope](_scope.md): CoroutineScope?

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [ADJUSTMENT_SCALE](-a-d-j-u-s-t-m-e-n-t_-s-c-a-l-e.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [CACHE_SIZE](-c-a-c-h-e_-s-i-z-e.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 2048

Number of slots in the primary (Tier-1) fast cache. Must be a power of 2 so that `key and MASK` is a fast modulo.

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [cachedUiMode](cached-ui-mode.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)

@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)private var [cachedUiModeConfigHash](cached-ui-mode-config-hash.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_ASPECT_RATIO](-c-t_-a-s-p-e-c-t_-r-a-t-i-o.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_DENSITY](-c-t_-d-e-n-s-i-t-y.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_DIAGONAL](-c-t_-d-i-a-g-o-n-a-l.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_INTERPOLATED](-c-t_-i-n-t-e-r-p-o-l-a-t-e-d.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_PERCENT](-c-t_-p-e-r-c-e-n-t.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_PERIMETER](-c-t_-p-e-r-i-m-e-t-e-r.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_SCALED](-c-t_-s-c-a-l-e-d.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentArMultiplier](current-ar-multiplier.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentAspectRatioMul](current-aspect-ratio-mul.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentDensity](current-density.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentDiagonalScale](current-diagonal-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentInterpolatedScale](current-interpolated-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentLogNormalizedAr](current-log-normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentLogScale](current-log-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentNormalizedAr](current-normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentPerimeterScale](current-perimeter-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentPowerScale](current-power-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentScale](current-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [currentSmallestWidthDp](current-smallest-width-dp.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

internal val [Context](https://developer.android.com/reference/kotlin/android/content/Context.html).[dataStore](data-store.md): ERROR CLASS: Symbol not found for DataStore<ERROR CLASS: Symbol not found for Preferences>

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [diagnosticsEnabled](diagnostics-enabled.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN When `true`, hit/miss/eviction counters are incremented on every cache operation. Uses [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html) for low-contention counting. Disabled by default so production apps pay zero overhead.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [evictionCount](eviction-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [factors](factors.md): [DimenCache.ScreenFactors](-screen-factors/index.md)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [hitCount](hit-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [INV_BASE_RATIO](-i-n-v_-b-a-s-e_-r-a-t-i-o.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0033333334f

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [isEnabled](is-enabled.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN Master switch for the cache system. If disabled, all calls will recompute. PT Chave mestre para o sistema de cache. Se desativado, todos os cálculos são refeitos.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [isInitialized](is-initialized.md): [AtomicBoolean](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [isInitializedFast](is-initialized-fast.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

Internal flag to avoid [AtomicBoolean.get](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html#get) overhead on every hot-path call.

internal val [isInitializing](is-initializing.md): [AtomicBoolean](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html)

internal val [KEY_CACHE_DATA](-k-e-y_-c-a-c-h-e_-d-a-t-a.md): ERROR CLASS: Unresolved name: byteArrayPreferencesKey

internal val [KEY_SW_DP](-k-e-y_-s-w_-d-p.md): ERROR CLASS: Unresolved name: intPreferencesKey

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [keysArray](keys-array.md): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)<[AtomicLongArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicLongArray.html)>

EN Backward-compatible accessors — still referenced by DimenCacheTest. These are thin aliases into [shards](shards.md); no extra memory is allocated.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [missCount](miss-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

private val [resetListeners](reset-listeners.md): [CopyOnWriteArrayList](https://developer.android.com/reference/kotlin/java/util/concurrent/CopyOnWriteArrayList.html)<() -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)>

private val [saveFlow](save-flow.md): MutableSharedFlow<[Context](https://developer.android.com/reference/kotlin/android/content/Context.html)>

internal val [scope](scope.md): CoroutineScope

private val [scopeLock](scope-lock.md): [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SENSITIVITY_DEFAULT](-s-e-n-s-i-t-i-v-i-t-y_-d-e-f-a-u-l-t.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SHARD_COUNT](-s-h-a-r-d_-c-o-u-n-t.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 4

EN Cache Sharding (Concurrency Partitioning) Split the cache into 4 shards to reduce false sharing and bus contention.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SHARD_MASK](-s-h-a-r-d_-m-a-s-k.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SHARD_SIZE](-s-h-a-r-d_-s-i-z-e.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SHARD_SIZE_MASK](-s-h-a-r-d_-s-i-z-e_-m-a-s-k.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [shards](shards.md): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)<[DimenCache.ShardWrapper](-shard-wrapper/index.md)>

EN Sharded, padded primitive cache storage. Replaces the previous `keysArray` / `valueBitsArray` pair. Each shard is wrapped in a [ShardWrapper](-shard-wrapper/index.md) with 128-byte padding.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal val [valueBitsArray](value-bits-array.md): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)<[AtomicIntegerArray](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicIntegerArray.html)>

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [addResetListener](add-reset-listener.md)(listener: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Registers a listener to be notified when the cache is cleared. PT Registra um listener para ser notificado quando o cache for limpo.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [buildKey](build-key.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), isLandscape: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), calcType: [DimenCache.CalcType](-calc-type/index.md), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), valueType: [DimenCache.ValueType](-value-type/index.md), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

Packs all dimension-calculation parameters into a single 64-bit [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) key.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [calculateRawScaling](calculate-raw-scaling.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Unified high-performance scaling engine. Reads from [factors](factors.md) — padded object, guaranteeing that the read of `scale` and `arMultiplier` land on the same cache line as all other factor fields.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [clear](clear.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Clears all cache slots. Java-compatible alias.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [clearAll](clear-all.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Clears all cache entries using [AtomicLongArray.lazySet](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicLongArray.html#lazyset) / [AtomicIntegerArray.lazySet](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicIntegerArray.html#lazyset) with 4× manual loop unrolling. This avoids issuing a full memory barrier on every element, which is safe because the next [getOrPut](get-or-put.md) will provide the required acquire/release semantics. Thread-safe.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getBatch](get-batch.md)(keys: [LongArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long-array/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, compute: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html)

EN SIMD-friendly batch resolution.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)internal fun [getCachedUiModeType](get-cached-ui-mode-type.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)inline fun [getOrPut](get-or-put.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), crossinline compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

Backward compatibility for non-context calls.

EN Reads from the cache or computes (and stores) a new value. **Lock-free.**

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [getOrPutAspectRatio](get-or-put-aspect-ratio.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [getOrPutInternal](get-or-put-internal.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)?, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Non-inline core logic for [getOrPut](get-or-put.md). Separated so that the public inline function does not need access to internal fields of [ShardWrapper](-shard-wrapper/index.md) directly. This function is @PublishedApi, making it visible to the inlined call-sites.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [init](init.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [invalidateOnConfigChange](invalidate-on-config-change.md)(old: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)?, new: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

EN Selectively invalidates the cache based on what actually changed in [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html).

private fun [launchSaveCollector](launch-save-collector.md)(target: CoroutineScope)

internal fun [loadFromByteArray](load-from-byte-array.md)(data: [ByteArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-byte-array/index.html))

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [peek](peek.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

EN Reads a stored cache value without computing a fallback. Returns `null` on a miss.

private suspend fun [performSave](perform-save.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [removeResetListener](remove-reset-listener.md)(listener: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Removes a previously registered reset listener. PT Remove um listener de reset previamente registrado.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [resetDiagnostics](reset-diagnostics.md)()

EN Resets the diagnostic counters (hit, miss, eviction) to zero. PT Zera os contadores de diagnóstico (hit, miss, eviction).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [saveToPersistence](save-to-persistence.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

internal fun [serializeToByteArray](serialize-to-byte-array.md)(): [ByteArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-byte-array/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [shutdown](shutdown.md)()

EN Cancels the background persistence scope. Intended for test teardown. The scope is automatically re-created on next use (e.g. [saveToPersistence](save-to-persistence.md)).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [stats](stats.md)(): [DimenCache.CacheStats](-cache-stats/index.md)

private fun [updateFactors](update-factors.md)(config: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

## Types

| Name | Summary |
|---|---|
| [CacheStats](-cache-stats/index.md) |  |
| [CalcType](-calc-type/index.md) |  |
| [ScreenFactors](-screen-factors/index.md) |  |
| [ShardWrapper](-shard-wrapper/index.md) |  |
| [ValueType](-value-type/index.md) |  |


## Properties

| Name | Summary |
|---|---|
| [_scope](_scope.md) |  |
| [ADJUSTMENT_SCALE](-a-d-j-u-s-t-m-e-n-t_-s-c-a-l-e.md) |  |
| [CACHE_SIZE](-c-a-c-h-e_-s-i-z-e.md) |  |
| [cachedUiMode](cached-ui-mode.md) |  |
| [cachedUiModeConfigHash](cached-ui-mode-config-hash.md) |  |
| [CT_ASPECT_RATIO](-c-t_-a-s-p-e-c-t_-r-a-t-i-o.md) |  |
| [CT_DENSITY](-c-t_-d-e-n-s-i-t-y.md) |  |
| [CT_DIAGONAL](-c-t_-d-i-a-g-o-n-a-l.md) |  |
| [CT_INTERPOLATED](-c-t_-i-n-t-e-r-p-o-l-a-t-e-d.md) |  |
| [CT_PERCENT](-c-t_-p-e-r-c-e-n-t.md) |  |
| [CT_PERIMETER](-c-t_-p-e-r-i-m-e-t-e-r.md) |  |
| [CT_SCALED](-c-t_-s-c-a-l-e-d.md) |  |
| [currentArMultiplier](current-ar-multiplier.md) |  |
| [currentAspectRatioMul](current-aspect-ratio-mul.md) |  |
| [currentDensity](current-density.md) |  |
| [currentDiagonalScale](current-diagonal-scale.md) |  |
| [currentInterpolatedScale](current-interpolated-scale.md) |  |
| [currentLogNormalizedAr](current-log-normalized-ar.md) |  |
| [currentLogScale](current-log-scale.md) |  |
| [currentNormalizedAr](current-normalized-ar.md) |  |
| [currentPerimeterScale](current-perimeter-scale.md) |  |
| [currentPowerScale](current-power-scale.md) |  |
| [currentScale](current-scale.md) |  |
| [currentSmallestWidthDp](current-smallest-width-dp.md) |  |
| [dataStore](data-store.md) |  |
| [diagnosticsEnabled](diagnostics-enabled.md) |  |
| [evictionCount](eviction-count.md) |  |
| [factors](factors.md) |  |
| [hitCount](hit-count.md) |  |
| [INV_BASE_RATIO](-i-n-v_-b-a-s-e_-r-a-t-i-o.md) |  |
| [isEnabled](is-enabled.md) |  |
| [isInitialized](is-initialized.md) |  |
| [isInitializedFast](is-initialized-fast.md) |  |
| [isInitializing](is-initializing.md) |  |
| [KEY_CACHE_DATA](-k-e-y_-c-a-c-h-e_-d-a-t-a.md) |  |
| [KEY_SW_DP](-k-e-y_-s-w_-d-p.md) |  |
| [keysArray](keys-array.md) |  |
| [missCount](miss-count.md) |  |
| [resetListeners](reset-listeners.md) |  |
| [saveFlow](save-flow.md) |  |
| [scope](scope.md) |  |
| [scopeLock](scope-lock.md) |  |
| [SENSITIVITY_DEFAULT](-s-e-n-s-i-t-i-v-i-t-y_-d-e-f-a-u-l-t.md) |  |
| [SHARD_COUNT](-s-h-a-r-d_-c-o-u-n-t.md) |  |
| [SHARD_MASK](-s-h-a-r-d_-m-a-s-k.md) |  |
| [SHARD_SIZE](-s-h-a-r-d_-s-i-z-e.md) |  |
| [SHARD_SIZE_MASK](-s-h-a-r-d_-s-i-z-e_-m-a-s-k.md) |  |
| [shards](shards.md) |  |
| [valueBitsArray](value-bits-array.md) |  |


## Functions

| Name | Summary |
|---|---|
| [addResetListener](add-reset-listener.md) |  |
| [buildKey](build-key.md) |  |
| [calculateRawScaling](calculate-raw-scaling.md) |  |
| [clear](clear.md) |  |
| [clearAll](clear-all.md) |  |
| [getBatch](get-batch.md) |  |
| [getCachedUiModeType](get-cached-ui-mode-type.md) |  |
| [getOrPut](get-or-put.md) |  |
| [getOrPutAspectRatio](get-or-put-aspect-ratio.md) |  |
| [getOrPutInternal](get-or-put-internal.md) |  |
| [init](init.md) |  |
| [invalidateOnConfigChange](invalidate-on-config-change.md) |  |
| [launchSaveCollector](launch-save-collector.md) |  |
| [loadFromByteArray](load-from-byte-array.md) |  |
| [peek](peek.md) |  |
| [performSave](perform-save.md) |  |
| [removeResetListener](remove-reset-listener.md) |  |
| [resetDiagnostics](reset-diagnostics.md) |  |
| [saveToPersistence](save-to-persistence.md) |  |
| [serializeToByteArray](serialize-to-byte-array.md) |  |
| [shutdown](shutdown.md) |  |
| [stats](stats.md) |  |
| [updateFactors](update-factors.md) |  |
