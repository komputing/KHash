package org.komputing.khash.sha256.extensions

/**
 * Writes a long split into 8 bytes.
 * @param [offset] start index
 * @param [value] the value to insert
 * Thanks to manu0466
 */
internal fun ByteArray.putLong(offset: Int, value: Long) {
    for (i in 7 downTo 0) {
        val temp = (value ushr (i * 8)).toUByte()
        this[offset + 7 - i] = temp.toByte()
    }
}

/**
 * Converts the given byte array into an int array via big-endian conversion (4 bytes become 1 int).
 * @throws IllegalArgumentException if the byte array size is not a multiple of 4.
 */
internal fun ByteArray.toIntArray(): IntArray {
    if (this.size % INT_BYTES != 0) {
        throw IllegalArgumentException("Byte array length must be a multiple of $INT_BYTES")
    }

    val array = IntArray(this.size / INT_BYTES)
    for (i in array.indices) {
        val integer = arrayOf(this[i* INT_BYTES], this[i* INT_BYTES + 1], this[i* INT_BYTES + 2], this[i* INT_BYTES + 3])
        array[i] = integer.toInt()
    }
    return array
}

/**
 * Copies an array from the specified source array, beginning at the
 * specified position, to the specified position of the destination array.
 */
internal fun ByteArray.copy(srcPos: Int, dest: ByteArray, destPos: Int, length: Int) {
    this.copyInto(dest, destPos, srcPos, srcPos + length)
}
