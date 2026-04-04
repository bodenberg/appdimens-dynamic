//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.common](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DpQualifier](-dp-qualifier/index.md) | [jvm] enum [DpQualifier](-dp-qualifier/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[DpQualifier](-dp-qualifier/index.md)>  EN Defines the screen qualifier types based on the device's smallest width (smallestWidthDp), height (screenHeightDp), or width (screenWidthDp). |
| [DpQualifierEntry](-dp-qualifier-entry/index.md) | [jvm] data class [DpQualifierEntry](-dp-qualifier-entry/index.md)(val type: [DpQualifier](-dp-qualifier/index.md), val value: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html)) EN Represents a custom qualifier entry, combining the type and the minimum DP value for the custom adjustment to be applied. |
| [Inverter](-inverter/index.md) | [jvm] enum [Inverter](-inverter/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[Inverter](-inverter/index.md)>  EN Defines the screen orientation inversion types of the device. Used to specify how dimension qualifiers should swap their values based on screen rotation. |
| [Orientation](-orientation/index.md) | [jvm] enum [Orientation](-orientation/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[Orientation](-orientation/index.md)>  EN Defines the screen orientation types of the device. |
| [UiModeType](-ui-mode-type/index.md) | [jvm] enum [UiModeType](-ui-mode-type/index.md)(val configValue: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[UiModeType](-ui-mode-type/index.md)>  EN Defines the Android UI Mode Types for dimension customization, based on Configuration.uiMode. |
| [UnitType](-unit-type/index.md) | [jvm] enum [UnitType](-unit-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[UnitType](-unit-type/index.md)>  EN Defines the supported physical measurement units for conversion into density-independent pixels (Dp) or screen pixels (Px). |