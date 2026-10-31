/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-dynamic.git
 *
 * Library: AppDimens — Custom scaling strategies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.custom

import android.content.Context
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics

/**
 * EN Converts the core per-window snapshot into the platform-free [ScreenSnapshot].
 * PT Converte o snapshot por janela do core no [ScreenSnapshot] independente de plataforma.
 */
fun DimenMetrics.toScreenSnapshot(): ScreenSnapshot = ScreenSnapshot(
    widthDp = screenWidthDp.toFloat(),
    heightDp = screenHeightDp.toFloat(),
    smallestWidthDp = smallestWidthDp,
    density = density,
    fontScale = fontScale,
    isLandscape = when (orientation) {
        2 -> true   // Configuration.ORIENTATION_LANDSCAPE
        1 -> false  // Configuration.ORIENTATION_PORTRAIT
        else -> screenWidthDp > screenHeightDp
    },
    isMultiWindow = isInMultiWindowMode,
    device = DeviceClass.fromAndroidUiMode(uiMode, smallestWidthDp),
)

/**
 * EN Resolves for a core snapshot; memoized per [DimenMetrics] (identity, then equality).
 * PT Resolve para um snapshot do core; memoizado por [DimenMetrics].
 */
fun ScalingStrategy.resolve(metrics: DimenMetrics): ResolvedScaling = resolve(metrics) { metrics.toScreenSnapshot() }

/**
 * EN `DimenCache` key for this strategy. Factor-only strategies are flagged non-cacheable
 * (resolved with one multiply); value-dependent ones are stored per window snapshot.
 * PT Chave do `DimenCache` para esta estratégia.
 */
fun ScalingStrategy.cacheKey(base: Float, valueType: DimenCache.ValueType): Long =
    DimenCache.buildCustomKey(
        baseValue = base,
        strategyKey = if (cacheId >= 0) cacheId else 0,
        isLandscape = false,          // the cache partition is already per window snapshot
        ignoreMultiWindows = false,
        valueType = valueType,
        cacheable = isValueDependent && cacheId >= 0,
    )

private val METRICS_PROBE_KEY: Long = DimenCache.buildCustomKey(
    baseValue = 0f, strategyKey = 0, isLandscape = false, ignoreMultiWindows = false,
    valueType = DimenCache.ValueType.DP, cacheable = false,
)

/**
 * EN Coherent window snapshot for [context] (same one the cache partitions by).
 * PT Snapshot coerente da janela de [context] (o mesmo usado pelo cache).
 */
fun DimenCache.metricsOf(context: Context): DimenMetrics {
    var out: DimenMetrics? = null
    getOrPut(METRICS_PROBE_KEY, context) {
        out = currentMetrics
        0f
    }
    return out ?: DimenMetrics.from(context.resources.configuration)
}
