package org.komputing.khash.extensions

/**
 * @see [Character.digit]
 */
internal actual fun Char.digit(radix: Int): Int {
    return Character.digit(this, radix)
}
