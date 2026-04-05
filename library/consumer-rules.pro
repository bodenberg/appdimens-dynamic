################################################################################
# AppDimens Dynamic — consumer-rules.pro
#
# This file is bundled INSIDE the AAR and automatically merged into the
# consuming app's R8/ProGuard run. The developer does NOT need to copy
# anything manually — these rules apply transparently when they add:
#
#   implementation("io.github.bodenberg:appdimens-dynamic:x.y.z")
#
# Design principle: keep the minimum surface needed for correctness.
# Do NOT impose -optimizationpasses or -allowaccessmodification here —
# those are app-level decisions, not library decisions.
################################################################################


################################################################################
# 1. PUBLIC API SURFACE
#
#    Every class under .code.**, .compose.** and .common.** is part of the
#    developer-facing API. R8 must not rename or remove any public or protected
#    member, because the consuming app calls them by name (Kotlin extension
#    properties, @Composable functions, Java-style static methods).
#
#    -keep (not -keepnames) because full mode may also _remove_ members that it
#    determines are unreachable from the app's entry points. We want to prevent
#    both removal and renaming.
################################################################################

-keep public class com.appdimens.dynamic.code.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.** { public protected *; }
-keep public class com.appdimens.dynamic.common.** { public protected *; }


################################################################################
# 2. DIMENCACHE — @PublishedApi internal members
#
#    Kotlin `inline fun` expands into the consuming app's bytecode at compile
#    time. The inlined body references @PublishedApi internal members of
#    DimenCache directly: factors, shards, CT_* int constants, buildKey(),
#    getOrPutInternal(), currentScale, currentArMultiplier, etc.
#
#    Without these keeps, R8 full mode sees those members referenced only from
#    the app's expanded bytecode — not from any non-inline path — and may decide
#    they are dead in the library's class files, removing them before the app
#    gets a chance to use them. The result is NoSuchFieldError / NoSuchMethod-
#    Error at runtime.
#
#    We keep the entire DimenCache object and its nested classes rather than
#    listing members one by one, so that future additions to the @PublishedApi
#    surface are automatically protected without a consumer-rules update.
################################################################################

-keep class com.appdimens.dynamic.core.DimenCache { *; }
-keep class com.appdimens.dynamic.core.DimenCache$* { *; }

# ScreenFactors padding fields (_p0.._p7): R8 full mode strips @JvmField fields
# it identifies as write-only (never read by name). These fields are never read —
# their only purpose is to occupy memory and prevent CPU false sharing on ARM64.
# Without them, DimenCache's hot-path performance degrades silently.
-keepclassmembers class com.appdimens.dynamic.core.DimenCache$ScreenFactors {
    <fields>;
}

# ShardWrapper padding fields (_p0.._pD): same reasoning.
-keepclassmembers class com.appdimens.dynamic.core.DimenCache$ShardWrapper {
    <fields>;
}

# CalcType / ValueType ordinals are packed into 64-bit cache keys. They are
# nested enums under DimenCache — already fully covered by the DimenCache$*
# rule above (no separate enum member rules: R8 treats **[] $VALUES as
# non-matching on Kotlin enums and reports "matches no class members").


################################################################################
# 3. COMMON ENUMS
#
#    DpQualifier, Inverter, Orientation, UiModeType, UnitType ordinals are
#    encoded into cache keys and used in when-expressions throughout the
#    builder chain. Renaming or reordering entries causes wrong dispatch and
#    silent scaling errors that are extremely hard to debug.
################################################################################

# Use <fields>/<methods> instead of Java-style values/valueOf/$VALUES patterns:
# Kotlin stores $VALUES privately; the $ in **[] $VALUES confuses R8's matcher.
-keepclassmembers enum com.appdimens.dynamic.common.* {
    <fields>;
    <methods>;
}

# Ordinals used in resize math — full keep so private enum synthetics survive.
-keep class com.appdimens.dynamic.compose.resize.AutoResizePercentBasis { *; }


################################################################################
# 4. SEALED CLASS — ResizeBound
#
#    R8 full mode eliminates sealed subclasses it never sees instantiated in the
#    current analysis scope. ResizeBound.FixedDp, FixedSp and Percent are all
#    instantiated inside the library's own helper functions (resizeFixedDp, etc.)
#    which the app calls — but R8 may not trace through those constructors when
#    processing the app if the helpers are inlined away differently.
#    Keeping the sealed hierarchy costs < 200 bytes and prevents runtime ClassNot-
#    FoundException in when-expressions over ResizeBound.
################################################################################

-keep class com.appdimens.dynamic.core.ResizeBound { *; }
-keep class com.appdimens.dynamic.core.ResizeBound$* { *; }
-keep class com.appdimens.dynamic.core.ResizeBoundKt { *; }


################################################################################
# 5. CORE PLUMBING — Compose integration
#
#    CompositionLocals are resolved by the Compose runtime at runtime using the
#    object's identity. AppDimensProvider and LocalUiModeType must survive both
#    shrinking and renaming so that CompositionLocalProvider can wire them.
#    ComposeRememberStamps and ComposeDimenRemember are referenced from the
#    inlined Composable extensions — same @PublishedApi risk as DimenCache.
################################################################################

-keep class com.appdimens.dynamic.core.CompositionLocalsKt { *; }
-keep class com.appdimens.dynamic.core.ComposeDimenRememberKt { *; }
-keep class com.appdimens.dynamic.core.ComposeRememberStampsKt { *; }
-keep class com.appdimens.dynamic.core.DimenCalculationPlumbing { *; }
-keep class com.appdimens.dynamic.core.ResizeMathKt { *; }
-keep class com.appdimens.dynamic.core.PercentSpaceMathKt { *; }
-keep class com.appdimens.dynamic.core.AspectRatioLookup { *; }
-keep class com.appdimens.dynamic.core.AspectRatioLookupKt { *; }
-keep class com.appdimens.dynamic.core.DesignScaleConstants { *; }


################################################################################
# 6. KOTLIN METADATA
#
#    kotlin.Metadata is the annotation the Kotlin compiler writes on every
#    class. Without it, kotlinx.reflect, Compose tooling, and some annotation
#    processors treat the library's classes as plain Java and break. Kotlin's
#    own gradle plugin normally adds this rule, but explicit is safer.
################################################################################

-keep class kotlin.Metadata { *; }
-keep class kotlin.jvm.internal.** { *; }


################################################################################
# 7. PARCELIZE / SERIALIZABLE
#
#    AppDimens does not currently expose Parcelable or Serializable public types,
#    but these rules protect any future addition and guard against DataStore's
#    own serialization edge cases. -keepnames (not -keep) is sufficient here:
#    we only need to prevent renaming, not removal, for classes the app never
#    instantiates directly.
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
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


################################################################################
# 8. DATASTORE
#
#    DataStore uses the class name to derive the on-disk preferences file name.
#    Obfuscating DimenCache would change the file path between builds, causing
#    the app to lose persisted cache data silently on every update.
################################################################################

-keep class androidx.datastore.** { *; }


################################################################################
# 9. SUPPRESS NOTES — full mode is noisier than compat mode
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
