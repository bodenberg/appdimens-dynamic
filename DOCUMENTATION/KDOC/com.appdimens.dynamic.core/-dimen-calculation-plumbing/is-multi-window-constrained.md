//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCalculationPlumbing](index.md)/[isMultiWindowConstrained](is-multi-window-constrained.md)

# isMultiWindowConstrained

fun [isMultiWindowConstrained](is-multi-window-constrained.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)? = null): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

Returns `true` when the app is in a multi-window mode (split-screen, freeform, PiP) **and** the caller opted into suppressing scaling via [ignoreMultiWindows](is-multi-window-constrained.md).

Primary detection uses the public [Activity.isInMultiWindowMode](https://developer.android.com/reference/kotlin/android/app/Activity.html#isinmultiwindowmode) API (available since API 24, which matches the library's minSdk). When no [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html) can be resolved from the supplied [context](is-multi-window-constrained.md), a heuristic based on [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html) dimensions is used as a best-effort fallback.

Optional [Context](https://developer.android.com/reference/kotlin/android/content/Context.html) used to resolve the hosting [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html). Pass this whenever available for reliable multi-window detection.
