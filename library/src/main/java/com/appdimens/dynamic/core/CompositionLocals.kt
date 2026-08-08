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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.appdimens.dynamic.common.UiModeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * EN CompositionLocal for the current UiModeType.
 * PT CompositionLocal para o UiModeType atual.
 */
val LocalUiModeType = compositionLocalOf { UiModeType.UNDEFINED }

/**
 * Per-composition window snapshot. Consumers may use it to avoid repeatedly reading the
 * broad [LocalConfiguration] state and to keep every dimension in a composition on the same
 * coherent window snapshot.
 */
val LocalDimenMetrics = compositionLocalOf<DimenMetrics?> { null }

/**
 * EN Resolves the [WindowLayoutInfo] flow for [AppDimensProvider]. Always returns a
 * non-null [Flow] so [collectAsState] can be called unconditionally.
 *
 * PT Resolve o Flow de [WindowLayoutInfo]; sempre não-nulo para collectAsState incondicional.
 */
@PublishedApi
internal fun windowLayoutInfoFlowOrEmpty(activity: Activity?): Flow<WindowLayoutInfo> =
    activity?.let { WindowInfoTracker.getOrCreate(it).windowLayoutInfo(it) } ?: emptyFlow()

/**
 * EN Provider that automatically computes and provides the [UiModeType] (including foldables)
 * to all child components. **Recommended for performance:** without it, [getCurrentUiModeType]
 * falls back to [UiModeType.fromConfiguration] on every `*Mode` / `*Screen` facilitator call.
 * See the root README section **Performance: DimenCache → Integration checklist**.
 *
 * PT Provedor que computa e fornece o [UiModeType] (incl. dobráveis). **Recomendado para
 * desempenho:** sem ele, [getCurrentUiModeType] recalcula via [UiModeType.fromConfiguration]
 * em cada chamada às extensões `*Mode` / `*Screen`.
 */
@Composable
fun AppDimensProvider(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    // Memoize Activity lookup — Context→Activity chain is stable for a given Context.
    val activity = remember(context) { context.findActivity() }

    // Always collect — never call collectAsState behind a null-safe `?.` which would
    // skip the @Composable call when activity is null and resume it later (Compose
    // slot-table / inconsistent composition rule). emptyFlow() never emits, so
    // foldingFeature stays null — same observable behaviour as before.
    val flow = remember(activity) { windowLayoutInfoFlowOrEmpty(activity) }
    val windowLayoutInfo = flow.collectAsState(initial = null)

    val foldingFeature = windowLayoutInfo.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()?.firstOrNull()

    // Key on fold semantics, not the FoldingFeature instance — WindowLayoutInfo
    // often re-emits a new feature object with identical state/orientation.
    val uiModeType = remember(
        context,
        configuration.uiMode,
        configuration.smallestScreenWidthDp,
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.densityDpi,
        foldingFeature?.state,
        foldingFeature?.orientation,
        foldingFeature?.isSeparating,
    ) {
        UiModeType.fromConfiguration(context, foldingFeature)
    }

    val metrics = remember(
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.smallestScreenWidthDp,
        configuration.densityDpi,
        configuration.fontScale,
        configuration.orientation,
        configuration.uiMode,
        activity?.isInMultiWindowMode,
    ) {
        DimenMetrics.from(configuration, activity?.isInMultiWindowMode == true)
    }

    CompositionLocalProvider(
        LocalUiModeType provides uiModeType,
        LocalDimenMetrics provides metrics,
    ) {
        content()
    }
}

/**
 * EN Internal helper to get the UiModeType, falling back to computing it if not provided.
 * PT Auxiliar interno para obter o UiModeType, recalculando se não for fornecido.
 */
@Composable
fun getCurrentUiModeType(): UiModeType {
    val provided = LocalUiModeType.current
    if (provided != UiModeType.UNDEFINED) return provided
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    // Track only fields that affect UiMode — same fingerprint idea as DimenCache.
    return remember(
        configuration.uiMode,
        configuration.smallestScreenWidthDp,
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.densityDpi,
    ) {
        DimenCache.getCachedUiModeType(context)
    }
}

/**
 * EN Walks [ContextWrapper] chain to find the hosting [Activity], if any.
 * PT Percorre a cadeia de [ContextWrapper] para encontrar a [Activity] hospedeira, se existir.
 */
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
