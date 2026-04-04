# Percent strategy (`compose.percent` / `code.percent`)

## What it is

**Percent** provides two API families:

1. **`psdp` / `phdp` / `pwdp` (and Sp variants)** — scales the base value **linearly** with the chosen axis, equivalent to multiplying by `dim/300` when **`applyAspectRatio` is off**; with **`a`**, uses the same correction family as scaled (difference from base and aspect).
2. **`space*` extensions** (`DimenPercentSpace.kt`) — interpret the receiver as **0–100** and return **`(percent / 100) × axisOrReference`** in **dp** (or px for `*Px` variants), using the current `Configuration` axis or the given reference length.

## Calculation used

- **psdp / phdp / pwdp:** in `calculatePercentDpCompose`, without AR: `base × dim × (1/300)`. With AR: aligned with adjustment from difference vs 300 dp and `logNormalizedAr` (as scaled with AR).
- **`space*`:** always computes a **length** in dp (or px for `*Px`), including in **multi-window** when the library’s heuristic marks the window as constrained: the axis read is still the **current** window’s `screenWidthDp` / `screenHeightDp` / `smallestScreenWidthDp` (or the reference length). Variants with **`i`** pass `ignoreMultiWindows = true` into the pipeline (affects **psdp**-style scaling elsewhere; **`space*`** still returns a fractional length, not a raw `50` for “50%”).

Suffixes **`a`**, **`i`**, **`ia`** and **inverters** follow the global library pattern.

**Mirrored Compose surface:** every `sdp` / `hdp` / `wdp` / `ssp`… name becomes `psdp` / `phdp` / `pwdp` / `pssp`… with the **same** suffix rules (`a`, `i`, `ia`, `Px`, `Ph`, …). See the prefix column in [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled) and facilitators `p*Rotate`, `p*Mode`, `p*Qualifier`, `p*Screen` in the percent packages (`DimenPercentDpExtensions.kt`, `DimenPercentSpExtensions.kt`).

## How to use

```kotlin
import com.appdimens.dynamic.compose.percent.psdp
import com.appdimens.dynamic.compose.percent.spaceW

Modifier.padding(12.psdp)
Modifier.width(10.spaceW) // 10% of Configuration.screenWidthDp
```

**Code:** `com.appdimens.dynamic.code.percent` — mirrors extensions and `DimenPercent` / `DimenPercentSp` for px and dp.

Use **`space*`** when you need a **literal screen percentage**; use **`psdp`…** when you want the same design “token” as other strategies but with a 1/300-style law on the axis (and optional AR).

---

## Literal `space*` API (`compose.percent` — `DimenPercentSpace.kt`)

**Contract:** the **receiver** is the percentage **0–100** (e.g. `10` → 10% of the chosen axis or reference). **`i`** / **`Wi`** / **`Swi`** / … variants set `ignoreMultiWindows = true` for APIs that forward that flag; **`space*`** results remain **dp** (or **px**) derived from the same percentage formula, not the literal receiver value.

### Screen-axis `Dp` / px (no extra args)

| Member | Returns | Meaning | Example |
|--------|---------|---------|---------|
| `spaceW` | `Dp` | % of `screenWidthDp` | `Modifier.width(15.spaceW)` |
| `spaceWi` | `Dp` | same + ignore multi-window | `Modifier.width(15.spaceWi)` |
| `spaceWPx` | `Float` | px | `val x = 15.spaceWPx` |
| `spaceWPxi` | `Float` | px + `i` | `val x = 15.spaceWPxi` |
| `spaceSw` | `Dp` | % of `smallestScreenWidthDp` | `Modifier.height(20.spaceSw)` |
| `spaceSwi` | `Dp` | + `i` | `Modifier.height(20.spaceSwi)` |
| `spaceSwPx` | `Float` | px | `20.spaceSwPx` |
| `spaceSwPxi` | `Float` | px + `i` | `20.spaceSwPxi` |
| `spaceH` | `Dp` | % of `screenHeightDp` | `Modifier.height(12.spaceH)` |
| `spaceHi` | `Dp` | + `i` | `Modifier.height(12.spaceHi)` |
| `spaceHPx` | `Float` | px | `12.spaceHPx` |
| `spaceHPxi` | `Float` | px + `i` | `12.spaceHPxi` |

