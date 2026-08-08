//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[rememberDimenSp](remember-dimen-sp.md)

# rememberDimenSp

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable)fun [rememberDimenSp](remember-dimen-sp.md)(cacheKey: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), spStamp: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), match: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, passthrough: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit) = TextUnit.Unspecified, compute: () -> [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit)): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit)

EN Remembers a scaled [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit) (Sp path). Passthrough when [match](remember-dimen-sp.md) is false, so the `*Plain` variants keep stable Compose slots while the miss branch stays a true no-op.

PT Lembra um [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit); passthrough quando [match](remember-dimen-sp.md) é falso.
