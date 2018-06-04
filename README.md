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
