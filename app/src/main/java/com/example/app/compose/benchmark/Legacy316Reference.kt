/**
 * @author Bodenberg
 *
 * EN Bytecode-faithful reference of the LEGACY published artifact
 *    `io.github.bodenberg:appdimens-sdps:3.1.6` (`com.appdimens.sdps.*`), used by the
 *    on-device "Compare" benchmark as the precision baseline.
 *
 *    The 3.1.6 runtime is resource-table driven: it resolves dimen resources
 *    (`_Nsdp` / `_Nwdp` / `_Nhdp`, plus `_1sdp` for the SDPA adjustment) via
 *    `Resources.getIdentifier/getDimension`, with ids cached process-wide, and
 *    clamps the base value with `coerceIn(-300, 600)`. When the resource table is
 *    missing the legacy falls back to `base * density` (no scaling). The reference
 *    below reproduces the table-installed semantics — the same convention the repo's
 *    parity harness emulates (JVM) and the reason the 3.1.8 kernel is bit-exact
 *    with it for sdp/wdp, ≤1 ulp for hdp axes where `axis/300f != axis * 1/300`,
 *    and ≤2 ulp for sdpa (the legacy re-normalizes through `v/density*300`).
 *
 * PT Referência fiel ao bytecode do artefato legado publicado
 *    `io.github.bodenberg:appdimens-sdps:3.1.6` (`com.appdimens.sdps.*`), usada pelo
 *    benchmark "Comparar" no device como linha de base de precisão.
 *
 *    O runtime 3.1.6 é dirigido por tabela de recursos: resolve dimen resources
 *    (`_Nsdp` / `_Nwdp` / `_Nhdp`, além de `_1sdp` para o ajuste SDPA) via
 *    `Resources.getIdentifier/getDimension`, com ids cacheados no processo, e
 *    clampeia o valor base com `coerceIn(-300, 600)`. Sem a tabela, o legado
 *    cai para `base * density` (sem escalonamento). A referência abaixo reproduz a
 *    semântica com a tabela instalada — a mesma convenção que o harness de paridade
 *    do repositório emula (JVM) e a razão pela qual o kernel 3.1.8 é bit-exact com
 *    ela para sdp/wdp, ≤1 ulp para eixos hdp onde `axis/300f != axis * 1/300`,
 *    e ≤2 ulp para sdpa (o legado re-normaliza via `v/density*300`).
 */
package com.example.app.compose.benchmark

import android.content.Context
import android.content.res.Configuration
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min

/**
 * EN Immutable snapshot of the device window used by the legacy reference math.
 * PT Snapshot imutável da janela do device usado pela matemática de referência legada.
 */
data class Legacy316Window(
    val sw: Int,
    val w: Int,
    val h: Int,
    val density: Float,
) {
    init {
        require(density > 0f) { "density must be positive" }
    }

    companion object {
        /**
         * EN Captures the window from a live [Context] (same sources the legacy reads:
         *    `Resources.getConfiguration()` + `DisplayMetrics.density`).
         * PT Captura a janela de um [Context] vivo (mesmas fontes que o legado lê:
         *    `Resources.getConfiguration()` + `DisplayMetrics.density`).
         */
        fun from(context: Context): Legacy316Window {
            val configuration = context.resources.configuration
            val density = context.resources.displayMetrics.density
            val sw = configuration.smallestScreenWidthDp
                .takeIf { it > 0 }
                ?: min(configuration.screenWidthDp, configuration.screenHeightDp).coerceAtLeast(0)
            return Legacy316Window(
                sw = sw,
                w = configuration.screenWidthDp.coerceAtLeast(0),
                h = configuration.screenHeightDp.coerceAtLeast(0),
                density = density,
            )
        }
    }
}

/**
 * EN Bytecode-faithful 3.1.6 math. Formulas mirror the decompiled
 *    `AppDimensSdpsFactors.rebuild/computeAxisAdjustment` and the dimen-table
 *    semantics of `DimenSdp.getDimensionInPx` (see the class KDoc). Float chains
 *    are kept in the exact legacy order — IEEE-754 is not associative, so any
 *    regrouping changes rounding.
 * PT Matemática 3.1.6 fiel ao bytecode. Fórmulas espelham o decompilado
 *    `AppDimensSdpsFactors.rebuild/computeAxisAdjustment` e a semântica de tabela
 *    de dimen de `DimenSdp.getDimensionInPx` (ver KDoc da classe). As cadeias float
 *    mantêm a ordem exata do legado — IEEE-754 não é associativo, então reagrupar
 *    muda o arredondamento.
 */
