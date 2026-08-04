package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.unit.Density
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.mockito.kotlin.mock

class ComposeRememberStampsTest {

    private val ctx: Context = mock()

    private fun config(
        sw: Int = 400,
        w: Int = 400,
        h: Int = 800,
        dpi: Int = 420,
        fontScale: Float = 1f,
        orientation: Int = Configuration.ORIENTATION_PORTRAIT,
    ): Configuration = Configuration().apply {
        smallestScreenWidthDp = sw
        screenWidthDp = w
        screenHeightDp = h
        densityDpi = dpi
        this.fontScale = fontScale
        this.orientation = orientation
    }

    @Test
    fun layoutStamp_ignoresFontScale() {
        val a = config(fontScale = 1f)
        val b = config(fontScale = 1.5f)
        assertEquals(layoutRememberStamp(a, ctx), layoutRememberStamp(b, ctx))
    }

    @Test
    fun layoutStamp_changesWithDpi() {
        val a = config(dpi = 420)
        val b = config(dpi = 320)
        assertNotEquals(layoutRememberStamp(a, ctx), layoutRememberStamp(b, ctx))
    }

    @Test
    fun layoutStamp_dpiDoesNotCollideWithHeightBits() {
        // Former bug: `h or (dpi shl 4)` made distinct (h,dpi) pairs share a stamp.
        val a = config(h = 16, dpi = 1)   // h bits get dpi<<4 under the old OR scheme
        val b = config(h = 0, dpi = 1)
        assertNotEquals(layoutRememberStamp(a, ctx), layoutRememberStamp(b, ctx))

        val c = config(h = 0x10, dpi = 0) // height bit 4 set, dpi 0
        val d = config(h = 0, dpi = 1)    // old scheme: dpi<<4 == 0x10 → same as c
        assertNotEquals(layoutRememberStamp(c, ctx), layoutRememberStamp(d, ctx))
    }

    @Test
    fun layoutStamp_changesWithOrientationAndSw() {
        val base = config()
        val rotated = config(orientation = Configuration.ORIENTATION_LANDSCAPE)
        val wider = config(sw = 600)
        assertNotEquals(layoutRememberStamp(base, ctx), layoutRememberStamp(rotated, ctx))
        assertNotEquals(layoutRememberStamp(base, ctx), layoutRememberStamp(wider, ctx))
    }

    @Test
    fun pxStamp_ignoresFontScale() {
        val layout = layoutRememberStamp(config(), ctx)
        val d1 = Density(density = 2f, fontScale = 1f)
        val d2 = Density(density = 2f, fontScale = 1.5f)
        assertEquals(pxRememberStamp(layout, d1), pxRememberStamp(layout, d2))
    }

    @Test
    fun spStamp_includesFontScale() {
        val layout = layoutRememberStamp(config(), ctx)
        val d1 = Density(density = 2f, fontScale = 1f)
        val d2 = Density(density = 2f, fontScale = 1.5f)
        assertNotEquals(spRememberStamp(layout, d1), spRememberStamp(layout, d2))
    }

    @Test
    fun scaledEntryStamp_ignoresDpiAndAspectRatioNoise() {
        val a = config(dpi = 420)
        val b = config(dpi = 320)
        // Same SW/W/H/orientation → same matcher stamp regardless of dpi / AR float.
        assertEquals(
            scaledEntryRememberStamp(0, a, aspectRatio = 0.5f, ignoreMultiWindows = false),
            scaledEntryRememberStamp(0, b, aspectRatio = 0.9f, ignoreMultiWindows = false),
        )
    }

    @Test
    fun scaledEntryStamp_tracksUiModeAndIgnoreMultiWindows() {
        val c = config()
        assertNotEquals(
            scaledEntryRememberStamp(0, c, 1f, false),
            scaledEntryRememberStamp(1, c, 1f, false),
        )
        assertNotEquals(
            scaledEntryRememberStamp(0, c, 1f, false),
            scaledEntryRememberStamp(0, c, 1f, true),
        )
    }
}
