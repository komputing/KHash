package org.komputing.khash.extensions

import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for the [ByteArray] extensions functions defined inside `ByteArrayExtensions.kt`
 */
class ByteArrayExtensionsTests {

    private val testData = mapOf(
        "" to "",
        "1234567890" to "31323334353637383930",
        "The quick brown fox jumps over the lazy dog" to "54686520717569636b2062726f776e20666f78206a756d7073206f76657220746865206c617a7920646f67"
    )

    @Test
    fun toHexStringWorksProperlyWithoutPrefix() {
        testData.forEach { (text, expected) ->
            assertEquals(expected, text.toByteArray(Charsets.UTF_8).toHexString(""))
        }
    }

    @Test
    fun toHexStringWorksProperlyWithPrefix() {
        testData.forEach { (text, expected) ->
            assertEquals("0x$expected", text.toByteArray(Charsets.UTF_8).toHexString("0x"))
        }
    }

    @Test
    fun toNoHexPrefixDoesNotAddAnyPrefix() {
        testData.forEach { (text, _) ->
            assertEquals(text.toByteArray(Charsets.UTF_8).toHexString(""), text.toByteArray(Charsets.UTF_8).toNoPrefixHexString())
        }
    }
}
