//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../index.md)/[com.appdimens.dynamic.core](index.md)/[spRememberStamp](sp-remember-stamp.md)

# spRememberStamp

fun [spRememberStamp](sp-remember-stamp.md)(layoutStamp: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html), density: ERROR CLASS: Symbol not found for Density): [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html)

EN Sp-path remember key: [layoutRememberStamp](layout-remember-stamp.md) xor raw bits of `Density.density` and `Density.fontScale` (font scale matters for sp). Prefer this over [pxRememberStamp](px-remember-stamp.md) for text units, so a font-scale change invalidates the remembered Sp result.

PT Chave de remember para caminhos Sp: [layoutRememberStamp](layout-remember-stamp.md) xor bits brutos de densidade e escala de fonte. Prefira esta a [pxRememberStamp](px-remember-stamp.md) para unidades de texto, para que a mudança de escala de fonte invalide o resultado Sp lembrado.
