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
package com.appdimens.dynamic.compose.custom

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp
import com.appdimens.dynamic.core.rememberDimenDp
import com.appdimens.dynamic.core.rememberDimenPxFromDp
import com.appdimens.dynamic.core.rememberDimenSp
import com.appdimens.dynamic.core.rememberDimenSpPx
import com.appdimens.dynamic.core.spRememberStamp
import com.appdimens.dynamic.custom.ScalingStrategies
import com.appdimens.dynamic.custom.ScalingStrategy
import com.appdimens.dynamic.custom.cacheKey
import com.appdimens.dynamic.custom.metricsOf
import com.appdimens.dynamic.custom.resolve

// EN Scope ─────────────────────────────────────────────────────────────────────
// PT Escopo ────────────────────────────────────────────────────────────────────

/**
 * EN Strategy provided to a subtree by [ProvideScalingStrategy]; `null` means
 * [ScalingStrategies.default].
 * PT Estratégia fornecida a uma subárvore; `null` significa [ScalingStrategies.default].
 */
val LocalScalingStrategy = compositionLocalOf<ScalingStrategy?> { null }

/**
 * EN Every `16.cdp` / `14.csp` without an explicit strategy inside [content] uses [strategy].
 * PT Todo `16.cdp` / `14.csp` sem estratégia explícita dentro de [content] usa [strategy].
 */
@Composable
fun ProvideScalingStrategy(strategy: ScalingStrategy, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalScalingStrategy provides strategy, content = content)
}

/** EN Strategy in effect at this point of the composition. PT Estratégia em vigor aqui. */
val currentScalingStrategy: ScalingStrategy
    @Composable @ReadOnlyComposable get() = LocalScalingStrategy.current ?: ScalingStrategies.default

/**
 * EN Layout stamp plus the UI mode, so a rule keyed on the device class (car dock, desk…)
 * recomputes when only the UI mode changes.
 */
private fun customLayoutStamp(configuration: Configuration, context: Context): Long =
    layoutRememberStamp(configuration, context) xor (configuration.uiMode.toLong() * -0x61c8864680b583ebL)

// EN Dp ────────────────────────────────────────────────────────────────────────

/**
 * EN Scales this dp value with [strategy] → [Dp]. `16.cdp(Brand)`.
 * PT Escala este valor dp com [strategy] → [Dp].
 */
@Composable
fun Number.cdp(strategy: ScalingStrategy): Dp {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val base = toFloat()
    return rememberDimenDp(strategy.cacheKey(base, DimenCache.ValueType.DP), customLayoutStamp(configuration, androidContext), androidContext) {
        strategy.resolve(DimenCache.currentMetrics).scale(base)
    }
}

/** EN `16.cdp` — uses [currentScalingStrategy]. PT Usa a estratégia do escopo. */
@get:Composable
val Number.cdp: Dp get() = cdp(currentScalingStrategy)

/** EN Scaled value in **px**. PT Valor escalado em **px**. */
@Composable
fun Number.cdpPx(strategy: ScalingStrategy): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val base = toFloat()
    val stamp = pxRememberStamp(customLayoutStamp(configuration, androidContext), density)
    return rememberDimenPxFromDp(strategy.cacheKey(base, DimenCache.ValueType.DP), stamp, androidContext, density) {
        strategy.resolve(DimenCache.currentMetrics).scale(base)
    }
}

@get:Composable
val Number.cdpPx: Float get() = cdpPx(currentScalingStrategy)

// EN Sp ────────────────────────────────────────────────────────────────────────

/**
 * EN Scales this sp value for text → [TextUnit], honoring the strategy font-scale policy
 * (`Follow` / `Ignore` / `Clamp`). `14.csp(Brand)`.
 * PT Escala este valor sp para texto, respeitando a política de font scale.
 */
