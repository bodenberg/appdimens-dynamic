# Resize module (`compose.resize` / `code.resize`)

## What it is

**Resize** is not a global scale curve like **scaled**: it is a set of **composables and helpers** that pick the **largest** size in a `[min, max]` range with **step** granularity that still **fits** inside the constraints from `BoxWithConstraints` (max width/height in dp). Typical uses are **auto-sized text** (similar to `TextView` auto-size) and **box sizes** (square, width, height) that should use space without overflowing.

## Calculation used

- Build a sequence of candidate **pixels** from `minPx` to `maxPx` with `stepPx` granularity (`buildResizeStepsPx` in `ResizeMath.kt`).
- **Binary search** (`findLargestFittingResizePx`) finds the largest candidate for which a “fits” predicate is true.
- For Compose text, each **sp** candidate is measured with `rememberTextMeasurer` and `Constraints(maxWidth, maxHeight)` aligned to local style; only `fontSize` is swept.
- Variants with **box-local percent** (`autoResize*Percent`) and **`ResizeBound`** for **screen-axis** percent (sw / w / h) or fixed dp/sp — see the main README and `ResizeBound.kt`.

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
