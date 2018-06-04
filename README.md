[![](https://jitpack.io/v/walleth/sha3.svg)](https://jitpack.io/#walleth/sha3)

SHA3
====

Kotlin SHA3 implementation. Also supports Keccak and Shake.

Heavily based on this java implementation: [@romus/sha](https://github.com/romus/sha)

Tested with [Nist test vectors](https://csrc.nist.gov/CSRC/media/Projects/Cryptographic-Algorithm-Validation-Program/documents/sha3/sha-3bittestvectors.zip)

Why
===

I was using the spongycastle/bouncycastle implementation before. That was working but had 2 major drawbacks:

 * huge footprint
 * as it is java it can only be used in JVM projects and especially for KEthereum I would like to be able to also target other platforms like native, WASM, JS, ..
 
How
===

```kotlin
import org.walleth.sha3.SHA3Parameter
import org.walleth.sha3.calculateSHA3

val hash = "hello world".calculateSHA3(SHA3Parameter.KECCAK_256)
```
The hash will be a ```ByteArray```

There is also an extension function that operates on a ```ByteArray```. This is also the core function:

```kotlin
fun ByteArray.calculateSHA3(parameter: SHA3Parameter): ByteArray {
   ...
}
```

The "hello world" example above just uses the extension function from this library that converts the String to a ByteArray before.

```kotlin
fun String.calculateSHA3(parameter: SHA3Parameter) = toByteArray().calculateSHA3(parameter)
```

These are the parameters you can use:

```kotlin
enum class SHA3Parameter constructor(val rateInBytes: Int,
                                     val outputLengthInBytes: Int,
                                     val d: Int) {

    KECCAK_224(144, 28, 0x01),
    KECCAK_256(136, 32, 0x01),
    KECCAK_384(104, 48, 0x01),
    KECCAK_512(72, 64, 0x01),

    SHA3_224(144, 28, 0x06),
    SHA3_256(136, 32, 0x06),
    SHA3_384(104, 48, 0x06),
    SHA3_512(72, 64, 0x06),

    SHAKE128(168, 32, 0x1F),
    SHAKE256(136, 64, 0x1F)
}
```

Links
=====

 * http://keccak.noekeon.org
 * https://github.com/romus/sha

Projects using this library
===========================

 * [WallETH - Android Ethereum Wallet](https://walleth.org)
 * [KEthereum - Kotlin Ethereum library](https://github.com/walleth/kethereum)

License
=======

MIT
