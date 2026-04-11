//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.resize](index.md)/[autoResizeSquareSize](auto-resize-square-size.md)

# autoResizeSquareSize

fun ERROR CLASS: Symbol not found for BoxWithConstraintsScope.[autoResizeSquareSize](auto-resize-square-size.md)(min: ERROR CLASS: Symbol not found for Dp, max: ERROR CLASS: Symbol not found for Dp, step: ERROR CLASS: Symbol not found for Dp = 2.dp, contentPadding: ERROR CLASS: Symbol not found for PaddingValues?? = null, contentPaddingUniformDp: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)? = null): ERROR CLASS: Symbol not found for Dp

EN Largest square side in `min…max` (step [step](auto-resize-square-size.md)) that fits inside this box (smaller of width/height). EN [contentPadding](auto-resize-square-size.md) optional (RTL-aware). [contentPaddingUniformDp](auto-resize-square-size.md): `null`, `≤ 0`, or `-1` ignored; used only if [contentPadding](auto-resize-square-size.md) is null. PT [contentPadding](auto-resize-square-size.md) opcional. [contentPaddingUniformDp](auto-resize-square-size.md): `null`, `≤ 0` ou `-1` ignorados; só vale se [contentPadding](auto-resize-square-size.md) for `null`.

PT Mesmo que [autoResizeSquareSize](auto-resize-square-size.md) com [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html) tratado como **dp** (`12` → `12.dp`).

EN Same as [autoResizeSquareSize](auto-resize-square-size.md) but min/max/step may use % of Sw / W / H via [ResizeBound](../com.appdimens.dynamic.core/-resize-bound/index.md).
