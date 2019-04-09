package khash.keccak.extensions

import khash.keccak.Keccak
import khash.keccak.KeccakParameter
import kotlinx.io.core.toByteArray

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
    return Keccak.digest(toByteArray(), parameter)
}
