# Perimeter strategy (`compose.perimeter` / `code.perimeter`)

**Same API surface as scaled** with prefixes `prsdp` / `prhdp` / `prwdp` / `prssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Perimeter** scales by the **normalized screen perimeter**: sum of shorter and longer side in dp, divided by reference **833 dp** (perimeter reference aligned with 300 + 533 in `DesignScaleConstants`). It reflects “width + height” together, unlike diagonal or a single axis.

## Calculation used

Mathematically:

- `scale = (shorter + longer) / BASE_PERIMETER_DP` with `BASE_PERIMETER_DP = 833` (`DesignScaleConstants`, aligned with 300 + 533).
- `out = base × scale`

**Implementation note:** `scale` is **pre-computed** once per configuration change (`DimenCache.currentPerimeterScale`). The formula matches `(shorter + longer) / 833`; the runtime does not re-sum sides on every call.

- With **`a`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculatePerimeterDpCompose` in `DimenPerimeterDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.perimeter.prsdp

Modifier.size(72.prsdp)
```

Prefixes: `prsdp`, `prhdp`, `prwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.perimeter`.

## Why use it

When **width and height together** should drive size (frame-like cards, grids that represent total area).

## When to use it

- Layouts that think in **total area** but want a **linear** formula on the sum of sides (vs the square root in **diagonal**).
- A perceptual alternative to **diagonal** when you want different weighting across dimensions.

## Advantages and trade-offs

- **Pros:** simple; folds both axes into one scalar.
- **Cons:** two screens with similar diagonal can differ from diagonal-only metrics — align with design on which metric is source of truth.

## Recommended usage strategy

Use on **card / thumbnail** families; avoid mixing at the same hierarchy level with **diagonal** without a clear guideline, or scales may clash.

[Back to index](README.md)
