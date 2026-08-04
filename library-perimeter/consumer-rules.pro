################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-perimeter
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.perimeter.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.perimeter.** { public protected *; }
-keep class com.appdimens.dynamic.perimeter.** { *; }
