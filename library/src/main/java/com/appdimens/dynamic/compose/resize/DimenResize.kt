/**
 * EN Compose auto-resize inside [BoxWithConstraints] (Image / Text). Views: [com.appdimens.dynamic.code.resize.DimenResize].
 * PT Auto-resize no Compose; em Views use [com.appdimens.dynamic.code.resize.DimenResize].
 */
package com.appdimens.dynamic.compose.resize

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.core.ResizeBound
import com.appdimens.dynamic.core.ResizeRangePx
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp
import com.appdimens.dynamic.core.resolveToPx
import com.appdimens.dynamic.core.resizeFixedDp
import com.appdimens.dynamic.core.resizeFixedSp
import kotlin.math.roundToInt

/**
 * EN Which inner box dimension scales [autoResizeTextSpPercent] min/max (0–100).
 * PT Qual dimensão interna define o % min/max do texto.
 */
enum class AutoResizePercentBasis {
    /** EN Inner content height (after padding). PT Altura útil. */
    HEIGHT,

    /** EN Inner content width. PT Largura útil. */
    WIDTH,

    /** EN `min(inner width, inner height)`. PT Mínimo entre largura e altura úteis. */
    MIN_SIDE,
}

private fun Dp.toResizeDpBound(): ResizeBound = ResizeBound.FixedDp(this.value)

private fun TextUnit.toSpFloat(): Float {
    check(type == TextUnitType.Sp) {
        "autoResizeTextSp: TextUnit must be Sp (e.g. 14.sp), got type=$type value=$value"
    }
    return value
}

private fun Number.toDp(): Dp = this.toFloat().dp

/** EN [Number] as percent 0–100 → multiplier 0..1. PT Percentagem 0–100 → fator 0..1. */
private fun Number.percentOfBoxToFactor(): Float = (toFloat() / 100f).coerceIn(0f, 1f)

/** EN Build [ResizeRangePx] from local box px (not screen %). PT Intervalo em px da caixa local. */
private fun localBoxResizeRangePx(minPx: Float, maxPx: Float, stepPx: Float): ResizeRangePx =
    ResizeRangePx(minPx, maxPx, stepPx)

/** EN `null`, `≤ 0`, or `-1` → unlimited lines. PT `null`, `≤ 0` ou `-1` → linhas ilimitadas. */
private fun resolveAutoResizeMaxLines(maxLines: Int?): Int =
    if (maxLines == null || maxLines <= 0 || maxLines == -1) Int.MAX_VALUE else maxLines

/** EN `null`, `≤ 0`, or `-1` → full [text]; else first [maxLength] UTF-16 code units. PT Idem; senão `take(maxLength)`. */
private fun resolveAutoResizeTextForMeasure(text: String, maxLength: Int?): String =
    when {
        maxLength == null || maxLength <= 0 || maxLength == -1 -> text
        else -> text.take(maxLength)
    }

/**
 * EN For [autoResizeSquareSize] only: [contentPadding] wins if non-null; else uniform dp if `> 0` (not `null`, `0`, `-1`, nor `≤ 0`).
 * PT Só quadrado: [contentPadding] se não nulo; senão inset uniforme em dp se `> 0`.
 */
private fun resolveSquareContentPadding(
    contentPadding: PaddingValues?,
    contentPaddingUniformDp: Int?,
): PaddingValues {
    if (contentPadding != null) return contentPadding
    val u = contentPaddingUniformDp
    if (u != null && u > 0) return PaddingValues(u.dp)
    return PaddingValues(0.dp)
}

/**
 * EN [maxWidth]/[maxHeight] of the scope minus [contentPadding] (RTL-aware horizontal insets).
 * PT Largura/altura úteis após descontar [contentPadding] (insets horizontais com RTL).
 */
