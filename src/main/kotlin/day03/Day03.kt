package day03

fun task1(input: String): Int {
    val diagnosticReport = parseInput(input)
    val gammaRate = calculateGammaRate(diagnosticReport)
    val epsilonRate = negate(gammaRate)

    return toDecimal(gammaRate) * toDecimal(epsilonRate)
}

fun task2(input: String): Int {
    return -1
}

fun parseInput(input: String): List<List<Int>> {
    check(input.isNotBlank())
    val parsedInput = input
        .splitToSequence("\n")
        .map { row -> row.map { char -> char.digitToInt() } }
        .toList()
    check(parsedInput.isNotEmpty())
    val firstRowSize = parsedInput.first().size
    check(parsedInput.all { it.size == firstRowSize })
    check(parsedInput.flatten().all { it == 0 || it == 1 })
    return parsedInput
}

fun calculateGammaRate(diagnosticReport: List<List<Int>>): List<Int> {
    val threshold = diagnosticReport.size / 2

    val bitValueSize = diagnosticReport.first().size
    val bitCountsByIdx = (0 until bitValueSize)
        .map { idx -> diagnosticReport.sumOf { it[idx] } }

    return bitCountsByIdx
        .map { bitCount -> if (bitCount > threshold) 1 else 0 }
}

fun negate(input: List<Int>): List<Int> {
    return input.map { if (it == 0) 1 else 0 }
}

fun toDecimal(input: List<Int>): Int {
    var result = 0
    var currentPosValue = 1
    for (currentBitIdx in input.indices.reversed()) {
        result += currentPosValue * input[currentBitIdx]
        currentPosValue *= 2
    }
    return result
}