package org.komputing.khash.sha256.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ArrayExtensionsTests {

    @Test
    fun testToIntWithMultipleByteArray() {
        val input = byteArrayOf(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3).toTypedArray()
        assertEquals(1, input.toInt())
    }

    @Test
    fun testToIntWithInvalidArrayThrowsException() {
        assertFailsWith<NumberFormatException> {
            byteArrayOf(1, 2, 3).toTypedArray().toInt()
        }
    }
}
