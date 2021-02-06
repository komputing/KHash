plugins {
    id("mpp-module")
}

kotlin {
    sourceSets{
        commonMain {
            dependencies {
                implementation("com.ionspin.kotlin:bignum:${Versions.bignum}")
            }
        }
    }
}