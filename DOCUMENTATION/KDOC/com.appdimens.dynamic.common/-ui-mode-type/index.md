//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.common](../index.md)/[UiModeType](index.md)

# UiModeType

enum [UiModeType](index.md)(val configValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[UiModeType](index.md)>

EN Defines the Android UI Mode Types for dimension customization, based on Configuration.uiMode.

PT Define os tipos de modo de interface do usuário (UI Mode Type) do Android para customização de dimensões, com base em Configuration.uiMode.

private constructor(configValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

[NORMAL](-n-o-r-m-a-l/index.md)

EN Default Phone/Tablet.

[TELEVISION](-t-e-l-e-v-i-s-i-o-n/index.md)

EN Television.

[CAR](-c-a-r/index.md)

EN Car.

[WATCH](-w-a-t-c-h/index.md)

EN Watch (Wear OS).

[DESK](-d-e-s-k/index.md)

EN Desk Device (Docked).

[APPLIANCE](-a-p-p-l-i-a-n-c-e/index.md)

EN Projection Device (e.g., Android Auto, Cast).

@[RequiresApi](https://developer.android.com/reference/kotlin/androidx/annotation/RequiresApi.html)(value = 26)[VR_HEADSET](-v-r_-h-e-a-d-s-e-t/index.md)

EN Virtual Reality (VR) Device.

[UNDEFINED](-u-n-d-e-f-i-n-e-d/index.md)

EN Any unspecified/other UI mode.

[FOLD_OPEN](-f-o-l-d_-o-p-e-n/index.md)

EN Foldable Device (Open state). PT Dispositivo Dobrável tipo Fold (Estado aberto).

[FOLD_CLOSED](-f-o-l-d_-c-l-o-s-e-d/index.md)

EN Foldable Device (Closed state). PT Dispositivo Dobrável tipo Fold (Estado fechado).

[FLIP_OPEN](-f-l-i-p_-o-p-e-n/index.md)

EN Flip Device (Open state). PT Dispositivo Dobrável tipo Flip (Estado aberto).

[FLIP_CLOSED](-f-l-i-p_-c-l-o-s-e-d/index.md)

EN Flip Device (Closed state). PT Dispositivo Dobrável tipo Flip (Estado fechado).

[FOLD_HALF_OPENED](-f-o-l-d_-h-a-l-f_-o-p-e-n-e-d/index.md)

EN Foldable Device (Half-opened state). PT Dispositivo Dobrável tipo Fold (Estado semiaberto).

[FLIP_HALF_OPENED](-f-l-i-p_-h-a-l-f_-o-p-e-n-e-d/index.md)

EN Flip Device (Half-opened state). PT Dispositivo Dobrável tipo Flip (Estado semiaberto).

object [Companion](-companion/index.md)

val [configValue](config-value.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)<[UiModeType](index.md)>

Returns a representation of an immutable list of all enum entries, in the order they're declared.

fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [UiModeType](index.md)

Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.)

fun [values](values.md)(): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)<[UiModeType](index.md)>

Returns an array containing the constants of this enum type, in the order they're declared.

## Constructors

| Name | Summary |
|---|---|
| [UiModeType](-ui-mode-type.md) |  |


## Entries

| Name | Summary |
|---|---|
| [NORMAL](-n-o-r-m-a-l/index.md) |  |
| [TELEVISION](-t-e-l-e-v-i-s-i-o-n/index.md) |  |
| [CAR](-c-a-r/index.md) |  |
| [WATCH](-w-a-t-c-h/index.md) |  |
| [DESK](-d-e-s-k/index.md) |  |
| [APPLIANCE](-a-p-p-l-i-a-n-c-e/index.md) |  |
| [VR_HEADSET](-v-r_-h-e-a-d-s-e-t/index.md) |  |
| [UNDEFINED](-u-n-d-e-f-i-n-e-d/index.md) |  |
| [FOLD_OPEN](-f-o-l-d_-o-p-e-n/index.md) |  |
| [FOLD_CLOSED](-f-o-l-d_-c-l-o-s-e-d/index.md) |  |
| [FLIP_OPEN](-f-l-i-p_-o-p-e-n/index.md) |  |
| [FLIP_CLOSED](-f-l-i-p_-c-l-o-s-e-d/index.md) |  |
| [FOLD_HALF_OPENED](-f-o-l-d_-h-a-l-f_-o-p-e-n-e-d/index.md) |  |
| [FLIP_HALF_OPENED](-f-l-i-p_-h-a-l-f_-o-p-e-n-e-d/index.md) |  |


## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) |  |


## Properties

| Name | Summary |
|---|---|
| [configValue](config-value.md) |  |
| [entries](entries.md) |  |


## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) |  |
| [values](values.md) |  |
