################################################################################
# 1. OPTIMIZATIONS
################################################################################

-optimizationpasses 5
-allowaccessmodification
-optimizations code/*,method/*,field/*,class/*,library/*

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