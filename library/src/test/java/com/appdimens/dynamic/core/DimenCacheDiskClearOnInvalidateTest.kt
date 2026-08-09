package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DimenCacheDiskClearOnInvalidateTest {

    @Before
    fun setup() {
        DimenCache.clearAll()
        DimenCache.isEnabled = true
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
    fun clearAll_clearsSnapshotPartitions() {
        val app = mockAppContext(config())
        DimenCache.init(app)
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key, app) { 99f }
        DimenCache.clearAll(app)
        assertNull(DimenCache.peek(key, app))
    }

    @Test
    fun invalidate_doesNotClearSnapshotPartitions() {
        val app = mockAppContext(config())
        DimenCache.init(app)
        val key = DimenCache.buildKey(
            42f, false, false, DimenCache.CalcType.FLUID,
            com.appdimens.dynamic.common.DpQualifier.SMALL_WIDTH,
            com.appdimens.dynamic.common.Inverter.DEFAULT, true, DimenCache.ValueType.DP
        )
        DimenCache.getOrPut(key, app) { 99f }
        DimenCache.invalidateOnConfigChange(config(sw = 300, w = 300, h = 800))
        assertEquals(
            "snapshot partitions must survive invalidate (compat hook only)",
            99f,
            DimenCache.peek(key, app)!!
        )
    }

    @Test
    fun invalidateBeforeInit_doesNotThrow() {
        DimenCache.invalidateOnConfigChange(config(sw = 500, w = 500, h = 900))
    }
}