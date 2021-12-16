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

    @Test
    fun task2_example() {
        assertEquals(3, task2("C200B40A82"))
        assertEquals(54, task2("04005AC33890"))
        assertEquals(7, task2("880086C3E88112"))
        assertEquals(9, task2("CE00C43D881120"))
        assertEquals(1, task2("D8005AC2A8F0"))
        assertEquals(0, task2("F600BC2D8F"))
        assertEquals(0, task2("9C005AC2F8F0"))
        assertEquals(1, task2("9C0141080250320F1802104A08"))
    }

    @Test
    fun task2_real() {
        assertEquals(1549026292886, task2(readResource("/day16_input.txt")))
    }

}