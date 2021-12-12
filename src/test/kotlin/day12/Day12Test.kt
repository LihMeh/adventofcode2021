package day12

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    @Test
    fun task1_examples() {
        assertEquals(
            10,
            task1("start-A\n" +
                    "start-b\n" +
                    "A-c\n" +
                    "A-b\n" +
                    "b-d\n" +
                    "A-end\n" +
                    "b-end")
        )
        assertEquals(
            19,
            task1("dc-end\n" +
                    "HN-start\n" +
                    "start-kj\n" +
                    "dc-start\n" +
                    "dc-HN\n" +
                    "LN-dc\n" +
                    "HN-end\n" +
                    "kj-sa\n" +
                    "kj-HN\n" +
                    "kj-dc")
        )
        assertEquals(
            226,
            task1("fs-end\n" +
                    "he-DX\n" +
                    "fs-he\n" +
                    "start-DX\n" +
                    "pj-DX\n" +
                    "end-zg\n" +
                    "zg-sl\n" +
                    "zg-pj\n" +
                    "pj-he\n" +
                    "RW-he\n" +
                    "fs-DX\n" +
                    "pj-RW\n" +
                    "zg-RW\n" +
                    "start-pj\n" +
                    "he-WI\n" +
                    "zg-he\n" +
                    "pj-fs\n" +
                    "start-RW")
        )
    }

    @Test
    fun task1_real() {
        assertEquals(4720, task1(readResource("/day12_input.txt")))
    }

}