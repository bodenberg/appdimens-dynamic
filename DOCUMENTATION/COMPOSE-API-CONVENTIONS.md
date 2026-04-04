# Compose API reference — conventions and scaled module

This document is the **authoritative surface-area reference** for Jetpack Compose extensions in **scaled** mode, plus rules that **every other strategy** (`percent`, `power`, `fluid`, …) mirrors with a different name prefix.

**Source of truth:** `library/src/main/java/com/appdimens/dynamic/compose/`.

---

## 1. Imports

Most Compose extensions live in the top-level package (re-exported from strategy packages):

```kotlin
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp
import com.appdimens.dynamic.compose.ssp
import com.appdimens.dynamic.compose.scaledDp
import com.appdimens.dynamic.compose.scaledSp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
```

Strategy-specific imports (examples):

```kotlin
import com.appdimens.dynamic.compose.percent.psdp
import com.appdimens.dynamic.compose.power.pwsdp
```

---

## 2. Shared behavior (all strategies)

### 2.1 Flags on `Number` properties

| Suffix | Meaning | When to use |
|--------|---------|-------------|
| *(none)* | Default scaling for that axis | Normal layouts |
| `a` | `applyAspectRatio = true` | Non-standard aspect ratios; refined curve |
| `i` | `ignoreMultiWindows = true` | Split-screen: may return **unscaled** base when heuristic matches |
| `ia` | Both `a` and `i` | TV/tablet + multi-window policies together |

### 2.2 `Px` variants

Properties ending in `Px` return **`Float` in px** (layout pixel units for the current density), not `Dp`. Use for Canvas, legacy interop, or custom `Modifier` math.

**Example:** `val stroke = 2f.sdpPx`

### 2.3 Orientation inverters (middle of the name)

These swap which **Configuration** metric is read when orientation matches the inverter rule (see `DimenCalculationPlumbing.effectiveQualifier`).

| Token in name | Typical use |
|---------------|-------------|
| `Ph` | SW-based default → **height** in **portrait** |
| `Lh` | SW-based default → **height** in **landscape** |
| `Pw` | SW-based default → **width** in **portrait** |
| `Lw` | SW-based default → **width** in **landscape** |
| `hdp` + `Lw` / `Pw` | Height axis with PH↔LW / LH↔PW style swaps |
| `wdp` + `Lh` / `Ph` | Width axis with PW↔LH / LW↔PH style swaps |

**Note:** `hdp` / `wdp` blocks expose **fewer** inverter combinations than `sdp` by design (see property tables below).

### 2.4 Low-level composable functions (escape hatch)

```kotlin
fun Number.toDynamicScaledDp(
    qualifier: DpQualifier,
    inverter: Inverter = Inverter.DEFAULT,
    ignoreMultiWindows: Boolean = false,
    applyAspectRatio: Boolean = false,
    customSensitivityK: Float? = null,
): Dp

fun Number.toDynamicScaledPx(...): Float
fun Number.toDynamicScaledSp(...): TextUnit  // also supports fontScale, inverter, etc.
```

Use when you need a combination that is not exposed as a `val` shortcut.

---

## 3. Strategy prefix map (mirror of scaled)

Replace the **bold** stem to get the same API shape in another package:

