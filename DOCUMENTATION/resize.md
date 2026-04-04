# Resize module (`compose.resize` / `code.resize`)

This module is **not** prefix-mirrored like sdp/hdp strategies; the full Compose list is under **§ Compose API reference** below. For `sdp`-style extensions, see [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md).

## What it is

**Resize** is not a global scale curve like **scaled**: it is a set of **composables and helpers** that pick the **largest** size in a `[min, max]` range with **step** granularity that still **fits** inside the constraints from `BoxWithConstraints` (max width/height in dp). Typical uses are **auto-sized text** (similar to `TextView` auto-size) and **box sizes** (square, width, height) that should use space without overflowing.

## Calculation used

- Build a sequence of candidate **pixels** from `minPx` to `maxPx` with `stepPx` granularity (`buildResizeStepsPx` in `ResizeMath.kt`).
- **Binary search** (`findLargestFittingResizePx`) finds the largest candidate for which a “fits” predicate is true. If **no** candidate satisfies `fits`, the result is **`0f`** (including when the step table has a **single** element that does not fit).
- For Compose text, each **sp** candidate is measured with `rememberTextMeasurer` and `Constraints(maxWidth, maxHeight)` aligned to local style; only `fontSize` is swept.
- Variants with **box-local percent** (`autoResize*Percent`) and **`ResizeBound`** for **screen-axis** percent (sw / w / h) or fixed dp/sp — see the main README and `ResizeBound.kt`. **`ResizeBound.resolveToPx`** requires **`density > 0`** and clamps negative dp/sp and out-of-range percent inputs to safe bounds.

`DimenCache.CalcType.RESIZE` tags related cache entries where applicable; core logic lives in the **resize** module, not `calculateRawScaling`.

## How to use

Call these **inside** `BoxWithConstraints { ... }` (`BoxWithConstraintsScope` extensions).

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

BoxWithConstraints(Modifier.fillMaxWidth()) {
    val fontSize = autoResizeTextSp(
        text = "Long title",
        minSp = 12,
        maxSp = 28,
        stepSp = 1,
        maxLines = 2,
    )
    Text("Long title", fontSize = fontSize, maxLines = 2)
}
```

**Views / non-Compose Kotlin:** `com.appdimens.dynamic.code.resize.DimenResize` — pixel APIs (`rangePx`, `fittingPx`) on the same `core` math.

---

## `ResizeBound` and helpers (`core/ResizeBound.kt`)

Use these when an overload takes `ResizeBound` for **min** / **max** / **step** (screen % or fixed dp/sp).

| API | Meaning | Example |
|-----|---------|---------|
| `resizeFixedDp(dp: Float)` | Fixed logical dp | `min = resizeFixedDp(12f)` |
| `resizeFixedSp(sp: Float)` | Fixed sp (uses font scale → px) | `step = resizeFixedSp(0.5f)` |
| `resizePercentSw(percent)` | % of `smallestScreenWidthDp` | `max = resizePercentSw(80f)` |
| `resizePercentW(percent)` | % of `screenWidthDp` | `max = resizePercentW(50f)` |
| `resizePercentH(percent)` | % of `screenHeightDp` | `min = resizePercentH(2f)` |

**Example (text range from screen height %):**

```kotlin
import com.appdimens.dynamic.core.resizeFixedSp
import com.appdimens.dynamic.core.resizePercentH

