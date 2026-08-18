package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.code.sdp
import com.appdimens.dynamic.code.toDynamicScaledDp
import com.appdimens.dynamic.code.toDynamicScaledPx
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * 3.1.9 fast-lane kernels ([DimenCache.resolveSdpPx] and siblings) must be
 * bit-exact against the untouched legacy full path (`toDynamicScaledPx` /
 * `toDynamicScaledDp`) and must follow the event-driven invalidation contract:
 * a configuration change invalidates the fast slot synchronously.
 *
 * PT Os kernels do fast lane 3.1.9 ([DimenCache.resolveSdpPx] e irmãos) devem ser
 * bit-exatos em relação ao caminho completo legado intacto (`toDynamicScaledPx` /
 * `toDynamicScaledDp`) e devem seguir o contrato de invalidação orientada a eventos:
 * uma mudança de configuração invalida o slot rápido sincronamente.
 */
class DimenFastLaneKernelsTest {

    private lateinit var configuration: Configuration
    private lateinit var ctx: Context

    @Before
    fun setup() {
        configuration = Configuration().apply {
            smallestScreenWidthDp = 360
            screenWidthDp = 720
            screenHeightDp = 800
            densityDpi = 420
            fontScale = 1f
            this.orientation = Configuration.ORIENTATION_PORTRAIT
        }
        ctx = mockContext(configuration)
        DimenCache.clearAll()
        DimenCache.isEnabled = true
        DimenCache.invalidateOnConfigChange(configuration)
    }

    private fun mockContext(configuration: Configuration): Context {
        val resources = mock<Resources>()
        whenever(resources.configuration).thenReturn(configuration)
        whenever(resources.displayMetrics).thenReturn(
            DisplayMetrics().apply { this.density = 2.625f }
        )
        val app = mock<Context>()
        whenever(app.resources).thenReturn(resources)
        whenever(app.applicationContext).thenReturn(app)
        return app
    }

    private fun legacyPx(base: Float, qualifier: DpQualifier, ar: Boolean = false): Float =
        base.toDynamicScaledPx(ctx, qualifier, applyAspectRatio = ar)

    private fun legacyDp(base: Float, qualifier: DpQualifier, ar: Boolean = false): Float =
        base.toDynamicScaledDp(ctx, qualifier, applyAspectRatio = ar)

    private val bases = floatArrayOf(1f, 2f, 7f, 16f, 24f, 48f, 100f, 300f, 1024f, 3.5f, 0.75f)

    @Test
    fun sdpKernel_isBitExactWithLegacyPath() {
        for (b in bases) {
            val fast = DimenCache.resolveSdpPx(b, ctx)
            val legacy = legacyPx(b, DpQualifier.SMALL_WIDTH)
            assertEquals("sdp px base=$b", legacy.toRawBits(), fast.toRawBits())
            val fastDp = DimenCache.resolveSdpDp(b, ctx)
            val legacyDp = legacyDp(b, DpQualifier.SMALL_WIDTH)
            assertEquals("sdp dp base=$b", legacyDp.toRawBits(), fastDp.toRawBits())
        }
    }

    @Test
    fun sdpaKernel_isBitExactWithLegacyPath() {
        for (b in bases) {
            val fast = DimenCache.resolveSdpaPx(b, ctx)
            val legacy = legacyPx(b, DpQualifier.SMALL_WIDTH, ar = true)
            assertEquals("sdpa px base=$b", legacy.toRawBits(), fast.toRawBits())
            val fastDp = DimenCache.resolveSdpaDp(b, ctx)
            val legacyDp = legacyDp(b, DpQualifier.SMALL_WIDTH, ar = true)
            assertEquals("sdpa dp base=$b", legacyDp.toRawBits(), fastDp.toRawBits())
        }
    }

    @Test
    fun hdpKernel_isBitExactWithLegacyPath() {
        for (b in bases) {
            val fast = DimenCache.resolveHdpPx(b, ctx)
            val legacy = legacyPx(b, DpQualifier.HEIGHT)
            assertEquals("hdp px base=$b", legacy.toRawBits(), fast.toRawBits())
            val fastDp = DimenCache.resolveHdpDp(b, ctx)
            val legacyDp = legacyDp(b, DpQualifier.HEIGHT)
            assertEquals("hdp dp base=$b", legacyDp.toRawBits(), fastDp.toRawBits())
        }
    }

    @Test
    fun wdpKernel_isBitExactWithLegacyPath() {
        for (b in bases) {
            val fast = DimenCache.resolveWdpPx(b, ctx)
            val legacy = legacyPx(b, DpQualifier.WIDTH)
            assertEquals("wdp px base=$b", legacy.toRawBits(), fast.toRawBits())
            val fastDp = DimenCache.resolveWdpDp(b, ctx)
            val legacyDp = legacyDp(b, DpQualifier.WIDTH)
            assertEquals("wdp dp base=$b", legacyDp.toRawBits(), fastDp.toRawBits())
        }
    }

    @Test
    fun kernelResults_matchPublicExtensionEntries() {
        val fast = DimenCache.resolveSdpPx(16f, ctx)
        val viaExtension = 16.sdp(ctx)
        assertEquals("16.sdp(ctx) must hit the same kernel", fast.toRawBits(), viaExtension.toRawBits())
        val fastDp = DimenCache.resolveSdpaDp(16f, ctx)
        val viaExtensionDp = 16f.toDynamicScaledDp(ctx, DpQualifier.SMALL_WIDTH, applyAspectRatio = true)
        assertEquals("toDynamicScaledDp(ar) must hit the same kernel", fastDp.toRawBits(), viaExtensionDp.toRawBits())
    }

    @Test
    fun configChange_invalidatesFastSlotSynchronously() {
        DimenCache.resolveSdpPx(16f, ctx)
        DimenCache.invalidateOnConfigChange(configuration)
        assertNull("invalidateOnConfigChange must null the fast slot", DimenCache.fastWindowSlot)

        configuration.smallestScreenWidthDp = 800
        configuration.screenWidthDp = 1440
        configuration.densityDpi = 560
        DimenCache.invalidateOnConfigChange(configuration)

        val fast = DimenCache.resolveSdpPx(16f, ctx)
        val expected = 16f * (800f * DimenCache.INV_BASE_RATIO) * (560f / 160f)
        assertEquals("kernel must rebuild with the new snapshot", expected, fast, 0f)
        val legacy = legacyPx(16f, DpQualifier.SMALL_WIDTH)
        assertEquals("kernel and legacy must stay bit-exact after the change", legacy.toRawBits(), fast.toRawBits())
    }
}