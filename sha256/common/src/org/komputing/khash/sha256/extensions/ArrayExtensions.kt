package org.komputing.khash.sha256.extensions

/**
 * Converts the first 4 bytes into their integer representation following the big-endian conversion.
 * @throws NumberFormatException if the array size is less than 4
 */
internal fun Array<Byte>.toInt(): Int {
    if (this.size < 4) throw NumberFormatException("The array size is less than 4")
    return (this[0].toUInt() shl 24) + (this[1].toUInt() shl 16) + (this[2].toUInt() shl 8) + (this[3].toUInt() shl 0)
}
