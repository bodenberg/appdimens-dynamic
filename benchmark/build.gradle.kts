/**
 * EN Macrobenchmark module. Generates the baseline profile for :app (and, by
 *    copy, for the :library AAR) and can also run regular macrobenchmarks
 *    against the app on a physical device (API 30+).
 * PT Módulo de macrobenchmark. Gera o baseline profile para :app (e, por cópia,
 *    para o AAR :library) e também executa macrobenchmarks contra o app em um
 *    device físico (API 30+).
 */
plugins {
    alias(libs.plugins.android.test)
}

android {
    namespace = "com.example.app.benchmark"
    compileSdk = 37

    defaultConfig {
        minSdk = 25
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"

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
        debug {
            // EN Must match the target app's signature or instrumentation is denied
            //    (SecurityException: signature does not match the target).
            // PT Deve corresponder à assinatura do app alvo ou a instrumentação é
            //    negada (SecurityException: assinatura não corresponde ao alvo).
            signingConfig = signingConfigs.getByName("sample")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // EN BaselineProfileRule lives in benchmark-macro-junit4 (since 1.3.x; the old
    //    benchmark-baseline-profile artifact stopped at 1.2.x). The profile is
    //    collected on-device to /data/local/tmp/baseline-prof.txt and copied manually
    //    into app/src/main/baselineProfiles/ and library/src/main/baselineProfiles/.
    // PT BaselineProfileRule vive em benchmark-macro-junit4 (desde 1.3.x; o antigo
    //    artifact benchmark-baseline-profile parou em 1.2.x). O perfil é coletado no
    //    device em /data/local/tmp/baseline-prof.txt e copiado manualmente para
    //    app/src/main/baselineProfiles/ e library/src/main/baselineProfiles/.
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.junit)

    // EN Needed by BaselineProfileRule on API < 34 to wait for profile install.
    // PT Necessário para BaselineProfileRule em API < 34 aguardar a instalação do perfil.
    implementation(libs.androidx.profileinstaller)

    // EN Pin the same lifecycle version the app uses. Without it, mixed transitive
    //    versions end up inside the test APK and the runner crashes on startup with
    //    StartupException/NoSuchFieldError (Lifecycle$State).
    // PT Fixa a mesma versão de lifecycle usada pelo app. Sem isso, versões
    //    transitivas misturadas entram no APK de teste e o runner quebra ao iniciar
    //    com StartupException/NoSuchFieldError (Lifecycle$State).
    implementation(libs.androidx.lifecycle.runtime.ktx)
}