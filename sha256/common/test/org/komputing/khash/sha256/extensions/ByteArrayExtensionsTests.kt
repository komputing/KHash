package org.komputing.khash.sha256.extensions

import io.mockk.every
import io.mockk.mockkObject
import org.komputing.khash.sha256.Sha256
import kotlin.test.Test
import kotlin.test.assertTrue

class ByteArrayExtensionsTests {

    @Test
    fun testToByteArrayEmpty() {
        val input = intArrayOf()
        val expected = byteArrayOf()

        assertTrue(expected.contentEquals(input.toByteArray()))
    }

    @Test
    fun testToByteArrayMultiple() {
        val input = intArrayOf(1, 2, 3)
        val expected = byteArrayOf(0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3)

        assertTrue(expected.contentEquals(input.toByteArray()))
    }

    @Test
    fun testByteArrayPutLong() {
        val value = 0xaa_bb_cc_dd_ee_ff_99
        val byteArray = ByteArray(8)
        byteArray.putLong(0, value)
        assertTrue(byteArrayOf(0, -86, -69, -52, -35, -18, -1, -103).contentEquals(byteArray))

        val value2 = -1L
        byteArray.putLong(0, value2)
        assertTrue(byteArrayOf(-1, -1, -1, -1, -1, -1, -1, -1).contentEquals(byteArray))
    }

    @Test
    fun sha256CallsProperMethod() = mockkObject(Sha256) {
        val output = byteArrayOf(3, 2, 1)
        every { Sha256.digest(any()) } returns output

        val input = byteArrayOf(1, 2, 3)
        val result = input.sha256()
        assertTrue(output.contentEquals(result))
    }
}
