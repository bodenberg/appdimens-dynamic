//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[AppDimensProvider](-app-dimens-provider.md)

# AppDimensProvider

fun [AppDimensProvider](-app-dimens-provider.md)(content: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Provider that automatically computes and provides the [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md) (including foldables) and, since 3.1.7, the per-composition [DimenMetrics](-dimen-metrics/index.md) snapshot via [LocalDimenMetrics](local-dimen-metrics.md) to all child components. The snapshot is keyed on the semantic configuration fields (size, density, font scale, orientation, uiMode, multi-window), so every dimension in the composition resolves on one coherent window snapshot. **Recommended for performance:** without it, [getCurrentUiModeType](get-current-ui-mode-type.md) falls back to [UiModeType.fromConfiguration](../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) on every `*Mode` / `*Screen` facilitator call.

PT Provedor que computa e fornece o [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md) (incl. dobráveis) e, desde a 3.1.7, o snapshot [DimenMetrics](-dimen-metrics/index.md) via [LocalDimenMetrics](local-dimen-metrics.md).
