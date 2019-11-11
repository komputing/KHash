package org.komputing.khash.sha256.extensions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IntExtensionsTests {

    @Test
    fun rotateRightWorks() {
        assertEquals(4, 8.rotateRight(1))
        assertEquals(2, 4.rotateRight(1))
        assertEquals(1, 2.rotateRight(1))
        assertEquals(1, 16.rotateRight(4))
    }

    @Test
    fun toByteWorksProperly() {
        // TODO: Add more examples
        val testData = mapOf(
            0 to byteArrayOf(0, 0, 0, 0).toTypedArray(),
            1 to byteArrayOf(0, 0, 0, 1).toTypedArray()
        )

        testData.forEach { (value, expected) ->
            assertTrue(expected.contentEquals(value.toBytes()))
        }
    }
}
