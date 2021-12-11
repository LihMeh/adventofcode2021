package day11

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun test_task1_step_by_step() {
        val states = listOf(
            "" +
                    "11111\n" +
                    "19991\n" +
                    "19191\n" +
                    "19991\n" +
                    "11111",
            "34543\n" +
                    "40004\n" +
                    "50005\n" +
                    "40004\n" +
                    "34543",
            "45654\n" +
                    "51115\n" +
                    "61116\n" +
                    "51115\n" +
                    "45654"
        )
        for (idx in 0 until (states.size - 1)) {
            val stateBefore = parseState(states[idx])
            val expectedStateAfter = parseState(states[idx + 1])
            assertEquals(expectedStateAfter, task1Step(stateBefore))
        }
    }

    @Test
    fun test_task1_longer_sequences() {
        var state = parseState(
            "" +
                    "5483143223\n" +
                    "2745854711\n" +
                    "5264556173\n" +
                    "6141336146\n" +
                    "6357385478\n" +
                    "4167524645\n" +
                    "2176841721\n" +
                    "6882881134\n" +
                    "4846848554\n" +
                    "5283751526"
        )
        for (stepNum in 0 until 10) {
            state = task1Step(state)
        }
        assertEquals(
            parseState(
                "" +
                        "0481112976\n" +
                        "0031112009\n" +
                        "0041112504\n" +
                        "0081111406\n" +
                        "0099111306\n" +
                        "0093511233\n" +
                        "0442361130\n" +
                        "5532252350\n" +
                        "0532250600\n" +
                        "0032240000"
            ),
            state
        )
    }

    @Test
    fun task1_example() {
        assertEquals(
            1656,
            task1(
                "" +
                        "5483143223\n" +
                        "2745854711\n" +
                        "5264556173\n" +
                        "6141336146\n" +
                        "6357385478\n" +
                        "4167524645\n" +
                        "2176841721\n" +
                        "6882881134\n" +
                        "4846848554\n" +
                        "5283751526"
            )
        )
    }

    @Test
    fun task1_real() {
        assertEquals(-1, task1(readResource("/day11_input.txt")))
    }

}