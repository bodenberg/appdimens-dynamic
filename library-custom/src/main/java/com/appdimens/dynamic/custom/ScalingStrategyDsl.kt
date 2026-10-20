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

@DslMarker
annotation class ScalingDsl

/**
 * EN Curve factories available in every DSL block (`curve = power(0.8f)`).
 * PT Fábricas de curva disponíveis em todo bloco da DSL.
 */
@ScalingDsl
abstract class ScalingCurves internal constructor() {
    fun linear(): ScalingCurve = ScalingCurve.Linear
    fun power(exponent: Float): ScalingCurve = ScalingCurve.Power(exponent)
    fun logarithmic(k: Float): ScalingCurve = ScalingCurve.Logarithmic(k)
    fun interpolated(t: Float): ScalingCurve = ScalingCurve.Interpolated(t)
    fun constant(factor: Float): ScalingCurve = ScalingCurve.Constant(factor)

    /** EN `breakpoints(360f to 1f, 600f to 1.3f)` — `axis dp to factor`. */
    fun breakpoints(vararg points: Pair<Number, Number>): ScalingCurve =
        ScalingCurve.Breakpoints(points.map { Breakpoint(it.first.toFloat(), it.second.toFloat()) })

    /**
     * EN Free formula as text (JSON-serializable): `expression("pow(ratio, 0.8) * (tablet ? 0.9 : 1)")`.
     * See [ScalingExpression] for variables and functions.
     * PT Fórmula livre em texto (serializável): veja [ScalingExpression].
     */
    fun expression(source: String): ScalingCurve = ScalingCurve.Expression(source)

    /** EN Free pure formula `(ratio, screen) -> factor`. PT Fórmula livre e pura. */
    fun custom(function: CurveFunction): ScalingCurve = ScalingCurve.Custom(function)
}

/**
 * EN Fields shared by the strategy root and by rule blocks.
 * PT Campos comuns à raiz da estratégia e aos blocos de regra.
 */
@ScalingDsl
abstract class ScalingFields internal constructor() : ScalingCurves() {
    abstract var axis: ScalingAxis?
    abstract var curve: ScalingCurve?
    abstract var multiplier: Float?
    abstract var fontScale: FontScalePolicy?
    abstract var ignoreMultiWindow: Boolean?

    internal abstract fun setReference(reference: ReferenceScreen)
    internal abstract fun setFactorBounds(min: Float?, max: Float?)
    internal abstract fun setValueBounds(min: Float?, max: Float?)
    internal abstract fun setAspectRatio(enabled: Boolean, sensitivity: Float?)
    internal abstract fun setTransform(transform: ValueTransform)

    /** EN Design frame in dp (default 300×533). PT Frame de design em dp. */
    fun reference(width: Number = 300f, height: Number = 533f) =
        setReference(ReferenceScreen(width.toFloat(), height.toFloat()))

    /** EN Bounds for the **factor** (`clamp(max = 1.5f)`). PT Limites do **fator**. */
    fun clamp(min: Number? = null, max: Number? = null) = setFactorBounds(min?.toFloat(), max?.toFloat())

    /** EN Bounds for the **resulting dp** (value-dependent → cached). PT Limites do dp resultante. */
    fun clampValue(min: Number? = null, max: Number? = null) = setValueBounds(min?.toFloat(), max?.toFloat())

    /** EN Aspect-ratio correction `× (1 + k·ln(AR / 1.78))`. PT Correção por proporção. */
    fun aspectRatio(enabled: Boolean = true, sensitivity: Float? = null) = setAspectRatio(enabled, sensitivity)

    /** EN Per-value pure hook (value-dependent → cached). PT Hook puro por valor. */
    fun transform(transform: ValueTransform) = setTransform(transform)

    /**
     * EN Per-value formula as text (value-dependent → cached), e.g. `formula("max(scaled, base)")`
     * or a full replacement `formula("base * pow(sw / 360, 0.7) + 2")`.
     * PT Fórmula por valor em texto (ex.: `formula("max(scaled, base)")`).
     */
    fun formula(source: String) = setTransform(FormulaTransform(source))

    /** EN Shortcut: multiply the factor. PT Atalho: multiplica o fator. */
    fun multiply(factor: Number) {
        multiplier = factor.toFloat()
    }

    /** EN Font-scale shortcuts for `csp`. */
    fun followFontScale() { fontScale = FontScalePolicy.Follow }
    fun ignoreFontScale() { fontScale = FontScalePolicy.Ignore }
    fun clampFontScale(max: Float) { fontScale = FontScalePolicy.Clamp(max) }
}

/**
 * EN Block of a rule: every field is optional and overrides the strategy only when the
 * rule matches.
 * PT Bloco de regra: todo campo é opcional e só sobrescreve quando a regra casa.
 */
class ScalingPatchBuilder internal constructor() : ScalingFields() {
    private var reference: ReferenceScreen? = null
    private var minFactor: Float? = null
    private var maxFactor: Float? = null
    private var minValue: Float? = null
    private var maxValue: Float? = null
    private var aspect: Boolean? = null
    private var sensitivity: Float? = null
    private var transformFn: ValueTransform? = null

    override var axis: ScalingAxis? = null
    override var curve: ScalingCurve? = null
    override var multiplier: Float? = null
    override var fontScale: FontScalePolicy? = null
    override var ignoreMultiWindow: Boolean? = null

