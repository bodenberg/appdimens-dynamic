# Scaled strategy (`compose.scaled` / mirrored `code`)

## What it is

**Scaled** is the **default** AppDimens Dynamic mode: it scales design values around the **300 dp** reference on the chosen axis (**smallest width**, **height**, or **width** of the window). This is the classic SDP / HDP / WDP behavior with cache, qualifiers, and `Configuration` integration.

## Calculation used

- Read the effective axis in dp (after orientation **inverters**, if any).
- **Without** `applyAspectRatio` (`a` / `ia`): result ≈ `base × (dim / 300)` — factor `1/300` (`INV_BASE_RATIO` in `DimenCache`).
- **With** `applyAspectRatio`: uses the refined curve in `DimenCache.calculateRawScaling`, combining deviation from 300 dp with adjustment tied to the log of the normalized screen aspect.
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

**Code (Views / Kotlin)** — `com.appdimens.dynamic.code.DimenSdp`, `DimenSsp`, extensions `ssp`, `scaledSp()`, etc.

Each strategy follows the same **file pattern**: `Dimen*Dp`, `*DpExtensions`, `*Scaled`, `*Sp`, `*SpExtensions`, `*SpScaled` (in `scaled`, Sp templates may use names like `DimenSsp*` in legacy code).

## Why use it

It is the library’s balanced choice: **predictable**, tightly integrated with **DimenCache**, supports **builders** (`scaledDp()` / `scaledSp()`), facilitators (rotation, UI mode, qualifier), and inverters — without exotic curves.

## When to use it

- Normal app layouts (phone, tablet, foldable).
- When you want the same baseline “look” the main README describes.
- Whenever there is no strong reason to use pure percent, power, fluid, etc.

## Advantages and trade-offs

- **Pros:** familiar curve; strong performance (including cache bypass on hot paths without AR); rich README examples.
- **Cons:** on very large screens, linear growth (without `a`) can feel aggressive vs **power** or **fluid** — then consider another strategy only for affected components.

## Recommended usage strategy

Use **SDP** (`.sdp`) for spacing and sizes that should stay coherent across rotation; **HDP** for vertical lists; **WDP** when available width should dominate. Enable **`a`** if non-standard aspect ratios hurt the layout. Only after QA, replace selectively with another strategy from this index.

[Back to index](README.md)
