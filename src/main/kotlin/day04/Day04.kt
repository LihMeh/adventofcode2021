package day04

class Input(val numbers: List<Int>, val cards: List<Card>)

class Card(proposedRows: List<List<Int>>) {
    val rows: List<MutableSet<Int>>
    val columns: List<MutableSet<Int>>

    init {
        check(proposedRows.isNotEmpty())
        val firstRowSize = proposedRows[0].size
        check(proposedRows.none { it.size != firstRowSize })
        rows = proposedRows
            .map { row -> row.toMutableSet() }
            .toList()
        columns = (0 until firstRowSize)
            .map { columnIdx ->
                proposedRows
                    .map { row -> row[columnIdx] }
                    .toMutableSet()
            }
            .toList()
    }

    fun markNumber(number: Int) {
        for (row in rows) {
            row.remove(number)
        }
        for (column in columns) {
            column.remove(number)
        }
    }

    fun isVictory(): Boolean {
        return rows.union(columns)
            .any { it.isEmpty() }
    }

    fun getItemSum(): Int {
        return rows.flatten().sum()
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

    val cardsMutable = gameData.cards.toList()
    val numbersQueue = ArrayDeque(gameData.numbers)
    var lastNumber: Int
    do {
        lastNumber = numbersQueue.removeFirst()
        cardsMutable.forEach { it.markNumber(lastNumber) }
    } while (cardsMutable.none { it.isVictory() })

    val winningCard = cardsMutable.first { it.isVictory() }
    return winningCard.getItemSum() * lastNumber
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