package day01

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    @Test
    fun task1_example() {
        assertEquals(
            7,
            task1(readResource("/day01_example.txt"))
        )
    }

    @Test

    fun task2_example() {
        assertEquals(
            5,
            task2(readResource("/day01_example.txt"))
        )
    }

    @Test

    fun task1_real() {
        assertEquals(
            1559,
            task1(readResource("/day01_input.txt"))
        )
    }

    @Test

    fun task2_real() {
        assertEquals(
            1600,
            task2(readResource("/day01_input.txt"))
        )
    }
}