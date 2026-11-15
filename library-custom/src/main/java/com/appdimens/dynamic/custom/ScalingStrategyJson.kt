/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-dynamic.git
 *
 * Library: AppDimens — Custom scaling strategies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.custom

import com.appdimens.dynamic.common.Orientation

/**
 * EN Parsed strategies document. PT Documento de estratégias lido.
 */
class ScalingStrategyDocument internal constructor(
    val strategies: Map<String, ScalingStrategy>,
    val defaultId: String?,
)

/**
 * EN JSON codec for the cross-platform strategy format
 * (`appdimens-skills/schemas/custom-strategy.schema.json`). Self-contained (no `org.json`)
 * so it behaves identically in unit tests, KMP and on device.
 *
 * ```json
 * { "version": 1, "default": "brand",
 *   "strategies": [ { "id": "brand", "extends": "power",
 *                     "reference": { "width": 360, "height": 640 },
 *                     "curve": { "type": "power", "exponent": 0.8 },
 *                     "clamp": { "min": 0.85, "max": 1.6 },
 *                     "rules": [ { "when": { "device": ["tablet"] },
 *                                  "set": { "curve": { "type": "logarithmic", "k": 0.4 } } } ] } ] }
 * ```
 *
 * PT Codec JSON do formato multiplataforma. Autocontido (sem `org.json`).
 */
object ScalingStrategyJson {
    const val FORMAT_VERSION = 1

    /**
     * EN Decodes a document (`{"strategies": [...]}`), a single strategy object, or an array.
     * `extends` may name another strategy of the same document, a registered strategy,
     * a built-in (`scaled`, `power`, …) or a preset (`tabletFriendly`, …).
     * PT Decodifica um documento, uma estratégia única ou um array.
     */
    @JvmStatic
    fun decode(json: String): ScalingStrategyDocument {
        val root = MiniJson(json).parse()
        val (items, defaultId) = when (root) {
            is Map<*, *> -> if (root.containsKey("strategies")) {
                val version = (root["version"] as? Number)?.toInt() ?: FORMAT_VERSION
                require(version <= FORMAT_VERSION) { "Unsupported strategies format version $version" }
                list(root["strategies"], "strategies") to (root["default"] as? String)
            } else {
                listOf(root) to null
            }
            is List<*> -> root to null
            else -> throw IllegalArgumentException("Expected a JSON object or array")
        }
        val raw = LinkedHashMap<String, Map<*, *>>()
        items.forEachIndexed { i, item ->
            val obj = item as? Map<*, *> ?: throw IllegalArgumentException("strategies[$i] must be an object")
            val id = obj["id"] as? String ?: throw IllegalArgumentException("strategies[$i].id is required")
            require(raw.put(id, obj) == null) { "Duplicate strategy id '$id'" }
        }
        val built = LinkedHashMap<String, ScalingStrategy>()
        fun build(id: String, stack: List<String>): ScalingStrategy {
            built[id]?.let { return it }
            require(id !in stack) { "Cyclic 'extends': ${(stack + id).joinToString(" → ")}" }
            val obj = raw.getValue(id)
            val parentId = obj["extends"] as? String
            val parent = when {
                parentId == null -> ScalingConfig()
                raw.containsKey(parentId) -> build(parentId, stack + id).config
                else -> (ScalingStrategies[parentId]
                    ?: throw IllegalArgumentException("'$id' extends unknown strategy '$parentId'")).config
            }
            val strategy = ScalingStrategy(id, readConfig(obj, parent, id))
            built[id] = strategy
            return strategy
        }
        raw.keys.forEach { build(it, emptyList()) }
        return ScalingStrategyDocument(built, defaultId)
    }

    /** EN Decodes and returns the single (or first) strategy. */
    @JvmStatic
    fun decodeStrategy(json: String): ScalingStrategy =
        decode(json).strategies.values.firstOrNull() ?: throw IllegalArgumentException("No strategy in JSON")

    /** EN Encodes one strategy. Lambda curves/transforms cannot be encoded. */
    @JvmStatic
    fun encode(strategy: ScalingStrategy): String = buildString { writeStrategy(strategy.id, strategy.config) }

    /** EN Encodes a full document. PT Codifica um documento completo. */
    @JvmStatic
    fun encodeDocument(strategies: Collection<ScalingStrategy>, defaultId: String? = null): String = buildString {
        append("{\"version\":").append(FORMAT_VERSION)
        if (defaultId != null) append(",\"default\":").append(quote(defaultId))
        append(",\"strategies\":[")
        strategies.forEachIndexed { i, s ->
            if (i > 0) append(',')
            writeStrategy(s.id, s.config)
        }
        append("]}")
    }

