# Cross-platform parity vectors

`appdimens-dynamic` is the source of truth for the AppDimens math. `generate.py` compiles the
**verbatim** strategy kernels of this repository (`calculate<Strategy>DpCompose`, `DimenMetrics`,
`DimenCalculationPlumbing`, the `*Factors` objects and `DimenCache` constants) on a plain JVM —
only `android.*` types are stubbed — and writes `parity-vectors.json`:

- 12 strategies × 11 axis variants (`s`, `h`, `w`, `Ph/Lh/Pw/Lw` inverters) × 4 flag combos (`—`, `a`, `i`, `ia`)
- 20 screens (phones, foldables, tablets, multi-window, TVs, 4K, 5K ultrawide, watches)
- custom aspect-ratio sensitivity (`k = 0.01`) for scaled and auto

```bash
python3 tools/parity/generate.py      # JDK 17+ and Gradle; no Android SDK needed
```

Ports that test against these vectors (float32 tolerance 3e-6): appdimens-gtk, appdimens-lvgl, appdimens-qt,
appdimens-py, appdimens-games (C/C++ SDK, .NET/Unity, Godot). appdimens-web 3.2.1 agrees to 2.9e-7 (float rounding).
