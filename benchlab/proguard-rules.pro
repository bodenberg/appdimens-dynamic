################################################################################
# BenchLab — ProGuard / R8 rules
################################################################################

# Keep the benchmark activity and all Compose UI
-keep class com.example.benchlab.** { *; }
-dontwarn com.example.benchlab.**

# AppDimens Dynamic library
-keep class com.appdimens.dynamic.** { *; }
-dontwarn com.appdimens.dynamic.**

# Concorrente 1 (legacy published artifact)
-keep class com.appdimens.sdps.** { *; }
-dontwarn com.appdimens.sdps.**

# Concorrente 2
-keep class network.chaintech.** { *; }
-dontwarn network.chaintech.**

# Compose
-dontwarn androidx.compose.**

# Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
