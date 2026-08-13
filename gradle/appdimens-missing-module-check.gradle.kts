/**
 * AppDimens — missing satellite module check (configuration-cache safe).
 *
 * Scans this module's Kotlin/Java sources for imports of
 * `com.appdimens.dynamic.compose.<strategy>` / `code.<strategy>` and fails if the
 * matching Maven artifact (or `project(":library-<strategy>")`) is not declared.
 *
 * Apply from an Android app/library `build.gradle.kts`:
 *
 *   apply(from = rootProject.file("gradle/appdimens-missing-module-check.gradle.kts"))
 *
 * Version is read from the `appdimens.version` Gradle property (see `gradle.properties`).
 */
import java.util.regex.Pattern

val appDimensVersion: String =
    providers.gradleProperty("appdimens.version").orElse("3.1.8").get()

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

val principalSegments = setOf("scaled", "plain", "common", "core")

val sourceDirPaths = listOf(
    "src/main/java",
    "src/main/kotlin",
    "src/debug/java",
    "src/debug/kotlin",
    "src/release/java",
    "src/release/kotlin",
).map { layout.projectDirectory.dir(it) }

val buildScriptFiles = listOf("build.gradle.kts", "build.gradle")
    .map { layout.projectDirectory.file(it) }

tasks.register("checkAppDimensModules") {
    group = "verification"
    description =
        "Fails when sources import an AppDimens strategy package without the matching dependency."

    val version = appDimensVersion
    val artifacts = strategyArtifacts
    val principals = principalSegments
    val sources = sourceDirPaths
    val scripts = buildScriptFiles

    inputs.files(sources.map { it.asFileTree }).optional()
    inputs.files(scripts.map { it.asFile }).optional()
    inputs.property("appdimens.version", version)

    doLast {
        val importPattern: Pattern = Pattern.compile(
            """import\s+com\.appdimens\.dynamic\.(compose|code)\.([a-zA-Z0-9_]+)\b"""
        )
        val projectDepPattern: Pattern = Pattern.compile(
            """project\s*\(\s*["']:library(-[a-zA-Z0-9_]+)?["']\s*\)"""
        )
        val mavenDepPattern: Pattern = Pattern.compile(
            """["']io\.github\.bodenberg:(appdimens-dynamic(?:-[a-zA-Z0-9_]+)?)(?::[^"']+)?["']"""
        )

        val presentArtifacts = linkedSetOf<String>()
        scripts.map { it.asFile }.filter { it.isFile }.forEach { script ->
            val text = script.readText()
            val pm = projectDepPattern.matcher(text)
            while (pm.find()) {
                val suffix = pm.group(1)
                if (suffix == null) presentArtifacts += "appdimens-dynamic"
                else presentArtifacts += "appdimens-dynamic$suffix"
            }
            val mm = mavenDepPattern.matcher(text)
            while (mm.find()) presentArtifacts += mm.group(1)
        }

        val errors = linkedSetOf<String>()
        val reportedSegments = linkedSetOf<String>()

        sources.map { it.asFile }.filter { it.isDirectory }.forEach { dir ->
            dir.walkTopDown()
                .filter { it.isFile && (it.extension == "kt" || it.extension == "java") }
                .forEach { file ->
                    file.useLines { lines ->
                        lines.forEach { line ->
                            val m = importPattern.matcher(line)
                            if (!m.find()) return@forEach
                            val segment = m.group(2)
                            if (segment in principals) return@forEach
                            val artifact = artifacts[segment] ?: return@forEach
                            if (artifact in presentArtifacts) return@forEach
                            if (!reportedSegments.add(segment)) return@forEach
                            val importFq = line.trim().removePrefix("import").trim().removeSuffix(";")
                            errors +=
                                "Missing AppDimens module for import …$segment… — add: " +
                                    """implementation("io.github.bodenberg:$artifact:$version")""" +
                                    " (import: $importFq)"
                        }
                    }
                }
        }

        if (errors.isNotEmpty()) {
            error(errors.joinToString(separator = "\n"))
        }
    }
}

tasks.matching { it.name.startsWith("compile") && it.name.contains("Kotlin") }.configureEach {
    dependsOn("checkAppDimensModules")
}
