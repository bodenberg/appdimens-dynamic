# Performance notes — `DimenCache` & Scaling Engine

## Fast bypass (`getOrPut`)

When **aspect ratio is off** (cache key bit 63 clear, i.e. `Long` key ≥ 0 in the signed interpretation used in the fast check) and `CalcType` is one of:

- `PERCENT` (ordinal 7)
- `SCALED` (ordinal 11)
- `DENSITY` (ordinal 14)
- `DIAGONAL` (ordinal 1)
- `INTERPOLATED` (ordinal 5)
- `PERIMETER` (ordinal 8)

`DimenCache.getOrPut` **returns `compute()` directly** and does **not** store the result in the shard table. These paths reduce to `baseValue * preComputedFactor` — a single multiplication using a value computed once per configuration change.

All other strategy ordinals (`AUTO`, `FLUID`, `POWER`, `LOGARITHMIC`, `FIT`, `FILL`, …) go through the normal cache path when the cache is enabled.

## Why

For the six bypassed types, measured cost of a single multiply is lower than a full cache-slot lookup; memoization is still provided by **Compose `remember`** (and by call-site batching where used). When **aspect ratio is on**, the computation is heavier and the cache path is used.

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

`consumer-rules.pro` keeps the public API surface while allowing R8 to inline and remove internal helpers (padding fields, shards, etc.) via `-allowoptimization`.

## Persistence

`DimenCache` writes to a Preferences DataStore with namespace **`com.appdimens.dynamic.cache`**. The write flow uses **`sample(500)`** (not `debounce`) so that a first-startup burst of cache misses flushes within 500 ms of the *first* miss, instead of waiting until the burst quiets. For testing, call **`DimenCache.shutdown()`** to cancel the internal `CoroutineScope` and avoid leaked writes during teardown.

## Benchmarks

Do not use SCALED / PERCENT / DENSITY / DIAGONAL / INTERPOLATED / PERIMETER **without** AR to measure cache throughput — those calls intentionally bypass shard storage.
