################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-fit
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.fit.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.fit.** { public protected *; }
-keep class com.appdimens.dynamic.fit.** { *; }