@Composable
private fun BoxWithConstraintsScope.innerMaxDimensionsPx(contentPadding: PaddingValues): Pair<Float, Float> {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    return remember(maxWidth, maxHeight, contentPadding, layoutDirection, density) {
        val boxWpx = with(density) { maxWidth.toPx() }
        val boxHpx = with(density) { maxHeight.toPx() }
        val padL = with(density) { contentPadding.calculateLeftPadding(layoutDirection).toPx() }
        val padR = with(density) { contentPadding.calculateRightPadding(layoutDirection).toPx() }
        val padT = with(density) { contentPadding.calculateTopPadding().toPx() }
        val padB = with(density) { contentPadding.calculateBottomPadding().toPx() }
        val innerW = (boxWpx - padL - padR).coerceAtLeast(1f)
        val innerH = (boxHpx - padT - padB).coerceAtLeast(1f)
        innerW to innerH
    }
}

/**
 * EN [ResizeRangePx] from **0–100** percents of inner width or height (px) and [step] in dp.
 * PT Intervalo em px a partir de % 0–100 da largura ou altura útil.
 */
@Composable
private fun BoxWithConstraintsScope.boxPercentResizeRange(
    useWidth: Boolean,
    minPercent: Number,
    maxPercent: Number,
    step: Dp,
    contentPadding: PaddingValues,
): ResizeRangePx {
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val base = if (useWidth) innerWpx else innerHpx
    val minPx = base * minPercent.percentOfBoxToFactor()
    val maxPx = base * maxPercent.percentOfBoxToFactor()
    val stepPx = with(density) { step.toPx() }
    return remember(innerWpx, innerHpx, minPercent, maxPercent, stepPx, contentPadding, useWidth) {
        localBoxResizeRangePx(minPx, maxPx, stepPx)
    }
}

/** EN Percents relative to `min(inner width, inner height)` in px. PT % relativos ao mínimo lado útil. */
@Composable
private fun BoxWithConstraintsScope.boxPercentSquareRange(
    minPercent: Number,
    maxPercent: Number,
    step: Dp,
    contentPadding: PaddingValues,
): ResizeRangePx {
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val base = minOf(innerWpx, innerHpx)
    val minPx = base * minPercent.percentOfBoxToFactor()
    val maxPx = base * maxPercent.percentOfBoxToFactor()
    val stepPx = with(density) { step.toPx() }
    return remember(innerWpx, innerHpx, minPercent, maxPercent, stepPx, contentPadding) {
        localBoxResizeRangePx(minPx, maxPx, stepPx)
    }
}

@Composable
private fun rememberResizeRangePx(
    min: ResizeBound,
    max: ResizeBound,
    step: ResizeBound,
): ResizeRangePx {
    val configuration = LocalConfiguration.current
    val androidContext = LocalContext.current
    val density = LocalDensity.current
    val layoutStamp = layoutRememberStamp(configuration, androidContext)
    val pxStamp = pxRememberStamp(layoutStamp, density)
    return remember(pxStamp, min, max, step) {
        val d = density.density
        val fs = density.fontScale.coerceAtLeast(1e-6f)
        ResizeRangePx(
            minPx = min.resolveToPx(configuration, d, fs),
            maxPx = max.resolveToPx(configuration, d, fs),
            stepPx = step.resolveToPx(configuration, d, fs),
        )
    }
}

@Composable
private fun rememberFittingResizePx(
    range: ResizeRangePx,
    constraintStamp: Any?,
    fits: (candidatePx: Float) -> Boolean,
): Float {
    val latest = rememberUpdatedState(fits)
    return remember(range.minPx, range.maxPx, range.stepPx, constraintStamp) {
        range.resolveFitting { latest.value(it) }
    }
}

/**
 * EN Largest square side in `min…max` (step [step]) that fits inside this box (smaller of width/height).
 * EN [contentPadding] optional (RTL-aware). [contentPaddingUniformDp]: `null`, `≤ 0`, or `-1` ignored; used only if [contentPadding] is null.
 * PT [contentPadding] opcional. [contentPaddingUniformDp]: `null`, `≤ 0` ou `-1` ignorados; só vale se [contentPadding] for `null`.
 *
 * ```
 * BoxWithConstraints {
 *     Image(Modifier.size(autoResizeSquareSize(12.sdp, 80.wdp)), …)
 * }
 * ```
 */
