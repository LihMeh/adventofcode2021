package day23

import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {

    @Test
    fun task1_example() {
        assertEquals(
            12521,
            task1(
                "" +
                        "#############\n" +
                        "#...........#\n" +
                        "###B#C#B#D###\n" +
                        "  #A#D#C#A#\n" +
                        "  #########"
            )
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            14460,
            task1("" +
                    "#############\n" +
                    "#...........#\n" +
                    "###D#B#D#A###\n" +
                    "  #C#C#A#B#\n" +
                    "  #########\n")
        )
    }

}