################################################################################
# AppDimens Dynamic — principal consumer-rules.pro (appdimens-dynamic)
#
# Bundled INSIDE the AAR and merged into the consuming app's R8/ProGuard run when:
#
#   implementation("io.github.bodenberg:appdimens-dynamic:x.y.z")
#
# Satellite AARs (appdimens-dynamic-<strategy>) ship their own consumer-rules.pro
# for that strategy's public API.
#
# Design principle (3.1.9): keep ONLY what is proven necessary. Direct bytecode
# references — including the @PublishedApi internals reached through inlined
# function bodies, which are expanded into the app's bytecode at compile time —
# are discovered by R8 full mode on its own (Android recommendation: keep rules
# are for reflection / JNI / name-based access, not for direct calls).
#   https://developer.android.com/topic/performance/app-optimization/keep-rules-overview
#
# Everything below exists because a real runtime failure (or a deliberate
# performance contract) was demonstrated without it. Do NOT add package-wide
# keeps "for safety" — they silently inflate every consumer app's DEX.
################################################################################


################################################################################
# 1. PUBLIC API SURFACE (principal = common + scaled + plain packages present here)
#
#    Satellites keep their own packages in their own AARs. -keepnames (not
#    -keep): unreachable members may still be removed, but names used by
#    Java/Kotlin call sites that resolved at compile time are never renamed
#    (mapping-file consumers and non-inlined binaries depend on it).
################################################################################

-keepnames public class com.appdimens.dynamic.code.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.** { public protected *; }
-keepnames public class com.appdimens.dynamic.common.** { public protected *; }


################################################################################
# 2. KOTLIN METADATA
#
#    kotlin.Metadata is the annotation the Kotlin compiler writes on every
#    class. Without it, kotlinx.reflect, Compose tooling, and some annotation
#    processors treat the library's classes as plain Java and break. Kotlin's
#    own gradle plugin normally adds this rule, but explicit is safer.
################################################################################

-keep class kotlin.Metadata { *; }


################################################################################
# 3. CACHE-KEY ENUMS
#
#    DpQualifier, Inverter, UiModeType, UnitType ordinals are encoded into
#    cache keys and used in when-expressions throughout the builder chain.
#    Renaming entries causes wrong dispatch and silent scaling errors. Use
#    <fields>/<methods> instead of Java-style values/valueOf/$VALUES patterns:
#    Kotlin stores $VALUES privately and the $ confuses R8's matcher.
################################################################################

-keepclassmembers enum com.appdimens.dynamic.common.* {
    <fields>;
    <methods>;
}

# AutoResizePercentBasis ordinals are used in resize math.
-keepnames class com.appdimens.dynamic.core.AutoResizePercentBasis { *; }


################################################################################
# 4. SEALED CLASS — ResizeBound
#
#    R8 full mode eliminates sealed subclasses it never sees instantiated in
#    the current analysis scope; the resize helpers instantiate the subclasses
#    indirectly and a lost branch surfaces as ClassNotFoundException in the
#    when-expression. Keeping the sealed hierarchy costs < 200 bytes.
################################################################################

-keepnames class com.appdimens.dynamic.core.ResizeBound { *; }
-keepnames class com.appdimens.dynamic.core.ResizeBound$* { *; }
-keepnames class com.appdimens.dynamic.core.ResizeBoundKt { *; }


################################################################################
# 5. ScreenFactors PADDING FIELDS (_p0.._p7)
#
#    R8 full mode strips @JvmField fields it identifies as write-only. These
#    fields are never read by name — their only purpose is to occupy memory and
#    prevent CPU false sharing on ARM64. Losing them silently undoes the
#    padding contract without any crash, so this rule is kept on purpose.
#    (Everything else on DimenCache/plumbing is reachable by direct reference
#    from inlined bytecode and needs no keep rule.)
################################################################################

-keepclassmembers class com.appdimens.dynamic.core.DimenCache$ScreenFactors {
    <fields>;
}


################################################################################
# 6. SUPPRESS NOTES — full mode is noisier than compat mode
#
#    -dontnote SUPPRESSES MESSAGES ONLY. It does not remove any protection.
#    These notes appear because android.jar stubs are present in the library
#    classpath but some implementation classes are not. All are safe to ignore.
################################################################################

-dontnote android.**
-dontnote androidx.**
-dontnote kotlin.**
-dontnote kotlinx.**
-dontwarn sun.misc.**
-dontnote sun.misc.**