package org.walleth.sha3

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.walleth.khex.hexToByteArray
import org.walleth.khex.toHexString
import org.walleth.khex.toNoPrefixHexString
import org.walleth.sha3.SHA3Parameter.*

class TheKeccak {

    val allNistTestVectors = listOf(
            testVectors224 to SHA3_224,
            testVectors256 to SHA3_256,
            testVectors384 to SHA3_384,
            testVectors512 to SHA3_512
    )

    @Test
    fun testNistVectors() {
        allNistTestVectors.forEach { vector ->
            vector.first.forEach { element->
                assertThat(element.first.hexToByteArray().calculateKeccak(vector.second).toNoPrefixHexString())
                        .isEqualTo(element.second)
            }
        }
    }

    @Test
    fun keccak256hashesAreCorrect() {

        assertThat("".calculateKeccak(KECCAK_256))
                .isEqualTo("c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470".hexToByteArray())

        assertThat("The quick brown fox jumps over the lazy dog".calculateKeccak(KECCAK_256))
                .isEqualTo("4d741b6f1eb29cb2a9b9911c82f56fa8d73b04959d3d9d222895df6c0b28aa15".hexToByteArray())

        assertThat("The quick brown fox jumps over the lazy dog.".calculateKeccak(KECCAK_256))
                .isEqualTo("578951e24efd62a3d63a86f7cd19aaa53c898fe287d2552133220370240b572d".hexToByteArray())


        assertThat("The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.".calculateKeccak(KECCAK_256))
                .isEqualTo("e35949d2ca446ea2fd99f49bed23c60e0b9849f5384661bc574a5c55fcaeb4bd".hexToByteArray())

        assertThat("a1b31be4d58a7ddd24b135db0da56a90fb5382077ae26b250e1dc9cd6232ce2270f4c995428bc76aa78e522316e95d7834d725efc9ca754d043233af6ca90113".hexToByteArray().calculateKeccak(KECCAK_256).toHexString(""))
                .isEqualTo("e1674295e8fc8ffdfb46cadb01c52b08330e05d731e38c856c1043288f7d9744")
    }

    @Test
    fun fillWithZeroesWorks() {
        assertThat("5".fillWithZero(3)).isEqualTo("500")
        assertThat("".fillWithZero(5)).isEqualTo("00000")
        assertThat("777".fillWithZero(1)).isEqualTo("777")
    }
}
