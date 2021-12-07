package day07

import kotlin.math.abs

fun parseInput(input: String): List<Int> {
    return input.splitToSequence(",")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toList()
}

fun calcFuelSimple(crabs: List<Int>, target: Int): Int {
    return crabs.sumOf { abs(it - target) }
}

fun task1(input: String): Int {
    val crabs = parseInput(input)
    return crabs.minOf { calcFuelSimple(crabs, it) }
}

fun calcFuelProgressive(crabs: List<Int>, target: Int): Int {
    return crabs.sumOf { crab ->
        val targetDistance = abs(crab - target)
        targetDistance * (targetDistance + 1) / 2
    }
}

fun task2(input: String): Int {
    val crabs = parseInput(input)
    return (crabs.minOf { it }..crabs.maxOf { it })
        .minOf { calcFuelProgressive(crabs, it) }
}