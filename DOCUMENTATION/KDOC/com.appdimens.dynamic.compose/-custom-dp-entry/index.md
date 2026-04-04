//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose](../index.md)/[CustomDpEntry](index.md)

# CustomDpEntry

data class [CustomDpEntry](index.md)(val uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, val dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, val orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, val customValue: <Error class: unknown class>, val finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, val priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), val inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

EN Represents a custom dimension entry with qualifiers and priority. Used by the DimenScaled class to define specific values for screen conditions.

PT Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe DimenScaled para definir valores específicos para condições de tela.

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

## Constructors

| | |
|---|---|
| [CustomDpEntry](-custom-dp-entry.md) | [jvm] constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: <Error class: unknown class>, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT) |

## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) | [jvm] val [customValue](custom-value.md): <Error class: unknown class> |
| [dpQualifierEntry](dp-qualifier-entry.md) | [jvm] val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null |
| [finalQualifierResolver](final-qualifier-resolver.md) | [jvm] val [finalQualifierResolver](final-qualifier-resolver.md): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null |
| [inverter](inverter.md) | [jvm] val [inverter](inverter.md): [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? |
| [orientation](orientation.md) | [jvm] val [orientation](orientation.md): [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? |
| [priority](priority.md) | [jvm] val [priority](priority.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [uiModeType](ui-mode-type.md) | [jvm] val [uiModeType](ui-mode-type.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null |