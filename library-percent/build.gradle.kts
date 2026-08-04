import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
}

val libraryVersion: String = providers.gradleProperty("appdimens.version").orElse("3.1.6").get()
val isJitPack = System.getenv("JITPACK") == "true"
        || System.getenv("jitpack") == "true"
        || System.getenv("CI") == "true"
        || System.getenv("ci") == "true"

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic-percent", libraryVersion)
    configure(AndroidSingleVariantLibrary())
    pom {
        name.set("AppDimens Dynamic — Percent")
        description.set("Percent / screen-fraction scaling strategy (psdp, space* / code.percent).")
        url.set("https://github.com/bodenberg/appdimens-dynamic")
        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("bodenberg")
                name.set("Jean Bodenberg")
                email.set("jean.bodenberg2@outlook.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/bodenberg/appdimens-dynamic.git")
            developerConnection.set("scm:git:ssh://github.com/bodenberg/appdimens-dynamic.git")
            url.set("https://github.com/bodenberg/appdimens-dynamic")
        }
    }
    if (!isJitPack) {
        publishToMavenCentral()
        signAllPublications()
    }
}

android {
    namespace = "com.appdimens.dynamic.percent"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    api(project(":library"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.window)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.core)
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