    override fun setReference(reference: ReferenceScreen) { this.reference = reference }
    override fun setFactorBounds(min: Float?, max: Float?) { minFactor = min; maxFactor = max }
    override fun setValueBounds(min: Float?, max: Float?) { minValue = min; maxValue = max }
    override fun setAspectRatio(enabled: Boolean, sensitivity: Float?) { aspect = enabled; this.sensitivity = sensitivity }
    override fun setTransform(transform: ValueTransform) { transformFn = transform }

    internal fun build() = ScalingPatch(
        reference = reference, axis = axis, curve = curve, multiplier = multiplier,
        minFactor = minFactor, maxFactor = maxFactor, minValue = minValue, maxValue = maxValue,
        aspectRatio = aspect, sensitivity = sensitivity, fontScale = fontScale,
        ignoreMultiWindow = ignoreMultiWindow, transform = transformFn,
    )
}

/**
 * EN Root builder used by [scalingStrategy] and [ScalingStrategy.extend].
 * PT Builder raiz usado por [scalingStrategy] e [ScalingStrategy.extend].
 */
class ScalingStrategyBuilder internal constructor(base: ScalingConfig = ScalingConfig()) : ScalingFields() {
    private var config = base
    private val rules = base.rules.toMutableList()

    override var axis: ScalingAxis?
        get() = config.axis
        set(value) { config = config.copy(axis = requireNotNull(value) { "axis" }) }
    override var curve: ScalingCurve?
        get() = config.curve
        set(value) { config = config.copy(curve = requireNotNull(value) { "curve" }) }
    override var multiplier: Float?
        get() = config.multiplier
        set(value) { config = config.copy(multiplier = value ?: 1f) }
    override var fontScale: FontScalePolicy?
        get() = config.fontScale
        set(value) { config = config.copy(fontScale = value ?: FontScalePolicy.Follow) }
    override var ignoreMultiWindow: Boolean?
        get() = config.ignoreMultiWindow
        set(value) { config = config.copy(ignoreMultiWindow = value ?: false) }

    override fun setReference(reference: ReferenceScreen) { config = config.copy(reference = reference) }
    override fun setFactorBounds(min: Float?, max: Float?) { config = config.copy(minFactor = min, maxFactor = max) }
    override fun setValueBounds(min: Float?, max: Float?) { config = config.copy(minValue = min, maxValue = max) }
    override fun setAspectRatio(enabled: Boolean, sensitivity: Float?) {
        config = config.copy(aspectRatio = enabled, sensitivity = sensitivity)
    }
    override fun setTransform(transform: ValueTransform) { config = config.copy(transform = transform) }

    /** EN Rule for one or more device classes. PT Regra para uma ou mais classes de dispositivo. */
    fun on(vararg devices: DeviceClass, name: String? = null, block: ScalingPatchBuilder.() -> Unit) =
        rule(ScreenCondition(devices = devices.toSet()), name, block)

    /** EN Rule for an arbitrary [condition]. PT Regra para uma [condition] qualquer. */
    fun on(condition: ScreenCondition, name: String? = null, block: ScalingPatchBuilder.() -> Unit) =
        rule(condition, name, block)

    /**
     * EN Rule by screen ranges (`min <= v < max`, dp).
     * `onScreen(minSmallestWidth = 600f, maxSmallestWidth = 840f) { … }`
     * PT Regra por faixas de tela.
     */
    fun onScreen(
        minSmallestWidth: Float? = null, maxSmallestWidth: Float? = null,
        minWidth: Float? = null, maxWidth: Float? = null,
        minHeight: Float? = null, maxHeight: Float? = null,
        minAspectRatio: Float? = null, maxAspectRatio: Float? = null,
        orientation: Orientation? = null,
        devices: Set<DeviceClass>? = null,
        multiWindow: Boolean? = null,
        name: String? = null,
        block: ScalingPatchBuilder.() -> Unit,
    ) = rule(
        ScreenCondition(
            devices, orientation, minSmallestWidth, maxSmallestWidth, minWidth, maxWidth,
            minHeight, maxHeight, minAspectRatio, maxAspectRatio, multiWindow,
        ),
        name, block,
    )

    fun landscape(block: ScalingPatchBuilder.() -> Unit) = rule(ScreenCondition(orientation = Orientation.LANDSCAPE), "landscape", block)
    fun portrait(block: ScalingPatchBuilder.() -> Unit) = rule(ScreenCondition(orientation = Orientation.PORTRAIT), "portrait", block)
    fun multiWindow(block: ScalingPatchBuilder.() -> Unit) = rule(ScreenCondition(multiWindow = true), "multiWindow", block)

    /** EN Shortcut for `sw >= 600` (tablet-like windows, any UI mode). */
    fun largeScreens(block: ScalingPatchBuilder.() -> Unit) =
        rule(ScreenCondition(minSmallestWidth = DeviceClass.TABLET_MIN_SMALLEST_WIDTH_DP), "largeScreens", block)

    private fun rule(condition: ScreenCondition, name: String?, block: ScalingPatchBuilder.() -> Unit) {
        rules += ScalingRule(condition, ScalingPatchBuilder().apply(block).build(), name)
    }

    internal fun build(): ScalingConfig = config.copy(rules = rules.toList())
}
