package org.komputing.khash.sha512

/**
 * Digest Class for SHA-512.
 * Original Java version at https://github.com/trittimo/SHA512
 */
public object Sha512 {

    private val K = longArrayOf(
        0x428A2F98D728AE22L, 0x7137449123EF65CDL, -0x4a3f043013b2c4d1L, -0x164a245a7e762444L,
        0x3956C25BF348B538L, 0x59F111F1B605D019L, -0x6dc07d5b50e6b065L, -0x54e3a12a25927ee8L,
        -0x27f855675cfcfdbeL, 0x12835B0145706FBEL, 0x243185BE4EE4B28CL, 0x550C7DC3D5FFB4E2L,
        0x72BE5D74F27B896FL, -0x7f214e01c4e9694fL, -0x6423f958da38edcbL, -0x3e640e8b3096d96cL,
        -0x1b64963e610eb52eL, -0x1041b879c7b0da1dL, 0x0FC19DC68B8CD5B5L, 0x240CA1CC77AC9C65L,
        0x2DE92C6F592B0275L, 0x4A7484AA6EA6E483L, 0x5CB0A9DCBD41FBD4L, 0x76F988DA831153B5L,
        -0x67c1aead11992055L, -0x57ce3992d24bcdf0L, -0x4ffcd8376704dec1L, -0x40a680384110f11cL,
        -0x391ff40cc257703eL, -0x2a586eb86cf558dbL, 0x06CA6351E003826FL, 0x142929670A0E6E70L,
        0x27B70A8546D22FFCL, 0x2E1B21385C26C926L, 0x4D2C6DFC5AC42AEDL, 0x53380D139D95B3DFL,
        0x650A73548BAF63DEL, 0x766A0ABB3C77B2A8L, -0x7e3d36d1b812511aL, -0x6d8dd37aeb7dcac5L,
        -0x5d40175eb30efc9cL, -0x57e599b443bdcfffL, -0x3db4748f2f07686fL, -0x3893ae5cf9ab41d0L,
        -0x2e6d17e62910ade8L, -0x2966f9dbaa9a56f0L, -0xbf1ca7aa88edfd6L, 0x106AA07032BBD1B8L,
        0x19A4C116B8D2D0C8L, 0x1E376C085141AB53L, 0x2748774CDF8EEB99L, 0x34B0BCB5E19B48A8L,
        0x391C0CB3C5C95A63L, 0x4ED8AA4AE3418ACBL, 0x5B9CCA4F7763E373L, 0x682E6FF3D6B2B8A3L,
        0x748F82EE5DEFB2FCL, 0x78A5636F43172F60L, -0x7b3787eb5e0f548eL, -0x7338fdf7e59bc614L,
        -0x6f410005dc9ce1d8L, -0x5baf9314217d4217L, -0x41065c084d3986ebL, -0x398e870d1c8dacd5L,
        -0x35d8c13115d99e64L, -0x2e794738de3f3df9L, -0x15258229321f14e2L, -0xa82b08011912e88L,
        0x06F067AA72176FBAL, 0x0A637DC5A2C898A6L, 0x113F9804BEF90DAEL, 0x1B710B35131C471BL,
        0x28DB77F523047D84L, 0x32CAAB7B40C72493L, 0x3C9EBE0A15C9BEBCL, 0x431D67C49C100D4CL,
        0x4CC5D4BECB3E42B6L, 0x597F299CFC657E2AL, 0x5FCB6FAB3AD6FAECL, 0x6C44198C4A475817L
    )

    private val H0 = longArrayOf(
        0x6A09E667F3BCC908L, -0x4498517a7b3558c5L, 0x3C6EF372FE94F82BL, -0x5ab00ac5a0e2c90fL,
        0x510E527FADE682D1L, -0x64fa9773d4c193e1L, 0x1F83D9ABFB41BD6BL, 0x5BE0CD19137E2179L
    )

    public fun digest(input: ByteArray): ByteArray = digest(input, H0)

