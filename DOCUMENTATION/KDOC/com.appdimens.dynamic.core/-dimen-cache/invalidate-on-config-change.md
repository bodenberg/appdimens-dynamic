//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[invalidateOnConfigChange](invalidate-on-config-change.md)

# invalidateOnConfigChange

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [invalidateOnConfigChange](invalidate-on-config-change.md)(new: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))

EN Compatibility hook (since 3.1.8). Snapshot partitions make explicit invalidation unnecessary for correctness: a rotated, resized, or recreated window can never read a value computed for another snapshot. This call refreshes the fallback snapshot and does **not** erase other windows' hot entries.

PT Gancho de compatibilidade (desde a 3.1.8). As partições por snapshot tornam a invalidação explícita desnecessária; esta chamada atualiza o snapshot fallback sem apagar entradas de outras janelas.