@Composable
fun BoxWithConstraintsScope.autoResizeSquareSize(
    min: Dp,
    max: Dp,
    step: Dp = 2.dp,
    contentPadding: PaddingValues? = null,
    contentPaddingUniformDp: Int? = null,
): Dp {
    val resolvedPadding = resolveSquareContentPadding(contentPadding, contentPaddingUniformDp)
    val range = rememberResizeRangePx(
        min.toResizeDpBound(),
        max.toResizeDpBound(),
        step.toResizeDpBound(),
    )
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(resolvedPadding)
    val limitPx = minOf(innerWpx, innerHpx)
    val px = rememberFittingResizePx(range, limitPx) { it <= limitPx }
    return with(density) { px.toDp() }
}

/** PT Mesmo que [autoResizeSquareSize] com [Number] tratado como **dp** (`12` → `12.dp`). */
@Composable
fun BoxWithConstraintsScope.autoResizeSquareSize(
    min: Number,
    max: Number,
    step: Number = 2,
    contentPadding: PaddingValues? = null,
    contentPaddingUniformDp: Int? = null,
): Dp = autoResizeSquareSize(min.toDp(), max.toDp(), step.toDp(), contentPadding, contentPaddingUniformDp)

/**
 * EN Largest width in `min…max` that fits the **inner** width (after [contentPadding]) of this box.
 *
 * ```
 * Image(Modifier.fillMaxWidth().height(autoResizeWidthSize(40.dp, 200.dp)), …)
 * ```
 */
@Composable
fun BoxWithConstraintsScope.autoResizeWidthSize(
    min: Dp,
    max: Dp,
    step: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = rememberResizeRangePx(
        min.toResizeDpBound(),
        max.toResizeDpBound(),
        step.toResizeDpBound(),
    )
    val density = LocalDensity.current
    val (innerWpx, _) = innerMaxDimensionsPx(contentPadding)
    val limitPx = innerWpx
    val px = rememberFittingResizePx(range, limitPx) { it <= limitPx }
    return with(density) { px.toDp() }
}

