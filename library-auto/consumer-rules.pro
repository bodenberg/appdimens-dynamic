################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-auto
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.auto.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.auto.** { public protected *; }
-keep class com.appdimens.dynamic.auto.** { *; }
