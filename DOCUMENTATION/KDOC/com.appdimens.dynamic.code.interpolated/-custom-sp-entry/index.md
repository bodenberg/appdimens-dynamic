//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.interpolated](../index.md)/[CustomSpEntry](index.md)

# CustomSpEntry

data class [CustomSpEntry](index.md)(val uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, val dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, val orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, val customValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), val finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, val priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), val inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, val fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true)

EN Represents a custom Sp entry with qualifiers and priority, for the non-Compose Sp builder.

PT Representa uma entrada de Sp customizada com qualificadores e prioridade, para o builder Sp fora do Compose.

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

## Constructors

| | |
|---|---|
| [CustomSpEntry](-custom-sp-entry.md) | [jvm] constructor(uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null, dpQualifierEntry: [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.DEFAULT, customValue: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, priority: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, fontScale: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true) |

## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) | [jvm] val [customValue](custom-value.md): [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) |
| [dpQualifierEntry](dp-qualifier-entry.md) | [jvm] val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../../com.appdimens.dynamic.common/-dp-qualifier-entry/index.md)? = null |
| [finalQualifierResolver](final-qualifier-resolver.md) | [jvm] val [finalQualifierResolver](final-qualifier-resolver.md): [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null |
| [fontScale](font-scale.md) | [jvm] val [fontScale](font-scale.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [inverter](inverter.md) | [jvm] val [inverter](inverter.md): [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) |
| [orientation](orientation.md) | [jvm] val [orientation](orientation.md): [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) |
| [priority](priority.md) | [jvm] val [priority](priority.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [uiModeType](ui-mode-type.md) | [jvm] val [uiModeType](ui-mode-type.md): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)? = null |