# Logarithmic strategy (`compose.logarithmic` / `code.logarithmic`)

**Same API surface as scaled** with prefixes `logsdp` / `loghdp` / `logwdp` / `logssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Logarithmic** shapes the multiplier with the **natural log** around **300 dp** on the effective axis: above 300 dp it grows with `ln(dim/300)`; below it shrinks with `ln(300/dim)`. Internal sensitivity **0.4** in the current implementation. Smooths extremes without rigid plateaus like **fluid**.

## Calculation used

Mathematically (effective axis `dim` in dp):

- `inv = 1/300`
- If `dim > 300`: `scale = 1 + 0.4 × ln(dim × inv)`
- If `dim ≤ 300`: `scale = 1 − 0.4 × ln(300 / dim)`
- `out = base × scale`

**Implementation note:** For **smallest-width** + **default inverter**, `scale` uses **smallestScreenWidthDp** and is **pre-computed** once per configuration change (`DimenCache.currentLogScale`). Other qualifiers still evaluate the piecewise `ln` formula above inline.

- With **`a`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculateLogarithmicDpCompose` in `DimenLogarithmicDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.logarithmic.logsdp

Modifier.height(56.logsdp)
```

Prefixes: `logsdp`, `loghdp`, `logwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.logarithmic`.

## Why use it

**Damped growth** across very wide size ranges, useful when linear jumps between compact phones and tablets hurt hierarchy.

## When to use it

- Very wide **sw** ranges with the same token.
- When **power** still grows too fast at the top end.

## Advantages and trade-offs

- **Pros:** smooth, monotonic curve; anchor at 300 dp.
- **Cons:** less intuitive for non-technical stakeholders; document **logarithmic** tokens in the design system.

## Recommended usage strategy

Apply **locally** (accent type, nav icons) and keep **scaled** for the base grid; compare side by side with **interpolated** in mocks before locking in.

[Back to index](README.md)
