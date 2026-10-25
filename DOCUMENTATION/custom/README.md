# Custom strategies: documentation

**Module:** `io.github.bodenberg:appdimens-dynamic-custom` (`:library-custom`) · packages `com.appdimens.dynamic.custom` (engine), `com.appdimens.dynamic.compose.custom` (Compose), `com.appdimens.dynamic.code.custom` (Views / Kotlin / Java)

The 13 built-in strategies (`sdp`, `power`, `auto`…) cover most apps. When yours needs its **own** growth law, this module lets you write it, in one line of Kotlin or as a full configuration that is shared through JSON with every AppDimens platform (Android, KMP, iOS, Flutter, React Native, Web, Games, .NET).

## Pick a form

| You want to… | Use | Read |
|---|---|---|
| Write the math yourself, in Kotlin, with autocomplete | `CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.8f) }` (factor) · `scalingFormula { base * pow(sw / 360f, 0.8f) }` (value) | [Quick start](quick-start.md) · [Code formulas](code-formulas.md) |
| Keep the formula in JSON or remote config | `scalingFormula("base * pow(sw / 360, 0.8)")` · `16.cdp("…")` | [Text formulas](text-formulas.md) |
| Different behavior per device, screen range or orientation, bounds, font-scale policy | `scalingStrategy("brand") { … }` | [Configuration](configuration.md) |
| Share one definition across apps and platforms | a strategies JSON file | [JSON format](json.md) |

## Guides

1. [Quick start](quick-start.md): install, the four forms and your first strategy in five minutes.
2. [Code formulas](code-formulas.md): `CustomScaleStrategy { … }` and `scalingFormula { … }`, with every name available inside the braces.
3. [Text formulas](text-formulas.md): the formula language (operators, functions, variables, errors, fast path).
4. [Configuration](configuration.md): reference frame, axes, curves, bounds, aspect ratio, font scale, multi-window, rules, `extend`, built-ins and presets.
5. [JSON format](json.md): the cross-platform document, loading, exporting and validation errors.
6. [API reference](api-reference.md): every Compose, Views and Java entry point, the registry and the debug helpers.
7. [Performance](performance.md): what each form costs and how caching works.
8. [Cookbook](cookbook.md): ready-to-use recipes (tablets, TV, watch, game HUD, readable text, A/B tests).
9. [Troubleshooting](troubleshooting.md): error messages, `explain()`, and frequent questions.

## How it works, in one picture

```
ratio  = axis(window) / axis(reference)                  // e.g. sw / 300
factor = clamp( curve(ratio) × multiplier × AR , min, max )   // once per window
value  = formula( clampValue( base × factor ) )           // per value, only if needed
```

`CustomScaleStrategy { … }` replaces the whole `factor` line. `scalingFormula { … }` replaces the whole `value` line. The full configuration lets you set each piece and change it per device with rules. The numbers are the same on every AppDimens platform: a shared 540-case fixture checks it.

[Back to the documentation index](../README.md) · [Overview page](../custom.md)
