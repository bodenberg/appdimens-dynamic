package com.appdimens.dynamic.custom

import com.appdimens.dynamic.common.Orientation
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import kotlin.math.ln
import kotlin.math.sqrt

/**
 * Pure-engine checks (no Android types): curves, rules, clamps, font scale, registry.
 * Built-in parity uses the exact formulas of `DimenMetrics` (power / logarithmic /
 * interpolated / diagonal / perimeter / AR multiplier).
 */
class ScalingEngineTest {

    private val phone = ScreenSnapshot(widthDp = 411f, heightDp = 891f, smallestWidthDp = 411f, density = 2.625f)
    private val tablet = ScreenSnapshot(widthDp = 1280f, heightDp = 800f, smallestWidthDp = 800f, density = 2f)
    private val small = ScreenSnapshot(widthDp = 240f, heightDp = 320f)

    @After
    fun tearDown() = ScalingStrategies.resetForTest()

    // ── Built-in parity ─────────────────────────────────────────────────────

    @Test
    fun builtIns_matchBuiltInModuleFormulas() {
        for (s in listOf(phone, tablet, small)) {
            val sw = s.smallestWidthDp
            assertEquals(sw / 300f, BuiltInStrategies.SCALED.factor(s), 1e-6f)
            assertEquals(Math.pow((sw / 300f).toDouble(), 0.75).toFloat(), BuiltInStrategies.POWER.factor(s), 1e-6f)
            val log = when {
                sw > 300f -> 1f + 0.4f * ln(sw * (1f / 300f))
                else -> 1f - 0.4f * ln(300f / sw)
            }
            assertEquals(log, BuiltInStrategies.LOGARITHMIC.factor(s), 1e-5f)
            assertEquals(1f + (sw * (1f / 300f) - 1f) * 0.5f, BuiltInStrategies.INTERPOLATED.factor(s), 1e-6f)
            val diag = sqrt(s.minDp * s.minDp + s.maxDp * s.maxDp) / 611.6305f
            assertEquals(diag, BuiltInStrategies.DIAGONAL.factor(s), 1e-5f)
            assertEquals((s.minDp + s.maxDp) / 833f, BuiltInStrategies.PERIMETER.factor(s), 1e-6f)
            assertEquals(1f, BuiltInStrategies.FIXED.factor(s), 0f)
        }
    }

    @Test
    fun aspectRatio_matchesSatelliteMultiplier() {
        val strategy = scalingStrategy("ar", register = false) { curve = power(0.75f); aspectRatio() }
        val normalized = (phone.maxDp / phone.minDp) / 1.78f
        val arMul = 1f + (0.08f / 30f) * ln(normalized.toDouble()).toFloat()
        val expected = Math.pow((411f / 300f).toDouble(), 0.75).toFloat() * arMul
        assertEquals(expected, strategy.factor(phone), 1e-6f)
    }

    // ── Curves ──────────────────────────────────────────────────────────────

    @Test
    fun breakpoints_interpolateOverAbsoluteAxis() {
        val s = scalingStrategy("bp", register = false) { curve = breakpoints(320 to 1f, 600 to 1.5f, 840 to 1.7f) }
        assertEquals(1f, s.factor(small), 1e-6f)                                   // below first
        assertEquals(1f + 0.5f * (411f - 320f) / 280f, s.factor(phone), 1e-6f)    // between
        assertEquals(1.7f, s.factor(ScreenSnapshot(1000f, 1000f)), 1e-6f)          // above last
    }

    @Test
    fun customLambda_receivesRatioAndScreen() {
        val s = scalingStrategy("lambda", register = false) {
            curve = custom { ratio, screen -> if (screen.isLandscape) 1f else ratio * 2f }
        }
        assertEquals(411f / 300f * 2f, s.factor(phone), 1e-6f)
        assertEquals(1f, s.factor(tablet), 1e-6f)
    }

    @Test
    fun axesAndReference() {
        val s = scalingStrategy("h", register = false) { axis = ScalingAxis.HEIGHT; reference(360, 800) }
        assertEquals(891f / 800f, s.factor(phone), 1e-6f)
        // withAxis keeps the reference frame: the width axis reads the reference width (360).
        assertEquals(phone.widthDp / 360f, s.withAxis(ScalingAxis.WIDTH).factor(phone), 1e-6f)
    }

