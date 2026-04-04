# AppDimens Dynamic — Consumer R8/ProGuard rules
# These rules are automatically applied to apps that depend on this library.

# Public API surface: keep all public/protected members in user-facing packages
-keep public class com.appdimens.dynamic.code.** { public protected *; }
-keep public class com.appdimens.dynamic.compose.** { public protected *; }
-keep public class com.appdimens.dynamic.common.** { public protected *; }

# Core public API: DimenCache public statics, CompositionLocals, ResizeBound, ResizeMath
-keep public class com.appdimens.dynamic.core.DimenCache { public static *; }
-keep public class com.appdimens.dynamic.core.CompositionLocalsKt { *; }
-keep public class com.appdimens.dynamic.core.ResizeBound { *; }
-keep public class com.appdimens.dynamic.core.ResizeBound$* { *; }
-keep public class com.appdimens.dynamic.core.ResizeMathKt { public *; }

# Allow R8 to inline/remove internal helpers (padding fields, shards, etc.)
-allowoptimization class com.appdimens.dynamic.core.**
