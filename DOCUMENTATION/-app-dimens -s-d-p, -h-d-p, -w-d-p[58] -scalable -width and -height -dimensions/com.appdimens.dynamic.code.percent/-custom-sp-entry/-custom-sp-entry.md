//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.percent](../index.md)/[CustomSpEntry](index.md)/[CustomSpEntry](-custom-sp-entry.md)

# CustomSpEntry

[jvm]
constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true)

#### Parameters

jvm

| | |
|---|---|
| uiModeType | The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode. |
| dpQualifierEntry | The Dp qualifier entry (type and value). Null if only UI mode is used. |
| orientation | The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT). |
| customValue | The base Int Sp value to be used if the condition is met. |
| finalQualifierResolver | Optional override for the scaling qualifier at resolution time. |
| priority | The resolution priority. 1 is most specific (UI + Qualifier), 4 is least specific. |
| inverter | The inverter type to adapt scaling on rotation changes. |
| fontScale | Whether to respect the system font scale (default true). |