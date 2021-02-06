import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    kotlin("multiplatform")
    id("com.github.ben-manes.versions")
}

kotlin {
    explicitApi()
    targets {
        jvm()
        js(BOTH) {
            compilations {
                this.forEach { compilation ->
                    compilation.compileKotlinTask.kotlinOptions.apply {
                        sourceMap = true
                        moduleKind = "umd"
                        metaInfo = true
                        sourceMapEmbedSources = "always"

                        if (compilation.name == "main") main = "noCall"
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha {
                        timeout = "120s"
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
                implementation("com.github.fullkomnun:khex:${Versions.khex}") // TODO: remove usage of maven local for KHex
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

tasks {
    named<Test>("jvmTest") {
        filter {
            isFailOnNoMatchingTests = false
        }
        testLogging {
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }

    listOf("Legacy", "Ir").forEach { jsCompiler ->

        named<KotlinJsTest>("js${jsCompiler}NodeTest") {
            testLogging {
                events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
                showExceptions = true
                exceptionFormat = TestExceptionFormat.FULL
            }
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