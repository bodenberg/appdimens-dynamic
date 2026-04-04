//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.core](../../index.md)/[DimenCache](../index.md)/[ValueType](index.md)

# ValueType

[jvm]
@[PublishedApi](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-published-api/index.html)

internal enum [ValueType](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)<[DimenCache.ValueType](index.md)> 

EN Dimension type discriminator for the cache key. PT Discriminador de tipo de dimensão para a chave de cache.

## Constructors

| | |
|---|---|
| [ValueType](-value-type.md) | [jvm] private constructor() |

## Entries

| | |
|---|---|
| [DP](-d-p/index.md) | [jvm] [DP](-d-p/index.md) |
| [PX](-p-x/index.md) | [jvm] [PX](-p-x/index.md) |
| [SP_WITH_SCALE](-s-p_-w-i-t-h_-s-c-a-l-e/index.md) | [jvm] [SP_WITH_SCALE](-s-p_-w-i-t-h_-s-c-a-l-e/index.md) |
| [SP_NO_SCALE](-s-p_-n-o_-s-c-a-l-e/index.md) | [jvm] [SP_NO_SCALE](-s-p_-n-o_-s-c-a-l-e/index.md) |
| [SP_PX_WITH_SCALE](-s-p_-p-x_-w-i-t-h_-s-c-a-l-e/index.md) | [jvm] [SP_PX_WITH_SCALE](-s-p_-p-x_-w-i-t-h_-s-c-a-l-e/index.md) |
| [SP_PX_NO_SCALE](-s-p_-p-x_-n-o_-s-c-a-l-e/index.md) | [jvm] [SP_PX_NO_SCALE](-s-p_-p-x_-n-o_-s-c-a-l-e/index.md) |

## Properties

| Name | Summary |
|---|---|
| [entries](entries.md) | [jvm] val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)<[DimenCache.ValueType](index.md)> Returns a representation of an immutable list of all enum entries, in the order they're declared. |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [jvm] fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [DimenCache.ValueType](index.md) Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm] fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-array/index.html)<[DimenCache.ValueType](index.md)> Returns an array containing the constants of this enum type, in the order they're declared. |