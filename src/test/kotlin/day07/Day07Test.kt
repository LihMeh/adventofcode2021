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
    fun calc_fuel_simple() {
        assertEquals(
            37,
            calcFuelSimple(listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14), 2)
        )
    }


    @Test
    fun task1_real() {
        assertEquals(343441, task1(readResource("/day07_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(168, task2("16,1,2,0,4,2,7,1,2,14"))
    }

    @Test
    fun calc_fuel_progressive() {
        assertEquals(0, calcFuelProgressive(listOf(1), 1))
        assertEquals(1, calcFuelProgressive(listOf(1), 2))
        assertEquals(3, calcFuelProgressive(listOf(1), 3))
        assertEquals(6, calcFuelProgressive(listOf(1), 4))

        val crabs = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)
        assertEquals(206, calcFuelProgressive(crabs, 2))
        assertEquals(168, calcFuelProgressive(crabs, 5))
    }

    @Test
    fun task2_real() {
        assertEquals(98925151, task2(readResource("/day07_input.txt")))
    }

}
