# appdimens-dynamic-custom

Your own scaling strategies for AppDimens Dynamic: write the math in one line of Kotlin, or configure a full strategy that loads from JSON on every AppDimens platform with the same numbers.

```kotlin
implementation("io.github.bodenberg:appdimens-dynamic-custom")
```

```kotlin
val Product = CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }   // factor
val Hud     = scalingFormula { base * pow(sw / 360f, 0.8f) }                  // final value
Modifier.padding(16.cdp(Product))
Text("Score", fontSize = 18.csp(Hud))
```

| Package | Contents |
|---|---|
| `com.appdimens.dynamic.custom` | engine: `CustomScaleStrategy`, `scalingFormula`, `scalingStrategy` DSL, `ScalingStrategies` registry, JSON, presets, built-ins |
| `com.appdimens.dynamic.compose.custom` | Compose: `16.cdp(…)`, `14.csp(…)`, `cdpPx`, `cspPx`, `ProvideScalingStrategy`, `explainCurrent()` |
| `com.appdimens.dynamic.code.custom` | Views / Kotlin / Java (px): `16.cdp(context, …)`, `cdpDp`, `csp`, `cspUnits`, `DimenCustom`, `loadAsset` |

Documentation: [DOCUMENTATION/custom/](../DOCUMENTATION/custom/README.md) (quick start, code formulas, text formulas, configuration, JSON, API, performance, cookbook, troubleshooting).

Tests run on the JVM without a device: `./gradlew :library-custom:testDebugUnitTest`. They include the cross-platform fixture (`src/test/resources/custom-strategy-fixture.json`, 540 cases) that every AppDimens platform must reproduce.
