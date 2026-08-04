# AppDimens Dynamic — library map (concise)

**Modules (3.1.6):** [DOCUMENTATION/MODULES.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/MODULES.md)

**Doc base (Git ref `3.1.6`):** [https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/)

This file supplements [SKILL.md](SKILL.md). Read it when you need **package locations**, **symmetry between Compose and `code`**, or **core types**.

## Module layout

Paths at Git ref **`3.1.6`**. Full Gradle/Maven matrix: [MODULES.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/MODULES.md) · [library-map.md](library-map.md).

- **`com.appdimens.dynamic.common`** — `DpQualifier`, `Inverter`, `Orientation`, `UiModeType`, `UnitType`, `DpQualifierEntry`.
- **`com.appdimens.dynamic.core`** — `DimenCache`, `StrategyFactorRegistry` / `SharedScreenMetrics`, `MissingModule`, `DimenCalculationPlumbing`, `DesignScaleConstants`, `AspectRatioLookup`, `PercentSpaceMath` / `ResizeMath` / `ResizeBound`, Compose (`AppDimensProvider`, stamps, `rememberDimen*`).
- **`com.appdimens.dynamic.compose.<strategy>`** / **`code.<strategy>`** — sources under `library/` (scaled + plain) or `library-<strategy>/`.
- **`compose.resize` / `code.resize`** — constraint resize (not `calculateRawScaling`).

Build hint for missing satellites: `gradle/appdimens-missing-module-check.gradle.kts` + `MissingModule`.

## Strategy → documentation file

| Folder suffix | Doc |
|---------------|-----|
| scaled | [DOCUMENTATION/scaled.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/scaled.md) |
| percent | [DOCUMENTATION/percent.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/percent.md) |
| power | [DOCUMENTATION/power.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/power.md) |
| fluid | [DOCUMENTATION/fluid.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/fluid.md) |
| auto | [DOCUMENTATION/auto.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/auto.md) |
| diagonal | [DOCUMENTATION/diagonal.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/diagonal.md) |
| fill | [DOCUMENTATION/fill.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/fill.md) |
| fit | [DOCUMENTATION/fit.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/fit.md) |
| interpolated | [DOCUMENTATION/interpolated.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/interpolated.md) |
| logarithmic | [DOCUMENTATION/logarithmic.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/logarithmic.md) |
| perimeter | [DOCUMENTATION/perimeter.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/perimeter.md) |
| density | [DOCUMENTATION/density.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/density.md) |
| resize | [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/resize.md) |
| units (physical) | [DOCUMENTATION/physical-units.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/physical-units.md) |

**Product / formal math (English):** [PRD.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/PRD.md) · [PDR.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/PDR.md) · [MATHEMATICS-AND-CALCULUS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)

## Example application module (`app`)

Upstream sample module only (not shipped inside `io.github.bodenberg:appdimens-dynamic`); use for pattern reference:

- **Compose** — [`ExampleActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/compose/ExampleActivity.kt), [`BenchmarkActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/compose/BenchmarkActivity.kt), [`compose/benchmark/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.6/app/src/main/java/com/example/app/compose/benchmark), [`DemoCalcRouting.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/compose/DemoCalcRouting.kt).
- **Kotlin Views** — [`ExampleActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/kotlin/ExampleActivity.kt).
- **Java Views** — [`ExampleActivity.java`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/java/ExampleActivity.java) (Data Binding sample, `DimenSdp`, `DimenSsp`, `DimenScaled`, `DimenResize`, physical units).
- **Application** — [`InitializeApplication.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/app/src/main/java/com/example/app/InitializeApplication.kt) (minimal; cache init patterns are documented in [README](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/README.md) / [library/PERFORMANCE.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/library/PERFORMANCE.md)).

## Internal `DimenCache.CalcType` (debug / cache tagging)

Maps to package families: AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC, PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY — see [`DimenCache.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.6/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt). End users think in **strategy names** and imports, not this enum.

## What this reference intentionally skips

Per the skill: **do not** expand on **`ignoreMultiWindows`** or **`*i` / `*ia`** suffix workflows when guiding users — those are omitted from the interactive workflow.
