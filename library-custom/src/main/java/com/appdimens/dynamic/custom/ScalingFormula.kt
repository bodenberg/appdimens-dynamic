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

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * EN The simplest custom strategy: you write how a value becomes its scaled value, in code.
 * `(base, screen) -> dp`. Must be pure — results are cached per window.
 * PT A estratégia custom mais simples: você escreve como o valor vira o valor escalado, em código.
 */
fun interface ScalingFormula {
    fun value(base: Float, screen: ScreenSnapshot): Float
}

/**
 * EN One-liner strategy from a text formula — the formula **is** the final value:
 * ```
 * val Hud = scalingFormula("base * pow(sw / 360, 0.8)")
 * Modifier.padding(16.cdp(Hud))    // or straight: 16.cdp("base * pow(sw / 360, 0.8)")
 * ```
 * Variables: `base` (the value you pass, e.g. 16), `sw`, `width`, `height`, `shortSide`,
 * `longSide`, `diagonal`, `aspectRatio`, `density`, `fontScale`, the device flags (`tablet`,
 * `tv`, `landscape`, …) and `ratio` (`sw / 300`, the same ratio `sdp` uses). Same language and
 * same numbers on every AppDimens platform; see `DOCUMENTATION/custom.md`.
 *
 * Writing it as `base * (…)` keeps it on the one-multiply fast path (the `(…)` part is
 * computed once per window). Any other shape still works and is cached per value and window.
 *
 * Without an [id] the strategy is shared per formula text (calling this again with the same
 * text returns the same instance), so it is cheap to call inside a composable.
 *
 * PT Estratégia em uma linha a partir de uma fórmula em texto — a fórmula **é** o valor final.
 * Escrever como `base * (…)` mantém o caminho rápido (uma multiplicação).
 */
@JvmOverloads
fun scalingFormula(formula: String, id: String? = null, register: Boolean = id != null): ScalingStrategy {
    if (id == null && !register) return ScalingStrategies.formula(formula)
    val strategy = ScalingStrategy(id ?: formulaId(formula), ScalingConfig(transform = FormulaTransform(formula)))
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/**
 * EN One-liner strategy from a code formula — full Kotlin, no limits:
 * ```
 * val Hud = scalingFormula { base, screen -> base * (screen.smallestWidthDp / 360f).pow(0.8f) }
 * ```
 * Not JSON-serializable (use the text form for that). Without an [id] a unique one is generated.
 * PT Estratégia em uma linha a partir de uma fórmula em código. Não serializa em JSON.
 */
fun scalingFormula(id: String? = null, register: Boolean = id != null, formula: ScalingFormula): ScalingStrategy {
    val transform = ValueTransform { base, _, screen -> formula.value(base, screen) }
    val strategy = ScalingStrategy(id ?: "formula#${lambdaIds.incrementAndGet()}", ScalingConfig(transform = transform))
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/**
 * EN Programmable factor: you get the window and return the **factor** that multiplies every
 * value. Plain Kotlin, so any function, branch or helper of your code works. Must be pure.
 * PT Fator programável: você recebe a janela e devolve o **fator** que multiplica todo valor.
 */
fun interface ScreenFactor {
    fun factor(screen: ScreenSnapshot): Float
}

/**
 * EN Strategy from a programmable factor — the most flexible form, written in code:
 * ```
 * val Product = CustomScaleStrategy { screen ->
 *     (screen.widthDp / 360f).coerceIn(0.85f, 1.80f)
 * }
 * Modifier.padding(16.cdp(Product))   // 16 × factor
 * ```
 * The factor depends only on the window, so it runs once per window and every value then
 * costs one multiply (no per-value cache). A non-finite factor throws when the window resolves.
 * Without an [id] a unique `scale#N` id is generated. Not JSON-serializable: for JSON, use a
 * text curve (`curve = expression("clamp(width / 360, 0.85, 1.8)")`).
 *
 * PT Estratégia a partir de um fator programável — a forma mais flexível, escrita em código.
 * O fator depende só da janela: roda uma vez por janela e cada valor custa uma multiplicação.
 */
@Suppress("FunctionName") // factory named like the type it builds, as in `CoroutineScope()`
fun CustomScaleStrategy(id: String? = null, register: Boolean = id != null, factor: ScreenFactor): ScalingStrategy {
    val curve = ScalingCurve.Custom(CurveFunction { _, screen -> factor.factor(screen) })
    val strategy = ScalingStrategy(id ?: "scale#${lambdaIds.incrementAndGet()}", ScalingConfig(curve = curve))
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/** EN Shared strategies for inline formulas (`16.cdp("…")`), keyed by the formula text. */
internal object FormulaStrategies {
    /** EN Bound for distinct texts; past it, strategies are built per call (still correct). */
    const val MAX = 256

    private val byText = ConcurrentHashMap<String, ScalingStrategy>()

    fun get(formula: String): ScalingStrategy {
        byText[formula]?.let { return it }
        val strategy = ScalingStrategy(formulaId(formula), ScalingConfig(transform = FormulaTransform(formula)))
        if (byText.size >= MAX) return strategy
        return byText.putIfAbsent(formula, strategy) ?: strategy
    }

    fun clear() = byText.clear()
}

private val lambdaIds = AtomicInteger(0)

private fun formulaId(formula: String) = "formula:$formula"
