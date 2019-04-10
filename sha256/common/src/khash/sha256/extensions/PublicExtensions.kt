package khash.sha256.extensions

import khash.sha256.Sha256
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray

/**
 * Returns the SHA256 digest of this byte array.
 */
fun ByteArray.sha256() = Sha256.digest(this)

/**
 * Returns the SHA256 digest of this string.
 */
fun String.sha256() = this.toByteArray(Charsets.UTF_8).sha256()
