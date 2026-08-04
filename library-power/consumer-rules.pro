################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-power
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.power.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.power.** { public protected *; }
-keep class com.appdimens.dynamic.power.** { *; }
