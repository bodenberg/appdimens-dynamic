# AppDimens Dynamic — library map

**Doc base (Git ref `3.1.8`):** https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/

Read this file when you need package locations, Compose↔`code` symmetry, or core types.

---


## Gradle / Maven modules (3.1.8)

| Strategy | Gradle project | Maven artifact | Source roots |
|---|---|---|---|
| scaled (+ common/core/plain) | `:library` | `appdimens-dynamic` | `library/src/main/.../{common,core,code/plain,code/scaled,compose/scaled}` |
| *(BOM)* | `:library-bom` | `appdimens-dynamic-bom` | `library-bom/build.gradle.kts` (`java-platform`) |
| auto | `:library-auto` | `appdimens-dynamic-auto` | `library-auto/src/main/.../{code,compose}/auto` |
| density | `:library-density` | `appdimens-dynamic-density` | `library-density/.../density` |
| diagonal | `:library-diagonal` | `appdimens-dynamic-diagonal` | `library-diagonal/.../diagonal` |
| fill | `:library-fill` | `appdimens-dynamic-fill` | `library-fill/.../fill` |
| fit | `:library-fit` | `appdimens-dynamic-fit` | `library-fit/.../fit` |
| fluid | `:library-fluid` | `appdimens-dynamic-fluid` | `library-fluid/.../fluid` |
| interpolated | `:library-interpolated` | `appdimens-dynamic-interpolated` | `library-interpolated/.../interpolated` |
| logarithmic | `:library-logarithmic` | `appdimens-dynamic-logarithmic` | `library-logarithmic/.../logarithmic` |
| percent | `:library-percent` | `appdimens-dynamic-percent` | `library-percent/.../percent` |
| perimeter | `:library-perimeter` | `appdimens-dynamic-perimeter` | `library-perimeter/.../perimeter` |
| power | `:library-power` | `appdimens-dynamic-power` | `library-power/.../power` |
| resize | `:library-resize` | `appdimens-dynamic-resize` | `library-resize/.../resize` |
| units | `:library-units` | `appdimens-dynamic-units` | `library-units/.../units` |

Full graph: [DOCUMENTATION/MODULES.md](../DOCUMENTATION/MODULES.md). Satellites depend only on `:library`. `:library-bom` publishes version constraints.

## Package layout (packages span `:library` and `:library-*`)

Paths are in the upstream repo at `3.1.8`. The consumer app does not contain this tree unless they clone the monorepo.

- **`com.appdimens.dynamic.common`** — shared enums/value types: `DpQualifier` (SMALL_WIDTH, HEIGHT, WIDTH), `Inverter`, `Orientation`, `UiModeType`, `UnitType`, `DpQualifierEntry`.
- **`com.appdimens.dynamic.core`** — cross-cutting engine: `DimenMetrics` (immutable per-window snapshot: size, density, font scale, orientation, ui mode, multi-window; eager AR computation), `DimenCache` (snapshot-partitioned cache, event-driven config watcher via `ensureConfigWatcher`, specialized kernels: `resolveSdpPx`/`resolveSdpaPx`/`resolveHdpPx`/`resolveWdpPx` + DP variants, `fastMetricsForCode` for non-Compose fast lane; init; explicit invalidation not required for correctness since 3.1.8 — `invalidateOnConfigChange` is a compat hook; no disk persistence; stable `CalcType` ordinals), `StrategyFactorRegistry` / `SharedScreenMetrics` (source-compatibility hook — satellite scales now derive from `DimenCache.currentMetrics`), `MissingModule` (Maven hint map), `DimenCalculationPlumbing` (qualifier resolution, screen dp reads, aspect-ratio multiplier, internal flags not surfaced to users), `DesignScaleConstants`, `AspectRatioLookup` (exact `ln` since 3.1.8), percent/resize math (`PercentSpaceMath`, `ResizeMath`, `ResizeBound`, `AutoResizePercentBasis`), Compose integration (`CompositionLocals` / `AppDimensProvider`, `LocalUiModeType`, `LocalDimenMetrics`, `ComposeRememberStamps`, `ComposeDimenRemember`).
- **`com.appdimens.dynamic.compose.<strategy>`** — one folder per scaling strategy for Compose UI (e.g. `compose/scaled/DimenSdp.kt`, `DimenSdpExtensions.kt`, `DimenScaled.kt`, Sp mirrors).
- **`com.appdimens.dynamic.code.<strategy>`** — mirror for Views / Kotlin / Java: `DimenSdp`, `DimenSsp`, `*DpExtensions`, `*SpExtensions`, `DimenScaled`, `Dimen*PlainPx.kt`, `code/plain/DimenPlainBranch.kt`.
- **`com.appdimens.dynamic.compose.resize`** / **`com.appdimens.dynamic.code.resize`** — constraint-based resize (binary search over discrete px steps, "fits" predicate); distinct from `calculateRawScaling` curves.

---

## Strategy → documentation file

| Folder suffix | Doc |
|---------------|-----|
| scaled | [DOCUMENTATION/scaled.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/scaled.md) |
| percent | [DOCUMENTATION/percent.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/percent.md) |
| power | [DOCUMENTATION/power.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/power.md) |
| fluid | [DOCUMENTATION/fluid.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/fluid.md) |
| auto | [DOCUMENTATION/auto.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/auto.md) |
| diagonal | [DOCUMENTATION/diagonal.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/diagonal.md) |
| fill | [DOCUMENTATION/fill.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/fill.md) |
| fit | [DOCUMENTATION/fit.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/fit.md) |
| interpolated | [DOCUMENTATION/interpolated.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/interpolated.md) |
| logarithmic | [DOCUMENTATION/logarithmic.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/logarithmic.md) |
| perimeter | [DOCUMENTATION/perimeter.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/perimeter.md) |
| density | [DOCUMENTATION/density.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/density.md) |
| resize | [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/resize.md) |
| units (physical) | [DOCUMENTATION/physical-units.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/physical-units.md) |

**Formal docs:** [PRD.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/PRD.md) · [PDR.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/PDR.md) · [MATHEMATICS-AND-CALCULUS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)  
**API detail:** [DOCUMENTATION/index.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/DOCUMENTATION/index.md) · per-package pages under [`DOCUMENTATION/KDOC/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.8/DOCUMENTATION/KDOC)

---

## Example app module (`app`)

Upstream sample only — not in the Maven artifact. Use for pattern reference.

- **Compose** — [ExampleActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/compose/ExampleActivity.kt) · [BenchmarkActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/compose/BenchmarkActivity.kt) · [DemoCalcRouting.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/compose/DemoCalcRouting.kt)
- **Kotlin Views** — [ExampleActivity.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/kotlin/ExampleActivity.kt)
- **Java Views** — [ExampleActivity.java](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/java/ExampleActivity.java) (Data Binding, `DimenSdp`, `DimenSsp`, `DimenScaled`, `DimenResize`, physical units)
- **App init** — [InitializeApplication.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/app/src/main/java/com/example/app/InitializeApplication.kt); cache init patterns in [README](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/README.md) / [library/PERFORMANCE.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/library/PERFORMANCE.md)

---

## Internal `DimenCache.CalcType`

Debug/cache tagging only — end users think in strategy names. Values: AUTO, DIAGONAL, FILL, FIT, FLUID, INTERPOLATED, LOGARITHMIC, PERCENT, PERIMETER, POWER, RESIZE, SCALED, UNITIES, ASPECT_RATIO, DENSITY.  
Source: [DimenCache.kt](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.8/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt)

---

## What this file intentionally omits

`ignoreMultiWindows`, `*i`, and `*ia` suffix workflows — omitted from the interactive guidance.
