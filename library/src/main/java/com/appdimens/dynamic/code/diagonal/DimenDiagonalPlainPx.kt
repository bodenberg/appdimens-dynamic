/**
 * EN View **Plain** helpers (`Float` px + [Context]): same **logic-only** branching as Compose `Dp`/`TextUnit` Plain pairs
 * in `*DpExtensions` / `*SpExtensions` (no extra scaling of receiver or alternate).
 * PT Helpers **Plain** para Views (`Float` px + [Context]): mesma ramificação **só lógica** que os pares Plain `Dp`/`TextUnit`
 * em `*DpExtensions` / `*SpExtensions` (sem nova escala do recetor nem do alternativo).
 *
 * EN Shared implementation: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 * PT Implementação partilhada: `com.appdimens.dynamic.code.plain` (`DimenPlainBranch.kt`): `plainRotatePx`, `plainModePx`, `plainQualifierPx`, `plainScreenPx`.
 */
package com.appdimens.dynamic.code.diagonal

import android.annotation.SuppressLint
import android.content.Context
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Orientation
import com.appdimens.dynamic.common.UiModeType
import com.appdimens.dynamic.code.plain.plainModePx
import com.appdimens.dynamic.code.plain.plainQualifierPx
import com.appdimens.dynamic.code.plain.plainRotatePx
import com.appdimens.dynamic.code.plain.plainScreenPx

fun Float.dgsdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.dgsdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dgsdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dgsdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.dghdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.dghdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dghdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dghdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainScreenPx(context, this, screenPx, uiModeType, qualifierType, qualifierValue)

fun Float.dgwdpRotatePlainPx(context: Context, rotationPx: Float, orientation: Orientation = Orientation.LANDSCAPE): Float =
    plainRotatePx(context, this, rotationPx, orientation)

fun Float.dgwdpModePlainPx(context: Context, modePx: Float, uiModeType: UiModeType): Float =
    plainModePx(context, this, modePx, uiModeType)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dgwdpQualifierPlainPx(context: Context, qualifiedPx: Float, qualifierType: DpQualifier, qualifierValue: Number): Float =
    plainQualifierPx(context, this, qualifiedPx, qualifierType, qualifierValue)

@SuppressLint("ConfigurationScreenWidthHeight")
fun Float.dgwdpScreenPlainPx(context: Context, screenPx: Float, uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Number): Float =
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
