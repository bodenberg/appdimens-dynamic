//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.resize](index.md)/[autoResizeWidthSize](auto-resize-width-size.md)

# autoResizeWidthSize

[jvm]
fun <Error class: unknown class>.[autoResizeWidthSize](auto-resize-width-size.md)(min: <Error class: unknown class>, max: <Error class: unknown class>, step: <Error class: unknown class> = 2.dp, contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

EN Largest width in `min…max` that fits the **inner** width (after [contentPadding](auto-resize-width-size.md)) of this box.

```kotlin
Image(Modifier.fillMaxWidth().height(autoResizeWidthSize(40.dp, 200.dp)), …)
```

[jvm]
fun <Error class: unknown class>.[autoResizeWidthSize](auto-resize-width-size.md)(min: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), max: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), step: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) = 2, contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

[jvm]
fun <Error class: unknown class>.[autoResizeWidthSize](auto-resize-width-size.md)(min: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), max: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), step: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md) = resizeFixedDp(1f), contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

EN [min](auto-resize-width-size.md)/[max](auto-resize-width-size.md)/[step](auto-resize-width-size.md) as [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md): fixed dp/sp or **screen** % (sw / w / h via [com.appdimens.dynamic.core.resizePercentSw](../com.appdimens.dynamic.core/resize-percent-sw.md) etc.).