---
name: appdimens-library-workflow
description: Use this skill for any Android responsive layout or scaling question — including making apps look right on tablets, foldables, or different screen sizes — and whenever the user mentions appdimens-dynamic, sdp, hdp, wdp, ssp, DimenCache, autoResize, responsive dp/sp, or scaling strategies. Also trigger when the user wants to add the AppDimens Dynamic dependency, choose between Compose vs Views scaling, implement constraint-based resize, or convert plain dp/sp values to scaled equivalents. When in doubt, use this skill — it covers the full lifecycle: install, strategy selection, Compose/Kotlin/Java implementation, and resize.
---

# AppDimens Dynamic — project workflow

**Library:** `io.github.bodenberg:appdimens-dynamic:3.1.3`

> **DOC_REF (maintainers):** bump every `3.1.3` URL in this skill and in `references/` together with the coordinate above on future releases.

**Authoritative GitHub docs (ref `3.1.3`):**
- [README.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/README.md) — install, `AppDimensProvider`, `DimenCache.invalidateOnConfigChange`
- [DOCUMENTATION/README.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/README.md) — all strategies, decision flow
- [DOCUMENTATION/COMPOSE-API-CONVENTIONS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) — Compose naming, facilitators, Plain chains, `code` parity
- [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/resize.md) — `compose.resize` / `code.resize`
- [GUIDE-FOR-BEGINNERS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/GUIDE-FOR-BEGINNERS.md) — narrative walkthrough
- Examples: [Compose](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/app/src/main/java/com/example/app/compose/ExampleActivity.kt) · [Kotlin Views](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/app/src/main/java/com/example/app/kotlin/ExampleActivity.kt) · [Java](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/app/src/main/java/com/example/app/java/ExampleActivity.java)

**Package map and strategy → doc index:** [references/library-map.md](references/library-map.md)

---

## Agent Preflight (run before any non-trivial edit)

1. Read [references/library-map.md](references/library-map.md) for package layout and strategy ↔ doc mapping.
2. For the **specific packages/symbols** you'll touch: browse [`library/src/main/java/com/appdimens/dynamic/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.3/library/src/main/java/com/appdimens/dynamic) on GitHub at `3.1.3`, **or** use IDE "Download Sources" / "Navigate to Declaration" on the Maven artifact. For API detail, see [DOCUMENTATION/index.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/index.md) and per-package pages under [`DOCUMENTATION/KDOC/`](https://github.com/bodenberg/appdimens-dynamic/tree/3.1.3/DOCUMENTATION/KDOC). Do not rely on memory.
3. Skim the upstream example that matches the user's stack (links above). Restrict deep reading to **relevant packages and call sites** — full module audits only when explicitly requested.

**Hard rule:** never surface `ignoreMultiWindows`, `*i`, or `*ia` suffixes to users.

---

## Phase 0 — Interactive Baseline

Ask questions **one at a time**. Wait for the answer before moving to the next step. Skip any already answered in the conversation.

### 0.1 UI Stack

Which surface is in scope?

| Choice | Package family | Key note |
|--------|---------------|----------|
| **Jetpack Compose** | `com.appdimens.dynamic.compose.*` | `16.sdp`, `scaledDp { }`; needs `AppDimensProvider` for facilitators |
| **Kotlin (Views)** | `com.appdimens.dynamic.code.*` | `DimenSdp.sdp(context, 16)`; outputs px for layout |
| **Java (Views)** | same `code.*` entry points | see [ExampleActivity.java](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/app/src/main/java/com/example/app/java/ExampleActivity.java) |

Record: drives imports, `AppDimensProvider` need, and whether resize runs in `compose.resize` or `code.resize`.

*→ Wait for answer, then ask 0.2.*

### 0.2 Screen Metric Qualifier (`DpQualifier`)

Which axis should `DpQualifier`-aware APIs use? (Affects `.sdpQualifier`, `.sdpScreen`, `.sspRotate`, power/screen branches, and their `code` mirrors.)

