# Resize module (`compose.resize` / `code.resize`)

This module is **not** prefix-mirrored like sdp/hdp strategies; the full Compose list is under **§ Compose API reference** below. For `sdp`-style extensions, see [COMPOSE-API-CONVENTIONS.md](COMPOSE-API-CONVENTIONS.md).

## What it is

**Resize** is not a global scale curve like **scaled**: it is a set of **composables and helpers** that pick the **largest** size in a `[min, max]` range with **step** granularity that still **fits** inside the constraints from `BoxWithConstraints` (max width/height in dp). Typical uses are **auto-sized text** (similar to `TextView` auto-size) and **box sizes** (square, width, height) that should use space without overflowing.

## Calculation used

- Build a table of candidate **pixels** from `minPx` to `maxPx` with `stepPx` granularity (`buildResizeStepsPx` in `ResizeMath.kt` — implemented with a pre-sized **`FloatArray`**, no boxed `Float` allocations).
- **Binary search** (`findLargestFittingResizePx`) finds the largest candidate for which a “fits” predicate is true. If **no** candidate satisfies `fits`, the result is **`0f`** (including when the step table has a **single** element that does not fit).
- For Compose text, each **sp** candidate is measured with `rememberTextMeasurer` and `Constraints(maxWidth, maxHeight)` aligned to local style; only `fontSize` is swept.
- Variants with **box-local percent** (`autoResize*Percent`) and **`ResizeBound`** for **screen-axis** percent (sw / w / h) or fixed dp/sp — see the main README and `ResizeBound.kt`. **`ResizeBound.resolveToPx`** requires **`density > 0`** and clamps negative dp/sp and out-of-range percent inputs to safe bounds.

`DimenCache.CalcType.RESIZE` tags related cache entries where applicable; core logic lives in the **resize** module, not `calculateRawScaling`.

## How to use

