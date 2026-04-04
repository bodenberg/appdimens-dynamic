//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[MAX_RESIZE_STEPS](-m-a-x_-r-e-s-i-z-e_-s-t-e-p-s.md)

# MAX_RESIZE_STEPS

[jvm]
private const val [MAX_RESIZE_STEPS](-m-a-x_-r-e-s-i-z-e_-s-t-e-p-s.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) = 4096

EN Builds ascending px samples from minPx to maxPx inclusive, advancing by stepPx. If stepPx<= 0, returns `[minPx.coerceIn(minPx, maxPx)]` (single sample).