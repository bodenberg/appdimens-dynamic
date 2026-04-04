# Physical units (`compose` + `code.units`)

## What it is

Conversion of **real-world** measures (millimeters, centimeters, inches) to **dp** (and px at the code level), using the current `DisplayMetrics` / `Resources` **density**. It does not follow the “300 dp axis” scaling model; use it when the requirement is **approximate physical size** on the device.

## Calculation used

Conversions based on screen dpi (e.g. inches → dp via `xdpi`/`ydpi` or equivalent inside `DimenPhysicalUnits`). In Compose, extensions like `10.mm`, `2.5f.cm`, `1.inch` yield `Dp`. The cache may use `CalcType.UNITIES` for paths that go through the library’s dimension system.

See `DimenPhysicalUnits.kt` under `compose/units` and `code/units` for exact signatures (`toDpFromMm`, `toDpFromCm`, `toDpFromInch`, etc.).

## How to use

**Compose:**

```kotlin
import com.appdimens.dynamic.compose.mm
import com.appdimens.dynamic.compose.cm

Modifier.width(10.mm)
Modifier.height(2.5f.cm)
```

**Code:**

```kotlin
import com.appdimens.dynamic.code.units.DimenPhysicalUnits

val dp = DimenPhysicalUnits.toDpFromCm(2.5f, resources)
```

## Why use it

Specs from **print**, **regulation** (minimum touch target in mm), ruler-based prototyping, or alignment with **physical** materials.

## When to use it

- Buttons with minimum height in **mm**.
- Previews that must match a **cm** mockup.
- When **dp alone** does not speak to stakeholders who think in physical units.

## Advantages and trade-offs

- **Pros:** close to the real world; independent of which scaling strategy you use elsewhere.
- **Cons:** “exact physical size” varies by device (real vs nominal dpi); does not solve **responsive layout** by itself — combine with constraints and **scaled** where needed.

## Recommended usage strategy

Use physical units for **legal / accessibility minimums** and isolated cases; build the main UI **grid** with **scaled** (or another strategy) for consistency across form factors.

[Back to index](README.md)
