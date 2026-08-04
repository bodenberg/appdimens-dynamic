################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-interpolated
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.interpolated.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.interpolated.** { public protected *; }
-keep class com.appdimens.dynamic.interpolated.** { *; }
