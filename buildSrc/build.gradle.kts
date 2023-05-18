import java.io.File
import java.util.*

plugins {
    idea
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

val rootProperties = Properties().apply {
    load(File(projectDir, "../gradle.properties").inputStream())
}

repositories {
    gradlePluginPortal()
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = false
    }
}

dependencies {
    implementation(kotlin("gradle-plugin", rootProperties.getProperty("kgp")))
    implementation(
        "com.github.ben-manes", "gradle-versions-plugin",
        rootProperties.getProperty("plugin.com.github.ben-manes.versions")
    )
}