    // ── decoding ────────────────────────────────────────────────────────────

    private fun readConfig(obj: Map<*, *>, parent: ScalingConfig, path: String): ScalingConfig {
        val patch = readPatch(obj, path)
        var cfg = parent.apply(patch)
        // Explicit nulls/false at the root reset inherited values.
        if (obj.containsKey("clamp") && obj["clamp"] == null) cfg = cfg.copy(minFactor = null, maxFactor = null)
        if (obj.containsKey("valueClamp") && obj["valueClamp"] == null) cfg = cfg.copy(minValue = null, maxValue = null)
        if (obj.containsKey("formula") && obj["formula"] == null) cfg = cfg.copy(transform = null)
        val rules = (obj["rules"]?.let { list(it, "$path.rules") } ?: emptyList<Any?>()).mapIndexed { i, r ->
            val rule = r as? Map<*, *> ?: throw IllegalArgumentException("$path.rules[$i] must be an object")
            ScalingRule(
                condition = readCondition(obj(rule["when"], "$path.rules[$i].when") ?: emptyMap<String, Any>(), "$path.rules[$i].when"),
                patch = readPatch(obj(rule["set"], "$path.rules[$i].set") ?: throw IllegalArgumentException("$path.rules[$i].set is required"), "$path.rules[$i].set"),
                name = rule["name"] as? String,
            )
        }
        return cfg.copy(rules = parent.rules + rules)
    }

    private fun readPatch(obj: Map<*, *>, path: String): ScalingPatch {
        val clamp = obj(obj["clamp"], "$path.clamp")
        val valueClamp = obj(obj["valueClamp"], "$path.valueClamp")
        val ar = obj["aspectRatio"]
        return ScalingPatch(
            reference = (obj["reference"] as? Map<*, *>)?.let {
                ReferenceScreen(num(it["width"], "$path.reference.width") ?: 300f, num(it["height"], "$path.reference.height") ?: 533f)
            },
            axis = (obj["axis"] as? String)?.let(ScalingAxis::fromJson),
            curve = obj["curve"]?.let { readCurve(it, "$path.curve") },
            multiplier = num(obj["multiplier"], "$path.multiplier"),
            minFactor = clamp?.let { num(it["min"], "$path.clamp.min") },
            maxFactor = clamp?.let { num(it["max"], "$path.clamp.max") },
            minValue = valueClamp?.let { num(it["min"], "$path.valueClamp.min") },
            maxValue = valueClamp?.let { num(it["max"], "$path.valueClamp.max") },
            aspectRatio = when (ar) {
                null -> null
                is Boolean -> ar
                is Map<*, *> -> bool(ar["enabled"], "$path.aspectRatio.enabled") ?: true
                else -> throw IllegalArgumentException("$path.aspectRatio must be a boolean or object")
            },
            sensitivity = (ar as? Map<*, *>)?.let { num(it["sensitivity"], "$path.aspectRatio.sensitivity") }
                ?: num(obj["sensitivity"], "$path.sensitivity"),
            fontScale = obj["fontScale"]?.let { readFontScale(it, "$path.fontScale") },
            ignoreMultiWindow = bool(obj["ignoreMultiWindow"], "$path.ignoreMultiWindow"),
            transform = when (val f = obj["formula"]) {
                null -> null
                is String -> wrap("$path.formula") { FormulaTransform(f) }
                else -> throw IllegalArgumentException("$path.formula must be a string")
            },
        )
    }

    private inline fun <T> wrap(path: String, block: () -> T): T = try {
        block()
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("$path: ${e.message}", e)
    }

