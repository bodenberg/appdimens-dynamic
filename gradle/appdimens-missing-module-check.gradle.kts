/**
 * AppDimens — missing satellite module check.
 *
 * Scans this module's Kotlin/Java sources for imports of
 * `com.appdimens.dynamic.compose.<strategy>` / `code.<strategy>` and fails if the
 * matching Maven artifact (or project dependency) is not on the compile classpath.
 *
 * Apply from an Android app/library `build.gradle.kts`:
 *
 *   apply(from = rootProject.file("gradle/appdimens-missing-module-check.gradle.kts"))
 *
 * Version is read from the `appdimens.version` Gradle property (see `gradle.properties`).
 */
import java.util.regex.Pattern

val appDimensVersion: String =
    providers.gradleProperty("appdimens.version").orElse("3.1.6").get()

val strategyArtifacts = mapOf(
    "auto" to "appdimens-dynamic-auto",
    "density" to "appdimens-dynamic-density",
    "diagonal" to "appdimens-dynamic-diagonal",
    "fill" to "appdimens-dynamic-fill",
    "fit" to "appdimens-dynamic-fit",
    "fluid" to "appdimens-dynamic-fluid",
    "interpolated" to "appdimens-dynamic-interpolated",
    "logarithmic" to "appdimens-dynamic-logarithmic",
    "percent" to "appdimens-dynamic-percent",
    "perimeter" to "appdimens-dynamic-perimeter",
    "power" to "appdimens-dynamic-power",
    "resize" to "appdimens-dynamic-resize",
    "units" to "appdimens-dynamic-units",
)

// scaled / plain / common / core ship in the principal artifact — not flagged.
val principalSegments = setOf("scaled", "plain", "common", "core")

val importPattern: Pattern = Pattern.compile(
    """import\s+com\.appdimens\.dynamic\.(compose|code)\.([a-zA-Z0-9_]+)\b"""
)

tasks.register("checkAppDimensModules") {
    group = "verification"
    description =
        "Fails when sources import an AppDimens strategy package without the matching dependency."

    val sourceDirs = project.files(
        "src/main/java",
        "src/main/kotlin",
        "src/debug/java",
        "src/debug/kotlin",
        "src/release/java",
        "src/release/kotlin",
    )

    inputs.files(sourceDirs).optional()
    inputs.property("appdimens.version", appDimensVersion)

    doLast {
        val presentArtifacts = linkedSetOf<String>()
        val presentProjects = linkedSetOf<String>()

        configurations.matching {
            it.isCanBeResolved && (
                it.name.contains("CompileClasspath", ignoreCase = true) ||
                    it.name.contains("compileClasspath", ignoreCase = true)
                )
        }.forEach { cfg ->
            runCatching {
                cfg.resolvedConfiguration.resolvedArtifacts.forEach { art ->
                    presentArtifacts += art.moduleVersion.id.name
                }
            }
            cfg.dependencies.forEach { dep ->
                when (dep) {
                    is ProjectDependency -> {
                        val path = dep.dependencyProject.path.removePrefix(":")
                        presentProjects += path
                        // Map :library-percent → appdimens-dynamic-percent
                        if (path == "library") {
                            presentArtifacts += "appdimens-dynamic"
                        } else if (path.startsWith("library-")) {
                            presentArtifacts += "appdimens-dynamic-" + path.removePrefix("library-")
                        }
                    }
                    else -> {
                        val name = dep.name
                        if (name.startsWith("appdimens-dynamic")) {
                            presentArtifacts += name
                        }
                    }
                }
            }
        }

        val errors = mutableListOf<String>()
        val seen = linkedSetOf<Pair<String, String>>() // segment to import

        sourceDirs.files.filter { it.isDirectory }.forEach { dir ->
            dir.walkTopDown()
                .filter { it.isFile && (it.extension == "kt" || it.extension == "java") }
                .forEach { file ->
                    file.useLines { lines ->
                        lines.forEach { line ->
                            val m = importPattern.matcher(line)
                            if (!m.find()) return@forEach
                            val segment = m.group(2)
                            if (segment in principalSegments) return@forEach
                            val artifact = strategyArtifacts[segment] ?: return@forEach
                            val importFq = line.trim().removePrefix("import").trim().removeSuffix(";")
                            if (!seen.add(segment to importFq)) return@forEach

                            val projectName = "library-$segment"
                            val ok = artifact in presentArtifacts ||
                                projectName in presentProjects ||
                                "library-$segment" in presentProjects
                            if (!ok) {
                                errors +=
                                    "Missing AppDimens module for import …$segment… — add: " +
                                        """implementation("io.github.bodenberg:$artifact:$appDimensVersion")""" +
                                        " (import: $importFq)"
                            }
                        }
                    }
                }
        }

        if (errors.isNotEmpty()) {
            error(errors.distinct().joinToString(separator = "\n"))
        }
    }
}

// Wire before Kotlin/Java compilation when those tasks exist.
pluginManager.withPlugin("com.android.application") {
    tasks.matching { it.name.startsWith("compile") && it.name.contains("Kotlin") }.configureEach {
        dependsOn("checkAppDimensModules")
    }
}
pluginManager.withPlugin("com.android.library") {
    tasks.matching { it.name.startsWith("compile") && it.name.contains("Kotlin") }.configureEach {
        dependsOn("checkAppDimensModules")
    }
}
pluginManager.withPlugin("org.jetbrains.kotlin.android") {
    tasks.matching { it.name.startsWith("compile") && it.name.contains("Kotlin") }.configureEach {
        dependsOn("checkAppDimensModules")
    }
}
