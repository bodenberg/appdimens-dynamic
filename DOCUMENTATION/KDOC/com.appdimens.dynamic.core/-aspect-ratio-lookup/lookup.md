//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[AspectRatioLookup](index.md)/[lookup](lookup.md)

# lookup

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [lookup](lookup.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?

EN Exact natural logarithm, kept for source compatibility. The function deliberately does not approximate: a dimension resolver must be deterministic for every valid window ratio.

Returns `ln(normalizedAr)` when [normalizedAr](lookup.md) is positive and finite, otherwise `null` (callers such as [fastLn](../fast-ln.md) fall back to a neutral value).

PT Logaritmo natural exato, mantido por compatibilidade de fonte. Não aproxima: o resolvedor de dimensões deve ser determinístico para toda razão de janela válida.

`currentAr / 1.78f` — the normalized aspect ratio

natural log of [normalizedAr](lookup.md), or `null` for invalid input
