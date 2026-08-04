/**
 * EN View / Context API: same building blocks as [com.appdimens.dynamic.compose.resize] — inner box px,
 * % of inner box, [ResizeBound] ranges, and text fitting via [StaticLayout].
 * PT API baseada em Context: equivalente ao Compose — caixa interna, % da caixa, [ResizeBound] e texto com [StaticLayout].
 */
package com.appdimens.dynamic.code.resize

import android.content.Context
import android.util.LayoutDirection
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import com.appdimens.dynamic.core.AutoResizePercentBasis
import com.appdimens.dynamic.core.ResizeBound
import com.appdimens.dynamic.core.ResizeRangePx
import com.appdimens.dynamic.core.resizeFixedDp
import com.appdimens.dynamic.core.resizeFixedSp
import com.appdimens.dynamic.core.resolveToPx
import kotlin.math.min
import kotlin.math.roundToInt

fun interface ResizeFitPredicate {
    fun fits(candidatePx: Float): Boolean
}

private fun requireFiniteBox(vararg values: Float, name: () -> String) {
    for (v in values) {
        require(v.isFinite()) { "${name()}: expected finite value, was $v" }
    }
}

private fun ResizeRangePx.requireFiniteRange(): ResizeRangePx = also {
    require(minPx.isFinite() && maxPx.isFinite() && stepPx.isFinite()) {
        "resize range must be finite (minPx=$minPx, maxPx=$maxPx, stepPx=$stepPx)"
    }
}

/** EN `null`, `≤ 0`, or `-1` → unlimited lines. PT `null`, `≤ 0` ou `-1` → linhas ilimitadas. */
fun resolveAutoResizeMaxLines(maxLines: Int?): Int =
    if (maxLines == null || maxLines <= 0 || maxLines == -1) Int.MAX_VALUE else maxLines

/** EN `null`, `≤ 0`, or `-1` → full [text]; else first [maxLength] UTF-16 code units. */
fun resolveAutoResizeTextForMeasure(text: String, maxLength: Int?): String =
    when {
        maxLength == null || maxLength <= 0 || maxLength == -1 -> text
        else -> text.take(maxLength)
    }

/** EN [Number] as percent 0–100 → multiplier 0..1 (same as Compose). PT Percentagem 0–100 → fator 0..1. */
fun percentOfBoxToFactor(percent: Number): Float =
    (percent.toFloat() / 100f).coerceIn(0f, 1f)

object DimenResize {

    /**
     * EN Inner width × height (px) after subtracting padding; each dimension is at least **1** px (matches Compose).
     * PT Largura e altura úteis após padding; cada valor ≥ **1** px.
     */
    @JvmStatic
    @JvmOverloads
    fun innerMaxDimensionsPx(
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Pair<Float, Float> {
        requireFiniteBox(boxWidthPx, boxHeightPx, paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx) {
            "innerMaxDimensionsPx"
        }
        val innerW = (boxWidthPx - paddingLeftPx - paddingRightPx).coerceAtLeast(1f)
        val innerH = (boxHeightPx - paddingTopPx - paddingBottomPx).coerceAtLeast(1f)
        return innerW to innerH
    }

    /**
     * EN Same as [innerMaxDimensionsPx] but horizontal padding uses **start** / **end** (mirrors Compose [PaddingValues] + RTL).
     * PT Igual a [innerMaxDimensionsPx] com padding horizontal **start** / **end** (RTL como no Compose).
     */
    @JvmStatic
    @JvmOverloads
    fun innerMaxDimensionsPxRelative(
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingStartPx: Float = 0f,
        paddingEndPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
        layoutDirection: Int = LayoutDirection.LTR,
    ): Pair<Float, Float> {
        val padLeft: Float
        val padRight: Float
        if (layoutDirection == LayoutDirection.RTL) {
            padLeft = paddingEndPx
            padRight = paddingStartPx
        } else {
            padLeft = paddingStartPx
            padRight = paddingEndPx
        }
        return innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            padLeft, padRight, paddingTopPx, paddingBottomPx,
        )
    }

