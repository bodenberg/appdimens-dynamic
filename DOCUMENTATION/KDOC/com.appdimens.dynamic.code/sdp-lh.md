//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code](index.md)/[sdpLh](sdp-lh.md)

# sdpLh

[jvm]
fun [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html).[sdpLh](sdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Smallest Width (swDP)**, but in landscape orientation it acts as **Screen Height (hDP)**. Usage example: `32.sdpLh(context)`.