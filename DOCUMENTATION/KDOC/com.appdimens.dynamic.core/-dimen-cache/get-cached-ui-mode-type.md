//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[getCachedUiModeType](get-cached-ui-mode-type.md)

# getCachedUiModeType

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [getCachedUiModeType](get-cached-ui-mode-type.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md)

EN Cached [UiModeType] resolution keyed per-[Context] in a weak map (an Activity/window can be collected normally — no leak; a process-wide single entry would be incorrect when two windows differ). The fingerprint covers `uiMode`, `smallestScreenWidthDp`, `densityDpi`, and min/max screen dp — not `Configuration.hashCode()`.

PT [UiModeType] cacheado por [Context] em mapa fraco (sem leak de Activity; entrada única global seria incorreta com duas janelas diferentes).
