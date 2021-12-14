package day14

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals


class Day14Test {

    @Test
    fun task1_example() {
        assertEquals(1588, task1(readResource("/day14_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(2975, task1(readResource("/day14_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(2188189693529, task2(readResource("/day14_example.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(-1, task2(readResource("/day14_input.txt")))
    }

}