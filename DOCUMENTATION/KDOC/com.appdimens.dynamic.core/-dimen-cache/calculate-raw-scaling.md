//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[calculateRawScaling](calculate-raw-scaling.md)

# calculateRawScaling

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [calculateRawScaling](calculate-raw-scaling.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)?): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Unified scaling engine over the immutable metrics of the current resolution ([currentMetrics](current-metrics.md)). Callers that resolve through [getOrPut](get-or-put.md) receive a per-window snapshot; no result is derived from a process-wide application configuration. Requires a finite [baseValue](calculate-raw-scaling.md).

PT Motor de escala unificado sobre as métricas imutáveis da resolução corrente ([currentMetrics](current-metrics.md)). Exige [baseValue](calculate-raw-scaling.md) finito.
