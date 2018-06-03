package org.walleth.keccak

import java.io.ByteArrayOutputStream
import java.lang.Math.min
import java.lang.System.arraycopy
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import java.util.Arrays.fill

private val BIT_64 = BigInteger("18446744073709551615")

fun String.calculateKeccak(parameter: Parameter) = toByteArray().calculateKeccak(parameter)

fun ByteArray.calculateKeccak(parameter: Parameter): ByteArray {
    val uState = IntArray(200)
    val uMessage = convertToUInt(this)

    val rateInBytes = parameter.rate / 8
    var blockSize = 0
    var inputOffset = 0

    // Absorbing phase
    while (inputOffset < uMessage.size) {
        blockSize = min(uMessage.size - inputOffset, rateInBytes)
        for (i in 0 until blockSize) {
            uState[i] = uState[i] xor uMessage[i + inputOffset]
        }

        if (blockSize == rateInBytes) {
            doKeccakf(uState)
        }

        inputOffset += blockSize
    }

    // Padding phase
    uState[blockSize] = uState[blockSize] xor parameter.d
    if (parameter.d and 0x80 != 0 && blockSize == rateInBytes - 1) {
        doKeccakf(uState)
    }

    uState[rateInBytes - 1] = uState[rateInBytes - 1] xor 0x80
    doKeccakf(uState)

    // Squeezing phase
    val byteResults = ByteArrayOutputStream()
    var tOutputLen = parameter.outputLengthInBytes
    while (tOutputLen > 0) {
        blockSize = min(tOutputLen, rateInBytes)
        for (i in 0 until blockSize) {
            byteResults.write(uState[i].toByte().toInt())
        }

        tOutputLen -= blockSize
        if (tOutputLen > 0) {
            doKeccakf(uState)
        }
    }

    return byteResults.toByteArray()
}

private fun doKeccakf(uState: IntArray) {
    val lState = Array(5) { Array(5) { ZERO } }

    for (i in 0..4) {
        for (j in 0..4) {
            val data = IntArray(8)
            arraycopy(uState, 8 * (i + 5 * j), data, 0, data.size)
            lState[i][j] = convertFromLittleEndianTo64(data)
        }
    }
    roundB(lState)

    fill(uState, 0)
    for (i in 0..4) {
        for (j in 0..4) {
            val data = convertFrom64ToLittleEndian(lState[i][j])
            arraycopy(data, 0, uState, 8 * (i + 5 * j), data.size)
        }
    }

}

/**
 * Permutation on the given state.
 *
 * @param state state
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
                state[i][j] = state[i][j].xor(d[i])
            }
        }

        //ρ and π steps
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

        //χ step
        for (j in 0..4) {
            val t = arrayOfNulls<BigInteger>(5)
            for (i in 0..4) {
                t[i] = state[i][j]
            }

            for (i in 0..4) {
                // ~t[(i + 1) % 5]
                val invertVal = t[(i + 1) % 5]!!.xor(BIT_64)
                // t[i] ^ ((~t[(i + 1) % 5]) & t[(i + 2) % 5])
                state[i][j] = t[i]!!.xor(invertVal.and(t[(i + 2) % 5]))
            }
        }

        //ι step
        for (i in 0..6) {
            lfsrState = (lfsrState shl 1 xor (lfsrState shr 7) * 0x71) % 256
            // pow(2, i) - 1
            val bitPosition = (1 shl i) - 1
            if (lfsrState and 2 != 0) {
                state[0][0] = state[0][0].xor(ONE.shiftLeft(bitPosition))
            }
        }
    }
}


internal fun String.fillWithZero(fillLength: Int) = this + "0".repeat(Math.max(fillLength - this.length, 0))

private fun convertToUInt(data: ByteArray) = IntArray(data.size, { data[it].toInt() and 0xFF })

internal fun convertFromLittleEndianTo64(data: IntArray) =
        BigInteger(data.map { Integer.toString(it, 16) }
                .map { if (it.length == 2) it else "0$it" }
                .reversed()
                .joinToString("")
                , 16)

private fun convertFrom64ToLittleEndian(uLong: BigInteger): IntArray {
    val asHex = uLong.toString(16)
    val asHexPadded = "0".repeat((8 * 2) - asHex.length) + asHex
    return IntArray(8, {
        ((7 - it) * 2).let {
            Integer.parseInt(asHexPadded.substring(it, it + 2), 16)
        }
    })
}


private fun BigInteger.leftRotate64Safely(rotate: Int) = leftRotate64(rotate % 64)

private fun BigInteger.leftRotate64(rotate: Int): BigInteger {
    val lp = shiftRight(64 - rotate)
    val rp = shiftLeft(rotate)

    return lp.add(rp).mod(BigInteger("18446744073709551616"))
}
