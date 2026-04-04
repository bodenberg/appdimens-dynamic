//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.resize](index.md)/[autoResizeHeightSize](auto-resize-height-size.md)

# autoResizeHeightSize

[jvm]
fun <Error class: unknown class>.[autoResizeHeightSize](auto-resize-height-size.md)(min: <Error class: unknown class>, max: <Error class: unknown class>, step: <Error class: unknown class> = 2.dp, contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

EN Largest height in `min…max` that fits the **inner** height (after [contentPadding](auto-resize-height-size.md)) of this box.

[jvm]
fun <Error class: unknown class>.[autoResizeHeightSize](auto-resize-height-size.md)(min: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), max: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), step: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) = 2, contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

[jvm]
fun <Error class: unknown class>.[autoResizeHeightSize](auto-resize-height-size.md)(min: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), max: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), step: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md) = resizeFixedDp(1f), contentPadding: <Error class: unknown class> = PaddingValues(0.dp)): <Error class: unknown class>

EN Same as [autoResizeWidthSize](auto-resize-width-size.md) for the vertical axis and **screen**[ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md)s.