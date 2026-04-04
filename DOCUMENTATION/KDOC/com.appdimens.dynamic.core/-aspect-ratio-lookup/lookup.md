//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)/[lookup](lookup.md)

# lookup

[jvm]
fun [lookup](lookup.md)(normalizedAr: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)?

Binary search with tolerance.  O(log n) ≈ 7 comparisons for 130 entries.

#### Return

`ln(normalizedAr)` from the table, or `null` if not within tolerance.

#### Parameters

jvm

| | |
|---|---|
| normalizedAr | `currentAr / 1.78f` |