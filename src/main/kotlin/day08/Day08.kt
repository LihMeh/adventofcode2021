package day08

data class Entry(val signalPatterns: List<Set<Char>>, val outputValue: List<Set<Char>>)

fun parseInput(input: String): List<Entry> {
    return input.split("\n")
        .flatMap { row -> row.split("|") }
        .map { row ->
            row.split(" ")
                .filter { value -> value.isNotBlank() }
                .map { value -> value.toCharArray().toSet() }
        }
        .windowed(2, 2)
        .map { pair -> Entry(pair[0], pair[1]) }
        .toList()
}

fun task1(input: String): Int {
    val entries = parseInput(input)
    val expectedSegmentCounts = setOf(2, 3, 4, 7)
    return entries
        .flatMap { it.outputValue }
        .count { expectedSegmentCounts.contains(it.size) }
}

fun task2(input: String): Int {
    val entries = parseInput(input)
    return entries
        .map { predictOutputValue(it) }
        .sum()
}

val digitToExpectedMapping: Map<Int, Set<Char>> = mapOf(
    0 to setOf('a', 'b', 'c', 'e', 'f', 'g'),
    1 to setOf('c', 'f'),
    2 to setOf('a', 'c', 'd', 'e', 'g'),
    3 to setOf('a', 'c', 'd', 'f', 'g'),
    4 to setOf('b', 'c', 'd', 'f'),
    5 to setOf('a', 'b', 'd', 'f', 'g'),
    6 to setOf('a', 'b', 'd', 'e', 'f', 'g'),
    7 to setOf('a', 'c', 'f'),
    8 to setOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),
    9 to setOf('a', 'b', 'c', 'd', 'f', 'g')
)

fun predictOutputValue(entry: Entry): Int {
    return 1
}