# Technical Performance Report: AppDimens Dynamic — Comparative

This report documents the performance of the **AppDimens Dynamic** library measured **on physical hardware** by the project's benchmark harnesses in **current test runs only** (2026-08-13). Two harnesses produced the data:

- **BenchLab** (`benchlab` module) — 3-way competitor comparison: **Dynamic 3.1.8** × **SDPS 3.1.6** × **Lib #2**, via Benchmark A (Compose probe), Benchmark B (Engine) and the legacy T1/T2/T3 tests.
- **BenchmarkActivity** (`app` module) — Calculation + Micro + Macro dashboard.

> [!NOTE]
> **How to read the numbers**
>
> Every measurement below was captured on **release** builds (`minifyEnabled = true` + R8) of the harnesses, on the same physical device, in the current test sessions. No comparisons against previous library versions or debug builds are included — the data reflects what the current library measured in the current tests.

<p align="center">
  <img src="IMAGES/screenshot_benchmark.jpg" alt="Benchmark dashboard — AppDimens Dynamic" width="200" />
  &nbsp;
  <img src="IMAGES/screenshot_benchmark2.jpg" alt="Benchmark dashboard — additional capture" width="200" />
</p>

---

## 1. Benchmark Harnesses

### 1.1 BenchLab — 3-way competitor comparison

The `benchlab` module runs **two independent benchmarks plus the legacy tests**, headlessly via the `AUTO_START` intent extra:

