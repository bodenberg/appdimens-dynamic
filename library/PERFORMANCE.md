# Performance notes — `DimenCache` & Scaling Engine

## Fast bypass (`getOrPut`)

When `CalcType` is one of:

- `PERCENT` (ordinal 7)
- `SCALED` (ordinal 11)
- `DENSITY` (ordinal 14)
- `DIAGONAL` (ordinal 1)
- `INTERPOLATED` (ordinal 5)
- `PERIMETER` (ordinal 8)

`DimenCache.getOrPut` **returns `compute()` directly** and does **not** store the result in the shard table when:

1. **Aspect ratio is off** (bit 63 clear / key ≥ 0) — formula is `baseValue * preComputedFactor`, or
2. **Default aspect ratio is on** (`qualifier = SMALL_WIDTH`, `inverter = DEFAULT`, `customSensitivityK = null`) — formula is already `baseValue * factors.arMultiplier` (precomputed in `updateFactors()`).

Both paths are a single multiply (~2 ns on Snapdragon 888) — cheaper than a shard lookup (~5 ns).

**Still cached (no bypass):**

- Non-default AR (custom `qualifier` / `inverter` / `customSensitivityK`)
- `AUTO`, `FLUID`, `POWER`, `LOGARITHMIC`, `FIT`, `FILL`, …
- `CT_ASPECT_RATIO` (internal `ln()` memoization via `getOrPutAspectRatio` / `fastLn`)

## Why

For the six bypassed types, measured cost of a single multiply is lower than a full cache-slot lookup; memoization is still provided by **Compose `remember`** (and by call-site batching where used). After the default-AR bypass, typical `.sdpa` cost approaches `.sdp`.

## Pre-computed strategy scale factors (`ScreenFactors`)

`ScreenFactors.updateFactors()` runs **only on configuration changes** and pre-computes:

| Field | Formula |
|---|---|
| `scale` | `sw / 300` |
| `arMultiplier` | `1 + (sw - 300) * (ADJUSTMENT_SCALE + SENSITIVITY_DEFAULT * logNormalizedAr)` |
| `diagonalScale` | `sqrt(sm² + lg²) / BASE_DIAGONAL_DP` |
| `powerScale` | `(sw / BASE_WIDTH_DP) ^ 0.75` |
| `logScale` | `1 ± 0.4 * ln(sw * INV_BASE_RATIO)` |
| `interpolatedScale` | `1 + (sw * INV_BASE_RATIO - 1) * 0.5` |
| `perimeterScale` | `(sm + lg) / BASE_PERIMETER_DP` |
| `aspectRatioMul` | `1 + SENSITIVITY_DEFAULT * logNormalizedAr` |

Each `calculate*Dp` function reads the pre-computed factor from `ScreenFactors` for the **default path** (qualifier = `SMALL_WIDTH`, inverter = `DEFAULT`, `customSensitivityK = null`). Non-default paths still compute inline but avoid `Double` conversions where possible.

## Cached `UiModeType`

`UiModeType.fromConfiguration(context, null)` — which accesses `SensorManager`, hinge sensor lookup, and `WindowMetricsCalculator` — is now cached per configuration hash in `DimenCache.getCachedUiModeType(context)`. The cache is invalidated automatically when the configuration hash changes. All `*Mode` / `*Screen` facilitators across 48 extension files read from this cache.

## Eliminated `Float→Double→Float` conversions

- **Diagonal:** `sqrt((sm² + lg²).toDouble()).toFloat()` eliminated — uses pre-computed `diagonalScale`.
- **Power:** `ratio.toDouble().pow(0.75).toFloat()` eliminated on default path — uses pre-computed `powerScale`. Non-default paths use `Math.pow`.
- **Logarithmic:** raw `kotlin.math.ln()` eliminated on default path — uses pre-computed `logScale`.

## `buildResizeStepsPx` — zero-boxing

`ResizeMath.buildResizeStepsPx` writes directly to a pre-allocated `FloatArray`, avoiding `ArrayList<Float>` boxing/unboxing overhead.

## `Int` / `Float` overloads

`toDynamicScaledPx`, `toDynamicScaledDp`, `sdp`, `hdp`, `wdp` (and their `a`/`i`/`ia` variants) have specialized `Int` and `Float` receiver overloads that avoid `Number.toFloat()` boxing.

## Consumer R8/ProGuard rules

`consumer-rules.pro` keeps the public API surface (`code`, `compose`, `common`, and the listed `core` types). The rest of `core` is not blanket-kept, so R8 may still shrink, obfuscate, and inline internal helpers where no other rule forbids it. (ProGuard’s `-allowoptimization` is not valid for R8 and was removed.)

## Persistence

`DimenCache` writes to a Preferences DataStore with namespace **`com.appdimens.dynamic.cache`**.

| Behaviour | Detail |
|---|---|
| **Quiescence flush** | `debounce(500)` — disk write only after 500 ms without new cache misses (zero I/O during scroll/animation) |
| **Safety-net flush** | `sample(10_000)` — still persists at least every ~10 s under pathological continuous writes |
| **Sparse blob** | Serializes only populated slots (`Int count` + entries), not a fixed `CACHE_SIZE × 12` dump |
| **Config change** | `invalidateOnConfigChange` clears memory **and** the DataStore blob via the `Application` context captured in `init()` |
| **Orientation** | Pure width↔height rotation does **not** clear the cache (`ScreenFactors` are min/max invariant) |

For testing, call **`DimenCache.shutdown()`** to cancel the internal `CoroutineScope` and avoid leaked writes during teardown. Use **`restartSaveCollectorForTest()`** after changing `saveDebounceMs` / `saveSampleMs`.

## Compose recomposition stamps

Packed `Long` remember keys in `ComposeRememberStamps` avoid `Configuration.hashCode()` and slot-table instability:

| Stamp | Keys | Does **not** invalidate on |
|---|---|---|
| `layoutRememberStamp` | orientation, SW, W, H, densityDpi (dpi mixed without overlapping H bits) | locale, keyboard, fontScale |
| `pxRememberStamp` | layout ⊕ physical density | fontScale |
| `spRememberStamp` | layout ⊕ density ⊕ fontScale | — |
| `scaledEntryRememberStamp` | SW/W/H/orientation ⊕ uiMode ⊕ ignoreMultiWindows | densityDpi, aspectRatio float noise |

Related Compose hygiene:

- `*Plain` paths always call `rememberDimen*` (no conditional `remember`)
- `fontScale`-only cache invalidation clears only `SP_NO_SCALE` / `SP_PX_*` entries
- `AppDimensProvider` keys fold UiMode on `state`/`orientation`/`isSeparating`, not the `FoldingFeature` instance
- `DimenResize` uses `spRememberStamp` when bounds are `FixedSp`; Dp inset paths key on `density` bits only

## Benchmarks

Do not use SCALED / PERCENT / DENSITY / DIAGONAL / INTERPOLATED / PERIMETER **without AR** — or **with default AR** — to measure shard-cache throughput; those calls intentionally bypass storage. Use a non-default qualifier, custom sensitivity, or `AUTO`/`FLUID`/`POWER` keys to exercise the cache path.