    // Does the actual hash
    internal fun digest(input: ByteArray, h0: LongArray): ByteArray {
        // First pad the input to the correct length, adding the bits specified in the SHA algorithm
        val paddedInput = padMessage(input)
        // Break the padded input up into blocks
        val blocks: Array<LongArray> = toBlocks(paddedInput)
        // And get the expanded message blocks
        val expandedMessageBlocks: Array<LongArray> = calculateMessageBlocks(blocks)
        // Set up the buffer which will eventually contain the final hash
        // Initially, it's set to the constants provided as part of the algorithm
        val buffer: LongArray = h0.copyOf()
        // For every block
        for (i in blocks.indices) { // a-h is set to the buffer initially
            var a = buffer[0]
            var b = buffer[1]
            var c = buffer[2]
            var d = buffer[3]
            var e = buffer[4]
            var f = buffer[5]
            var g = buffer[6]
            var h = buffer[7]
            // Run 80 rounds of the SHA-512 compression function on a-h
            for (j in 0..79) {
                val t1: Long = h + bigSig1(e) + ch(e, f, g) + K[j] + expandedMessageBlocks[i][j]
                val t2: Long = bigSig0(a) + maj(a, b, c)
                h = g
                g = f
                f = e
                e = d + t1
                d = c
                c = b
                b = a
                a = t1 + t2
            }
            // After finishing the compression, save the state to the buffer
            buffer[0] += a
            buffer[1] += b
            buffer[2] += c
            buffer[3] += d
            buffer[4] += e
            buffer[5] += f
            buffer[6] += g
            buffer[7] += h
        }
        return buffer.foldIndexed(ByteArray(64)) { index, acc, value ->
            val indexBytes = index * 8
            acc[indexBytes + 0] = (value ushr 56).toByte()
            acc[indexBytes + 1] = (value ushr 48).toByte()
            acc[indexBytes + 2] = (value ushr 40).toByte()
            acc[indexBytes + 3] = (value ushr 32).toByte()
            acc[indexBytes + 4] = (value ushr 24).toByte()
            acc[indexBytes + 5] = (value ushr 16).toByte()
            acc[indexBytes + 6] = (value ushr 8).toByte()
            acc[indexBytes + 7] = value.toByte()
            acc
        }
    }

    // Used in the compression function
    private fun ch(x: Long, y: Long, z: Long) = x and y xor (x.inv() and z)

    // Used in the compression function
    private fun maj(x: Long, y: Long, z: Long) = x and y xor (x and z) xor (y and z)

    // Used in the compression function
    private fun rotate(x: Long, l: Int) = x ushr l or (x shl Long.SIZE_BITS - l)

    // Used in the compression function
    // Sn = right rotate by n bits
    // Rn = right shift by n bits
    private fun bigSig0(x: Long): Long { // S28(x) ^ S34(x) ^ S39(x)
        return rotate(x, 28) xor rotate(x, 34) xor rotate(x, 39)
    }

    // Used in the compression function
    private fun bigSig1(x: Long): Long { // S14(x) ^ S18(x) ^ S41(x)
        return rotate(x, 14) xor rotate(x, 18) xor rotate(x, 41)
    }

    // Used in the message schedule
    private fun smallSig0(x: Long): Long { // S1(x) ^ S8(x) ^ R7(x)
        return rotate(x, 1) xor rotate(x, 8) xor (x ushr 7)
    }

    // Used in the message schedule
    private fun smallSig1(x: Long): Long { // S19(x) ^ S61(x) ^ R6(x)
        return rotate(x, 19) xor rotate(x, 61) xor (x ushr 6)
    }

    // Pads the input byte array
    private fun padMessage(input: ByteArray): ByteArray {
        // Need to append at least 17 bytes (16 for length of the message, and 1 for the '1' bit)
        // then fill with 0s until multiple of 128 bytes
        val size = (input.size + 17).let { padded_input ->
            (padded_input % 128).let { remainder ->
                if (remainder == 0) padded_input else padded_input + 128 - remainder
            }
        }

        val len = input.size * 8L
        // The padded byte array
        return ByteArray(size) { i ->
            when {
                i < input.size -> input[i]
                i == input.size -> 0x80.toByte()
                i == size - 1 -> len.toByte()
                i == size - 2 -> (len ushr 8).toByte()
                i == size - 3 -> (len ushr 16).toByte()
                i == size - 4 -> (len ushr 24).toByte()
                else -> 0
            }
        }

    }

    // Converts the byte array input starting at index j into a long
    private fun arrToLong(input: ByteArray, j: Int) = (0..7).fold(0L) { acc, i ->
        (acc shl 8) + input[i + j].toUByte().toLong()
    }

    // Converts the byte array input into blocks of longs
    // a block has: 1024 bits = 128 bytes = 16 longs
    private fun toBlocks(input: ByteArray) = Array(input.size / 128) {
        LongArray(16) { j -> arrToLong(input, it * 128 + j * 8) }
    }

    // Calculates the expanded message blocks W0-W79
    private fun calculateMessageBlocks(M: Array<LongArray>) = Array(M.size) { i ->
        LongArray(80) { j -> if (j < 16) M[i][j] else 0 }
    }.apply {
        for (i in M.indices) {
            (16..79).forEach { j ->
                get(i)[j] = smallSig1(get(i)[j - 2]) + get(i)[j - 7] + smallSig0(get(i)[j - 15]) + get(i)[j - 16]
            }
        }
    }
}