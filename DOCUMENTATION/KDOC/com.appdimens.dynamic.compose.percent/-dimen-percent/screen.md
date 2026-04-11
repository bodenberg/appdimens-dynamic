//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.percent](../index.md)/[DimenPercent](index.md)/[screen](screen.md)

# screen

fun [screen](screen.md)(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: ERROR CLASS: Symbol not found for Dp, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT): [DimenPercent](index.md)

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).

PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). This is an overload that accepts an Int for `customValue`.

PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w). Esta é uma sobrecarga que aceita um Int para `customValue`.

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).

PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). This is an overload that accepts an Int for `customValue`.

PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH). Esta é uma sobrecarga que aceita um Int para `customValue`.

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.

PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. This is an overload that accepts an Int for `customValue`.

PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType. Esta é uma sobrecarga que aceita um Int para `customValue`.

EN Priority 4: Orientation. This is an overload that accepts an Int for `customValue`.

PT Prioridade 4: Orientation. Esta é uma sobrecarga que aceita um Int para `customValue`.
