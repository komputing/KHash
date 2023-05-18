import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("multiplatform")
    id("com.github.ben-manes.versions")
    id("maven-publish")
}

val darwinTargets = arrayOf(
    "macosX64", "macosArm64",
    "iosArm64", "iosX64", "iosSimulatorArm64",
    "tvosArm64", "tvosX64", "tvosSimulatorArm64",
    "watchosArm32", "watchosArm64", "watchosX64", "watchosSimulatorArm64",
)
val linuxTargets = arrayOf("linuxX64", "linuxArm64")
val mingwTargets = arrayOf("mingwX64")
val nativeTargets = linuxTargets + darwinTargets + mingwTargets

kotlin {
    explicitApi()
    targets {
        jvm {
            compilations.all {
                kotlinOptions.jvmTarget = "11"
            }
        }
        js(IR) {
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
                        timeout = "300s"
                    }
                }
            }
        }
        for (target in nativeTargets) {
            targets.add(presets.getByName(target).createTarget(target))
        }
    }
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("com.github.komputing.khex:extensions:${Versions.khex}")
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
    withType<AbstractTestTask> {
        testLogging {
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
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

System.getenv("GITHUB_REPOSITORY")?.let { githubRepo ->
    val (owner, repoName) = githubRepo.split('/').map(String::toLowerCase)
    group = "com.github.$owner.$repoName"
    version = System.getProperty("version")
    publishing {
        repositories {
            maven {
                name = "github"
                url = uri("https://maven.pkg.github.com/$githubRepo")
                credentials(PasswordCredentials::class)
            }
        }
    }
}
