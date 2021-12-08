package day08

import common.readResource
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day08Test {

    @Test
    fun task1_example() {
        assertEquals(26, task1(readResource("/day08_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(452, task1(readResource("/day08_input.txt")))
    }

    @Test
    fun task2_example_short() {
        assertEquals(
            5353,
            task2("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
        )
    }


    @Test
    fun task2_example_full() {
        assertEquals(61229, task2(readResource("/day08_example.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(1096964, task2(readResource("/day08_input.txt")))
    }

}