    /**
     * EN Uniform padding in **dp** on all sides (Compose [contentPaddingUniformDp] for square / symmetric inset).
     * PT Padding uniforme em **dp** (equivalente a [contentPaddingUniformDp] quando só há inset simétrico).
     */
    @JvmStatic
    fun innerMaxDimensionsPxUniformDp(
        context: Context,
        boxWidthPx: Float,
        boxHeightPx: Float,
        uniformPaddingDp: Float,
    ): Pair<Float, Float> {
        require(uniformPaddingDp.isFinite()) { "uniformPaddingDp must be finite, was $uniformPaddingDp" }
        if (uniformPaddingDp <= 0f) {
            return innerMaxDimensionsPx(boxWidthPx, boxHeightPx)
        }
        val d = context.resources.displayMetrics.density
        require(d > 0f) { "density must be positive" }
        val p = uniformPaddingDp * d
        return innerMaxDimensionsPx(boxWidthPx, boxHeightPx, p, p, p, p)
    }

    /**
     * EN [ResizeRangePx] from [ResizeBound]s (screen %, fixed dp/sp) — same as Compose [rememberResizeRangePx].
     * PT Intervalo em px a partir de [ResizeBound] (equivalente ao Compose).
     */
    @JvmStatic
    fun rangePx(context: Context, min: ResizeBound, max: ResizeBound, step: ResizeBound): ResizeRangePx {
        val res = context.resources
        val cfg = res.configuration
        val d = res.displayMetrics.density
        val fs = cfg.fontScale.coerceAtLeast(1e-6f)
        return ResizeRangePx(
            minPx = min.resolveToPx(cfg, d, fs),
            maxPx = max.resolveToPx(cfg, d, fs),
            stepPx = step.resolveToPx(cfg, d, fs),
        ).requireFiniteRange()
    }

    /**
     * EN [minPercent]/[maxPercent] are **0–100** of the chosen inner edge ([basis]); [stepDp] is logical dp → px via density.
     * PT % 0–100 da aresta interna; passo em **dp**.
     */
    @JvmStatic
    fun rangePxPercentOfInnerBox(
        context: Context,
        basis: AutoResizePercentBasis,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
    ): ResizeRangePx {
        requireFiniteBox(innerWidthPx, innerHeightPx) { "rangePxPercentOfInnerBox(inner)" }
        require(stepDp.isFinite()) { "stepDp must be finite, was $stepDp" }
        val base = when (basis) {
            AutoResizePercentBasis.HEIGHT -> innerHeightPx
            AutoResizePercentBasis.WIDTH -> innerWidthPx
            AutoResizePercentBasis.MIN_SIDE -> min(innerWidthPx, innerHeightPx)
        }
        val minPx = base * percentOfBoxToFactor(minPercent)
        val maxPx = base * percentOfBoxToFactor(maxPercent)
        val d = context.resources.displayMetrics.density
        require(d > 0f) { "density must be positive" }
        val stepPx = stepDp.coerceAtLeast(0f) * d
        return ResizeRangePx(minPx, maxPx, stepPx).requireFiniteRange()
    }

    /**
     * EN Font-size range: min/max in px from **0–100** % of inner edge ([basis]); step from **sp** (density + font scale).
     * PT Tamanho de texto: min/max em px por % da aresta interna; passo em **sp**.
     */
    @JvmStatic
    fun rangePxTextSizePercentOfInnerBox(
        context: Context,
        basis: AutoResizePercentBasis,
        minPercent: Number,
        maxPercent: Number,
        stepSp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
    ): ResizeRangePx {
        requireFiniteBox(innerWidthPx, innerHeightPx) { "rangePxTextSizePercentOfInnerBox(inner)" }
        require(stepSp.isFinite()) { "stepSp must be finite, was $stepSp" }
        val res = context.resources
        val cfg = res.configuration
        val d = res.displayMetrics.density
        val fs = cfg.fontScale.coerceAtLeast(1e-6f)
        val base = when (basis) {
            AutoResizePercentBasis.HEIGHT -> innerHeightPx
            AutoResizePercentBasis.WIDTH -> innerWidthPx
            AutoResizePercentBasis.MIN_SIDE -> min(innerWidthPx, innerHeightPx)
        }
        val minPx = base * percentOfBoxToFactor(minPercent)
        val maxPx = base * percentOfBoxToFactor(maxPercent)
        val stepPx = resizeFixedSp(stepSp).resolveToPx(cfg, d, fs)
        return ResizeRangePx(minPx, maxPx, stepPx).requireFiniteRange()
    }

