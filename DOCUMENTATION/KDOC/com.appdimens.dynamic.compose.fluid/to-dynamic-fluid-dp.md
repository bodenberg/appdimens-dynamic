//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.fluid](index.md)/[toDynamicFluidDp](to-dynamic-fluid-dp.md)

# toDynamicFluidDp

fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[toDynamicFluidDp](to-dynamic-fluid-dp.md)(qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Converts a [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (base Dp value) into a dynamically scaled Dp for use in Jetpack Compose.

The scaling logic:

Checks [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) first. On a cache hit, returns the precomputed value; otherwise, computes via [calculateFluidDpCompose](calculate-fluid-dp-compose.md) and stores it.

Uses the internal bypass mechanism in [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) for sub-nanosecond latency on common width-scaling paths.

The remember block ensures recalculation only when configuration changes.

⚠️ **Bypass note**: when [applyAspectRatio](to-dynamic-fluid-dp.md) is `false` and [qualifier](to-dynamic-fluid-dp.md) is `SMALL_WIDTH` with `DEFAULT` inverter, the cache is bypassed internally because a raw multiply (~2 ns) is faster than the cache lookup (~5 ns). This is intentional and not a bug.

PT Converte um [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) (valor Dp base) em um Dp dinamicamente escalado para uso no Jetpack Compose.

A lógica de escalonamento:

Consulta o [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) primeiro. No acerto, retorna o Float cacheado; no miss, calcula via [calculateFluidDpCompose](calculate-fluid-dp-compose.md) e armazena.

O bloco remember garante que o valor só seja recalculado quando um parâmetro de configuração realmente muda.

Dynamically scaled Dp value.

Screen dimension qualifier: [DpQualifier.SMALL_WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-s-m-a-l-l_-w-i-d-t-h/index.md), [DpQualifier.HEIGHT](../com.appdimens.dynamic.common/-dp-qualifier/-h-e-i-g-h-t/index.md), or [DpQualifier.WIDTH](../com.appdimens.dynamic.common/-dp-qualifier/-w-i-d-t-h/index.md).

Orientation-based dimension swap rule (default: [Inverter.DEFAULT](../com.appdimens.dynamic.common/-inverter/-d-e-f-a-u-l-t/index.md)).

If `true`, returns the base value unscaled when the app is in split-screen.

If `true`, applies aspect-ratio multiplier for more aggressive scaling.

Override for the AR sensitivity constant (null = library default).