| Strategy | Package | Dp stem (SW / H / W) | Sp stem (scaled font) | “Fixed” Sp (no font scale) |
|----------|---------|----------------------|------------------------|-----------------------------|
| **scaled** | `compose` | `sdp` / `hdp` / `wdp` | `ssp` / `hsp` / `wsp` | `nem` / `hem` / `wem` |
| **percent** | `compose.percent` | `psdp` / `phdp` / `pwdp` | `pssp` / `phsp` / `pwsp` | `pnem` / `phem` / `pwem` |
| **power** | `compose.power` | `pwsdp` / `pwhdp` / `pwwdp` | `pwssp` / `pwhsp` / `pwwsp` | `pwnem` / `pwhem` / `pwwem` |
| **fluid** | `compose.fluid` | `fsdp` / `fhdp` / `fwdp` | `fssp` / `fhsp` / `fwsp` | `fnem` / `fhem` / `fwem` |
| **auto** | `compose.auto` | `asdp` / `ahdp` / `awdp` | `assp` / `ahsp` / `awsp` | `anem` / `ahem` / `awem` |
| **diagonal** | `compose.diagonal` | `dgsdp` / `dghdp` / `dgwdp` | `dgssp` / `dghsp` / `dgwsp` | `dgnem` / `dghem` / `dgwem` |
| **fill** | `compose.fill` | `flsdp` / `flhdp` / `flwdp` | `flssp` / `flhsp` / `flwsp` | `flnem` / `flhem` / `flwem` |
| **fit** | `compose.fit` | `ftsdp` / `fthdp` / `ftwdp` | `ftssp` / `fthsp` / `ftwsp` | `ftnem` / `fthem` / `ftwem` |
| **interpolated** | `compose.interpolated` | `isdp` / `ihdp` / `iwdp` | `issp` / `ihsp` / `iwsp` | `inem` / `ihem` / `iwem` |
| **logarithmic** | `compose.logarithmic` | `logsdp` / `loghdp` / `logwdp` | `logssp` / `loghsp` / `logwsp` | `lognem` / `loghem` / `logwem` |
| **perimeter** | `compose.perimeter` | `prsdp` / `prhdp` / `prwdp` | `prssp` / `prhsp` / `prwsp` | `prnem` / `prhem` / `prwem` |
| **density** | `compose.density` | `dsdp` / `dhdp` / `dwdp` | `dssp` / `dhsp` / `dwsp` | `dnem` / `dhem` / `dwem` |

**Recommendation:** learn **scaled** first; for other strategies, keep the **same suffix pattern** (`a`, `i`, `ia`, `Px`, `Ph`, …) and only change the prefix column.

---

## 4. Facilitators — Dp (`DimenSdpExtensions.kt`)

All functions are **`@Composable`**. They combine a **base** value with **conditional** scaling (rotation, UI mode, sw/h/w thresholds, or both).

**Common optional parameters (where present):**

- `finalQualifierResolver: DpQualifier` — which axis scales the chosen branch (default matches axis: sdp→SW, hdp→HEIGHT, wdp→WIDTH).
- `orientation: Orientation` — usually `LANDSCAPE` or `PORTRAIT` for rotate helpers.
- `ignoreMultiWindows`, `applyAspectRatio`, `customSensitivityK` — same meaning as on `Number` properties.

### 4.1 Rotation family

| Function | Receiver | Returns | What it does | Example |
|----------|----------|---------|--------------|---------|
| `sdpRotate` | `Int` | `Dp` | Base `Int` as sdp unless `orientation` matches → then `rotationValue` scaled with `finalQualifierResolver` | `80.sdpRotate(50, Orientation.LANDSCAPE)` |
| `sdpRotatePx` | `Int` | `Float` | Same, px | `80.sdpRotatePx(50)` |
| `sdpRotate` | `Dp` | `Dp` | **Always** scales the active branch: uses scaled receiver in default orientation, scaled `rotationValue` when `orientation` matches | `30.dp.sdpRotate(50, orientation = Orientation.LANDSCAPE)` |
| `sdpRotatePx` | `Dp` | `Float` | px variant | `30.dp.sdpRotatePx(50)` |
| `sdpRotatePlain` | `Dp` | `Dp` | **Plain** fallback: portrait uses raw `Dp` of receiver; landscape scales `rotationValue` | `16.sdp.sdpRotatePlain(50)` |
| `sdpRotatePlainPx` | `Dp` | `Float` | px | `16.sdp.sdpRotatePlainPx(50)` |

Repeat the same **name pattern** for **`hdpRotate*`** and **`wdpRotate*`** (defaults: `finalQualifierResolver` = HEIGHT or WIDTH).

### 4.2 UI mode family (`UiModeType`)

| Function | Receiver | Example |
|----------|----------|---------|
| `sdpMode` / `sdpModePx` | `Int` | `30.sdpMode(200, UiModeType.TELEVISION)` |
| `sdpMode` / `sdpModePx` | `Dp` | `30.dp.sdpMode(200, UiModeType.TELEVISION)` |
| `sdpModePlain` / `sdpModePlainPx` | `Dp` | Plain branch when not in target mode |

