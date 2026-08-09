################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-perimeter
# Core R8 rules arrive transitively via appdimens-dynamic (main).
################################################################################

-keepnames public class com.appdimens.dynamic.code.perimeter.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.perimeter.** { public protected *; }

################################################################################
# Satellite internal helpers are NOT kept (static-only references, no
# reflection) so the app's R8 pass may shrink them freely.
################################################################################