- **`SMALL_WIDTH` (default)** — smallest-width (swDP) baseline; correct for most phone/tablet layouts.
- **Explicit `WIDTH` / `HEIGHT` (or per-call-site mix)** — when design requires width-dp or height-dp branching. Read [COMPOSE-API-CONVENTIONS.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) and `DpQualifier` KDoc before proposing thresholds.

Record before suggesting any `.screen`, `.qualifier`, rotate, or power-curve APIs.

*→ Wait for answer, then ask 0.3.*

### 0.3 Task Type

Disambiguate first — these are **separate** workflows:
- **Scaling strategy** = global dimension curve (`sdp`, percent, fluid, …) → Phase 1.
- **Resize** = constraint-based fit (`autoResize*`, `ResizeBound`) → Phase 3.

Ask which applies:
- **Strategy selection only** — also ask: **manual** (user picks after seeing options) or **AI-proposed** (you propose, user confirms)? Warn that automatic proposals are more error-prone on mixed form factors and resize-heavy UIs.
- **Resize only** — still complete 0.1, 0.2, and 0.4; go directly to Phase 3.
- **Both** — run Phase 1, then Phase 3.

If automatic strategy mode: confirm each major screen or module after proposing, before bulk edits.

*→ Wait for answer, then ask 0.4.*

### 0.4 Scope

Full migration (app-wide) or partial? For partial scope, list concrete paths or identifiers (files, packages, screens, composables, XML layouts, specific dimensions) before editing.

*→ Wait for answer, then ask 0.5.*

### 0.5 Acceptance Criteria

For each major screen or module, ask for plain-language criteria (e.g., "no clipped titles on foldable inner display", "comfortable padding on 10-inch tablet"). Tie every recommendation explicitly to these.

*→ Once answered, proceed to the relevant phase(s).*

---

## Phase 1 — Scaling Strategy Selection

*Run only when Phase 0.3 includes strategy selection. Skip for resize-only tasks with unchanged surrounding curves.*

### Default: Scaled

**`compose.scaled` / `code.scaled`** — recommend this first. Linear scaling around a **300 dp** reference on the chosen axis.

| Variant | Typical use |
|---------|-------------|
| `.sdp` | General spacing, padding, corner radii |
| `.hdp` | Vertical rhythm, row heights |
| `.wdp` | Width-driven columns |
| `.ssp` | Text; use `hsp`/`wsp` for axis variants |
| `.sdpa` / `.hdpa` | Very wide or tall screens where plain scaled is too aggressive or too conservative |

In **manual mode**: present this default and ask which UI areas use it. Accept "everything scaled except …" rules.  
In **automatic mode**: propose per screen, confirm before bulk edits.

### Other Strategies

Use only when requirements or QA justify leaving scaled. **Before recommending any strategy below**, read its matching doc in `DOCUMENTATION/` — see [references/library-map.md](references/library-map.md) § "Strategy → doc". Pull trade-offs from that doc, not from memory.

| Strategy | Role | Typically when | Not ideal when |
|----------|------|----------------|----------------|
| **Percent** | Literal fraction of a screen axis | Sizes must track sw/w/h directly (hero = 90% w) | Only need "design at 300 dp" feel — use scaled |
| **Power** | Sublinear growth, softer on large screens | Large phones/tablets feel "too big" with linear | Need predictable linear mapping everywhere |
| **Fluid** | Band behavior between reference widths | Strong control inside a width band | One simple curve app-wide without band tuning |
| **Auto** | Breakpoint-style blend (linear + log past threshold) | Clear "phone vs tablet" knee in the curve | Need mathematically smooth everywhere |
| **Diagonal** | Curve on a diagonal screen metric | Ultrawide / non-standard aspect emphasis | Simple portrait phone layouts |
| **Fill** | Cover-like growth vs reference ("bold") | Visual dominance on large canvas | Risk oversized touch targets without QA |
| **Fit** | Contain-like growth vs reference (conservative) | Prefer smaller on large screens | Need aggressive use of space on tablets |
| **Interpolated** | Piecewise curve between configured points | Hand-tuned points from design spec | No intermediate data — high maintenance |
| **Logarithmic** | Log-shaped curve, dampens growth | Strong dampening needed | Need linear proportional feel |
| **Perimeter** | Perimeter-style metric in formula | Designs keyed to "frame" perception | No such requirement |
| **Density** | Classic density-style scaling | Matching legacy dp-to-physical expectations | Want screen-shape-aware curves |
| **Physical units** | mm / cm / in helpers | Real-world sizing (rulers, print-like UI) | Most Material layout — use scaled instead |
| **Resize** | *(not a global curve)* Largest discrete step in [min,max] fitting constraints | Auto-fit text or boxes inside known max | Whole-screen proportional layout |

