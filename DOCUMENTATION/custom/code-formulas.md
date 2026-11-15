# Code formulas

Two one-line entry points let you write the scaling math in plain Kotlin. Inside the braces you get a typed **scope** whose names match the [text language](text-formulas.md), so you only learn them once. The IDE autocompletes them, and a typo fails at compile time.

| | `CustomScaleStrategy { … }` | `scalingFormula { … }` |
|---|---|---|
| You return | the **factor** (every value is multiplied by it) | the **final value** |
| Scope | `ScreenScope` | `FormulaScope` (= `ScreenScope` + `base`) |
| Runs | once per window | once per value and window (cached) |
| Cost per value | one multiply | one cache lookup |
| JSON | no (use a text `expression` curve) | no (use a text `formula`) |

## `CustomScaleStrategy { … }`: programmable factor

```kotlin
val Product = CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }
val Living  = CustomScaleStrategy { if (tv) height / 540f else ratio }
val Steps   = CustomScaleStrategy {
    when {
        sw < 360f -> 0.9f
        sw < 600f -> sw / 400f
        else -> 1.5f + (sw - 600f) / 2000f
    }
}
```

- Signature: `CustomScaleStrategy(id: String? = null, register: Boolean = id != null, factor: ScreenScope.() -> Float): ScalingStrategy`.
- Without an `id` a unique `scale#N` id is generated and the strategy is not registered. With an id it is registered, so `ScalingStrategies["product"]` finds it.
- A non-finite factor (NaN, infinity) throws `IllegalArgumentException` when the window resolves.

## `scalingFormula { … }`: the final value

```kotlin
val Hud  = scalingFormula { base * pow(sw / 360f, 0.8f) }
val Card = scalingFormula { if (tablet) base * 1.2f else base * ratio }
val Snap = scalingFormula { round(base * sw / 360f) }          // whole dp only
val Min  = scalingFormula { max(base * ratio, base) }          // never smaller than the design value
```

- Signature: `scalingFormula(id: String? = null, register: Boolean = id != null, formula: FormulaScope.() -> Float): ScalingStrategy`.
- `base` is the value you pass: in `16.cdp(Hud)` it is `16f`. For `csp`, the result then follows the user font scale (see [font scale](configuration.md#font-scale-csp)).
- Without an `id`, a unique `formula#N` id is generated.

## Names inside the braces

| Name | Type | Meaning |
|---|---|---|
| `base` | `Float` | the value being scaled (`scalingFormula` only) |
| `sw` | `Float` | smallest width of the window (dp) |
| `width` · `height` | `Float` | window width · height (dp) |
| `shortSide` · `longSide` | `Float` | shorter · longer side (dp) |
| `diagonal` · `perimeter` | `Float` | `√(w² + h²)` · `w + h` (dp) |
| `aspectRatio` | `Float` | `longSide / shortSide` |
| `ratio` | `Float` | `sw / 300`, the ratio `sdp` uses (`base * ratio` is exactly `sdp`) |
| `density` · `fontScale` | `Float` | screen density · user font scale |
| `phone` `tablet` `desktop` `tv` `watch` `car` `vr` | `Boolean` | device class of the window |
| `landscape` · `portrait` · `multiWindow` | `Boolean` | orientation · split screen |
| `device` | `DeviceClass` | the device class itself (for `when`) |
| `screen` | `ScreenSnapshot` | the whole snapshot (`widthDp`, `heightDp`, `smallestWidthDp`, `isLandscape`…) |
| `pow(x, e)` `sqrt` `ln` `log10` `exp` `abs` | `Float` | math, no import needed |
| `round(x)` | `Float` | half up: `floor(x + 0.5)` |
| `min(a, b)` `max(a, b)` `clamp(x, lo, hi)` | `Float` | bounds |
| `lerp(a, b, t)` · `smoothstep(e0, e1, x)` | `Float` | blending |

The device class comes from the Android UI mode (TV, watch, car, VR, desk) and the smallest width (`sw ≥ 600` is a tablet).

## Rules for the lambda

- **Keep it pure.** It must not read mutable state, preferences or time: the factor is memoized per window, and values are cached per window. Use a different strategy (or a rule) for a different state.
- **Declare it once.** Every call to `CustomScaleStrategy { … }` or `scalingFormula { … }` creates a new strategy with its own cache id. Use a top-level `val`, an `object`, or `remember` in Compose, never a fresh lambda on every recomposition.
- **Watch out for shadowing.** A local variable named like a scope property wins over it. In `val tv = …; CustomScaleStrategy { if (tv) … }` the `tv` is your local, not the device flag.
- **Prefer the factor form when you can.** If your formula is `base × (something about the screen)`, write the something as a `CustomScaleStrategy`: it costs one multiply instead of a cache lookup.

## When to use something else

- The formula must live in JSON, a design-token file or remote config: [Text formulas](text-formulas.md).
- You need rules per device, bounds or a font-scale policy on top of a curve: [Configuration](configuration.md). The DSL also accepts code inside it: `curve = custom { ratio, screen -> … }` and `transform { base, scaled, screen -> … }`.

[Back to the custom docs](README.md)
