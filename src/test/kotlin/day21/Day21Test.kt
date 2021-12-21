package day21

import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    @Test
    fun task1_example() {
        assertEquals(739785, task1(listOf(3, 7)))
    }

    @Test
    fun task1_real() {
        assertEquals(598416, task1(listOf(0, 1)))
    }

    @Test
    fun task2_example() {
        assertEquals(444356092776315, task2(listOf(3, 7)))
    }

    @Test
    fun task2_real() {
        assertEquals(-1, task2(listOf(0, 1)))
    }

}