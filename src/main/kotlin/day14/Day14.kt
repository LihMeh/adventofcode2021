package day14

data class Input(val polymer: String, val rules: Map<String, Char>)

fun parseInput(input: String): Input {
    val (polymer, insertionRulesRaw) = input.split("\n\n")
    val rules = insertionRulesRaw
        .split("\n")
        .map { row ->
            val (left, right) = row.split(" -> ")
            left to right.first()
        }
        .toMap()
    return Input(polymer, rules)
}

fun process(input: String, stepsLeft: Int, rules: Map<String, Char>): Map<Char, Long> {
    return process(input, stepsLeft, rules, mutableMapOf())
}

fun process(
    input: String,
    stepsLeft: Int,
    rules: Map<String, Char>,
    cache: MutableMap<Pair<String, Int>, Map<Char, Long>>
): Map<Char, Long> {
    check(input.length >= 2)
    check(stepsLeft >= 0)

    if (stepsLeft == 0) {
        val result = mutableMapOf<Char, Long>()
        for (chr in input) {
            result[chr] = (result[chr] ?: 0) + 1
        }
        return result
    }

    if (input.length == 2) {
        val cacheKey = input to stepsLeft
        val cachedResult = cache[cacheKey]
        if (cachedResult != null) {
            return cachedResult
        }

        val left = input.first()
        val right = input.last()
        val middle = rules[input]!!
        val leftPairResult = process("" + left + middle, stepsLeft - 1, rules, cache)
        val rightPairResult = process("" + middle + right, stepsLeft - 1, rules, cache)
        val result = mutableMapOf<Char, Long>()
        for (chr in leftPairResult.keys.union(rightPairResult.keys)) {
            result[chr] = (leftPairResult[chr] ?: 0) + (rightPairResult[chr] ?: 0)
        }
        result[middle] = result[middle]!! - 1
        cache[cacheKey] = result
        return result
    }

    val pairs = input.windowed(2)
    val result = mutableMapOf<Char, Long>()
    for (pair in pairs) {
        val pairResult = process(pair, stepsLeft, rules, cache)
        pairResult.forEach { (chr, count) -> result[chr] = (result[chr] ?: 0) + count }
        val firstChar = pair.first()
        result[firstChar] = result[firstChar]!! - 1
    }
    val overallFirstChar = pairs.first().first()
    result[overallFirstChar] = result[overallFirstChar]!! + 1
    return result
}


fun task1(inputString: String): Long {
    return taskImpl(inputString, 10)
}

fun task2(inputString: String): Long {
    return taskImpl(inputString, 40)
}

fun taskImpl(inputString: String, steps: Int): Long {
    val input = parseInput(inputString)

    val charToCount = process(input.polymer, steps, input.rules)
    val biggestAmount = charToCount
        .maxOf { it.value }
    val smallestAmount = charToCount
        .minOf { it.value }
    return biggestAmount - smallestAmount
}
