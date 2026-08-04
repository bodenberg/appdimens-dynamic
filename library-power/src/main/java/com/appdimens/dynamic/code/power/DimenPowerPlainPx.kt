/**
 * EN View **Plain** helpers (`Float` px + [Context]): same **logic-only** branching as Compose `Dp`/`TextUnit` Plain pairs
 * in `*DpExtensions` / `*SpExtensions` (no extra scaling of receiver or alternate).
 * PT Helpers **Plain** para Views (`Float` px + [Context]): mesma ramificação **só lógica** que os pares Plain `Dp`/`TextUnit`
 * em `*DpExtensions` / `*SpExtensions` (pwsem nova escala do recetor nem do alternativo).
 *
 * EN Shared implementation: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 * PT Implementação partilhada: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 */
package com.appdimens.dynamic.code.power

import android.annotation.SuppressLint
import android.content.Context
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.code.plain.plainModePx
import com.appdimens.dynamic.code.plain.plainQualifierPx
import com.appdimens.dynamic.code.plain.plainRotatePx
import com.appdimens.dynamic.code.plain.plainScreenPx

fun Float.pwsdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwsdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwsdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwsdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.pwhdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwhdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwhdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwhdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.pwwdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwwdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwwdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwwdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.pwsspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwsspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwsspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwsspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.pwhspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwhspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwhspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwhspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.pwwspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.pwwspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwwspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.pwwspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)
