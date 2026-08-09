################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-power — proguard-rules.pro
#
# LIVE at AAR build time (release isMinifyEnabled = true): the satellite is
# PRE-SHRUNK and PRE-OPTIMIZED, never obfuscated (-dontobfuscate — renaming
# happens once, in the consuming app's own R8 pass).
#
# The public API is fully retained here because published AARs must not strip
# members apps call by name; core rules arrive transitively from appdimens-
# dynamic's consumer-rules.pro (merged into THIS module's R8 run as well).
# Internal helpers (e.g. com.appdimens.dynamic.auto.** internals, if any) are
# NOT kept: they stay when reachable and are shrink candidates when dead.
################################################################################

-dontobfuscate

-optimizationpasses 10
-allowaccessmodification

-keep public class com.appdimens.dynamic.code.power.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.power.** { public protected *; }

-keep class kotlin.Metadata { *; }

-dontnote android.**
-dontnote androidx.**
-dontnote kotlin.**
-dontnote kotlinx.**
-dontwarn sun.misc.**
-dontnote sun.misc.**