# Scaled strategy (`compose.scaled` / mirrored `code`)

**Complete API surface (all `sdp`/`hdp`/`wdp`/`ssp`… properties, facilitators, `scaledDp`/`scaledSp`, prefix map for other strategies):** [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md)

## What it is

**Scaled** is the **default** AppDimens Dynamic mode: it scales design values around the **300 dp** reference on the chosen axis (**smallest width**, **height**, or **width** of the window). This is the classic SDP / HDP / WDP behavior with cache, qualifiers, and `Configuration` integration.

## Calculation used

- Read the effective axis in dp (after orientation **inverters**, if any).
- **Without** `applyAspectRatio` (`a` / `ia`): result ≈ `base × (dim / 300)` — factor `1/300` (`INV_BASE_RATIO` in `DimenCache`).
- **With** `applyAspectRatio`: uses `DimenCache.calculateRawScaling`, which combines deviation from 300 dp with adjustment from **pre-computed** screen factors (`arMultiplier`, `logNormalizedAr` in `ScreenFactors`, updated on configuration change).
- With `ignoreMultiWindows` (`i` / `ia`) and the multi-window heuristic active: returns the **raw base** value (no scaling).
- Reference constant: `BASE_WIDTH_DP = 300f` in `DesignScaleConstants` (which axis is used depends on the qualifier).

Common suffixes: **`a`** = aspect ratio; **`i`** = ignore multi-window; **`ia`** = both. Inverters (e.g. `.sdpPh`, `.sdpLw`) swap the effective axis by orientation.

## How to use

**Compose** — import extensions from `com.appdimens.dynamic.compose.scaled` (the package that exposes `sdp`, `hdp`, `wdp`, `ssp`, etc.):

```kotlin
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp

Modifier.padding(16.sdp).width(100.wdp)
```

The **`code`** module also exposes **`Int`** and **`Float`** receiver overloads for `sdp` / `hdp` / `wdp` (and `a` / `i` / `ia` variants), plus `toDynamicScaledPx` / `toDynamicScaledDp`, to avoid `Number` boxing on hot paths. The **`Number`** overloads remain for backward compatibility.

**Code (Views / Kotlin)** — `com.appdimens.dynamic.code.DimenSdp`, `DimenSsp`, extensions `ssp`, `scaledSp()`, etc.

Each strategy follows the same **file pattern**: `Dimen*Dp`, `*DpExtensions`, `*Scaled`, `*Sp`, `*SpExtensions`, `*SpScaled` (in `scaled`, Sp templates may use names like `DimenSsp*` in legacy code).

**Builders (`scaledDp` / `scaledSp`):** `customValue` accepts **fractional** dp/sp (`Number` / `Dp`); values are **not** truncated to integers. The **`screen(..., customValue: Number)`** overload forwards **`applyAspectRatio`** and **`customSensitivityK`** like the **`Dp`** overload. **SSP** scaling uses the same **`DimenCalculationPlumbing`** multi-window and qualifier rules as the Dp path.

## Why use it

It is the library’s balanced choice: **predictable**, tightly integrated with **DimenCache**, supports **builders** (`scaledDp()` / `scaledSp()`), facilitators (rotation, UI mode, qualifier), and inverters — without exotic curves.

## When to use it

- Normal app layouts (phone, tablet, foldable).
- When you want the same baseline “look” the main README describes.
- Whenever there is no strong reason to use pure percent, power, fluid, etc.

## Advantages and trade-offs

- **Pros:** familiar curve; strong performance — without aspect ratio, `DimenCache.getOrPut` bypasses shard storage for six cheap `CalcType`s (**PERCENT**, **SCALED**, **DENSITY**, **DIAGONAL**, **INTERPOLATED**, **PERIMETER**); see [library/PERFORMANCE.md](../library/PERFORMANCE.md). Rich README examples.
- **Cons:** on very large screens, linear growth (without `a`) can feel aggressive vs **power** or **fluid** — then consider another strategy only for affected components.

## Recommended usage strategy

Use **SDP** (`.sdp`) for spacing and sizes that should stay coherent across rotation; **HDP** for vertical lists; **WDP** when available width should dominate. Enable **`a`** if non-standard aspect ratios hurt the layout. Only after QA, replace selectively with another strategy from this index.

[Back to index](README.md)