    private fun readCurve(value: Any, path: String): ScalingCurve {
        if (value is String) return readCurve(mapOf("type" to value), path)
        val obj = value as? Map<*, *> ?: throw IllegalArgumentException("$path must be an object or string")
        return when (val type = obj["type"] as? String ?: throw IllegalArgumentException("$path.type is required")) {
            "linear" -> ScalingCurve.Linear
            "power" -> ScalingCurve.Power(num(obj["exponent"], "$path.exponent") ?: 0.75f)
            "logarithmic" -> ScalingCurve.Logarithmic(num(obj["k"], "$path.k") ?: 0.4f)
            "interpolated" -> ScalingCurve.Interpolated(num(obj["t"], "$path.t") ?: 0.5f)
            "constant" -> ScalingCurve.Constant(num(obj["factor"], "$path.factor") ?: 1f)
            "expression" -> {
                val src = obj["expression"] as? String ?: throw IllegalArgumentException("$path.expression is required")
                wrap("$path.expression") { ScalingCurve.Expression(src) }
            }
            "breakpoints" -> ScalingCurve.Breakpoints(list(obj["points"], "$path.points").mapIndexed { i, p ->
                val point = p as? Map<*, *> ?: throw IllegalArgumentException("$path.points[$i] must be an object")
                Breakpoint(
                    num(point["at"], "$path.points[$i].at") ?: throw IllegalArgumentException("$path.points[$i].at is required"),
                    num(point["factor"], "$path.points[$i].factor") ?: throw IllegalArgumentException("$path.points[$i].factor is required"),
                )
            })
            else -> throw IllegalArgumentException("$path.type '$type' is not one of linear, power, logarithmic, interpolated, constant, breakpoints, expression")
        }
    }

    private fun readFontScale(value: Any, path: String): FontScalePolicy = when {
        value == "follow" -> FontScalePolicy.Follow
        value == "ignore" -> FontScalePolicy.Ignore
        value is Map<*, *> -> FontScalePolicy.Clamp(num(value["max"], "$path.max") ?: throw IllegalArgumentException("$path.max is required"))
        else -> throw IllegalArgumentException("$path must be \"follow\", \"ignore\" or {\"max\": n}")
    }

    private fun readCondition(obj: Map<*, *>, path: String): ScreenCondition {
        fun range(key: String): Pair<Float?, Float?> {
            val raw = obj[key] ?: return null to null
            val r = raw as? Map<*, *> ?: throw IllegalArgumentException("$path.$key must be an object with min/max")
            return num(r["min"], "$path.$key.min") to num(r["max"], "$path.$key.max")
        }
        val devices = when (val d = obj["device"]) {
            null -> null
            is String -> setOf(DeviceClass.fromJson(d))
            is List<*> -> d.map { DeviceClass.fromJson(it as? String ?: throw IllegalArgumentException("$path.device entries must be strings")) }.toSet()
            else -> throw IllegalArgumentException("$path.device must be a string or array")
        }
        val orientation = when (val o = obj["orientation"] as? String) {
            null -> null
            "portrait" -> Orientation.PORTRAIT
            "landscape" -> Orientation.LANDSCAPE
            else -> throw IllegalArgumentException("$path.orientation '$o' must be portrait or landscape")
        }
        val sw = range("smallestWidth")
        val w = range("width")
        val h = range("height")
        val ar = range("aspectRatio")
        return ScreenCondition(
            devices, orientation, sw.first, sw.second, w.first, w.second, h.first, h.second,
            ar.first, ar.second, bool(obj["multiWindow"], "$path.multiWindow"),
        )
    }

    /** EN Present values must be objects; `null`/absent → null. */
    private fun obj(v: Any?, path: String): Map<*, *>? = when (v) {
        null -> null
        is Map<*, *> -> v
        else -> throw IllegalArgumentException("$path must be an object")
    }

    /** EN Present values must be booleans; `null`/absent → null. */
    private fun bool(v: Any?, path: String): Boolean? = when (v) {
        null -> null
        is Boolean -> v
        else -> throw IllegalArgumentException("$path must be a boolean")
    }

    private fun num(v: Any?, path: String): Float? = when (v) {
        null -> null
        is Number -> v.toFloat().also { require(it.isFinite()) { "$path must be finite" } }
        else -> throw IllegalArgumentException("$path must be a number")
    }

    private fun list(v: Any?, path: String): List<*> =
        v as? List<*> ?: throw IllegalArgumentException("$path must be an array")

    // ── encoding ────────────────────────────────────────────────────────────

    private fun StringBuilder.writeStrategy(id: String, c: ScalingConfig) {
        append("{\"id\":").append(quote(id))
        writePatchFields(c.toPatch(), defaults = true)
        if (c.rules.isNotEmpty()) {
            append(",\"rules\":[")
            c.rules.forEachIndexed { i, rule ->
                if (i > 0) append(',')
                append('{')
                rule.name?.let { append("\"name\":").append(quote(it)).append(',') }
                append("\"when\":")
                writeCondition(rule.condition)
                append(",\"set\":{")
                val start = length
                writePatchFields(rule.patch, defaults = false)
                if (length > start && this[start] == ',') deleteCharAt(start)
                append("}}")
            }
            append(']')
        }
        append('}')
    }

