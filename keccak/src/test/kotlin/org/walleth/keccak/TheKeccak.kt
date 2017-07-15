package org.walleth.keccak

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.walleth.keccak.Parameter.KECCAK_256

class TheKeccak {

    @Test
    fun keccack256hashesAreCorrect() {
        val k = Keccak()

        assertThat(k.getHash("", KECCAK_256))
                .isEqualTo("c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470")

        assertThat(k.getHash("The quick brown fox jumps over the lazy dog", KECCAK_256))
                .isEqualTo("4d741b6f1eb29cb2a9b9911c82f56fa8d73b04959d3d9d222895df6c0b28aa15")

        assertThat(k.getHash("The quick brown fox jumps over the lazy dog.", KECCAK_256))
                .isEqualTo("578951e24efd62a3d63a86f7cd19aaa53c898fe287d2552133220370240b572d")
    }

    @Test
    fun fillWithZeroesWorks() {
        assertThat("5".fillWithZero(3)).isEqualTo("500")
        assertThat("".fillWithZero(5)).isEqualTo("00000")
        assertThat("777".fillWithZero(1)).isEqualTo("777")
    }
}
