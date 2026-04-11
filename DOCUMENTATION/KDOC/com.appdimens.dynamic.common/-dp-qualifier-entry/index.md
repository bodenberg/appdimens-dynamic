//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.common](../index.md)/[DpQualifierEntry](index.md)

# DpQualifierEntry

data class [DpQualifierEntry](index.md)(val type: [DpQualifier](../-dp-qualifier/index.md), val value: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html))

EN Represents a custom qualifier entry, combining the type and the minimum DP value for the custom adjustment to be applied.

PT Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP para que o ajuste customizado seja aplicado.

constructor(type: [DpQualifier](../-dp-qualifier/index.md), value: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html))

val [type](type.md): [DpQualifier](../-dp-qualifier/index.md)

EN The dimension type (SMALL_WIDTH, HEIGHT, WIDTH). PT O tipo de dimensão (SMALL_WIDTH, HEIGHT, WIDTH).

val [value](value.md): [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html)

EN The minimum dimension in DP to activate this qualifier (e.g., 600). PT A dimensão mínima em DP para ativar este qualificador (ex: 600).

## Constructors

| Name | Summary |
|---|---|
| [DpQualifierEntry](-dp-qualifier-entry.md) |  |


## Properties

| Name | Summary |
|---|---|
| [type](type.md) |  |
| [value](value.md) |  |
