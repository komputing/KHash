subprojects {
    repositories {
        mavenLocal() // TODO: remove usage of maven local for KHex
        jcenter()
        maven("https://jitpack.io")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}