    /** EN Twin of [autoResizeWidthSizePercent]: % of **inner width**. PT % da **largura útil**. */
    @JvmStatic
    fun rangePxPercentOfInnerWidth(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
    ): ResizeRangePx = rangePxPercentOfInnerBox(
        context,
        AutoResizePercentBasis.WIDTH,
        minPercent,
        maxPercent,
        stepDp,
        innerWidthPx,
        innerHeightPx,
    )

    /** EN Twin of [autoResizeHeightSizePercent]: % of **inner height**. PT % da **altura útil**. */
    @JvmStatic
    fun rangePxPercentOfInnerHeight(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
    ): ResizeRangePx = rangePxPercentOfInnerBox(
        context,
        AutoResizePercentBasis.HEIGHT,
        minPercent,
        maxPercent,
        stepDp,
        innerWidthPx,
        innerHeightPx,
    )

    /** EN Twin of [autoResizeSquareSizePercent]: % of `min(inner width, inner height)`. PT % do **menor lado** útil. */
    @JvmStatic
    fun rangePxPercentOfInnerMinSide(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
    ): ResizeRangePx = rangePxPercentOfInnerBox(
        context,
        AutoResizePercentBasis.MIN_SIDE,
        minPercent,
        maxPercent,
        stepDp,
        innerWidthPx,
        innerHeightPx,
    )

    @JvmStatic
    fun fittingPx(range: ResizeRangePx, predicate: ResizeFitPredicate): Float =
        range.resolveFitting { predicate.fits(it) }

