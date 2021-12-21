package day21

data class State(
    val boardSize: Int,
    val playerPositions: List<Int>,
    val playerScores: List<Long>,
    val currentPlayer: Int,
    val diesRolled: Int,
)

interface Dice {
    fun next(): Int
}

class DetermenisticDice : Dice {
    var lastValue = 0
    override fun next(): Int {
        var sum = 0
        for (idx in 0 until 3) {
            lastValue++
            sum += lastValue
        }
        return sum
    }
}

fun step(state: State, dice: Dice): State {
    val dicedValue = dice.next()
    val nextDicesRolled = state.diesRolled + 1

    val nextCurrentPlayerPosition = (state.playerPositions[state.currentPlayer] + dicedValue) % state.boardSize
    val nextPositions = ArrayList(state.playerPositions)
    nextPositions[state.currentPlayer] = nextCurrentPlayerPosition

    val nextScores = ArrayList(state.playerScores)
    nextScores[state.currentPlayer] = state.playerScores[state.currentPlayer] + nextCurrentPlayerPosition + 1

    val nextPlayer = (state.currentPlayer + 1) % state.playerPositions.size

    return State(
        state.boardSize,
        nextPositions,
        nextScores,
        nextPlayer,
        nextDicesRolled
    )
}

fun task1(playerStartingPositions: List<Int>): Long {
    check(playerStartingPositions.size >= 2)

    val targetScore = 1000
    var state = State(
        10,
        playerStartingPositions,
        playerStartingPositions.map { 0 },
        0,
        0
    )
    val dice = DetermenisticDice()
    while (state.playerScores.all { it < targetScore }) {
        state = step(state, dice)
    }

    val losingScore = state.playerScores
        .find { it < targetScore }!!

    return losingScore * state.diesRolled * 3
}

class PredefinedDice(val value: Int) : Dice {
    override fun next(): Int {
        return value
    }
}

fun task2(playerStartingPositions: List<Int>): Long {
    val possibleDies = mutableListOf<List<Int>>()
    for (x in 1..3) {
        for (y in 1..3) {
            for (z in 1..3) {
                possibleDies.add(listOf(x, y, z))
            }
        }
    }
    val diceValueToUniverseCount = possibleDies
        .map { it.sum() }
        .groupingBy { it }
        .eachCount()

    val initialState = State(
        10,
        playerStartingPositions,
        playerStartingPositions.map { 0L }.toList(),
        0,
        0
    )
    val statesToProcess = ArrayDeque<Pair<State, Long>>()
    statesToProcess.add(initialState to 1L)

    val playerToWinUniverses = mutableListOf(0L, 0L)

    var reportCounter = 0
    val reportEvery = 1000

    while (statesToProcess.isNotEmpty()) {
        val (prevState, prevUniverses) = statesToProcess.removeFirst()

        diceValueToUniverseCount.forEach { diceValue, diceUniverseCount ->
            val nextState = step(prevState, PredefinedDice(diceValue))
            val nextUniverses = prevUniverses * diceUniverseCount

            if (nextState.playerScores.all { it < 21 }) {
                statesToProcess.addFirst(nextState to nextUniverses)
            } else {
                val winPlayer = if (nextState.playerScores[0] >= 21) 0 else 1
                playerToWinUniverses[winPlayer] = playerToWinUniverses[winPlayer] + nextUniverses
            }
        }

        reportCounter++
        if (reportCounter >= reportEvery) {
            println("wins: ${playerToWinUniverses[0]} / ${playerToWinUniverses[1]} , to process: ${statesToProcess.size}")
            reportCounter -= reportEvery
        }
    }

    return playerToWinUniverses.maxOf { it }
}