//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.logarithmic](../index.md)/[CustomDpEntry](index.md)/[CustomDpEntry](-custom-dp-entry.md)

# CustomDpEntry

[jvm]
constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: <Error class: unknown class>, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

#### Parameters

jvm

| | |
|---|---|
| uiModeType | The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode. |
| dpQualifierEntry | The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used. |
| orientation | The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT). |
| customValue | The Dp value to be used if the condition is met. |
| finalQualifierResolver | Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time. |
| priority | The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only). |
| inverter | The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT). |