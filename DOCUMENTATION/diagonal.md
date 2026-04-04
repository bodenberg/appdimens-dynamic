# Diagonal strategy (`compose.diagonal` / `code.diagonal`)

## What it is

**Diagonal** scales by the **length of the screen rectangle’s diagonal** in dp: it combines width and height into one number, reflecting “perceived canvas size” better than a single axis alone. The SDP/HDP/WDP qualifier **does not change** the diagonal used — the math always uses the current window’s shorter and longer side.

## Calculation used

- `shorter = min(widthDp, heightDp)`, `longer = max(...)`
- `diag = √(shorter² + longer²)`
- `out = base × (diag / BASE_DIAGONAL_DP)` with `BASE_DIAGONAL_DP = √(300² + 533²) ≈ 611.63` (`DesignScaleConstants`).
- With **`a`**: additional `aspectRatioMultiplier`.

Implementation: `calculateDiagonalDpCompose` in `DimenDiagonalDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.diagonal.dgsdp

Modifier.size(64.dgsdp)
```

Main Compose prefixes: `dgsdp`, `dghdp`, `dgwdp` (plus `a` / `i` / `ia`, `Px`, inverters like `.dgsdpPh`, `.dgsdpLw`, … in `DimenDiagonalDp.kt`).

**Code:** `com.appdimens.dynamic.code.diagonal`.

## Why use it

Elements that should react to **overall canvas size** (games, dense dashboards, frames) rather than width or height alone.

## When to use it

- Balance **wide short** vs **tall narrow** screens fairly.
- When **sdp** or **wdp** alone skew visual hierarchy.

## Advantages and trade-offs

- **Pros:** one stable scalar for “how big is the screen” overall.
- **Cons:** less intuitive for designers used to width breakpoints; inverters have limited effect on the diagonal itself (the read axis changes, but the formula still uses the current sides).

## Recommended usage strategy

Reserve **diagonal** for specific components (e.g. board piece size, centered hero icon); keep lists and running text on **scaled** or **hdp**/**wdp** depending on the dominant axis.

[Back to index](README.md)
