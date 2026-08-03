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
}
