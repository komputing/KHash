package khash.extensions

/**
 * Converts [this] HEX-encoded string into a [ByteArray].
 */
fun String.hexToByteArray(): ByteArray {
    if (length % 2 != 0) {
        throw IllegalArgumentException("hex-string must have an even number of digits (nibbles)")
    }

    val cleanInput = if (startsWith("0x", ignoreCase = true)) substring(2) else this

    return ByteArray(cleanInput.length / 2).apply {
        var i = 0
        while (i < cleanInput.length) {
            this[i / 2] = ((cleanInput[i].getNibbleValue() shl 4) + cleanInput[i + 1].getNibbleValue()).toByte()
            i += 2
        }
    }
}