Same for **`hdpMode*`**, **`wdpMode*`**.

### 4.3 Qualifier threshold family (`DpQualifier` + value)

| Function | Receiver | Example |
|----------|----------|---------|
| `sdpQualifier` / `sdpQualifierPx` | `Number` | `60.sdpQualifier(120, DpQualifier.SMALL_WIDTH, 600)` |
| `sdpQualifier` / `sdpQualifierPx` / `sdpQualifierPlain` / `sdpQualifierPlainPx` | `Dp` | `60.sdp.sdpQualifierPlain(120, DpQualifier.SMALL_WIDTH, 600)` |

Same for **`hdpQualifier*`**, **`wdpQualifier*`**.

### 4.4 Combined screen family (mode + qualifier)

| Function | Receiver | Example |
|----------|----------|---------|
| `sdpScreen` / `sdpScreenPx` | `Number` | `70.sdpScreen(150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)` |
| `sdpScreen` / `sdpScreenPx` / `sdpScreenPlain` / `sdpScreenPlainPx` | `Dp` | `70.sdp.sdpScreenPlain(150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)` |

Same for **`hdpScreen*`**, **`wdpScreen*`**.

**Recommendation:** wrap the tree with `AppDimensProvider` when using mode/fold-related `UiModeType` heavily (see [library/PERFORMANCE.md](../library/PERFORMANCE.md)).

---

## 5. Facilitators — Sp (`DimenSsp.kt` + `DimenSspExtensions.kt`)

### 5.1 `Number` rotation (returns `TextUnit`)

| Function | Example |
|----------|---------|
| `sspRotate` | `16.sspRotate(24, orientation = Orientation.LANDSCAPE)` |
| `hspRotate` | `16.hspRotate(22)` |
| `wspRotate` | `16.wspRotate(20)` |

Optional: `fontScale` (default `true`), `ignoreMultiWindows`, `applyAspectRatio`, `customSensitivityK`.

### 5.2 `Number` px rotation

| Function | Example |
|----------|---------|
| `sspRotatePx` | `val px = 16.sspRotatePx(24)` |

(`hspRotatePx`, `wspRotatePx` same pattern.)

### 5.3 `TextUnit` rotation / mode / qualifier / screen

For **each** axis (`ssp`, `hsp`, `wsp`) the module provides composable extensions on **`TextUnit`**:

- `sspRotate`, `sspRotatePx`, `sspRotatePlain`, `sspRotatePlainPx`
- `sspMode`, `sspModePx`, `sspModePlain`, `sspModePlainPx`
- `sspQualifier`, `sspQualifierPx`, `sspQualifierPlain`, `sspQualifierPlainPx`
- `sspScreen`, `sspScreenPx`, `sspScreenPlain`, `sspScreenPlainPx`

**Examples:**

```kotlin
Text("A", fontSize = 16.ssp.sspRotatePlain(24))
Text("B", fontSize = 16.sspMode(40, UiModeType.TELEVISION))
Text("C", fontSize = 16.ssp.sspQualifier(24, DpQualifier.SMALL_WIDTH, 600))
Text("D", fontSize = 16.ssp.sspScreen(32, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600))
```

`Number` also exposes **`sspQualifier`**, **`sspScreen`** (+ `Px` variants) where implemented; **`sspMode`** on `Number` is **not** present — use `TextUnit` or property `16.ssp` then `sspMode` on the result, or `sspModePx` on `Number` if you need px.

---

## 6. Builders — `DimenScaled` / `ScaledSp`

### 6.1 Entry points

```kotlin
val chain = 16.scaledDp()   // or: 16.dp.scaledDp()
val spChain = 16.scaledSp()
```

### 6.2 Fluent configuration

| Method | Purpose | Example |
|--------|---------|---------|
| `ignoreMultiWindows(Boolean)` | Split-screen heuristic | `.ignoreMultiWindows(true)` |
| `aspectRatio(Boolean, sensitivityK?)` | AR scaling | `.aspectRatio(true)` |

### 6.3 `screen(...)` overloads (priority order)

