# Power strategy (`compose.power` / `code.power`)

**Same extension / facilitator / builder shape as scaled** — replace the prefix `sdp` → `pwsdp`, `hdp` → `pwhdp`, `wdp` → `pwwdp`, `ssp` → `pwssp`, etc. Full table: [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Power** applies **sublinear** growth on the axis: the scale factor goes like `(dim/300)^0.75` instead of `dim/300`. Useful to keep icons, margins, and text from “blowing up” on wide monitors or large tablets while still growing smoothly.

## Calculation used

Conceptually, for effective axis `dim` (dp), after inverters and with multi-window handling:

- `ratio = dim / 300`
- `out = base × ratio^0.75`

**Implementation note:** For **smallest-width** + **default inverter**, `ratio^0.75` is **pre-computed** once per configuration change (`DimenCache.currentPowerScale`). Other qualifiers still compute `ratio` from `readScreenDp` and use `Math.pow(ratio, 0.75)`.

- With **`applyAspectRatio`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculatePowerDpCompose` in `DimenPowerDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.power.pwsdp
import com.appdimens.dynamic.compose.power.pwhdp
import com.appdimens.dynamic.compose.power.pwwdp

Modifier.size(48.pwsdp) // smallest-width axis, power curve
```

**Code:** `com.appdimens.dynamic.code.power` — mirrored prefixes (`pwsdp`, etc.) on the same pattern.

Artifacts: `DimenPowerDp`, `DimenPower`, `DimenPowerSp`, `PowerSp`, … Kotlin facilitators: `DimenPowerDpExtensions.kt`, `DimenPowerSpExtensions.kt`; Plain (Views): `DimenPowerPlainPx.kt`.

## Why use it

Lowers the effective growth exponent on large screens vs linear, for a “more contained” feel without breakpoint jumps.

## When to use it

- Monitors, tablets in landscape, layouts with a lot of usable width.
- When **scaled** oversizes elements but **logarithmic** would be too conservative.

## Advantages and trade-offs

- **Pros:** smooth transition; same API as other strategies (inverters, `a`/`i`).
- **Cons:** on narrow phones values stay close to linear; in mixed projects, document where **power** is used for design/dev alignment.

## Recommended usage strategy

Keep **scaled** as the global default; apply **power** to **component families** (e.g. dashboard grid, toolbar icons) after QA on at least one phone and one wide tablet.

[Back to index](README.md)
