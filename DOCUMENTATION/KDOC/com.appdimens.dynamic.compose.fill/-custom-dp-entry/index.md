//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.fill](../index.md)/[CustomDpEntry](index.md)

# CustomDpEntry

data class [CustomDpEntry](index.md)(val uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, val dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, val orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, val customValue: ERROR CLASS: Symbol not found for Dp, val finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

EN Represents a custom dimension entry with qualifiers and priority. Used by the DimenFill class to define specific values for screen conditions.

PT Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe DimenFill para definir valores específicos para condições de tela.

The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.

The Dp qualifier entry (type and value, e.g., SMALL_WIDTH > 600). Null if only UI mode is used.

The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).

The Dp value to be used if the condition is met.

Optional dimension qualifier (e.g., HEIGHT) to be applied at resolution time.

The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only).

The inverter type to adapt scaling width/height on rotation changes (default is Inverter.DEFAULT).

constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: ERROR CLASS: Symbol not found for Dp, finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

val [customValue](custom-value.md): ERROR CLASS: Symbol not found for Dp

val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)?

val [finalQualifierResolver](final-qualifier-resolver.md): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)?

val [inverter](inverter.md): [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)?

val [orientation](orientation.md): [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)?

val [priority](priority.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

val [uiModeType](ui-mode-type.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)?

## Constructors

| Name | Summary |
|---|---|
| [CustomDpEntry](-custom-dp-entry.md) |  |


## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) |  |
| [dpQualifierEntry](dp-qualifier-entry.md) |  |
| [finalQualifierResolver](final-qualifier-resolver.md) |  |
| [inverter](inverter.md) |  |
| [orientation](orientation.md) |  |
| [priority](priority.md) |  |
| [uiModeType](ui-mode-type.md) |  |
