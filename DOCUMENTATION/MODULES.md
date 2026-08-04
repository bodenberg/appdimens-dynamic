# AppDimens Dynamic — Gradle / Maven modules (3.1.6)

## Module graph

```
app (demo)
 ├── library                      → io.github.bodenberg:appdimens-dynamic
 ├── library-bom                  → …:appdimens-dynamic-bom
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

Satellites depend only on `:library` (`api(project(":library"))`).

Android Gradle `namespace` values are unique per module (`com.appdimens.dynamic` for the principal, `com.appdimens.dynamic.<strategy>` for satellites). Kotlin packages remain `com.appdimens.dynamic.*` as before.

## Artifacts

| Artifact | Contents |
|----------|----------|
| `appdimens-dynamic` | `common`, `core`, **scaled**, **plain** |
| `appdimens-dynamic-<strategy>` | `code.<strategy>` + `compose.<strategy>` |
| `appdimens-dynamic-bom` | Version constraints for the set above (`java-platform`) |

All published coordinates share `appdimens.version` in `gradle.properties` (**3.1.6**). Kotlin packages are unchanged from 3.1.5.

## Installation

```kotlin
dependencies {
    implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.1.6"))
    implementation("io.github.bodenberg:appdimens-dynamic")
    implementation("io.github.bodenberg:appdimens-dynamic-percent")
}
```

Without the BOM, pin the same version on each coordinate. See [README — Installation](../README.md#installation-v316).

## Core layout

- Shared screen metrics (`scale`, aspect ratio, density) are updated in `DimenCache.updateFactors()`.
- Strategy-specific scales register through `StrategyFactorRegistry` from the satellite that owns them (`diagonal`, `power`, `logarithmic`, `interpolated`, `perimeter`).
- `CalcType` ordinals live in core so cache keys stay stable across modules.

## Migration from 3.1.5

| 3.1.5 | 3.1.6 |
|-------|-------|
| Single `appdimens-dynamic` AAR with every strategy | Principal = scaled + core; add one satellite per extra strategy |
| Version on each dependency | Optional `platform("…:appdimens-dynamic-bom:3.1.6")` |

## See also

- [DOCUMENTATION/README.md](README.md) — strategy guides
- [R8-PROGUARD.md](../R8-PROGUARD.md) — per-AAR consumer rules
- [CONTRIBUTING.md](../CONTRIBUTING.md) — module contribution rules
