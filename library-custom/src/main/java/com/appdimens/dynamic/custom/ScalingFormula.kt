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
import kotlin.math.pow

/**
 * EN What a code formula can read, with the **same names as the text formulas**, so the IDE
 * autocompletes them and typos fail at compile time. Values are in dp; flags are `Boolean`.
 * PT O que uma fórmula em código pode ler, com os **mesmos nomes das fórmulas em texto**: o IDE
 * autocompleta e erros de digitação falham na compilação.
 */
open class ScreenScope internal constructor(
    /** EN The full window snapshot. PT O snapshot completo da janela. */
    val screen: ScreenSnapshot,
) {
    /** EN Smallest width (dp). */
    val sw: Float get() = screen.smallestWidthDp
    val width: Float get() = screen.widthDp
    val height: Float get() = screen.heightDp
    val shortSide: Float get() = screen.minDp
    val longSide: Float get() = screen.maxDp
    val diagonal: Float get() = screen.read(ScalingAxis.DIAGONAL)
    val perimeter: Float get() = screen.read(ScalingAxis.PERIMETER)
    val aspectRatio: Float get() = screen.aspectRatio
    val density: Float get() = screen.density
    val fontScale: Float get() = screen.fontScale

    /** EN `sw / 300` — the ratio `sdp` uses (`base * ratio` = `sdp`). */
    val ratio: Float get() = sw / ReferenceScreen.DEFAULT.read(ScalingAxis.SMALLEST_WIDTH)

    val landscape: Boolean get() = screen.isLandscape
    val portrait: Boolean get() = !screen.isLandscape
    val multiWindow: Boolean get() = screen.isMultiWindow
    val device: DeviceClass get() = screen.device
    val phone: Boolean get() = screen.device == DeviceClass.PHONE
    val tablet: Boolean get() = screen.device == DeviceClass.TABLET
    val desktop: Boolean get() = screen.device == DeviceClass.DESKTOP
    val tv: Boolean get() = screen.device == DeviceClass.TV
    val watch: Boolean get() = screen.device == DeviceClass.WATCH
    val car: Boolean get() = screen.device == DeviceClass.CAR
    val vr: Boolean get() = screen.device == DeviceClass.VR

    // EN Math helpers, so no import is needed. PT Funções matemáticas, sem precisar de import.
    fun pow(x: Float, exponent: Float): Float = x.pow(exponent)
    fun sqrt(x: Float): Float = kotlin.math.sqrt(x)
    fun ln(x: Float): Float = kotlin.math.ln(x)
    fun log10(x: Float): Float = kotlin.math.log10(x)
    fun exp(x: Float): Float = kotlin.math.exp(x)
    fun abs(x: Float): Float = kotlin.math.abs(x)
    fun round(x: Float): Float = kotlin.math.floor(x + 0.5f)
    fun min(a: Float, b: Float): Float = kotlin.math.min(a, b)
    fun max(a: Float, b: Float): Float = kotlin.math.max(a, b)
    fun clamp(x: Float, min: Float, max: Float): Float = x.coerceIn(min, max)
    fun lerp(a: Float, b: Float, t: Float): Float = a + (b - a) * t
    fun smoothstep(edge0: Float, edge1: Float, x: Float): Float {
        val t = ((x - edge0) / (edge1 - edge0)).coerceIn(0f, 1f)
        return t * t * (3f - 2f * t)
    }
}

/**
 * EN [ScreenScope] plus [base], the value being scaled (the `16` in `16.cdp(Hud)`).
 * PT [ScreenScope] mais [base], o valor sendo escalado (o `16` em `16.cdp(Hud)`).
 */
class FormulaScope internal constructor(screen: ScreenSnapshot, val base: Float) : ScreenScope(screen)

