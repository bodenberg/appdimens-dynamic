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
        DimenCache.isInitializedFast = false
        DimenCache.isInitialized.set(false)
        DimenCache.isInitializing.set(false)
    }

    @After
    fun teardown() {
        DimenCache.persistenceWritesEnabled = false
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
    fun clearAll_withContext_requestsDiskClearFlag() {
        val app = mockAppContext(config())
        DimenCache.diskClearRequested = false
        DimenCache.clearAll(app)
        assertTrue(DimenCache.diskClearRequested)
    }

    @Test
    fun invalidate_doesNotRequestDiskClear() {
        val app = mockAppContext(config())
        DimenCache.init(app)
        DimenCache.diskClearRequested = false
        DimenCache.invalidateOnConfigChange(config(sw = 300, w = 300, h = 800))
        assertFalse(
            "snapshot partitions removed DataStore coupling from invalidate",
            DimenCache.diskClearRequested
        )
    }

    @Test
    fun invalidateBeforeInit_doesNotThrow_andSkipsDiskClear() {
        assertNull(DimenCache.savedAppContext)
        DimenCache.diskClearRequested = false
        DimenCache.invalidateOnConfigChange(config(sw = 500, w = 500, h = 900))
        assertFalse(DimenCache.diskClearRequested)
    }
}
