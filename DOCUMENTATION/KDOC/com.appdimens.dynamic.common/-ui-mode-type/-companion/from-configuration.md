//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../../index.md)/[com.appdimens.dynamic.common](../../index.md)/[UiModeType](../index.md)/[Companion](index.md)/[fromConfiguration](from-configuration.md)

# fromConfiguration

[jvm]
fun [fromConfiguration](from-configuration.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), foldingFeature: <Error class: unknown class>? = null): [UiModeType](../index.md)

EN Returns the UiModeType corresponding to the Configuration.uiMode value, taking into account physical foldable features using Jetpack WindowManager.

PT Retorna o UiModeType correspondente ao valor de Configuration.uiMode, levando em conta características físicas de dispositivos dobráveis usando Jetpack WindowManager.

#### Parameters

jvm

| | |
|---|---|
| context | Application context. |
| foldingFeature | Optional FoldingFeature obtained from WindowInfoTracker to dynamically adapt. |