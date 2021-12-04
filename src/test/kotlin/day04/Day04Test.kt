package day04

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    @Test
    fun task1_example() {
        assertEquals(
            4512,
            task1(readResource("/day04_example.txt"))
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            41668,
            task1(readResource("/day04_input.txt"))
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            1924,
            task2(readResource("/day04_example.txt"))
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            10478,
            task2(readResource("/day04_input.txt"))
        )
    }
}