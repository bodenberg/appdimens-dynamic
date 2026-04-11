//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.auto](../index.md)/[CustomDpEntry](index.md)/[CustomDpEntry](-custom-dp-entry.md)

# CustomDpEntry

constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: ERROR CLASS: Symbol not found for Dp, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.

The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used.

The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).

The Dp value to be used if the condition is met.

Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time.

The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only).

The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT).
