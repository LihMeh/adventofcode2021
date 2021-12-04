package day04

class Input(val numbers: List<Int>, val cards: List<Card>)

class Card(proposedRows: List<List<Int>>) {
    val rows: List<MutableSet<Int>>
    val columns: List<MutableSet<Int>>

    init {
        check(proposedRows.isNotEmpty()) { "proposedRows is empty" }
        val firstRowSize = proposedRows[0].size
        check(proposedRows.none { it.size != firstRowSize }) { "Not all proposedRows have size $firstRowSize" }

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

    val cards = gameData.cards.toList()
    val numbersQueue = ArrayDeque(gameData.numbers)
    var lastNumber: Int
    do {
        lastNumber = numbersQueue.removeFirst()
        cards.forEach { it.markNumber(lastNumber) }
    } while (cards.none { it.isVictory() })

    val winningCard = cards.first { it.isVictory() }
    return winningCard.getItemSum() * lastNumber
}

fun task2(input: String): Int {
    val gameData = parseInput(input)

    val activeCards = gameData.cards.toMutableList()
    val numberQueue = ArrayDeque(gameData.numbers)

    var lastWinCard: Card? = null
    var lastWinNumber = -1
    while (numberQueue.isNotEmpty() && activeCards.isNotEmpty()) {
        val newNumber = numberQueue.removeFirst()
        activeCards.forEach { it.markNumber(newNumber) }
        val winningCards = activeCards
            .filter { it.isVictory() }
            .toList()
        if (winningCards.isNotEmpty()) {
            lastWinNumber = newNumber
            lastWinCard = winningCards.last()
            activeCards.removeAll(winningCards)
        }
    }
    check(lastWinCard != null && lastWinNumber >= 0) { "no winning card found" }

    return lastWinCard.getItemSum() * lastWinNumber
}