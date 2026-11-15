package android.app
open class Activity(@JvmField var mw: Boolean) : android.content.ContextWrapper(null) { val isInMultiWindowMode: Boolean get() = mw }
