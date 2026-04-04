# Performance notes — `DimenCache` bypass

## Fast bypass (`getOrPut`)

When **aspect ratio is off** (cache key bit 63 clear, i.e. `Long` key ≥ 0 in the signed interpretation used in the fast check) and `CalcType` is one of:

- `PERCENT` (ordinal 7)
- `SCALED` (ordinal 11)
- `DENSITY` (ordinal 14)

`DimenCache.getOrPut` **returns `compute()` directly** and does **not** store the result in the shard table. Those paths reduce to a cheap multiply (or equivalent) without `ln` / `sqrt` / `pow`.

All other strategy ordinals (`AUTO`, `FLUID`, `POWER`, `LOGARITHMIC`, `INTERPOLATED`, `DIAGONAL`, `PERIMETER`, `FIT`, `FILL`, …) **always** go through the normal cache path when the cache is enabled.

## Why

For the three “simple multiplier” types above, measured cost of a single multiply is lower than a full cache slot lookup; memoization is still provided by **Compose `remember`** (and by call-site batching where used). When **aspect ratio is on**, the computation is heavier and the cache path is used.

## Persistence

`DimenCache` writes to a Preferences DataStore with namespace **`com.appdimens.dynamic.cache`**. The write flow uses **`sample(500)`** (not `debounce`) so that a first-startup burst of cache misses flushes within 500 ms of the *first* miss, instead of waiting until the burst quiets. For testing, call **`DimenCache.shutdown()`** to cancel the internal `CoroutineScope` and avoid leaked writes during teardown.

## Benchmarks

Do not use SCALED / PERCENT / DENSITY **without** AR to measure cache throughput — those calls intentionally bypass shard storage.
