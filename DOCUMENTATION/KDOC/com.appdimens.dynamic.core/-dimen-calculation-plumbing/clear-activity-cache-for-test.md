//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCalculationPlumbing](index.md)/[clearActivityCacheForTest](clear-activity-cache-for-test.md)

# clearActivityCacheForTest

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)internal fun [clearActivityCacheForTest](clear-activity-cache-for-test.md)()

EN Kept as a source-compatible test hook. There is no longer a Context→Activity cache: a weak key paired with the same Activity as value would retain the key indirectly.

PT Hook de teste mantido por compatibilidade de fonte; o cache Context→Activity foi removido na 3.1.7.
