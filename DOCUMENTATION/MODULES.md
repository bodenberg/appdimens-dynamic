# AppDimens Dynamic — Gradle / Maven modules (3.1.6)

## Graph

```
app (demo)
 ├── library                      → io.github.bodenberg:appdimens-dynamic
 ├── library-auto                 → …:appdimens-dynamic-auto
 ├── library-density              → …:appdimens-dynamic-density
 ├── library-diagonal             → …:appdimens-dynamic-diagonal
 ├── library-fill                 → …:appdimens-dynamic-fill
 ├── library-fit                  → …:appdimens-dynamic-fit
 ├── library-fluid                → …:appdimens-dynamic-fluid
 ├── library-interpolated         → …:appdimens-dynamic-interpolated
 ├── library-logarithmic          → …:appdimens-dynamic-logarithmic
 ├── library-percent              → …:appdimens-dynamic-percent
 ├── library-perimeter            → …:appdimens-dynamic-perimeter
 ├── library-power                → …:appdimens-dynamic-power
 ├── library-resize               → …:appdimens-dynamic-resize
 └── library-units                → …:appdimens-dynamic-units
```

Every satellite declares `api(project(":library"))` only. **No satellite→satellite** edges. **No ALL/BOM.**

## Core decoupling

- Shared metrics (`scale`, AR, density) stay in `DimenCache.updateFactors()`.
- Strategy scales register through `StrategyFactorRegistry` from each satellite that needs them.
- `CalcType` ordinals remain in core for stable 64-bit cache keys.

## Version

All artifacts share `appdimens.version` from `gradle.properties` (**3.1.6**).
