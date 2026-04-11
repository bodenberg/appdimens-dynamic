//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.compose](../index.md)/[DimenPhysicalUnits](index.md)

# DimenPhysicalUnits

object [DimenPhysicalUnits](index.md)

EN Singleton object providing functions for physical unit conversion (MM, CM, Inch) and measurement utilities.

PT Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch) e utilitários de medição.

private const val [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.md): [Double](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-double/index.html)

val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cm](cm.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert CM to Dp.

EN Int extension to convert CM to Dp.

val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inch](inch.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert Inch to Dp.

EN Int extension to convert Inch to Dp.

val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mm](mm.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert MM to Dp.

EN Int extension to convert MM to Dp.

private const val [MM_TO_CM_FACTOR](-m-m_-t-o_-c-m_-f-a-c-t-o-r.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 10.0f

EN Constants for Physical Unit Conversion.

private const val [MM_TO_INCH_FACTOR](-m-m_-t-o_-i-n-c-h_-f-a-c-t-o-r.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 25.4f

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToInch](cm-to-inch.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert CM to Inch.

EN Int extension to convert CM to Inch.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToMm](cm-to-mm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert CM to MM.

EN Int extension to convert CM to MM.

fun [convertCmToInch](convert-cm-to-inch.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Centimeters (CM) to Inches (Inch).

fun [convertCmToMm](convert-cm-to-mm.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Centimeters (CM) to Millimeters (MM).

fun [convertInchToCm](convert-inch-to-cm.md)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Inches (Inch) to Centimeters (CM).

fun [convertInchToMm](convert-inch-to-mm.md)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Inches (Inch) to Millimeters (MM).

fun [convertMmToCm](convert-mm-to-cm.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Millimeters (MM) to Centimeters (CM).

fun [convertMmToInch](convert-mm-to-inch.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Millimeters (MM) to Inches (Inch).

fun [displayMeasureDiameter](display-measure-diameter.md)(diameter: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), isCircumference: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Adjusts a diameter value to Circumference if requested.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToCm](inch-to-cm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert Inch to CM.

EN Int extension to convert Inch to CM.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToMm](inch-to-mm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert Inch to MM.

EN Int extension to convert Inch to MM.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[measureDiameter](measure-diameter.md)(isCircumference: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to adjust the measurement for Diameter or Circumference.

EN Int extension to adjust the measurement for Diameter or Circumference.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToCm](mm-to-cm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert MM to CM.

EN Int extension to convert MM to CM.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToInch](mm-to-inch.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert MM to Inch.

EN Int extension to convert MM to Inch.

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[radius](radius.md)(type: [UnitType](../../com.appdimens.dynamic.common/-unit-type/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to calculate the Radius in Dp.

EN Int extension to calculate the Radius in Dp.

EN Converts a diameter value in a specific physical unit to Radius in Dp.

fun [toCm](to-cm.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Centimeters (CM) to Dp.

fun [toInch](to-inch.md)(inches: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts Inches (Inch) to Dp.

fun [toMm](to-mm.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a value in millimeters (mm) to its equivalent in Dp.

fun [unitSizeInDp](unit-size-in-dp.md)(type: [UnitType](../../com.appdimens.dynamic.common/-unit-type/index.md), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Calculates the size of 1 unit (1.0f) in Dp for a specific physical unit.

## Properties

| Name | Summary |
|---|---|
| [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.md) |  |
| [cm](cm.md) |  |
| [inch](inch.md) |  |
| [mm](mm.md) |  |
| [MM_TO_CM_FACTOR](-m-m_-t-o_-c-m_-f-a-c-t-o-r.md) |  |
| [MM_TO_INCH_FACTOR](-m-m_-t-o_-i-n-c-h_-f-a-c-t-o-r.md) |  |


## Functions

| Name | Summary |
|---|---|
| [cmToInch](cm-to-inch.md) |  |
| [cmToMm](cm-to-mm.md) |  |
| [convertCmToInch](convert-cm-to-inch.md) |  |
| [convertCmToMm](convert-cm-to-mm.md) |  |
| [convertInchToCm](convert-inch-to-cm.md) |  |
| [convertInchToMm](convert-inch-to-mm.md) |  |
| [convertMmToCm](convert-mm-to-cm.md) |  |
| [convertMmToInch](convert-mm-to-inch.md) |  |
| [displayMeasureDiameter](display-measure-diameter.md) |  |
| [inchToCm](inch-to-cm.md) |  |
| [inchToMm](inch-to-mm.md) |  |
| [measureDiameter](measure-diameter.md) |  |
| [mmToCm](mm-to-cm.md) |  |
| [mmToInch](mm-to-inch.md) |  |
| [radius](radius.md) |  |
| [toCm](to-cm.md) |  |
| [toInch](to-inch.md) |  |
| [toMm](to-mm.md) |  |
| [unitSizeInDp](unit-size-in-dp.md) |  |
