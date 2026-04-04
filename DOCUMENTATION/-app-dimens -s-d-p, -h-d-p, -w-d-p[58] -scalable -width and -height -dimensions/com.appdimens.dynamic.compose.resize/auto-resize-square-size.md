//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.resize](index.md)/[autoResizeSquareSize](auto-resize-square-size.md)

# autoResizeSquareSize

[jvm]
fun <Error class: unknown class>.[autoResizeSquareSize](auto-resize-square-size.md)(min: <Error class: unknown class>, max: <Error class: unknown class>, step: <Error class: unknown class> = 2.dp, contentPadding: <Error class: unknown class>? = null, contentPaddingUniformDp: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)? = null): <Error class: unknown class>

EN Largest square side in `min…max` (step [step](auto-resize-square-size.md)) that fits inside this box (smaller of width/height). EN [contentPadding](auto-resize-square-size.md) optional (RTL-aware). [contentPaddingUniformDp](auto-resize-square-size.md): `null`, `≤ 0`, or `-1` ignored; used only if [contentPadding](auto-resize-square-size.md) is null. PT [contentPadding](auto-resize-square-size.md) opcional. [contentPaddingUniformDp](auto-resize-square-size.md): `null`, `≤ 0` ou `-1` ignorados; só vale se [contentPadding](auto-resize-square-size.md) for `null`.

```kotlin
BoxWithConstraints {
    Image(Modifier.size(autoResizeSquareSize(12.sdp, 80.wdp)), …)
}
```

[jvm]
fun <Error class: unknown class>.[autoResizeSquareSize](auto-resize-square-size.md)(min: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), max: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html), step: [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) = 2, contentPadding: <Error class: unknown class>? = null, contentPaddingUniformDp: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)? = null): <Error class: unknown class>

PT Mesmo que [autoResizeSquareSize](auto-resize-square-size.md) com [Number](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-number/index.html) tratado como **dp** (`12` → `12.dp`).

[jvm]
fun <Error class: unknown class>.[autoResizeSquareSize](auto-resize-square-size.md)(min: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), max: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md), step: [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md) = resizeFixedDp(1f), contentPadding: <Error class: unknown class>? = null, contentPaddingUniformDp: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)? = null): <Error class: unknown class>

EN Same as [autoResizeSquareSize](auto-resize-square-size.md) but min/max/step may use % of Sw / W / H via [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md).