/**
 * EN One-liner strategy from a **code** formula — the result **is** the final value. Typed Kotlin
 * with autocomplete, no strings, no limits:
 * ```
 * val Hud = scalingFormula { base * pow(sw / 360f, 0.8f) }
 * val Card = scalingFormula { if (tablet) base * 1.2f else base * ratio }
 * Modifier.padding(16.cdp(Hud))
 * ```
 * Inside the braces: `base`, the window ([ScreenScope]: `sw`, `width`, `height`, `ratio`,
 * `tablet`, `landscape`, …) and `pow`, `clamp`, `min`, `max`, `lerp`… Must be pure (results are
 * cached per value and window). Declare it once (e.g. a top-level `val`), not inside a composable.
 * Not JSON-serializable: the text form `scalingFormula("base * pow(sw / 360, 0.8)")` is.
 * Without an [id] a unique `formula#N` id is generated; an id registers.
 *
 * PT Estratégia em uma linha a partir de uma fórmula em **código** — o resultado **é** o valor
 * final. Kotlin tipado com autocomplete, sem strings, sem limites.
 */
fun scalingFormula(id: String? = null, register: Boolean = id != null, formula: FormulaScope.() -> Float): ScalingStrategy {
    val transform = ValueTransform { base, _, screen -> FormulaScope(screen, base).formula() }
    val strategy = ScalingStrategy(id ?: "formula#${lambdaIds.incrementAndGet()}", ScalingConfig(transform = transform))
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/**
 * EN Strategy from a programmable **factor** — you return the number that multiplies every value:
 * ```
 * val Product = CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }
 * val Tv = CustomScaleStrategy { if (tv) height / 540f else ratio }
 * Modifier.padding(16.cdp(Product))   // 16 × factor
 * ```
 * Inside the braces: the window ([ScreenScope]) with autocomplete, plus any Kotlin code of yours.
 * The factor depends only on the window, so it runs once per window and every value then costs
 * one multiply (no per-value cache). A non-finite factor throws when the window resolves.
 * Without an [id] a unique `scale#N` id is generated; an id registers. Not JSON-serializable:
 * for JSON use a text curve (`curve = expression("clamp(width / 360, 0.85, 1.8)")`).
 *
 * PT Estratégia a partir de um **fator** programável — você devolve o número que multiplica todo
 * valor. Roda uma vez por janela; cada valor custa uma multiplicação.
 */
@Suppress("FunctionName") // factory named like the type it builds, as in `CoroutineScope()`
fun CustomScaleStrategy(id: String? = null, register: Boolean = id != null, factor: ScreenScope.() -> Float): ScalingStrategy {
    val curve = ScalingCurve.Custom(CurveFunction { _, screen -> ScreenScope(screen).factor() })
    val strategy = ScalingStrategy(id ?: "scale#${lambdaIds.incrementAndGet()}", ScalingConfig(curve = curve))
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/**
 * EN One-liner strategy from a **text** formula, for JSON, remote config or when the formula comes
 * from outside the code. Same names as the code form (`base`, `sw`, `tablet`, `pow`, …):
 * ```
 * val Hud = scalingFormula("base * pow(sw / 360, 0.8)")
 * Modifier.padding(16.cdp("base * pow(sw / 360, 0.8)"))   // inline
 * ```
 * Unknown names fail right away with the position and the list of valid names. Writing it as
 * `base * (…)` keeps it on the one-multiply fast path (the `(…)` part is computed once per
 * window); any other shape still works and is cached per value and window. Without an [id] the
 * strategy is shared per text, so it is cheap to call inside a composable.
 *
 * PT Estratégia em uma linha a partir de uma fórmula em **texto** (JSON, config remota).
 */
@JvmOverloads
fun scalingFormula(formula: String, id: String? = null, register: Boolean = id != null): ScalingStrategy {
    if (id == null && !register) return ScalingStrategies.formula(formula)
    val strategy = ScalingStrategy(id ?: formulaId(formula), ScalingConfig(transform = FormulaTransform(formula)))
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
