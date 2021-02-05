package org.komputing.khash.sha512

import org.komputing.khex.extensions.toNoPrefixHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha512Tests {

    @Test
    fun testDigest1() {
        testHash(
            "".encodeToByteArray(),
            "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"
        )
    }

    @Test
    fun testDigest2() {
        testHash(
            "Hello world!".encodeToByteArray(),
            "f6cde2a0f819314cdde55fc227d8d7dae3d28cc556222a0a8ad66d91ccad4aad6094f517a2182360c9aacf6a3dc323162cb6fd8cdffedb0fe038f55e85ffb5b6"
        )
    }

    @Test
    fun testDigest3() {
        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Proin pulvinar turpis purus, sit amet dapibus magna commodo quis metus."
        testHash(
            loremIpsum.encodeToByteArray(),
            "de94877cc9711605dcdc09d85bd3080f74398d5e1ad8f0dcd1726c54ac93f2b4b781c3f56de1fbc725ac261a2c09d1d5bb24d0afa7449e4ffe4b2a7e6d09f40d"
        )
    }

    private fun testHash(input: ByteArray, expected: String) {
        assertEquals(expected, Sha512.digest(input).toNoPrefixHexString())
    }

    @Test
    fun testHashRawBytes() {
        val b = ByteArray(256)
        for (i in b.indices) {
            b[i] = i.toByte()
        }
        testHash(
            b,
            "1e7b80bc8edc552c8feeb2780e111477e5bc70465fac1a77b29b35980c3f0ce4a036a6c9462036824bd56801e62af7e9feba5c22ed8a5af877bf7de117dcac6d"
        )
    }
}
