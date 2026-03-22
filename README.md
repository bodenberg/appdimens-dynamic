# AppDimens Dynamic (SDP, HDP, WDP)

![AppDimens Banner](IMAGES/banner_top.png)

**AppDimens Dynamic** is the most complete responsive dimension library for Android. It provides a purely dynamic, code-level scaling system — including Jetpack Compose extensions, code-level APIs, conditional builders, orientation-aware inverters, and physical unit converters — all in a single, zero-configuration dependency.

---

## 🛠️ Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:3.0.0-beta1")
}
```

**Requirements:** Min SDK 24 · Compile SDK 36 · Kotlin & Java · Jetpack Compose

> [!NOTE]
> **Version 3.0.0-beta**
> This version implements scale-based calculations (the Android system standard). Support for other calculation methods will be implemented soon.

---

## 💻 Usage Examples

### 0. Initialization (Optional - For Persistence)

To enable **DimenCache persistence** (avoiding recalculations after the first app launch), initialize the library in your `Application` class:

```kotlin
import android.app.Application
import com.appdimens.dynamic.core.DimenCache

class InitializeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /*
        * EN Initialize the AppDimens cache.
        * Initializes the cache persistence setting to avoid recalculations when opening the app after the first launch.
        *
        * PT Inicializa o cache AppDimens.
        * Inicializa a configuração de persistência do cache para evitar recalculações quando abrir o aplicativo após o primeiro lançamento.
        */
        DimenCache.init(this)
    }
}
```

### 1. Jetpack Compose

**Basic — Auto-Scaling Extensions:**
```kotlin
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.compose.hdp
import com.appdimens.dynamic.compose.wdp

Box(
    modifier = Modifier
        .width(100.wdp)    // Scales relative to the device's width
        .height(100.hdp)   // Scales relative to the device's height
        .padding(16.sdp)   // Scales relative to the smallest width
) {
    Text("Hello World", fontSize = 14.sdp.value.sp) // Manual conversion
    Text("Scalable Text", fontSize = 16.ssp)       // Direct TextUnit extension
}
```

**Advanced Suffixes — Multi-Window & Aspect Ratio:**
You can append flags to any extension to control scaling behavior:
- `a`: **Apply Aspect Ratio** — Mathematically refined scaling for non-standard screen ratios.
- `i`: **Ignore Multi-Window** — Maintains layout scale even in split-screen/resizing modes.
- `ia`: **Both flags enabled**.

```kotlin
// Examples:
val sdpAr = 16.sdpa     // Smallest Width + Aspect Ratio
val hdpMw = 32.hdpi     // Height + Ignore Multi-Window
val wspBoth = 50.wdpia  // Width + Both
val sspAr = 16.sspa     // Scalable Sp + Aspect Ratio
val seiMw = 16.seii     // Fixed Sp + Ignore Multi-Window
```

**Scalable Fonts (TextUnit) — Auto-Scaling Extensions:**
```kotlin
import com.appdimens.dynamic.compose.ssp // Smallest Width
import com.appdimens.dynamic.compose.hsp // Height
import com.appdimens.dynamic.compose.wsp // Width

// Fixed Sp (Ignores system font scale)
import com.appdimens.dynamic.compose.sei // Smallest Width (Ignore scale)
import com.appdimens.dynamic.compose.hei // Height (Ignore scale)
import com.appdimens.dynamic.compose.wei // Width (Ignore scale)

Text("Scalable", fontSize = 16.ssp)
Text("Height based", fontSize = 20.hsp)
Text("No font scale", fontSize = 16.sei)
```

**Inverter Shortcuts — Orientation-Aware Scaling:**
```kotlin
import com.appdimens.dynamic.compose.sdpPh
import com.appdimens.dynamic.compose.sdpLw
import com.appdimens.dynamic.compose.hdpLw
import com.appdimens.dynamic.compose.wdpLh

// .sdpPh → uses Smallest Width by default; in Portrait → switches to Height
val adaptiveVert = 32.sdpPh

// .sdpLw → uses Smallest Width by default; in Landscape → switches to Width
val adaptiveHorz = 32.sdpLw

