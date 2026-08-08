//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenMetrics](index.md)

# DimenMetrics

data class [DimenMetrics](index.md)(val screenWidthDp: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val screenHeightDp: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val smallestScreenWidthDp: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val densityDpi: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val orientation: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val isInMultiWindowMode: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))

EN A value snapshot, not a process-wide mutable "current screen". The primary constructor intentionally contains only the inputs that affect a result, so Kotlin's generated equality can be used as an exact cache partition key; derived values are calculated once when the snapshot is created.

Since 3.1.7 every dimension is a pure function of the window in which it is rendered: keeping these values together prevents a calculation from observing a mix of old and new configuration fields while a window is being resized, and the cache is partitioned per snapshot ([DimenCache](../-dimen-cache/index.md)).

PT Snapshot de valores por janela, não um "tela atual" mutável de processo. Igualdade estrutural serve de chave exata de partição do cache (desde a 3.1.7).

val [density](density.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [defaultAspectRatioMultiplier](default-aspect-ratio-multiplier.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [defaultScaledAspectRatioMultiplier](default-scaled-aspect-ratio-multiplier.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [fontScale](font-scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [logNormalizedAspectRatio](log-normalized-aspect-ratio.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [maxDimensionDp](max-dimension-dp.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [minDimensionDp](min-dimension-dp.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [normalizedAspectRatio](normalized-aspect-ratio.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [scale](scale.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

val [smallestWidthDp](smallest-width-dp.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [aspectRatioMultiplier](aspect-ratio-multiplier.md)(customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

fun [scaledMultiplier](scaled-multiplier.md)(applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

companion object

fun [from](-companion/from.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), isInMultiWindowMode: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false): [DimenMetrics](index.md)

val [DEFAULT](-companion/-d-e-f-a-u-l-t.md): [DimenMetrics](index.md)

## Properties

| Name | Summary |
|---|---|
| [density](density.md) |  |
| [defaultAspectRatioMultiplier](default-aspect-ratio-multiplier.md) |  |
| [defaultScaledAspectRatioMultiplier](default-scaled-aspect-ratio-multiplier.md) |  |
| [fontScale](font-scale.md) |  |
| [logNormalizedAspectRatio](log-normalized-aspect-ratio.md) |  |
| [maxDimensionDp](max-dimension-dp.md) |  |
| [minDimensionDp](min-dimension-dp.md) |  |
| [normalizedAspectRatio](normalized-aspect-ratio.md) |  |
| [scale](scale.md) |  |
| [smallestWidthDp](smallest-width-dp.md) |  |


## Functions

| Name | Summary |
|---|---|
| [aspectRatioMultiplier](aspect-ratio-multiplier.md) |  |
| [scaledMultiplier](scaled-multiplier.md) |  |


## Companion

| Name | Summary |
|---|---|
| [DEFAULT](-companion/-d-e-f-a-u-l-t.md) |  |
| [from](-companion/from.md) |  |
