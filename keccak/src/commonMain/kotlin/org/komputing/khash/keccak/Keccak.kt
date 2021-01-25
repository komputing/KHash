package org.komputing.khash.keccak

import com.ionspin.kotlin.bignum.integer.BigInteger
import org.komputing.khash.keccak.extensions.fillWith
import kotlin.math.min

object Keccak {

    private val BIT_65 = BigInteger.ONE shl (64)
    private val MAX_64_BITS = BIT_65 - BigInteger.ONE

    fun digest(value: ByteArray, parameter: KeccakParameter): ByteArray {
        val uState = IntArray(200)
        val uMessage = convertToUInt(value)

        var blockSize = 0
        var inputOffset = 0

        // Absorbing phase
        while (inputOffset < uMessage.size) {
            blockSize = min(uMessage.size - inputOffset, parameter.rateInBytes)
            for (i in 0 until blockSize) {
                uState[i] = uState[i] xor uMessage[i + inputOffset]
            }

            inputOffset += blockSize

            if (blockSize == parameter.rateInBytes) {
                doF(uState)
                blockSize = 0
            }
        }

        // Padding phase
        uState[blockSize] = uState[blockSize] xor parameter.d
        if (parameter.d and 0x80 != 0 && blockSize == parameter.rateInBytes - 1) {
            doF(uState)
        }

        uState[parameter.rateInBytes - 1] = uState[parameter.rateInBytes - 1] xor 0x80
        doF(uState)

        // Squeezing phase
        val byteResults = mutableListOf<Byte>()
        var tOutputLen = parameter.outputLengthInBytes
        while (tOutputLen > 0) {
            blockSize = min(tOutputLen, parameter.rateInBytes)
            for (i in 0 until blockSize) {
                byteResults.add(uState[i].toByte().toInt().toByte())
            }

            tOutputLen -= blockSize
            if (tOutputLen > 0) {
                doF(uState)
            }
        }

        return byteResults.toByteArray()
    }

    private fun doF(uState: IntArray) {
        val lState = Array(5) { Array(5) { BigInteger.ZERO } }

        for (i in 0..4) {
            for (j in 0..4) {
                val data = IntArray(8)
                val index = 8 * (i + 5 * j)
                uState.copyInto(data, 0, index, index + data.size)
                lState[i][j] = convertFromLittleEndianTo64(data)
            }
        }
        roundB(lState)

        uState.fillWith(0)
        for (i in 0..4) {
            for (j in 0..4) {
                val data = convertFrom64ToLittleEndian(lState[i][j])
                data.copyInto(uState, 8 * (i + 5 * j))
            }
        }
    }

    /**
     * Permutation on the given state.
     */
    private fun roundB(state: Array<Array<BigInteger>>) {
        var lfsrState = 1
        for (round in 0..23) {
            val c = arrayOfNulls<BigInteger>(5)
            val d = arrayOfNulls<BigInteger>(5)

            // θ step
            for (i in 0..4) {
                c[i] = state[i][0].xor(state[i][1]).xor(state[i][2]).xor(state[i][3]).xor(state[i][4])
            }

            for (i in 0..4) {
                d[i] = c[(i + 4) % 5]!!.xor(c[(i + 1) % 5]!!.leftRotate64(1))
            }

            for (i in 0..4) {
                for (j in 0..4) {
                    state[i][j] = state[i][j].xor(d[i]!!)
                }
            }

            // ρ and π steps
            var x = 1
            var y = 0
            var current = state[x][y]
            for (i in 0..23) {
                val tX = x
                x = y
                y = (2 * tX + 3 * y) % 5

                val shiftValue = current
                current = state[x][y]

                state[x][y] = shiftValue.leftRotate64Safely((i + 1) * (i + 2) / 2)
            }

            // χ step
            for (j in 0..4) {
                val t = arrayOfNulls<BigInteger>(5)
                for (i in 0..4) {
                    t[i] = state[i][j]
                }

                for (i in 0..4) {
                    // ~t[(i + 1) % 5]
                    val invertVal = t[(i + 1) % 5]!!.xor(MAX_64_BITS)
                    // t[i] ^ ((~t[(i + 1) % 5]) & t[(i + 2) % 5])
                    state[i][j] = t[i]!!.xor(invertVal.and(t[(i + 2) % 5]!!))
                }
            }

            // ι step
            for (i in 0..6) {
                lfsrState = (lfsrState shl 1 xor (lfsrState shr 7) * 0x71) % 256
                // pow(2, i) - 1
                val bitPosition = (1 shl i) - 1
                if (lfsrState and 2 != 0) {
                    state[0][0] = state[0][0].xor(BigInteger.ONE shl bitPosition)
                }
            }
        }
    }

    /**
     * Converts the given [data] array to an [IntArray] containing UInt values.
     */
    private fun convertToUInt(data: ByteArray) = IntArray(data.size) {
        data[it].toInt() and 0xFF
    }

    /**
     * Converts the given [data] array containing the little endian representation of a number to a [BigInteger].
     */
    private fun convertFromLittleEndianTo64(data: IntArray): BigInteger {
        val value = data.map { it.toString(16) }
            .map { if (it.length == 2) it else "0$it" }
            .reversed()
            .joinToString("")
        return BigInteger.parseString(value, 16)
    }

    /**
     * Converts the given [BigInteger] to a little endian representation as an [IntArray].
     */
    private fun convertFrom64ToLittleEndian(uLong: BigInteger): IntArray {
        val asHex = uLong.toString(16)
        val asHexPadded = "0".repeat((8 * 2) - asHex.length) + asHex
        return IntArray(8) {
            ((7 - it) * 2).let { pos ->
                asHexPadded.substring(pos, pos + 2).toInt(16)
            }
        }
    }

    private fun BigInteger.leftRotate64Safely(rotate: Int) = leftRotate64(rotate % 64)

    private fun BigInteger.leftRotate64(rotate: Int) = (this shr (64 - rotate)).add(this shl rotate).mod(BIT_65)
}
