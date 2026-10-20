# Custom strategies (`custom` / `compose.custom` / `code.custom`)

**Artifact:** `io.github.bodenberg:appdimens-dynamic-custom:3.2.0` (`:library-custom`)
See [MODULES.md](MODULES.md) · [README installation](../README.md#installation-v320) · [Mathematics §Custom](MATHEMATICS-AND-CALCULUS.md#43-custom-strategies-appdimens-dynamic-custom)

The 13 built-in strategies cover most products. When an app (or a design system shared by several apps) needs its **own** growth law, use this module to define your own strategy instead of forking a built-in one. The configuration is identical on every AppDimens platform (Android, KMP, iOS, Flutter, React Native, Web, Games, .NET) and can be loaded from JSON.

## What it is

A custom strategy is an immutable, named configuration that turns the window into one **factor**:

```
ratio  = axis(window) / axis(reference)
factor = clamp( curve(ratio) × multiplier × AR , minFactor, maxFactor )
dp     = transform( clampValue( base × factor , minValue, maxValue ) )
```

Rules can override any field per **device class** (phone, tablet, desktop, TV, watch, car, VR), **screen range** (smallest width, width, height, aspect ratio), **orientation** and **multi-window**.

## Configuration fields

| Field | DSL | JSON | Default |
|---|---|---|---|
| Reference frame (dp) | `reference(width = 360, height = 640)` | `"reference": {"width": 360, "height": 640}` | 300 × 533 |
| Axis | `axis = ScalingAxis.HEIGHT` | `"axis": "height"` | `smallestWidth` |
| Curve | `curve = power(0.8f)` | `"curve": {"type": "power", "exponent": 0.8}` | `linear` |
| Multiplier | `multiply(0.9)` | `"multiplier": 0.9` | 1 |
| Factor bounds | `clamp(min = 0.85, max = 1.6)` | `"clamp": {"min": 0.85, "max": 1.6}` | none |
| Value bounds (dp) | `clampValue(min = 10, max = 32)` | `"valueClamp": {"min": 10, "max": 32}` | none |
| Aspect ratio | `aspectRatio(sensitivity = 0.004f)` | `"aspectRatio": {"enabled": true, "sensitivity": 0.004}` | off, k = 0.08 / 30 |
| Font scale (`csp`) | `followFontScale()` · `ignoreFontScale()` · `clampFontScale(1.3f)` | `"fontScale": "follow"` · `"ignore"` · `{"max": 1.3}` | `follow` |
| Multi-window | `ignoreMultiWindow = true` | `"ignoreMultiWindow": true` | false |
| Per-value hook | `transform { base, scaled, screen -> … }` | — (code only) | none |
| Rules | `on(…) { … }`, `onScreen(…) { … }`, `landscape { … }` … | `"rules": [{"when": {…}, "set": {…}}]` | none |
| Parent | `BuiltInStrategies.POWER.extend("x") { … }` | `"extends": "power"` | — |

### Axes

| Axis | Screen value | Reference value |
|---|---|---|
| `smallestWidth` | `sw` | `min(refW, refH)` |
| `width` / `height` | window width / height | `refW` / `refH` |
| `min` / `max` | shorter / longer side | `min` / `max` of the reference |
| `diagonal` | `√(w² + h²)` | `√(refW² + refH²)` |
| `perimeter` | `w + h` | `refW + refH` |

> The built-in `hdp` divides the height by **300**, not by the reference height. A custom `height` axis divides by `reference.height` (533 by default), which is what a designer drawing on a full frame expects.

### Curves

| Curve | Factor | Notes |
|---|---|---|
| `linear()` | `r` | Same as `sdp` with the default reference |
| `power(e)` | `r^e` | Built-in power uses 0.75 |
| `logarithmic(k)` | `1 + k·ln(r)` | Built-in logarithmic uses 0.4 |
| `interpolated(t)` | `1 + (r − 1)·t` | Built-in interpolated uses 0.5 |
| `constant(f)` | `f` | `constant(1f)` = plain dp |
| `breakpoints(360 to 1, 600 to 1.25)` | piecewise linear over the **absolute** axis value (dp) | clamped to the first and last points |
| `custom { ratio, screen -> … }` | your formula | must be **pure**; not JSON-serializable |

### Rules

- A condition matches when **every** field set in it matches. Ranges are `min <= value < max`.
- All matching rules apply **in declaration order**. A later rule overrides the fields an earlier one set, and fields a rule leaves out keep their current value.
- Applied rule names appear in `explain()` (rules without a name show as `#index`).
- If combined rules leave crossed bounds (`min > max`), the **max** bound wins. Declared configs are validated up front.

## How to use

### 1. Define

```kotlin
import com.appdimens.dynamic.custom.*

val Brand = scalingStrategy("brand") {
    reference(width = 360, height = 640)       // design frame
    curve = power(0.8f)                          // sublinear growth
    clamp(min = 0.85f, max = 1.6f)
    clampFontScale(1.3f)                         // csp follows the user font scale up to 1.3×

    on(DeviceClass.TABLET, DeviceClass.DESKTOP, name = "large") { curve = logarithmic(0.4f) }
    onScreen(minSmallestWidth = 600f, orientation = Orientation.LANDSCAPE) { multiply(0.9f) }
    on(DeviceClass.WATCH) { axis = ScalingAxis.MIN; reference(192, 192); clamp(0.85f, 1.15f) }
}
```

`scalingStrategy` also **registers** the strategy, so `ScalingStrategies["brand"]` finds it. Pass `register = false` for throwaway instances.

### 2. Use in Compose

```kotlin
import com.appdimens.dynamic.compose.custom.*

Modifier.padding(16.cdp(Brand))         // Dp
Text("Title", fontSize = 20.csp(Brand)) // TextUnit (font-scale policy applied)
val px = 24.cdpPx(Brand)                 // Float px
Brand.dp(16); Brand.sp(14); Brand.px(8)  // strategy-first form
16.dp.scaledBy(Brand)                    // scale an existing Dp
```

**Scope or global default.** Without an explicit strategy, `16.cdp`, `14.csp`, `cdpPx` and `cspPx` use, in this order, the nearest `ProvideScalingStrategy`, then `ScalingStrategies.default` (initially `BuiltInStrategies.SCALED`, which gives the same numbers as `sdp`).

```kotlin
ScalingStrategies.default = Brand          // app-wide (e.g. in Application.onCreate)

ProvideScalingStrategy(CustomPresets.TV_SAFE) {
    Text("Living room", fontSize = 18.csp)  // uses tvSafe
}
```

### 3. Use in Views / Kotlin / Java (`code.custom`, returns **px**)

```kotlin
import com.appdimens.dynamic.code.custom.*

view.setPadding(16.cdp(context, Brand).toInt(), 0, 0, 0)       // px
val dp = 16.cdpDp(context, Brand)                                // dp
textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14.csp(context, Brand))
textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.cspUnits(context, Brand))
```

```java
float px = DimenCustom.cdp(context, 16f, DimenCustom.strategy("brand"));
```

### 4. Extend a built-in or a preset

```kotlin
val Soft = BuiltInStrategies.POWER.extend("soft") { curve = power(0.6f) }
val MyTv = CustomPresets.TV_SAFE.extend("myTv") { on(DeviceClass.TV) { clamp(max = 1.6f) } }
val Heights = Brand.withAxis(ScalingAxis.HEIGHT)   // "brand@height", cached
```

Built-ins as configs: `SCALED`, `POWER`, `LOGARITHMIC`, `INTERPOLATED`, `DIAGONAL`, `PERIMETER`, `FIXED`. Their factors match the built-in modules on the default smallest-width path. This is covered by `CustomParityWithCoreTest`.

### 5. Load from JSON (design tokens, remote config, A/B tests)

```kotlin
ScalingStrategies.loadAsset(context, "appdimens-strategies.json") // registers + applies "default"
val brand = ScalingStrategies.getOrThrow("brand")
val json  = brand.toJson()                                        // export
```

```json
{
  "$schema": "https://github.com/bodenberg/appdimens-skills/schemas/custom-strategy.schema.json",
  "version": 1,
  "default": "brand",
  "strategies": [
    {
      "id": "brand",
      "reference": { "width": 360, "height": 640 },
      "curve": { "type": "power", "exponent": 0.8 },
      "clamp": { "min": 0.85, "max": 1.6 },
      "fontScale": { "max": 1.3 },
      "rules": [
        { "name": "large", "when": { "device": ["tablet", "desktop"] },
          "set": { "curve": { "type": "logarithmic", "k": 0.4 } } }
      ]
    },
    { "id": "brandText", "extends": "brand", "valueClamp": { "min": 10, "max": 32 } }
  ]
}
```

`extends` may name another strategy of the same document, a registered strategy, a built-in (`scaled`, `power`, `logarithmic`, `interpolated`, `diagonal`, `perimeter`, `fixed`) or a preset. The parent's rules come first. Cycles, duplicate ids, unknown curve types and malformed values fail with a path in the message (e.g. `brand.rules[0].set.curve.type`). The same file works on every AppDimens platform. Schema: [`custom-strategy.schema.json`](https://github.com/bodenberg/appdimens-skills/blob/main/schemas/custom-strategy.schema.json).

### 6. Debug

```kotlin
Text(Brand.explainCurrent())   // Compose
Log.d("dimens", Brand.explain(context))
// [brand] tablet smallestWidth=800dp / ref 360 = 2.2222 -> Logarithmic(k=0.4) = 1.3194 x 0.9 clamp[0.85, 1.6] => factor 1.1875 rules=[large, #1]
```

`factor(context)` / `currentFactor()` return only the number. `Context.screenSnapshot()` gives the platform-free `ScreenSnapshot` the strategy sees.

## Presets (`CustomPresets`)

| Preset | Id | What it does |
|---|---|---|
| `TABLET_FRIENDLY` | `tabletFriendly` | Linear on phones; `power(0.5)` on tablets/desktop; factor ≤ 1.8 |
| `COMPACT_FIRST` | `compactFirst` | Reference 360×640, `interpolated(0.5)`, factor 0.9–1.4 |
| `TV_SAFE` | `tvSafe` | On TV: height axis vs 540 dp, factor 1–2 |
| `WEARABLE` | `wearable` | On watch: shorter side vs 192 dp, factor 0.85–1.15 |
| `GAME_HUD` | `gameHud` | Shorter side, `power(0.85)`, aspect ratio on, 0.75–2.5, fixed-size text |
| `READABLE_TEXT` | `readableText` | `logarithmic(0.4)`, font scale capped at 1.3 |

Presets are not registered by default, but `ScalingStrategies["tvSafe"]` and JSON `extends` find them by id.

## Performance and caching

- The factor is computed **once per window snapshot and strategy**, then memoized on the strategy. This is the same idea as `DimenMetrics.powerScale`.
- **Factor-only strategies** (no value bounds, no `transform`) take the bypass lane of `DimenCache`: each call is one multiply, with no table lookup.
- **Value-dependent strategies** are stored in the per-window `DimenCache` partition under `CalcType.CUSTOM`. The strategy id sits in the 16 sensitivity bits of the key, so two strategies never alias. Bit 2 marks cacheability (`DimenCache.buildCustomKey`).
- Up to 65 536 strategy instances per process get a cache id. Beyond that, strategies still work but always compute.
- `custom { }` curves and `transform { }` hooks run only on a memo miss (factor) or a cache miss (value). Keep them **pure**: no I/O, no mutable state.

## When to use it

- A design system shared by several apps that needs one growth law everywhere.
- A product whose UI must behave differently per device class: TV, watch, car, tablet.
- Remote-config or A/B experiments on scaling, by shipping a new JSON.
- Built-ins that are close but need a different reference frame, exponent or bounds.

## Advantages and trade-offs

- **Pros:**
  - One config for all platforms.
  - Declarative rules.
  - JSON round-trip.
  - Explainable results.
  - Same cost as the built-ins.
- **Cons:**
  - A strategy defined at runtime cannot be checked by `checkAppDimensModules`. Name your strategies and keep them in one place (e.g. `Dimens.kt` or the JSON file).
  - The aspect-ratio correction always uses the satellite law (`× (1 + k·ln(AR/1.78))`), never the scaled linear law.

## Recommended usage strategy

1. **Keep a single source.** Define the strategies in one file, either a JSON asset or `object AppStrategies`, and set `ScalingStrategies.default` once.
2. **Start from a parent.** Base each strategy on a built-in or preset with `extend` / `"extends"`.
3. **Check the factor before shipping.** Use `explainCurrent()` on a phone, a tablet and the smallest supported device.
4. **Use value bounds sparingly.** Use `clampValue` only where an absolute size limit is a real requirement (e.g. tap targets ≥ 48 dp). It moves the strategy to the cached lane.

[Back to index](README.md)
