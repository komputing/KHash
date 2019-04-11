package org.komputing.khash.ripemd160.extensions

import org.komputing.khash.ripemd160.Ripemd160Digest
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray

/**
 * Computes the RIPEMD160 from [this] [ByteArray].
 */
fun ByteArray.digestRipemd160(): ByteArray {
    return ByteArray(Ripemd160Digest.DIGEST_LENGTH).apply {
        val digest = Ripemd160Digest()
        digest.update(this@digestRipemd160, 0, this@digestRipemd160.size)
        digest.doFinal(this, 0)
    }
}

/**
 * Computes the RIPEMD160 for [this] string.
 */
fun String.digestRipemd160() = toByteArray(Charsets.UTF_8).digestRipemd160()
