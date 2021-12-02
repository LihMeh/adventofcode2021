package day2

import common.ResourceReader
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    @Test
    fun task1_example() {
        assertEquals(
            "150", Day2.task1(
                "" +
                        "forward 5\n" +
                        "down 5\n" +
                        "forward 8\n" +
                        "up 3\n" +
                        "down 8\n" +
                        "forward 2"
            )
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            "1924923",
            Day2.task1(ResourceReader.readResource("/day2_input.txt"))
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            "900", Day2.task2(
                "" +
                        "forward 5\n" +
                        "down 5\n" +
                        "forward 8\n" +
                        "up 3\n" +
                        "down 8\n" +
                        "forward 2"
            )
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            "1982495697",
            Day2.task2(ResourceReader.readResource("/day2_input.txt"))
        )
    }


}