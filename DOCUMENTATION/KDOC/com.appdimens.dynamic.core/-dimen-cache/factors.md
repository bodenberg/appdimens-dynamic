//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[factors](factors.md)

# factors

@[PublishedApi](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-published-api/index.html)@[JvmField](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-field/index.html)internal val [factors](factors.md): [DimenCache.ScreenFactors](-screen-factors/index.md)

EN Legacy padded screen-factors object, retained for binary/source compatibility. Production formulas resolve through [currentMetrics](current-metrics.md); [updateFactors](update-factors.md) mirrors the snapshot's derived values here so older code keeps compiling.

PT Objeto legado de fatores com padding, mantido por compatibilidade. As fórmulas de produção resolvem por [currentMetrics](current-metrics.md).
