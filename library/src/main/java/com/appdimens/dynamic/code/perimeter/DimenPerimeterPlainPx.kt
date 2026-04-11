/**
 * EN View **Plain** helpers (`Float` px + [Context]): same **logic-only** branching as Compose `Dp`/`TextUnit` Plain pairs
 * in `*DpExtensions` / `*SpExtensions` (no extra scaling of receiver or alternate).
 * PT Helpers **Plain** para Views (`Float` px + [Context]): mesma ramificação **só lógica** que os pares Plain `Dp`/`TextUnit`
 * em `*DpExtensions` / `*SpExtensions` (sem nova escala do recetor nem do alternativo).
 *
 * EN Shared implementation: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 * PT Implementação partilhada: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 */
package com.appdimens.dynamic.code.perimeter

import android.annotation.SuppressLint
import android.content.Context
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.code.plain.plainModePx
import com.appdimens.dynamic.code.plain.plainQualifierPx
import com.appdimens.dynamic.code.plain.plainRotatePx
import com.appdimens.dynamic.code.plain.plainScreenPx

fun Float.prsdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.prsdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prsdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prsdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.prhdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.prhdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prhdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prhdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.prwdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.prwdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prwdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.prwdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.sspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.sspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.sspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.sspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.hspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.hspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.hspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.hspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.wspRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.wspModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.wspQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.wspScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)
