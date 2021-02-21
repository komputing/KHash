package org.komputing.khash.sha256.extensions

import org.komputing.khash.sha256.Sha256

/**
 * Returns the SHA256 digest of this byte array.
 */
public fun ByteArray.sha256(): ByteArray = Sha256.digest(this)

/**
 * Returns the SHA256 digest of this string.
 */
public fun String.sha256(): ByteArray = this.encodeToByteArray().sha256()
