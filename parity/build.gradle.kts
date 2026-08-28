plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.appdimens.parity"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Local 3.2.0 library under test.
    implementation(project(":library"))
    // Legacy published artifact: io.github.bodenberg:appdimens-sdps:3.1.6 (com.appdimens.sdps.*).
    implementation(libs.appdimens.sdps.legacy)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
}