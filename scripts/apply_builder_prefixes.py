#!/usr/bin/env python3
"""Apply strategy-prefixed accessor names in *Scaled / *SpScaled builders (excludes scaled strategy)."""
from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LIB = ROOT / "library" / "src" / "main" / "java" / "com" / "appdimens" / "dynamic"

PAC = {
    "auto": "Auto",
    "percent": "Percent",
    "power": "Power",
    "fluid": "Fluid",
    "density": "Density",
    "diagonal": "Diagonal",
    "fill": "Fill",
    "fit": "Fit",
    "interpolated": "Interpolated",
    "logarithmic": "Logarithmic",
    "perimeter": "Perimeter",
}

STEMS: dict[str, tuple[str, str, str, str, str, str, str, str, str]] = {
    "auto": ("asdp", "ahdp", "awdp", "assp", "ahsp", "awsp", "asem", "ahem", "awem"),
    "percent": ("psdp", "phdp", "pwdp", "pssp", "phsp", "pwsp", "psem", "phem", "pwem"),
    "power": ("pwsdp", "pwhdp", "pwwdp", "pwssp", "pwhsp", "pwwsp", "pwsem", "pwhem", "pwwem"),
    "fluid": ("fsdp", "fhdp", "fwdp", "fssp", "fhsp", "fwsp", "fsem", "fhem", "fwem"),
    "density": ("dsdp", "dhdp", "dwdp", "dssp", "dhsp", "dwsp", "dsem", "dhem", "dwem"),
    "diagonal": ("dgsdp", "dghdp", "dgwdp", "dgssp", "dghsp", "dgwsp", "dgsem", "dghem", "dgwem"),
    "fill": ("flsdp", "flhdp", "flwdp", "flssp", "flhsp", "flwsp", "flsem", "flhem", "flwem"),
    "fit": ("ftsdp", "fthdp", "ftwdp", "ftssp", "fthsp", "ftwsp", "ftsem", "fthem", "ftwem"),
    "interpolated": ("isdp", "ihdp", "iwdp", "issp", "ihsp", "iwsp", "isem", "ihem", "iwem"),
    "logarithmic": ("logsdp", "loghdp", "logwdp", "logssp", "loghsp", "logwsp", "logsem", "loghem", "logwem"),
    "perimeter": ("prsdp", "prhdp", "prwdp", "prssp", "prhsp", "prwsp", "prsem", "prhem", "prwem"),
}


def cap_mid(s: str) -> str:
    return s[0].upper() + s[1:] if s else s


def patch_compose_dp_scaled(text: str, sw: str, sh: str, swd: str) -> str:
    text = text.replace(
        "determined by the access property (.sdp, .hdp, .wdp).",
        f"determined by the access property (.{sw}, .{sh}, .{swd}).",
    )
    repl = [
        ("val sdp: Dp get()", f"val {sw}: Dp get()"),
        ("val hdp: Dp get()", f"val {sh}: Dp get()"),
        ("val wdp: Dp get()", f"val {swd}: Dp get()"),
        ("val sdpPx: Float get()", f"val {sw}Px: Float get()"),
        ("val hdpPx: Float get()", f"val {sh}Px: Float get()"),
        ("val wdpPx: Float get()", f"val {swd}Px: Float get()"),
    ]
    for a, b in repl:
        text = text.replace(a, b)
    return text