@Composable
fun Number.csp(strategy: ScalingStrategy): TextUnit {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val base = toFloat()
    val key = strategy.cacheKey(base, DimenCache.ValueType.SP_WITH_SCALE)
    val stamp = spRememberStamp(customLayoutStamp(configuration, androidContext), density)
    return rememberDimenSp(key, stamp) {
        DimenCache.getOrPut(key, androidContext) {
            strategy.resolve(DimenCache.currentMetrics).scaleSpUnits(base)
        }.sp
    }
}

@get:Composable
val Number.csp: TextUnit get() = csp(currentScalingStrategy)

/** EN Text size in **px**. PT Tamanho de texto em **px**. */
@Composable
fun Number.cspPx(strategy: ScalingStrategy): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val base = toFloat()
    val key = strategy.cacheKey(base, DimenCache.ValueType.SP_WITH_SCALE)
    val stamp = spRememberStamp(customLayoutStamp(configuration, androidContext), density)
    return rememberDimenSpPx(key, stamp xor 0x5BD1E995L) {
        val units = DimenCache.getOrPut(key, androidContext) {
            strategy.resolve(DimenCache.currentMetrics).scaleSpUnits(base)
        }
        with(density) { units.sp.toPx() }
    }
}

@get:Composable
val Number.cspPx: Float get() = cspPx(currentScalingStrategy)

// EN Inline formulas ───────────────────────────────────────────────────────────
// PT Fórmulas inline ───────────────────────────────────────────────────────────

/**
 * EN Scales with a text formula — the formula **is** the value: `16.cdp("base * pow(sw / 360, 0.8)")`.
 * Shared per text (see [scalingFormula]); write `base * (…)` for the one-multiply fast path.
 * PT Escala com uma fórmula em texto — a fórmula **é** o valor.
 */
@Composable
fun Number.cdp(formula: String): Dp = cdp(ScalingStrategies.formula(formula))

/** EN `16.cdpPx("base * (sw / 360)")` → px. */
@Composable
fun Number.cdpPx(formula: String): Float = cdpPx(ScalingStrategies.formula(formula))

/** EN `14.csp("base * min(sw / 360, 1.3)")` → [TextUnit] (font scale followed). */
@Composable
fun Number.csp(formula: String): TextUnit = csp(ScalingStrategies.formula(formula))

/** EN `14.cspPx("…")` → px. */
@Composable
fun Number.cspPx(formula: String): Float = cspPx(ScalingStrategies.formula(formula))

// EN Facilitators ──────────────────────────────────────────────────────────────
// PT Facilitadores ─────────────────────────────────────────────────────────────

/** EN `Brand.dp(16)`. */
@Composable
fun ScalingStrategy.dp(value: Number): Dp = value.cdp(this)

/** EN `Brand.sp(14)`. */
@Composable
fun ScalingStrategy.sp(value: Number): TextUnit = value.csp(this)

/** EN `Brand.px(16)`. */
@Composable
fun ScalingStrategy.px(value: Number): Float = value.cdpPx(this)

/**
 * EN Scales an existing [Dp] with [strategy]: `16.dp.scaledBy(Brand)`.
 * PT Escala um [Dp] existente: `16.dp.scaledBy(Brand)`.
 */
@Composable
fun Dp.scaledBy(strategy: ScalingStrategy = currentScalingStrategy): Dp = value.cdp(strategy)

/**
 * EN Factor of [strategy] for the current window (debug overlays, previews).
 * PT Fator de [strategy] para a janela atual.
 */
@Composable
fun ScalingStrategy.currentFactor(): Float {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    return remember(this, customLayoutStamp(configuration, androidContext)) {
        resolve(DimenCache.metricsOf(androidContext)).factor
    }
}

/** EN Human-readable breakdown for the current window. PT Explicação para a janela atual. */
@Composable
fun ScalingStrategy.explainCurrent(): String {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    return remember(this, customLayoutStamp(configuration, androidContext)) {
        resolve(DimenCache.metricsOf(androidContext)).explain(id)
    }
}
