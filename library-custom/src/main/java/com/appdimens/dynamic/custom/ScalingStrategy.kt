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
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow

/**
 * EN Everything a strategy needs for one window: the screen-only [factor] plus the
 * value-dependent parts. Computed once per snapshot and memoized on the strategy.
 *
 * PT Tudo que a estratégia precisa para uma janela: o [factor] (só da tela) e as partes
 * dependentes do valor. Calculado uma vez por snapshot e memoizado.
 */
class ResolvedScaling internal constructor(
    val screen: ScreenSnapshot,
    val config: ScalingConfig,
    val factor: Float,
    val appliedRules: List<String>,
    internal val axisDp: Float,
    internal val referenceDp: Float,
    internal val curveFactor: Float,
    internal val aspectRatioMultiplier: Float,
) {
    /** EN Scales [base] (dp) → dp. PT Escala [base] (dp) → dp. */
    fun scale(base: Float): Float {
        var out = base * factor
        val min = config.minValue
        val max = config.maxValue
        if ((min != null || max != null) && out != 0f) {
            // Bounds apply to the magnitude so negative offsets mirror positive ones; 0 stays 0.
            var magnitude = abs(out)
            if (min != null && magnitude < min) magnitude = min
            if (max != null && magnitude > max) magnitude = max
            out = if (out < 0f) -magnitude else magnitude
        }
        config.transform?.let { out = it.transform(base, out, screen) }
        return out
    }

    /**
     * EN Scales [base] for text, returning **sp units** (the platform multiplies sp by the
     * system font scale) adjusted so the effective scale follows [ScalingConfig.fontScale].
     * PT Escala para texto, em **unidades sp**, ajustado pela [ScalingConfig.fontScale].
     */
    fun scaleSpUnits(base: Float): Float {
        val system = screen.fontScale
        val correction = if (system > 0f) config.fontScale.effective(system) / system else 1f
        return scale(base) * correction
    }

    /** EN Scales [base] for text, returning dp-equivalent size after font scale. */
    fun scaleSpDp(base: Float): Float = scale(base) * config.fontScale.effective(screen.fontScale)

    /** EN Human-readable breakdown, for logs and debug overlays. PT Explicação legível. */
    fun explain(strategyId: String): String = buildString {
        append("[").append(strategyId).append("] ")
        append(screen.device.jsonName).append(' ')
        append(config.axis.jsonName).append('=').append(fmt(axisDp)).append("dp")
        append(" / ref ").append(fmt(referenceDp)).append(" = ").append(fmt(axisDp / referenceDp))
        append(" -> ").append(config.curve).append(" = ").append(fmt(curveFactor))
        if (config.multiplier != 1f) append(" x ").append(fmt(config.multiplier))
        if (config.aspectRatio) append(" x AR ").append(fmt(aspectRatioMultiplier))
        if (config.minFactor != null || config.maxFactor != null) {
            append(" clamp[").append(config.minFactor?.let(::fmt) ?: "-").append(", ")
                .append(config.maxFactor?.let(::fmt) ?: "-").append(']')
        }
        append(" => factor ").append(fmt(factor))
        if (appliedRules.isNotEmpty()) append(" rules=").append(appliedRules)
    }

    private fun fmt(v: Float): String = "%.4f".format(java.util.Locale.ROOT, v).trimEnd('0').trimEnd('.')
}

/**
 * EN A named, user-defined scaling strategy. Create with [scalingStrategy], [extend],
 * [ScalingStrategies.fromJson] or a preset in [CustomPresets].
 *
 * Instances are immutable and thread-safe. The factor for a window is computed once and
 * memoized, so resolving a value is one multiply (plus optional value clamps).
 *
 * PT Estratégia de escala nomeada, definida pelo usuário. Imutável e thread-safe; o fator
 * por janela é calculado uma vez e memoizado.
 */
