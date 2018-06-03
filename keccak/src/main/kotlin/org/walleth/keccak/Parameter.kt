package org.walleth.keccak

/**
 * Parameters defining the FIPS 202 standard
 */


enum class Parameter constructor(val rate: Int,
                                 val outputLengthInBytes: Int,
                                 val d: Int) {

    KECCAK_224(1152, 28, 0x01),
    KECCAK_256(1088, 32, 0x01),
    KECCAK_384(832, 48, 0x01),
    KECCAK_512(576, 64, 0x01),
    SHA3_224(1152, 28, 0x06),
    SHA3_256(1088, 32, 0x06),
    SHA3_384(832, 48, 0x06),
    SHA3_512(576, 64, 0x06),
    SHAKE128(1344, 32, 0x1F),
    SHAKE256(1088, 64, 0x1F)
}
