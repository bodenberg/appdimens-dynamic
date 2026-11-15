"""Builds a JVM-only harness from the VERBATIM appdimens-dynamic kernels and writes parity-vectors.json.

    python3 tools/parity/generate.py          # needs JDK 17+ and Gradle (no Android SDK)

Every other AppDimens port (iOS, Flutter, RN, Web, .NET, KMP, Qt, GTK, LVGL, Python, Games)
can compare its factors with parity-vectors.json: 12 strategies × 11 axis variants × 4 flag
combos × 20 screens + custom aspect-ratio sensitivity. Only android.* types are stubbed;
the math is compiled from library sources.
"""
import json
import os
import re
import shutil
import subprocess
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(HERE))
BUILD = os.path.join(HERE, "build")
SRC = os.path.join(BUILD, "src", "main", "kotlin")
CAP = {s: s.capitalize() for s in ["auto", "percent", "power", "fluid", "interpolated", "logarithmic",
                                   "diagonal", "perimeter", "fit", "fill", "density"]}


def extract(path, name):
    s = open(path, encoding="utf8").read()
    i = s.index("internal fun %s(" % name)
    j = s.index("{", s.index(")", s.index("): Float", i)))
    depth = 0
    for k in range(j, len(s)):
        depth += s[k] == "{"
        depth -= s[k] == "}"
        if s[k] == "}" and depth == 0:
            return s[i:k + 1]
    raise ValueError(name)


def main():
    shutil.rmtree(BUILD, ignore_errors=True)
    os.makedirs(os.path.join(SRC, "dyn"))
    os.makedirs(os.path.join(SRC, "harness"))
    shutil.copytree(os.path.join(HERE, "stubs", "android"), os.path.join(SRC, "android"))
    core = os.path.join(ROOT, "library/src/main/java/com/appdimens/dynamic")
    for f in ["DesignScaleConstants", "DimenMetrics", "DimenCalculationPlumbing", "AspectRatioLookup"]:
        shutil.copy(os.path.join(core, "core", f + ".kt"), os.path.join(SRC, "dyn"))
    for f in ["DpQualifier", "Inverter"]:
        shutil.copy(os.path.join(core, "common", f + ".kt"), os.path.join(SRC, "dyn"))
    for s in ["power", "interpolated", "perimeter", "diagonal", "logarithmic"]:
        d = os.path.join(ROOT, "library-%s/src/main/java/com/appdimens/dynamic/%s" % (s, s))
        for f in os.listdir(d):
            if f.endswith("Factors.kt"):
                shutil.copy(os.path.join(d, f), os.path.join(SRC, "dyn"))
    dc = open(os.path.join(core, "core", "DimenCache.kt"), encoding="utf8").read()
    consts = [l for l in dc.splitlines() if re.match(r"\s*const val (INV_BASE_RATIO|ADJUSTMENT_SCALE|SENSITIVITY_DEFAULT)\b", l)]
    i = dc.index("fun calculateRawScaling(")
    raw = dc[i:dc.index("\n    }\n", i) + 6]
    open(os.path.join(SRC, "dyn", "DimenCacheStub.kt"), "w").write(
        "package com.appdimens.dynamic.core\n// Constants and calculateRawScaling copied verbatim from DimenCache.kt.\nobject DimenCache {\n"
        + "\n".join(consts) + "\n    @Volatile var currentMetrics: DimenMetrics = DimenMetrics.DEFAULT\n"
        "    val currentLogNormalizedAr get() = currentMetrics.logNormalizedAspectRatio\n"
        "    val currentAspectRatioMul get() = currentMetrics.defaultAspectRatioMultiplier\n    " + raw + "\n}\n")
    kernels = {"calculateScaledDpCompose": "library/src/main/java/com/appdimens/dynamic/compose/scaled/DimenSdp.kt"}
    for s, c in CAP.items():
        kernels["calculate%sDpCompose" % c] = "library-%s/src/main/java/com/appdimens/dynamic/compose/%s/Dimen%sDp.kt" % (s, s, c)
    head = ["package com.appdimens.dynamic.harness", "import android.content.res.Configuration",
            "import com.appdimens.dynamic.common.DpQualifier", "import com.appdimens.dynamic.common.Inverter",
            "import com.appdimens.dynamic.core.*"] + ["import com.appdimens.dynamic.%s.%sFactors" % (s, s.capitalize())
                                                    for s in ["power", "interpolated", "perimeter", "diagonal", "logarithmic"]]
    body = [extract(os.path.join(ROOT, p), n) for n, p in kernels.items()]
    open(os.path.join(SRC, "harness", "Kernels.kt"), "w").write("\n".join(head) + "\n\n" + "\n\n".join(body) + "\n")
    g = json.load(open(os.path.join(HERE, "screens.json")))
    cases = ",\n        ".join("C(%d, %d, %d, %rf, %s)" % (c["w"], c["h"], round(c["density"] * 160), float(c["fontScale"]),
                                                          "true" if c["multiWindow"] else "false") for c in g["cases"])
    variants = ",\n        ".join("DpQualifier.%s to Inverter.%s" % (v["qualifier"], v["inverter"]) for v in g["variants"])
    names = ["scaled"] + list(CAP)
    klist = ", ".join('"%s" to ::calculate%sDpCompose' % (n, "Scaled" if n == "scaled" else CAP[n]) for n in names)
    open(os.path.join(SRC, "harness", "Main.kt"), "w").write(MAIN % (cases, variants, klist))
    open(os.path.join(BUILD, "settings.gradle.kts"), "w").write('rootProject.name = "appdimens-parity"\n')
    open(os.path.join(BUILD, "build.gradle.kts"), "w").write(
        'plugins { kotlin("jvm") version "2.0.21"; application }\nrepositories { mavenCentral() }\n'
        'application { mainClass.set("com.appdimens.dynamic.harness.MainKt") }\n')
    out = subprocess.run(["gradle", "-q", "--no-daemon", "run"], cwd=BUILD, capture_output=True, text=True)
    if out.returncode:
        sys.stderr.write(out.stderr)
        sys.exit(out.returncode)
    data = json.loads(out.stdout.strip().splitlines()[-1])
    data["strategies"] = names
    data["variants"] = g["variants"]
    data["screens"] = [{k: c[k] for k in ("w", "h", "density", "fontScale", "multiWindow")} for c in g["cases"]]
    json.dump(data, open(os.path.join(HERE, "parity-vectors.json"), "w"))
    print("wrote tools/parity/parity-vectors.json")