### Reference length `%` (`Dp` or `Number` as dp)

| Member | Returns | Meaning | Example |
|--------|---------|---------|---------|
| `space(reference: Dp)` | `Dp` | % of `reference.value` | `25.space(200.dp)` |
| `space(reference: Number)` | `Dp` | % of `reference` dp | `25.space(200)` |
| `spacePx(reference: Dp)` | `Float` | px | `25.spacePx(200.dp)` |
| `spacePx(reference: Number)` | `Float` | px | `25.spacePx(200)` |
| `spaceI(reference: Dp)` | `Dp` | + ignore multi-window | `25.spaceI(180.dp)` |
| `spaceI(reference: Number)` | `Dp` | + `i` | `25.spaceI(180)` |
| `spacePxi(reference: Dp)` | `Float` | px + `i` | `25.spacePxi(180.dp)` |
| `spacePxi(reference: Number)` | `Float` | px + `i` | `25.spacePxi(180)` |

### `TextUnit` (`sp`) — same geometry as dp result; `fontScale` optional

| Member | Returns | Example |
|--------|---------|---------|
| `spaceWSp(fontScale = true, ignoreMultiWindows = false)` | `TextUnit` | `Text("A", fontSize = 5.spaceWSp())` |
| `spaceWSpi(fontScale = true)` | `TextUnit` | `Text("A", fontSize = 5.spaceWSpi())` |
| `spaceWSpPx(...)` | `Float` | `val px = 5.spaceWSpPx()` |
| `spaceWSpiPx()` | `Float` | `5.spaceWSpiPx()` |
| `spaceSwSp` / `spaceSwSpi` / `spaceSwSpPx` / `spaceSwSpiPx` | same pattern for sw | `Text("B", fontSize = 4.spaceSwSp())` |
| `spaceHSp` / `spaceHSpi` / `spaceHSpPx` / `spaceHSpiPx` | same for height | `Text("C", fontSize = 3.spaceHSp())` |
| `spaceSp(reference: Dp, …)` | `TextUnit` | `Text("D", fontSize = 10.spaceSp(200.dp))` |
| `spaceSp(reference: Number, …)` | `TextUnit` | `10.spaceSp(200)` |
| `spaceSpi(reference: Dp)` / `spaceSpi(reference: Number)` | `TextUnit` + `i` | `10.spaceSpi(200.dp)` |
| `spaceSpPx` / `spaceSpiPx` | `Float` | `10.spaceSpPx(200.dp)` |

**Recommendation:** prefer **`spaceW` / `spaceSw` / `spaceH`** for true layout %-columns; keep **`psdp`** for token-style scaling. For **`psdp` / `pssp`** and other scaled-style APIs, test **`i`** in split-screen (may return **unscaled base** when the multi-window heuristic applies); **`space*`** keeps the **% of axis** semantics.

## Why use it

For layouts where size should track one screen edge **uniformly**, or for “always X% of width” columns, without the implicit tablet damping of some other curves.

## When to use it

- Full-width columns, proportional gutters, typography tied to a **real %** of the window (`space*`).
- When **scaled** with AR still does not give the desired proportion across two axes.

## Advantages and trade-offs

- **Pros:** clear linear math (without AR); `space*` separates “design dp” from “% of screen”.
- **Cons:** without AR, growth on tablets can be **stronger** than **power**; combine **`psdp`** with **`i`** carefully in split-screen (behavior differs from **`space*`** percentage lengths).

## Recommended usage strategy

Define most UI tokens with **scaled**; reserve **percent** where the design spec is **explicitly** percentage-based or linear along an edge. Validate multi-window behavior for **`psdp`** / **`i`**; **`space*`** continues to express a true **% of window** in dp.

[Back to index](README.md)
