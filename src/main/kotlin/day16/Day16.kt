package day16

fun charToBitList(chr: Char): List<Int> {
    val result = mutableListOf(0, 0, 0, 0)
    var intVal = chr.digitToInt(16)
    for (idx in 3 downTo 0) {
        result[idx] = intVal % 2
        intVal /= 2
    }
    return result
}

fun stringToBitList(inputString: String): List<Int> {
    return inputString
        .map { chr -> charToBitList(chr) }
        .flatten()
        .toList()
}

fun bitListToInt(bits: List<Int>): Long {
    check(bits.isNotEmpty())
    var result = 0L
    var posMultiplier = 1L
    for (idx in bits.indices.reversed()) {
        result += posMultiplier * bits[idx]
        posMultiplier *= 2
    }
    return result
}

data class Packet(val version: Long, val typeId: Long, val literalValue: Long, val subPackets: List<Packet>) {
    init {
        if (typeId == 4L) {
            check(subPackets.isEmpty())
        } else {
            check(literalValue == 0L)
        }
    }

    constructor(version: Long, literalValue: Long) : this(version, 4, literalValue, listOf())
    constructor(version: Long, typeId: Long, subPackets: List<Packet>) : this(version, typeId, 0L, subPackets)
}


fun parsePacket(bits: List<Int>): Packet {
    val (packet, _) = parsePacketImpl(bits)
    return packet
}

fun parsePacketImpl(bits: List<Int>): Pair<Packet, Int> {
    check(bits.size >= 6)
    val version = bitListToInt(bits.subList(0, 3))
    val typeId = bitListToInt(bits.subList(3, 6))

    if (typeId == 4L) {
        check(bits.size >= 11)
        val literalValueBits = mutableListOf<Int>()
        var nextValueStartIdx = 6
        do {
            for (idx in (nextValueStartIdx + 1) until (nextValueStartIdx + 5)) {
                literalValueBits.add(bits[idx])
            }
            val isNotLast = bits[nextValueStartIdx] > 0
            nextValueStartIdx += 5
        } while (isNotLast)
        return Packet(version, bitListToInt(literalValueBits)) to (nextValueStartIdx)

    } else {
        val lengthTypeId = bits[6]
        if (lengthTypeId == 0) {
            val subPacketsLengthSize = 15
            val subPacketsLength = bitListToInt(bits.subList(7, 7 + subPacketsLengthSize)).toInt()
            check(subPacketsLength > 0)

            var nextSubPacketStart = 7 + subPacketsLengthSize
            val subPacketsRightBorder = nextSubPacketStart + subPacketsLength
            val subPackets = mutableListOf<Packet>()
            while (nextSubPacketStart < subPacketsRightBorder) {
                val subPacketsTail = bits.subList(nextSubPacketStart, subPacketsRightBorder)
                val (nextSubPacket, nextSubPacketSize) = parsePacketImpl(subPacketsTail)
                subPackets.add(nextSubPacket)
                nextSubPacketStart += nextSubPacketSize
            }

            return Packet(version, typeId, subPackets) to subPacketsRightBorder

        } else {
            val subPackets = mutableListOf<Packet>()
            var subPacketCountLeft = bitListToInt(bits.subList(7, 7 + 11))
            var nextSubPacketStart = 7 + 11
            while (subPacketCountLeft > 0) {
                val subPacketsTail = bits.subList(nextSubPacketStart, bits.lastIndex + 1)
                val (nextSubPacket, nextSubPacketSize) = parsePacketImpl(subPacketsTail)
                subPackets.add(nextSubPacket)
                nextSubPacketStart += nextSubPacketSize
                subPacketCountLeft--
            }
            return Packet(version, typeId, subPackets) to nextSubPacketStart
        }
    }
}

fun task1(inputString: String): Long {
    val inputBits = stringToBitList(inputString)
    val rootPacket = parsePacket(inputBits)

    var versionSum = 0L
    val nextPackets = ArrayDeque<Packet>()
    nextPackets.add(rootPacket)
    while (nextPackets.isNotEmpty()) {
        val packet = nextPackets.removeFirst()
        versionSum += packet.version
        nextPackets.addAll(packet.subPackets)
    }

    return versionSum
}


fun execute(packet: Packet): Long {
    if (packet.typeId == 4L) {
        return packet.literalValue
    }

    if (packet.typeId in 0..3) {
        check(packet.subPackets.isNotEmpty())
    } else if (packet.typeId in 5..7) {
        check(packet.subPackets.size == 2)
    }

    val subPacketValues = packet.subPackets
        .map { execute(it) }

    if (packet.typeId == 0L) {
        return subPacketValues.sum()

    } else if (packet.typeId == 1L) {
        return subPacketValues
            .fold(1) { product, value -> product * value }

    } else if (packet.typeId == 2L) {
        return subPacketValues.minOf { it }

    } else if (packet.typeId == 3L) {
        return subPacketValues.maxOf { it }

    } else if (packet.typeId == 5L) {
        return if (subPacketValues[0] > subPacketValues[1]) 1 else 0

    } else if (packet.typeId == 6L) {
        return if (subPacketValues[0] < subPacketValues[1]) 1 else 0

    } else if (packet.typeId == 7L) {
        return if (subPacketValues[0] == subPacketValues[1]) 1 else 0

    } else {
        throw IllegalStateException()
    }
}

fun task2(inputString: String): Long {
    val inputBits = stringToBitList(inputString)
    val rootPacket = parsePacket(inputBits)
    return execute(rootPacket)
}