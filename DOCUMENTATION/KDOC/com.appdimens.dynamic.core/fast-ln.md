//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[fastLn](fast-ln.md)

# fastLn

inline fun [fastLn](fast-ln.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Fast natural-logarithm via binary-search lookup table, falling back to the intrinsic `ln()`.

Because the result is stored in `remember()`, this function is called **at most once per configuration change** per composable. The lookup pays for itself on devices that trigger many simultaneous recompositions (e.g. orientation flip with a large Lazy list).

PT Logaritmo natural rápido via tabela de busca binária, com fallback para `ln()` intrínseco.

natural log of [normalizedAr](fast-ln.md)

`currentAr / 1.78f` — the normalized aspect ratio
