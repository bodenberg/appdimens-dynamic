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

import com.appdimens.dynamic.common.Orientation
import kotlin.math.sqrt

/**
 * EN Screen measure a custom strategy scales against. The value read from the screen is
 * divided by the same measure of the [ReferenceScreen] to obtain the ratio fed to the curve.
 *
 * PT Medida da tela usada pela estratégia custom. O valor lido da tela é dividido pela
 * mesma medida da [ReferenceScreen] para obter a razão passada à curva.
 */
enum class ScalingAxis(val jsonName: String) {
    /** EN Smallest width (`sw`) — ref = min(width, height). PT Menor largura. */
    SMALLEST_WIDTH("smallestWidth"),
    /** EN Current window width — ref = reference width. PT Largura atual da janela. */
    WIDTH("width"),
    /** EN Current window height — ref = reference height. PT Altura atual da janela. */
    HEIGHT("height"),
    /** EN Shorter side of the window. PT Menor lado da janela. */
    MIN("min"),
    /** EN Longer side of the window. PT Maior lado da janela. */
    MAX("max"),
    /** EN √(w² + h²). PT Diagonal da janela. */
    DIAGONAL("diagonal"),
    /** EN w + h (half perimeter). PT Semiperímetro. */
    PERIMETER("perimeter");

