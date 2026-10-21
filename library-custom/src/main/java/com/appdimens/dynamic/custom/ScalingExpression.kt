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

import kotlin.math.E
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

/**
 * EN Free-form formula written as text, so it can live in JSON and behaves the same on every
 * platform. Booleans are numbers (true = 1, false = 0; non-zero is true).
 *
 * Operators: `+ - * / % ^` (`^` is power, right-associative; `%` keeps the sign of the dividend),
 * `== != < <= > >=`, `&& || !`, `cond ? a : b`, parentheses.
 * Functions: `min(a, b, …)`, `max(a, b, …)`, `clamp(x, lo, hi)`, `pow(x, y)`, `sqrt`, `ln`, `log`
 * (natural), `log10`, `log2`, `exp`, `abs`, `sign`, `floor`, `ceil`, `round` (half up),
 * `lerp(a, b, t)`, `step(edge, x)`, `smoothstep(e0, e1, x)`.
 * Screen variables: [SCREEN_VARIABLES]. Curve variables add `ratio`, `axis`, `ref`, `refWidth`,
 * `refHeight`; value formulas add `base`, `scaled` and `factor` as well.
 *
 * PT Fórmula livre escrita como texto — vive no JSON e se comporta igual em todas as plataformas.
 */
class ScalingExpression private constructor(val source: String, private val root: Node) {

    /** EN Evaluates with [vars] (a name → value lookup). PT Avalia com [vars]. */
    fun evaluate(vars: (String) -> Double): Double = root.eval(vars)

