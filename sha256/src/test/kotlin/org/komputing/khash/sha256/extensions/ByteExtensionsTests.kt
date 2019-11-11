package org.komputing.khash.sha256.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

class ByteExtensionsTests {

    private val testData = mapOf(
        0.toByte() to 0,
        1.toByte() to 1,
        63.toByte() to 63,
        127.toByte() to 127,
        (-128).toByte() to 128,
        (-12).toByte() to 244,
        (-1).toByte() to 255
    )

    @Test
    fun toUIntWorks() {
        testData.forEach { (value, expected) ->
            assertEquals(expected, value.toUInt())
        }
    }
}
