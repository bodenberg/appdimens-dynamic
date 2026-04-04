# Fluid strategy (`compose.fluid` / `code.fluid`)

**Same API surface as scaled** with prefixes `fsdp` / `fhdp` / `fwdp` / `fssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Fluid** clamps variation to a **narrow band** around the base in the mid phone range: the effective size interpolates between **80%** and **120%** of the base while the axis (in dp) runs from **320** to **768**; below 320 it stays at 80%; above 768 it caps at 120%.

## Calculation used

With `dim` the effective axis in dp:

- `minV = base × 0.8`, `maxV = base × 1.2`
- If `dim ≤ 320` → `v = minV`
- If `dim ≥ 768` → `v = maxV`
- Else → linear interpolation of `minV` to `maxV` between 320 and 768
- With **`a`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculateFluidDpCompose` in `DimenFluidDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.fluid.fsdp
import com.appdimens.dynamic.compose.fluid.fssp

Modifier.padding(16.fsdp)
Text("Title", fontSize = 20.fssp)
```

Compose prefixes: `fsdp`, `fhdp`, `fwdp` (and Sp, px, inverter, `a`/`i` variants).

**Code:** `com.appdimens.dynamic.code.fluid`.

## Why use it

For typography and spacing that **should not swing much** across typical phones, avoiding visible jumps on small width changes.

## When to use it

- Dense UI in the 320–768 dp band.
- When **scaled** feels too sensitive between two phone sizes.

## Advantages and trade-offs

- **Pros:** stable behavior for most devices; clear plateaus outside the band.
- **Cons:** above 768 dp size **stops growing** (120% cap) — may or may not be desired; large tablets may need another strategy or larger tokens.

## Recommended usage strategy

Use **fluid** on specific components (e.g. card body text) alongside **scaled** elsewhere; test at **320** and **768** and on tablets to validate the upper plateau.

[Back to index](README.md)
