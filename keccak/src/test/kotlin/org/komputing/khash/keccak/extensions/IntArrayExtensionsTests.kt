package org.komputing.khash.keccak.extensions

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Tests for the extensions functions present inside `IntArrayExtensions.kt`
 */
class IntArrayExtensionsTests {

    @Test
    fun fillsWithWorksWithDefaultValues() {
        val input = intArrayOf(0, 0, 0, 0, 0)
        input.fillWith(5)

        val expected = intArrayOf(5, 5, 5, 5, 5)
        assertTrue(expected.contentEquals(input))
    }

    @Test
    fun fillsWithWorksWithCustomValues() {
        val input = intArrayOf(0, 0, 0, 0, 0)
        input.fillWith(9, fromIndex = 3, toIndex = 4)

        val expected = intArrayOf(0, 0, 0, 9, 0)
        assertTrue(expected.contentEquals(input))
    }
}