def patch_compose_sp_scaled(text: str, ssp: str, hsp: str, wsp: str, sem: str, hem: str, wem: str) -> str:
    # KDoc lines that referenced generic scaled names
    text = text.replace(
        "same idea as `sem` / `dwem` accessors",
        f"same idea as `{sem}` / `{wem}` accessors",
    )
    text = text.replace(
        "mesmo propósito dos acessores `sem` / `dwem`",
        f"mesmo propósito dos acessores `{sem}` / `{wem}`",
    )
    text = text.replace(
        "same idea as `sem` / `awem` accessors",
        f"same idea as `{sem}` / `{wem}` accessors",
    )
    text = text.replace(
        "mesmo propósito dos acessores `sem` / `awem`",
        f"mesmo propósito dos acessores `{sem}` / `{wem}`",
    )
    repl = [
        ("val ssp: TextUnit get()", f"val {ssp}: TextUnit get()"),
        ("val hsp: TextUnit get()", f"val {hsp}: TextUnit get()"),
        ("val wsp: TextUnit get()", f"val {wsp}: TextUnit get()"),
        ("val sem: TextUnit get()", f"val {sem}: TextUnit get()"),
        ("val hem: TextUnit get()", f"val {hem}: TextUnit get()"),
        ("val wem: TextUnit get()", f"val {wem}: TextUnit get()"),
        ("val sspPx: Float get()", f"val {ssp}Px: Float get()"),
        ("val hspPx: Float get()", f"val {hsp}Px: Float get()"),
        ("val wspPx: Float get()", f"val {wsp}Px: Float get()"),
        ("val semPx: Float get()", f"val {sem}Px: Float get()"),
        ("val hemPx: Float get()", f"val {hem}Px: Float get()"),
        ("val wemPx: Float get()", f"val {wem}Px: Float get()"),
    ]
    for a, b in repl:
        text = text.replace(a, b)
    return text


def patch_code_dp_scaled(text: str, sw: str, sh: str, swd: str) -> str:
    trip = sw + cap_mid(sh) + cap_mid(swd) + "Px"
    text = text.replace("fun sdpHdpWdpPx(", f"fun {trip}(")
    text = text.replace("* EN Resolves sdp, hdp, and wdp in one pass", f"* EN Resolves {sw}, {sh}, and {swd} in one pass")
    text = text.replace("* PT Resolve sdp, hdp e wdp numa só passagem", f"* PT Resolve {sw}, {sh} e {swd} numa só passagem")
    text = text.replace("val sdp = resolveDpInternal", f"val {sw} = resolveDpInternal")
    text = text.replace("val hdp = resolveDpInternal", f"val {sh} = resolveDpInternal")
    text = text.replace("val wdp = resolveDpInternal", f"val {swd} = resolveDpInternal")
    text = text.replace("return Triple(sdp, hdp, wdp)", f"return Triple({sw}, {sh}, {swd})")
    repl = [
        ("fun sdp(context: Context)", f"fun {sw}(context: Context)"),
        ("fun hdp(context: Context)", f"fun {sh}(context: Context)"),
        ("fun wdp(context: Context)", f"fun {swd}(context: Context)"),
        ("fun sdpBase(context: Context)", f"fun {sw}Base(context: Context)"),
        ("fun hdpBase(context: Context)", f"fun {sh}Base(context: Context)"),
        ("fun wdpBase(context: Context)", f"fun {swd}Base(context: Context)"),
    ]
    for a, b in repl:
        text = text.replace(a, b)
    return text


