package org.komputing.khash.sha512

public object Sha512_256 {
    private val H0 = longArrayOf(
        0x22312194fc2bf72cL, -0x60aaa05c37b39b3eL, 0x2393b86b6f53b151L, -0x69c788e6a6bf1543L,
        -0x69d7c11d5771001dL, -0x41a1e1daac79c66eL, 0x2b0199fc2c85b8aaL, 0x0eb72ddc81c52ca2L
    )

    public fun digest(message: ByteArray): ByteArray =
        Sha512.digest(message, H0).sliceArray(0 until 32)
}