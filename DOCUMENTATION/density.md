# Density strategy (`compose.density` / `code.density`)

**Same API surface as scaled** with prefixes `dsdp` / `dhdp` / `dwdp` / `dssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Density** multiplies the base by **`densityDpi / 160`** — the same factor that ties dp to px per density bucket. It does **not** replace width-based responsive layout; it aligns sizes with the **mdpi/hdpi/xhdpi/… bucket**.

## Calculation used

- `out = base × (configuration.densityDpi / 160f)`
- With **`a`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculateDensityDpCompose` in `DimenDensityDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.density.dsdp

Modifier.size(24.dsdp)
```

Prefixes: `dsdp`, `dhdp`, `dwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.density`.

## Why use it

When the design calls for **explicit alignment** to the density bucket (bitmap icons, strokes that track `mdpi`/`xxhdpi`, asset pipelines).

## When to use it

- Harmonize with `drawable-*dpi` resources when the rest of the layout is already in logical dp.
- Internal tools or themes that **must** scale with physical dpi.

## Advantages and trade-offs

- **Pros:** predictable for **dpi**-first thinking.
- **Cons:** devices with the **same width dp** but different dpi **diverge** — usually the opposite of sw-based responsive layout; use deliberately.

## Recommended usage strategy

Prefer **scaled** for general layout. Reserve **density** for **legacy assets** or areas where the spec is “approximate physical size” alongside static resources — and document the exception.

[Back to index](README.md)
