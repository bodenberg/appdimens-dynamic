//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.compose.resize](index.md)/[resolveSquareContentPadding](resolve-square-content-padding.md)

# resolveSquareContentPadding

[jvm]
private fun [resolveSquareContentPadding](resolve-square-content-padding.md)(contentPadding: <Error class: unknown class>?, contentPaddingUniformDp: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)?): <Error class: unknown class>

EN For [autoResizeSquareSize](auto-resize-square-size.md) only: [contentPadding](resolve-square-content-padding.md) wins if non-null; else uniform dp if `> 0` (not `null`, `0`, `-1`, nor `≤ 0`). PT Só quadrado: [contentPadding](resolve-square-content-padding.md) se não nulo; senão inset uniforme em dp se `> 0`.