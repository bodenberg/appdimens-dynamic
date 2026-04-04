//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.power](index.md)/[calculatePowerDp](calculate-power-dp.md)

# calculatePowerDp

[jvm]
internal fun [calculatePowerDp](calculate-power-dp.md)(baseValue: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), qualifier: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md), inverter: [Inverter](../com.appdimens.dynamic.common/-inverter/index.md), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Shared pure-math scaling kernel used by [toDynamicPowerPx](to-dynamic-power-px.md) and [toDynamicPowerDp](to-dynamic-power-dp.md).

Algorithm summary:

1. 
   Applies [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) rules to swap the effective [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) based on screen orientation.
2. 
   If [ignoreMultiWindows](calculate-power-dp.md) is `true`, detects split-screen mode via layout flags; if active,     returns [baseValue](calculate-power-dp.md) unchanged so the UI does not over-scale inside a small window.
3. 
   For the common path (`SMALL_WIDTH` + `DEFAULT` inverter + no custom sensitivity),     delegates to [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) which reads pre-computed factors from     [DimenCache.ScreenFactors](../com.appdimens.dynamic.core/-dimen-cache/-screen-factors/index.md) — a single float multiply, zero extra allocations.
4. 
   For other qualifiers or a custom sensitivity constant, reads the screen dimension from     [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) and performs the scaling formula inline.

**Performance**: Simple paths without Aspect Ratio complete in ~2 ns (single multiply). Paths with Aspect Ratio require ~41 ns on Snapdragon 888 (includes ln() fallback). Results are memoized by the [DimenCache](../com.appdimens.dynamic.core/-dimen-cache/index.md) shared across code and compose packages.

**Note**: Both `code/` and `compose/` packages intentionally maintain separate copies of this function because the `code/` variant operates on [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) directly (no Compose runtime), while `compose/` reads it from androidx.compose.ui.platform.LocalConfiguration. The math is identical; only the Context acquisition path differs.

PT Núcleo de escalonamento puro compartilhado por [toDynamicPowerPx](to-dynamic-power-px.md) e [toDynamicPowerDp](to-dynamic-power-dp.md).

Resumo do algoritmo:

1. 
   Aplica as regras de [Inverter](../com.appdimens.dynamic.common/-inverter/index.md) para trocar o [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md) efetivo conforme a orientação.
2. 
   Se [ignoreMultiWindows](calculate-power-dp.md) for `true`, detecta split-screen via flags de layout;     se ativo, retorna [baseValue](calculate-power-dp.md) sem escalar.
3. 
   Para o caminho comum (SMALL_WIDTH + DEFAULT + sem sensibilidade customizada),     delega para [DimenCache.calculateRawScaling](../com.appdimens.dynamic.core/-dimen-cache/calculate-raw-scaling.md) com os fatores pré-calculados.
4. 
   Para outros qualificadores ou sensibilidade customizada, lê a dimensão da tela     da [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) e executa a fórmula de escalonamento inline.

**Nota**: Os pacotes `code/` e `compose/` mantêm cópias separadas intencionalmente. A versão `code/` opera sobre [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) diretamente, enquanto a versão `compose/` usa androidx.compose.ui.platform.LocalConfiguration. A matemática é idêntica; apenas a obtenção do contexto difere.

#### Return

Scaled Dp value as a raw [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html).

#### Parameters

jvm

| | |
|---|---|
| baseValue | Raw Dp value to scale (e.g. `16f` for 16 dp). |
| configuration | Current [android.content.res.Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) from the context. |
| qualifier | Original screen qualifier before inversion. |
| inverter | Orientation-swap rule. |
| ignoreMultiWindows | Whether to suppress scaling in multi-window mode. |
| applyAspectRatio | Whether to apply the AR multiplier. |
| customSensitivityK | Custom AR sensitivity constant, or `null` for the library default. |