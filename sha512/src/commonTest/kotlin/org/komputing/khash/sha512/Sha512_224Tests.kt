package org.komputing.khash.sha512

import org.komputing.khex.extensions.toNoPrefixHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha512_224Tests {

    @Test
    fun testDigest1() {
        testHash(
            "".encodeToByteArray(),
            "6ed0dd02806fa89e25de060c19d3ac86cabb87d6a0ddd05c333b84f4"
        )
    }

    @Test
    fun testDigest2() {
        testHash(
            "Hello world!".encodeToByteArray(),
            "b48c4994a3d2b6b48ae7fa6fcc09f33dc0c985109c0b7493fd3c74d0"
        )
    }

    @Test
    fun testDigest3() {
        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Proin pulvinar turpis purus, sit amet dapibus magna commodo quis metus."
        testHash(
            loremIpsum.encodeToByteArray(),
            "09c63e3a2e822c8477b192da10afa757824ba057d9996b823ad08656"
        )
    }

    private fun testHash(input: ByteArray, expected: String) {
        assertEquals(expected, Sha512_224.digest(input).toNoPrefixHexString())
    }

    @Test
    fun testHashRawBytes() {
        val b = ByteArray(255)
        for (i in b.indices) {
            b[i] = i.toByte()
        }
        testHash(b, "9e31b671e498e8160ba558aafacd05defd252b2c7d8ba8e9e618fbf1")
    }
}
