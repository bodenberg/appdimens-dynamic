//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)

# AspectRatioLookup

object [AspectRatioLookup](index.md)

EN Pre-computed binary-search lookup table for `ln(ar / 1.78f)`.

**Why this exists:** The aspect-ratio scaling path inside `toDynamicScaledDp` / `toDynamicScaledSp` needs to call `ln(currentAr / 1.78f)` on every configuration change. Although the outer `remember()` block already avoids recomputing during frame rendering, a configuration change (e.g. orientation flip, multi-window resize, font-scale toggle) still forces a full recomposition for every dimension that uses the aspect-ratio path.

Replacing `ln()` (a transcendental CPU instruction) with a **O(log n) binary search** over a contiguous `FloatArray` yields:

No boxing overhead (primitive `FloatArray` vs `HashMap<Float, Float>`)

Better CPU cache locality (48 contiguous bytes per 12 floats)

~7-8× fewer comparisons on cache miss (O(log 160) ≈ 7 vs O(n) up to 160)

Expected hit-rate > 95 % for production devices

The table stores `(ar / 1.78f)` keys so only **one division** is needed before the lookup, and the value is the ready-to-use `ln(ar / 1.78f)` result.

Note: Aspect ratio itself does **not** depend on pixel resolution (4K 16:9 = 1080p 16:9). The table therefore covers all ratios that exist in the wild, from portrait ultra-tall phones to 32:9 super-ultrawide and theoretical 12K panoramic displays.

PT Tabela de consulta pré-calculada com busca binária para `ln(ar / 1.78f)`. Substitui a chamada transcendental `ln()` por uma busca O(log n) em FloatArray primitivo.

private val [keys](keys.md): [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html)

private const val [TOLERANCE](-t-o-l-e-r-a-n-c-e.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.003f

Tolerance: ±0.35 % of the key value. Tight enough to avoid false hits between adjacent ratios yet forgiving enough that floating-point rounding from integer division (e.g. 1920 / 1080 = 1.7777…) still matches the canonical 16:9 entry.

private val [values](values.md): [FloatArray](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float-array/index.html)

fun [lookup](lookup.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

Binary search with tolerance. O(log n) ≈ 7 comparisons for 111 entries.

## Properties

| Name | Summary |
|---|---|
| [keys](keys.md) |  |
| [TOLERANCE](-t-o-l-e-r-a-n-c-e.md) |  |
| [values](values.md) |  |


## Functions

| Name | Summary |
|---|---|
| [lookup](lookup.md) |  |
