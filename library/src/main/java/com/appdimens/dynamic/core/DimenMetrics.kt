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
    private val fontScaleBits: Int,
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

    val normalizedAspectRatio: Float = run {
        val raw = if (minDimensionDp > 0f) maxDimensionDp / minDimensionDp else 1f
        (raw / DesignScaleConstants.REFERENCE_ASPECT_RATIO)
            .takeIf { it.isFinite() && it > 0f }
            ?: 1f
    }

    /** Exact natural logarithm, computed once per snapshot rather than on the hot path. */
    val logNormalizedAspectRatio: Float = ln(normalizedAspectRatio.toDouble()).toFloat()

    val defaultAspectRatioMultiplier: Float =
        1f + DimenCache.SENSITIVITY_DEFAULT * logNormalizedAspectRatio

    val defaultScaledAspectRatioMultiplier: Float =
        1f + (smallestWidthDp - DesignScaleConstants.BASE_WIDTH_DP) *
            (DimenCache.ADJUSTMENT_SCALE + DimenCache.SENSITIVITY_DEFAULT * logNormalizedAspectRatio)

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
