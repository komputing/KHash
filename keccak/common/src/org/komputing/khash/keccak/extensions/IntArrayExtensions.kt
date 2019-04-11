package org.komputing.khash.keccak.extensions

/**
 * Assigns the specified int value to each element of the specified
 * range of the specified array of ints.  The range to be filled
 * extends from index <tt>fromIndex</tt>, inclusive, to index
 * <tt>toIndex</tt>, exclusive.  (If <tt>fromIndex==toIndex</tt>, the
 * range to be filled is empty.)
 *
 * @param fromIndex the index of the first element (inclusive) to be
 *        filled with the specified value
 * @param toIndex the index of the last element (exclusive) to be
 *        filled with the specified value
 * @param value the value to be stored in all elements of the array
 * @throws IllegalArgumentException if <tt>fromIndex &gt; toIndex</tt>
 * @throws ArrayIndexOutOfBoundsException if <tt>fromIndex &lt; 0</tt> or
 *         <tt>toIndex &gt; a.length</tt>
 */
internal fun IntArray.fillWith(value: Int, fromIndex: Int = 0, toIndex: Int = this.size) {
    if (fromIndex > toIndex) {
        throw IllegalArgumentException(
            "fromIndex($fromIndex) > toIndex($toIndex)"
        )
    }

    if (fromIndex < 0) {
        throw ArrayIndexOutOfBoundsException(fromIndex)
    }
    if (toIndex > this.size) {
        throw ArrayIndexOutOfBoundsException(toIndex)
    }

    for (i in fromIndex until toIndex)
        this[i] = value
}

/**
 * Constructs a new [ArrayIndexOutOfBoundsException]
 * class with an argument indicating the illegal index.
 * @param index the illegal index.
 */
internal class ArrayIndexOutOfBoundsException(index: Int) : Throwable("Array index out of range: $index")
