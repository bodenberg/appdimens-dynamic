#!/usr/bin/env python3
"""Generate Dimen*Sp* (Compose + code) per strategy from compose/scaled and code/scaled SSP templates.

Type/file names use `Dimen{Strategy}Sp` (not `Ssp`) outside `scaled/`; templates remain DimenSsp.kt in scaled/.
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


STRATEGIES = [
    ("percent", "Percent", "PERCENT"),
    ("power", "Power", "POWER"),
    ("auto", "Auto", "AUTO"),
    ("logarithmic", "Logarithmic", "LOGARITHMIC"),
    ("fluid", "Fluid", "FLUID"),
    ("interpolated", "Interpolated", "INTERPOLATED"),
    ("diagonal", "Diagonal", "DIAGONAL"),
    ("perimeter", "Perimeter", "PERIMETER"),
    ("fit", "Fit", "FIT"),
    ("fill", "Fill", "FILL"),
    ("density", "Density", "DENSITY"),
]

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


def qualify_sp(ps: str, ph: str, pw: str) -> dict:
    s_sp = ps[:-1] + "ssp" if ps.endswith("s") else ps + "ssp"
    h_sp = ph[:-1] + "hsp" if ph.endswith("h") else ph + "hsp"
    w_sp = pw[:-1] + "wsp" if pw.endswith("w") else pw + "wsp"
    s_nem = ps[:-1] + "nem" if ps.endswith("s") else ps + "nem"
    h_hem = ph[:-1] + "hem" if ph.endswith("h") else ph + "hem"
    w_wem = pw[:-1] + "wem" if pw.endswith("w") else pw + "wem"
    return {
        "s_sp": s_sp,
        "h_sp": h_sp,
        "w_sp": w_sp,
        "s_nem": s_nem,
        "h_hem": h_hem,
        "w_wem": w_wem,
    }


def rename_sp_identifiers(text: str, sn: dict) -> str:
    for old, new in (
        ("wem", sn["w_wem"]),
        ("wsp", sn["w_sp"]),
        ("hem", sn["h_hem"]),
        ("hsp", sn["h_sp"]),
        ("nem", sn["s_nem"]),
        ("ssp", sn["s_sp"]),
    ):
        text = re.sub(r"\b" + re.escape(old) + r"\b", new, text)
    return text


def replace_block_after_needle(src: str, needle: str, replacement: str) -> str:
    start = src.find(needle)
    if start < 0:
        raise SystemExit(f"missing needle: {needle!r}")
    brace = src.find("{", start)
    depth = 0
    j = brace
    while j < len(src):
        c = src[j]
        if c == "{":
            depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0:
                end = j + 1
                return src[:start] + replacement + src[end:]
        j += 1
    raise SystemExit(f"unbalanced braces for {needle!r}")


def replace_compose_ssp_calc(src: str, cls: str, calc_dp_compose: str) -> str:
    old_name = "calculateSspValueCompose"
    new_name = f"calculate{cls}SpValueCompose"
    needle = f"internal fun {old_name}("
    sig_end = src.find("): Float {", src.find(needle))
    if sig_end < 0:
        raise SystemExit("bad calculateSspValueCompose signature")
    params_start = src.find("(", src.find(needle)) + 1
    params = src[params_start:sig_end]
    new_fn = (
        f"internal fun {new_name}({params}): Float = "
        f"{calc_dp_compose}(baseValue, configuration, qualifier, inverter, "
        f"ignoreMultiWindows, applyAspectRatio, customSensitivityK)\n"
    )
    out = replace_block_after_needle(src, needle, new_fn)
    return out.replace(old_name, new_name)


def replace_code_ssp_calc(src: str, cls: str, calc_dp: str) -> str:
    old_name = "calculateScaledSp"
    new_name = f"calculate{cls}Sp"
    needle = f"private fun {old_name}("
    sig_end = src.find("): Float {", src.find(needle))
    if sig_end < 0:
        raise SystemExit("bad calculateScaledSp signature")
    params_start = src.find("(", src.find(needle)) + 1
    params = src[params_start:sig_end]
    new_fn = (
        f"private fun {new_name}({params}): Float = "
        f"{calc_dp}(baseValue, configuration, qualifier, inverter, "
        f"ignoreMultiWindows, applyAspectRatio, customSensitivityK)\n"
    )
    out = replace_block_after_needle(src, needle, new_fn)
    return out.replace(old_name, new_name)


def transform_compose_ssp_core(
    text: str, folder: str, cls: str, ct: str, sn: dict, include_scaled_sp: bool
) -> str:
    t = text.replace("package com.appdimens.dynamic.compose\n", f"package com.appdimens.dynamic.compose.{folder}\n", 1)
    t = t.replace("DimenSsp", f"Dimen{cls}Sp")
    if include_scaled_sp:
        t = t.replace("ScaledSp", f"{cls}Sp")
        t = t.replace("scaledSp()", f"{folder}Sp()")
        t = t.replace("scaledSp(", f"{folder}Sp(")
    t = t.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
    t = t.replace("toDynamicScaledSp", f"toDynamic{cls}Sp")
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = t.replace("rememberScaledSpPx", f"remember{cls}SpPx")
    t = t.replace("rememberScaledSp", f"remember{cls}Sp")
    t = rename_sp_identifiers(t, sn)
    t = t.replace("kept in DimenSsp.kt", f"kept in Dimen{cls}Sp.kt")
    return t


def transform_compose_ssp_main(text: str, folder: str, cls: str, ct: str, sn: dict, calc_dp_compose: str) -> str:
    t = transform_compose_ssp_core(text, folder, cls, ct, sn, include_scaled_sp=False)
    t = replace_compose_ssp_calc(t, cls, calc_dp_compose)
    return t


def transform_compose_ssp_extensions(text: str, folder: str, cls: str, ct: str, sn: dict) -> str:
    return transform_compose_ssp_core(text, folder, cls, ct, sn, include_scaled_sp=False)


def transform_compose_ssp_scaled(text: str, folder: str, cls: str, ct: str, sn: dict) -> str:
    t = text.replace("package com.appdimens.dynamic.compose\n", f"package com.appdimens.dynamic.compose.{folder}\n", 1)
    t = t.replace("ScaledSp", f"{cls}Sp")
    t = t.replace("scaledSp()", f"{folder}Sp()")
    t = t.replace("scaledSp(", f"{folder}Sp(")
    t = t.replace("toDynamicScaledSp", f"toDynamic{cls}Sp")
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = t.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
    t = rename_sp_identifiers(t, sn)
    return t


def transform_code_ssp_ext(text: str, folder: str, cls: str, ct: str, sn: dict, calc_dp: str) -> str:
    t = text.replace("package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1)
    t = t.replace("DimenSsp", f"Dimen{cls}Sp")
    t = t.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
    t = t.replace("toDynamicScaledSpPx", f"toDynamic{cls}SpPx")
    t = t.replace("toDynamicScaledSp", f"toDynamic{cls}Sp")
    t = replace_code_ssp_calc(t, cls, calc_dp)
    t = rename_sp_identifiers(t, sn)
    if folder == "power":
        t = t.replace(
            "import com.appdimens.dynamic.core.DimenCache\n\n// EN",
            "import com.appdimens.dynamic.core.DimenCache\nimport kotlin.math.pow\n\n// EN",
            1,
        )
    return t


def transform_code_ssp_obj(text: str, folder: str, cls: str, sn: dict) -> str:
    t = text.replace("package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1)
    t = t.replace("object DimenSsp", f"object Dimen{cls}Sp")
    t = t.replace("DimenSsp", f"Dimen{cls}Sp")
    t = t.replace("ScaledSp", f"{cls}Sp")
    t = t.replace("toDynamicScaledSpPx", f"toDynamic{cls}SpPx")
    t = t.replace("toDynamicScaledSp", f"toDynamic{cls}Sp")
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = rename_sp_identifiers(t, sn)
    return t


def transform_code_ssp_scaled(text: str, folder: str, cls: str, ct: str, sn: dict) -> str:
    t = text.replace("package com.appdimens.dynamic.code\n", f"package com.appdimens.dynamic.code.{folder}\n", 1)
    t = t.replace("ScaledSp", f"{cls}Sp")
    t = t.replace("scaledSp(", f"{folder}Sp(")
    t = t.replace("toDynamicScaledSpPx", f"toDynamic{cls}SpPx")
    t = t.replace("toDynamicScaledSp", f"toDynamic{cls}Sp")
    t = t.replace("toDynamicScaledPx", f"toDynamic{cls}Px")
    t = t.replace("DimenCache.CalcType.SCALED", f"DimenCache.CalcType.{ct}")
    t = rename_sp_identifiers(t, sn)
    return t


def main():
    compose_ssp = read("compose/scaled/DimenSsp.kt")
    compose_ssp_ext = read("compose/scaled/DimenSspExtensions.kt")
    compose_ssp_scaled = read("compose/scaled/DimenSspScaled.kt")
    code_ssp = read("code/scaled/DimenSsp.kt")
    code_ssp_ext = read("code/scaled/DimenSspExtensions.kt")
    code_ssp_scaled = read("code/scaled/DimenSspScaled.kt")

    for folder, cls, ct in STRATEGIES:
        ps, ph, pw = PREFIXES[folder]
        sn = qualify_sp(ps, ph, pw)
        calc_dp_compose = f"calculate{cls}DpCompose"
        calc_dp = f"calculate{cls}Dp"

        c1 = transform_compose_ssp_main(compose_ssp, folder, cls, ct, sn, calc_dp_compose)
        write(f"compose/{folder}/Dimen{cls}Sp.kt", c1)
        print("wrote compose", folder, "Sp")

        c2 = transform_compose_ssp_extensions(compose_ssp_ext, folder, cls, ct, sn)
        write(f"compose/{folder}/Dimen{cls}SpExtensions.kt", c2)
        print("wrote compose", folder, "SpExtensions")

        c3 = transform_compose_ssp_scaled(compose_ssp_scaled, folder, cls, ct, sn)
        write(f"compose/{folder}/Dimen{cls}SpScaled.kt", c3)
        print("wrote compose", folder, "SpScaled")

        k1 = transform_code_ssp_obj(code_ssp, folder, cls, sn)
        write(f"code/{folder}/Dimen{cls}Sp.kt", k1)
        print("wrote code", folder, "Sp")

        k2 = transform_code_ssp_ext(code_ssp_ext, folder, cls, ct, sn, calc_dp)
        write(f"code/{folder}/Dimen{cls}SpExtensions.kt", k2)
        print("wrote code", folder, "SpExtensions")

        k3 = transform_code_ssp_scaled(code_ssp_scaled, folder, cls, ct, sn)
        write(f"code/{folder}/Dimen{cls}SpScaled.kt", k3)
        print("wrote code", folder, "SpScaled")


if __name__ == "__main__":
    main()