    /**
     * EN Non-null when the whole formula is `base`, `base * E` or `E * base` and `E` reads no
     * value variable (`base`, `scaled`, `factor`). Such a formula is a pure screen factor, so
     * strategies evaluate `E` once per window and skip the per-value cache (same result, bit for bit).
     * PT Não nulo quando a fórmula é `base`, `base * E` ou `E * base` e `E` não lê variáveis de
     * valor: vira um fator só da tela, calculado uma vez por janela (mesmo resultado).
     */
    internal val baseCoefficient: ScalingExpression? by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val r = root
        val coefficient = when {
            r is Var && r.name == "base" -> Num(1.0)
            r is Binary && r.op == "*" && r.a.isBase() && !r.b.reads(VALUE_VARIABLES) -> r.b
            r is Binary && r.op == "*" && r.b.isBase() && !r.a.reads(VALUE_VARIABLES) -> r.a
            else -> null
        }
        coefficient?.let { ScalingExpression(source, it) }
    }

    override fun equals(other: Any?): Boolean = other is ScalingExpression && other.source == source
    override fun hashCode(): Int = source.hashCode()
    override fun toString(): String = source

    companion object {
        /** EN Variables read from the window. PT Variáveis lidas da janela. */
        @JvmField
        val SCREEN_VARIABLES: Set<String> = setOf(
            "sw", "width", "height", "shortSide", "longSide", "diagonal", "perimeter", "aspectRatio",
            "density", "fontScale", "landscape", "portrait", "multiWindow",
            "phone", "tablet", "desktop", "tv", "watch", "car", "vr", "pi", "e",
        )

        /** EN Variables of a curve expression. PT Variáveis de uma curva. */
        @JvmField
        val CURVE_VARIABLES: Set<String> = SCREEN_VARIABLES + setOf("ratio", "axis", "ref", "refWidth", "refHeight")

        /** EN Variables of a value formula. PT Variáveis de uma fórmula por valor. */
        @JvmField
        val FORMULA_VARIABLES: Set<String> = CURVE_VARIABLES + setOf("base", "scaled", "factor")

        /**
         * EN Parses [source]; every identifier must be in [allowed]. Errors carry the position.
         * PT Analisa [source]; todo identificador precisa estar em [allowed].
         */
        @JvmStatic
        fun parse(source: String, allowed: Set<String> = FORMULA_VARIABLES): ScalingExpression =
            ScalingExpression(source, Parser(source, allowed).parseAll())

        /**
         * EN Variable lookup for [screen] plus the strategy context. Unknown names return NaN
         * (parse-time validation makes that unreachable).
         */
        internal fun variables(
            screen: ScreenSnapshot, ratio: Float, axisDp: Float, refDp: Float, reference: ReferenceScreen,
            base: Float = Float.NaN, scaled: Float = Float.NaN, factor: Float = Float.NaN,
        ): (String) -> Double = { name ->
            when (name) {
                "sw" -> screen.smallestWidthDp.toDouble()
                "width" -> screen.widthDp.toDouble()
                "height" -> screen.heightDp.toDouble()
                "shortSide" -> screen.minDp.toDouble()
                "longSide" -> screen.maxDp.toDouble()
                "diagonal" -> screen.read(ScalingAxis.DIAGONAL).toDouble()
                "perimeter" -> screen.read(ScalingAxis.PERIMETER).toDouble()
                "aspectRatio" -> screen.aspectRatio.toDouble()
                "density" -> screen.density.toDouble()
                "fontScale" -> screen.fontScale.toDouble()
                "landscape" -> if (screen.isLandscape) 1.0 else 0.0
                "portrait" -> if (screen.isLandscape) 0.0 else 1.0
                "multiWindow" -> if (screen.isMultiWindow) 1.0 else 0.0
                "phone" -> flag(screen, DeviceClass.PHONE)
                "tablet" -> flag(screen, DeviceClass.TABLET)
                "desktop" -> flag(screen, DeviceClass.DESKTOP)
                "tv" -> flag(screen, DeviceClass.TV)
                "watch" -> flag(screen, DeviceClass.WATCH)
                "car" -> flag(screen, DeviceClass.CAR)
                "vr" -> flag(screen, DeviceClass.VR)
                "pi" -> PI
                "e" -> E
                "ratio" -> ratio.toDouble()
                "axis" -> axisDp.toDouble()
                "ref" -> refDp.toDouble()
                "refWidth" -> reference.width.toDouble()
                "refHeight" -> reference.height.toDouble()
                "base" -> base.toDouble()
                "scaled" -> scaled.toDouble()
                "factor" -> factor.toDouble()
                else -> Double.NaN
            }
        }

        private fun flag(screen: ScreenSnapshot, d: DeviceClass) = if (screen.device == d) 1.0 else 0.0

        private val VALUE_VARIABLES = setOf("base", "scaled", "factor")

        private fun Node.isBase() = this is Var && name == "base"
    }

    // ── AST ─────────────────────────────────────────────────────────────────

    private sealed class Node {
        abstract fun eval(v: (String) -> Double): Double

        /** EN True when this subtree reads any of [names]. */
        abstract fun reads(names: Set<String>): Boolean
    }

    private class Num(val value: Double) : Node() {
        override fun eval(v: (String) -> Double) = value
        override fun reads(names: Set<String>) = false
    }

    private class Var(val name: String) : Node() {
        override fun eval(v: (String) -> Double) = v(name)
        override fun reads(names: Set<String>) = name in names
    }

    private class Unary(val op: Char, val a: Node) : Node() {
        override fun reads(names: Set<String>) = a.reads(names)

        override fun eval(v: (String) -> Double): Double {
            val x = a.eval(v)
            return when (op) {
                '-' -> -x
                '!' -> if (x != 0.0) 0.0 else 1.0
                else -> x
            }
        }
    }

    private class Binary(val op: String, val a: Node, val b: Node) : Node() {
        override fun reads(names: Set<String>) = a.reads(names) || b.reads(names)

        override fun eval(v: (String) -> Double): Double {
            when (op) {
                "&&" -> return if (a.eval(v) != 0.0 && b.eval(v) != 0.0) 1.0 else 0.0
                "||" -> return if (a.eval(v) != 0.0 || b.eval(v) != 0.0) 1.0 else 0.0
            }
            val x = a.eval(v)
            val y = b.eval(v)
            return when (op) {
                "+" -> x + y
                "-" -> x - y
                "*" -> x * y
                "/" -> x / y
                "%" -> x % y
                "^" -> x.pow(y)
                "==" -> b(x == y)
                "!=" -> b(x != y)
                "<" -> b(x < y)
                "<=" -> b(x <= y)
                ">" -> b(x > y)
                else -> b(x >= y)
            }
        }

        private fun b(c: Boolean) = if (c) 1.0 else 0.0
    }

    private class Ternary(val c: Node, val a: Node, val b: Node) : Node() {
        override fun eval(v: (String) -> Double) = if (c.eval(v) != 0.0) a.eval(v) else b.eval(v)
        override fun reads(names: Set<String>) = c.reads(names) || a.reads(names) || b.reads(names)
    }

    private class Call(val name: String, val args: List<Node>) : Node() {
        override fun reads(names: Set<String>) = args.any { it.reads(names) }

        override fun eval(v: (String) -> Double): Double {
            val x = DoubleArray(args.size) { args[it].eval(v) }
            return when (name) {
                "min" -> x.min()
                "max" -> x.max()
                "clamp" -> maxOf(x[1], minOf(x[2], x[0]))
                "pow" -> x[0].pow(x[1])
                "sqrt" -> sqrt(x[0])
                "ln", "log" -> ln(x[0])
                "log10" -> log10(x[0])
                "log2" -> log2(x[0])
                "exp" -> exp(x[0])
                "abs" -> abs(x[0])
                "sign" -> sign(x[0])
                "floor" -> floor(x[0])
                "ceil" -> ceil(x[0])
                "round" -> floor(x[0] + 0.5)
                "lerp" -> x[0] + (x[1] - x[0]) * x[2]
                "step" -> if (x[1] < x[0]) 0.0 else 1.0
                else -> { // smoothstep
                    val t = ((x[2] - x[0]) / (x[1] - x[0])).coerceIn(0.0, 1.0)
                    t * t * (3 - 2 * t)
                }
            }
        }
    }

    // ── Parser (recursive descent) ──────────────────────────────────────────

    private class Parser(private val src: String, private val allowed: Set<String>) {
        private var i = 0

        fun parseAll(): Node {
            if (src.isBlank()) fail("empty expression")
            val n = ternary()
            ws()
            if (i < src.length) fail("unexpected '${src[i]}'")
            return n
        }

        private fun ternary(): Node {
            val c = or()
            if (!eat("?")) return c
            val a = ternary()
            if (!eat(":")) fail("expected ':'")
            return Ternary(c, a, ternary())
        }

        private fun or(): Node {
            var n = and()
            while (eat("||")) n = Binary("||", n, and())
            return n
        }

        private fun and(): Node {
            var n = cmp()
            while (eat("&&")) n = Binary("&&", n, cmp())
            return n
        }

        private fun cmp(): Node {
            val n = add()
            for (op in listOf("==", "!=", "<=", ">=", "<", ">")) if (eat(op)) return Binary(op, n, add())
            return n
        }

        private fun add(): Node {
            var n = mul()
            while (true) n = when {
                eat("+") -> Binary("+", n, mul())
                eat("-") -> Binary("-", n, mul())
                else -> return n
            }
        }

        private fun mul(): Node {
            var n = unary()
            while (true) n = when {
                eat("*") -> Binary("*", n, unary())
                eat("/") -> Binary("/", n, unary())
                eat("%") -> Binary("%", n, unary())
                else -> return n
            }
        }

        private fun unary(): Node = when {
            eat("-") -> Unary('-', unary())
            eat("+") -> unary()
            peekIs("!") && !peekIs("!=") -> { eat("!"); Unary('!', unary()) }
            else -> power()
        }

        private fun power(): Node {
            val n = primary()
            return if (eat("^")) Binary("^", n, unary()) else n
        }

        private fun primary(): Node {
            ws()
            if (i >= src.length) fail("unexpected end")
            val c = src[i]
            if (c == '(') {
                i++
                val n = ternary()
                if (!eat(")")) fail("expected ')'")
                return n
            }
            if (c.isDigit() || c == '.') return number()
            if (c.isLetter() || c == '_') {
                val start = i
                while (i < src.length && (src[i].isLetterOrDigit() || src[i] == '_')) i++
                val name = src.substring(start, i)
                if (eat("(")) return call(name, start)
                if (name == "true") return Num(1.0)
                if (name == "false") return Num(0.0)
                if (name !in allowed) {
                    i = start
                    fail("unknown variable '$name' (allowed: ${allowed.sorted().joinToString(", ")})")
                }
                return Var(name)
            }
            fail("unexpected '$c'")
        }

        private fun call(name: String, start: Int): Node {
            val args = ArrayList<Node>()
            if (!eat(")")) {
                do args += ternary() while (eat(","))
                if (!eat(")")) fail("expected ')'")
            }
            val arity = ARITY[name] ?: run { i = start; fail("unknown function '$name'") }
            if (args.size < arity.first || args.size > arity.last) {
                i = start
                fail("'$name' takes ${if (arity.first == arity.last) "${arity.first}" else "${arity.first}+"} argument(s), got ${args.size}")
            }
            return Call(name, args)
        }

        private fun number(): Node {
            val start = i
            while (i < src.length && (src[i].isDigit() || src[i] == '.')) i++
            if (i < src.length && (src[i] == 'e' || src[i] == 'E')) {
                val save = i
                i++
                if (i < src.length && (src[i] == '+' || src[i] == '-')) i++
                if (i < src.length && src[i].isDigit()) { while (i < src.length && src[i].isDigit()) i++ } else i = save
            }
            val text = src.substring(start, i)
            return Num(text.toDoubleOrNull() ?: run { i = start; fail("bad number '$text'") })
        }

        private fun ws() {
            while (i < src.length && src[i].isWhitespace()) i++
        }

        private fun peekIs(token: String): Boolean {
            ws()
            return src.startsWith(token, i)
        }

        private fun eat(token: String): Boolean {
            if (!peekIs(token)) return false
            i += token.length
            return true
        }

        private fun fail(msg: String): Nothing =
            throw IllegalArgumentException("Invalid expression at $i: $msg in \"$src\"")

        companion object {
            private const val MANY = Int.MAX_VALUE
            val ARITY: Map<String, IntRange> = mapOf(
                "min" to 1..MANY, "max" to 1..MANY, "clamp" to 3..3, "pow" to 2..2, "sqrt" to 1..1,
                "ln" to 1..1, "log" to 1..1, "log10" to 1..1, "log2" to 1..1, "exp" to 1..1,
                "abs" to 1..1, "sign" to 1..1, "floor" to 1..1, "ceil" to 1..1, "round" to 1..1,
                "lerp" to 3..3, "step" to 2..2, "smoothstep" to 3..3,
            )
        }
    }
}

