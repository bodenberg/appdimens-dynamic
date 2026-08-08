//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[withCompositionMetrics](with-composition-metrics.md)

# withCompositionMetrics

internal fun <T> [withCompositionMetrics](with-composition-metrics.md)(metrics: [DimenMetrics](../-dimen-metrics/index.md)?, block: () -> T): T

EN Used by Compose helpers to make nested legacy strategy calls observe [LocalDimenMetrics](../local-dimen-metrics.md). Runs [block](with-composition-metrics.md) with the supplied snapshot active on the current thread (via a thread-local), restoring the previous value afterwards. No-op when [metrics](with-composition-metrics.md) is `null`.

PT Usado pelos helpers de Compose para que chamadas legadas observem o [LocalDimenMetrics](../local-dimen-metrics.md). Executa [block](with-composition-metrics.md) com o snapshot ativo na thread corrente.
