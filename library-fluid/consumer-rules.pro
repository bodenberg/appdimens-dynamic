################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-fluid
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keepnames public class com.appdimens.dynamic.code.fluid.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.fluid.** { public protected *; }

################################################################################
# Satellite internal helpers are NOT kept (static-only references, no
# reflection) so the app's R8 pass may shrink them freely.
################################################################################
