################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-units
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.units.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.units.** { public protected *; }
-keep class com.appdimens.dynamic.units.** { *; }