class ScalingStrategy internal constructor(
    val id: String,
    val config: ScalingConfig,
) {
    /**
     * EN Process-unique id packed into `DimenCache` keys (16 bits); `-1` when the registry
     * ran out of ids — such a strategy still works but never writes the shared cache.
     * PT Id único no processo usado nas chaves do `DimenCache`; `-1` quando esgotado.
     */
    val cacheId: Int = ScalingStrategies.nextCacheId()

    /** EN True when results depend on the value, not only on the screen. */
    val isValueDependent: Boolean = config.isValueDependent

    private class Memo(val token: Any, val resolved: ResolvedScaling)

    @Volatile
    private var memo: Memo? = null

    init {
        require(id.isNotBlank()) { "strategy id must not be blank" }
        config.validate()
    }

    /** EN Resolves (memoized) for [screen]. PT Resolve (memoizado) para [screen]. */
    fun resolve(screen: ScreenSnapshot): ResolvedScaling = resolve(screen) { screen }

    /**
     * EN Resolves for a platform snapshot [token] (e.g. `DimenMetrics`), building the
     * [ScreenSnapshot] only on a memo miss. [token] must have value equality.
     * PT Resolve para um [token] de plataforma; o snapshot só é construído em miss.
     */
    fun resolve(token: Any, snapshot: () -> ScreenSnapshot): ResolvedScaling {
        val m = memo
        if (m != null && (m.token === token || m.token == token)) return m.resolved
        val resolved = ScalingEngine.resolve(config, snapshot())
        memo = Memo(token, resolved)
        return resolved
    }

    /** EN Screen-only factor. PT Fator dependente só da tela. */
    fun factor(screen: ScreenSnapshot): Float = resolve(screen).factor

    /** EN `base` dp → scaled dp. PT `base` dp → dp escalado. */
    fun scale(base: Float, screen: ScreenSnapshot): Float = resolve(screen).scale(base)

    /** EN Debug breakdown for [screen]. PT Explicação para [screen]. */
    fun explain(screen: ScreenSnapshot): String = resolve(screen).explain(id)

    /**
     * EN New strategy that starts from this one; rules of both are kept (parent first).
     * PT Nova estratégia a partir desta; regras de ambas são mantidas (pai primeiro).
     */
    fun extend(id: String, register: Boolean = true, block: ScalingStrategyBuilder.() -> Unit): ScalingStrategy {
        val builder = ScalingStrategyBuilder(config).apply(block)
        return ScalingStrategy(id, builder.build()).also { if (register) ScalingStrategies.register(it) }
    }

    /** EN Same strategy on another axis (e.g. heights). Id: `"<id>@<axis>"`. */
    fun withAxis(axis: ScalingAxis): ScalingStrategy {
        if (axis == config.axis) return this
        val derivedId = "$id@${axis.jsonName}"
        return ScalingStrategies.derived(derivedId) { ScalingStrategy(derivedId, config.copy(axis = axis)) }
    }

    /** EN JSON for this strategy (throws for lambda curves/transforms). */
    fun toJson(): String = ScalingStrategyJson.encode(this)

    override fun toString(): String = "ScalingStrategy($id)"
}

/**
 * EN Pure math shared by every platform binding.
 * PT Matemática pura compartilhada por todas as plataformas.
 */
internal object ScalingEngine {
    /** EN Default AR sensitivity — same constant as the built-in strategies (0.08 / 30). */
    const val SENSITIVITY_DEFAULT = 0.08f / 30f
    const val REFERENCE_ASPECT_RATIO = 1.78f

    fun resolve(base: ScalingConfig, screen: ScreenSnapshot): ResolvedScaling {
        var cfg = base
        val applied = ArrayList<String>(0)
        base.rules.forEachIndexed { index, rule ->
            if (rule.condition.matches(screen)) {
                cfg = cfg.apply(rule.patch)
                applied += rule.name ?: "#$index"
            }
        }
        val axisDp = screen.read(cfg.axis)
        val refDp = cfg.reference.read(cfg.axis)
        if (cfg.ignoreMultiWindow && screen.isMultiWindow) {
            return ResolvedScaling(screen, cfg, 1f, applied, axisDp, refDp, 1f, 1f)
        }
        val ratio = if (refDp > 0f) axisDp / refDp else 1f
        val curveFactor = curve(cfg.curve, ratio, axisDp, screen)
        var factor = curveFactor * cfg.multiplier
        var arMul = 1f
        if (cfg.aspectRatio) {
            val k = cfg.sensitivity ?: SENSITIVITY_DEFAULT
            arMul = 1f + k * ln((screen.aspectRatio / REFERENCE_ASPECT_RATIO).toDouble()).toFloat()
            factor *= arMul
        }
        cfg.minFactor?.let { if (factor < it) factor = it }
        cfg.maxFactor?.let { if (factor > it) factor = it }
        require(factor.isFinite()) { "custom strategy produced a non-finite factor ($factor)" }
        return ResolvedScaling(screen, cfg, factor, applied, axisDp, refDp, curveFactor, arMul)
    }

    fun curve(curve: ScalingCurve, ratio: Float, axisDp: Float, screen: ScreenSnapshot): Float = when (curve) {
        ScalingCurve.Linear -> ratio
        is ScalingCurve.Power -> if (ratio > 0f) ratio.toDouble().pow(curve.exponent.toDouble()).toFloat() else 0f
        is ScalingCurve.Logarithmic -> if (ratio > 0f) 1f + curve.k * ln(ratio.toDouble()).toFloat() else 1f
        is ScalingCurve.Interpolated -> 1f + (ratio - 1f) * curve.t
        is ScalingCurve.Constant -> curve.factor
        is ScalingCurve.Breakpoints -> interpolate(curve.points, axisDp)
        is ScalingCurve.Custom -> curve.function.factor(ratio, screen)
    }