@Composable
fun BoxWithConstraintsScope.autoResizeWidthSize(
    min: Number,
    max: Number,
    step: Number = 2,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp = autoResizeWidthSize(min.toDp(), max.toDp(), step.toDp(), contentPadding)

/**
 * EN Largest height in `min…max` that fits the **inner** height (after [contentPadding]) of this box.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeHeightSize(
    min: Dp,
    max: Dp,
    step: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = rememberResizeRangePx(
        min.toResizeDpBound(),
        max.toResizeDpBound(),
        step.toResizeDpBound(),
    )
    val density = LocalDensity.current
    val (_, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val limitPx = innerHpx
    val px = rememberFittingResizePx(range, limitPx) { it <= limitPx }
    return with(density) { px.toDp() }
}

@Composable
fun BoxWithConstraintsScope.autoResizeHeightSize(
    min: Number,
    max: Number,
    step: Number = 2,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp = autoResizeHeightSize(min.toDp(), max.toDp(), step.toDp(), contentPadding)

/**
 * EN [minPercent]/[maxPercent] are **0–100** of the **inner content width** (after [contentPadding]). Step in dp.
 * PT % 0–100 da **largura útil** da caixa.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeWidthSizePercent(
    minPercent: Number,
    maxPercent: Number,
    step: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = boxPercentResizeRange(useWidth = true, minPercent, maxPercent, step, contentPadding)
    val density = LocalDensity.current
    val (innerWpx, _) = innerMaxDimensionsPx(contentPadding)
    val px = rememberFittingResizePx(range, innerWpx) { it <= innerWpx }
    return with(density) { px.toDp() }
}

/**
 * EN [minPercent]/[maxPercent] are **0–100** of the **inner content height** (after [contentPadding]).
 * PT % 0–100 da **altura útil** da caixa.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeHeightSizePercent(
    minPercent: Number,
    maxPercent: Number,
    step: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = boxPercentResizeRange(useWidth = false, minPercent, maxPercent, step, contentPadding)
    val density = LocalDensity.current
    val (_, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val px = rememberFittingResizePx(range, innerHpx) { it <= innerHpx }
    return with(density) { px.toDp() }
}

/**
 * EN [min]/[max]/[step] as [ResizeBound]: fixed dp/sp or **screen** % (sw / w / h via [com.appdimens.dynamic.core.resizePercentSw] etc.).
 */
@Composable
fun BoxWithConstraintsScope.autoResizeWidthSize(
    min: ResizeBound,
    max: ResizeBound,
    step: ResizeBound = resizeFixedDp(1f),
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = rememberResizeRangePx(min, max, step)
    val density = LocalDensity.current
    val (innerWpx, _) = innerMaxDimensionsPx(contentPadding)
    val px = rememberFittingResizePx(range, innerWpx) { it <= innerWpx }
    return with(density) { px.toDp() }
}

/**
 * EN Same as [autoResizeWidthSize] for the vertical axis and **screen** [ResizeBound]s.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeHeightSize(
    min: ResizeBound,
    max: ResizeBound,
    step: ResizeBound = resizeFixedDp(1f),
    contentPadding: PaddingValues = PaddingValues(0.dp),
): Dp {
    val range = rememberResizeRangePx(min, max, step)
    val density = LocalDensity.current
    val (_, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val px = rememberFittingResizePx(range, innerHpx) { it <= innerHpx }
    return with(density) { px.toDp() }
}

/**
 * EN Same as [autoResizeSquareSize] but min/max/step may use % of Sw / W / H via [ResizeBound].
 */
@Composable
fun BoxWithConstraintsScope.autoResizeSquareSize(
    min: ResizeBound,
    max: ResizeBound,
    step: ResizeBound = resizeFixedDp(1f),
    contentPadding: PaddingValues? = null,
    contentPaddingUniformDp: Int? = null,
): Dp {
    val resolvedPadding = resolveSquareContentPadding(contentPadding, contentPaddingUniformDp)
    val range = rememberResizeRangePx(min, max, step)
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(resolvedPadding)
    val limitPx = minOf(innerWpx, innerHpx)
    val px = rememberFittingResizePx(range, limitPx) { it <= limitPx }
    return with(density) { px.toDp() }
}

/**
 * EN [minPercent]/[maxPercent] are **0–100** of `min(inner width, inner height)` after padding. [step] in dp.
 * PT % 0–100 do menor lado útil da caixa.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeSquareSizePercent(
    minPercent: Number,
    maxPercent: Number,
    step: Dp = 2.dp,
    contentPadding: PaddingValues? = null,
    contentPaddingUniformDp: Int? = null,
): Dp {
    val resolvedPadding = resolveSquareContentPadding(contentPadding, contentPaddingUniformDp)
    val range = boxPercentSquareRange(minPercent, maxPercent, step, resolvedPadding)
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(resolvedPadding)
    val limitPx = minOf(innerWpx, innerHpx)
    val px = rememberFittingResizePx(range, limitPx) { it <= limitPx }
    return with(density) { px.toDp() }
}

/**
 * EN Largest font size between [minSp] and [maxSp] (step [stepSp]) so [text] fits width/height of this box.
 * PT [Number] = valor em **sp** (ex. `12`, `12.5f`). [Dp] usa o **valor numérico** do dp como sp (ex. `12.dp` → 12 sp).
 *
 * EN Measurement uses the full resolved style except `fontSize` (which is swept): font weight, letter spacing, line height,
 * `lineHeightStyle`, `platformTextStyle` / font padding, text alignment, etc. — align with your `Text` composable.
 * Pass [softWrap] and [overflow] with the same values as `Text` when needed.
 * PT Estilo resolvido como no `Text`; repita [softWrap] e [overflow] quando necessário.
 * EN/PT [contentPadding]: subtract parent (or slot) insets from the box so sizing matches the padded content area.
 *
 * EN [style] `null` → `LocalTextStyle.current`. [maxLines] / [maxLength] `null`, `≤ 0`, or `-1` → unlimited / full string.
 * PT [style] `null` → `LocalTextStyle.current`. [maxLines] / [maxLength] `null`, `≤ 0` ou `-1` → sem limite / texto completo.
 * EN If [maxLength] truncates, use the same prefix in `Text` (e.g. `text.take(maxLength)`) so the size matches.
 * PT Se [maxLength] cortar o texto, use o mesmo prefixo no `Text` para o tamanho bater certo.
 *
 * ```
 * BoxWithConstraints {
 *     Text("…", fontSize = autoResizeTextSp("…", 12, 48))
 *     Text("…", fontSize = autoResizeTextSp("…", 12.sp, 48.sp))
 * }
 * ```
 */
@Composable
fun BoxWithConstraintsScope.autoResizeTextSp(
    text: String,
    min: ResizeBound,
    max: ResizeBound,
    step: ResizeBound = resizeFixedSp(1f),
    style: TextStyle? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): TextUnit {
    val resolvedStyle = style ?: LocalTextStyle.current
    val measureText = resolveAutoResizeTextForMeasure(text, maxLength)
    val effMaxLines = resolveAutoResizeMaxLines(maxLines)
    val range = rememberResizeRangePx(min, max, step)
    return autoResizeTextSpWithRange(
        measureText = measureText,
        range = range,
        resolvedStyle = resolvedStyle,
        effMaxLines = effMaxLines,
        softWrap = softWrap,
        overflow = overflow,
        contentPadding = contentPadding,
    )
}

@Composable
fun BoxWithConstraintsScope.autoResizeTextSp(
    text: String,
    minSp: Number,
    maxSp: Number,
    stepSp: Number = 2,
    style: TextStyle? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): TextUnit = autoResizeTextSpImpl(
    text = text,
    minSp = minSp.toFloat(),
    maxSp = maxSp.toFloat(),
    stepSp = stepSp.toFloat(),
    style = style,
    maxLines = maxLines,
    maxLength = maxLength,
    softWrap = softWrap,
    overflow = overflow,
    contentPadding = contentPadding,
)

@Composable
fun BoxWithConstraintsScope.autoResizeTextSp(
    text: String,
    minSp: TextUnit,
    maxSp: TextUnit,
    stepSp: TextUnit = 2.sp,
    style: TextStyle? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): TextUnit = autoResizeTextSpImpl(
    text = text,
    minSp = minSp.toSpFloat(),
    maxSp = maxSp.toSpFloat(),
    stepSp = stepSp.toSpFloat(),
    style = style,
    maxLines = maxLines,
    maxLength = maxLength,
    softWrap = softWrap,
    overflow = overflow,
    contentPadding = contentPadding,
)

@Composable
fun BoxWithConstraintsScope.autoResizeTextSp(
    text: String,
    minSp: Dp,
    maxSp: Dp,
    stepSp: Dp = 2.dp,
    style: TextStyle? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): TextUnit = autoResizeTextSpImpl(
    text = text,
    minSp = minSp.value,
    maxSp = maxSp.value,
    stepSp = stepSp.value,
    style = style,
    maxLines = maxLines,
    maxLength = maxLength,
    softWrap = softWrap,
    overflow = overflow,
    contentPadding = contentPadding,
)

/**
 * EN Font size range from **0–100** [minPercent]/[maxPercent] of the inner box edge chosen by [percentBasis]
 * (default [AutoResizePercentBasis.HEIGHT]). [stepSp] is in **sp** (same as fixed [autoResizeTextSp]).
 * PT Intervalo de texto em % 0–100 da aresta interna ([percentBasis]); passo em **sp**.
 */
@Composable
fun BoxWithConstraintsScope.autoResizeTextSpPercent(
    text: String,
    minPercent: Number,
    maxPercent: Number,
    stepSp: Number = 2,
    percentBasis: AutoResizePercentBasis = AutoResizePercentBasis.HEIGHT,
    style: TextStyle? = null,
    maxLines: Int? = null,
    maxLength: Int? = null,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    contentPadding: PaddingValues = PaddingValues(0.dp),
): TextUnit {
    val resolvedStyle = style ?: LocalTextStyle.current
    val measureText = resolveAutoResizeTextForMeasure(text, maxLength)
    val effMaxLines = resolveAutoResizeMaxLines(maxLines)
    val range = textSpPercentResizeRange(
        minPercent = minPercent,
        maxPercent = maxPercent,
        stepSp = stepSp,
        percentBasis = percentBasis,
        contentPadding = contentPadding,
    )
    return autoResizeTextSpWithRange(
        measureText = measureText,
        range = range,
        resolvedStyle = resolvedStyle,
        effMaxLines = effMaxLines,
        softWrap = softWrap,
        overflow = overflow,
        contentPadding = contentPadding,
    )
}

/** EN [ResizeRangePx] for text: min/max in px from % of inner box; step from sp. */
@Composable
private fun BoxWithConstraintsScope.textSpPercentResizeRange(
    minPercent: Number,
    maxPercent: Number,
    stepSp: Number,
    percentBasis: AutoResizePercentBasis,
    contentPadding: PaddingValues,
): ResizeRangePx {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val d = density.density
    val fs = density.fontScale.coerceAtLeast(1e-6f)
    val base = when (percentBasis) {
        AutoResizePercentBasis.HEIGHT -> innerHpx
        AutoResizePercentBasis.WIDTH -> innerWpx
        AutoResizePercentBasis.MIN_SIDE -> minOf(innerWpx, innerHpx)
    }
    val minPx = base * minPercent.percentOfBoxToFactor()
    val maxPx = base * maxPercent.percentOfBoxToFactor()
    val stepPx = resizeFixedSp(stepSp.toFloat()).resolveToPx(configuration, d, fs)
    return remember(
        configuration,
        innerWpx,
        innerHpx,
        minPercent,
        maxPercent,
        stepSp,
        percentBasis,
        contentPadding,
        d,
        fs,
    ) {
        localBoxResizeRangePx(minPx, maxPx, stepPx)
    }
}

@Composable
private fun BoxWithConstraintsScope.autoResizeTextSpWithRange(
    measureText: String,
    range: ResizeRangePx,
    resolvedStyle: TextStyle,
    effMaxLines: Int,
    softWrap: Boolean,
    overflow: TextOverflow,
    contentPadding: PaddingValues,
): TextUnit {
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val (innerWpx, innerHpx) = innerMaxDimensionsPx(contentPadding)
    val d = density.density
    val fs = density.fontScale.coerceAtLeast(1e-6f)
    val maxWInt = innerWpx.roundToInt().coerceAtLeast(1)
    val maxHInt = innerHpx.roundToInt().coerceAtLeast(1)

    val px = remember(
        measureText,
        resolvedStyle,
        effMaxLines,
        softWrap,
        overflow,
        layoutDirection,
        contentPadding,
        innerWpx,
        innerHpx,
        maxWInt,
        maxHInt,
        range.minPx,
        range.maxPx,
        range.stepPx,
    ) {
        range.resolveFitting { candidatePx ->
            val spSize = candidatePx / (d * fs)
            val layout = measurer.measure(
                text = AnnotatedString(measureText),
                style = resolvedStyle.copy(fontSize = spSize.sp),
                overflow = overflow,
                softWrap = softWrap,
                maxLines = effMaxLines,
                constraints = Constraints(maxWidth = maxWInt, maxHeight = maxHInt),
                layoutDirection = layoutDirection,
            )
            layout.size.width <= innerWpx + 0.5f &&
                layout.size.height <= innerHpx + 0.5f &&
                !layout.didOverflowWidth &&
                !layout.didOverflowHeight
        }
    }
    val chosenSp = px / (d * fs)
    return chosenSp.sp
}

@Composable
private fun BoxWithConstraintsScope.autoResizeTextSpImpl(
    text: String,
    minSp: Float,
    maxSp: Float,
    stepSp: Float,
    style: TextStyle?,
    maxLines: Int?,
    maxLength: Int?,
    softWrap: Boolean,
    overflow: TextOverflow,
    contentPadding: PaddingValues,
): TextUnit {
    val resolvedStyle = style ?: LocalTextStyle.current
    val measureText = resolveAutoResizeTextForMeasure(text, maxLength)
    val effMaxLines = resolveAutoResizeMaxLines(maxLines)
    val range = rememberResizeRangePx(
        resizeFixedSp(minSp),
        resizeFixedSp(maxSp),
        resizeFixedSp(stepSp),
    )
    return autoResizeTextSpWithRange(
        measureText = measureText,
        range = range,
        resolvedStyle = resolvedStyle,
        effMaxLines = effMaxLines,
        softWrap = softWrap,
        overflow = overflow,
        contentPadding = contentPadding,
    )
}
