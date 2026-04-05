################################################################################
# AppDimens Dynamic — proguard-rules.pro (library module)
#
# Scope: applied ONLY during the library's own build (minifyEnabled = true on
# the :library module). Does NOT affect apps that consume the AAR — that is
# consumer-rules.pro's job.
#
# With android.enableR8.fullMode=true in gradle.properties, R8 runs in full
# mode even here when minifyEnabled = true. Keep that in mind for every rule.
################################################################################


################################################################################
# 1. OPTIMIZATIONS
#    These are only meaningful when minifyEnabled = true on the :library module
#    itself. If your library build keeps minifyEnabled = false (the default for
#    AAR modules), R8 will ignore these passes entirely — the optimizations run
#    only in the consuming app's build instead.
################################################################################

# Run 5 optimisation rounds. Each pass may unlock new opportunities exposed by
# the previous one (e.g. an inlined method reveals a dead branch).
-optimizationpasses 5

# Allow R8 to widen access modifiers (private → package-private → public) when
# it needs to inline or merge classes across package boundaries. Safe for a
# library because the AAR is compiled before the app sees it.
-allowaccessmodification

# Explicit optimisation categories. "library/*" is critical for AAR builds:
# it tells R8 it may optimise across the library's own class boundary when the
# consuming app's classes are not yet on the classpath.
-optimizations code/simplification/*,code/removal/*,method/inlining/*,
               method/removal/*,field/removal/*,class/merging/*,
               class/unboxing/*,library/*


################################################################################
# 2. DEBUG ATTRIBUTES
#    Keep enough metadata for readable stack traces in crash reports.
#    SourceFile + LineNumberTable → exact crash lines.
#    Signature + InnerClasses + EnclosingMethod → Kotlin reflection, coroutines.
#    RuntimeVisible* → annotation processors and DI frameworks.
################################################################################

-keepattributes SourceFile,
                LineNumberTable,
                Signature,
                InnerClasses,
                EnclosingMethod,
                RuntimeVisibleAnnotations,
                RuntimeVisibleParameterAnnotations,
                AnnotationDefault,
                Exceptions

# Renames all SourceFile attributes to "AppDimens" in the obfuscated build so
# that stack traces are still groupable even when line numbers are stripped.
-renamesourcefileattribute AppDimens


################################################################################
# 3. KOTLIN METADATA
#    kotlin.Metadata is the annotation the Kotlin compiler writes on every
#    class/function. Without it, kotlinx.reflect, Compose tooling and some
#    annotation processors see only plain Java classes and break.
#
#    In R8 full mode, -keep class kotlin.** { *; } is deliberately NOT used —
#    it would block dead-code removal of the entire kotlin stdlib. Only
#    kotlin.Metadata is required.
################################################################################

-keep class kotlin.Metadata { *; }

# Kotlin stdlib intrinsics used at runtime for null checks and array ops.
# R8 full mode may treat these as dead if it cannot prove they are reachable.
-keep class kotlin.jvm.internal.** { *; }

# Coroutine continuation chains must survive obfuscation to keep readable
# async stack traces. The debug metadata class carries the frame names.
-keepclassmembernames class kotlinx.coroutines.** {
    volatile <fields>;
}
-keep class kotlinx.coroutines.internal.** { *; }


################################################################################
# 4. PARCELIZE / SERIALIZABLE
#    AppDimens does not currently expose Parcelable or Serializable types, but
#    these rules protect any subclass added in the future and also guard against
#    DataStore's internal use of Java serialization for edge cases.
################################################################################

-keepnames class * implements android.os.Parcelable
-keepnames interface * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable
-keepnames interface * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


################################################################################
# 5. DIMEN CACHE — @PublishedApi internal members
#
#    Kotlin `inline fun` is copy-pasted into the call-site at compile time.
#    The inlined body references @PublishedApi internal members of DimenCache
#    directly (factors, shards, CT_* constants, currentScale*, buildKey, etc.).
#
#    In R8 full mode, "internal" means nothing — R8 sees JVM access flags and
#    may decide those fields/methods are unused if it cannot trace every
#    call-site. Because the inline expansion happens in the CONSUMING APP's
#    bytecode (not here), R8 processing the library module will not see those
#    call-sites at all → it would silently strip the members.
#
#    We must keep every @PublishedApi internal member explicitly.
################################################################################

# The object itself and its nested classes must never be renamed or removed.
-keep class com.appdimens.dynamic.core.DimenCache { *; }
-keep class com.appdimens.dynamic.core.DimenCache$* { *; }
-keep class com.appdimens.dynamic.core.DimenCache$ScreenFactors { *; }
-keep class com.appdimens.dynamic.core.DimenCache$ShardWrapper { *; }
-keep class com.appdimens.dynamic.core.DimenCache$CacheStats { *; }

# CalcType / ValueType ordinals are encoded into 64-bit cache keys. Covered by
# DimenCache$* above — avoid separate -keepclassmembers enum { … $VALUES … }
# rules; R8 often reports "matches no class members" for **[] $VALUES on Kotlin.

# Padding fields (_p0.._pD in ShardWrapper, _p0.._p7 in ScreenFactors) exist
# solely to push objects into separate ARM64 cache lines. R8 full mode will
# remove them as "unused" unless we protect them explicitly.
-keepclassmembers class com.appdimens.dynamic.core.DimenCache$ShardWrapper {
    <fields>;
}
-keepclassmembers class com.appdimens.dynamic.core.DimenCache$ScreenFactors {
    <fields>;
}

# AspectRatioLookup object + its fastLn inline — same reasoning as DimenCache.
-keep class com.appdimens.dynamic.core.AspectRatioLookup { *; }
-keep class com.appdimens.dynamic.core.AspectRatioLookupKt { *; }

# DesignScaleConstants: pure const vals, inlined by Kotlin compiler. Keep the
# object so R8 does not remove them before the consuming app's inline expansion.
-keep class com.appdimens.dynamic.core.DesignScaleConstants { *; }


################################################################################
# 6. PUBLIC API SURFACE
#
#    Everything under .code.** and .compose.** is part of the developer-facing
#    API. R8 must not rename or remove any public or protected member here,
#    because the app code calls these by name (Kotlin extension properties,
#    Compose composables, Java static methods).
################################################################################

-keep public class com.appdimens.dynamic.code.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.** { public protected *; }
-keep public class com.appdimens.dynamic.common.** { public protected *; }


################################################################################
# 7. COMMON ENUMS
#    DpQualifier, Inverter, Orientation, UiModeType, UnitType ordinals are
#    encoded into cache keys and builder chains. Same risk as CalcType above.
################################################################################

-keepclassmembers enum com.appdimens.dynamic.common.* {
    <fields>;
    <methods>;
}

# AutoResizePercentBasis — full keep (Kotlin private $VALUES + R8 member rules).
-keep class com.appdimens.dynamic.compose.resize.AutoResizePercentBasis { *; }


################################################################################
# 8. CORE PLUMBING — Compose integration
#
#    CompositionLocals, AppDimensProvider, stamps — used by Compose runtime
#    reflection to wire @Composable trees. R8 full mode can strip these if
#    their referencing composables are not directly visible in this build unit.
################################################################################

-keep class com.appdimens.dynamic.core.CompositionLocalsKt { *; }
-keep class com.appdimens.dynamic.core.ComposeDimenRememberKt { *; }
-keep class com.appdimens.dynamic.core.ComposeRememberStampsKt { *; }
-keep class com.appdimens.dynamic.core.DimenCalculationPlumbing { *; }
-keep class com.appdimens.dynamic.core.PercentSpaceMathKt { *; }
-keep class com.appdimens.dynamic.core.ResizeMathKt { *; }


################################################################################
# 9. SEALED CLASS — ResizeBound
#
#    In R8 full mode, subclasses of a sealed class that are never directly
#    instantiated in the CURRENT build unit are treated as dead code and removed.
#    The instantiation happens in the consuming app (e.g. resizePercentSw(16f)),
#    which R8 cannot see during the library's own build.
################################################################################

-keep class com.appdimens.dynamic.core.ResizeBound { *; }
-keep class com.appdimens.dynamic.core.ResizeBound$* { *; }
-keep class com.appdimens.dynamic.core.ResizeBoundKt { *; }


################################################################################
# 10. DATASTORE + COROUTINES
#     DataStore Preferences uses class names for the datastore file name
#     (preferencesDataStore delegate). Obfuscating the class name here would
#     change the on-disk file path between builds, corrupting the cache.
################################################################################

-keep class androidx.datastore.** { *; }

# Proto/Preferences DataStore uses reflection internally for type tokens.
-dontwarn com.google.protobuf.**
-keep class com.google.protobuf.** { *; }


################################################################################
# 11. SUPPRESS KNOWN SAFE WARNINGS (full mode generates more notes than compat)
#
#     -dontnote does NOT remove protection — it only silences informational
#     messages about classes that exist in the android.jar but are absent from
#     the compile-time classpath. These are safe to ignore for a library AAR.
################################################################################

-dontnote android.**
-dontnote androidx.**
-dontnote kotlin.**
-dontnote kotlinx.**

# sun.misc.Unsafe is referenced by AtomicLongArray internally (JVM internals).
# AppDimens does not call it directly. Suppress the note; do NOT keep it —
# keeping sun.misc.Unsafe would prevent R8 from optimising atomic operations.
-dontnote sun.misc.**
-dontwarn sun.misc.**


################################################################################
# 12. DEBUG OUTPUT  (comment out before shipping a release AAR)
#
#     These files are written relative to the project root during R8 processing.
#     NEVER put these in consumer-rules.pro — they would write to the app
#     developer's build directory on every release build, potentially breaking
#     CI pipelines that enforce a clean output directory.
################################################################################

# -printseeds     build/outputs/mapping/release/seeds.txt
# -printmapping   build/outputs/mapping/release/mapping.txt
# -printconfiguration build/outputs/mapping/release/configuration.txt
# -printusage     build/outputs/mapping/release/usage.txt
# -verbose
