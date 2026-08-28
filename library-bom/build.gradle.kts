import com.vanniktech.maven.publish.JavaPlatform

plugins {
    `java-platform`
    alias(libs.plugins.vanniktech.maven.publish)
}

val libraryVersion: String = providers.gradleProperty("appdimens.version").orElse("3.1.9.1").get()
val isJitPack = System.getenv("JITPACK") == "true"
        || System.getenv("jitpack") == "true"
        || System.getenv("CI") == "true"
        || System.getenv("ci") == "true"

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api("io.github.bodenberg:appdimens-dynamic:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-auto:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-density:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-diagonal:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-fill:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-fit:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-fluid:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-interpolated:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-logarithmic:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-percent:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-perimeter:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-power:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-resize:$libraryVersion")
        api("io.github.bodenberg:appdimens-dynamic-units:$libraryVersion")
    }
}

mavenPublishing {
    coordinates("io.github.bodenberg", "appdimens-dynamic-bom", libraryVersion)
    configure(JavaPlatform())
    pom {
        name.set("AppDimens Dynamic — BOM")
        description.set(
            "Bill of Materials for AppDimens Dynamic — version constraints for " +
                "appdimens-dynamic and appdimens-dynamic-<strategy> modules."
        )
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
