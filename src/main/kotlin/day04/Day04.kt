package day04

class Input(val numbers: List<Int>, val cards: List<Card>)

class Card(val rows: List<List<Int>>) {
    val columns: List<List<Int>>

    init {
        check(rows.isNotEmpty())
        val firstRowSize = rows.get(0).size
        check(rows.none { it.size != firstRowSize })

        columns = ArrayList(firstRowSize)
        for (idx in 0 until firstRowSize) {
            columns.add(rows
                .map { it[idx] }
                .toList())
        }
    }
}

fun parseInput(input: String): Input {
    val inputBlocks = input.split("\n\n")

    val numbers = inputBlocks.component1()
        .splitToSequence(",")
        .map { it.toInt() }
        .toList()

    val cards = inputBlocks.slice(1 until inputBlocks.size)
        .map { parseCard(it) }
        .toList()

    return Input(numbers, cards)
}

fun parseCard(input: String): Card {
    val rows = input.splitToSequence("\n")
        .map {
            it.split(" ")
                .filter { it.isNotBlank() }
                .map { value -> value.toInt() }
                .toList()
        }
        .toList()
    return Card(rows)
}

fun task1(input: String): Int {
    val gameData = parseInput(input)
    return gameData.numbers.indices
        .mapNotNull { stepIdx ->
            val numbersTillCurrentStep = gameData.numbers.slice(0..stepIdx)
            val numbersTillCurrentStepSet = numbersTillCurrentStep.toSet()

            val winningCard = gameData.cards
                .firstOrNull { card -> isWinningCard(card, numbersTillCurrentStepSet) }

            if (winningCard != null) {
                calculateScore(winningCard, numbersTillCurrentStep)
            } else {
                null
            }
        }
        .first()
}

fun isWinningCard(card: Card, numberSet: Set<Int>): Boolean {
    return card.rows.union(card.columns)
        .any { rowOrColumn -> numberSet.containsAll(rowOrColumn) }
}

fun calculateScore(card: Card, numbers: List<Int>): Int {
    val numberSet = HashSet(numbers)
    val unmarkedNumberSum = card.rows
        .flatten()
        .filter { !numberSet.contains(it) }
        .sum()
    return unmarkedNumberSum * numbers.last()
}

fun task2(input: String): Int {
    val gameData = parseInput(input)

    val activeCards = ArrayList(gameData.cards)
    var lastWinCard: Card? = null
    var lastWinStepIdx = -1
    var currentStepIdx = 0
    while (currentStepIdx < gameData.numbers.size && activeCards.isNotEmpty()) {
        val numbersByCurrentStepSet = gameData.numbers.slice(0..currentStepIdx).toSet()

        val winningCards = activeCards
            .filter { isWinningCard(it, numbersByCurrentStepSet) }
            .toList()
        if (winningCards.isNotEmpty()) {
            lastWinStepIdx = currentStepIdx
            lastWinCard = winningCards.last()
            activeCards.removeAll(winningCards)
        }

        currentStepIdx++
    }
    check(lastWinCard != null)
    check(lastWinStepIdx >= 0)

    return calculateScore(lastWinCard, gameData.numbers.slice(0..lastWinStepIdx))
}