    // ── Rules ───────────────────────────────────────────────────────────────

    @Test
    fun rules_applyInOrder_laterWins() {
        val s = scalingStrategy("rules", register = false) {
            curve = linear()
            on(DeviceClass.TABLET, name = "tablet") { curve = power(0.5f); multiply(0.9f) }
            landscape { multiply(0.8f) }
        }
        assertEquals(411f / 300f, s.factor(phone), 1e-6f)
        val expectedTablet = Math.pow((800f / 300f).toDouble(), 0.5).toFloat() * 0.8f
        assertEquals(expectedTablet, s.factor(tablet), 1e-6f)
        assertEquals(listOf("tablet", "landscape"), s.resolve(tablet).appliedRules)
    }

    @Test
    fun rules_rangesAreMinInclusiveMaxExclusive() {
        val s = scalingStrategy("ranges", register = false) {
            onScreen(minSmallestWidth = 400f, maxSmallestWidth = 411f) { curve = constant(2f) }
        }
        assertEquals(411f / 300f, s.factor(phone), 1e-6f) // 411 is excluded
        assertEquals(2f, s.factor(ScreenSnapshot(400f, 900f)), 0f)
    }

    @Test
    fun condition_matchesEveryField() {
        val c = ScreenCondition(
            devices = setOf(DeviceClass.PHONE), orientation = Orientation.PORTRAIT,
            minWidth = 400f, maxHeight = 900f, minAspectRatio = 2f, multiWindow = false,
        )
        assertTrue(c.matches(phone))
        assertFalse(c.matches(phone.copy(isMultiWindow = true)))
        assertFalse(c.matches(tablet))
        assertTrue(ScreenCondition().matches(tablet))
    }

    @Test
    fun ignoreMultiWindow_returnsUnitFactor() {
        val s = scalingStrategy("mw", register = false) { ignoreMultiWindow = true }
        assertEquals(1f, s.factor(phone.copy(isMultiWindow = true)), 0f)
        assertEquals(411f / 300f, s.factor(phone), 1e-6f)
    }

    // ── Clamps ──────────────────────────────────────────────────────────────

    @Test
    fun factorClamp_andCrossedBoundsUseMax() {
        val s = scalingStrategy("clamp", register = false) {
            clamp(min = 0.9f, max = 1.2f)
        }
        assertEquals(1.2f, s.factor(phone), 0f)
        assertEquals(0.9f, s.factor(small), 0f)

        // Each rule is valid on its own; combined at runtime they cross → the max bound wins.
        val crossed = scalingStrategy("crossed", register = false) {
            on(DeviceClass.TABLET) { clamp(min = 2f) }
            landscape { clamp(max = 1.2f) }
        }
        assertEquals(1.2f, crossed.factor(tablet), 0f)
    }

    @Test
    fun valueClamp_isMagnitudeBased_andKeepsZero() {
        val s = scalingStrategy("vclamp", register = false) { clampValue(min = 4f, max = 20f) }
        assertTrue(s.isValueDependent)
        assertEquals(20f, s.scale(100f, phone), 0f)
        assertEquals(4f, s.scale(1f, phone), 0f)
        assertEquals(-20f, s.scale(-100f, phone), 0f)
        assertEquals(0f, s.scale(0f, phone), 0f)
    }

    @Test
    fun transform_runsAfterClamp() {
        val s = scalingStrategy("round", register = false) {
            transform { _, scaled, _ -> Math.round(scaled).toFloat() }
        }
        assertEquals(Math.round(16f * 411f / 300f).toFloat(), s.scale(16f, phone), 0f)
    }

    // ── Font scale ──────────────────────────────────────────────────────────

    @Test
    fun fontScalePolicies() {
        val big = phone.copy(fontScale = 2f)
        val f = 411f / 300f
        val follow = scalingStrategy("f", register = false) {}
        val ignore = scalingStrategy("i", register = false) { ignoreFontScale() }
        val clamp = scalingStrategy("c", register = false) { clampFontScale(1.3f) }
        assertEquals(14f * f, follow.resolve(big).scaleSpUnits(14f), 1e-5f)
        assertEquals(14f * f * 2f, follow.resolve(big).scaleSpDp(14f), 1e-5f)
        assertEquals(14f * f / 2f, ignore.resolve(big).scaleSpUnits(14f), 1e-5f)
        assertEquals(14f * f, ignore.resolve(big).scaleSpDp(14f), 1e-5f)
        assertEquals(14f * f * 1.3f, clamp.resolve(big).scaleSpDp(14f), 1e-5f)
    }

