plugins {
    id("mpp-module")
}

kotlin {
    sourceSets{
        commonMain {
            dependencies {
                implementation("com.ionspin.kotlin:bignum:0.2.4")
            }
        }
    }
}