Entries are sorted by **priority** (1 = most specific) and **qualifier value** (larger sw first).

| Priority | Overload idea | Example |
|----------|---------------|---------|
| 1 | `UiModeType` + `DpQualifier` + threshold | `.screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600, customValue = 40)` |
| 2 | `UiModeType` only | `.screen(UiModeType.TELEVISION, 40)` |
| 3 | `DpQualifier` + threshold | `.screen(DpQualifier.SMALL_WIDTH, 600, 24)` |
| 4 | `Orientation` | `.screen(Orientation.LANDSCAPE, 12)` |

Each overload has variants taking `customValue` as `Dp` or `Number`, and optional `finalQualifierResolver`, `inverter`.

### 6.4 Resolution properties (`DimenScaled`)

| Property | Result |
|----------|--------|
| `.sdp` / `.hdp` / `.wdp` | Final `Dp` on that axis |
| `.sdpPx` / `.hdpPx` / `.wdpPx` | Final px `Float` |

**Example:**

```kotlin
val pad = 16.scaledDp()
    .aspectRatio(true)
    .ignoreMultiWindows(true)
    .screen(UiModeType.TELEVISION, 40)
    .screen(DpQualifier.SMALL_WIDTH, 600, 24)
    .sdp
```

### 6.5 Resolution properties (`ScaledSp`)

| Property | Result |
|----------|--------|
| `.ssp` / `.hsp` / `.wsp` | `TextUnit` |
| `.sspPx` / `.hspPx` / `.wspPx` | px `Float` |

---

## Appendix A — Complete `Number` property catalog (scaled)

The following tables list **every** `Number` extension property in **scaled** mode. Other strategies use the **same names after swapping the prefix** (see §3).

### A.1 Scaled — `Number` Dp / px properties (`DimenSdp.kt`)

