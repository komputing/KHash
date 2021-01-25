package org.komputing.khash.keccak.extensions

import org.komputing.khash.keccak.Keccak
import org.komputing.khash.keccak.KeccakParameter

/**
 * Computes the proper Keccak digest of [this] byte array based on the given [parameter]
 */
fun ByteArray.digestKeccak(parameter: KeccakParameter): ByteArray {
    return Keccak.digest(this, parameter)
}

/**
 * Computes the proper Keccak digest of [this] string based on the given [parameter]
 */
fun String.digestKeccak(parameter: KeccakParameter): ByteArray {
    return Keccak.digest(encodeToByteArray(), parameter)
}
