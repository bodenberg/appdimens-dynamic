//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.fill](../index.md)/[FillSp](index.md)/[screen](screen.md)

# screen

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [screen](screen.md)(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = defaultFontScale): [FillSp](index.md)

EN Priority 1: Most specific qualifier — combines [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) and Dp qualifier (sw, h, w). PT Prioridade 1: qualificador mais específico — combina [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) e qualificador Dp (sw, h, w).

EN Priority 2: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) only (e.g. TELEVISION, WATCH). PT Prioridade 2: apenas [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) (ex.: TELEVISION, WATCH).

EN Priority 3: Dp qualifier (sw, h, w) without [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md) restriction. PT Prioridade 3: qualificador Dp (sw, h, w) sem restrição de [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md).

EN Priority 4: orientation only. PT Prioridade 4: apenas orientação.
