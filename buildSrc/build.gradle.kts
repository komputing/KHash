plugins {
    idea
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
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
    implementation(kotlin("gradle-plugin", "${property("kgp")}"))
    implementation("com.github.ben-manes", "gradle-versions-plugin", "${property("plugin.com.github.ben-manes.versions")}")
}