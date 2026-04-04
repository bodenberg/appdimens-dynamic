//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.perimeter](index.md)/[prsdpLw](prsdp-lw.md)

# prsdpLw

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[prsdpLw](prsdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but in landscape orientation it acts as **Screen Width (wDP)**. Usage example: `32.sdpLw(context)`.