autoResizeTextSp(
    text = "Headline",
    min = resizePercentH(2f),
    max = resizePercentH(12f),
    step = resizeFixedSp(0.5f),
)
```

---

## `AutoResizePercentBasis` (`compose.resize`)

Used by **`autoResizeTextSpPercent`** to choose which **inner** box edge defines 0–100%:

| Enum | Basis |
|------|--------|
| `HEIGHT` | Inner height (after `contentPadding`) |
| `WIDTH` | Inner width |
| `MIN_SIDE` | `min(inner width, inner height)` |

---

## Compose API reference — every `autoResize*` overload

All are `BoxWithConstraintsScope` extensions — call **only** inside `BoxWithConstraints { }`.

### Square side (`Dp` / `Number` as dp)

| Signature | Purpose |
|-----------|---------|
| `autoResizeSquareSize(min: Dp, max: Dp, step: Dp = 2.dp, contentPadding: PaddingValues? = null, contentPaddingUniformDp: Int? = null)` | Largest square side fitting `min(inner W, inner H)` |
| `autoResizeSquareSize(min: Number, max: Number, step: Number = 2, …)` | Same; numbers treated as **dp** |
| `autoResizeSquareSize(min: ResizeBound, max: ResizeBound, step: ResizeBound = resizeFixedDp(1f), contentPadding, contentPaddingUniformDp)` | Min/max/step from screen % or fixed bounds |

**Example:** `Modifier.size(autoResizeSquareSize(12, 96, step = 4, contentPaddingUniformDp = 8))`

### Square — box %

| Signature | Purpose |
|-----------|---------|
| `autoResizeSquareSizePercent(minPercent, maxPercent, step: Dp = 2.dp, contentPadding?, contentPaddingUniformDp?)` | 0–100% of `min(inner width, inner height)` |

**Example:** `autoResizeSquareSizePercent(10f, 80f, step = 2.dp)`

### Width / height (fixed dp)

| Signature | Purpose |
|-----------|---------|
| `autoResizeWidthSize(min: Dp, max: Dp, step: Dp = 2.dp, contentPadding: PaddingValues = 0)` | Largest width ≤ inner width |
| `autoResizeWidthSize(min: Number, max: Number, step: Number = 2, …)` | Number = dp |
| `autoResizeHeightSize(min: Dp, max: Dp, …)` | Largest height ≤ inner height |
| `autoResizeHeightSize(min: Number, max: Number, …)` | Number = dp |

**Example:** `Modifier.width(autoResizeWidthSize(24.dp, maxWidth, 4.dp, contentPadding = PaddingValues(horizontal = 6.dp)))`

### Width / height — box %

| Signature | Purpose |
|-----------|---------|
| `autoResizeWidthSizePercent(minPercent, maxPercent, step: Dp = 2.dp, contentPadding)` | % of inner **width** |
| `autoResizeHeightSizePercent(minPercent, maxPercent, step: Dp = 2.dp, contentPadding)` | % of inner **height** |

### Width / height — `ResizeBound`

| Signature | Purpose |
|-----------|---------|
| `autoResizeWidthSize(min: ResizeBound, max: ResizeBound, step: ResizeBound = resizeFixedDp(1f), contentPadding)` | Screen % or fixed dp on width axis |
| `autoResizeHeightSize(min: ResizeBound, max: ResizeBound, step: ResizeBound = resizeFixedDp(1f), contentPadding)` | Same for height |

### Text — `autoResizeTextSp`

| Signature | Notes |
|-----------|--------|
| `(text, min: ResizeBound, max: ResizeBound, step: ResizeBound = resizeFixedSp(1f), style, maxLines, maxLength, softWrap, overflow, contentPadding)` | Full control; use `ResizeBound` for screen % |
| `(text, minSp: Number, maxSp: Number, stepSp: Number = 2, …)` | Numbers = **sp** |
| `(text, minSp: TextUnit, maxSp: TextUnit, stepSp: TextUnit = 2.sp, …)` | Explicit `sp` |
| `(text, minSp: Dp, maxSp: Dp, stepSp: Dp = 2.dp, …)` | **Numeric value** of dp used as sp (see KDoc) |

**Shared parameters:** `style` `null` → `LocalTextStyle.current`; `maxLines` / `maxLength` `null`, `≤ 0`, or `-1` → no cap / full string; if `maxLength` truncates, use the same prefix in `Text`.

### Text — box %

| Signature | Purpose |
|-----------|---------|
| `autoResizeTextSpPercent(text, minPercent, maxPercent, stepSp: Number = 2, percentBasis: AutoResizePercentBasis = HEIGHT, …)` | Font size range from % of inner edge |

**Example:** `autoResizeTextSpPercent("Title", 4f, 28f, stepSp = 1, percentBasis = AutoResizePercentBasis.HEIGHT)`

## Why use it

When size **cannot** be a closed formula from `Configuration` alone: it depends on the **actual** parent composable space, text, `maxLines`, padding, etc.

## When to use it

- Titles that should use **full** available width without premature `…`.
- Icons or squares that should be as **large as possible** inside a card.
- Layouts with varying aspect where fixed tokens fail.

## Advantages and trade-offs

- **Pros:** fit-by-construction (if the predicate is correct); works with real constraints.
- **Cons:** cost of **multiple text measurements** or search steps — keep `step` reasonable; does not replace global design tokens for the rest of the app.

## Recommended usage strategy

Use **resize** on **isolated** components (cells, dynamic headers, thumbs). Keep **scaled** (or another strategy) for grids, margins, and typography **not** tied to a specific parent rectangle. Validate with `maxLines` and `contentPadding` as in the README.

[Back to index](README.md)
