//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose.resize](../index.md)/[AutoResizePercentBasis](index.md)

# AutoResizePercentBasis

[jvm]
enum [AutoResizePercentBasis](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[AutoResizePercentBasis](index.md)> 

EN Which inner box dimension scales [autoResizeTextSpPercent](../auto-resize-text-sp-percent.md) min/max (0–100). PT Qual dimensão interna define o % min/max do texto.

## Constructors

| | |
|---|---|
| [AutoResizePercentBasis](-auto-resize-percent-basis.md) | [jvm] private constructor() |

## Entries

| | |
|---|---|
| [HEIGHT](-h-e-i-g-h-t/index.md) | [jvm] [HEIGHT](-h-e-i-g-h-t/index.md) EN Inner content height (after padding). PT Altura útil. |
| [WIDTH](-w-i-d-t-h/index.md) | [jvm] [WIDTH](-w-i-d-t-h/index.md) EN Inner content width. PT Largura útil. |
| [MIN_SIDE](-m-i-n_-s-i-d-e/index.md) | [jvm] [MIN_SIDE](-m-i-n_-s-i-d-e/index.md) EN `min(inner width, inner height)`. PT Mínimo entre largura e altura úteis. |

## Properties

| Name | Summary |
|---|---|
| [entries](entries.md) | [jvm] val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)<[AutoResizePercentBasis](index.md)> Returns a representation of an immutable list of all enum entries, in the order they're declared. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [jvm] fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [AutoResizePercentBasis](index.md) Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm] fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-array/index.html)<[AutoResizePercentBasis](index.md)> Returns an array containing the constants of this enum type, in the order they're declared. |