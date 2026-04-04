#!/usr/bin/env python3
"""One-off generator: strategy Dp/Extensions from scaled templates. Do not re-run blindly.

After changing Compose/Code scaled *Dp* templates, also run sibling script:
  python3 library/scripts/generate_ssp_strategy_modules.py
to regenerate Dimen*Sp* files for each strategy (from scaled `DimenSsp*` templates).
"""
import pathlib
import re

ROOT = pathlib.Path(__file__).resolve().parents[1] / "src/main/java/com/appdimens/dynamic"

def read(p):
    return (ROOT / p).read_text(encoding="utf-8")

def write(p, s):
    path = ROOT / p
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(s, encoding="utf-8")

# --- Compose DimenSdp.kt: extract extension block (lines with val Number.) and dynamic section ---
COMPOSE_SDP = read("compose/scaled/DimenSdp.kt")

def transform_compose_extensions(content: str, sp: str, hp: str, wp: str) -> str:
    c = content
    # method names
    c = c.replace("toDynamicScaledDp", f"toDynamic{sp[:1].upper()}{sp[1:]}Dp".replace("toDynamicp", "toDynamicP"))  # broken
    return c

# Simpler: manual replacements per strategy using regex on extension names only
def rename_number_extensions(text: str, s_pre: str, h_pre: str, w_pre: str) -> str:
    """s_pre e.g. 'ps' for psdp (insert before sdp core: ps+dp = psdp — actually psdp is p+sdp -> prefix p + sdp)"""
    # Number.sdp -> Number.psdp  means prefix 'p' before 'sdp' for percent
    def repl_sdp(m):
        return m.group(1) + s_pre + m.group(2)
    text = re.sub(r"(\bval Number\.)sdp([A-Za-z]*:)", repl_sdp, text)
    def repl_hdp(m):
        return m.group(1) + h_pre + m.group(2)
    text = re.sub(r"(\bval Number\.)hdp([A-Za-z]*:)", repl_hdp, text)
    def repl_wdp(m):
        return m.group(1) + w_pre + m.group(2)
    text = re.sub(r"(\bval Number\.)wdp([A-Za-z]*:)", repl_wdp, text)
    return text

STRATEGIES = [
    ("percent", "Percent", "PERCENT", "p", "p", "p"),
    ("power", "Power", "POWER", "pw", "pw", "pw"),
    ("auto", "Auto", "AUTO", "a", "a", "a"),
    ("logarithmic", "Logarithmic", "LOGARITHMIC", "log", "log", "log"),
    ("fluid", "Fluid", "FLUID", "f", "f", "f"),
    ("interpolated", "Interpolated", "INTERPOLATED", "i", "i", "i"),
    ("diagonal", "Diagonal", "DIAGONAL", "dg", "dg", "dg"),
    ("perimeter", "Perimeter", "PERIMETER", "pr", "pr", "pr"),
    ("fit", "Fit", "FIT", "ft", "ft", "ft"),
    ("fill", "Fill", "FILL", "fl", "fl", "fl"),
    ("density", "Density", "DENSITY", "d", "d", "d"),
]

def sdp_prefix(s: str) -> str:
    """prefix before sdp: p+ sdp -> psdp; pw+ sdp -> pwsdp"""
    return s + "s" if not s.endswith("s") else s  # p + sdp = psdp -> 'p' + 'sdp' = psdp
    # percent: p + sdp = psdp
    # power: pw + sdp = pwsdp

def make_s_prefix(folder):
    for f, _, _, sp, _, _ in STRATEGIES:
        if f == folder:
            return sp + "s"  # ps, pws, as, logs, fs, is, dgs, prs, fts, fls, ds
    return ""

