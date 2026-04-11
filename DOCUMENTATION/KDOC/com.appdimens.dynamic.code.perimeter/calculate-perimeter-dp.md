//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.perimeter](index.md)/[calculatePerimeterDp](calculate-perimeter-dp.md)

# calculatePerimeterDp

internal fun [calculatePerimeterDp](calculate-perimeter-dp.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?, context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Shared pure-math scaling kernel used by [toDynamicPerimeterPx](to-dynamic-perimeter-px.md) and [toDynamicPerimeterDp](to-dynamic-perimeter-dp.md).

Algorithm summary:

Applies [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) rules to swap the effective [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) based on screen orientation.

If [ignoreMultiWindows](calculate-perimeter-dp.md) is `true`, detects split-screen mode via layout flags; if active, returns [baseValue](calculate-perimeter-dp.md) unchanged so the UI does not over-scale inside a small window.

For the common path (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity), delegates to [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) which reads pre-computed factors from [DimenCache.ScreenFactors](../com.appdimens.dynamic.core/-dimen-cache/-screen-factors/index.md) — a single float multiply, zero extra allocations.

For other qualifiers or a custom sensitivity constant, reads the screen dimension from [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) and performs the scaling formula inline.

**Performance**: Simple paths without Aspect Ratio complete in ~2 ns (single multiply). Paths with Aspect Ratio require ~41 ns on Snapdragon 888 (includes ln() fallback). Results are memoized by the [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) shared across code and compose packages.

**Note**: Both `code/` and `compose/` packages intentionally maintain separate copies of this function because the `code/` variant operates on [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) directly (no Compose runtime), while `compose/` reads it from androidx.compose.ui.platform.LocalConfiguration. The math is identical; only the Context acquisition path differs.

PT Núcleo de escalonamento puro compartilhado por [toDynamicPerimeterPx](to-dynamic-perimeter-px.md) e [toDynamicPerimeterDp](to-dynamic-perimeter-dp.md).

Resumo do algoritmo:

Aplica as regras de [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) para trocar o [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) efetivo conforme a orientação.

Se [ignoreMultiWindows](calculate-perimeter-dp.md) for `true`, detecta split-screen via flags de layout; se ativo, retorna [baseValue](calculate-perimeter-dp.md) sem escalar.

Para o caminho comum (SMALL_WIDTH + DEFAULT + sem sensibilidade customizada), delega para [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) com os fatores pré-calculados.

Para outros qualificadores ou sensibilidade customizada, lê a dimensão da tela da [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) e executa a fórmula de escalonamento inline.

**Nota**: Os pacotes `code/` e `compose/` mantêm cópias separadas intencionalmente. A versão `code/` opera sobre [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) diretamente, enquanto a versão `compose/` usa androidx.compose.ui.platform.LocalConfiguration. A matemática é idêntica; apenas a obtenção do contexto difere.

Scaled Dp value as a raw [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).

Raw Dp value to scale (e.g. `16f` for 16 dp).

Current [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) from the context.

Original screen qualifier before inversion.

Orientation-swap rule.

Whether to suppress scaling in multi-window mode.

Whether to apply the AR multiplier.

Custom AR sensitivity constant, or `null` for the library default.
