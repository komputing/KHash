buildscript {
    repositories {
        mavenLocal()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}



subprojects {
    // a trick to allow gradual conversion of modules to MPP
    afterEvaluate {
        if (!plugins.hasPlugin("mpp-module")) {
            group = "org.komputing"

            plugins.apply("kotlin")
            plugins.apply("maven")

            dependencies {
                "implementation"("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")

                "testCompile"("org.junit.jupiter:junit-jupiter-api:${Versions.jupiter}")
                "testCompile"("org.junit.jupiter:junit-jupiter-params:${Versions.jupiter}")
                "testRuntime"("org.junit.jupiter:junit-jupiter-engine:${Versions.jupiter}")

                "testImplementation"("org.jetbrains.kotlin:kotlin-test")

                "testImplementation"("com.github.komputing:khex:1.0.0")
            }

            tasks.withType<Test> {
                useJUnitPlatform()
                testLogging {
                    events("passed", "skipped", "failed")
                }
            }
        }
    }

    repositories {
        mavenLocal() // TODO: remove usage of maven local for KHex
        jcenter()
        maven("https://jitpack.io")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}
