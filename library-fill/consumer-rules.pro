################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-fill
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.fill.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.fill.** { public protected *; }
-keep class com.appdimens.dynamic.fill.** { *; }
