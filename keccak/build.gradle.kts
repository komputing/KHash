plugins {
    id("mpp-module")
}

repositories {
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

kotlin {
    sourceSets{
        commonMain {
            dependencies {
                // TODO: switch to next stable version that will support the new JS IR compiler and include bug fixes to bitwise operations
                implementation("com.ionspin.kotlin:bignum:0.2.4-SNAPSHOT")
            }
        }
    }
}