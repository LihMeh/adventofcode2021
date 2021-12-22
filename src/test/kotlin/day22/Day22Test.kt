package day22

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {

    @Test
    fun task1_example() {
        assertEquals(590784L, task1(readResource("/day22_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(648681L, task1(readResource("/day22_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(2758514936282235L, task2(readResource("/day22_example2.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(1302784472088899L, task2(readResource("/day22_input.txt")))
    }

}