| Property | Type | Example |
|----------|------|---------|
| `sdp` | `Dp` | `Modifier.padding(16.sdp)` |
| `sdpa` | `Dp` | `Modifier.padding(16.sdpa)` |
| `sdpi` | `Dp` | `Modifier.padding(16.sdpi)` |
| `sdpia` | `Dp` | `Modifier.padding(16.sdpia)` |
| `sdpPx` | `Float` | `val px = 16.sdpPx` |
| `sdpaPx` | `Float` | `val px = 16.sdpaPx` |
| `sdpiPx` | `Float` | `val px = 16.sdpiPx` |
| `sdpiaPx` | `Float` | `val px = 16.sdpiaPx` |
| `sdpPh` | `Dp` | `Modifier.padding(16.sdpPh)` |
| `sdpPha` | `Dp` | `Modifier.padding(16.sdpPha)` |
| `sdpPhi` | `Dp` | `Modifier.padding(16.sdpPhi)` |
| `sdpPhia` | `Dp` | `Modifier.padding(16.sdpPhia)` |
| `sdpPxPh` | `Float` | `val px = 16.sdpPxPh` |
| `sdpPxaPh` | `Float` | `val px = 16.sdpPxaPh` |
| `sdpPxiPh` | `Float` | `val px = 16.sdpPxiPh` |
| `sdpPxiaPh` | `Float` | `val px = 16.sdpPxiaPh` |
| `sdpLh` | `Dp` | `Modifier.padding(16.sdpLh)` |
| `sdpLha` | `Dp` | `Modifier.padding(16.sdpLha)` |
| `sdpLhi` | `Dp` | `Modifier.padding(16.sdpLhi)` |
| `sdpLhia` | `Dp` | `Modifier.padding(16.sdpLhia)` |
| `sdpPxLh` | `Float` | `val px = 16.sdpPxLh` |
| `sdpPxaLh` | `Float` | `val px = 16.sdpPxaLh` |
| `sdpPxiLh` | `Float` | `val px = 16.sdpPxiLh` |
| `sdpPxiaLh` | `Float` | `val px = 16.sdpPxiaLh` |
| `sdpPw` | `Dp` | `Modifier.padding(16.sdpPw)` |
| `sdpPwa` | `Dp` | `Modifier.padding(16.sdpPwa)` |
| `sdpPwi` | `Dp` | `Modifier.padding(16.sdpPwi)` |
| `sdpPwia` | `Dp` | `Modifier.padding(16.sdpPwia)` |
| `sdpPxPw` | `Float` | `val px = 16.sdpPxPw` |
| `sdpPxaPw` | `Float` | `val px = 16.sdpPxaPw` |
| `sdpPxiPw` | `Float` | `val px = 16.sdpPxiPw` |
| `sdpPwiaPx` | `Float` | `val px = 16.sdpPwiaPx` |
| `sdpLw` | `Dp` | `Modifier.padding(16.sdpLw)` |
| `sdpLwa` | `Dp` | `Modifier.padding(16.sdpLwa)` |
| `sdpLwi` | `Dp` | `Modifier.padding(16.sdpLwi)` |
| `sdpLwia` | `Dp` | `Modifier.padding(16.sdpLwia)` |
| `sdpPxLw` | `Float` | `val px = 16.sdpPxLw` |
| `sdpPxaLw` | `Float` | `val px = 16.sdpPxaLw` |
| `sdpPxiLw` | `Float` | `val px = 16.sdpPxiLw` |
| `sdpPxiaLw` | `Float` | `val px = 16.sdpPxiaLw` |
| `hdp` | `Dp` | `Modifier.padding(16.hdp)` |
| `hdpa` | `Dp` | `Modifier.padding(16.hdpa)` |
| `hdpi` | `Dp` | `Modifier.padding(16.hdpi)` |
| `hdpia` | `Dp` | `Modifier.padding(16.hdpia)` |
| `hdpPx` | `Float` | `val px = 16.hdpPx` |
| `hdpaPx` | `Float` | `val px = 16.hdpaPx` |
| `hdpiPx` | `Float` | `val px = 16.hdpiPx` |
| `hdpiaPx` | `Float` | `val px = 16.hdpiaPx` |
| `hdpLw` | `Dp` | `Modifier.padding(16.hdpLw)` |
| `hdpLwa` | `Dp` | `Modifier.padding(16.hdpLwa)` |
| `hdpLwi` | `Dp` | `Modifier.padding(16.hdpLwi)` |
| `hdpLwia` | `Dp` | `Modifier.padding(16.hdpLwia)` |
| `hdpPxLw` | `Float` | `val px = 16.hdpPxLw` |
| `hdpPxaLw` | `Float` | `val px = 16.hdpPxaLw` |
| `hdpPxiLw` | `Float` | `val px = 16.hdpPxiLw` |
| `hdpPxiaLw` | `Float` | `val px = 16.hdpPxiaLw` |
| `hdpPw` | `Dp` | `Modifier.padding(16.hdpPw)` |
| `hdpPwa` | `Dp` | `Modifier.padding(16.hdpPwa)` |
| `hdpPwi` | `Dp` | `Modifier.padding(16.hdpPwi)` |
| `hdpPwia` | `Dp` | `Modifier.padding(16.hdpPwia)` |
| `hdpPxPw` | `Float` | `val px = 16.hdpPxPw` |
| `hdpPxaPw` | `Float` | `val px = 16.hdpPxaPw` |
| `hdpPxiPw` | `Float` | `val px = 16.hdpPxiPw` |
| `hdpPxiaPw` | `Float` | `val px = 16.hdpPxiaPw` |
| `wdp` | `Dp` | `Modifier.padding(16.wdp)` |
| `wdpa` | `Dp` | `Modifier.padding(16.wdpa)` |
| `wdpi` | `Dp` | `Modifier.padding(16.wdpi)` |
| `wdpia` | `Dp` | `Modifier.padding(16.wdpia)` |
| `wdpPx` | `Float` | `val px = 16.wdpPx` |
| `wdpaPx` | `Float` | `val px = 16.wdpaPx` |
| `wdpiPx` | `Float` | `val px = 16.wdpiPx` |
| `wdpiaPx` | `Float` | `val px = 16.wdpiaPx` |
| `wdpLh` | `Dp` | `Modifier.padding(16.wdpLh)` |
| `wdpLha` | `Dp` | `Modifier.padding(16.wdpLha)` |
| `wdpLhi` | `Dp` | `Modifier.padding(16.wdpLhi)` |
| `wdpLhia` | `Dp` | `Modifier.padding(16.wdpLhia)` |
| `wdpPxLh` | `Float` | `val px = 16.wdpPxLh` |
| `wdpPxaLh` | `Float` | `val px = 16.wdpPxaLh` |
| `wdpPxiLh` | `Float` | `val px = 16.wdpPxiLh` |
| `wdpPxiaLh` | `Float` | `val px = 16.wdpPxiaLh` |
| `wdpPh` | `Dp` | `Modifier.padding(16.wdpPh)` |
| `wdpPha` | `Dp` | `Modifier.padding(16.wdpPha)` |
| `wdpPhi` | `Dp` | `Modifier.padding(16.wdpPhi)` |
| `wdpPhia` | `Dp` | `Modifier.padding(16.wdpPhia)` |
| `wdpPxPh` | `Float` | `val px = 16.wdpPxPh` |
| `wdpPxaPh` | `Float` | `val px = 16.wdpPxaPh` |
| `wdpPxiPh` | `Float` | `val px = 16.wdpPxiPh` |
| `wdpPxiaPh` | `Float` | `val px = 16.wdpPxiaPh` |

