//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[updateFactors](update-factors.md)

# updateFactors

private fun [updateFactors](update-factors.md)(config: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

EN Compatibility — builds a [DimenMetrics](../-dimen-metrics/index.md) snapshot from [config](update-factors.md), stores it as the fallback snapshot, and mirrors the derived values into the legacy padded [factors](factors.md) object. Production formulas resolve through [currentMetrics](current-metrics.md); no process-global strategy update is published.

PT Compatibilidade — cria um snapshot de [DimenMetrics](../-dimen-metrics/index.md), salva como fallback e espelha os valores no objeto legado [factors](factors.md).
