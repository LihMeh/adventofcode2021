package day10

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class Day10Test {

    @Test
    fun test_illegal_character() {
        // good ones
        assertNull(findIllegalCharacter(""))
        assertNull(findIllegalCharacter("()"))
        assertNull(findIllegalCharacter("[]"))
        assertNull(findIllegalCharacter("([])"))
        assertNull(findIllegalCharacter("{()()()}"))
        assertNull(findIllegalCharacter("<([{}])>"))
        assertNull(findIllegalCharacter("[<>({}){}[([])<>]]"))
        assertNull(findIllegalCharacter("(((((((((())))))))))"))


        // incomplete
        assertNull(findIllegalCharacter(""))
        assertNull(findIllegalCharacter("("))
        assertNull(findIllegalCharacter("["))
        assertNull(findIllegalCharacter("(["))
        assertNull(findIllegalCharacter("{()()("))
        assertNull(findIllegalCharacter("<([{}]"))
        assertNull(findIllegalCharacter("[<>({}){}[([])<>"))
        assertNull(findIllegalCharacter("(((((((((())))))))"))

        // illegal
        assertEquals(']', findIllegalCharacter("(]"))
        assertEquals('>', findIllegalCharacter("{()()()>"))
        assertEquals('}', findIllegalCharacter("(((()))}"))
        assertEquals(')', findIllegalCharacter("<([]){()}[{}])"))
    }

    @Test
    fun task1_example() {
        assertEquals(26397, task1(readResource("/day10_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(315693, task1(readResource("/day10_input.txt")))
    }
}
