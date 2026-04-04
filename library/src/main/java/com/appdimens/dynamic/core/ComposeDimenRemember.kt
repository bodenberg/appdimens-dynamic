/**
 * Shared Compose remember + [DimenCache] wiring (no scaling formulas).
 */
package com.appdimens.dynamic.core

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun rememberDimenDp(
    cacheKey: Long,
    layoutStamp: Long,
    androidContext: Context,
    compute: () -> Float,
): Dp = remember(cacheKey, layoutStamp) {
    DimenCache.getOrPut(cacheKey, androidContext, compute).dp
}

@Composable
internal fun rememberDimenPxFromDp(
    cacheKey: Long,
    pxStamp: Long,
    androidContext: Context,
    density: Density,
    compute: () -> Float,
): Float = remember(cacheKey, pxStamp) {
    DimenCache.getOrPut(cacheKey, androidContext) {
        val scaledDp = compute()
        density.run { scaledDp.dp.toPx() }
    }
}
