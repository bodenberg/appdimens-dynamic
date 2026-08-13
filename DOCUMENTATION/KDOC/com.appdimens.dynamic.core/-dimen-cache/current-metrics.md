//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[currentMetrics](current-metrics.md)

# currentMetrics

@get:[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)val [currentMetrics](current-metrics.md): [DimenMetrics](../-dimen-metrics/index.md)

EN The immutable window snapshot active for the current resolution (since 3.1.8). During a [getOrPut](get-or-put.md) resolution it is the exact metrics supplied to that call; outside a resolution it is the fallback snapshot. It is never a partially updated global factor set — satellite strategy modules derive their scales from this snapshot, which keeps multi-window / foldable configurations independent.

PT Snapshot imutável da janela ativo na resolução corrente (desde a 3.1.8). Nunca é um conjunto global de fatores parcialmente atualizado.