    private fun ScalingConfig.toPatch() = ScalingPatch(
        reference, axis, curve, multiplier, minFactor, maxFactor, minValue, maxValue,
        aspectRatio, sensitivity, fontScale, ignoreMultiWindow, transform,
    )

    private fun StringBuilder.writePatchFields(p: ScalingPatch, defaults: Boolean) {
        check(p.transform == null || p.transform is FormulaTransform) { "ValueTransform lambdas cannot be encoded to JSON" }
        p.reference?.let { if (!defaults || it != ReferenceScreen.DEFAULT) append(",\"reference\":{\"width\":").append(n(it.width)).append(",\"height\":").append(n(it.height)).append('}') }
        p.axis?.let { if (!defaults || it != ScalingAxis.SMALLEST_WIDTH) append(",\"axis\":").append(quote(it.jsonName)) }
        p.curve?.let { append(",\"curve\":"); writeCurve(it) }
        p.multiplier?.let { if (!defaults || it != 1f) append(",\"multiplier\":").append(n(it)) }
        if (p.minFactor != null || p.maxFactor != null) append(",\"clamp\":").append(range(p.minFactor, p.maxFactor))
        if (p.minValue != null || p.maxValue != null) append(",\"valueClamp\":").append(range(p.minValue, p.maxValue))
        if (p.aspectRatio != null && (!defaults || p.aspectRatio || p.sensitivity != null)) {
            append(",\"aspectRatio\":{\"enabled\":").append(p.aspectRatio)
            p.sensitivity?.let { append(",\"sensitivity\":").append(n(it)) }
            append('}')
        } else if (p.aspectRatio == null && p.sensitivity != null) {
            append(",\"sensitivity\":").append(n(p.sensitivity))   // EN sensitivity-only patch
        }
        p.fontScale?.let {
            if (!defaults || it != FontScalePolicy.Follow) append(",\"fontScale\":").append(
                when (it) {
                    FontScalePolicy.Follow -> "\"follow\""
                    FontScalePolicy.Ignore -> "\"ignore\""
                    is FontScalePolicy.Clamp -> "{\"max\":${n(it.max)}}"
                },
            )
        }
        p.ignoreMultiWindow?.let { if (!defaults || it) append(",\"ignoreMultiWindow\":").append(it) }
        (p.transform as? FormulaTransform)?.let { append(",\"formula\":").append(quote(it.expression.source)) }
    }

    private fun StringBuilder.writeCurve(c: ScalingCurve) {
        when (c) {
            ScalingCurve.Linear -> append("{\"type\":\"linear\"}")
            is ScalingCurve.Power -> append("{\"type\":\"power\",\"exponent\":").append(n(c.exponent)).append('}')
            is ScalingCurve.Logarithmic -> append("{\"type\":\"logarithmic\",\"k\":").append(n(c.k)).append('}')
            is ScalingCurve.Interpolated -> append("{\"type\":\"interpolated\",\"t\":").append(n(c.t)).append('}')
            is ScalingCurve.Constant -> append("{\"type\":\"constant\",\"factor\":").append(n(c.factor)).append('}')
            is ScalingCurve.Breakpoints -> append("{\"type\":\"breakpoints\",\"points\":[")
                .append(c.points.joinToString(",") { "{\"at\":${n(it.at)},\"factor\":${n(it.factor)}}" }).append("]}")
            is ScalingCurve.Expression -> append("{\"type\":\"expression\",\"expression\":").append(quote(c.expression.source)).append('}')
            is ScalingCurve.Custom -> throw IllegalStateException("Custom curve lambdas cannot be encoded to JSON")
        }
    }

    private fun StringBuilder.writeCondition(c: ScreenCondition) {
        val parts = ArrayList<String>()
        c.devices?.let { d -> parts += "\"device\":[" + d.sortedBy { it.ordinal }.joinToString(",") { quote(it.jsonName) } + "]" }
        when (c.orientation) {
            Orientation.PORTRAIT -> parts += "\"orientation\":\"portrait\""
            Orientation.LANDSCAPE -> parts += "\"orientation\":\"landscape\""
            else -> Unit
        }
        if (c.minSmallestWidth != null || c.maxSmallestWidth != null) parts += "\"smallestWidth\":" + range(c.minSmallestWidth, c.maxSmallestWidth)
        if (c.minWidth != null || c.maxWidth != null) parts += "\"width\":" + range(c.minWidth, c.maxWidth)
        if (c.minHeight != null || c.maxHeight != null) parts += "\"height\":" + range(c.minHeight, c.maxHeight)
        if (c.minAspectRatio != null || c.maxAspectRatio != null) parts += "\"aspectRatio\":" + range(c.minAspectRatio, c.maxAspectRatio)
        c.multiWindow?.let { parts += "\"multiWindow\":$it" }
        append('{').append(parts.joinToString(",")).append('}')
    }

