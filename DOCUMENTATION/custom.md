# Custom strategies (`custom` / `compose.custom` / `code.custom`)

**Artifact:** `io.github.bodenberg:appdimens-dynamic-custom` (`:library-custom`)
See [MODULES.md](MODULES.md) · [README installation](../README.md#installation-v320) · [Mathematics §Custom](MATHEMATICS-AND-CALCULUS.md#43-custom-strategies-appdimens-dynamic-custom) · **[Full custom-strategy documentation](custom/README.md)**

The 13 built-in strategies cover most products. When an app (or a design system shared by several apps) needs its **own** growth law, this module lets you write it. The simplest form is one line of typed Kotlin; the full form is a configuration that loads from JSON on every AppDimens platform (Android, KMP, iOS, Flutter, React Native, Web, Games, .NET) with the same numbers.

```kotlin
import com.appdimens.dynamic.custom.*
import com.appdimens.dynamic.compose.custom.*

// Factor: the number that multiplies every value
val Product = CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }
// Formula: the final value (base = the 16 in 16.cdp)
val Hud = scalingFormula { base * pow(sw / 360f, 0.8f) }
// Text, for JSON and remote config
val Remote = scalingFormula("base * pow(sw / 360, 0.8)")
// Full configuration with rules
val Brand = scalingStrategy("brand") {
    reference(width = 360, height = 640)
    curve = power(0.8f)
    clamp(min = 0.85f, max = 1.6f)
    on(DeviceClass.TABLET, DeviceClass.DESKTOP) { curve = logarithmic(0.4f) }
}

Modifier.padding(16.cdp(Product))        // Compose → Dp
Text("Score", fontSize = 18.csp(Hud))    // TextUnit
view.setPadding(16.cdp(context, Brand).toInt(), 0, 0, 0)   // Views → px
```

## Documentation

| Guide | What it covers |
|---|---|
| [Overview](custom/README.md) | which form to pick, how the pieces fit |
| [Quick start](custom/quick-start.md) | install and first strategy in five minutes |
| [Code formulas](custom/code-formulas.md) | `CustomScaleStrategy { … }`, `scalingFormula { … }` and every name inside the braces |
| [Text formulas](custom/text-formulas.md) | the formula language: operators, functions, variables, errors, fast path |
| [Configuration](custom/configuration.md) | reference, axes, curves, bounds, aspect ratio, font scale, multi-window, rules, `extend`, built-ins, presets |
| [JSON format](custom/json.md) | the cross-platform document, loading, exporting, validation |
| [API reference](custom/api-reference.md) | Compose, Views, Java, registry, debug helpers |
| [Performance](custom/performance.md) | what each form costs, caching |
| [Cookbook](custom/cookbook.md) | recipes: tablets, TV, watch, game HUD, readable text, A/B tests |
| [Troubleshooting](custom/troubleshooting.md) | error messages, `explain()`, FAQ |

## When to use it

- A design system shared by several apps that needs one growth law everywhere.
- A product whose UI must behave differently per device class: TV, watch, car, tablet.
- Remote-config or A/B experiments on scaling, by shipping a new JSON.
- Built-ins that are close but need a different reference frame, exponent or bounds.

## Trade-offs

- A strategy defined at runtime cannot be checked by `checkAppDimensModules`. Keep your strategies in one place (a `Dimens.kt` file or the JSON document).
- Code lambdas cannot be exported to JSON; their text equivalents can.
- The aspect-ratio correction always uses the satellite law (`× (1 + k·ln(AR/1.78))`).

[Back to index](README.md)
