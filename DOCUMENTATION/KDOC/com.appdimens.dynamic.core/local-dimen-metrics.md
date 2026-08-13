//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[LocalDimenMetrics](local-dimen-metrics.md)

# LocalDimenMetrics

val [LocalDimenMetrics](local-dimen-metrics.md): [CompositionLocal](https://developer.android.com/reference/kotlin/androidx/compose/runtime/CompositionLocal)<[DimenMetrics](-dimen-metrics/index.md)?>

EN Per-composition window snapshot. Consumers may use it to avoid repeatedly reading the broad [LocalConfiguration](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/LocalConfiguration) state and to keep every dimension in a composition on the same coherent window snapshot.

Provided by [AppDimensProvider](-app-dimens-provider.md) (since 3.1.8). When it is `null`, the Compose helpers fall back to the window snapshot derived from the captured application context.

PT Snapshot de janela por composição. Fornecido pelo [AppDimensProvider](-app-dimens-provider.md) (desde a 3.1.8).
