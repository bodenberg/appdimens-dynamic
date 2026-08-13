# R8, full mode, and ProGuard rules (AppDimens Dynamic)

This guide explains **why** and **how** to use R8/minify, **what changes** when you turn on **full mode**, and **which ProGuard rule file does what** in this repository. It is written for developers who are new to R8/ProGuard but want accurate, technical detail.

---


## Consumer rules per artifact

Each published AAR ships its own `consumer-rules.pro`:

- **`appdimens-dynamic`** — `common`, scaled `code`/`compose`, plus the few proven-specific core rules (cache-key enums, `ResizeBound`, `ScreenFactors` padding, `kotlin.Metadata`)
- **`appdimens-dynamic-<strategy>`** — that strategy’s public `code` / `compose` API (core rules arrive transitively)

Gradle merges consumer rules from the modules you depend on.

## Why use minify and R8?

**R8** runs when you enable **code shrinking** (often called “minify”) on a **release** (or other) build type. In practice you get three related benefits:

1. **Smaller APK/AAB** — Unused classes, methods, and fields from your app **and** from dependencies can be removed (*tree shaking*). Users download less; install footprint is smaller.
2. **Faster runtime on device** — R8 can **inline** calls, simplify control flow, and remove indirection. For CPU-heavy libraries (like dimension scaling on hot UI paths), that can show up as **lower nanoseconds per call** in benchmarks. This repo documents that effect in [`PERFORMANCE.md`](PERFORMANCE.md) and [`PERFORMANCE-COMPARATIVE.md`](PERFORMANCE-COMPARATIVE.md) (release + minify + R8 vs debug without minify).
3. **Obfuscation** — Short names replace original class/member names in the shipped bytecode. This is **not** encryption, but it makes casual reverse engineering harder and complements Play App Signing.

**R8 full mode** (`android.enableR8.fullMode=true`) asks the toolchain for **stricter** optimizations than “compat” mode. You may get **even better** size and speed, but **more** code paths are candidates for removal or renaming—so **correct ProGuard rules matter more**.

**Trade-off in one sentence:** you exchange a bit of build time and configuration care for smaller binaries and often faster, harder-to-read release code.

---

## How to enable and use it

### 1. Turn on minify for your **application** module

In the module that produces the installable APK (usually `:app`), set **`minifyEnabled`** (Kotlin DSL: **`isMinifyEnabled`**) to **`true`** for the build type you ship to users—typically **`release`**.

Example pattern (your file may differ slightly):

