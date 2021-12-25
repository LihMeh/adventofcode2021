package day25

import common.readResource
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day25Test {

    @Test
    fun task1_example() {
        assertEquals(58, task1(readResource("/day25_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(0, task1(readResource("/day25_input.txt")))
    }

}