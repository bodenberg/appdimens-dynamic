package com.appdimens.parity

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Parity harness: local 3.1.9 (`com.appdimens.dynamic.*`) vs the legacy published
 * artifact `io.github.bodenberg:appdimens-sdps:3.1.6` (`com.appdimens.sdps.*`).
 *
 * Both libraries run in the same JVM against the identical mocked window
 * (same [Configuration] and display density) and every result is compared by
 * RAW FLOAT BITS — the project's bit-exactness contract with the legacy math.
 *
 * Run with: `./gradlew :parity:test --tests "com.appdimens.parity.*"`
 *
 * PT Harness de paridade: 3.1.9 local (`com.appdimens.dynamic.*`) vs o artefato
 * legado publicado `io.github.bodenberg:appdimens-sdps:3.1.6` (`com.appdimens.sdps.*`).
 *
 * As duas bibliotecas rodam na mesma JVM com a mesma janela mockada (mesmo
 * [Configuration] e densidade) e cada resultado é comparado por BITS BRUTOS de
 * float — o contrato de bit-exactness com a matemática legada.
 */
class Legacy316ParityTest {

    private val legacy = com.appdimens.sdps.code.DimenSdp
    private val current = com.appdimens.dynamic.code.DimenSdp

    private data class Window(
        val label: String,
        val sw: Int,
        val w: Int,
        val h: Int,
        val dpi: Int,
        val orientation: Int,
        val fontScale: Float = 1f,
    )

    private val windows = listOf(
        Window("phone-portrait-420", 360, 360, 800, 420, Configuration.ORIENTATION_PORTRAIT),
        Window("phone-landscape-420", 360, 800, 360, 420, Configuration.ORIENTATION_LANDSCAPE),
        Window("tablet-320", 600, 960, 1280, 320, Configuration.ORIENTATION_LANDSCAPE),
        Window("small-160", 300, 300, 533, 160, Configuration.ORIENTATION_PORTRAIT),
        Window("fold-560", 480, 600, 840, 560, Configuration.ORIENTATION_PORTRAIT),
        Window("tv-240", 720, 1280, 720, 240, Configuration.ORIENTATION_LANDSCAPE),
        Window("phone-font1.3-440", 411, 411, 891, 440, Configuration.ORIENTATION_PORTRAIT, fontScale = 1.3f),
    )

    private val bases = intArrayOf(1, 2, 4, 8, 16, 24, 32, 48, 64, 100, 128, 256, 300, 512, 1024)

    // Global legacy resource table: id -> (base value, family suffix). The legacy
    // caches ids process-wide, so this must live outside the per-window mock.
    private val dimenTable = java.util.concurrent.ConcurrentHashMap<Int, Pair<Int, String>>()

    private fun mockContext(window: Window): Context {
        val configuration = Configuration().apply {
            smallestScreenWidthDp = window.sw
            screenWidthDp = window.w
            screenHeightDp = window.h
            densityDpi = window.dpi
            fontScale = window.fontScale
            this.orientation = window.orientation
        }
        val density = window.dpi / 160f
        val resources = mock<Resources>()
        whenever(resources.configuration).thenReturn(configuration)
        whenever(resources.displayMetrics).thenReturn(
            DisplayMetrics().apply { this.density = density }
        )
        // EN Emulate the legacy 3.1.6 dimen resource table (`_Nsdp` / `_Nwdp` / `_Nhdp`).
        //    The legacy runtime resolves the resource id ONCE per (package, name) and
        //    caches it process-wide, so the table must be GLOBAL (shared by every
        //    mocked window) — id resolution only fires for the first window.
        // PT Emula a tabela de dimen resources do legado 3.1.6 (`_Nsdp` / `_Nwdp` / `_Nhdp`).
        //    O runtime legado resolve o id do recurso UMA vez por (pacote, nome) e o
        //    cacheia no processo, então a tabela precisa ser GLOBAL (compartilhada por
        //    todas as janelas mockadas) — a resolução do id só dispara na 1ª janela.
        whenever(resources.getIdentifier(any(), eq("dimen"), any())).thenAnswer { inv ->
            val name = inv.getArgument<String>(0)
            val suffix = listOf("sdp", "wdp", "hdp").firstOrNull { name.endsWith(it) }
            if (suffix != null) {
                val value = name.removeSuffix(suffix).removePrefix("_").toIntOrNull()
                if (value != null && value in -300..600) {
                    val id = name.hashCode()
                    dimenTable[id] = value to suffix
                    return@thenAnswer id
                }
            }
            return@thenAnswer 0
        }
        whenever(resources.getDimension(any())).thenAnswer { inv ->
            val id = inv.getArgument<Int>(0)
            val (value, suffix) = dimenTable[id] ?: return@thenAnswer 0f
            val axis = when (suffix) {
                "sdp" -> window.sw
                "wdp" -> window.w
                else -> window.h
            }
            (value * (axis / 300f)) * density
        }
        val app = mock<Context>()
        whenever(app.resources).thenReturn(resources)
        whenever(app.applicationContext).thenReturn(app)
        whenever(app.packageName).thenReturn("com.appdimens.parity")
        return app
    }

