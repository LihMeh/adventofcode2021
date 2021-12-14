package day14

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals


class Day14Test {

    @Test
    fun task1_example() {
        assertEquals(1588, task1(readResource("/day14_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(2975, task1(readResource("/day14_input.txt")))
    }

    @Test
    fun task2_example() {
        assertEquals(2188189693529, task2(readResource("/day14_example.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(3015383850689, task2(readResource("/day14_input.txt")))
    }

    @Test
    fun process() {
        val rules = parseInput(readResource("/day14_example.txt")).rules
        assertEquals(
            mapOf(
                'N' to 1L,
                'C' to 1L
            ),
            process("NC", 0, rules)
        )
        assertEquals(
            mapOf(
                'N' to 2L,
                'C' to 1L,
                'B' to 1L
            ),
            process("NNCB", 0, rules)
        )
        assertEquals(
            mapOf(
                'N' to 2L,
                'C' to 1L
            ),
            process("NN", 1, rules)
        )
        assertEquals(
            mapOf(
                'N' to 2L,
                'C' to 2L,
                'B' to 1L
            ),
            process("NN", 2, rules)
        )
        assertEquals(
            mapOf(
                'N' to 2L,
                'C' to 2L,
                'B' to 2L,
                'H' to 1L
            ),
            process("NNCB", 1, rules)
        )
        assertEquals(
            mapOf(
                'N' to 11L,
                'B' to 23L,
                'C' to 10L,
                'H' to 5L
            ),
            process("NNCB", 4, rules)
        )
    }

}