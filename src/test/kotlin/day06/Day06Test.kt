package day06

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    @Test
    fun task_examples() {
        assertEquals(1, task("3", 3))
        assertEquals(2, task("3", 4))
        assertEquals(2, task("3", 4))
        assertEquals(26, task("3,4,3,1,2", 18))
        assertEquals(5934, task("3,4,3,1,2", 80))
        assertEquals(26984457539, task("3,4,3,1,2", 256))
    }

    @Test
    fun task1_real() {
        assertEquals(359344, task(readResource("/day06_input.txt"), 80))
    }

    @Test
    fun task2_real() {
        assertEquals(1629570219571, task(readResource("/day06_input.txt"), 256))
    }


}