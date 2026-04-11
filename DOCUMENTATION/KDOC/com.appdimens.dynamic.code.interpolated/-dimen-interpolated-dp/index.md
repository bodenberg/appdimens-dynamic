//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.interpolated](../index.md)/[DimenInterpolatedDp](index.md)

# DimenInterpolatedDp

object [DimenInterpolatedDp](index.md)

EN Utility object for handling SDP (Scalable Dp) dimensions from Java.

PT Objeto utilitário para manipulação de dimensões SDP (Scalable Dp) no Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInDp](get-dimension-in-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic DP scaling function for Java. PT Função de escala DP genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInPx](get-dimension-in-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic scaling function for Java. PT Função de escala genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdp](ihdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp). PT Resolução rápida para Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpa](ihdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpi](ihdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpia](ihdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpLw](ihdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpLwa](ihdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpLwi](ihdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpLwia](ihdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ihdpMode](ihdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpPw](ihdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpPwa](ihdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpPwi](ihdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [ihdpPwia](ihdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ihdpQualifier](ihdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ihdpRotate](ihdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.HEIGHT, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [ihdpScreen](ihdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdp](isdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp). PT Resolução rápida para Smallest Width (sdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpa](isdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpa) with aspect ratio. PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpi](isdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows. PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpia](isdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio. PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLh](isdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLha](isdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLhi](isdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLhia](isdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLw](isdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLwa](isdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLwi](isdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpLwia](isdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [isdpMode](isdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPh](isdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPha](isdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPhi](isdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPhia](isdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPw](isdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPwa](isdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPwi](isdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [isdpPwia](isdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [isdpQualifier](isdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [isdpRotate](isdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [isdpScreen](isdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdp](iwdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp). PT Resolução rápida para Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpa](iwdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpi](iwdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpia](iwdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpLh](iwdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpLha](iwdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpLhi](iwdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpLhia](iwdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [iwdpMode](iwdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpPh](iwdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpPha](iwdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpPhi](iwdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [iwdpPhia](iwdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [iwdpQualifier](iwdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [iwdpRotate](iwdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [iwdpScreen](iwdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [scaled](scaled.md)(initialBaseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [DimenInterpolated](../-dimen-interpolated/index.md)

EN Starts the build chain for the custom dimension DimenInterpolated from a base Float.

EN Starts the build chain for the custom dimension DimenInterpolated from a base Int. PT Inicia a cadeia de construção para a dimensão customizada DimenInterpolated a partir de um Int base.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [warmupCache](warmup-cache.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Eagerly initializes [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work. PT Inicializa o [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) antecipadamente para evitar custo lazy no primeiro uso.

## Functions

| Name | Summary |
|---|---|
| [getDimensionInDp](get-dimension-in-dp.md) |  |
| [getDimensionInPx](get-dimension-in-px.md) |  |
| [ihdp](ihdp.md) |  |
| [ihdpa](ihdpa.md) |  |
| [ihdpi](ihdpi.md) |  |
| [ihdpia](ihdpia.md) |  |
| [ihdpLw](ihdp-lw.md) |  |
| [ihdpLwa](ihdp-lwa.md) |  |
| [ihdpLwi](ihdp-lwi.md) |  |
| [ihdpLwia](ihdp-lwia.md) |  |
| [ihdpMode](ihdp-mode.md) |  |
| [ihdpPw](ihdp-pw.md) |  |
| [ihdpPwa](ihdp-pwa.md) |  |
| [ihdpPwi](ihdp-pwi.md) |  |
| [ihdpPwia](ihdp-pwia.md) |  |
| [ihdpQualifier](ihdp-qualifier.md) |  |
| [ihdpRotate](ihdp-rotate.md) |  |
| [ihdpScreen](ihdp-screen.md) |  |
| [isdp](isdp.md) |  |
| [isdpa](isdpa.md) |  |
| [isdpi](isdpi.md) |  |
| [isdpia](isdpia.md) |  |
| [isdpLh](isdp-lh.md) |  |
| [isdpLha](isdp-lha.md) |  |
| [isdpLhi](isdp-lhi.md) |  |
| [isdpLhia](isdp-lhia.md) |  |
| [isdpLw](isdp-lw.md) |  |
| [isdpLwa](isdp-lwa.md) |  |
| [isdpLwi](isdp-lwi.md) |  |
| [isdpLwia](isdp-lwia.md) |  |
| [isdpMode](isdp-mode.md) |  |
| [isdpPh](isdp-ph.md) |  |
| [isdpPha](isdp-pha.md) |  |
| [isdpPhi](isdp-phi.md) |  |
| [isdpPhia](isdp-phia.md) |  |
| [isdpPw](isdp-pw.md) |  |
| [isdpPwa](isdp-pwa.md) |  |
| [isdpPwi](isdp-pwi.md) |  |
| [isdpPwia](isdp-pwia.md) |  |
| [isdpQualifier](isdp-qualifier.md) |  |
| [isdpRotate](isdp-rotate.md) |  |
| [isdpScreen](isdp-screen.md) |  |
| [iwdp](iwdp.md) |  |
| [iwdpa](iwdpa.md) |  |
| [iwdpi](iwdpi.md) |  |
| [iwdpia](iwdpia.md) |  |
| [iwdpLh](iwdp-lh.md) |  |
| [iwdpLha](iwdp-lha.md) |  |
| [iwdpLhi](iwdp-lhi.md) |  |
| [iwdpLhia](iwdp-lhia.md) |  |
| [iwdpMode](iwdp-mode.md) |  |
| [iwdpPh](iwdp-ph.md) |  |
| [iwdpPha](iwdp-pha.md) |  |
| [iwdpPhi](iwdp-phi.md) |  |
| [iwdpPhia](iwdp-phia.md) |  |
| [iwdpQualifier](iwdp-qualifier.md) |  |
| [iwdpRotate](iwdp-rotate.md) |  |
| [iwdpScreen](iwdp-screen.md) |  |
| [scaled](scaled.md) |  |
| [warmupCache](warmup-cache.md) |  |
