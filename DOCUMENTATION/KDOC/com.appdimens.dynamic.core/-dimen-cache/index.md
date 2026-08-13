//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)

# DimenCache

object [DimenCache](index.md)

EN Global, lock-free, shared cache for all AppDimens dimension calculations.

**Thread Safety**: Completely thread-safe. Since 3.1.8 the cache is **partitioned per immutable window snapshot** ([DimenMetrics](../-dimen-metrics/index.md)); each entry is published as a single atomic `CacheEntry` (key + value bits) reference, so concurrent readers can never observe another key's value.

PT Cache global, lock-free e compartilhado para todos os cálculos de dimensão do AppDimens.

data class [CacheStats](-cache-stats/index.md)(val capacity: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val populated: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val fillRatio: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val hits: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val misses: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val evictions: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) = 0, val hitRate: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0f)

EN Cache usage statistics snapshot over the active snapshot partitions. The hits, misses, evictions, and hitRate fields are only meaningful when [diagnosticsEnabled](diagnostics-enabled.md) is `true`.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal enum [CalcType](-calc-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[DimenCache.CalcType](-calc-type/index.md)>

EN Calculation types based on the library's package structure. PT Tipos de cálculo baseados na estrutura de pacotes da biblioteca.

internal class [ScreenFactors](-screen-factors/index.md)

EN Holds all screen-derived scaling factors in an object padded to exceed two ARM64 cache lines (2 × 64 bytes = 128 bytes). Retained for binary/source compatibility — production formulas resolve through [currentMetrics](current-metrics.md).

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal enum [ValueType](-value-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[DimenCache.ValueType](-value-type/index.md)>

EN Dimension type discriminator for the cache key. PT Discriminador de tipo de dimensão para a chave de cache.

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [ADJUSTMENT_SCALE](-a-d-j-u-s-t-m-e-n-t_-s-c-a-l-e.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [cachedUiMode](cached-ui-mode.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)

@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)private var [cachedUiModeConfigHash](cached-ui-mode-config-hash.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_ASPECT_RATIO](-c-t_-a-s-p-e-c-t_-r-a-t-i-o.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_DENSITY](-c-t_-d-e-n-s-i-t-y.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_DIAGONAL](-c-t_-d-i-a-g-o-n-a-l.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_INTERPOLATED](-c-t_-i-n-t-e-r-p-o-l-a-t-e-d.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_PERCENT](-c-t_-p-e-r-c-e-n-t.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_PERIMETER](-c-t_-p-e-r-i-m-e-t-e-r.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_POWER](-c-t_-p-o-w-e-r.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_LOGARITHMIC](-c-t_-l-o-g-a-r-i-t-h-m-i-c.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [CT_SCALED](-c-t_-s-c-a-l-e-d.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@get:[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)val [currentMetrics](current-metrics.md): [DimenMetrics](../-dimen-metrics/index.md)

EN The immutable window snapshot active for the current resolution — never a partially updated global factor set.

val [currentArMultiplier](current-ar-multiplier.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentAspectRatioMul](current-aspect-ratio-mul.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentDensity](current-density.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentLogNormalizedAr](current-log-normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentNormalizedAr](current-normalized-ar.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentScale](current-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [currentSmallestWidthDp](current-smallest-width-dp.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [diagnosticsEnabled](diagnostics-enabled.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN When `true`, hit/miss/eviction counters are incremented on every cache operation. Uses [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html) for low-contention counting. Disabled by default so production apps pay zero overhead.

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [evictionCount](eviction-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [factors](factors.md): [DimenCache.ScreenFactors](-screen-factors/index.md)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [hitCount](hit-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [INV_BASE_RATIO](-i-n-v_-b-a-s-e_-r-a-t-i-o.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.0033333334f

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [isEnabled](is-enabled.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN Master switch for the cache system. If disabled, all calls will recompute. PT Chave mestre para o sistema de cache. Se desativado, todos os cálculos são refeitos.

val [isInitialized](is-initialized.md): [AtomicBoolean](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html)

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[Volatile](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-volatile/index.html)internal var [isInitializedFast](is-initialized-fast.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

Internal flag to avoid [AtomicBoolean.get](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html#get) overhead on every hot-path call.

internal val [isInitializing](is-initializing.md): [AtomicBoolean](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/AtomicBoolean.html)

@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)val [missCount](miss-count.md): [LongAdder](https://developer.android.com/reference/kotlin/java/util/concurrent/atomic/LongAdder.html)

private val [resetListeners](reset-listeners.md): [CopyOnWriteArrayList](https://developer.android.com/reference/kotlin/java/util/concurrent/CopyOnWriteArrayList.html)<() -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html)>

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal const val [SENSITIVITY_DEFAULT](-s-e-n-s-i-t-i-v-i-t-y_-d-e-f-a-u-l-t.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [addResetListener](add-reset-listener.md)(listener: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Registers a listener to be notified when the cache is cleared. PT Registra um listener para ser notificado quando o cache for limpo.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [buildKey](build-key.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), isLandscape: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), calcType: [DimenCache.CalcType](-calc-type/index.md), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), valueType: [DimenCache.ValueType](-value-type/index.md), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

Packs all dimension-calculation parameters into a single 64-bit [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html) key. Requires finite inputs.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [calculateRawScaling](calculate-raw-scaling.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Unified scaling engine over the immutable metrics of the current resolution ([currentMetrics](current-metrics.md)).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [clear](clear.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Clears all cache slots. Java-compatible alias.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [clearAll](clear-all.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null)

EN Detaches all snapshot partitions atomically (no disk I/O).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [clearFontScaleDependentEntries](clear-font-scale-dependent-entries.md)()

EN Compatibility hook — drops all partitions (font scale participates in [DimenMetrics](../-dimen-metrics/index.md) equality).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getBatch](get-batch.md)(keys: [LongArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long-array/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, compute: ([Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html)

EN SIMD-friendly batch resolution, atomic with respect to one window snapshot.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getCachedUiModeType](get-cached-ui-mode-type.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)

EN Per-context cached UiModeType via a weak map (no Activity leak).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPut](get-or-put.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Resolves against the window snapshot derived from [context]; a lookup never crosses snapshot partitions. Additional overloads accept an explicit [DimenMetrics](../-dimen-metrics/index.md) or [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPutAspectRatio](get-or-put-aspect-ratio.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Exact `ln()` computed once per snapshot — no lookup table.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getOrPutInternal](get-or-put-internal.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)?, compute: () -> [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Compatibility entry point; converts the context to an immutable window snapshot before any cache lookup.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [init](init.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Synchronous, window-local initialization (no DataStore, no background I/O).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [invalidateOnConfigChange](invalidate-on-config-change.md)(new: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

EN Compatibility hook — snapshot partitions make explicit invalidation unnecessary for correctness.

internal fun [loadFromByteArray](load-from-byte-array.md)(data: [ByteArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-byte-array/index.html))

EN Compatibility no-op (persistence removed in 3.1.8).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [peek](peek.md)(key: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

EN Reads a stored cache value without computing a fallback. Returns `null` on a miss. Overloads accept a [Context](https://developer.android.com/reference/kotlin/android/content/Context.html) or an explicit [DimenMetrics](../-dimen-metrics/index.md) partition.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [removeResetListener](remove-reset-listener.md)(listener: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Removes a previously registered reset listener. PT Remove um listener de reset previamente registrado.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [resetDiagnostics](reset-diagnostics.md)()

EN Resets the diagnostic counters (hit, miss, eviction) to zero. PT Zera os contadores de diagnóstico (hit, miss, eviction).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [saveToPersistence](save-to-persistence.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Binary-compatible no-op; the result cache is intentionally in-memory and snapshot-scoped.

internal fun [serializeToByteArray](serialize-to-byte-array.md)(): [ByteArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-byte-array/index.html)

EN Compatibility stub returning an empty blob.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [shutdown](shutdown.md)()

EN No-op — dimension resolution no longer owns a background persistence scope.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [stats](stats.md)(): [DimenCache.CacheStats](-cache-stats/index.md)

EN Cache usage statistics over the active snapshot partitions.

private fun [updateFactors](update-factors.md)(config: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

EN Compatibility — populates the legacy [factors](factors.md) object and refreshes the fallback snapshot.

internal fun <T> [withCompositionMetrics](with-composition-metrics.md)(metrics: [DimenMetrics](../-dimen-metrics/index.md)?, block: () -> T): T

EN Runs [block] with the supplied snapshot active on the current thread (used by Compose helpers).

## Types

| Name | Summary |
|---|---|
| [CacheStats](-cache-stats/index.md) |  |
| [CalcType](-calc-type/index.md) |  |
| [ScreenFactors](-screen-factors/index.md) |  |
| [ValueType](-value-type/index.md) |  |


## Properties

| Name | Summary |
|---|---|
| [ADJUSTMENT_SCALE](-a-d-j-u-s-t-m-e-n-t_-s-c-a-l-e.md) |  |
| [cachedUiMode](cached-ui-mode.md) |  |
| [cachedUiModeConfigHash](cached-ui-mode-config-hash.md) |  |
| [CT_ASPECT_RATIO](-c-t_-a-s-p-e-c-t_-r-a-t-i-o.md) |  |
| [CT_DENSITY](-c-t_-d-e-n-s-i-t-y.md) |  |
| [CT_DIAGONAL](-c-t_-d-i-a-g-o-n-a-l.md) |  |
| [CT_INTERPOLATED](-c-t_-i-n-t-e-r-p-o-l-a-t-e-d.md) |  |
| [CT_LOGARITHMIC](-c-t_-l-o-g-a-r-i-t-h-m-i-c.md) |  |
| [CT_PERCENT](-c-t_-p-e-r-c-e-n-t.md) |  |
| [CT_PERIMETER](-c-t_-p-e-r-i-m-e-t-e-r.md) |  |
| [CT_POWER](-c-t_-p-o-w-e-r.md) |  |
| [CT_SCALED](-c-t_-s-c-a-l-e-d.md) |  |
| [currentArMultiplier](current-ar-multiplier.md) |  |
| [currentAspectRatioMul](current-aspect-ratio-mul.md) |  |
| [currentDensity](current-density.md) |  |
| [currentLogNormalizedAr](current-log-normalized-ar.md) |  |
| [currentMetrics](current-metrics.md) |  |
| [currentNormalizedAr](current-normalized-ar.md) |  |
| [currentScale](current-scale.md) |  |
| [currentSmallestWidthDp](current-smallest-width-dp.md) |  |
| [diagnosticsEnabled](diagnostics-enabled.md) |  |
| [evictionCount](eviction-count.md) |  |
| [factors](factors.md) |  |
| [hitCount](hit-count.md) |  |
| [INV_BASE_RATIO](-i-n-v_-b-a-s-e_-r-a-t-i-o.md) |  |
| [isEnabled](is-enabled.md) |  |
| [isInitialized](is-initialized.md) |  |
| [isInitializedFast](is-initialized-fast.md) |  |
| [isInitializing](is-initializing.md) |  |
| [missCount](miss-count.md) |  |
| [resetListeners](reset-listeners.md) |  |


## Functions

| Name | Summary |
|---|---|
| [addResetListener](add-reset-listener.md) |  |
| [buildKey](build-key.md) |  |
| [calculateRawScaling](calculate-raw-scaling.md) |  |
| [clear](clear.md) |  |
| [clearAll](clear-all.md) |  |
| [clearFontScaleDependentEntries](clear-font-scale-dependent-entries.md) |  |
| [getBatch](get-batch.md) |  |
| [getCachedUiModeType](get-cached-ui-mode-type.md) |  |
| [getOrPut](get-or-put.md) |  |
| [getOrPutAspectRatio](get-or-put-aspect-ratio.md) |  |
| [getOrPutInternal](get-or-put-internal.md) |  |
| [init](init.md) |  |
| [invalidateOnConfigChange](invalidate-on-config-change.md) |  |
| [loadFromByteArray](load-from-byte-array.md) |  |
| [peek](peek.md) |  |
| [removeResetListener](remove-reset-listener.md) |  |
| [resetDiagnostics](reset-diagnostics.md) |  |
| [saveToPersistence](save-to-persistence.md) |  |
| [serializeToByteArray](serialize-to-byte-array.md) |  |
| [shutdown](shutdown.md) |  |
| [stats](stats.md) |  |
| [updateFactors](update-factors.md) |  |
| [withCompositionMetrics](with-composition-metrics.md) |  |
