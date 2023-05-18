![CI](https://github.com/komputing/KHash/workflows/Continuous%20Integration/badge.svg)

# KHash

KHash is a Kotlin multiplatform library implementing common hashing functions.

Apart from that, it also provides some useful [extensions functions](https://kotlinlang.org/docs/reference/extensions.html).

The implemented hashing functions are the following. 
* [RIPEMD160](https://en.wikipedia.org/wiki/RIPEMD)
* [SHA-256](https://en.wikipedia.org/wiki/SHA-2) 
* [Keccak](https://en.wikipedia.org/wiki/SHA-3)
   - Heavily based on this java implementation: [@romus/sha](https://github.com/romus/sha)
   - Tested with [Nist test vectors](https://csrc.nist.gov/CSRC/media/Projects/Cryptographic-Algorithm-Validation-Program/documents/sha3/sha-3bittestvectors.zip)

## Get it

### GitHub Packages

This library is available through GitHub Packages.

![badge][badge-android]
![badge][badge-ios]
![badge][badge-js-ir]
![badge][badge-jvm]
![badge][badge-linux]
![badge][badge-mac]
![badge][badge-apple-silicon]

In order to use it, first include the GitHub Packages maven repository inside your project `build.gradle.kts` file:

```kotlin
repositories {
    maven {
        name = "komputing/KHash GitHub Packages"
        url = uri("https://maven.pkg.github.com/komputing/KHash")
        credentials {
            username = "token"
            password = "\u0039\u0032\u0037\u0034\u0031\u0064\u0038\u0033\u0064\u0036\u0039\u0061\u0063\u0061\u0066\u0031\u0062\u0034\u0061\u0030\u0034\u0035\u0033\u0061\u0063\u0032\u0036\u0038\u0036\u0062\u0036\u0032\u0035\u0065\u0034\u0061\u0065\u0034\u0032\u0062"
        }
    }
}
```

## Set it up

Include the modules inside your project:

```kotlin
dependencies {
    implementation("com.github.komputing.khash:<module>:<version>")
}
```

Where `<version>` can be either a [release](https://github.com/komputing/KHash/releases) or `<branch>-SNAPSHOT` such as `master-SNAPHOT`.

## Usage
### `keccak`
Module containing the implementation of the Kecccak hashing algorithms.  

#### Object usage
```kotlin
// Compute the Keccak digest of a byte array based on the given parameter
Keccak.digest(byteArrayOf(1, 2, 3), KeccakParameter.KECCAK_512) 
Keccak.digest(byteArrayOf(1, 2, 3), KeccakParameter.SHA3_224) 
Keccak.digest(byteArrayOf(1, 2, 3), KeccakParameter.SHAKE_128) 
```

#### Extension functions
```kotlin
// Compute a specific Keccak digest of a byte array based on the given parameter
byteArrayOf(1, 2, 3).digestKeccak(KeccakParameter.KECCAK_512)

// Compute a specific Keccak digest of a string based on the given parameter
"The quick brown fox jumps over the lazy dog".digestKeccak(parameter = KeccakParameter.SHA3_384)
```

### `ripemd160`
Module containing the implementation of the RIPEMD160 hashing algorithms.  

#### Object usage
```kotlin
// Compute the RIPEMD160 digest of a byte array
val input = byteArrayOf(1, 2, 3)
val output = ByteArray(Ripemd160Digest.DIGEST_LENGTH)
Ripemd160Digest().apply {
  update(input, 0, input.size)
  doFinal(output, 0)
} 
```

#### Extension functions
```kotlin
// Compute the RIPEMD160 digest of a byte array
byteArrayOf(1, 2, 3).ripemd160()

// Compute the RIPEMD160 digest of a string
"The quick brown fox jumps over the lazy dog".ripemd160()
```

### `sha256`
Module containing the implementation of the SHA256 hashing algorithms.  

#### Object usage
```kotlin
// Compute the SHA-256 digest of a byte array 
Sha256.digest(byteArrayOf(1, 2, 3))
```

#### Extension functions
```kotlin
// Compute the SHA-256 digest of a byte array
byteArrayOf(1, 2, 3).sha256()

// Compute the SHA-256 digest of a string
"The quick brown fox jumps over the lazy dog".sha256()
```

# Disclaimer
The results *should* be correct as the Nist test vectors pass and also second degree tests pass in KEthereum. That said there should be more eyes on this project before it is used in really critical situations. If you can spare some time please have a look at the code - feedback is very welcome.

Also this code is not hardened against side channel attacks. Keep this in mind when hashing sensitive content! I would be really happy about input from security researchers here - there is also an [open issue](https://github.com/walleth/sha3/issues/5) with a bounty for this.

# Links
 * http://keccak.noekeon.org
 * https://github.com/romus/sha
 * https://repository.library.northeastern.edu/files/neu:cj82qf503/fulltext.pdf

# Projects using this library
 * [WallETH - Android Ethereum Wallet](https://walleth.org)
 * [KEthereum - Kotlin Ethereum library](https://github.com/komputing/KEthereum)

# License
MIT

[badge-android]: http://img.shields.io/badge/-android-6EDB8D.svg?style=flat
[badge-android-native]: http://img.shields.io/badge/support-[AndroidNative]-6EDB8D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat
[badge-js-ir]: http://img.shields.io/badge/-js--IR-F8DB5D.svg?style=flat
[badge-nodejs]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat
[badge-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat