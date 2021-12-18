package day18

import common.readResource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {

    private fun num(value: Long): Number {
        return Number(value)
    }

    private fun num(left: Long, right: Long): Number {
        return num(num(left), num(right))
    }

    private fun num(left: Number, right: Number): Number {
        return Number(left, right)
    }

    @Test
    fun parseNumber() {
        assertEquals(num(777), parseNumber("777"))
        assertEquals(num(1, 2), parseNumber("[1,2]"))
        assertEquals(num(num(1, 2), num(3)), parseNumber("[[1,2],3]"))
        assertEquals(num(num(9), num(8, 7)), parseNumber("[9,[8,7]]"))
        assertEquals(num(num(1, 9), num(8, 5)), parseNumber("[[1,9],[8,5]]"))
    }

    @Test
    fun reduce_explode() {
        assertEquals(parseNumber("[[[[0,9],2],3],4]"), reduce(parseNumber("[[[[[9,8],1],2],3],4]")))
        assertEquals(parseNumber("[7,[6,[5,[7,0]]]]"), reduce(parseNumber("[7,[6,[5,[4,[3,2]]]]]")))
        assertEquals(parseNumber("[[6,[5,[7,0]]],3]"), reduce(parseNumber("[[6,[5,[4,[3,2]]]],1]")))
        assertEquals(
            parseNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
            reduce(parseNumber("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]"))
        )
        assertEquals(
            parseNumber("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
            reduce(parseNumber("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"))
        )
    }

    @Test
    fun reduce_split() {
        assertEquals(parseNumber("[5,5]"), reduce(parseNumber("10")))
        assertEquals(parseNumber("[5,6]"), reduce(parseNumber("11")))
        assertEquals(parseNumber("[6,6]"), reduce(parseNumber("12")))
        assertEquals(parseNumber("[[5,6],[6,6]]"), reduce(parseNumber("[11,12]")))
    }

    @Test
    fun reduce_complex() {
        assertEquals(
            parseNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
            reduce(parseNumber("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"))
        )
    }

    @Test
    fun magnitude() {
        assertEquals(143, magnitude(parseNumber("[[1,2],[[3,4],5]]")))
        assertEquals(1384, magnitude(parseNumber("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")))
        assertEquals(445, magnitude(parseNumber("[[[[1,1],[2,2]],[3,3]],[4,4]]")))
        assertEquals(791, magnitude(parseNumber("[[[[3,0],[5,3]],[4,4]],[5,5]]")))
        assertEquals(1137, magnitude(parseNumber("[[[[5,0],[7,4]],[5,5]],[6,6]]")))
        assertEquals(3488, magnitude(parseNumber("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")))
    }

    @Test
    fun task1_example() {
        assertEquals(4140, task1(readResource("/day18_example.txt")))
    }

    @Test
    fun task1_real() {
        assertEquals(-1, task1(readResource("/day18_input.txt")))
    }

}