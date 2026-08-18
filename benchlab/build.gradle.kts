/**
 * EN Benchmark module — compares AppDimens Dynamic 3.1.9 (local)
 *    vs Concorrente 1 (legacy published artifact) vs Concorrente 2 on real devices.
 *
 * PT Módulo de benchmark — compara AppDimens Dynamic 3.1.9 (local) vs
 *    Concorrente 1 (artefato legado publicado) vs Concorrente 2 em dispositivos reais.
 */
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.benchlab"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.benchlab"
        minSdk = 25
        targetSdk = 37
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystoreFile = rootProject.file("test_keystore.jks")

    val createTestKeystore by tasks.registering(Exec::class) {
        onlyIf { !keystoreFile.exists() }
        val keytoolBin = File(System.getProperty("java.home"), "bin/keytool")
        commandLine(
            if (keytoolBin.exists()) keytoolBin.absolutePath else "keytool",
            "-genkeypair", "-v",
            "-keystore", keystoreFile.absolutePath,
            "-storetype", "PKCS12",
            "-alias", "test",
            "-keyalg", "RSA", "-keysize", "2048", "-validity", "10000",
            "-storepass", "123456", "-keypass", "123456",
            "-dname", "CN=AppDimens Android CI, OU=CI, O=AppDimens, L=Unspecified, ST=Unspecified, C=BR"
        )
        outputs.file(keystoreFile)
    }
    tasks.matching { it.name == "preBuild" }.configureEach {
        dependsOn(createTestKeystore)
    }

    signingConfigs {
        create("sample") {
            storeFile = keystoreFile
            storePassword = System.getenv("SAMPLE_STORE_PASSWORD") ?: "123456"
            keyAlias = "test"
            keyPassword = System.getenv("SAMPLE_KEY_PASSWORD") ?: "123456"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("sample")
        }
        debug {
            signingConfig = signingConfigs.getByName("sample")
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

dependencies {
    // AppDimens Dynamic 3.1.9 — main library under test (core + scaled).
    api(project(":library"))

    // Concorrente 1 — legacy published artifact: io.github.bodenberg:appdimens-sdps:3.1.6 (com.appdimens.sdps.*).
    implementation(libs.appdimens.sdps.legacy)

    // Concorrente 2 — Android artifact (network.chaintech:sdp-ssp-compose-multiplatform-android:1.0.7).
    implementation("network.chaintech:sdp-ssp-compose-multiplatform-android:1.0.7")

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
    testImplementation(libs.junit)
}
