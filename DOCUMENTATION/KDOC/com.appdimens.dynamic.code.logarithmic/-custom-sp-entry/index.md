//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.logarithmic](../index.md)/[CustomSpEntry](index.md)

# CustomSpEntry

data class [CustomSpEntry](index.md)(val uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, val dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, val orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, val customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), val finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, val fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true)

EN Represents a custom Sp entry with qualifiers and priority, for the non-Compose Sp builder.

PT Representa uma entrada de Sp customizada com qualificadores e prioridade, para o builder Sp fora do Compose.

The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode.

The Dp qualifier entry (type and value). Null if only UI mode is used.

The screen orientation (LANDSCAPE, PORTRAIT, DEFAULT).

The base Int Sp value to be used if the condition is met.

Optional override for the scaling qualifier at resolution time.

The resolution priority. 1 is most specific (UI + Qualifier), 4 is least specific.

The inverter type to adapt scaling on rotation changes.

Whether to respect the system font scale (default true).

constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true)

val [customValue](custom-value.md): [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)

val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)?

val [finalQualifierResolver](final-qualifier-resolver.md): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)?

val [fontScale](font-scale.md): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

val [inverter](inverter.md): [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md)

val [orientation](orientation.md): [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md)

val [priority](priority.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

val [uiModeType](ui-mode-type.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)?

## Constructors

| Name | Summary |
|---|---|
| [CustomSpEntry](-custom-sp-entry.md) |  |


## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) |  |
| [dpQualifierEntry](dp-qualifier-entry.md) |  |
| [finalQualifierResolver](final-qualifier-resolver.md) |  |
| [fontScale](font-scale.md) |  |
| [inverter](inverter.md) |  |
| [orientation](orientation.md) |  |
| [priority](priority.md) |  |
| [uiModeType](ui-mode-type.md) |  |
