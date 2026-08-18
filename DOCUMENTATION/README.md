# AppDimens Dynamic documentation — strategies by package

This folder goes deeper into each **scaling strategy** in [AppDimens Dynamic](../README.md): what it is, the formula, how to import it, and when to pick each mode. Each strategy’s code lives in `com.appdimens.dynamic.compose.<strategy>` and `com.appdimens.dynamic.code.<strategy>` with **no cross-imports** between strategies.

**Modules (3.1.9):** principal `appdimens-dynamic` (scaled + core/common/plain); strategy modules `appdimens-dynamic-<strategy>`; BOM `appdimens-dynamic-bom`. See [README — Installation](../README.md#installation-v318) · [MODULES.md](MODULES.md).

### 3.1.9 Changes

- **Atomic fast-partition slot**: `metrics + partition` are now published as **one** `@Volatile` `FastPartitionSlot` — eliminates the race where a multi-window app could transiently resolve against another window's partition.
- **Hardened race tests**: any wrong return value fails; each thread must get its own value back; new `concurrentSnapshots_neverReturnValueFromAnotherSnapshot` test.
- **Diagnostics counters internal**: `hitCount` / `missCount` / `evictionCount` moved from public `@JvmField` to `@PublishedApi internal`.

### 3.1.8 Changes

- **Event-driven config watcher**: Replaces sampled validation (`validationTick`) with synchronous invalidation via `ComponentCallbacks2`.
- **Specialized kernels**: Zero-branch resolution per family/qualifier (`resolveSdpPx`, `resolveSdpaPx`, etc.).
- **Non-Compose fast lane**: `fastMetricsForCode` skips ThreadLocal probe.
- **DimenMetrics eager AR**: `normalizedAspectRatio` / `logNormalizedAspectRatio` changed from `lazy` to plain `val`.
- **BenchLab module**: New competitor benchmark (Dynamic vs SDPS vs Lib #2) with full scroll screenshot + report export.

**Product docs:** [PRD.md](PRD.md) · [PDR.md](PDR.md) · [MATHEMATICS-AND-CALCULUS.md](MATHEMATICS-AND-CALCULUS.md).

For **cache, bypass, and performance**, see also [library/PERFORMANCE.md](../library/PERFORMANCE.md).

**Naming parity (`compose` vs `code`):** In the multi-module tree, each strategy lives under **`library/`** (scaled) or **`library-<strategy>/`**, pairing **`Dimen<Strategy>DpExtensions.kt`** (layout facilitators → `Float` px + `Context`) with **`Dimen<Strategy>SpExtensions.kt`** where Sp facilitators exist — the same filenames as under `compose/<strategy>/`, so it is easy to jump between UI toolkits. **Scaled** uses **`DimenSdpExtensions.kt`** and **`DimenSspExtensions.kt`** inside the `scaled/` subfolder (packages stay top-level `compose` / `code`). **Plain** View helpers remain in **`Dimen<Strategy>PlainPx.kt`** per strategy plus shared logic in **`com.appdimens.dynamic.code.plain`** (principal artifact).

**Compose API catalog:** [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md) (scaled surface + prefix map; §4.5 View/`code` Plain). Resize: [resize.md](resize.md) · KDoc [`compose.resize`](KDOC/com.appdimens.dynamic.compose.resize/index.md), [`code.resize`](KDOC/com.appdimens.dynamic.code.resize/index.md).

**KDoc export:** [index.md](index.md) · pages under [`KDOC/`](KDOC/).

**Refresh KDoc:** Dokka output goes to the git-ignored `DOCUMENTATION2/` directory (`./gradlew :library:dokkaGenerateHtml`). The `KDOC/` pages committed here are a snapshot and may lag the source — prefer [library/PERFORMANCE.md](../library/PERFORMANCE.md) and the strategy guides for cache / modular behavior.

**Note:** Dokka may show `ERROR CLASS` for some Compose types in exported pages (classpath resolution during generation), not a library runtime error.

## Summary

| Strategy | Maven artifact (3.1.9) | Document |
|----------|------------------------|----------|
| **Unified math (all strategies)** | — | [MATHEMATICS-AND-CALCULUS.md](MATHEMATICS-AND-CALCULUS.md) |
| **Module graph / packaging** | see [MODULES.md](MODULES.md) | [MODULES.md](MODULES.md) |
| **BOM (version alignment only)** | `appdimens-dynamic-bom` | [MODULES.md](MODULES.md) |
| Scaled (default SDP / HDP / WDP) | `appdimens-dynamic` (principal) | [scaled.md](scaled.md) |
| Percent (linear 1/300 + `space*`) | `appdimens-dynamic-percent` | [percent.md](percent.md) |
| Power (sublinear) | `appdimens-dynamic-power` | [power.md](power.md) |
| Fluid (320–768 dp band) | `appdimens-dynamic-fluid` | [fluid.md](fluid.md) |
| Auto (linear + log after 480 dp) | `appdimens-dynamic-auto` | [auto.md](auto.md) |
| Diagonal | `appdimens-dynamic-diagonal` | [diagonal.md](diagonal.md) |
| Fill (“cover”) | `appdimens-dynamic-fill` | [fill.md](fill.md) |
| Fit (“contain”) | `appdimens-dynamic-fit` | [fit.md](fit.md) |
| Interpolated | `appdimens-dynamic-interpolated` | [interpolated.md](interpolated.md) |
| Logarithmic | `appdimens-dynamic-logarithmic` | [logarithmic.md](logarithmic.md) |
| Perimeter | `appdimens-dynamic-perimeter` | [perimeter.md](perimeter.md) |
| Density | `appdimens-dynamic-density` | [density.md](density.md) |
| Resize (constraint-based auto-fit) | `appdimens-dynamic-resize` | [resize.md](resize.md) |
| Physical units (mm, cm, in) | `appdimens-dynamic-units` | [physical-units.md](physical-units.md) |

### Quick links

- [PRD.md](PRD.md) · [PDR.md](PDR.md)

0. [KDoc API — root index](index.md)  
0a. [Modules — Maven/Gradle graph (3.1.9)](MODULES.md)  
0b. [Mathematics & calculus — formal reference](MATHEMATICS-AND-CALCULUS.md)  
1. [Compose API reference — conventions & scaled catalog](COMPOSE-API-CONVENTIONS.md)  
2. [Scaled](scaled.md) — recommended starting point  
3. [Percent](percent.md)  
4. [Power](power.md)  
5. [Fluid](fluid.md)  
6. [Auto](auto.md)  
7. [Diagonal](diagonal.md)  
8. [Fill](fill.md)  
9. [Fit](fit.md)  
10. [Interpolated](interpolated.md)  
11. [Logarithmic](logarithmic.md)  
12. [Perimeter](perimeter.md)  
13. [Density](density.md)  
14. [Resize](resize.md)  
15. [Physical units](physical-units.md)  
16. [Mathematics & calculus](MATHEMATICS-AND-CALCULUS.md)  

## Suggested decision flow

```mermaid
flowchart LR
  start[New_layout]
  scaled[scaled_sdp_hdp_wdp]
  qa[QA_phone_tablet]
  other[Other_strategy]
  start --> scaled
  scaled --> qa
  qa -->|curve_not_right| other
```

Always start with **scaled**; switch strategy only where visual QA or requirements (TV, ultrawide, split-screen) need a different growth curve.
