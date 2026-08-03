package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DimenCacheDiskClearOnInvalidateTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
        DimenCache.persistenceWritesEnabled = false
        DimenCache.diskClearRequested = false
        DimenCache.savedAppContext = null
        // Reset init flags so init() can run again for this test process.
        DimenCache.isInitializedFast = false
        DimenCache.isInitialized.set(false)
        DimenCache.isInitializing.set(false)
    }

    @After
    fun teardown() {
        DimenCache.persistenceWritesEnabled = true
        DimenCache.diskClearRequested = false
        DimenCache.savedAppContext = null
        DimenCache.shutdown()
    }

    private fun config(sw: Int = 400, w: Int = 400, h: Int = 800, dpi: Int = 420): Configuration =
        Configuration().apply {
            smallestScreenWidthDp = sw
            screenWidthDp = w
            screenHeightDp = h
            densityDpi = dpi
            fontScale = 1f
        }

    private fun mockAppContext(configuration: Configuration): Context {
        val resources = mock<Resources>()
        whenever(resources.configuration).thenReturn(configuration)
        val app = mock<Context>()
        whenever(app.resources).thenReturn(resources)
        whenever(app.applicationContext).thenReturn(app)
        return app
    }

    @Test
    fun invalidateAfterInit_requestsDiskClearOnPhysicalChange() {
        val cfg = config()
        val app = mockAppContext(cfg)
        DimenCache.init(app)
        assertTrue(DimenCache.savedAppContext === app)

        DimenCache.diskClearRequested = false
        // Seed lastConfiguration via a matching invalidate first.
        DimenCache.invalidateOnConfigChange(cfg)
        DimenCache.diskClearRequested = false

        val resized = config(sw = 300, w = 300, h = 800)
        DimenCache.invalidateOnConfigChange(resized)

        assertTrue(
            "physical change after init must clear DataStore via savedAppContext",
            DimenCache.diskClearRequested
        )
    }

    @Test
    fun invalidateBeforeInit_doesNotThrow_andSkipsDiskClear() {
        assertNull(DimenCache.savedAppContext)
        DimenCache.diskClearRequested = false

        // Signature unchanged; null savedAppContext → memory-only clearAll(null).
        DimenCache.invalidateOnConfigChange(config(sw = 500, w = 500, h = 900))

        assertFalse(
            "without init, disk clear must not be requested",
            DimenCache.diskClearRequested
        )
    }
}