### A.2 Scaled — `Number` Sp / px properties (`DimenSsp.kt`)

| Property | Type | Example |
|----------|------|---------|
| `ssp` | `TextUnit` | `Text("Hi", fontSize = 16.ssp)` |
| `sspa` | `TextUnit` | `Text("Hi", fontSize = 16.sspa)` |
| `sspi` | `TextUnit` | `Text("Hi", fontSize = 16.sspi)` |
| `sspia` | `TextUnit` | `Text("Hi", fontSize = 16.sspia)` |
| `sspPx` | `Float` | `val px = 16.sspPx` |
| `sspPxa` | `Float` | `val px = 16.sspPxa` |
| `sspPxi` | `Float` | `val px = 16.sspPxi` |
| `sspPxia` | `Float` | `val px = 16.sspPxia` |
| `nem` | `TextUnit` | `Text("Hi", fontSize = 16.nem)` |
| `nema` | `TextUnit` | `Text("Hi", fontSize = 16.nema)` |
| `nemi` | `TextUnit` | `Text("Hi", fontSize = 16.nemi)` |
| `nemia` | `TextUnit` | `Text("Hi", fontSize = 16.nemia)` |
| `nemPx` | `Float` | `val px = 16.nemPx` |
| `nemPxa` | `Float` | `val px = 16.nemPxa` |
| `nemPxi` | `Float` | `val px = 16.nemPxi` |
| `nemPxia` | `Float` | `val px = 16.nemPxia` |
| `sspPh` | `TextUnit` | `Text("Hi", fontSize = 16.sspPh)` |
| `sspPha` | `TextUnit` | `Text("Hi", fontSize = 16.sspPha)` |
| `sspPhi` | `TextUnit` | `Text("Hi", fontSize = 16.sspPhi)` |
| `sspPhia` | `TextUnit` | `Text("Hi", fontSize = 16.sspPhia)` |
| `sspPxPh` | `Float` | `val px = 16.sspPxPh` |
| `sspPxaPh` | `Float` | `val px = 16.sspPxaPh` |
| `sspPxiPh` | `Float` | `val px = 16.sspPxiPh` |
| `sspPxiaPh` | `Float` | `val px = 16.sspPxiaPh` |
| `sspLh` | `TextUnit` | `Text("Hi", fontSize = 16.sspLh)` |
| `sspLha` | `TextUnit` | `Text("Hi", fontSize = 16.sspLha)` |
| `sspLhi` | `TextUnit` | `Text("Hi", fontSize = 16.sspLhi)` |
| `sspLhia` | `TextUnit` | `Text("Hi", fontSize = 16.sspLhia)` |
| `sspPxLh` | `Float` | `val px = 16.sspPxLh` |
| `sspPxaLh` | `Float` | `val px = 16.sspPxaLh` |
| `sspPxiLh` | `Float` | `val px = 16.sspPxiLh` |
| `sspPxiaLh` | `Float` | `val px = 16.sspPxiaLh` |
| `sspPw` | `TextUnit` | `Text("Hi", fontSize = 16.sspPw)` |
| `sspPwa` | `TextUnit` | `Text("Hi", fontSize = 16.sspPwa)` |
| `sspPwi` | `TextUnit` | `Text("Hi", fontSize = 16.sspPwi)` |
| `sspPwia` | `TextUnit` | `Text("Hi", fontSize = 16.sspPwia)` |
| `sspPxPw` | `Float` | `val px = 16.sspPxPw` |
| `sspPxaPw` | `Float` | `val px = 16.sspPxaPw` |
| `sspPxiPw` | `Float` | `val px = 16.sspPxiPw` |
| `sspPxiaPw` | `Float` | `val px = 16.sspPxiaPw` |
| `sspLw` | `TextUnit` | `Text("Hi", fontSize = 16.sspLw)` |
| `sspLwa` | `TextUnit` | `Text("Hi", fontSize = 16.sspLwa)` |
| `sspLwi` | `TextUnit` | `Text("Hi", fontSize = 16.sspLwi)` |
| `sspLwia` | `TextUnit` | `Text("Hi", fontSize = 16.sspLwia)` |
| `sspPxLw` | `Float` | `val px = 16.sspPxLw` |
| `sspPxaLw` | `Float` | `val px = 16.sspPxaLw` |
| `sspPxiLw` | `Float` | `val px = 16.sspPxiLw` |
| `sspPxiaLw` | `Float` | `val px = 16.sspPxiaLw` |
| `hsp` | `TextUnit` | `Text("Hi", fontSize = 16.hsp)` |
| `hspa` | `TextUnit` | `Text("Hi", fontSize = 16.hspa)` |
| `hspi` | `TextUnit` | `Text("Hi", fontSize = 16.hspi)` |
| `hspia` | `TextUnit` | `Text("Hi", fontSize = 16.hspia)` |
| `hspPx` | `Float` | `val px = 16.hspPx` |
| `hspPxa` | `Float` | `val px = 16.hspPxa` |
| `hspPxi` | `Float` | `val px = 16.hspPxi` |
| `hspPxia` | `Float` | `val px = 16.hspPxia` |
| `hem` | `TextUnit` | `Text("Hi", fontSize = 16.hem)` |
| `hema` | `TextUnit` | `Text("Hi", fontSize = 16.hema)` |
| `hemi` | `TextUnit` | `Text("Hi", fontSize = 16.hemi)` |
| `hemia` | `TextUnit` | `Text("Hi", fontSize = 16.hemia)` |
| `hemPx` | `Float` | `val px = 16.hemPx` |
| `hemPxa` | `Float` | `val px = 16.hemPxa` |
| `hemPxi` | `Float` | `val px = 16.hemPxi` |
| `hemPxia` | `Float` | `val px = 16.hemPxia` |
| `hspLw` | `TextUnit` | `Text("Hi", fontSize = 16.hspLw)` |
| `hspLwa` | `TextUnit` | `Text("Hi", fontSize = 16.hspLwa)` |
| `hspLwi` | `TextUnit` | `Text("Hi", fontSize = 16.hspLwi)` |
| `hspLwia` | `TextUnit` | `Text("Hi", fontSize = 16.hspLwia)` |
| `hspPxLw` | `Float` | `val px = 16.hspPxLw` |
| `hspPxaLw` | `Float` | `val px = 16.hspPxaLw` |
| `hspPxiLw` | `Float` | `val px = 16.hspPxiLw` |
| `hspPxiaLw` | `Float` | `val px = 16.hspPxiaLw` |
| `hspPw` | `TextUnit` | `Text("Hi", fontSize = 16.hspPw)` |
| `hspPwa` | `TextUnit` | `Text("Hi", fontSize = 16.hspPwa)` |
| `hspPwi` | `TextUnit` | `Text("Hi", fontSize = 16.hspPwi)` |
| `hspPwia` | `TextUnit` | `Text("Hi", fontSize = 16.hspPwia)` |
| `hspPxPw` | `Float` | `val px = 16.hspPxPw` |
| `hspPxaPw` | `Float` | `val px = 16.hspPxaPw` |
| `hspPxiPw` | `Float` | `val px = 16.hspPxiPw` |
| `hspPxiaPw` | `Float` | `val px = 16.hspPxiaPw` |
| `wsp` | `TextUnit` | `Text("Hi", fontSize = 16.wsp)` |
| `wspa` | `TextUnit` | `Text("Hi", fontSize = 16.wspa)` |
| `wspi` | `TextUnit` | `Text("Hi", fontSize = 16.wspi)` |
| `wspia` | `TextUnit` | `Text("Hi", fontSize = 16.wspia)` |
| `wspPx` | `Float` | `val px = 16.wspPx` |
| `wspPxa` | `Float` | `val px = 16.wspPxa` |
| `wspPxi` | `Float` | `val px = 16.wspPxi` |
| `wspPxia` | `Float` | `val px = 16.wspPxia` |
| `wem` | `TextUnit` | `Text("Hi", fontSize = 16.wem)` |
| `wema` | `TextUnit` | `Text("Hi", fontSize = 16.wema)` |
| `wemi` | `TextUnit` | `Text("Hi", fontSize = 16.wemi)` |
| `wemia` | `TextUnit` | `Text("Hi", fontSize = 16.wemia)` |
| `wemPx` | `Float` | `val px = 16.wemPx` |
| `wemPxa` | `Float` | `val px = 16.wemPxa` |
| `wemPxi` | `Float` | `val px = 16.wemPxi` |
| `wemPxia` | `Float` | `val px = 16.wemPxia` |
| `wspLh` | `TextUnit` | `Text("Hi", fontSize = 16.wspLh)` |
| `wspLha` | `TextUnit` | `Text("Hi", fontSize = 16.wspLha)` |
| `wspLhi` | `TextUnit` | `Text("Hi", fontSize = 16.wspLhi)` |
| `wspLhia` | `TextUnit` | `Text("Hi", fontSize = 16.wspLhia)` |
| `wspPxLh` | `Float` | `val px = 16.wspPxLh` |
| `wspPxaLh` | `Float` | `val px = 16.wspPxaLh` |
| `wspPxiLh` | `Float` | `val px = 16.wspPxiLh` |
| `wspPxiaLh` | `Float` | `val px = 16.wspPxiaLh` |
| `wspPh` | `TextUnit` | `Text("Hi", fontSize = 16.wspPh)` |
| `wspPha` | `TextUnit` | `Text("Hi", fontSize = 16.wspPha)` |
| `wspPhi` | `TextUnit` | `Text("Hi", fontSize = 16.wspPhi)` |
| `wspPhia` | `TextUnit` | `Text("Hi", fontSize = 16.wspPhia)` |
| `wspPxPh` | `Float` | `val px = 16.wspPxPh` |
| `wspPxaPh` | `Float` | `val px = 16.wspPxaPh` |
| `wspPxiPh` | `Float` | `val px = 16.wspPxiPh` |
| `wspPxiaPh` | `Float` | `val px = 16.wspPxiaPh` |
| `wemPh` | `TextUnit` | `Text("Hi", fontSize = 16.wemPh)` |
| `wemPha` | `TextUnit` | `Text("Hi", fontSize = 16.wemPha)` |
| `wemPhi` | `TextUnit` | `Text("Hi", fontSize = 16.wemPhi)` |
| `wemPhia` | `TextUnit` | `Text("Hi", fontSize = 16.wemPhia)` |
| `wemPxPh` | `Float` | `val px = 16.wemPxPh` |
| `wemPxaPh` | `Float` | `val px = 16.wemPxaPh` |
| `wemPxiPh` | `Float` | `val px = 16.wemPxiPh` |
| `wemPxiaPh` | `Float` | `val px = 16.wemPxiaPh` |

---

## Appendix B — Maintenance

When the library adds a new property or facilitator, update:

1. The Kotlin sources under `library/src/main/java/com/appdimens/dynamic/compose/`.
2. This file (or regenerate Appendix A with the project script).
3. The strategy overview in [scaled.md](scaled.md) and [README.md](../README.md).