    private fun range(min: Float?, max: Float?): String =
        listOfNotNull(min?.let { "\"min\":${n(it)}" }, max?.let { "\"max\":${n(it)}" }).joinToString(",", "{", "}")

    private fun n(v: Float): String {
        val s = v.toString()
        return if (s.endsWith(".0")) s.dropLast(2) else s
    }

    private fun quote(s: String): String = buildString {
        append('"')
        for (ch in s) when (ch) {
            '"' -> append("\\\"")
            '\\' -> append("\\\\")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> if (ch < ' ') append("\\u%04x".format(ch.code)) else append(ch)
        }
        append('"')
    }
}

/**
 * EN Minimal RFC 8259 parser → `Map` / `List` / `String` / `Double` / `Boolean` / `null`.
 * PT Parser mínimo RFC 8259.
 */
internal class MiniJson(private val src: String) {
    private var i = 0

    fun parse(): Any? {
        val v = value()
        ws()
        if (i != src.length) fail("Unexpected trailing content")
        return v
    }

    private fun value(): Any? {
        ws()
        if (i >= src.length) fail("Unexpected end of JSON")
        return when (val c = src[i]) {
            '{' -> obj()
            '[' -> arr()
            '"' -> str()
            't' -> lit("true", true)
            'f' -> lit("false", false)
            'n' -> lit("null", null)
            else -> if (c == '-' || c.isDigit()) num() else fail("Unexpected character '$c'")
        }
    }

    private fun obj(): Map<String, Any?> {
        val out = LinkedHashMap<String, Any?>()
        i++
        ws()
        if (peek() == '}') { i++; return out }
        while (true) {
            ws()
            if (peek() != '"') fail("Expected a string key")
            val k = str()
            ws()
            expect(':')
            out[k] = value()
            ws()
            when (peek()) {
                ',' -> i++
                '}' -> { i++; return out }
                else -> fail("Expected ',' or '}'")
            }
        }
    }

    private fun arr(): List<Any?> {
        val out = ArrayList<Any?>()
        i++
        ws()
        if (peek() == ']') { i++; return out }
        while (true) {
            out += value()
            ws()
            when (peek()) {
                ',' -> i++
                ']' -> { i++; return out }
                else -> fail("Expected ',' or ']'")
            }
        }
    }

    private fun str(): String {
        i++
        val sb = StringBuilder()
        while (true) {
            if (i >= src.length) fail("Unterminated string")
            when (val c = src[i++]) {
                '"' -> return sb.toString()
                '\\' -> {
                    if (i >= src.length) fail("Unterminated escape")
                    when (val e = src[i++]) {
                        '"', '\\', '/' -> sb.append(e)
                        'b' -> sb.append('\b')
                        'f' -> sb.append('\u000C')
                        'n' -> sb.append('\n')
                        'r' -> sb.append('\r')
                        't' -> sb.append('\t')
                        'u' -> {
                            if (i + 4 > src.length) fail("Bad unicode escape")
                            sb.append(src.substring(i, i + 4).toInt(16).toChar())
                            i += 4
                        }
                        else -> fail("Bad escape '\\$e'")
                    }
                }
                else -> sb.append(c)
            }
        }
    }

    private fun num(): Double {
        val start = i
        if (peek() == '-') i++
        while (i < src.length && (src[i].isDigit() || src[i] in ".eE+-")) i++
        return src.substring(start, i).toDoubleOrNull() ?: fail("Bad number '${src.substring(start, i)}'")
    }

    private fun lit(word: String, v: Any?): Any? {
        if (!src.startsWith(word, i)) fail("Expected '$word'")
        i += word.length
        return v
    }

    private fun ws() {
        while (i < src.length && src[i].isWhitespace()) i++
    }

    private fun peek(): Char = if (i < src.length) src[i] else '\u0000'

    private fun expect(c: Char) {
        if (peek() != c) fail("Expected '$c'")
        i++
    }

    private fun fail(msg: String): Nothing = throw IllegalArgumentException("Invalid JSON at $i: $msg")
}
