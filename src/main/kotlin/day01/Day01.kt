package day01

fun parseIntList(input: String): List<Int> {
    return input.splitToSequence("\n")
        .filter { row -> row.isNotBlank() }
        .map { row -> row.toInt() }
        .toList()
}

fun task1(input: String): Int {
    val depthReport = parseIntList(input)

    val increaseCount = depthReport
        .windowed(2)
        .count { pair -> pair[1] > pair[0] }

    return increaseCount
}

fun task2(input: String): Int {
    val depthReport = parseIntList(input)

    val slidingWindows = depthReport
        .windowed(3)
        .map { triple -> triple.sum() }
    val increaseCount = slidingWindows
        .windowed(2)
        .count { pair -> pair[1] > pair[0] }

    return increaseCount
}