/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-sdps.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 */
package com.appdimens.dynamic.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.appdimens.dynamic.common.UiModeType

/**
 * EN CompositionLocal for the current UiModeType.
 * PT CompositionLocal para o UiModeType atual.
 */
val LocalUiModeType = compositionLocalOf { UiModeType.UNDEFINED }

/**
 * EN Provider that automatically computes and provides the [UiModeType] (including foldables)
 * to all child components. This is highly recommended to improve performance of 'mode' extensions.
 *
 * PT Provedor que computa e fornece automaticamente o [UiModeType] (incluindo dobráveis)
 * para todos os componentes filhos. Isso é altamente recomendado para melhorar a performance das extensões 'mode'.
 */
@Composable
fun AppDimensProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = context.findActivity()
    
    val windowLayoutInfo = remember(activity) {
        activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) }
    }?.collectAsState(initial = null)
    
    val foldingFeature = windowLayoutInfo?.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()
        
    val uiModeType = UiModeType.fromConfiguration(context, foldingFeature)
    
    CompositionLocalProvider(LocalUiModeType provides uiModeType) {
        content()
    }
}

/**
 * EN Internal helper to get the UiModeType, falling back to computing it if not provided.
 * PT Auxiliar interno para obter o UiModeType, recalculando se não for fornecido.
 */
@Composable
@ReadOnlyComposable
internal fun getCurrentUiModeType(): UiModeType {
    val provided = LocalUiModeType.current
    if (provided != UiModeType.UNDEFINED) return provided
    
    // Fallback if AppDimensProvider is not used (less efficient but maintains backward compatibility)
    val context = LocalContext.current
    // Note: We don't use WindowInfoTracker here as it's not ReadOnlyComposable and expensive
    return UiModeType.fromConfiguration(context, null)
}

internal fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