// .hdpLw → uses Height by default; in Landscape → switches to Width
val heightToWidth = 50.hdpLw

// .wdpLh → uses Width by default; in Landscape → switches to Height
val widthToHeight = 50.wdpLh
```

**Facilitators — Quick Conditional Overrides:**
```kotlin
import com.appdimens.dynamic.compose.sdpRotate
import com.appdimens.dynamic.compose.sdpRotatePlain
import com.appdimens.dynamic.compose.sdpMode
import com.appdimens.dynamic.compose.sdpQualifier
import com.appdimens.dynamic.compose.sdpScreen

// 1. Int Extension: Scales by default (80.sdp default, 50.sdp in Landscape)
val rotInt = 80.sdpRotate(50)

// 2. Dp Extension (Always Scales): 30.dp scaled by swDP by default
val rotDp = 30.dp.sdpRotate(50)

// 3. Dp Extension (Plain - Raw Fallback): Returns 30.sdp in Portrait, 50 scaled in Landscape
val rotPlain = 30.sdp.sdpRotatePlain(50)

// Mode, Qualifier, Screen examples (All support Int, Dp, and Plain variants)
val modeVal = 30.sdpMode(200, UiModeType.TELEVISION)
val qualVal = 60.sdpQualifier(120, DpQualifier.SMALL_WIDTH, 600)
val scrVal = 70.sdpScreen(150, UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 600)

// Sp Facilitators (Returns TextUnit)
// Supports: .sspRotate, .sspRotatePlain, .sspMode, etc.
val fontRot = 16.sspRotate(24)
val fontPlain = 16.ssp.sspRotatePlain(24)
val fontTV = 16.sspMode(40, UiModeType.TELEVISION)
```

**DimenScaled & ScaledSp Builder — Complex Multi-Condition Chains:**
```kotlin
import com.appdimens.dynamic.compose.scaledDp
import com.appdimens.dynamic.compose.scaledSp

val dynamicPadding = 16.scaledDp()
    .aspectRatio(true)           // Enable Aspect Ratio scaling
    .ignoreMultiWindows(true)    // Ignore Multi-Window resizing
    .setEnableCache(true)        // Explicitly enable/disable cache
    .screen(UiModeType.TELEVISION, 40)
    .screen(DpQualifier.SMALL_WIDTH, 600, 24)
    .sdp // Resolution: sdp, hdp, or wdp

val dynamicText = 16.scaledSp()
    .aspectRatio(true)
    .ignoreMultiWindows(true)
    .screen(UiModeType.TELEVISION, 40)
    .ssp // Resolution: ssp, hsp, wsp, sem, hem, or wem
```

---

### 2. Kotlin (Code Level)

```kotlin
import com.appdimens.dynamic.code.scaled.DimenSdp
import com.appdimens.dynamic.code.scaled.DimenSsp

// Core — Pixel values
val paddingPx = DimenSdp.sdp(context, 16)     // Smallest Width
val heightPx  = DimenSdp.hdp(context, 32)     // Height
val widthPx   = DimenSdp.wdp(context, 100)    // Width

// Scalable Sp - Pixel values
val fontSizePx = DimenSsp.ssp(context, 16)    // With font scaling
val fixedSpPx  = DimenSsp.sei(context, 16)    // Without font scaling

// Kotlin Extensions for Sp
import com.appdimens.dynamic.code.ssp
import com.appdimens.dynamic.code.hsp
import com.appdimens.dynamic.code.scaledSp

val size = 16.ssp(context)
val adaptiveFont = 16.hsp(context)
val builderSp = 16.scaledSp().screen(UiModeType.TELEVISION, 40).ssp(context)

// Inverter shortcuts
val adaptive = DimenSdp.hdpLw(context, 50)    // Height → Width in Landscape

// Facilitators
val rotated = DimenSdp.sdpRotate(context, 30, Orientation.LANDSCAPE)
val modeVal = DimenSdp.sdpMode(context, 30, 200, UiModeType.TELEVISION)
val qualVal = DimenSdp.sdpQualifier(context, 30, 80, DpQualifier.SMALL_WIDTH, 600)

