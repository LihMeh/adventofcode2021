package day05

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    @Test
    fun task1_example() {
        assertEquals(
            5,
            task1(readResource("/day05_example.txt"))
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            7438,
            task1(readResource("/day05_input.txt"))
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            12,
            task2(readResource("/day05_example.txt"))
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            -1,
            task2(readResource("/day05_input.txt"))
        )
    }

}