package android.content
open class Context
open class ContextWrapper(private val base: Context?) : Context() { val baseContext: Context get() = base ?: this }
