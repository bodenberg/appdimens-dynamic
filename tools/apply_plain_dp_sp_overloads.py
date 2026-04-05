#!/usr/bin/env python3
"""Insert Plain Dp/TextUnit overload blocks into compose strategy extension files."""
from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SCALED_DP = ROOT / "library/src/main/java/com/appdimens/dynamic/compose/scaled/DimenSdpExtensions.kt"
SCALED_SP = ROOT / "library/src/main/java/com/appdimens/dynamic/compose/scaled/DimenSspExtensions.kt"

# Line ranges in scaled DimenSdpExtensions / DimenSspExtensions: only Plain Dp/TextUnit overloads (no Number branches).
DP_RANGES = [
    (280, 314),
    (316, 345),
    (347, 376),
    (1070, 1131),
    (1796, 1869),
    (2544, 2600),
    (3078, 3105),
]

SP_RANGES = [
    (284, 313),
    (553, 582),
    (822, 851),
    (1074, 1093),
    (1312, 1331),
    (1550, 1569),
    (1839, 1862),
    (2127, 2150),
    (2415, 2438),
    (2736, 2763),
    (3056, 3083),
    (3376, 3403),
]

STRATEGIES = {
    "percent": ("psdp", "phdp", "pwdp"),
    "power": ("pwsdp", "pwhdp", "pwwdp"),
    "auto": ("asdp", "ahdp", "awdp"),
    "logarithmic": ("logsdp", "loghdp", "logwdp"),
    "fluid": ("fsdp", "fhdp", "fwdp"),
    "interpolated": ("isdp", "ihdp", "iwdp"),
    "diagonal": ("dgsdp", "dghdp", "dgwdp"),
    "perimeter": ("prsdp", "prhdp", "prwdp"),
    "fit": ("ftsdp", "fthdp", "ftwdp"),
    "fill": ("flsdp", "flhdp", "flwdp"),
    "density": ("dsdp", "dhdp", "dwdp"),
}

DP_FILES = {
    "percent": "DimenPercentDpExtensions.kt",
    "power": "DimenPowerDpExtensions.kt",
    "auto": "DimenAutoDpExtensions.kt",
    "logarithmic": "DimenLogarithmicDpExtensions.kt",
    "fluid": "DimenFluidDpExtensions.kt",
    "interpolated": "DimenInterpolatedDpExtensions.kt",
    "diagonal": "DimenDiagonalDpExtensions.kt",
    "perimeter": "DimenPerimeterDpExtensions.kt",
    "fit": "DimenFitDpExtensions.kt",
    "fill": "DimenFillDpExtensions.kt",
    "density": "DimenDensityDpExtensions.kt",
}

SP_FILES = {k: v.replace("Dp", "Sp") for k, v in DP_FILES.items()}

SUFFIX = r"(RotatePlain|RotatePlainPx|ModePlain|ModePlainPx|QualifierPlain|QualifierPlainPx|ScreenPlain|ScreenPlainPx)"


def extract_ranges(path: Path, ranges: list[tuple[int, int]]) -> str:
    lines = path.read_text().splitlines()
    parts: list[str] = []
    for a, b in ranges:
        parts.append("\n".join(lines[a - 1 : b]))
    return "\n\n".join(parts) + "\n"


def subst_dp(text: str, sdp: str, hdp: str, wdp: str) -> str:
    text = text.replace("30.sdp.sdpRotatePlain(20.sdp)", f"30.{sdp}.{sdp}RotatePlain(20.{sdp})")
    for old, new in (("wdp", wdp), ("hdp", hdp), ("sdp", sdp)):
        text = re.sub(rf"\b{old}{SUFFIX}\b", rf"{new}\1", text)
    for old, new in (("this@wdp", f"this@{wdp}"), ("this@hdp", f"this@{hdp}"), ("this@sdp", f"this@{sdp}")):
        text = text.replace(old, new)
    return text


def insert_dp_if_missing(pkg: str, sdp: str, hdp: str, wdp: str, block: str) -> None:
    path = ROOT / f"library/src/main/java/com/appdimens/dynamic/compose/{pkg}/{DP_FILES[pkg]}"
    content = path.read_text()
    if f"fun Dp.{sdp}RotatePlain(rotation: Dp," in content:
        return

    needle = (
        f"        density.run {{ this@{sdp}RotatePlainPx.toPx() }}\n"
        f"    }}\n}}\n\n"
        "/**\n"
        " * EN\n"
        " * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.\n"
        " * Uses the base value by default, but when the device is in the specified [orientation],"
    )
    if needle not in content:
        raise SystemExit(f"Anchor not found in {path}")

    replacement = (
        f"        density.run {{ this@{sdp}RotatePlainPx.toPx() }}\n"
        f"    }}\n}}\n\n"
        + block
        + "/**\n"
        " * EN\n"
        " * Extension for Dp with dynamic scaling based on **Screen Height (hDP)**.\n"
        " * Uses the base value by default, but when the device is in the specified [orientation],"
    )
    content = content.replace(needle, replacement, 1)
    path.write_text(content)


def insert_sp_if_missing(pkg: str, block: str) -> None:
    path = ROOT / f"library/src/main/java/com/appdimens/dynamic/compose/{pkg}/{SP_FILES[pkg]}"
    content = path.read_text()
    if "fun TextUnit.sspRotatePlain(rotation: TextUnit," in content:
        return

    pattern = re.compile(
        r"(        density\.run \{ this@sspRotatePlainPx\.toPx\(\) \}\n"
        r"    \}\n"
        r"\}\n\n)"
        r"(// Removed duplicate Int\.hspRotate \(kept in Dimen\w+Sp\.kt\)\n)"
    )
    m = pattern.search(content)
    if not m:
        raise SystemExit(f"Sp anchor not found in {path}")
    content = content[: m.start()] + m.group(1) + block + m.group(2) + content[m.end() :]
    path.write_text(content)


def main() -> None:
    dp_template = extract_ranges(SCALED_DP, DP_RANGES)
    sp_template = extract_ranges(SCALED_SP, SP_RANGES)

    for pkg, (sdp, hdp, wdp) in STRATEGIES.items():
        block = subst_dp(dp_template, sdp, hdp, wdp)
        insert_dp_if_missing(pkg, sdp, hdp, wdp, block)
        insert_sp_if_missing(pkg, sp_template)


if __name__ == "__main__":
    main()
