plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.app"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 25
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("sample") {
            val keystore = rootProject.file("test_keystore.jks")
            if (keystore.exists()) {
                storeFile = keystore
                storePassword = System.getenv("SAMPLE_STORE_PASSWORD") ?: "123456"
                keyAlias = "test"
                keyPassword = System.getenv("SAMPLE_KEY_PASSWORD") ?: "123456"
            }
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("sample")
        }
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("sample")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
        resValues = true
    }
}

dependencies {
    // Main artifact = core + scaled. Satellites are opt-in (no ALL aggregator).
    api(project(":library"))
    api(project(":library-auto"))
    api(project(":library-density"))
    api(project(":library-diagonal"))
    api(project(":library-fill"))
    api(project(":library-fit"))
    api(project(":library-fluid"))
    api(project(":library-interpolated"))
    api(project(":library-logarithmic"))
    api(project(":library-percent"))
    api(project(":library-perimeter"))
    api(project(":library-power"))
    api(project(":library-resize"))
    api(project(":library-units"))

    // Maven Central (release consumers) — preferred with BOM:
    // implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.1.7"))
    // implementation("io.github.bodenberg:appdimens-dynamic")
    // implementation("io.github.bodenberg:appdimens-dynamic-percent")
    // …

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Fail compile when strategy imports lack the matching AppDimens dependency.
apply(from = rootProject.file("gradle/appdimens-missing-module-check.gradle.kts"))
