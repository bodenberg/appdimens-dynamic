################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-power
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keepnames public class com.appdimens.dynamic.code.power.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.power.** { public protected *; }

################################################################################
# Satellite internal helpers are NOT kept (static-only references, no
# reflection) so the app's R8 pass may shrink them freely.
################################################################################
