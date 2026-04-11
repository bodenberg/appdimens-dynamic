/**
 * EN Basis edge for **0–100** percent auto-resize ranges (local box, not screen %).
 * PT Aresta de referência para intervalos em **% 0–100** da caixa local (não % de ecrã).
 */
package com.appdimens.dynamic.core

enum class AutoResizePercentBasis {
    /** EN Inner content height (after padding). PT Altura útil. */
    HEIGHT,

    /** EN Inner content width. PT Largura útil. */
    WIDTH,

    /** EN `min(inner width, inner height)`. PT Mínimo entre largura e altura úteis. */
    MIN_SIDE,
}
