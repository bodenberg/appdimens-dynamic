# Interpolated strategy (`compose.interpolated` / `code.interpolated`)

**Same API surface as scaled** with prefixes `isdp` / `ihdp` / `iwdp` / `issp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Interpolated** takes the **arithmetic mean** between the **fixed** base token (dp) and the **fully linear** value at the 300 dp reference: `base + (linear − base) × 0.5`. It is a compromise between “always the same dp” and “fully proportional to the axis”.

## Calculation used

- `linear = base × dim × (1/300)` on the effective axis.
- `out = base + (linear − base) × 0.5f`
- With **`a`**: `aspectRatioMultiplier`.

Implementation: `calculateInterpolatedDpCompose` in `DimenInterpolatedDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.interpolated.isdp

Modifier.padding(14.isdp)
```

Prefixes: `isdp`, `ihdp`, `iwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.interpolated`.

## Why use it

Softens pure linear scaling on tablets without going as aggressive as **logarithmic** or **power** everywhere.

## When to use it

- When **scaled** pulls too hard but fixed dp tokens look **too small** on tablet.
- Fine-tuning **hierarchy** between two reference sizes.

## Advantages and trade-offs

- **Pros:** one simple formula; easy to explain (“halfway”).
- **Cons:** the 0.5 blend is fixed in the library — no knob; for other blends use conditional builders or another strategy.

## Recommended usage strategy

Try **interpolated** on components where **scaled** and **percent** did not work; validate on a small phone and a 600+ sw tablet so the midpoint is acceptable.

[Back to index](README.md)
