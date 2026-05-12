# AppDimens Dynamic

## Responsive `dp` / `sp` for Android — Jetpack Compose and Kotlin APIs

<p align="center">
  <a href="https://github.com/bodenberg/appdimens-dynamic/releases" title="Releases">
    <img src="https://img.shields.io/badge/version-3.1.5-blue.svg" alt="Version 3.1.5">
  </a>
  &nbsp;
  <a href="LICENSE" title="Apache License 2.0">
    <img src="https://img.shields.io/badge/license-Apache%202.0-green.svg" alt="License Apache 2.0">
  </a>
  &nbsp;
  <a href="https://developer.android.com" title="Android platform">
    <img src="https://img.shields.io/badge/platform-Android-3DDC84.svg?logo=android&logoColor=white" alt="Platform Android">
  </a>
  &nbsp;
  <img src="https://img.shields.io/badge/Kotlin-2.x-7F52FF.svg?logo=kotlin&logoColor=white" alt="Kotlin">
  &nbsp;
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?logo=jetpackcompose&logoColor=white" alt="Jetpack Compose">
  &nbsp;
  <img src="https://img.shields.io/badge/min%20SDK-24-2E7D32.svg" alt="Min SDK 24">
  &nbsp;
  <a href="./DOCUMENTATION/README.md" title="Scaling strategies and modules">
    <img src="https://img.shields.io/badge/scaling%20modes-14-orange.svg" alt="14 scaling modes">
  </a>
</p>

<p align="center">
  <a href="./GUIDE-FOR-BEGINNERS.md" title="Step-by-step guide for beginners">
    <img src="https://img.shields.io/badge/Beginner%20guide-Step%20by%20step-f59e0b?style=for-the-badge&logo=readthedocs&logoColor=white&labelColor=b45309" alt="Beginner guide">
  </a>
  &nbsp;&nbsp;
  <a href="./DOCUMENTATION/README.md" title="Strategies, formulas, and when to use each scaling mode">
    <img src="https://img.shields.io/badge/Docs-Full%20documentation-1d4ed8?style=for-the-badge&logo=gitbook&logoColor=white&labelColor=1e3a8a" alt="Full documentation">
  </a>
  &nbsp;&nbsp;
  <a href="./DOCUMENTATION/index.md" title="API documentation — Markdown package index (KDoc export)">
    <img src="https://img.shields.io/badge/API%20DOCUMENTATION--7F52FF?style=for-the-badge&logo=markdown&logoColor=white&labelColor=4c1d95" alt="API documentation">
  </a>
  &nbsp;&nbsp;
  <a href="https://appdimens3.web.app/" title="Hosted Dokka — searchable API reference">
    <img src="https://img.shields.io/badge/KDOC%20REFERENCE--0d9488?style=for-the-badge&logo=firebase&logoColor=white&labelColor=115e59" alt="KDoc reference">
  </a>
</p>

<p align="center">
  <a href="./PERFORMANCE.md" title="Technical performance report">
    <img src="https://img.shields.io/badge/Performance-Report-0f766e?style=for-the-badge&logo=google-analytics&logoColor=white&labelColor=134e4a" alt="Performance report">
  </a>
  &nbsp;&nbsp;
  <a href="./PERFORMANCE-COMPARATIVE.md" title="Performance after optimization phases — comparative report">
    <img src="https://img.shields.io/badge/Performance-Comparative-7c3aed?style=for-the-badge&logo=speedtest&logoColor=white&labelColor=5b21b6" alt="Performance comparative">
  </a>
  &nbsp;&nbsp;
  <a href="./R8-PROGUARD.md" title="R8 full mode and ProGuard rules — library, consumer, app">
    <img src="https://img.shields.io/badge/R8%20%26%20ProGuard-Rules-334155?style=for-the-badge&logo=android&logoColor=white&labelColor=1e293b" alt="R8 and ProGuard rules">
  </a>
</p>

---

![AppDimens Banner](IMAGES/banner_top.png)

One dependency: you write values like `16.sdp` and the library scales them from the current screen **Configuration** (size, density, optional flags).

**New here?** Use **Quick start** below, then [**GUIDE-FOR-BEGINNERS**](./GUIDE-FOR-BEGINNERS) for every strategy in plain language.

**All 14 modes** (scaled, percent, power, fluid, …): [DOCUMENTATION/README.md](DOCUMENTATION/README.md) · [KDoc (hosted)](https://appdimens3.web.app/) · **Product / engineering:** [DOCUMENTATION/PRD.md](DOCUMENTATION/PRD.md) · [DOCUMENTATION/PDR.md](DOCUMENTATION/PDR.md) · [DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md](DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)

---

## Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.5")
}
```

**Requirements:** Min SDK **24** · Compile SDK **36** · **Kotlin** & **Java 17** · **Jetpack Compose** (BOM aligned with this library)

---

## Quick start — Scaled (Compose)

```kotlin
import com.appdimens.dynamic.compose.*