// DimenScaled builder
val dynamicPx = DimenSdp.scaled(16)
    .screen(UiModeType.TELEVISION, 32)
    .screen(DpQualifier.SMALL_WIDTH, 600, 24)
    .screen(Orientation.LANDSCAPE, 12)
    .sdp(context)

// Physical units
import com.appdimens.dynamic.code.units.DimenPhysicalUnits
val dpFromCm = DimenPhysicalUnits.toDpFromCm(2.5f, resources)
```

---

### 3. Java (Code Level)

```java
import com.appdimens.dynamic.code.scaled.DimenSdp;
import com.appdimens.dynamic.code.scaled.DimenSsp;

// Core (Resolves dynamically at runtime)
float paddingPx = DimenSdp.sdp(context, 16);
float heightPx = DimenSdp.hdp(context, 32);

// Scalable Sp
float fontSizePx = DimenSsp.ssp(context, 16);

// Inverter shortcuts
float adaptive = DimenSdp.hdpLw(context, 50);

// DimenScaled builder
DimenScaled scaled = DimenSdp.scaled(16)
    .aspectRatio(true)
    .ignoreMultiWindows(true)
    .screen(UiModeType.TELEVISION, 32)
    .screen(Orientation.LANDSCAPE, 12);

float result = scaled.sdp(context);
```

---

### 4. Physical Units

```kotlin
// Compose extensions
import com.appdimens.dynamic.compose.mm
import com.appdimens.dynamic.compose.cm
import com.appdimens.dynamic.compose.inch

val widthMm = 10.mm       // 10mm → Dp
val widthCm = 2.5f.cm     // 2.5cm → Dp
val widthIn = 1.inch      // 1 inch → Dp

