# Troubleshooting

## See what a strategy does

```kotlin
Text(Brand.explainCurrent())              // Compose
Log.d("dimens", Brand.explain(context))   // Views
```

```
[brand] tablet smallestWidth=800dp / ref 360 = 2.2222 -> Logarithmic(k=0.4) = 1.3194 x 0.9 clamp[0.85, 1.6] => factor 1.1875 rules=[large, #1]
[hud] phone smallestWidth=411dp / ref 300 = 1.37 -> Linear = 1.37 => factor 1.37 then Formula(base * pow(sw / 360, 0.8))
```

The line reads left to right: device class, axis value and reference, the curve and its result, multiplier, aspect ratio, bounds, the final factor, the applied rules and, when there is one, the per-value formula or transform. `factor(context)` / `currentFactor()` return only the number.

## Common errors

| Message | Cause | Fix |
|---|---|---|
| `Invalid expression at 7: unknown variable 'sww' …` | typo in a text formula | use a name from the list in the message, or switch to a [code formula](code-formulas.md), which the compiler checks |
| `'clamp' takes 3 argument(s), got 2` | wrong number of arguments | see the [function table](text-formulas.md#functions) |
| `custom strategy produced a non-finite factor (NaN)` | division by zero, `ln` of 0, NaN from a lambda | guard the formula (`max(x, 1)`) or clamp the factor |
| `<path> must be an object` / `must be a boolean` | wrong JSON type | see the [field tables](json.md#strategy-fields) |
| `maxValue must be >= 0` | negative value bound | value bounds limit the magnitude; use a positive number |
| `minFactor must be <= maxFactor` | crossed bounds in the declaration | swap them (crossed bounds produced by rules at runtime let the max win) |
| `No scaling strategy 'x'. Registered: […]` | id not registered | register it, load the JSON first, or check the id |
| `ValueTransform lambdas cannot be encoded to JSON` | `toJson()` on a code lambda | use the text form for strategies you export |

## Frequent questions

**My lambda result never changes.** The factor is memoized per window. A lambda that reads mutable state (a setting, the time) is not re-run until the window changes. Use separate strategies and pick one, or make the setting part of the screen (a rule).

**`16.cdp(Product)` and `16.sdp` differ.** Expected: the default reference is 300 dp of smallest width; `ratio` is `sw / 300`. `base * ratio` (or `BuiltInStrategies.SCALED`) gives exactly `sdp`.

**My text formula is slow / misses the fast path.** Only `base * E` qualifies. `base * sw / 360` reads as `(base * sw) / 360`; write `base * (sw / 360)`.

**A strategy created inside a composable leaks cache ids.** `CustomScaleStrategy { … }` and `scalingFormula { … }` build a new strategy on each call. Declare them once outside the composable, or `remember` them. Text formulas inline (`16.cdp("…")`) are shared per text and are fine.

**Fonts ignore my strategy.** `csp` follows the user font scale by default. Use `ignoreFontScale()` for fixed text, or `clampFontScale(max)` to cap it.

**Are the numbers the same on iOS / Flutter / Web?** Yes for everything that can be written as JSON (curves, rules, text formulas): a shared 540-case fixture checks every platform. Code lambdas run your own code, so they match as long as the code does.

[Back to the custom docs](README.md)
