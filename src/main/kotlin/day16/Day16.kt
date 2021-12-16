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

fun bitListToInt(bits: List<Int>): Int {
    check(bits.isNotEmpty())
    var result = 0
    var posMultiplier = 1
    for (idx in bits.indices.reversed()) {
        result += posMultiplier * bits[idx]
        posMultiplier *= 2
    }
    return result
}

data class Packet(val version: Int, val typeId: Int, val literalValue: Int, val subPackets: List<Packet>) {
    init {
        if (typeId == 4) {
            check(subPackets.isEmpty())
        } else {
            check(literalValue == 0)
        }
    }

    constructor(version: Int, literalValue: Int) : this(version, 4, literalValue, listOf())
    constructor(version: Int, typeId: Int, subPackets: List<Packet>) : this(version, typeId, 0, subPackets)
}


fun parsePacket(bits: List<Int>): Packet {
    val (packet, _) = parsePacketImpl(bits)
    return packet
}

fun parsePacketImpl(bits: List<Int>): Pair<Packet, Int> {
    check(bits.size >= 6)
    val version = bitListToInt(bits.subList(0, 3))
    val typeId = bitListToInt(bits.subList(3, 6))

    if (typeId == 4) {
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
            val subPacketsLength = bitListToInt(bits.subList(7, 7 + subPacketsLengthSize))
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

fun task1(inputString: String): Int {
    val inputBits = stringToBitList(inputString)
    val rootPacket = parsePacket(inputBits)

    var versionSum = 0
    val nextPackets = ArrayDeque<Packet>()
    nextPackets.add(rootPacket)
    while (nextPackets.isNotEmpty()) {
        val packet = nextPackets.removeFirst()
        versionSum += packet.version
        nextPackets.addAll(packet.subPackets)
    }

    return versionSum
}


fun execute(packet: Packet): Int {
    if (packet.typeId == 4) {
        return packet.literalValue
    }

    if (packet.typeId in 0..3) {
        check(packet.subPackets.isNotEmpty())
    } else if (packet.typeId in 5..7) {
        check(packet.subPackets.size == 2)
    }

    val subPacketValues = packet.subPackets
        .map { execute(it) }

    if (packet.typeId == 0) {
        return subPacketValues.sum()

    } else if (packet.typeId == 1) {
        return subPacketValues
            .fold(1) { product, value -> product * value }

    } else if (packet.typeId == 2) {
        return subPacketValues.minOf { it }

    } else if (packet.typeId == 3) {
        return subPacketValues.maxOf { it }

    } else if (packet.typeId == 5) {
        return if (subPacketValues[0] > subPacketValues[1]) 1 else 0

    } else if (packet.typeId == 6) {
        return if (subPacketValues[0] < subPacketValues[1]) 1 else 0

    } else if (packet.typeId == 7) {
        return if (subPacketValues[0] == subPacketValues[1]) 1 else 0

    } else {
        throw IllegalStateException()
    }
}

fun task2(inputString: String): Int {
    val inputBits = stringToBitList(inputString)
    val rootPacket = parsePacket(inputBits)
    return execute(rootPacket)
}