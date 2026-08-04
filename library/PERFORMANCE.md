# Performance notes — `DimenCache` & Scaling Engine

## Fast bypass (`getOrPut` / `shouldBypassCache`)

`DimenCache.getOrPut` skips shard storage when `shouldBypassCache(key)` is true — the call reduces to `compute()` (typically one multiply against a factor updated on configuration change).

**Always eligible** (with or without default aspect ratio when the key uses `SMALL_WIDTH` + `DEFAULT` inverter + null custom sensitivity):

- `PERCENT`, `SCALED`, `DENSITY`, `DIAGONAL`, `INTERPOLATED`, `PERIMETER`

**Conditionally eligible** (only `SMALL_WIDTH` + `DEFAULT` inverter — WIDTH/HEIGHT still use the shard cache):

- `POWER`, `LOGARITHMIC`

**Not bypassed:** `AUTO`, `FLUID`, `FIT`, `FILL`, `RESIZE`, `UNITIES`, `ASPECT_RATIO` (ln memoization), and any path with custom sensitivity or non-default qualifier/inverter.

Default aspect ratio (`sdpa` with default settings) uses the same bypass when the type is eligible — `arMultiplier` is already precomputed in `updateFactors()`.

## Pre-computed factors

`DimenCache.updateFactors()` runs on configuration changes and updates **shared** `ScreenFactors` fields only:

| Field | Role |
|---|---|
| `scale` | `sw / 300` |
| `arMultiplier` | Scaled AR adjustment |
| `aspectRatioMul` | Shared AR multiply helper |
| `density` / AR logs / `smallestWidthDp` | From `Configuration` |

Strategy-specific scales (`diagonal`, `power`, `log`, `interpolated`, `perimeter`) live in satellite modules (`DiagonalFactors`, `PowerFactors`, …) and register through `StrategyFactorRegistry`. Absent satellites do no work.

## Invalidation (`invalidateOnConfigChange`)

Uses `ConfigSnapshot` (explicit fields — not a full `Configuration` copy):

| Change | Behavior |
|--------|----------|
| Orientation-only (min/max/SW/dpi unchanged) | Updates factors; **does not** `clearAll` |
| Physical size / density | `clearAll(savedAppContext)` (memory + DataStore) |
| `fontScale` only | `clearFontScaleDependentEntries()` (SP_* value types only) |

## Compose recomposition stamps

- `layoutRememberStamp` packs SW/W/H/orientation + `mixDpi` — **does not** use `Configuration.hashCode()`
- Sp paths use `spRememberStamp` (includes fontScale); px paths use `pxRememberStamp` (density only)
- `rememberDimen*` always runs (stable slots); `match = false` returns `passthrough`

## Cached `UiModeType`

`DimenCache.getCachedUiModeType` uses a fingerprint of `uiMode`, `smallestScreenWidthDp`, and min/max screen dp — not `Configuration.hashCode()`. Facilitators read this cache.

## Persistence

Preferences DataStore namespace `com.appdimens.dynamic.cache`. Write scheduling: `merge(debounce(500ms), sample(10_000ms))`. Serialization is **sparse** (populated slots only); load still accepts legacy dense blobs. Blobs include `KEY_DPI`; SW/dpi mismatch rejects cold-start restore. Call `DimenCache.shutdown()` in tests to cancel the writer scope.

## Other

- Diagonal / Power / Logarithmic **default** paths: satellite precomputed scales when the module is present
- `ResizeMath.buildResizeStepsPx`: pre-allocated `FloatArray` (no boxing)
- Specialized `Int` / `Float` overloads avoid `Number.toFloat()` boxing on hot Scaled paths

## Consumer R8/ProGuard

Each AAR ships `consumer-rules.pro`. Core/scaled rules come from `appdimens-dynamic`; strategy rules from each satellite.

## Benchmarks

Do not use always-bypass types on the default path to measure **shard** throughput. Use custom sensitivity, non-default qualifiers, or non-bypass `CalcType`s (`AUTO`, `FLUID`, …) when measuring cache hits.
