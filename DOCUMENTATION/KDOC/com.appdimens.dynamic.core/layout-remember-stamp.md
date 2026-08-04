//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[layoutRememberStamp](layout-remember-stamp.md)

# layoutRememberStamp

fun [layoutRememberStamp](layout-remember-stamp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

Packs layout fields (SW / width / height / orientation) and mixes `densityDpi` without bit overlap. Does **not** use `Configuration.hashCode()`.

See also [spRememberStamp](sp-remember-stamp.md), [pxRememberStamp](px-remember-stamp.md).
