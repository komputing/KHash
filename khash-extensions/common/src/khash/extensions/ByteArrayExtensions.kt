package khash.extensions

/**
 * Converts the given [ByteArray] into its hex string representation, prefixed by the given [prefix].
 */
fun ByteArray.toHexString(prefix: String = "0x"): String {
    return prefix + joinToString("") { it.toHexString() }
}

/**
 * Converts the given [ByteArray] into its hex string representation, without any prefix.
 */
fun ByteArray.toNoPrefixHexString(): String = toHexString("")
