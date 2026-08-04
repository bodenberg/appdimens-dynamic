/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens-dynamic.git
 *
 * EN Maps strategy package segments to Maven coordinates so missing-satellite
 * failures produce actionable dependency hints (Gradle check + runtime helpers).
 *
 * PT Mapeia pacotes de estratégia → coordenadas Maven para mensagens acionáveis.
 */
package com.appdimens.dynamic.core

/**
 * EN Known strategy modules that ship separately from the principal
 * `appdimens-dynamic` artifact. Package segment is the last component of
 * `com.appdimens.dynamic.compose.<segment>` / `code.<segment>`.
 *
 * PT Módulos de estratégia publicados à parte do artefacto principal.
 */
object MissingModule {
    const val GROUP_ID: String = "io.github.bodenberg"
    const val PRINCIPAL_ARTIFACT: String = "appdimens-dynamic"
    const val BOM_ARTIFACT: String = "appdimens-dynamic-bom"

    /** EN package segment → Maven artifactId. PT segmento → artifactId. */
    @JvmField
    val ARTIFACT_BY_PACKAGE: Map<String, String> = mapOf(
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
        // scaled / plain / common / core live in the principal artifact
        "scaled" to PRINCIPAL_ARTIFACT,
        "plain" to PRINCIPAL_ARTIFACT,
    )

    /**
     * EN Builds the consumer dependency line for [packageSegment] at [version].
     * PT Linha `implementation(...)` para o segmento e versão dados.
     */
    @JvmStatic
    fun dependencyHint(packageSegment: String, version: String): String {
        val artifact = ARTIFACT_BY_PACKAGE[packageSegment]
            ?: "appdimens-dynamic-$packageSegment"
        return """implementation("$GROUP_ID:$artifact:$version")"""
    }

    /**
     * EN Message used by the Gradle missing-module check (and optional runtime asserts).
     * PT Mensagem do check Gradle (e asserts opcionais em runtime).
     */
    @JvmStatic
    fun missingImportMessage(importFqName: String, packageSegment: String, version: String): String =
        "Missing AppDimens module for import …$packageSegment… — add: ${dependencyHint(packageSegment, version)}" +
            " (import: $importFqName)"

    /**
     * EN Throws if [packageSegment] is a known satellite that is not listed in [presentArtifacts]
     * (artifactIds without version). Useful for custom runtime diagnostics.
     *
     * PT Lança se o satélite conhecido não estiver em [presentArtifacts].
     */
    @JvmStatic
    fun requireArtifact(
        packageSegment: String,
        presentArtifacts: Set<String>,
        version: String,
        importFqName: String = "com.appdimens.dynamic.*.$packageSegment",
    ) {
        val artifact = ARTIFACT_BY_PACKAGE[packageSegment] ?: return
        if (artifact == PRINCIPAL_ARTIFACT) return
        if (artifact !in presentArtifacts) {
            throw IllegalStateException(missingImportMessage(importFqName, packageSegment, version))
        }
    }
}
