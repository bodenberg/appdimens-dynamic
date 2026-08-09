################################################################################
# AppDimens Dynamic satellite — appdimens-dynamic-auto
# Core R8 rules arrive transitively via appdimens-dynamic (main).
#
# Public API only. Internal helpers (strategy-private packages, e.g.
# com.appdimens.dynamic.auto.**) are intentionally NOT kept: every reference is
# static bytecode (no reflection), so the app's R8 pass may shrink them freely.
################################################################################

-keepnames public class com.appdimens.dynamic.code.auto.** { public protected *; }
-keepnames public class com.appdimens.dynamic.compose.auto.** { public protected *; }
