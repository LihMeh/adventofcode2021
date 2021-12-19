package day19

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {

    @Test
    fun task1_example() {
        assertEquals(79, task1(readResource("/day19_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(465, task1(readResource("/day19_input.txt")))
    }

    /*
представьте себе такой одномерный ввод:
координата абсолтная: 0 1 2 3 4 5 6 7 8 9
                маяк:   *     *     *
     центры сканеров:     0     1

сканер 0: -1  2  5
сканер 1: -4 -1  2

leftStartingPoint: -1
rightStartingPoint: -4

targetDiff (result): leftStartingPoint - rightStartingPoint
targetDiff: 3

Почему?
Требования к targetDiff такие: если к точке в координатах правого маяка прибавить targetDiff, получится точка в координатах левого маяка.

маяк 1: -4 + 3 = -1
маяк 4: -1 + 3 =  2
маяк 7:  2 + 3 =  5

 */
    @Test
    fun find_intersection_shift_example_small() {
        assertEquals(
            listOf(3),
            findIntersectionShift(
                setOf(listOf(-1), listOf(2), listOf(5)),
                setOf(listOf(-4), listOf(-1), listOf(2)),
                3
            )
        )
    }

    /*
    абсолютная координата:  0 1 2 3 4 5 6 7 8 9
                     маяк:    *   *     *     *
          центры сканеров:      1         0

     сканер 0: -4 -1  2
     сканер 1: -1  1  4
     targetDiff: -5
     */
    @Test
    fun find_intersection_shift_example_big() {
        assertEquals(
            listOf(-5),
            findIntersectionShift(
                setOf(listOf(-4), listOf(-1), listOf(2)),
                setOf(listOf(-1), listOf(1), listOf(4)),
                2
            )
        )
    }

    @Test
    fun directions() {
        val scanners = parseInput(
            "" +
                    "--- scanner 0 ---\n" +
                    "-1,-1,1\n" +
                    "-2,-2,2\n" +
                    "-3,-3,3\n" +
                    "-2,-3,1\n" +
                    "5,6,-4\n" +
                    "8,0,7\n" +
                    "\n" +
                    "--- scanner 0 ---\n" +
                    "1,-1,1\n" +
                    "2,-2,2\n" +
                    "3,-3,3\n" +
                    "2,-1,3\n" +
                    "-5,4,-6\n" +
                    "-8,-7,0\n" +
                    "\n" +
                    "--- scanner 0 ---\n" +
                    "-1,-1,-1\n" +
                    "-2,-2,-2\n" +
                    "-3,-3,-3\n" +
                    "-1,-3,-2\n" +
                    "4,6,5\n" +
                    "-7,0,8\n" +
                    "\n" +
                    "--- scanner 0 ---\n" +
                    "1,1,-1\n" +
                    "2,2,-2\n" +
                    "3,3,-3\n" +
                    "1,3,-2\n" +
                    "-4,-6,5\n" +
                    "7,0,8\n" +
                    "\n" +
                    "--- scanner 0 ---\n" +
                    "1,1,1\n" +
                    "2,2,2\n" +
                    "3,3,3\n" +
                    "3,1,2\n" +
                    "-6,-4,-5\n" +
                    "0,7,-8"
        )
        for (scannerToRotate in scanners) {
            val allDirections = allPossibleDirections(scannerToRotate)
            val expectedButNotFoundDirections = scanners
                .filter { !allDirections.contains(it) }
                .toList()
            assert(expectedButNotFoundDirections.isEmpty()) {
                "" +
                        "scanner to rotate:\n" + scannerToRotate + "\n" +
                        "not found directions: \n" + expectedButNotFoundDirections + "\n"
            }
        }
    }

}