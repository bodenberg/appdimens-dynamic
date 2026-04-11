//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)/[lookup](lookup.md)

# lookup

fun [lookup](lookup.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

Binary search with tolerance. O(log n) ≈ 7 comparisons for 111 entries.

`ln(normalizedAr)` from the table, or `null` if not within tolerance.

`currentAr / 1.78f`