    private fun interpolate(points: List<Breakpoint>, x: Float): Float {
        val first = points.first()
        if (x <= first.at) return first.factor
        for (i in 1 until points.size) {
            val b = points[i]
            if (x <= b.at) {
                val a = points[i - 1]
                val span = b.at - a.at
                return if (span <= 0f) b.factor else a.factor + (b.factor - a.factor) * ((x - a.at) / span)
            }
        }
        return points.last().factor
    }
}

/**
 * EN Process-wide registry of strategies by id, plus the default used by `16.cdp`.
 * PT Registro de estratégias por id e a estratégia padrão usada por `16.cdp`.
 */
object ScalingStrategies {
    private val byId = ConcurrentHashMap<String, ScalingStrategy>()
    private val derivedCache = ConcurrentHashMap<String, ScalingStrategy>()
    private val cacheIds = AtomicInteger(0)

    /** EN Largest id that fits `DimenCache` custom keys. */
    const val MAX_CACHE_ID = 0xFFFF

    internal fun nextCacheId(): Int {
        val next = cacheIds.getAndIncrement()
        return if (next in 0..MAX_CACHE_ID) next else -1
    }

    @Volatile
    private var defaultStrategy: ScalingStrategy? = null

    /**
     * EN Strategy used when none is passed (`16.cdp`) and none is provided by scope.
     * Defaults to [BuiltInStrategies.SCALED] (same numbers as `16.sdp`).
     * PT Estratégia usada quando nenhuma é passada. Padrão: [BuiltInStrategies.SCALED].
     */
    @JvmStatic
    var default: ScalingStrategy
        get() = defaultStrategy ?: BuiltInStrategies.SCALED
        set(value) {
            defaultStrategy = value
        }

    /** EN Registers (or replaces) [strategy] under its id. PT Registra (ou substitui). */
    @JvmStatic
    fun register(strategy: ScalingStrategy): ScalingStrategy {
        byId[strategy.id] = strategy
        return strategy
    }

    /** EN Strategy for [id], or `null`. PT Estratégia para [id], ou `null`. */
    @JvmStatic
    operator fun get(id: String): ScalingStrategy? = byId[id] ?: BuiltInStrategies.byName(id) ?: CustomPresets.byName(id)

    /** EN Like [get] but throws a descriptive error. */
    @JvmStatic
    fun getOrThrow(id: String): ScalingStrategy =
        get(id) ?: throw IllegalArgumentException("No scaling strategy '$id'. Registered: ${byId.keys.sorted()}")

    /** EN Ids registered by the app (built-ins/presets excluded). */
    @JvmStatic
    val ids: Set<String> get() = byId.keys.toSet()

    /** EN Removes [id]. PT Remove [id]. */
    @JvmStatic
    fun unregister(id: String) {
        byId.remove(id)
    }

    /**
     * EN Parses a strategies document (see `custom-strategy.schema.json`), registers every
     * strategy and applies its `default`. Returns the parsed strategies by id.
     * PT Lê um documento JSON de estratégias, registra todas e aplica o `default`.
     */
    @JvmStatic
    fun loadJson(json: String): Map<String, ScalingStrategy> {
        val doc = ScalingStrategyJson.decode(json)
        doc.strategies.values.forEach { register(it) }
        doc.defaultId?.let { default = doc.strategies[it] ?: getOrThrow(it) }
        return doc.strategies
    }

    internal fun derived(id: String, create: () -> ScalingStrategy): ScalingStrategy =
        derivedCache.getOrPut(id, create)

    /** EN Test helper. */
    @JvmStatic
    internal fun resetForTest() {
        byId.clear()
        derivedCache.clear()
        defaultStrategy = null
    }
}

/**
 * EN Creates **and registers** a strategy with the DSL.
 * ```
 * val Brand = scalingStrategy("brand") {
 *     reference(width = 360f)
 *     curve = power(0.8f)
 *     clamp(min = 0.85f, max = 1.6f)
 *     on(DeviceClass.TABLET) { curve = logarithmic(0.4f) }
 * }
 * ```
 * PT Cria **e registra** uma estratégia com a DSL.
 */
fun scalingStrategy(id: String, register: Boolean = true, block: ScalingStrategyBuilder.() -> Unit): ScalingStrategy {
    val strategy = ScalingStrategy(id, ScalingStrategyBuilder().apply(block).build())
    if (register) ScalingStrategies.register(strategy)
    return strategy
}

/** EN Wraps an existing [ScalingConfig] as a strategy (registered). */
fun ScalingConfig.toStrategy(id: String, register: Boolean = true): ScalingStrategy =
    ScalingStrategy(id, this).also { if (register) ScalingStrategies.register(it) }