    // ── Registry / memo ─────────────────────────────────────────────────────

    @Test
    fun registry_defaultAndLookup() {
        assertSame(BuiltInStrategies.SCALED, ScalingStrategies.default)
        val brand = scalingStrategy("brand") { curve = power(0.8f) }
        assertSame(brand, ScalingStrategies["brand"])
        assertSame(BuiltInStrategies.POWER, ScalingStrategies["power"])
        assertSame(CustomPresets.GAME_HUD, ScalingStrategies["gameHud"])
        ScalingStrategies.default = brand
        assertSame(brand, ScalingStrategies.default)
        try {
            ScalingStrategies.getOrThrow("missing")
            fail()
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!.contains("brand"))
        }
    }

    @Test
    fun cacheIds_areUnique() {
        val a = scalingStrategy("a", register = false) {}
        val b = scalingStrategy("b", register = false) {}
        assertTrue(a.cacheId != b.cacheId)
    }

    @Test
    fun memo_reusesResolutionForEqualToken() {
        val s = scalingStrategy("memo", register = false) {}
        val first = s.resolve(phone)
        assertSame(first, s.resolve(phone.copy()))
        assertNotSame(first, s.resolve(tablet))
    }

    @Test
    fun extend_keepsParentRules() {
        val child = CustomPresets.TABLET_FRIENDLY.extend("child", register = false) {
            on(DeviceClass.PHONE) { multiply(1.1f) }
        }
        assertEquals(2, child.config.rules.size)
        assertEquals(411f / 300f * 1.1f, child.factor(phone), 1e-6f)
        assertEquals(Math.pow((800f / 300f).toDouble(), 0.5).toFloat(), child.factor(tablet), 1e-6f)
    }

    @Test
    fun withAxis_isCachedPerId() {
        val s = scalingStrategy("axis") {}
        assertSame(s.withAxis(ScalingAxis.HEIGHT), s.withAxis(ScalingAxis.HEIGHT))
        assertSame(s, s.withAxis(ScalingAxis.SMALLEST_WIDTH))
        assertEquals("axis@height", s.withAxis(ScalingAxis.HEIGHT).id)
    }

    @Test
    fun explain_mentionsRulesAndFactor() {
        val text = CustomPresets.TABLET_FRIENDLY.explain(tablet)
        assertTrue(text, text.contains("[tabletFriendly]") && text.contains("rules=[large]") && text.contains("factor"))
    }

    @Test
    fun deviceClass_fromAndroidUiMode() {
        assertEquals(DeviceClass.PHONE, DeviceClass.fromAndroidUiMode(0x11, 411f))
        assertEquals(DeviceClass.TABLET, DeviceClass.fromAndroidUiMode(0x11, 600f))
        assertEquals(DeviceClass.DESKTOP, DeviceClass.fromAndroidUiMode(0x12, 411f))
        assertEquals(DeviceClass.CAR, DeviceClass.fromAndroidUiMode(0x13, 411f))
        assertEquals(DeviceClass.TV, DeviceClass.fromAndroidUiMode(0x14, 540f))
        assertEquals(DeviceClass.WATCH, DeviceClass.fromAndroidUiMode(0x16, 192f))
        assertEquals(DeviceClass.VR, DeviceClass.fromAndroidUiMode(0x17, 411f))
    }

    @Test
    fun invalidConfigs_areRejected() {
        expectIae { scalingStrategy(" ", register = false) {} }
        expectIae { scalingStrategy("x", register = false) { clamp(min = 2f, max = 1f) } }
        expectIae { scalingStrategy("x", register = false) { reference(width = 0) } }
        expectIae { scalingStrategy("x", register = false) { curve = power(Float.NaN) } }
        expectIae { scalingStrategy("x", register = false) { on(DeviceClass.TV) { clampValue(min = 5, max = 1) } } }
    }

    private fun expectIae(block: () -> Unit) {
        try {
            block()
            fail("expected IllegalArgumentException")
        } catch (_: IllegalArgumentException) {
        }
    }
}
