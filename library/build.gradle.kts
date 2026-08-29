import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
//import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
//import org.jetbrains.dokka.gradle.tasks.DokkaGenerateTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
//import java.net.URI
//import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.vanniktech.maven.publish)
    //alias(libs.plugins.dokka.jetbrains)
}

val isJitPack = System.getenv("JITPACK") == "true"
        || System.getenv("jitpack") == "true"
        || System.getenv("CI") == "true"
        || System.getenv("ci") == "true"

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic", providers.gradleProperty("appdimens.version").orElse("3.2.0").get())

    configure(
        AndroidSingleVariantLibrary()
    )

    pom {
        name.set("AppDimens Dynamic — Core + Scaled")
        description.set("AppDimens Dynamic core: shared cache/plumbing plus the default scaled strategy (sdp/hdp/wdp/ssp). Additional strategies are separate artifacts (appdimens-dynamic-<strategy>).")
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

/* dokka {
    dokkaPublications.html {
        moduleName.set("AppDimens SDP, HDP, WDP: Scalable Width and Height Dimensions")
        outputDirectory.set(rootProject.layout.projectDirectory.dir("DOCUMENTATION2"))
        suppressInheritedMembers.set(true)
        // AGP + Android Dokka plugin register overlapping "jvm" and "release" source sets
        // (same src/main paths). Document only the explicit "main" source set.
        // https://github.com/Kotlin/dokka/issues/3701
        dokkaSourceSets.configureEach {
            suppress.set(name != "main")
        }
        dokkaSourceSets.register("main") {
            sourceRoots.from(file("src/main/java"), file("src/main/kotlin"))
            documentedVisibilities.set(
                setOf(
                    VisibilityModifier.Public,
                    VisibilityModifier.Internal,
                    VisibilityModifier.Protected,
                    VisibilityModifier.Private,
                    VisibilityModifier.Package
                )
            )
            skipEmptyPackages.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(false)
            enableAndroidDocumentationLink.set(true)
            enableJdkDocumentationLink.set(true)
            enableKotlinStdLibDocumentationLink.set(true)
            failOnWarning.set(false)
            val localPropsFile = rootProject.file("local.properties")
            val sdkDirFromLocal: String? = if (localPropsFile.exists()) {
                val props = Properties()
                props.load(localPropsFile.inputStream())
                props.getProperty("sdk.dir")
            } else null
            val sdkDir = sdkDirFromLocal ?: System.getenv("ANDROID_SDK_ROOT") ?: System.getenv("ANDROID_HOME")
            val compileSdkVersion = try {
                android.compileSdk.toString()
            } catch (ignored: Exception) {
                null
            }
            if (sdkDir != null && compileSdkVersion != null) {
                val androidJarPath = file("${sdkDir}/platforms/android-$compileSdkVersion/android.jar")
                if (androidJarPath.exists()) {
                    classpath.from(files(androidJarPath))
                    logger.lifecycle("Dokka: added android.jar to classpath: $androidJarPath")
                } else {
                    logger.warn("Dokka: android.jar not found at $androidJarPath — Dokka pode continuar com símbolos não resolvidos.")
                }
            } else {
                logger.warn("Dokka: Android SDK não encontrado (local.properties/sdk.dir ou ANDROID_SDK_ROOT/ANDROID_HOME) ou compileSdk não disponível.")
            }
            val compileCp = configurations.findByName("compileClasspath") ?: configurations.getByName("debugCompileClasspath")
            classpath.from(compileCp)
            configurations.findByName("releaseRuntimeClasspath")?.let { classpath.from(it) }
            externalDocumentationLinks {
                create("android") {
                    url.set(URI("https://developer.android.com/reference/"))
                    packageListUrl.set(URI("https://developer.android.com/reference/package-list"))
                }
                create("androidx") {
                    url.set(URI("https://developer.android.com/reference/androidx/"))
                    packageListUrl.set(URI("https://developer.android.com/reference/androidx/package-list"))
                }
            }
        }
        pluginsConfiguration.html {
            footerMessage.set("Bodenberg")
            homepageLink.set("https://github.com/bodenberg")
        }
    }
}

tasks.withType<DokkaGenerateTask>().configureEach {
    doFirst {
        System.setProperty("java.awt.headless", "true")
    }
}*/

android {
    namespace = "com.appdimens.dynamic"
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
    // Compose is compileOnly-scoped on purpose: the library compiles against the
    // BOM's Compose but NEVER ships or pins a Compose version. The consumer's
    // Compose is the single runtime (composables must share one Recomposer), so
    // version skew / "class or function not found" errors can't happen regardless
    // of the Compose version the dev chooses. The library still builds standalone
    // (R8 / release AAR) using its own compile classpath. Consumers (Compose apps)
    // always provide Compose themselves at compile and runtime.
    compileOnly(platform(libs.androidx.compose.bom))
    compileOnly(libs.androidx.compose.ui)
    compileOnly(libs.androidx.compose.runtime)

    implementation(libs.androidx.window)
    implementation(libs.androidx.annotation)

    //dokkaPlugin(libs.android.documentation.plugin)
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