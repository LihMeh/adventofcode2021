package day05

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class Day05Test {
    @Test
    fun task1_example() {
        assertEquals(
            5,
            task1(readResource("/day05_example.txt"))
        )
    }

    @Test
    fun task1_real() {
        assertEquals(
            7438,
            task1(readResource("/day05_input.txt"))
        )
    }

    @Test
    fun task2_example() {
        assertEquals(
            12,
            task2(readResource("/day05_example.txt"))
        )
    }

    @Test
    fun task2_real() {
        assertEquals(
            -1,
            task2(readResource("/day05_input.txt"))
        )
    }

    @Test
    fun intersect() {
        assertEquals(
            Line(Point2D(5, 1), Point2D(10, 1)),
            intersectLines(
                Line(Point2D(1, 1), Point2D(10, 1)),
                Line(Point2D(5, 1), Point2D(20, 1))
            )
        )

        assertEquals(
            Line(Point2D(1, 5), Point2D(1, 10)),
            intersectLines(
                Line(Point2D(1, 1), Point2D(1, 10)),
                Line(Point2D(1, 5), Point2D(1, 20))
            )
        )

        assertEquals(
            Line(Point2D(7, 4), Point2D(7, 4)),
            intersectLines(
                Line(Point2D(7, 0), Point2D(7, 4)),
                Line(Point2D(3, 4), Point2D(9, 4))
            )
        )

        assertEquals(
            Line(Point2D(1, 1), Point2D(1, 1)),
            intersectLines(
                Line(Point2D(0, 1), Point2D(10, 1)),
                Line(Point2D(0, 0), Point2D(5, 5))
            )
        )

        assertEquals(
            Line(Point2D(1, 1), Point2D(1, 1)),
            intersectLines(
                Line(Point2D(1, 0), Point2D(1, 10)),
                Line(Point2D(0, 0), Point2D(5, 5))
            )
        )

        assertEquals(
            Line(Point2D(1, 1), Point2D(1, 1)),
            intersectLines(
                Line(Point2D(0, 0), Point2D(2, 2)),
                Line(Point2D(2, 0), Point2D(0, 2))
            )
        )

        assertNull(
            intersectLines(
                Line(Point2D(0, 0), Point2D(2, 2)),
                Line(Point2D(3, 2), Point2D(5, 0))
            )
        )

        assertNull(
            intersectLines(
                Line(Point2D(2, 2), Point2D(2, 1)),
                Line(Point2D(0, 9), Point2D(5, 9))
            )
        )

        throw NotImplementedError("Please test parallel diagonals")
    }

}