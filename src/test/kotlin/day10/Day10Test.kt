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

    @Test
    fun autocomplete() {
        assertEquals("}}]])})]".toList(), autocompleteLine("[({(<(())[]>[[{[]{<()<>>"))
        assertEquals(")}>]})".toList(), autocompleteLine("[(()[<>])]({[<{<<[]>>("))
        assertEquals("}}>}>))))".toList(), autocompleteLine("(((({<>}<{<{<>}{[]{[]{}"))
        assertEquals("]]}}]}]}>".toList(), autocompleteLine("{<[[]]>}<{[{[{[]{()[[[]"))
        assertEquals("])}>".toList(), autocompleteLine("<{([{{}}[<[[[<>{}]]]>[]]"))
    }

    @Test
    fun autocomplete_score() {
        assertEquals(288957, calcAutocompleteScore("}}]])})]".toList()))
        assertEquals(5566, calcAutocompleteScore(")}>]})".toList()))
        assertEquals(1480781, calcAutocompleteScore("}}>}>))))".toList()))
        assertEquals(995444, calcAutocompleteScore("]]}}]}]}>".toList()))
        assertEquals(294, calcAutocompleteScore("])}>".toList()))
    }

    @Test
    fun task2_example() {
        assertEquals(288957, task2(readResource("/day10_example.txt")))
    }

    @Test
    fun task2_real() {
        assertEquals(1870887234, task2(readResource("/day10_input.txt")))
    }
}
