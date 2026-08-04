# Performance notes — `DimenCache` & Scaling Engine

> **Packaging (3.1.6):** shared cache/factors live in the principal AAR (`appdimens-dynamic`). Strategy-specific scales register via `StrategyFactorRegistry` only when the matching satellite is on the classpath. See [MODULES.md](../DOCUMENTATION/MODULES.md).

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

## Pre-computed screen factors (`ScreenFactors` + satellite registry)

`DimenCache.updateFactors()` runs **only on configuration changes** and pre-computes **shared** fields:

| Field | Formula |
|---|---|
| `scale` | `sw / 300` |
| `arMultiplier` | `1 + (sw - 300) * (ADJUSTMENT_SCALE + SENSITIVITY_DEFAULT * logNormalizedAr)` |
| `aspectRatioMul` | `1 + SENSITIVITY_DEFAULT * logNormalizedAr` |
| `density` / AR logs | from `Configuration` |

Strategy-specific scales (`diagonal` / `power` / `log` / `interpolated` / `perimeter`) register via `StrategyFactorRegistry` inside each satellite module. If the satellite is not on the classpath, that work is never scheduled. Default paths read the satellite’s cached scale; non-default paths still compute inline.

## Cached `UiModeType`

`UiModeType.fromConfiguration(context, null)` — which accesses `SensorManager`, hinge sensor lookup, and `WindowMetricsCalculator` — is now cached per configuration hash in `DimenCache.getCachedUiModeType(context)`. The cache is invalidated automatically when the configuration hash changes. All `*Mode` / `*Screen` facilitators across strategy modules read from this cache.

## Eliminated `Float→Double→Float` conversions

- **Diagonal / Power / Logarithmic default paths:** use satellite-registered precomputed scales (updated only when that module is present).
- Non-default qualifier/inverter paths may still use `Math.pow` / `ln` inline.
## `buildResizeStepsPx` — zero-boxing

`ResizeMath.buildResizeStepsPx` writes directly to a pre-allocated `FloatArray`, avoiding `ArrayList<Float>` boxing/unboxing overhead.

## `Int` / `Float` overloads

`toDynamicScaledPx`, `toDynamicScaledDp`, `sdp`, `hdp`, `wdp` (and their `a`/`i`/`ia` variants) have specialized `Int` and `Float` receiver overloads that avoid `Number.toFloat()` boxing.

## Consumer R8/ProGuard rules

`consumer-rules.pro` keeps the public API surface (`code`, `compose`, `common`, and the listed `core` types). The rest of `core` is not blanket-kept, so R8 may still shrink, obfuscate, and inline internal helpers where no other rule forbids it. (ProGuard’s `-allowoptimization` is not valid for R8 and was removed.)

## Persistence

`DimenCache` writes to a Preferences DataStore with namespace **`com.appdimens.dynamic.cache`**. The write flow uses **`sample(500)`** (not `debounce`) so that a first-startup burst of cache misses flushes within 500 ms of the *first* miss, instead of waiting until the burst quiets. For testing, call **`DimenCache.shutdown()`** to cancel the internal `CoroutineScope` and avoid leaked writes during teardown.

## Benchmarks

Do not use SCALED / PERCENT / DENSITY / DIAGONAL / INTERPOLATED / PERIMETER **without** AR to measure cache throughput — those calls intentionally bypass shard storage.
