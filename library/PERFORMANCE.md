# Performance notes — `DimenCache` & Scaling Engine

## Fast bypass (`getOrPut` / `shouldBypassCache`)

`DimenCache.getOrPut` skips snapshot-cache storage when `shouldBypassCache(key)` is true — the call reduces to `compute()` (typically one multiply against a factor updated on configuration change).

**Always eligible** (with or without default aspect ratio when the key uses `SMALL_WIDTH` + `DEFAULT` inverter + null custom sensitivity):

- `PERCENT`, `SCALED`, `DENSITY`, `DIAGONAL`, `INTERPOLATED`, `PERIMETER`

**Conditionally eligible** (only `SMALL_WIDTH` + `DEFAULT` inverter — WIDTH/HEIGHT still use the snapshot cache):

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
- `rememberDimen*` in 3.1.8 remembers on **two keys** (`cacheKey`, stamp) when `match = true`; `match = false` returns `passthrough` immediately
- `AppDimensProvider` provides `LocalDimenMetrics`, keeping every dimension in a composition on one coherent window snapshot

## Cached `UiModeType`

`DimenCache.getCachedUiModeType` fingerprints `uiMode`, `smallestScreenWidthDp`, min/max screen dp and `densityDpi` — not `Configuration.hashCode()` — and stores per-`Context` entries in a weak map (no Activity leak). Facilitators read this cache.

## Persistence

**Removed in 3.1.8.** `DimenCache` no longer persists results to Preferences DataStore. `shutdown()`, `saveToPersistence()` and `serializeToByteArray()` remain as **binary-compatibility no-ops** for consumers built against ≤ 3.1.6; there is no background writer scope and no disk I/O on the dimension path.

## Other

- Diagonal / Power / Logarithmic **default** paths: satellite scales derived from the current window snapshot
- `ResizeMath.buildResizeStepsPx`: pre-allocated `FloatArray` (no boxing)
- Specialized `Int` / `Float` overloads avoid `Number.toFloat()` boxing on hot Scaled paths

## Consumer R8/ProGuard

Each AAR ships `consumer-rules.pro`. Core/scaled rules come from `appdimens-dynamic`; strategy rules from each satellite.

## Benchmarks

Do not use always-bypass types on the default path to measure **snapshot-cache** throughput. Use custom sensitivity, non-default qualifiers, or non-bypass `CalcType`s (`AUTO`, `FLUID`, …) when measuring cache hits.

### Measured numbers (2026-08-09 — Xiaomi 2107113SG, release APK + AOT `speed`, 3 runs)

With the **3.1.8 fast lane**, the dominant resolutions (`sdp` / `hdp` / `wdp`, and `sdpa` = SMALL_WIDTH + AR) are a single float multiply over the coherent per-window `DimenMetrics` snapshot — no key encoding, no `getOrPut`, no `remember` machinery:

- **Micro combined avg**: ~40–64 ns/op (typical ~50–60; families uniform within ~8 ns of each other).
- **Same-device baseline** (3 runs each): library 3.1.5 ≈ 158 ns/op; **debug APK ≈ 508–857 ns/op** (interpreter — debuggable APKs are pinned to compiler filter `verify`).
- **Macro scroll (1k-item LazyColumn)**: ~368–382 ms — frame-limited (366 ms floor at 60 fps), ≈ 4% under 3.1.5's ~393 ms and ~2.4× under the ~940 ms debug APK.
- **Validation**: the fast lane uses event-driven config validation (`ensureConfigWatcher`) — a `ComponentCallbacks2` listener registered on the Application invalidates fast slots synchronously on any real configuration change. This replaces the previous sampled `validationTick` (1-in-16) with zero sampling cost on the hot lane. A ThreadLocal variant was explored and **rejected**: two hash-table lookups per call lost to the single acquire-load + release-store on a core-local cache line in the single-threaded main-thread case, and added cold-start cost.
- **Specialized kernels**: `resolveSdpPx`, `resolveSdpDp`, `resolveSdpaPx`, `resolveSdpaDp`, `resolveHdpPx`, `resolveHdpDp`, `resolveWdpPx`, `resolveWdpDp` — one kernel per family/qualifier, zero branches, volatile load + identity compare + legacy multiply order — bit-identical results to the full path.
- **Non-Compose fast lane**: `fastMetricsForCode` skips the ThreadLocal probe entirely — one volatile load, one identity compare, two float multiplies on the hit path.
- **DimenMetrics eager AR**: `normalizedAspectRatio` and `logNormalizedAspectRatio` changed from `lazy` to plain `val` — removes the hidden `synchronized` probe from the SDPA fast lane.
- **Reproducibility**: the harness holds `THREAD_PRIORITY_URGENT_AUDIO` plus a 1.5 s thermal ramp for the whole measurement window; without it cold-core artifacts inflate the first family by ~100 ns and run-to-run spread to 2–4×. On the Redmi 25062RN2DA (Android 16/SDK 36, same SoC) the release numbers are ~117–118 ns/op — still ~10× the fast-lane floor of the multiply itself.
