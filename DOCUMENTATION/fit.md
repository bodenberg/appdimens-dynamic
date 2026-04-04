# Fit strategy (`compose.fit` / `code.fit`)

**Same API surface as scaled** with prefixes `ftsdp` / `fthdp` / `ftwdp` / `ftssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Fit** is the dual of **fill**: a **“contain”**-style factor. It uses the **minimum** of `shorter/300` and `longer/533`, limiting scale by the **tighter** side of the screen so the layout does not mentally or physically **overflow**.

## Calculation used

- `rw = shorter / 300`, `rh = longer / 533`
- `out = base × min(rw, rh)`
- With **`a`**: multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculateFitDpCompose` in `DimenFitDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.fit.ftsdp

Modifier.widthIn(max = 400.ftsdp)
```

Prefixes: `ftsdp`, `fthdp`, `ftwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.fit`.

## Why use it

Enforces a **conservative** design box: forms, long reading, modals, and content that should not hug edges on wide landscape.

## When to use it

- Very wide aspect ratios when **fill** or **wdp** make elements too large.
- When the priority is **no clipping** and comfortable viewing.

## Advantages and trade-offs

- **Pros:** safe on ultrawide; stable hierarchy.
- **Cons:** can leave **a lot of empty space** on sides or top/bottom — acceptable if the design plans for it.

## Recommended usage strategy

Use **fit** for **readable body** content and **fill** for **hero** or backgrounds in the same project, and document that split in your design system.

[Back to index](README.md)
