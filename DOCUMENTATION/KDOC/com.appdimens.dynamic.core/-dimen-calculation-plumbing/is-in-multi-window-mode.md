//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCalculationPlumbing](index.md)/[isInMultiWindowMode](is-in-multi-window-mode.md)

# isInMultiWindowMode

fun [isInMultiWindowMode](is-in-multi-window-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)?): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)

EN Returns the real window mode when an Activity is available, without retaining it. Since 3.1.8 this is a public helper so window snapshots ([DimenMetrics](../-dimen-metrics/index.md)) can be built per window; the previous internal `Context→Activity` cache was removed (a weak key paired with the same Activity as value would retain the key indirectly).

PT Retorna o modo real da janela quando há Activity disponível, sem retê-la (desde a 3.1.8).
