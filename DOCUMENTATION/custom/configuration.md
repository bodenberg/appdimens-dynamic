# Configuration

`scalingStrategy(id) { … }` builds a full strategy: the [code](code-formulas.md) and [text](text-formulas.md) one-liners are shortcuts for the simplest cases of it. Every field below also exists in the [JSON format](json.md), on every platform.

```kotlin
val Brand = scalingStrategy("brand") {            // creates AND registers "brand"
    reference(width = 360, height = 640)          // design frame (dp)
    axis = ScalingAxis.SMALLEST_WIDTH             // what to compare
    curve = power(0.8f)                            // how the ratio becomes a factor
    multiply(1.0f)
    clamp(min = 0.85f, max = 1.6f)                 // factor bounds
    clampValue(min = 10, max = 64)                 // dp bounds (value-dependent)
    aspectRatio(sensitivity = 0.004f)              // long/thin screen correction
    clampFontScale(1.3f)                           // csp policy
    ignoreMultiWindow = false
    on(DeviceClass.TABLET, DeviceClass.DESKTOP, name = "large") { curve = logarithmic(0.4f) }
}
```

Pass `register = false` to build a throwaway strategy.

## The formula

```
ratio  = axis(window) / axis(reference)
factor = clamp( curve(ratio) × multiplier × AR , minFactor, maxFactor )
value  = formula( clampValue( base × factor , minValue, maxValue ) )
```

## Fields

| Field | DSL | JSON | Default |
|---|---|---|---|
| Reference frame (dp) | `reference(width = 360, height = 640)` | `"reference": {"width": 360, "height": 640}` | 300 × 533 |
| Axis | `axis = ScalingAxis.HEIGHT` | `"axis": "height"` | `smallestWidth` |
| Curve | `curve = power(0.8f)` | `"curve": {"type": "power", "exponent": 0.8}` | `linear` |
| Multiplier | `multiply(0.9)` | `"multiplier": 0.9` | 1 |
| Factor bounds | `clamp(min = 0.85, max = 1.6)` | `"clamp": {"min": 0.85, "max": 1.6}` | none |
| Value bounds (dp, ≥ 0) | `clampValue(min = 10, max = 32)` | `"valueClamp": {"min": 10, "max": 32}` | none |
| Aspect ratio | `aspectRatio(sensitivity = 0.004f)` | `"aspectRatio": {"enabled": true, "sensitivity": 0.004}` | off, k = 0.08 / 30 |
| Font scale (`csp`) | `followFontScale()` · `ignoreFontScale()` · `clampFontScale(1.3f)` | `"fontScale": "follow"` · `"ignore"` · `{"max": 1.3}` | `follow` |
| Multi-window | `ignoreMultiWindow = true` | `"ignoreMultiWindow": true` | false |
| Per-value formula | `formula("max(scaled, base)")` | `"formula": "max(scaled, base)"` | none |
| Per-value code hook | `transform { base, scaled, screen -> … }` | (code only) | none |
| Rules | `on(…) { … }`, `onScreen(…) { … }`, `landscape { … }`… | `"rules": [{"when": {…}, "set": {…}}]` | none |
| Parent | `BuiltInStrategies.POWER.extend("x") { … }` | `"extends": "power"` | none |

## Axes

| Axis | DSL | JSON | Screen value | Reference value |
|---|---|---|---|---|
| Smallest width | `SMALLEST_WIDTH` | `smallestWidth` | `sw` | `min(refW, refH)` |
| Width · height | `WIDTH` · `HEIGHT` | `width` · `height` | window width · height | `refW` · `refH` |
| Shorter · longer side | `MIN` · `MAX` | `min` · `max` | shorter · longer side | `min` · `max` of the reference |
| Diagonal | `DIAGONAL` | `diagonal` | `√(w² + h²)` | `√(refW² + refH²)` |
| Perimeter | `PERIMETER` | `perimeter` | `w + h` | `refW + refH` |

The built-in `hdp` divides the height by **300**. A custom `height` axis divides by `reference.height` (533 by default), which is what a designer drawing on a full frame expects.

## Curves

| Curve | DSL | JSON | Factor |
|---|---|---|---|
| Linear | `linear()` | `"linear"` | `r` (= `sdp` with the default reference) |
| Power | `power(0.8f)` | `{"type": "power", "exponent": 0.8}` | `r^e` (built-in power uses 0.75) |
| Logarithmic | `logarithmic(0.4f)` | `{"type": "logarithmic", "k": 0.4}` | `1 + k·ln(r)` |
| Interpolated | `interpolated(0.5f)` | `{"type": "interpolated", "t": 0.5}` | `1 + (r − 1)·t` |
| Constant | `constant(1f)` | `{"type": "constant", "factor": 1}` | `f` (`constant(1f)` = plain dp) |
| Breakpoints | `breakpoints(360 to 1, 600 to 1.25)` | `{"type": "breakpoints", "points": [{"at": 360, "factor": 1}, …]}` | piecewise linear over the **absolute** axis value (dp), clamped to the first and last points |
| Text expression | `expression("pow(ratio, 0.8)")` | `{"type": "expression", "expression": "…"}` | your formula ([text formulas](text-formulas.md)) |
| Code | `custom { ratio, screen -> … }` | (code only) | your formula, pure |

