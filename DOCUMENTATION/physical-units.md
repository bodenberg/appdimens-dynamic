# Physical units (`compose` + `code.units`)

## What it is

Conversion of **real-world** measures (millimeters, centimeters, inches) using `DisplayMetrics` / `Resources`. It does **not** follow the “300 dp axis” scaling model; use when you need **approximate physical size** on the device.

## Calculation used

- **`toMm(mm, resources, context?)`**: `TypedValue.COMPLEX_UNIT_MM` → **dp** as `Float` (`/ density`); may use **`DimenCache`** (`CalcType.UNITIES`).
- **`toCm(cm, resources)`**: implemented via mm conversion → **px** `Float` from `applyDimension` (see source).
- **`toInch(inches, resources)`**: **px** `Float`.
- **Composable** `Float`/`Int`.`mm` / `.cm` / `.inch`: wrap the above with `LocalDensity` / `LocalResources` — **return `Float`** (physical conversion result); for layout, convert to `Dp` with **`.dp`** where appropriate.

Pure helpers (`convertMmToCm`, `inchToMm`, …) are **math-only** between unit labels.

## How to use

**Compose** (import `com.appdimens.dynamic.compose.mm`, `.cm`, `.inch`):

```kotlin
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.mm
import com.appdimens.dynamic.compose.cm

Modifier.width(10f.mm.dp)
Modifier.height(2.5f.cm.dp)
```

| Composable property | Receivers | Return | Typical use |
|--------------------|-----------|--------|---------------|
| `mm` | `Float`, `Int` | `Float` | `10f.mm.dp` → `Dp` for `Modifier` |
| `cm` | `Float`, `Int` | `Float` | `2.5f.cm.dp` |
| `inch` | `Float`, `Int` | `Float` | `1f.inch.dp` |

**Composable instance helpers** (on `DimenPhysicalUnits` receiver in the same file)

| API | Example |
|-----|---------|
| `Float.radius(type: UnitType)` | `48f.radius(UnitType.MM)` — radius in px |
| `Number.radius(type: UnitType)` | `48.radius(UnitType.DP)` |
| `Float.measureDiameter(isCircumference: Boolean)` | Toggle diameter vs circumference display |
| `Number.measureDiameter(isCircumference: Boolean)` | Same |

**Object `DimenPhysicalUnits` (code / tests)**

| API | Role | Example |
|-----|------|---------|
| `toMm(mm, resources, context?)` | mm → dp `Float` | `DimenPhysicalUnits.toMm(10f, resources)` |
| `toCm(cm, resources)` | cm → px `Float` | `DimenPhysicalUnits.toCm(2.5f, resources)` |
| `toInch(inches, resources)` | inch → px `Float` | `DimenPhysicalUnits.toInch(1f, resources)` |
| `convertMmToCm` / `convertMmToInch` | pure `Float` | `convertMmToCm(100f)` |
| `convertCmToMm` / `convertCmToInch` | pure `Float` | `convertCmToInch(2.54f)` |
| `convertInchToCm` / `convertInchToMm` | pure `Float` | `convertInchToMm(1f)` |
| `Float.mmToCm()`, `Number.mmToCm()`, `Float.mmToInch()`, … | sugar over `convert*` | `5f.mmToInch()` |
| `Float.cmToMm()`, `Number.cmToMm()`, `Float.cmToInch()`, … | sugar | `1f.cmToMm()` |
| `Float.inchToCm()`, `Number.inchToCm()`, `Float.inchToMm()`, … | sugar | `1f.inchToCm()` |
| `radius(diameter, type: UnitType, resources)` | half-size in px | `radius(24f, UnitType.MM, resources)` |
| `displayMeasureDiameter(diameter, isCircumference)` | scale for circumference | internal + extensions |
| `unitSizePerPx(type, resources)` | size of **1.0** unit in px | `unitSizePerPx(UnitType.MM, resources)` |

Use **`UnitType`** (`MM`, `CM`, `INCH`, `SP`, `DP`, …) with `radius` and `unitSizePerPx`.

## Why use it

Specs from **print**, **regulation** (touch target in mm), ruler-based prototyping, or **physical** mockups.

## When to use it

- Minimum tap target in **mm**.
- Matching a **cm** spec.
- When stakeholders reason in **inches** / **mm**, not dp.

## Advantages and trade-offs

- **Pros:** speaks “real world”; orthogonal to scaling strategies.
- **Cons:** real vs nominal dpi varies; not a substitute for **scaled** / breakpoints for layout grids.

## Recommended usage strategy

Use for **legal / a11y minimums** and isolated measurements; build the main **grid** with **scaled** (or another strategy).

[Back to index](README.md)
