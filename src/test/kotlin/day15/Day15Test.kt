package day15

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {

    @Test
    fun task1_example() {
        assertEquals(40, task1(readResource("/day15_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(415, task1(readResource("/day15_input.txt")))
    }

}