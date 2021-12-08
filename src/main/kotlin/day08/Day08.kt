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