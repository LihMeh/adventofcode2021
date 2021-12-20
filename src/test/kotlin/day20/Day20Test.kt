package day20

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals


class Day20Test {

    @Test
    fun task1_example() {
        assertEquals(35, task1(readResource("/day20_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(5349, task1(readResource("/day20_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(3351, task2(readResource("/day20_example.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(15806, task2(readResource("/day20_input.txt")))
    }

}