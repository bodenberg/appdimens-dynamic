# Fill strategy (`compose.fill` / `code.fill`)

**Same API surface as scaled** with prefixes `flsdp` / `flhdp` / `flwdp` / `flssp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Fill** uses a **CSS “cover”**-style factor: the **maximum** of scale by the shorter side (vs 300 dp) and the longer side (vs 533 dp). Content “fills” the available space, favoring whichever side demands more scale.

## Calculation used

- `rw = shorter / 300`, `rh = longer / 533` (`BASE_WIDTH_DP`, `BASE_HEIGHT_DP` in `DesignScaleConstants`).
- `out = base × max(rw, rh)`
- With **`a`**: `aspectRatioMultiplier`.

Implementation: `calculateFillDpCompose` in `DimenFillDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.fill.flsdp

Modifier.height(120.flsdp)
```

Prefixes: `flsdp`, `flhdp`, `flwdp` (+ Sp, px, variants).

**Code:** `com.appdimens.dynamic.code.fill`.

## Why use it

Reduces empty strips in **landscape** or **ultrawide**, letting heroes, backgrounds, or grids grow to fill the canvas.

## When to use it

- Background images, banners, regions that should **dominate** space.
- When **fit** leaves too much margin but **scaled** does not pull the right axis.

## Advantages and trade-offs

- **Pros:** strong fill feeling; good for marketing visuals.
- **Cons:** padding and margins can become **too large** on very tall or wide screens — you may need `heightIn`/`widthIn` or smaller tokens.

## Recommended usage strategy

Use **fill** in isolated sections; avoid applying it to **all** global spacing. Pair with **fit** on reading panels if you need contrast on the same screen.

[Back to index](README.md)