    /**
     * EN Largest width in [range] that fits **inner** width (after padding) of the box.
     * PT Maior largura no intervalo que cabe na largura útil.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerWidthPx(
        range: ResizeRangePx,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (innerW, _) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        return fittingPx(range) { it <= innerW }
    }

    /**
     * EN Largest height in [range] that fits **inner** height.
     * PT Maior altura no intervalo que cabe na altura útil.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerHeightPx(
        range: ResizeRangePx,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (_, innerH) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        return fittingPx(range) { it <= innerH }
    }

    /**
     * EN Largest size in [range] that fits `min(inner width, inner height)` (square slot).
     * PT Maior medida no intervalo que cabe no menor lado útil (quadrado).
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerSquareSidePx(
        range: ResizeRangePx,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (innerW, innerH) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        val limit = min(innerW, innerH)
        return fittingPx(range) { it <= limit }
    }

    /**
     * EN Fixed **dp** range (like [autoResizeWidthSize] Dp overload) + largest value that fits inner width.
     * PT Intervalo em **dp** + maior valor que cabe na largura útil.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerWidthPx(
        context: Context,
        minDp: Float,
        maxDp: Float,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val range = rangePx(
            context,
            resizeFixedDp(minDp),
            resizeFixedDp(maxDp),
            resizeFixedDp(stepDp),
        )
        return fittingInnerWidthPx(
            range, boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
    }

    /**
     * EN Fixed **dp** range + largest value that fits inner height ([autoResizeHeightSize] twin).
     * PT Intervalo em **dp** + maior valor que cabe na altura útil.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerHeightPx(
        context: Context,
        minDp: Float,
        maxDp: Float,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val range = rangePx(
            context,
            resizeFixedDp(minDp),
            resizeFixedDp(maxDp),
            resizeFixedDp(stepDp),
        )
        return fittingInnerHeightPx(
            range, boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
    }

    /**
     * EN Fixed **dp** range + largest value that fits `min(inner w, inner h)` ([autoResizeSquareSize] twin).
     * PT Intervalo em **dp** + maior valor no menor lado útil.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerSquareSidePx(
        context: Context,
        minDp: Float,
        maxDp: Float,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val range = rangePx(
            context,
            resizeFixedDp(minDp),
            resizeFixedDp(maxDp),
            resizeFixedDp(stepDp),
        )
        return fittingInnerSquareSidePx(
            range, boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
    }

    /**
     * EN % of inner width + fit ([autoResizeWidthSizePercent] twin).
     * PT % da largura útil + fitting.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerWidthPercentPx(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (innerW, innerH) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        val range = rangePxPercentOfInnerWidth(
            context, minPercent, maxPercent, stepDp, innerW, innerH,
        )
        return fittingPx(range) { it <= innerW }
    }

    /**
     * EN % of inner height + fit ([autoResizeHeightSizePercent] twin).
     * PT % da altura útil + fitting.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerHeightPercentPx(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (innerW, innerH) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        val range = rangePxPercentOfInnerHeight(
            context, minPercent, maxPercent, stepDp, innerW, innerH,
        )
        return fittingPx(range) { it <= innerH }
    }

    /**
     * EN % of min(inner w, h) + fit square side ([autoResizeSquareSizePercent] twin).
     * PT % do menor lado + fitting de quadrado.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingInnerSquareSidePercentPx(
        context: Context,
        minPercent: Number,
        maxPercent: Number,
        stepDp: Float,
        boxWidthPx: Float,
        boxHeightPx: Float,
        paddingLeftPx: Float = 0f,
        paddingRightPx: Float = 0f,
        paddingTopPx: Float = 0f,
        paddingBottomPx: Float = 0f,
    ): Float {
        val (innerW, innerH) = innerMaxDimensionsPx(
            boxWidthPx, boxHeightPx,
            paddingLeftPx, paddingRightPx, paddingTopPx, paddingBottomPx,
        )
        val limit = min(innerW, innerH)
        val range = rangePxPercentOfInnerMinSide(
            context, minPercent, maxPercent, stepDp, innerW, innerH,
        )
        return fittingPx(range) { it <= limit }
    }

    /**
     * EN Largest font size (px) in [range] so [text] fits in the inner box (same idea as [autoResizeTextSp]).
     * PT Maior tamanho de fonte em px para o texto caber na área útil.
     *
     * EN Configure [textPaint] (typeface, flags, letterSpacing, etc.) like the target [android.widget.TextView].
     * PT Configure [textPaint] como no [android.widget.TextView] de destino.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingTextSpPx(
        text: String,
        range: ResizeRangePx,
        innerWidthPx: Float,
        innerHeightPx: Float,
        textPaint: TextPaint,
        maxLines: Int? = null,
        maxLength: Int? = null,
        softWrap: Boolean = true,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        includePad: Boolean = true,
    ): Float {
        requireFiniteBox(innerWidthPx, innerHeightPx) { "fittingTextSpPx(inner)" }
        val measureText = resolveAutoResizeTextForMeasure(text, maxLength)
        val effMaxLines = resolveAutoResizeMaxLines(maxLines)
        val maxW = innerWidthPx.roundToInt().coerceAtLeast(1)
        val maxH = innerHeightPx.roundToInt().coerceAtLeast(1)
        return range.resolveFitting { candidatePx ->
            textFitsInnerBox(
                text = measureText,
                basePaint = textPaint,
                textSizePx = candidatePx,
                maxWidthPx = maxW,
                maxHeightPx = maxH,
                maxLines = effMaxLines,
                softWrap = softWrap,
                alignment = alignment,
                includePad = includePad,
            )
        }
    }

    /**
     * EN Twin of [autoResizeTextSp] with min/max/step as **sp** ([Number] overload in Compose).
     * PT Equivalente a [autoResizeTextSp] com min/max/step em **sp**.
     */
    @JvmStatic
    @JvmOverloads
    fun fittingTextSpPx(
        context: Context,
        text: String,
        minSp: Float,
        maxSp: Float,
        stepSp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
        textPaint: TextPaint,
        maxLines: Int? = null,
        maxLength: Int? = null,
        softWrap: Boolean = true,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        includePad: Boolean = true,
    ): Float {
        val range = rangePx(
            context,
            resizeFixedSp(minSp),
            resizeFixedSp(maxSp),
            resizeFixedSp(stepSp),
        )
        return fittingTextSpPx(
            text = text,
            range = range,
            innerWidthPx = innerWidthPx,
            innerHeightPx = innerHeightPx,
            textPaint = textPaint,
            maxLines = maxLines,
            maxLength = maxLength,
            softWrap = softWrap,
            alignment = alignment,
            includePad = includePad,
        )
    }

