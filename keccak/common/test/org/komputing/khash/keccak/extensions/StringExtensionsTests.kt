package org.komputing.khash.keccak.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests for the [String] extensions methods defined inside `StringExtensions.kt`
 */
class StringExtensionsTests {

    @Test
    fun parseIntWorksProperlyWithValidValues() {
        assertEquals(0, "0".parseInt(10))
        assertEquals(473, "473".parseInt(10))
        assertEquals(42, "+42".parseInt(10))
        assertEquals(0, "-0".parseInt(10))
        assertEquals(-255, "-FF".parseInt(16))
        assertEquals(102, "1100110".parseInt(2))
        assertEquals(2147483647, "2147483647".parseInt(10))
        assertEquals(-2147483648, "-2147483648".parseInt(10))
        assertEquals(411787, "Kona".parseInt(27))
    }

    @Test
    fun parseIntThrowsExceptionWithInvalidNumber() {
        assertFailsWith<NumberFormatException> {
            "2147483648".parseInt(10)
        }

        assertFailsWith<NumberFormatException> {
            "99".parseInt(8)
        }

        assertFailsWith<NumberFormatException> {
            "Kona".parseInt(10)
        }
    }
}
