/**
 * Immutable inputs used to resolve a dynamic dimension for one Android window.
 *
 * A dimension must be a pure function of the window in which it is rendered.  Keeping
 * these values together prevents a calculation from observing a mix of old and new
 * configuration fields while a window is being resized.
 */
package com.appdimens.dynamic.core

import android.content.res.Configuration
import kotlin.math.ln

/**
 * A value snapshot, not a process-wide mutable "current screen".
 *
 * The primary constructor intentionally contains only the inputs that affect a result.
 * Kotlin's generated equality can therefore be used as an exact cache partition key;
 * derived values are calculated once when the snapshot is created.
 */
data class DimenMetrics(
    val screenWidthDp: Int,
    val screenHeightDp: Int,
    val smallestScreenWidthDp: Int,
    val densityDpi: Int,
    internal val fontScaleBits: Int,
    val orientation: Int,
    val uiMode: Int,
    val isInMultiWindowMode: Boolean,
) {
    /** The configured font scale, normalized to a safe value for malformed configurations. */
    val fontScale: Float = Float.fromBits(fontScaleBits).takeIf { it.isFinite() && it > 0f } ?: 1f

    /** Current window bounds in dp, not physical-display bounds. */
    val minDimensionDp: Float = minOf(screenWidthDp, screenHeightDp).coerceAtLeast(0).toFloat()
    val maxDimensionDp: Float = maxOf(screenWidthDp, screenHeightDp).coerceAtLeast(0).toFloat()

    /**
     * Keeps the historical `sw` contract when Android provides it, while remaining
     * well-defined for synthetic/test configurations where it is undefined.
     */
    val smallestWidthDp: Float = smallestScreenWidthDp
        .takeIf { it > 0 }
        ?.toFloat()
        ?: minDimensionDp.takeIf { it > 0f }
        ?: DesignScaleConstants.BASE_WIDTH_DP

    val density: Float = (densityDpi.toFloat() / 160f).takeIf { it.isFinite() && it > 0f } ?: 1f
    val scale: Float = smallestWidthDp * DimenCache.INV_BASE_RATIO

    /**
     * EN Precomputed WIDTH/HEIGHT factors: the hot lane pays one `iget` instead of an
     *    int → float conversion plus a multiply on every resolution. Values are
     *    bit-identical to `screenWidthDp * INV_BASE_RATIO` / `screenHeightDp * INV_BASE_RATIO`.
     * PT Fatores WIDTH/HEIGHT pré-calculados: o caminho quente paga um `iget` em vez de
     *    conversão int → float mais uma multiplicação por resolução. Valores
     *    bit-idênticos a `screenWidthDp * INV_BASE_RATIO` / `screenHeightDp * INV_BASE_RATIO`.
     */
    @PublishedApi
    internal val screenWidthFactor: Float = screenWidthDp * DimenCache.INV_BASE_RATIO

    @PublishedApi
    internal val screenHeightFactor: Float = screenHeightDp * DimenCache.INV_BASE_RATIO

    /**
     * EN Plain `val` (not `lazy`): every real snapshot reads [defaultAspectRatioMultiplier]
     *    / [defaultScaledAspectRatioMultiplier] at construction, so the lazy
     *    double-checked-read would be paid anyway — eagerly computing removes the
     *    hidden `synchronized` probe from the SDPA fast lane.
     * PT `val` puro (não `lazy`): todo snapshot real lê [defaultAspectRatioMultiplier]
     *    / [defaultScaledAspectRatioMultiplier] na construção, então a leitura lazy
     *    seria paga de qualquer forma — calcular de forma antecipada remove o probe
     *    `synchronized` oculto do fast lane SDPA.
     */
    val normalizedAspectRatio: Float = run {
        val raw = if (minDimensionDp > 0f) maxDimensionDp / minDimensionDp else 1f
        (raw / DesignScaleConstants.REFERENCE_ASPECT_RATIO)
            .takeIf { it.isFinite() && it > 0f }
            ?: 1f
    }

    /** Exact natural logarithm — computed once when the snapshot is created. */
    val logNormalizedAspectRatio: Float = ln(normalizedAspectRatio.toDouble()).toFloat()

    /**
     * EN Plain `val` (not `lazy`) so the hot SDPA fast path never pays the lazy
     *    double-checked `synchronized` read on every resolution. Snapshots are built
     *    rarely (once per window), so the eager math is amortized to ~zero.
     *    The formulas are the exact historical ones (see [logNormalizedAspectRatio]).
     * PT `val` puro (não `lazy`) para o caminho rápido SDPA nunca pagar a leitura
     *    `synchronized` do lazy a cada resolução. Snapshots são construídos raramente
     *    (uma vez por janela), então a matemática antecipada é amortizada.
     *    As fórmulas são exatamente as históricas (ver [logNormalizedAspectRatio]).
     */
    val defaultAspectRatioMultiplier: Float =
        1f + DimenCache.SENSITIVITY_DEFAULT * logNormalizedAspectRatio

    val defaultScaledAspectRatioMultiplier: Float =
        1f + (smallestWidthDp - DesignScaleConstants.BASE_WIDTH_DP) *
            (DimenCache.ADJUSTMENT_SCALE + DimenCache.SENSITIVITY_DEFAULT * logNormalizedAspectRatio)

    // ─────────────────────────────────────────────────────────────────────────
    // SATELLITE FACTORS — computed at most once per snapshot, only when read.
    //
    // Power / logarithmic / diagonal / interpolated / perimeter satellites used to
    // re-evaluate `Math.pow` / `ln` / `sqrt` on every read of their factor (even on
    // the bypass path). These are pure functions of the fields above, so they are
    // memoized on the snapshot (`by lazy`): a snapshot that never touches a satellite
    // factor pays nothing, and one that does pays the transcendental math once per
    // snapshot instead of once per call. They are deliberately NOT part of the
    // primary constructor: Kotlin equality (the cache partition key) must only cover
    // the raw window inputs, never derived values.
    // ─────────────────────────────────────────────────────────────────────────

    /** `(sw / 300)^0.75` — power satellite default-path scale. */
    val powerScale: Float by lazy {
        Math.pow((smallestWidthDp / DesignScaleConstants.BASE_WIDTH_DP).toDouble(), 0.75).toFloat()
    }

    /** `1 + (sw * INV_BASE_RATIO - 1) * 0.5` — interpolated satellite default-path scale. */
    val interpolatedScale: Float by lazy {
        1f + (smallestWidthDp * DimenCache.INV_BASE_RATIO - 1f) * 0.5f
    }

    /** `√(min² + max²) / BASE_DIAGONAL` — diagonal satellite default-path scale. */
    val diagonalScale: Float by lazy {
        kotlin.math.sqrt(minDimensionDp * minDimensionDp + maxDimensionDp * maxDimensionDp) /
            DesignScaleConstants.BASE_DIAGONAL_DP
    }

    /** `(min + max) / BASE_PERIMETER` — perimeter satellite default-path scale. */
    val perimeterScale: Float by lazy {
        (minDimensionDp + maxDimensionDp) / DesignScaleConstants.BASE_PERIMETER_DP
    }

    /** Logarithmic satellite default-path scale (matches its historical when-chain). */
    val logarithmicScale: Float by lazy {
        when {
            smallestWidthDp > DesignScaleConstants.BASE_WIDTH_DP ->
                1f + 0.4f * kotlin.math.ln(smallestWidthDp * DimenCache.INV_BASE_RATIO)
            smallestWidthDp > 0f ->
                1f - 0.4f * kotlin.math.ln(DesignScaleConstants.BASE_WIDTH_DP / smallestWidthDp)
            else -> 1f
        }
    }

    /**
     * Multiplier used by the scaled SDP/SSP path.  Invalid sensitivities are rejected
     * instead of leaking NaN or infinity into a layout.
     */
    fun scaledMultiplier(applyAspectRatio: Boolean, customSensitivityK: Float?): Float {
        if (!applyAspectRatio) return scale
        if (customSensitivityK == null) return defaultScaledAspectRatioMultiplier
        require(customSensitivityK.isFinite()) { "customSensitivityK must be finite" }
        val result = 1f + (smallestWidthDp - DesignScaleConstants.BASE_WIDTH_DP) *
            (DimenCache.ADJUSTMENT_SCALE + customSensitivityK * logNormalizedAspectRatio)
        require(result.isFinite()) { "customSensitivityK produces a non-finite dimension multiplier" }
        return result
    }

    /** Multiplier shared by satellite strategies that apply AR after their base formula. */
    fun aspectRatioMultiplier(customSensitivityK: Float?): Float {
        if (customSensitivityK == null) return defaultAspectRatioMultiplier
        require(customSensitivityK.isFinite()) { "customSensitivityK must be finite" }
        val result = 1f + customSensitivityK * logNormalizedAspectRatio
        require(result.isFinite()) { "customSensitivityK produces a non-finite aspect-ratio multiplier" }
        return result
    }

    companion object {
        @JvmStatic
        fun from(
            configuration: Configuration,
            isInMultiWindowMode: Boolean = false,
        ): DimenMetrics = DimenMetrics(
            screenWidthDp = configuration.screenWidthDp,
            screenHeightDp = configuration.screenHeightDp,
            smallestScreenWidthDp = configuration.smallestScreenWidthDp,
            densityDpi = configuration.densityDpi,
            fontScaleBits = configuration.fontScale.toRawBits(),
            orientation = configuration.orientation,
            uiMode = configuration.uiMode,
            isInMultiWindowMode = isInMultiWindowMode,
        )

        @JvmField
        val DEFAULT: DimenMetrics = DimenMetrics(
            screenWidthDp = 300,
            screenHeightDp = 533,
            smallestScreenWidthDp = 300,
            densityDpi = 160,
            fontScaleBits = 1f.toRawBits(),
            orientation = Configuration.ORIENTATION_UNDEFINED,
            uiMode = 0,
            isInMultiWindowMode = false,
        )
    }
}
