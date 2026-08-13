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

### Measured numbers (2026-08-13 — Xiaomi 2107113SG, release APK + R8, current test runs)

With the **fast lane**, the dominant resolutions (`sdp` / `hdp` / `wdp`, and `sdpa` = SMALL_WIDTH + AR) are a single float multiply over the coherent per-window `DimenMetrics` snapshot — no key encoding, no `getOrPut`, no `remember` machinery. Data below comes from the **current test runs** of the two harnesses:

**BenchLab** (3-way competitor: Dynamic vs Concorrente 1 vs Concorrente 2, 3 test passes × 2 rounds):

- **Time per single 1dp call (sdp), Round 1**: Dynamic T1/T2/T3 = 26 / 11 / 5 ns vs Concorrente 1 3,392 / 2,934 / 2,754 ns vs Concorrente 2 1,205 ns → **avg 14 ns vs 3,026 ns vs 1,205 ns**.
- **Time per single 1dp call (sdp), Round 2**: Dynamic T1/T2/T3 = 26 / 14 / 5 ns vs Concorrente 1 3,330 / 2,989 / 2,738 ns vs Concorrente 2 1,141 ns → **avg 15 ns vs 3,019 ns vs 1,141 ns**.
- **Time per single 1dp call (sdpa/AR)**: Round 1 avg **71 ns** vs Concorrente 1 **2,949 ns**; Round 2 avg **82 ns** vs Concorrente 1 **2,969 ns**.
- **Resolution parity (px, deterministic across all tests and both rounds)**: sdp 1/10/100dp = 3.6025 / 36.025 / 360.25 px on all three libraries; sdpa 1/10/100dp = Dynamic 3.7289135 / 37.289135 / 372.89136 vs Concorrente 1 3.7289138 / 37.289135 / 372.89206.
- **Device**: Xiaomi 2107113SG (Redmi Note 11) · sw=393dp w=393dp h=842dp · density 2.75.

**BenchmarkActivity** (Calculation + Micro + Macro, same device):

- **Calculation Test** (40,000 calls): avg resolution **~32–91 ns** (latest on-screen value: **32 ns**).
- **Micro combined avg**: **~29–39 ns/op**; families sdp 24–49, hdp 31–42, wdp 23–38, sdpa 24–38; single value no-AR 30–41, +AR 30–42; direct ext `100.sdp(ctx)` 7–13, direct api 8–26 ns.
- **Macro scroll (1k-item LazyColumn)**: **~1,490 ms** — includes the **full round trip** (scroll down to the last item and back up to the first item); the return trip (subida) is counted in the measured duration.
- **Compare (Dynamic × Concorrente 1)**: avg **23 ns vs 2,903 ns** → **~126×**.

**Fast-lane validation**: the fast lane uses event-driven config validation (`ensureConfigWatcher`) — a `ComponentCallbacks2` listener registered on the Application invalidates fast slots synchronously on any real configuration change. A ThreadLocal variant was explored and **rejected**: two hash-table lookups per call lost to the single acquire-load + release-store on a core-local cache line in the single-threaded main-thread case, and added cold-start cost.

**Specialized kernels**: `resolveSdpPx`, `resolveSdpDp`, `resolveSdpaPx`, `resolveSdpaDp`, `resolveHdpPx`, `resolveHdpDp`, `resolveWdpPx`, `resolveWdpDp` — one kernel per family/qualifier, zero branches, volatile load + identity compare + legacy multiply order — bit-identical results to the full path.

**Non-Compose fast lane**: `fastMetricsForCode` skips the ThreadLocal probe entirely — one volatile load, one identity compare, two float multiplies on the hit path.

**DimenMetrics eager AR**: `normalizedAspectRatio` and `logNormalizedAspectRatio` are plain `val`s — no hidden synchronized probe on the SDPA fast lane.

**Reproducibility**: the harnesses hold `THREAD_PRIORITY_URGENT_AUDIO` plus a 1.5 s thermal ramp for the whole measurement window; without it cold-core artifacts inflate the first family by ~100 ns and run-to-run spread to 2–4×.

See [PERFORMANCE.md §2](../PERFORMANCE.md) / [PERFORMANCE-COMPARATIVE.md §2–3](../PERFORMANCE-COMPARATIVE.md) for the full tables and reading guidance.
