package khash.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 *
 */
class ByteExtensionsTests {

    private val testData = mapOf(
        0.toByte() to "00",
        1.toByte() to "01",
        2.toByte() to "02",
        3.toByte() to "03",
        4.toByte() to "04",
        5.toByte() to "05",
        6.toByte() to "06",
        7.toByte() to "07",
        8.toByte() to "08",
        9.toByte() to "09",
        10.toByte() to "0a",
        11.toByte() to "0b",
        12.toByte() to "0c",
        13.toByte() to "0d",
        14.toByte() to "0e",
        15.toByte() to "0f",
        33.toByte() to "21",
        75.toByte() to "4b",
        153.toByte() to "99"
    )

    @Test
    fun toHexStringWorks() {
        testData.forEach { (value, expected) ->
            assertEquals(expected, value.toHexString())
        }
    }
}