Box(
    Modifier
        .padding(16.sdp)
        .width(100.wdp)
        .height(48.hdp)
) {
    Text("Hello", fontSize = 16.ssp)
}
```

| Extension | Based on | Typical use |
|-----------|----------|-------------|
| **`sdp`** | Smallest window width | Padding, margins |
| **`hdp`** | Screen height | Row height |
| **`wdp`** | Screen width | Column width |
| **`ssp`** | Same idea as `sdp`, for text | `fontSize` |
| **`sem`** | Same idea as `sdp`, for text | `fontSize ignore system font scale` |

---

## Compose — setup before advanced APIs

**If you only use `sdp` / `hdp` / `wdp` / `ssp` / `hsp` / `wsp`/ `sem` / `hem` / `wem` (and variants like `sdpa`), you can skip this block.**

### `AppDimensProvider`

Use it when you call **`.sdpMode`**, **`.sdpScreen`**, **`.sspMode`**, **`.sspScreen`**, or similar **facilitators** that depend on **UI mode / fold state**. It sets `LocalUiModeType` once for the tree instead of resolving mode on every call.

```kotlin
import com.appdimens.dynamic.core.AppDimensProvider

setContent {
    AppDimensProvider {
        MyApp()
    }
}
```

### `DimenCache.invalidateOnConfigChange`

Call this when the **same Activity** stays alive across **rotation, split-screen, or density/font changes** and sizes look **stale**. If the Activity is **recreated** on config change (default), you often don’t need it. Details: [library/PERFORMANCE.md](library/PERFORMANCE.md).

The previous `Configuration` is tracked internally by `DimenCache` — callers only need to pass the new one.

```kotlin
import com.appdimens.dynamic.core.DimenCache

override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    DimenCache.invalidateOnConfigChange(newConfig)
}
```

You only get `onConfigurationChanged` if the Activity lists `android:configChanges` for those changes in the manifest; otherwise the process usually recreates the Activity and config is fresh automatically.

---

## Compose — next steps

### Suffixes (`a`, `i`, `ia`)

| Suffix | Meaning |
|--------|---------|
| *(none)* | Default |
| **`a`** | Aspect ratio–aware curve |
| **`i`** | Ignore multi-window heuristic (may return unscaled base when it triggers) |
| **`ia`** | Both |

```kotlin
16.sdpa      // + aspect ratio
32.hdpi      // height axis + ignore multi-window
16.sspa      // scalable sp + aspect ratio
```

### More text styles

```kotlin
Text("Scaled (sw)", fontSize = 16.ssp)
Text("Scaled (height)", fontSize = 20.hsp)
Text("Scaled (width)", fontSize = 18.wsp)
Text("No system font scale (sw)", fontSize = 16.sem)   // sem / hem / wem
```

### Orientation inverters (examples)

```kotlin
32.sdpPh   // SW-based; in portrait uses height
32.sdpLw   // SW-based; in landscape uses width
50.hdpLw   // Height-based; in landscape uses width
50.wdpLh   // Width-based; in landscape uses height
```

### Facilitators (after `AppDimensProvider` if you use mode/screen)

```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType

80.sdpRotate(50, orientation = Orientation.LANDSCAPE)
30.sdpMode(200, UiModeType.TELEVISION)
60.sdpQualifier(120, DpQualifier.SMALL_WIDTH, 600)
16.sspRotate(24, orientation = Orientation.LANDSCAPE)
```

Full catalog: [DOCUMENTATION/COMPOSE-API-CONVENTIONS.md](DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

### Builders (`scaledDp` / `scaledSp`)

```kotlin
val pad = 16.scaledDp()
    .aspectRatio(true)
    .screen(UiModeType.TELEVISION, 40)
    .screen(DpQualifier.SMALL_WIDTH, 600, 24)
    .sdp
```

### Auto-resize (inside `BoxWithConstraints`)

Picks the **largest** font or size in a **min…max** range that still **fits** the space. Use for titles, squares, etc.

```kotlin
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.appdimens.dynamic.compose.resize.autoResizeTextSp

BoxWithConstraints(Modifier.fillMaxWidth()) {
    val fontSize = autoResizeTextSp(
        text = "Headline that must fit",
        minSp = 12,
        maxSp = 28,
        stepSp = 1,
        maxLines = 2,
    )
    Text("Headline that must fit", fontSize = fontSize, maxLines = 2)
}
```

More APIs (`autoResizeSquareSize`, `ResizeBound`, …): [DOCUMENTATION/resize.md](DOCUMENTATION/resize.md).

---

## Kotlin (Views / non-Composable)

```kotlin
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.DimenSsp

val paddingPx = DimenSdp.sdp(context, 16)
val heightPx = DimenSdp.hdp(context, 32)
val widthPx = DimenSdp.wdp(context, 100)
val fontPx = DimenSsp.ssp(context, 16)

// Extensions (see code package)
// 16.ssp(context), DimenSdp.scaled(16).screen(...).sdp(context), sdpRotate, …
```

---

## Java

```java
import com.appdimens.dynamic.code.DimenSdp;
import com.appdimens.dynamic.code.DimenScaled;
import com.appdimens.dynamic.code.DimenSsp;
import com.appdimens.dynamic.common.UiModeType;

