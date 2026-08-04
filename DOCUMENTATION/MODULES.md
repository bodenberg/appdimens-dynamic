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

## What ships where

| Contents | Artifact |
|----------|----------|
| `common`, `core` (`DimenCache`, `StrategyFactorRegistry`, …), **scaled**, **plain** | `appdimens-dynamic` (principal) |
| One scaling / resize / units strategy | `appdimens-dynamic-<strategy>` |

Kotlin **packages and imports are unchanged** from 3.1.5. Only Gradle coordinates change.

## Core decoupling

- Shared metrics (`scale`, AR, density) stay in `DimenCache.updateFactors()`.
- Strategy scales register through `StrategyFactorRegistry` from each satellite that needs them (`diagonal`, `power`, `logarithmic`, `interpolated`, `perimeter`).
- Absent satellites do **not** precompute those factors.
- `CalcType` ordinals remain in core for stable 64-bit cache keys.

## Consumer install (minimal)

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.6") // scaled + core
    implementation("io.github.bodenberg:appdimens-dynamic-percent:3.1.6") // example satellite
}
```

Full matrix: [README Installation](../README.md#installation-v316--modular).

## Migration from 3.1.5

| Antes (3.1.5) | Depois (3.1.6) |
|---------------|----------------|
| One `appdimens-dynamic` AAR with every strategy | Principal = scaled+core; add one satellite per extra strategy |
| `implementation("…:appdimens-dynamic:3.1.5")` | Same coordinate for scaled; add `…-percent`, `…-power`, … as needed |
| ALL / BOM aggregator | **Does not exist** — depend explicitly |

## Smoke checks (maintainers)

- Principal AAR / `:library` classes must **not** contain satellite strategy packages (`compose.percent`, `compose.power`, …).
- `:app` (demo) depends on principal **and** all satellites.
- Unit tests: `./gradlew :library:testDebugUnitTest` plus `:library-<strategy>:testDebugUnitTest` for touched satellites.

## Related docs

- Strategy narratives: [DOCUMENTATION/README.md](README.md)
- R8 / ProGuard per AAR: [R8-PROGUARD.md](../R8-PROGUARD.md)
- Contributing module rules: [CONTRIBUTING.md](../CONTRIBUTING.md)
- Agent package map: [skills/library-map.md](../skills/library-map.md)

## Version

All artifacts share `appdimens.version` from `gradle.properties` (**3.1.6**).
