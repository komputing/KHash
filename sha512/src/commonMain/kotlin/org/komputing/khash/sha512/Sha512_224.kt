package org.komputing.khash.sha512

public object Sha512_224 {
    private val H0 = longArrayOf(
        -0x73c2c837e6abb25eL, 0x73E1996689DCD4D6L, 0x1DFAB7AE32FF9C82L, 0x679DD514582F9FCFL,
        0x0F6D2B697BD44DA8L, 0x77E36F7304C48942L, 0x3F9D85A86A1D36C8L, 0x1112E6AD91D692A1L
    )

    public fun digest(message: ByteArray): ByteArray =
        Sha512.digest(message, H0).sliceArray(0 until 28)
}