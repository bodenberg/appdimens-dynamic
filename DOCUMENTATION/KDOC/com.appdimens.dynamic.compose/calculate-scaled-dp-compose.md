//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose](index.md)/[calculateScaledDpCompose](calculate-scaled-dp-compose.md)

# calculateScaledDpCompose

internal fun [calculateScaledDpCompose](calculate-scaled-dp-compose.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?, context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Shared pure-math scaling kernel used by [toDynamicScaledDp](to-dynamic-scaled-dp.md) and [toDynamicScaledPx](to-dynamic-scaled-px.md).

Algorithm summary:

Applies [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) rules to swap the effective [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) based on orientation.

If [ignoreMultiWindows](calculate-scaled-dp-compose.md) is `true`, detects split-screen via layout flags; if active, returns [baseValue](calculate-scaled-dp-compose.md) unchanged so the UI does not over-scale in a small window.

For the common path (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity), delegates to [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) which reads the pre-computed factors from [DimenCache.ScreenFactors](../com.appdimens.dynamic.core/-dimen-cache/-screen-factors/index.md) — one float multiply, zero extra allocations.

For other qualifiers or custom sensitivity, reads the screen dimension from [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) and performs the scaling formula inline.

**Performance**: Simple paths without Aspect Ratio complete in ~2 ns (single multiply). Paths with Aspect Ratio require ~41 ns on Snapdragon 888 (includes ln() fallback). Results are memoized by the surrounding remember block and [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md).

PT Núcleo de escalonamento puro compartilhado por [toDynamicScaledDp](to-dynamic-scaled-dp.md) e [toDynamicScaledPx](to-dynamic-scaled-px.md).

Resumo do algoritmo:

Aplica as regras de [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) para trocar o [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) efetivo conforme a orientação.

Se [ignoreMultiWindows](calculate-scaled-dp-compose.md) for `true`, detecta split-screen via flags de layout; se ativo, retorna [baseValue](calculate-scaled-dp-compose.md) sem escalar.

Para o caminho comum (SMALL_WIDTH + DEFAULT + sem sensibilidade customizada), delega para [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) com os fatores pré-calculados.

Para outros qualificadores ou sensibilidade customizada, lê a dimensão da tela do [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) e executa a fórmula de escalonamento inline.

Scaled Dp value as a raw [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) (caller converts to Dp or pixels).

Raw Dp value to scale (e.g. `16f` for 16 dp).

Current [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) snapshot from LocalConfiguration.current.

Original screen qualifier before inversion.

Orientation-swap rule.

Whether to suppress scaling in multi-window mode.

Whether to apply the AR multiplier.

Custom AR sensitivity constant, or `null` for the library default.
