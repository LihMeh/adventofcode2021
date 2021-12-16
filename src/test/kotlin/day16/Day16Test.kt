package day16

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {

    @Test
    fun parse_packet() {
        assertEquals(
            Packet(6, 2021),
            parsePacket(stringToBitList("D2FE28"))
        )
        assertEquals(
            Packet(
                1, 6, listOf(
                    Packet(6, 10),
                    Packet(2, 20)
                )
            ),
            parsePacket(stringToBitList("38006F45291200"))
        )
        assertEquals(
            Packet(
                7, 3, listOf(
                    Packet(2, 1),
                    Packet(4, 2),
                    Packet(1, 3)
                )
            ),
            parsePacket(stringToBitList("EE00D40C823060"))
        )
    }

    @Test
    fun task1_example() {
        assertEquals(16, task1("8A004A801A8002F478"))
        assertEquals(12, task1("620080001611562C8802118E34"))
        assertEquals(23, task1("C0015000016115A2E0802F182340"))
        assertEquals(31, task1("A0016C880162017C3686B18A3D4780"))
    }

    @Test
    fun task1_real() {
        assertEquals(963, task1(readResource("/day16_input.txt")))
    }

}