package com.appdimens.dynamic.custom

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ScalingStrategyJsonTest {

    private val phone = ScreenSnapshot(widthDp = 411f, heightDp = 891f, smallestWidthDp = 411f)
    private val tablet = ScreenSnapshot(widthDp = 1280f, heightDp = 800f, smallestWidthDp = 800f)

    @After
    fun tearDown() = ScalingStrategies.resetForTest()

    private val doc = """
        {
          "${'$'}schema": "https://github.com/bodenberg/appdimens-skills/schemas/custom-strategy.schema.json",
          "version": 1,
          "default": "brand",
          "strategies": [
            {
              "id": "brand",
              "extends": "power",
              "reference": { "width": 360, "height": 640 },
              "curve": { "type": "power", "exponent": 0.8 },
              "clamp": { "min": 0.85, "max": 1.6 },
              "fontScale": { "max": 1.3 },
              "rules": [
                { "name": "tablet", "when": { "device": ["tablet", "desktop"] },
                  "set": { "curve": { "type": "logarithmic", "k": 0.4 } } },
                { "when": { "orientation": "landscape", "smallestWidth": { "min": 600 } },
                  "set": { "multiplier": 0.9 } }
              ]
            },
            { "id": "brandText", "extends": "brand", "valueClamp": { "min": 10, "max": 32 } },
            { "id": "steps", "axis": "width",
              "curve": { "type": "breakpoints", "points": [ { "at": 360, "factor": 1 }, { "at": 840, "factor": 1.5 } ] } }
          ]
        }
    """.trimIndent()

    @Test
    fun decode_buildsStrategiesAndInheritance() {
        val parsed = ScalingStrategyJson.decode(doc)
        assertEquals(listOf("brand", "brandText", "steps"), parsed.strategies.keys.toList())
        assertEquals("brand", parsed.defaultId)

        val brand = parsed.strategies.getValue("brand")
        assertEquals(Math.pow((411f / 360f).toDouble(), 0.8).toFloat(), brand.factor(phone), 1e-6f)
        val tabletFactor = (1f + 0.4f * kotlin.math.ln(800f / 360f)) * 0.9f
        assertEquals(tabletFactor, brand.factor(tablet), 1e-5f)
        assertEquals(FontScalePolicy.Clamp(1.3f), brand.config.fontScale)

        val text = parsed.strategies.getValue("brandText")
        assertEquals(2, text.config.rules.size)
        assertTrue(text.isValueDependent)
        assertEquals(32f, text.scale(100f, phone), 0f)

        val steps = parsed.strategies.getValue("steps")
        assertEquals(1f + 0.5f * (411f - 360f) / 480f, steps.factor(phone), 1e-6f)
    }

    @Test
    fun loadJson_registersAndSetsDefault() {
        val loaded = ScalingStrategies.loadJson(doc)
        assertSame(loaded["brand"], ScalingStrategies.default)
        assertSame(loaded["steps"], ScalingStrategies["steps"])
    }

    @Test
    fun encode_roundTrips() {
        val parsed = ScalingStrategyJson.decode(doc).strategies
        for (s in parsed.values) {
            val again = ScalingStrategyJson.decodeStrategy(s.toJson())
            assertEquals(s.id, again.id)
            assertEquals(s.config, again.config)
            assertEquals(s.factor(tablet), again.factor(tablet), 0f)
        }
        val all = ScalingStrategyJson.decode(ScalingStrategyJson.encodeDocument(parsed.values, "brand"))
        assertEquals(parsed.keys, all.strategies.keys)
    }

    @Test
    fun presets_roundTripThroughJson() {
        for (preset in CustomPresets.all + BuiltInStrategies.all) {
            val again = ScalingStrategyJson.decodeStrategy(preset.toJson())
            assertEquals(preset.id, preset.config, again.config)
        }
    }

    @Test
    fun singleObjectAndArrayForms() {
        assertEquals("one", ScalingStrategyJson.decodeStrategy("""{"id":"one","curve":"linear"}""").id)
        assertEquals(2, ScalingStrategyJson.decode("""[{"id":"a"},{"id":"b","extends":"a"}]""").strategies.size)
    }

    @Test
    fun aspectRatioForms() {
        val a = ScalingStrategyJson.decodeStrategy("""{"id":"a","aspectRatio":true}""")
        assertTrue(a.config.aspectRatio)
        val b = ScalingStrategyJson.decodeStrategy("""{"id":"b","aspectRatio":{"sensitivity":0.004}}""")
        assertTrue(b.config.aspectRatio)
        assertEquals(0.004f, b.config.sensitivity!!, 0f)
    }

    @Test
    fun errors_areDescriptive() {
        expectError("Cyclic") { ScalingStrategyJson.decode("""[{"id":"a","extends":"b"},{"id":"b","extends":"a"}]""") }
        expectError("unknown strategy 'nope'") { ScalingStrategyJson.decode("""{"id":"a","extends":"nope"}""") }
        expectError("Duplicate") { ScalingStrategyJson.decode("""[{"id":"a"},{"id":"a"}]""") }
        expectError("type 'wave'") { ScalingStrategyJson.decode("""{"id":"a","curve":{"type":"wave"}}""") }
        expectError("Unknown device") { ScalingStrategyJson.decode("""{"id":"a","rules":[{"when":{"device":"fridge"},"set":{}}]}""") }
        expectError("must be a number") { ScalingStrategyJson.decode("""{"id":"a","multiplier":"x"}""") }
        expectError("Invalid JSON") { ScalingStrategyJson.decode("""{"id":"a",}""") }
        expectError("version") { ScalingStrategyJson.decode("""{"version":2,"strategies":[]}""") }
    }

    @Test
    fun lambdas_cannotBeEncoded() {
        val s = scalingStrategy("l", register = false) { curve = custom { r, _ -> r } }
        try {
            s.toJson()
            fail()
        } catch (e: IllegalStateException) {
            assertTrue(e.message!!.contains("lambda"))
        }
    }

    private fun expectError(fragment: String, block: () -> Unit) {
        try {
            block()
            fail("expected error containing '$fragment'")
        } catch (e: IllegalArgumentException) {
            assertTrue("'${e.message}' should contain '$fragment'", e.message!!.contains(fragment))
        }
    }
}
