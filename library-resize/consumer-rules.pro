################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-resize
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.resize.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.resize.** { public protected *; }
-keep class com.appdimens.dynamic.resize.** { *; }