float paddingPx = DimenSdp.sdp(context, 16);
float heightPx = DimenSdp.hdp(context, 32);
float fontPx = DimenSsp.ssp(context, 16);

DimenScaled scaled = DimenSdp.scaled(16)
    .applyAspectRatio(true)
    .screen(UiModeType.TELEVISION, 32);
float result = scaled.sdp(context);
```

---

## Physical units (mm, cm, inch)

Approximate **real-world** size on screen (density-based). Compose: use helpers from the library and **`.dp`** on the result where needed — see [DOCUMENTATION/physical-units.md](DOCUMENTATION/physical-units.md). Code module: `com.appdimens.dynamic.code.units.DimenPhysicalUnits` (`toDpFromMm`, …).

---

<p align="center">
  <img src="IMAGES/screenshot.jpg" alt="Layout example" width="200"/>
  &nbsp;
  <img src="IMAGES/screenshot_benchmark.jpg" alt="Benchmark" width="200" />
</p>

---

## More strategies & full API

**Recommendation order for most apps:** **Scaled** (with or without `a`) → then **percent** → then **auto**; explore the rest when you have a clear need (fluid, fit, diagonal, etc.).

Other strategies (**percent**, **power**, **fluid**, **auto**, **diagonal**, **fill**, **fit**, **interpolated**, **logarithmic**, **perimeter**, **density**) use the **same suffix patterns** as Scaled with a **different import prefix** — see [DOCUMENTATION/README.md](DOCUMENTATION/README.md) and [**GUIDE-FOR-BEGINNERS**](./GUIDE-FOR-BEGINNERS).

| Resource | Use for |
|----------|---------|
| [DOCUMENTATION/README.md](DOCUMENTATION/README.md) | Per-strategy explanations |
| [COMPOSE-API-CONVENTIONS.md](DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) | Every Compose property & facilitator (scaled catalog + prefix map) |
| [DOCUMENTATION/index.md](DOCUMENTATION/index.md) | Markdown API index (KDoc export) |
| [appdimens3.web.app](https://appdimens3.web.app/) | Searchable KDoc |

**Example app:** `app/.../compose/ExampleActivity.kt` (includes auto-resize demos).

---

## Optional: cache & performance

- Results are cached in **`DimenCache`** (lock-free, optional persistence).
- Some paths **skip** storing in the shard table when a cheap multiply is enough — see [library/PERFORMANCE.md](library/PERFORMANCE.md).
- **Batch / low-level keys:** not needed for normal app code; library extensions already use the cache.

---

**Scaled** uses **300 dp** as the design reference. It is the **most widely used** strategy in real apps and the **recommended default**: use plain `sdp` / `hdp` / `wdp` / `ssp` when a single curve is enough, and the **`a`** suffix (aspect ratio–aware), e.g. `16.sdpa`, when you want scaling tuned to screen shape. **After Scaled**, the next strategies teams typically adopt are **percent** (sizes as a fraction of an axis) and **auto** (breakpoint-style steps); the other modes are for specialized layouts — see [DOCUMENTATION/README.md](DOCUMENTATION/README.md).

---

**Facilitators — two “Plain” styles:** `*RotatePlain`, `*ModePlain`, `*QualifierPlain`, `*ScreenPlain` (and `*PlainPx`) exist with the alternate as **`Number`** (active branch still runs through scaling/cache) or as **`Dp` / `TextUnit`** (only the condition is evaluated; **no** second scaling). For **nested** chains such as `30.sdp.sdpRotatePlain(20.sdp).sdpModePlain(40.sdp, UiModeType.TELEVISION)`, prefer **`Dp` / `TextUnit` alternates** so neither the receiver nor the alternate is scaled twice. **Nesting order** is the order you write the chain (outer → inner). That is **different** from **`DimenScaled` `.screen` chains**, where **priority is defined inside the builder API**, not by lexical nesting — see [DOCUMENTATION/COMPOSE-API-CONVENTIONS.md](DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

---

**Views / `code`:** the same **logic-only** Plain branching exists on **`Float` px** + **`Context`** — `Dimen*PlainPx.kt` per strategy (e.g. `psdpRotatePlainPx` in `com.appdimens.dynamic.code.percent`), with shared helpers in **`com.appdimens.dynamic.code.plain`** (`DimenPlainBranch.kt`). **Dp/Sp facilitator** sources use the same **`Dimen<Strategy>DpExtensions.kt` / `Dimen<Strategy>SpExtensions.kt`** names as in `compose/<strategy>/` (scaled: `DimenSdpExtensions.kt` / `DimenSspExtensions.kt` under `code/scaled/`). Details in [DOCUMENTATION/COMPOSE-API-CONVENTIONS.md](DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) §4.5 and [DOCUMENTATION/README.md](DOCUMENTATION/README.md).

---

## Highlights (v3.x)

- Code-only scaling (no XML dimen grids) · **SDP / HDP / WDP** + **14** scaling modes  
- **Aspect ratio** & **multi-window** flags · **Inverters** & **facilitators** · **Foldable** awareness via WindowManager  
- **Physical units** · **Resize** helpers · **DimenScaled** chains  

---

*Apache License 2.0 — responsive layout utilities for Android.*
