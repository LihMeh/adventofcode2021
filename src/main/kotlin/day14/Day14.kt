package day14

data class Input(val polymer: String, val rules: Map<String, String>)

fun parseInput(input: String): Input {
    val (polymer, insertionRulesRaw) = input.split("\n\n")
    val rules = insertionRulesRaw
        .split("\n")
        .map { row ->
            val (left, right) = row.split(" -> ")
            left to right
        }
        .toMap()
    return Input(polymer, rules)
}

fun applyRules(input: String, rules: Map<String, String>): String {
    val result = StringBuilder()
    for (idx in 0 until input.length - 1) {
        result.append(input[idx])
        val ruleKey = input.substring(idx, idx + 2)
        val insertCandidate = rules[ruleKey]
        if (insertCandidate != null) {
            result.append(insertCandidate)
        }
    }
    result.append(input.last())
    return result.toString()
}

fun countChars(input: String): Map<Char, Long> {
    val result = mutableMapOf<Char, Long>()
    for (chr in input) {
        result[chr] = (result[chr] ?: 0) + 1
    }
    return result
}

fun taskImpl(inputString: String, steps: Int): Long {
    val input = parseInput(inputString)

    var polymer = input.polymer
    for (step in 0 until steps) {
        polymer = applyRules(polymer, input.rules)
    }

    val charToCount = countChars(polymer)
    val leastCommonElementCount = charToCount.minOf { it.value }
    val mostCommonElementCount = charToCount.maxOf { it.value }

    return mostCommonElementCount - leastCommonElementCount
}

fun task1(input: String): Long {
    return taskImpl(input, 10)
}

fun task2(input: String): Long {
    return taskImpl(input, 40)
}