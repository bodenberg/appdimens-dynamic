################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-fluid
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.fluid.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.fluid.** { public protected *; }
-keep class com.appdimens.dynamic.fluid.** { *; }
