//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.common](../index.md)/[Inverter](index.md)

# Inverter

[jvm]
enum [Inverter](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[Inverter](index.md)> 

EN Defines the screen orientation inversion types of the device. Used to specify how dimension qualifiers should swap their values based on screen rotation.

PT Define os tipos de inversão de orientação de tela do dispositivo. Usado para especificar como os qualificadores de dimensão devem trocar seus valores com base na rotação da tela.

## Constructors

| | |
|---|---|
| [Inverter](-inverter.md) | [jvm] private constructor() |

## Entries

| | |
|---|---|
| [PH_TO_LW](-p-h_-t-o_-l-w/index.md) | [jvm] [PH_TO_LW](-p-h_-t-o_-l-w/index.md) EN Maps Portrait Height to Landscape Width. PT Mapeia a Altura em Retrato para a Largura em Paisagem. |
| [PW_TO_LH](-p-w_-t-o_-l-h/index.md) | [jvm] [PW_TO_LH](-p-w_-t-o_-l-h/index.md) EN Maps Portrait Width to Landscape Height. PT Mapeia a Largura em Retrato para a Altura em Paisagem. |
| [LH_TO_PW](-l-h_-t-o_-p-w/index.md) | [jvm] [LH_TO_PW](-l-h_-t-o_-p-w/index.md) EN Maps Landscape Height to Portrait Width. PT Mapeia a Altura em Paisagem para a Largura em Retrato. |
| [LW_TO_PH](-l-w_-t-o_-p-h/index.md) | [jvm] [LW_TO_PH](-l-w_-t-o_-p-h/index.md) EN Maps Landscape Width to Portrait Height. PT Mapeia a Largura em Paisagem para a Altura em Retrato. |
| [SW_TO_LH](-s-w_-t-o_-l-h/index.md) | [jvm] [SW_TO_LH](-s-w_-t-o_-l-h/index.md) EN Maps Smallest Width to Landscape Height. PT Mapeia a Smallest Width para a Altura em Paisagem. |
| [SW_TO_LW](-s-w_-t-o_-l-w/index.md) | [jvm] [SW_TO_LW](-s-w_-t-o_-l-w/index.md) EN Maps Smallest Width to Landscape Width. PT Mapeia a Smallest Width para a Largura em Paisagem. |
| [SW_TO_PH](-s-w_-t-o_-p-h/index.md) | [jvm] [SW_TO_PH](-s-w_-t-o_-p-h/index.md) EN Maps Smallest Width to Portrait Height. PT Mapeia a Smallest Width para a Altura em Retrato. |
| [SW_TO_PW](-s-w_-t-o_-p-w/index.md) | [jvm] [SW_TO_PW](-s-w_-t-o_-p-w/index.md) EN Maps Smallest Width to Portrait Width. PT Mapeia a Smallest Width para a Largura em Retrato. |
| [DEFAULT](-d-e-f-a-u-l-t/index.md) | [jvm] [DEFAULT](-d-e-f-a-u-l-t/index.md) EN Default behavior, no inversion is applied. PT Comportamento padrão, nenhuma inversão é aplicada. |

## Properties

| Name | Summary |
|---|---|
| [entries](entries.md) | [jvm] val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)<[Inverter](index.md)> Returns a representation of an immutable list of all enum entries, in the order they're declared. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [jvm] fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Inverter](index.md) Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm] fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-array/index.html)<[Inverter](index.md)> Returns an array containing the constants of this enum type, in the order they're declared. |