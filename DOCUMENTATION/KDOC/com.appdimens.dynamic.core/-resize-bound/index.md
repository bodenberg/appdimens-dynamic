//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[ResizeBound](index.md)

# ResizeBound

sealed class [ResizeBound](index.md)

EN One end of a resize range or the step granularity.

[FixedDp](-fixed-dp/index.md): logical dp already chosen by the caller (e.g. `16.sdp` → pass `16f` or `dp.value`).

[FixedSp](-fixed-sp/index.md): sp value; converts to px with fontScale (like `COMPLEX_UNIT_SP`).

[Percent](-percent/index.md): `value` is 0–100 of axis using [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) (same idea as `spaceW` / `spaceSw` / `spaceH`).

protected constructor()

data class [FixedDp](-fixed-dp/index.md)(val dp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) : [ResizeBound](index.md)

data class [FixedSp](-fixed-sp/index.md)(val sp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) : [ResizeBound](index.md)

data class [Percent](-percent/index.md)(val value: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val axis: [ResizeAxisQualifier](../-resize-axis-qualifier/index.md)) : [ResizeBound](index.md)

fun [ResizeBound](index.md).[resolveToPx](../resolve-to-px.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), density: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), fontScale: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts bound to **px** for layout/measure (density + font scale for sp).

## Constructors

| Name | Summary |
|---|---|
| [ResizeBound](-resize-bound.md) |  |


## Types

| Name | Summary |
|---|---|
| [FixedDp](-fixed-dp/index.md) |  |
| [FixedSp](-fixed-sp/index.md) |  |
| [Percent](-percent/index.md) |  |


## Functions

| Name | Summary |
|---|---|
| [resolveToPx](../resolve-to-px.md) |  |
