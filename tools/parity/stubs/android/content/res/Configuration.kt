package android.content.res
class Configuration {
    @JvmField var screenWidthDp = 0
    @JvmField var screenHeightDp = 0
    @JvmField var smallestScreenWidthDp = 0
    @JvmField var densityDpi = 160
    @JvmField var fontScale = 1f
    @JvmField var orientation = ORIENTATION_UNDEFINED
    @JvmField var uiMode = 0
    companion object { const val ORIENTATION_UNDEFINED = 0; const val ORIENTATION_PORTRAIT = 1; const val ORIENTATION_LANDSCAPE = 2 }
}
