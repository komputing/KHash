[![](https://jitpack.io/v/komputing/khash.svg)](https://jitpack.io/#komputing/khash)

# KHash

KHash is a Kotlin library implementing common hashing functions.

A part from that, it also provides some useful [extensions functions](https://kotlinlang.org/docs/reference/extensions.html).

The implemented hashing functions are the following. 
* [RIPEMD160](https://en.wikipedia.org/wiki/RIPEMD)
* [SHA-256](https://en.wikipedia.org/wiki/SHA-2) 
* [Keccak](https://en.wikipedia.org/wiki/SHA-3)
   - Heavily based on this java implementation: [@romus/sha](https://github.com/romus/sha)
   - Tested with [Nist test vectors](https://csrc.nist.gov/CSRC/media/Projects/Cryptographic-Algorithm-Validation-Program/documents/sha3/sha-3bittestvectors.zip)


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
