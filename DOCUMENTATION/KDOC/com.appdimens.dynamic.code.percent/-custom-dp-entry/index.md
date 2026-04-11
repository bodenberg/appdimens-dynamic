//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.percent](../index.md)/[CustomDpEntry](index.md)

# CustomDpEntry

data class [CustomDpEntry](index.md)(val uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, val dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, val orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, val customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

EN Represents a custom dimension entry with qualifiers and priority. Used by the DimenPercent class to define specific values for screen conditions.

PT Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe DimenPercent para definir valores específicos para condições de tela.

constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)? = Orientation.DEFAULT, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)? = Inverter.DEFAULT)

val [customValue](custom-value.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

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
