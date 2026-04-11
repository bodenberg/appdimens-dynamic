//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.percent](../index.md)/[DimenPercentDp](index.md)

# DimenPercentDp

object [DimenPercentDp](index.md)

EN Utility object for handling SDP (Scalable Dp) dimensions from Java.

PT Objeto utilitário para manipulação de dimensões SDP (Scalable Dp) no Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInDp](get-dimension-in-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic DP scaling function for Java. PT Função de escala DP genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInPx](get-dimension-in-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic scaling function for Java. PT Função de escala genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdp](phdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp). PT Resolução rápida para Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpa](phdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpi](phdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpia](phdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpLw](phdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpLwa](phdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpLwi](phdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpLwia](phdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [phdpMode](phdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpPw](phdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpPwa](phdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpPwi](phdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [phdpPwia](phdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [phdpQualifier](phdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [phdpRotate](phdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.HEIGHT, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [phdpScreen](phdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdp](psdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp). PT Resolução rápida para Smallest Width (sdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpa](psdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpa) with aspect ratio. PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpi](psdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows. PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpia](psdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio. PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLh](psdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLha](psdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLhi](psdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLhia](psdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLw](psdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLwa](psdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLwi](psdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpLwia](psdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [psdpMode](psdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPh](psdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPha](psdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPhi](psdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPhia](psdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPw](psdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPwa](psdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPwi](psdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [psdpPwia](psdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [psdpQualifier](psdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [psdpRotate](psdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [psdpScreen](psdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdp](pwdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp). PT Resolução rápida para Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpa](pwdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpi](pwdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpia](pwdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpLh](pwdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpLha](pwdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpLhi](pwdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpLhia](pwdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [pwdpMode](pwdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpPh](pwdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpPha](pwdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpPhi](pwdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [pwdpPhia](pwdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [pwdpQualifier](pwdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [pwdpRotate](pwdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [pwdpScreen](pwdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [scaled](scaled.md)(initialBaseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [DimenPercent](../-dimen-percent/index.md)

EN Starts the build chain for the custom dimension DimenPercent from a base Float.

EN Starts the build chain for the custom dimension DimenPercent from a base Int. PT Inicia a cadeia de construção para a dimensão customizada DimenPercent a partir de um Int base.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [warmupCache](warmup-cache.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Eagerly initializes [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work. PT Inicializa o [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) antecipadamente para evitar custo lazy no primeiro uso.

## Functions

| Name | Summary |
|---|---|
| [getDimensionInDp](get-dimension-in-dp.md) |  |
| [getDimensionInPx](get-dimension-in-px.md) |  |
| [phdp](phdp.md) |  |
| [phdpa](phdpa.md) |  |
| [phdpi](phdpi.md) |  |
| [phdpia](phdpia.md) |  |
| [phdpLw](phdp-lw.md) |  |
| [phdpLwa](phdp-lwa.md) |  |
| [phdpLwi](phdp-lwi.md) |  |
| [phdpLwia](phdp-lwia.md) |  |
| [phdpMode](phdp-mode.md) |  |
| [phdpPw](phdp-pw.md) |  |
| [phdpPwa](phdp-pwa.md) |  |
| [phdpPwi](phdp-pwi.md) |  |
| [phdpPwia](phdp-pwia.md) |  |
| [phdpQualifier](phdp-qualifier.md) |  |
| [phdpRotate](phdp-rotate.md) |  |
| [phdpScreen](phdp-screen.md) |  |
| [psdp](psdp.md) |  |
| [psdpa](psdpa.md) |  |
| [psdpi](psdpi.md) |  |
| [psdpia](psdpia.md) |  |
| [psdpLh](psdp-lh.md) |  |
| [psdpLha](psdp-lha.md) |  |
| [psdpLhi](psdp-lhi.md) |  |
| [psdpLhia](psdp-lhia.md) |  |
| [psdpLw](psdp-lw.md) |  |
| [psdpLwa](psdp-lwa.md) |  |
| [psdpLwi](psdp-lwi.md) |  |
| [psdpLwia](psdp-lwia.md) |  |
| [psdpMode](psdp-mode.md) |  |
| [psdpPh](psdp-ph.md) |  |
| [psdpPha](psdp-pha.md) |  |
| [psdpPhi](psdp-phi.md) |  |
| [psdpPhia](psdp-phia.md) |  |
| [psdpPw](psdp-pw.md) |  |
| [psdpPwa](psdp-pwa.md) |  |
| [psdpPwi](psdp-pwi.md) |  |
| [psdpPwia](psdp-pwia.md) |  |
| [psdpQualifier](psdp-qualifier.md) |  |
| [psdpRotate](psdp-rotate.md) |  |
| [psdpScreen](psdp-screen.md) |  |
| [pwdp](pwdp.md) |  |
| [pwdpa](pwdpa.md) |  |
| [pwdpi](pwdpi.md) |  |
| [pwdpia](pwdpia.md) |  |
| [pwdpLh](pwdp-lh.md) |  |
| [pwdpLha](pwdp-lha.md) |  |
| [pwdpLhi](pwdp-lhi.md) |  |
| [pwdpLhia](pwdp-lhia.md) |  |
| [pwdpMode](pwdp-mode.md) |  |
| [pwdpPh](pwdp-ph.md) |  |
| [pwdpPha](pwdp-pha.md) |  |
| [pwdpPhi](pwdp-phi.md) |  |
| [pwdpPhia](pwdp-phia.md) |  |
| [pwdpQualifier](pwdp-qualifier.md) |  |
| [pwdpRotate](pwdp-rotate.md) |  |
| [pwdpScreen](pwdp-screen.md) |  |
| [scaled](scaled.md) |  |
| [warmupCache](warmup-cache.md) |  |