- **Benchmark A — Compose API (main thread)**: Dynamic 3.1.8 × SDPS 3.1.6 × Lib #2 measured **together inside the same composition** — identical 20,000-iteration warm-up per library, **9 samples × 50,000 iterations** per sample, per-sample order rotation, anti-DCE checksums, two workloads (constant 1dp + mixed values). The measurement is **chunked at 5,000 ops per frame** (one chunk per recomposition), so the main thread never blocks for more than a few dozen ms — no ANR/freeze, no MIUI/thermal throttling corrupting the environment; per-chunk timing excludes inter-chunk gaps.
- **Benchmark B — Engine (`Dispatchers.Default`)**: Dynamic × SDPS only, off the main thread (Lib #2 has no non-Compose API → N/A). Same methodology as A.
- **Legacy T1/T2/T3**: original methodology (mean of 3 passes over 50,000-iteration timing cells), kept for continuity; px resolution values (1/10/100 dp, sdp + sdpa) are captured on every pass.

Results are logged to logcat (`adb logcat -s BENCHLAB`) and shown in the dashboard; the headline number is the **median** (ns/op).

### 1.2 BenchmarkActivity — Calculation + Micro + Macro

The `app` module hosts the production-grade dashboard:

1. **Calculation Test** — 40,000 calls (sw+h+w, +AR, 10,000 iterations × 4 call types).
2. **Microbenchmark (CPU-bound)** — runs off the main thread with 10k warmup + 600k measurement iterations; measures `sdp`, `hdp`, `wdp` (bypass) and `sdpa` (cache) separately, plus single-value with/without AR and direct call-site overhead.
3. **Macrobenchmark (UI-bound)** — real scroll performance in a `LazyColumn` with 1,000 items.

> **Note (Macro):** Wall-clock scroll duration includes the **full round trip** — the scroll down to the last item **and the return scroll up to the first item** (the list is brought back to the first item when the test finishes, and that subida is counted in the measured time). The duration also includes the full cost of each list row — dimension resolution plus Compose composition and drawing.

---

## 2. BenchLab — Current Results (2026-08-13 · release APK + R8)

**Device:** Xiaomi 2107113SG (vili) · sw=393dp w=393dp h=842dp · density 2.75 (1080×2400 @ 440 dpi).

### Methodology

All three libraries were measured under identical conditions: the same physical device, the same session, and the same **release** build of the harness (`minifyEnabled = true` + R8) — no debug builds, no previous versions, no separate sessions per library. The protocol is:

1. **Identical warm-up (discarded)**: 20,000 iterations of `1dp` resolution per library in the same composition (Benchmark A) / same loop shape off-main (Benchmark B), priming the JIT, branch predictors and cache lines before any timing.
2. **9 samples × 50,000 iterations per workload**: constant `1dp` (hot call site) and a **predetermined mixed-value set** (12 dimensions: 1, 4, 8, 10, 12, 16, 20, 24, 32, 48, 64, 100 — no RNG inside the timed region, mirrors a real screen).
3. **Order rotation**: each library occupies each position equally across samples (3 rotating orders in A, alternating order in B), so no library benefits from a consistent position.
4. **Anti-DCE checksums**: every timed loop accumulates its result into a checksum reported with the result, so the JIT cannot eliminate the work.
5. **Chunked measurement (Benchmark A)**: 5,000 ops per composition chunk; per-chunk `System.nanoTime()` summed per sample, excluding inter-chunk gaps.
6. **Timing**: per-call ns = sample elapsed ÷ 50,000; per workload the reported values are the **median of the 9 samples** (min/P90/max also captured).
7. **Legacy T1/T2/T3**: original mean-of-3-passes methodology over 50,000-iteration cells, kept for continuity; px resolution parity (1/10/100 dp, sdp + sdpa) is re-verified on every pass.
8. **Reproducibility**: headless via the `AUTO_START` intent extra; every phase, probe/engine medians, legacy cells and device info are logged to logcat (`adb logcat -s BENCHLAB`).

### Test Scenario

All measurements come from the two harnesses running on the same physical device, in the same session, against the same release configuration:

| Aspect | Scenario |
|---|---|
| **Device** | Xiaomi 2107113SG (vili) · Qualcomm kryo300-class (Snapdragon 888-class, ARMv8.2-A) · max 2.84 GHz |
| **Window** | sw=393dp · w=393dp · h=842dp · density 2.75 (1080×2400 @ 440 dpi) |
| **Build** | Release APK + R8 (`minifyEnabled = true`) on both harnesses |
| **Sessions** | Current test runs only (2026-08-13) — no debug builds, no previous versions |
| **Harness (comparison)** | BenchLab — Benchmark A (Compose probe, chunked 5,000 ops/frame, 540 chunks) + Benchmark B (engine, off-main) + legacy T1/T2/T3, headless via `AUTO_START` |
| **Harness (dashboard)** | BenchmarkActivity — Calculation (40,000 calls), Micro (600,000 ops), Macro (1,000-item scroll), Compare (2 passes) |
| **Measurement hygiene** | Chunked main-thread measurement (max ~50 ms blocks) — no ANR, no frame-skip avalanches, no MIUI/thermal throttling corrupting the environment |
| **Warmup** | Discarded 20,000-iteration warm-up per library before every timed block (Benchmarks A and B) |
| **Fairness** | All libraries measured on the same device/session/build, same dp values, same call shape; px resolution parity verified in every legacy pass |

The T1 → T3 spread within the legacy passes (e.g. sdp 20 → 7 ns) is the ART JIT warming up during the measurement window — the standard release install has no pre-compiled PGO profiles, so the steady-state (hot JIT) row is T3. The captured window is a standard portrait phone window (no split-screen / multi-window active during the runs).

### Benchmark A — Compose API (main thread, chunked) — median per 1dp call

**Constant 1dp:**

| Library | Median | Min | P90 | Max |
| :--- | :---: | :---: | :---: | :---: |
| **Dynamic 3.1.8** | **27.7 ns** | 9.1 ns | 52.7 ns | 82.5 ns |
| SDPS 3.1.6 | 5.27 µs | 4.11 µs | 6.35 µs | 6.51 µs |
| Lib #2 | 1.99 µs | 1.35 µs | 2.14 µs | 2.43 µs |

**Mixed values (12 dimensions):**

| Library | Median | Min | P90 | Max |
| :--- | :---: | :---: | :---: | :---: |
| **Dynamic 3.1.8** | **30.1 ns** | 21.9 ns | 32.4 ns | 115.4 ns |
| SDPS 3.1.6 | 5.43 µs | 4.61 µs | 5.63 µs | 5.82 µs |
| Lib #2 | 1.93 µs | 1.37 µs | 1.97 µs | 2.03 µs |

### Benchmark B — Engine (off main thread) — median per 1dp call

**Constant 1dp:**

| Library | Median |
| :--- | :---: |
| **Dynamic 3.1.8** | **6.83 ns** |
| SDPS 3.1.6 | 3.26 µs |

**Mixed values (12 dimensions):**

| Library | Median |
| :--- | :---: |
| **Dynamic 3.1.8** | **8.09 ns** |
| SDPS 3.1.6 | 3.60 µs |

### Legacy T1/T2/T3 — time per single 1dp call (original methodology, kept for continuity)

**sdp (no AR):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 | Lib #2 |
| :--- | :---: | :---: | :---: |
| **T1** | **20 ns** | 3,399 ns | 1,114 ns |
| **T2** | **7 ns** | 3,273 ns | 1,114 ns |
| **T3** | **7 ns** | 3,278 ns | 1,114 ns |
| **Média** | **11 ns** | **3,316 ns** | **1,114 ns** |

**sdpa (with AR, Dynamic × SDPS):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 |
| :--- | :---: | :---: |
| **T1** | **260 ns** | 3,987 ns |
| **T2** | **61 ns** | 3,637 ns |
| **T3** | **7 ns** | 3,542 ns |
| **Média** | **109 ns** | **3,722 ns** |

### Resolution values (px) — deterministic, identical across all tests and passes

| dp | Dynamic 3.1.8 (sdp) | SDPS 3.1.6 (sdp) | Lib #2 (sdp) | Dynamic (sdpa) | SDPS 3.1.6 (sdpa) |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **1dp** | 3.6025 | 3.6025 | 3.6025 | 3.7289135 | 3.7289138 |
| **10dp** | 36.025 | 36.0249 | 36.025 | 37.289135 | 37.289135 |
| **100dp** | 360.25 | 360.25 | 360.25 | 372.89136 | 372.89206 |

> **How to read**: Dynamic's numbers are the **inlined fast lane** (single float multiply over the coherent per-window snapshot). SDPS (legacy table-based artifact) and Lib #2 (per-call `@Composable` scaling, measured inside composition) pay per-call dispatch/table work, so they measure in the µs range — **Dynamic is ~190× faster than SDPS and ~72× faster than Lib #2 on the Compose-probe constant average** (5,268/27.7; 1,988/27.7), **~477× faster than SDPS off-main** (3,260/6.83) and **~301× vs SDPS / ~101× vs Lib #2 on the legacy average** (3,316/11; 1,114/11). The off-main engine numbers (7–8 ns) are lower than the in-composition probe (27–30 ns) because the composition environment carries per-frame/JIT overhead — both measure the same call shape.

---

## 3. BenchmarkActivity — Current Results (2026-08-13 · release APK + R8)

**Device:** Xiaomi 2107113SG (vili) · sw=393dp w=393dp h=842dp · density 2.75 (1080×2400 @ 440 dpi).

### Calculation Test (Scaled: sw+h+w, +AR, 40,000 calls)

| Metric | Result |
| :--- | :---: |
| **Avg resolution** | **~32–91 ns** (latest on-screen value: **32 ns**) |

### Microbenchmark (600,000 ops, thermal-ramped)

| Path | Result (per op) |
| :--- | :---: |
| **Combined avg** | **~29–39 ns** |
| sdp (bypass) | 24–49 ns |
| hdp (bypass) | 31–42 ns |
| wdp (bypass) | 23–38 ns |
| sdpa (cache) | 24–38 ns |
| single value (no AR) | 30–41 ns |
| single value (+ AR) | 30–42 ns |
| direct ext `100.sdp(ctx)` | 7–13 ns |
| direct api `DimenSdp.sdp` | 8–26 ns |

> The T1→T3 spread within each round (e.g. sdp 26 → 5 ns) is the ART JIT warming up — the hot steady-state is the T3 row.

### Macrobenchmark — 1,000-item scroll

> [!NOTE]
> **The measured scroll duration includes the full round trip: scroll down to the last item AND scroll back up to the first item.** The list is returned to the first item when the test finishes; that return trip (subida) is part of the reported time.

| Metric | Result |
| :--- | :---: |
| **Scroll duration (down + up)** | **~1,490 ms** (1,488–1,509 ms across runs) |
| Avg frame | ~1.5 ms |
| P90 frame | ~2.2–2.3 ms |
| P99 frame | ~3.0 ms |

> **Measurement**: same device, same release APK + R8, same session as the BenchLab runs. The comparison runs off the main thread: 5,000 warmup calls are discarded, then each timing cell is 50,000 single-1 dp calls (`System.nanoTime()` ÷ 50,000) across **2 independent test passes**, for both `sdp` and `sdpa`. Resolution values (1/10/100 dp) are captured in the same passes.

### Compare (Dynamic × Concorrente 1, per single 1dp call)

| Test | Dynamic | Concorrente 1 |
| :--- | :---: | :---: |
| **#1** | 25 ns | 3,059 ns |
| **#2** | 22 ns | 2,748 ns |
| **Média** | **23 ns** | **2,903 ns** |

> **How to read**: Dynamic is **~126× faster** than Concorrente 1 on the per-call average in this harness (2,903/23).

---

## 4. Optimization Analysis

The hot-path design behind these numbers:

### F1 — Public getBatch()

The batch API exposes a continuous loop that the JIT/ART can optimize aggressively. On Android, the gain is largely about amortizing context and init work across N resolutions.

**Recommended usage:**
```kotlin
val keys = LongArray(views.size) { i ->
    DimenCache.buildKey(values[i].toFloat(), isLandscape,
        false, DimenCache.CalcType.SCALED, DpQualifier.SMALL_WIDTH,
        Inverter.DEFAULT, false, DimenCache.ValueType.PX)
}
val results = DimenCache.getBatch(keys, context) { i -> computeDimension(i) }
```

### F2 — Snapshot-Partitioned Cache (anti-false-sharing)

The in-memory cache is **partitioned per snapshot**: each immutable `DimenMetrics` window snapshot owns a bounded `AtomicReferenceArray` (entries published as single atomic `CacheEntry` references), eliminating cross-core cache-line invalidation between threads — a key concern on octa-core devices.

### F3 — ScreenFactors (@Volatile Padding)

The shared `@Volatile` fields (`scale`, `arMultiplier`, `aspectRatioMul`, `normalizedAr`, `logNormalizedAr`, `density`, `smallestWidthDp`) sit on isolated cache lines, preventing sporadic jank from cross-core invalidation on configuration change.

### F4 — clearAll() detaches partitions atomically

`clearAll()` detaches all snapshot partitions (one `ConcurrentHashMap.clear()` with an atomic bootstrap) — no per-entry zeroing on the dimension path.

### F5–F7 — Fast-lane kernels

- **F5 Specialized kernels**: `resolveSdpPx`, `resolveSdpaPx`, `resolveHdpPx`, `resolveWdpPx` (+ DP variants) — zero branches, volatile load + identity compare + legacy multiply order.
- **F6 `fastMetricsForCode`**: non-Compose fast lane skips the ThreadLocal probe — one volatile load, one identity compare.
- **F7 DimenMetrics eager AR**: `normalizedAspectRatio` / `logNormalizedAspectRatio` are plain `val`s — no hidden synchronized probe on the SDPA fast lane.

---

## 5. Test Integrity

```
✅ DimenCacheTest         — 5/5 tests passed
✅ DimenPerformanceTest   — executed successfully (local JVM)
✅ ExampleUnitTest        — passes
✅ DimenAndroidPerformanceTest — 2/2 tests on physical device
✅ ExampleInstrumentedTest    — passes
✅ BenchmarkActivity      — executed successfully on physical device
✅ BenchLab               — Benchmark A (Compose) + Benchmark B (Engine) + legacy T1/T2/T3 executed successfully on physical device (release + R8, headless AUTO_START)
```

---

## 6. Simple Calculations Faster Than Cache

For eligible `CalcType`s on the default path (`shouldBypassCache`), `getOrPut` returns `compute()` without touching the snapshot cache. `AUTO` / `FLUID` / `FIT` / `FILL` always use the cache.

> Measured on-device: **~2 ns** (multiply) vs **~5 ns** (hash + atomic lookup).

| Path | Cost | Cache? |
|:---|:---:|:---:|
| SCALED default path | ~2 ns | ❌ Bypass |
| SCALED custom sensitivity / non-default qualifier | varies | ✅ Cache |
| POWER / LOG on SW+DEFAULT | ~2 ns | ❌ Bypass |
| AUTO / FLUID | lookup + compute | ✅ Cache |

**Consequence for benchmarks**: default `sdp` / `hdp` / `wdp` measure raw math. Use non-bypass paths to measure snapshot-partition throughput.

---

## 7. Benchmark Variability

All numbers in this document were captured on a **Xiaomi 2107113SG (vili · Qualcomm kryo300-class / Snapdragon 888-class · 2.84 GHz max)**. Real-world results will differ based on:

- **Device class**: budget Cortex-A55 cores can be 5–10× slower on atomic operations
- **JIT stage**: cold start is 3–10× slower than steady-state hot JIT
- **ART PGO**: pre-compiled `.prof` profiles skip cold JIT
- **Background load**: GC pressure and CPU governor affect ns measurements
- **Cache fill state**: first access after a physical-size `clearAll()` is a miss; orientation-only config changes do **not** clear the cache
- **Measurement hygiene**: the harness holds `THREAD_PRIORITY_URGENT_AUDIO` + a 1.5 s thermal ramp for the whole measurement window

Treat figures as reference points, not guarantees.

---

*Report generated on: 2026-08-13 · AppDimens Dynamic Performance Lab · Xiaomi 2107113SG (vili · Qualcomm kryo300-class · 2.84 GHz max) · Physical Hardware — release APK + R8 · Data from current test runs: BenchLab (§2) and BenchmarkActivity (§3) · JVM 17 host*
*Compiled with: Kotlin 2.x · JVM 17 · ART · Gradle 9.x*
