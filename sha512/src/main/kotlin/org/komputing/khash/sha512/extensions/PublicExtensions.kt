package org.komputing.khash.sha512.extensions

import org.komputing.khash.sha512.Sha512

/**
 * Returns the SHA512 digest of this byte array.
 */
fun ByteArray.sha512() = Sha512.digest(this)

/**
 * Returns the SHA512 digest of this string.
 */
fun String.sha512() = this.toByteArray(Charsets.UTF_8).sha512()