# Performance notes — `DimenCache` & Scaling Engine

## Fast bypass (`getOrPut` / `shouldBypassCache`)

`DimenCache.getOrPut` skips shard storage when `shouldBypassCache(key)` is true — the call reduces to `compute()` (typically one multiply against a factor updated on configuration change).

**Always eligible** (with or without default aspect ratio when the key uses `SMALL_WIDTH` + `DEFAULT` inverter + null custom sensitivity):

- `PERCENT`, `SCALED`, `DENSITY`, `DIAGONAL`, `INTERPOLATED`, `PERIMETER`

**Conditionally eligible** (only `SMALL_WIDTH` + `DEFAULT` inverter — WIDTH/HEIGHT still use the shard cache):

- `POWER`, `LOGARITHMIC`

**Not bypassed:** `AUTO`, `FLUID`, `FIT`, `FILL`, `RESIZE`, `UNITIES`, `ASPECT_RATIO` (ln memoization), and any path with custom sensitivity or non-default qualifier/inverter.

Default aspect ratio (`sdpa` with default settings) uses the same bypass when the type is eligible — the multiplier is already derived in the `DimenMetrics` snapshot.

## Pre-computed factors

The source of truth is the immutable **`DimenMetrics`** window snapshot, built once per window/configuration change. Derived factors are computed once when the snapshot is created; `DimenCache.updateFactors()` and the legacy `ScreenFactors` fields are retained only for source compatibility:

| `DimenMetrics` derived field | Role |
|---|---|
| `scale` | `sw / 300` |
| `defaultScaledAspectRatioMultiplier` | Scaled AR adjustment |
| `defaultAspectRatioMultiplier` | Shared AR multiply helper |
| `density` / `logNormalizedAspectRatio` / `smallestWidthDp` | From the snapshot's `Configuration` |

Strategy-specific scales (`diagonal`, `power`, `log`, `interpolated`, `perimeter`) live in satellite modules (`DiagonalFactors`, `PowerFactors`, …) and are derived lazily from `DimenCache.currentMetrics` at resolution time. Absent satellites do no work, and `StrategyFactorRegistry` is kept as a compatibility hook only.

## Cache partitioning & invalidation

The in-memory cache is **partitioned per snapshot** (`ConcurrentHashMap<DimenMetrics, SnapshotCache>`): a key is only ever served from the partition of the exact window it was computed for (size, density, font scale, ui mode, multi-window state all participate in the snapshot).

| Situation | Behavior |
|--------|----------|
| Orientation swap / resize / density / font change | New `DimenMetrics` partition; a partition is evicted when the budget is full (bounded: 4 × 512 = 2048 slots) |
| `invalidateOnConfigChange(newConfig)` | Compatibility hook; refreshes the fallback snapshot and **does not** wipe other windows' hot entries |
| `clearAll(context)` / `clearFontScaleDependentEntries()` | Detach all partitions atomically (no disk I/O) |

## Compose recomposition stamps

- `layoutRememberStamp` packs SW/W/H/orientation + `mixDpi` — **does not** use `Configuration.hashCode()`
- Sp paths use `spRememberStamp` (includes fontScale); px paths use `pxRememberStamp` (density only)
- `rememberDimen*` in 3.1.7 remembers on **two keys** (`cacheKey`, stamp) when `match = true`; `match = false` returns `passthrough` immediately
- `AppDimensProvider` provides `LocalDimenMetrics`, keeping every dimension in a composition on one coherent window snapshot

## Cached `UiModeType`

`DimenCache.getCachedUiModeType` fingerprints `uiMode`, `smallestScreenWidthDp`, min/max screen dp and `densityDpi` — not `Configuration.hashCode()` — and stores per-`Context` entries in a weak map (no Activity leak). Facilitators read this cache.

## Persistence

**Removed in 3.1.7.** `DimenCache` no longer persists results to Preferences DataStore. `shutdown()`, `restartSaveCollectorForTest()` and `persistenceWritesEnabled` remain as no-op compatibility hooks for old test fixtures; there is no background writer scope and no disk I/O on the dimension path.

## Other

- Diagonal / Power / Logarithmic **default** paths: satellite scales derived from the current window snapshot
- `ResizeMath.buildResizeStepsPx`: pre-allocated `FloatArray` (no boxing)
- Specialized `Int` / `Float` overloads avoid `Number.toFloat()` boxing on hot Scaled paths

## Consumer R8/ProGuard

Each AAR ships `consumer-rules.pro`. Core/scaled rules come from `appdimens-dynamic`; strategy rules from each satellite.

## Benchmarks

Do not use always-bypass types on the default path to measure **shard** throughput. Use custom sensitivity, non-default qualifiers, or non-bypass `CalcType`s (`AUTO`, `FLUID`, …) when measuring cache hits.