# Correct mapping: 
# percent -> ps + dp = psdp  => s_h_prefix = "ps" for s variants, "ph" for h, "pw" for w
PREFIXES = {
    "percent": ("ps", "ph", "pw"),
    "power": ("pws", "pwh", "pww"),
    "auto": ("as", "ah", "aw"),
    "logarithmic": ("logs", "logh", "logw"),
    "fluid": ("fs", "fh", "fw"),
    "interpolated": ("is", "ih", "iw"),
    "diagonal": ("dgs", "dgh", "dgw"),
    "perimeter": ("prs", "prh", "prw"),
    "fit": ("fts", "fth", "ftw"),
    "fill": ("fls", "flh", "flw"),
    "density": ("ds", "dh", "dw"),
}

def transform_compose_dp_extensions(text: str, folder: str, cls: str, ct: str, ps: str, ph: str, pw: str) -> str:
    """DimenSdpExtensions.kt → Dimen{Cls}DpExtensions.kt (same package as Dimen{Cls}Dp)."""
    t = text.replace("package com.appdimens.dynamic.compose\n", f"package com.appdimens.dynamic.compose.{folder}\n", 1)
    t = t.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
    t = t.replace("rememberScaledPxFromDp", f"remember{cls}PxFromDp")
    t = t.replace("rememberScaledDp", f"remember{cls}Dp")
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = t.replace("toDynamicScaledDp", f"toDynamic{cls}Dp")
    for recv in ("Dp", "Int", "Number"):
        t = t.replace(f"{recv}.wdp", f"{recv}.{pw}dp")
        t = t.replace(f"{recv}.hdp", f"{recv}.{ph}dp")
        t = t.replace(f"{recv}.sdp", f"{recv}.{ps}dp")
    # Extension implicit labels (this@functionName) must match renamed functions
    t = t.replace("this@wdp", f"this@{pw}dp")
    t = t.replace("this@hdp", f"this@{ph}dp")
    t = t.replace("this@sdp", f"this@{ps}dp")
    return t


def transform_compose_scaled_class(text: str, folder: str, cls: str) -> str:
    """compose/scaled/DimenScaled.kt → compose/{folder}/Dimen{Cls}Scaled.kt"""
    t = text.replace("package com.appdimens.dynamic.compose\n", f"package com.appdimens.dynamic.compose.{folder}\n", 1)
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = t.replace("toDynamicScaledDp", f"toDynamic{cls}Dp")
    t = t.replace("DimenScaled", f"Dimen{cls}Scaled")
    t = t.replace("fun Dp.scaledDp():", f"fun Dp.{folder}Dp():")
    t = t.replace("this@scaledDp", f"this@{folder}Dp")
    t = t.replace("fun Number.scaledDp():", f"fun Number.{folder}Dp():")
    t = t.replace(".dp.scaledDp()", f".dp.{folder}Dp()")
    return t


def rename_ext(text: str, sp: str, sh: str, sw: str) -> str:
    """sp/sh/sw are prefixes before 'dp', e.g. ps+dp -> psdp."""
    text = re.sub(r"Number\.sdp", f"Number.{sp}dp", text)
    text = re.sub(r"Number\.hdp", f"Number.{sh}dp", text)
    text = re.sub(r"Number\.wdp", f"Number.{sw}dp", text)
    return text