def patch_code_sp_scaled(
    text: str, ssp: str, hsp: str, wsp: str, sem: str, hem: str, wem: str
) -> str:
    trip_sp = ssp + cap_mid(hsp) + cap_mid(wsp) + "Px"
    trip_em = sem + cap_mid(hem) + cap_mid(wem) + "Px"
    text = text.replace("fun sspHspWspPx(", f"fun {trip_sp}(")
    text = text.replace("fun seiHeiWeiPx(", f"fun {trip_em}(")

    text = text.replace(
        "* EN Resolves ssp, hsp, and wsp in one pass",
        f"* EN Resolves {ssp}, {hsp}, and {wsp} in one pass",
    )
    text = text.replace(
        "* PT Resolve ssp, hsp e wsp numa só passagem",
        f"* PT Resolve {ssp}, {hsp} e {wsp} numa só passagem",
    )
    text = text.replace(
        "* EN Resolves assp, ahsp, and awsp in one pass",
        f"* EN Resolves {ssp}, {hsp}, and {wsp} in one pass",
    )
    text = text.replace(
        "* PT Resolve assp, ahsp e awsp numa só passagem",
        f"* PT Resolve {ssp}, {hsp} e {wsp} numa só passagem",
    )
    text = text.replace(
        "* EN Resolves pssp, phsp, and pwsp in one pass",
        f"* EN Resolves {ssp}, {hsp}, and {wsp} in one pass",
    )
    text = text.replace(
        "* PT Resolve pssp, phsp e pwsp numa só passagem",
        f"* PT Resolve {ssp}, {hsp} e {wsp} numa só passagem",
    )
    text = text.replace(
        "* EN Resolves dssp, dhsp, and dwsp in one pass",
        f"* EN Resolves {ssp}, {hsp}, and {wsp} in one pass",
    )
    text = text.replace(
        "* PT Resolve dssp, dhsp e dwsp numa só passagem",
        f"* PT Resolve {ssp}, {hsp} e {wsp} numa só passagem",
    )
    text = text.replace(
        "* EN Resolves sei, hei, and wei in one pass (fixed Sp / no font-scale path).",
        f"* EN Resolves {sem}, {hem}, and {wem} in one pass (fixed Sp / no font-scale path).",
    )
    text = text.replace(
        "* PT Resolve sei, hei e wei numa só passagem (Sp fixo / sem escala de fonte).",
        f"* PT Resolve {sem}, {hem} e {wem} numa só passagem (Sp fixo / sem escala de fonte).",
    )
    text = text.replace(
        "* EN Shared implementation for [resolvePx], [sspHspWspPx], and [seiHeiWeiPx].",
        f"* EN Shared implementation for [resolvePx], [{trip_sp}], and [{trip_em}].",
    )
    text = text.replace(
        "* PT Implementação compartilhada para [resolvePx], [sspHspWspPx] e [seiHeiWeiPx].",
        f"* PT Implementação compartilhada para [resolvePx], [{trip_sp}] e [{trip_em}].",
    )
    text = text.replace("[sspHspWspPx]", f"[{trip_sp}]")
    text = text.replace("[seiHeiWeiPx]", f"[{trip_em}]")

    text = text.replace("fun sei(context: Context)", f"fun {sem}(context: Context)")
    text = text.replace("fun hei(context: Context)", f"fun {hem}(context: Context)")
    text = text.replace("fun wei(context: Context)", f"fun {wem}(context: Context)")

    text = text.replace("fun ssp(context: Context)", f"fun {ssp}(context: Context)")
    text = text.replace("fun hsp(context: Context)", f"fun {hsp}(context: Context)")
    text = text.replace("fun wsp(context: Context)", f"fun {wsp}(context: Context)")
    return text


def main() -> None:
    for folder, st in STEMS.items():
        sw, sh, swd, ssp, hsp, wsp, sem, hem, wem = st
        pc = PAC[folder]

        c_dp = LIB / "compose" / folder / f"Dimen{pc}Scaled.kt"
        c_sp = LIB / "compose" / folder / f"Dimen{pc}SpScaled.kt"
        k_dp = LIB / "code" / folder / f"Dimen{pc}Scaled.kt"
        k_sp = LIB / "code" / folder / f"Dimen{pc}SpScaled.kt"

        if c_dp.exists():
            t = c_dp.read_text(encoding="utf-8")
            nt = patch_compose_dp_scaled(t, sw, sh, swd)
            if nt != t:
                c_dp.write_text(nt, encoding="utf-8")
                print("patched", c_dp.relative_to(ROOT))
        if c_sp.exists():
            t = c_sp.read_text(encoding="utf-8")
            nt = patch_compose_sp_scaled(t, ssp, hsp, wsp, sem, hem, wem)
            if nt != t:
                c_sp.write_text(nt, encoding="utf-8")
                print("patched", c_sp.relative_to(ROOT))
        if k_dp.exists():
            t = k_dp.read_text(encoding="utf-8")
            nt = patch_code_dp_scaled(t, sw, sh, swd)
            if nt != t:
                k_dp.write_text(nt, encoding="utf-8")
                print("patched code", k_dp.relative_to(ROOT))
        if k_sp.exists():
            t = k_sp.read_text(encoding="utf-8")
            nt = patch_code_sp_scaled(t, ssp, hsp, wsp, sem, hem, wem)
            if nt != t:
                k_sp.write_text(nt, encoding="utf-8")
                print("patched code", k_sp.relative_to(ROOT))


if __name__ == "__main__":
    main()
