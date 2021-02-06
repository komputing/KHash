package org.komputing.khash.sha512.extensions

import org.komputing.khash.sha512.Sha512

/**
 * Returns the SHA512 digest of this byte array.
 */
public fun ByteArray.sha512(): ByteArray = Sha512.digest(this)

/**
 * Returns the SHA512 digest of this string.
 */
public fun String.sha512(): ByteArray = encodeToByteArray().sha512()