    /**
     * EN Same as [fittingTextSpPx] but builds [range] from [ResizeBound]s via [rangePx].
     * PT Idem, construindo o intervalo com [rangePx].
     */
    @JvmStatic
    @JvmOverloads
    fun fittingTextSpPx(
        context: Context,
        text: String,
        min: ResizeBound,
        max: ResizeBound,
        innerWidthPx: Float,
        innerHeightPx: Float,
        textPaint: TextPaint,
        step: ResizeBound = resizeFixedSp(1f),
        maxLines: Int? = null,
        maxLength: Int? = null,
        softWrap: Boolean = true,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        includePad: Boolean = true,
    ): Float {
        val range = rangePx(context, min, max, step)
        return fittingTextSpPx(
            text = text,
            range = range,
            innerWidthPx = innerWidthPx,
            innerHeightPx = innerHeightPx,
            textPaint = textPaint,
            maxLines = maxLines,
            maxLength = maxLength,
            softWrap = softWrap,
            alignment = alignment,
            includePad = includePad,
        )
    }

    /**
     * EN Percent-of-inner-box text range + [fittingTextSpPx] (Compose [autoResizeTextSpPercent] twin).
     * PT Intervalo % da caixa + fitting — equivalente ao [autoResizeTextSpPercent].
     */
    @JvmStatic
    @JvmOverloads
    fun fittingTextSpPercentPx(
        context: Context,
        text: String,
        basis: AutoResizePercentBasis,
        minPercent: Number,
        maxPercent: Number,
        stepSp: Float,
        innerWidthPx: Float,
        innerHeightPx: Float,
        textPaint: TextPaint,
        maxLines: Int? = null,
        maxLength: Int? = null,
        softWrap: Boolean = true,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
        includePad: Boolean = true,
    ): Float {
        val range = rangePxTextSizePercentOfInnerBox(
            context = context,
            basis = basis,
            minPercent = minPercent,
            maxPercent = maxPercent,
            stepSp = stepSp,
            innerWidthPx = innerWidthPx,
            innerHeightPx = innerHeightPx,
        )
        return fittingTextSpPx(
            text = text,
            range = range,
            innerWidthPx = innerWidthPx,
            innerHeightPx = innerHeightPx,
            textPaint = textPaint,
            maxLines = maxLines,
            maxLength = maxLength,
            softWrap = softWrap,
            alignment = alignment,
            includePad = includePad,
        )
    }
}

fun ResizeRangePx.fittingPx(fits: (candidatePx: Float) -> Boolean): Float =
    resolveFitting(fits)

private fun textFitsInnerBox(
    text: String,
    basePaint: TextPaint,
    textSizePx: Float,
    maxWidthPx: Int,
    maxHeightPx: Int,
    maxLines: Int,
    softWrap: Boolean,
    alignment: Layout.Alignment,
    includePad: Boolean,
): Boolean {
    if (!textSizePx.isFinite() || textSizePx <= 0f) return false
    val p = TextPaint(basePaint)
    p.textSize = textSizePx
    if (!softWrap) {
        val w = p.measureText(text)
        val fm = p.fontMetrics
        val h = if (fm != null) {
            fm.descent - fm.ascent
        } else {
            // JVM unit tests (Android stubs) may return null from getFontMetrics().
            // Use line height ≈ text size so a square box test (e.g. maxHeight == max font px) still fits.
            textSizePx
        }
        return w <= maxWidthPx + 0.5f && h <= maxHeightPx + 0.5f
    }
    val builder = StaticLayout.Builder.obtain(text, 0, text.length, p, maxWidthPx)
        .setAlignment(alignment)
        .setIncludePad(includePad)
    if (maxLines != Int.MAX_VALUE) {
        builder.setMaxLines(maxLines)
        builder.setEllipsize(TextUtils.TruncateAt.END)
    }
    val layout = builder.build()
    if (layout.height > maxHeightPx + 0.5f) return false
    if (maxLines != Int.MAX_VALUE) {
        if (layout.lineCount > maxLines) return false
        val last = layout.lineCount - 1
        if (last >= 0 && layout.getEllipsisCount(last) > 0) return false
    }
    return true
}
