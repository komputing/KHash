package org.komputing.khash.sha256.extensions

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IntArrayExtensionsTests {

    @Test
    fun testToIntArrayEmpty() {
        val input = byteArrayOf()
        val expected = intArrayOf()
        assertTrue(expected.contentEquals(input.toIntArray()))
    }

    @Test
    fun testToIntSingle() {
        val input = byteArrayOf(0, 0, 0, 0)
        val expected = intArrayOf(0)
        assertTrue(expected.contentEquals(input.toIntArray()))

        val input2 = byteArrayOf(0, 0, 0, 1)
        val expected2 = intArrayOf(1)
        assertTrue(expected2.contentEquals(input2.toIntArray()))
    }

    @Test
    fun testToIntArrayMultiple() {
        val input = byteArrayOf(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3)
        val expected = intArrayOf(1, 2, 3)
        assertTrue(expected.contentEquals(input.toIntArray()))
    }

    @Test
    fun testToIntArrayThrowsForIllegalLength() {
        assertFailsWith(IllegalArgumentException::class) {
            byteArrayOf(0, 0, 0, 1, 0).toIntArray()
        }
    }
}
