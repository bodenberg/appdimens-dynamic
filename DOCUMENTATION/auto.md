# Auto strategy (`compose.auto` / `code.auto`)

**Same API surface as scaled** with prefixes `asdp` / `ahdp` / `awdp` / `assp` / … — see [COMPOSE-API-CONVENTIONS.md §3](COMPOSE-API-CONVENTIONS.md#3-strategy-prefix-map-mirror-of-scaled).

## What it is

**Auto** combines **linear** scaling with 300 dp reference up to **480 dp** on the axis; beyond that it adds a **logarithmic** term so tablet growth is more restrained than a pure line, without being as “flat” as fluid’s plateaus.

## Calculation used

With `inv = 1/300`, `transition = 480`, `sensitivity = 0.4`:

- If `dim ≤ 480`: `scale = dim × inv` (same as linear scaled in that range).
- If `dim > 480`: `scale = (480 × inv) + sensitivity × ln(1 + (dim − 480) × inv)`
- Result: `base × scale`; with **`a`**, multiply by the pre-computed aspect-ratio factor (`DimenCache.currentAspectRatioMul`); custom sensitivity uses `1 + k × logNormalizedAr`.

Implementation: `calculateAutoDpCompose` in `DimenAutoDp.kt`.

## How to use

```kotlin
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.ahdp

Modifier.padding(16.asdp)
```

Prefixes: `asdp`, `ahdp`, `awdp` (+ Sp, px, inverters, `a`/`i`).

**Code:** `com.appdimens.dynamic.code.auto`.

## Why use it

One token set that breathes on phones but **does not double** everything on tablets, without hand-tuned breakpoints.

## When to use it

- Apps that want **less redesign** between phone and tablet.
- When **scaled** grows too much on tablet but **power** or **fluid** do not match the desired feel.

## Advantages and trade-offs

- **Pros:** continuous transition; familiar linear behavior on phones.
- **Cons:** past 480 dp the curve changes character — validate long copy and grids there.

## Recommended usage strategy

Prototype with **scaled**; if tablet is the only pain point, try **auto** on the same tokens before mixing many strategies on one screen.

[Back to index](README.md)
