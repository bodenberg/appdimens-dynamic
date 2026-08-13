//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[fastLn](fast-ln.md)

# fastLn

fun [fastLn](fast-ln.md)(normalizedAr: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Exact natural logarithm with a safe neutral fallback for invalid configuration input.

Since 3.1.8 the aspect-ratio factor is computed as a deterministic `ln()` once per [DimenMetrics](-dimen-metrics/index.md) snapshot — the hand-maintained binary-search lookup table was removed, so two nearby window ratios can never collapse to the same approximated result. Returns `0f` when [normalizedAr](fast-ln.md) is not a positive finite value (neutral multiplier `1 + k·0`).

PT Logaritmo natural exato com fallback neutro seguro para configurações inválidas. A tabela de busca binária foi removida na 3.1.8.

natural log of [normalizedAr](fast-ln.md)

`currentAr / 1.78f` — the normalized aspect ratio
