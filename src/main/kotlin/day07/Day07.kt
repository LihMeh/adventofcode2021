package day07

import kotlin.math.abs

fun parseInput(input: String): List<Int> {
    return input.splitToSequence(",")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toList()
}

fun calcFuel(crabs: List<Int>, target: Int): Int {
    return crabs.sumOf { abs(it - target) }
}

fun task1(input: String): Int {
    val crabs = parseInput(input)

    val minValue = crabs.minOfOrNull { calcFuel(crabs, it) }
    checkNotNull(minValue)
    return minValue
}