################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-diagonal
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keep public class com.appdimens.dynamic.code.diagonal.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.diagonal.** { public protected *; }
-keep class com.appdimens.dynamic.diagonal.** { *; }
