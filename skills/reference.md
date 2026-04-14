# AppDimens Dynamic — library map (concise)

**Doc base (Git ref `3.1.4`):** [https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/)

This file supplements [SKILL.md](SKILL.md). Read it when you need **package locations**, **symmetry between Compose and `code`**, or **core types**.

## Module layout (`library`)

Paths below are as in the upstream repo at **`3.1.4`**; the consumer app does not contain this tree unless they clone the monorepo.

- **`com.appdimens.dynamic.common`** — shared enums/value types: `DpQualifier` (SMALL_WIDTH, HEIGHT, WIDTH), `Inverter`, `Orientation`, `UiModeType`, `UnitType`, `DpQualifierEntry`.
- **`com.appdimens.dynamic.core`** — cross-cutting engine: `DimenCache` (sharded cache, init, invalidation, internal `CalcType` per strategy family), `DimenCalculationPlumbing` (qualifier resolution, screen dp reads, aspect-ratio multiplier helper, and internal paths tied to **caller-supplied flags** the skill workflow does **not** surface to end users), `DesignScaleConstants`, `AspectRatioLookup`, percent/resize math (`PercentSpaceMath`, `ResizeMath`, `ResizeBound`, `AutoResizePercentBasis`), Compose integration (`CompositionLocals` / `AppDimensProvider`, `LocalUiModeType`, remember-stamp helpers in `ComposeRememberStamps`, `ComposeDimenRemember`).
- **`com.appdimens.dynamic.compose.<strategy>`** — one folder per **scaling strategy** for Compose UI (e.g. `compose/scaled/DimenSdp.kt`, `DimenSdpExtensions.kt`, `DimenScaled.kt`, Sp mirrors).
- **`com.appdimens.dynamic.code.<strategy>`** — mirror of the same **strategy** for Views / non-Compose Kotlin (and Java callers): `DimenSdp`, `DimenSsp`, `*DpExtensions`, `*SpExtensions`, `DimenScaled`, `Dimen*PlainPx.kt` for `Float` px + `Context` branching, `code/plain/DimenPlainBranch.kt` shared plain logic.
- **`com.appdimens.dynamic.compose.resize`** / **`com.appdimens.dynamic.code.resize`** — constraint-based **resize** (binary search over discrete px steps, “fits” predicate); not the same as `calculateRawScaling` curves.

## Strategy → documentation file

| Folder suffix | Doc |
|---------------|-----|
| scaled | [DOCUMENTATION/scaled.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/scaled.md) |
| percent | [DOCUMENTATION/percent.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/percent.md) |
| power | [DOCUMENTATION/power.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/power.md) |
| fluid | [DOCUMENTATION/fluid.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/fluid.md) |
| auto | [DOCUMENTATION/auto.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/auto.md) |
| diagonal | [DOCUMENTATION/diagonal.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/diagonal.md) |
| fill | [DOCUMENTATION/fill.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/fill.md) |
| fit | [DOCUMENTATION/fit.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/fit.md) |
| interpolated | [DOCUMENTATION/interpolated.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/interpolated.md) |
| logarithmic | [DOCUMENTATION/logarithmic.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/logarithmic.md) |
| perimeter | [DOCUMENTATION/perimeter.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/perimeter.md) |
| density | [DOCUMENTATION/density.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/density.md) |
| resize | [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/resize.md) |
| units (physical) | [DOCUMENTATION/physical-units.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/physical-units.md) |

**Product / formal math (English):** [PRD.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/PRD.md) · [PDR.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/PDR.md) · [MATHEMATICS-AND-CALCULUS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)

## Example application module (`app`)

Upstream sample module only (not shipped inside `io.github.bodenberg:appdimens-dynamic`); use for pattern reference:

- **Compose** — [`ExampleActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/compose/ExampleActivity.kt), [`BenchmarkActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/compose/BenchmarkActivity.kt), [`compose/benchmark/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.4/app/src/main/java/com/example/app/compose/benchmark), [`DemoCalcRouting.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/compose/DemoCalcRouting.kt).
- **Kotlin Views** — [`ExampleActivity.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/kotlin/ExampleActivity.kt).
- **Java Views** — [`ExampleActivity.java`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/java/ExampleActivity.java) (Data Binding sample, `DimenSdp`, `DimenSsp`, `DimenScaled`, `DimenResize`, physical units).
- **Application** — [`InitializeApplication.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/app/src/main/java/com/example/app/InitializeApplication.kt) (minimal; cache init patterns are documented in [README](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/README.md) / [library/PERFORMANCE.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/library/PERFORMANCE.md)).

## Internal `DimenCache.CalcType` (debug / cache tagging)

Maps to package families: AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC, PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY — see [`DimenCache.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.4/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt). End users think in **strategy names** and imports, not this enum.

## What this reference intentionally skips

Per the skill: **do not** expand on **`ignoreMultiWindows`** or **`*i` / `*ia`** suffix workflows when guiding users — those are omitted from the interactive workflow.
