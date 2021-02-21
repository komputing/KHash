package org.komputing.khash.sha512

import org.komputing.khex.extensions.toNoPrefixHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha384Tests {

    @Test
    fun testDigest1() {
        testHash(
            "".encodeToByteArray(),
            "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b"
        )
    }

    @Test
    fun testDigest2() {
        testHash(
            "Hello world!".encodeToByteArray(),
            "86255fa2c36e4b30969eae17dc34c772cbebdfc58b58403900be87614eb1a34b8780263f255eb5e65ca9bbb8641cccfe"
        )
    }

    @Test
    fun testDigest3() {
        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Proin pulvinar turpis purus, sit amet dapibus magna commodo quis metus."
        testHash(
            loremIpsum.encodeToByteArray(),
            "bd2143a9952d27a18331aa3632a01331531ffec2a4ff351a5859706ebdb39068b34553ac81f83f1bc6b1b16f31c06e2b"
        )
    }

    private fun testHash(input: ByteArray, expected: String) {
        assertEquals(expected, Sha384.digest(input).toNoPrefixHexString())
    }

    @Test
    fun testHashRawBytes() {
        val b = ByteArray(256)
        for (i in b.indices) {
            b[i] = i.toByte()
        }
        testHash(
            b,
            "ffdaebff65ed05cf400f0221c4ccfb4b2104fb6a51f87e40be6c4309386bfdec2892e9179b34632331a59592737db5c5"
        )
    }
}
