//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.code.fill](index.md)/[flsdpMode](flsdp-mode.md)

# flsdpMode

@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html).[flsdpMode](flsdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Extension for Int with dynamic scaling based on **Smallest Width (swDP)**. Uses the base value by default, but when the device matches the specified [uiModeType](flsdp-mode.md), it uses [modeValue](flsdp-mode.md) instead.
