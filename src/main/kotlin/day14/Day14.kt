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
    check(input.length >= 2)
    check(stepsLeft >= 0)

    if (stepsLeft == 0) {
        val result = mutableMapOf<Char, Long>()
        for (chr in input) {
            result[chr] = (result[chr] ?: 0) + 1
        }
        return result
    }

    val leftPairResult: Map<Char, Long>
    val rightPairResult: Map<Char, Long>
    val middle: Char
    if (input.length == 2) {
        val left = input.first()
        val right = input.last()
        middle = rules[input]!!
        leftPairResult = process("" + left + middle, stepsLeft - 1, rules)
        rightPairResult = process("" + middle + right, stepsLeft - 1, rules)
    } else {
        val splitIdx = input.length / 2
        val left = input.substring(0, splitIdx + 1)
        val right = input.substring(splitIdx)
        middle = input[splitIdx]
        leftPairResult = process(left, stepsLeft, rules)
        rightPairResult = process(right, stepsLeft, rules)
    }

    val result = mutableMapOf<Char, Long>()
    for (chr in leftPairResult.keys.union(rightPairResult.keys)) {
        result[chr] = (leftPairResult[chr] ?: 0) + (rightPairResult[chr] ?: 0)
    }
    result[middle] = result[middle]!! - 1
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
