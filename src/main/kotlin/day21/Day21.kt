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
        lastValue++
        return lastValue
    }
}

fun step(state: State, dice: Dice): State {
    val diceCount = 3
    val dicedValue = generateSequence { dice.next() }.take(diceCount).sum()
    val nextDicesRolled = state.diesRolled + diceCount

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

    return losingScore * state.diesRolled
}

fun task2(playerStartingPositions: List<Int>): Long {
    return 0
}