MAIN = '''package com.appdimens.dynamic.harness
import android.app.Activity
import android.content.res.Configuration
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenMetrics

data class C(val w: Int, val h: Int, val dpi: Int, val fs: Float, val mw: Boolean)
typealias K = (Float, Configuration, DpQualifier, Inverter, Boolean, Boolean, Float?, android.content.Context?) -> Float

fun main() {
    val cases = listOf(
        %s)
    val variants = listOf(
        %s)
    val kernels: List<Pair<String, K>> = listOf(%s)
    val sb = StringBuilder("{\\"source\\":\\"appdimens-dynamic kernels (verbatim, JVM)\\",\\"cases\\":[")
    cases.forEachIndexed { ci, c ->
        val cfg = Configuration().apply {
            screenWidthDp = c.w; screenHeightDp = c.h; smallestScreenWidthDp = minOf(c.w, c.h); densityDpi = c.dpi; fontScale = c.fs
            orientation = if (c.w > c.h) Configuration.ORIENTATION_LANDSCAPE else Configuration.ORIENTATION_PORTRAIT
        }
        DimenCache.currentMetrics = DimenMetrics.from(cfg, c.mw)
        val act = Activity(c.mw)
        if (ci > 0) sb.append(',')
        sb.append("{\\"w\\":${c.w},\\"h\\":${c.h},\\"factors\\":{")
        kernels.forEachIndexed { ki, (name, k) ->
            if (ki > 0) sb.append(',')
            sb.append("\\"$name\\":[")
            var first = true
            for ((q, inv) in variants) for (f in 0 until 4) {
                if (!first) sb.append(','); first = false
                sb.append(k(1f, cfg, q, inv, (f and 2) != 0, (f and 1) != 0, null, act).toString())
            }
            sb.append(']')
        }
        val ks = calculateScaledDpCompose(1f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, true, 0.01f, act)
        val ka = calculateAutoDpCompose(1f, cfg, DpQualifier.SMALL_WIDTH, Inverter.DEFAULT, false, true, 0.01f, act)
        sb.append("},\\"k001\\":{\\"scaled\\":$ks,\\"auto\\":$ka}}")
    }
    sb.append("]}")
    println(sb)
}
'''

if __name__ == "__main__":
    main()
