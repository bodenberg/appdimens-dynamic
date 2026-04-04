# AppDimens Dynamic

## Responsive `dp` / `sp` for Android — Jetpack Compose and Kotlin APIs

<p align="center">
  <a href="https://github.com/bodenberg/appdimens-dynamic/releases" title="Releases">
    <img src="https://img.shields.io/badge/version-3.1.0-blue.svg" alt="Version 3.1.0">
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
  <a href="./GUIDE-FOR-BEGINNERS" title="Step-by-step guide for beginners">
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

---

![AppDimens Banner](IMAGES/banner_top.png)

One dependency: you write values like `16.sdp` and the library scales them from the current screen **Configuration** (size, density, optional flags).

**New here?** Use **Quick start** below, then [**GUIDE-FOR-BEGINNERS**](./GUIDE-FOR-BEGINNERS) for every strategy in plain language.

**All 14 modes** (scaled, percent, power, fluid, …): [DOCUMENTATION/README.md](DOCUMENTATION/README.md) · [KDoc (hosted)](https://appdimens3.web.app/)

---

## Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.0")
}
```

**Requirements:** Min SDK **24** · Compile SDK **36** · **Kotlin** & **Java 17** · **Jetpack Compose** (BOM aligned with this library)

---

## Quick start — Scaled (Compose)

**Scaled** uses **300 dp** as the design reference. It is what **most apps use most of the time** — with or without **`a`** (aspect ratio), e.g. `16.sdp` vs `16.sdpa`.

| Extension | Based on | Typical use |
|-----------|----------|-------------|
| **`sdp`** | Smallest window width | Padding, margins |
| **`hdp`** | Screen height | Row height |
| **`wdp`** | Screen width | Column width |
| **`ssp`** | Same idea as `sdp`, for text | `fontSize` |

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

---

## Compose — setup before advanced APIs

**If you only use `sdp` / `hdp` / `wdp` / `ssp` (and variants like `sdpa`), you can skip this block.**

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

```kotlin
import android.content.res.Configuration
import com.appdimens.dynamic.core.DimenCache

private var lastConfiguration: Configuration? = null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lastConfiguration = Configuration(resources.configuration)
}

override fun onConfigurationChanged(newConfig: Configuration) {
    DimenCache.invalidateOnConfigChange(lastConfiguration, newConfig)
    lastConfiguration = Configuration(newConfig)
    super.onConfigurationChanged(newConfig)
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
Text("No system font scale (sw)", fontSize = 16.nem)   // nem / hem / wem
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

## Highlights (v3.x)

- Code-only scaling (no XML dimen grids) · **SDP / HDP / WDP** + **14** scaling modes  
- **Aspect ratio** & **multi-window** flags · **Inverters** & **facilitators** · **Foldable** awareness via WindowManager  
- **Physical units** · **Resize** helpers · **DimenScaled** chains  

---

*Apache License 2.0 — responsive layout utilities for Android.*