    companion object {
        @JvmStatic
        fun fromJson(name: String): ScalingAxis =
            entries.firstOrNull { it.jsonName.equals(name, ignoreCase = true) || it.name.equals(name, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown axis '$name'. Expected one of ${entries.map { it.jsonName }}")
    }
}

/**
 * EN Device family used by rules. Detected from the window snapshot (UI mode + size).
 * PT Família do dispositivo usada pelas regras. Detectada pelo snapshot (modo de UI + tamanho).
 */
enum class DeviceClass(val jsonName: String) {
    PHONE("phone"),
    /** EN Normal UI mode with `sw >= 600`. PT Modo normal com `sw >= 600`. */
    TABLET("tablet"),
    /** EN Desk-docked / desktop windowing. PT Desktop / dock de mesa. */
    DESKTOP("desktop"),
    TV("tv"),
    WATCH("watch"),
    CAR("car"),
    VR("vr");

    companion object {
        /** EN `sw` (dp) from which a normal device is a tablet. PT `sw` a partir do qual é tablet. */
        const val TABLET_MIN_SMALLEST_WIDTH_DP = 600f

        @JvmStatic
        fun fromJson(name: String): DeviceClass =
            entries.firstOrNull { it.jsonName.equals(name, ignoreCase = true) || it.name.equals(name, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown device '$name'. Expected one of ${entries.map { it.jsonName }}")

        /**
         * EN Maps an Android `Configuration.uiMode` (type bits) and the smallest width to a class.
         * Constants are inlined so the mapping stays platform-free (KMP / unit tests).
         * PT Mapeia `Configuration.uiMode` (bits de tipo) e a menor largura para uma classe.
         */
        @JvmStatic
        fun fromAndroidUiMode(uiMode: Int, smallestWidthDp: Float): DeviceClass = when (uiMode and 0x0F) {
            2 -> DESKTOP      // UI_MODE_TYPE_DESK
            3 -> CAR          // UI_MODE_TYPE_CAR
            4 -> TV           // UI_MODE_TYPE_TELEVISION
            6 -> WATCH        // UI_MODE_TYPE_WATCH
            7 -> VR           // UI_MODE_TYPE_VR_HEADSET
            else -> if (smallestWidthDp >= TABLET_MIN_SMALLEST_WIDTH_DP) TABLET else PHONE
        }
    }
}

/**
 * EN Immutable, platform-free description of the window a value is resolved for.
 * Every custom formula is a pure function of this snapshot.
 *
 * PT Descrição imutável e independente de plataforma da janela. Toda fórmula custom é
 * uma função pura deste snapshot.
 */
data class ScreenSnapshot(
    val widthDp: Float,
    val heightDp: Float,
    val smallestWidthDp: Float = minOf(widthDp, heightDp),
    val density: Float = 1f,
    val fontScale: Float = 1f,
    val isLandscape: Boolean = widthDp > heightDp,
    val isMultiWindow: Boolean = false,
    val device: DeviceClass = if (smallestWidthDp >= DeviceClass.TABLET_MIN_SMALLEST_WIDTH_DP) DeviceClass.TABLET else DeviceClass.PHONE,
) {
    val minDp: Float get() = minOf(widthDp, heightDp)
    val maxDp: Float get() = maxOf(widthDp, heightDp)

    /** EN `max / min` (≥ 1). PT `maior / menor` (≥ 1). */
    val aspectRatio: Float get() = if (minDp > 0f) maxDp / minDp else 1f

    /** EN Reads [axis] from this snapshot, in dp. PT Lê [axis] deste snapshot, em dp. */
    fun read(axis: ScalingAxis): Float = when (axis) {
        ScalingAxis.SMALLEST_WIDTH -> smallestWidthDp
        ScalingAxis.WIDTH -> widthDp
        ScalingAxis.HEIGHT -> heightDp
        ScalingAxis.MIN -> minDp
        ScalingAxis.MAX -> maxDp
        ScalingAxis.DIAGONAL -> sqrt(minDp * minDp + maxDp * maxDp)
        ScalingAxis.PERIMETER -> minDp + maxDp
    }
}

/**
 * EN Design frame the values were drawn on (dp). Defaults to the AppDimens base 300×533.
 * PT Frame de design em que os valores foram desenhados (dp). Padrão: base 300×533.
 */
data class ReferenceScreen(val width: Float = 300f, val height: Float = 533f) {
    init {
        require(width.isFinite() && width > 0f) { "reference width must be > 0" }
        require(height.isFinite() && height > 0f) { "reference height must be > 0" }
    }

    /** EN Same measure as [ScreenSnapshot.read], for the reference frame. PT Mesma medida no frame de referência. */
    fun read(axis: ScalingAxis): Float = when (axis) {
        ScalingAxis.SMALLEST_WIDTH, ScalingAxis.MIN -> minOf(width, height)
        ScalingAxis.WIDTH -> width
        ScalingAxis.HEIGHT -> height
        ScalingAxis.MAX -> maxOf(width, height)
        ScalingAxis.DIAGONAL -> sqrt(width * width + height * height)
        ScalingAxis.PERIMETER -> width + height
    }

    companion object {
        @JvmField val DEFAULT = ReferenceScreen()
    }
}

/**
 * EN Point of a [ScalingCurve.Breakpoints] table: at [at] dp on the axis, the factor is [factor].
 * PT Ponto da tabela [ScalingCurve.Breakpoints]: em [at] dp no eixo, o fator é [factor].
 */
data class Breakpoint(val at: Float, val factor: Float) {
    init {
        require(at.isFinite() && at >= 0f) { "breakpoint 'at' must be finite and >= 0" }
        require(factor.isFinite()) { "breakpoint factor must be finite" }
    }
}

/**
 * EN Pure user formula: `(ratio, screen) -> factor`. Must not read mutable state — it runs
 * once per window snapshot and the result is memoized.
 * PT Fórmula pura do usuário: `(razão, tela) -> fator`. Não pode ler estado mutável — roda
 * uma vez por snapshot e o resultado é memoizado.
 */
fun interface CurveFunction {
    fun factor(ratio: Float, screen: ScreenSnapshot): Float
}

/**
 * EN Pure per-value hook applied after scaling: `(base, scaled, screen) -> result` (dp).
 * Using it makes the strategy value-dependent, so results go through `DimenCache`.
 * PT Hook puro por valor aplicado após a escala. Torna a estratégia dependente do valor,
 * então os resultados passam pelo `DimenCache`.
 */
fun interface ValueTransform {
    fun transform(base: Float, scaled: Float, screen: ScreenSnapshot): Float
}

/**
 * EN How the axis ratio `r = screen / reference` becomes a factor.
 * PT Como a razão `r = tela / referência` vira fator.
 */
sealed class ScalingCurve {
    /** EN `r` — proportional (built-in `sdp`). PT Proporcional. */
    data object Linear : ScalingCurve()

    /** EN `r^exponent` — sublinear for `exponent < 1` (built-in power uses 0.75). */
    data class Power(val exponent: Float) : ScalingCurve() {
        init { require(exponent.isFinite()) { "exponent must be finite" } }
    }

    /** EN `1 + k·ln(r)` — damped growth (built-in logarithmic uses 0.4). */
    data class Logarithmic(val k: Float) : ScalingCurve() {
        init { require(k.isFinite()) { "k must be finite" } }
    }

    /** EN `1 + (r − 1)·t` — blend between fixed (t = 0) and linear (t = 1). Built-in uses 0.5. */
    data class Interpolated(val t: Float) : ScalingCurve() {
        init { require(t.isFinite()) { "t must be finite" } }
    }

    /** EN Fixed factor, ignores the screen (`Constant(1f)` = plain dp). */
    data class Constant(val factor: Float) : ScalingCurve() {
        init { require(factor.isFinite()) { "factor must be finite" } }
    }

    /**
     * EN Piecewise-linear table over the **absolute** axis value (dp). Below the first point
     * the first factor is used; above the last, the last factor.
     * PT Tabela linear por partes sobre o valor **absoluto** do eixo (dp).
     */
    class Breakpoints(points: List<Breakpoint>) : ScalingCurve() {
        val points: List<Breakpoint> = points.sortedBy { it.at }

        init { require(this.points.isNotEmpty()) { "breakpoints need at least one point" } }

        override fun equals(other: Any?): Boolean = other is Breakpoints && other.points == points
        override fun hashCode(): Int = points.hashCode()
        override fun toString(): String = "Breakpoints($points)"
    }

    /**
     * EN Free formula written as text (`"pow(ratio, 0.8) * (tablet ? 0.9 : 1)"`) — see
     * [ScalingExpression]. Serializable to JSON, identical on every platform.
     * PT Fórmula livre em texto — serializável em JSON, idêntica em todas as plataformas.
     */
    class Expression(val expression: ScalingExpression) : ScalingCurve() {
        constructor(source: String) : this(ScalingExpression.parse(source, ScalingExpression.CURVE_VARIABLES))

        override fun equals(other: Any?): Boolean = other is Expression && other.expression == expression
        override fun hashCode(): Int = expression.hashCode()
        override fun toString(): String = "Expression(${expression.source})"
    }

    /** EN Free formula — see [CurveFunction]. Not serializable to JSON. */
    class Custom(val function: CurveFunction) : ScalingCurve() {
        override fun toString(): String = "Custom"
    }
}

/**
 * EN Font-scale handling for `csp`. PT Tratamento do font scale em `csp`.
 */
sealed class FontScalePolicy {
    /** EN Respect the user font scale (default, accessible). PT Segue o font scale do sistema. */
    data object Follow : FontScalePolicy()

    /** EN Ignore the user font scale (fixed-size labels, game HUD). PT Ignora o font scale. */
    data object Ignore : FontScalePolicy()

    /** EN Follow up to [max] (e.g. 1.3). PT Segue até [max]. */
    data class Clamp(val max: Float) : FontScalePolicy() {
        init { require(max.isFinite() && max > 0f) { "fontScale max must be > 0" } }
    }

    /** EN Multiplier that turns the system font scale into the effective one. */
    fun effective(systemFontScale: Float): Float = when (this) {
        Follow -> systemFontScale
        Ignore -> 1f
        is Clamp -> minOf(systemFontScale, max)
    }
}

/**
 * EN When a [ScalingRule] applies. Every non-null field must match; ranges are
 * `min <= value < max` (dp). An empty condition always matches.
 *
 * PT Quando uma [ScalingRule] se aplica. Todo campo não nulo precisa casar; faixas são
 * `min <= valor < max` (dp). Condição vazia sempre casa.
 */
data class ScreenCondition(
    val devices: Set<DeviceClass>? = null,
    val orientation: Orientation? = null,
    val minSmallestWidth: Float? = null,
    val maxSmallestWidth: Float? = null,
    val minWidth: Float? = null,
    val maxWidth: Float? = null,
    val minHeight: Float? = null,
    val maxHeight: Float? = null,
    val minAspectRatio: Float? = null,
    val maxAspectRatio: Float? = null,
    val multiWindow: Boolean? = null,
) {
    fun matches(screen: ScreenSnapshot): Boolean {
        if (devices != null && screen.device !in devices) return false
        when (orientation) {
            Orientation.LANDSCAPE -> if (!screen.isLandscape) return false
            Orientation.PORTRAIT -> if (screen.isLandscape) return false
            else -> Unit
        }
        if (!inRange(screen.smallestWidthDp, minSmallestWidth, maxSmallestWidth)) return false
        if (!inRange(screen.widthDp, minWidth, maxWidth)) return false
        if (!inRange(screen.heightDp, minHeight, maxHeight)) return false
        if (!inRange(screen.aspectRatio, minAspectRatio, maxAspectRatio)) return false
        if (multiWindow != null && screen.isMultiWindow != multiWindow) return false
        return true
    }

    private fun inRange(value: Float, min: Float?, max: Float?): Boolean =
        (min == null || value >= min) && (max == null || value < max)
}

/**
 * EN Partial override applied by a rule. `null` keeps the current value.
 * PT Sobrescrita parcial aplicada por uma regra. `null` mantém o valor atual.
 */
data class ScalingPatch(
    val reference: ReferenceScreen? = null,
    val axis: ScalingAxis? = null,
    val curve: ScalingCurve? = null,
    val multiplier: Float? = null,
    val minFactor: Float? = null,
    val maxFactor: Float? = null,
    val minValue: Float? = null,
    val maxValue: Float? = null,
    val aspectRatio: Boolean? = null,
    val sensitivity: Float? = null,
    val fontScale: FontScalePolicy? = null,
    val ignoreMultiWindow: Boolean? = null,
    val transform: ValueTransform? = null,
) {
    internal val isValueDependent: Boolean
        get() = minValue != null || maxValue != null || transform != null
}

/**
 * EN `when` [condition] matches, apply [patch]. Matching rules apply in declaration order;
 * a later rule overrides fields set by an earlier one.
 * PT Quando [condition] casa, aplica [patch]. Regras casadas aplicam em ordem de declaração;
 * uma regra posterior sobrescreve campos de uma anterior.
 */
data class ScalingRule(
    val condition: ScreenCondition,
    val patch: ScalingPatch,
    val name: String? = null,
)

/**
 * EN Full, immutable definition of a custom strategy.
 *
 * `factor = clamp(curve(axis(screen) / axis(reference)) × multiplier × AR, minFactor, maxFactor)`
 * `dp     = transform(clampValue(base × factor, minValue, maxValue))`
 *
 * PT Definição completa e imutável de uma estratégia custom (fórmula acima).
 */
data class ScalingConfig(
    val reference: ReferenceScreen = ReferenceScreen.DEFAULT,
    val axis: ScalingAxis = ScalingAxis.SMALLEST_WIDTH,
    val curve: ScalingCurve = ScalingCurve.Linear,
    val multiplier: Float = 1f,
    val minFactor: Float? = null,
    val maxFactor: Float? = null,
    val minValue: Float? = null,
    val maxValue: Float? = null,
    val aspectRatio: Boolean = false,
    val sensitivity: Float? = null,
    val fontScale: FontScalePolicy = FontScalePolicy.Follow,
    val ignoreMultiWindow: Boolean = false,
    val transform: ValueTransform? = null,
    val rules: List<ScalingRule> = emptyList(),
) {
    init {
        require(multiplier.isFinite()) { "multiplier must be finite" }
        requireFiniteOrNull(minFactor, "minFactor")
        requireFiniteOrNull(maxFactor, "maxFactor")
        requireFiniteOrNull(minValue, "minValue")
        requireFiniteOrNull(maxValue, "maxValue")
        requireFiniteOrNull(sensitivity, "sensitivity")
    }

    /**
     * EN Cross-field checks for a declared config (the base and each rule on top of it).
     * Rules combined at runtime never throw: when bounds cross, the max bound wins.
     * PT Validação entre campos da config declarada. Em runtime, se limites cruzarem, o max vence.
     */
    fun validate(): ScalingConfig {
        if (minFactor != null && maxFactor != null) require(minFactor <= maxFactor) { "minFactor must be <= maxFactor" }
        if (minValue != null && maxValue != null) require(minValue <= maxValue) { "minValue must be <= maxValue" }
        rules.forEach { apply(it.patch).copy(rules = emptyList()).validate() }
        return this
    }

    /** EN True when some path depends on the value itself (not only on the screen). */
    val isValueDependent: Boolean
        get() = minValue != null || maxValue != null || transform != null || rules.any { it.patch.isValueDependent }

    /** EN Returns this config with [patch] applied. PT Retorna a config com [patch] aplicado. */
    fun apply(patch: ScalingPatch): ScalingConfig = copy(
        reference = patch.reference ?: reference,
        axis = patch.axis ?: axis,
        curve = patch.curve ?: curve,
        multiplier = patch.multiplier ?: multiplier,
        minFactor = patch.minFactor ?: minFactor,
        maxFactor = patch.maxFactor ?: maxFactor,
        minValue = patch.minValue ?: minValue,
        maxValue = patch.maxValue ?: maxValue,
        aspectRatio = patch.aspectRatio ?: aspectRatio,
        sensitivity = patch.sensitivity ?: sensitivity,
        fontScale = patch.fontScale ?: fontScale,
        ignoreMultiWindow = patch.ignoreMultiWindow ?: ignoreMultiWindow,
        transform = patch.transform ?: transform,
    )

    private fun requireFiniteOrNull(v: Float?, name: String) {
        require(v == null || v.isFinite()) { "$name must be finite" }
    }
}
