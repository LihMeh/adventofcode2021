package day23

import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {

    @Test
    fun task1_example() {
        assertEquals(
            12521,
            task(
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
            task(
                "" +
                        "#############\n" +
                        "#...........#\n" +
                        "###D#B#D#A###\n" +
                        "  #C#C#A#B#\n" +
                        "  #########\n"
            )
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            44169,
            task(
                "" +
                        "#############\n" +
                        "#...........#\n" +
                        "###B#C#B#D###\n" +
                        "  #D#C#B#A#\n" +
                        "  #D#B#A#C#\n" +
                        "  #A#D#C#A#\n" +
                        "  #########"
            )
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            41366,
            task(
                "" +
                        "#############\n" +
                        "#...........#\n" +
                        "###D#B#D#A###\n" +
                        "  #D#C#B#A#\n" +
                        "  #D#B#A#C#\n" +
                        "  #C#C#A#B#\n" +
                        "  #########\n"
            )
        )
    }

}