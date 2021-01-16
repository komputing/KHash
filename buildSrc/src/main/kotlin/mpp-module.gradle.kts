import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("multiplatform")
    id("com.github.ben-manes.versions")
}

kotlin {
    //explicitApi()
    targets {
        jvm()
        js(BOTH) {
            nodejs {
                testTask {
                    useMocha {
                        timeout = "5s"
                    }
                }
            }
        }
    }
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("com.github.komputing:khex:1.0.0")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        matching { it.name.contains("test", ignoreCase = true) }
            .forEach {
                it.languageSettings.enableLanguageFeature("InlineClasses")
            }
    }
}

/**
 * The Gradle Versions Plugin is another Gradle plugin to discover dependency updates
 * plugins.id("com.github.ben-manes.versions")
 * Run it with $ ./gradlew --scan dependencyUpdates
 * https://github.com/ben-manes/gradle-versions-plugin
 * **/
tasks.named("dependencyUpdates", DependencyUpdatesTask::class).configure {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version)
    }
    checkConstraints = true
}