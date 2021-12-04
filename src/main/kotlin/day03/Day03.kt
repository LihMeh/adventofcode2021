package day03

fun task1(input: String): Int {
    val diagnosticReport = parseInput(input)
    val gammaRate = calculateGammaRate(diagnosticReport)
    val epsilonRate = negate(gammaRate)

    return toDecimal(gammaRate) * toDecimal(epsilonRate)
}

fun task2(input: String): Int {
    val diagnosticReport = parseInput(input)
    val oxyGenRating = calculateMetric(diagnosticReport) { currentValue, positiveCount, negativeCout ->
        if (positiveCount >= negativeCout) {
            currentValue == 1
        } else {
            currentValue == 0
        }
    }
    val co2ScubRating = calculateMetric(diagnosticReport) { currentValue, positiveCount, negativeCout ->
        if (positiveCount >= negativeCout) {
            currentValue == 0
        } else {
            currentValue == 1
        }
    }
    return toDecimal(oxyGenRating) * toDecimal(co2ScubRating)
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

fun calculateMetric(diagnosticalReport: List<List<Int>>, strategy: MetricStrategy): List<Int> {
    check(diagnosticalReport.isNotEmpty())

    val currentState = diagnosticalReport.toMutableList()
    var currentIdx = 0
    while (currentState.size > 1) {
        val bitCount = currentState.first().size
        check(currentIdx < bitCount)

        val positiveBits = currentState
            .map { row -> row[currentIdx] }
            .sum()
        val negativeBits = currentState.size - positiveBits
        currentState.removeAll { row -> !strategy.isShouldPreserveRow(row[currentIdx], positiveBits, negativeBits) }

        currentIdx++
    }
    check(currentState.size == 1)
    return currentState.first()
}

fun interface MetricStrategy {
    fun isShouldPreserveRow(currentValue: Int, positiveCount: Int, negativeCount: Int): Boolean
}