---

## Phase 2 — Implementation Rules

Apply regardless of strategy or UI stack:

1. **Strategy isolation** — `compose.<strategy>` and `code.<strategy>` do not cross-import. One strategy per calculation path.
2. **`AppDimensProvider`** — required for Compose facilitators like `.sdpMode`, `.sdpScreen`, `.sspScreen` (see README).
3. **Config churn** — if the Activity is not recreated on handled config changes (rotation, font scale, density, `configChanges` in manifest), consider `DimenCache.invalidateOnConfigChange`.
4. **`code` hot paths** — prefer `Int`/`Float` receivers for sdp/hdp/wdp to avoid boxing.
5. **Nested Plain facilitators** — use `Dp`/`TextUnit` alternates in `*Plain` chains to avoid double-scaling (README + COMPOSE-API-CONVENTIONS).

---

## Phase 3 — Resize Work

*Run when the user touches `autoResize*` APIs, `ResizeBound`, `ResizeRangePx`, or `DimenResize`.*

Ask in order, waiting for each answer before continuing:

1. **Element type** — text (font size sweep, sp) or non-text component (heights, widths, square sizes, images, cards)?
2. **Constraint source** — Compose: must run inside `BoxWithConstraints` (max width/height in dp). Views/code: how is max px/dp obtained?
3. **Expected behavior** — fill to cap, shrink to fit, or prefer a size? Any min/max in dp, sp, axis percent (screen), or percent of inner box?
4. **Bounds semantics** — clarify `ResizeBound.FixedDp` / `FixedSp` / `Percent` (sw/w/h axis). For percent-in-container resize, clarify `AutoResizePercentBasis` (min/max side, width, height) and use `autoResizeTextSpPercent` (Compose) or `fittingTextSpPercentPx` (code). Remind: `resolveToPx` requires `density > 0`; invalid inputs are clamped (library KDoc).
5. **Approach check** — global proportional sizing across the screen is usually scaled/percent/fluid, not resize. Reserve resize for fit-to-container problems.

Reference: [DOCUMENTATION/resize.md](https://github.com/bodenberg/appdimens-dynamic/blob/3.1.3/DOCUMENTATION/resize.md)

---

## Phase 4 — Execution Checklist

- [ ] Agent Preflight completed for touched symbols.
- [ ] Phase 0 answered: stack, `DpQualifier` baseline, task type (strategy / resize / both), scope, acceptance criteria.
- [ ] Scaled presented as default; other strategies have matching `DOCUMENTATION/*.md` opened and trade-offs cited.
- [ ] `ignoreMultiWindows` / `*i` / `*ia` not surfaced to user.
- [ ] Docs or KDoc read for anything beyond trivial one-liners.
- [ ] Diffs are small and reviewable; aligned with upstream example patterns.
- [ ] Build / lint the touched module if available.

---

## Output Style

For every decision state: chosen strategy, package family (`compose.*` or `code.*`), axis (`sdp` vs `hdp` vs `wdp`), whether resize vs global scaling, and which acceptance criterion it satisfies. Prefer small, reviewable diffs aligned with existing project patterns.
