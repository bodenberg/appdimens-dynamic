################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-density
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.density.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.density.** { public protected *; }
-keep class com.appdimens.dynamic.density.** { *; }
