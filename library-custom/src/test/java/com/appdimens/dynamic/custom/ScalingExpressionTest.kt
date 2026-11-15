package com.appdimens.dynamic.custom

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import kotlin.math.pow

class ScalingExpressionTest {

    private val phone = ScreenSnapshot(widthDp = 411f, heightDp = 891f, smallestWidthDp = 411f, density = 2.625f)
    private val tablet = ScreenSnapshot(widthDp = 1280f, heightDp = 800f, smallestWidthDp = 800f, density = 2f)

    @After
    fun tearDown() = ScalingStrategies.resetForTest()

    private fun eval(src: String, screen: ScreenSnapshot = phone): Double =
        ScalingExpression.parse(src).evaluate(ScalingExpression.variables(screen, 1.5f, 411f, 300f, ReferenceScreen.DEFAULT, 16f, 20f, 1.25f))

    @Test
    fun operators_precedenceAndAssociativity() {
        assertEquals(7.0, eval("1 + 2 * 3"), 0.0)
        assertEquals(9.0, eval("(1 + 2) * 3"), 0.0)
        assertEquals(2.0.pow(9.0), eval("2 ^ 3 ^ 2"), 0.0)   // right-associative
        assertEquals(-4.0, eval("-2 ^ 2"), 0.0)               // unary binds looser than ^
        assertEquals(-1.0, eval("-7 % 3"), 0.0)               // sign of the dividend
        assertEquals(1.0, eval("1 < 2 && 2 <= 2 && !(3 == 4) && 3 != 4"), 0.0)
        assertEquals(1.0, eval("0 || 5"), 0.0)
        assertEquals(3.0, eval("0 ? 2 : 1 ? 3 : 4"), 0.0)
        assertEquals(1.0, eval("true && !false"), 0.0)
        assertEquals(1500.0, eval("1.5e3"), 0.0)
    }

    @Test
    fun functions() {
        assertEquals(1.0, eval("min(3, 1, 2)"), 0.0)
        assertEquals(3.0, eval("max(3, 1, 2)"), 0.0)
        assertEquals(2.0, eval("clamp(5, 0, 2)"), 0.0)
        assertEquals(3.0, eval("round(2.5)"), 0.0)
        assertEquals(-2.0, eval("round(-2.5)"), 0.0)
        assertEquals(0.5, eval("smoothstep(0, 10, 5)"), 1e-12)
        assertEquals(1.0, eval("step(1, 1)"), 0.0)
        assertEquals(15.0, eval("lerp(10, 20, 0.5)"), 0.0)
        assertEquals(3.0, eval("log2(8) * log10(10) * ln(e)"), 1e-12)
    }

    @Test
    fun variables_comeFromScreenAndContext() {
        assertEquals(411.0, eval("sw"), 0.0)
        assertEquals(1.0, eval("phone && portrait && !landscape && !tablet"), 0.0)
        assertEquals(1.0, eval("tablet && landscape", tablet), 0.0)
        assertEquals(36.0, eval("base + scaled"), 0.0)
        assertEquals(1.5, eval("ratio"), 0.0)
    }

    @Test
    fun parseErrors_areDescriptive() {
        fun msg(src: String, allowed: Set<String> = ScalingExpression.FORMULA_VARIABLES) = try {
            ScalingExpression.parse(src, allowed); ""
        } catch (e: IllegalArgumentException) { e.message ?: "" }
        assertTrue(msg("sw +").contains("unexpected end"))
        assertTrue(msg("foo * 2").contains("unknown variable 'foo'"))
        assertTrue(msg("base", ScalingExpression.CURVE_VARIABLES).contains("unknown variable 'base'"))
        assertTrue(msg("wave(1)").contains("unknown function 'wave'"))
        assertTrue(msg("clamp(1, 2)").contains("'clamp' takes 3"))
        assertTrue(msg("(1 + 2").contains("expected ')'"))
        assertTrue(msg("1 ? 2").contains("expected ':'"))
        assertTrue(msg("").contains("empty"))
    }

    @Test
    fun curveExpression_isFactorOnlyAndMatchesBuiltIn() {
        val s = scalingStrategy("expr", register = false) { curve = expression("pow(ratio, 0.75)") }
        assertTrue(!s.isValueDependent)
        assertEquals(BuiltInStrategies.POWER.resolve(tablet).factor, s.resolve(tablet).factor, 1e-6f)
    }

    @Test
    fun formula_replacesTheValueFreely() {
        val s = scalingStrategy("free", register = false) { formula("base * pow(sw / 360, 0.7) + 2") }
        assertTrue(s.isValueDependent)
        assertEquals(16.0 * (800.0 / 360).pow(0.7) + 2, s.resolve(tablet).scale(16f).toDouble(), 1e-4)
    }

    @Test
    fun nonFiniteFactor_isRejected() {
        val s = scalingStrategy("bad", register = false) { curve = expression("1 / (sw - sw)") }
        try {
            s.resolve(phone); fail()
        } catch (_: IllegalArgumentException) {
        }
    }

    @Test
    fun json_roundTripAndErrors() {
        val loaded = ScalingStrategies.loadJson(
            """[{"id":"a","curve":{"type":"expression","expression":"ratio * (tablet ? 0.9 : 1)"},"formula":"max(scaled, 12)"},
               {"id":"b","extends":"a","formula":null}]""",
        )
        val a = loaded.getValue("a")
        val back = ScalingStrategyJson.decodeStrategy(a.toJson())
        assertEquals(a.resolve(tablet).scale(4f), back.resolve(tablet).scale(4f), 1e-6f)
        assertEquals(12f, a.resolve(phone).scale(1f), 0f)
        assertTrue(!loaded.getValue("b").isValueDependent)
        try {
            ScalingStrategyJson.decode("""{"id":"x","curve":{"type":"expression","expression":"base"}}""")
            fail()
        } catch (e: IllegalArgumentException) {
            assertTrue(e.message!!, e.message!!.contains("x.curve.expression") && e.message!!.contains("unknown variable 'base'"))
        }
    }
}
