@file:Suppress("EnumEntryName")

package day02

enum class Direction {
    forward,
    down,
    up
}

class Command(val direction: Direction, val arg: Int)

class State(val horizontalPos: Int, val verticalPos: Int, val aim: Int)

fun parseInput(input: String): List<Command> {
    return input
        .splitToSequence("\n")
        .filter { row -> row.isNotBlank() }
        .map { row ->
            val splitRow = row.split(" ")
            val direction = Direction.valueOf(splitRow[0])
            val arg = splitRow[1].toInt()
            Command(direction, arg)
        }
        .toList()
}

fun task1(input: String): Int {
    val commands = parseInput(input)

    val finalState = commands.fold(State(0, 0, 0)) { state, command ->
        when (command.direction) {
            Direction.forward -> State(state.horizontalPos + command.arg, state.verticalPos, state.aim)
            Direction.down -> State(state.horizontalPos, state.verticalPos + command.arg, state.aim)
            Direction.up -> State(state.horizontalPos, state.verticalPos - command.arg, state.aim)
        }
    }

    return finalState.horizontalPos * finalState.verticalPos
}

fun task2(input: String): Int {
    val commands = parseInput(input)

    val finalState = commands.fold(State(0, 0, 0)) { state, command ->
        when (command.direction) {
            Direction.down -> State(state.horizontalPos, state.verticalPos, state.aim + command.arg)
            Direction.up -> State(state.horizontalPos, state.verticalPos, state.aim - command.arg)
            Direction.forward -> State(
                state.horizontalPos + command.arg,
                state.verticalPos + state.aim * command.arg,
                state.aim
            )
        }
    }

    return finalState.horizontalPos * finalState.verticalPos
}