// Code level
import com.appdimens.dynamic.code.units.DimenPhysicalUnits
DimenPhysicalUnits.toDpFromMm(25f, resources)
DimenPhysicalUnits.toDpFromCm(2.5f, resources)
DimenPhysicalUnits.toDpFromInch(1f, resources)
```

<br/>
<p align="center">
  <img src="IMAGES/screenshot.png" alt="Layout example" width="25%" />
</p>
<br/>

---

## ✨ What's New in Version 3.x

| Feature | Description |
|---------|-------------|
| **Dynamic Calculation** | 100% code-level resolution — no XML files required, fits into any project size |
| **DimenCache** | Ultra-optimized, lock-free caching with persistence support |
| **Aspect Ratio Scaling** | `applyAspectRatio` flag for mathematically refined scaling on non-standard screen ratios |
| **Multi-Window Support** | `ignoreMultiWindows` flag to maintain scale during split-screen or app resizing |
| **Inverter Shortcuts** | `.sdpPh`, `.sdpLw`, `.sdpLh`, `.sdpPw`, `.hdpLw`, `.hdpPw`, `.wdpLh`, `.wdpPh` — orientation-aware switching |
| **Facilitators** | `sdpRotate`, `sdpMode`, `sdpQualifier`, `sdpScreen` — quick conditional overrides |
| **DimenScaled Builder** | Priority-based chain with `UiModeType`, `DpQualifier`, `Orientation`, and `Inverter` support |
| **Foldable Detection** | `FoldingFeature` integration — detects Fold/Flip open, closed, or half-opened states |
| **UiModeType** | `NORMAL`, `TELEVISION`, `CAR`, `WATCH`, `DESK`, `APPLIANCE`, `VR_HEADSET`, `FOLD_OPEN`, `FOLD_CLOSED`, `FOLD_HALF_OPENED`, `FLIP_OPEN`, `FLIP_CLOSED`, `FLIP_HALF_OPENED` |
| **Physical Units** | `DimenPhysicalUnits` — convert mm, cm, inches to Dp/Px |
| **Sp & TextUnit** | Full support for Scalable Sp (`ssp`) and Fixed Sp (`sei`) — respects or ignores font scale |

---

## 🧮 Why Dynamic & Qualifier-Aware Scaling?

Most responsive Android solutions use simple runtime multiplication. **AppDimens Dynamic** takes it further by combining Android's native configuration awareness with a sophisticated scaling system:

### The Problem with Simple Linear Ratios

```kotlin
// ❌ Simple linear approach
fun scaledDp(value: Int): Float {
    val screenWidth = resources.displayMetrics.widthPixels
    val baseWidth = 360f 
    return value * (screenWidth / baseWidth) 
}
```

Issues:
- **No context awareness** — ignores device type (TV vs Phone)
- **Linear scaling only** — produces values that don't always feel right on extreme resolutions
- **Calculation cost** — repeated logic on every frame if not cached

### The AppDimens Solution: Dynamic + Cached + Multi-Axis

AppDimens Dynamic calculates values at runtime using **mathematically refined curves** tuned for current screen metrics, then stores the results in a high-performance **DimenCache**.

| Aspect | Simple Multiplication | AppDimens Dynamic |
|--------|----------------------|-------------------|
| **CPU Cost** | Low (no cache) | Near-Zero (Internal Cache) |
| **Scaling Quality** | Linear (imprecise) | Refined curves tuned per configuration |
| **Android Integration** | Bypasses system hints | Uses native `Configuration` and `UiMode` |
| **Orientation Handling** | Manual code required | Automatic via Inverters & Built-in logic |
| **Library Footprint** | Small | Small (Pure code, no pre-generated resources) |

---

## ⚡ Performance: DimenCache

AppDimens Dynamic is designed for maximum efficiency:

- **Automatic Caching**: Once a dimension is calculated for a specific screen configuration, it is stored in `DimenCache` for instant reuse.
- **Persistence**: Call `DimenCache.init(this)` to enable persistence via DataStore, avoiding recalculations across app launches.
- **Compose Optimization**: Uses `LocalConfiguration.current` and `remember{}` internally to ensure no unnecessary recompositions.
- **Zero Resource Lookups**: By eliminating `@dimen` XML file dependency, it avoids system resource resolution overhead.

---

## 📖 How It Works

### Three Scaling Axes

| Qualifier | Extension | Based On |
|-----------|-----------|----------|
| **SDP** | `.sdp` | `smallestScreenWidthDp` — the smaller of width/height, independent of orientation |
| **HDP** | `.hdp` | `screenHeightDp` — the current screen height in dp |
| **WDP** | `.wdp` | `screenWidthDp` — the current screen width in dp |

### Conditional Dimension Resolution

The **DimenScaled** builder uses a priority system to decide which value to apply:

1. **UiMode + Qualifier + Orientation** (e.g., TV with sw≥600 in Landscape)
2. **UiMode + Orientation** (e.g., Any TV device)
3. **Qualifier + Orientation** (e.g., sw≥600 regardless of device type)
4. **Orientation** (Landscape or Portrait fallback)

### Inverter System

Inverters adapt dimension semantics when the screen rotates (e.g., swapping Width-based scaling for Height-based scaling in Landscape).

---

## 🏆 Why AppDimens Dynamic?

| Feature | AppDimens Dynamic | Typical Scaling Libs |
|---------|-------------------|----------------------|
| **Architecture** | 100% Dynamic Code | XML Resources or Linear Math |
| **Compose Support** | Native Hooks | Manual conversion required |
| **Multi-Axis** | SDP + HDP + WDP | Usually 1 axis only |
| **Orientation Awareness** | Built-in Inverters | Code-heavy manual work |
| **Device Detection** | TV, Foldables, Watch, etc. | Generic resolution |
| **Negative Values** | Full Support | Usually limited |
| **Physical Units** | mm, cm, inches | Not available |

---

## 📏 Physical Units (DimenPhysicalUnits)

AppDimens Dynamic provides direct conversion of **real physical measurement units** (mm, cm, inches) to DP/PX — ensuring absolute physical size regardless of device density.

Compose extensions: `10.mm`, `2.5f.cm`, `1.inch` → `Dp` values directly.

---

*Created with the best responsive layout practices for the Android ecosystem.*
