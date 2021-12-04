package day03


import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun task1_example() {
        assertEquals(
            198,
            task1(
                "00100\n" +
                        "11110\n" +
                        "10110\n" +
                        "10111\n" +
                        "10101\n" +
                        "01111\n" +
                        "00111\n" +
                        "11100\n" +
                        "10000\n" +
                        "11001\n" +
                        "00010\n" +
                        "01010"
            )
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            4138664,
            task1(readResource("/day03_input.txt"))
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            230,
            task2(
                "00100\n" +
                        "11110\n" +
                        "10110\n" +
                        "10111\n" +
                        "10101\n" +
                        "01111\n" +
                        "00111\n" +
                        "11100\n" +
                        "10000\n" +
                        "11001\n" +
                        "00010\n" +
                        "01010"
            )
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            4273224,
            task2(readResource("/day03_input.txt"))
        )
    }
}