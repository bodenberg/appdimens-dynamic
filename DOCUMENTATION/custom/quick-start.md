# Quick start

## 1. Install

```kotlin
dependencies {
    implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:<version>"))
    implementation("io.github.bodenberg:appdimens-dynamic-custom")
}
```

```kotlin
import com.appdimens.dynamic.custom.*            // strategies
import com.appdimens.dynamic.compose.custom.*    // Compose: 16.cdp(…), 14.csp(…)
import com.appdimens.dynamic.code.custom.*       // Views / Kotlin: 16.cdp(context, …) → px
```

## 2. Write the math (recommended)

Declare the strategy **once** (for example as a top-level `val`), then use it anywhere.

```kotlin
// Factor: the number that multiplies every value
val Product = CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }

// Formula: the final value (`base` = the value you pass, the 16 in 16.cdp)
val Hud = scalingFormula { base * pow(sw / 360f, 0.8f) }
```

Inside the braces the IDE autocompletes `width`, `sw`, `height`, `ratio`, `tablet`, `tv`, `landscape`, `pow`, `clamp`, `min`, `max`, `lerp`… and a typo is a compile error. Any other Kotlin works too (`if`, `when`, `coerceIn`, your own helpers). The full list is in [Code formulas](code-formulas.md).

## 3. Use it

```kotlin
// Compose
Modifier.padding(16.cdp(Product))                 // Dp
Text("Score", fontSize = 18.csp(Hud))             // TextUnit, follows the user font scale
val px = 24.cdpPx(Product)                        // Float px

// Views / Kotlin (px, like every code.* package)
view.setPadding(16.cdp(context, Product).toInt(), 0, 0, 0)
textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 14.csp(context, Hud))
```

```java
float px = DimenCustom.cdp(context, 16f, product);
```

## 4. Make it the default (optional)

```kotlin
ScalingStrategies.default = Product          // app-wide: 16.cdp now uses Product
ProvideScalingStrategy(Hud) { Text("Lives", fontSize = 14.csp) }   // one subtree
```

## 5. The same thing as text (for JSON and remote config)

```kotlin
val Remote = scalingFormula("base * pow(sw / 360, 0.8)")
Modifier.padding(16.cdp("base * (sw / 360)"))     // inline, shared per text
```

The text form uses the same names (flags are 1 or 0) and works in the strategies JSON (`{"id": "hud", "formula": "base * pow(sw / 360, 0.8)"}`). See [Text formulas](text-formulas.md).

## 6. Need more structure?

Rules per device class, factor bounds, a font-scale cap or a different reference frame go in the full configuration:

```kotlin
val Brand = scalingStrategy("brand") {
    reference(width = 360, height = 640)
    curve = power(0.8f)
    clamp(min = 0.85f, max = 1.6f)
    clampFontScale(1.3f)
    on(DeviceClass.TABLET, DeviceClass.DESKTOP) { curve = logarithmic(0.4f) }
}
```

See [Configuration](configuration.md) and [JSON format](json.md).

## 7. Check the numbers

```kotlin
Text(Product.explainCurrent())
// [scale#1] phone smallestWidth=411dp / ref 300 = 1.37 -> Custom = 1.1417 => factor 1.1417
```

[Back to the custom docs](README.md)