## Bounds

- `clamp(min, max)` limits the **factor**. If rules leave `min > max`, the max wins. Declared configs are validated up front.
- `clampValue(min, max)` limits the **resulting dp**. The bounds apply to the magnitude, so a negative offset mirrors a positive one and 0 stays 0; they must be ≥ 0. Value bounds make the strategy value-dependent (cached per value).

## Aspect ratio

`aspectRatio()` multiplies the factor by `1 + k·ln(AR / 1.78)`, where `AR = longSide / shortSide` and the default `k` is the built-in 0.08 / 30. Raise `k` for a stronger correction on very long or very square screens.

## Font scale (`csp`)

| Policy | Effect on `14.csp(Brand)` |
|---|---|
| `followFontScale()` (default) | follows the user font scale, like `sp` |
| `ignoreFontScale()` | fixed size; the system font scale is cancelled |
| `clampFontScale(1.3f)` | follows it up to 1.3×, then stops |

## Multi-window

`ignoreMultiWindow = true` keeps values unscaled in split screen and freeform windows: the factor is 1, and formulas and transforms are skipped. Value bounds still apply. To react to split screen yourself instead, use a rule (`multiWindow { … }`) or read `multiWindow` in a formula.

## Rules

A rule changes some fields when its condition matches the window.

```kotlin
scalingStrategy("brand") {
    curve = power(0.8f)
    on(DeviceClass.TABLET, DeviceClass.DESKTOP, name = "large") { curve = logarithmic(0.4f) }
    onScreen(minSmallestWidth = 600f, orientation = Orientation.LANDSCAPE) { multiply(0.9f) }
    on(DeviceClass.WATCH) { axis = ScalingAxis.MIN; reference(192, 192); clamp(0.85f, 1.15f) }
    landscape { multiply(0.95f) }
    multiWindow { clamp(max = 1.2f) }
    largeScreens { clampFontScale(1.2f) }        // sw >= 600, any UI mode
}
```

| Condition | DSL | JSON `when` |
|---|---|---|
| Device class | `on(DeviceClass.TV)` | `"device": "tv"` or `["tablet", "desktop"]` |
| Orientation | `landscape { }` · `portrait { }` | `"orientation": "landscape"` |
| Smallest width / width / height (dp) | `onScreen(minSmallestWidth = 600f, maxWidth = 1200f)` | `"smallestWidth": {"min": 600}`, `"width": {"max": 1200}`, `"height": {…}` |
| Aspect ratio | `onScreen(minAspectRatio = 2f)` | `"aspectRatio": {"min": 2}` |
| Split screen | `multiWindow { }` | `"multiWindow": true` |

- A condition matches when **every** field set in it matches. Ranges are `min <= value < max`.
- All matching rules apply **in declaration order**. A later rule overrides the fields an earlier one set; fields a rule leaves out keep their current value.
- Applied rule names show in `explain()`. Rules without a name show as `#index`.
- Device classes: `PHONE`, `TABLET`, `DESKTOP`, `TV`, `WATCH`, `CAR`, `VR`. On Android they come from the UI mode, and `sw ≥ 600` makes a phone a tablet.

## Extending

```kotlin
val Soft    = BuiltInStrategies.POWER.extend("soft") { curve = power(0.6f) }
val MyTv    = CustomPresets.TV_SAFE.extend("myTv") { on(DeviceClass.TV) { clamp(max = 1.6f) } }
val Heights = Brand.withAxis(ScalingAxis.HEIGHT)   // "brand@height", cached per strategy
```

`extend` keeps the parent's fields and rules (the parent's rules first) and lets you change any of them.

### Built-ins as configs

`BuiltInStrategies.SCALED`, `POWER`, `LOGARITHMIC`, `INTERPOLATED`, `DIAGONAL`, `PERIMETER` and `FIXED`. Their factors match the built-in modules on the default smallest-width path (checked by `CustomParityWithCoreTest`), so they are a safe starting point.

### Presets (`CustomPresets`)

| Preset | Id | What it does |
|---|---|---|
| `TABLET_FRIENDLY` | `tabletFriendly` | Linear on phones; `power(0.5)` on tablets and desktop; factor ≤ 1.8 |
| `COMPACT_FIRST` | `compactFirst` | Reference 360×640, `interpolated(0.5)`, factor 0.9–1.4 |
| `TV_SAFE` | `tvSafe` | On TV: height axis vs 540 dp, factor 1–2 |
| `WEARABLE` | `wearable` | On watch: shorter side vs 192 dp, factor 0.85–1.15 |
| `GAME_HUD` | `gameHud` | Shorter side, `power(0.85)`, aspect ratio on, 0.75–2.5, fixed-size text |
| `READABLE_TEXT` | `readableText` | `logarithmic(0.4)`, font scale capped at 1.3 |

Presets are not registered by default, but `ScalingStrategies["tvSafe"]` and JSON `extends` find them by id.

[Back to the custom docs](README.md)
