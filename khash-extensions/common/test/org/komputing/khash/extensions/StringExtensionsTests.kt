package org.komputing.khash.extensions

import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Tests for the [String] extensions defined inside `StringExtensions.kt`
 */
class StringExtensionsTests {

    private val testData = mapOf(
        "" to "",
        "1234567890" to "31323334353637383930",
        "The quick brown fox jumps over the lazy dog" to "54686520717569636b2062726f776e20666f78206a756d7073206f76657220746865206c617a7920646f67"
    )

    @Test
    fun hexToByteArrayWorksWithout0xPrefix() {
        testData.forEach { (text, hex) ->
            assertTrue(text.toByteArray(Charsets.UTF_8).contentEquals(hex.hexToByteArray()))
        }
    }

    @Test
    fun hexToByteArrayWorksWith0xPrefix() {
        testData.forEach { (text, hex) ->
            assertTrue(text.toByteArray(Charsets.UTF_8).contentEquals("0x$hex".hexToByteArray()))
        }
    }

    @Test
    fun hexToByteArrayWorksWith0XPrefix() {
        testData.forEach { (text, hex) ->
            assertTrue(text.toByteArray(Charsets.UTF_8).contentEquals("0X$hex".hexToByteArray()))
        }
    }
}
