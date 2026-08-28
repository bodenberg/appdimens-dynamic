pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "AppDimens Dynamic"
include(":app")
include(":library")
include(":library-bom")
include(":library-auto")
include(":library-density")
include(":library-diagonal")
include(":library-fill")
include(":library-fit")
include(":library-fluid")
include(":library-interpolated")
include(":library-logarithmic")
include(":library-percent")
include(":library-perimeter")
include(":library-power")
include(":library-resize")
include(":library-units")
include(":parity")

// EN Benchmark module (3.2.0 vs Concorrente 1 vs Concorrente 2).
// PT Módulo de benchmark (3.2.0 vs Concorrente 1 vs Concorrente 2).
include(":benchlab")
