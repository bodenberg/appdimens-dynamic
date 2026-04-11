//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.fluid](index.md)/[toDynamicFluidPx](to-dynamic-fluid-px.md)

# toDynamicFluidPx

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicFluidPx](to-dynamic-fluid-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (base Dp value) into a dynamically scaled pixel [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) for View-based (non-Compose) code.

The scaling logic:

Builds a 64-bit packed cache key from all dimension parameters.

**If****enableCache****is**`true` (default): checks [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) first. On a hit, returns the cached pixel value immediately. On a miss, calls [calculateFluidDp](calculate-fluid-dp.md) and converts Dp→px via `scaledDp * displayMetrics.density` (equivalent to [android.util.TypedValue.applyDimension](https://developer.android.com/reference/kotlin/android/util/TypedValue.html#applydimension) for `COMPLEX_UNIT_DIP`), then stores the result.

**If****enableCache****is**`false`: computes directly via [calculateFluidDp](calculate-fluid-dp.md), bypassing cache.

⚠️ **Bypass note**: when [applyAspectRatio](to-dynamic-fluid-px.md) is `false` and [qualifier](to-dynamic-fluid-px.md) is `SMALL_WIDTH` with `DEFAULT` inverter, the [DimenCache.getOrPut](../com.appdimens.dynamic.core/-dimen-cache/get-or-put.md) call internally bypasses the hash lookup because a raw multiply (~2 ns) is faster than the cache access (~5 ns). Calls with these parameters measure raw math performance, NOT cache throughput.

**Bulk resolution:** for many keys in one pass, prefer building [LongArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long-array/index.html) keys with [DimenCache.buildKey](../com.appdimens.dynamic.core/-dimen-cache/build-key.md) and [DimenCache.getBatch](../com.appdimens.dynamic.core/-dimen-cache/get-batch.md). **Early init:** call DimenSdp.warmupCache (or DimenSsp.warmupCache) once with your [android.content.Context](https://developer.android.com/reference/kotlin/android/content/Context.html) so persistence/DataStore work does not land on the first hot-frame call.

PT Converte um [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (valor Dp base) em um [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) em pixels dinamicamente escalado para código View-based.

A lógica de escalonamento:

Constrói uma chave de cache de 64 bits a partir de todos os parâmetros da dimensão.

**Se****enableCache****for**`true` (padrão): consulta o [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) primeiro. No acerto, retorna o valor em pixels cacheado; no miss, calcula via [calculateFluidDp](calculate-fluid-dp.md) e armazena.

**Se****enableCache****for**`false`: calcula diretamente via [calculateFluidDp](calculate-fluid-dp.md).

Dynamically scaled pixel value as [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).

Android [android.content.Context](https://developer.android.com/reference/kotlin/android/content/Context.html) for configuration and density access.

Screen dimension qualifier: [com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-s-m-a-l-l_-w-i-d-t-h/index.md), [com.appdimens.dynamic.common.DpQualifier.HEIGHT](../com.appdimens.dynamic.common/-dp-qualifier/-h-e-i-g-h-t/index.md), or [com.appdimens.dynamic.common.DpQualifier.WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-w-i-d-t-h/index.md).

Orientation-based dimension swap rule (default: [Inverter.DEFAULT](../com.appdimens.dynamic.common/-inverter/-d-e-f-a-u-l-t/index.md)).

If `true`, returns the base value in pixels unscaled when in split-screen.

If `true`, applies the aspect-ratio multiplier.

Override for the AR sensitivity constant (null = library default).
