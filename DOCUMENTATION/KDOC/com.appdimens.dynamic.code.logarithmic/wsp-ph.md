//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.logarithmic](index.md)/[wspPh](wsp-ph.md)

# wspPh

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[wspPh](wsp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on the **Screen Width (wDP)**, but in portrait orientation it acts as **Screen Height (hDP)**. Usage example: `100.wspPh(context)`.