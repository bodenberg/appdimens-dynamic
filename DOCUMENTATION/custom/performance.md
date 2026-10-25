# Performance

Custom strategies cost the same as the built-ins when they only depend on the screen. Which form you pick decides the lane.

| Form | Per window | Per value | Lane |
|---|---|---|---|
| `CustomScaleStrategy { … }`, any curve, rules, factor bounds, aspect ratio | lambda / curve once | one multiply | bypass (no table lookup) |
| text formula shaped `base * E` (E reads no value) | `E` once | one multiply | bypass |
| `scalingFormula { … }`, other text formulas, `transform { }`, `clampValue` | | formula on a cache miss | per-window `DimenCache` |

## How it works

- **Memo per window.** Each strategy keeps the last resolved window (rules applied, factor computed). A new window (rotation, resize, fold, font scale, UI mode) resolves again once.
- **Bypass lane.** A screen-only strategy is flagged non-cacheable in `DimenCache`, so `16.cdp(Product)` is a memo read plus one multiply.
- **Cached lane.** A value-dependent strategy stores each result in the per-window `DimenCache` partition under `CalcType.CUSTOM`. The strategy's cache id sits in the 16 sensitivity bits of the key, so two strategies never alias.
- **Cache ids.** Each strategy instance gets one of 65 536 ids. Past that, strategies still work but always compute. This is why strategies should be declared once and not rebuilt on every recomposition.
- **Fast path for text.** `base * E` is detected when the formula is parsed; the result is bit for bit the same as evaluating it per value.

## Guidelines

1. Prefer a factor (`CustomScaleStrategy`, a curve, rules) over a per-value formula when the math is "base × something about the screen".
2. Use `clampValue` only for real absolute limits (tap targets ≥ 48 dp, max text size).
3. Declare strategies once: a top-level `val`, an `object AppStrategies`, or a JSON document loaded at startup.
4. Keep lambdas pure and cheap. They run on memo or cache misses, on the calling thread.
5. Text formulas inline (`16.cdp("…")`) are shared per text, so they are fine in composables. Building strategies from dynamic strings in a loop is not: past 256 distinct texts each call builds a new strategy.

[Back to the custom docs](README.md)
