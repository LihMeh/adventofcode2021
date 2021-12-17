package day17

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {

    @Test
    fun task1_example() {
        assertEquals(45, task1("target area: x=20..30, y=-10..-5"))
    }

    @Test
    fun task1_real() {
        assertEquals(4005, task1(readResource("/day17_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(112, task2("target area: x=20..30, y=-10..-5"))
    }

    @Test
    fun task2_real() {
        assertEquals(2953, task2(readResource("/day17_input.txt")))
    }

}