Call these **inside** `BoxWithConstraints { ... }` (`BoxWithConstraintsScope` extensions). See **[Usage examples](#usage-examples)** below for typography, `TextStyle`, and full composables (`Card`, `Image`, …).

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

## Usage examples

All snippets assume `import androidx.compose.foundation.layout.*` and `import com.appdimens.dynamic.compose.resize.*` where needed.

### Text — same `TextStyle` for measure and draw

`autoResizeTextSp` sweeps **only** `fontSize`; everything else (**font weight**, **letter spacing**, **line height**, **font family**, etc.) comes from the `style` you pass (or from `LocalTextStyle.current` when `style` is `null`). **Use the same base style on `Text`** and override `fontSize` with the returned value so layout matches measurement.

**Material 3 typography + extra weight**

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

@Composable
fun AutoResizeHeadline(title: String, modifier: Modifier = Modifier) {
    val baseStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val fontSize = autoResizeTextSp(
            text = title,
            minSp = 14,
            maxSp = 34,
            stepSp = 1,
            style = baseStyle,
            maxLines = 2,
        )
        Text(
            text = title,
            style = baseStyle,
            fontSize = fontSize,
            maxLines = 2,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
```

**Custom `TextStyle` (letter spacing, line height)**

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

@Composable
fun PromoBanner() {
    val promoStyle = TextStyle(
        letterSpacing = 0.02.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold,
    )
    BoxWithConstraints(Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
        val body = "Limited offer — fits this slot."
        val fontSize = autoResizeTextSp(
            text = body,
            minSp = 10,
            maxSp = 20,
            stepSp = 0.5f,
            style = promoStyle,
            maxLines = 2,
            contentPadding = PaddingValues(horizontal = 8.dp),
        )
        Text(
            text = body,
            style = promoStyle,
            fontSize = fontSize,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}
```

**Ellipsis / no wrap — match `Text` flags**

If `Text` uses `maxLines = 1`, `overflow = TextOverflow.Ellipsis`, or `softWrap = false`, pass the **same** values into `autoResizeTextSp` so the binary search uses the same layout rules.

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

@Composable
fun SingleLineEllipsisSample() {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val line = "One line only with ellipsis when too long…………"
        val fontSize = autoResizeTextSp(
            text = line,
            minSp = 8,
            maxSp = 18,
            stepSp = 1,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = line,
            fontSize = fontSize,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
```

**Scaled min/max (`ssp` / library sp extensions)**

You can use `TextUnit` overloads so bounds follow the same scaling strategy as the rest of the screen:

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.resize.autoResizeTextSp
import com.appdimens.dynamic.compose.ssp

@Composable
fun ScaledBoundsSample() {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val t = "Dynamic range from scaled sp"
        val fontSize = autoResizeTextSp(
            text = t,
            minSp = 10.ssp,
            maxSp = 22.ssp,
            stepSp = 1.sp,
            maxLines = 2,
        )
        Text(t, fontSize = fontSize, maxLines = 2)
    }
}
```

### Components — `Card` slot with auto title

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

@Composable
fun MetricCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(),
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            BoxWithConstraints(Modifier.fillMaxWidth().heightIn(min = 40.dp, max = 72.dp)) {
                val valueStyle = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                val fontSize = autoResizeTextSp(
                    text = value,
                    minSp = 18,
                    maxSp = 48,
                    stepSp = 2,
                    style = valueStyle,
                    maxLines = 1,
                )
                Text(
                    text = value,
                    style = valueStyle,
                    fontSize = fontSize,
                    maxLines = 1,
                )
            }
        }
    }
}
```

### Components — square `Image` inside a fixed box

`autoResizeSquareSize` returns the largest side (in `dp`) that fits `min(inner width, inner height)`. Pair it with `Image` / `Icon` / a clipped `Box`.

```kotlin
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.appdimens.dynamic.compose.resize.autoResizeSquareSize

@Composable
fun AdaptiveThumbnail(drawableRes: Int, modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier
            .aspectRatio(1f)
            .fillMaxWidth()
    ) {
        val side = autoResizeSquareSize(
            min = 32,
            max = 200,
            step = 4,
            contentPaddingUniformDp = 8,
        )
        Image(
            painter = painterResource(drawableRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.Center)
                .size(side),
        )
    }
}
```

### Components — width / height bars in a row

Same pattern as the sample app: two `BoxWithConstraints` siblings — one drives **width**, the other **height**.

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.resize.autoResizeHeightSize
import com.appdimens.dynamic.compose.resize.autoResizeWidthSize

@Composable
fun WidthHeightBarsSample() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        BoxWithConstraints(
            Modifier
                .weight(1f)
                .height(64.dp)
        ) {
            val w = autoResizeWidthSize(min = 16.dp, max = maxWidth, step = 4.dp)
            Box(
                Modifier
                    .align(Alignment.Center)
                    .width(w)
                    .height(32.dp)
                    .background(Color(0xFF1565C0), RoundedCornerShape(6.dp)),
            )
        }
        BoxWithConstraints(
            Modifier
                .weight(1f)
                .height(64.dp)
        ) {
            val h = autoResizeHeightSize(min = 12, max = 48, step = 4)
            Box(
                Modifier
                    .align(Alignment.Center)
                    .width(36.dp)
                    .height(h)
                    .background(Color(0xFF2E7D32), RoundedCornerShape(6.dp)),
            )
        }
    }
}
```

### Percent-of-box text

Font size range expressed as **0–100%** of the inner box edge (see [AutoResizePercentBasis](#autoresizepercentbasis-core) below):

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.core.AutoResizePercentBasis
import com.appdimens.dynamic.compose.resize.autoResizeTextSpPercent

@Composable
fun PercentBoxTextSample() {
    BoxWithConstraints(Modifier.fillMaxWidth().height(120.dp).padding(8.dp)) {
        val title = "Percent-based range"
        val fontSize = autoResizeTextSpPercent(
            text = title,
            minPercent = 5f,
            maxPercent = 35f,
            stepSp = 1,
            percentBasis = AutoResizePercentBasis.MIN_SIDE,
            maxLines = 2,
        )
        Text(title, fontSize = fontSize, maxLines = 2)
    }
}
```

Working demos: `app/src/main/java/com/example/app/compose/ExampleActivity.kt` (auto-resize section).

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

## `AutoResizePercentBasis` (`core`)

The enum lives in **`com.appdimens.dynamic.core.AutoResizePercentBasis`** — that is the type to import.

Used by **`autoResizeTextSpPercent`** (Compose) and **`DimenResize.rangePxPercentOfInnerBox`** / **`fittingTextSpPercentPx`** (Views/code) to choose which **inner** box edge defines 0–100%:

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
