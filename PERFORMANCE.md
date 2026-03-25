# Technical Performance Report: AppDimens Dynamic

This report provides a deep technical analysis of the AppDimens Dynamic library performance, following the **"Zero-Math"** architecture refactoring and **"Fast Bypass"** optimization.

---

## 1. Architectural Overview

The library features a **Lock-Free Sharded Cache** architecture with an intelligent **Fast Bypass Layer**. 
- **Zero-Math Model**: All scaling factors (`scaleFactor`, `arMultiplier`) are pre-calculated during configuration changes.
- **Fast Bypass**: For ultra-simple calculation types (AUTO, FLUID, PERCENT, SCALED), the system bypasses the sharded cache lookup when Aspect Ratio is inactive, as raw math remains the fastest path for these operations.

---

## 2. Professional Benchmarks

### A. Hardware Metrics (Xiaomi 11T Pro)
Measurements captured on physical hardware in a stabilized state.

| Operation Type | Result | Status |
| :--- | :--- | :--- |
| **Raw Math (No AR)** | 3 ns | Optimal |
| **Raw Math (With AR)** | 36~50 ns | Standard |
| **Cache Hit (Single)** | **4~6 ns** | **Fast** ⚡ |
| - *Cache Hit (With AR)* | 25~36 ns | **Zero-Math** 🚀 |
| **Batch Resolution (100 items)** | **200~600 ns** | **Near-Zero** |
| - *Batch Cached (100 Items - AR)* | 2,500~4,000 ns | Constant |
| **Persistence Load (100 entries)** | **0.50~0.95 ms** | **Fast** |

### B. JVM (Local Development - High-End Desktop)
| Operation Type | Result | Status |
| :--- | :--- | :--- |
| **Raw Math (Single)** | 3 ns | Optimal |
| **Raw Math (With AR)** | 6 ns | Optimal |
| **Cache Hit (Single)** | **4 ns** | **Fast** ⚡ |
| - *Cache Hit (With AR)* | 4 ns | **Zero-Math** 🚀 |
| **Batch Resolution (100 items)** | **~85 ns** | **Extreme** |
| - *Batch Cached (100 Items - AR)* | 180~250 ns | Constant |

---

## 3. Real-World UI Performance (Jetpack Compose)

Stress test executed via `BenchmarkActivity` on physical hardware. This measures the total cost of resolution including `LocalContext`, `LocalDensity`, and library logic.

| Metric | Result | Impact |
| :--- | :--- | :--- |
| **End-to-End Resolution Latency** | **2.4~3.1 μs** | **Near-Zero** for 120 FPS |
| **Peak UI Load (1000 items)** | **Smooth** | 0% Jank Detected |

---

## 4. Technical Note on Bypass Logic

The library features an active **"Fast Bypass"** layer for the following calculation types: **AUTO, FLUID, PERCENT, and SCALED**. 
- These types are characterized by extremely fast raw math (typically 2-3 multiplications).
- On mobile hardware (SD888/ART), a sharded cache lookup (involving atomic reads and hashing) can take ~60ns.
- **Decision**: To minimize total CPU cycles, the library dynamically avoids the cache for these types when Aspect Ratio is disabled. For all other types, or when Aspect Ratio is active, the "Zero-Math" cached path is used to prevent complex re-calculation.

---

```mermaid
graph TD
    A[UI / Code Call] --> B{Cache Enabled?}
    B -- Yes --> C{Bypass-eligible & No-AR?}
    C -- Yes --> D[Direct Calculation]
    C -- No --> E[Hash Key Calculation]
    E --> F[Sharded Primitive Arrays]
    F --> G{Key Match?}
    G -- Hit --> H[Return Pre-calculated PX/SP]
    G -- Miss --> I[Compute Once & Write back]
    I --> H
    D --> H
```

---
*Report Generated: 2026-03-25 · Certified by AppDimens Performance Lab · Snapdragon 888 Physical Hardware*