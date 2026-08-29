import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
}

val libraryVersion: String = providers.gradleProperty("appdimens.version").orElse("3.2.0").get()
val isJitPack = System.getenv("JITPACK") == "true"
        || System.getenv("jitpack") == "true"
        || System.getenv("CI") == "true"
        || System.getenv("ci") == "true"

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic-power", libraryVersion)
    configure(AndroidSingleVariantLibrary())
    pom {
        name.set("AppDimens Dynamic — Power")
        description.set("Power-curve scaling strategy (pwsdp / code.power).")
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
    namespace = "com.appdimens.dynamic.power"
    compileSdk = 37
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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
    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.compose.runtime)
    implementation(libs.androidx.window)
    compileOnly(libs.androidx.activity.compose)
    compileOnly(libs.androidx.compose.ui.graphics)
    compileOnly(libs.androidx.compose.ui.tooling.preview)
    compileOnly(libs.androidx.compose.material3)
    compileOnly(libs.androidx.compose.material.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.compose.ui)
    testImplementation(libs.androidx.compose.runtime)
    testImplementation(libs.androidx.compose.ui.graphics)
    testImplementation(libs.androidx.compose.ui.tooling.preview)
    testImplementation(libs.androidx.compose.material3)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
