################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-density
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keepnames public class com.appdimens.dynamic.code.density.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.density.** { public protected *; }

################################################################################
# Satellite internal helpers are NOT kept (static-only references, no
# reflection) so the app's R8 pass may shrink them freely.
################################################################################
