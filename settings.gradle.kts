@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        maven {
            name = "komputing/KHex GitHub Packages"
            url = uri("https://maven.pkg.github.com/komputing/KHex")
            credentials {
                username = "token"
                // see: https://github.community/t/download-from-github-package-registry-without-authentication/14407/44
                password = "\u0039\u0032\u0037\u0034\u0031\u0064\u0038\u0033\u0064\u0036\u0039\u0061\u0063\u0061\u0066\u0031\u0062\u0034\u0061\u0030\u0034\u0035\u0033\u0061\u0063\u0032\u0036\u0038\u0036\u0062\u0036\u0032\u0035\u0065\u0034\u0061\u0065\u0034\u0032\u0062"
            }
        }
        mavenCentral()
    }
}

include(":keccak")
include(":sha256")
include(":sha512")
include(":ripemd160")