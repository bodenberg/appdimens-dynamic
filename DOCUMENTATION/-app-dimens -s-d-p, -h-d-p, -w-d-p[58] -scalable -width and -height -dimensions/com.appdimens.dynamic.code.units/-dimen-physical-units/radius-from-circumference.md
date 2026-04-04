//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.units](../index.md)/[DimenPhysicalUnits](index.md)/[radiusFromCircumference](radius-from-circumference.md)

# radiusFromCircumference

[jvm]
@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)

fun [radiusFromCircumference](radius-from-circumference.md)(circumference: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html), unitType: [UnitType](../../com.appdimens.dynamic.common/-unit-type/index.md), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a circumference value in a specific physical unit to radius in Dp.

#### Return

The radius in Dp. PT Converte um valor de circunferência em uma unidade física específica para raio em Dp.

O raio em Dp.

#### Parameters

jvm

| | |
|---|---|
| circumference | O valor da circunferência. |
| unitType | O tipo de unidade (mm, cm, inch). |
| resources | Os Resources do Context. |