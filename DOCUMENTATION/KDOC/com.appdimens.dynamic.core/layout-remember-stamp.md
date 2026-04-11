//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[layoutRememberStamp](layout-remember-stamp.md)

# layoutRememberStamp

internal fun [layoutRememberStamp](layout-remember-stamp.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

EN Layout stamp for androidx.compose.runtime.remember keys. Uses [Configuration.hashCode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#hashcode) which reflects actual content (densityDpi, locale, fontScale, orientation, etc.), ensuring correct recomposition when any configuration field changes.

PT Carimbo de layout para chaves de remember. Usa [Configuration.hashCode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#hashcode) que reflete o conteúdo real, garantindo recomposição correta quando qualquer campo da configuração muda.
