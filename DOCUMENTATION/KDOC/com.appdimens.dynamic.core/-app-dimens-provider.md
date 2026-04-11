//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[AppDimensProvider](-app-dimens-provider.md)

# AppDimensProvider

fun [AppDimensProvider](-app-dimens-provider.md)(content: () -> [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Provider that automatically computes and provides the [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md) (including foldables) to all child components. **Recommended for performance:** without it, [getCurrentUiModeType](get-current-ui-mode-type.md) falls back to [UiModeType.fromConfiguration](../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) on every `*Mode` / `*Screen` facilitator call. See the root README section **Performance: DimenCache → Integration checklist**.

PT Provedor que computa e fornece o [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md) (incl. dobráveis). **Recomendado para desempenho:** sem ele, [getCurrentUiModeType](get-current-ui-mode-type.md) recalcula via [UiModeType.fromConfiguration](../com.appdimens.dynamic.common/-ui-mode-type/-companion/from-configuration.md) em cada chamada às extensões `*Mode` / `*Screen`.