object Legacy316Reference {

    /** EN Legacy 3.1.6 accepts only bases in [-300, 600] (`coerceIn`). PT O legado aceita apenas bases em [-300, 600] (`coerceIn`). */
    const val MIN_BASE = -300
    const val MAX_BASE = 600

    /** EN `1f / 300f` as a float literal — the legacy's dimen-table divisor is `300f`. PT `1f / 300f` como literal float. */
    private const val ADJUSTMENT_SCALE = 0.0033333334f

    /** EN `0.08f / 30f` legacy literal (SENSITIVITY). PT Literal legado (SENSITIVITY). */
    private const val SENSITIVITY = 0.0026666666f

    private const val REFERENCE_ASPECT_RATIO = 1.78f

    /** EN The legacy clamps the base before resolving the resource name. PT O legado clampeia a base antes de resolver o nome do recurso. */
    fun clampedBase(base: Int): Int = base.coerceIn(MIN_BASE, MAX_BASE)

    /** EN True when 3.1.6 would resolve the resource for a different (clamped) base. PT True quando o 3.1.6 resolveria o recurso para outra base (clampeada). */
    fun isClamped(base: Int): Boolean = base != clampedBase(base)

    /** EN Generated dimen value: `(N * axis/300f) * density` — the 3.1.6 resource table entry. PT Valor de dimen gerado: `(N * axis/300f) * density`. */
    private fun dimenPx(value: Int, axisDp: Int, density: Float): Float =
        (value * (axisDp / 300f)) * density

    /**
     * EN The SDPA adjustment, `computeAxisAdjustment(res, sw, density, "_1sdp", pkg, logAR)`
     *    with the `_1sdp` dimen = `(1 * sw/300f) * density` (resource installed):
     *    `adjRaw = v/density*300f`, `arFactor = 1 + (sw-300f)*(ADJUSTMENT_SCALE + SENSITIVITY*logAR)`,
     *    result = `arFactor * 300f / adjRaw`.
     * PT O ajuste SDPA, `computeAxisAdjustment(...)` com o dimen `_1sdp` instalado.
     */
    private fun sdpaAdjustment(window: Legacy316Window): Float {
        val density = window.density
        val v = dimenPx(1, window.sw, density)
        val adjRaw = (v / density) * 300f
        if (adjRaw <= 0f) return 1f
        val logAR = logNormalizedAspectRatio(window)
        val arFactor = 1f + (window.sw - 300f) * (ADJUSTMENT_SCALE + SENSITIVITY * logAR)
        return (arFactor * 300f) / adjRaw
    }

    /** EN `ln((max/min)/1.78)` computed as the legacy does (double ln → float). PT `ln((max/min)/1.78)` como o legado calcula (ln double → float). */
    fun logNormalizedAspectRatio(window: Legacy316Window): Float {
        val minDp = min(window.w, window.h).coerceAtLeast(0).toFloat()
        val maxDp = max(window.w, window.h).coerceAtLeast(0).toFloat()
        val ar = if (minDp > 0f) maxDp / minDp else 1f
        return ln((ar / REFERENCE_ASPECT_RATIO).toDouble()).toFloat()
    }

    /** EN Legacy `sdp(value)` with the resource table installed. PT Legacy `sdp(value)` com a tabela instalada. */
    fun sdp(window: Legacy316Window, base: Int): Float = dimenPx(clampedBase(base), window.sw, window.density)

    /** EN Legacy `wdp(value)` with the resource table installed. PT Legacy `wdp(value)` com a tabela instalada. */
    fun wdp(window: Legacy316Window, base: Int): Float = dimenPx(clampedBase(base), window.w, window.density)

    /** EN Legacy `hdp(value)` with the resource table installed. PT Legacy `hdp(value)` com a tabela instalada. */
    fun hdp(window: Legacy316Window, base: Int): Float = dimenPx(clampedBase(base), window.h, window.density)

    /** EN Legacy `sdpa(value)` — `sdp(value) * adjustment`. PT Legacy `sdpa(value)` — `sdp(value) * adjustment`. */
    fun sdpa(window: Legacy316Window, base: Int): Float =
        dimenPx(clampedBase(base), window.sw, window.density) * sdpaAdjustment(window)
}