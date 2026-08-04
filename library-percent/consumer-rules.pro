################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-percent
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.percent.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.percent.** { public protected *; }
-keep class com.appdimens.dynamic.percent.** { *; }
