package day06

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun task1_example() {
        assertEquals(1, task1("3", 3))
        assertEquals(2, task1("3", 4))
        assertEquals(2, task1("3", 4))
        assertEquals(26, task1("3,4,3,1,2", 18))
        assertEquals(5934, task1("3,4,3,1,2", 80))
    }

    @Test
    fun task1_real() {
        assertEquals(-1, task1(readResource("/day06_input.txt"), 80))
    }

}