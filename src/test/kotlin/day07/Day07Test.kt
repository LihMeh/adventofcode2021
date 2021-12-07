package day07

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    @Test
    fun task1_example() {
        assertEquals(37, task1("16,1,2,0,4,2,7,1,2,14"))
    }

    @Test
    fun calc_fuel() {
        assertEquals(
            37,
            calcFuel(listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), 2)
        )
    }

    @Test
    fun task1_real() {
        assertEquals(-1, task1(readResource("/day07_input.txt")))
    }

}
