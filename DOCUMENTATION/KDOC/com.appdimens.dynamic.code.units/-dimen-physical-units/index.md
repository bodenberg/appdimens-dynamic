//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.units](../index.md)/[DimenPhysicalUnits](index.md)

# DimenPhysicalUnits

object [DimenPhysicalUnits](index.md)

EN Utility class for physical unit conversions. PT Classe utilitária para conversões de unidades físicas.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToInch](cm-to-inch.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert CM to Inch. PT Extensão de Float para converter CM para Inch.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToMm](cm-to-mm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert CM to MM. PT Extensão de Float para converter CM para MM.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToCm](inch-to-cm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert Inch to CM. PT Extensão de Float para converter Inch para CM.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToMm](inch-to-mm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert Inch to MM. PT Extensão de Float para converter Inch para MM.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToCm](mm-to-cm.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert MM to CM. PT Extensão de Float para converter MM para CM.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToInch](mm-to-inch.md)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Float extension to convert MM to Inch. PT Extensão de Float para converter MM para Inch.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [radiusFromCircumference](radius-from-circumference.md)(circumference: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), unitType: [UnitType](../../com.appdimens.dynamic.common/-unit-type/index.md), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a circumference value in a specific physical unit to radius in Dp.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [radiusFromDiameter](radius-from-diameter.md)(diameter: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), unitType: [UnitType](../../com.appdimens.dynamic.common/-unit-type/index.md), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts a diameter value in a specific physical unit to radius in Dp.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toDpFromCm](to-dp-from-cm.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts centimeters to Dp. PT Converte centímetros para Dp.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toDpFromInch](to-dp-from-inch.md)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts inches to Dp. PT Converte polegadas para Dp.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toDpFromMm](to-dp-from-mm.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts millimeters to Dp. PT Converte milímetros para Dp.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toPxFromCm](to-px-from-cm.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts centimeters to Pixels. PT Converte centímetros para Pixels.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toPxFromInch](to-px-from-inch.md)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts inches to Pixels. PT Converte polegadas para Pixels.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toPxFromMm](to-px-from-mm.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts millimeters to Pixels. PT Converte milímetros para Pixels.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toSpFromCm](to-sp-from-cm.md)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts centimeters to SP. PT Converte centímetros para SP.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toSpFromInch](to-sp-from-inch.md)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts inches to SP. PT Converte polegadas para SP.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [toSpFromMm](to-sp-from-mm.md)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Converts millimeters to SP. PT Converte milímetros para SP.

## Functions

| Name | Summary |
|---|---|
| [cmToInch](cm-to-inch.md) |  |
| [cmToMm](cm-to-mm.md) |  |
| [inchToCm](inch-to-cm.md) |  |
| [inchToMm](inch-to-mm.md) |  |
| [mmToCm](mm-to-cm.md) |  |
| [mmToInch](mm-to-inch.md) |  |
| [radiusFromCircumference](radius-from-circumference.md) |  |
| [radiusFromDiameter](radius-from-diameter.md) |  |
| [toDpFromCm](to-dp-from-cm.md) |  |
| [toDpFromInch](to-dp-from-inch.md) |  |
| [toDpFromMm](to-dp-from-mm.md) |  |
| [toPxFromCm](to-px-from-cm.md) |  |
| [toPxFromInch](to-px-from-inch.md) |  |
| [toPxFromMm](to-px-from-mm.md) |  |
| [toSpFromCm](to-sp-from-cm.md) |  |
| [toSpFromInch](to-sp-from-inch.md) |  |
| [toSpFromMm](to-sp-from-mm.md) |  |
