package org.komputing.khash.sha512

public object Sha384 {
    private val H0 = longArrayOf(
        -0x344462a23efa6128L, 0x629a292a367cd507L, -0x6ea6fea5cf8f22e9L, 0x152fecd8f70e5939L,
        0x67332667ffc00b31L, -0x714bb57897a7eaefL, -0x24f3d1f29b067059L, 0x47b5481dbefa4fa4L
    )

    public fun digest(message: ByteArray): ByteArray =
        Sha512.digest(message, H0).sliceArray(0 until 48)
}