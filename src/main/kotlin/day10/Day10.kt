package day10

val openCharToCloseChar = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

fun findIllegalCharacter(input: String): Char? {
    val expectedCloseChars = ArrayDeque<Char>()
    val charIterator = input.toCharArray().iterator()
    while (charIterator.hasNext()) {
        val nextChar = charIterator.next()
        val isNextCharOpen = openCharToCloseChar.containsKey(nextChar)
        if (isNextCharOpen) {
            expectedCloseChars.addFirst(openCharToCloseChar[nextChar]!!)
        } else {
            val expectedNextChar = expectedCloseChars.removeFirstOrNull()
            if (nextChar != expectedNextChar) {
                return nextChar
            }
        }
    }

    return null
}

fun task1(input: String): Int {
    val charToScore = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    return input
        .split("\n")
        .filter { it.isNotBlank() }
        .mapNotNull { findIllegalCharacter(it) }
        .map { charToScore[it]!! }
        .sum()
}