def main():
    dyn_hdr = "// EN Dynamic scaling functions (pure-math approach)."
    tail_raw = COMPOSE_SDP.split(dyn_hdr, 1)[1]
    tail_raw = re.sub(
        r"(?s)\n/\*\*\s*\n \* EN\s*\n \* Shared pure-math scaling kernel.*?^internal fun calculateScaledDpCompose\(.*?\n\}\n",
        "\n",
        tail_raw,
        count=1,
        flags=re.MULTILINE,
    )
    tail_raw = tail_raw.replace("ComposeDimenRemember.rememberDimenDp", "rememberDimenDp")
    tail_raw = tail_raw.replace("ComposeDimenRemember.rememberDimenPxFromDp", "rememberDimenPxFromDp")
    tail = tail_raw.strip()
    tail = tail.replace("calculateScaledDpCompose", "CALC_PLACEHOLDER")
    tail = tail.replace("rememberScaledDp", "REMEMBER_DP_PLACEHOLDER")
    tail = tail.replace("rememberScaledPxFromDp", "REMEMBER_PX_PLACEHOLDER")
    tail = tail.replace("toDynamicScaledDp", "TO_DP_PLACEHOLDER")
    tail = tail.replace("toDynamicScaledPx", "TO_PX_PLACEHOLDER")
    tail = tail.replace("DimenCache.CalcType.SCALED", "CALC_TYPE_PLACEHOLDER")

    calc_bodies = {
        "percent": """internal fun calculatePercentDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    if (!applyAspectRatio) return baseValue * dim * DimenCache.INV_BASE_RATIO
    val diff = dim - DesignScaleConstants.BASE_WIDTH_DP
    val adj = (customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT) * DimenCache.currentLogNormalizedAr
    return baseValue * (1f + diff * (DimenCache.ADJUSTMENT_SCALE + adj))
}""",
        "power": """internal fun calculatePowerDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    val ratio = dim / DesignScaleConstants.BASE_WIDTH_DP
    var out = baseValue * ratio.toDouble().pow(0.75).toFloat()
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "auto": """internal fun calculateAutoDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    val inv = DimenCache.INV_BASE_RATIO
    val transition = 480f
    val sensitivity = 0.4f
    val scale = if (dim <= transition) {
        dim * inv
    } else {
        (transition * inv) + sensitivity * kotlin.math.ln(1f + (dim - transition) * inv)
    }
    var out = baseValue * scale
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "logarithmic": """internal fun calculateLogarithmicDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    val sens = 0.4f
    val inv = DimenCache.INV_BASE_RATIO
    val scale = if (dim > DesignScaleConstants.BASE_WIDTH_DP) {
        1f + sens * kotlin.math.ln(dim * inv)
    } else {
        1f - sens * kotlin.math.ln(DesignScaleConstants.BASE_WIDTH_DP / dim)
    }
    var out = baseValue * scale
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "fluid": """internal fun calculateFluidDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    val minV = baseValue * 0.8f
    val maxV = baseValue * 1.2f
    val minW = 320f
    val maxW = 768f
    val v = when {
        dim <= minW -> minV
        dim >= maxW -> maxV
        else -> minV + (maxV - minV) * (dim - minW) / (maxW - minW)
    }
    var out = v
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "interpolated": """internal fun calculateInterpolatedDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val q = DimenCalculationPlumbing.effectiveQualifier(qualifier, inverter, isLandscape, isPortrait)
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val dim = DimenCalculationPlumbing.readScreenDp(configuration, q)
    val linear = baseValue * dim * DimenCache.INV_BASE_RATIO
    var out = baseValue + (linear - baseValue) * 0.5f
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "diagonal": """internal fun calculateDiagonalDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val sm = DimenCalculationPlumbing.smallestSideDp(configuration)
    val lg = DimenCalculationPlumbing.largestSideDp(configuration)
    val diag = kotlin.math.sqrt((sm * sm + lg * lg).toDouble()).toFloat()
    var out = baseValue * (diag / DesignScaleConstants.BASE_DIAGONAL_DP)
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "perimeter": """internal fun calculatePerimeterDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val sm = DimenCalculationPlumbing.smallestSideDp(configuration)
    val lg = DimenCalculationPlumbing.largestSideDp(configuration)
    var out = baseValue * ((sm + lg) / DesignScaleConstants.BASE_PERIMETER_DP)
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "fit": """internal fun calculateFitDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val sm = DimenCalculationPlumbing.smallestSideDp(configuration)
    val lg = DimenCalculationPlumbing.largestSideDp(configuration)
    val rw = sm / DesignScaleConstants.BASE_WIDTH_DP
    val rh = lg / DesignScaleConstants.BASE_HEIGHT_DP
    var out = baseValue * minOf(rw, rh)
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "fill": """internal fun calculateFillDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val sm = DimenCalculationPlumbing.smallestSideDp(configuration)
    val lg = DimenCalculationPlumbing.largestSideDp(configuration)
    val rw = sm / DesignScaleConstants.BASE_WIDTH_DP
    val rh = lg / DesignScaleConstants.BASE_HEIGHT_DP
    var out = baseValue * maxOf(rw, rh)
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
        "density": """internal fun calculateDensityDpCompose(
    baseValue: Float,
    configuration: Configuration,
    qualifier: DpQualifier,
    inverter: Inverter,
    ignoreMultiWindows: Boolean,
    applyAspectRatio: Boolean,
    customSensitivityK: Float?
): Float {
    if (DimenCalculationPlumbing.isMultiWindowConstrained(configuration, ignoreMultiWindows)) return baseValue
    val densityScale = configuration.densityDpi / 160f
    var out = baseValue * densityScale
    if (applyAspectRatio) {
        out *= DimenCalculationPlumbing.aspectRatioMultiplier(
            configuration,
            customSensitivityK ?: DimenCache.SENSITIVITY_DEFAULT
        )
    }
    return out
}""",
    }

    names = {
        "percent": ("calculatePercentDpCompose", "rememberPercentDp", "rememberPercentPxFromDp", "toDynamicPercentDp", "toDynamicPercentPx"),
        "power": ("calculatePowerDpCompose", "rememberPowerDp", "rememberPowerPxFromDp", "toDynamicPowerDp", "toDynamicPowerPx"),
        "auto": ("calculateAutoDpCompose", "rememberAutoDp", "rememberAutoPxFromDp", "toDynamicAutoDp", "toDynamicAutoPx"),
        "logarithmic": ("calculateLogarithmicDpCompose", "rememberLogarithmicDp", "rememberLogarithmicPxFromDp", "toDynamicLogarithmicDp", "toDynamicLogarithmicPx"),
        "fluid": ("calculateFluidDpCompose", "rememberFluidDp", "rememberFluidPxFromDp", "toDynamicFluidDp", "toDynamicFluidPx"),
        "interpolated": ("calculateInterpolatedDpCompose", "rememberInterpolatedDp", "rememberInterpolatedPxFromDp", "toDynamicInterpolatedDp", "toDynamicInterpolatedPx"),
        "diagonal": ("calculateDiagonalDpCompose", "rememberDiagonalDp", "rememberDiagonalPxFromDp", "toDynamicDiagonalDp", "toDynamicDiagonalPx"),
        "perimeter": ("calculatePerimeterDpCompose", "rememberPerimeterDp", "rememberPerimeterPxFromDp", "toDynamicPerimeterDp", "toDynamicPerimeterPx"),
        "fit": ("calculateFitDpCompose", "rememberFitDp", "rememberFitPxFromDp", "toDynamicFitDp", "toDynamicFitPx"),
        "fill": ("calculateFillDpCompose", "rememberFillDp", "rememberFillPxFromDp", "toDynamicFillDp", "toDynamicFillPx"),
        "density": ("calculateDensityDpCompose", "rememberDensityDp", "rememberDensityPxFromDp", "toDynamicDensityDp", "toDynamicDensityPx"),
    }

    eb_hdr = "// EN Composable extensions for quick dynamic scaling."
    eb_suffix = COMPOSE_SDP.split(eb_hdr, 1)[1].split("// EN Dynamic scaling functions")[0].lstrip("\n")

    for folder, cls, ct, _, _, _ in STRATEGIES:
        sp, sh, sw = PREFIXES[folder]
        eb = eb_hdr + "\n" + eb_suffix
        eb = rename_ext(eb, sp, sh, sw)
        eb = eb.replace("toDynamicScaledDp", names[folder][3]).replace("toDynamicScaledPx", names[folder][4])

        t = tail.replace("CALC_PLACEHOLDER", names[folder][0])
        t = t.replace("REMEMBER_DP_PLACEHOLDER", names[folder][1])
        t = t.replace("REMEMBER_PX_PLACEHOLDER", names[folder][2])
        t = t.replace("TO_DP_PLACEHOLDER", names[folder][3])
        t = t.replace("TO_PX_PLACEHOLDER", names[folder][4])
        t = t.replace("CALC_TYPE_PLACEHOLDER", f"DimenCache.CalcType.{ct}")

        pow_import = "import kotlin.math.pow\n" if folder == "power" else ""
        header = f'''/**
 * Strategy module: {ct} — calculation logic lives in this package only.
 */
package com.appdimens.dynamic.compose.{folder}

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.common.DpQualifier
import com.appdimens.dynamic.common.Inverter
import com.appdimens.dynamic.core.DesignScaleConstants
import com.appdimens.dynamic.core.DimenCache
import com.appdimens.dynamic.core.DimenCalculationPlumbing
import com.appdimens.dynamic.core.layoutRememberStamp
import com.appdimens.dynamic.core.pxRememberStamp
import com.appdimens.dynamic.core.rememberDimenDp
import com.appdimens.dynamic.core.rememberDimenPxFromDp
{pow_import}
/**
 * EN
 * Gets the actual value from the Configuration for the given DpQualifier.
 */
internal fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {{
    return DimenCalculationPlumbing.readScreenDp(configuration, qualifier)
}}

'''
        body = calc_bodies[folder]
        out = header + eb + "\n\n" + body + "\n\n" + t
        write(f"compose/{folder}/Dimen{cls}Dp.kt", out)
        print("wrote compose", folder)

    # --- Code extensions (View / non-Compose) ---
    CODE_EXT = read("code/scaled/DimenSdpExtensions.kt")
    calc_start = CODE_EXT.find("private fun calculateScaledDp(")
    calc_end = CODE_EXT.find("}\n\n/**", calc_start)
    if calc_end < 0:
        raise SystemExit("could not find calculateScaledDp block")
    calc_end = calc_end + 1

    def rename_code_number_ext(text: str, sp: str, sh: str, sw: str) -> str:
        text = re.sub(r"fun Number\.sdp", f"fun Number.{sp}dp", text)
        text = re.sub(r"fun Number\.hdp", f"fun Number.{sh}dp", text)
        text = re.sub(r"fun Number\.wdp", f"fun Number.{sw}dp", text)
        return text

    code_calc_names = {f: calc_bodies[f].replace("DpCompose", "Dp") for f in calc_bodies}

    for folder, cls, ct, _, _, _ in STRATEGIES:
        sp, sh, sw = PREFIXES[folder]
        ndp, _, _, todp, topx = names[folder]
        calc_fn = ndp.replace("DpCompose", "Dp")

        s = CODE_EXT[:calc_start] + code_calc_names[folder] + CODE_EXT[calc_end:]
        s = s.replace("package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1)
        s = s.replace(
            "import com.appdimens.dynamic.core.DimenCache\n",
            "import com.appdimens.dynamic.core.DesignScaleConstants\nimport com.appdimens.dynamic.core.DimenCalculationPlumbing\nimport com.appdimens.dynamic.core.DimenCache\n",
            1,
        )
        s = s.replace("toDynamicScaledPx", topx)
        s = s.replace("toDynamicScaledDp", todp)
        s = s.replace("calculateScaledDp", calc_fn)
        s = s.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
        s = rename_code_number_ext(s, sp, sh, sw)
        if folder == "power":
            s = s.replace(
                "import com.appdimens.dynamic.core.DimenCache\n\n// EN",
                "import com.appdimens.dynamic.core.DimenCache\nimport kotlin.math.pow\n\n// EN",
                1,
            )
        write(f"code/{folder}/Dimen{cls}Extensions.kt", s)
        print("wrote code ext", folder)

    # --- Code DimenSdp JVM object mirror ---
    CODE_SDP_OBJ = read("code/scaled/DimenSdp.kt")
    for folder, cls, ct, _, _, _ in STRATEGIES:
        sp, sh, sw = PREFIXES[folder]
        _, _, _, todp, topx = names[folder]
        o = CODE_SDP_OBJ.replace("package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1)
        o = o.replace(
            "import com.appdimens.dynamic.core.DimenCache\n",
            "import com.appdimens.dynamic.core.DimenCache\n",
            1,
        )
        o = o.replace("object DimenSdp", f"object Dimen{cls}")
        o = o.replace("DimenSdp", f"Dimen{cls}")
        o = o.replace("toDynamicScaledPx", topx)
        o = o.replace("toDynamicScaledDp", todp)
        o = re.sub(r"\bfun sdp", f"fun {sp}dp", o)
        o = re.sub(r"\bfun hdp", f"fun {sh}dp", o)
        o = re.sub(r"\bfun wdp", f"fun {sw}dp", o)
        o = re.sub(r"\bfun sdpRotate", f"fun {sp}dpRotate", o)
        o = re.sub(r"\bfun hdpRotate", f"fun {sh}dpRotate", o)
        o = re.sub(r"\bfun wdpRotate", f"fun {sw}dpRotate", o)
        o = re.sub(r"\bfun sdpMode", f"fun {sp}dpMode", o)
        o = re.sub(r"\bfun hdpMode", f"fun {sh}dpMode", o)
        o = re.sub(r"\bfun wdpMode", f"fun {sw}dpMode", o)
        o = re.sub(r"value\.sdp", f"value.{sp}dp", o)
        o = re.sub(r"value\.hdp", f"value.{sh}dp", o)
        o = re.sub(r"value\.wdp", f"value.{sw}dp", o)
        o = o.replace("DimenScaled", f"Dimen{cls}Scaled")
        write(f"code/{folder}/Dimen{cls}.kt", o)
        print("wrote code obj", folder)

    CODE_SCALED_CLASS = read("code/scaled/DimenScaled.kt")
    for folder, cls, ct, _, _, _ in STRATEGIES:
        _, _, _, todp, _ = names[folder]
        dm = CODE_SCALED_CLASS.replace(
            "package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1
        )
        dm = dm.replace("DimenScaled", f"Dimen{cls}Scaled")
        dm = dm.replace("fun Float.scaledDp():", f"fun Float.{folder}ScaledDp():")
        dm = dm.replace("fun Number.scaledDp():", f"fun Number.{folder}ScaledDp():")
        dm = dm.replace("this.toFloat().scaledDp()", f"this.toFloat().{folder}ScaledDp()")
        dm = dm.replace("toDynamicScaledDp", todp)
        write(f"code/{folder}/Dimen{cls}Scaled.kt", dm)
        print("wrote code scaled class", folder)

    COMPOSE_SDP_EXT = read("compose/scaled/DimenSdpExtensions.kt")
    COMPOSE_DIMEN_SCALED = read("compose/scaled/DimenScaled.kt")
    for folder, cls, ct, _, _, _ in STRATEGIES:
        ps, ph, pw = PREFIXES[folder]
        ext = transform_compose_dp_extensions(COMPOSE_SDP_EXT, folder, cls, ct, ps, ph, pw)
        write(f"compose/{folder}/Dimen{cls}DpExtensions.kt", ext)
        print("wrote compose", folder, "DpExtensions")
        dsc = transform_compose_scaled_class(COMPOSE_DIMEN_SCALED, folder, cls)
        write(f"compose/{folder}/Dimen{cls}Scaled.kt", dsc)
        print("wrote compose", folder, "Scaled (Compose)")


if __name__ == "__main__":
    main()
