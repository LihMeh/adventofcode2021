package day22

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {

    @Test
    fun task1_example() {
        assertEquals(590784, task1(readResource("/day22_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(648681, task1(readResource("/day22_input.txt")))
    }

    @Test
    fun expandCuboid() {
        assertEquals(27, expandCuboid(Region(10..12, 10..12, 10..12)).size)
    }

}