package org.komputing.khash.extensions

/**
 *  chars for nibble
 */
private const val CHARS = "0123456789abcdef"

/**
 *  Returns a 2 char hex string representation for the given [Byte].
 */
fun Byte.toHexString() = toInt().let {
    CHARS[it.shr(4) and 0x0f].toString() + CHARS[it.and(0x0f)].toString()
}