/**
 * EN Per-value formula written as text (`"formula": "max(scaled, base)"`); serializable to JSON.
 * Inside a strategy it sees every curve variable plus `base`, `scaled` (after the factor and the
 * value clamps) and `factor`.
 * PT Fórmula por valor escrita como texto; serializável em JSON.
 */
class FormulaTransform(val expression: ScalingExpression) : ValueTransform {
    constructor(source: String) : this(ScalingExpression.parse(source, ScalingExpression.FORMULA_VARIABLES))

    /** EN Standalone use (outside a strategy): curve-context variables read as NaN. */
    override fun transform(base: Float, scaled: Float, screen: ScreenSnapshot): Float =
        expression.evaluate(
            ScalingExpression.variables(
                screen, Float.NaN, Float.NaN, Float.NaN, ReferenceScreen.DEFAULT, base, scaled,
                if (base != 0f) scaled / base else Float.NaN,
            ),
        ).toFloat()

    /** EN True when the formula is `base × (screen-only E)`: no per-value cache needed. */
    val isProportional: Boolean get() = expression.baseCoefficient != null

    override fun equals(other: Any?): Boolean = other is FormulaTransform && other.expression == expression
    override fun hashCode(): Int = expression.hashCode()
    override fun toString(): String = "Formula(${expression.source})"
}
