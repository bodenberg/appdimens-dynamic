################################################################################
# AppDimens Dynamic — proguard-rules.pro (library module)
#
# THIS FILE IS LIVE: the :library release build type runs R8 at AAR build time
# (isMinifyEnabled = true in build.gradle.kts). The published AAR is therefore
# PRE-SHRUNK and PRE-OPTIMIZED — every consumer app gets the optimized code even
# before its own R8 pass (and without the app author configuring anything).
#
# Design decisions:
#
#  1. -dontobfuscate
#     Names are never renamed at library build time (AndroidX-style). Renaming
#     happens once, in the CONSUMER APP's own R8 pass, where the app's own rules
#     and mapping file apply. This keeps the AAR debuggable, avoids shipping a
#     mapping contract to consumers, and eliminates every double-obfuscation
#     failure mode (NoSuchFieldError / NoSuchMethodError across AAR boundaries).
#
#  2. Aggressive optimizer: R8 may still SHRINK (delete unreachable code) and
#     OPTIMIZE (inline, constant-fold, merge, reorder) anything not listed
#     below. -optimizationpasses drives multiple optimizer iterations for the
#     hot DimenCache/plumbing paths; -allowaccessmodification lets R8 relax
#     access modifiers so it can inline across visibility boundaries.
#
#  3. Keeps = the whole cross-module contract:
#     - public .code.** / .compose.** / .common.** API (apps call these by name)
#     - everything in .core.** (satellite AARs reference core directly AND via
#       @PublishedApi bodies inlined at their compile time — a member removed
#       here would only fail at satellite runtime, so none may be removed)
#     - enums whose ordinals are packed into cache keys
#     - kotlin.Metadata (read reflectively by Compose tooling / kotlinx.reflect)
#
#  consumer-rules.pro is a SEPARATE contract: it runs in the CONSUMING APP's R8
#  pass and deliberately allows the app to drop unused library members. Do not
#  weaken this file to match that one, and vice versa.
################################################################################

-dontobfuscate

-optimizationpasses 10
-allowaccessmodification


################################################################################
# 1. PUBLIC API SURFACE — classes apps call by name (Kotlin extensions,
#    @Composable functions, Java-style statics). Full -keep: a published AAR
#    must not strip public members; only the consuming app may decide they are
#    unused (it does, via -keepnames in consumer-rules.pro).
################################################################################

-keep public class com.appdimens.dynamic.code.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.** { public protected *; }
-keep public class com.appdimens.dynamic.common.** { public protected *; }


################################################################################
# 2. CORE ENGINE — never shrunk at library build time
#
#    Satellite AARs (:library-auto .. :library-units) compile against these
#    classes and inline @PublishedApi bodies from DimenCache into their own
#    bytecode at compile time. If THIS build removed a member, the satellites
#    would still compile (they compile against the unshrunk classes) and only
#    crash at app runtime — the worst possible failure mode. So the entire
#    engine is retained here; consumers shrink it per-app with -keepnames.
################################################################################

-keep class com.appdimens.dynamic.core.** { *; }


################################################################################
# 3. KOTLIN METADATA — kotlin.Metadata is read reflectively (Compose tooling,
#    kotlinx.reflect). Attribute retention comes from the AGP default file;
#    keeping the annotation CLASS is required for reflective reads.
################################################################################

-keep class kotlin.Metadata { *; }


################################################################################
# 4. SUPPRESS NOTES — android.jar stubs are on the library classpath but some
#    implementation classes are not; all safe to ignore (messages only).
################################################################################

-dontnote android.**
-dontnote androidx.**
-dontnote kotlin.**
-dontnote kotlinx.**
-dontwarn sun.misc.**
-dontnote sun.misc.**