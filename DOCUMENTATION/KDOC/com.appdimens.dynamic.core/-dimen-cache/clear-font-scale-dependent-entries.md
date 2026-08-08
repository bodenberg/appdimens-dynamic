//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[clearFontScaleDependentEntries](clear-font-scale-dependent-entries.md)

# clearFontScaleDependentEntries

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)internal fun [clearFontScaleDependentEntries](clear-font-scale-dependent-entries.md)()

EN Compatibility hook. Font scale is part of [DimenMetrics](../-dimen-metrics/index.md) equality, so existing entries can never be read by a new font-scale snapshot; this drops all partitions (bounded) and notifies reset listeners.

PT Gancho de compatibilidade. Font scale participa da igualdade de [DimenMetrics](../-dimen-metrics/index.md), então entradas antigas nunca são lidas por um snapshot de novo fontScale; isto remove todas as partições.
