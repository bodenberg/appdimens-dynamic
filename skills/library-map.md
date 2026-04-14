# AppDimens Dynamic — library map

**Doc base (Git ref `3.1.5`):** https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/

Read this file when you need package locations, Compose↔`code` symmetry, or core types.

---

## Module layout (`library`)

Paths are in the upstream repo at `3.1.5`. The consumer app does not contain this tree unless they clone the monorepo.

- **`com.appdimens.dynamic.common`** — shared enums/value types: `DpQualifier` (SMALL_WIDTH, HEIGHT, WIDTH), `Inverter`, `Orientation`, `UiModeType`, `UnitType`, `DpQualifierEntry`.
- **`com.appdimens.dynamic.core`** — cross-cutting engine: `DimenCache` (sharded cache, init, invalidation, internal `CalcType` per strategy family), `DimenCalculationPlumbing` (qualifier resolution, screen dp reads, aspect-ratio multiplier, internal flags not surfaced to users), `DesignScaleConstants`, `AspectRatioLookup`, percent/resize math (`PercentSpaceMath`, `ResizeMath`, `ResizeBound`, `AutoResizePercentBasis`), Compose integration (`CompositionLocals` / `AppDimensProvider`, `LocalUiModeType`, `ComposeRememberStamps`, `ComposeDimenRemember`).
- **`com.appdimens.dynamic.compose.<strategy>`** — one folder per scaling strategy for Compose UI (e.g. `compose/scaled/DimenSdp.kt`, `DimenSdpExtensions.kt`, `DimenScaled.kt`, Sp mirrors).
- **`com.appdimens.dynamic.code.<strategy>`** — mirror for Views / Kotlin / Java: `DimenSdp`, `DimenSsp`, `*DpExtensions`, `*SpExtensions`, `DimenScaled`, `Dimen*PlainPx.kt`, `code/plain/DimenPlainBranch.kt`.
- **`com.appdimens.dynamic.compose.resize`** / **`com.appdimens.dynamic.code.resize`** — constraint-based resize (binary search over discrete px steps, "fits" predicate); distinct from `calculateRawScaling` curves.

---

## Strategy → documentation file

| Folder suffix | Doc |
|---------------|-----|
| scaled | [DOCUMENTATION/scaled.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/scaled.md) |
| percent | [DOCUMENTATION/percent.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/percent.md) |
| power | [DOCUMENTATION/power.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/power.md) |
| fluid | [DOCUMENTATION/fluid.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/fluid.md) |
| auto | [DOCUMENTATION/auto.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/auto.md) |
| diagonal | [DOCUMENTATION/diagonal.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/diagonal.md) |
| fill | [DOCUMENTATION/fill.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/fill.md) |
| fit | [DOCUMENTATION/fit.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/fit.md) |
| interpolated | [DOCUMENTATION/interpolated.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/interpolated.md) |
| logarithmic | [DOCUMENTATION/logarithmic.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/logarithmic.md) |
| perimeter | [DOCUMENTATION/perimeter.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/perimeter.md) |
| density | [DOCUMENTATION/density.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/density.md) |
| resize | [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/resize.md) |
| units (physical) | [DOCUMENTATION/physical-units.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/physical-units.md) |

**Formal docs:** [PRD.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/PRD.md) · [PDR.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/PDR.md) · [MATHEMATICS-AND-CALCULUS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)  
**API detail:** [DOCUMENTATION/index.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/DOCUMENTATION/index.md) · per-package pages under [`DOCUMENTATION/KDOC/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.5/DOCUMENTATION/KDOC)

---

## Example app module (`app`)

Upstream sample only — not in the Maven artifact. Use for pattern reference.

- **Compose** — [ExampleActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/compose/ExampleActivity.kt) · [BenchmarkActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/compose/BenchmarkActivity.kt) · [DemoCalcRouting.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/compose/DemoCalcRouting.kt)
- **Kotlin Views** — [ExampleActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/kotlin/ExampleActivity.kt)
- **Java Views** — [ExampleActivity.java](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/java/ExampleActivity.java) (Data Binding, `DimenSdp`, `DimenSsp`, `DimenScaled`, `DimenResize`, physical units)
- **App init** — [InitializeApplication.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/app/src/main/java/com/example/app/InitializeApplication.kt); cache init patterns in [README](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/README.md) / [library/PERFORMANCE.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/library/PERFORMANCE.md)

---

## Internal `DimenCache.CalcType`

Debug/cache tagging only — end users think in strategy names. Values: AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC, PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY.  
Source: [DimenCache.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.5/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt)

---

## What this file intentionally omits

`ignoreMultiWindows`, `*i`, and `*ia` suffix workflows — omitted from the interactive guidance.
