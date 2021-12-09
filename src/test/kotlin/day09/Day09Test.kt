package day09

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    @Test
    fun task1_examlpe() {
        assertEquals(15, task1(readResource("/day09_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(566, task1(readResource("/day09_input.txt")))
    }

}