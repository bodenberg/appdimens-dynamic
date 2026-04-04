# Physical units (`compose` + `code.units`)

## What it is

Conversion of **real-world** measures (millimeters, centimeters, inches) using `DisplayMetrics` / `Resources`. It does **not** follow the “300 dp axis” scaling model; use when you need **approximate physical size** on the device.

## Calculation used

- **`toMm(mm, resources, context?)`**: `TypedValue.COMPLEX_UNIT_MM` divided by **`density`** → **dp** as `Float`; may use **`DimenCache`** (`CalcType.UNITIES`).
- **`toCm(cm, resources, context?)`**: same pattern as `toMm` via mm (`cm × 10`) → **dp** `Float` (cached like `toMm`).
- **`toInch(inches, resources, context?)`**: `TypedValue.COMPLEX_UNIT_IN` divided by **`density`** → **dp** `Float` (cached like `toMm`).
- **Composable** `Float` / `Int` **`.mm`**, **`.cm`**, **`.inch`**: wrap the above with `LocalDensity` / `LocalResources` / `LocalContext`. They return a **`Float` in dp-equivalent units** (not layout pixels). For Compose layout, use **`.dp`** on the result, e.g. `10f.mm.dp`.

Pure helpers (`convertMmToCm`, `inchToMm`, …) are **math-only** between unit labels.

**Code module (`com.appdimens.dynamic.code.units.DimenPhysicalUnits`):**

- **`toPxFromMm` / `Cm` / `Inch`**: direct `applyDimension` → **pixels** (no double conversion).
- **`toDpFromMm` / `Cm` / `Inch`**: physical unit → **dp**.
- **`toSpFromMm` / `Cm` / `Inch`**: via dp and **`scaledDensity`**.
- **`radiusFromDiameter`** / **`radiusFromCircumference`**: radius in **dp** after normalizing the diameter/circumference: **MM / CM / INCH** via `toDp*`, **DP** raw, **SP** as `value × (scaledDensity / density)`, **PX** as `value / density`.

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
|--------------------|-----------|--------|-------------|
| `mm` | `Float`, `Int` | `Float` (dp) | `10f.mm.dp` → `Dp` for `Modifier` |
| `cm` | `Float`, `Int` | `Float` (dp) | `2.5f.cm.dp` |
| `inch` | `Float`, `Int` | `Float` (dp) | `1f.inch.dp` |

**Composable instance helpers** (on `DimenPhysicalUnits` in the same file)

| API | Example |
|-----|---------|
| `Float.radius(type: UnitType)` | `48f.radius(UnitType.MM)` — radius in **dp** |
| `Number.radius(type: UnitType)` | `48.radius(UnitType.DP)` |
| `Float.measureDiameter(isCircumference: Boolean)` | Toggle diameter vs circumference display |
| `Number.measureDiameter(isCircumference: Boolean)` | Same |

**Object `DimenPhysicalUnits` (Compose) — non-composable helpers**

| API | Role | Example |
|-----|------|---------|
| `toMm(mm, resources, context?)` | mm → dp `Float` | `DimenPhysicalUnits.toMm(10f, resources)` |
| `toCm(cm, resources, context?)` | cm → dp `Float` | `DimenPhysicalUnits.toCm(2.5f, resources)` |
| `toInch(inches, resources, context?)` | inch → dp `Float` | `DimenPhysicalUnits.toInch(1f, resources)` |
| `convertMmToCm` / `convertMmToInch` | pure `Float` | `convertMmToCm(100f)` |
| `convertCmToMm` / `convertCmToInch` | pure `Float` | `convertCmToInch(2.54f)` |
| `convertInchToCm` / `convertInchToMm` | pure `Float` | `convertInchToMm(1f)` |
| `Float.mmToCm()`, `Number.mmToCm()`, … | sugar over `convert*` | `5f.mmToInch()` |
| `radius(diameter, type: UnitType, resources)` | half-size in **dp** | `radius(24f, UnitType.MM, resources)` |
| `displayMeasureDiameter(diameter, isCircumference)` | scale for circumference | internal + extensions |
| `unitSizeInDp(type, resources)` | size of **1.0** logical unit in **dp** (mm/cm/inch/dp/sp/px normalized to dp) | `unitSizeInDp(UnitType.MM, resources)` |

Use **`UnitType`** (`MM`, `CM`, `INCH`, `SP`, `DP`, `PX`, …) with `radius` and `unitSizeInDp`.

> **Note:** the former name `unitSizePerPx` was renamed to **`unitSizeInDp`** because the values are expressed in **dp**, not raw pixels. If you still see `unitSizePerPx` in an old **KDoc** export under `DOCUMENTATION/KDOC/`, regenerate HTML with Dokka (see [README.md](README.md) in this folder).

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
