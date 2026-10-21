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
package com.appdimens.dynamic.code.custom

import android.content.Context
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.custom.ScalingStrategies
import com.appdimens.dynamic.custom.ScalingStrategy
import com.appdimens.dynamic.custom.ScreenSnapshot
import com.appdimens.dynamic.custom.cacheKey
import com.appdimens.dynamic.custom.metricsOf
import com.appdimens.dynamic.custom.resolve
import com.appdimens.dynamic.custom.toScreenSnapshot

// EN Views / Kotlin code API. Like the other `code.*` packages, the short names return **px**.
// PT API para Views / código Kotlin. Como os outros pacotes `code.*`, os nomes curtos retornam **px**.

/**
 * EN Scales this dp value with [strategy] → **px**. `16.cdp(context, Brand)`.
 * PT Escala este valor dp com [strategy] → **px**.
 */
@JvmOverloads
fun Number.cdp(context: Context, strategy: ScalingStrategy = ScalingStrategies.default): Float {
    val base = toFloat()
    return DimenCache.getOrPut(strategy.cacheKey(base, DimenCache.ValueType.PX), context) {
        val m = DimenCache.currentMetrics
        strategy.resolve(m).scale(base) * m.density
    }
}

/**
 * EN Scales this dp value with [strategy] → **dp** (`Float`).
 * PT Escala este valor dp com [strategy] → **dp** (`Float`).
 */
@JvmOverloads
fun Number.cdpDp(context: Context, strategy: ScalingStrategy = ScalingStrategies.default): Float {
    val base = toFloat()
    return DimenCache.getOrPut(strategy.cacheKey(base, DimenCache.ValueType.DP), context) {
        strategy.resolve(DimenCache.currentMetrics).scale(base)
    }
}

/**
 * EN Scales this sp value for text → **px**, applying the strategy font-scale policy.
 * PT Escala este valor sp para texto → **px**, aplicando a política de font scale.
 */
@JvmOverloads
fun Number.csp(context: Context, strategy: ScalingStrategy = ScalingStrategies.default): Float {
    val base = toFloat()
    return DimenCache.getOrPut(strategy.cacheKey(base, DimenCache.ValueType.SP_PX_WITH_SCALE), context) {
        val m = DimenCache.currentMetrics
        strategy.resolve(m).scaleSpDp(base) * m.density
    }
}

/**
 * EN Text size in **sp units** for `TextView.setTextSize(COMPLEX_UNIT_SP, …)`
 * (the system applies its font scale; the strategy policy is pre-compensated).
 * PT Tamanho em **unidades sp** para `setTextSize(COMPLEX_UNIT_SP, …)`.
 */
@JvmOverloads
fun Number.cspUnits(context: Context, strategy: ScalingStrategy = ScalingStrategies.default): Float {
    val base = toFloat()
    return DimenCache.getOrPut(strategy.cacheKey(base, DimenCache.ValueType.SP_WITH_SCALE), context) {
        strategy.resolve(DimenCache.currentMetrics).scaleSpUnits(base)
    }
}

// EN Inline formulas — the formula **is** the value. PT Fórmulas inline — a fórmula **é** o valor.

/** EN `16.cdp(context, "base * pow(sw / 360, 0.8)")` → px. Shared per text. */
fun Number.cdp(context: Context, formula: String): Float = cdp(context, ScalingStrategies.formula(formula))

/** EN `16.cdpDp(context, "…")` → dp. */
fun Number.cdpDp(context: Context, formula: String): Float = cdpDp(context, ScalingStrategies.formula(formula))

/** EN `14.csp(context, "…")` → px for text. */
fun Number.csp(context: Context, formula: String): Float = csp(context, ScalingStrategies.formula(formula))

/** EN `14.cspUnits(context, "…")` → sp units. */
fun Number.cspUnits(context: Context, formula: String): Float = cspUnits(context, ScalingStrategies.formula(formula))

// EN Strategy-first facilitators. PT Facilitadores a partir da estratégia.

/** EN `Brand.px(context, 16)` → px. */
fun ScalingStrategy.px(context: Context, value: Number): Float = value.cdp(context, this)

/** EN `Brand.dp(context, 16)` → dp. */
fun ScalingStrategy.dp(context: Context, value: Number): Float = value.cdpDp(context, this)

/** EN `Brand.spPx(context, 14)` → px for text. */
fun ScalingStrategy.spPx(context: Context, value: Number): Float = value.csp(context, this)

/** EN Current factor for the window of [context]. PT Fator atual da janela de [context]. */
fun ScalingStrategy.factor(context: Context): Float = resolve(DimenCache.metricsOf(context)).factor

/** EN Debug breakdown for the window of [context]. PT Explicação para a janela de [context]. */
fun ScalingStrategy.explain(context: Context): String = resolve(DimenCache.metricsOf(context)).explain(id)

/** EN Platform-free snapshot of the window of this context. */
fun Context.screenSnapshot(): ScreenSnapshot = DimenCache.metricsOf(this).toScreenSnapshot()

/**
 * EN Reads a strategies JSON from `assets/` and registers it (see `ScalingStrategies.loadJson`).
 * PT Lê um JSON de estratégias de `assets/` e registra.
 */
fun ScalingStrategies.loadAsset(context: Context, path: String): Map<String, ScalingStrategy> =
    loadJson(context.assets.open(path).bufferedReader().use { it.readText() })

/**
 * EN Java-friendly facade. `DimenCustom.cdp(context, 16f, brand)`.
 * PT Fachada amigável para Java.
 */
object DimenCustom {
    @JvmStatic @JvmOverloads
    fun cdp(context: Context, value: Float, strategy: ScalingStrategy = ScalingStrategies.default): Float = value.cdp(context, strategy)

    @JvmStatic @JvmOverloads
    fun cdpDp(context: Context, value: Float, strategy: ScalingStrategy = ScalingStrategies.default): Float = value.cdpDp(context, strategy)

    @JvmStatic @JvmOverloads
    fun csp(context: Context, value: Float, strategy: ScalingStrategy = ScalingStrategies.default): Float = value.csp(context, strategy)

    @JvmStatic @JvmOverloads
    fun cspUnits(context: Context, value: Float, strategy: ScalingStrategy = ScalingStrategies.default): Float = value.cspUnits(context, strategy)

    @JvmStatic
    fun strategy(id: String): ScalingStrategy = ScalingStrategies.getOrThrow(id)

    /** EN `DimenCustom.cdp(context, 16f, DimenCustom.formula("base * (sw / 360)"))`. */
    @JvmStatic
    fun formula(formula: String): ScalingStrategy = ScalingStrategies.formula(formula)

    @JvmStatic
    fun loadAsset(context: Context, path: String): Map<String, ScalingStrategy> = ScalingStrategies.loadAsset(context, path)
}
