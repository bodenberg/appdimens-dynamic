# Cookbook

Ready-to-use strategies. Each one shows the code form and, where it matters, the JSON form.

## Grow on phones, calm down on tablets

```kotlin
val Calm = CustomScaleStrategy { if (sw < 600f) ratio else 2f + (sw - 600f) / 1000f }
```

```kotlin
val Calm = CustomPresets.TABLET_FRIENDLY            // linear on phones, power(0.5) on tablets, ≤ 1.8
```

## Design frame of 360 dp, never below 85% or above 160%

```kotlin
val Brand = CustomScaleStrategy { (sw / 360f).coerceIn(0.85f, 1.6f) }
```

```json
{ "id": "brand", "reference": { "width": 360, "height": 640 }, "clamp": { "min": 0.85, "max": 1.6 } }
```

## Sublinear growth (big screens grow slower)

```kotlin
val Soft = CustomScaleStrategy { pow(sw / 360f, 0.75f) }
```

```json
{ "id": "soft", "reference": { "width": 360, "height": 640 }, "curve": { "type": "power", "exponent": 0.75 } }
```

## TV: scale by height, 10-foot UI

```kotlin
val Living = CustomScaleStrategy { if (tv) (height / 540f).coerceIn(1f, 2f) else ratio }
```

```json
{ "id": "living", "rules": [ { "when": { "device": "tv" },
  "set": { "axis": "height", "reference": { "width": 960, "height": 540 }, "clamp": { "min": 1, "max": 2 } } } ] }
```

## Watch: almost fixed sizes

```kotlin
val Wrist = CustomScaleStrategy { if (watch) (shortSide / 192f).coerceIn(0.85f, 1.15f) else ratio }
```

## Game HUD: shorter side, aspect-ratio aware

```kotlin
val Hud = scalingStrategy("hud") {
    axis = ScalingAxis.MIN
    curve = power(0.85f)
    aspectRatio()
    clamp(0.75f, 2.5f)
    ignoreFontScale()                    // HUD text never follows the system font size
}
```

## Readable text: grow slowly, cap the font scale

```kotlin
val Body = scalingStrategy("body") { curve = logarithmic(0.4f); clampFontScale(1.3f) }
Text("…", fontSize = 16.csp(Body))
```

## Minimum tap target

```kotlin
val Touch = scalingStrategy("touch") { clampValue(min = 48) }
Modifier.size(40.cdp(Touch))             // never below 48 dp
```

## Whole dp only

```kotlin
val Snap = scalingFormula { round(base * ratio) }
```

```json
{ "id": "snap", "formula": "round(base * ratio)" }
```

## Never smaller than the design value

```kotlin
val Floor = scalingFormula { max(base * ratio, base) }
```

## Split screen: keep the design values

```kotlin
val Steady = scalingStrategy("steady") { ignoreMultiWindow = true }
```

## Landscape phones: a bit smaller

```kotlin
val Brand = scalingStrategy("brand") {
    curve = power(0.8f)
    onScreen(maxSmallestWidth = 600f, orientation = Orientation.LANDSCAPE) { multiply(0.9f) }
}
```

## A/B test or remote config

```kotlin
val json = remoteConfig.getString("appdimens_strategies")      // a strategies document
runCatching { ScalingStrategies.loadJson(json) }               // atomic: a bad document changes nothing
    .onFailure { Log.w("dimens", "bad strategies JSON", it) }
Modifier.padding(16.cdp(ScalingStrategies.getOrThrow("brand")))
```

## One growth law for a design system

Ship a single `appdimens-strategies.json` with the design tokens, load it in every app (`loadAsset` on Android, `loadJson` elsewhere), and use the ids only. iOS, Flutter, React Native, Web, KMP, Games and .NET read the same file and get the same numbers.

[Back to the custom docs](README.md)