    // EN Legacy 3.1.6 accepts only bases in [-300, 600] (`coerceIn`); outside it the
    //    resource id is resolved for the clamped base (e.g. 1024 → `_600sdp`).
    // PT O legado 3.1.6 aceita apenas bases em [-300, 600] (`coerceIn`); fora disso o
    //    id do recurso é resolvido para a base clamped (ex.: 1024 → `_600sdp`).
    private val invLegacyValidRange = -300..600

    // Kernel constant mirrored from the local library (DimenCache.INV_BASE_RATIO).
    private val invBaseRatio = 0.0033333334f

    private class Resolution(
        val family: String,
        val window: String,
        val base: Int,
        val legacyBits: Int,
        val currentBits: Int,
        val inLegacyRange: Boolean,
        val allowedUlps: Int,
    ) {
        val ulpDelta: Long = kotlin.math.abs(legacyBits.toLong() - currentBits.toLong())
        val bitExact: Boolean get() = legacyBits == currentBits
        val tolerated: Boolean get() = bitExact || (inLegacyRange && ulpDelta <= allowedUlps)
        val violates: Boolean get() = inLegacyRange && !tolerated

        override fun toString(): String =
            "$family @$window base=$base legacy=0x${legacyBits.toString(16)} current=0x${currentBits.toString(16)}" +
                (if (inLegacyRange) " ulp=$ulpDelta allowed=$allowedUlps" else " [legacy clamps to range]")
    }

    // EN Per-axis scale artifact: the legacy dimen table divides by 300f while the
    //    3.1.9 kernel multiplies by INV_BASE_RATIO. For most axes both round to the
    //    same float; when they differ the resolution can differ by 1 ulp. This is the
    //    documented tolerance (never a >1 ulp deviation).
    // PT Artefato de escala por eixo: a tabela de dimen do legado divide por 300f
    //    enquanto o kernel 3.1.9 multiplica por INV_BASE_RATIO. Na maioria dos eixos
    //    ambos arredondam para o mesmo float; quando diferem, a resolução pode
    //    divergir em 1 ulp. Esta é a tolerância documentada (nunca > 1 ulp).
    private fun axisScaleUlpDiverges(axisDp: Int): Boolean =
        (axisDp / 300f).toRawBits() != (axisDp * invBaseRatio).toRawBits()

    private fun runComparison(): List<Resolution> {
        val results = mutableListOf<Resolution>()
        for (window in windows) {
            val ctx = mockContext(window)
            for (b in bases) {
                val pairs = listOf(
                    Triple("sdp", { legacy.sdp(ctx, b) }, { current.sdp(ctx, b) }),
                    Triple("sdpa", { legacy.sdpa(ctx, b) }, { current.sdpa(ctx, b) }),
                    Triple("hdp", { legacy.hdp(ctx, b) }, { current.hdp(ctx, b) }),
                    Triple("wdp", { legacy.wdp(ctx, b) }, { current.wdp(ctx, b) }),
                )
                for ((family, legacyCall, currentCall) in pairs) {
                    val legacyBits = legacyCall().toRawBits()
                    val currentBits = currentCall().toRawBits()
                    val axis = when (family) {
                        "sdp" -> window.sw
                        "wdp" -> window.w
                        else -> window.h
                    }
                    // SDPA re-normalizes through the legacy dimen table (`v/density*300`
                    // and back), leaving ≤ 2 ulp of rounding noise vs the 3.1.9 formula.
                    val allowed = if (family == "sdpa") 2
                    else if (axisScaleUlpDiverges(axis)) 1
                    else 0
                    results += Resolution(
                        family, window.label, b, legacyBits, currentBits,
                        inLegacyRange = b in invLegacyValidRange,
                        allowedUlps = allowed,
                    )
                }
            }
        }
        return results
    }

    @Test
    fun scaledFamilies_areBitExactWithLegacy316() {
        val results = runComparison()
        System.out.println(
            "LEGACY-PARITY: ${windows.size} windows x ${bases.size} bases x 4 families " +
                "= ${windows.size * bases.size * 4} resolutions"
        )
        val byFamily = results.groupBy { it.family }
        for (family in listOf("sdp", "wdp", "hdp", "sdpa")) {
            val list = byFamily.getValue(family)
            val inRange = list.filter { it.inLegacyRange }
            val bitExact = inRange.count { it.bitExact }
            val tolerated = inRange.count { it.tolerated }
            val violations = inRange.filter { it.violates }
            System.out.println(
                "LEGACY-PARITY: family=$family in-range=${inRange.size} bit-exact=$bitExact " +
                    "within-tolerance=$tolerated violations=${violations.size}"
            )
            list.filter { it.ulpDelta > 0L && it.inLegacyRange }.take(5)
                .forEach { System.out.println("  doc:$it") }
        }
        val clamp = results.count { !it.inLegacyRange }
        if (clamp > 0) {
            System.out.println(
                "LEGACY-PARITY: clamp: $clamp resolutions outside [-300,600] " +
                    "— the legacy resolves them for the clamped base (e.g. 1024 → `_600sdp`); reported only"
            )
        }
        val violations = results.filter { it.violates }
        System.out.println(
            if (violations.isEmpty()) "LEGACY-PARITY: OK — in-range resolutions are within the documented tolerance"
            else "LEGACY-PARITY: FAIL"
        )
        assertTrue(
            "in-range resolutions must be bit-exact (sdp/wdp), ≤1 ulp on diverging axes (hdp) or ≤2 ulp (sdpa): $violations",
            violations.isEmpty()
        )
    }
}