//[AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions](../../../index.md)/[com.appdimens.dynamic.code.perimeter](../index.md)/[DimenPerimeterDp](index.md)

# DimenPerimeterDp

object [DimenPerimeterDp](index.md)

EN Utility object for handling SDP (Scalable Dp) dimensions from Java.

PT Objeto utilitário para manipulação de dimensões SDP (Scalable Dp) no Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInDp](get-dimension-in-dp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic DP scaling function for Java. PT Função de escala DP genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [getDimensionInPx](get-dimension-in-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), qualifier: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), inverter: [Inverter](../../com.appdimens.dynamic.common/-inverter/index.md) = Inverter.DEFAULT, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Generic scaling function for Java. PT Função de escala genérica para Java.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdp](prhdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp). PT Resolução rápida para Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpa](prhdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpi](prhdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpia](prhdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpLw](prhdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in landscape orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpLwa](prhdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpLwi](prhdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpLwia](prhdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prhdpMode](prhdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpPw](prhdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hdp), but in portrait orientation it acts as Screen Width (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpPwa](prhdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpPwi](prhdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prhdpPwia](prhdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prhdpQualifier](prhdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prhdpRotate](prhdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.HEIGHT, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Height (hdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prhdpScreen](prhdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Height (hDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdp](prsdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp). PT Resolução rápida para Smallest Width (sdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpa](prsdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpa) with aspect ratio. PT Resolução rápida para Smallest Width (sdpa) com proporção de tela.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpi](prsdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpi) ignoring multi-windows. PT Resolução rápida para Smallest Width (sdpi) ignorando janelas múltiplas.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpia](prsdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdpia) ignoring multi-windows and with aspect ratio. PT Resolução rápida para Smallest Width (sdpia) ignorando janelas múltiplas e com proporção.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLh](prsdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLha](prsdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLhi](prsdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLhia](prsdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLw](prsdp-lw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in landscape orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação paisagem atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLwa](prsdp-lwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLwi](prsdp-lwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpLwia](prsdp-lwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prsdpMode](prsdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPh](prsdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Height (hdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Altura da Tela (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPha](prsdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPhi](prsdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPhia](prsdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPw](prsdp-pw.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (sdp), but in portrait orientation it acts as Screen Width (wdp). PT Resolução rápida para Smallest Width (sdp), mas na orientação retrato atua como Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPwa](prsdp-pwa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPwi](prsdp-pwi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prsdpPwia](prsdp-pwia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prsdpQualifier](prsdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prsdpRotate](prsdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.SMALL_WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Smallest Width (sdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prsdpScreen](prsdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Smallest Width (swDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdp](prwdp.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp). PT Resolução rápida para Largura da Tela (wdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpa](prwdpa.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpi](prwdpi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpia](prwdpia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpLh](prwdp-lh.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in landscape orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpLha](prwdp-lha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpLhi](prwdp-lhi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpLhia](prwdp-lhia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prwdpMode](prwdp-mode.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), modeValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with UiModeType override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpPh](prwdp-ph.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wdp), but in portrait orientation it acts as Screen Height (hdp).

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpPha](prwdp-pha.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpPhi](prwdp-phi.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [prwdpPhia](prwdp-phia.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prwdpQualifier](prwdp-qualifier.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), qualifiedValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prwdpRotate](prwdp-rotate.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), rotationValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md) = DpQualifier.WIDTH, orientation: [Orientation](../../com.appdimens.dynamic.common/-orientation/index.md) = Orientation.LANDSCAPE, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Facilitator for Screen Width (wdp) with rotation override.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)@[JvmOverloads](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-overloads/index.html)fun [prwdpScreen](prwdp-screen.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), screenValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), uiModeType: [UiModeType](../../com.appdimens.dynamic.common/-ui-mode-type/index.md), qualifierType: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md), qualifierValue: [Number](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-number/index.html), finalQualifierResolver: [DpQualifier](../../com.appdimens.dynamic.common/-dp-qualifier/index.md)? = null, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, applyAspectRatio: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, customSensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Quick resolution for Screen Width (wDP) context conditional scaling.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [scaled](scaled.md)(initialBaseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [DimenPerimeter](../-dimen-perimeter/index.md)

EN Starts the build chain for the custom dimension DimenPerimeter from a base Float.

EN Starts the build chain for the custom dimension DimenPerimeter from a base Int. PT Inicia a cadeia de construção para a dimensão customizada DimenPerimeter a partir de um Int base.

@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)fun [warmupCache](warmup-cache.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

EN Eagerly initializes [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) (persistence / DataStore) so the first resolution on a hot path avoids lazy-init work. PT Inicializa o [DimenCache](../../com.appdimens.dynamic.core/-dimen-cache/index.md) antecipadamente para evitar custo lazy no primeiro uso.

## Functions

| Name | Summary |
|---|---|
| [getDimensionInDp](get-dimension-in-dp.md) |  |
| [getDimensionInPx](get-dimension-in-px.md) |  |
| [prhdp](prhdp.md) |  |
| [prhdpa](prhdpa.md) |  |
| [prhdpi](prhdpi.md) |  |
| [prhdpia](prhdpia.md) |  |
| [prhdpLw](prhdp-lw.md) |  |
| [prhdpLwa](prhdp-lwa.md) |  |
| [prhdpLwi](prhdp-lwi.md) |  |
| [prhdpLwia](prhdp-lwia.md) |  |
| [prhdpMode](prhdp-mode.md) |  |
| [prhdpPw](prhdp-pw.md) |  |
| [prhdpPwa](prhdp-pwa.md) |  |
| [prhdpPwi](prhdp-pwi.md) |  |
| [prhdpPwia](prhdp-pwia.md) |  |
| [prhdpQualifier](prhdp-qualifier.md) |  |
| [prhdpRotate](prhdp-rotate.md) |  |
| [prhdpScreen](prhdp-screen.md) |  |
| [prsdp](prsdp.md) |  |
| [prsdpa](prsdpa.md) |  |
| [prsdpi](prsdpi.md) |  |
| [prsdpia](prsdpia.md) |  |
| [prsdpLh](prsdp-lh.md) |  |
| [prsdpLha](prsdp-lha.md) |  |
| [prsdpLhi](prsdp-lhi.md) |  |
| [prsdpLhia](prsdp-lhia.md) |  |
| [prsdpLw](prsdp-lw.md) |  |
| [prsdpLwa](prsdp-lwa.md) |  |
| [prsdpLwi](prsdp-lwi.md) |  |
| [prsdpLwia](prsdp-lwia.md) |  |
| [prsdpMode](prsdp-mode.md) |  |
| [prsdpPh](prsdp-ph.md) |  |
| [prsdpPha](prsdp-pha.md) |  |
| [prsdpPhi](prsdp-phi.md) |  |
| [prsdpPhia](prsdp-phia.md) |  |
| [prsdpPw](prsdp-pw.md) |  |
| [prsdpPwa](prsdp-pwa.md) |  |
| [prsdpPwi](prsdp-pwi.md) |  |
| [prsdpPwia](prsdp-pwia.md) |  |
| [prsdpQualifier](prsdp-qualifier.md) |  |
| [prsdpRotate](prsdp-rotate.md) |  |
| [prsdpScreen](prsdp-screen.md) |  |
| [prwdp](prwdp.md) |  |
| [prwdpa](prwdpa.md) |  |
| [prwdpi](prwdpi.md) |  |
| [prwdpia](prwdpia.md) |  |
| [prwdpLh](prwdp-lh.md) |  |
| [prwdpLha](prwdp-lha.md) |  |
| [prwdpLhi](prwdp-lhi.md) |  |
| [prwdpLhia](prwdp-lhia.md) |  |
| [prwdpMode](prwdp-mode.md) |  |
| [prwdpPh](prwdp-ph.md) |  |
| [prwdpPha](prwdp-pha.md) |  |
| [prwdpPhi](prwdp-phi.md) |  |
| [prwdpPhia](prwdp-phia.md) |  |
| [prwdpQualifier](prwdp-qualifier.md) |  |
| [prwdpRotate](prwdp-rotate.md) |  |
| [prwdpScreen](prwdp-screen.md) |  |
| [scaled](scaled.md) |  |
| [warmupCache](warmup-cache.md) |  |
