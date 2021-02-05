package org.komputing.khash.sha512

import org.komputing.khex.extensions.toNoPrefixHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class Sha512_256Tests {

    @Test
    fun testDigest1() {
        testHash(
            "".encodeToByteArray(),
            "c672b8d1ef56ed28ab87c3622c5114069bdd3ad7b8f9737498d0c01ecef0967a"
        )
    }

    @Test
    fun testDigest2() {
        testHash(
            "Hello world!".encodeToByteArray(),
            "f8162ad49196c1c12bddbcff1d362ddacf03ae246b6a7864b75c244b965fe475"
        )
    }

    @Test
    fun testDigest3() {
        val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Proin pulvinar turpis purus, sit amet dapibus magna commodo quis metus."
        testHash(
            loremIpsum.encodeToByteArray(),
            "3e4d6734eb5ea2195a818833ad47bd3be83320bc4619e076a9cc21028e0dfd7e"
        )
    }

    private fun testHash(input: ByteArray, expected: String) {
        assertEquals(expected, Sha512_256.digest(input).toNoPrefixHexString())
    }

    @Test
    fun testHashRawBytes() {
        val b = ByteArray(255)
        for (i in b.indices) {
            b[i] = i.toByte()
        }
        testHash(b, "fd932614f375bf71420530a690cb16e52c08e99cfe741ac8436fca8c8bfd5676")
    }
}