```kotlin
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            // Optional: remove unused resources after code shrink
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

In **this** repository, the sample app already does this in [`app/build.gradle.kts`](app/build.gradle.kts). Your own project needs the same idea on **your** `app` module.

**What you do day to day:** build the **release** variant (e.g. **Build → Generate Signed Bundle / APK**, or `./gradlew assembleRelease`), install that build on a **real device**, and run through critical flows. **Debug** installs usually keep `minifyEnabled = false` so iteration stays fast—that is normal.

### 2. (Optional) Enable **R8 full mode** for the whole project

In the root [`gradle.properties`](gradle.properties), uncomment:

```properties
android.enableR8.fullMode=true
```

Sync Gradle, then **rebuild release** and **retest**. Full mode does not replace step 1; it changes **how aggressively** R8 optimizes **when** minify runs.

### 3. Rules: what you edit vs what you get for free

- **Your app:** put app-specific keeps in **`app/proguard-rules.pro`** (reflection, JNI, serialization libraries, etc.).
- **AppDimens Dynamic:** each AAR you depend on ships its own `consumer-rules.pro`, merged by Gradle automatically. Core/scaled rules come from `appdimens-dynamic`; strategy rules come from each `appdimens-dynamic-<strategy>` AAR.

If a release build **crashes** only after turning on minify or full mode, open **R8 / ProGuard** mapping or stack traces, then adjust **keeps** (see sections below).

---

## Risks specific to R8 full mode

Full mode is **optional**. If you enable it (step 2 under **How to enable and use it** above), expect the same categories of issues as minify—just **more likely** until rules are tight. R8 then applies **stricter** shrinking, optimization, and obfuscation. That is usually good for size and speed, but it can:

- Remove or rename members that look “unused” to R8 but are still required at runtime (for example **Kotlin `inline` bodies** expanded in the **app** that reference **`internal` APIs** marked `@PublishedApi`).
- Strip **padding fields** that exist only to avoid **false sharing** on multi-core CPUs, silently hurting hot-path performance.
- Break **Compose** wiring (`CompositionLocal`, providers) if keep rules are incomplete.

**You must keep the library’s consumer rules in sync** and test a **release** build (minify on) on a real device. If something crashes with `NoSuchFieldError`, `NoSuchMethodError`, or missing composables, the fix is almost always an extra `-keep` or a corrected rule scope—not “disable R8 forever.”

See also: [`library/PERFORMANCE.md`](library/PERFORMANCE.md) (cache notes) and the performance reports [`PERFORMANCE.md`](PERFORMANCE.md) / [`PERFORMANCE-COMPARATIVE.md`](PERFORMANCE-COMPARATIVE.md).

---

## R8 and ProGuard rules in plain terms

**R8** is the Android toolchain step that can **shrink** (delete unused code), **optimize** (inline, simplify), and **obfuscate** (rename classes/members) your compiled bytecode.

**ProGuard rule files** (`.pro`) are text instructions such as “do not remove this class” (`-keep`) or “allow these optimizations” (`-optimizations`). Gradle merges **default rules**, **your app rules**, and **rules shipped inside AARs** (`consumer-rules`) into one configuration for the app’s release build.

Think of it as: **R8 reads your app + dependencies + merged rules → outputs a smaller, faster, possibly renamed program.** If a rule is wrong, the program still builds but can fail at runtime.

---

## 1. Library module: `library/proguard-rules.pro` (and each satellite's)

**When it runs:** At **AAR build time** for the **release** variant. Every AppDimens
library module (`:library`, `:library-auto`, …) sets **`isMinifyEnabled = true`**
on its **release** build type, so R8 now processes the library code **before it is
published**.

**What this file is for:**

- Aggressive **optimization**: `-optimizationpasses 10` (more optimizer
  iterations) and `-allowaccessmodification` (relaxed visibility so R8 can
  inline across boundaries).
- **`-dontobfuscate`**: the AAR is **shrunk and optimized, never renamed**
  (AndroidX-style). Renaming still happens once, in the **consuming app's own
  R8 pass**, where the app's mapping file applies. This is what keeps stack
  traces sane and eliminates every double-obfuscation edge case.
- **Keeps** = the cross-module contract, now with **`allowoptimization`**
  (since 3.1.8 — a bare `-keep` also forbids R8 from *optimizing* the kept
  members, and the hot path lives inside them):
  - public API packages (`code.**`, `compose.**`, `common.**`) — kept, so a
    published AAR never strips public members, but their bodies stay
    optimizable;
  - **the whole `core.**` engine** — satellites compile against it and inline
    its `@PublishedApi` members; a member removed or renamed here would fail
    only at consumer runtime, so none may be removed, but all may be
    optimized.

**Result:** every app that depends on AppDimens gets pre-optimized bytecode —
even in **debug** builds without minify — and the app's own release R8 pass
shrinks the remaining surface per-app (via `consumer-rules.pro`).

> **3.1.8 note:** persistence was removed from `DimenCache`, and the legacy
> `androidx.datastore.**` keep blocks have already been dropped from the `.pro`
> files — no `datastore` references remain in any module rules.
>
> **3.1.8 specialized kernels:** The new `resolveSdpPx`, `resolveSdpaPx`, etc.
> are `@PublishedApi internal inline` — R8 inlines them at call sites, so no
> additional keep rules are needed. The `ensureConfigWatcher` listener is
> registered via `ComponentCallbacks2` and kept by the `Application` reference.

---

## 2. Consumer rules: `library/consumer-rules.pro`

**When it runs:** Automatically. This file is registered with:

```kotlin
consumerProguardFiles("consumer-rules.pro")
```

Gradle **packages** it into the **AAR**. When an app enables **minify/R8** on **release**, those rules are **merged** into the app’s R8 configuration **without** the app author copying anything.

**What this file is for:**

- The **minimum** set of rules so the **published library** stays correct inside **any** consuming app under R8 (including **full mode**).
- Since 3.1.8 the file is **almost empty by design**: the `@PublishedApi` internals reached through inlined extension bodies are expanded into the app's bytecode at compile time, so R8 discovers them as plain direct references and keeps them automatically. All reflection/JNI-driven keeps were removed (`kotlin.jvm.internal.**`, generic `Parcelable`/`Serializable`, `androidx.tracing`/`androidx.profileinstaller` — the latter belong to the libraries that own them).
- Remaining specific rules, each with a demonstrated runtime or performance failure behind it:
  - public API surfaces (`-keepnames`: the app's R8 may drop library members the app does not use, but may never rename them);
  - `kotlin.Metadata` (reflective reads by Compose tooling / `kotlinx.reflect`);
  - enums whose **ordinals are packed into cache keys** (renaming breaks dispatch);
  - the `ResizeBound` sealed hierarchy (R8 full mode can eliminate uninstantiated sealed subclasses → `ClassNotFoundException`);
  - `ScreenFactors` **padding fields** (write-only by design; a plain `-keep` prevents R8 from stripping the ARM64 false-sharing protection).

**Design choice:** Consumer rules intentionally **do not** force app-wide policies like `-optimizationpasses` on the app—those remain **app** decisions. They also deliberately **do not** keep satellite-internal helper packages: all satellite references are plain static bytecode (no reflection), so the app's R8 shrinks them freely.

**Beginner mental model:**  
`proguard-rules.pro` (library) = “when we minify **the library artifact** itself.”  
`consumer-rules.pro` = “when **someone else’s app** minifies **their** APK that contains our AAR.”

---

## 3. App module: `app/proguard-rules.pro`

**When it runs:** When the **`:app`** module has **`minifyEnabled = true`** (release in [`app/build.gradle.kts`](app/build.gradle.kts)).

**What this file is for:**

- Rules for **this sample application**: extra `-keep` for **Kotlin Serialization**, JNI-style keeps, and general release tuning.
- It works **together** with:
  - Android Gradle **default** ProGuard/R8 files (`proguard-android-optimize.txt`, etc.).
  - **Merged consumer rules** from dependencies (including AppDimens Dynamic’s `consumer-rules.pro`).

**Important:** Your **own** app will have **its own** `proguard-rules.pro`. The sample under `app/` is **not** a substitute for rules your code may need (reflection, Gson, Hilt, etc.). AppDimens only ships what the library needs via **consumer** rules; everything else is your responsibility.

---

## Quick checklist (release + R8)

1. The **published AARs are already shrink+optimized** (release library builds run R8 with `-dontobfuscate`); the checks below still apply to the **consumer app**'s own pass.
2. Build and run **`release`** (or minified) on a device—not only `debug`.
3. Exercise screens that use **Compose** extensions, **resize** APIs, and **configuration changes**.
4. If you enable **`android.enableR8.fullMode=true`**, repeat the same tests; failures usually mean a missing or overly narrow `-keep`. (On AGP 9+ full mode is always on and this flag is a no-op.)
5. For performance comparisons, see [`PERFORMANCE.md`](PERFORMANCE.md): **debug without minify** vs **release with minify + R8** are different environments; do not mix numbers without reading the methodology note.

---

*AppDimens Dynamic — R8 / ProGuard reference*
