//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.core](../index.md)/[DimenCache](index.md)/[init](init.md)

# init

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [init](init.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Synchronous, window-local initialization (since 3.1.7). Captures the application context, refreshes the fallback snapshot from the current configuration, and marks the cache initialized. There is no DataStore read and no background I/O — a persisted result cache cannot be made correct across a configuration, formula, density or multi-window change.

PT Inicialização síncrona e local à janela (desde a 3.1.7). Sem leitura de DataStore e sem I/O em background.
