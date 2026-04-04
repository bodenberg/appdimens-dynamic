/**
 * EN View / Context API: resolve auto-resize bounds to px and pick the largest fitting size.
 * PT API baseada em Context: limites em px e escolha da maior medida que ainda cabe.
 */
package com.appdimens.dynamic.code.resize

import android.content.Context
import com.appdimens.dynamic.core.ResizeBound
import com.appdimens.dynamic.core.ResizeRangePx
import com.appdimens.dynamic.core.resolveToPx

fun interface ResizeFitPredicate {
    fun fits(candidatePx: Float): Boolean
}

object DimenResize {

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
        )
    }

    @JvmStatic
    fun fittingPx(range: ResizeRangePx, predicate: ResizeFitPredicate): Float =
        range.resolveFitting { predicate.fits(it) }
}

fun ResizeRangePx.fittingPx(fits: (candidatePx: Float) -> Boolean): Float =
    resolveFitting(fits)
