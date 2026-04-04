# Percent strategy (`compose.percent` / `code.percent`)

## What it is

**Percent** provides two API families:

1. **`psdp` / `phdp` / `pwdp` (and Sp variants)** — scales the base value **linearly** with the chosen axis, equivalent to multiplying by `dim/300` when **`applyAspectRatio` is off**; with **`a`**, uses the same correction family as scaled (difference from base and aspect).
2. **`space*` extensions** (`DimenPercentSpace.kt`) — return a **true fraction** (receiver 0–100) of width, height, smallest width, or a reference length in dp.

## Calculation used

- **psdp / phdp / pwdp:** in `calculatePercentDpCompose`, without AR: `base × dim × (1/300)`. With AR: aligned with adjustment from difference vs 300 dp and `logNormalizedAr` (as scaled with AR).
- **`space*`:** explicit percentage of a `Configuration` metric (or reference); full contract in the main README (`i` variants for ignore-multi-window with raw-dp fallback when applicable).

Suffixes **`a`**, **`i`**, **`ia`** and **inverters** follow the global library pattern.

## How to use

```kotlin
import com.appdimens.dynamic.compose.percent.psdp
import com.appdimens.dynamic.compose.percent.spaceW

Modifier.padding(12.psdp)
Modifier.fillMaxWidth() // parent width; for 10% of screen width:
// Modifier.width(10.spaceW)
```

**Code:** `com.appdimens.dynamic.code.percent` — mirrors extensions and `DimenPercent` / `DimenPercentSp` for px and dp.

Use **`space*`** when you need a **literal screen percentage**; use **`psdp`…** when you want the same design “token” as other strategies but with a 1/300-style law on the axis (and optional AR).

## Why use it

For layouts where size should track one screen edge **uniformly**, or for “always X% of width” columns, without the implicit tablet damping of some other curves.

## When to use it

- Full-width columns, proportional gutters, typography tied to a **real %** of the window (`space*`).
- When **scaled** with AR still does not give the desired proportion across two axes.

## Advantages and trade-offs

- **Pros:** clear linear math (without AR); `space*` separates “design dp” from “% of screen”.
- **Cons:** without AR, growth on tablets can be **stronger** than **power**; `space*` needs care with split-screen and the `i` contract.

## Recommended usage strategy

Define most UI tokens with **scaled**; reserve **percent** where the design spec is **explicitly** percentage-based or linear along an edge. Validate multi-window behavior if you use `space*` without `i`.

[Back to index](README.md)
