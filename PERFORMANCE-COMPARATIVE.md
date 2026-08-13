# Technical Performance Report: AppDimens Dynamic — Comparative

This report documents the performance of the **AppDimens Dynamic** library measured **on physical hardware** by the project's benchmark harnesses in **current test runs only** (2026-08-13). Two harnesses produced the data:

- **BenchLab** (`benchlab` module) — 3-way competitor comparison: **Dynamic 3.1.8** × **SDPS 3.1.6** (published legacy artifact) × **Chaintech SDP-SSP Compose Multiplatform 1.0.7**.
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

The `benchlab` module runs **3 independent test passes (T1/T2/T3) × 2 full rounds**, 50,000 iterations per timing cell, headlessly via the `AUTO_START` intent extra. Each test captures dp resolution values (1dp/10dp/100dp, sdp + sdpa) and time per single 1dp call for all three libraries. Results are logged to logcat (`adb logcat -s BENCHLAB`) and shown in the dashboard.

### 1.2 BenchmarkActivity — Calculation + Micro + Macro

The `app` module hosts the production-grade dashboard:

1. **Calculation Test** — 40,000 calls (sw+h+w, +AR, 10,000 iterations × 4 call types).
2. **Microbenchmark (CPU-bound)** — runs off the main thread with 10k warmup + 600k measurement iterations; measures `sdp`, `hdp`, `wdp` (bypass) and `sdpa` (cache) separately, plus single-value with/without AR and direct call-site overhead.
3. **Macrobenchmark (UI-bound)** — real scroll performance in a `LazyColumn` with 1,000 items.

> **Note (Macro):** Wall-clock scroll duration includes the **full round trip** — the scroll down to the last item **and the return scroll up to the first item** (the list is brought back to the first item when the test finishes, and that subida is counted in the measured time). The duration also includes the full cost of each list row — dimension resolution plus Compose composition and drawing.

---

## 2. BenchLab — Current Results (2026-08-13 · release APK + R8)

**Device:** Xiaomi 2107113SG (Redmi Note 11) · sw=393dp w=393dp h=842dp · density 2.75 (1080×2400 @ 440 dpi).

### Round 1 — time per single 1dp call

**sdp (no AR):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 | Chaintech 1.0.7 |
| :--- | :---: | :---: | :---: |
| **T1** | **26 ns** | 3,392 ns | 1,205 ns |
| **T2** | **11 ns** | 2,934 ns | 1,205 ns |
| **T3** | **5 ns** | 2,754 ns | 1,205 ns |
| **Média** | **14 ns** | **3,026 ns** | **1,205 ns** |

**sdpa (with AR, Dynamic × SDPS):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 |
| :--- | :---: | :---: |
| **T1** | **167 ns** | 3,225 ns |
| **T2** | **42 ns** | 2,915 ns |
| **T3** | **5 ns** | 2,708 ns |
| **Média** | **71 ns** | **2,949 ns** |

### Round 2 — time per single 1dp call

**sdp (no AR):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 | Chaintech 1.0.7 |
| :--- | :---: | :---: | :---: |
| **T1** | **26 ns** | 3,330 ns | 1,141 ns |
| **T2** | **14 ns** | 2,989 ns | 1,141 ns |
| **T3** | **5 ns** | 2,738 ns | 1,141 ns |
| **Média** | **15 ns** | **3,019 ns** | **1,141 ns** |

**sdpa (with AR, Dynamic × SDPS):**

| Test | Dynamic 3.1.8 | SDPS 3.1.6 |
| :--- | :---: | :---: |
| **T1** | **180 ns** | 3,241 ns |
| **T2** | **62 ns** | 2,945 ns |
| **T3** | **5 ns** | 2,723 ns |
| **Média** | **82 ns** | **2,969 ns** |

### Resolution values (px) — deterministic, identical across all tests and both rounds

| dp | Dynamic 3.1.8 (sdp) | SDPS 3.1.6 (sdp) | Chaintech (sdp) | Dynamic (sdpa) | SDPS (sdpa) |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **1dp** | 3.6025 | 3.6025 | 3.6025 | 3.7289135 | 3.7289138 |
| **10dp** | 36.025 | 36.0249 | 36.025 | 37.289135 | 37.289135 |
| **100dp** | 360.25 | 360.25 | 360.25 | 372.89136 | 372.89206 |

> **How to read**: Dynamic's numbers are the **inlined fast lane** (single float multiply over the coherent per-window snapshot). SDPS 3.1.6 (legacy table-based artifact) and Chaintech (per-call `@Composable` scaling, measured inside composition) pay per-call dispatch/table work — µs range. **Dynamic is ~75–215× faster on the sdp average across rounds** (e.g. Round 2: 3,019/15 ≈ 201× vs SDPS, 1,141/15 ≈ 76× vs Chaintech).

---

## 3. BenchmarkActivity — Current Results (2026-08-13 · release APK + R8)

**Device:** Xiaomi 2107113SG (Redmi Note 11) · sw=393dp w=393dp h=842dp · density 2.75 (1080×2400 @ 440 dpi).

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

### Compare (Dynamic × SDPS, per single 1dp call)

| Test | Dynamic | SDPS |
| :--- | :---: | :---: |
| **#1** | 25 ns | 3,059 ns |
| **#2** | 22 ns | 2,748 ns |
| **Média** | **23 ns** | **2,903 ns** |

> **How to read**: Dynamic is **~126× faster** than SDPS on the per-call average in this harness (2,903/23).

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
✅ BenchLab               — 3 tests × 2 rounds executed successfully (release + R8)
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

All numbers in this document were captured on a **Xiaomi 2107113SG (Redmi Note 11 · Qualcomm bengal / Snapdragon 680-class · 2.8 GHz max)**. Real-world results will differ based on:

- **Device class**: budget Cortex-A55 cores can be 5–10× slower on atomic operations
- **JIT stage**: cold start is 3–10× slower than steady-state hot JIT
- **ART PGO**: pre-compiled `.prof` profiles skip cold JIT
- **Background load**: GC pressure and CPU governor affect ns measurements
- **Cache fill state**: first access after a physical-size `clearAll()` is a miss; orientation-only config changes do **not** clear the cache
- **Measurement hygiene**: the harness holds `THREAD_PRIORITY_URGENT_AUDIO` + a 1.5 s thermal ramp for the whole measurement window

Treat figures as reference points, not guarantees.

---

*Report generated on: 2026-08-13 · AppDimens Dynamic Performance Lab · Xiaomi 2107113SG (Qualcomm bengal · 2.8 GHz max) · Physical Hardware — release APK + R8 · Data from current test runs: BenchLab (§2) and BenchmarkActivity (§3) · JVM 17 host*
*Compiled with: Kotlin 2.x · JVM 17 · ART · Gradle 9.x*
