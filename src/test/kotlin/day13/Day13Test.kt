package day13

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

    @Test
    fun task1_example() {
        assertEquals(
            17, task1(
                "" +
                        "6,10\n" +
                        "0,14\n" +
                        "9,10\n" +
                        "0,3\n" +
                        "10,4\n" +
                        "4,11\n" +
                        "6,0\n" +
                        "6,12\n" +
                        "4,1\n" +
                        "0,13\n" +
                        "10,12\n" +
                        "3,4\n" +
                        "3,0\n" +
                        "8,4\n" +
                        "1,10\n" +
                        "2,14\n" +
                        "8,10\n" +
                        "9,0\n" +
                        "\n" +
                        "fold along y=7\n" +
                        "fold along x=5"
            )
        )
    }

    @Test
    fun task1_real() {
        assertEquals(790, task1(readResource("/day13_input.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(
            "\n" +
                    "###...##..#..#.####.###..####...##..##.\n" +
                    "#..#.#..#.#..#....#.#..#.#.......#.#..#\n" +
                    "#..#.#....####...#..###..###.....#.#...\n" +
                    "###..#.##.#..#..#...#..#.#.......#.#...\n" +
                    "#....#..#.#..#.#....#..#.#....#..#.#..#\n" +
                    "#.....###.#..#.####.###..#.....##...##.",
            task2(readResource("/day13_input.txt"))
        )
    }

}