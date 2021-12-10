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
        .sumOf { charToScore[it]!! }
}

fun autocompleteLine(input: String): List<Char> {
    val expectedCloseChars = ArrayDeque<Char>()
    val charIterator = input.toCharArray().iterator()
    while (charIterator.hasNext()) {
        val nextChar = charIterator.next()
        val isNextCharOpen = openCharToCloseChar.containsKey(nextChar)
        if (isNextCharOpen) {
            expectedCloseChars.addFirst(openCharToCloseChar[nextChar]!!)
        } else {
            val expectedNextChar = expectedCloseChars.removeFirstOrNull()
            check(nextChar == expectedNextChar) { "corrupted input" }
        }
    }

    return expectedCloseChars.toList()
}

fun task2(input: String): Long {
    val sortedScores = input.split("\n")
        .filter { it.isNotBlank() }
        .filter { findIllegalCharacter(it) == null }
        .map { autocompleteLine(it) }
        .map { calcAutocompleteScore(it) }
        .sortedBy { it }
    check(sortedScores.size % 2 == 1)

    return sortedScores[sortedScores.size / 2]
}

val closeCharToAutocompleteScore = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

fun calcAutocompleteScore(input: List<Char>): Long {
    return input
        .map { closeCharToAutocompleteScore[it]!!}
        .fold(0L) { acc, next -> acc * 5 + next }
}