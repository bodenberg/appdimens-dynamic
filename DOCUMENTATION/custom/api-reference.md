# API reference

## Building strategies (`com.appdimens.dynamic.custom`)

| Function | Returns | Notes |
|---|---|---|
| `CustomScaleStrategy(id?, register?) { factor }` | `ScalingStrategy` | programmable factor, `ScreenScope` receiver ([code formulas](code-formulas.md)) |
| `scalingFormula(id?, register?) { value }` | `ScalingStrategy` | code formula, `FormulaScope` receiver |
| `scalingFormula("text", id?, register?)` | `ScalingStrategy` | text formula, shared per text without an id ([text formulas](text-formulas.md)) |
| `scalingStrategy(id, register = true) { … }` | `ScalingStrategy` | full configuration ([configuration](configuration.md)) |
| `ScalingStrategy.extend(id, register = true) { … }` | `ScalingStrategy` | child of an existing strategy |
| `ScalingStrategy.withAxis(axis)` | `ScalingStrategy` | same strategy on another axis, id `"<id>@<axis>"`, cached per strategy |
| `ScalingConfig.toStrategy(id, register = true)` | `ScalingStrategy` | wrap a config built by hand |
| `BuiltInStrategies.SCALED` … `FIXED` · `CustomPresets.TV_SAFE` … | `ScalingStrategy` | ready-made parents |

## Registry: `ScalingStrategies`

| Member | Notes |
|---|---|
| `default` | strategy used by `16.cdp` / `14.csp` without an argument; initially `BuiltInStrategies.SCALED` |
| `register(strategy)` · `unregister(id)` | add or replace · remove |
| `get(id)` / `ScalingStrategies["id"]` · `getOrThrow(id)` | lookup, including built-ins and presets |
| `ids` | ids registered by the app |
| `formula("text")` | shared text-formula strategy (Java friendly) |
| `loadJson(json)` · `loadAsset(context, path)` | register a [JSON document](json.md) and apply its `default`; atomic |

## Compose (`com.appdimens.dynamic.compose.custom`)

| Call | Returns |
|---|---|
| `16.cdp(strategy)` · `16.cdp` · `16.cdp("text")` | `Dp` |
| `16.cdpPx(strategy)` · `16.cdpPx` · `16.cdpPx("text")` | `Float` px |
| `14.csp(strategy)` · `14.csp` · `14.csp("text")` | `TextUnit` (font-scale policy applied) |
| `14.cspPx(strategy)` · `14.cspPx` · `14.cspPx("text")` | `Float` px |
| `strategy.dp(16)` · `strategy.sp(14)` · `strategy.px(16)` | strategy-first forms |
| `16.dp.scaledBy(strategy)` | scale an existing `Dp` |
| `ProvideScalingStrategy(strategy) { … }` · `LocalScalingStrategy` · `currentScalingStrategy` | scoped default |
| `strategy.currentFactor()` · `strategy.explainCurrent()` | debug |

Without an explicit strategy, `16.cdp` uses the nearest `ProvideScalingStrategy`, then `ScalingStrategies.default`.

## Views / Kotlin (`com.appdimens.dynamic.code.custom`)

Short names return **px**, like every `code.*` package.

| Call | Returns |
|---|---|
| `16.cdp(context, strategy = default)` · `16.cdp(context, "text")` | px |
| `16.cdpDp(context, …)` | dp |
| `14.csp(context, …)` | px for text (`setTextSize(COMPLEX_UNIT_PX, …)`) |
| `14.cspUnits(context, …)` | sp units (`setTextSize(COMPLEX_UNIT_SP, …)`; the policy is pre-compensated) |
| `strategy.px(context, 16)` · `.dp(context, 16)` · `.spPx(context, 14)` | strategy-first forms |
| `strategy.factor(context)` · `strategy.explain(context)` | debug |
| `context.screenSnapshot()` | the `ScreenSnapshot` strategies see |

## Java: `DimenCustom`

```java
float px  = DimenCustom.cdp(context, 16f, DimenCustom.strategy("brand"));
float dp  = DimenCustom.cdpDp(context, 16f, DimenCustom.formula("base * (sw / 360)"));
float tx  = DimenCustom.csp(context, 14f);                    // default strategy
DimenCustom.loadAsset(context, "appdimens-strategies.json");
```

`cdp`, `cdpDp`, `csp`, `cspUnits`, `strategy(id)`, `formula(text)` and `loadAsset(context, path)`.

## Strategy members

| Member | Notes |
|---|---|
| `id` · `config` · `isValueDependent` | identity · full config · whether results depend on the value |
| `resolve(screen)` → `ResolvedScaling` | memoized per window: `factor`, `appliedRules`, `scale(base)`, `scaleSpDp(base)`, `scaleSpUnits(base)`, `explain(id)` |
| `factor(screen)` · `scale(base, screen)` · `explain(screen)` | platform-free helpers (tests, previews) |
| `toJson()` | export (throws for code lambdas) |

`ScreenSnapshot(widthDp, heightDp, smallestWidthDp, density, fontScale, isLandscape, isMultiWindow, device)` builds a window by hand, for tests and previews.

[Back to the custom docs](README.md)
