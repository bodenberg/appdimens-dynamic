################################################################################
# 1. OPTIMIZATIONS
#
# AGP 9 runs R8 in full mode, where ALL optimization types (code, method,
# field, class, library) are already enabled — the legacy -optimizations flag
# is ignored, so it is not listed here. What still matters is the number of
# optimizer iterations (-optimizationpasses; default 1) — more passes squeeze
# the hot dimension-scaling paths further for a small build-time cost —
# and -allowaccessmodification (enabled by default in full mode; repeated here
# to document intent) so R8 may inline across visibility boundaries.
################################################################################

-optimizationpasses 10
-allowaccessmodification

################################################################################
# 2. KEEPS
################################################################################

-keepattributes Annotation,Exceptions,LineNumberTable,Signature,InnerClasses,EnclosingMethod,RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,AnnotationDefault
-keep class kotlin.Metadata { *; }

-keepnames class * implements android.os.Parcelable
-keepnames interface * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepnames interface * implements java.io.Serializable

################################################################################
# 4. KOTLINX SERIALIZATION
################################################################################

-keep @kotlinx.serialization.Serializable class **
-keepclassmembers class ** { @kotlinx.serialization.Serializable *; }
-keepclassmembers class **$serializer { public static ** INSTANCE; }
-keepclassmembernames class ** {
    @kotlinx.serialization.Serializable <fields>;
}

-keepclasseswithmembernames,includedescriptorclasses class * { native <methods>; }
-keep class sun.misc.Unsafe.** { *; }

################################################################################
# 8. LOGS DEBUG
################################################################################
-printseeds build/outputs/mapping/release/seeds.txt
-printmapping build/outputs/mapping/release/mapping.txt
-printconfiguration build/outputs/mapping/release/configuration.txt
-printusage build/outputs/mapping/release/usage.txt
-verbose
################################################################################
# 9. LEGACY SDPS 3.1.6 (com.appdimens.sdps.*) — comparison benchmark target.
#    The published artifact ships its own consumer rules; these extras keep the
#    exact legacy bytecode semantics (reflection-free, resources-based lookup)
#    alive in the release minified variant.
################################################################################

-keep class com.appdimens.sdps.** { *; }
-dontwarn com.appdimens.sdps.**
